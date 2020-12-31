(function() {
  'use strict';

  /**
   * 分页指令 ——————————陈哲
   *
   * Example:
   *
           <page limit="10"
           page="1"
           params="srl.querySerial.model"
           total="srl.showModel.totalCount"
           data="srl.showModel.serialList"
           query="srl.querySerial.fn();">
           </page>
   *
   * 使用注意事项：
   * 1.limit: 初始化每页展示个数；
   * 2.cuttent-page: 初始化当前页数;
   * 3.params：查询的分页参数必须为（名字需对应一致）： rows(每页展示个数); page(当前页数);
   * 4.total: 查询结果总条目数，用户计算总页数，所以查询返回参数中必须带有totalCount这个参数
   * 5.data: 列表ng-repeat的数据模型
   * 6.query: 查询方法
   */
  angular.module('PaaS5')
    .directive('page', function(CORE_VALUE) {
      return {
        restrict: 'E',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'scripts/directive/page.html',
        replace: true,
        scope: {
          limit: '@', // 展示条目数
          page: '@', // 当前页
          params: '=',
          total: '=',
          data: '=',
          // pageUrl: '='
          query: '&'
        },
        link: function(scope) {
          var keepParams = {};
          var time = 0;

          function toNumber(str) {
            if (typeof str === 'string') {
              return parseInt(str);
            } else {
              return str;
            }
          }

          // 得到总页数
          function calculatetotalCount() {
            return Math.ceil(scope.total / scope.params.rows);
          }

          function keep(obj) {
            for (var k in obj) {
              if (obj[k] === 'undefined' || obj[k] === '') {
                keepParams[k] = '';
              } else {
                keepParams[k] = obj[k];
              }
            }
          }

          // deepCopy obj
          function getKeep(objA, objB) {
            for (var k in objB) {
              objA[k] = objB[k];
            }
          }

          //与下拉框绑定
          scope.pageNow = toNumber(scope.page);
          scope.rowsNow = toNumber(scope.limit);

          // init
          function init() {
            scope.totalCount = calculatetotalCount();
            if (scope.totalCount === 0) {
              scope.page = 0;
              scope.pageNow = 0;
            }
            if (time === 0 && scope.totalCount !== 0) {
              scope.page = 1;
              scope.pageNow = 1;
            }
          }
          init();

          /**
           * 选择每页显示数
           * @param  {number} lmt 每页显示数（5，10，15，...）
           * @return {null}     [description]
           */
          scope.changeLimit = function(lmt) {
            time = 0;
            scope.pageNow = 1;
            scope.page = 1;
            // scope.params = angular.copy(keepParams);
            getKeep(scope.params, keepParams);
            scope.params.page = scope.pageNow;
            scope.rowsNow = lmt;
            scope.params.rows = scope.rowsNow;
            // scope.params.rows = lmt;
            setTimeout(function() {
              scope.query();
            }, 200);
            init();
          };

          // 设置当前页码
          scope.setpage = function(page) {
            time = 0;
            if (typeof page !== 'number') {
              return;
            }
            scope.pageNow = toNumber(page);
            scope.page = toNumber(page);
            scope.params.page = scope.pageNow;
            scope.query();
            init();
            setTimeout(function() { scope.params.page = 1; }, 1000);
          };

          // 跳转
          scope.go = function(gogogo) {
            time++;
            var g;
            if(typeof(gogogo) === 'number')
                gogogo = gogogo+'';
            if (gogogo === '0' || gogogo === '') {
              gogogo = 1;
              scope.pageNow = scope.page;
              return;
            }
            if (gogogo.length !== 1 && gogogo.substring(0, 1) === '0') {
              g = gogogo.substring(1);
              if (g >= scope.totalCount) {
                gogogo = scope.totalCount;
              } else {
                gogogo = g;
              }
            }
            if (gogogo > scope.totalCount) { gogogo = scope.totalCount; }
            scope.page = gogogo;
            scope.pageNow = gogogo;
            scope.params.page = gogogo;
            scope.query();
            init();
          };

          /**
           * 上一页下一页组件方法
           * @param  {string} n 用于判断用户点击的页数
           * @return {[type]}   [description]
           */
          scope.play = function(n) {
            time++;
            switch (n) {
              case 'first':
                if (scope.page > 1) {
                  scope.page = 1;
                  scope.pageNow = 1;
                  scope.params.page = 1;
                  scope.query();
                  init();
                }
                break;
              case 'above':
                if (scope.page !== 1 && scope.page !== 0) {
                  scope.page--;
                  scope.pageNow--;
                  scope.params.page--;
                  scope.query();
                  init();
                }
                break;
              case 'next':
                if (scope.page !== scope.totalCount) {
                  scope.page++;
                  scope.pageNow++;
                  scope.params.page++;
                  scope.query();
                  init();
                }
                break;
              case 'end':
                if (scope.page !== 0 && scope.page !== scope.totalCount) {
                  scope.page = scope.totalCount;
                  scope.pageNow = scope.totalCount;
                  scope.params.page = scope.totalCount;
                  scope.query();
                  init();
                }
                break;
              default:
                break;
            }
          };

          scope.mykey = function(e) {
            var keycode = window.event ? e.keyCode : e.which; //获取按键编码
            if (keycode === 13) {
              scope.go(scope.pageNow); //如果等于回车键编码执行方法
            }
          };

          scope.keyDownFn = function(e) {
            var ss = window.event || e;
            // 只能输入数字
            if (!(ss.keyCode === 8 || (ss.keyCode > 47 && ss.keyCode < 58) || (ss.keyCode > 95 && ss.keyCode < 106))) {
              // 阻止默认事件
              ss.preventDefault();
              if(ss.keycode === 16) {
                ss.preventDefault();
              }
            }
          };

          scope.$watch('total', function() {
            init();
          });

          scope.$watch('data', function() {
            keep(scope.params);
            if (scope.params.page === 1 || scope.page === 0) {
              scope.page = 1;
              scope.pageNow = 1;
            }
            if (typeof scope.data === 'undefined' || scope.data === null || scope.data.length === 0) {
              scope.pageNow = 0;
              scope.page = 0;
            }
           
          });

          scope.$watch('pageNow', function(newValue, oldValue) {
            if(isNaN(newValue)) {
              scope.pageNow = oldValue;
            }
          })
        }
      };
    });


})();
