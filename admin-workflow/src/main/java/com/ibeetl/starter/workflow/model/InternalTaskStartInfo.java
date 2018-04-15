package com.ibeetl.starter.workflow.model;
/**
 * 任务启动带来一系列动作，此对象记录了出发后续动作的的信息集合
 * @author xiandafu
 *
 */
public class InternalTaskStartInfo {
	private String startTaskInsId ;
	private WfUser assigner ;
	private String buttonCode;
	private String comment;
	public WfUser getAssigner() {
		return assigner;
	}
	public void setAssigner(WfUser assigner) {
		this.assigner = assigner;
	}
	public String getButtonCode() {
		return buttonCode;
	}
	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
	public String getStartTaskInsId() {
		return startTaskInsId;
	}
	public void setStartTaskInsId(String startTaskInsId) {
		this.startTaskInsId = startTaskInsId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
