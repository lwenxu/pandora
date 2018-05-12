package com.lwen.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;
import java.util.Date;
/**
 *Users查询
 */
public class UsersQuery extends PageParam {
    @Query(name = "id", display = true)        
    private Integer id;
    @Query(name = "name", display = true)        
    private String name;
    public Integer getId(){
        return  id;
    }
    public void setId(Integer id ){
        this.id = id;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name ){
        this.name = name;
    }
 
}
