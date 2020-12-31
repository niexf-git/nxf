(function () {
  'use strict';

  // 这里只能注入constant和各种Provider。本阶段主要用于通过Provider对各种服务进行配置。
  angular.module('PaaS5').constant("navState", {
    menu: [],
    // 当hash在下面地址，且处于CICD模块下时，切换应用时跳到CICD模块主页
    changeAppLinkToCicd: ['#/serialDetail/editModalDevOps', '#/serialDetail/report', '#/serialDetail/deployLog', '#/serialDetail/staticReport', '#/serialDetail/stepReport'],
    // 当iframe处于下面模块时，切换应用不刷新
    iframeNotReload: ['监控运维'],
  });

})();
