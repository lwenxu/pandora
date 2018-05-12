package com.lwen.dao;

import java.util.List;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import org.beetl.sql.core.engine.PageQuery;

import  com.lwen.entity.*;

/**
 * Users Dao
 */
@SqlResource("cms.users")
public interface UsersDao extends BaseMapper<Users>{
    public PageQuery<Users> queryByCondition(PageQuery query);
    public void batchDelUsersByIds( List<Long> ids);
}