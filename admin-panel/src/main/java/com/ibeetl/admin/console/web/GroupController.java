package com.ibeetl.admin.console.web;

import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.exception.ServiceExecException;
import com.ibeetl.admin.console.service.GroupService;
import com.ibeetl.admin.core.entity.CoreGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lwen on 2018/9/15
 */
@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    private static final String MODEL = "/admin/group";


    @GetMapping(MODEL + "/group")
    public ResultDO<List<CoreGroup>> getAllGroup() {
        ResultDO<List<CoreGroup>> resultDO = groupService.queryAllGroups();
        if (!resultDO.getSuccess()) {
            throw new ServiceExecException("sad");
        }
        return resultDO;
    }
}
