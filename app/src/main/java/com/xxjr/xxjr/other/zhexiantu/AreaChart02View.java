/**
 * Copyright 2014  XCL-Charts
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.5
 */
package com.xxjr.xxjr.other.zhexiantu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.xxjr.xxjr.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName AreaChart02View
 * @Description 平滑面积图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class AreaChart02View extends DemoView {

    private List<String> label = new ArrayList<>();// 这个东西是x轴的显示
    private List<Double> yData = new ArrayList<>() ; //  y轴坐标

    private String TAG = "AreaChart02View";
    private AreaChart chart = new AreaChart();
    //标签集合
    private LinkedList<String> mLabels = new LinkedList<String>();
    //数据集合
    private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();//  线 包含区域面积的  容器

    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();//  线的容器

    public AreaChart02View(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public AreaChart02View(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initView();
    }

    public AreaChart02View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void initView() {
        chartLabels();
        chartDataSet();
        chartRender();

        //綁定手势滑动事件
//        this.bindTouch(this, chart);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
            //轴数据源
            //标签轴
            chart.setCategories(mLabels);
            //数据轴
            chart.setDataSource(mDataset);
            //仅横向平移
            chart.setPlotPanMode(XEnum.PanMode.FREE);
            //数据轴最大值
            chart.getDataAxis().setAxisMax(100);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(20);



            //网格
//            chart.getPlotGrid().showHorizontalLines();
//            chart.getPlotGrid().showVerticalLines();
            //把顶轴和右轴隐藏
//            chart.hideTopAxis();
//            chart.hideRightAxis();
            //把轴线和刻度线给隐藏起来
//            chart.getDataAxis().hideAxisLine();
            chart.getDataAxis().hideTickMarks();
//            chart.getCategoryAxis().hideAxisLine();
//            chart.getCategoryAxis().hideTickMarks();

            //标题
           /* chart.setTitle("平滑区域图");
            chart.addSubtitle("(XCL-Charts Demo)");
            //轴标题
            chart.getAxisTitle().setLowerTitle("(年份)");

            //透明度
            chart.setAreaAlpha(180);
            //显示图例
            chart.getPlotLegend().show();*/

            //激活点击监听
            chart.ActiveListenItemClick();
            //为了让触发更灵敏，可以扩大5px的点击监听范围
            chart.extPointClickRange(5);

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });

            //设定交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }
            });

            //扩大显示宽度
            //chart.getPlotArea().extWidth(100f);

//            chart.disablePanMode(); //test

            //todo 标示线
               /* CustomLineData line1 = new CustomLineData("标识线",60d,Color.RED,7);
				line1.setCustomLineCap(XEnum.DotStyle.CROSS);
				line1.setLabelHorizontalPostion(Align.CENTER);
				line1.setLabelOffset(15);
				line1.getLineLabelPaint().setColor(Color.RED);
				mCustomLineDataset.add(line1);
				chart.setCustomLines(mCustomLineDataset);*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    private void chartDataSet() {
        //将标签与对应的数据集分别绑定
        //标签对应的数据集
        //todo
		/*List<Double> dataSeries1= new LinkedList<Double>();
		dataSeries1.add((double)0);//0.001);  //25  0.001
		dataSeries1.add((double)50); 
		dataSeries1.add((double)51); 
		dataSeries1.add((double)60);
		dataSeries1.add((double)0); //45*/


        //todo
		/*List<Double> dataSeries3 = new LinkedList<Double>();
		//dataSeries3.add((double)50); //50
		//dataSeries3.add((double)62);
		dataSeries3.add((double)70);  //70
		dataSeries3.add((double)90);
		//dataSeries3.add((double)75);


		//设置每条线各自的显示属性
		//key,数据集,线颜色,区域颜色
		//  todo 隐藏
		AreaData line1 = new AreaData("小熊",dataSeries1,
				Color.parseColor("#4CA200"),Color.WHITE,Color.parseColor("#80C007"));
		//不显示点
		line1.setDotStyle(XEnum.DotStyle.HIDE);//隐藏图形
		//line1.setDotStyle(XEnum.DotStyle.RECT);
		//line1.setLabelVisible(true);
		line1.setApplayGradient(true);
		line1.setAreaBeginColor(Color.WHITE);
		line1.setAreaEndColor(Color.parseColor("#80C007"));*/



        AreaData line2 = new AreaData("微店统计", yData,
                Color.parseColor("#2ec7c9"),
                Color.parseColor("#93dfe0"));
        //设置点标签
        line2.setDotStyle(XEnum.DotStyle.RING);

        //line2.setApplayGradient(true);
        //line2.setGradientMode(Shader.TileMode.MIRROR);

        //Color.RED,Color.WHITE  Color.WHITE,Color.RED、
        //  todo
		/*AreaData line3 = new AreaData("小小小熊",dataSeries3,
		Color.parseColor("#B6D3FD"),Color.parseColor("#5394EB"));
		line3.setDotStyle(XEnum.DotStyle.HIDE);
		line3.setApplayGradient(true);*/

