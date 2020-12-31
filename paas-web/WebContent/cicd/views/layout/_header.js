/**
 * Created by shenjia on 2017/7/13.
 */
(function () {
  'use strict';

  /**
   * @description
   * # Header页面
   */
  angular.module('PaaS5').controller('LayoutHeaderCtrl', function ($rootScope, serialServer, $scope, layoutHeaderService, CORE_VALUE, $timeout, $window, toastr) {
    $scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;
    $scope.queryAppInfoData = {}; //获取应用列表请求参数
    $scope.changeAppInfoData = {}; //保存应用列表请求参数
    $scope.queryAppInfoResData = {}; //保存应用列表响应结果
    $scope.changeAppInfoResData = {}; //保存应用列表响应结果
    $scope.loginUserInfo = {}; //保存登录用户的信息
    $scope.app = { //导航头应用默认为"全部",当选择具体的应用之后会变成具体的应用的名称
      appName: '全部',
      id: '0',
      operType: '' //操作类型 1:开发 2:测试
    };
    $scope.userInfo = { //用户信息
      oldPassWord: "",
      newPassWord: "",
      confirmPassWord: ""
    };

    //判断导航栏是否进入的是CICD
    $scope.isCICD = CORE_VALUE.isCICD;
    $scope.navNameData = {
      navName: ""
    };
    $rootScope.$on('update', function (event, navName) {
      $timeout(function(){
        $scope.navNameData.navName = navName;
      },100);
    });
    
    $scope.init = function() {
      $scope.changeHeaderApp($scope.app);
    }
 

    var result; //定时器返回值
    //判断window.sessionStorage.appInfo初始值是不是为空，为空显示”全部“，否则显示window.sessionStorage.appInfo相应的值
    if (window.sessionStorage.appInfo !== undefined) {
      $scope.app = CORE_VALUE.PROJECTINFO.APPINFO = angular.fromJson(window.sessionStorage.appInfo);
    } else {
      CORE_VALUE.PROJECTINFO.APPINFO = {
        appName: '全部',
        id: '0',
        operType: ''
      };
    }
    console.log(CORE_VALUE.PROJECTINFO.APPINFO);
    

    //调整数据的格式便于页面展示
    $scope.adjustResDataFormat = function (resData) {
      var tmp = resData.result;
      resData.formatResult = {};
      resData.formatResult.admin = null;
      resData.formatResult.data = [];
      resData.formatResult.admin = tmp.admin;
      if (resData.formatResult.admin == true) { //管理员角色
        for (var i = 0; i < tmp.appInfo.length; i++) {
          resData.formatResult.data[i] = {};
          resData.formatResult.data[i].id = tmp.appInfo[i].id;
          if (tmp.appInfo[i].operType == '1') {
            resData.formatResult.data[i].appName = tmp.appInfo[i].appName + ' 开发';
          } else if (tmp.appInfo[i].operType == '2') {
            resData.formatResult.data[i].appName = tmp.appInfo[i].appName + ' 测试';
          } else if (tmp.appInfo[i].operType == '3') {
            resData.formatResult.data[i].appName = tmp.appInfo[i].appName + ' 运维';
          }
          resData.formatResult.data[i].operType = tmp.appInfo[i].operType;
        }
      } else { //普通用户角色
        for (var i = 0; i < tmp.appInfo.length; i++) {
          resData.formatResult.data[i] = {};
          resData.formatResult.data[i].id = tmp.appInfo[i].id;
          resData.formatResult.data[i].appName = tmp.appInfo[i].appName;
          resData.formatResult.data[i].operType = tmp.appInfo[i].operType;
        }
  
        //当只有一个应用的时候，不显示全部，并切换到这个应用
        if(resData.formatResult.data.length === 1){
          $scope.changeHeaderApp(resData.formatResult.data[0]);
        }
      }
      
    };
    //获取应用列表数据
    layoutHeaderService.queryAppInfo($scope.queryAppInfoData,
      function (res) { //请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          $scope.queryAppInfoResData.result = JSON.parse(res.data);
          $scope.adjustResDataFormat($scope.queryAppInfoResData);
        }
      },
      function (res) { //请求失败
        console.log("请求失败! " + window.location.href);
      }
    );
    //取正在登陆的用户信息
    layoutHeaderService.queryUserRoleAndOperType({},
      function (res) { //请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          $scope.loginUserInfo.data = JSON.parse(res.data);
        }
      },
      function (res) { //请求失败
        console.log("请求失败! " + window.location.href);
      }
    );
    //更改导航头处应用，并向后台发送选择的结果
    $scope.changeHeaderApp = function (appData, events) {
      function getHasSearch() {
        if ($window.frames["oldPage"].document.getElementById("search") !== null) {
          return true;
        } else {
          return false;
        }
      }

      function getActiveNavName() {
        var active = $('ul.nav-sidebar a.active'),
            name = active.text().trim();
        return name;
      }
      if (!events || events.button === 0 || events.button === 1 ) {
        if (appData == null) { //选择全部的话，返回0
          $scope.app = {
            appName: '全部',
            id: '0',
            operType: ''
          };
        } else { //选择除全部以外的应用
          $scope.app = appData;
        }
        $scope.changeAppInfoData.appId = $scope.app.id;
        $scope.changeAppInfoData.typeName = $scope.app.operType;
        layoutHeaderService.changeAppInfo($scope.changeAppInfoData,
          function (res) { //请求成功
            $timeout.cancel(result);
            if (res.resultCode === "200") {
              console.log("请求成功!");
              var activeName = getActiveNavName();
              // iframe刷新
              if(activeName === '服务管理') $window.frames["oldPage"].document.location.reload();
              CORE_VALUE.PROJECTINFO.APPINFO = $scope.app;
              window.sessionStorage.appInfo = angular.toJson($scope.app);
              $rootScope.$broadcast("changeHeaderAppSuccess", "ok");
              //和原有的jsp界面进行交互，当更换应用的时候模拟点击jsp页面的查询按钮
              // 判断是否在列表页
              if(activeName === '基础服务') {$window.frames["oldPage"].document.location.reload(true);}
              if (getHasSearch() && activeName !== 'CI/CD') {
                console.log("getHasSearch() && activeName !== 'CI/CD'")
                // 待优化： 切换应用时，iframe 是否刷新判断过于混乱，建议集中处理
                if(activeName === '审计日志') return;
                $scope.simulationClickEvent();
              } else {
                // 待优化： 切换应用时，iframe 是否刷新判断过于混乱，建议集中处理
                if(activeName === '审计日志') return;
                $rootScope.$broadcast("isJump", activeName);
              }
            }
          },
          function (res) { //请求失败
            console.log("请求失败! " + window.location.href);
          }
        );
      }
    };
    //显示导航头"全部"看板
    $scope.showTotalPanel = function (event) {
      var target = event.currentTarget;
      //var topPosition = $("#navbar li.total").offset().top + 60;
      //var leftPosition = $("#navbar li.total").offset().left - 30;
      //var style = "top:" + topPosition + "px; " + "left:" + leftPosition + "px;";
      var hasHiddenClass = $("#totalPanel").hasClass("hidden");
      if (hasHiddenClass == true) {
        //获取应用列表数据
        layoutHeaderService.queryAppInfo($scope.queryAppInfoData,
          function (res) { //请求成功
            if (res.resultCode === "200" && res.data.length >= 1) {
              $scope.queryAppInfoResData.result = JSON.parse(res.data);
              $scope.adjustResDataFormat($scope.queryAppInfoResData);
              //显示下拉框
             // $("#totalPanel").attr("style", style);
              $("#totalPanel").removeClass("hidden");
              $(target).removeClass("fa-angle-down");
              $(target).addClass("fa-angle-up");
            }
          },
          function (res) { //请求失败
            console.log("请求失败! " + window.location.href);
          }
        );
      }
    }
    //隐藏导航头"全部"看板
    $scope.hideTotalPanel = function (event) {
      var target = event.currentTarget;
      var hasHiddenClass = $("#totalPanel").hasClass("hidden");
      if (hasHiddenClass == false) {
        $("#totalPanel").addClass("hidden");
        $(target).removeClass("fa-angle-up");
        $(target).addClass("fa-angle-down");
      }
    }
    //显示导航头"Admin"看板
    $scope.showAdminPanel = function (event) {
      // var target = event.currentTarget;
      // var topPosition = $("#navbar li.admin").offset().top + 60;
      // var leftPosition = $("#navbar li.admin").offset().left + 0;
      // var style = "top:" + topPosition + "px; " + "left:" + leftPosition + "px;";
      var hasHiddenClass = $("#adminPanel").hasClass("hidden");
      if (hasHiddenClass == true) {
        // $("#adminPanel").attr("style", style);
        $("#adminPanel").removeClass("hidden");
        // $(target).removeClass("fa-angle-down");
        // $(target).addClass("fa-angle-up");
      }
    }
    //隐藏导航头"Admin"看板
    $scope.hideAdminPanel = function (event) {
      var target = event.currentTarget;
      var hasHiddenClass = $("#adminPanel").hasClass("hidden");
      if (hasHiddenClass == false) {
        $("#adminPanel").addClass("hidden");
        // $(target).removeClass("fa-angle-up");
        // $(target).addClass("fa-angle-down");
      }
    }
    //跳转到修改密码页面
    $scope.changePassword = function () {
      //模拟点击一下，使得mask能够出来，ng-blur使得点击无效，所以的模拟点击
      $scope.userInfo.oldPassWord = "";
      $scope.userInfo.newPassWord = "";
      $scope.userInfo.confirmPassWord = "";
      $scope.passwordModify.$setPristine();
      $scope.passwordModify.$setUntouched();
      $("li#cgpw").click();
    }
    //退出当前用户账号，返回登录界面
    $scope.backToLoginPage = function () {
      layoutHeaderService.deleteSessionInfo({}, //删除Session信息
        function (res) { //请求成功
          console.log("请求成功");
          window.localStorage.clear(); //清空缓存
          window.sessionStorage.clear();
          window.location.replace(window.location.origin + "/paas/jsp/index.jsp");
        },
        function (res) { //请求失败
        }
      );
    }
    //和原有的jsp界面进行交互，当更换应用的时候模拟点击jsp页面的查询按钮
    $scope.simulationClickEvent = function () {
      if (window.frames["oldPage"].document.getElementById("search") != null)
        window.frames["oldPage"].document.getElementById("search").click();
    }
    //用户填写完密码后，先检查是否合规，然后再发送请求保存
    $scope.saveData = function () {
      var passwordModifyFormInvalid = $scope.passwordModify.$invalid;
      var params = {
        password: md5($scope.userInfo.newPassWord + CORE_VALUE.USERINFO.loginName),
        oldPassword: md5($scope.userInfo.oldPassWord + CORE_VALUE.USERINFO.loginName)
      };
      if (!passwordModifyFormInvalid) {
        layoutHeaderService.updatePassword(params,
          function (res) { //请求成功
            if(res.resultCode === 'success') {
              $("#changePassWordCancelId").click();
              toastr.success(res.resultMessage);
            }else {
              toastr.warning(res.resultMessage);
            }
          },
          function (res) { //请求失败
            console.log("请求失败");
          }
        );
      }
    }

  });

  angular.module('PaaS5').service('layoutHeaderService', function ($http, $rootScope) {
    //对接后台获取数据
    return {
      queryAppInfo: queryAppInfo,
      changeAppInfo: changeAppInfo,
      deleteSessionInfo: deleteSessionInfo,
      updatePassword: updatePassword,
      queryUserRoleAndOperType: queryUserRoleAndOperType,
      getEnvironment: getEnvironment
    }
    //获取应用列表数据
    function queryAppInfo(params, successCallback, failCallback) {
      $http({
        url: '/paas/user/queryAppInfo.action',
        method: 'GET',
        data: params
      }).success(successCallback).error(failCallback);
    }
    //保存应用列表数据
    function changeAppInfo(params, successCallback, failCallback) {
      $http({
        url: '/paas/user/changeAppInfo.action',
        method: 'post',
        data: params,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).success(successCallback).error(failCallback);
    }
    //删除Session信息
    function deleteSessionInfo(params, successCallback, failCallback) {
      $http({
        url: '/paas/user/deleteSessionInfo.action',
        method: 'GET',
        cache: false,
        data: params
      }).success(successCallback).error(failCallback);
    }
    //保存新的密码
    function updatePassword(params, successCallback, failCallback) {
      $http({
        url: '/paas/user/updatePassword.action',
        method: 'post',
        data: params,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).success(successCallback).error(failCallback);
    }
    //取正在登陆的用户信息
    function queryUserRoleAndOperType(params, successCallback, failCallback) {
      $http({
        url: '/paas/permission/queryUserRoleAndOperType.action',
        method: 'GET',
        data: params
      }).success(successCallback).error(failCallback);
    }
    //获取运行环境
    function getEnvironment(callback){
      $http({
        url: '/paas/user/queryCurrenEvn.action',
        method: 'GET'
      }).success(function (res) {
        $rootScope.environment = JSON.parse(res.data).currenEvn;
        callback();
      });
    }

  });

  angular.module('PaaS5').directive('layoutHeader', function (CORE_VALUE) {
    return {
      restrict: 'EA',
      scope: {},
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/layout/_header.html',
      controller: 'LayoutHeaderCtrl'
    };
  });

})();
