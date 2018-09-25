package com.ibeetl.admin.console.service;

import com.google.common.collect.Lists;
import com.ibeetl.admin.console.dao.GroupDao;
import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.template.ServiceTemplate;
import com.ibeetl.admin.core.entity.CoreGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
                return new ServiceTemplate.LogInfo("[ GroupService ] --> queryAllGroups error occurred !");
            }
        }.execute();
    }

    public ResultDO<List<CoreGroup>> queryGroupById(Long gId) {
        return new ServiceTemplate<List<CoreGroup>>(logger) {

            @Override
            protected List<CoreGroup> invoke() {
                List<CoreGroup> all = groupDao.all();
                LinkedList<CoreGroup> list = Lists.newLinkedList();
                CoreGroup current = null;
                for (CoreGroup coreGroup : all) {
                    if (coreGroup.getId() == gId) {
                        current = coreGroup;
                    }
                }
                list.addFirst(current);
                if (current != null) {
                    boolean flag = true;
                    while (flag) {
                        flag = false;
                        for (CoreGroup coreGroup : all) {
                            if (coreGroup.getId() == current.getPid()) {
                                list.addFirst(coreGroup);
                                flag = true;
                                current = coreGroup;
                                break;
                            }
                        }
                    }
                }
                return list;
            }
            @Override
            public LogInfo logInfo() {
                return new LogInfo("[ GroupService ] --> queryGroupById error occurred !");
            }
        }.execute();
    }

}
