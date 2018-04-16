package com.ibeetl.starter.workflow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.ParallelGateway;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibeetl.starter.workflow.engine.CurrentTaskLocal;
import com.ibeetl.starter.workflow.engine.cmd.CommonTaskJumpCmd;
import com.ibeetl.starter.workflow.event.TaslClaimEvent;
import com.ibeetl.starter.workflow.exception.WfException;
import com.ibeetl.starter.workflow.kit.Constants;
import com.ibeetl.starter.workflow.model.InternalTaskStartInfo;
import com.ibeetl.starter.workflow.model.SimpleProcessProgress;
import com.ibeetl.starter.workflow.model.WfTaskButton;
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
	
	

	@Override
	public void complete(String taskInsId, WfUser wfUser, String comment, String title, String buttonCode,
			WfUser[] targetAcUsers, Map<String, Object> processVars, Map<String, Object> parameters) {
		InternalTaskStartInfo taskStartInfo = new InternalTaskStartInfo();
		taskStartInfo.setAssigner(wfUser);
		taskStartInfo.setButtonCode(buttonCode);
		taskStartInfo.setStartTaskInsId(taskInsId);
		//保存当前信息，供后续操作使用
		CurrentTaskLocal.setTaskInfo(taskStartInfo);
		Task currentTask = taskService.createTaskQuery().taskId(taskInsId).includeTaskLocalVariables().singleResult();
		if (currentTask == null) {
			throw new WfException("taskInsId=" + taskInsId + " 已经完成或者此代办不存在");
		}
		String procInsId = currentTask.getProcessInstanceId();
		FlowElement taskElement = this.getFlowElement(null, procInsId, currentTask.getTaskDefinitionKey());
		List<WfTaskButton> buttons = this.getButtonList(taskElement.getExtensionElements().get("toolbar").get(0));
		WfTaskButton button = this.findButtonByCode(buttons, buttonCode);
		
		if (processVars != null && processVars.size() != 0) {
			runtimeService.setVariablesLocal(procInsId, processVars);
		}

		if (parameters != null && parameters.size() != 0) {
			taskService.setVariablesLocal(taskInsId, parameters);
		}
		
		// 设置任务点击的按钮，可以用于后面的gateway(如果有) 走向判断
		taskService.setVariableLocal(taskInsId, Constants.VAR_BUTTON_CODE, buttonCode);
		taskService.addComment(taskInsId, procInsId, Constants.TYPE_COMMENT_AGREE, comment);
		
		if (Constants.BUTTON_TYPE_GENERAL.equals(button.getType())) {
			taskGeneral(currentTask, targetAcUsers, title);
		}else if (Constants.BUTTON_TYPE_JUMP.equals(button.getType())) {
			// 跳转到任意节点,如回退
			this.taskJump(taskInsId, button.getTargetRef(), wfUser, targetAcUsers[0], title, comment);
			return;
		}
		
		//更多流程
	}
	
	


	@Override
	public void complete(String taskInsId, WfUser wfUser, String buttonCode, WfUser targetWfUser) {
		this.complete(taskInsId, wfUser, "", "你有一个代办", buttonCode, new WfUser[] {targetWfUser}, null, null);

	}

	@Override
	public boolean claim(WfUser user, String taskInsId) {
		
		taskService.claim(taskInsId, user.getUserId());
		TaslClaimEvent claimEvent = new TaslClaimEvent();
		claimEvent.setTaskInsId(taskInsId);
		this.notifyService.taskClaim(claimEvent);
		return true;
	}

	@Override
	public WfTaskSpec getTaskSpec(String processKey, String processInsId, String taskId) {
		FlowElement taskElement = this.getFlowElement(processKey, processInsId, taskId);
		Map<String, List<ExtensionElement>> root = taskElement.getExtensionElements();

		Map processVars = null;
		if (StringUtils.isNotEmpty(processInsId)) {
			processVars = this.runtimeService.getVariables(processInsId);
		}
		ExtensionElement toolBar = root.get("toolbar").get(0);
		List<WfTaskButton> buttonList = getButtonList(toolBar, processVars);
		for (WfTaskButton button : buttonList) {
			String refTask = button.getTargetRef();
			if (StringUtils.isEmpty(refTask)) {
				// 结束按钮
				continue;
			} else {
				// 如果按奶没有设定目标task，则自动寻找，通过outgoing
				List<WfUserSpec> userSpecs = this.getOutgoingTaskUserDefine(processKey, processInsId, refTask);
				button.setTargetUserSpecs(userSpecs);
			}
		}

		WfUserSpec curretTaskSpec = getTaskUserDefine(processKey, processInsId, taskId);
		WfTaskSpec taskSpec = new WfTaskSpec();
		taskSpec.setButtons(buttonList);
		taskSpec.setUserSpec(curretTaskSpec);
		return taskSpec;
	}

	@Override
	public WfUserSpec getTaskUserDefine(String processKey, String processInsId, String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WfUser getHistoryWfUser(String procInsId, String taskId) {
		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().finished()
				.processInstanceId(procInsId).taskDefinitionKey(taskId).orderByTaskCreateTime().desc()
				.includeTaskLocalVariables().list();
		if (tasks.size() == 0) {
			throw new WfException("procInsId=" + procInsId + ",taskId=" + taskId + " 还未被执行");
		}
		HistoricTaskInstance task = tasks.get(0);

		String assignee = task.getAssignee();
		Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
		String roleId = (String) taskLocalVariables.get(Constants.VAR_TASK_ROLE);
		String orgId = (String) taskLocalVariables.get(Constants.VAR_TASK_ORG);
		WfUser wfUser = new WfUser();
		wfUser.setUserId(assignee);
		wfUser.setRoleId(roleId);
		wfUser.setOrgId(orgId);
		return wfUser;
	}

	@Override
	public WfUser getHistoryProcessOwnerWfUser(String procInsId) {
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInsId)
				.orderByTaskCreateTime().asc().includeTaskLocalVariables().listPage(0, 1);

		if (list.size() == 0) {
			throw new WfException("procInsId=" + procInsId + " 不存在");
		}

		HistoricTaskInstance ins = list.get(0);

		Map map = ins.getTaskLocalVariables();
		String userId = (String) ins.getAssignee();
		String roleId = (String) map.get(Constants.VAR_TASK_ROLE);
		String orgId = (String) map.get(Constants.VAR_TASK_ORG);

		WfUser wfUser = new WfUser();
		wfUser.setRoleId(roleId);
		wfUser.setUserId(userId);
		wfUser.setOrgId(orgId);
		return wfUser;
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
		BpmnModel model = this.getBpmnMode(null, processInsId);
		FlowNode currentElement = (FlowNode) model.getMainProcess().getInitialFlowElement();
		List<String> names = new ArrayList<String>();
		List<String> ids = new ArrayList<String>();
		while (!currentElement.getOutgoingFlows().isEmpty()) {
			//未考虑分支情况，TODO
			if (currentElement.getOutgoingFlows().size() != 1) {
				throw new WfException("不支持的流程类型 " + model.getMainProcess().getId());
			}
			SequenceFlow defaultFlow = currentElement.getOutgoingFlows().get(0);

			FlowElement defaultElement = defaultFlow.getTargetFlowElement();
			if (defaultElement instanceof UserTask) {
				String taskName = defaultElement.getName();
				String id = defaultElement.getId();
				if (ids.contains(id)) {
					throw new WfException("环节名称重复或者循环" + model.getMainProcess().getId());
				}
				names.add(taskName);
				ids.add(id);

				currentElement = (FlowNode) defaultElement;
			} else if (defaultElement instanceof EndEvent) {
				// 结束
				break;
			} else if (defaultElement instanceof SubProcess) {
				// 未来考虑支持SubProcess
				throw new WfException("不支持的流程类型 " + model.getMainProcess().getId());
			} else {
				throw new WfException(
						"不支持的流程类型 " + model.getMainProcess().getId() + " nodeId=" + defaultElement.getId());
			}
		}
		SimpleProcessProgress progress = new SimpleProcessProgress();
		progress.setTaskNames(names);
		progress.setTaskIds(ids);
		if (StringUtils.isNotEmpty(processInsId)) {
			List<Task> list = this.taskService.createTaskQuery().processInstanceId(processInsId).orderByTaskCreateTime()
					.desc().listPage(0, 1);
			if (list.isEmpty()) {
				// 未找到，认为流程已经完成
				progress.setComplete(true);
				return progress;
			}
			Task currentTask = list.get(0);
			String currentId = currentTask.getTaskDefinitionKey();
			for (int i = 0; i < ids.size(); i++) {
				String taskId = ids.get(i);
				if (currentId.equals(taskId)) {
					progress.setCurrentIndex(i);
					progress.setCurrentTaskName(names.get(i));
					break;
				}
			}
		}

		return progress;
	}

	@Override
	public void setTaskAssignee(TaskInfo task, WfUser assignee, String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isProcessCompleted(String taskInsId) {
		TaskInfo taskInfo = this.historyService.createHistoricTaskInstanceQuery().taskId(taskInsId).singleResult();
		String processInsId = taskInfo.getProcessInstanceId();
		List<ProcessInstance> list = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInsId)
				.list();
		return list.isEmpty();
	}
	
	
	/**
	 * 获取taskId对应的定义，如果processId为空，取最新的流程定义，否则，取流程特定版本的定义。
	 * 
	 * @param processKey
	 * @param processId
	 * @param taskKey
	 * @return
	 */
	protected FlowElement getFlowElement(String processKey, String processInsId, String taskId) {
		ProcessDefinition pd = null;
		if (StringUtils.isEmpty(processInsId)) {
			// 取定义
			pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion()
					.singleResult();
		} else {
			String processId = this.getProcessId(processInsId);
			pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processId).singleResult();
			processKey = pd.getKey();
		}
		BpmnModel model = repositoryService.getBpmnModel(pd.getId());
		FlowElement target = model.getFlowElement(taskId);
		
		if (target == null) {
			throw new WfException("环节不存在 " + processKey + " 环节 " + taskId);
		}
		return target;
	}
	
	protected String getProcessId(String processInsId) {
		ProcessInstance processIns = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInsId)
				.singleResult();
		String processId = null;
		if (processIns != null) {
			processId = processIns.getProcessDefinitionId();

		} else {
			HistoricProcessInstance his = this.historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInsId).singleResult();
			processId = his.getProcessDefinitionId();

		}
		return processId;
	}
	
	

	/**
	 * 获得按钮定义
	 * 
	 * @param toolBar
	 * @return
	 */
	private List<WfTaskButton> getButtonList(ExtensionElement toolBar) {
		return this.getButtonList(toolBar, null, true);
	}

	/**
	 * 提供流程参数，获得按钮定义
	 * 
	 * @param toolBar
	 * @param processVars
	 * @return
	 */
	private List<WfTaskButton> getButtonList(ExtensionElement toolBar, Map processVars) {
		return this.getButtonList(toolBar, processVars, false);
	}

	private List<WfTaskButton> getButtonList(ExtensionElement toolBar, Map processVars, boolean skipEval) {
		//TODO,目前还不具备动态获取处理信息
		Map<String, List<ExtensionElement>> child = toolBar.getChildElements();
		List<ExtensionElement> list = child.get("button");
		List<WfTaskButton> buttonList = new ArrayList<WfTaskButton>();
		for (ExtensionElement ee : list) {
			String code = ee.getAttributeValue(null, "code");
			String name = ee.getAttributeValue(null, "name");
			String displayName = ee.getAttributeValue(null, "displayName");
			String targetRef = ee.getAttributeValue(null, "targetRef");
			String displayCss = ee.getAttributeValue(null, "displayCss");
			String strategy = ee.getAttributeValue(null, "strategy");
			String type = ee.getAttributeValue(null, "type");
			List<ExtensionElement> paramList = ee.getExtensionElements().get("para");

			Map<String, String> strategyParameter = new HashMap<String, String>();
			if (paramList != null) {
				for (ExtensionElement paramter : paramList) {
					String key = paramter.getAttributeValue(null, "key");
					String value = paramter.getAttributeValue(null, "value");
					strategyParameter.put(key, value);
				}
			}

			WfTaskButton button = new WfTaskButton();
			button.setName(name);
			button.setType(type);
			button.setDisplayName(displayName);
			button.setDisplayCss(displayCss);
			button.setStrategy(strategy);
			button.setTargetRef(targetRef);
			button.setStrategyParameter(strategyParameter);

			buttonList.add(button);

		}
		return buttonList;
	}
	
	
	private WfTaskButton findButtonByCode(List<WfTaskButton> buttons, String buttonCode) {
		for (WfTaskButton button : buttons) {
			if (button.getName().equals(buttonCode)) {
				return button;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (WfTaskButton button : buttons) {
			sb.append(button.getName()).append(",");
		}
		sb.setLength(sb.length() - 1);

		throw new WfException("buttonCode未发现:" + buttonCode + ",期望是 " + sb.toString());
	}

	

	protected void taskGeneral(Task currentTask, WfUser[] targetAcUsers, String title) {
		// 顺序流程，或者汇聚
		String taskInsId = currentTask.getId();
		this.taskService.complete(taskInsId, null);
		Task t = taskService.createTaskQuery().executionId(currentTask.getExecutionId())
				.taskVariableValueEquals(Constants.VAR_LAST_TASK_ID, taskInsId).singleResult();
		if (t == null) {
			// 没有代办(或者分支汇聚)或者流程结束
			return;
		}

		if (t.getAssignee() == null) {
			//流程没有自动指定执行人，采用通常的业务侧指定
			WfUser assignee = targetAcUsers[0];
			this.setTaskAssignee(t, assignee, title);
		} 

		return;
	}
	
	
	protected void taskJump(String currentTaskInsId, String targetTaskId, final WfUser acUser, final WfUser targetUser,
			final String title, String comment) {

		CommonTaskJumpCmd cmd = new CommonTaskJumpCmd(this, currentTaskInsId, targetTaskId, targetUser, title);
		  org.flowable.engine.ManagementService managementService = processEngine.getManagementService();  
		  managementService.executeCommand(cmd);
	}
	

	protected String getProcessKey(String processDefineId) {
		// simple-workflow:4:40007 -->simple-workflow
		String[] arrays = processDefineId.split(":");
		return arrays[0];
	}
	
	
	protected List<WfUserSpec> getOutgoingTaskUserDefine(String processKey, String processId, String refer) {
		if (refer.equals(Constants.TASK_END)) {
			return Collections.EMPTY_LIST;
		}
		FlowElement out = (FlowElement) this.getFlowElement(processKey, processId, refer);

		List<WfUserSpec> list = new ArrayList<WfUserSpec>();
		if (out instanceof Activity) {
			WfUserSpec spec = this.getTaskUserDefine(processKey, processId, out.getId());
			list.add(spec);
			return list;
		} else if (out instanceof SequenceFlow) {
			Activity activity = (Activity) ((SequenceFlow) out).getTargetFlowElement();
			WfUserSpec spec = this.getTaskUserDefine(processKey, processId, activity.getId());
			list.add(spec);
			return list;
		} else if (out instanceof ParallelGateway) {
			for (SequenceFlow flow : ((ParallelGateway) out).getOutgoingFlows()) {
				Activity activity = (Activity) flow.getTargetFlowElement();
				list.add(this.getTaskUserDefine(processKey, processId, activity.getId()));
			}
			return list;
		} else {
			throw new UnsupportedOperationException("不支持的类型 " + out);
		}

	}

	protected BpmnModel getBpmnMode(String processKey, String processInsId) {
		String processId = null;
		if (StringUtils.isEmpty(processInsId)) {
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey)
					.latestVersion().singleResult();
			processId = pd.getId();
		} else {
			ProcessInstance processIns = this.runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInsId).singleResult();

			if (processIns != null) {
				processId = processIns.getProcessDefinitionId();

			} else {
				HistoricProcessInstance his = this.historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInsId).singleResult();
				processId = his.getProcessDefinitionId();

			}

		}
		BpmnModel model = repositoryService.getBpmnModel(processId);
		return model;

	}
	
	
	private void notifyProcessStart(String processKey,String processInsId,WfUser startUser,Map paras,Task firstTask) {
		
	}

}
