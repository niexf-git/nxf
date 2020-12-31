/**
 * CICD 流水列表界面控制器
 * 陈哲  2017-09-04
 * @return {[type]} [description]
 *
 * 关键性逻辑：
 *  1. 查询函数会根据是否存在“执行中”的流水，来创建定时器，每次查询，会将此次查询的参数保存在 vm.querySerial.timerParams
 *  中， 用于定时器的查询参数（防止用户更改输入框字符时，将输入框字符用户定时器查询）
 * 	2. 点击立即构建 -> 2S后查询列表 -> 若存在“执行中”流水，则添加以流水ID为后缀的定时器（每10S查询一次）
 * 	-> 直至流水列表不存在“执行中”的流水
 *
 *
 * 流水状态值： 0：未执行；1：成功；2：失败；3：构建中；
 * 部署（deploy）： 1.自动部署；3.灰度部署；
 * 用户角色： ‘1’.开发；‘2’.测试； ‘1，2’.admin
 *
 */
(function () {
  angular.module('PaaS5.controllers')
    .controller('serialController', function ($scope, serialServer, toastr, CORE_VALUE, COREDATA, $rootScope, $interval, $timeout) {
      var vm = this;
      vm.flowInfo;
      vm.appInfo;

      function showLoad() {
        $('#cicdLoadImg').css('display', 'block');
      }
      function hideLoad() {
        $('#cicdLoadImg').css('display', 'none');
      }
      
			/**
			 * 页面初始化函数；
			 * 1.vm.querySerial.model：将列表查询参数重置；
			 * 2.vm.queryUser.fn：执行当前登录人员信息查询；
			 * 3.vm.querySerial.fn：执行流水列表查询；
			 * @return {[type]} [description]
			 */
      vm.init = function (first) {
        if(first){
          vm.querySerial.model = {
            searchKey: '',
            page: 1,
            rows: 10,
            sidx: 'updateTime',
            sord: 'desc'
          };
        }
        vm.queryUser.fn();
        vm.querySerial.fn();
      };

      // 流水列表展示对象
      vm.showModel = {
        serialList: [],
        totalCount: 0
      };

			/**
			 * 查询登录用户信息模块
			 * 根据用户信息，修改模态框中可选步骤的步骤数组
			 */
      vm.queryUser = {
        model: {},
        fn: function () {
          serialServer.queryUser(function (res) {
            if (res.resultCode !== '200') {
              COREDATA.resMessage(res.resultMessage);
              return;
            }
            var data = JSON.parse(res.data);
            CORE_VALUE.USERINFO = data;
            // role: 0(管理员)， 1（普通用户）
            // operType: 1(开发), 2(测试)
            vm.queryUser.model.role = data.role;
            if (data.operType) {
              vm.queryUser.model.operType = data.operType;
              // 如果为测试人员，step数组更换
              if (vm.queryUser.model.operType === '2') {
                vm.modifySerial.showSteps = [];
                vm.modifySerial.showSteps = vm.modifySerial.onlyTestStep;
              } else {
                vm.modifySerial.showSteps = vm.modifySerial.steps;
              }
            }

          })
        }
      };

			/**
			 * 模糊搜索模块
			 * 1.modal: 模糊分页查询所需参数（searchKey: 关键词；currentPage: 当前展示页；pageSize: 每页展示个数）
			 * 2.fn: 模糊查询方法
			 * 3.timerFn: 每次查询出来遍历数据，先清空所有定时器，如果存在“执行中”的流水，则添加定时器，定时查询，
			 * 			  直到当前展示列表中，没有“执行中”状态的流水
			 * @type {Object}
			 */
      $scope.timer;
      vm.querySerial = {
        // 当前流水排序, 引用其他数组，次数据空间不可做修改
        currentFlowSortById: [],
        // 用户存放“执行中”流水Id
        timerArr: [],
        // 临时保存上一次查询参数，用于定时器的查询
        timerParams: {},
        preModel: {
        
        },
        model: {
          searchKey: '',
          sidx: 'updateTime',//排序字段名
          sord: 'desc',//升序 （asc）或者 降序（desc）
          page: 1,
          rows: 10
        },
        // 查询参数处理函数
        handelParams: function(isClickSearch) {
          var keyPattern = /[^A-Za-z0-9\-\_\/\.]/g;
  
          // 升序、降序操作
          if (!vm.querySerial.model.sidx) {
            vm.querySerial.model.sidx = 'updateTime';
            vm.querySerial.model.sord = 'desc';
          }
          
          // 判断是否通过点击搜索按钮执行查询
          if (!isClickSearch) {
            vm.querySerial.model.searchKey = vm.querySerial.preModel.searchKey;
          }else {
            vm.querySerial.model.page = 1;
            vm.querySerial.preModel = JSON.parse(JSON.stringify(vm.querySerial.model))
          }
          
          // 正则规则匹配
          if(keyPattern.test(vm.querySerial.model.searchKey)){
            vm.querySerial.model.searchKey = vm.querySerial.model.searchKey.replace(keyPattern,'')
          }
        },
        fn: function (timerParams, isClickSearch) {
          console.log('查询流水')
          vm.querySerial.handelParams(isClickSearch);
          var params = timerParams || vm.querySerial.model;
          vm.querySerial.timerParams = params;
          
          serialServer.querySerial(params, function (res) {
            if (res.resultCode === '200') {
              var data = JSON.parse(res.data);
              vm.querySerial.timerFn(data.result);
              vm.showModel.serialList = data.result;
              vm.showModel.totalCount = Number(data.totalCount);
              vm.appInfo = CORE_VALUE.PROJECTINFO.APPINFO;
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }

          });
        },
        handleFlowData: function (data) {
          if (!(data instanceof Array)) return data;

          var arr = new Array(data.length);
          var self = this;

          data.forEach(function (ele) {
            var index = self.currentFlowSortById.indexOf(ele.flowId);
            arr[index] = ele;
          });
          return arr;
        },
        getSerialByIds: function (ids) {
          console.log('进入getSerilaByIds')
          if (!(ids instanceof Array)) throw new Error('ids参数 不是一个数组');
          var param = {};
          // 将数组拼接成字符串 '流水ID, 流水ID, ...';
          param.flowIds = ids.reduce(function (accumulator, currentValue, currentIndex, arr) {
            if (currentIndex === 1) return accumulator + "," + currentValue + ",";
            if (currentIndex === arr.length - 1) return accumulator + currentValue;
            return accumulator + currentValue + ",";
          });
          this.currentFlowSortById = param.flowIds.split(',');
          serialServer.getSerialByIds(param, function (res) {
            if (res.resultCode === '200') {
              var data = vm.querySerial.handleFlowData(JSON.parse(res.data));
              vm.querySerial.timerFn(data);
              vm.showModel.serialList = data;
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          });
        },
        timerFn: function (data) {
          console.log('进入timerFn')
          vm.querySerial.timerArr = [];
          if ($scope.timer) { $interval.cancel($scope.timer) };
          function findBuilding() {
            return data.find(function (ele) {
              return ele.buildStatus === '3';
            })
          }
          if (findBuilding()) {
            for (var i = 0; i < data.length; i++) {
              vm.querySerial.timerArr.push(data[i].flowId);
            }
            $scope.timer = $interval(function () {
              vm.querySerial.getSerialByIds(vm.querySerial.timerArr);
            }, 10000)
          }
        },
        serchKeyup: function (e) {
          var keycode = window.event ? e.keyCode : e.which;
          if (keycode == 13) {
            vm.querySerial.fn(null, true);
          }
        },
        sortByName: function (name) {//排序查询
          if (vm.querySerial.model.sidx === name) {
            vm.querySerial.model.sord = vm.querySerial.model.sord === 'asc' ? 'desc' : 'asc';
          } else {
            vm.querySerial.model.sidx = name;
            vm.querySerial.model.sord = 'desc';
          }
          vm.querySerial.fn();
        }
      };

			/**
			 * 立即构建模块
			 * @type {Object}
			 */

      vm.building = {
        fn: function (id) {
          var params = {
            flowId: id
          };
          serialServer.building(params, function (res) {
            if (res.resultCode === '200') {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
              $timeout(function () {
                vm.querySerial.fn(vm.querySerial.timerParams);
              }, 2000);
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          });
        }
      };

			/**
			 * 停止流水构建模块
			 */
      vm.stopBuild = {
        fn: function (id) {
          var params = {
            flowId: id
          };
          serialServer.stopBuild(params, function (res) {
            if (res.resultCode === '200') {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
              $timeout(function () {
                vm.querySerial.fn(vm.querySerial.timerParams);
              }, 2000);
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          });
        }
      };

			/**
			 * 删除流水模块
			 * 1.model: 要删除的流水信息;
			 * 2.fn: 删除流水函数(只需要传流水ID)
			 * @type {Object}
			 */
      vm.delSerial = {
        model: {},
        fn: function () {
          var params = {
            flowId: vm.delSerial.model.flowId
          };
          serialServer.delSerial(params, function (res) {
            if (res.resultCode === '200') {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }, function () {
            vm.querySerial.model.page = 1;
            vm.querySerial.model.rows = 10;
            vm.querySerial.model.searchKey = '';
            vm.querySerial.fn();
          });
        }
      };

			/**
			 * 复制流水
			 */
      vm.copySerial = {
        model: {},
        showSteps: [],
        querySerialDetail: function (operType) {
          vm.modifySerial.querySerialDetail.call(this, operType);
        },
        checkStep: function (data) {
          vm.modifySerial.checkStep.call(this, data);
        },
        fn: function () {
          var _this = this;
          this.model.stepList = [];
          for (var i = 0; i < this.showSteps.length; i++) {
            if (this.showSteps[i].isChoise == 1) {
              this.model.stepList.push(this.showSteps[i]);
            }
          }
          console.log(this.model);
          var p = {
            flowId: this.model.flowId,
            flowName: this.model.flowName,
            flowDescription: this.model.flowDescription,
            stepList: this.model.stepList,
            operType: this.model.operType + '',
            appId: this.model.appId + ''
          };
          var t = {
            "flow": p
          };
          showLoad();
          serialServer.copySerial(t, function (res) {
            hideLoad();
            if (res.resultCode === '200') {
              $('#copySerialModalCancel').click();
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }
          }, function () {
            vm.querySerial.fn();
          });
        }

      };

			/**
			 * 修改流水模块
			 * 逻辑顺序：
			 * 	1.点击修改流水按钮；
			 * 	2.查询当前点击流水的详细信息；
			 * 	3.根据详细信息中已勾选流水步骤， 修改模态框中步骤勾选状态
			 * @type {Object}
			 */
      vm.modifySerial = {
        showSteps: [],
        // 测试能够修改的步骤
        onlyTestStep: [{
          stepName: 'testdeploy', //测试部署
          isChoise: 0,
          description: '测试目标程序在具体硬件配置情况下，是否能正常将镜像部署到服务器'
        }, {
          stepName: 'testautotest', //自动化测试
          isChoise: 0,
          description: '以人为驱动的测试行为转化为机器执行的一种过程'
        }, {
          stepName: 'testintetest', //集成测试
          isChoise: 0,
          description: '将不同服务组合起来进行测试'
        }, {
          stepName: 'testperftest', //性能测试
          isChoise: 0,
          description: '模拟多种正常、峰值以及异常负载条件来对系统的各项性能指标进行测试'
        }, {
          stepName: 'testrelease', //测试发布
          isChoise: 0,
          description: '将镜像发布给测试或者生产'
        }],
        // 所有的流水步骤
        steps: [{
          stepName: 'downloadcheck', //下载审查
          isChoise: 1,
          description: '下载源码并对源码进行代码质量检查'
        }, {
          stepName: 'build', //编译构建
          isChoise: 1,
          description: '将程序源码编译打包并生成镜像文件'
        }, {
          stepName: 'depscan', //部署扫描
          isChoise: 0,
          description: '将镜像部署到服务器并进行安全扫描'
        }, {
          stepName: 'autotest', //自动化测试
          isChoise: 0,
          description: '以人为驱动的测试行为转化为机器执行的一种过程'
        }, {
          stepName: 'intetest', //集成测试
          isChoise: 0,
          description: '将不同服务组合起来进行测试'
        },
        {
          stepName: 'perftest', //性能测试
          isChoise: 0,
          description: '模拟多种正常、峰值以及异常负载条件来对系统的各项性能指标进行测试'
        },
        {
          stepName: 'release', //发布测试
          isChoise: 0,
          description: '将镜像发布给测试或者生产'
        }
        ],
        model: {},
				/**
				 * 查询单个流水信息后，判断哪些step被勾选的
				 * @param  {stepNameArr} data 当前点击流水已勾选的步骤数组
				 * @return {[type]}      [description]
				 */
        checkStep: function (data) {
          console.log('checkStep');
          console.log(this);
          // console.log(data);
          var stepNameArr = [];
          // 将已勾选步骤名，放入stepNameArr临时数组
          for (var i = 0; i < data.length; i++) {
            var step = data[i];
            stepNameArr.push(step.stepName);
          }


          // 根据stepNameArr临时数组，修改展示数据
          for (var k = 0; k < this.showSteps.length; k++) {
            var name = this.showSteps[k].stepName;
            // 如果临时数组存在该名字，则改为勾选状态
            if (stepNameArr.indexOf(name) !== -1) {
              this.showSteps[k].isChoise = 1;
            } else {
              this.showSteps[k].isChoise = 0;
            }
          }
        },
        querySerialDetail: function (operType) {
          var _this = this;
          this.showSteps = (operType === '1') ? vm.modifySerial.steps : vm.modifySerial.onlyTestStep;
          var params = {
            flowId: this.model.flowId
          };

          serialServer.serialDetail(params, function (res) {
            if (res.resultCode === '200') {
              var data = JSON.parse(res.data);
              _this.model = angular.copy(data);
              _this.model.operType = operType;
              _this.checkStep(data.stepList);
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
            }

          });
        },
        fn: function () {
          var _this = this;
          vm.modifySerial.model.stepList = [];
          for (var i = 0; i < vm.modifySerial.showSteps.length; i++) {
            if (vm.modifySerial.showSteps[i].isChoise == 1) {
              vm.modifySerial.model.stepList.push(vm.modifySerial.showSteps[i]);
            }
          }
          var p = {
            flowId: vm.modifySerial.model.flowId,
            flowName: vm.modifySerial.model.flowName,
            flowDescription: vm.modifySerial.model.flowDescription,
            stepList: vm.modifySerial.model.stepList
          };
          var t = {
            "flow": p
          };
          serialServer.modifySerial(t, function (res) {
            if (res.resultCode === '200') {
              $('#modifySerialModalCancel').click();
            } else {
              COREDATA.resMessage(res.resultCode, res.resultMessage);
              _this.checkStep([]);
            }
          }, function () {
            vm.querySerial.fn();
          });
        }
      };

			/**
			 * 拓展下拉选择菜单控制（修改流水，删除流水）
			 * showMenu: 点击下拉展示下拉菜单（暂时不用，使用onmouseover）
			 * closeAllMenu: 关闭所有下拉菜单
			 * @param  {[type]} event [description]
			 * @return {[type]}       [description]
			 */
      vm.expandMenu = {
        showMenu: function (event) {
          // event.stopProgation();
          if ($(event.target).next().css('display') == 'none') {
            $(event.target).next().css('display', 'block')
          } else {
            $(event.target).next().css('display', 'none')
          }
        },
        closeAllMenu: function () {
          $('div[name = "expandMenu"]').css('display', 'none');
        }
      };

			/**
			 * 保存当前点击流水的信心到浏览器缓存
			 * @param  {Object} serial 流水信息对象
			 * @return {}        none
			 */
      vm.saveSerial = function (serial) {
        window.sessionStorage.serialInfo = angular.toJson(serial);
        CORE_VALUE.PROJECTINFO.FLOWINFO = serial;

      };

      vm.showExecutionRecordPanel = function (serial) {
        vm.flowInfo = serial;
        // 广播事件，让自定义指令接收到信号及参数
        $scope.$broadcast('showExecutionRecordPanel', serial);
        // 展示流水执行记录框
        $("body").attr("style", "padding-right: 10px; overflow: hidden;");
        $("div[name='chenzhe'] .modal-mask").removeClass("modal-mask-hidden");
        $("div[name='chenzhe'] .flowBuildLogModal").show();
      };
      
      
      /* ===============
          广播机制部分
        ================*/
      var eventListeners = {};
      $scope.$on('$destroy', function () {
        console.log('进入 destory')
        // 回收广播
        for (var key in eventListeners) { eventListeners[key](); };
        if ($scope.timer) {
          console.log('取消定时器')
          $interval.cancel($scope.timer);
        }
      });
      // 改变应用广播，更新VIEW
      eventListeners['changeHeaderAppSuccess'] = $rootScope.$on('changeHeaderAppSuccess', function () {
        vm.init();
      });

      eventListeners['SwitchNav'] = $rootScope.$on('switchNav', function () {
        console.log('switchNav')
        if ($scope.timer) {
          console.log('取消定时器')
          $interval.cancel($scope.timer);
        }
      });

      eventListeners['isClickCICD'] = $rootScope.$on("isClickCICD", function () {
        console.log('isclickcicd');
        vm.querySerial.model.searchKey = '';
        vm.querySerial.model.page = 1;
        vm.querySerial.model.rows = 10;
        vm.querySerial.fn(null, true);
      });




      return vm;
    });
})();