//		mDataset.add(line3);
//		mDataset.add(line1);
        mDataset.add(line2);

		/*List<Double> dataSeries4 = new LinkedList<Double>();
		dataSeries4.add((double)0); 
		dataSeries4.add((double)55); 
		dataSeries4.add((double)0); 	
		dataSeries4.add((double)0); 
		dataSeries4.add((double)65); 
		
		
		List<Double> dataSeries5 = new LinkedList<Double>();			
		dataSeries5.add((double)36); 
		dataSeries5.add((double)37); 
		dataSeries5.add((double)0); 	
		dataSeries5.add((double)0); 
		dataSeries5.add((double)0); 
		
		
		List<Double> dataSeries6 = new LinkedList<Double>();			
		dataSeries6.add((double)36); 
		dataSeries6.add((double)0); 
		dataSeries6.add((double)0); 	
		dataSeries6.add((double)0); 
		dataSeries6.add((double)73); 
		
		AreaData line4 = new AreaData("line4",dataSeries4,
				Color.BLUE,Color.BLUE); 
		//设置线上每点对应标签的颜色
		//line3.getDotLabelPaint().setColor(Color.YELLOW);
		line4.setLineStyle(XEnum.LineStyle.DOT);	
		
		AreaData line5 = new AreaData("line5",dataSeries5,
				Color.CYAN,Color.CYAN); 
		//设置线上每点对应标签的颜色
		//line3.getDotLabelPaint().setColor(Color.YELLOW);
		line5.setLineStyle(XEnum.LineStyle.SOLID);	
		
		AreaData line6 = new AreaData("line6",dataSeries6,
				Color.YELLOW,Color.YELLOW); 
		//设置线上每点对应标签的颜色
		//line3.getDotLabelPaint().setColor(Color.YELLOW);
		line6.setLineStyle(XEnum.LineStyle.DASH);	
		
		//设置线上点的大小
		//line6.setDotRadius(radius)
		//设置线的粗线
		//line6.getLinePaint().setStrokeWidth(5);
				
		mDataset.add(line4);		
		mDataset.add(line5);
		mDataset.add(line6);*/

    }

    //  显示的x轴坐标的东西
    private void chartLabels() {
        for (int i = 0; i<label.size();i++){
             mLabels.add(label.get(i));
        }
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<Double> getyData() {
        return yData;
    }

    //  y轴数据
    public void setyData(List<Double> yData) {
        this.yData = yData;
//        chartDataSet();
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }


        return true;
    }


    //触发监听
    private void triggerClick(float x, float y) {

        PointPosition record = chart.getPositionRecord(x, y);
        if (null == record) return;

        AreaData lData = mDataset.get(record.getDataID());
        Double lValue = lData.getLinePoint().get(record.getDataChildID());

        Toast.makeText(this.getContext(),
                record.getPointInfo() +
                        " Key:" + lData.getLineKey() +
                        " Label:" + lData.getLabel() +
                        " Current Value:" + Double.toString(lValue),
                Toast.LENGTH_SHORT).show();
    }
}

