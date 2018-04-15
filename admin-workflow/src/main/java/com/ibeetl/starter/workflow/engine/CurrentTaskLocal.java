package com.ibeetl.starter.workflow.engine;

import com.ibeetl.starter.workflow.model.InternalTaskStartInfo;

/**
 * 记录了当前执行的信息
 * @author xiandafu
 *
 */
public class CurrentTaskLocal {
	static ThreadLocal<InternalTaskStartInfo>  locals = new ThreadLocal<InternalTaskStartInfo>() {  
        public InternalTaskStartInfo initialValue() {  
            return null;  
        }  
    };  
    
    public static InternalTaskStartInfo getTaskInfo() {
    	return locals.get();
    }
    public static void setTaskInfo(InternalTaskStartInfo id) {
    	locals.set(id);
    }
    
    
}
