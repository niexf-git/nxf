(function () {
	angular.module('PaaS5.services')
	.factory('deployLogService', function($http) {
		return {
			// 安全扫描报告
			goScanReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/querySecurityScanReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},
			// 自动测试报告
			goAutoReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/queryAutoTestReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},
			// 集成测试报告
			goIntegrateReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/queryIntegrateTestReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},
			//性能测试报告
			goPerformanceReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/queryPerformanceTestReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},
			//单元测试报告
			goUnitReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/queryUnitTestReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},
			//覆盖率报告
			/*goCoverageRateReport: function(params, callback) {
				$http({
					method: 'get',
					url: '/paas/queryreport/queryUnitTestCoverageRateReport.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' }
				}).success(callback);
			},*/
			//查询所有列表的执行记录
			queryAllRecord: function(params, callback) {
				$http({
					method: 'GET',
					url: '/paas/excuteRecord/queryFlowExcuteRecordList.action',
					params: params,
					headers: { 'Content-Type': 'text/plain' },
					// headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				}).success(callback);
			}
		};
	});
})();
