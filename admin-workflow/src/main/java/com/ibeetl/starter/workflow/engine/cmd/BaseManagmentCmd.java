package com.ibeetl.starter.workflow.engine.cmd;

import java.io.Serializable;
import java.util.Map;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.common.impl.interceptor.Command;
import org.flowable.engine.common.impl.interceptor.CommandContext;
import org.flowable.engine.impl.history.HistoryManager;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityManager;

import com.ibeetl.starter.workflow.kit.Constants;
import com.ibeetl.starter.workflow.model.WfUser;
import com.ibeetl.starter.workflow.service.WfTaskService;



public abstract class BaseManagmentCmd implements Command, Serializable{

	protected ExecutionEntityManager  executionManager;
	protected TaskEntityManager taskEntityManager;
	protected HistoryManager historyManager;
	protected WfTaskService service;
	protected TaskService taskService;
	protected RuntimeService runtimeService;
	
	public BaseManagmentCmd(WfTaskService service) {
		this.service = service;
	}
	
	@Override
	public Object execute(CommandContext commandContext) {
		executionManager = CommandContextUtil.getExecutionEntityManager();
		taskEntityManager = CommandContextUtil.getTaskServiceConfiguration().getTaskEntityManager();
		historyManager = CommandContextUtil.getHistoryManager();
		taskService = CommandContextUtil.getTaskService();
		runtimeService = CommandContextUtil.getProcessEngineConfiguration().getRuntimeService();
		return complete();
	}
	
	protected TaskInfo getRunningTask(String taskInsId) {
		return taskService.createTaskQuery().taskId(taskInsId).includeTaskLocalVariables().singleResult();
	}
	
	protected ExecutionEntity getRunningExecutiton(String executionId) {
		return executionManager.findById(executionId);
	}
	
	protected FlowElement getCurrentFlowElement(String processId,String taskKey) {
		return ProcessDefinitionUtil.getBpmnModel(processId).getFlowElement(taskKey);
	}
	
	protected WfUser getHistoryUser(TaskInfo task) {
		WfUser user = new WfUser();
		user.setUserId(task.getAssignee());
		Map<String,Object> map = task.getTaskLocalVariables();
		if(map.containsKey(Constants.VAR_TASK_ORG)) {
			user.setOrgId((String)map.get(Constants.VAR_TASK_ORG));
		}
		if(map.containsKey(Constants.VAR_TASK_ROLE)) {
			user.setRoleId((String)map.get(Constants.VAR_TASK_ROLE));
		}
		return user;
		
	}
	
	protected TaskInfo createTask(ExecutionEntity taskExecution,FlowElement target) {
		//创建执行人
	    TaskEntity task = taskEntityManager.create();
	    task.setExecutionId(taskExecution.getId());
	    task.setTaskDefinitionKey(target.getId());
	    task.setName(target.getName());
	    taskEntityManager.insert(task);
	    return  task;
	}
	
	public abstract Object complete() ;

}
