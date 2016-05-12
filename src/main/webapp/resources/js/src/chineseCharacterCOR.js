var courseWareCOR=(function(config,functions){
    return{
        addTags:[],
        deleteTags:[],
        submitForm:function(form){
            var me=this;
            functions.showLoading();
            $(form).ajaxSubmit({
                dataType:"json",
                data:{
                    insertTag:me.addTags.join(","),
                    deleteTag:me.deleteTags.join(",")
                },
                success:function(response){
                    if(response.resultCode==200){
                        $().toastmessage("showSuccessToast",config.messages.optSuccess);
                        setTimeout(function(){
                            window.location.href="courseware/courseWareMgr";
                        },3000);
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
    tinymce.init({
        selector: "#content",
        height:300,
        toolbar1: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
        toolbar2: 'print preview media | forecolor backcolor emoticons',
        //image_advtab: true,
        plugins : 'link image preview fullscreen table textcolor colorpicker code',
        setup: function (ed) {
            ed.on('blur', function (e) {
                $("#content").val(ed.getContent());
                if(ed.getContent()){
                    $(".error[for='content']").remove();
                }
            });
        }
    });
    functions.createQiNiuUploader({
        maxSize:config.uploader.sizes.img,
        filter:config.uploader.filters.img,
        uploadBtn:"uploadBtn",
        multiSelection:false,
        multipartParams:null,
        uploadContainer:"uploadContainer",
        fileAddCb:null,
        progressCb:null,
        uploadedCb:function(info,file,up){
            //后台的up-token里面要注明返回图片信息
            if(info.w==500&&info.h==500){
                $("#imageUrl").val(info.url);

                $("#image").attr("src",info.url);

                $(".error[for='imageUrl']").remove();
            }else{
                $().toastmessage("showErrorToast",config.messages.imageSizeError);
            }
        }
    });

    $("#tags").on("click",".tag",function(){
        var tag=$(this).val(),
            tagId=$(this).data("tag-id");

        //存在tag-id,代表是修改的时候原始数据存在的tag，需要加入到deleteTags
        if(tagId&&$.inArray(tagId,courseWareCOR.deleteTags)==-1){
            courseWareCOR.deleteTags.push(tagId);
        }else{

            //如果不是原始存在的tag,删除addTags中的数据
            var index= $.inArray(tag,courseWareCOR.addTags);
            courseWareCOR.addTags.splice(index,1);
        }

        $(this).remove();
    });
    $("#tagInput").keydown(function(event){
        if(event.keyCode==13){
            var tag=$(this).val(),
                hasFlag=false;
            $(".tag").each(function(index,el){
                if($(this).text()==tag){
                    hasFlag=true;

                    //中断循环
                    return false;
                }
            });

            if(hasFlag){
                $().toastmessage("showErrorToast",config.messages.tagExist);
            }else{
                $('<span class="tag">'+tag+'</span>').appendTo($("#tags"));
            }

            courseWareCOR.addTags.push(tag);

            $(this).val("");
        }
    });


    $("#myForm").validate({
        ignore:[],
        rules:{
            title:{
                required:true,
                maxlength:32
            },
            author:{
                required:true,
                maxlength:32
            },
            createTime:{
                required:true
            },
            thumbnail:{
                required:true
            },
            abstract_:{
                required:true,
                maxlength:250
            },
            content:{
                required:true
            }
        },
        messages:{
            title:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            author:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            createTime:{
                required:config.validErrors.required
            },
            thumbnail:{
                required:config.validErrors.required
            },
            abstract_:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",250)
            },
            content:{
                required:config.validErrors.required
            }
        },
        submitHandler:function(form) {
            if($("#tags .tag").length==0){
                $().toastmessage("showErrorToast",config.messages.noTag);
            }else{
                courseWareCOR.submitForm(form);
            }
        }
    });
});
