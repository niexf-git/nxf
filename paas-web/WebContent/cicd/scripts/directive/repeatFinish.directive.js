(function () {
  'use strict';
  /**
   * @ngdoc directive
   * @name repeatFinish
   * @module PaaS5
   * @restrict EA
   * @description repeat完成指令
   */
  /**
   * 使用方法：
   * <li ng-repeat="oneData in ResData" repeat-finish">
   */
  angular.module('PaaS5').directive('repeatFinish', function () {
    return {
      link: function (scope, element, attr) {
        if (scope.$last == true) {
          //向父控制器传递事件
          scope.$emit('repeat-to-parent');
          //向子控制器传递事件
          scope.$broadcast('repeat-to-child');
        }
      }
    }
  });

})();
