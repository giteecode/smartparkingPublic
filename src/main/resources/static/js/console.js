"use strict";
layui.use(["element","okUtils", "table", "countUp"], function () {
    var countUp = layui.countUp;
    var table = layui.table;
    var okUtils = layui.okUtils;
    var $ = layui.jquery;

    //总体统计
    function statText() {
        okUtils.ajaxCloud({
            url:"/sys/interface/query",
            param:{'type':'indexStatistics'},
            async:true,
            success : function(result) {
                $("#orgNumber").html(result.msg[0].orgNumber);
                $("#parkNumber").html(result.msg[0].parkNumber);
                $("#carNumber").html(result.msg[0].carNumber);
                $("#cost").html(result.msg[0].cost);
            }
        });
    }
    //订单支付类型
    function payType(){
        var myChart = echarts.init($("#payType")[0], "theme");
        var option = {
            color: ['#19be6b'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            legend: {
                data:['订单数']
            },
            xAxis: [
                {
                    type: 'category',
                    data: [],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '订单数',
                    type: 'bar',
                    barWidth: '25%',
                    data: []
                }
            ]
        };
        okUtils.ajaxCloud({
            url:"/sys/interface/query",
            param:{'type':'payTypeStatistics'},
            async:true,
            success : function(result) {
                var array1 = [];
                var array2 = [];
                result.msg.forEach(function(entity){
                    array1.push(entity.name);
                    array2.push(entity.data);
                });
                option.xAxis[0].data = array1;
                option.series[0].data = array2;
                myChart.setOption(option);
                okUtils.echartsResize([myChart]);
            }
        });
    }

    //最近七日支付订单
    function lately7Day() {
        var userSourceOption = {
            title: {text: ""},
            tooltip: {trigger: "axis", axisPointer: {type: "cross", label: {backgroundColor: "#6a7985"}}},
            legend: {data: []},
            toolbox: {feature: {saveAsImage: {}}},
            grid: {left: "3%", right: "4%", bottom: "3%", containLabel: true},
            xAxis: [{type: "category", boundaryGap: false, data: []}],
            yAxis: [{type: "value",minInterval: 1}],
            series: [
            ]
        };
        okUtils.ajaxCloud({
            url:"/sys/interface/query",
            param:{'type':'total7Pay'},
            async:false,
            success : function(result) {
                var dataMap = {};
                result.msg.forEach(function(entity){
                    if (dataMap.hasOwnProperty(entity.payType)) {
                        dataMap[entity.payType].data.push(entity.amount)
                    } else {
                        userSourceOption.legend.data.push(entity.payType);
                        dataMap[entity.payType] = {name: entity.payType, type: "line", stack: "总量", areaStyle: {}, data: [entity.amount]}
                    }

                    if (userSourceOption.xAxis[0].data.indexOf(entity.date) < 0) {
                        userSourceOption.xAxis[0].data.push(entity.date);
                    }
                });
                userSourceOption.series = Object.values(dataMap);
                console.log(userSourceOption);
                var userSourceMap = echarts.init($("#lately7Day")[0], "theme");
                userSourceMap.setOption(userSourceOption);
                okUtils.echartsResize([userSourceMap]);
            }
        });
    }
    statText();
    payType();
    lately7Day();
});


