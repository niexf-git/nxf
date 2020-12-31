(function () {
  'use strict';
  angular.module('ui.bootstrap.modal', ['PaaS5.filters'])

    .directive('createSerialModal', function ($http, CORE_VALUE, COREDATA, toastr) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serial/createSerial_modal.html',
        scope: {
          init: '&'
        },
        replace: false,
        link: function (scope, element, attrs) {
          scope.svnUrlPrefix = CORE_VALUE.svnUrlPrefix;
          scope.canClick = CORE_VALUE;

          var params = { flowId: 1 },
            flowArr = [];
          // 十个可选卡片步骤信息
          var _init = function () {
            scope.steps = [
              {
                stepName: 'downloadcheck',//下载审查
                isChoise: 1,
                description: '下载源码并对源码进行代码质量检查'
              }, {
                stepName: 'build',//编译构建
                isChoise: 1,
                description: '将程序源码编译打包并生成镜像文件'
              }, {
                stepName: 'depscan',//部署扫描
                isChoise: 0,
                description: '将镜像部署到服务器并进行安全扫描'
              }, {
                stepName: 'autotest',//自动化测试
                isChoise: 0,
                description: '以人为驱动的测试行为转化为机器执行的一种过程'
              }, {
                stepName: 'intetest',//集成测试
                isChoise: 0,
                description: '将不同服务组合起来进行测试'
              },
              {
                stepName: 'perftest', //性能测试
                isChoise: 0,
                description: '模拟多种正常、峰值以及异常负载条件来对系统的各项性能指标进行测试'
              },
              {
                stepName: 'release',//发布
                isChoise: 0,
                description: '将镜像发布给测试或者生产'
              }

              // },{
              // 	stepName: 'devrelease',//发布测试
              // 	isChoise: 0
              // },{
              // 	stepName: 'testautotest',//自动化测试
              // 	isChoise: 0
              // },{
              // 	stepName: 'testintetest',//集成测试
              // 	isChoise: 0
              // },{
              // 	stepName: 'deploy',//部署
              // 	isChoise: 0
              // },{
              // 	stepName: 'testrelease',//测试
              // 	isChoise: 0
              // }
            ];
            scope.serial = { name: '', description: '' };
          }
          _init();
          scope.initModal = function () { _init(); scope.createSerialForm.$setPristine(); };

          scope.createSerial = function (str) {
            if (str) {
              toastr.warning(str);
              return;
            }
            flowArr = [];
            var checkbox = $('input[name="flowSteps"]');
            for (var a = 0; a < scope.steps.length; a++) {
              console.log(scope.steps[a])
              if (scope.steps[a].isChoise === 1) {
                flowArr.push(scope.steps[a]);
              }
            }

            var obj = {
              flowName: scope.serial.name,
              flowDescription: scope.serial.description,
              stepList: flowArr
            };
            var t = { "flow": obj };

            $http({
              method: 'POST',
              url: '/paas/flow/createFlow.action',
              data: t,
              headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (res) {
              if (res.resultCode !== '200') {
                COREDATA.resMessage(res.resultCode, res.resultMessage);
                return;
              }
              $('#createSerialModalCancel').click();
              scope.serial = { name: '', description: '' };
              for (var i = 2; i < scope.steps.length; i++) {
                scope.steps[i] = 0;
              };
              scope.init();
              _init();
            });



          };
        }
      };
    })


    .directive('common', function () {
      return {
        restrict: 'A',
        controller: function ($scope, $element) {

        },
        link: function (scope, element, attrs) {
          $(element).click(function () {
            $("body").attr("style", "padding-right: 10px; overflow: hidden;");
            $("#executionRecordPanel .modal-mask").removeClass("modal-mask-hidden");
            $("#executionRecordPanel .flowBuildLogModal").show();
          })
        }

      }
    })


		/**
		 * 执行记录模态框 Directive
		 * @param  {Object} $http       Angular封装发送Ajax请求
		 * @param  {Object} CORE_VALUE) CICD 封装常量对象
		 * @param  {function} link:     自定义指令链接函数
		 * @return {none}             	not return
		 *
		 * example:
		 * 	In HTML:
		 * 		<!-- 3.执行技术模态框 -->
				<record-modal></record-modal>
		
			In JavaScript:
				In Controller:
					var vm = this;
					vm.showExecutionRecordPanel = function(serial) {
						vm.flowInfo = serial;
						// 广播事件，让自定义指令接收到信号及参数
						$scope.$broadcast('showExecutionRecordPanel', serial);
						// 展示流水执行记录框
						$("body").attr("style", "padding-right: 10px; overflow: hidden;");
						$("div[name='chenzhe'] .modal-mask").removeClass("modal-mask-hidden");
						$("div[name='chenzhe'] .flowBuildLogModal").show();
					}
		 *
		 */
    .directive('recordModal', function ($http, CORE_VALUE, $interval) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'scripts/directive/record_modal.html',
        scope: {
          attribute: '@',
          // info: '@',
        },
        // require: '^ngModel',
        // controller: function($scope) {
        // 	$scope.recordList = [{status: 3}, {status: 4}]
        // },
        link: function (scope, element, attrs) {
          var instance = angular.element(document.getElementById('CICDCtrl')).scope();

          // 流水信息
          var serialInfo,
            time = 1,
            preSerialInfo = {};
          // 执行记录列表
          scope.recordList = [];
          scope.cardsList = [];


          scope.statusColor = function (status) {
            var obj = { color: '' }
            if (status === 0) {
              obj.color = '#333';
            } else if (status === 1) {
              obj.color = '#3fc000';
            } else if (status === 2) {
              obj.color = '#e4342f';
            } else if (status === 3) {
              obj.color = '#02a0da';
            } else if (status === 4) {
              obj.color = '#ccc';
            }
            return obj;
          };

          // 监听 showExecutionRecordPanel 方法在作用域上的广播
          scope.$on('showExecutionRecordPanel', function (event, data) {
            console.log('change........')
            serialInfo = angular.copy(data);
            scope.recordList = [];
            scope.timer = $interval(function () {
              queryRecord(serialInfo);
            }, 3000);
            queryRecord(data);
          });

          function findOpenDeatil() {
            var divArr = $("div[name='record-detail-cz']");
            for (var i = 0; i < divArr.length; i++) {
              if ($(divArr[i]).hasClass('collapse-content-active')) {
                var stepInfo = JSON.parse($(divArr[i]).attr('info'));
                queryStep(stepInfo);
              }
            }
          }


					/**
					 * 查询报告列表
					 * @param  {obj} data 当前流水信息
					 * @return {[type]}      [description]
					 */
          var queryRecord = function (data) {
            // scope.recordList = [];
            var params = { flowId: data.flowId, flowRecordId: data.flowRecordId || '' };
            if (params.flowRecordId === '')
              return;
            $http({
              method: 'GET',
              url: '/paas/excuteRecord/queryFlowExcuteRecord.action',
              params: params,
              headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (res) {
              var data = JSON.parse(res.data);
              scope.recordList = JSON.parse(res.data);
              if (time === 1) {
                scope.cardsList = JSON.parse(res.data)
              } else {
                var length = scope.cardsList.length;
                scope.cardsList = scope.cardsList.concat(data.slice(length));
              }
              time = 2;
              findOpenDeatil();
            })
          }

					/**
					 * 点击展开步骤执行信息时， 走查询
					 * @param  {obj} step 点击的步骤对象
					 * @return {[type]}      [description]
					 */
          var queryStep = function (step) {
            var params = { flowId: serialInfo.flowId, stepId: step.flowRecordId, stepName: step.aliasName };
            // 为--3: 执行中状态，走webSocket
            if (step.status === 3) {
              wu(params)
            } else {
              stepRecord(params)
            }
          }

					/**
					 * 执行成功、执行失败
					 * @param  {[type]} params [description]
					 * @return {[type]}        [description]
					 */
          var stepRecord = function (params) {
            $http({
              method: 'GET',
              url: '/paas/excuteRecord/queryBuildLogs.action',
              params: params,
              headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            }).success(function (res) {

              for (var i = 0; i < scope.cardsList.length; i++) {
                console.log(scope.cardsList[i].aliasName)

                if (scope.cardsList[i].aliasName === params.stepName) {
                  scope.cardsList[i].info = res.resultMessage;
                  console.log('日志长度：' + scope.cardsList[i].info.length);
                }

              }
            })
          }

					/**
					 * 执行中。。。长连接（暂不用）
					 * @param  {[type]} params [description]
					 * @return {[type]}        [description]
					 */
          var wsStepRecord = function (params) {
            // var params = {flowId: , recorcId: , stepId: }
            var params = { flowId: '6666dhh', stepId: '212dhh', stepName: 'app1_dhh_depscan' }
            var ws = new WebSocket("ws://" + window.location.host + "/paas/websocket/buildLog");

            ws.onopen = function () {
              ws.send(params.flowId + '&' + params.stepId + '&' + params.stepName);
            }

            ws.onmessage = function (evt) {
              console.log("Received Message: " + evt.data);
              ws.close();
            };

            // Listen for messages
            ws.addEventListener('message', function (event) {
              console.log('Message from server ', event.data);
              var result = JSON.parse(event.data);
              console.log(event)
              // 成功
              if (result['resultCode'].indexOf('PAAS-00') > -1) {
                // 对数据模型做操作
              } else {
                // 失败，则关闭webSocket
                ws.close();
              }
            });
          }

					/**
					 * 执行中调用长连接现行方法
					 * @param  {[type]} params [description]
					 * @return {[type]}        [description]
					 */
          var wu = function (params) {
            var socket;
            if (window.WebSocket) {
              if (socket != undefined && soket.readState == 1) { socket.close() }

              socket = new WebSocket('ws://' + window.location.host + '/paas/websocket/stepLog');

              socket.onmessage = function (event) {
                var result = JSON.parse(event.data);
                if (result.resultCode != "0") {
                  for (var i = 0; i < scope.cardsList.length; i++) {
                    if (scope.cardsList[i].aliasName === params.stepName) {
                      var name = scope.cardsList[i].aliasName;
                      scope.cardsList[i].info += result.resultMessage;
                      scope.$apply();
                      $('div[name=' + name + ']').scrollTop($('div[name=' + name + ']')[0].scrollHeight);

                    }
                  }
                } else if (result['resultCode'] == '0') {
                  //           	socket.close();
                } else {
                  scoket.close();
                  //parent.parent.alertError(result['resultCode']，result['resultMessage'])
                }
              }
              socket.onopen = function () {
                console.log('长连接打开')
              }
              socket.onclose = function () {
                console.log('长连接关闭')
                queryRecord(serialInfo)
              }
              socket.onerror = function () {
                alert('长连接错误')
              }
              setTimeout(function () {
                socket.send(params.flowId + '&' + params.stepId + '&' + params.stepName);
                // socket.send('6666dhh&212dhh&app1_dhh_depscan')
              }, 200)

            } else {
              alert('您的浏览器不支持 webSocket 长连接')
            }
          }





          //改变执行记录框大小
          scope.changeExecutionRecordPanel = function () {
            var isSmallModal = $("div#StageBuildLog").attr("class") == "smallModal";
            if (isSmallModal) {
              $("div#StageBuildLog").attr("class", "bigModal");
            } else {
              $("div#StageBuildLog").attr("class", "smallModal");
            }
          }
          var closeAll = function () {
            var activeDivList = $("div[name='chenzhe'] .collapse-header");
            activeDivList.each(function () {
              $(this).attr("aria-expanded", "false");
              $(this).next().removeClass("collapse-content-active");
              $(this).next().addClass("collapse-content-inactive");
              $(this).children(":first").removeClass("fa-sort-asc");
              $(this).children(":first").addClass("fa-sort-desc");
            })
          };
          //展开或者折叠内容
          scope.openOrFoldContent = function (step, event) {
            var isOpen = $(event.currentTarget).attr("aria-expanded") == "true";
            closeAll()
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
            }
            // 调用查询方法
            queryStep(step);
          };


          //关闭执行记录框
          scope.closeExecutionRecordPanel = function () {
            $interval.cancel(scope.timer);
            $("body").attr("style", "overflow-y:scroll;");
            preSerialInfo = {};
            time = 1;
            $("div[name='chenzhe'] .flowBuildLogModal").hide();
            $("div[name='chenzhe'] .modal-mask").addClass("modal-mask-hidden");
          };

        }
      };
    })
})();