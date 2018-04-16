package com.ibeetl.starter.workflow.event;

public class ProcessPauseEvent {
	String processInsId;
	String processKey;
	String processName;
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
	@Override
	public String toString() {
		return "ProcessPauseEvent [processInsId=" + processInsId + ", processKey=" + processKey + ", processName="
				+ processName + "]";
	}
	
	
}
