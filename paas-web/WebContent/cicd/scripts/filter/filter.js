(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name PaaS5.filters:codeFilter
   * @description
   * # MainFilter
   * Filter of the PaaS5
   */
  angular.module('PaaS5.filters', [])
    .filter('codeFilter', codeFilter)
    .filter('executeStatus', executeStatus)
    .filter('checkStepName', checkStepName)
    .filter('stepStatusFilter', stepStatusFilter)
    .filter('navNameFilter', navNameFilter)
    .filter('operTypeFilter', operTypeFilter)
    .filter('statusFilter',statusFilter);
  function codeFilter() {
    return function (value) {
      var str = value.substring(0, 8);
      str = str + '...';
      return str;
    };
  }

  function executeStatus() {
    return function (value) {
      switch (value + '') {
        case '0':
          return '未执行';
        case '1':
          return '执行成功';
        case '2':
          return '执行失败';
        case '3':
          return '运行中';
        case '4':
          return '等待中';
      }
    };
  }

  function checkStepName() {
    return function (value) {
      switch (value + '') {
        case 'downloadcheck':
          return '代码审查';
        case 'build':
          return '编译构建';
        case 'depscan':
          return '部署扫描';
        case 'autotest':
          return '自动化测试';
        case 'intetest':
          return '集成测试';
        case 'release':
          return '发布';
        case 'testdeploy':
          return '测试部署';
        case 'testautotest':
          return '自动化测试';
        case 'testintetest':
          return '集成测试';
        case 'testrelease':
          return '发布生产';
        case 'perftest':
          return '性能测试';
        case 'testperftest':
          return '性能测试';

      }
    };
  }

  function stepStatusFilter() {
    return function (value) {
      switch (value + '') {
        case '0':
          return '等待执行';
        case '1':
          return '执行中...';
        case '2':
          return '执行成功';
        case '3':
          return '执行失败';
      }
    };
  }

  function navNameFilter() {
    return function (value) {
      switch (value + '') {
        case 'PaaS5':
          return 'CI/CD';
        case 'overview':
          return ' 总览';
        case 'outReport':
          return ' > 报表';
        case 'serial':
          return ' > 流水';
        case 'serialDetail':
          return ' > 流水';//流水详情
        case 'deployLog':
          return ' > 流水执行记录';
        case 'editModalDevOps':
          return ' > 流水详情';//流水步骤
        case 'stepReport':
          return ' > 流水报告';
        case 'staticReport':
          return ' > 静态报告页面';
        case '流水定义':
          return ' > editModalDevOps';
        case 'report':
          return ' > 流水报表';
        case 'codeCheck':
          return ' > 代码详情列表';
        case 'codeCheckReport':
          return ' > 代码详情报告';
        case 'checkReportDetail':
          return ' > 代码详情问题代码';
      }
    };
  }

  function operTypeFilter() {
    return function(num) {
      switch(num + '') {
        case '1':
          return '开发';
        case '2':
          return '测试';
      }
    };
  }
  function statusFilter() {
    return function (value) {
      switch (value + '') {
        case '0':
          return '未执行';
        case '1':
          return '成功';
        case '2':
          return '失败';
        case '3':
          return '运行中';
        case '4':
          return '等待中';
        case '5':
          return '删除';
      }
    };
  }

})();
