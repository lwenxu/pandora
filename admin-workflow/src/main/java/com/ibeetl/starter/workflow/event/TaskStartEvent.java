package com.ibeetl.starter.workflow.event;

import java.util.Map;

import com.ibeetl.starter.workflow.model.WfUser;

/**
 * 任务开始
 * @author lijiazhi
 *
 */
public class TaskStartEvent {
	WfUser assignee;
	String taskId;
	String taskName;
	WfUser assigner;
	String lastTaskId;
	String lastTaskName;
	String processKey;
	String processName;
	String processInsId;
	String buttonCode;
	String url;
	String title;
	//流程是否开始
	boolean processStart;
	//流程参数
	Map processVars;
	public WfUser getAssignee() {
		return assignee;
	}
	public void setAssignee(WfUser assignee) {
		this.assignee = assignee;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public WfUser getAssigner() {
		return assigner;
	}
	public void setAssigner(WfUser assigner) {
		this.assigner = assigner;
	}
	public String getLastTaskId() {
		return lastTaskId;
	}
	public void setLastTaskId(String lastTaskId) {
		this.lastTaskId = lastTaskId;
	}
	public String getLastTaskName() {
		return lastTaskName;
	}
	public void setLastTaskName(String lastTaskName) {
		this.lastTaskName = lastTaskName;
	}
	public String getButtonCode() {
		return buttonCode;
	}
	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
	
	
	public String getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	
	
	public boolean isProcessStart() {
		return processStart;
	}
	public void setProcessStart(boolean processStart) {
		this.processStart = processStart;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Map getProcessVars() {
		return processVars;
	}
	public void setProcessVars(Map processVars) {
		this.processVars = processVars;
	}
	@Override
	public String toString() {
		return "TaskStartEvent [assignee=" + assignee+", taskName=" + taskName + ", taskId=" + taskId +  ", assigner="
				+ assigner + ", lastTaskId=" + lastTaskId + ", lastTaskName=" + lastTaskName + ", processKey="
				+ processKey + ", processName=" + processName + ", processInsId=" + processInsId + ", buttonCode="
				+ buttonCode + ", url=" + url + ", title=" + title + ", processStart=" + processStart + "]";
	}
	
	
}
