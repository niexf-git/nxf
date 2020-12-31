(function () {
  'use strict';

  angular.module('PaaS5')
    .directive('integrationTestModal', function ($http,CORE_VALUE,COREDATA,toastr,layoutHeaderService) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix +'views/serialDetail/editModalDevOps/integrationTest/integrationTestModal.html',
        scope: {
          init: '&'
        },
        replace: false,
        link: function (scope, element, attrs) {
          scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
          //是否禁用编辑按钮
          scope.flowInfo = CORE_VALUE.PROJECTINFO;
          scope.isCreat = true//创建和引用切换
          scope.isSvn = true;//svn和git切换
          scope.ok=true;
          scope.gitok=true;

          //页面显示参数
          scope.pageData = {
            git:{},
            type:'', //类型为创建or引用
            flowIntegration:'', //所引用的某条流水的集成测试
            repositoryType:'',//代码库类型
            branchName:''
          };
          //代码库切换
          scope.chooseType=function(type){
            if(type=='1'){
              scope.isSvn=true;
              scope.pageData.repositoryType='1';
            }
            else{
              scope.isSvn=false;
              scope.pageData.repositoryType='2';
            };
            if(scope.isSvn === true){
              $("#SVN").addClass('codeTapyHover');
              $("#GIT").removeClass('codeTapyHover');
            }else{
              $("#SVN").removeClass('codeTapyHover');
              $("#GIT").addClass('codeTapyHover');
            }

          };
            //创建引用切换
          scope.chooseCreate=function(type){
            if(type=='0'){
              scope.isCreat=true;
            }else{
              scope.isCreat=false;
            }
            scope.pageData.type=type;
          };

          //查询参数
          var queryParams = {
             flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId//流水id
          };
          //查询modal数据方法
          var respoJson  = {};
          scope.alinasname='';
          scope.queryData=function(){
            scope.ok=true;
            scope.gitok=true;
            scope.pageData = {
              url:'',//地址
              userName:'',  //用户名
              password:'', //密码
              testCommand:'', //测试命令
              type:'', //类型为创建or引用
              flowIntegration:'', //所引用的某条流水的集成测试
              repositoryType:'',//代码库类型
              branchName:'',
              git:{
                url:'',//地址
                userName:'',  //用户名
                password:'', //密码
                testCommand:''//测试命令
              }
            };
            $http({
              method: 'GET',
              url: '/paas/integrationtest/queryInteTest.action',
              params: queryParams
            }).success(function (res) {
              if(res.resultCode + '' === '200') {
                var data = JSON.parse(res.data);
                scope.pageData.type = data.inteType;
                scope.pageData.repositoryType = data.repositoryInfo.repositoryType;//代码库类型
                if(data.repositoryInfo.repositoryType=='1'){
                  scope.isSvn=true;
                  scope.pageData.url = data.repositoryInfo.url;
                  scope.pageData.userName = data.repositoryInfo.name;
                  scope.pageData.password = data.repositoryInfo.password;
                  scope.pageData.testCommand = data.testCmd;
                  $("#SVN").addClass('codeTapyHover');
                }else if(data.repositoryInfo.repositoryType=='2'){
                  scope.isSvn=false;
                  scope.pageData.git.url = data.repositoryInfo.url;
                  scope.pageData.git.userName = data.repositoryInfo.name;
                  scope.pageData.git.password = data.repositoryInfo.password;
                  scope.pageData.git.testCommand = data.testCmd;
                  scope.pageData.branchName=data.repositoryInfo.branchName;
                  $("#GIT").addClass('codeTapyHover');
                  scope.getBranch(scope.pageData.git);
                }

                scope.pageData.flowIntegration = data.relationName;
                //把引用的数据后面的下划线后面的英语替换成汉字
                for(var i=0;i<scope.pageData.flowIntegration.length;i++){
                  var str = scope.pageData.flowIntegration[i].aliasName;
                  var strNew  = str ;
                  str = str.substring(str.indexOf('_') + 1);
                  var strArry =  str.split("_");
                  var type = strArry[strArry.length - 1];
                  if(type == 'intetest'){str = str.replace('intetest',"开发集成测试");str = str.replace("_dev_","_");}
                  if(type == 'testintetest'){str = str.replace('testintetest',"测试集成测试");str = str.replace("_test_","_");}
                  scope.pageData.flowIntegration[i].aliasName = str;
                  respoJson[str] = strNew;
                }

                scope.pageData.selectedItem=data.aliasName;
                if(!data.aliasName){
                  scope.pageData.selectedItem=data.relationName[0]['aliasName'];
                }else{
                  scope.pageData.selectedItem= replaces(data.aliasName);
                }
                if (scope.pageData.type == '1') {
                  scope.isCreat = false;
                }else{
                  scope.isCreat = true;
                }

              }else{
                COREDATA.resMessage(res.resultMessage);
              }
            }).error(function(){
              COREDATA.resMessage();
            });
          };

          //把引用的数据后面的下划线后面的英语替换成汉字方法
          function replaces(str){
            str = str.substring(str.indexOf('_') + 1);
            var strArry =  str.split("_");
            var type = strArry[strArry.length - 1];
            if(type == 'intetest')
            {
              str = str.replace('intetest',"开发集成测试");
              str = str.replace("_dev_","_");
            }
            if(type == 'testintetest')
            {
              str = str.replace('testintetest',"测试集成测试");
              str = str.replace("_test_","_");
            }
            return str;
          }
          //代码库信息改变前参数
          scope.tempRepo = '';
          scope.setBaseUrl = function (itemUrl) {
            scope.tempRepo = itemUrl+'';
          };
          //查询代码库信息(svn地址的输入框失去焦点时进行查询)
          /*scope.queryAccount = function(item){
            if(item.url&&item.url.length>0) {
              var repositoryInfo = {
                url:item.url,//代码库地址
                repositoryType:item.repositoryType||'1'//代码库类型
              };
                $http({
                  method: 'POST',
                  url: '/paas/downloadCheck/queryRepository.action',
                  data: {repositoryInfo:repositoryInfo},
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function (res) {
                  if (res.resultCode + '' === '200'&& res.data!='null') {
                    var datas=JSON.parse(res.data);
                    if(scope.isSvn){
                      scope.pageData.userName =datas.name;
                      scope.pageData.password = datas.password;
                      scope.ok=true;
                    }else{
                      scope.pageData.git.userName =datas.name;
                      scope.pageData.git.password = datas.password;
                      scope.gitok=true;
                    }
                    scope.verification(item);
                  }else {
                    COREDATA.resMessage(res.resultCode, res.resultMessage);
                   // scope.ok=false;
                    scope.isSvn?scope.ok=false:scope.gitok=false;
                  }
                }).error(function(res){
                  COREDATA.resMessage( res.resultMessage);
                  scope.isSvn?scope.ok=false:scope.gitok=false;
                })
            }
          };*/



          //验证用户名，密码方法

          scope.verification=function(item){
            //验证用户名，密码参数
            var repositoryInfo={
                url:item.url,//代码库地址
                name: item.userName,//用户名
                password:item.password,//密码
                repositoryType:scope.pageData.repositoryType||'1'//代码库类型
              }


            if (item.url && item.userName&&item.password) {
                $http({
                  method: "POST",
                  url: "/paas/downloadCheck/verifyRepositoryCertificate.action",
                  data: {repositoryInfo: repositoryInfo},
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function (res) {
                  if (res.resultCode + '' === '200') {
                      if (JSON.parse(res.data).id !== '' || JSON.parse(res.data).id !== null || JSON.parse(res.data).id !== undefined) {
                        item.id = JSON.parse(res.data).id;
                        if (scope.pageData.repositoryType =="2") {
                          scope.getBranch(item);
                        }
                        //item.asTrue = false;
                      } else {
                        alert('用户名和密码对应id值为空');
                      }
                  }else {
                    COREDATA.resMessage(res.resultCode, res.resultMessage);
                    scope.isSvn?scope.ok=false:scope.gitok=false;
                  }
                }).error(function(res){
                  COREDATA.resMessage();
                  scope.isSvn?scope.ok=false:scope.gitok=false;
              })
            }
          };


          //查询分支名称
          scope.getBranch=function(gitone) {
            //查询分支参数
            var repositoryInfo = {
              url:gitone.url, //代码库地址
              name:gitone.userName, //用户名
              password:gitone.password //密码
            };
            $http({
              method: 'POST',
              url: '/paas/downloadCheck/queryBranches.action',
              data: {repositoryInfo: repositoryInfo},
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if(res.resultCode + '' === '200') {
                scope.branchData = JSON.parse(res.data);
                gitone.branchs = scope.branchData;
                if (!gitone.branchName) {
                  scope.pageData.branchName = gitone.branchs[0].value;//当新建git仓库item时设置分支默认值
                }

              }else{
                COREDATA.resMessage(res.resultCode, res.resultMessage);
              }
            }).error(function (res) {
              COREDATA.resMessage();
            });

          }


        /*  scope.change=function(){
            scope.pageData.aliasName=scope.pageData.selectedItem;
          }
*/
          //保存方法
          scope.saveData = function(item) {

            //验证用户名，密码参数

            if(scope.pageData.type=='0') {
              var repositoryInfo;
              if (scope.isSvn) {
                repositoryInfo = {
                  url: item.url,//代码库地址
                  name: item.userName,//用户名
                  password: item.password,//密码
                  repositoryType: scope.pageData.repositoryType || '1'//代码库类型
                };
                if(item.url==""||item.url==null||item.userName==""||item.userName==null||item.password==""||item.password==null){
                  COREDATA.resMessage("带*号信息不可为空");
                  return false;
                }
              } else {
                repositoryInfo = {
                  url: item.git.url,//代码库地址
                  name: item.git.userName,//用户名
                  password: item.git.password,//密码
                  repositoryType: scope.pageData.repositoryType || '1'//代码库类型
                };
                if(item.git.url==""||item.git.url==null||item.git.userName==""||item.git.userName==null||item.git.password==""||item.git.password==null){
                  COREDATA.resMessage("带*号信息不可为空");
                  return false;
                }
              };
              //if (scope.pageData.url && scope.pageData.userName && scope.pageData.password) {
                $http({
                  method: "POST",
                  url: "/paas/downloadCheck/verifyRepositoryCertificate.action",
                  data: {repositoryInfo: repositoryInfo},
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function (res) {
                  if (res.resultCode + '' === '200') {

                    if (JSON.parse(res.data).id !== '' || JSON.parse(res.data).id !== null || JSON.parse(res.data).id !== undefined) {
                      scope.isSvn ? scope.ok = true : scope.gitok = true;
                      item.id = JSON.parse(res.data).id;
                      saveFun();
                      if (scope.pageData.repositoryType == "2") {
                        //scope.getBranch(item);
                      }
                      //item.asTrue = false;
                    } else {
                      alert('用户名和密码对应id值为空');
                    }
                  } else {
                    COREDATA.resMessage(res.resultCode, res.resultMessage);
                    scope.isSvn ? scope.ok = false : scope.gitok = false;
                  }
                }).error(function (res) {
                  COREDATA.resMessage();
                  scope.isSvn ? scope.ok = false : scope.gitok = false;
                });
             // }else{
              //   COREDATA.resMessage("带*号的不能为空");
             // }
            }else{
              saveFun();
            }

            //保存
            function saveFun() {
              var InteTestEntity;
              if (scope.isSvn) {
                InteTestEntity = {
                  testCmd: scope.pageData.testCommand,
                  aliasName: respoJson[scope.pageData.selectedItem],//所引用别名
                  inteType: scope.pageData.type || 0,//创建应用
                  repositoryInfo: {
                    repositoryType: scope.pageData.repositoryType || 1,//代码库类型
                    url: scope.pageData.url,
                    name: scope.pageData.userName,
                    password: scope.pageData.password,
                    branchName: scope.pageData.branchName//分支名称
                  }
                };
              } else {
                InteTestEntity = {
                  testCmd: scope.pageData.git.testCommand,
                  aliasName: respoJson[scope.pageData.selectedItem],//所引用别名
                  inteType: scope.pageData.type || 0,//创建应用
                  repositoryInfo: {
                    repositoryType: scope.pageData.repositoryType || 1,//代码库类型
                    url: scope.pageData.git.url,
                    name: scope.pageData.git.userName,
                    password: scope.pageData.git.password,
                    branchName: scope.pageData.branchName//分支名称
                  }
                };
              }
              var paramData = {
                "flowId": queryParams.flowId,
                "inteTestEntity": InteTestEntity
              }
              if (scope.pageData.type == 0) {
                if (scope.isSvn) {
                  if (scope.ok) {
                    if (scope.pageData.url != '' && scope.pageData.userName != '' && scope.pageData.password != '') {
                      saveModify(paramData);
                    } else {
                      COREDATA.resMessage("带*号的不能为空");
                    }
                  } else {
                    COREDATA.resMessage("请正确填写所有信息项");
                  }

                } else {
                  if (scope.gitok) {
                    if (scope.pageData.git.url != '' && scope.pageData.git.userName != '' && scope.pageData.git.password != '' && scope.pageData.branchName != '') {
                      saveModify(paramData);
                    } else {
                      COREDATA.resMessage("带*号的不能为空");
                    }
                  } else {
                    COREDATA.resMessage("请正确填写所有信息项");
                  }

                }
              } else if (scope.pageData.type == 1) {
                if (scope.pageData.selectedItem) {
                  saveModify(paramData);
                } else {
                  COREDATA.resMessage("请选择引用");
                }

              }
            }

            function saveModify(paramData){
              $http({
                method: 'POST',
                url: '/paas/integrationtest/updateInteTest.action',
                data: paramData,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
              }).success(function (res) {
                if (res.resultCode + '' === '200') {
                  $("#cancel-btn").click();
                  scope.$emit('updateStep');//刷新流水步骤
                  COREDATA.resMessage(res.resultCode, res.resultMessage);
                }else{
                  COREDATA.resMessage(res.resultCode, res.resultMessage);
                }
              }).error(function (res) {
                COREDATA.resMessage(res.resultMessage);
              });
            }
          };

        }
      }
    })
})();
