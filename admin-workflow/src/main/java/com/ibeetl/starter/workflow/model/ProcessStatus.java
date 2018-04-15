package com.ibeetl.starter.workflow.model;

public enum ProcessStatus {
	NEW(1),RUNNING(1), COMPLETED(2), CALLBACK(3), CANCEL(4), DELETE(5);
	private int status = 0;
	private ProcessStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
