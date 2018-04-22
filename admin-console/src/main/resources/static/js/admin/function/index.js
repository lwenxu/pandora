layui.define(['form', 'laydate', 'table', 'treeGrid'], function (exports) {
    var form = layui.form;
    var laydate = layui.laydate;
    var table = layui.table;
    var treeGrid = layui.treeGrid;
    var functionTable = null;
    var view = {

        init: function () {
            this.initTable();
            this.initSearchForm();
            this.initToolBar();
            window.dataReload = function () {
                Lib.doSearchForm($("#functionSearchForm"), functionTable)
            }


        },
        initTable: function () {

            functionTable = treeGrid.render({
                elem: '#functionTable',
                height : Lib.getTableHeight(4),
                url: Common.ctxPath + '/admin/function/list.json',
                method: 'post',
                treeId: 'id',//树形id字段名称
                treeUpId: 'parentId',//树形父id字段名称
                treeShowName: 'name',//以树形式显示的字段
                cols: [[ //表头
                    {type: 'numbers', title: '序号'},
                    {
                        type: 'checkbox',
                        // fixed: 'left'
                    },

                    {
                        field: 'name',
                        title: '功能名称',
                        width: 150
                    },

                    {
                        field: 'typeText',
                        title: '功能类型',
                        width: 120,
                        align:'center'

                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        width: 120,
                        align:'center',
                        templet: function (d) {
                            return Common.getDate(d.createTime);
                        }
                    },
                    {
                        field: 'id',
                        title: 'id',
                        align:'center',
                        width: 80
                    }

                ]],
                done: function (res, curr, count) {
                    $("[data-field='id']").css('display', 'none');
                },
                page: false
            });


            // functionTable = table.render({
            // 	elem : '#functionTable',
            // 	height : 'full-280',
            // 	method : 'post',
            // 	url : Common.ctxPath + '/admin/function/list.json' //数据接口
            // 	,page : {"layout":['count','prev', 'page', 'next']} //开启分页
            // 	,limit : 10,
            // 	cols : [ [ //表头
            // 	{
            // 		type : 'checkbox',
            // 		fixed:'left',
            // 	},
            // 	{
            // 		field : 'id',
            // 		title : 'id',
            // 		width : 80,
            // 		fixed:'left',
            // 		sort : true
            // 	}, {
            // 		field : 'code',
            // 		title : '功能代码',
            // 		width : 150
            // 	}, {
            // 		field : 'name',
            // 		title : '功能名称',
            // 		width : 150,
            // 		sort : true
            // 	}, {
            // 		field : 'accessUrl',
            // 		title : '访问地址',
            // 		width : 300,
            // 		sort : true
            // 	}, {
            // 		field : 'parentFunctionText',
            // 		title : '上一级功能',
            // 		width : 120,
            // 		sort : true
            // 	},{
            // 		field : 'typeText',
            // 		title : '功能类型',
            // 		width : 120,
            // 		sort : true
            // 	},
            // 	{
            // 		field : 'createTime',
            // 		title : '创建时间',
            // 		width : 120,
            // 		templet:function(d){
            // 			return Common.getDate(d.createTime);
            // 		},
            // 		sort : true
            // 	}
            //
            // 	] ]
            //
            // });
        },

        initSearchForm: function () {
            Lib.initSearchForm($("#functionSearchForm"), functionTable, form);
        },
        initToolBar: function () {
            toolbar = {
                add: function () { //获取选中数据
                    var url = "/admin/function/add.do";
                    Common.openDlg(url, "功能点管理>新增");
                },
                edit: function () { //获取选中数目
                    var data = Common.getOneFromTable(treeGrid, "functionTable");
                    if (data == null) {
                        return;
                    }
                    var url = "/admin/function/edit.do?id=" + data.id;
                    Common.openDlg(url, "功能点管理>编辑");

                },
                del: function () {
                    layui.use(['del'], function () {
                        var delView = layui.del;
                        delView.delBatch();
                    });
                }

            };
            $('.ext-toolbar').on('click', function () {
                var type = $(this).data('type');
                toolbar[type] ? toolbar[type].call(this) : '';
            });
        }
    };

    exports('index', view);

});