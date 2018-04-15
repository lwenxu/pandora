package com.ibeetl.starter.workflow.model;

import java.util.List;

public class WfTaskSpec {
	List<WfTaskButton> buttons = null;
	WfUserSpec userSpec = null;
	
	public List<WfTaskButton> getButtons() {
		return buttons;
	}
	public void setButtons(List<WfTaskButton> buttons) {
		this.buttons = buttons;
	}
	public WfUserSpec getUserSpec() {
		return userSpec;
	}
	public void setUserSpec(WfUserSpec userSpec) {
		this.userSpec = userSpec;
	}
	
	
}
