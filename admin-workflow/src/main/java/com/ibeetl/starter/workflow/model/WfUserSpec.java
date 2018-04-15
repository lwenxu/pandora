package com.ibeetl.starter.workflow.model;

/**
 * 流程参与人类型定义,role或者org 标识了这个参与者的特性
 *
 */
public class WfUserSpec implements java.io.Serializable {
	String roleId;
	String orgId;
	
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
}
