/**
 * Created by shenjia on 2017/7/10.
 */
(function () {
  'use strict';

  /**
   * @ngdoc overview
   * @name PaaS5
   * @description
   * # PaaS5
   *
   * Main module of the PaaS5.
   */
  angular
    .module('PaaS5')
    .run(function (CORE_VALUE, Restangular, $location, $rootScope) {
      //过滤请求
      // Restangular.addResponseInterceptor(function(data) {
      //   if(angular.isString(data) && data.length > 2){
      //     var fourIndex = data.indexOf('4');
      //     if (data.slice(fourIndex,3) === '401') {
      //       localStorage.clear();
      //       COREDATA.getFrom('chaoshi');
      //       $location.path('/login').replace();
      //       return data;
      //     }
      //   } else {
      //     COREDATA.getFrom('zhijie');
      //     return data;
      //   }
      // });
      // CORE_VALUE.PROJECTINFO = angular.fromJson(window.localStorage.projectInfo);//项目信息
      CORE_VALUE.PROJECTINFO.APPINFO = angular.fromJson(window.sessionStorage.appInfo);//应用信息
      CORE_VALUE.PROJECTINFO.FLOWINFO = angular.fromJson(window.sessionStorage.serialInfo);//流水信息
      $rootScope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;
    })

})();
