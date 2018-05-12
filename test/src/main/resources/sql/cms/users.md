queryByCondition
===


    select 
    @pageTag(){
    t.*
    @}
    from users t
    where del_flag=0 
    @//数据权限，该sql语句功能点  
    and #function("users.query")#
    @if(!isEmpty(id)){
        and  t.id =#id#
    @}
    @if(!isEmpty(name)){
        and  t.name =#name#
    @}
    
    
    

batchDelUsersByIds
===

* 批量逻辑删除

    update users set del_flag = 1 where gender  in( #join(ids)#)
    
