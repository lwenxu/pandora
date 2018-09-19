package com.ibeetl.admin.console.exception;

import com.ibeetl.admin.console.utils.ResultUtil;
import com.ibeetl.admin.console.web.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lwen on 2018/9/18
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceExecException.class)
    public ResultVO serviceExecExceptionHandler(ServiceExecException e) {
        return ResultUtil.failed().setMsg(e.getMessage());
    }
}
