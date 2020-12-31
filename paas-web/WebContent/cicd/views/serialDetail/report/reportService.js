(function () {
  'use strict';

  angular.module('PaaS5.services')
    .factory('reportFactory', function ($http, COREDATA) {

      //查询报告
      return {
        getCodeQuality: getCodeQuality,
        getBuildRecode: getBuildRecode,
        getAutoTest: getAutoTest,
        getIntegrate: getIntegrate,
        //by tanzq
        getPerformance: getPerformance,
        getUnitTest: getUnitTest
      };
      //代码质量 /paas/reportform/queryCodeQualityList.action
      function getCodeQuality(params, tableDta) {
        $http({
          method: 'get',
          url: '/paas/reportform/queryCodeQualityList.action',
          params: params
        }).success(function (response) {
          // COREDATA.resMessage(response.resultCode, response.resultMessage);
          tableDta.list = JSON.parse(response.data).result;
          tableDta.totalCount = JSON.parse(response.data).totalCount;
        }).error(function (response) {
          COREDATA.resMessage(response.resultCode, response.resultMessage);
        });
      }
      //构建记录 /paas/reportform/queryBuildRecordList.action
      function getBuildRecode(params, tableDta) {
        $http({
          method: 'get',
          url: '/paas/reportform/queryBuildRecordList.action',
          params: params
        }).success(function (response) {
          tableDta.list = JSON.parse(response.data).result;
          tableDta.totalCount = JSON.parse(response.data).totalCount;
        }).error(function (response) {
          COREDATA.resMessage(response.resultCode, response.resultMessage);
        });
      }
      //自动化测试 /paas/reportform/queryAutoTestReportList.action
      function getAutoTest(params, tableDta) {
        $http({
          method: 'get',
          url: '/paas/reportform/queryAutoTestReportList.action',
          params: params
        }).success(function (response) {
          tableDta.list = JSON.parse(response.data).result;
          tableDta.totalCount = JSON.parse(response.data).totalCount;
        }).error(function (response) {
          COREDATA.resMessage(response.resultCode, response.resultMessage);
        });
      }
      //集成测试 /paas/reportform/queryIntegrateTestReports.action
      function getIntegrate(params, tableDta) {
        $http({
          method: 'get',
          url: '/paas/reportform/queryIntegrateTestReports.action',
          params: params
        }).success(function (response) {
          tableDta.list = JSON.parse(response.data).result;
          tableDta.totalCount = JSON.parse(response.data).totalCount;
        }).error(function (response) {
          COREDATA.resMessage(response.resultCode, response.resultMessage);
        });
      }
      //by tanzq 性能测试
        function getPerformance(params, tableDta) {
          $http({
            method: 'get',
            url: '/paas/reportform/queryPerformanceTestReportList.action',
            params: params
          }).success(function (response) {
            tableDta.list = JSON.parse(response.data).result;
            tableDta.totalCount = JSON.parse(response.data).totalCount;
          }).error(function (response) {
            COREDATA.resMessage(response.resultCode, response.resultMessage);
          });
        }
        //单元测试
        function getUnitTest(params, tableDta) {
          $http({
            method: 'get',
            url: '/paas/reportform/queryUnitTestReportList.action',
            params: params
          }).success(function (response) {
            tableDta.list = JSON.parse(response.data).result;
            tableDta.totalCount = JSON.parse(response.data).totalCount;
          }).error(function (response) {
            COREDATA.resMessage(response.resultCode, response.resultMessage);
          });
        }

    });

})();
