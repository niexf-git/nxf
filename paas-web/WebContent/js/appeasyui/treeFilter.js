var _treeFilterRoot;
/*
function filterComboTree(q, comboid) {
	var datalist = [];
	var combotreeid = "#" + comboid;
	// 得到根节点数组
//	var roots = $(combotreeid).combotree('tree').tree('getRoots');
	 var roots = _treeFilterRoot;
	var entertext = $(combotreeid).combotree('getText');
	
	$(combotreeid).combotree('setText', q);
	if (entertext == null || entertext == "") {
		// 如果文本框的值为空，或者将文本框的值删除了，重新reload数据
		$(combotreeid).combotree('reload');
		$(combotreeid).combotree('clear');
		$(combotreeid).combotree('setText', q);
		return;
	}
	// 循环数组，找到与输入值相似的
	for ( var i = 0; i < roots.length; i++) {
		if (q == roots[i].text) {
			$(combotreeid).combotree('tree').tree('select', roots[i].target);
			return;
		} else {
			if (roots[i].text.indexOf(q) >= 0 && roots[i].text != "") {
				var org = {
					"id" : roots[i].id,
					"text" : roots[i].text
				};
				datalist.push(org);
			}
			var root_children = roots[i].children;
			if(root_children){
				for ( var j = 0; j < root_children.length; j++) {
					if (root_children[j].text.indexOf(q) >= 0 && root_children[j].text != "") {
						var org_c = {
							"id" : root_children[j].id,
							"text" : root_children[j].text
						};
						datalist.push(org_c);
					}
				}
			}
		}
		// 找子节点（递归）
		childrensTree(combotreeid, roots[i], datalist, q);//modify by yzw at 0522 优化树加载
	}
	// 加载过滤的结果
	if (datalist.length > 0) {
		$(combotreeid).combotree('loadData', datalist);
		$(combotreeid).combotree('setText', q);
		// 重新赋值是避免再次过滤时会有重复的记录
		datalist = [];
		return;
	} else {
		// add by yzw at 0519
		// $(combotreeid).combotree('reload');
//		$(combotreeid).combotree('clear');
		$(combotreeid).combotree('loadData', []);
		$(combotreeid).combotree('setText', q);
		return;
	}
}


function childrensTree(combotreeid, rootstarget, datalist, q) {
	var isLeaf = $(combotreeid).combotree('tree').tree('isLeaf', rootstarget);
	if (isLeaf) {
		return;
	}
//	var children = $(combotreeid).combotree('tree').tree('getChildren',
//			rootstarget);
	var children = rootstarget.children;
	for ( var j = 0; j < children.length; j++) {
		if (q == children[j].text) {
			$(combotreeid).combotree('tree').tree('select', children[j].target);
			return;
		} else {
			if (children[j].text.indexOf(q) >= 0 && children[j].text != "") {
				var org = {
					"id" : children[j].id,
					"text" : children[j].text
				};
				datalist.push(org);
			}
		}
	}
}
*/


//应用树combotree 过滤查询
function filterComboTree(q, comboid) {
	//var datalist = [];//用平面的combobox来展示过滤的结果
	var combotreeid = "#" + comboid;
	//$(combotreeid).combotree('loadData', roots);
	//var roots = $(combotreeid).combotree('tree').tree('getRoots');//得到根节点数组
	var roots = _treeFilterRoot;//得到根节点数组
	var entertext = $(combotreeid).combotree('getText');
	$(combotreeid).combotree('setText', q);
	
	if (entertext == null || entertext == "") {//如果文本框的值为空，或者将文本框的值删除了，重新reload数据
	   // $(combotreeid).combotree('reload');//如果文本框输入值为空，不再从服务端reload，而是从缓存中获取，提高效率
		$(combotreeid).combotree('loadData', roots);
	    $(combotreeid).combotree('clear');
	    $(combotreeid).combotree('setText', q);
	    return;
	}
	//循环数组，找到与输入值相似的，加到前面定义的数组中，
	for (var i = 0; i < roots.length; i++) {
	    if (q == roots[i].text) {
			$(combotreeid).combotree('tree')
				.tree('select', roots[i].target);
		    return;
	    } else {
			if (roots[i].text.indexOf(q) >= 0 && roots[i].text != "") {
				//$(combotreeid).combotree('loadData',[{id:'all:',text:'全部'}]);
				//$(combotreeid).combotree('setText', q);
				return;
			}
	    }
	    
	    var result = childrensTreeJson(combotreeid, roots[i].target, q);
	    //console.log(result);
	    result = parseJsonStr(eval(result),q);
	    result = parseJsonStr(eval(result),q);
	    $(combotreeid).combotree('loadData',eval(result));
		$(combotreeid).combotree('setText', q);
		return;
    }
}

