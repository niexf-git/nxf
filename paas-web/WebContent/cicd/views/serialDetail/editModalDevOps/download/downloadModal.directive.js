(function () {
  'use strict';

  angular.module('PaaS5.serialDetail')
    .service('downloadService', downloadService)
    .directive('downloadModal', downloadModal);

  function downloadModal(CORE_VALUE, $http, COREDATA, downloadService, $filter) {
    return {
      restrict: 'EA',
      templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/download/downloadModal.html',
      scope: {
        init: '&'
      },
      replace: false,
      link: function (scope, element, attrs) {
        
        scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
        //构建中禁用
        scope.flowInfo = CORE_VALUE.PROJECTINFO;

        scope.pageData = downloadService.pageData;
        scope.pageData.svn.repositoryInfo = [{}];
        scope.pageData.git.repositoryInfo = [{}];

        $('#exeuteData').datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          autoclose: 1,
          todayHighlight: 1,
          startView: 1,
          minView: 0,
          maxView: 1,
          forceParse: 0,
          format: 'hh:ii'
        }).on('changeDate', function (ev) {
          scope.pageData.svn.triggerTime = $("#exeuteData").val();
        });
        $('#exeuteDataGit').datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          autoclose: 1,
          todayHighlight: 1,
          startView: 1,
          minView: 0,
          maxView: 1,
          forceParse: 0,
          format: 'hh:ii'
        }).on('changeDate', function (ev) {
          scope.pageData.git.triggerTime = $("#exeuteDataGit").val();
        });


        //切换代码库类型
        scope.chooseRep = function (repositoryType) {
          if (repositoryType === 'svn') {
            scope.pageData.isSvn = true;
          };
          if (repositoryType === 'git'){
            scope.pageData.isSvn = false;
          };
          if(scope.pageData.isSvn === true){
            $("#svns").addClass('codeTapyHover');
            $("#gits").removeClass('codeTapyHover');
            if(scope.repositoryType !== '1'&& scope.repositoryType !== '0'){
                scope.pageData.svn.name = "";
                scope.pageData.svn.password = "";
             }
          }else{
            $("#svns").removeClass('codeTapyHover');
            $("#gits").addClass('codeTapyHover');
            if(scope.repositoryType !== '2'){
                scope.pageData.git.name = "";
                scope.pageData.git.password = "";
             }
          }
        };
        //页面添加List方法
        scope.addList = function (how) {
          if (how === 'svn') {
            scope.pageData.svn.repositoryInfo.push({});
          } else {
            scope.pageData.git.repositoryInfo.push({});
          }
        };
        //页面删除List方法
        scope.deleteList = function (arr, index) {
          for (var i = 0; i < arr.length; i++) {
            if (i == index) {
              arr.splice(i, 1);
              break;
            }
          }
        };


        // 查询modal数据方法
        scope.queryData = function () {
          $http({
            method: 'GET',
            url: '/paas/downloadCheck/queryCodeDownloadAndCheck.action',
            params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}//流水id
          }).success(function (res) {
            if (res.resultCode + '' === '200') {
              if (res.data !== "" && res.data !== null && res.data !== undefined) {
                scope.pageData.svn.repositoryInfo = [{}];
                scope.pageData.git.repositoryInfo = [{}];
                var data = JSON.parse(res.data);
                scope.pageData.isSvn = (data.repositoryType === '1' || data.repositoryType === '0');
                if(!scope.pageData.isSvn){
                  $("#svns").removeClass('codeTapyHover');
                }
                if(data.repositoryType === '1' || data.repositoryType === '0'){
                  if(data.triggerType === '1'||data.triggerType === '2'||data.triggerType === '3'){
                    scope.pageData.svn.triggerType = data.triggerType;
                  }else {
                    scope.pageData.svn.triggerType = '1';
                  }
                  scope.pageData.svn.triggerTime = $filter('date')(data.triggerTime||new Date(), '00:00');
                  scope.pageData.svn.isCodeCheck = data.isCodeCheck;
                  scope.pageData.svn.scanDir = data.scanDir;
                  scope.pageData.svn.name = data.name;
                  scope.pageData.svn.password = data.password;
                  scope.pageData.git.isCodeCheck = '0';
                  scope.pageData.git.triggerType = '1';
                }else{
                  if(data.triggerType === '1'||data.triggerType === '3'||data.triggerType === '4'){
                    scope.pageData.git.triggerType = data.triggerType;
                  }else {
                    scope.pageData.git.triggerType = '1';
                  }
                  scope.pageData.git.triggerTime = $filter('date')(data.triggerTime||new Date(), '00:00');
                  scope.pageData.git.isCodeCheck = data.isCodeCheck;
                  scope.pageData.git.scanDir = data.scanDir;
                  scope.pageData.git.name = data.name;
                  scope.pageData.git.password = data.password;
                  scope.pageData.svn.isCodeCheck = '0';
                  scope.pageData.svn.triggerType = '1';
                }
                if(data.repositoryInfo.length !== 0){
                  if (data.repositoryType === '1') {
                    scope.pageData.svn.repositoryInfo = [];
                    angular.forEach(data.repositoryInfo, function (value) {
                      scope.pageData.svn.repositoryInfo.push(value);
                    });
                  }else if (data.repositoryType === '2'){
                    scope.pageData.git.repositoryInfo = [];
                    angular.forEach(data.repositoryInfo, function (value) {
                      scope.getBranch(value, scope.pageData.git.repositoryInfo);//git仓库需要单独处理分支
                    });
                  }
                }
                if(data.repositoryType === '1' || data.repositoryType === '0'){
                  $("#svns").addClass('codeTapyHover');
                  $("#gits").removeClass('codeTapyHover');
                  data.repositoryType = '1';
                }else{
                  $("#gits").addClass('codeTapyHover');
                  $("#svns").removeClass('codeTapyHover');
                }
                scope.repositoryType = data.repositoryType;//查询出来的代码库类型
              } else {
                scope.pageData.svn.triggerType = '1';
                scope.pageData.svn.isCodeCheck = '0';
                scope.pageData.git.triggerType = '1';
                scope.pageData.git.isCodeCheck = '0';
              }
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }).error(function (res) {
            COREDATA.resMessage();
          });
        };


        //代码路径处理
        scope.turorfor = function (one) {
          if(one.storePath){
            one.storePath = one.storePath.trim();
            one.turor = (one.storePath.substr(0, 1) === '/')||((/[~#^$@%&!?%*\s+]/gi).test(one.storePath));
          }else{
            one.turor = false;
          }
        };

        //代码库信息改变前参数
        scope.tempRepo = '';
        scope.setBaseUrl = function (itemUrl) {
          scope.tempRepo = itemUrl+'';
        };

        // 查询代码库信息(svn/git地址的输入框失去焦点且用户名密码不为空时进行查询校验)
        // scope.queryAccount = function (item, typeNum) {
        //   if(item.url && item.url.length > 0){
        //     if(scope.tempRepo !== item.url){//确定修改之后再查询代码库信息
        //       var repositoryInfo = {
        //         url: item.url,//代码库地址
        //         repositoryType: typeNum//代码库类型
        //       };
        //       $http({
        //         method: 'POST',
        //         url: '/paas/downloadCheck/queryRepository.action',
        //         data: {repositoryInfo: repositoryInfo},
        //         headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        //       }).success(function (res) {
        //         if (res.resultCode + '' === '200') {
        //           if(JSON.parse(res.data)!=null){
        //              item.name = JSON.parse(res.data).name;
        //              item.password = JSON.parse(res.data).password;
        //              scope.verification(item,typeNum);
        //           }else{
        //              scope.verification(item,typeNum);
        //           }
        //         }else {
        //           COREDATA.resMessage(res.resultCode, res.resultMessage);
        //         }
        //       }).error(function (res) {
        //         COREDATA.resMessage();
        //       });
        //     }
        //   }
        // };

        //对扫描目录进行实时监控
        scope.$watch('pageData.svn.scanDir', function () {
          scope.isNo = true;
          if (scope.pageData.svn.scanDir != "") {
            if ((/[~#^$@%&!?%*\s+]/gi).test(scope.pageData.svn.scanDir)) {
              scope.isNo = false;
            }
          }
        });
        //对扫描目录进行实时监控
        scope.$watch('pageData.git.scanDir', function () {
          scope.isNo = true;
          if (scope.pageData.git.scanDir != "") {
            if ((/[~#^$@%&!?%*\s+]/gi).test(scope.pageData.git.scanDir)) {
              scope.isNo = false;
            }
          }
        });

        //对输入的地址验证输入的用户名和密码
        scope.checkCompletion = true;
        scope.verification = function (item,typeNum, sgdata) {
          if (item.url && sgdata.name && sgdata.password && item.url.length > 0 && sgdata.name.length > 0 && sgdata.password.length > 0) {
            scope.checkCompletion = false;
            var repositoryInfo = {//验证地址的用户名密码参数
              url: item.url, //代码库地址
              name: sgdata.name, //用户名
              password: sgdata.password, //密码
              repositoryType: typeNum//代码库类型
            };
            $http({
              method: 'POST',
              url: '/paas/downloadCheck/verifyRepositoryCertificate.action',
              data: {repositoryInfo: repositoryInfo},
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if (res.resultCode + '' === '200') {
                if(JSON.parse(res.data).id !== '' && JSON.parse(res.data).id !== null && JSON.parse(res.data).id !== undefined){
                  item.id = JSON.parse(res.data).id;
                  if (typeNum === "2") {
                    scope.getBranch(item);
                  }
                  item.asTrue = false;
                }
              } else {
                item.asTrue = true;
                scope.getBranch(item);
                COREDATA.resMessage(res.resultCode, res.resultMessage);
              }
              scope.checkCompletion = true;
            }).error(function (res) {
              COREDATA.resMessage();
            });
          }
        };



      //对输入的多个地址验证用户名和密码
        var clickEvents = true;
        scope.queryAllData = function (svnDatas,svnOrGit){
          if(svnDatas.name && svnDatas.password && svnDatas.name.length > 0 && svnDatas.password.length > 0){
            clickEvents = false;
              var testRepoInfo = [];//需要验证的仓库信息
              var formInvalid = true;//form是否合法
              if (scope.pageData.isSvn) {
                scope.newData = angular.copy(scope.pageData.svn);//卡片数据
                testRepoInfo = scope.pageData.svn.repositoryInfo;//代码库数据
                scope.newData.repositoryInfo = [];//过滤以后传到后台的代码库数据
                angular.forEach(testRepoInfo, function (value) {
                  if(value.url !== "" && value.url !== null && value.url != undefined){
                    scope.newData.repositoryInfo.push({
                            url: value.url,//svn地址
                            storePath: value.storePath,//代码目录
                            versionNumber:value.versionNumber,//版本号
                            repositoryType:svnOrGit
                          });
                  }
                  });

                 delete scope.newData.isCodeCheck;
                 delete scope.newData.scanDir;
                 delete scope.newData.triggerType;
                 delete scope.newData.triggerTime;
                 delete scope.newData.repositoryType;
              } else {
                scope.newData = angular.copy(scope.pageData.git);
                testRepoInfo = scope.pageData.git.repositoryInfo;
                scope.newData.repositoryInfo = [];
                angular.forEach(testRepoInfo, function (value) {
                  if(value.url !== "" && value.url !== null && value.url != undefined){
                   scope.newData.repositoryInfo.push({
                          url: value.url,//git地址
                          storePath: value.storePath,//代码目录
                          repositoryType:svnOrGit
                        });
                  }
                  });
                 delete scope.newData.isCodeCheck;
                 delete scope.newData.scanDir;
                 delete scope.newData.triggerType;
                 delete scope.newData.triggerTime;
                 delete scope.newData.repositoryType;
              }
            $http({
                 method: 'POST',
                 url: '/paas/downloadCheck/verifyMultRepositoryCertificate.action',
                 data: {codeDownloadAndCheckEntity: scope.newData},
                 headers: {'Content-Type': 'application/x-www-form-urlencoded'}
               }).success(function (res) {
                 if(res.resultCode === '200'){
                    scope.result = JSON.parse(res.data);
                    angular.forEach(testRepoInfo, function (value,index,array) {//代码库数据
                      angular.forEach(scope.result, function (value1) {//查询成功以后返回的数据
                            if(value.url == value1.url){
                               value.id = value1.id;
                               if(svnOrGit == '2'){
                                 scope.getBranch(value);
                               }
                           }
                      });
                      value.asTrue = false;
                      if(value.id === '' || value.id === null || value.id === undefined){
                      value.asTrue = true;
                      }
                   });
                 }else{
                   COREDATA.resMessage(res.resultCode, res.resultMessage);
                 }
                 clickEvents = true;
               }).error(function (res) {
                 COREDATA.resMessage();
               });

          }

        };

        //查询分支名称
        scope.getBranch = function (gitone, pushData) {
          scope.sendData = angular.copy(scope.pageData.git);
          //查询分支参数
          var repositoryInfo = {
            url: gitone.url, //代码库地址
            name: scope.sendData.name, //用户名
            password: scope.sendData.password //密码
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
                gitone.branchName = gitone.branchs[0].value;//当新建git仓库item时设置分支默认值
              }
              if(scope.branchData.length < 1){
                gitone.branchName = '';
              }
              if (pushData) { pushData.push(gitone); }
            }else{
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }).error(function (res) {
            COREDATA.resMessage();
          });
        };




        scope.sendData = downloadService.sendData;
        //保存方法
        scope.saveData = function () {
          if(clickEvents && scope.checkCompletion && scope.isNo){
            var testRepoInfo = [];//需要验证的仓库信息
                var formInvalid = true;//form是否合法
                var noSubmit = false;//判断是否准许提交
                if (scope.pageData.isSvn) {
                  scope.sendData = angular.copy(scope.pageData.svn);
                  testRepoInfo = scope.pageData.svn.repositoryInfo;
                  formInvalid = scope.downloadModify.$invalid;
                } else {
                  scope.sendData = angular.copy(scope.pageData.git);
                  testRepoInfo = scope.pageData.git.repositoryInfo;
                  formInvalid = scope.downloadModifyGit.$invalid;
                }
                var str = scope.sendData.scanDir;
                scope.sendData.scanDir = str.replace(/\s+/g, "");
                scope.sendData.repositoryInfo = [];
                //不定时的不传参数triggerTime
                if(scope.sendData.triggerType !== '3'){
                  delete scope.sendData.triggerTime;
                }
                //数据是否满足提交的条件
                angular.forEach(testRepoInfo, function (value) {
                  if (value.asTrue === true || value.turor === true || scope.sendData.name === "" || scope.sendData.password === "") {//asTrue 用户名密码是否错误 turor 代码路径不能以/开头
                    noSubmit = true;
                  }
                });
                if (!noSubmit && !formInvalid && scope.isNo) {
                  if(scope.pageData.isSvn){
                    angular.forEach(testRepoInfo, function (value) {
                      scope.sendData.repositoryInfo.push({
                        url: value.url,
                        storePath: value.storePath,
                        versionNumber:value.versionNumber,
                        id: value.id
                      });
                    });
                  }else{
                    angular.forEach(testRepoInfo, function (value) {
                      scope.sendData.repositoryInfo.push({
                        url: value.url,
                        storePath: value.storePath,
                        branchName: value.branchName,
                        id: value.id
                      });
                    });
                  }
                  $http({
                    method: 'POST',
                    url: '/paas/downloadCheck/modifyCodeDownloadAndCheck.action',
                    data: {codeDownloadAndCheckEntity: scope.sendData, flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                  }).success(function (res) {
                    if(res.resultCode === '200'){
                      $('#downloadCancelId').click();
                      COREDATA.resMessage(res.resultCode, res.resultMessage);
                      scope.$emit('serialDetailFresh');//刷新流水步骤
                    }else{
                      COREDATA.resMessage(res.resultCode, res.resultMessage);
                    }
                    scope.$emit('updateStep');//刷新流水步骤
                  }).error(function (res) {
                    COREDATA.resMessage();
                  });
                } else {
                  COREDATA.resMessage("请正确填写所有信息项");
                }
          }else{
            COREDATA.resMessage("正在校验填写的代码库信息，请稍后点击保存！");
          }

        };

        //取消表格
        scope.cancelForm = function () {
          angular.forEach(scope.downloadModify, function (value, key) {
            if(key.indexOf('svn') !== -1){
              value.$dirty = false;
              scope.isNo = true;
            }
          });
          angular.forEach(scope.downloadModifyGit, function (value, key) {
            if(key.indexOf('git') !== -1){
              value.$dirty = false;
              scope.isNo = true;
            }
          });
        };

      }
    }
  }

  function downloadService($filter) {
    this.pageData = {
      isSvn: true,//svn和git切换
      svn: {
        repositoryInfo :[{}],
        repositoryType: '1',//1代码库类型（1.svn，2.git）
        triggerType: '1', //2触发类型(1.手动，2.轮询，3.定时，4.代码提交)
        triggerTime: $filter('date')(new Date(), '00:00'), //3启动时间,//设置定时触发下载审查的时间
        isCodeCheck: '0', //4是否代码审查(0.否，1.是)
        scanDir:"",
        name:"",
        password:""

      },
      git: {
        repositoryInfo :[{}],
        repositoryType: '2',//1代码库类型（1.svn，2.git）
        triggerType: '1', //2触发类型(1.手动，2.轮询，3.定时，4.代码提交)
        triggerTime: $filter('date')(new Date(), '00:00'), //3启动时间,//设置定时触发下载审查的时间
        isCodeCheck: '0', //4是否代码审查(0.否，1.是)
        scanDir:"",
        name:"",
        password:""

      }
    };
    this.sendData = {
      repositoryType: '',//1代码库类型（1.svn，2.git）
      triggerType: '', //2触发类型(0.手动，1.定时，2.轮询，3.代码提交)
      triggerTime: '', //3启动时间,//设置定时触发下载审查的时间
      isCodeCheck: '', //4是否代码审查(0.否，1.是)
      scanDir:"",//扫描目录
      repositoryInfo: [], //5仓库数据
      name:"",
      password:""
    };

  }

})();
