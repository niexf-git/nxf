(function () {
  'use strict';

  angular.module('PaaS5')
    .directive('publishTestModal', publishTestModal);

  function publishTestModal(CORE_VALUE, $http, COREDATA, $filter) {
    return {
      restrict: 'EA',
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/publishTest/publishTestModal.html',
      scope: {
        init: '&'
      },
      replace: false,
      link: function (scope, element, attrs) {

        scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
        // 构建中禁用
        scope.flowInfo = CORE_VALUE.PROJECTINFO;

         //编辑保存参数
         scope.saveParams = {
           destination:"",//发布到
             versionNumber:"",//版本号
             type:""//发布方式
          };

        scope.change = function (type) {
          if (type === '3') {
            scope.saveParams.destination = '3';
            scope.saveParams.type = 0;
          }
        };

        // 查询modal数据方法
        scope.queryData = function () {
          $http({
            method: 'GET',
            url: '/paas/release/queryRelease.action',
            params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}//流水id
          }).success(function (res) {
            if (res.resultCode + '' === '200') {
              if(res.data !== "" && res.data !== null && res.data !== undefined && res.data !== "null"){
                scope.data = JSON.parse(res.data);
                scope.saveParams.destination = scope.data.destination;
                scope.saveParams.versionNumber = scope.data.versionNumber;
                scope.saveParams.type = scope.data.type;
              }else{
                  if(scope.flowInfo.FLOWINFO.operType === '2'|| scope.flowInfo.FLOWINFO.roleType === '2'){
                    scope.saveParams.destination = '3';
                }else{
                  scope.saveParams.destination = '2';
                }
                  scope.saveParams.type = '0';
              }
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }).error(function (res) {
            COREDATA.resMessage();
          });
        };


        // 卡片数据编辑
        scope.modifyData = function(data){
          $http({
            method: 'POST',
            url: '/paas/release/modifyRelease.action',
            data: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, releaseEntity:scope.saveParams},//流水id
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
          }).success(function (res) {
            if (res.resultCode + '' === '200') {
                $('#publishCancelId').click();
                COREDATA.resMessage(res.resultCode, res.resultMessage);
                scope.$emit('serialDetailFresh');//刷新流水步骤
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
            scope.$emit('updateStep');//刷新流水步骤
          }).error(function (res) {
            COREDATA.resMessage();
          });
        };
        //发布测试的时候，如果选择自动，情况input，并禁用
        scope.isAutoPublish = function () {
          if(scope.saveParams.destination==='2'&&scope.saveParams.type===1){
            scope.saveParams.versionNumber = '';
          }
        };




      }
    }
  }


})();
