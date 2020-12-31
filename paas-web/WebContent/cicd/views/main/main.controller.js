(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name PaaS5.controller:MainCtrl
   * @description
   * # MainCtrl
   * Controller of the PaaS5
   */
  angular.module('PaaS5.controllers', [
    'PaaS5.serialDetail',
    'PaaS5.controllers.outReport'
  ])
    .controller('MainCtrl', function ($scope, $state, $q, CORE_VALUE, COREDATA) {

      //判断导航栏是否进入的是CICD
      $scope.isCICD = CORE_VALUE.isCICD;
      COREDATA.setUserInfo();//查询用户信息
      /**
       * 导航栏样式
       */
      $scope.$on('$stateChangeSuccess', function (ev, state) {
        var navName = state.name.split('.');
        if(navName.length === 1){
          $state.go('PaaS5.overview');
        }
        var createNavInfo = function(){
          var d = $q.defer();
          CORE_VALUE.isCICD.navInfo = navName.map(function (value, index, arr) {
            var tempUrl = '';
            angular.forEach(arr.slice(0,index+1),function (value, key) {
              if(key === index){
                if(value === 'serialDetail'){
                  tempUrl += 'serial';
                }else{
                  tempUrl += value;
                }
              }else {
                tempUrl += value+'.';
              }
            });
            return {
              name: value,
              url: tempUrl
            }
          });
          d.resolve('navInfo生成完成');
          return d.promise;
        }
        createNavInfo().then(function(data){
          if(CORE_VALUE.isCICD.navInfo[1].name === 'overview'){
            CORE_VALUE.isCICD.navInfo = CORE_VALUE.isCICD.navInfo.slice(1,2);
          }
          if(CORE_VALUE.isCICD.navInfo[0].name === 'PaaS5'){
            CORE_VALUE.isCICD.navInfo[0].url = 'PaaS5.serial';
          }
        });
        
      })

    });

})();
