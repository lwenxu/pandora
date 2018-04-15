package com.ibeetl.starter.workflow.model;

/**
 * 流程参与人
 *
 */
public class WfUser extends WfUserSpec{
	String userId;
	
	public String getUserId() {
		return userId;
	}
	public WfUser() {
		
	}
	
	public WfUser(String userId,String orgId,String roleId) {
		this.userId = userId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
	

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	@Override
	public String toString() {
		return userId+"("+this.orgId+")" ;
	}
	
}
