layui.define(['form', 'laydate', 'table', 'treeGrid'], function (exports) {
    var form = layui.form;
    var laydate = layui.laydate;
    // var table = layui.table;
    var treeGrid = layui.treeGrid;
    var userTable = null;

    var view = {

        init: function () {
            this.initTable();
            this.initSearchForm();
            this.initToolBar();
            window.dataReload = function () {
                Lib.doSearchForm($("#orgSearchForm"), userTable)
            }


        },
        initTable: function () {
            userTable = treeGrid.render({
                elem: '#orgTable',
                height : Lib.getTableHeight(4),
                url: Common.ctxPath + '/admin/org/list.json',
                method: 'post',
                cellMinWidth: 100,
                treeId: 'id',//树形id字段名称
                treeUpId: 'parentOrgId',//树形父id字段名称
                treeShowName: 'name',//以树形式显示的字段
                cols: [[ //表头
                    // {type: 'numbers', title: '序号', fixed: 'left'},
                    {type: 'numbers', title: '序号'},

                    {
                        type: 'checkbox',
                        // fixed: 'left'
                        // fixed: 'left'
                    },
                    {
                        field: 'name',
                        title: '机构名称',
                        width: 500
                    },
                    {
                        field: 'typeText',
                        title: '机构类型',
                        width: 120,
                        align: 'center'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        width: 120,
                        align: 'center',
                        templet: function (d) {
                            return Common.getDate(d.createTime);
                        }
                    }, {
                        field: 'id',
                        title: 'id',
                        width: 50,
                        align: 'center'
                    }

                ]],
                done: function (res, curr, count) {
                    $("[data-field='id']").css('display', 'none');
                },
                page: false
            });


            


        },

        initSearchForm: function () {
            Lib.initSearchForm($("#orgSearchForm"), userTable, form);
        },
        initToolBar: function () {
            toolbar = {
                add: function () { //获取选中数据
                    var url = "/admin/org/add.do";
                    Common.openDlg(url, "用户管理>新增");
                },
                edit: function () { //获取选中数目

                    var data = Common.getOneFromTable(treeGrid, "orgTable");
                    if (data == null) {
                        return;
                    }
                    var url = "/admin/org/edit.do?id=" + data.id;
                    Common.openDlg(url, "用户管理>编辑");

                },
                del: function () {
                    layui.use(['del'], function () {
                        var delView = layui.del;
                        delView.delBatch();
                    });
                },
                orgUser: function () {
                    var data = Common.getOneFromTable(treeGrid, "orgTable");
                    if (data == null) {
                        return;
                    }
                    var url = "/admin/org/user/list.do?orgId=" + data.id;
                    Common.openDlg(url, "组织管理>用户列表");

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