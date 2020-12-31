(function () {
  'use strict';

  angular.module('PaaS5.serialDetail')
    .controller('reportCtrl', function ($scope, reportFactory, CORE_VALUE) {

      $scope.showName = 'quality';
      //查询参数
      $scope.queryParams = {
        codeParams: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'},
        buildParams: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'},
        autoParams: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'},
        inteParams: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'},
        //by tanzq
        performance: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'},
        unitTest: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, page: '1', rows: '10'}
      };
      //表格数据
      /**
       * ===代码质量表格
       * codeQuality.list []
       * executionRecord: '';//执行记录
       * cause:string;//致使
       * serious:string;//严重
       * commonly:string;//一般
       * slight:string;//轻微
       * repeat:string;//重复
       * loopComplexity:string//圈复杂度
       * securityVulnerabilities:string//安全漏洞
       * person:string//责任人
       * ===构建记录表格
       * buildRecode.list []
       * executionRecord: '';//执行记录
       * downloadTime:string;//下载审查时间
       * downloadMessage:string;//下载审查结果
       * buildTime:string;//编译构建时间
       * buildMessage:string;//编译构建结果
       * deployTime:string;//部署扫描时间
       * deployMessage:string;//部署扫描结果
       * autoTime:string;//自动测试时间
       * autoMessage:string;//自动测试结果
       * integrateTime:string;//集成测试时间
       * integrateMessage:string;//集成测试结果
       * performanceTime:stirng//性能测试时间
       * performanceMessage:string//性能测试结果
       * releaseTime:string;//发布测试时间
       * releaseMessage:string;//发布测试结果
       * ===自动化测试表格
       * integrate.list []
       * executionRecord: '';//执行记录
       * totals:string //用例总数
       * successNumber:string //成功数
       * successRate:成功率 //成功数
       * failNumber:string //失败数
       * failRate:string //失败率
       * person:string //责任人
       * ===集成测试表格
       * integrate.list []
       * executionRecord: '';//执行记录
       * executions:string //执行次数
       * totals:string //用例总数
       * successNumber:string //成功数
       * successRate:string //成功率
       * failNumber:string //失败数
       * failRate:string //失败率
       * person:string //责任人
       */
      $scope.tableData = {
        codeQuality: {list:[], totalCount: ''},//代码质量表格数据
        buildRecode: {list:[], totalCount: ''},//构建记录表格数据
        autoTest: {list:[], totalCount: ''},//自动化测试表格数据
        integrate: {list:[], totalCount: ''},//集成测试表格数据
        //by tanzq
        performance:{list:[], totalCount: ''},
        unitTest:{list:[], totalCount: ''}
      };
      //展示报告
      $scope.showTable = function (showName) {
        $scope.showName = showName;
        if(showName === 'quality'){
          reportFactory.getCodeQuality($scope.queryParams.codeParams, $scope.tableData.codeQuality);
        }else if(showName === 'record'){
          reportFactory.getBuildRecode($scope.queryParams.buildParams, $scope.tableData.buildRecode);
        }else if(showName === 'auto'){
          reportFactory.getAutoTest($scope.queryParams.autoParams, $scope.tableData.autoTest);
        }else if(showName === 'integration'){
          reportFactory.getIntegrate($scope.queryParams.inteParams, $scope.tableData.integrate);
        }//by tanzq
        else if(showName === 'performance'){
          reportFactory.getPerformance($scope.queryParams.performance, $scope.tableData.performance);
        }else if(showName === 'unitTest'){
          reportFactory.getUnitTest($scope.queryParams.unitTest, $scope.tableData.unitTest);
        }
      };

      // $scope.$on('serialDetailFresh', function(event, value) {
      //   if(value === 'auto')
      //     return;
      //   $scope.showTable($scope.showName);
      // });
      //是否显示代码质量与单元测试
      function isTest() {
        // operType: 1(开发), 2(测试)
        if(CORE_VALUE.PROJECTINFO.FLOWINFO.roleType === '2'){
          $scope.isTest = true;
          $scope.showTable('record');
        }else{
          $scope.isTest = false;
          $scope.showTable('quality');
        }
      }
      isTest();

    });

})();
