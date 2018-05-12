/*访问后台的代码*/
layui.define([], function(exports) {
    var api={
            updateUsers:function(form,callback){
                Lib.submitForm("/users/users/update.json",form,{},callback)
            },
            addUsers:function(form,callback){
                Lib.submitForm("/users/users/add.json",form,{},callback)
            },
            del:function(ids,callback){
                Common.post("/users/users/delete.json",{"ids":ids},function(){
                    callback();
                })
            }
		
    };
    exports('usersApi',api);
});