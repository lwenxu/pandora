package com.ibeetl.starter.workflow.model;

import java.util.List;
import java.util.Map;

/**
 *  任务按钮配置信息
 *
 */
public class WfTaskButton {

	//按钮名称
	String name;
	//按钮显示名称 
	String displayName;
	//按钮样式
	String displayCss;
	//目标任务ID
	String targetRef;
	//目标任务的期望用户类型。
	 List<WfUserSpec> targetUserSpecs;
	//取人规则
	String strategy;
	//取人规则补充参数
	Map<String,String> strategyParameter;
	//general: 普通按钮    finish 结束流程按钮  jump 跳转按钮，atuo，自动处理，比如汇聚
	String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTargetRef() {
		return targetRef;
	}
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public Map<String, String> getStrategyParameter() {
		return strategyParameter;
	}
	public void setStrategyParameter(Map<String, String> strategyParameter) {
		this.strategyParameter = strategyParameter;
	}
	public String getDisplayCss() {
		return displayCss;
	}
	public void setDisplayCss(String displayCss) {
		this.displayCss = displayCss;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<WfUserSpec> getTargetUserSpecs() {
		return targetUserSpecs;
	}
	public void setTargetUserSpecs(List<WfUserSpec> targetUserSpecs) {
		this.targetUserSpecs = targetUserSpecs;
	}

	
	
	
}
