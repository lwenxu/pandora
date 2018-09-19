package com.ibeetl.admin.console.dao;

import com.ibeetl.admin.core.entity.CoreGroup;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by lwen on 2018/9/15
 */
@SqlResource("console.user")
@Repository
public interface GroupDao extends BaseMapper<CoreGroup> {

}
