package com.lwen.service;

import java.util.List;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeetl.admin.core.util.PlatformException;

import com.lwen.dao.UsersDao;
import com.lwen.entity.Users;
import com.ibeetl.admin.core.service.BaseService;

/**
 * Users Service
 */

@Service
@Transactional
public class UsersService extends BaseService<Users>{

    @Autowired private UsersDao usersDao;

    public PageQuery<Users>queryByCondition(PageQuery query){
        PageQuery ret =  usersDao.queryByCondition(query);
        queryListAfter(ret.getList());
        return ret;
    }

    public void batchDelUsers(List<Long> ids){
        try {
            usersDao.batchDelUsersByIds(ids);
        } catch (Exception e) {
            throw new PlatformException("批量删除Users失败", e);
        }
    }
}