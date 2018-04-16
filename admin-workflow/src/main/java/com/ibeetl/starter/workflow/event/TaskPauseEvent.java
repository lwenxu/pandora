package com.ibeetl.starter.workflow.event;

public class TaskPauseEvent {
	String taskId;
	String taskName;
	String taskInsId;
	String processKey;
	String processName;
	String processInsId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskInsId() {
		return taskInsId;
	}
	public void setTaskInsId(String taskInsId) {
		this.taskInsId = taskInsId;
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
	public String getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	@Override
	public String toString() {
		return "TaskPauseEvent [taskId=" + taskId + ", taskName=" + taskName + ", taskInsId=" + taskInsId
				+ ", processKey=" + processKey + ", processName=" + processName + ", processInsId=" + processInsId
				+ "]";
	}
	
	
}
