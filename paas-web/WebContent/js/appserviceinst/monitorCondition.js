
$(document).ready(function(){
	   var appId = $.getUrlParam('id');
	   $.ajax({
	        type: "POST",
	        contentType: "application/json;utf-8",
	        dataType: "json",
	        url: "/paas/monitor/instMonitor!queryInstByAppId?appId="+appId,
	        success: function (result) {
	        	if(result['resultCode'] == 'success'){
	        		var json = eval(result['resultMessage']);
	        		 $.each(json, function () {
	 	                $("#instSelect").append("<option value=" + this.value + ">" + this.text + "</option>\r\n");
	 	            });
	        	}
	        }
	    });
	});

$(document).ready(function(){
		$.ajax({
		     type: "POST",
		     contentType: "application/json;utf-8",
		     dataType: "json",
		     url: "/paas/monitor/instMonitor!initDateTime",
		     success: function (result) {
		     	if(result['resultCode'] == 'success'){
		     		var dateTime = $.parseJSON(result['resultMessage']); 
		     		$('#begin_date').val(dateTime['minDate']);
		     		$('#end_date').val(dateTime['maxDate']);
		     	}
		     }
		 });
	});

$(function(){
    $('#begin_date').calendar({format:'yyyy-MM-dd HH:mm:ss'});
    $('#end_date').calendar({format:'yyyy-MM-dd HH:mm:ss'});
});

function checkDateTime(){
	var startTime=$("#begin_date").val();
	var endTime=$("#end_date").val();
	var selectValue = $("#instSelect").val();
	
	if(selectValue == '' || selectValue == null){
		parent.parent.alertWarn('实例名称不能为空!');
		return false;
    }
	
	if(startTime == '' || endTime == ''){
		parent.parent.alertWarn("时间区间不能有空，请选择时间区间!");
		return false;
	}
	
	var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	var millis = end.getTime()-start.getTime();  //时间差的毫秒数
	var time = 7*24*60*60*1000;
	
	if(end<start){
		parent.parent.alertWarn("开始时间不能大于结束时间!");
	 	return false;
	}
	if(millis>time){
		parent.parent.alertWarn("时间区间相差不能超过一周!");
	 	return false;
	}
	return true;
}
