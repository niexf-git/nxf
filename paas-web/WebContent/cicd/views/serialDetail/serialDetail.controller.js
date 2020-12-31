(function () {
  'use strict';

  angular.module('PaaS5.serialDetail', [])
    .controller('serialDetailCtrl', function($http, $rootScope, $scope, $timeout, navState, serialServer, CORE_VALUE, COREDATA, toastr, deployLogService, $state, $interval) {

      $scope.userInfo = CORE_VALUE.USERINFO;

      $scope.statusColor = function(status) {
        var obj = {color: ''}
        if(status === '0') {
          obj.color = '#333';
        }else if(status === '1') {
          obj.color = '#3fc000';
        }else if(status === '2') {
          obj.color = '#e4342f';
        }else if(status === '3') {
          obj.color = '#02a0da';
        }else if(status === '4') {
          obj.color = '#ccc';
        }
        return obj;
      };

      //镜像查询
       $scope.openImage = function(){
         CORE_VALUE.isCICD.value1 = false;
         CORE_VALUE.isCICD.value2 = true;
         angular.forEach(navState.menu, function (value) {
           value.isInside = false;
           value.openState = false;
           if(value.hasChild&&value.name === '镜像管理'){
             value.openState = true;
             angular.forEach(value.childrens, function (value1) {
               value1.isInside = value1.name === '私有镜像';
             });
           }
         });
         $rootScope.$broadcast('update', '私有镜像');
        $timeout(function(){
          $('#mainIframe').attr('src','/paas/jsp/imagemanager/privateimage/listPrivateImage.jsp?imageId='+$scope.flowDetailInfo.imageId);
        },100)
       };

      $scope.$on('$stateChangeSuccess', function (evt, state){
        var moduleName = state.name.split('.')[2];
        var modules = ['editModalDevOps', 'deployLog', 'report'];
        for(var p in modules){
          if(moduleName === modules[p]){
            $('#' + moduleName).css({
              'border-bottom': '2px solid #02a0da',
              'color': '#02a0da',
              'height': '30px'
            });
          }else if(moduleName === 'codeCheck' || moduleName === 'codeCheckReport' || moduleName === 'checkReportDetail'){
            $('#' + 'deployLog').css({
              'border-bottom': '2px solid #02a0da',
              'color': '#02a0da',
              'height': '30px'
            });
          } else {
            $('#' + modules[p]).removeAttr('style');
          }
        }

      });

      /**********************************   头部流水详情部分 陈哲  *****************************************/
      $scope.init = function() {
         $scope.querySerialDetail();
      };

      // 立即构建
      $scope.build = function(callback) {
        var params = {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId};
        serialServer.building(params, function(res) {
        	callback();
          if(res.resultCode === '200') {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }else {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      };

      // 停止构建
      $scope.stopBuild = function (callback) {
        var params = {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}
        serialServer.stopBuild(params, function(res) {
          if(res.resultCode === '200') {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
            setTimeout(function() {
            	callback();
            }, 500);
          }else {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      }

      // 详情查询
      $scope.flowDetailInfo;
      $scope.querySerialDetail = function() {
        var params = {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId};
        serialServer.serialDetail(params, function(res) {
          if (res.data)
            var data = JSON.parse(res.data);
          if(res.resultCode === '200') {
            $scope.flowDetailInfo = data;
            $scope.flowDetailInfo.loadData = true;
            window.sessionStorage.serialInfo = angular.toJson(data);
            CORE_VALUE.PROJECTINFO.FLOWINFO = data;

          }else {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      };

      // 刷新
      $scope.fresh = function(value) {
        $scope.$broadcast('serialDetailFresh', value);
        $scope.init();
      };

      var watchBuildStatus = $scope.$watch('flowDetailInfo.buildStatus', function(newValue, oldValue) {
        console.log('oldValue: ' + oldValue);
        console.log('newValue: ' + newValue);
        if(newValue === '3') {
          console.log('设定1.5S定时器');
          $interval.cancel($scope.timer);
          $scope.timer = $interval(function(){
            $scope.fresh();
          }, 2000);
        }else {
          console.log('设定10S定时器');
          $interval.cancel($scope.timer);
          $scope.timer = $interval(function() {
            $scope.fresh();
          }, 10000);
        }
      });

      $scope.showExecutionRecordPanel = function() {
        $scope.$broadcast('showExecutionRecordPanel', CORE_VALUE.PROJECTINFO.FLOWINFO);
        // 展示流水执行记录框
        $("body").attr("style", "padding-right: 10px; overflow: hidden;");
        $("div[name='chenzhe'] .modal-mask").removeClass("modal-mask-hidden");
        $("div[name='chenzhe'] .flowBuildLogModal").show();
      };

      $scope.$on('updateStep', function() {
        console.log('updateStep');
        $scope.fresh();
      });

      $scope.$on('$destroy', function() {
        console.info('destroy');
        watchBuildStatus();
        $interval.cancel($scope.timer);
      });

      $scope.$on('switchNav', function(){
        $interval.cancel($scope.timer);
      });

    });

})();