//function childrensTree(combotreeid, rootstarget, datalist, q) {
//	children = $(combotreeid).combotree('tree').tree('getChildren', rootstarget);
//	//console.log(children);
//	alert(children.length);
//	for (j = 0; j < children.length; j++) {
//		alert(children[j].id);
//		if (q == children[j].text) {
//			$(combotreeid).combotree('tree').tree('select',
//					children[j].target);
//			return;
//		} else {
//			if (children[j].text.indexOf(q) >= 0 && children[j].text != "") {
//				var org = {
//					"id" : children[j].id,
//					"text" : children[j].text
//				};
//				datalist.push(org);
//			}
//		}
//		//childrensTree(combotreeid,children[j].target,datalist,q);
//	}
//}

var num = 0;
var treeAll;
function childrensTreeJson(combotreeid, rootstarget, key){
	//拿到所有的子节点
	var children = $(combotreeid).combotree('tree').tree('getChildren', rootstarget);
	if(num == 0){
		treeAll = children;
		num ++; 
	}
	children = treeAll;
	
	var jsonStr = '[';
	var preNode = 'null';
	for (var j = 0; j < children.length; j++) {
		if(children[j].id.indexOf('a') == 0 && children[j].text.indexOf(key) == -1){
			continue;
		}
		//集群
		if(children[j].id.indexOf('c:') == 0 ){
			if(preNode == 'null'){
			}else if(preNode == 'c:'){
				jsonStr += ']},';
			}else if(preNode == 'g:'){
				jsonStr += ']}]},';
			}else if(preNode == 'a:'){
				jsonStr += ']}]}]},';
			}
			preNode = 'c:';
		}else if(children[j].id.indexOf('g:') == 0){//应用组
			if(preNode == 'null'){
			}else if(preNode == 'c:'){
			}else if(preNode == 'g:'){
				jsonStr += ']},';
			}else if(preNode == 'a:'){
				jsonStr += ']}]},';
			}
			preNode = 'g:';
		}else if(children[j].id.indexOf('a:') == 0){//应用
			if(preNode == 'null'){
			}else if(preNode == 'c:'){
			}else if(preNode == 'g:'){
			}else if(preNode == 'a:'){
				jsonStr += ']},';
			}
			preNode = 'a:';
		}
		jsonStr += '{';
		jsonStr += 'id:"' + children[j].id + '",text:"' + children[j].text;
		jsonStr += '",children: [';
	}
	//最后一个节点不同
	if(preNode == 'null'){
		jsonStr += ']';
	}
	if(preNode == 'c:'){
		jsonStr += ']}]';
	}else if(preNode == 'g:'){
		jsonStr += ']}]}]';
	}else if(preNode == 'a:'){
		jsonStr += ']}]}]}]';
	}
	return jsonStr;
}

function parseJsonStr(arr,key){
		var gnum3 = 0;
		var jsonStr3 = '[';
		for(var i=0; i<arr.length; i++){//集群
			var arr2 = arr[i].children;
			var gnum2 = 0;
			var jsonStr2 = 'children:[';
			for(var j=0; j<arr2.length; j++){//应用组
				var arr3 = arr2[j].children;
				var gnum1 = 0;
				var jsonStr1 = 'children:[';
				for(var z=0; z<arr3.length;z++){//应用
					if(gnum1==0){
						jsonStr1 += "{id:'"+arr3[z].id+"',text:'"+arr3[z].text+"',children:[]}";
					}else{
						jsonStr1 += ",{id:'"+arr3[z].id+"',text:'"+arr3[z].text+"',children:[]}";
					}
					gnum1 ++;
				}
				jsonStr1 += ']';
				if(gnum1 > 0 || arr2[j].text.indexOf(key) > -1){//有子节点 或者 应用组本身包含搜索字符串
					if(gnum2 == 0 ){
						jsonStr2 += "{id:'"+arr2[j].id+"',text:'"+arr2[j].text+"',"+jsonStr1+"}";
					}else{
						jsonStr2 += ",{id:'"+arr2[j].id+"',text:'"+arr2[j].text+"',"+jsonStr1+"}";
					}
					gnum2 ++;
				}
			}
			jsonStr2 += ']';
			if(gnum2 > 0 || arr[i].text.indexOf(key) > -1){
				if(gnum3 == 0){
					jsonStr3 += "{id:'"+arr[i].id+"',text:'"+arr[i].text + "',"+jsonStr2+"}";
				}else{
					jsonStr3 += ",{id:'"+arr[i].id+"',text:'"+arr[i].text + "',"+jsonStr2+"}";
				}
				gnum3++;
			}
		}
		jsonStr3 += ']';
		return jsonStr3;
	}