(function($) {
	//加密
	$.extend({
		aesEncrypt : function(data) {
	        var sendData = CryptoJS.enc.Utf8.parse(data);
	        var key = CryptoJS.enc.Utf8.parse("dvyYRQlnPRCMdQSe");//秘钥
	        var iv  = CryptoJS.enc.Utf8.parse("face0123456789ai");//偏移量
	        var encrypted = CryptoJS.AES.encrypt(sendData, key,{iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});//ZeroPadding=NoPadding
	        return CryptoJS.enc.Base64.stringify(encrypted.ciphertext); 
	    }
	});
})(jQuery);