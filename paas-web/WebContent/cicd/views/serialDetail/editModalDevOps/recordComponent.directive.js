(function () {
  'use strict';

  angular.module('PaaS5.d')
    .directive('recordModal',function (CORE_VALUE) {
      return {
        restrict: 'EA',
        templateUrl: CORE_VALUE.pageUrlPrefix + 'views/serialDetail/editModalDevOps/recordComponent.html',
        scope: {},
        replace: false,
        link: function (scope, element, attrs) {
          //显示执行记录框
          function showExecutionRecordPanel(){
            $("body").attr("style", "padding-right: 10px; overflow: hidden;");
            $("#executionRecordPanel .modal-mask").removeClass("modal-mask-hidden");
            $("#executionRecordPanel .flowBuildLogModal").show();
          }
          //改变执行记录框大小
          function changeExecutionRecordPanel(){
            var isSmallModal = $("div#StageBuildLog").attr("class") == "smallModal";
            console.log($("div#StageBuildLog").attr("class"));
            if(isSmallModal){
              $("div#StageBuildLog").attr("class", "bigModal");
            }else{
              $("div#StageBuildLog").attr("class", "smallModal");
            }
          }
          //展开或者折叠内容
          function openOrFoldContent(event){
            var isOpen = $(event).attr("aria-expanded") == "true";
            if(isOpen){//折叠
              $(event).attr("aria-expanded", "false");
              $(event).next().removeClass("collapse-content-active");
              $(event).next().addClass("collapse-content-inactive");
              $(event).children(":first").removeClass("fa-sort-asc");
              $(event).children(":first").addClass("fa-sort-desc");
            }else{//展开
              $(event).attr("aria-expanded", "true");
              $(event).next().removeClass("collapse-content-inactive");
              $(event).next().addClass("collapse-content-active");
              $(event).children(":first").removeClass("fa-sort-desc");
              $(event).children(":first").addClass("fa-sort-asc");
            }
          }
          //关闭执行记录框
          function closeExecutionRecordPanel(){
            $("body").attr("style", "overflow-y:scroll;");
            $("#executionRecordPanel .flowBuildLogModal").hide();
            $("#executionRecordPanel .modal-mask").addClass("modal-mask-hidden");
          }
        }
      };


    });

})();
