(function () {
  /**
   * 部署测试模态框
   * Created by zhihua_wu on 2017/8/29.
   */

  'use strict';
  angular.module('PaaS5')
    .directive('deployTestModal', function (CORE_VALUE, $http,COREDATA, $timeout,$interval) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/deployTest/deployTestModal.html',
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
            isCheck: true, //是否安全 0是 1否
            type: 0, //1 普通 2 灰度
            webUrl: '', //web地址
            serviceID:0
            // grayNum: 0,
            // totalNum: 0
          };

          //灰度滑动条 暂时保留 2017-11-23
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
          scope.queryDeployInfo = function () {
            $http({
              method: 'GET',
              url: '/paas/deploy/queryDepEntity.action',
              params: {flowId: CORE_VALUE.PROJECTINFO.FLOWINFO.flowId}
            }).success(function (res) {
              if (res.resultCode === '200') {
                res.data= JSON.parse(res.data);
                // console.log(res.data);
                if (res.data.isCheck === '0') {
                  scope.saveParams.isCheck = true;
                } else {
                  scope.saveParams.isCheck = false;
                }
                scope.resData = res.data;
                scope.saveParams.type = res.data.type;
                scope.saveParams.appSvcName = res.data.appSvcName;
                scope.saveParams.webUrl = res.data.webUrl;
                scope.saveParams.serviceID = res.data.serviceID;
                //滑动条数据-开始 暂时保留 2017-11-23
                /*scope.mySlider.greyParams.value[0] = res.data.grayNum;
                scope.mySlider.greyParams.value[1] = res.data.totalNum;
                scope.mySlider.greyParams.max = res.data.totalNum*2;
                $timeout(function () {
                  if(scope.mySlider.slider.bootstrapSlider){
                    scope.mySlider.slider.bootstrapSlider('setAttribute','max',scope.mySlider.greyParams.max);
                    scope.mySlider.slider.bootstrapSlider('setAttribute','value',scope.mySlider.greyParams.value);
                    scope.mySlider.slider.bootstrapSlider('refresh');
                  }else{
                    scope.mySlider.slider = $("#deployTestSlider").bootstrapSlider().on('slideStop', scope.mySlider.change);
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
          scope.saveScanInfo = function () {
            // scope.saveParams.grayNum = scope.mySlider.slider.bootstrapSlider('getValue')[0];
            // scope.saveParams.totalNum = scope.mySlider.slider.bootstrapSlider('getValue')[1];
            var tempParams = {'depScanEntity':scope.saveParams,flowId:CORE_VALUE.PROJECTINFO.FLOWINFO.flowId};
            if (scope.saveParams.isCheck === true) {
              scope.saveParams.isCheck = 0;
            } else {
              scope.saveParams.isCheck = 1;
            }
            $http({
              method: 'POST',
              url: '/paas/deploy/modifyDep.action',
              data: tempParams,
              headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).success(function (res) {
              if (res.resultCode === '200') {
                $('#cancelTest').click();
                scope.$emit('updateStep');//刷新流水步骤
              }
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }).error(function (res) {
                COREDATA.resMessage();
              });
          };

          //创建服务方法
          scope.invoke = function () {
            openDialog('/paas/jsp/appservice/createAppService.jsp?flowId='+CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,
             '创建服务', 610,530);
            $('#cancelTest').click();
            //定时查询创建服务是否成功，创建成功后 弹出模态框(模拟点击事件)
            scope.timer = $interval(function(){
              scope.queryDeployInfo();
              if(scope.saveParams.appSvcName !='' && document.getElementById('dialogIf') ==null){
                $("#deployTestId").click(); 
                $interval.cancel(scope.timer);//停止循环
              }else if(document.getElementById('dialogIf') ==null && scope.saveParams.appSvcName ===''){
                $interval.cancel(scope.timer);//停止循环
              }else{
                // scope.queryDeployInfo();
              }
            },1500);
          };

          //取消方法
          scope.cancel = function () {
            scope.queryDeployInfo();//清空选择数据
          };


        }
      };
    });

})();
