/**
 * 字符串常用方法
 */
String.prototype.trim=function(){
	return this.replace(/^\s+/, '').replace(/\s+$/, '');
}
String.prototype.isInteger=function(){
		var str=this.trim();
    	return new Number(parseInt(str)).toString()===str;
}
String.prototype.isFloat=function(){
		var str=this.trim();
    	
    	var length=str.length;
    	if(str.indexOf(".")===str.lastIndexOf (".")){
    	    while(str.charAt(--length)==='0'||str.charAt(length)==='.'){
    			 str=str.substring(0,length);
    	    }
    	}
    	return new Number(parseFloat(str)).toString()===str;
}
String.prototype.isNone=function(){
	return this.trim().length===0;
}

String.isNone=function(str){
	
	return (typeof str==="undefined")||str===null||str.trim().length===0;
}

/**
 * 获取dom元素
 * @param target 可传入id，tagName，name等
 * id -- 直接传入id值
 * name--参数格式为@name
 * tagName--传入"<div>";
 * @param doc 文档对象，默认为当前窗口的document
 * @returns 返回dom元素，多个返回数组
 */
var $=function(target,doc){
	if(typeof target!=="string"){
		return target;
	}
	var $result;
	doc=doc||document;
	if(target.charAt(0)==="@"){
		$result=doc.getElementsByName(target.substr(1));
		return $result
	}else if(target.charAt(0)==="<"&&target.charAt(target.length-1)===">"){
		$result=doc.getElementsByTagName(target.substring(1,target.length-1));
		return $result;
	}else{
		return doc.getElementById(target);
	}
};

/**
 * 事件绑定工具方法,定义在$变量上
 * @param $node 事件注册对象
 * @param type 事件类型
 * @param callback 事件处理方法引用
 * @returns
 */
$.bind=function($node,sType,fCallback){
	//转换事件类型为全部小写
	var sIEType=(sType=sType.toLowerCase());
	//如果是ie
	if($node.attachEvent){
		$node.attachEvent((sType.charAt(0)==='o'&&sType.charAt(1)==="n")?sType:"on"+sType,fCallback);
	}else if($node.addEventListener){//如果非ie
		$node.addEventListener((sType.charAt(0)==='o'&&sType.charAt(1)==="n")?sType.substr(2):sType,fCallback);
	}
};
/**
 * 取消事件绑定工具方法
 * @param e 事件注册对象
 * @param type 事件类型
 * @param callback 事件处理方法引用
 * @returns
 */
$.unbind=function($node,sType,fCallback){
	//转换事件类型为全部小写
	var sIEType=(sType=sType.toLowerCase());
	//如果是ie
	if($node.detachEvent){
		$node.attachEvent((sType.charAt(0)==='o'&&sType.charAt(1)==="n")?sType:"on"+sType,fCallback);
	}else if($node.addEventListener){//如果非ie
		$node.removeEventListener((sType.charAt(0)==='o'&&sType.charAt(1)==="n")?sType.substr(2):sType,fCallback);
	}
};


/**
* 摇晃指定节点
*@param $node 节点对象
*/
$.shake = function($node) {
	if(!$node)return;
	var style = $node.style;
	var p = [ 4, 8, 4, 0, -4, -8, -4, 0 ];
	var fx = function() {
		style.marginLeft = p.shift() + "px";
		if (p.length <= 0) {
			style.marginLeft = 0;
			clearInterval(timerId);
		}
	};
	p = p.concat(p.concat(p));
	timerId = setInterval(fx, 13);
};

/**
 * 用于提交ajax请求 
 */
$.ajax=function(surl,fwhenSucceed,fwhenOther){
	return new Ajax(surl,fwhenSucceed,fwhenOther);
}
$.window=function(url,param,style){
	//获取页面base标签
	var $base=$("<base>")[0];
	//构建新增用户页面地址
	var sUrl=$base.href+url;
	var styleString=null;
	var height="300px";
	var width="400px";
	if(style){
		 height=style.height||height;
		 width=style.width||width;
	}
	window.showModalDialog(sUrl,param,"dialogHeight="+height+";dialogWidth="+width+";help=false;resizable=no;status=no");
	
};
//封装ajax
/**
 * @param surl 请求的路径
 * @param fwhenSucceed 请求成功的回调方法
 * @param fwhenOther 请求其他状态的回调方法
 */
var Ajax=function(surl,fwhenSucceed,fwhenOther){
	//AJAX 主要用到的对象
	this.xmlhttp = null;
	//保存请求路径
	this.url=surl;
	//保存请求成功的回调方法
	this.whenSucceed=fwhenSucceed;
	//保存其他状态的回调方法
	this.whenOther=fwhenOther;
	//创建XHR对象
	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		this.xhr = new XMLHttpRequest();
	} else {
		// code for IE6, IE5
		this.xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	//设置监听方法
	var ajax=this;
	this.xhr.onreadystatechange=function() {
		//请求就绪 调用成功的回调方法
		if (this.readyState == 4 && this.status == 200) {
			if(ajax.whenSucceed){
				
				ajax.whenSucceed.call(this);
			}
		}else{
			//其他状态
			if(ajax.whenOther){
				ajax.whenOther.call(this);
			}
		}
	};
};
/**
 * get方法请求
 * @param bAsyn 是否同步
 */
Ajax.prototype.get=function(bAsyn){
	if(this.url!=null){
		
		this.xhr.open("GET", encodeURI(encodeURI(this.url)), !bAsyn);
	}
	//提交请求
	this.xhr.send();
};
/**
 * post方法提交请求
 * @param squeryString 提交参数 字符串
 * @param bAsyn 是否同步
 */
Ajax.prototype.post=function(squeryString,bAsyn){
	if(this.url!=null){
		this.xhr.open("POST", this.url, !bAsyn);
	}
	//设置请求头，模拟form表单提交
	this.xhr.setRequestHeader(
			"Content-type","application/x-www-form-urlencoded");
	//提交请求
	this.xhr.send(encodeURI(encodeURI(squeryString)));
};


var fCloseWindow=function(){
	window.opener=null;
	window.close();
};
/*------------------------分页----------------------------------------*/

/***
 * 页面跳转
 * @param iPageNo 当前页码
 * @param iPageSize 分页大小
 * @returns void
 */
var fJumpPage=function(iPageNo,iPageSize){
	//如果未传入当前页码，则取页面元素默认值
	iPageNo=iPageNo||$("jumpPageNoInput").value;
	//如果未传入分页大小，则取页面元素默认值
	iPageSize=iPageSize||$("pageSizeInput").value;
	//改变查询表单页码input的值
	$("@pageNo").value=iPageNo;
	//改变查询表单分页大小input的值
	$("@pageSize").value=iPageSize;
	//提交form表单
	$("queryForm").submit();
};

var checkValid=function(input,maxPageNo){
	if(input.value==""){
		return;
	}
	var inputNo=parseInt(input.value);
	if(isNaN(inputNo)||inputNo>maxPageNo){
		alert("请输入合法页码");
		input.value="";
		return;
	}
}


