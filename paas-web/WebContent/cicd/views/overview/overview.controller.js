(function () {
  'use strict';
  /**
   * 总览页面CTRL
   * 上面7个图，下面一大堆
   */
  angular.module('PaaS5.controllers').controller('overviewCtrl', function ($rootScope, $http, $scope,overviewService1, overviewGetChartOption, overviewService,$filter,$timeout,CORE_VALUE,COREDATA) {

    var vm = this;
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('servicepPie'));//1总览_服务
    var myChart2 = echarts.init(document.getElementById('instancePie'));//2总览_实利
    var myChart3 = echarts.init(document.getElementById('alarmLine'));//3总览_告警
    var myChart4 = echarts.init(document.getElementById('cpu_allocated_utilization'));//4总览_已经分配利用率
    var myChart41 = echarts.init(document.getElementById('memory_allocated_utilization'));//+1
    var myChart5 = echarts.init(document.getElementById('cpu_actual_utilization'));//5总览_实际利用率
    var myChart51 = echarts.init(document.getElementById('memory_actual_utilization'));//+1
    var myChart52 = echarts.init(document.getElementById('disk_actual_utilization'));//+2
    var myChart6 = echarts.init(document.getElementById('flowBuildPie'));//流水

    vm.queryServiceStateFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryServiceState(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          vm.serviceStateData = JSON.parse(res.data);
          var myChartOption1 = overviewGetChartOption('pie');
          if(vm.serviceStateData.working === '0' && vm.serviceStateData.stopped === '0'){
            myChartOption1.color = ['#cfdbe7'];
            myChartOption1.series[0].data = [];
            myChartOption1.series[0].data.push({ name: '运行中', value: '0'});
          }else{
            myChartOption1.series[0].data = [];
            myChartOption1.series[0].data.push({ name: '运行中', value: vm.serviceStateData.working });
            myChartOption1.series[0].data.push({ name: '已停止', value: vm.serviceStateData.stopped });
          }
          myChartOption1.title = {
            text: whole(vm.serviceStateData.working,vm.serviceStateData.stopped),
            subtext: '全部',
            x: 'center',
            y: 'center'
          };
          myChart1.setOption(myChartOption1);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    vm.queryInstanceStateFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryInstanceState(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          vm.InstanceStateData = JSON.parse(res.data);
          var myChartOption2 = overviewGetChartOption('pie');
          if (vm.InstanceStateData.running === '0'
            && vm.InstanceStateData.stopped === '0'
            && vm.InstanceStateData.waiting === '0'
            && vm.InstanceStateData.unassigned === '0'
            && vm.InstanceStateData.unknow === '0') {
            myChartOption2.color = ['#cfdbe7'];
            myChartOption2.series[0].data = [];
            myChartOption2.series[0].data.push({ name: '运行中', value: '0'});
          }else{
            myChartOption2.series[0].data = [];
            myChartOption2.series[0].data.push({ name: '运行中', value: vm.InstanceStateData.running });
            myChartOption2.series[0].data.push({ name: '停止中', value: vm.InstanceStateData.stopped });
            myChartOption2.series[0].data.push({ name: '等待中', value: vm.InstanceStateData.waiting });
            myChartOption2.series[0].data.push({ name: '未调度', value: vm.InstanceStateData.unassigned });
            myChartOption2.series[0].data.push({ name: '未知', value: vm.InstanceStateData.unknow });
          }
          myChartOption2.title = {
            text: whole1(vm.InstanceStateData.running,vm.InstanceStateData.stopped,vm.InstanceStateData.waiting,vm.InstanceStateData.unassigned,vm.InstanceStateData.unknow),
            subtext: '全部',
            x: 'center',
            y: 'center'
          };
          myChart2.setOption(myChartOption2);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    vm.queryAlarmDetailsFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryAlarmDetails(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          var AlarmDetailsData = JSON.parse(res.data).alarmDetailsList;
          var myChartOption3 = overviewGetChartOption('line');
          myChartOption3.xAxis.data = [];
          myChartOption3.series[0].data = [];
          angular.forEach(AlarmDetailsData, function(value, key){
            myChartOption3.xAxis.data.push(value['alarmTime'].slice(5));
            myChartOption3.series[0].data.push(value['alarmCount']);
          });
          myChart3.setOption(myChartOption3);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    vm.queryAssignedResourceRateFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryAssignedResourceRate(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          var assignedResourceData = JSON.parse(res.data);
          var myChartOption4 = overviewGetChartOption('liquid');
          assignedResourceData.totalCpu == 0 ? myChartOption4.series[0].data[0].value = 0 : myChartOption4.series[0].data[0].value = (assignedResourceData.assignedCpu/assignedResourceData.totalCpu).toFixed(2);
          if (assignedResourceData.assignedCpu == 0) {
            myChartOption4.series[0].data[0].value = 0;
          } else {
            var val = (assignedResourceData.assignedCpu / assignedResourceData.totalCpu).toFixed(2);
            if (val < 0.01) {
              myChartOption4.series[0].data[0].value = 0.01;
            } else {
              myChartOption4.series[0].data[0].value = (assignedResourceData.assignedCpu / assignedResourceData.totalCpu).toFixed(2);
            }
          };
          myChart4.setOption(myChartOption4);
          var myChartOption41 = overviewGetChartOption('liquid');
          assignedResourceData.totalMemory == 0 ? myChartOption41.series[0].data[0].value = 0 : myChartOption41.series[0].data[0].value = (assignedResourceData.assignedMemory/assignedResourceData.totalMemory).toFixed(2);
          if (assignedResourceData.assignedMemory == 0) {
            myChartOption41.series[0].data[0].value = 0;
          } else {
            var val = (assignedResourceData.assignedMemory / assignedResourceData.totalMemory).toFixed(2);
            if (val < 0.01) {
              myChartOption41.series[0].data[0].value = 0.01;
            }else{
              myChartOption41.series[0].data[0].value = (assignedResourceData.assignedMemory / assignedResourceData.totalMemory).toFixed(2);
            }
          };
          myChart41.setOption(myChartOption41);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    vm.queryActualResourceRateFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryActualResourceRate(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          var actualResourceData = JSON.parse(res.data);
          var myChartOption5 = overviewGetChartOption('liquid');
          actualResourceData.totalCpu == 0 ? myChartOption5.series[0].data[0].value = 0 : myChartOption5.series[0].data[0].value = (actualResourceData.usedCpu/actualResourceData.totalCpu).toFixed(2);
          myChart5.setOption(myChartOption5);
          var myChartOption51 = overviewGetChartOption('liquid');
          actualResourceData.totalMemory == 0 ? myChartOption51.series[0].data[0].value = 0 : myChartOption51.series[0].data[0].value = (actualResourceData.usedMemory/actualResourceData.totalMemory).toFixed(2) ;
          myChart51.setOption(myChartOption51);
          var myChartOption52 = overviewGetChartOption('liquid');
          actualResourceData.totalDisk == 0 ? myChartOption52.series[0].data[0].value = 0 : myChartOption52.series[0].data[0].value = (actualResourceData.usedDisk/actualResourceData.totalDisk).toFixed(2) ;
          myChart52.setOption(myChartOption52);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    //总量
    vm.queryTotalResourceFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryTotalResource(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          vm.totalResource = JSON.parse(res.data);
          if(!vm.totalResource.host){
             vm.totalResource.host = 0
          }
          if(!vm.totalResource.cpu){
            vm.totalResource.cpu = 0
          }
          if(!vm.totalResource.memory){
            vm.totalResource.memory = 0
          }
          if(!vm.totalResource.disk){
            vm.totalResource.disk = 0
          }
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };
    //CI/CD
    vm.queryFlowBuildStateFn = function() {
      // 使用刚指定的配置项和数据显示图表。
      overviewService1.queryFlowBuildState(function (res) {//请求成功
        if (res.resultCode === "200" && res.data.length >= 1) {
          vm.flowBuildState = JSON.parse(res.data);
          var myChartOption6 = overviewGetChartOption('pie');
          if (vm.flowBuildState.success === '0'
            && vm.flowBuildState.fail === '0'
            && vm.flowBuildState.working === '0'
            && vm.flowBuildState.unexecuted === '0') {
            myChartOption6.color = ['#cfdbe7'];
            myChartOption6.series[0].data = [];
            myChartOption6.series[0].data.push({ name: '构建成功', value: '0'});
          }else{
            myChartOption6.series[0].data = [];
            myChartOption6.series[0].data.push({ name: '构建成功', value: vm.flowBuildState.success });
            myChartOption6.series[0].data.push({ name: '构建失败', value: vm.flowBuildState.fail });
            myChartOption6.series[0].data.push({ name: '正在构建', value: vm.flowBuildState.working });
            myChartOption6.series[0].data.push({ name: '未执行', value: vm.flowBuildState.unexecuted });
          }
          myChartOption6.title = {
            text: whole2(vm.flowBuildState.success,vm.flowBuildState.fail,vm.flowBuildState.working,vm.flowBuildState.unexecuted),
            subtext: '全部',
            x: 'center',
            y: 'center'
          }
           myChart6.setOption(myChartOption6);
        }
      },function (res) {//请求失败
          console.log("请求失败! " + window.location.href);
        }
      );
    };

    vm.initQueryState = function (){
      vm.queryServiceStateFn();
      vm.queryInstanceStateFn();
      vm.queryAlarmDetailsFn();
      vm.queryAssignedResourceRateFn();
      vm.queryActualResourceRateFn();
      vm.queryTotalResourceFn();
      vm.queryFlowBuildStateFn();
    };
    vm.initQueryState();
    $rootScope.$on('changeHeaderAppSuccess', function () {
      vm.initQueryState();
    });
    $scope.$on("clickOverview", function (e, m) {
      vm.initQueryState();
      $scope.getData($scope.queryParams);
    });

    /**
     * 原CICD总览部分
     */
      var ctrl = this;
      ctrl.commits = [];//计算总提交次数
      ctrl.pulls = [];
      $scope.pageCode = {
        url: '',
        type: '',
        branch:'',
        svnSelect:0,
        gitSelect:0
      };//修改代码仓库的类型与地址
      $scope.graphShow = true;//开始默认不显示图表
      $scope.indexData = 0;
      $scope.flowName = '流水列表';
      $scope.queryFlowNameListParams = {};//筛选条件-流水名称请求参数
      $scope.queryFlowNameListResData = null;//筛选条件-流水名称响应数据
      $scope.queryParams = {};
      $scope.buildResData = null;//构建响应数据
      $scope.deployResData = {};//部署数据
      $scope.automateTestResData = null;//自动化测试响应数据
      $scope.integrationTestResData = null;//集成测试响应数据
      $scope.queryCodeRepoResData = null;//代码仓库数据
      $scope.buildTimes = [];
      $scope.codeRepoListResData = null;
      $scope.codeRepoResData = null;
      $scope.detail = {};
      $scope.APPINFOTYPE = CORE_VALUE.PROJECTINFO.APPINFO;//获取初始开发类型（开发/测试）
      $scope.USERINFOTYPE = CORE_VALUE.USERINFO;//获取用户类型（开发/测试）
      if($scope.USERINFOTYPE.operType =='2'){
        $('.graph').height(550)
      }else{
        $('.graph').height(800)
      };
     // $scope.onlyOneTime = true;//只接收一次广播
      var isClickCICD = $rootScope.$on("overview",function(event,data){    //点击CI/CD
        if(data==='ok'/* && $scope.onlyOneTime === true*/){   //未知原因导致多次接收广播。
          var firstData = {flowId:$scope.queryFlowNameListResData[0].flowId};
          $scope.getData(firstData);//获取各图表数据
          //$scope.onlyOneTime = false;
          $scope.USERINFOTYPE = CORE_VALUE.USERINFO;//获取用户类型
          if($scope.USERINFOTYPE.operType =='2'){
            $('.graph').height(550)
          }else{
            $('.graph').height(800)
          };
        }
      });
      $scope.$on('$destroy',function(){
        isClickCICD();//销毁广播
      });
     var changeHeader = $rootScope.$on("changeHeaderAppSuccess", function (event, data) {//监听应用变化
       function queryListAfter () {
         if (data === "ok") {
           //重新获取流水列表响应数据
           overviewService.queryFlowNameList($scope.queryFlowNameListParams,
               function (res) {//请求成功
                 $scope.queryFlowNameListResData = JSON.parse(res.data);
                 //存入当前选择的第一个流水
                 window.sessionStorage.serialInfo = angular.toJson($scope.queryFlowNameListResData[0]||{});
                 CORE_VALUE.PROJECTINFO.FLOWINFO = $scope.queryFlowNameListResData[0];
                 if (res.resultCode === "200" && JSON.parse(res.data).length >= 1) {
                   $scope.queryParams = {flowId:$scope.queryFlowNameListResData[0].flowId};
                   $scope.flowName = $scope.queryFlowNameListResData[0].flowName;
                   $scope.flowType = $scope.queryFlowNameListResData[0].type || '0';
                   $scope.graphShow = true;
                   $scope.getData($scope.queryParams);//获取各图表数据
                 }else{
                   $scope.flowName = '流水列表';
                   $scope.graphShow = false;
                 }
               },
               function (res) {//请求失败
                 console.log("请求失败! " + window.location.href);
               }
           );
           $scope.USERINFOTYPE = CORE_VALUE.USERINFO;//获取用户类型（开发/测试）
           if($scope.USERINFOTYPE.operType =='2'){
             $('.graph').height(550)
           }else{
             $('.graph').height(800)
           };
           $scope.APPINFOTYPE = CORE_VALUE.PROJECTINFO.APPINFO;//获取应用类型
           if($scope.APPINFOTYPE.operType =='2'){
             $('.graph').height(550)
           }else{
             $('.graph').height(800)
           }
         }
       }
       COREDATA.setUserInfo(queryListAfter);//查询用户信息
      });
      $scope.$on('$destroy',function(){
        changeHeader();//销毁广播
      });
      //判断分支是否显示
      $('#select').change(function(){
        $scope.optionVal = $(this).val();
      });
      // 改应用下是否有流水列表
       $scope.hasFlow = true;
      //获取流水列表响应数据
      overviewService.queryFlowNameList($scope.queryFlowNameListParams,
          function (res) {//请求成功
            if (res.resultCode === "200" && res.data.length >= 1) {
              $scope.queryFlowNameListResData = JSON.parse(res.data);
              $scope.hasFlow = ($scope.queryFlowNameListResData) ? true : false;
              window.sessionStorage.serialInfo = angular.toJson($scope.queryFlowNameListResData[0]||{});
              CORE_VALUE.PROJECTINFO.FLOWINFO = $scope.queryFlowNameListResData[0];
              var firstData = ($scope.queryFlowNameListResData.length > 0) ? {flowId:$scope.queryFlowNameListResData[0].flowId} : {};
              $scope.flowName = ($scope.queryFlowNameListResData.length > 0) ? $scope.queryFlowNameListResData[0].flowName : '';
              $scope.flowType = ($scope.queryFlowNameListResData.length > 0) ? $scope.queryFlowNameListResData[0].type : '0';
              $scope.getData(firstData);//获取第一条流水各图表数据
            }
          },
          function (res) {//请求失败
            console.log("请求失败! " + window.location.href);
          }
      );
      //从流水列表里面选择具体的流水的时候,相应的流水的名字就会替换"流水列表"
      $scope.changeFlowName = function (event, resData) {
        $scope.graphShow = true;//选择流水后显示图表
        $scope.flowName = resData.flowName;
        $scope.flowType = resData.type;
        $scope.queryParams = {flowId: resData.flowId};
        $scope.getData($scope.queryParams);//获取各图表数据
        //存入当前选择的流水
        window.sessionStorage.serialInfo = angular.toJson(resData||{});
        CORE_VALUE.PROJECTINFO.FLOWINFO = resData;
      };
      //获取图表数据
      $scope.getData = function(item){
        overviewService.queryBuildList(item,//构建
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1) {
                $scope.buildResData = JSON.parse(res.data);
                //获取最后一天构建情况
                if ($scope.buildResData) {
                  var $build = $scope.buildResData.buildsPerDayEntities;
                  var $len = $build.length;
                  $scope.buildTimes = $build[$len - 1].latestBuilds;
                } else {
                  $scope.buildTimes = [];
                };
                generateBuildChart($scope.buildResData);
              }else{
                $scope.buildResData = null;
                $scope.buildTimes = [];
                generateBuildChart($scope.buildResData);
              }
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
        overviewService.queryQualityAnalysis(item,//质量分析
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1&&$scope.flowType!=2) {
                $scope.qualityResData = JSON.parse(res.data);
                generateQualityChart($scope.qualityResData);
              }
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
        overviewService.queryDeployList(item,//部署
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1) {
                $scope.deployResData.data = JSON.parse(res.data);
              }
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
        overviewService.queryAutomateTestList(item,//自动化测试
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1) {
                $scope.automateTestResData = JSON.parse(res.data);
                if(!$scope.automateTestResData||$scope.automateTestResData.length==0){
                 // $scope.automateTestResData = [];
                  $('#automateTest').hide();
                  $('.automate-test').show();
                }else{
               $('.automate-test').hide();
               $('#automateTest').show();
                  generateAutomateTestChart($scope.automateTestResData);
                };
              }
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
        overviewService.queryIntegrationTestList(item,//集成测试
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1) {
                $scope.integrationTestResData = JSON.parse(res.data);
                if(!$scope.integrationTestResData||$scope.integrationTestResData.length==0){
                  //$scope.integrationTestResData = []
                  $('#integrationTest').hide();
                  $('.integration-test').show();
                }else{
                  $('.integration-test').hide();
                  $('#integrationTest').show();
                  generateIntegrationTestChart($scope.integrationTestResData);
                };
              }
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
        //获取代码仓库数据
        overviewService.queryCodeRepoList(item,
            function (res) {//请求成功
              if (res.resultCode === "200" && res.data.length >= 1&&$scope.flowType!=2) {
                $scope.codeRepoListResData = JSON.parse(res.data);
                if(!$scope.codeRepoListResData){
                  $scope.pageCode.url = '';
                  $scope.pageCode.svnSelect = 0;
                  $scope.pageCode.gitSelect = 0;
                  $scope.codeRepoResData = [];
                  commitTimes($scope.codeRepoResData);//计算总提交与总构建次数
                  generateCodeRepoListChart($scope.codeRepoResData)//生成提交次数图表
                  return false;
                }else if($scope.codeRepoListResData[0].scm = '1'){
                  $scope.pageCode.url = $scope.codeRepoListResData[0].url;
                  $scope.pageCode.type = '1';
                  $scope.optionVal = '1';
                  $scope.pageCode.svnSelect = 1;
                  $scope.pageCode.gitSelect = 0;
                }else if($scope.codeRepoListResData[0].scm = '0'){
                  $scope.pageCode.url = $scope.codeRepoListResData[0].url;
                  $scope.pageCode.type = '0';
                  $scope.optionVal = '0';
                  $scope.pageCode.svnSelect = 0;
                  $scope.pageCode.gitSelect = 1;
                };
                overviewService.queryCodeRepo({
                      url: $scope.codeRepoListResData[0].url,
                      type: $scope.codeRepoListResData[0].scm
                    },
                    function (res) {//请求成功
                      if (res.resultCode === "200" && res.data.length >= 1) {
                        $scope.codeRepoResData = JSON.parse(res.data);
                        if(!$scope.codeRepoResData){
                          $scope.codeRepoResData = [];
                        };
                        commitTimes($scope.codeRepoResData);//计算总提交与总构建次数
                        generateCodeRepoListChart($scope.codeRepoResData)//生成提交次数图表
                      }
                      ;
                    },
                    function (res) {//请求失败
                      console.log("请求失败! " + window.location.href);
                    }
                );
              };
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
      };
      //生成构建次数图表
      function generateBuildChart(resData) {
          //基于准备好的dom，初始化echarts实例
          var buildNumChart = echarts.init(document.getElementById('buildNum'));
          var buildTimeChart = echarts.init(document.getElementById('buildTime'));
        buildNumChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
        buildTimeChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
          //改变数据的格式，适合图表显示
        if(resData){
         var tmpData = {
            flowSuccNumData: [],//成功次数
            flowFailNumData: [],//失败次数
            flowbuildDateData: [],//构建日期
            flowAvgTimeDateData: [],//平均构建时间
            flowAvgbuildDateData: [],//平均构建日期
          };
          for (var i = 0; i < resData.buildsPerDayEntities.length; i++) {
            tmpData.flowSuccNumData.push(resData.buildsPerDayEntities[i].successCount);
            tmpData.flowFailNumData.push(resData.buildsPerDayEntities[i].failuresCount);
            tmpData.flowbuildDateData.push(resData.buildsPerDayEntities[i].buildDate);
          }
          ;
          for (var i = 0; i < resData.avgBuilds.length; i++) {
            tmpData.flowAvgTimeDateData.push(resData.avgBuilds[i].avgBuildTime);
            tmpData.flowAvgbuildDateData.push(resData.avgBuilds[i].buildDate);
          };
        }else {
          var $date = $filter('date')(new Date(), 'MM-dd');
          var tmpData = {
            flowSuccNumData: [0],//成功次数
            flowFailNumData: [0],//失败次数
            flowbuildDateData: [$date],//构建日期
            flowAvgTimeDateData: [0],//平均构建时间
            flowAvgbuildDateData: [$date],//平均构建日期
          };
        };
          //指定图表的配置项和构建次数数据
          var buildNumOption = {
            title: {
              subtext: '每天构建次数',
              subtextStyle: {
                fontSize: 12,
                align: 'center',
                color: '#b0b0b0'
              }
            },
            grid:{
              left:'10%'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'line'// 默认为直线，可选为：'line' | 'shadow'
              }
            },
            legend: {
              data: ['成功次数', '失败次数'],
              right: 30,
              top: 10,
            },
            calculable: true,
            xAxis: [
              {
                type: 'category',
                data: tmpData.flowbuildDateData,
                axisLabel: {
                  interval: 0,
                  rotate: 45
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: '成功次数',
                type: 'line',
                data: tmpData.flowSuccNumData
              },
              {
                name: '失败次数',
                type: 'line',
                data: tmpData.flowFailNumData
              }
            ],
            dataZoom: [
              {textStyle: {color: '#8392A5'}}, {type: 'inside'}
            ]
          };
          //指定图表的配置项和构建时间数据
          var buildTimeOption = {
            color: ['#3398DB'],
            title: {
              subtext: '平均构建时间(s)',
              subtextStyle: {
                fontSize: 12,
                align: 'center',
                color: '#b0b0b0'
              }
            },
            grid:{
              left:'20%'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'// 默认为直线，可选为：'line' | 'shadow'
              }
            },
            calculable: true,
            xAxis: [
              {
                type: 'category',
                data: tmpData.flowAvgbuildDateData,
                axisLabel: {
                  interval: 0,
                  rotate: 45
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: '时间',
                type: 'bar',
                data: tmpData.flowAvgTimeDateData
              },
            ],
            dataZoom: [
              {textStyle: {color: '#8392A5'}}, {type: 'inside'}
            ]
          };
          //使用刚指定的配置项和数据显示图表。
        setTimeout(function (){
          buildNumChart.hideLoading();
        },500);
        setTimeout(function (){
          buildTimeChart.hideLoading();
        },500);
          buildNumChart.setOption(buildNumOption);
          buildTimeChart.setOption(buildTimeOption);
          window.onresize = function(){
            buildNumChart.resize();
            buildTimeChart.resize()
          };
      }

      //生成质量分析图表
      function generateQualityChart(resData) {
        //基于准备好的dom，初始化echarts实例
        var staticsCodeChart = echarts.init(document.getElementById('staticsCode'));
        var unitTestChart = echarts.init(document.getElementById('unitTest'));
        var CovRateChart = echarts.init(document.getElementById('CovRate'));
        if(!resData.uniLineCovRate){
          resData.uniLineCovRate = 0;
        };
        staticsCodeChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
        unitTestChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
        CovRateChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
        //配置图表数据-静态代码分析
        var staticsCodeOption = {
          title: {
            text: '静态代码分析',
            top: 20,
            left:15,
            textStyle: {
              fontSize: 12,
              align: 'center',
              color: '#b0b0b0'
            },
          },
          legend: {
            right: 0
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            axisLabel: {
              interval: 0,
              rotate: 50
            },
          },
          yAxis: {
            type: 'category',
            data: ['Issues', 'Major', 'Critical', 'blocker']
          },
          series: [
            {
              name: '数量',
              type: 'bar',
              data: [resData.staticAnalysis.checkIssues, resData.staticAnalysis.checkMajor, resData.staticAnalysis.checkCritical, resData.staticAnalysis.checkBlocker]
            },
          ]
        };
        //配置图表数据-静态代码分析
        var unitTestOption = {
          color: ['#3398DB'],
          title: {
            text: '单元测试分析',
            top: 20,
            left:15,
            textStyle: {
              fontSize: 12,
              align: 'center',
              color: '#b0b0b0'
            },
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            axisLabel: {
              interval: 0,
              rotate: 50
            },
          },
          yAxis: {
            type: 'category',
            data: ['Test', 'Error', 'Failures', 'Success']
          },
          series: [
            {
              name: '数量',
              type: 'bar',
              data: [resData.unitTests.uniTotal, resData.unitTests.uniErrNum, resData.unitTests.uniFaiNumber, resData.unitTests.uniSucNumber]
            },
          ]
        };
        //配置图表数据-代码行覆盖率
        var CovRateOption = {
          tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
          },
          title: {
            text: '总代码行覆盖率',
            top: 20,
            textStyle: {
              fontSize: 12,
              align: 'center',
              color: '#b0b0b0'
            },
          },
          series: [
            {
              name: '覆盖率',
              center:['50%','60%'],
              type: 'gauge',
              axisLine: {
                lineStyle: {
                  width: 20 // 这个是修改表盘宽度的属性
                }
              },
              pointer:{
                show:true, //指针是否显示
                width:3,    //指针宽度
                length:'70%'   //长度
              },
              itemStyle:{
                normal:{
                  color:'green'
                }
              },
                  detail: {
                formatter: '{value}%',
                offsetCenter: [0, '80%'],
                textStyle: {
                  fontSize: 16,
                }
              },
              data: [{value: resData.uniLineCovRate*100, name: '',}]
            }
          ]
        };
        setTimeout(function (){
          staticsCodeChart.hideLoading();
        },1000);
        setTimeout(function (){
          unitTestChart.hideLoading();
        },1000);
        setTimeout(function (){
          CovRateChart.hideLoading();
        },1000);
        staticsCodeChart.setOption(staticsCodeOption);
        unitTestChart.setOption(unitTestOption);
        CovRateChart.setOption(CovRateOption);
        window.onresize = function(){
          staticsCodeChart.resize();
          unitTestChart.resize();
          CovRateChart.resize();
        };
      }

      //部署标题
      $scope.deployResData.title = ['状态', '镜像版本', '镜像名称', '时间', '总部署次数'];

      //生成自动化测试图表
      function generateAutomateTestChart(resData) {
        //基于准备好的dom，初始化echarts实例
        var automateTestChartDiv = document.getElementById('automateTest');
        var automateTestChart = echarts.init(automateTestChartDiv);
        //改变数据的格式，适合图表显示
        var tmpData = {
          flowIdData: [],
          flowNameData: [],
          flowSeriousProblemNumberData: [],
          flowCommonProblemNumberData: []
        };
        for (var i = 0; i < resData.length; i++) {
          tmpData.flowIdData.push(resData[i].flowId);
          tmpData.flowNameData.push(resData[i].flowName);
          tmpData.flowSeriousProblemNumberData.push(resData[i].flowSeriousProblemNumber);
          tmpData.flowCommonProblemNumberData.push(resData[i].flowCommonProblemNumber);
        }
        //指定图表的配置项和数据
        var automateTestOption = {
          title: {
            subtext: '问题次数',
            subtextStyle: {
              fontSize: 12,
              align: 'center',
              color: '#b0b0b0'
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['用例数', '缺陷数'],
            top:10,
            right: 0,
            textStyle: {
              color: '#b0b0b0',
              fontSize: 12
            },
            height: automateTestChartDiv.style.height,
            width: automateTestChartDiv.style.width
          },
          calculable: true,
          xAxis: [
            {
              type: 'category',
              data: tmpData.flowNameData,
              axisLabel: {
                interval: 0,
                rotate: 45
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '用例数',
              type: 'bar',
              data: tmpData.flowSeriousProblemNumberData
            },
            {
              name: '缺陷数',
              type: 'bar',
              data: tmpData.flowCommonProblemNumberData
            }
          ],
          dataZoom: [
            {textStyle: {color: '#8392A5'}}, {type: 'inside'}
          ]
        };
        //使用刚指定的配置项和数据显示图表。
        automateTestChart.setOption(automateTestOption);
        window.resize = function(){
       automateTestChartDiv = document.getElementById('automateTest');
          automateTestChart.resize()
        }
      }

      //生成集成测试图表
      function generateIntegrationTestChart(resData) {
        //基于准备好的dom，初始化echarts实例
        var integrationTestChartDiv = document.getElementById('integrationTest');
        var integrationTestChart = echarts.init(integrationTestChartDiv);
        //改变数据的格式，适合图表显示
        var tmpData = {
          flowIdData: [],
          flowNameData: [],
          flowSeriousProblemNumberData: [],
          flowCommonProblemNumberData: []
        };
        for (var i = 0; i < resData.length; i++) {
          tmpData.flowIdData.push(resData[i].flowId);
          tmpData.flowNameData.push(resData[i].flowName);
          tmpData.flowSeriousProblemNumberData.push(resData[i].flowSeriousProblemNumber);
          tmpData.flowCommonProblemNumberData.push(resData[i].flowCommonProblemNumber);
        }
        //指定图表的配置项和数据
        var integrationTestOption = {
          title: {
            subtext: '问题次数',
            subtextStyle: {
              fontSize: 12,
              align: 'center',
              color: '#b0b0b0'
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['用例数', '缺陷数'],
            top:10,
            right:0,
            textStyle: {
              color: '#b0b0b0',
              fontSize: 12
            },
            height: integrationTestChartDiv.style.height,
            width: integrationTestChartDiv.style.width
          },
          calculable: true,
          xAxis: [
            {
              type: 'category',
              data: tmpData.flowNameData,
              axisLabel: {
                interval: 0,
                rotate: 45
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '用例数',
              type: 'bar',
              data: tmpData.flowSeriousProblemNumberData
            },
            {
              name: '缺陷数',
              type: 'bar',
              data: tmpData.flowCommonProblemNumberData
            }
          ],
          dataZoom: [
            {textStyle: {color: '#8392A5'}}, {type: 'inside'}
          ]
        };
        //使用刚指定的配置项和数据显示图表。
        integrationTestChart.setOption(integrationTestOption);
        window.resize = function(){
       integrationTestChartDiv = document.getElementById('integrationTest');
          integrationTestChart.resize()
        }
      }

      //点击代码仓库保存
      $scope.saveCodeData = function (item) {
        var $url = /^((https|http)?:\/\/)[^\s]+(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)/;
        /*if(!$url.test(item.url)){
          COREDATA.resMessage("请正确填写代码库地址");
          item.url = '';
          return false;
        };*/
        if(item.type=='0'&&item.branch==''){
          COREDATA.resMessage("分支不可为空");
          return false;
        };
        if($url.test(item.branch)){
          COREDATA.resMessage("分支不正确");
          item.branch = '';
          return false;
        };
        overviewService.queryCodeRepo({url: item.url, type: item.type},
            function (res) {//请求成功
              if (res.resultCode === "200") {
                $('#cancelSave').click();
                COREDATA.resMessage(res.resultCode,res.resultMessage);
                $scope.codeRepoResData = JSON.parse(res.data);
                commitTimes($scope.codeRepoResData);//计算总提交与总构建次数
                generateCodeRepoListChart($scope.codeRepoResData);//生成提交次数图表
              }else{
                COREDATA.resMessage("请正确填写所有信息项");
                item.url = '';
              };
            },
            function (res) {//请求失败
              console.log("请求失败! " + window.location.href);
            }
        );
      };
      //计算总提交与总构建次数
      function commitTimes(data) {
        var today = toMidnight(new Date());
        var sevenDays = toMidnight(new Date());
        var fourteenDays = toMidnight(new Date());
        sevenDays.setDate(sevenDays.getDate() - 7);
        fourteenDays.setDate(fourteenDays.getDate() - 14);

        var lastDayCommitCount = 0;
        var lastDayCommitContributors = [];

        var lastSevenDayCommitCount = 0;
        var lastSevenDaysCommitContributors = [];

        var lastFourteenDayCommitCount = 0;
        var lastFourteenDaysCommitContributors = [];

        function toMidnight(date) {
          date.setHours(0, 0, 0, 0);
          return date;
        }

        // loop through and add to counts
        _(data).forEach(function (commit) {

          if (commit.scmCommitTimestamp >= today.getTime()) {
            lastDayCommitCount++;

            if (lastDayCommitContributors.indexOf(commit.scmAuthor) == -1) {
              lastDayCommitContributors.push(commit.scmAuthor);
            }
          }

          if (commit.scmCommitTimestamp >= sevenDays.getTime()) {
            lastSevenDayCommitCount++;

            if (lastSevenDaysCommitContributors.indexOf(commit.scmAuthor) == -1) {
              lastSevenDaysCommitContributors.push(commit.scmAuthor);
            }
          }

          if (commit.scmCommitTimestamp >= fourteenDays.getTime()) {
            lastFourteenDayCommitCount++;
            ctrl.commits.push(commit);
            if (lastFourteenDaysCommitContributors.indexOf(commit.scmAuthor) == -1) {
              lastFourteenDaysCommitContributors.push(commit.scmAuthor);
            }
          }
        });
        $scope.lastDayCommitCount = lastDayCommitCount;
        $scope.lastDayCommitContributorCount = lastDayCommitContributors.length;
        $scope.lastSevenDaysCommitCount = lastSevenDayCommitCount;
        $scope.lastSevenDaysCommitContributorCount = lastSevenDaysCommitContributors.length;
        $scope.lastFourteenDaysCommitCount = lastFourteenDayCommitCount;
        $scope.lastFourteenDaysCommitContributorCount = lastFourteenDaysCommitContributors.length;
      }

      //生成代码仓库图表
      function generateCodeRepoListChart(resData) {
        //基于准备好的dom，初始化echarts实例
        var codeRepoChartDiv = document.getElementById('codeRepo');
        var codeRepoChart = echarts.init(codeRepoChartDiv);
        codeRepoChart.showLoading({
          text : '数据获取中',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(122, 122, 122, 0.8)',
          zlevel: 0
          //effect: 'whirling'
        });
        //改变数据的格式，适合图表显示

        var dateArr = [];
        var numArr = [];
        var commitArr = [];
        for (var i = 14; i >= 0; i--) {
          dateArr.push($filter('date')(new Date().getTime() - 86400000 * i, 'MM-dd'));
          numArr.push(0);
          commitArr.push([]);
        }
        for (var par1 in resData) {
          var tempObj = $filter('date')(new Date(resData[par1].scmCommitTimestamp - 0), 'MM-dd');
          for (var par2 in dateArr) {
            if (dateArr[par2] === tempObj) {
              numArr[par2]++;
              resData[par1].stmapTime = $filter('date')(new Date(resData[par1].scmCommitTimestamp - 0), 'MM-dd,HH:mm:ss');
              commitArr[par2].push(resData[par1]);
            }
          }
        }
        ;
        //指定图表的配置项和数据
        var codeRepoOption = {
          title: {
            top: 20,
            text: '每天提交次数',
            textStyle: {
              fontSize: 12,
              color: '#b0b0b0'
            },
          },
          legend: {
            height: codeRepoChartDiv.style.height,
            width: codeRepoChartDiv.style.width
          },
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            left: '3%',
            right: '10%',
            bottom: '10%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: dateArr,
            axisLabel: {
              interval: 0,
              rotate: 50
            },
          },
          yAxis: {
            type: 'value',
          },
          series: [
            {
              name: '提交次数',
              type: 'line',
              data: numArr,
            },
          ]
        };
        //使用刚指定的配置项和数据显示图表。
        setTimeout(function (){
          codeRepoChart.hideLoading();
          },1500);
        codeRepoChart.setOption(codeRepoOption);
        window.resize = function(){
       codeRepoChartDiv = document.getElementById('codeRepo');
          codeRepoChart.resize();
        };
        codeRepoChart.on('click', function (params) {
          $("#codeRepoLog1").dialog({
            width : 1000,   //弹出框宽度
            height :500,   //弹出框高度
            modal : true    // 设置为模态对话框
          });
          $timeout(function () {
            $scope.detail = commitArr[params.dataIndex];
          }, 1);
          $("#codeRepoLog1").parent().css("top","100px");
          $("#codeRepoLog1").parent().css("left","250px");
        })
      }
      function whole(a,b){
        var whint = parseInt(a) + parseInt(b);
        return whint;
     };
     function whole1(a,b,c,d,e){
        var whint = parseInt(a) + parseInt(b) + parseInt(c) + parseInt(d) + parseInt(e);
        return whint;
     };
     function whole2(a,b,c,d){
        var whint = parseInt(a) + parseInt(b) + parseInt(c) + parseInt(d);
        return whint;
     };
  });
})();



