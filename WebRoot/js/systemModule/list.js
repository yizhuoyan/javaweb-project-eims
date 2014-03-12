/**
 * 全选/全不选
 * @returns
 */
var fToggleCheckRow=function(evt){
	evt=evt||window.event;
	//获取事件源
	var $target=evt.srcElement||evt.target;
	//获取所有checkbox
	var $userCheckBoxs=$("@systemModuleCheckBox");
	for ( var i = 0; i <$userCheckBoxs.length; i++) {
		//如果不一样，则设置为一样
		if($userCheckBoxs[i].checked!==$target.checked){
			$userCheckBoxs[i].checked=$target.checked;
		}
	}
};

/**
 * 新增按钮点击事件处理
 * @returns void
 */
var fAddModule=function(){
	$.window("jsp/systemModule/add.jsp",function(bSuccessful){
		if(bSuccessful){//如果新增成功，则查询form表单重新提交
			$("queryForm").submit();	
		}
		
	});
};


/**
 * 删除按钮点击事件处理
 * @returns void
 */
var fDelModule=function(){
	
	//获取所用checkbox
	var $userCheckBoxs=$("@systemModuleCheckBox");
	//定义删除用户的数组
	var checksUserIds=[];
	for ( var i = 0; i <$userCheckBoxs.length; i++) {
		//如果用户被选中
		if($userCheckBoxs[i].checked){
			//保存到被删除用户数组，注意保存的是用户id
			checksUserIds.push($userCheckBoxs[i].value);
		}
	}
	
	//如果一个都没选中，则忽略此次点击事件
	if(checksUserIds.length==0){
		return;
	}
	//删除操作比较危险，我们一般会再一次确认此操作。
	if(!window.confirm("确认删除所选用户？")){
		//用户取消删除动作，直接返回。
		return;
	}
	var url="systemModule/delete.do";
	$.ajax(url,function(){
		var jsonStr=this.responseText;
		var responce=eval("("+jsonStr+")");
		alert(responce.message);
		if(responce.state){//表示操作成功
			$("queryForm").submit();	
		}
	}).post("ids="+checksUserIds.join(","));
	
};



/**
 * 查看或修改某个用户
 * @param sId 用户id
 */
var fShowOrMod=function(sId){
	var url="systemModule/show.do?id="+sId;
	$.window(url,function(bSuccessful){
		if(bSuccessful){//修改成功，刷新查询界面
			$("queryForm").submit();	
		}
		
	});
};


