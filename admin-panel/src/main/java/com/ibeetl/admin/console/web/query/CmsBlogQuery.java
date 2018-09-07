package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.web.query.PageParam;

/**
 *CmsBlog查询
 */
public class CmsBlogQuery extends PageParam {
    @Query(name = "id", display = true)
    private Integer id;
    public Integer getId(){
        return  id;
    }
    public void setId(Integer id ){
        this.id = id;
    }
 
}
