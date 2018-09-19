package com.ibeetl.admin.console.service;

import com.ibeetl.admin.console.dao.GroupDao;
import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.template.ServiceTemplate;
import com.ibeetl.admin.core.entity.CoreGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lwen on 2018/9/15
 */
@Service
public class GroupService {
    @Autowired
    GroupDao groupDao;

    private Logger logger = LoggerFactory.getLogger(GroupService.class);

    public ResultDO<List<CoreGroup>> queryAllGroups() {
        return new ServiceTemplate<List<CoreGroup>>(logger) {
            @Override
            protected List<CoreGroup> invoke() {
                return groupDao.all();
            }

            @Override
            public LogInfo logInfo() {
                return new ServiceTemplate.LogInfo("[ GroupService ] --> error occurred !");
            }
        }.execute();
    }

}
