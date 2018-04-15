package com.ibeetl.starter.workflow.kit;

public class Constants {
	//每个任务，都还包含如下扩展信息
	public static final String VAR_TASK_ORG = "_orgId";
	public static final String VAR_TASK_ROLE = "_roleId";
	public static final String VAR_TASK_TITLE = "_title";
	public static final String VAR_LAST_TASK_ID = "_lastTaskId";
	public static final String VAR_USER_FLAG = "_userFlag";
	public static final String VAR_TASK_WAIT = "_wait";
	public static final String VAR_PROCESS_STATUS= "_processStatus";
	
	public static final String TYPE_COMMENT_AGREE = "comment";
	public static final String TYPE_COMMENT_DELEGATE = "delegate";
	public static final String TASK_END = "theEnd";
	public static final String TASK_START = "theStart";
	
	
	public static final String HISTORY_PARENT_EXECUTION = "_parentExecutionId";
	public static final String HISTORY_LAST_TASK = "_lastTaskId";
	
	
	
	
	//环节对应的页面
	public static final String EXT_TASK_PAGE = "page";
	
	//普通按钮
	public static final String BUTTON_TYPE_GENERAL = "general";
	//任意跳转
	public static final String BUTTON_TYPE_JUMP = "jump";
	
	
	//回到上一个任务,有问题
	public static final String BUTTON_TYPE_BACK = "back";
	
	//自动
	public static final String BUTTON_TYPE_AUTO = "auto";
	//直接结束流程的按钮
	public static final String BUTTON_TYPE_END_PROCESS = "complete";
	//多实例
	public static final String BUTTON_TYPE_MUTILPLE = "mutiple";
	//并行
	public static final String BUTTON_TYPE_PARALLEL= "parallel";
	//等待
	public static final String BUTTON_TYPE_WAIT = "wait";
	
	//按照角色选取
	public static final String USER_ROLE = "role";
	//流程的历史执行人
	public static final String USER_task_history = "task_history";
	//流程的创建者
	public static final String USER_PROCESS_CREATOR = "processe_creator";
	//来自于某个变量，因此不必选取,暂时没有这个需求，取消
//	public static final String USER_DYNAMIC_VARS = "dynamic_vars";
	
	//业务侧按钮的渲人策略
	public static final String CHOOSE_ONE = "one";
	public static final String CHOOSE_RANDOM_ONE = "random_one";
	public static final String CHOOSE_MUTIPLE = "mutiple";
}
