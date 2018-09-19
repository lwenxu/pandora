package com.ibeetl.admin.console.utils;

import com.ibeetl.admin.console.web.vo.ResultVO;
import com.ibeetl.admin.core.web.JsonReturnCode;

/**
 * Created by lwen on 2018/9/18
 */
public class ResultUtil {
    public static ResultVO success() {
        return new ResultVO(JsonReturnCode.SUCCESS.getCode(), true);
    }

    public static ResultVO failed() {
        return new ResultVO(JsonReturnCode.FAIL.getCode(), false);
    }

    public static ResultVO notFound() {
        return new ResultVO(JsonReturnCode.NOT_FOUND.getCode(), false);
    }
}
