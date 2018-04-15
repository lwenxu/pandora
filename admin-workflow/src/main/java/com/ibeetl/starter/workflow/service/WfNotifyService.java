package com.ibeetl.starter.workflow.service;

import com.ibeetl.starter.workflow.event.ProcessEndEvent;
import com.ibeetl.starter.workflow.event.ProcessStartEvent;
import com.ibeetl.starter.workflow.event.TaskEndEvent;
import com.ibeetl.starter.workflow.event.TaskOwnerChangeEvent;
import com.ibeetl.starter.workflow.event.TaskStartEvent;
import com.ibeetl.starter.workflow.event.TaslClaimEvent;

/**
 * 业务侧接口，业务测的代办中心可以将工作流消息纳入到业务的代办中心
 * @author xiandafu
 *
 */
public interface WfNotifyService {
	public void processStart(ProcessStartEvent  startEvent);
	public void taskStart(TaskStartEvent startEvent);
	public void taskPause(String taskInsId);
	public void processPause(String processInsId);
	public void taskEnd(TaskEndEvent endEvent);
	public void processEnd(ProcessEndEvent  endEvent);
	public void taskOwnerChanged(TaskOwnerChangeEvent  changeEvent);
	public void taskClaim(TaslClaimEvent  claimEvent);
	
}
