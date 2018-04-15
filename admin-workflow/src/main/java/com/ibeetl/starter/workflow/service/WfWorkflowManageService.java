package com.ibeetl.starter.workflow.service;

import java.util.Map;

import com.ibeetl.starter.workflow.model.WfUser;


/**
 * 管理员接口，用于管理流程，以及实现业务上的任性
 * @author xiandafu
 *
 */
public interface WfWorkflowManageService {
	/**
	 * 所有运行的流程切换到processId 这个版本
	 * @param processKey
	 * @param processInsId
	 */
	public void overwriteAll(String processKey,String processInsId);
	
	/**
	 * 切换所有流产到最新版本
	 * @param processKey
	 */
	public void overwriteAll(String processKey);
	
	/**
	 * 多实例节点（子流程）增加一个新的参数人，也适合subProcess
	 * @param refTaskInsId  参考的代办，建立并行的另外一个代办
	 * @param user
	 * @param title
	 */
	public void addAssignee(String refTaskInsId,WfUser user,String title);
	
	/**
	 * 删除多示例节点,也适合subProcess
	 * @param taskInsId
	 */
	public void removeAssignee(String taskInsId);
	
	
	/**
	 * 删除其他代办，直接设置新的代办为历史上的某个环节 ,如果user为空，则设置历史执行人
	 * 
	 */
	public boolean reset(String executionId,String refTaskInsId,WfUser user);
	
	
	/**
	 * 挂起流程
	 * @param processDefinitionKey
	 */
	public void suspendProcessByKey(String processDefinitionKey,boolean includeIns);
	
	/**
	 * 恢复流程
	 * @param processDefinitionKey
	 */
	public void activateProcessByKey(String processDefinitionKey);
	
	/**
	 * 重新设置流程参数
	 * @param processInsId
	 * @param paras
	 */
	public void setProcessParameters(String processInsId,Map paras);
	
	/**
	 *  重新设置流程执行人
	 * @param taskInsId
	 * @param assignee
	 * @param title
	 */
	public void setTaskAssignee(String taskInsId,WfUser assignee,String title);
}
