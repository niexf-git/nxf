(function () {
  'use strict';
  angular.module('PaaS5.serialDetail')
    .directive('checkReportShow', function() {
      return {
        restrict: 'A',
        link: function(scope, element, attrs) {
          $(element).click(function() {
            var _this = $(this);
            // if(_this.attr('name') === 'checkReport') {
            //   _this.parent().parent().next().show();
            //   _this.hide();
            // }else {
            //   _this.next().toggle();
            //   if(_this.parent().parent().next().css('display') === 'block')
            //     _this.next().hide();
            //   _this.parent().parent().next().hide();
            // }
            _this.parent().parent().next().toggle();
          })
        }
      }
    })
    .controller('deployLogController', function ($scope, $state, deployLogService,CORE_VALUE,COREDATA, $rootScope,$cookieStore) {

    $("[name=hoverName]").hover(
        function () {
          $("#build").show();
        },
        function () {
          $("#build").hide();
        }
      );

    //状态样式
    $scope.statusColor = function(status) {
        var obj = {color: ''}
        if(status == '0') {
          obj.color = '#333';
        }else if(status == '1') {
          obj.color = '#3fc000';
        }else if(status == '2') {
          obj.color = '#e4342f';
        }else if(status == '3') {
          obj.color = '#02a0da';
        }else if(status == '4') {
          obj.color = '#ccc';
        }
        return obj;
      }

      //列表查询参数
      $scope.initParams = {
        flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,
        rows: 10, //每页条数
        page: 1,  //当前页
      };

      //查询列表方法
      $scope.init = function () {
        $scope.flowInfo = CORE_VALUE.PROJECTINFO.FLOWINFO;
        deployLogService.queryAllRecord($scope.initParams, function (res) {
          if (res.resultCode === '200') {
            $scope.res= JSON.parse(res.data);
            $scope.resData = $scope.res.result;
            $scope.totalCount = $scope.res.totalCount;
          } else{
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
        });
      };

      $scope.init();//调用默认查询方法

      //报告参数
      $scope.reportParams = {
        flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId
      };

      //关闭模态框
      $scope.closeDia = function () {
        $('#scanReportModal').dialog('close');
      };

      //报告跳转是否插入多个页面
      $rootScope.isShowHtml = false;

      //安全扫描报告
      $scope.goScanReport = function (recordId) {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goScanReport({flowRecordId: recordId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $rootScope.isShowHtml = false;
            document.getElementById("stepReport").contentWindow.document.write('');
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").src = JSON.parse(res.data);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };

      // 自动测试报告
      $scope.goTestReport = function (recordId) {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goAutoReport({flowRecordId: recordId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $rootScope.isShowHtml = false;
            document.getElementById("stepReport").contentWindow.document.write('');
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").src = JSON.parse(res.data);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };

      //集成测试报告
      $scope.goReport = function (recordId) {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goIntegrateReport({flowRecordId: recordId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $rootScope.isShowHtml = false;
            document.getElementById("stepReport").contentWindow.document.write('');
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").src = JSON.parse(res.data);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };

      //性能测试报告
      $scope.goPerformanceReport = function (recordId) {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goPerformanceReport({flowRecordId: recordId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $rootScope.isShowHtml = false;
            document.getElementById("stepReport").contentWindow.document.write('');
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").src = JSON.parse(res.data);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };

      //单元测试报告
      $scope.goUnitReport = function (recordId) {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goUnitReport({flowRecordId: recordId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            $rootScope.isShowHtml = true;
            var resArray = JSON.parse(res.data).split(',');
            $rootScope.unitReportUrl = resArray[0];
            $rootScope.jacocoReportUrl = resArray[1];
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
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };

      //覆盖率报告 暂时保留
      /*$scope.goCoverageRateReport = function () {
        $state.go('PaaS5.serialDetail.stepReport');
        deployLogService.goCoverageRateReport({flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}, function (res) {
          if(JSON.parse(res.data) !=null && JSON.parse(res.data)!= ''){
            document.getElementById("stepReport").contentWindow.document.write('');
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").contentWindow.document.write(JSON.parse(res.data));
            document.getElementById("stepReport").contentWindow.document.close();
            document.getElementById("stepReport").src = JSON.parse(res.data);
          }else{
            $state.go('PaaS5.serialDetail.staticReport');
          }
        })
      };*/

      $scope.showExecutionRecordPanel = function(p) {
        // vm.flowInfo = p;
        // 广播事件，让自定义指令接收到信号及参数
        $rootScope.$broadcast('showExecutionRecordPanel', p);
        // 展示流水执行记录框
        $("body").attr("style", "padding-right: 10px; overflow: hidden;");
        $("div[name='chenzhe'] .modal-mask").removeClass("modal-mask-hidden");
        $("div[name='chenzhe'] .flowBuildLogModal").show();
      };

      $scope.$on('serialDetailFresh', function() {
          $scope.init();
      });




      //进入到代码详情问题列表页面
      $scope.problemList = function(flowRecordId,index){
        COREDATA.setStorageInfo('PROJECTINFO','flowRecordId',flowRecordId, 'flowRecordId');
        $cookieStore.put('idIndex', index);//id
        // $state.go('PaaS5.codeCheck');
      }

    });
})();
