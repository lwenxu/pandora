queryByCondition
===


    select 
    \@pageTag(){
    t.*
    \@}
    from ${entity.tableName} t
    where del_flag=0 
    \@//数据权限，该sql语句功能点  
    and #function("${entity.username}.query")#
    @for(attr in entity.list){
    		@if(attr.showInQuery){
    \@if(!isEmpty(${attr.username})){
        and  t.${attr.colName} =#${attr.username}#
    \@}
    		@}
    @}
    
    
    

batchDel${entity.username}ByIds
===

* 批量逻辑删除

    update ${entity.tableName} set del_flag = 1 where ${entity.idAttribute.colName}  in( #join(ids)#)
    
