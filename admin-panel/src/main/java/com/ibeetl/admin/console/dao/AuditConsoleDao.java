package com.ibeetl.admin.console.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreAudit;
import org.springframework.stereotype.Repository;

@SqlResource("console.audit")
@Repository
public interface AuditConsoleDao extends BaseMapper<CoreAudit> {

    PageQuery<CoreAudit> queryByCondtion(@Param("query") PageQuery<CoreAudit> query);

}

