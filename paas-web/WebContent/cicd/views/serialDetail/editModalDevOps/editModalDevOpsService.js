(function () {
  'use strict';
  angular.module('PaaS5.services')
    .factory('editModalDevOpsService', function ($http,$rootScope) {
      return {
        //流水执行记录
        queryRecord: function (params, callback) {
          $http({
            method: 'GET',
            url: '/paas/excuteRecord/queryExcuteRecord.action',
            params: params
          }).success(callback);
        },
        //构建完成日志
        queryRecordLog: function (params, callback) {
          $http({
            method: 'GET',
            url: '/paas/excuteRecord/queryBuildLogs.action',
            params: params,
            cache: true
          }).success(callback);
        },


        // 流水步骤卡片查询
        queryFlowStep: function (params, callback) {
          $http({
            method: 'GET',
            url: '/paas/flow/queryStepDetail.action',
            params: params
          }).success(callback);
        },


        //刷新日志长链接查询
        buildLogWebSoket: function (flow_id, data, ele) {
          var socket;
          if (window.WebSocket) {
            if (socket != undefined && soket.readState == 1) {
              socket.close()
            }
            $rootScope.text='';
            socket = new WebSocket('ws://' + window.location.host + '/paas/websocket/stepLog');

            socket.onmessage = function (event) {
              var result = JSON.parse(event.data);
              if (result.resultCode != "0") {
                $rootScope.text+=result.resultMessage;
                var $div=$('.collapse-content.collapse-content-active');
                for(var i=0;i<$div.length;i++){
                  if($($div[i]).hasClass('collapse-content-active')){
                    var hgt=$($div[i]).find('#flowLog').height();
                  }
                }
                $('#flowStageBuildLog').scrollTop(20000);

              } else if (result.resultCode == '0') {
                socket.close();
                console.log('长连接关闭');
              } else {
                scoket.close();
              }
            };
            socket.onopen = function () {
              console.log('长连接打开')
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


      }
    })
})();
