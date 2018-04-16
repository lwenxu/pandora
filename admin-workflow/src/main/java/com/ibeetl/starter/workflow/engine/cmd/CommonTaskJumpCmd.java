package com.ibeetl.starter.workflow.engine.cmd;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.FlowableEngineAgenda;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import com.ibeetl.starter.workflow.model.WfUser;
import com.ibeetl.starter.workflow.service.WfTaskService;

public class CommonTaskJumpCmd extends BaseManagmentCmd {
	protected String taskInsId;
	protected String target;
	WfUser targetUser;
	String title;
	public CommonTaskJumpCmd(WfTaskService service,String taskInsId, String target, WfUser targetUser,String title) {
		super(service);
		this.taskInsId = taskInsId;
		this.target = target;
		this.targetUser = targetUser;
		this.title = title;
		
	}

	@Override
	public Object complete() {
		TaskEntity taskEntity = taskEntityManager.findById(taskInsId);
		ExecutionEntity ee = executionManager.findById(taskEntity.getExecutionId());
		Process process = ProcessDefinitionUtil.getProcess(ee.getProcessDefinitionId());
		FlowElement targetFlowElement = process.getFlowElement(target);
		ee.setCurrentFlowElement(targetFlowElement);
		FlowableEngineAgenda agenda = CommandContextUtil.getAgenda();
		agenda.planContinueProcessInCompensation(ee);
		taskEntityManager.delete(taskInsId);
		return null;
	}

}
