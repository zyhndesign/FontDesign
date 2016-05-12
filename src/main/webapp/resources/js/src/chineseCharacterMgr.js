var courseWareMgr=(function(config,functions){
    var loadedData={};
    /**
     * 创建datatable
     * @returns {*|jQuery}
     */
    function createTable(){

        var ownTable=$("#myTable").dataTable({
            "bServerSide": true,
            "sAjaxSource": config.ajaxUrls.chineseCharacterGetAll,
            "bInfo":true,
            "bProcessing":true,
            "bLengthChange": false,
            "bFilter": false,
            "bSort":false,
            "bAutoWidth": false,
            "iDisplayLength":config.perLoadCounts.table,
            "sPaginationType":"full_numbers",
            "oLanguage": {
                "sUrl":config.dataTable.langUrl
            },
            "aoColumns": [
                { "mDataProp": "thumbnail",
                    "fnRender":function(oObj){
                        return '<img class="thumb" src="'+oObj.aData.thumbnail+'">';
                    }},
                { "mDataProp": "title"},
                { "mDataProp": "author"},
                { "mDataProp": "createTime",
                    "fnRender":function(oObj){
                        return functions.formatDate("y-m-d",oObj.aData.createTime);
                    }
                },
                { "mDataProp": "topTag",
                    "fnRender":function(oObj){
                        return config.topTag[oObj.aData.topTag];
                    }
                },
                { "mDataProp": "opt",
                    "fnRender":function(oObj){
                        return '<a href="chineseCharacter/chineseCharacterCOR/'+oObj.aData.id+'">修改</a>&nbsp;' +
                            '<a class="delete" href="'+oObj.aData.id+'">删除</a>';
                    }
                }
            ] ,
            "fnServerParams": function ( aoData ) {
                aoData.push({
                    name:"category",
                    value:$("#searchCategory").val()
                })
            },
            "fnServerData": function(sSource, aoData, fnCallback) {

                //回调函数
                $.ajax({
                    "dataType":'json',
                    "type":"get",
                    "url":sSource,
                    "data":aoData,
                    "success": function (response) {
                        if(response.success===false){
                            functions.ajaxReturnErrorHandler(response.error_code);
                        }else{
                            var json = {
                                "sEcho" : response.sEcho
                            };

                            for (var i = 0, iLen = response.aaData.length; i < iLen; i++) {
                                loadedData[response.aaData[i].notice_id]=response.aaData[i];
                                response.aaData[i].opt="opt";
                            }

                            json.aaData=response.aaData;
                            json.iTotalRecords = response.iTotalRecords;
                            json.iTotalDisplayRecords = response.iTotalDisplayRecords;
                            fnCallback(json);
                        }

                    }
                });
            },
            "fnFormatNumber":function(iIn){
                return iIn;
            }
        });

        return ownTable;
    }

    return {
        ownTable:null,
        createTable:function(){
            this.ownTable=createTable();
        },
        tableRedraw:function(){
            this.ownTable.fnSettings()._iDisplayStart=0;
            this.ownTable.fnDraw();
        },
        delete:function(id){
            functions.showLoading();
            var me=this;
            $.ajax({
                url:config.ajaxUrls.chineseCharacterDelete.replace(":id",id),
                type:"delete",
                dataType:"json",
                success:function(response){
                    if(response.resultCode==200){
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        me.ownTable.fnDraw();
                        functions.hideLoading();
                    }else{
                        functions.ajaxReturnErrorHandler(response.message);
                    }

                },
                error:function(){
                    functions.ajaxErrorHandler();
                }
            });
        }
    }
})(config,functions);

$(document).ready(function(){

    courseWareMgr.createTable();
    $("#searchBtn").click(function(e){
        courseWareMgr.tableRedraw();
    });
    $("#myTable").on("click","a.delete",function(){
        if(confirm(config.messages.confirmDelete)){
            courseWareMgr.delete($(this).attr("href"));
        }
        return false;
    })
});

