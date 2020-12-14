package com.duo.modules.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.core.BaseService;
import com.duo.core.utils.ConvertUtil;
import com.duo.core.utils.StringUtils;
import com.duo.modules.tool.entity.ToolChart;
import com.duo.modules.tool.entity.ToolChartPart;
import com.duo.modules.tool.mapper.ToolChartMapper;
import com.duo.modules.tool.mapper.ToolChartPartMapper;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2020/1/22]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class ChartService extends BaseService {
    @Autowired
    private ToolChartMapper toolChartMapper;
    @Autowired
    private ToolChartPartMapper toolChartPartMapper;

    public Map<String,Object> chartExcute(Map<String, Object> requestMap) throws Exception {
        Map<String,Object> dataMp=new HashMap<String,Object>();

        //根据配置项显示图表
        String chart_id=(String)requestMap.get("chartId");
        if(chart_id!=null){
            ToolChart chart=toolChartMapper.selectByPrimaryKey(chart_id);
            if(chart!=null){
                dataMp.put("title",chart.getChart_title());
                dataMp.put("subtext",chart.getChart_subtitle());
                dataMp.put("chartType","default");
                if(StringUtils.isNotEmpty(chart.getChart_type()))  dataMp.put("chartType",chart.getChart_type());
                dataMp.put("legend","[]");
                if(StringUtils.isNotEmpty(chart.getFix_colors())) dataMp.put("color",chart.getFix_colors());
                dataMp.put("toolbox",false);
                if(chart.getHas_tool().equals("1")){
                    dataMp.put("toolbox",true);
                }
                List<Map<String,Object>> datas=getDatas(chart);//获取数据集

                if(!dataMp.get("chartType").equals("pie")) {//Pie图不需要设置x、y轴
                    //X轴
                    if (chart.getX_valuetype().equals("fix")) {
                        dataMp.put("xdata", chart.getX_value());
                    } else if (chart.getX_valuetype().equals("column")) {

                        if (datas != null && datas.size() > 0) {
                            String[] ary = new String[datas.size()];
                            for (int i = 0; i < datas.size(); i++) {
                                Map<String, Object> mp = datas.get(i);
                                ary[i] = (String) mp.get("xtype");
                            }
                            String xvalue = "['" + StringUtils.join(ary, "','") + "']";
                            dataMp.put("xdata", xvalue);
                        }
                    }
                    //组建Y轴
                    Map<String, Object> y1 = new HashMap<String, Object>();
                    y1.put("type", "value");
                    y1.put("name", chart.getY1_name());
                    if (chart.getY1_min() != null) {
                        y1.put("min", chart.getY1_min());
                    }
                    if (chart.getY1_max() != null) {
                        y1.put("max", chart.getY1_max());
                    }
                    if (chart.getY1_interval() != null) {
                        y1.put("interval", chart.getY1_interval());
                    }
                    if (chart.getY1_unit() != null) {
                        Map<String, Object> axisLabel = new HashMap<String, Object>();
                        axisLabel.put("formatter", "{value} " + chart.getY1_unit());
                        y1.put("axisLabel", axisLabel);
                    }
                    if (StringUtils.isNotEmpty(chart.getY2_name())) {
                        Map<String, Object> y2 = new HashMap<String, Object>();
                        y2.put("type", "value");
                        y2.put("name", chart.getY2_name());
                        if (chart.getY2_min() != null) {
                            y2.put("min", chart.getY2_min());
                        }
                        if (chart.getY2_max() != null) {
                            y2.put("max", chart.getY2_max());
                        }
                        if (chart.getY2_interval() != null) {
                            y2.put("interval", chart.getY2_interval());
                        }
                        if (chart.getY2_unit() != null) {

                            Map<String, Object> axisLabel = new HashMap<String, Object>();
                            axisLabel.put("formatter", "{value} " + chart.getY2_unit());
                            y2.put("axisLabel", axisLabel);
                        }
                        dataMp.put("yAxis", "[" + JSON.toJSONString(y1) + "," + JSON.toJSONString(y2) + "]");
                    } else {
                        dataMp.put("yAxis", "[" + JSON.toJSONString(y1) + "]");
                    }

                }

//                //part组成
                ToolChartPart query=new ToolChartPart();
                query.setChart_id(chart_id);
                List<ToolChartPart> chartParts=toolChartPartMapper.select(query);//获取元素构成
                List<Map<String,Object>> series=new ArrayList<Map<String, Object>>()  ;
                if(chartParts!=null) {
                    String[] legend =null;
                    for (int i=0;i< chartParts.size();i++) {//每个子图构成
                        ToolChartPart part=chartParts.get(i);
                        Map<String,Object> pmp=new HashMap<String,Object>();
                        pmp.put("name",part.getPart_name());
                        pmp.put("type",part.getPart_type());
                        if (StringUtils.isNotEmpty(part.getLabel_type())) pmp.put("label", part.getLabel_type());
                        if(part.getPart_type().equals("pie")||part.getPart_type().equals("gauge")){//pie类型、gauge类型
                            if(i>1) break;//只支持2层pie
                            if(legend==null) legend =new  String[datas.size()];
                            String pieindex=StringUtils.emptyToDefault(part.getYaxis_index(),"0");
                            if(part.getPart_type().equals("pie")) {
                                pmp.put("selectedMode", "single");
                                pmp.put("radius", chartParts.size() > 1 ? (pieindex.equals("0") ? "[0, '45%']" : "['60%', '85%']") : "[0, '85%']");
                            }else if(part.getPart_type().equals("gauge")){

                            }
                            List<Map<String,Object>> piedatas=new ArrayList<Map<String,Object>>();
                             for (int ii = 0; ii < datas.size(); ii++) {
                                Map<String, Object> mp = datas.get(ii);
                                String index= (String)ConvertUtil.castFromObject(mp.get("pieindex"), String.class);
                                 System.out.println(pieindex+"==========="+index);
                                if(pieindex.equals(index)){
                                    Map<String,Object> d=new HashMap<String,Object>();
                                    d.put("value",mp.get(part.getData_column()));
                                    d.put("name", (String)mp.get("xtype"));
                                    piedatas.add(d);
                                }
                               if(legend[ii]==null) legend[ii] = (String)mp.get("xtype");//组成legend
                            }
                             System.out.println("==========="+piedatas.size());
                            String xvalue =JSON.toJSONString(piedatas) ;
                            pmp.put("data", xvalue);

                        }else {
                            if(legend==null) legend =new  String[chartParts.size()];
                            pmp.put("yAxisIndex", StringUtils.emptyToDefault(part.getYaxis_index(), "0"));
                            if (StringUtils.isNotEmpty(part.getIs_stack()) && part.getIs_stack().equals("1"))
                                pmp.put("stack", "stack1");

                            //是否指定x值，是则需要对datas的数据进行按x数组进行排序
                            if (chart.getX_valuetype().equals("fix")) {
                                Map<String, Object> value = new HashMap<String, Object>();
                                for (int ii = 0; ii < datas.size(); ii++) {
                                    Map<String, Object> mp = datas.get(ii);
                                    value.put((String) mp.get("xtype"), mp.get(part.getData_column()));
                                }
                                String xdata = chart.getX_value();
                                List<String> ls = JSONObject.parseArray(xdata, String.class);
                                String[] ary = new String[ls.size()];
                                for (int ii = 0; ii < ls.size(); ii++) {
                                    ary[ii] = (String) ConvertUtil.castFromObject(value.get(ls.get(ii)), String.class);
                                    if (ary[ii] == null) ary[ii] = "0";
                                }
                                String xvalue = "['" + StringUtils.join(ary, "','") + "']";
                                pmp.put("data", xvalue);
                            } else {
                                //从结果集里获取
                                if (datas != null && datas.size() > 0) {
                                    String[] ary = new String[datas.size()];
                                    for (int ii = 0; ii < datas.size(); ii++) {
                                        Map<String, Object> mp = datas.get(ii);
                                        ary[ii] = (String) ConvertUtil.castFromObject(mp.get(part.getData_column()), String.class);
                                    }
                                    String xvalue = "['" + StringUtils.join(ary, "','") + "']";
                                    pmp.put("data", xvalue);
                                } else {
                                    pmp.put("data", "[]");
                                }
                                pmp.put("barGap", "0");
                            }
                            legend[i] = part.getPart_name();//组成legend
                        }
                        series.add(pmp);
                    }

                    if (chart.getHas_legend().equals("1")) {//显示legend
                        dataMp.put("legend", "['"+ StringUtils.join(legend,"','")+"']");
                    }
                    dataMp.put("series",JSON.toJSONString(series));
                }
            }
        }

        //返回数据
        for (Map.Entry<String, Object> entry : dataMp.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        return dataMp;
    }

    private List<Map<String,Object>> getDatas(ToolChart chart) throws Exception {
        List<Map<String,Object>> datas=null;
        //从sql获取数据
        if(chart.getData_from().equals("sql")){
            SqlSession session =getSession(chart.getData_source());
            SqlMapper sqlMapper=new SqlMapper(session);
            try {
                datas=  sqlMapper.selectList(chart.getSql_statement(),addDefaultValue2Map(new HashMap<>()));
            }catch (Exception e){
                log.error("查询sql出错！",e);
                throw new Exception(e);
            }finally {
                session.close();
            }

        }else if(chart.getData_from().equals("fun")){

        }else if(chart.getData_from().equals("url")){

        }

        return datas;
    }
}
