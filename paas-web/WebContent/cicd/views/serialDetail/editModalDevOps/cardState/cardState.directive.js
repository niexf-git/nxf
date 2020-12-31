(function () {
  'use strict';

  /**
   * 卡片状态directive
   */
  angular.module('PaaS5.serialDetail')
    .directive('cardState', cardState);

  function cardState(CORE_VALUE) {
    return {
      restrict: 'EA',
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/cardState/cardState.html',
      scope: {
        stepState: '='
      },
      replace: true,
      link:  function (scope) {
        scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
      }
    };
  }

})();
