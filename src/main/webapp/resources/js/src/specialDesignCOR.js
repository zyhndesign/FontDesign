var courseDesignCOR=(function(config,functions){
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
                            window.location.href="coursedesign/courseDesignMgr";
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
    functions.createQiNiuUploader({
        maxSize:config.uploader.sizes.img,
        filter:config.uploader.filters.img,
        uploadBtn:"uploadBgBtn",
        multiSelection:false,
        multipartParams:null,
        uploadContainer:"uploadBgContainer",
        fileAddCb:null,
        progressCb:null,
        uploadedCb:function(info,file,up){
            //后台的up-token里面要注明返回图片信息
            if(info.w==960&&info.h==540){
                $("#bgUrl").val(info.url);

                $("#bg").attr("src",info.url);

                $(".error[for='bgUrl']").remove();
            }else{
                $().toastmessage("showErrorToast",config.messages.imageSizeError);
            }
        }
    });


    $("#tags").on("click",".tag",function(){
        var tag=$(this).val(),
            tagId=$(this).data("tag-id");

        //存在tag-id,代表是修改的时候原始数据存在的tag，需要加入到deleteTags
        if(tagId&&$.inArray(tagId,courseDesignCOR.deleteTags)==-1){
            courseDesignCOR.deleteTags.push(tagId);
        }else{

            //如果不是原始存在的tag,删除addTags中的数据
            var index= $.inArray(tag,courseDesignCOR.addTags);
            courseDesignCOR.addTags.splice(index,1);
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

            courseDesignCOR.addTags.push(tag);

            $(this).val("");
        }
    });


    $("#myForm").validate({
        ignore:[],
        rules:{
            thumbnail:{
                required:true
            },
            bg:{
                required:true
            },
            title:{
                required:true,
                maxlength:32
            },
            teacher:{
                required:true,
                maxlength:32
            },
            createTime:{
                required:true
            },
            course_detail_id:{
                required:true
            },
            abstract_:{
                required:true,
                maxlength:250
            }
        },
        messages:{
            thumbnail:{
                required:config.validErrors.required
            },
            bg:{
                required:config.validErrors.required
            },
            title:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            teacher:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",32)
            },
            createTime:{
                required:config.validErrors.required
            },
            course_detail_id:{
                required:config.validErrors.required
            },
            abstract_:{
                required:config.validErrors.required,
                maxlength:config.validErrors.maxLength.replace("${max}",250)
            }
        },
        submitHandler:function(form) {
            if($("#tags .tag").length==0){
                $().toastmessage("showErrorToast",config.messages.noTag);
            }else{
                courseDesignCOR.submitForm(form);
            }

        }
    });
});
