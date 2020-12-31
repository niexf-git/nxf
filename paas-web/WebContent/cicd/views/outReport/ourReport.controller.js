(function () {
  'use strict';
  angular.module('PaaS5.controllers.outReport', []).controller('outReportCtrl', outReportCtrl);
  function outReportCtrl($scope, $rootScope, $state, CORE_VALUE, outReportFactory) {
    console.log(CORE_VALUE.USERINFO);

    $scope.userInfo = CORE_VALUE.USERINFO;//判断用户是开发还是测试 //operType: 1(开发), 2(测试)
    $scope.showName = 'codeQuality';//显示哪一个表(1.2.3.4.5.6.)
    //查询参数
    $scope.queryParams = {
      codeQualityParams: {url: '/paas/reportform/queryCodeQualityReport.action', pageParam: {page: '1', rows: '10'}},//查询代码质量报表params
      buildRecordParams: {url: '/paas/reportform/queryBuildRecordReport.action', pageParam: {page: '1', rows: '10'}},//查询构建记录报表params
      autoTestParams: {url: '/paas/reportform/queryAutoTestReport.action', pageParam: {page: '1', rows: '10'}},//查询自动化测试报表params
      integrateTestParams: {url: '/paas/reportform/queryIntegrateTestReport.action', pageParam: {page: '1', rows: '10'}},//查询集成测试报表params
      unitTestParams: {url: '/paas/reportform/queryUnitTestReport.action', pageParam: {page: '1', rows: '10'}},//查询单元测试报表params
      performanceTestParams: {url: '/paas/reportform/queryPerformanceTestReport.action', pageParam: {page: '1', rows: '10'}}//查询性能测试报表params
    };
    //表格数据
    $scope.tableData = {
      codeQuality: {list: [], totalCount: ''},//代码质量表格data
      buildRecord: {list: [], totalCount: ''},//构建记录表格data
      autoTest: {list: [], totalCount: ''},//自动化测试表格data
      integrateTest: {list: [], totalCount: ''},//集成测试表格data
      unitTest: {list: [], totalCount: ''},//单元测试表格data
      performanceTest: {list: [], totalCount: ''}//性能测试表格data
    };
    //展示报告
    $scope.showTable = function (showName) {
      $scope.showName = showName;
      if (showName === 'codeQuality') {
        outReportFactory.getSixTabData($scope.queryParams.codeQualityParams, $scope.tableData.codeQuality);
      } else if (showName === 'buildRecord') {
        outReportFactory.getSixTabData($scope.queryParams.buildRecordParams, $scope.tableData.buildRecord);
      } else if (showName === 'autoTest') {
        outReportFactory.getSixTabData($scope.queryParams.autoTestParams, $scope.tableData.autoTest);
      } else if (showName === 'integrateTest') {
        outReportFactory.getSixTabData($scope.queryParams.integrateTestParams, $scope.tableData.integrateTest);
      } else if (showName === 'unitTest') {
        outReportFactory.getSixTabData($scope.queryParams.unitTestParams, $scope.tableData.unitTest);
      } else if (showName === 'performanceTest') {
        outReportFactory.getSixTabData($scope.queryParams.performanceTestParams, $scope.tableData.performanceTest);
      }
    };

    $scope.showChart = function (showName, flowId, flowName) {
      if (showName === 'codeQuality') {
        outReportFactory.getSixTabChart('/paas/reportform/queryCodeQualityLineChart.action', flowId, flowName);
      } else if (showName === 'autoTest') {
        outReportFactory.getSixTabChart('/paas/reportform/queryAutoTestLineChart.action', flowId, flowName);
      } else if (showName === 'integrateTest') {
        outReportFactory.getSixTabChart('/paas/reportform/queryIntegrateTestLineChart.action', flowId, flowName);
      } else if (showName === 'unitTest') {
        outReportFactory.getSixTabChart('/paas/reportform/queryUnitTestLineChart.action', flowId, flowName);
      } else if (showName === 'performanceTest') {
        outReportFactory.getSixTabChart('/paas/reportform/queryPerformanceTestLineChart.action', flowId, flowName);
      }

    };
    //是否显示代码质量与单元测试
    function isTest() {
      if(CORE_VALUE.PROJECTINFO.APPINFO.operType === '2' || $scope.userInfo.operType === '2'){
        $scope.isTest = true;
        $scope.showTable('buildRecord');
      }else{
        $scope.isTest = false;
        $scope.showTable('codeQuality');
      }
    }
    isTest();

    //切换应用刷新
    $scope.$on('changeHeaderAppSuccess', function () {
      isTest();
    });


  }

})();
