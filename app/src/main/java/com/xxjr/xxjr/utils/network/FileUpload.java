package com.xxjr.xxjr.utils.network;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;

import org.ddq.common.util.JsonUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileUpload {
    HttpURLConnection conn;
    String boundary = "--------httppost123";
    Map<String, String> textParams = new HashMap<String, String>();
    DataOutputStream ds;
    private File file;
    private String urlString;
    private List<File> mFileList;
    private Handler handler;

    public FileUpload(File file, String urlString, Handler handler){
        this.file = file;
        this.urlString = urlString;
        this.handler = handler;
    }
    public FileUpload(List<File> mFileList, String urlString, Handler handler){
        this.mFileList = mFileList;
        this.urlString = urlString;
        this.handler = handler;
    }

    // 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
    public byte[] send() throws Exception {
        handler.sendEmptyMessage(ConstantUtils.UPLOADING);
        Map<String, File> fileparams = new HashMap<String, File>();
        if (file != null) {
            fileparams.put("img", file);
        }else if (mFileList !=null){
            for (int i=0 ; i<mFileList.size();i++){
                fileparams.put(i+"img", mFileList.get(i));
            }
        }
        URL url = new URL(urlString+"&uid="+ MyApplication.uid+"&UUID="+MyApplication.device);
        System.out.println(url);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(100000); //连接超时为10秒
        conn.setRequestMethod("POST");
//        conn.addRequestProperty("uid",MyApplication.getUid());
        conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
        conn.connect();
        ds = new DataOutputStream(conn.getOutputStream());

        Set<String> keySet = fileparams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String name = it.next();
            File value = fileparams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
           // ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }

        Set<String> keySet1 = textParams.keySet();
        for (Iterator<String> it = keySet1.iterator(); it.hasNext();) {
            String name = it.next();
            String value = textParams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
                    + "\"\r\n");
            ds.writeBytes("\r\n");
            ds.writeBytes(encode(value) + "\r\n");
        }

        ds.writeBytes("--" + boundary + "--" + "\r\n");
        ds.writeBytes("\r\n");

        InputStream in = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }

        setReturnDatas(out.toString());
        conn.disconnect();
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.UPLOAD_RETURN_DATAS,out.toString());
        message.setData(bundle);
        message.what = ConstantUtils.UPLOAD_SUCCESS;
        handler.sendMessage(message);
        return out.toByteArray();
    }

    //把文件转换成字节数组
    private byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }
    // 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
    private String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }

    public void setReturnDatas(String res){
        jsonDatas(res);
    }
    private void jsonDatas(String respone){
        Map<String,Object> map = JsonUtil.getInstance().json2Object(respone.toString(),Map.class);
        List<String> returnImaList = (List<String>) map.get("fileId");
        sendMessgeJsonDatas( returnImaList);
    }
    private void sendMessgeJsonDatas(List<String> returnImaList){
        Message message = handler.obtainMessage();
        message.what = ConstantUtils.UPLOAD_RETURN_DATA;
        Bundle bundle = new Bundle();
        bundle.putSerializable("returnImaList",(Serializable)returnImaList);
        message.setData(bundle);
        handler.sendMessage(message);
    }

   public String returDatas(HttpURLConnection conn) throws IOException {
       StringBuffer strBuf = new StringBuffer();
       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       String line = null;
       while ((line = reader.readLine()) != null) {
           strBuf.append(line)/*.append("\n")*/;
       }
       String res = strBuf.toString();
       reader.close();

       return res;

}

}
