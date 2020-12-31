(function () {
  'use strict';
  /**
   * @ngdoc function
   * @name PaaS5.controller:editModalDevOpsCtrl
   * @description
   * # editModalDevOpsCtrl 卡片页面ctrl
   * Controller of the PaaS5
   */
  angular.module('PaaS5.controllers')
    .controller('editModalDevOpsCtrl', function ($scope,navState, editModalDevOpsService, $http, CORE_VALUE,deployLogService,COREDATA,$rootScope,$state,$interval,$timeout) {

      //$scope.recordData = [];
      var socket;
      //下载审查的配置信息的查询参数（执行记录查询参数）
      $scope.queryParams = {
        flowId:  CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,//流水id
        stepName: "depscan"//步骤名称
      };

      //显示执行记录框
      $scope.timer='';
      $scope.showExecutionRecordPanel = function (event) {
        $scope.recordData = [];
        $scope.queryParams = {
          flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,//流水id
          stepName: event//步骤名称
        };

        $("body").attr("style", "padding-right: 10px; overflow: hidden;");
        $("div[name='wzp'] .modal-mask").removeClass("modal-mask-hidden");
        $("div[name='wzp'] .flowBuildLogModal").show();
        $scope.queryRecord();
      };
      
      $scope.recordStatus = 0; // 0：无运行中状态 ；1:有运行中状态
      $scope.fresh=function(){
        editModalDevOpsService.queryRecord($scope.queryParams, function (res) {
          if (res.resultCode + '' === '200') {
            $scope.recordData = JSON.parse(res.data);
            for (var i = 0; i <$scope.record.length; i++) {
              if ($scope.record[i].status == '3') {
            		$scope.recordStatus = 1;
            		return;
              }else {
            	  $scope.recordStatus = 0;
              }
            }
          }
        });
      };
//      var watchRecordStatus = $scope.$watch('recordStatus', function(newValue, oldValue) {
//    	  console.log('进入watchRecordStatus');
//    	  console.log('newValue:' + newValue)
//    	  if(newValue === '3') {
//    		  $interval.cancel($scope.timer);
//    		  $scope.timer = $interval(function() {$scope.fresh();}, 2000);
//    	  }else {
//    		  $interval.cancel($scope.timer);
//    	  }
//      })
      

      $scope.$on('$destroy',function(){
        $interval.cancel($scope.timer);
//        watchRecordStatus();
      });
      /**
       * 配合后台先查询出最新的流水记录ID(flowRecordId),然后点击卡片上面的报告进入报告页面
       */
      //列表查询参数
      $scope.initParams = {
        flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,
        rows: 10, //每页条数
        page: 1  //当前页
      };
      //查询flowRecordId列表
      $scope.queryFlowRecordId = function () {
        deployLogService.queryAllRecord($scope.initParams, function (res) {
          if (res.resultCode === '200') {
            $scope.flowRecordIdList= JSON.parse(res.data).result;
          } else{
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      };//end
      $scope.$on('serialDetailFresh', $scope.queryFlowRecordId)
      $scope.queryFlowRecordId();

      //安全扫描报告
      $scope.goScanReport = function () {
        if($scope.flowRecordIdList.length ===0){//未执行立即构建或者正在执行中操作，跳转到暂无报告页面
          $state.go('PaaS5.serialDetail.staticReport');
          return;
        }
        $http({
         method: 'get',
         url: '/paas/queryreport/querySecurityScanReport.action',
         params: {flowRecordId: $scope.flowRecordIdList[0].flowRecordId},
         headers: { 'Content-Type': 'text/plain' }
         }).success(function(res){
          if(res.resultCode === '200' &&JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $state.go('PaaS5.serialDetail.stepReport');
            $rootScope.isShowHtml = false;
            $timeout(function () {
              document.getElementById("stepReport").contentWindow.document.write('');
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").src = JSON.parse(res.data);
            }, 100);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
         });
      };

      //集成测试报告
      $scope.goReport = function () {
        if($scope.flowRecordIdList.length ===0){//未执行立即构建操作，跳转到暂无报告页面
          $state.go('PaaS5.serialDetail.staticReport');
          return;
        }
        deployLogService.goIntegrateReport({ flowRecordId: $scope.flowRecordIdList[0].flowRecordId }, function (res) {
          if (res.resultCode === '200' &&JSON.parse(res.data) !=null && JSON.parse(res.data)!= '') {
            $state.go('PaaS5.serialDetail.stepReport');
            $rootScope.isShowHtml = false;
            $timeout(function () {
              document.getElementById("stepReport").contentWindow.document.write('');
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").src = JSON.parse(res.data);
            }, 100);
          } else {
            $state.go('PaaS5.serialDetail.staticReport');
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        })
      };

      //点击自动化测试报告
      $scope.goTestReport = function () {
        if($scope.flowRecordIdList.length ===0 ){//未执行立即构建操作，跳转到暂无报告页面
          $state.go('PaaS5.serialDetail.staticReport');
          return;
        }
        deployLogService.goAutoReport({ flowRecordId: $scope.flowRecordIdList[0].flowRecordId }, function (res) {
          if (res.resultCode === '200' &&JSON.parse(res.data) !=null && JSON.parse(res.data)!= '') {
            $state.go('PaaS5.serialDetail.stepReport');
            $rootScope.isShowHtml = false;
            $timeout(function () {
              document.getElementById("stepReport").contentWindow.document.write('');
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").src = JSON.parse(res.data);
            }, 100);
          } else {
            $state.go('PaaS5.serialDetail.staticReport');
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        })
      },

      //点击性能测试报告
      $scope.goPerforReport = function () {
        if($scope.flowRecordIdList.length ===0 ){//未执行立即构建操作，跳转到暂无报告页面
          $state.go('PaaS5.serialDetail.staticReport');
          return;
        }
        deployLogService.goPerformanceReport({ flowRecordId: $scope.flowRecordIdList[0].flowRecordId }, function (res) {
          if (res.resultCode === '200' &&JSON.parse(res.data) !=null && JSON.parse(res.data)!= '') {
            $state.go('PaaS5.serialDetail.stepReport');
            $rootScope.isShowHtml = false;
            $timeout(function(){
              document.getElementById("stepReport").contentWindow.document.write('');
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").src = JSON.parse(res.data);
            },100);
          } else {
            $state.go('PaaS5.serialDetail.staticReport');
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        })
      };

      //编译构建报告
      $scope.goCompileReport = function () {
        if($scope.flowRecordIdList.length ===0 ){//未执行成功，跳转到暂无报告页面
          $state.go('PaaS5.serialDetail.staticReport');
          return;
        }
        $http({
          method: 'get',
          url: '/paas/queryreport/queryUnitTestReport.action',
          params: { flowRecordId: $scope.flowRecordIdList[0].flowRecordId },
          headers: { 'Content-Type': 'text/plain' }
        }).success(function (res) {
          if (res.resultCode === '200' &&JSON.parse(res.data) !=null && JSON.parse(res.data)!= '') {
            $state.go('PaaS5.serialDetail.stepReport');
            $rootScope.isShowHtml = true;
            var resArray = JSON.parse(res.data).split(',');
            $rootScope.unitReportUrl = resArray[0];
            $rootScope.jacocoReportUrl = resArray[1];
            $timeout(function(){
              document.getElementById("stepReport").contentWindow.document.write('');
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").contentWindow.document.write(resArray[0]);
              document.getElementById("stepReport").contentWindow.document.close();
              document.getElementById("stepReport").src = resArray[0];
              document.getElementById("stepReport2").contentWindow.document.write('');
              document.getElementById("stepReport2").contentWindow.document.close();
              document.getElementById("stepReport2").contentWindow.document.write(resArray[1]);
              document.getElementById("stepReport2").contentWindow.document.close();
              document.getElementById("stepReport2").src = resArray[1];
            },100);
          } else {
            $state.go('PaaS5.serialDetail.staticReport');
            COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      };


      //执行记录
      $scope.queryRecord = function (event) {
        editModalDevOpsService.queryRecord($scope.queryParams, function (res) {
          if (res.resultCode + '' === '200') {
            $scope.recordData = JSON.parse(res.data);
            console.log($scope.recordData)
            $scope.stepname = '';
            if ($scope.recordData != null && $scope.recordData[0].stepName) {
              for (var i = 0; i < $scope.recordData.length; i++) {
                if ($scope.recordData[i].status == '3') {
                  var div = $('.collapse-header');
                  $scope.recordStatus = 1;
                  socket = buildLogWebSoket($scope.queryParams.flowId, $scope.recordData[i], div[i]);
                  return;
                }
              }
            }
          } else {
            COREDATA.resMessage(res.resultMessage);
          }
        });
      };

      /**
       * 获取打开中的执行记录下标
       */
      function findOpenRecordIndex () {
        var stepRecodeId = this.stepId;
        for (var i = 0; i < $scope.recordData.length; i++) {
          if (stepRecodeId === $scope.recordData[i].stepRecodeId) {
            return i;
          }
        }
      }

      /**
       * 新建webSocket长连接
       * @param {*} flow_id
       * @param {*} data
       * @param {*} ele
       */
      function buildLogWebSoket (flow_id, data, ele) {
        var socket;
        var wsIndex;

        if (window.WebSocket) {
          if (socket != undefined && soket.readState == 1) {
            socket.close()
          }
          socket = new WebSocket('ws://' + window.location.host + '/paas/websocket/stepLog');

          socket.onmessage = function (event) {
            var result = JSON.parse(event.data);
            console.info('webSocket message 返回');
            if (result.resultCode != "0") {
              var $div=$('.collapse-content.collapse-content-active');
              for(var i=0;i<$div.length;i++){
                if($($div[i]).hasClass('collapse-content-active')){
                  var hgt=$($div[i]).find('#flowLog').height();
                }
              }
              $('#flowStageBuildLog').scrollTop(20000);

              $scope.recordData[wsIndex].text += result.resultMessage;
            } else if (result.resultCode == '0') {
              socket.close();
              console.log('长连接关闭');
            } else {
              scoket.close();
            }
          };
          socket.onopen = function () {
            console.log('长连接打开');
            wsIndex = 0;
            $scope.recordData[wsIndex].text = '';
          };
          socket.onclose = function () {

          };
          socket.onerror = function () {
            console.log('长连接错误')
          };
          setTimeout(function () {
            socket.send(flow_id + '&' + data.stepRecodeId + '&' + data.asName);
          }, 200);

        } else {
          alert('您的浏览器不支持 webSocket 长连接');
        }
      }

      //步骤日志(执行完成)
      $scope.recordLog = function (data,event) {
        var text = $(event.target).parent().find('span.fail').text();
        var ele = $(event.target).closest('.collapse-header');

        if (ele.attr("aria-expanded") == "false") {
          return;
        }

        $scope.queryLogParams = {
          flowId: $scope.queryParams.flowId,//流水id
          stepId: data.stepRecodeId,
          stepName: data.asName,//步骤名称
          stepStatus: data.stepStatus//是否执行完成
        };
        if (text == '运行中') {
        	if(socket != null) {socket.close();console.log('关闭长连接')};
          console.log("点击recordLog下拉 赋值 socket" );
          socket = buildLogWebSoket($scope.queryParams.flowId, data, ele)
        }
        else {
          console.log("socket: -------")
          console.log(socket)
          // 存在webSocket则将其关闭
          if(socket != null) {socket.close();console.log('关闭长连接')};
          
          editModalDevOpsService.queryRecordLog($scope.queryLogParams, function (res) {
            console.log(res);
            if (res.resultCode + '' === 'success') {
              var recordIndex = findOpenRecordIndex.call($scope.queryLogParams);
              console.log(recordIndex);
              $scope.recordData[recordIndex].text = res.resultMessage;
            } else {
              COREDATA.resMessage(res.resultMessage);
              console.log("查询失败")
            }
          });
        }
      };




      /**************************************** 流水步骤卡片展示 陈哲  *********************************************/
      /**
       * 流水步骤展示模块
       * @type {Object}
       */
      $scope.queryFlowSteps = {
        data: {},
        fn: function () {
          var params = {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId};
          editModalDevOpsService.queryFlowStep(params, function (res) {
            if (res.resultCode === '200') {
              console.log(JSON.parse(res.data));
              $scope.queryFlowSteps.packageFn(JSON.parse(res.data));
              if(JSON.parse(res.data).codeDownloadAndCheckEntity !== null){
            	  var isCodeCheck = JSON.parse(res.data).codeDownloadAndCheckEntity.isCodeCheck;
                  $scope.testabc = !(isCodeCheck === "0");
              }
              var isScanCheck = $scope.queryFlowSteps.data.depScanEntity.isCheck;
              $scope.onOff = !(isScanCheck === '0');
            }
          })
        },
        packageFn: function (data) {
          for (var key in data) {
            var a = (key === 'autoTestEntity' | key === 'inteTestEntity' | key === 'testAutoTestEntity' | key === 'testInteTestEntity') && data[key] !== null;
            $scope.queryFlowSteps.data[key] = data[key];
            if (a) {
              if (Number(data[key].fallNumber) === 0) {
                $scope.queryFlowSteps.data[key].percent = 0;
              } else {
                var percent = (Number(data[key].fallNumber) / Number(data[key].allNumber) * 100).toFixed(2);
                $scope.queryFlowSteps.data[key].percent = percent;
              }
            }

          }
        }
      };

      $scope.queryFlowSteps.fn();

      $scope.$on('serialDetailFresh', function () {
        $scope.queryFlowSteps.fn();
      });


      /****************************************卡片开关*********************************************/


      //下载审查是否代码审查开关
      $scope.isCodeCheck = function (isCheck) {
        if(CORE_VALUE.PROJECTINFO.FLOWINFO.buildStatus === '3') return;
        //查询参数
        $scope.checkParams = {
          flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId, //流水id
          isCodeCheck: 0 //是否代码审查
        };

        //是否代码审查（0、否,1、是）
        if (isCheck === true) {
          $scope.testabc = false;
          $scope.checkParams.isCodeCheck = 0;
        } else {
          $scope.testabc = true;
          $scope.checkParams.isCodeCheck = 1;
        }

        $http({
          method: 'GET',
          url: '/paas/downloadCheck/modifyCodeCheckState.action',
          params: $scope.checkParams,
        }).success(function (res) {
           if (res.resultCode + '' === '200') {
               console.log(res.resultMessage)
           }else{
             $scope.testabc = !$scope.testabc;
              COREDATA.resMessage(res.resultCode, res.resultMessage);
           }
        });
      };


      //部署扫描开关参数
      $scope.scanParams = {
        flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,
        isCheck: 0
      };

      //部署扫描卡片开关
      $scope.isSafeScan = function (onOff) {
        if(CORE_VALUE.PROJECTINFO.FLOWINFO.buildStatus === '3'){
          //运行中的流水开关不可用
          return;
        }
        if (onOff === true) {
          $scope.onOff = false;
          $scope.scanParams.isCheck = 0;
        } else {
          $scope.onOff = true;
          $scope.scanParams.isCheck = 1;
        }
        $http({
          method: 'GET',
          url: '/paas/deploymentscan/modifyIsCheckSecurity.action',
          params: $scope.scanParams
        }).success(function (res) {
          console.log(res.resultMessage)
        });
      };

    //开发发布-手动发布生产按钮
      $scope.isReleaseProduction = function () {
         $http({
           method: 'GET',
           url: '/paas/release/releaseProduction.action',
           params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}//流水id
         }).success(function (res) {
            if (res.resultCode + '' === '200') {
               COREDATA.resMessage(res.resultCode, res.resultMessage);
            }else{
               COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
         });
     };

     //部署扫描服务名链接跳转
     $scope.skipDepScanService = function(){
       function compareLocationSearch(search) {
         var index = $('iframe.oldPageClass').attr('src').indexOf('?');
        return $('iframe.oldPageClass').attr('src').substring(index)=== search ? true : false;
       }
       var search = '?appServiceId='
         +$scope.queryFlowSteps.data.depScanEntity.serviceID
         +'&appServiceName='+$scope.queryFlowSteps.data.depScanEntity.appSvcName
         +'&runVersion='+$scope.queryFlowSteps.data.depScanEntity.runVersion
         +'&runVersionId='+$scope.queryFlowSteps.data.depScanEntity.runVersionId
         +'&state='+$scope.queryFlowSteps.data.depScanEntity.state
         +'&_instance_num='+$scope.queryFlowSteps.data.depScanEntity.instanceNum;
      
       if( typeof $scope.queryFlowSteps.data.depScanEntity.appSvcName !='undefined' &&
        $scope.queryFlowSteps.data.depScanEntity.appSvcName!='' ){
         if(!compareLocationSearch(search))
            $('iframe.oldPageClass').attr('src','/paas/jsp/appservice/appServiceDetail.jsp' + search);
        CORE_VALUE.isCICD.value1 = false;
        CORE_VALUE.isCICD.value2 = true;
        angular.forEach(navState.menu, function (value) {
           value.isInside = false;
           value.openState = false;
           if(value.name === '服务管理'){
             value.isInside = true;
           }
        });
      }
     };

     //部署测试服务名链接跳转
     $scope.skipTestService = function(){
      if(typeof $scope.queryFlowSteps.data.testDeployEntity.appSvcName!='undefined' &&
        $scope.queryFlowSteps.data.testDeployEntity.appSvcName !=''){
        $('iframe.oldPageClass').attr('src','/paas/jsp/appservice/appServiceDetail.jsp?appServiceId='
        +$scope.queryFlowSteps.data.testDeployEntity.serviceID
        +'&appServiceName='+$scope.queryFlowSteps.data.testDeployEntity.appSvcName
        +'&runVersion='+$scope.queryFlowSteps.data.testDeployEntity.runVersion
        +'&runVersionId='+$scope.queryFlowSteps.data.testDeployEntity.runVersionId
        +'&state='+$scope.queryFlowSteps.data.testDeployEntity.state
        +'&_instance_num='+$scope.queryFlowSteps.data.testDeployEntity.instanceNum);
        CORE_VALUE.isCICD.value1 = false;
        CORE_VALUE.isCICD.value2 = true;
        angular.forEach(navState.menu, function (value) {
           value.isInside = false;
           value.openState = false;
           if(value.name === '服务管理'){
             value.isInside = true;
           }
        });
      }
     };

      /**
        * 执行记录模态框展示控制
        */
        $scope.recordCtrl = {
          //改变执行记录框大小
          changeExecutionRecordPanel: function () {
            var isSmallModal = $("div#StageBuildLog").attr("class") == "smallModal";
            console.log($("div#StageBuildLog").attr("class"));
            if (isSmallModal) {
              $("div#StageBuildLog").attr("class", "bigModal");
            } else {
              $("div#StageBuildLog").attr("class", "smallModal");
            }
          },
          //展开或者折叠内容
          openOrFoldContent: function (event) {
           var isOpen = $(event.currentTarget).attr("aria-expanded") == "true";
           if (isOpen) {//折叠
             $(event.currentTarget).attr("aria-expanded", "false");
             $(event.currentTarget).next().removeClass("collapse-content-active");
             $(event.currentTarget).next().addClass("collapse-content-inactive");
             $(event.currentTarget).children(":first").removeClass("fa-sort-asc");
             $(event.currentTarget).children(":first").addClass("fa-sort-desc");
           } else {//展开
             $(event.currentTarget).attr("aria-expanded", "true");
             $(event.currentTarget).next().removeClass("collapse-content-inactive");
             $(event.currentTarget).next().addClass("collapse-content-active");
             $(event.currentTarget).children(":first").removeClass("fa-sort-desc");
             $(event.currentTarget).children(":first").addClass("fa-sort-asc");
             $(event.currentTarget).closest('.rightBox').siblings().find('.collapse-header').attr("aria-expanded", "false");
             $(event.currentTarget).closest('.rightBox').siblings().find('.collapse-content').addClass('collapse-content-inactive').removeClass('collapse-content-active');
             $(event.currentTarget).closest('.rightBox').siblings().find('i.fa-sort-asc').removeClass("fa-sort-asc").addClass('fa-sort-desc');
           }
         },
         // 关闭模态框
         closeExecutionRecordPanel:function closeExecutionRecordPanel() {
        	 if(socket)  socket.close();
//        	 if($scope.recordTimer) $interval.cancel($scope.recordTimer);
           $("body").attr("style", "overflow-y:scroll;");
           $("div[name='wzp'] .flowBuildLogModal").hide();
           $("div[name='wzp'] .modal-mask").addClass("modal-mask-hidden");
         }
       
        }
 

    });
})();
