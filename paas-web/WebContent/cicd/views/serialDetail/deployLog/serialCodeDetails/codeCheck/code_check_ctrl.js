(function () {
'use strict';
angular.module('PaaS5.serialDetail')
	.controller('codeCheckCtrls', function ($scope,toastr,$state,$cookieStore,codeCheckService,$stateParams,CORE_VALUE,COREDATA) {

	  //选择的数据
	   $scope.idIndex = $cookieStore.get("idIndex");
	   //查询参数
	    $scope.queryParams = {
	      flowId : CORE_VALUE.PROJECTINFO.FLOWINFO.flowId,//流水id
	      flowRecordId : CORE_VALUE.PROJECTINFO.FLOWINFO.flowRecordId
	      // flowRecordId :"d38e372a993011e7acf100505694211b"
	    };

	    /*
	      列表查询
	    */
	    $scope.queryCode = function() {
	    	codeCheckService.query($scope.queryParams, function (res){
	     		if (res.resultCode + '' === '200'){
	        		$scope.codeCheckList = JSON.parse(res.data);
	        		// $scope.codeCheckList = res.data.CodeDetailsEntity;
	        		console.log($scope.codeCheckList);
	       		}else{
	         	    // COREDATA.resMessage(res.resultCode, res.resultMessage);
	         	     COREDATA.resMessage(res.resultCode, res.resultMessage);
	       		}
	      	});
	    };

	    // 进入到各个错误类型的列表展示页
	    $scope.queryCheckReport = function(data){
	    	$cookieStore.put('codeCheckList', data);
	    }


	     //初始化
		$scope.queryCode();

	});
})();
