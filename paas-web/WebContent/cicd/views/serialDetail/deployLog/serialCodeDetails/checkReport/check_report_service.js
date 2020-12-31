(function () {
	angular.module('PaaS5.services')
	.factory('checkReportService', function($http) {
		return {
			//代码检查列表
			issueListQuery: function(params,callback) {
				$http({
					method: 'GET',
					url: '/paas/codedetails/queryCodeDetails.action',
					params: params, 
					headers: { 'Content-Type': 'text/plain' },
				}).success(callback);
			},
			//代码检查报告详情
			queryDetail:function(params,callback){
				$http({
					method: 'GET',
					url: '/paas/codedetails/queryProblemDetails.action',
					params: params, 
					headers: { 'Content-Type': 'text/plain' },
				}).success(callback);
			}

		}
	})
})();
