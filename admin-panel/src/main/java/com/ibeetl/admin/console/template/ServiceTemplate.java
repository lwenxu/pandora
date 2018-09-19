package com.ibeetl.admin.console.template;


import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.handler.ExceptionHandler;
import org.slf4j.Logger;

/**
 * Created by lwen on 2018/9/15
 */
public abstract class ServiceTemplate<T> {

    private Logger logger = null;
    private ExceptionHandler handler;

    public ServiceTemplate(Logger logger) {
        this.logger = logger;
        this.handler = defaultHandler();
    }

    public ServiceTemplate(Logger logger, ExceptionHandler handler) {
        this.logger = logger;
        this.handler = handler;
    }

    protected ExceptionHandler defaultHandler(){
        return new ExceptionHandler(){
            @Override
            public void handle(Exception exception, ResultDO result) {
                logger.error(logger.getName() + " Error occurred ! Reason :" + exception.getMessage());
                result.setSuccess(false);
                result.setErrorMessage(exception.getMessage());
                result.setException(exception);
            }
        };
    }

    public ResultDO<T> execute() {
        ResultDO<T> resultDO = new ResultDO<>();
        try {
            resultDO.setSuccess(true);
            resultDO.setResult(invoke());
            return resultDO;
        } catch (Exception e) {
            logInfo();
            handlerException(e,resultDO);
            return resultDO;
        }
    }

    protected abstract T invoke();

    private void handlerException(Exception e, ResultDO<T> resultDO){
        logException(e);
        handler.handle(e, resultDO);
    }

    private void logException(Exception e) {
        LogInfo logInfo = logInfo();
        if (logInfo != null) {
            if (logInfo.params == null) {
                logger.error(logInfo.errorMessage);
            }else {
                logger.error(logInfo.errorMessage,logInfo.params);
            }
        }
    }

    public abstract LogInfo logInfo();

    public static class LogInfo {
        private String errorMessage;
        private Object[] params;

        public LogInfo(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public LogInfo(String errorMessage, Object[] params) {
            this.errorMessage = errorMessage;
            this.params = params;
        }
    }

}
