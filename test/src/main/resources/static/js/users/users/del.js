layui.define(['table', 'usersApi'], function(exports) {
    var usersApi = layui.usersApi;
    var table=layui.table;
    var view = {
        init:function(){
        },
        delBatch:function(){
            var data = Common.getMoreDataFromTable(table,"usersTable");
            if(data==null){
                return ;
            }
            Common.openConfirm("确认要删除这些Users?",function(){
            var ids =Common.concatBatchId(data,"id");
            usersApi.del(ids,function(){
                Common.info("删除成功");
                    dataReload();
                })
            })
        }
    }
    exports('del',view);
	
});