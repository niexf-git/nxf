(function() {
  'use strict';

  angular.module('PaaS5.serialDetail')
    .controller('checkReportCtrl', function($scope,$state,$cookies,$cookieStore,checkReportService,COREDATA) {
    

      var serialInfo = angular.fromJson(window.sessionStorage.serialInfo)//流水id
      $scope.codeCheckList = $cookieStore.get('codeCheckList');
      //查询问题详情的参数
      $scope.issueListParams = {
        flowId : serialInfo.flowId,//流水id
        problemType: '', //问题严重度
        sonarUUID:$scope.codeCheckList.sonarUUID
      };


      /*
         代码检查报告列表
       */

      $scope.issueList = function(s) {
        var tempLi = $('div.code-check-title ul li');
        angular.forEach(tempLi, function (value,key) {
          if(key == s){
            $(value).css({
              'background': '#fff'
            });
          }else{
            $(value).removeAttr('style')
          }
        });

        $scope.isActive = s;
        $scope.issueListParams.problemType = s;
        checkReportService.issueListQuery($scope.issueListParams, function(res) {
          if (res.resultCode + '' === '200') {
            $scope.checkList = JSON.parse(res.data);
            // $scope.checkList = res.data.codeProblemDetailInfoList;
            console.log( $scope.checkList)
          }else{
            COREDATA.resMessage(res.resultCode, res.resultMessage);
            // COREDATA.resMessage(res.resultCode, res.resultMessage);
          }
        });
      };



      $scope.issueList('0')
      
      /*
         进入报告详情页
       */
      $scope.goDetail = function(data) {
        $cookies.put('problemType', $scope.issueListParams.problemType);//问题严重度
        $cookies.put('codepath', data.codepath);//文件名称
        $cookies.put('flowId', serialInfo.flowId);//文件名称
      };

    });
})();
