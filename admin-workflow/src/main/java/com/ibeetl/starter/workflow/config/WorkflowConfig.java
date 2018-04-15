package com.ibeetl.starter.workflow.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibeetl.starter.workflow.event.ProcessEndEvent;
import com.ibeetl.starter.workflow.event.ProcessStartEvent;
import com.ibeetl.starter.workflow.event.TaskEndEvent;
import com.ibeetl.starter.workflow.event.TaskOwnerChangeEvent;
import com.ibeetl.starter.workflow.event.TaskStartEvent;
import com.ibeetl.starter.workflow.event.TaslClaimEvent;
import com.ibeetl.starter.workflow.service.WfNotifyService;


@Configuration	
public class WorkflowConfig {
	Log log = LogFactory.getLog(WorkflowConfig.class);
	@Autowired
	ApplicationContext applicationContext;
	
	/**
	 * 如果没有配置通知接口，默认打印出消息
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(WfNotifyService.class)
	public WfNotifyService WfNotifyService() {
		return new WfNotifyService() {
			Log log = LogFactory.getLog(this.getClass());
			@Override
			public void taskStart(TaskStartEvent startEvent) {
				log.info(startEvent);
				
			}

			@Override
			public void taskEnd(TaskEndEvent endEvent) {
				log.info(endEvent);
				
			}

			@Override
			public void processEnd(ProcessEndEvent endEvent) {
				log.info(endEvent);
				
			}

			@Override
			public void taskOwnerChanged(TaskOwnerChangeEvent changeEvent) {
				log.info(changeEvent);
				
			}

			@Override
			public void taskClaim(TaslClaimEvent claimEvent) {
				log.info(claimEvent);
				
			}

			@Override
			public void processStart(ProcessStartEvent startEvent) {
				log.info(startEvent);
				
			}

			@Override
			public void taskPause(String taskInsId) {
				log.info("taskIns "+taskInsId+" pause");
				
			}

			@Override
			public void processPause(String processInsId) {
				log.info("processInsId "+processInsId+" pause");
				
			}
			
			
		};
		
	}
	
}
