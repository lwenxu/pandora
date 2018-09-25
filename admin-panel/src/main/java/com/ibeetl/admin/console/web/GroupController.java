package com.ibeetl.admin.console.web;

import com.ibeetl.admin.console.constant.ResultType;
import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.exception.ServiceExecException;
import com.ibeetl.admin.console.service.GroupService;
import com.ibeetl.admin.console.utils.ResultUtil;
import com.ibeetl.admin.console.web.vo.GroupVO;
import com.ibeetl.admin.console.web.vo.ResultVO;
import com.ibeetl.admin.core.entity.CoreGroup;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by lwen on 2018/9/15
 */
@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    private static final String MODEL = "/admin/group";


    @GetMapping(MODEL + "/list")
    public ResultVO<List<GroupVO>> getAllGroup() throws ServiceExecException {
        ResultDO<List<CoreGroup>> resultDO = groupService.queryAllGroups();
        if (!resultDO.getSuccess()) {
            throw new ServiceExecException(ResultType.SERVICE_FAILURE.getDesc());
        }
        List<CoreGroup> groups = resultDO.getResult();
        List<GroupVO> ret = Lists.newArrayList();
        Set<CoreGroup> groupSet = Sets.newHashSet();
        for (CoreGroup group : groups) {
            if (groupSet.contains(group)) {
                continue;
            }
            GroupVO current = GroupVO.transToGroupVO(group);
            ret.add(current);
            for (CoreGroup other : groups) {
                if (other.getPid() == group.getId()) {
                    current.getChildren().add(GroupVO.transToGroupVO(other));
                    groupSet.add(other);
                }
            }
        }
        ResultVO<List<GroupVO>> resultVO = ResultUtil.success();
        resultVO.setData(ret);
        return resultVO;
    }
}
