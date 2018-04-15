package com.ibeetl.starter.workflow.service;

import java.util.Map;

import org.flowable.task.api.TaskInfo;

import com.ibeetl.starter.workflow.model.SimpleProcessProgress;
import com.ibeetl.starter.workflow.model.WfTaskSpec;
import com.ibeetl.starter.workflow.model.WfUser;
import com.ibeetl.starter.workflow.model.WfUserSpec;


/**
 * 业务侧常用接口
 * @author xiandafu
 *
 */
public interface WfTaskService {
	
	/**
	 * 启动工作流
	 * @param procKey
	 * @param startUser
	 * @param paras
	 * @return 第一个task的id
	 */
	public String start(String procKey, WfUser startUser,Map paras);
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	public void complete(String taskInsId, WfUser acUser,String comment, String title, String buttonCode,WfUser[] targetWfUsers,Map<String, Object> processVars, Map<String, Object> parameters);	
	
	/**
	 * 同上，简洁版的完成，用于单元测试，
	 * @param taskInsId
	 * @param acUser
	 * @param buttonCode
	 * @param targetAcUser
	 */
	public void complete(String taskInsId, WfUser wfUser, String buttonCode, WfUser targetWfUser);
		

	
	/**
	 * 签收任务，调用签收前，业务侧应该负责检测此用户是否还具备此角色以及部门
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（用户登录名）
	 */
	public boolean claim(WfUser user,String taskInsId);
	
	/**
	 * 获取某个环节的描述信息，用于业务侧暂时和选人，processKey和processInsId二选一提供
	 * @param processKey
	 * @param processInsId
	 * @param taskId
	 * @return
	 */
	public WfTaskSpec getTaskSpec(String processKey,String processInsId,String taskId) ;
	
	/**
	 * 获取某个环节期望的用户类型
	 * @param processKey
	 * @param taskId
	 * @return
	 */
	public WfUserSpec getTaskUserDefine(String processKey,String processInsId,String taskId);
	
	/**
	 * 获取历史执行人，用于选人策略.环节会被多次执行，此获取最新的一次
	 * @param procInsId
	 * @param taskId
	 * @return
	 */
	public WfUser getHistoryWfUser(String procInsId,String taskKey);
	
	
	/**
	 * 获取流程发起人
	 * @param procInsId
	 * @return
	 */
	public WfUser getHistoryProcessOwnerWfUser(String procInsId);
	

	
		
	
	
	/**
	 * 委派任务
	 * @param taskId 任务ID
	 * @param userId 被委派人
	 */
	public void delegateTask(String taskInsId, WfUser wfUsers,String comment);
	



	 
    /** 
     * 撤回流程 ,在用户还未claim情况下可以撤回
     * @param taskInsId  当前任务ID 
     *
     */  
    public boolean callBackTask(String taskInsId);
    
     
    /**
	 * 撤销流程，需要管理人员调用。此删除并没有备份，物理删除了
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 * @param status 结束状态，1 正常结束 2 流程删除
	 */
	public void processEnd(String procInsId, String deleteReason,Integer status);
	
	
	
	/**
	 * 流程简单的进展
	 * @param procInsId
	 * @return
	 */
	public SimpleProcessProgress getSimpleProgress(String processInsId);
	
	/**
	 * 重新指定流程执行人	
	 * @param task
	 * @param assignee
	 * @param title
	 */
	public void setTaskAssignee(TaskInfo task,WfUser assignee,String title );
	
	/**
	 * 判断流程是否执行完毕
	 * @param taskInsId
	 * @return
	 */
	public boolean isProcessCompleted(String taskInsId);
	
	
		
}
