package com.ibeetl.starter.workflow.event;

import com.ibeetl.starter.workflow.model.ProcessStatus;

/**
 * 任务结束
 * @author lijiazhi
 *
 */
public class TaskEndEvent {
	String taskId;
	Integer status = ProcessStatus.COMPLETED.getStatus();
	String comment;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "TaskEndEvent [taskId=" + taskId + ", status=" + status + ", comment="
				+ comment + "]";
	}
	
	
	
}
