package com.ibeetl.starter.workflow.event;

import java.util.Map;

import com.ibeetl.starter.workflow.model.ProcessStatus;
import com.ibeetl.starter.workflow.model.WfUser;

public class ProcessStartEvent {
	String processInsId;
	String processKey;
	String processName;
	String taskInsId;
	WfUser user;
	Map processVars;
	public String getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Map getProcessVars() {
		return processVars;
	}
	public void setProcessVars(Map processVars) {
		this.processVars = processVars;
	}
	public String getTaskInsId() {
		return taskInsId;
	}
	public void setTaskInsId(String taskInsId) {
		this.taskInsId = taskInsId;
	}
	public WfUser getUser() {
		return user;
	}
	public void setUser(WfUser user) {
		this.user = user;
	}
	
	
	
	
}
