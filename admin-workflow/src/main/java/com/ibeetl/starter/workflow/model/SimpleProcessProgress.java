package com.ibeetl.starter.workflow.model;

import java.util.List;

/**
 * 流程进展，适合文本方式显示，现在还不支持并行文本显示，
 * @author xiandafu
 *
 */
public class SimpleProcessProgress {
	private List<String> taskNames;
	private List<String> taskIds;
	private String currentTaskName;
	private int currentIndex = -1;
	private boolean complete = false;
	
	public List<String> getTaskNames() {
		return taskNames;
	}
	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}
	public String getCurrentTaskName() {
		return currentTaskName;
	}
	public void setCurrentTaskName(String currentTaskName) {
		this.currentTaskName = currentTaskName;
	}
	
	
	public List<String> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
}
