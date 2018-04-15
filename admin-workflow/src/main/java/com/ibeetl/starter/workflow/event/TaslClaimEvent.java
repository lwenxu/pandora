package com.ibeetl.starter.workflow.event;

public class TaslClaimEvent {
	private String taskInsId;

	public String getTaskInsId() {
		return taskInsId;
	}

	public void setTaskInsId(String taskInsId) {
		this.taskInsId = taskInsId;
	}

	@Override
	public String toString() {
		return "TaslClaimEvent [taskInsId=" + taskInsId + "]";
	}
	
}
