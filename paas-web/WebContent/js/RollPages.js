// RollPages

var rollPages = new Object();
rollPages.navs = null;
rollPages.tabs = null;
rollPages.arrows = null;
rollPages.box = null;
rollPages.path = null;
rollPages.back = null;
rollPages.pageUrl = null;
rollPages.newData = null;
rollPages.loadPath = '';
//
rollPages.init = function(){
	this.arrows.parent().hide();
	this.bind();
//	this.arrows.click(function(){
//		var _index = $(this).attr('rel');
//		if (_index) {
//			rollPages.navs.eq($(this).attr('rel')).click();
//		}
//		//
//		return false;
//	});
}
rollPages.bind = function(){
	var _count = this.navs.size();
	var _arrows = this.arrows;
	this.navs.live('click', function(){
		if (rollPages.pageUrl != $(this).attr('rel')) {
			var _subs = $(this).next('.subNav');
			if ($(this).parents('#navMenu').size()) {
				if (_subs.html()) {
					rollPages.tabs.html(_subs.html());
				} else {
					rollPages.tabs.empty();
				}
			}
			$(this).blur().parent().addClass('selected').siblings().removeClass();
			rollPages.path.text($(this).text());
			rollPages.pageUrl = $(this).attr('rel');
			rollPages.loadPage();
			//
			var _index = $(this).parent().index();
			var _prev = (_index-1 < 0)? '':_index-1;
			var _next = (_index+1 >= _count)? '':_index+1;
//			_arrows.first('.prev').attr('rel',_prev);
//			_arrows.last('.next').attr('rel',_next);
		}
		//
		return false;
	});
	this.back.live('click', function(){
		rollPages.navs.first().click();
		return false;
	});
}
rollPages.unBind = function(){
	this.navs.die('click');
	this.back.die('click');
}
rollPages.loadPage = function(){
	if (!this.pageUrl) return false;
	//
	var _url = this.pageUrl;
	$.ajax(_url,{
		dataType:"html",
		cache:false,
		success:function(data,txt){
			rollPages.newData = data;
			rollPages.roll();
		},
		error:function(xhr,txt,er){
			//
		},
		complete:function(xhr,txt){
			//
		}
	});
}
rollPages.title = function(title) {
	this.path.html(title);
}
rollPages.roll = function(){
	var _table = this.box.children('.rollTable');
	var _old = _table.find('td.rollCell').first();
	var _new = _table.find('td.rollCell').last();
	if (_old.text()) {
		_new.html(this.newData);
		this.unBind();
		_table.animate({marginLeft:-1000}, 600, 'swing', function(){
			_old.empty().insertAfter(_new);
			$(this).css('marginLeft',0);
			rollPages.bind();
		});
	} else {
		_old.html(this.newData);
	}
}
