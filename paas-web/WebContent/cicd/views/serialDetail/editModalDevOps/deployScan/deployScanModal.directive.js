(function () {
  /**
   * 部署扫描弹出框
   */

  'use strict';
  angular.module('PaaS5')
    .directive('deployScanModal', function (CORE_VALUE, $http, COREDATA, $timeout ,$interval) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/deployScan/deployScanModal.html',
        scope: {
          init: '&'
        },
        replace: false,
        link: function (scope, element, attrs) {

          scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;//图片路径
          //是否禁用编辑按钮
          scope.flowInfo = CORE_VALUE.PROJECTINFO;

          //创建服务能否点击
          scope.canClick = CORE_VALUE;

          //编辑保存参数
          scope.saveParams = {
            appSvcName: '', //服务名称
            isCheck: true, //是否安全 0否 1是
            type: 0, //1 普通 2 灰度
            webUrl: '', //web地址
            serviceID: 0
            // grayNum: 0,
            // totalNum: 0
          };

          //灰度滑动条 暂时保留 20017-11-23
          /*scope.mySlider = {
            greyParams: {
              min: 0,
              max: 10,
              step: 1,
              value: [0, 0]
            },
            slider: {},
            change: function (sliderObj) {
              if(sliderObj.value[1] === scope.mySlider.greyParams.max){
                scope.mySlider.greyParams.max = scope.mySlider.greyParams.max*2;
              }else if(sliderObj.value[1] < (scope.mySlider.greyParams.max/2)){
                scope.mySlider.greyParams.max = scope.mySlider.greyParams.max/2;
              }
              $timeout(function () {
                scope.mySlider.greyParams.value[0] = sliderObj.value[0];
                scope.mySlider.greyParams.value[1] = sliderObj.value[1];
                scope.mySlider.slider.bootstrapSlider('setAttribute','max',scope.mySlider.greyParams.max);
                scope.mySlider.slider.bootstrapSlider('setAttribute','value',scope.mySlider.greyParams.value);
                scope.mySlider.slider.bootstrapSlider('refresh');
              },10);
            }
          };*/

          // 查询卡片信息方法
          scope.queryStepInfo = function () {
            $http({
              method: 'GET',
              url: '/paas/deploymentscan/queryDepScanEntity.action',
              params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}
              // params: {flowId: 'd645c5f48e4111e7acf100505694211b'}
            }).success(function (res) {
              if (res.resultCode === '200') {
                res.data = JSON.parse(res.data);
                if (res.data.isCheck === '1') {
                  scope.saveParams.isCheck = true;
                } else {
                  scope.saveParams.isCheck = false;
                }
                scope.resData = res.data;
                scope.saveParams.type = res.data.type;
                scope.saveParams.appSvcName = res.data.appSvcName;
                scope.saveParams.webUrl = res.data.webUrl;
                scope.saveParams.serviceID = res.data.serviceID;
                //滑动条数据-开始
                /*scope.mySlider.greyParams.value[0] = res.data.grayNum;
                scope.mySlider.greyParams.value[1] = res.data.totalNum;
                scope.mySlider.greyParams.max = res.data.totalNum*2;
                $timeout(function () {
                  if(scope.mySlider.slider.bootstrapSlider){
                    scope.mySlider.slider.bootstrapSlider('setAttribute','max',scope.mySlider.greyParams.max);
                    scope.mySlider.slider.bootstrapSlider('setAttribute','value',scope.mySlider.greyParams.value);
                    scope.mySlider.slider.bootstrapSlider('refresh');
                  }else{
                    scope.mySlider.slider = $("#deployScanSlider").bootstrapSlider().on('slideStop', scope.mySlider.change);
                  }
                },10);*/
                //滑动条数据-结束
              } else {
                COREDATA.resMessage(res.resultCode, res.resultMessage);
              }
            }).error(function (res) {
              COREDATA.resMessage();
            });
          };

          // 保存方法
          scope.saveStepInfo = function () {
            // scope.saveParams.grayNum = scope.mySlider.slider.bootstrapSlider('getValue')[0];
            // scope.saveParams.totalNum = scope.mySlider.slider.bootstrapSlider('getValue')[1];
            var tempParams = angular.copy(scope.saveParams);
            if (tempParams.isCheck === true) {
              tempParams.isCheck = 1;
            } else {
              tempParams.isCheck = 0;
            }
            $http({
              method: 'POST',
              url: '/paas/deploymentscan/modifyDepScan.action',
              data: {depScanEntity: tempParams, flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId},
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (res) {
              if (res.resultCode === '200') {
                $('#cancel').click();
                scope.$emit('updateStep');//刷新流水步骤
              }
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }).error(function (res) {
              COREDATA.resMessage();
            });
          };

          //创建服务方法
          scope.invokeService = function () {
            openDialog('/paas/jsp/appservice/createAppService.jsp?flowId=' + CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,
              '创建服务', 610, 530);
            $('#cancel').click();
            //定时查询创建服务是否成功，创建成功后 弹出模态框
            scope.timer = $interval(function(){
              scope.queryStepInfo();
            if(scope.saveParams.appSvcName !='' && document.getElementById('dialogIf') ==null){//服务名存在时,关闭定时且弹出模态框
              $("#deployScanId").click(); 
              $interval.cancel(scope.timer);//停止循环
            }else if(document.getElementById('dialogIf') ==null && scope.saveParams.appSvcName ===''){//关闭创建服务框时关闭定时
              $interval.cancel(scope.timer);//停止循环
            }else{
              // scope.queryStepInfo();//循环
            }
          },1500);

          };

          //取消方法
          scope.cancel = function () {
            scope.queryStepInfo();//清空选择数据
          };



        }
      };
    });

})();
