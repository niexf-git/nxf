(function () {
  'use strict';

  /**
   * @description
   * # 路由配置
   */
  angular.module('PaaS5').config(function ($stateProvider, $urlRouterProvider, CORE_VALUEProvider) {
    var urlPrefix = CORE_VALUEProvider.$get().pageUrlPrefix;//页面获取不到时增加前缀
    $urlRouterProvider.otherwise('/overview');
    $stateProvider.state('PaaS5', {
      url: '',
      templateUrl: urlPrefix + 'views/main/main.html',
      controller: 'MainCtrl'
    })
    //流水列表-查询流水-添加流水-陈哲
      .state('PaaS5.serial', {
        url: '/serial',
        templateUrl: urlPrefix + 'views/serial/serial.html',
        controller: 'serialController',
        controllerAs: 'srl'
      })
      //流水详情界面
      .state('PaaS5.serialDetail', {
        url: '/serialDetail',
        templateUrl: urlPrefix + 'views/serialDetail/serialDetail.html',
        controller: 'serialDetailCtrl'
      })
      //流水执行记录-张树金
      .state('PaaS5.serialDetail.deployLog', {
        url: '/deployLog',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/deployLogList.html',
        controller: 'deployLogController'
      })
      //流水步骤-卢蒙
      .state('PaaS5.serialDetail.editModalDevOps', {
        url: '/editModalDevOps',
        templateUrl: urlPrefix + 'views/serialDetail/editModalDevOps/editModalDevOps.html',
        controller: 'editModalDevOpsCtrl'
      })
      //代码详情列表-卢蒙
      .state('PaaS5.serialDetail.codeCheck', {
        url: '/codeCheck',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/serialCodeDetails/codeCheck/code_check.html',
        controller: 'codeCheckCtrls'
      })
      //代码详情报告-卢蒙
      .state('PaaS5.serialDetail.codeCheckReport', {
        url: '/codeCheckReport',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/serialCodeDetails/checkReport/report.html',
        controller: 'checkReportCtrl'
      })
      //代码详情问题代码-卢蒙
       .state('PaaS5.serialDetail.checkReportDetail', {
        url: '/checkReportDetail',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/serialCodeDetails/checkReport/detail.html',
        controller: 'checkReportDetailCtrl'
      })
      //流水报告-张树金
      .state('PaaS5.serialDetail.stepReport', {
        url: '/stepReport',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/stepReport/stepReport.html',
        controller:'stepReportCtrl'
      })
      //静态报告页面-张树金
      .state('PaaS5.serialDetail.staticReport', {
        url: '/staticReport',
        templateUrl: urlPrefix + 'views/serialDetail/deployLog/stepReport/staticReport.html'
      })
      //流水报表-申佳
      .state('PaaS5.serialDetail.report', {
        url: '/report',
        templateUrl: urlPrefix + 'views/serialDetail/report/report.html',
        controller: 'reportCtrl'
      })
      //(外)报表-申佳
      .state('PaaS5.outReport', {
        url: '/outReport',
        templateUrl: urlPrefix + 'views/outReport/outReport.html',
        controller: 'outReportCtrl'
      })
      //总览-申佳
      .state('PaaS5.overview', {
        url: '/overview',
        templateUrl: urlPrefix + 'views/overview/overview.html',
        controller: 'overviewCtrl',
        controllerAs: 'ovC'
      });

  });

})();
