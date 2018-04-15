package com.ibeetl.starter.workflow.service.impl;

import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibeetl.starter.workflow.engine.CurrentTaskLocal;
import com.ibeetl.starter.workflow.exception.WfException;
import com.ibeetl.starter.workflow.model.InternalTaskStartInfo;
import com.ibeetl.starter.workflow.model.SimpleProcessProgress;
import com.ibeetl.starter.workflow.model.WfTaskSpec;
import com.ibeetl.starter.workflow.model.WfUser;
import com.ibeetl.starter.workflow.model.WfUserSpec;
import com.ibeetl.starter.workflow.service.WfNotifyService;
import com.ibeetl.starter.workflow.service.WfTaskService;

public class WfTaskServiceImpl implements WfTaskService {

	@Autowired
	protected TaskService taskService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected HistoryService historyService;

	@Autowired
	protected IdentityService identityService;

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	private ProcessEngine processEngine;

	

	@Autowired
	protected WfNotifyService notifyService;
	
	@Override
	public String start(String procKey, WfUser startUser,Map paras) {
		identityService.setAuthenticatedUserId(startUser.getUserId());
		// 启动流程并返回流程实体
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceByKey(procKey);
		String procInsId = startProcessInstanceById.getId();
		// 完成第一个节点，通常是填报节点
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(startProcessInstanceById.getId()).list();
		if (tasks.size() != 1) {
			throw new WfException("错误流程配置，不允许开始有多个节点 " + procKey);
		}
		Task firstTask = tasks.get(0);
		notifyProcessStart(procKey,procInsId,startUser,paras,firstTask);
		return firstTask.getId();
	}
	
	private void notifyProcessStart(String processKey,String processInsId,WfUser startUser,Map paras,Task firstTask) {
		
	}

	@Override
	public void complete(String taskInsId, WfUser acUser, String comment, String title, String buttonCode,
			WfUser[] targetAcUsers, Map<String, Object> processVars, Map<String, Object> parameters) {
		InternalTaskStartInfo taskStartInfo = new InternalTaskStartInfo();
		taskStartInfo.setAssigner(acUser);
		taskStartInfo.setButtonCode(buttonCode);
		taskStartInfo.setStartTaskInsId(taskInsId);
		CurrentTaskLocal.setTaskInfo(taskStartInfo);
		Task currentTask = taskService.createTaskQuery().taskId(taskInsId).includeTaskLocalVariables().singleResult();
		if (currentTask == null) {
			throw new WfException("taskInsId=" + taskInsId + " 已经完成或者此代办不存在");
		}
//		String procInsId = currentTask.getProcessInstanceId();
//		FlowElement taskElement = this.getFlowElement(null, procInsId, currentTask.getTaskDefinitionKey());
//		List<AcTaskButton> buttons = this.getButtonList(taskElement.getExtensionElements().get("toolbar").get(0));
//		AcTaskButton button = this.findButtonByCode(buttons, buttonCode);
//
//		// 设置任务点击的按钮，可以用于后面的gateway(如果有) 走向判断
//		Map<String, Object> taskVar = new HashMap<String, Object>();
//		taskVar.put("buttonCode", buttonCode);
//		if (processVars != null && processVars.size() != 0) {
//			runtimeService.setVariablesLocal(procInsId, processVars);
//		}
//
//		if (parameters != null && parameters.size() != 0) {
//			taskService.setVariablesLocal(taskInsId, parameters);
//		}
//
//		taskService.addComment(taskInsId, procInsId, Constants.TYPE_COMMENT_AGREE, comment);
//
//		if (Constants.BUTTON_TYPE_JUMP.equals(button.getType())) {
//			// 跳转到任意节点
//			this.taskJump(taskInsId, button.getTargetRef(), acUser, targetAcUsers[0], title, comment);
//			return;
//		} else if (Constants.BUTTON_TYPE_WAIT.equals(button.getType())) {
//			// 等待，其他按钮或者外部事件来真正完成
//			this.taskWait(taskInsId);
//			return;
//		} else if (Constants.BUTTON_TYPE_END_PROCESS.equals(button.getType())) {
//			// 流程结束
//			this.processEnd(taskInsId, comment, DeleteReason.PRAOCESS_CANCEL);
//			return;
//		} else if (Constants.BUTTON_TYPE_MUTILPLE.equals(button.getType())) {
//
//			this.taskMutipleTask(currentTask, targetAcUsers, title);
//
//			return;
//		} else if (Constants.BUTTON_TYPE_GENERAL.equals(button.getType())) {
//			taskGeneral(currentTask, targetAcUsers, title);
//		} else if (Constants.BUTTON_TYPE_PARALLEL.equals(button.getType())) {
//			// 并行流程
//			taskParallel(currentTask, targetAcUsers, title);
//
//		} else if (Constants.BUTTON_TYPE_AUTO.equals(button.getType())) {
//
//			taskAuto(currentTask);
//		} else {
//			throw new UnsupportedOperationException("不支持的按钮类型" + button.getType());
//		}

	}

	@Override
	public void complete(String taskInsId, WfUser wfUser, String buttonCode, WfUser targetWfUser) {
		this.complete(taskInsId, wfUser, "", "你有一个代办", buttonCode, new WfUser[] {targetWfUser}, null, null);

	}

	@Override
	public boolean claim(WfUser user, String taskInsId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WfTaskSpec getTaskSpec(String processKey, String processInsId, String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WfUserSpec getTaskUserDefine(String processKey, String processInsId, String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WfUser getHistoryWfUser(String procInsId, String taskKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WfUser getHistoryProcessOwnerWfUser(String procInsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delegateTask(String taskInsId, WfUser wfUsers, String comment) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean callBackTask(String taskInsId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processEnd(String procInsId, String deleteReason, Integer status) {
		// TODO Auto-generated method stub

	}

	@Override
	public SimpleProcessProgress getSimpleProgress(String processInsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaskAssignee(TaskInfo task, WfUser assignee, String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isProcessCompleted(String taskInsId) {
		// TODO Auto-generated method stub
		return false;
	}

}
