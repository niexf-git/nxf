/**
 * Created by shenjia on 2017/7/13.
 */
(function () {
  'use strict';

  /**
   * @description
   * # Nav页面
   */
  angular.module('PaaS5').controller('LayoutNavCtrl', function ($rootScope, $scope, $state, layoutNavService, layoutHeaderService, $timeout, CORE_VALUE, navState) {
    $scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;
    $scope.queryMenuPermissionData = {}; //获取菜单栏请求参数
    $scope.queryMenuPermissionResData = null; //获取菜单栏请求参数
    $scope.adjustResData = navState.menu; //存储调整返回的响应数据
    
    /**
     * 显示iframe内嵌页面，关闭CICD页面
     * @constructor
     */
    $rootScope.SHOWIFRAME = function() {
      CORE_VALUE.isCICD.value1 = false;
      CORE_VALUE.isCICD.value2 = true;
    };
    /**
     * 关闭iframe内嵌页面，显示CICD页面
     * @constructor
     */
    $rootScope.CLOSEIFRAME = function() {
      CORE_VALUE.isCICD.value1 = true;
      CORE_VALUE.isCICD.value2 = false;
    };
    
    //检查部署在哪个环境，运维环境返回true,就得显示流水"全部"
    $rootScope.environment = false;
    layoutHeaderService.getEnvironment(queryMu);
    //获取菜单栏数据
    function queryMu(oldResData, newResData) {
      layoutNavService.queryMenuPermission($scope.queryMenuPermissionData,
        function (res) { //请求成功
          if (res.resultCode === "200" && res.data.length >= 1) {
            $scope.queryMenuPermissionResData = JSON.parse(res.data);
            adjustResponseDataFormat(JSON.parse(res.data), $scope.adjustResData);
          }
        },
        function (res) { //请求失败
          console.log("请求失败! " + window.location.href);
        }
      )
    }
    
    
    //调整返回的响应数据格式，适应页面的使用
    function adjustResponseDataFormat(oldResData, newResData) {
      var newResDataNum = 0, cache = [];
      for (var i = 0; i < oldResData.length; i++) {
        if (oldResData[i].pId === "0") {
          newResData[newResDataNum] = oldResData[i];
          newResData[newResDataNum].hasChild = false;
          newResData[newResDataNum].childrens = [];
          newResData[newResDataNum].initStyle = "";
          newResData[newResDataNum].isInside = false; //是否在这个模块
          newResData[newResDataNum].openState = false; //菜单是否展开
          if (newResData[newResDataNum].name === '系统管理' || newResData[newResDataNum].name === '镜像管理' || newResData[newResDataNum].name === 'CI/CD' || newResData[newResDataNum].name === '总览') {
            newResData[newResDataNum].url = 'javascript:void(0);';
            newResData[newResDataNum].targetObj = 'noPage';
          } else {
            newResData[newResDataNum].targetObj = 'oldPage';
          }
          newResDataNum++;
        } else {
          cache.push(oldResData[i]);
        }
      }
      cache.forEach(function(obj){
    	  newResData.forEach(function(_obj) {
    		  if (obj.pId === _obj.id) {
    	            _obj.childrens.push(obj);
    	            _obj.hasChild = true;
    	          }
    	  });
      });
      delCICD(newResData);
    }
    //运维环境删除cicd
    function delCICD(menu){
      if ($rootScope.environment) {
        var tempKey;
        menu.forEach(function (value, key) {
          if (value.name === 'CI/CD') {
            tempKey = key;
          }
        });
        tempKey !== undefined && menu.splice(tempKey, 1);
      }
    }
    //显示二级导航栏
    $scope.showChildNav = function (event, resData) {
      //判断是否点击的是CICD
      if (resData.name === "CI/CD") {
        CORE_VALUE.isCICD.value1 = true;
        CORE_VALUE.isCICD.value2 = false;
        $rootScope.$broadcast("isClickCICD", "ok");
        $state.go('PaaS5.serial');
      } else if (resData.name === '总览') {
        CORE_VALUE.isCICD.value1 = true;
        CORE_VALUE.isCICD.value2 = false;
        $rootScope.$broadcast("clickOverview", "ok");
        $state.go('PaaS5.overview');
      } else if (resData.name === '镜像管理' || resData.name === '系统管理') {
        // CORE_VALUE.isCICD.value1 = true;
        // CORE_VALUE.isCICD.value2 = false;
      } else {
        CORE_VALUE.isCICD.value1 = false;
        CORE_VALUE.isCICD.value2 = true;
      }
      if(resData.name === '服务管理'){
        $rootScope.$broadcast('update', '服务管理');
      }
    };
    window.showChildNav = $scope.showChildNav;
    $scope.switchNav = function (childData, childName, event) {
      event.stopPropagation();
      CORE_VALUE.isCICD.value1 = false;
      CORE_VALUE.isCICD.value2 = true;
      angular.forEach($scope.adjustResData, function (value) {
        value.isInside = false;
      });
      angular.forEach(childData, function (value) {
        value.isInside = value.name === childName;
      });
    };

    //点击导航栏样式
    $scope.addActiveClass = function (navName) {
      $rootScope.$broadcast('switchNav');
      if(CORE_VALUE.isCICD.value2){
    	    $("a[href*='/paas/jsp/']").each(function(index){
    			$(this).removeClass("active");
    		});
      }
      if (navName) {
        angular.forEach($scope.adjustResData, function (value) {
          value.isInside = value.name === navName;
          if (value.hasChild) {
            angular.forEach(value.childrens, function (value1) {
              value1.isInside = false;
            });
          }
          if (value.name === navName) {
            value.openState = !value.openState;
          }
          $rootScope.$broadcast('update', navName);
        });
      } else {
        $scope.adjustResData[0].isInside = true;
        $scope.navName = $scope.adjustResData[0].name;
        if($scope.navName === '总览'){
          CORE_VALUE.isCICD.value1 = true;
          CORE_VALUE.isCICD.value2 = false;
          $state.go('PaaS5.overview');
        }
        $rootScope.$broadcast('update', $scope.navName);
      }
    };
    window.addActiveClass = $scope.addActiveClass;
    $scope.$on('repeat-to-parent', function () {
      $scope.addActiveClass();
    });

    $scope.$on('isJump', function (event, data) {
      if (data === 'CI/CD') {
        var hash = window.location.hash;
        if (navState.changeAppLinkToCicd.indexOf(hash) !== -1) {
          $state.go('PaaS5.serial');
        }
      } else if (navState.iframeNotReload.indexOf(data) === -1) {
        try {
          $('ul.nav-sidebar a.active>i').trigger('click');
        } catch (error) {
          throw new Error('点击NAV出错');
        }
      }

    });
    $scope.showZonglan = function (){
      CORE_VALUE.isCICD.value1 = true;
      CORE_VALUE.isCICD.value2 = false;
      $state.go('PaaS5.overview');
    };

  });

  angular.module('PaaS5').service('layoutNavService', function ($http) {
    //对接后台获取数据
    return {
      queryMenuPermission: queryMenuPermission
    };
    //获取菜单栏数据
    function queryMenuPermission(params, successCallback, failCallback) {
      $http({
        url: '/paas/permission/queryMenuPermission.action',
        method: 'GET',
        data: params
      }).success(successCallback).error(failCallback);
    }
  });


  angular.module('PaaS5').directive('layoutNav', function (CORE_VALUE) {
    return {
      restrict: 'EA',
      scope: {},
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/layout/_nav.html',
      controller: 'LayoutNavCtrl'
    };
  });

})();
