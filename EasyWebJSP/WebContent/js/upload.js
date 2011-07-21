var upload;
$(document).ready(function() {
    Ext.onReady(function() {
        var openFile = $("#openFile");
       // var bizId = $("#bizId").val();
       // var bizType = $("#bizType").val();
      //  var action = $("#action").val();
        openFile.before("<SPAN id='spanButtonPlaceholder2'></SPAN>");
        openFile.hide();
        var select = (openFile.attr("select") && openFile.attr("select") == 'file') ? "file" : "files";
        var up_limit = (openFile.attr("up_limit")) ? openFile.attr("up_limit") : '0';
        var sObj = new Object();
        sObj.progressTarget = "fsUploadProgress2";
        sObj.cancelButtonId = "cancelButtonId";
        var fObj = getFlashObj();
        //fObj.upload_url = "adjunctAction_saveFile.box?action=" + action + "&bizId=" + bizId + "&bizType=" + bizType;
        fObj.upload_url = "http://localhost:8080/EasyWeb/FileUploadServlet";
        fObj.button_image_url = "../extjs/resources/images/broswe.PNG";
        fObj.button_placeholder_id = "spanButtonPlaceholder2";
        fObj.button_width = 91;
        fObj.button_height = 22;
        if (select == "file") {
            fObj.button_action = SWFUpload.BUTTON_ACTION.SELECT_FILE;
        } else {
            fObj.button_action = SWFUpload.BUTTON_ACTION.SELECT_FILES;
        }
        fObj.custom_settings = sObj;
        fObj.file_types = openFile.attr("filetypes");
        fObj.file_size_limit = openFile.attr("filesize") ? openFile.attr("filesize") : 0;
        //fObj.upload_complete_handler = completeUploadFile;
        fObj.file_types_description = openFile.attr("filetypesdescription");
        fObj.file_upload_limit = up_limit;
        upload = new SWFUpload(fObj);
        /*function completeUploadFile() {
            if (upload.getStats().files_queued == 0) {
                checkV();v.curform.submit();
            }
        }*/
        $("a[@id='deletefile']").each(function(i, n) {
            $(n).click(function() {
                BoxUI.MessageBox.confirm('确认框', '您确定要删除此附件吗?', function(btn) {
                    if (btn == "yes") {
                        var rid = $(n).attr("rid");
                        var realhref = $(n).attr("realhref") + "?fileId=" + rid;
                        $.post(realhref, "", function(msg) {
                            $("#showdiv" + rid).fadeOut(1000);
                        });
                    }
                });

            });
        });
    });

});

function getFlashObj() {
    var obj = new Object();
    obj.file_size_limit = "104857600";
    obj.file_types = "*.*";
    obj.file_types_description = "所有文件";
    obj.file_upload_limit = "0";
    obj.file_queue_limit = "0";
    obj.file_autoUpload = false;
    
    // Event Handler Settings (all my handlers are in the Handler.js file)
	obj.file_queued_handler = fileQueued;
    obj.file_queue_error_handler = fileQueueError;
    obj.file_dialog_start_handler = fileDialogStart;
    
    obj.file_dialog_complete_handler = fileDialogComplete;
    obj.upload_start_handler = uploadStart;
    obj.upload_progress_handler = uploadProgress;
    obj.upload_error_handler = uploadError;
    obj.upload_success_handler = uploadSuccess;
    obj.upload_complete_handler = uploadComplete;

    // obj.Flash Settings
    obj.flash_url = "../extjs/js/plugin/upload/swfupload.swf"; // Relative to this file (or you can use
    // absolute paths)
	//是否调试
    obj.debug = true;
    return obj;
}