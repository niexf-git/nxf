(function () {
    'use strict';
    angular.module('PaaS5.services')
    .factory('overviewGetChartOption', overviewGetChartOption)
    .service('overviewService1', overviewService1);
    function overviewService1($http) {
      return {
        queryServiceState: queryServiceState,
        queryInstanceState: queryInstanceState,
        queryAlarmDetails: queryAlarmDetails,
        queryAssignedResourceRate: queryAssignedResourceRate,
        queryActualResourceRate: queryActualResourceRate,
        queryTotalResource: queryTotalResource,
        queryFlowBuildState: queryFlowBuildState
      };
      /**
       * 1 查询《服务》状态统计
       * 响应参数 「get」申佳联调廖汉伟
        { 
          working:string//运行中
          stopped:string//已停止
          operationing:string//操作中
        }
       */
      function queryServiceState(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryServiceState.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 2 查询《实例》状态统计
       * 响应参数 「get」申佳联调廖汉伟
        { 
          running:string//运行中
          stopped:string//停止中
          unknow:string//未知
          unassigned:string//未调度
          waiting:string//等待中
        }
       */
      function queryInstanceState(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryInstanceState.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 3 查询《告警》统计
       * 响应参数 「get」申佳联调廖汉伟
        { 
          [{
            alarmTime:string//告警时间（天）
            alarmCount:string//告警次数
          }*7]
        }
       */
      function queryAlarmDetails(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryAlarmDetails.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 4 查询《已分配资源利用率》
       * 响应参数 「get」申佳联调廖汉伟
        {
          assignedCpu:string //已分配CPU"{"totalMemory":"224091","assignedCpu":"550","assignedMemory":"190","totalCpu":"56000"}"
          totalCpu:string //总CPU
          assignedMemory:string //已分配内存
          totalMemory:string //总内存
        }
       */
      function queryAssignedResourceRate(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryAssignedResourceRate.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 5 查询《实际资源利用率》
       * 响应参数 「get」申佳联调廖汉伟
        {
          usedCpu:string //实际使用CPU
          totalCpu:string //总CPU
          usedMemory:string //实际使用内存
          totalMemory:string //总内存
          usedDisk:string //实际使用磁盘
          totalDisk:string //总磁盘大小
        }
       */
      function queryActualResourceRate(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryActualResourceRate.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 6 查询《总量／资源》统计
       * 响应参数 「get」申佳联调廖汉伟
        {
          host:string//主机
          cpu:string//CPU
          memory:string//内存
          disk：string//磁盘
        }
       */
      function queryTotalResource(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryTotalResource.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
      /**
       * 7 查询《构建流水／CICD》统计
       * 响应参数 「get」申佳联调廖汉伟
        {
          working:string//正在构建
          success:string //构建成功
          fail:string //构建失败
        }
       */
      function queryFlowBuildState(successCallback, failCallback) {
        $http({
          url: '/paas/overview/queryFlowBuildState.action',
          method: 'GET'
        }).success(successCallback).error(failCallback);
      }
    }

    function overviewGetChartOption() {
      function optionType(type) {
        if (type === 'line') {
          return angular.copy(lineOption, {});
        } else if (type === 'pie') {
          return angular.copy(pieOption, {});
        } else if (type === 'liquid') {
          return angular.copy(liquidOption, {});
        }
      };
  
      //line图表的配置项和数据
      var lineOption = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07']
        },
        yAxis: {
          name: '告警次数',
          type: 'value'
        },
        series: [{
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line'
        }],
        grid: [
          { x: '5%', y: '20%', width: '80%', height: '80%', containLabel: true }
        ],
      };
  
      // pie图表的配置项和数据
      var pieOption = {
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['68%', '80%'],
            label: { normal: { show: false, position: 'center' } },
            data: [
              { value: 335, name: '直接访问' },
              { value: 234, name: '联盟广告' },
              { value: 1548, name: '搜索引擎' }
            ]
          }
        ],
        color: ['#397eff', '#f95b7b', '#ffa32a', '#a6ffe1', '#999']//cfdbe7灰色
      };
  
      //liquid图表的配置项和数据
      var liquidOption = {
        series: [{
          type: 'liquidFill',
          radius: '80%',
          data: [{
            value: '0.5588'
          }],
          color: ['#397eff'],
          label: { fontSize: 16},
          outline: {
            show: false
          }
        }]
      };
      return optionType;
    }

    angular.module('PaaS5.services').service('overviewService', function($http){
      //对接后台获取数据
      return{
        queryFlowNameList: queryFlowNameList,                 //流水列表
        queryAutomateTestList: queryAutomateTestList,        //自动化测试
        queryIntegrationTestList: queryIntegrationTestList,  //集成测试
        queryBuildList:queryBuildList,                        //构建列表
        queryQualityAnalysis:queryQualityAnalysis,           //质量分析
        queryDeployList:queryDeployList,                     //部署列表
        queryCodeRepoList:queryCodeRepoList,                 //代码仓库
        queryCodeRepo:queryCodeRepo
      };
      //查询流水列表
      function queryFlowNameList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryFlowNameList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取自动化测试数据
      function queryAutomateTestList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryAutomateTestList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取集成测试数据
      function queryIntegrationTestList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryIntegrationTestList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取构建数据
      function queryBuildList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryBuildList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取质量分析数据
      function queryQualityAnalysis(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryQualityAnalysis.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取部署数据
      function queryDeployList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryDeployList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取代码仓库数据
      function queryCodeRepoList(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryCodeRepoList.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
      //度量指标-获取代码仓库数据
      function queryCodeRepo(params, successCallback, failCallback){
        $http({
          url: '/paas/measure/queryCodeRepo.action',
          method: 'GET',
          params: params
        }).success(successCallback).error(failCallback);
      }
    });

  })();