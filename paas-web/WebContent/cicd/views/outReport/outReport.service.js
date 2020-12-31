(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name PaaS5.service:MainService
   * @description
   * # MainService 公用核心服务
   * Service of the PaaS5
   */
  angular.module('PaaS5.services.outReportService', [])
    .factory('outReportFactory', outReportFactory);

  function outReportFactory($http, COREDATA) {
    return {
      getSixTabData: getSixTabData,
      getSixTabChart: getSixTabChart
    };
    function getSixTabData(params, tableDta) {
      $http({
        method: 'get',
        url: params.url,
        params: params.pageParam
      }).success(function (response) {
        // COREDATA.resMessage(response.resultCode, response.resultMessage);
        tableDta.list = JSON.parse(response.data).result;
        tableDta.totalCount = JSON.parse(response.data).totalCount;
      }).error(function (response) {
        COREDATA.resMessage(response.resultCode, response.resultMessage);
      });
    }
    function getSixTabChart(url, flowId, flowName) {
      $http({
        method: 'get',
        url: url,
        params: {flowId: flowId}
      }).success(function (response) {
        var option = {
          visualMap: [{
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 100
          }],
          title: {text: '流水：'+flowName},
          tooltip: {trigger: 'axis',axisPointer: {
            type: 'cross'
          }},
          toolbox: {
            show : true,
            feature : {
              dataView : {show: true, readOnly: true},
              magicType : {show: true, type: ['line', 'bar']},
              saveAsImage : {show: true}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: JSON.parse(response.data).xList,
            axisPointer: {label: {formatter: function (params) {return '构建次数  ' + params.value;}}}
          },
          yAxis: {type: 'value'},
          series: [
            {
              name:'问题数量',
              type:'line',
              data: JSON.parse(response.data).yList,
              // markPoint : {data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]},
              markLine : {data : [{type : 'average', name: '平均值'}]}
            }
          ]
        };
        echarts.init(document.getElementById('outReportChart')).setOption(option);
      }).error(function (response) {
        COREDATA.resMessage(response.resultCode, response.resultMessage);
      });
    }
  }

})();
