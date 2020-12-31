(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name PaaS5.service:MainService
   * @description
   * # MainService 公用核心服务
   * Service of the PaaS5
   */
  angular.module('PaaS5.services', [
    'PaaS5.services.outReportService'
  ])
    /**
     * PROJECTINFO:项目信息
     * {
     *   APPINFO: object, //应用信息
     *   FLOWINFO: object, //流水信息
     *   FLOWID: string //流水id
     * }
     */
    .value('CORE_VALUE', {
      USERINFO: {},
      PROJECTINFO: {
        APPINFO: { appName: '全部', id: '0', operType: '' },//初始化应用的信息
        FLOWINFO: {},
        FLOWID: '',
        flowRecordId: ''//流水记录id
      },
      svnUrlPrefix: '/paas/cicd/',//打包的时候改成 /paas/cicd/，本地跑改成''
      pageUrlPrefix: '/paas/cicd/',//页面前缀
      isCICD: { value1: false, value2: true, navInfo: [] }//判断是否进入的是CICD,value1为true进入CICD.
    })
    .factory('COREDATA', COREDATA);

  function COREDATA($location, toastr, CORE_VALUE, $http) {
    return {
      setStorageInfo: setStorageInfo,
      setAppInfo: setAppInfo,
      resMessage: resMessage,
      setUserInfo: setUserInfo
    };
    //同时设置CORE_VALUE和Stotage，(哪个公共值，哪个属性，赋值，storage名字)
    function setStorageInfo(info, pName, pValue, storageName) {
      if (info !== undefined && storageName !== undefined) {
        if (CORE_VALUE.hasOwnProperty(info) && CORE_VALUE[info].hasOwnProperty(pName)) {
          CORE_VALUE[info][pName] = pValue;
          window.sessionStorage[storageName] = angular.toJson(pValue);
        } else {
          console.error('请先到main.service.js注册属性,CORE_VALUE.' + info + '.' + pName)
        }
      } else {
        console.warn('请传正确参数' + '(info,pName,pValue,storageName)');
      }
    }
    function setAppInfo(infoData) {
      if (infoData !== undefined || infoData !== null) {
        window.sessionStorage.appInfo = angular.toJson(infoData);
        CORE_VALUE.PROJECTINFO.APPINFO.appId = infoData.appId;
        CORE_VALUE.PROJECTINFO.APPINFO.appName = infoData.appName;
      } else {

      }
    }
    function resMessage(resultCode, resultMessage) {
      var tempCode = resultCode + '';
      if (tempCode === '200') {
        toastr.success(resultMessage);
      } else if (tempCode.slice(0, 7) === 'PAAS-00') {

      } else if (tempCode) {//错误信息多展示5秒
        if (resultMessage === undefined) {
          toastr.warning(tempCode);
        } else {
          toastr.error(resultCode + ' ' + resultMessage, {
            closeButton: true,
            progressBar: true,
            timeOut: 5000,
            extendedTimeOut: 8000
          });
        }
      } else if (resultCode === undefined && resultMessage === undefined) {
        toastr.error('系统异常');
      }
    }
    function setUserInfo(afterFn) {
      $http({
        method: 'GET',
        url: '/paas/permission/queryUserRoleAndOperType.action'
      }).success(function (res) {
        if (res.resultCode !== '200') {
          toastr.warning(res.resultMessage);
          return;
        }
        CORE_VALUE.USERINFO = JSON.parse(res.data);
        if (afterFn) { afterFn(); }
      });
    }
  }

})();
