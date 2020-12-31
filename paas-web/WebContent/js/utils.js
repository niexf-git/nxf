 
(function ($) { 
$.fn.openWidow = function (options) { 
var divId = "dialog" + Math.round(Math.random() * 100); 
var settings = { 
id: divId, 
width: 300, 
height: 200, 
modal: true, 
buttons: { 
}, 
show: "explode", 
hide: "highlight", 
title: "提示", 
url: "/test.aspx", 
close: function () { 
$("#" + this.id).remove(); 
//debugger 
if (document.getElementById(this.id)) 
document.body.removeChild(document.getElementById(this.id)); 
} 
}; 
if (options) { 
$.extend(settings, options); 
} 
$("body").append('<div id="' + settings.id + '" title="Dialog Title"><p class="loading"> </div>'); 
// Dialog 
$('#' + settings.id).dialog({ 
autoOpen: false, 
title: settings.title, 
width: settings.width, 
height: settings.height, 
modal: true, 
bgiframe: true, 
show: settings.show, 
hide: settings.hide, 
buttons: settings.buttons, 
close: settings.close, 
open: function () { 
$("#" + settings.id).html('<iframe src="' + settings.url + '" frameborder="0" height="100%" width="100%" id="dialogFrame" scrolling="auto"></iframe>'); 
}, 
resizeStop: function () { 
$("#dialogFrame").css("width", parseInt($(this).css("width")) - 5); 
$("#dialogFrame").css("height", parseInt($(this).css("height")) - 5); 
} 
}); 

$('#' + settings.id).dialog("open"); 
return this; 
}; 
})(jQuery); 

(function($){
	$.getUrlParam = function(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!= null) 
		return unescape(r[2]);
	return null;
	}
})(jQuery);

//禁止回退键功能
function banBackSpace(){  
	$("body").keydown(function(event) {
        if(event.keyCode==8){//判断按键为backSpace键  
            //获取按键按下时光标做指向的element  
            var elem = event.srcElement || event.target || event.currentTarget ;   
            //判断是否需要阻止按下键盘的事件默认传递  
            var name = elem.nodeName;  
            
            if(name!='INPUT' && name!='TEXTAREA'){  
                return _stopIt(event);  
            }  
            var type_e = elem.type.toUpperCase();  
            if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){  
                return _stopIt(event);  
            }  
            //当敲Backspace键时，事件源类型为 密码、单行、多行文本，并且readOnly属性为true或disabled属性为true的，则退格键失效
            if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){  
                return _stopIt(event);  
            }  
        }  
	});  
}; 
function _stopIt(e) {
	if (e.returnValue) {
		e.returnValue = false;
	}
	if (e.preventDefault) {
		e.preventDefault();
	}
	return false;
}

