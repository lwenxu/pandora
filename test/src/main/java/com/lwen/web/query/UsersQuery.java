package com.lwen.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;
import java.util.Date;
/**
 *Users查询
 */
public class UsersQuery extends PageParam {
    @Query(name = "name", display = true)        
    private String name;
    @Query(name = "gender", display = true)        
    private String gender;
    public String getName(){
        return  name;
    }
    public void setName(String name ){
        this.name = name;
    }
    public String getGender(){
        return  gender;
    }
    public void setGender(String gender ){
        this.gender = gender;
    }
 
}
