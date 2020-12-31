(function () {
  'use strict';

  // 这里只创建模块，不要写逻辑，所依赖的模块可以根据需要裁减
  angular.module('PaaS5', [
    'ngAnimate',  // 动画效果
    'ngCookies',  // 在程序中访问Cookie
    'ngMessages',
    'ngResource',  // 访问REST对象
    'ui.router',  // 第三方的路由访问器
    'ngSanitize',  // 对html内容进行净化，以防范xss等前端攻击
    'restangular',
    'toastr',
    'PaaS5.controllers',
    'PaaS5.services',
    'PaaS5.filters',
    'angularTips', //页面提示指令
    'ui.bootstrap.modal'

  ])
    .config(function (RestangularProvider, $httpProvider, toastrConfig) {
      angular.extend(toastrConfig, {
        timeOut: 500,
        positionClass: 'toast-top-center',
        allowHtml: true
      });
      // RestangularProvider.setDefaultHeaders({'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'});
      var param = function (obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
        for (name in obj) {
          value = obj[name];
          if (value instanceof Array) {
            for (i = 0; i < value.length; ++i) {
              subValue = value[i];
              fullSubName = name + '[' + i + ']';
              innerObj = {};
              innerObj[fullSubName] = subValue;
              query += param(innerObj) + '&';
            }
          }
          else if (value instanceof Object) {
            for (subName in value) {
              subValue = value[subName];
              fullSubName = name + '.' + subName;
              innerObj = {};
              innerObj[fullSubName] = subValue;
              query += param(innerObj) + '&';
            }
          }
          else if (value !== undefined && value !== null)
            query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
        return query.length ? query.substr(0, query.length - 1) : query;
      };
      // Override $http service's default transformRequest
      $httpProvider.defaults.transformRequest = [function (data) {
        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
      }];
      $httpProvider.interceptors.push('httpInterceptor');
    })
    .factory('httpInterceptor', function ($q, $injector) {
      return{
        // responseError : function(response) {
        //   if (response.status == 401) {
        //     var rootScope = $injector.get('$rootScope');
        //     rootScope.stateBeforLogin = $injector.get('$rootScope').$state.current.name;
        //     rootScope.$state.go("login");
        //     return $q.reject(response);
        //   } else if (response.status === 404) {
        //     alert("404!");
        //     return $q.reject(response);
        //   }
        // },
        response : function(response) {
          if(typeof response.data === 'string' && response.data.indexOf('用户登陆超时或者没有登录') !== -1){
            window.location.href = '/paas';
          }
          return response;
        }

      };
    });

})();
