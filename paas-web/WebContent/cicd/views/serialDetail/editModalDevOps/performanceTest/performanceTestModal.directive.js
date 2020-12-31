(function () {
  'use strict';

//编辑弹窗
  angular.module('PaaS5')
    .directive('performanceTestModal', function (CORE_VALUE,$http,COREDATA) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/performanceTest/performanceTestModal.html',
        scope: {
          init: '&'
        },
        replace: false,
        link: function (scope, element, attrs) {

          scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
          //构建中禁用
          scope.flowInfo = CORE_VALUE.PROJECTINFO;

          scope.isSvn = true;//svn和git切换

          scope.pageData = {
            repositoryType:'1',//代码库类型
            resultCode:'',//返回码
            git:{},//git信息
            svn:{},//svn信息
            //url:'',//地址
            //name:'',//用户名
            //password:'',//密码
            //testCmd:'',//测试命令
            //branchName:''//分支
          };

          //切换代码库类型
          scope.chooseRep = function (rep) {
            if(rep === 'svn'){
              scope.isSvn=true;
              scope.pageData.repositoryType = '1';
            }else {
              scope.isSvn=false;
              scope.pageData.repositoryType = '2';
            };
            if(scope.isSvn === true){
              $("#Svns").addClass('codeTapyHover');
              $("#Gits").removeClass('codeTapyHover');
            }else{
              $("#Svns").removeClass('codeTapyHover');
              $("#Gits").addClass('codeTapyHover');
            }
          };

          //var serialInfo = angular.fromJson(window.sessionStorage.serialInfo)//流水id

          //查询参数
          var queryParams = {
            // flowId :serialInfo.flowId,//流水id
            flowId :  CORE_VALUE.PROJECTINFO.FLOWINFO.flowId//流水id
          };

          //查询modal数据方法
          scope.queryData = function() {
            $http({
              method: 'GET',
              url: '/paas/performancetest/queryPerformanceTest.action',
              params: queryParams
            }).success(function (res) {
              if (res.resultCode + '' === '200'){
                var $es = JSON.parse(res.data).repositoryInfo;
                if($es.repositoryType=="1"){
                  scope.isSvn = true;
                  scope.pageData.repositoryType = '1';
                  scope.pageData.svn.url = $es.url;
                  scope.pageData.svn.name = $es.name;
                  scope.pageData.svn.password =  $es.password;
                  scope.pageData.svn.testCmd =  JSON.parse(res.data).testCmd;
                  $("#Svns").addClass('codeTapyHover');

                }else if($es.repositoryType=="2"){
                  scope.isSvn = false;
                  scope.pageData.repositoryType = '2';
                  scope.pageData.git.url = $es.url;
                  scope.pageData.git.name = $es.name;
                  scope.pageData.git.password =  $es.password;
                  scope.pageData.git.branchName =  $es.branchName;
                  scope.pageData.git.testCmd =  JSON.parse(res.data).testCmd;
                  $("#Gits").addClass('codeTapyHover');
                  //查询分支
                  scope.getBranch($es);
                }else{
                  scope.isSvn = true;
                  scope.pageData.repositoryType = '1';
                  $("#Svns").addClass('codeTapyHover');
                };

              }else{
                COREDATA.resMessage(res.resultMessage);
              }
            });
          };

          //代码库信息改变前参数
          scope.tempRepo = '';
          scope.setBaseUrl = function (itemUrl) {
            scope.tempRepo = itemUrl+'';
          };
          //查询代码库信息(svn地址的输入框失去焦点时进行查询)
          /*scope.queryAccount = function (item) {
            if(item.url && item.url.length > 0){
              if(scope.tempRepo !== item.url){//确定修改之后再查询代码库信息
                var repositoryInfo = {
                  url: item.url,//代码库地址
                  repositoryType: scope.pageData.repositoryType//代码库类型
                };
                $http({
                  method: 'POST',
                  url: '/paas/downloadCheck/queryRepository.action',
                  data: {repositoryInfo: repositoryInfo},
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function (res) {
                  if (res.resultCode + '' === '200') {
                    item.name = JSON.parse(res.data).name;
                    item.password = JSON.parse(res.data).password;
                    scope.verification(item);
                  }else {
                    COREDATA.resMessage(res.resultCode, res.resultMessage);
                  }
                }).error(function (res) {
                  COREDATA.resMessage();
                });
              }
            }
          };*/

          //验证地址的用户名密码
          scope.verification = function (item) {
            if (item.url && item.name && item.password && item.url.length > 0 && item.name.length > 0 && item.password.length > 0) {
              var repositoryInfo = {//验证地址的用户名密码参数
                url: item.url, //代码库地址
                name: item.name, //用户名
                password: item.password, //密码
                repositoryType: scope.pageData.repositoryType//代码库类型
              };
              $http({
                method: 'POST',
                url: '/paas/downloadCheck/verifyRepositoryCertificate.action',
                data: {repositoryInfo: repositoryInfo},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
              }).success(function (res) {
                if (res.resultCode + '' === '200') {
                  if(JSON.parse(res.data).id !== '' || JSON.parse(res.data).id !== null || JSON.parse(res.data).id !== undefined){
                    item.id = JSON.parse(res.data).id;
                    if (scope.pageData.repositoryType === "2") {
                      scope.getBranch(item);
                    }
                    item.asTrue = false;
                  }else {
                    alert('用户名和密码对应id值为空');
                  }
                } else {
                  item.asTrue = true;
                  COREDATA.resMessage(res.resultCode, res.resultMessage);
                }
              }).error(function (res) {
                COREDATA.resMessage();
              });
            }
          };

          //查询分支名称
          scope.getBranch = function (gitone) {
            //查询分支参数
            var repositoryInfo = {
              url: gitone.url, //代码库地址
              name: gitone.name, //用户名
              password: gitone.password //密码
            };
            $http({
              method: 'POST',
              url: '/paas/downloadCheck/queryBranches.action',
              data: {repositoryInfo: repositoryInfo},
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if(res.resultCode + '' === '200'){
                scope.branchData = JSON.parse(res.data);
                gitone.branchs = scope.branchData;
                if(!gitone.branchName){
                  scope.pageData.git.branchName = gitone.branchs[0].value;//当新建git仓库item时设置分支默认值
                }
              }else{
                COREDATA.resMessage(res.resultCode, res.resultMessage);
              }
            }).error(function (res) {
              COREDATA.resMessage();
            });
          };

          //代码路径处理
          /*scope.turorfor = function (one) {
            if(one.storePath){
              one.storePath = one.storePath.trim();
              one.turor = one.storePath.substr(0, 1) === '/'
            }else{
              one.turor = false;
            }
          };*/

          //保存方法前先验证
          scope.saveData = function (item) {
            if(item.url==""||item.url==null||item.name==""||item.name==null||item.password==""||item.password==null){
              COREDATA.resMessage("带*号信息不可为空");
              return false;
            };
            if (item.url && item.name && item.password && item.url.length > 0 && item.name.length > 0 && item.password.length > 0) {
              var repositoryInfo = {//验证地址的用户名密码参数
                url: item.url, //代码库地址
                name: item.name, //用户名
                password: item.password, //密码
                repositoryType: scope.pageData.repositoryType//代码库类型
              };
              $http({
                method: 'POST',
                url: '/paas/downloadCheck/verifyRepositoryCertificate.action',
                data: {repositoryInfo: repositoryInfo},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
              }).success(function (res) {
                if (res.resultCode + '' === '200') {
                  if(JSON.parse(res.data).id !== '' || JSON.parse(res.data).id !== null || JSON.parse(res.data).id !== undefined){
                    item.id = JSON.parse(res.data).id;
                    scope.save();
                  }else {
                    alert('用户名和密码对应id值为空');
                  }
                } else {
                  item.asTrue = true;
                  COREDATA.resMessage(res.resultCode, res.resultMessage);
                }
              }).error(function (res) {
                COREDATA.resMessage();
              });
            }
          };
          //验证后保存
          scope.save = function() {
            if(scope.isSvn){
            var  performanceTestEntity = {
                flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,//流水id,
                testCmd: scope.pageData.svn.testCmd,
                repositoryInfo: {
                  //codeType:'svn',
                  url: scope.pageData.svn.url,
                  name: scope.pageData.svn.name,
                  password: scope.pageData.svn.password,
                  repositoryType: '1'
                }
              }
            }else{
            var  performanceTestEntity = {
                flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,//流水id,
                testCmd:scope.pageData.git.testCmd,
                repositoryInfo:{
                  //codeType:'git',
                  url:scope.pageData.git.url,
                  name:scope.pageData.git.name,
                  password:scope.pageData.git.password,
                  branchName: scope.pageData.git.branchName,//分支名称
                  repositoryType:'2'
                }
              }
            };

            $http({
              method: 'POST',
              url: '/paas/performancetest/updatePerformanceTest.action',
              data: {
                performanceTestEntity:performanceTestEntity,
                flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId
              },
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if (res.resultCode + '' === '200'){
                $('#cancelPerfor').click();
                scope.$emit('updateStep');//刷新流水步骤
                COREDATA.resMessage(res.resultCode,res.resultMessage);
              }else{
               // COREDATA.resMessage("请正确填写所有信息项");
                COREDATA.resMessage(res.resultCode,res.resultMessage);
              }
            });
          };

          //关闭模态框
          scope.cancelModal = function () {
            $('#cancelPerfor').click();
          };
        }
      }
    });

})();
