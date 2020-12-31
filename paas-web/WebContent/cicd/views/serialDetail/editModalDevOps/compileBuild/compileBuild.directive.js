(function () {
  'use strict';

  angular.module('PaaS5.serialDetail')
    .directive('deployCompileModal', deployCompileModal);

  function deployCompileModal(CORE_VALUE, $http, COREDATA, $timeout) {
    return {
      restrict: 'EA',
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/compileBuild/compileBuildModal.html',
      scope: {},
      replace: false,
      link: function (scope) {

        scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
        /**
         * 基础功能
         */
        //页面绑定参数
        scope.formData = {
          repositoryInfo:[],
          dockerFilePath: '', //Dockerfile文件路径
          executeCommand: '',//执行命令
          //需求新增四个字段显示  开始
          isUnitTest:false,//是否勾选
          unitTestReportDir:'',//测试报告路径
          unitTestReport:'', //测试报告文件
          jacocoReportDir:'', //覆盖率文件路径
          jacocoReport:'' //覆盖率文件
          //新增结束
        };
        //构建中禁用
        scope.flowInfo = CORE_VALUE.PROJECTINFO;
        //检查dockerFilePath
        scope.dockerFileValid = false;
        scope.testDockerFile = function (fileName) {
          scope.dockerFileValid = false;
          angular.forEach(scope.formData.repositoryInfo, function (value) {
            if(fileName.indexOf(value.storePath) !== -1){
              scope.dockerFileValid = true;
            }
          });
        };

        //单元测试校验
        scope.unitTestReportDirValid = true;
        scope.unitTestReportValid = true;
        scope.changeUnitTestReport = function(){
          scope.unitTestReportDirValid = true;
          scope.unitTestReportValid = true;
          if(scope.formData.unitTestReportDir !='' &&scope.formData.unitTestReport===''){
              scope.unitTestReportDirValid = true;
              scope.unitTestReportValid = false;
          }
          if(scope.formData.unitTestReport !='' &&scope.formData.unitTestReportDir===''){
              scope.unitTestReportDirValid = false;
              scope.unitTestReportValid = true;
          }
        };

        //覆盖率校验
        scope.jacocoReportDirValid = true;
        scope.jacocoReportValid = true;
        scope.changeJacocoReport = function(){
          scope.jacocoReportDirValid = true;
          scope.jacocoReportValid = true;
          if(scope.formData.jacocoReportDir !='' &&scope.formData.jacocoReport===''){
              scope.jacocoReportDirValid = true;
              scope.jacocoReportValid = false;
          }
          if(scope.formData.jacocoReport !='' &&scope.formData.jacocoReportDir===''){
              scope.jacocoReportDirValid = false;
              scope.jacocoReportValid = true;
          }
        };

        //获取编辑页面数据
        scope.getFormData = function () {
          $http({
            method: 'get',
            url: '/paas/compileBuild/queryCompileBuild.action',
            params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}
          }).success(function (res) {
            if (res.resultCode + '' === '200') {
              var tempData = JSON.parse(res.data);
              scope.formData.dockerFilePath = tempData.dockerFilePath;
              scope.formData.executeCommand = tempData.executeCommand;
              //需求新增开始
              if (tempData.isUnitTest == 1) {
                  scope.formData.isUnitTest = true;
                } else {
                  scope.formData.isUnitTest = false;
              }
              scope.formData.unitTestReportDir = tempData.unitTestReportDir;
              scope.formData.unitTestReport = tempData.unitTestReport;
              scope.formData.jacocoReportDir = tempData.jacocoReportDir;
              scope.formData.jacocoReport = tempData.jacocoReport;
              //新增结束
              scope.formData.repositoryInfo = [];//清空原来的，modify林军
              angular.forEach(tempData.repositoryInfo, function(value) {
                scope.formData.repositoryInfo.push(value);
              });
              scope.testDockerFile(scope.formData.dockerFilePath);
            }else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }).error(function () {
            COREDATA.resMessage();
          });
        };

        //保存编辑页面数据
        scope.saveFormData = function () {
          //单元测试校验是否通过
          if(!scope.unitTestReportDirValid ||!scope.unitTestReportValid){
            scope.noSubmit = true;
            var tempTime = $timeout(function () {
              scope.noSubmit = false;
              $timeout.cancel(tempTime);
            },1000);
            return;
          }
          //覆盖率是否校验通过
          if(!scope.jacocoReportDirValid ||!scope.jacocoReportValid){
            scope.noSubmit = true;
            var tempTime = $timeout(function () {
              scope.noSubmit = false;
              $timeout.cancel(tempTime);
            },1000);
            return;
          }
          //Dockerfile路径是否校验通过
          if(scope.compileForm.$invalid){
            scope.noSubmit = true;
            var tempTime = $timeout(function () {
              scope.noSubmit = false;
              $timeout.cancel(tempTime);
            },1000);
            return;
          }

          var tempParam = angular.copy(scope.formData);
          //是否勾选转换
          if (tempParam.isUnitTest === true) {
              tempParam.isUnitTest = 1;
          } else {
              tempParam.isUnitTest = 0;
              //未勾选时,清楚四个字段的值
              tempParam.unitTestReportDir = '';
              tempParam.unitTestReport = '';
              tempParam.jacocoReportDir = '';
              tempParam.jacocoReport = '';
          }
        
          delete tempParam.repositoryInfo;
          if(scope.dockerFileValid){
            $http({
              method: 'post',
              url: '/paas/compileBuild/modifyCompileBuild.action',
              data: {compileBuildEntity: tempParam,flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId},
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if (res.resultCode + '' === '200') {
                COREDATA.resMessage(res.resultCode, res.resultMessage);
                $('#compileCancel').click();
              }else {
                COREDATA.resMessage(res.resultCode, res.resultMessage);
              }
              scope.$emit('updateStep');//刷新流水步骤
            }).error(function () {
              COREDATA.resMessage();
            });
          }
        };

      }
    };
  }

})();
