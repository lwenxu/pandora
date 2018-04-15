package com.ibeetl.starter.workflow.exception;

public class WfException extends RuntimeException {
	public WfException(String msg){
		super(msg);
	}
	
	public WfException(String msg,Throwable cause){
		super(msg,cause);
	}
}
