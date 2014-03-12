package com.zit.waeims.vo;

import java.util.Date;
/**
 * 用户对象
 * @author ben
 *
 */
public class UserVO {
	/**用户id*/
	private String id;
	/**用户登录账号*/
	private String account;
	/**用户名称*/
	private String name;
	/**用户密码*/
	private String password;
	/**创建时间*/
	private Date createTime;
	/**最后修改时间*/
	private Date lastModTime;
	/**密码最后修改时间*/
	private Date lastModPassTime;
	/**是否只读*/
	private boolean readOnly;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModTime() {
		return lastModTime;
	}
	public void setLastModTime(Date lastModTime) {
		this.lastModTime = lastModTime;
	}
	public Date getLastModPassTime() {
		return lastModPassTime;
	}
	public void setLastModPassTime(Date lastModPassTime) {
		this.lastModPassTime = lastModPassTime;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		readOnly = readOnly;
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
