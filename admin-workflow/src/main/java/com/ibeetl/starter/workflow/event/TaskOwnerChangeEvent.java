package com.ibeetl.starter.workflow.event;

import com.ibeetl.starter.workflow.model.WfUser;

public class TaskOwnerChangeEvent {
	private WfUser assignee;
	private String taskInsId;
	public WfUser getAssignee() {
		return assignee;
	}
	public void setAssignee(WfUser assignee) {
		this.assignee = assignee;
	}
	public String getTaskInsId() {
		return taskInsId;
	}
	public void setTaskInsId(String taskInsId) {
		this.taskInsId = taskInsId;
	}
	@Override
	public String toString() {
		return "TaskOwnerChangeEvent [assignee=" + assignee + ", taskInsId=" + taskInsId + "]";
	}
	
}
