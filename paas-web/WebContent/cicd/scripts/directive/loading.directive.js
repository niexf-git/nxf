(function () {
    angular.module('PaaS5')
    
    .directive('loadingAnimation', function (CORE_VALUE) {
        return {
            restrict: 'E',
            templateUrl: CORE_VALUE.pageUrlPrefix + 'scripts/directive/loading.html',
            scope: {
                switch: '='
            },
            replace: true,
            link: function(scope, element, attrs) {
                
            }
        };
    });
})();