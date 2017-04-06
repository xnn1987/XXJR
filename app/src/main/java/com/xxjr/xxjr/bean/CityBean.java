package com.xxjr.xxjr.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class CityBean implements Serializable{

    /**
     * forward : null
     * errorCode : null
     * message : null
     * page : {"everyPage":10,"totalPage":1,"currentPage":1,"beginIndex":0,"recordCount":0,"totalRecords":0}
     * success : true
     * attr : {"areas":[{"citys":[{"nameEn":"BJ","nameCn":"北京市","pid":351,"code":"1100","shortName":"北京","tempPcode":"11"}],"provice":"北京市","code":"11"},{"citys":[{"nameEn":"TJ","nameCn":"天津市","pid":223,"code":"1200","shortName":"天津","tempPcode":"12"}],"provice":"天津市","code":"12"},{"citys":[{"nameEn":"SJZ","nameCn":"石家庄市","pid":98,"code":"1301","shortName":"石家庄","tempPcode":"13"},{"nameEn":"TS","nameCn":"唐山市","pid":98,"code":"1302","shortName":"唐山","tempPcode":"13"},{"nameEn":"QHD","nameCn":"秦皇岛市","pid":98,"code":"1303","shortName":"秦皇岛","tempPcode":"13"},{"nameEn":"HD","nameCn":"邯郸市","pid":98,"code":"1304","shortName":"邯郸","tempPcode":"13"},{"nameEn":"XT","nameCn":"邢台市","pid":98,"code":"1305","shortName":"邢台","tempPcode":"13"},{"nameEn":"BD","nameCn":"保定市","pid":98,"code":"1306","shortName":"保定","tempPcode":"13"},{"nameEn":"ZJK","nameCn":"张家口市","pid":98,"code":"1307","shortName":"张家口","tempPcode":"13"},{"nameEn":"CD","nameCn":"承德市","pid":98,"code":"1308","shortName":"承德","tempPcode":"13"},{"nameEn":"CZ","nameCn":"沧州市","pid":98,"code":"1309","shortName":"沧州","tempPcode":"13"},{"nameEn":"LF","nameCn":"廊坊市","pid":98,"code":"1310","shortName":"廊坊","tempPcode":"13"},{"nameEn":"HS","nameCn":"衡水市","pid":98,"code":"1311","shortName":"衡水","tempPcode":"13"}],"provice":"河北省","code":"13"},{"citys":[{"nameEn":"TY","nameCn":"太原市","pid":86,"code":"1401","shortName":"太原","tempPcode":"14"},{"nameEn":"DT","nameCn":"大同市","pid":86,"code":"1402","shortName":"大同","tempPcode":"14"},{"nameEn":"YQ","nameCn":"阳泉市","pid":86,"code":"1403","shortName":"阳泉","tempPcode":"14"},{"nameEn":"CZ","nameCn":"长治市","pid":86,"code":"1404","shortName":"长治","tempPcode":"14"},{"nameEn":"XC","nameCn":"晋城市","pid":86,"code":"1405","shortName":"晋城","tempPcode":"14"},{"nameEn":"SZ","nameCn":"朔州市","pid":86,"code":"1406","shortName":"朔州","tempPcode":"14"},{"nameEn":"JZ","nameCn":"晋中市","pid":86,"code":"1407","shortName":"晋中","tempPcode":"14"},{"nameEn":"YC","nameCn":"运城市","pid":86,"code":"1408","shortName":"运城","tempPcode":"14"},{"nameEn":"XZ","nameCn":"忻州市","pid":86,"code":"1409","shortName":"忻州","tempPcode":"14"},{"nameEn":"LF","nameCn":"临汾市","pid":86,"code":"1410","shortName":"临汾","tempPcode":"14"},{"nameEn":"LL","nameCn":"吕梁市","pid":86,"code":"1411","shortName":"吕梁","tempPcode":"14"}],"provice":"山西省","code":"14"},{"citys":[{"nameEn":"HHHT","nameCn":"呼和浩特市","pid":35,"code":"1501","shortName":"呼和浩特","tempPcode":"15"},{"nameEn":"BT","nameCn":"包头市","pid":35,"code":"1502","shortName":"包头","tempPcode":"15"},{"nameEn":"WH","nameCn":"乌海市","pid":35,"code":"1503","shortName":"乌海","tempPcode":"15"},{"nameEn":"CF","nameCn":"赤峰市","pid":35,"code":"1504","shortName":"赤峰","tempPcode":"15"},{"nameEn":"TL","nameCn":"通辽市","pid":35,"code":"1505","shortName":"通辽","tempPcode":"15"},{"nameEn":"EEDS","nameCn":"鄂尔多斯市","pid":35,"code":"1506","shortName":"鄂尔多斯","tempPcode":"15"},{"nameEn":"HLBE","nameCn":"呼伦贝尔市","pid":35,"code":"1507","shortName":"呼伦贝尔","tempPcode":"15"},{"nameEn":"BYNE","nameCn":"巴彦淖尔市","pid":35,"code":"1508","shortName":"巴彦淖尔","tempPcode":"15"},{"nameEn":"WLC","nameCn":"乌兰察布市","pid":35,"code":"1509","shortName":"乌兰察布","tempPcode":"15"},{"nameEn":"XAM","nameCn":"兴安盟","pid":35,"code":"1522","shortName":"","tempPcode":"15"},{"nameEn":"XLGLM","nameCn":"锡林郭勒盟","pid":35,"code":"1525","shortName":"","tempPcode":"15"},{"nameEn":"ALSM","nameCn":"阿拉善盟","pid":35,"code":"1529","shortName":"","tempPcode":"15"}],"provice":"内蒙古","code":"15"},{"citys":[{"nameEn":"SY","nameCn":"沈阳市","pid":2,"code":"2101","shortName":"沈阳","tempPcode":"21"},{"nameEn":"DL","nameCn":"大连市","pid":2,"code":"2102","shortName":"大连","tempPcode":"21"},{"nameEn":"AS","nameCn":"鞍山市","pid":2,"code":"2103","shortName":"鞍山","tempPcode":"21"},{"nameEn":"FS","nameCn":"抚顺市","pid":2,"code":"2104","shortName":"抚顺","tempPcode":"21"},{"nameEn":"BX","nameCn":"本溪市","pid":2,"code":"2105","shortName":"本溪","tempPcode":"21"},{"nameEn":"DD","nameCn":"丹东市","pid":2,"code":"2106","shortName":"丹东","tempPcode":"21"},{"nameEn":"JZ","nameCn":"锦州市","pid":2,"code":"2107","shortName":"锦州","tempPcode":"21"},{"nameEn":"YK","nameCn":"营口市","pid":2,"code":"2108","shortName":"营口","tempPcode":"21"},{"nameEn":"FX","nameCn":"阜新市","pid":2,"code":"2109","shortName":"阜新","tempPcode":"21"},{"nameEn":"LY","nameCn":"辽阳市","pid":2,"code":"2110","shortName":"辽阳","tempPcode":"21"},{"nameEn":"BJ","nameCn":"盘锦市","pid":2,"code":"2111","shortName":"盘锦","tempPcode":"21"},{"nameEn":"TL","nameCn":"铁岭市","pid":2,"code":"2112","shortName":"铁岭","tempPcode":"21"},{"nameEn":"CY","nameCn":"朝阳市","pid":2,"code":"2113","shortName":"朝阳","tempPcode":"21"},{"nameEn":"HLD","nameCn":"葫芦岛市","pid":2,"code":"2114","shortName":"葫芦岛","tempPcode":"21"}],"provice":"辽宁省","code":"21"},{"citys":[{"nameEn":"CC","nameCn":"长春市","pid":189,"code":"2201","shortName":"长春","tempPcode":"22"},{"nameEn":"JL","nameCn":"吉林市","pid":189,"code":"2202","shortName":"吉林","tempPcode":"22"},{"nameEn":"SP","nameCn":"四平市","pid":189,"code":"2203","shortName":"四平","tempPcode":"22"},{"nameEn":"LY","nameCn":"辽源市","pid":189,"code":"2204","shortName":"辽源","tempPcode":"22"},{"nameEn":"TH","nameCn":"通化市","pid":189,"code":"2205","shortName":"通化","tempPcode":"22"},{"nameEn":"BS","nameCn":"白山市","pid":189,"code":"2206","shortName":"白山","tempPcode":"22"},{"nameEn":"SY","nameCn":"松原市","pid":189,"code":"2207","shortName":"松原","tempPcode":"22"},{"nameEn":"BC","nameCn":"白城市","pid":189,"code":"2208","shortName":"白城","tempPcode":"22"},{"nameEn":"YB","nameCn":"延边朝鲜族自治州","pid":189,"code":"2224","shortName":"延边朝鲜族","tempPcode":"22"}],"provice":"吉林省","code":"22"},{"citys":[{"nameEn":"HEB","nameCn":"哈尔滨市","pid":113,"code":"2301","shortName":"哈尔滨","tempPcode":"23"},{"nameEn":"QQHE","nameCn":"齐齐哈尔市","pid":113,"code":"2302","shortName":"齐齐哈尔","tempPcode":"23"},{"nameEn":"JX","nameCn":"鸡西市","pid":113,"code":"2303","shortName":"鸡西","tempPcode":"23"},{"nameEn":"HG","nameCn":"鹤岗市","pid":113,"code":"2304","shortName":"鹤岗","tempPcode":"23"},{"nameEn":"SYS","nameCn":"双鸭山市","pid":113,"code":"2305","shortName":"双鸭山","tempPcode":"23"},{"nameEn":"DQ","nameCn":"大庆市","pid":113,"code":"2306","shortName":"大庆","tempPcode":"23"},{"nameEn":"YC","nameCn":"伊春市","pid":113,"code":"2307","shortName":"伊春","tempPcode":"23"},{"nameEn":"JMS","nameCn":"佳木斯市","pid":113,"code":"2308","shortName":"佳木斯","tempPcode":"23"},{"nameEn":"QTH","nameCn":"七台河市","pid":113,"code":"2309","shortName":"七台河","tempPcode":"23"},{"nameEn":"MDJ","nameCn":"牡丹江市","pid":113,"code":"2310","shortName":"牡丹江","tempPcode":"23"},{"nameEn":"HH","nameCn":"黑河市","pid":113,"code":"2311","shortName":"黑河","tempPcode":"23"},{"nameEn":"SH","nameCn":"绥化市","pid":113,"code":"2312","shortName":"绥化","tempPcode":"23"},{"nameEn":"DXAL","nameCn":"大兴安岭地区","pid":113,"code":"2327","shortName":"大兴安岭","tempPcode":"23"}],"provice":"黑龙江省","code":"23"},{"citys":[{"nameEn":"SH","nameCn":"上海市","pid":199,"code":"3101","shortName":"上海","tempPcode":"31"}],"provice":"上海市","code":"31"},{"citys":[{},{"nameEn":"WX","nameCn":"无锡市","pid":175,"code":"3202","shortName":"无锡","tempPcode":"32"},{"nameEn":"XZ","nameCn":"徐州市","pid":175,"code":"3203","shortName":"徐州","tempPcode":"32"},{"nameEn":"CZ","nameCn":"常州市","pid":175,"code":"3204","shortName":"常州","tempPcode":"32"},{"nameEn":"SZ","nameCn":"苏州市","pid":175,"code":"3205","shortName":"苏州","tempPcode":"32"},{"nameEn":"NT","nameCn":"南通市","pid":175,"code":"3206","shortName":"南通","tempPcode":"32"},{"nameEn":"LYG","nameCn":"连云港市","pid":175,"code":"3207","shortName":"连云港","tempPcode":"32"},{"nameEn":"HA","nameCn":"淮安市","pid":175,"code":"3208","shortName":"淮安","tempPcode":"32"},{"nameEn":"YC","nameCn":"盐城市","pid":175,"code":"3209","shortName":"盐城","tempPcode":"32"},{"nameEn":"YZ","nameCn":"扬州市","pid":175,"code":"3210","shortName":"扬州","tempPcode":"32"},{"nameEn":"ZJ","nameCn":"镇江市","pid":175,"code":"3211","shortName":"镇江","tempPcode":"32"},{"nameEn":"TZ","nameCn":"泰州市","pid":175,"code":"3212","shortName":"泰州","tempPcode":"32"},{"nameEn":"XQ","nameCn":"宿迁市","pid":175,"code":"3213","shortName":"宿迁","tempPcode":"32"}],"provice":"江苏省","code":"32"},{"citys":[{"nameEn":"HZ","nameCn":"杭州市","pid":265,"code":"3301","shortName":"杭州","tempPcode":"33"},{"nameEn":"NB","nameCn":"宁波市","pid":265,"code":"3302","shortName":"宁波","tempPcode":"33"},{"nameEn":"WZ","nameCn":"温州市","pid":265,"code":"3303","shortName":"温州","tempPcode":"33"},{"nameEn":"JX","nameCn":"嘉兴市","pid":265,"code":"3304","shortName":"嘉兴","tempPcode":"33"},{"nameEn":"HZ","nameCn":"湖州市","pid":265,"code":"3305","shortName":"湖州","tempPcode":"33"},{"nameEn":"SX","nameCn":"绍兴市","pid":265,"code":"3306","shortName":"绍兴","tempPcode":"33"},{"nameEn":"JH","nameCn":"金华市","pid":265,"code":"3307","shortName":"金华","tempPcode":"33"},{"nameEn":"QZ","nameCn":"衢州市","pid":265,"code":"3308","shortName":"衢州","tempPcode":"33"},{"nameEn":"ZS","nameCn":"舟山市","pid":265,"code":"3309","shortName":"舟山","tempPcode":"33"},{"nameEn":"TZ","nameCn":"台州市","pid":265,"code":"3310","shortName":"台州","tempPcode":"33"},{"nameEn":"LS","nameCn":"丽水市","pid":265,"code":"3311","shortName":"丽水","tempPcode":"33"}],"provice":"浙江省","code":"33"},{"citys":[{"nameEn":"HF","nameCn":"合肥市","pid":353,"code":"3401","shortName":"合肥","tempPcode":"34"},{"nameEn":"WH","nameCn":"芜湖市","pid":353,"code":"3402","shortName":"芜湖","tempPcode":"34"},{"nameEn":"BB","nameCn":"蚌埠市","pid":353,"code":"3403","shortName":"蚌埠","tempPcode":"34"},{"nameEn":"HN","nameCn":"淮南市","pid":353,"code":"3404","shortName":"淮南","tempPcode":"34"},{"nameEn":"MAS","nameCn":"马鞍山市","pid":353,"code":"3405","shortName":"马鞍山","tempPcode":"34"},{"nameEn":"HB","nameCn":"淮北市","pid":353,"code":"3406","shortName":"淮北","tempPcode":"34"},{"nameEn":"TL","nameCn":"铜陵市","pid":353,"code":"3407","shortName":"铜陵","tempPcode":"34"},{"nameEn":"AQ","nameCn":"安庆市","pid":353,"code":"3408","shortName":"安庆","tempPcode":"34"},{"nameEn":"HS","nameCn":"黄山市","pid":353,"code":"3410","shortName":"黄山","tempPcode":"34"},{"nameEn":"CZ","nameCn":"滁州市","pid":353,"code":"3411","shortName":"滁州","tempPcode":"34"},{"nameEn":"FY","nameCn":"阜阳市","pid":353,"code":"3412","shortName":"阜阳","tempPcode":"34"},{"nameEn":"SZ","nameCn":"宿州市","pid":353,"code":"3413","shortName":"宿州","tempPcode":"34"},{"nameEn":"CH","nameCn":"巢湖市","pid":353,"code":"3414","shortName":"","tempPcode":"34"},{"nameEn":"LA","nameCn":"六安市","pid":353,"code":"3415","shortName":"六安","tempPcode":"34"},{"nameEn":"HZ","nameCn":"亳州市","pid":353,"code":"3416","shortName":"亳州","tempPcode":"34"},{"nameEn":"CZ","nameCn":"池州市","pid":353,"code":"3417","shortName":"池州","tempPcode":"34"},{"nameEn":"XC","nameCn":"宣城市","pid":353,"code":"3418","shortName":"宣城","tempPcode":"34"}],"provice":"安徽省","code":"34"},{"citys":[{"nameEn":"FZ","nameCn":"福州市","pid":341,"code":"3501","shortName":"福州","tempPcode":"35"},{"nameEn":"XM","nameCn":"厦门市","pid":341,"code":"3502","shortName":"厦门","tempPcode":"35"},{"nameEn":"PT","nameCn":"莆田市","pid":341,"code":"3503","shortName":"莆田","tempPcode":"35"},{"nameEn":"SM","nameCn":"三明市","pid":341,"code":"3504","shortName":"三明","tempPcode":"35"},{"nameEn":"XZ","nameCn":"泉州市","pid":341,"code":"3505","shortName":"泉州","tempPcode":"35"},{"nameEn":"ZZ","nameCn":"漳州市","pid":341,"code":"3506","shortName":"漳州","tempPcode":"35"},{"nameEn":"NP","nameCn":"南平市","pid":341,"code":"3507","shortName":"南平","tempPcode":"35"},{"nameEn":"LY","nameCn":"龙岩市","pid":341,"code":"3508","shortName":"龙岩","tempPcode":"35"},{"nameEn":"ND","nameCn":"宁德市","pid":341,"code":"3509","shortName":"宁德","tempPcode":"35"}],"provice":"福建省","code":"35"},{"citys":[{"nameEn":"NC","nameCn":"南昌市","pid":17,"code":"3601","shortName":"南昌","tempPcode":"36"},{"nameEn":"JDZ","nameCn":"景德镇市","pid":17,"code":"3602","shortName":"景德镇","tempPcode":"36"},{"nameEn":"PX","nameCn":"萍乡市","pid":17,"code":"3603","shortName":"萍乡","tempPcode":"36"},{"nameEn":"JJ","nameCn":"九江市","pid":17,"code":"3604","shortName":"九江","tempPcode":"36"},{"nameEn":"XY","nameCn":"新余市","pid":17,"code":"3605","shortName":"新余","tempPcode":"36"},{"nameEn":"YT","nameCn":"鹰潭市","pid":17,"code":"3606","shortName":"鹰潭","tempPcode":"36"},{"nameEn":"GZ","nameCn":"赣州市","pid":17,"code":"3607","shortName":"赣州","tempPcode":"36"},{"nameEn":"JA","nameCn":"吉安市","pid":17,"code":"3608","shortName":"吉安","tempPcode":"36"},{"nameEn":"YC","nameCn":"宜春市","pid":17,"code":"3609","shortName":"宜春","tempPcode":"36"},{"nameEn":"FZ","nameCn":"抚州市","pid":17,"code":"3610","shortName":"抚州","tempPcode":"36"},{"nameEn":"SR","nameCn":"上饶市","pid":17,"code":"3611","shortName":"上饶","tempPcode":"36"}],"provice":"江西省","code":"36"},{"citys":[{"nameEn":"JN","nameCn":"济南市","pid":48,"code":"3701","shortName":"济南","tempPcode":"37"},{"nameEn":"QD","nameCn":"青岛市","pid":48,"code":"3702","shortName":"青岛","tempPcode":"37"},{"nameEn":"ZB","nameCn":"淄博市","pid":48,"code":"3703","shortName":"淄博","tempPcode":"37"},{"nameEn":"ZZ","nameCn":"枣庄市","pid":48,"code":"3704","shortName":"枣庄","tempPcode":"37"},{"nameEn":"DY","nameCn":"东营市","pid":48,"code":"3705","shortName":"东营","tempPcode":"37"},{"nameEn":"YT","nameCn":"烟台市","pid":48,"code":"3706","shortName":"烟台","tempPcode":"37"},{"nameEn":"LF","nameCn":"潍坊市","pid":48,"code":"3707","shortName":"潍坊","tempPcode":"37"},{"nameEn":"JN","nameCn":"济宁市","pid":48,"code":"3708","shortName":"济宁","tempPcode":"37"},{"nameEn":"TA","nameCn":"泰安市","pid":48,"code":"3709","shortName":"泰安","tempPcode":"37"},{"nameEn":"WH","nameCn":"威海市","pid":48,"code":"3710","shortName":"威海","tempPcode":"37"},{"nameEn":"RZ","nameCn":"日照市","pid":48,"code":"3711","shortName":"日照","tempPcode":"37"},{"nameEn":"LW","nameCn":"莱芜市","pid":48,"code":"3712","shortName":"莱芜","tempPcode":"37"},{"nameEn":"LF","nameCn":"临沂市","pid":48,"code":"3713","shortName":"临沂","tempPcode":"37"},{"nameEn":"CD","nameCn":"德州市","pid":48,"code":"3714","shortName":"德州","tempPcode":"37"},{"nameEn":"LC","nameCn":"聊城市","pid":48,"code":"3715","shortName":"聊城","tempPcode":"37"},{"nameEn":"BZ","nameCn":"滨州市","pid":48,"code":"3716","shortName":"滨州","tempPcode":"37"},{"nameEn":"HZ","nameCn":"菏泽市","pid":48,"code":"3729","shortName":"菏泽","tempPcode":"37"}],"provice":"山东省","code":"37"},{"citys":[{"nameEn":"ZZ","nameCn":"郑州市","pid":127,"code":"4101","shortName":"郑州","tempPcode":"41"},{"nameEn":"KF","nameCn":"开封市","pid":127,"code":"4102","shortName":"开封","tempPcode":"41"},{"nameEn":"LY","nameCn":"洛阳市","pid":127,"code":"4103","shortName":"洛阳","tempPcode":"41"},{"nameEn":"PDS","nameCn":"平顶山市","pid":127,"code":"4104","shortName":"平顶山","tempPcode":"41"},{"nameEn":"AY","nameCn":"安阳市","pid":127,"code":"4105","shortName":"安阳","tempPcode":"41"},{"nameEn":"HB","nameCn":"鹤壁市","pid":127,"code":"4106","shortName":"鹤壁","tempPcode":"41"},{"nameEn":"XX","nameCn":"新乡市","pid":127,"code":"4107","shortName":"新乡","tempPcode":"41"},{"nameEn":"JZ","nameCn":"焦作市","pid":127,"code":"4108","shortName":"焦作","tempPcode":"41"},{"nameEn":"PY","nameCn":"濮阳市","pid":127,"code":"4109","shortName":"濮阳","tempPcode":"41"},{"nameEn":"XC","nameCn":"许昌市","pid":127,"code":"4110","shortName":"许昌","tempPcode":"41"},{"nameEn":"LH","nameCn":"漯河市","pid":127,"code":"4111","shortName":"漯河","tempPcode":"41"},{"nameEn":"SMX","nameCn":"三门峡市","pid":127,"code":"4112","shortName":"三门峡","tempPcode":"41"},{"nameEn":"NY","nameCn":"南阳市","pid":127,"code":"4113","shortName":"南阳","tempPcode":"41"},{"nameEn":"SQ","nameCn":"商丘市","pid":127,"code":"4114","shortName":"商丘","tempPcode":"41"},{"nameEn":"XY","nameCn":"信阳市","pid":127,"code":"4115","shortName":"信阳","tempPcode":"41"},{"nameEn":"ZK","nameCn":"周口市","pid":127,"code":"4116","shortName":"周口","tempPcode":"41"},{"nameEn":"ZMD","nameCn":"驻马店市","pid":127,"code":"4117","shortName":"驻马店","tempPcode":"41"}],"provice":"河南省","code":"41"},{"citys":[{"nameEn":"WH","nameCn":"武汉市","pid":160,"code":"4201","shortName":"武汉","tempPcode":"42"},{"nameEn":"HS","nameCn":"黄石市","pid":160,"code":"4202","shortName":"黄石","tempPcode":"42"},{"nameEn":"SY","nameCn":"十堰市","pid":160,"code":"4203","shortName":"十堰","tempPcode":"42"},{"nameEn":"YC","nameCn":"宜昌市","pid":160,"code":"4205","shortName":"宜昌","tempPcode":"42"},{"nameEn":"XF","nameCn":"襄樊市","pid":160,"code":"4206","shortName":"襄樊","tempPcode":"42"},{"nameEn":"EZ","nameCn":"鄂州市","pid":160,"code":"4207","shortName":"鄂州","tempPcode":"42"},{"nameEn":"JM","nameCn":"荆门市","pid":160,"code":"4208","shortName":"荆门","tempPcode":"42"},{"nameEn":"XG","nameCn":"孝感市","pid":160,"code":"4209","shortName":"孝感","tempPcode":"42"},{"nameEn":"JZ","nameCn":"荆州市","pid":160,"code":"4210","shortName":"荆州","tempPcode":"42"},{"nameEn":"HG","nameCn":"黄冈市","pid":160,"code":"4211","shortName":"黄冈","tempPcode":"42"},{"nameEn":"XN","nameCn":"咸宁市","pid":160,"code":"4212","shortName":"咸宁","tempPcode":"42"},{"nameEn":"SZ","nameCn":"随州市","pid":160,"code":"4213","shortName":"随州","tempPcode":"42"},{"nameEn":"ESS","nameCn":"恩施土家族苗族自治州","pid":160,"code":"4228","shortName":"恩施土家族苗族","tempPcode":"42"},{"nameEn":"","nameCn":"省直辖行政单位","pid":160,"code":"4290","shortName":"","tempPcode":"42"}],"provice":"湖北省","code":"42"},{"citys":[{"nameEn":"CS","nameCn":"长沙市","pid":145,"code":"4301","shortName":"长沙","tempPcode":"43"},{"nameEn":"ZZ","nameCn":"株州市","pid":145,"code":"4302","shortName":"株州","tempPcode":"43"},{"nameEn":"XT","nameCn":"湘潭市","pid":145,"code":"4303","shortName":"湘潭","tempPcode":"43"},{"nameEn":"HY","nameCn":"衡阳市","pid":145,"code":"4304","shortName":"衡阳","tempPcode":"43"},{"nameEn":"SY","nameCn":"邵阳市","pid":145,"code":"4305","shortName":"邵阳","tempPcode":"43"},{"nameEn":"YY","nameCn":"岳阳市","pid":145,"code":"4306","shortName":"岳阳","tempPcode":"43"},{"nameEn":"CD","nameCn":"常德市","pid":145,"code":"4307","shortName":"常德","tempPcode":"43"},{"nameEn":"ZJJ","nameCn":"张家界市","pid":145,"code":"4308","shortName":"张家界","tempPcode":"43"},{"nameEn":"YY","nameCn":"益阳市","pid":145,"code":"4309","shortName":"益阳","tempPcode":"43"},{"nameEn":"CZ","nameCn":"郴州市","pid":145,"code":"4310","shortName":"郴州","tempPcode":"43"},{"nameEn":"YZ","nameCn":"永州市","pid":145,"code":"4311","shortName":"永州","tempPcode":"43"},{"nameEn":"HH","nameCn":"怀化市","pid":145,"code":"4312","shortName":"怀化","tempPcode":"43"},{"nameEn":"LD","nameCn":"娄底市","pid":145,"code":"4313","shortName":"娄底","tempPcode":"43"},{"nameEn":"XXTJZ","nameCn":"湘西土家族苗族自治州","pid":145,"code":"4331","shortName":"湘西土家族苗族","tempPcode":"43"}],"provice":"湖南省","code":"43"},{"citys":[{"nameEn":"GZ","nameCn":"广州市","pid":304,"code":"4401","shortName":"广州","tempPcode":"44"},{"nameEn":"SG","nameCn":"韶关市","pid":304,"code":"4402","shortName":"韶关","tempPcode":"44"},{"nameEn":"SZ","nameCn":"深圳市","pid":304,"code":"4403","shortName":"深圳","tempPcode":"44"},{"nameEn":"ZH","nameCn":"珠海市","pid":304,"code":"4404","shortName":"珠海","tempPcode":"44"},{"nameEn":"ST","nameCn":"汕头市","pid":304,"code":"4405","shortName":"汕头","tempPcode":"44"},{"nameEn":"FS","nameCn":"佛山市","pid":304,"code":"4406","shortName":"佛山","tempPcode":"44"},{"nameEn":"JM","nameCn":"江门市","pid":304,"code":"4407","shortName":"江门","tempPcode":"44"},{"nameEn":"ZJ","nameCn":"湛江市","pid":304,"code":"4408","shortName":"湛江","tempPcode":"44"},{"nameEn":"MM","nameCn":"茂名市","pid":304,"code":"4409","shortName":"茂名","tempPcode":"44"},{"nameEn":"ZQ","nameCn":"肇庆市","pid":304,"code":"4412","shortName":"肇庆","tempPcode":"44"},{"nameEn":"HZ","nameCn":"惠州市","pid":304,"code":"4413","shortName":"惠州","tempPcode":"44"},{"nameEn":"MZ","nameCn":"梅州市","pid":304,"code":"4414","shortName":"梅州","tempPcode":"44"},{"nameEn":"SW","nameCn":"汕尾市","pid":304,"code":"4415","shortName":"汕尾","tempPcode":"44"},{"nameEn":"HY","nameCn":"河源市","pid":304,"code":"4416","shortName":"河源","tempPcode":"44"},{"nameEn":"YJ","nameCn":"阳江市","pid":304,"code":"4417","shortName":"阳江","tempPcode":"44"},{"nameEn":"QY","nameCn":"清远市","pid":304,"code":"4418","shortName":"清远","tempPcode":"44"},{"nameEn":"DG","nameCn":"东莞市","pid":304,"code":"4419","shortName":"东莞","tempPcode":"44"},{"nameEn":"ZS","nameCn":"中山市","pid":304,"code":"4420","shortName":"中山","tempPcode":"44"},{"nameEn":"CZ","nameCn":"潮州市","pid":304,"code":"4451","shortName":"潮州","tempPcode":"44"},{"nameEn":"JY","nameCn":"揭阳市","pid":304,"code":"4452","shortName":"揭阳","tempPcode":"44"},{"nameEn":"YF","nameCn":"云浮市","pid":304,"code":"4453","shortName":"云浮","tempPcode":"44"}],"provice":"广东省","code":"44"},{"citys":[{"nameEn":"NN","nameCn":"南宁市","pid":289,"code":"4501","shortName":"南宁","tempPcode":"45"},{"nameEn":"LZ","nameCn":"柳州市","pid":289,"code":"4502","shortName":"柳州","tempPcode":"45"},{"nameEn":"GL","nameCn":"桂林市","pid":289,"code":"4503","shortName":"桂林","tempPcode":"45"},{"nameEn":"WZ","nameCn":"梧州市","pid":289,"code":"4504","shortName":"梧州","tempPcode":"45"},{"nameEn":"BH","nameCn":"北海市","pid":289,"code":"4505","shortName":"北海","tempPcode":"45"},{"nameEn":"FYG","nameCn":"防城港市","pid":289,"code":"4506","shortName":"防城港","tempPcode":"45"},{"nameEn":"QZ","nameCn":"钦州市","pid":289,"code":"4507","shortName":"钦州","tempPcode":"45"},{"nameEn":"GG","nameCn":"贵港市","pid":289,"code":"4508","shortName":"贵港","tempPcode":"45"},{"nameEn":"YL","nameCn":"玉林市","pid":289,"code":"4509","shortName":"玉林","tempPcode":"45"},{"nameEn":"BS","nameCn":"百色市","pid":289,"code":"4510","shortName":"百色","tempPcode":"45"},{"nameEn":"HZ","nameCn":"贺州市","pid":289,"code":"4511","shortName":"贺州","tempPcode":"45"},{"nameEn":"HC","nameCn":"河池市","pid":289,"code":"4512","shortName":"河池","tempPcode":"45"},{"nameEn":"LB","nameCn":"来宾市","pid":289,"code":"4513","shortName":"来宾","tempPcode":"45"},{"nameEn":"CZ","nameCn":"崇左市","pid":289,"code":"4514","shortName":"崇左","tempPcode":"45"}],"provice":"广西壮族自治区","code":"45"},{"citys":[{"nameEn":"HK","nameCn":"海口市","pid":110,"code":"4601","shortName":"海口","tempPcode":"46"},{"nameEn":"SY","nameCn":"三亚市","pid":110,"code":"4602","shortName":"三亚","tempPcode":"46"}],"provice":"海南省","code":"46"},{"citys":[{"nameEn":"CQ","nameCn":"重庆市","pid":277,"code":"5000","shortName":"重庆","tempPcode":"50"}],"provice":"重庆市","code":"50"},{"citys":[{"nameEn":"CD","nameCn":"成都市","pid":201,"code":"5101","shortName":"成都","tempPcode":"51"},{"nameEn":"ZG","nameCn":"自贡市","pid":201,"code":"5103","shortName":"自贡","tempPcode":"51"},{"nameEn":"PZH","nameCn":"攀枝花市","pid":201,"code":"5104","shortName":"攀枝花","tempPcode":"51"},{"nameEn":"LZ","nameCn":"泸州市","pid":201,"code":"5105","shortName":"泸州","tempPcode":"51"},{"nameEn":"DY","nameCn":"德阳市","pid":201,"code":"5106","shortName":"德阳","tempPcode":"51"},{"nameEn":"MY","nameCn":"绵阳市","pid":201,"code":"5107","shortName":"绵阳","tempPcode":"51"},{"nameEn":"GY","nameCn":"广元市","pid":201,"code":"5108","shortName":"广元","tempPcode":"51"},{"nameEn":"SN","nameCn":"遂宁市","pid":201,"code":"5109","shortName":"遂宁","tempPcode":"51"},{"nameEn":"NJ","nameCn":"内江市","pid":201,"code":"5110","shortName":"内江","tempPcode":"51"},{"nameEn":"LS","nameCn":"乐山市","pid":201,"code":"5111","shortName":"乐山","tempPcode":"51"},{"nameEn":"NC","nameCn":"南充市","pid":201,"code":"5113","shortName":"南充","tempPcode":"51"},{"nameEn":"MS","nameCn":"眉山市","pid":201,"code":"5114","shortName":"眉山","tempPcode":"51"},{"nameEn":"XB","nameCn":"宜宾市","pid":201,"code":"5115","shortName":"宜宾","tempPcode":"51"},{"nameEn":"GA","nameCn":"广安市","pid":201,"code":"5116","shortName":"广安","tempPcode":"51"},{"nameEn":"DZ","nameCn":"达州市","pid":201,"code":"5117","shortName":"达州","tempPcode":"51"},{"nameEn":"YA","nameCn":"雅安市","pid":201,"code":"5118","shortName":"雅安","tempPcode":"51"},{"nameEn":"BZ","nameCn":"巴中市","pid":201,"code":"5119","shortName":"巴中","tempPcode":"51"},{"nameEn":"ZY","nameCn":"资阳市","pid":201,"code":"5120","shortName":"资阳","tempPcode":"51"},{"nameEn":"ABZZ","nameCn":"阿坝藏族羌族自治州","pid":201,"code":"5132","shortName":"阿坝藏族羌族","tempPcode":"51"},{"nameEn":"GZZZ","nameCn":"甘孜藏族自治州","pid":201,"code":"5133","shortName":"甘孜藏族","tempPcode":"51"},{"nameEn":"LSYZ","nameCn":"凉山彝族自治州","pid":201,"code":"5134","shortName":"凉山彝族","tempPcode":"51"}],"provice":"四川省","code":"51"},{"citys":[{"nameEn":"GY","nameCn":"贵阳市","pid":279,"code":"5201","shortName":"贵阳","tempPcode":"52"},{"nameEn":"LPS","nameCn":"六盘水市","pid":279,"code":"5202","shortName":"六盘水","tempPcode":"52"},{"nameEn":"ZY","nameCn":"遵义市","pid":279,"code":"5203","shortName":"遵义","tempPcode":"52"},{"nameEn":"AS","nameCn":"安顺市","pid":279,"code":"5204","shortName":"安顺","tempPcode":"52"},{"nameEn":"TR","nameCn":"铜仁市","pid":279,"code":"5222","shortName":"铜仁","tempPcode":"52"},{"nameEn":"QX","nameCn":"黔西南布依族苗族自治州","pid":279,"code":"5223","shortName":"","tempPcode":"52"},{"nameEn":"BJ","nameCn":"毕节市","pid":279,"code":"5224","shortName":"毕节","tempPcode":"52"},{"nameEn":"QD","nameCn":"黔东南苗族侗族自治州","pid":279,"code":"5226","shortName":"","tempPcode":"52"},{"nameEn":"QN","nameCn":"黔南布依族苗族自治州","pid":279,"code":"5227","shortName":"","tempPcode":"52"}],"provice":"贵州省","code":"52"},{"citys":[{"nameEn":"KM","nameCn":"昆明市","pid":248,"code":"5301","shortName":"昆明","tempPcode":"53"},{"nameEn":"QJ","nameCn":"曲靖市","pid":248,"code":"5303","shortName":"曲靖","tempPcode":"53"},{"nameEn":"YX","nameCn":"玉溪市","pid":248,"code":"5304","shortName":"玉溪","tempPcode":"53"},{"nameEn":"BS","nameCn":"保山市","pid":248,"code":"5305","shortName":"保山","tempPcode":"53"},{"nameEn":"ST","nameCn":"昭通市","pid":248,"code":"5306","shortName":"昭通","tempPcode":"53"},{"nameEn":"LJ","nameCn":"丽江市","pid":248,"code":"5307","shortName":"丽江","tempPcode":"53"},{"nameEn":"PE","nameCn":"普洱市","pid":248,"code":"5308","shortName":"普洱","tempPcode":"53"},{"nameEn":"LC","nameCn":"临沧市","pid":248,"code":"5309","shortName":"临沧","tempPcode":"53"},{"nameEn":"CX","nameCn":"楚雄彝族自治州","pid":248,"code":"5323","shortName":"楚雄彝族","tempPcode":"53"},{"nameEn":"HHHN","nameCn":"红河哈尼族彝族自治州","pid":248,"code":"5325","shortName":"红河哈尼族彝族","tempPcode":"53"},{"nameEn":"WSZZ","nameCn":"文山壮族苗族自治州","pid":248,"code":"5326","shortName":"文山壮族苗族","tempPcode":"53"},{"nameEn":"XSBN","nameCn":"西双版纳傣族自治州","pid":248,"code":"5328","shortName":"西双版纳傣族","tempPcode":"53"},{"nameEn":"DL","nameCn":"大理白族自治州","pid":248,"code":"5329","shortName":"大理白族","tempPcode":"53"},{"nameEn":"DHDZ","nameCn":"德宏傣族景颇族自治州","pid":248,"code":"5331","shortName":"德宏傣族景颇族","tempPcode":"53"},{"nameEn":"NJLSZ","nameCn":"怒江僳僳族自治州","pid":248,"code":"5333","shortName":"怒江僳僳族","tempPcode":"53"},{"nameEn":"DQZZ","nameCn":"迪庆藏族自治州","pid":248,"code":"5334","shortName":"迪庆藏族","tempPcode":"53"}],"provice":"云南省","code":"53"},{"citys":[{"nameEn":"LS","nameCn":"拉萨市","pid":225,"code":"5401","shortName":"拉萨","tempPcode":"54"},{"nameEn":"CD","nameCn":"昌都地区","pid":225,"code":"5421","shortName":"昌都","tempPcode":"54"},{"nameEn":"SN","nameCn":"山南地区","pid":225,"code":"5422","shortName":"山南","tempPcode":"54"},{"nameEn":"RKZ","nameCn":"日喀则地区","pid":225,"code":"5423","shortName":"日喀则","tempPcode":"54"},{"nameEn":"NQ","nameCn":"那曲地区","pid":225,"code":"5424","shortName":"那曲","tempPcode":"54"},{"nameEn":"AL","nameCn":"阿里地区","pid":225,"code":"5425","shortName":"阿里","tempPcode":"54"},{"nameEn":"LZ","nameCn":"林芝地区","pid":225,"code":"5426","shortName":"林芝","tempPcode":"54"}],"provice":"西藏","code":"54"},{"citys":[{"nameEn":"XA","nameCn":"西安市","pid":75,"code":"6101","shortName":"西安","tempPcode":"61"},{"nameEn":"TZ","nameCn":"铜川市","pid":75,"code":"6102","shortName":"铜川","tempPcode":"61"},{"nameEn":"BJ","nameCn":"宝鸡市","pid":75,"code":"6103","shortName":"宝鸡","tempPcode":"61"},{"nameEn":"XY","nameCn":"咸阳市","pid":75,"code":"6104","shortName":"咸阳","tempPcode":"61"},{"nameEn":"WN","nameCn":"渭南市","pid":75,"code":"6105","shortName":"渭南","tempPcode":"61"},{"nameEn":"YA","nameCn":"延安市","pid":75,"code":"6106","shortName":"延安","tempPcode":"61"},{"nameEn":"HZ","nameCn":"汉中市","pid":75,"code":"6107","shortName":"汉中","tempPcode":"61"},{"nameEn":"YL","nameCn":"榆林市","pid":75,"code":"6108","shortName":"榆林","tempPcode":"61"},{"nameEn":"AT","nameCn":"安康市","pid":75,"code":"6109","shortName":"安康","tempPcode":"61"},{"nameEn":"SL","nameCn":"商洛市","pid":75,"code":"6110","shortName":"商洛","tempPcode":"61"}],"provice":"陕西省","code":"61"},{"citys":[{"nameEn":"LZ","nameCn":"兰州市","pid":326,"code":"6201","shortName":"兰州","tempPcode":"62"},{"nameEn":"JYG","nameCn":"嘉峪关市","pid":326,"code":"6202","shortName":"嘉峪关","tempPcode":"62"},{"nameEn":"JC","nameCn":"金昌市","pid":326,"code":"6203","shortName":"金昌","tempPcode":"62"},{"nameEn":"BY","nameCn":"白银市","pid":326,"code":"6204","shortName":"白银","tempPcode":"62"},{"nameEn":"TS","nameCn":"天水市","pid":326,"code":"6205","shortName":"天水","tempPcode":"62"},{"nameEn":"WW","nameCn":"武威市","pid":326,"code":"6206","shortName":"武威","tempPcode":"62"},{"nameEn":"ZY","nameCn":"张掖市","pid":326,"code":"6207","shortName":"张掖","tempPcode":"62"},{"nameEn":"PL","nameCn":"平凉市","pid":326,"code":"6208","shortName":"平凉","tempPcode":"62"},{"nameEn":"JQ","nameCn":"酒泉市","pid":326,"code":"6209","shortName":"酒泉","tempPcode":"62"},{"nameEn":"QY","nameCn":"庆阳市","pid":326,"code":"6210","shortName":"庆阳","tempPcode":"62"},{"nameEn":"DX","nameCn":"定西市","pid":326,"code":"6211","shortName":"定西","tempPcode":"62"},{"nameEn":"LN","nameCn":"陇南市","pid":326,"code":"6212","shortName":"陇南","tempPcode":"62"},{"nameEn":"LXHZ","nameCn":"临夏回族自治州","pid":326,"code":"6229","shortName":"临夏回族","tempPcode":"62"},{"nameEn":"GNZZ","nameCn":"甘南藏族自治州","pid":326,"code":"6230","shortName":"甘南藏族","tempPcode":"62"}],"provice":"甘肃省","code":"62"},{"citys":[{"nameEn":"XN","nameCn":"西宁市","pid":66,"code":"6301","shortName":"西宁","tempPcode":"63"},{"nameEn":"HD","nameCn":"海东地区","pid":66,"code":"6321","shortName":"海东","tempPcode":"63"},{"nameEn":"HBZZ","nameCn":"海北藏族自治州","pid":66,"code":"6322","shortName":"海北藏族","tempPcode":"63"},{"nameEn":"HNZZ","nameCn":"黄南藏族自治州","pid":66,"code":"6323","shortName":"黄南藏族","tempPcode":"63"},{"nameEn":"HNZZ","nameCn":"海南藏族自治州","pid":66,"code":"6325","shortName":"海南藏族","tempPcode":"63"},{"nameEn":"GLZZ","nameCn":"果洛藏族自治州","pid":66,"code":"6326","shortName":"果洛藏族","tempPcode":"63"},{"nameEn":"YSZZ","nameCn":"玉树藏族自治州","pid":66,"code":"6327","shortName":"玉树藏族","tempPcode":"63"},{"nameEn":"HXMG","nameCn":"海西蒙古族藏族自治州","pid":66,"code":"6328","shortName":"海西蒙古族藏族","tempPcode":"63"}],"provice":"青海省","code":"63"},{"citys":[{"nameEn":"YC","nameCn":"银川市","pid":29,"code":"6401","shortName":"银川","tempPcode":"64"},{"nameEn":"SZS","nameCn":"石嘴山市","pid":29,"code":"6402","shortName":"石嘴山","tempPcode":"64"},{"nameEn":"WZ","nameCn":"吴忠市","pid":29,"code":"6403","shortName":"吴忠","tempPcode":"64"},{"nameEn":"GY","nameCn":"固原市","pid":29,"code":"6404","shortName":"固原","tempPcode":"64"},{"nameEn":"ZW","nameCn":"中卫市","pid":29,"code":"6405","shortName":"中卫","tempPcode":"64"}],"provice":"宁夏","code":"64"},{"citys":[{"nameEn":"WLMQ","nameCn":"乌鲁木齐市","pid":233,"code":"6501","shortName":"乌鲁木齐","tempPcode":"65"},{"nameEn":"KLMY","nameCn":"克拉玛依市","pid":233,"code":"6502","shortName":"克拉玛依","tempPcode":"65"},{"nameEn":"TLF","nameCn":"吐鲁番地区","pid":233,"code":"6521","shortName":"吐鲁番","tempPcode":"65"},{"nameEn":"HM","nameCn":"哈密地区","pid":233,"code":"6522","shortName":"哈密","tempPcode":"65"},{"nameEn":"CJHZ","nameCn":"昌吉回族自治州","pid":233,"code":"6523","shortName":"昌吉回族","tempPcode":"65"},{"nameEn":"BETL","nameCn":"博尔塔拉蒙古自治州","pid":233,"code":"6527","shortName":"博尔塔拉蒙古","tempPcode":"65"},{"nameEn":"BYGL","nameCn":"巴音郭楞蒙古自治州","pid":233,"code":"6528","shortName":"巴音郭楞蒙古","tempPcode":"65"},{"nameEn":"AKS","nameCn":"阿克苏地区","pid":233,"code":"6529","shortName":"阿克苏","tempPcode":"65"},{"nameEn":"KZLS","nameCn":"克孜勒苏柯尔克孜自治州","pid":233,"code":"6530","shortName":"克孜勒苏柯尔克孜","tempPcode":"65"},{"nameEn":"KS","nameCn":"喀什地区","pid":233,"code":"6531","shortName":"喀什","tempPcode":"65"},{"nameEn":"HT","nameCn":"和田地区","pid":233,"code":"6532","shortName":"和田","tempPcode":"65"},{"nameEn":"YLHSK","nameCn":"伊犁哈萨克自治州","pid":233,"code":"6540","shortName":"伊犁哈萨克","tempPcode":"65"},{"nameEn":"TC","nameCn":"塔城地区","pid":233,"code":"6542","shortName":"塔城","tempPcode":"65"},{"nameEn":"ALT","nameCn":"阿勒泰地区","pid":233,"code":"6543","shortName":"阿勒泰","tempPcode":"65"}],"provice":"新疆","code":"65"}]}
     * rows : []
     */

    private Object forward;
    private Object errorCode;
    private Object message;
    /**
     * everyPage : 10
     * totalPage : 1
     * currentPage : 1
     * beginIndex : 0
     * recordCount : 0
     * totalRecords : 0
     */

    private PageEntity page;
    private boolean success;
    private AttrEntity attr;
    private List<?> rows;

    public void setForward(Object forward) {
        this.forward = forward;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAttr(AttrEntity attr) {
        this.attr = attr;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Object getForward() {
        return forward;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public Object getMessage() {
        return message;
    }

    public PageEntity getPage() {
        return page;
    }

    public boolean isSuccess() {
        return success;
    }

    public AttrEntity getAttr() {
        return attr;
    }

    public List<?> getRows() {
        return rows;
    }

    public static class PageEntity implements Serializable{
        private int everyPage;
        private int totalPage;
        private int currentPage;
        private int beginIndex;
        private int recordCount;
        private int totalRecords;

        public void setEveryPage(int everyPage) {
            this.everyPage = everyPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getEveryPage() {
            return everyPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public int getTotalRecords() {
            return totalRecords;
        }
    }

    public static class AttrEntity  implements Serializable{
        /**
         * citys : [{"nameEn":"BJ","nameCn":"北京市","pid":351,"code":"1100","shortName":"北京","tempPcode":"11"}]
         * provice : 北京市
         * code : 11
         */

        private List<AreasEntity> areas;

        public void setAreas(List<AreasEntity> areas) {
            this.areas = areas;
        }

        public List<AreasEntity> getAreas() {
            return areas;
        }

        public static class AreasEntity  implements Serializable{
            private String provice;
            private String code;
            /**
             * nameEn : BJ
             * nameCn : 北京市
             * pid : 351
             * code : 1100
             * shortName : 北京
             * tempPcode : 11
             */

            private List<CitysEntity> citys;

            public void setProvice(String provice) {
                this.provice = provice;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public void setCitys(List<CitysEntity> citys) {
                this.citys = citys;
            }

            public String getProvice() {
                return provice;
            }

            public String getCode() {
                return code;
            }

            public List<CitysEntity> getCitys() {
                return citys;
            }

            public static class CitysEntity  implements Serializable{
                private String nameEn;
                private String nameCn;
                private int pid;
                private String code;
                private String shortName;
                private String tempPcode;

                public void setNameEn(String nameEn) {
                    this.nameEn = nameEn;
                }

                public void setNameCn(String nameCn) {
                    this.nameCn = nameCn;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public void setShortName(String shortName) {
                    this.shortName = shortName;
                }

                public void setTempPcode(String tempPcode) {
                    this.tempPcode = tempPcode;
                }

                public String getNameEn() {
                    return nameEn;
                }

                public String getNameCn() {
                    return nameCn;
                }

                public int getPid() {
                    return pid;
                }

                public String getCode() {
                    return code;
                }

                public String getShortName() {
                    return shortName;
                }

                public String getTempPcode() {
                    return tempPcode;
                }
            }
        }
    }
}
