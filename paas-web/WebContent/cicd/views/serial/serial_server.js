(function () {
	angular.module('PaaS5.services')
	.factory('serialServer', function($http) {
		return {
			// 查询当前登录用户信息
			queryUser: function(callback) {
				$http({
					method: 'GET',
					url: '/paas/permission/queryUserRoleAndOperType.action',
				}).success(callback);
			},
			// 模糊查询流水列表
			querySerial: function(params, callback) {
				$http({
					method: 'GET',
					url: '/paas/flow/queryFlowList.action',
					params: params
				}).success(callback);
			},
			getSerialByIds: function(params, callback) {
				$http({
					method: 'GET',
					url: '/paas/flow/queryFlowListByIds.action',
					params: params
				}).success(callback);
			},
			// 删除流水
			delSerial: function(params, callback1, callback2) {
				$http({
					method: 'POST',
					url: '/paas/flow/deleteFlow.action',
					data: params,
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
				}).success(callback1).then(callback2);
			},
			// 修改流水
			modifySerial: function(params, callback, callback2) {
				$http({
					method: 'POST',
					url: '/paas/flow/updateFlow.action',
					data: params,
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
				}).success(callback).then(callback2);
			},
			// 复制流水
			copySerial: function(params, callback, callback2) {
				$http({
					method: 'POST',
					url: '/paas/flow/copyFlow.action',
					data: params,
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
				}).success(callback).then(callback2);
			},
			// 查询单个流水详情
			serialDetail: function(params, callback) {
				$http({
					method: 'GET',
					url: '/paas/flow/queryFlowDetail.action',
					params: params
				}).success(callback);
			},
			// 立即构建
			building: function(params, callback) {
				$http({
					method: 'POST',
					url: '/paas/flow/executeBuild.action',
					data: params,
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
				}).success(callback);
			},
			// 停止构建
			stopBuild: function(params, callback) {
				$http({
					method: 'POST',
					url: '/paas/flow/stopFlow.action',
					data: params,
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
				}).success(callback);
			}

		}
	})
})();
