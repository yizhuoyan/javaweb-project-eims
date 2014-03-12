package com.zit.waeims.vo;

import java.util.Date;
/**
 * 系统参数
 * @author ben
 *
 */
public class SystemParameterVO {
	/**id*/
	private String id;
	/**名称*/
	private String name;
	/**参数值*/
	private String value;
	/**创建时间*/
	private UserVO lastModUser;
	/**最后修改时间*/
	private Date lastModTime;
	/**用户状态  0停用 1启用  2删除*/
	private int state;
	/**备注*/
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UserVO getLastModUser() {
		return lastModUser;
	}
	public void setLastModUser(UserVO lastModUser) {
		this.lastModUser = lastModUser;
	}
	public Date getLastModTime() {
		return lastModTime;
	}
	public void setLastModTime(Date lastModTime) {
		this.lastModTime = lastModTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
