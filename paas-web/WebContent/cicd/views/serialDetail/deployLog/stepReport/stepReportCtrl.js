(function () {
  'use strict';
  angular.module('PaaS5.serialDetail')
    .controller('stepReportCtrl', function ($scope, $state,$rootScope) {

    	$scope.isCheckReport = {
    		unitChecked:true,
    		jacocoChecked:false
    	};

        $scope.isCheckedStyle = {
            color:'#000',
            cursor:'pointer'
        };

        $scope.noCheckedStyle = {
            'color':'#999',
            cursor:'pointer'
        };

    	$scope.showUnitReport = function(){
    		$scope.isCheckReport.unitChecked = true;
    		$scope.isCheckReport.jacocoChecked = false;
            $scope.isCheckedStyle.color='#000';
            $scope.noCheckedStyle.color = '#999';
            document.getElementById("stepReport").src = $rootScope.unitReportUrl;
    	};

    	$scope.showjacocoReport = function(){
    		$scope.isCheckReport.unitChecked = false;
    		$scope.isCheckReport.jacocoChecked = true;
            $scope.isCheckedStyle.color='#999';
            $scope.noCheckedStyle.color = '#000';
            document.getElementById("stepReport2").src = $rootScope.jacocoReportUrl;
    	};


    });
})();
