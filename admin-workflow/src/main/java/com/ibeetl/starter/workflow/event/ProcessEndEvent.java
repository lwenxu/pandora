package com.ibeetl.starter.workflow.event;

import com.ibeetl.starter.workflow.model.ProcessStatus;
import com.ibeetl.starter.workflow.model.WfUser;

public class ProcessEndEvent {
	String processInsId;
	//是否正常结束
	Integer status = ProcessStatus.COMPLETED.getStatus();
	
	WfUser endUser = null;
	String endButtonCode;
	public String getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public WfUser getEndUser() {
		return endUser;
	}
	public void setEndUser(WfUser endUser) {
		this.endUser = endUser;
	}
	public String getEndButtonCode() {
		return endButtonCode;
	}
	public void setEndButtonCode(String endButtonCode) {
		this.endButtonCode = endButtonCode;
	}
	@Override
	public String toString() {
		return "ProcessEndEvent [processInsId=" + processInsId + ", status=" + status + ", endUser=" + endUser
				+ ", endButtonCode=" + endButtonCode + "]";
	}
	
	
	
	
}
