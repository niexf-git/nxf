
var flag = false;

function queryAppLog(flag) {
//	$('#appLog').val("正在获取日志，请稍等...");
	var downloadPath = $.getUrlParam('downloadPath');
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"downloadPath" : downloadPath.toString(),
			"flag" : flag
		},
		url : "/paas/log/viewAppLog.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var json = eval(result['resultMessage']);
				if(flag){
					$('#appLog').val($('#appLog').val() + "\n" + json);
				}else{
					$('#appLog').val(json);
				}
			} else {
				parent.parent
						.alertError(result['resultCode'],
								result['resultMessage']);
			}
		}
	});
}

$(function() {
	queryAppLog(flag);
	
	//滚动条下拉触发请求
	$("#appLog").scroll(function(){
//		if ($("textarea").scrollTop() == $(document).height() - $("textarea").height()) {
//			alert("ok");
//		}
		
		flag = true; //滚动条触发请求
		var $this =$(this),
        viewH =$(this).height(),//可见高度
        contentH =$(this).get(0).scrollHeight,//内容高度
        scrollTop =$(this).scrollTop();//滚动高度
//		alert("可见高度:" + viewH + ",内容高度:" + contentH + ",滚动高度:" + scrollTop);
//       if((contentH - viewH) >= scrollTop) { //到达底部100px时,加载新内容
       if(scrollTop/(contentH -viewH)>=0.95){ //到达底部100px时,加载新内容
    	   queryAppLog(flag);
       }
		
	});
	
//	$(window).scroll(function () {
//		if ($(window).scrollTop() == $(document).height() - $(window).height()) {
////			alert('bottom!!');
//			queryAppLog();
//		}
//	});
	
});