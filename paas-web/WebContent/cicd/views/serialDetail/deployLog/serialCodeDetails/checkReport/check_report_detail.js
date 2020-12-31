(function() {
  'use strict';

  angular.module('PaaS5.serialDetail')
    .controller('checkReportDetailCtrl', function($scope,$cookies,checkReportService,COREDATA) {

      //查询参数
      $scope.detailParams = {
            problemType  : $cookies.get("problemType"),
            codePath : $cookies.get("codepath"),
            flowId : $cookies.get("flowId")
      };


      var problemList;
      $scope.queryReportDetail = function() {
        $scope.fileName  = $scope.detailParams.codePath;
        checkReportService.queryDetail($scope.detailParams, function(res) {
          if (res.resultCode + '' === '200') {
            $scope.detailList = [];
            var data = JSON.parse(res.data);
            // var data = res.data;
            $scope.detailList.push(data);//返回的数据
            problemList = data.codeProblemList;//错误信息行数等
            console.log(data)
            // $scope.detailList.push(res.data);//返回的数据
            // problemList = res.data.codeProblemList;//错误信息行数等
            $('#codePlace').empty();
            var codeLists = '<pre class="brush: js; toolbar: false" >';
            angular.forEach($scope.detailList[0].code, function(value) {
              codeLists += value ;
            });
            codeLists += '</pre>';
            $('#codePlace').append(codeLists);
            SyntaxHighlighter.highlight();
            $scope.codeQuestionLists = problemList;
            addIdeaList('codePlace', problemList);
          } else {
            COREDATA.resMessage(res.resultCode, res.resultMessage);
            // $scope.setNoticeMsg('查看代码检查报告失败！');
          }
        });
};

$scope.queryReportDetail();


    //显示隐藏代码行提示
      $scope.modelHide = function(id) {
        var objDiv = $('#' + id + '');
        $(objDiv).css('display', 'none');
      };
      var clickRow = '';

      function show(obj, id) {
        var objDiv = $('#' + id + '');
        $(objDiv).css('display', 'block');
        $(objDiv).css('left', obj.offset().left + obj.width() + 30);
        $(objDiv).css('top', obj.offset().top);
        clickRow = obj[0].textContent;
        if (id === 'mydiv2') {
          angular.forEach($scope.codeQuestionLists, function(val) {
            if (val.line+'' === clickRow) {
              objDiv.children().children().get(1).innerHTML = '<div style="color: red;">problemType:</div>' + val.problemType;
              objDiv.children().children().get(2).innerHTML = '<div style="color: red;">message:</div>' + val.message;
              objDiv.children().children().get(3).innerHTML = '<div style="color: red;">description:</div>' + val.description;
            }
          });
        }
      }


       function addIdeaList(objId, list) {
         angular.forEach(list, function(value) {
           $('#' + objId + ' .gutter').children('.number' + value.line).addClass('code-err-info zeroN');
         });
         setTimeout(function() {
           $('.gutter > .zeroN').click(function() {
             show($(this), 'mydiv2');
           });
         }, 500);
       }


      // 
      // 
      // 
      // //查询参数
      // var params = {
      //   fileUuid: $cookies.get('uuId'),
      //   codeId: $cookies.get('id'),
      //   filePath: $cookies.get('filePath'),
      //   severity:$cookies.get('severity')
      // };
      // var problemList;
      // var zeroData = [];//0行数据

      // $scope.init = function() {
      //   var fileName = params.filePath;
      //   $scope.fileName = fileName.substring(4);
      //   devCheckReportService.queryDetail(params, function(res) {
      //     if (res.success) {
      //       zeroData = [];//0行数据
      //       $scope.detailList = [];
      //       $scope.detailList.push(res.data);
      //       problemList = res.data.spdmodel;
      //       $('#codePlace').empty();
      //       var codeLists = '<pre class="brush: js; toolbar: false" >';
      //       angular.forEach($scope.detailList[0].code, function(value) {
      //         codeLists += value + '\n';
      //       });
      //       codeLists += '</pre>';
      //       $('#codePlace').append(codeLists);
      //       //0hang
      //       $('#codeLine').empty();
      //       var codeLists0 = '<pre class="brush: js; toolbar: false" ></pre>';
      //       angular.forEach(problemList, function(value) {
      //         if(value.line === 0){
      //           zeroData.push(value);
      //           $scope.isHaveZero = true;
      //         }
      //       });
      //       $('#codeLine').append(codeLists0);
      //       SyntaxHighlighter.highlight();
      //       $scope.codeQuestionLists = problemList;
      //       addIdeaList('codePlace', problemList);
      //       addIdeaList0('codeLine', zeroData);

      //     } else {
      //       $scope.setNoticeMsg('查看代码检查报告失败！');
      //     }
      //   });
      // };

      // //显示隐藏代码行提示
      // $scope.modelHide = function(id) {
      //   var objDiv = $('#' + id + '');
      //   $(objDiv).css('display', 'none');
      // };
      // var clickRow = '';

      // function show(obj, id) {
      //   var objDiv = $('#' + id + '');
      //   $(objDiv).css('display', 'block');
      //   $(objDiv).css('left', obj.offset().left + obj.width() + 30);
      //   $(objDiv).css('top', obj.offset().top);
      //   clickRow = obj[0].textContent;
      //   if (id === 'mydiv2') {
      //     angular.forEach($scope.codeQuestionLists, function(val) {
      //       if (val.line+'' === clickRow) {
      //         objDiv.children().children().get(1).innerHTML = '<div style="color: red;">titleName:</div>' + val.titleName;
      //         objDiv.children().children().get(2).innerHTML = '<div style="color: red;">message:</div>' + val.message;
      //         objDiv.children().children().get(3).innerHTML = '<div style="color: red;">description:</div>' + val.description;
      //       }
      //     });
      //   }
      // }

      // function show0(obj, id) {
      //   var objDiv0 = $('#' + id + '');
      //   $(objDiv0).css('display', 'block');
      //   $(objDiv0).css('left', obj.offset().left + obj.width() + 30);
      //   $(objDiv0).css('top', obj.offset().top);
      //   clickRow0 = obj[0].textContent;
      //   $scope.zeroData = zeroData;

      //   if (id === 'mydiv0') {
      //     angular.forEach(zeroData, function(val) {
      //       objDiv0.children().children().get(1).innerHTML = '<div style="color: red;">titleName:</div>' + val.titleName;
      //       objDiv0.children().children().get(2).innerHTML = '<div style="color: red;">message:</div>' + val.message;
      //       objDiv0.children().children().get(3).innerHTML = '<div style="color: red;">description:</div>' + val.description;
      //     });
      //   }
      // }

      // function addIdeaList(objId, list) {
      //   angular.forEach(list, function(value) {
      //     $('#' + objId + ' .gutter').children('.number' + value.line).addClass('code-err-info zeroN');
      //   });
      //   setTimeout(function() {
      //     $('.gutter > .zeroN').click(function() {
      //       show($(this), 'mydiv2');
      //     });
      //   }, 500);
      // }
      // //0hang
      // var clickRow0 = '';
      // function addIdeaList0(objId, list) {
      //   var guuter = $('#' + objId + ' .gutter');
      //   guuter.children('.number' + 1).addClass('code-err-info zeroY');
      //   guuter.children()[0].innerHTML = '   ';
      //   setTimeout(function() {
      //     $('.gutter > .zeroY').click(function() {
      //       show0($(this), 'mydiv0');
      //     });
      //   }, 500);
      // }



    });
})();
