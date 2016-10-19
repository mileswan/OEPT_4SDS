/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/08/19
 * Description: Methods to do common operations.
 */
var autodsCommon = function(){

	 // initialize events function for all elements.
    var initElementEvents = function() {

    	$('#changePassword-reset').click(function() {
	    	resetForm();
	    });
	    
	    $('button.cancel').click(function(event) {
	    	event.preventDefault();
	    	window.location.reload();
	    });
	    
	    $('a.reload').click(function() {
	    	window.location.reload();
	    });
	    
	    $('span.button-group [data-value="true"]').click(function(event) {
	    	event.preventDefault();
	    	$(this).addClass("active");
	    	$(this).parent().find('[data-value="false"]').removeClass("active");
	    });
	    $('span.button-group [data-value="false"]').click(function(event) {
	    	event.preventDefault();
	    	$(this).addClass("active");
	    	$(this).parent().find('[data-value="true"]').removeClass("active");
	    });
	    
	    if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: Metronic.isRTL(),
                orientation: "left",
                autoclose: true,
                language: 'zh-CN'
            }).on('changeDate',function(ev){    	
		    	 var end = $("#endtime").val();
		    	 var start = $("#starttime").val();
		    	  if(end<start && end != null && end !=""){
		    	   alert("“有效结束日期 ”不能早于“有效结束日期 ” ！");	    	   
		    	  }
		    });
	    };
	    
	    $('#spinner1').spinner({value:0, step: 1, min: 0, max: 59});
	    
	    if (jQuery().inputmask) {
	    	$(".mask_duration").inputmask("h:s:s", {
	    		"placeholder": "hh:mm:ss"
	    	}); 
	    }
	    
	    $('body').on('click', '#changePassword', function() {
	    	checkChangePassword();			
        });
	    
	    if (jQuery().dataTable) {
	    	initPlaylistItemsTable();
	    	initEditableTable();
	    }
	    
	    $(".framesetThumbnail div.frame").each(function(){
			$(this).mouseover(function(){
				$(".framesetThumbnail div.frame").removeClass("thumbnailFrameSelected");
				$(this).toggleClass("thumbnailFrameSelected");
			});
			$(this).click(function(){
				$('.actions').show();
				$(".framesetThumbnail div.frame").removeClass("thumbnailFrameSelected");
				$(this).toggleClass("thumbnailFrameSelected");
				var channelId = $('div#channelId').text();
				var channelName = $('div#channelName').text();
				var frameId = $(this).attr('id');
				$('.actions').find('a').attr('href','../schedule/list.do?channelId='+channelId+
						'&channelName='+channelName+'&frameId='+frameId);
			});
		});	 	
    };
    
 // reset form input fields.
    var resetForm = function() {

		$(".form-control").val("");
		 	
    };
 
 // Initialize playlist items table.  
    var initPlaylistItemsTable = function () {

        var table = $('#playlistItems');

        // begin first table
        table.dataTable({
            
        	"bPaginate": false,
        	"bInfo": false,
        	"bFilter": false,
        	"bSort": false,
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        table.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).attr("checked", true);
                    $(this).parents('tr').addClass("active");
                } else {
                    $(this).attr("checked", false);
                    $(this).parents('tr').removeClass("active");
                }
            });
            jQuery.uniform.update(set);
        });

        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });
    };
    
 // change if can change password.
    var checkChangePassword = function() {

		if($("#changePassword").is(":checked")){
			$("#newPassword").attr("readonly", false);
			$("#confirmPassword").attr("readonly", false);
		}else{
			$("#newPassword").attr("readonly", true);
			$("#confirmPassword").attr("readonly", true);
		};
		 	
    };
    
 // initialize editable table   
    var initEditableTable = function () {

        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
            jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[4] + '">';
            jqTds[5].innerHTML = '<a class="edit" href="">保存</a>';
            jqTds[6].innerHTML = '<a class="cancel" href="">取消</a>';
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
            oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 5, false);
            oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 6, false);
            oTable.fnDraw();
        }

        function cancelEditRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
            oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 5, false);
            oTable.fnDraw();
        }

        var table = $('#_editable_');

        var oTable = table.dataTable({

        	"bPaginate": false,
        	"bInfo": false,
        	"bFilter": false,
        	"bSort": false,
            "order": [
                [0, "asc"]
            ] // set name column as a default sort by asc
        });

        var nEditing = null;
        var nNew = false;

        $('#sample_editable_1_new').click(function (e) {
            e.preventDefault();

            if (nNew && nEditing) {
                if (confirm("Previose row not saved. Do you want to save it ?")) {
                    saveRow(oTable, nEditing); // save
                    $(nEditing).find("td:first").html("Untitled");
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;
                    
                    return;
                }
            }

            var aiNew = oTable.fnAddData(['', '', '', '', '', '', '', '']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
        });

        table.on('click', '.delete', function (e) {
            e.preventDefault();

            if (confirm("Are you sure to delete this row ?") == false) {
                return;
            }

            var nRow = $(this).parents('tr')[0];
            oTable.fnDeleteRow(nRow);
            alert("Deleted! Do not forget to do some ajax to sync with backend :)");
        });

        table.on('click', '.cancel', function (e) {
            e.preventDefault();
            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
                nNew = false;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }
        });

        table.on('click', '.edit', function (e) {
            e.preventDefault();

            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                /* Currently editing - but not this row - restore the old before continuing to edit mode */
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {
                /* Editing this row and want to save it */
                saveRow(oTable, nEditing);
                nEditing = null;
                alert("Updated! Do not forget to do some ajax to sync with backend :)");
            } else {
                /* No edit in progress - let's start one */
                editRow(oTable, nRow);
                nEditing = nRow;
            }
        });
    };
    
    // initialize select modal table.
    var initSelectModalTable = function() {

    	$('#select-modal').find('tbody tr .checkboxes').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $(':checkbox').removeAttr('checked');
                    $(':checkbox').parents('tr').removeClass('active');
                    $(this).attr('checked','checked');
                    $(this).parents('tr').toggleClass("active");
                    $('#selectedName a').text($(this).parents('tr').find('td.selectName').text());
                    $('#selectedName a').attr('id', $(this).parents('tr').find('td.ID').text());
                }else{
                	$(':checkbox').parents('tr').removeClass('active');
                }
            })
        });
    };
    
    // Initialize publish buttons action.
    var initPublishButtons = function() {

    	$('#sample_1').find('tbody tr a[href="#publish-confirm"]').each(function(){
            $(this).click(function(){
            	$('div#channelId').text($(this).parents('tr').find('td.publishId').text());
            })
        });
    };
    
 // Initialize location tree view.
    var initLocationTree = function() {

    	$("#location_tree").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data': [{
                        "text": "总店",
                        "children": [{
                            "text": "北京分店",
                            "state": {
                                "selected": true
                            }
                        }, {
                            "text": "四川分店",
                            "icon": "fa fa-warning icon-state-danger"
                        }, {
                            "text": "深圳分店",
                            "icon" : "fa fa-folder icon-state-success",
                            "state": {
                                "opened": true
                            },
                            "children": [
                                {"text": "罗田店", "icon" : "fa fa-file icon-state-warning"}
                            ]
                        }, {
                            "text": "加盟店",
                            "icon": "fa fa-warning icon-state-warning"
                        }, {
                            "text": "已关闭的门店",
                            "icon": "fa fa-check icon-state-success",
                            "state": {
                                "disabled": true
                            }
                        }, {
                            "text": "上海分店",
                            "icon": "fa fa-folder icon-state-danger",
                            "children": [
                                {"text": "浦东店", "icon" : "fa fa-file icon-state-warning"},
                                {"text": "普陀店", "icon" : "fa fa-file icon-state-success"},
                                {"text": "静安店", "icon" : "fa fa-file icon-state-default"},
                                {"text": "黄浦店", "icon" : "fa fa-file icon-state-danger"},
                                {"text": "长宁店", "icon" : "fa fa-file icon-state-info"}
                            ]
                        }]
                    },
                    "海外分店"
                ]
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder icon-state-warning icon-lg"
                },
                "file" : {
                    "icon" : "fa fa-file icon-state-warning icon-lg"
                }
            },
            "state" : { "key" : "demo2" },
            "plugins" : [ "contextmenu", "dnd", "state", "types" ]
        });
    	
    	$("#member_tree").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data': [{
                        "text": "播放组A",
                        "children": [{
                            "text": "播放器A1",
                            "state": {
                                "selected": true
                            }
                        }, {
                            "text": "播放器A2"
                        }, {
                            "text": "播放器A3"
                        }, {
                            "text": "播放组C",
                            "children": [
                                {"text": "播放器C1", "icon" : "fa fa-file icon-state-info"},
                                {"text": "播放器C2", "icon" : "fa fa-file icon-state-info"},
                                {"text": "播放器C3", "icon" : "fa fa-file icon-state-info"}
                            ]
                        }]
                    },
                    "播放组B"
                ]
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder icon-state-warning icon-lg"
                },
                "file" : {
                    "icon" : "fa fa-file icon-state-warning icon-lg"
                }
            },
            "state" : { "key" : "demo2" },
            "plugins" : [ "contextmenu", "dnd", "state", "types" ]
        });
    };
    
	return {
	
		init: function(){
			
			initElementEvents();		    
		    initSelectModalTable();
		    initPublishButtons();
		    if(jQuery().jstree){
		    	initLocationTree();
		    }	    
		}
	};
}();