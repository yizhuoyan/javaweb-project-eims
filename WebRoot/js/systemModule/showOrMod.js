/**
 * 角色修改
 * @param form
 * @returns
 */
var systemModuleFormSubmit=function(form){
	
	var queryString=[];
	queryString.push("id="+form.id.value);
	var name=form.name.value;
	queryString.push("name="+name);
	var url=form.url.value;
	queryString.push("url="+url);
	var icon=form.icon.value;
	queryString.push("icon="+icon);
	var stateRadios=$("@state");
	var state;
	for(var i=0;i<stateRadios.length;i++){
		if(stateRadios[i].checked){
				state=stateRadios[i].value;
				break;
		}
		
	}
	queryString.push("state="+state);
	var remark=form.remark.value;
	queryString.push("remark="+remark);
	var url=form.action;
	$.ajax(url,function(){
		var jsonStr=this.responseText;
		var responce=eval("("+jsonStr+")");
		alert(responce.message);
		//获取父窗口的传递的对话框参数（完成时的回调）
		var callback=window.dialogArguments;
		//如果存在回调，则执行回调
		if(callback){
			callback(responce.state);
		}
		if(responce.state){//表示添加成功
			//关闭当前窗口
			window.close();		
		}
		
	}).post(queryString.join("&"));
	
};

