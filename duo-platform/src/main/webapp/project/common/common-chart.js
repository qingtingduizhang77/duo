
var labelOption1 = {
    show: true,
    align: 'left',
    position: 'insideBottom',
    distance: 50,
    verticalAlign: 'center',
    formatter: '{a} {c} ',
    fontSize: 12
};

var labelOption2 = {
    show: true,
    rotate: 90,
    align: 'left',
    position: 'insideBottom',
    distance: 25,
    verticalAlign: 'center',
    formatter: '{a} {c} ',
    fontSize: 12
};
var labelOption3 = {
    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
    backgroundColor: '#eee',
    borderColor: '#aaa',
    borderWidth: 1,
    borderRadius: 4,
    rich: {
        a: {
            color: '#999',
            lineHeight: 22,
            align: 'center'
        },
        hr: {
            borderColor: '#aaa',
            width: '100%',
            borderWidth: 0.5,
            height: 0
        },
        b: {
            fontSize: 12,
            lineHeight: 33
        },
        per: {
            color: '#eee',
            backgroundColor: '#334455',
            padding: [2, 4],
            borderRadius: 2
        }
    }
};

var labelOption4 ={
    position: 'inner'
};
function viewChartLine(chartId){
    viewChart("#chartmain",chartId);
}
function viewChart(obj,chartId){
    var callback= function(result){
        if(result.success) {
            // alert(result.data);
            eval("var data=" + result.data + ";");
            //指定图标的配置和数据
            var option = {};
            if (data.chartType == "default") {
                option = {
                    title: {
                        text: '图表例子',
                        subtext: ' ',
                        left: '50%',
                        textAlign: 'center'
                    },
                    color: ['#003366', '#006699', '#4cabce', '#e5323e'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                    },
                    yAxis: [
                        {
                            type: 'value',
                            name: '水量',
                            min: 0,
                            max: 900,
                            interval: 100,
                            axisLabel: {
                                formatter: '{value} ml'
                            }
                        },
                        {
                            type: 'value',
                            name: '温度',
                            min: 0,
                            max: 45,
                            interval: 5,
                            axisLabel: {
                                formatter: '{value} °C'
                            }
                        }
                    ],
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: false, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        //orient: 'vertical',
                        x: 'center',
                        y: 'top',
                        padding: [30, 50, 0, 0],
                        data: ['Forest', 'Forest1', '温度']
                    },
                    series: [{
                        name: '温度',
                        type: 'line',
                        yAxisIndex: 1,
                        z: 10,
                        label: labelOption2,
                        barGap: 0,
                        data: [12, 20, 30, 30, 25, 33, 40]
                    },
                        {
                            name: 'Forest',
                            type: 'bar',
                            label: labelOption1,
                            stack: '搜索引擎',
                            barGap: 0,
                            data: [120, 300, 150, 80, 70, 120, 150]
                        },
                        {
                            name: 'Forest1',
                            type: 'bar',
                            label: labelOption1,
                            stack: '搜索引擎',
                            barGap: 0,
                            data: [120, 300, 150, 80, 70, 120, 150]
                        }]
                };
                option.title.text=data.title;
                option.title.subtext=data.subtext;
                eval("option.xAxis.data="+data.xdata+";");
                if(data["color"]!=null)  eval("option.color="+data.color+";");
                option.toolbox.show=data.toolbox;
                eval("option.legend.data="+data.legend+";");
                //  alert(data.yAxis);
                eval("option.yAxis="+data.yAxis+";");
                eval("option.series="+data.series+";");
                for(var i=0;i< option.series.length;i++){
                    // alert(option.series[i].data);
                    eval("option.series[i].data="+option.series[i].data);
                    if(option.series[i].label!=null&&option.series[i].label!="") eval("option.series[i].label="+option.series[i].label);
                }

            }else if(data.chartType == "pie"){
                option = {
                    title: {
                        text: '图表例子',
                        subtext: ' ',
                        left: '50%',
                        textAlign: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 10,
                        data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
                    },
                    series: [
                        {
                            "name": '访问来源',
                            type: 'pie',
                            selectedMode: 'single',
                            radius: [0, '45%'],
                            label: {
                                position: 'inner'
                            },
                            data: [
                                {value: 335, name: '直达', selected: true},
                                {value: 679, name: '营销广告'},
                                {value: 1548, name: '搜索引擎'}
                            ]
                        },
                        {
                            name: '访问来源',
                            type: 'pie',
                            selectedMode: 'single',
                            radius: ['60%', '85%'],
                            label:labelOption3 ,
                            data: [
                                {value: 335, name: '直达'},
                                {value: 310, name: '邮件营销'},
                                {value: 234, name: '联盟广告'},
                                {value: 135, name: '视频广告'},
                                {value: 1048, name: '百度'},
                                {value: 251, name: '谷歌'},
                                {value: 147, name: '必应'},
                                {value: 102, name: '其他'}
                            ]
                        }
                    ]
                };
                option.title.text=data.title;
                option.title.subtext=data.subtext;
                eval("option.legend.data="+data.legend+";");
                eval("option.series="+data.series+";");
                for(var i=0;i< option.series.length;i++){
                    // alert("option.series[i].data="+option.series[i].data);
                    eval("option.series[i].radius="+option.series[i].radius);
                    eval("option.series[i].data="+option.series[i].data);
                    if(option.series[i].label!=null&&option.series[i].label!="") eval("option.series[i].label="+option.series[i].label);
                }
            }else if(data.chartType == "gauge"){
                option = {
                    title: {
                        text: '图表例子',
                        subtext: ' ',
                        left: '50%',
                        textAlign: 'center'
                    },
                    tooltip: {
                        formatter: '{a} <br/>{b} : {c}%'
                    },
                    toolbox: {
                        feature: {
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    series: [
                        {
                            name: '业务指标',
                            type: 'gauge',
                            detail: {formatter: '{value}%'},
                            data: [{value: 50, name: '完成率'}]
                        }
                    ]
                };
                option.title.text=data.title;
                option.title.subtext=data.subtext;

                eval("option.series="+data.series+";");
                for(var i=0;i< option.series.length;i++){
                    // alert("option.series[i].data="+option.series[i].data);
                    eval("option.series[i].data="+option.series[i].data);
                    option.series[i]["detail"]={formatter: '{value}%'};
                }
            }
            //初始化echarts实例
            var myChart = echarts.init(document.getElementById('chartmain'));

            //使用制定的配置项和数据显示图表
            myChart.setOption(option);

            // myChart.on('click', function (params) {
            //     alert("单击了"+params.value);
            // });
        }
    }
    ajaxSumit("/common/chart",{chartId:chartId},callback);
    $(obj).height(document.body.clientHeight);
}

