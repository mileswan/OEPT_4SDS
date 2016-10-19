/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/08/05
 * Description: Method to invoke Content Manager API.
 */

var originalChannelName = "";
var modalTableInit = false;

var ContentManagerAPI = function(){
	
	// execute change password action.
    var changePassword = function() {

		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var confirmPassword = $("#confirmPassword").val();
		var success = false;
		 
		$.ajax({
			type: "GET",
			url: "changePassword.do",
			data: {
				oldPassword:oldPassword,
				newPassword:newPassword,
				confirmPassword:confirmPassword
			},
			dataType: "text",
			success: function (result) {	            	 
				$('.alert-success').hide();	
				$('.alert-danger').hide();
				if( result == "success" ){
					$('.alert-success span').text("密码修改成功！");
					$('.alert-success').show();	
					success = true;
				}else if( result == "oldPasswordErr" ){
					$('.alert-danger span').text("原始密码错误，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}else if( result == "newPasswordErr" ){
					$('.alert-danger span').text("新密码不匹配，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}else if( result == "inputErr" ){
					$('.alert-danger span').text("输入有误，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}	
			},
			error: function(xhr, textStatus, thrownError) {
				alert(textStatus);
			}
		});
		
		setTimeout(function(){
			if(success){
				$('.alert-success').fadeOut();
			}else
				$('.alert-danger').fadeOut();	
		},8000);
		$('html, body').animate({scrollTop:0}, 'slow');
    };
    
 // execute update user settings action.
    var updateUserSettings = function() {

		var lastname = $("#lastname").val();
		var firstname = $("#firstname").val();
		var emailAddress = $("#emailAddress").val();
		var receiveEmailAlerts;
		var isAutoMediaApprover;
		if( $('span.receiveEmailAlerts').find('.active').attr('data-value') == "true" ){
			receiveEmailAlerts = true;
		}else{
			receiveEmailAlerts = false;
		}
		if( $('span.isAutoMediaApprover').find('.active').attr('data-value') == "true" ){
			isAutoMediaApprover = true;
		}else{
			isAutoMediaApprover = false;
		}
		var success = false;
		 
		$.ajax({
			type: "POST",
			url: "updateSettings.do",
			data: {
				lastname:lastname,
				firstname:firstname,
				emailAddress:emailAddress,
				receiveEmailAlerts:receiveEmailAlerts,
				isAutoMediaApprover:isAutoMediaApprover
			},
			dataType: "text",
			success: function (result) {	            	 
				$('.alert-success').hide();	
				$('.alert-danger').hide();
				if( result == "success" ){
					$('.alert-success span').text("账户信息修改成功！");
					$('.alert-success').show();	
					success = true;
				}else{
					$('.alert-danger span').text("账户信息修改失败，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}    	   	 	 
			},
			error: function(xhr, textStatus, thrownError) {
				$('.alert-danger span').text("账户信息修改异常，请重新输入！");
				$('.alert-danger').show();
				success = false;
			}
		});
		
		setTimeout(function(){
			if(success){
				$('.alert-success').fadeOut();
			}else
				$('.alert-danger').fadeOut();	
		},8000);
		$('html, body').animate({scrollTop:0}, 'slow');
    };
    
    // delete selected media items.
    var deleteMedias = function() {

		var table=document.getElementById("sample_1");
		var mediaIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(mediaIds == ""){
					mediaIds = table.rows[i].cells[1].innerHTML;
				}else{
					mediaIds = mediaIds + "," + table.rows[i].cells[1].innerHTML;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
             type: "GET",
             url: "delete.do",
             data: {mediaIds:mediaIds},
             dataType: "text",
             success: function (result) {	            	 
     	   	 	 $('.alert-success span').text("媒体内容已成功删除！");
     	   	 	 $('.alert-success').show();	     	   	 	 
             },
             error: function(xhr, textStatus, thrownError) {
            	 alert(textStatus);
             }
         });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);	
    };
    
 // execute update media details action.
    var updateMediaDetails = function() {

		var description = $("#description").val();
		var startValidDate = $("#starttime").val();
		var endValidDate = $("#endtime").val();
		var mediaId = $('#mediaId').text();
		var audioDucking;
		var playFullscreen;
		if( $('span.audioDucking').find('.active').attr('data-value') == "true" ){
			audioDucking = true;
		}else{
			audioDucking = false;
		}
		if( $('span.playFullscreen').find('.active').attr('data-value') == "true" ){
			playFullscreen = true;
		}else{
			playFullscreen = false;
		}
		var success = false;
		 
		$.ajax({
			type: "POST",
			url: "updateSingle.do",
			data: {
				mediaId:mediaId,
				description:description,
				startValidDate:startValidDate,
				endValidDate:endValidDate,
				audioDucking:audioDucking,
				playFullscreen:playFullscreen
			},
			dataType: "text",
			success: function (result) {	            	 
				$('.alert-success').hide();	
				$('.alert-danger').hide();
				var responseJson = eval ("(" + result + ")");
				if( responseJson.value == "done" ){
					$('.alert-success span').text("媒体信息修改成功！");
					$('.alert-success').show();	
					success = true;
				}else{
					$('.alert-danger span').text("媒体信息修改失败，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}    	   	 	 
			},
			error: function(xhr, textStatus, thrownError) {
				$('.alert-danger span').text("媒体信息修改异常，请重新输入！");
				$('.alert-danger').show();
				success = false;
			}
		});
		
		setTimeout(function(){	
			if(success){
				$('.alert-success').fadeOut();
			}else
				$('.alert-danger').fadeOut();	
		},8000);
		$('html, body').animate({scrollTop:0}, 'slow');
    };
    
 // delete selected playlist.
    var deletePlayList = function() {

		var table=document.getElementById("sample_1");
		var playlistIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(playlistIds == ""){
					playlistIds = table.rows[i].cells[1].innerHTML;
				}else{
					playlistIds = playlistIds + "," + table.rows[i].cells[1].innerHTML;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "delete.do",
            data: {playlistIds:playlistIds},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("播表已成功删除！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
 // display delete option if any checked.
    var checkDelete = function(table) {

		//var table=document.getElementById("sample_1");
		var len=table.rows.length;
		var checked=false;
		
		for(var i=0;i<len;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				checked = true;
				break;
	        }
		}
		if(checked){
			$('#delete_option').show();
		}else{
			$('#delete_option').hide();
		}
    };
    
 // add selected media items to playlist.
    var addPlaylistItems = function() {

		var table=document.getElementById("sample_1");
		var len=table.rows.length;
		var playlistId = $("#playlistId").text();
		var mediaIds = "";
		
		for(var i=0;i<len;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(mediaIds == ""){
					mediaIds = table.rows[i].cells[1].id;
				}else{
					mediaIds = mediaIds + "," + table.rows[i].cells[1].id;
				}
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "addItems.do",
            data: {playlistId:playlistId,
            	   mediaIds:mediaIds},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.value == "Done" ){
            		$('.alert-success span').text("媒体项目已添加成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("媒体项目添加失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "details.do?playlistId="+playlistId;
		},2000);
    };
    
    // update single playlist.
    var updatePlaylist = function() {

		var playlistId = $("#playlistId").text();
		var name = $("#name").val();
		var description = $("#description").val();
		var imageDuration = $("#imageDuration").val();
		var htmlDuration = $("#htmlDuration").val();
		var mediaIds = "";
		
		$.ajax({
            type: "POST",
            url: "updateSingle.do",
            data: {playlistId:playlistId,
            	name:name,
            	description:description,
            	imageDuration:imageDuration,
            	htmlDuration:htmlDuration},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.value == "Done" ){
            		$('.alert-success span').text("编排信息已更新成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("编排信息更新失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "details.do?playlistId="+playlistId;
		},2000);
    };
    
 // remove selected media items from playlist.
    var removePlaylistItems = function() {

		var table=document.getElementById("playlistItems");
		var len=table.rows.length;
		var playlistId = $("#playlistId").text();
		var name = $("#name").val();
		var ItemIds = "";
		
		for(var i=0;i<len;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(ItemIds == ""){
					ItemIds = table.rows[i].cells[1].id;
				}else{
					ItemIds = ItemIds + "," + table.rows[i].cells[1].id;
				}
	        }
		}
		
		$.ajax({
            type: "POST",
            url: "removeItems.do",
            data: {playlistId:playlistId,
            	   name:name,
            	   ItemIds:ItemIds},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.value == "Done" ){
            		$('.alert-success span').text("媒体项目已删除成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("媒体项目删除失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "details.do?playlistId="+playlistId;
		},2000);
    };
    
 // create normal playlist.
    var newNormalPlaylist = function() {

		var playlistId = $("#playlistId").text();
		var name = $("div.modal-body input").val();
		var description = $("div.modal-body textarea").val();
		var playlistType = $("div.modal-body option:selected").val();
			
		$.ajax({
            type: "POST",
            url: "new.do",
            data: {playlistType:playlistType,
            	   name:name,
            	   description:description},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.id ){
            		$('.alert-success span').text("编排表创建成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("编排表创建失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
 // execute update channel details action.
    var updateChannelDetails = function() {

		var description = $("#description").val();
		var name = $("#channelName").val();
		if(name == originalChannelName){
			name = "";
		}
		var channelId = $('#channelId').text();
		var channelType = $('#channelType').text();
		var muteAudioFromVisual;
		if( $('span.muteAudioFromVisual').find('.active').attr('data-value') == "true" ){
			muteAudioFromVisual = true;
		}else{
			muteAudioFromVisual = false;
		}
		var success = false;
		 
		$.ajax({
			type: "POST",
			url: "update.do",
			data: {
				channelId:channelId,
				channelType:channelType,
				description:description,
				name:name,
				muteAudioFromVisual:muteAudioFromVisual
			},
			dataType: "text",
			success: function (result) {	            	 
				$('.alert-success').hide();	
				$('.alert-danger').hide();
				var responseJson = eval ("(" + result + ")");
				if( responseJson.value == "Done" ){
					$('.alert-success span').text("频道信息修改成功！");
					$('.alert-success').show();	
					success = true;
				}else{
					$('.alert-danger span').text("频道信息修改失败，请重新输入！");
					$('.alert-danger').show();
					success = false;
				}    	   	 	 
			},
			error: function(xhr, textStatus, thrownError) {
				$('.alert-danger span').text("频道信息修改异常，请重新输入！");
				$('.alert-danger').show();
				success = false;
			}
		});
		
		setTimeout(function(){	
			if(success){
				$('.alert-success').fadeOut();
			}else
				$('.alert-danger').fadeOut();	
		},8000);
		$('html, body').animate({scrollTop:0}, 'slow');
    };
    
 // delete selected channels.
    var deleteChannels = function() {

		var table=document.getElementById("sample_1");
		var channelIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(channelIds == ""){
					channelIds = table.rows[i].cells[1].innerHTML;
				}else{
					channelIds = channelIds + "," + table.rows[i].cells[1].innerHTML;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "delete.do",
            data: {channelIds:channelIds},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("频道已成功删除！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
 // create channel.
    var createChannel = function() {

		var name = $("div.modal-body input").val();
		var description = $("div.modal-body textarea").val();
		var framesetId = $('#selectedName a').attr('id');
			
		$.ajax({
            type: "POST",
            url: "create.do",
            data: {name:name,
            	   description:description,
            	   framesetId:framesetId},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.id ){
            		$('.alert-success span').text("频道创建成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("频道创建失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
 // delete selected users.
    var deleteUser = function() {

		var table=document.getElementById("sample_1");
		var userIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(userIds == ""){
					userIds = table.rows[i].cells[1].id;
				}else{
					userIds = userIds + "," + table.rows[i].cells[1].id;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "delete.do",
            data: {userIds:userIds},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("用户已成功删除！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
 // create user.
    var createUser = function() {

		var username = $("div.modal-body input#username").val();
		var password = $("div.modal-body input#password").val();
		var lastname = $("div.modal-body input#lastname").val();
		var firstname = $("div.modal-body input#firstname").val();
		var emailAddress = $("div.modal-body input#emailAddress").val();
		var roleIds = "";
		
		$("div.radio-list").find("input:checked").each(function(){
		    if(roleIds == ""){
		    	roleIds = $(this).attr("id");
		    }else{
		    	roleIds = roleIds +","+ $(this).attr("id");
		    }		
		  });
			
		$.ajax({
            type: "POST",
            url: "create.do",
            data: {username:username,
            	password:password,
            	lastname:lastname,
            	firstname:firstname,
            	emailAddress:emailAddress,
            	roleIds:roleIds},
            dataType: "text",
            success: function (result) {	
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.id ){
            		$('.alert-success span').text("用户创建成功！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("用户创建失败，请重新操作！");
            		$('.alert-danger').show();
            	} 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
    // delete selected framesets.
    var deleteFrameSet = function() {

		var table=document.getElementById("sample_1");
		var frameSetIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(frameSetIds == ""){
					frameSetIds = table.rows[i].cells[1].id;
				}else{
					frameSetIds = frameSetIds + "," + table.rows[i].cells[1].id;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "delete.do",
            data: {frameSetIds:frameSetIds},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("框架集已成功删除！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
    // load framesets list.
    var loadFrameSetList = function() {
		
    	var tableRows = "";
		$.ajax({
            type: "GET",
            url: "../frameset/list.do?json=#",
            dataType: "text",
            async: false,
            success: function (result) {
            	var responseJson = eval ("(" + result + ")"); 
            	for(var i=0;i<responseJson.length;i++){
            		
            		var frames = responseJson[i].frames;
            		var framePreviews = "";
            		for(var j=0;j<frames.length;j++){
            			framePreviews = framePreviews +
            			'<div style="width: '+frames[j].width/responseJson[i].width*100+'%; height: '
            			+frames[j].height/responseJson[i].height*100+'%; left: '
            			+frames[j].left/responseJson[i].width*100+'%; top: '
            			+frames[j].top/responseJson[i].height*100+'%; position: absolute; background-color: '
            			+frames[j].color+';"></div>';
            		}
            		
            		tableRows = tableRows + '<tr class="odd gradeX"> \
            			<td> \
						<input type="checkbox" class="checkboxes" value="1"/> \
					</td> \
            		<td class="ID">'+responseJson[i].id+'</td> \
            		<td> \
            		<div class="framesetThumbnailContainer"> \
            			<div class="framesetThumbnail" style="height: 72%; width: 100%;">'
            					+framePreviews+
            			'</div> \
            		</div> \
					</td> \
            		<td class="selectName">'+ responseJson[i].name +	
            		'</td> \
            		<td> \
            		屏幕尺寸：'+ responseJson[i].width +' x '+ responseJson[i].height +' \
            		</td> \
            		<td>'
            			+frames.length+
            		'</td> \
            		</tr>';
            		
            		$("#add-frameset").find("div.modal-body tbody").html(tableRows);
            	}
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
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
		 
		if(!modalTableInit){
			initModalTable();
		}
    };
    
    var initModalTable = function () {

        var table = $('#select-modal');

        // begin first table
        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "表格中无有效的记录",
                "info": "正在显示  _TOTAL_ 项记录中的第  _START_ 到  _END_ 项",
                "infoEmpty": "记录为空 ",
                "infoFiltered": "(filtered1 from _MAX_ total entries)",
                "lengthMenu": "显示 _MENU_ 记录",
                "search": "Search:",
                "zeroRecords": "找不到匹配的记录"
            },

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "columns": [{
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": true
            }],
            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "全部"] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,            
            "pagingType": "bootstrap_full_number",
            "language": {
                "search": "我的搜索: ",
                "lengthMenu": "  _MENU_ 记录",
                "info": "正在显示_TOTAL_项记录中的第 _START_ 到 _END_项",
                "paginate": {
                    "previous":"上一页",
                    "next": "下一页",
                    "last": "末页",
                    "first": "首页"
                }
            },
            "columnDefs": [{  // set default column settings
                'orderable': false,
                'targets': [0]
            }, {
                "searchable": false,
                "targets": [0]
            }],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_1_wrapper'); 

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown
        
        modalTableInit = true;
    };
    
    // add selected frameset.
    var addFrameSet = function() {

		var table=document.getElementById("sample_1");
		var frameSetIds="";
		
		for(var i=0;i<table.rows.length;i++){		
			if(table.rows[i].cells[0].getElementsByTagName('input')[0].checked){
				if(frameSetIds == ""){
					frameSetIds = table.rows[i].cells[1].id;
				}else{
					frameSetIds = frameSetIds + "," + table.rows[i].cells[1].id;
				}
				table.deleteRow(i);
				i--;
	        }
		}
		
		$.ajax({
            type: "GET",
            url: "delete.do",
            data: {frameSetIds:frameSetIds},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("框架集已成功删除！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "list.do";
		},2000);
    };
    
    // publish selected channel to selected player.
    var publishChannel = function() {

		var channelIds=$('div#channelId').text();
		var playerId=$('#selectedName a').attr('id');
		var playerName=$('#selectedName a').text();
		
		$.ajax({
            type: "POST",
            url: "../player/update.do",
            data: {channelIds:channelIds,
            	playerId:playerId,
            	name:playerName},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("营销频道已成功发布！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "publish.do";
		},2000);
    };
    
    // Update player.
    var updatePlayer = function() {

    	var channelIds = "";
		var playerId=$('div#playerId').text();
		var playerName=$('input#playerName').val();
		var description=$('textarea.form-control').val();
		
		$.ajax({
            type: "POST",
            url: "update.do",
            async: false,
            data: {channelIds:channelIds,
            	playerId:playerId,
            	name:playerName,
            	description:description},
            dataType: "text",
            success: function (result) {	            	 
    	   	 	 $('.alert-success span').text("营销频道已成功更新！");
    	   	 	 $('.alert-success').show();	     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
		
		setTimeout(function(){
			location.href = "details.do?playerId="+playerId;
		},2000);
    };
    
    // Generate Plan.
    var generatePlan = function() {

		var playerId=$('#selectedName a').attr('id');
		$('.alert-success').hide();
		
		$.ajax({
            type: "POST",
            url: "../player/generate.do",
            data: {
            	playerId:playerId},
            dataType: "text",
            success: function (result) {
            	var responseJson = eval ("(" + result + ")");
            	if( responseJson.value == "done" ){
            		$('.alert-success span').text("营销频道已成功发布并激活计划！");
       	   	 	 	$('.alert-success').show();
            	}else{
            		$('.alert-danger span').text("计划激活失败，请重新操作！");
            		$('.alert-danger').show();
            	}     	   	 	 
            },
            error: function(xhr, textStatus, thrownError) {
           	 alert(textStatus);
            }
        });
    };
    
    // initialize events function for all elements.
    var initElementEvents = function() {
    	$('body').on('click', '.checkboxes', function() {
            var table1 = document.getElementById("sample_1");
            var table2 = document.getElementById("playlistItems");
			if(table1){
				checkDelete(table1);
			}
            if(table2){
            	checkDelete(table2);
            }				
        });
		$('body').on('click', '.group-checkable', function() {
			var table1 = document.getElementById("sample_1");
            var table2 = document.getElementById("playlistItems");
            if(table1){
				checkDelete(table1);
			}
            if(table2){
            	checkDelete(table2);
            }
        });
		
		$('#changePassword-confirm').click(function() {
	    	changePassword();
	    });
		
		$('#updateSettings-confirm').click(function() {
	    	updateUserSettings();
	    });
		
		$('#updateMedia-confirm').click(function() {
		    updateMediaDetails();
		});
		
	    $('#deleteMedia-confirm').click(function() {
	    	deleteMedias();
	    });
	    
	    $('#deletePlaylist-confirm').click(function() {
	    	deletePlayList();
	    });
	    
	    $('#addMedia-confirm').click(function() {
	    	addPlaylistItems();
	    });
	    
	    $('#updatePlaylist-confirm').click(function() {
	    	updatePlaylist();
	    });
	    
	    $('#removePlaylistItems-confirm').click(function() {
	    	removePlaylistItems();
	    });	
	    
	    $('#newPlaylist-confirm').click(function() {
	    	newNormalPlaylist();
	    });	
	    
	    originalChannelName = $("#channelName").val();
	    $('#updateChannel-confirm').click(function() {
	    	updateChannelDetails();
	    });
	    
	    $('#deleteChannel-confirm').click(function() {
	    	deleteChannels();
	    });
	    
	    $('#newChannel-confirm').click(function() {
	    	createChannel();
	    });
	    
	    $('#deleteUser-confirm').click(function() {
	    	deleteUser();
	    });
	    
	    $('#newUser-confirm').click(function() {
	    	createUser();
	    });
	    
	    $('#deleteFrameSet-confirm').click(function() {
	    	deleteFrameSet();
	    });

	    $('a[href="#add-frameset"]').click(function() {
	    	loadFrameSetList();
	    });
	    $("div.modal-body tbody").ajaxStart(function(){
	    	  $(this).html("<img src='../img/loading.gif' />");
	    	}); 
	    
	    $('button.publish').click(function() {
	    	publishChannel();
	    });
	    
	    $('button.generatePlan').click(function() {
	    	publishChannel();
	    	generatePlan();
	    });
	    
	    $('#updatePlayer-confirm').click(function() {
	    	updatePlayer();
	    });
    };
    
	return {
	
		init: function(){
			
			initElementEvents();    
		}
	};
}();