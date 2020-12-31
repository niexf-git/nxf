(function () {
	angular.module('PaaS5.services')
	.factory('codeCheckService', function($http) {
		return {
			//代码检查列表
			query: function(params,callback) {
				$http({
					method: 'GET',
					url: '/paas/codedetails/queryCodeDetailsList.action',
					params: params, 
					headers: { 'Content-Type': 'text/plain' },
				}).success(callback);
			}

		}
	})
})();