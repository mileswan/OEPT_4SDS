<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oept.autods.common.util.PropFileManager"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<%
	PropFileManager _propFileMgr = new PropFileManager("interface.properties");
	String ContentManagerResourceURL = _propFileMgr.getProperty("ContentManager.Resource.uri");
	String ContentManagerURL = _propFileMgr.getProperty("ContentManager.uri");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/08/27
 * Description:  Folder details management page.
 */
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>文件夹管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->

<link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="../assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-datepicker/css/datepicker3.css">
<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN PAGE STYLES -->
<link href="../assets/admin/pages/css/tasks.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE STYLES -->
<!-- BEGIN THEME STYLES -->
<!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
<link href="../assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="../assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" type="text/css">
<!-- END THEME STYLES -->
<!-- BEGIN APPLICATION STYLES -->
<link href="../css/autods.css" rel="stylesheet" type="text/css"/>
<!-- END APPLICATION STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-quick-sidebar-over-content page-style-square"> 
<!-- BEGIN HEADER -->
<jsp:include page="header.jsp" />
<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<jsp:include page="navigationSidebar.jsp" />
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
		<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade bs-modal-lg" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">添加媒体项目</h4>
						</div>
						<div class="modal-body">
							 <table class="table table-striped table-bordered table-hover" id="sample_1">
							<thead>
							<tr>
								<th class="table-checkbox">
									<input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes"/>
								</th>
								<th>
									 ID
								</th>
								<th>
									 预览
								</th>
								<th>
									 基本信息
								</th>
								<th>
									 最后修改时间
								</th>
								<th>
									 状态
								</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="media" items="${mediaList}">
	                 		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%> 
	                		<tr class="odd gradeX">
	                			<td>
									<input type="checkbox" class="checkboxes" value="1"/>
								</td>
	                    		<td id="${media.id}">${media.id}</td>
	                    		<td>
	                    		<img src="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${media.thumbnailDownloadPaths.extraSmall}">
	                    		</td>
												<td>名称：<a
													href="<%=path%>/media/details.do?mediaId=${media.id}"
													class="properties">${media.name}</a><br> 
													类型： 
													<c:choose>
														<c:when test="${media.prettifyType == 'Image'}">
															图像
														</c:when>
														<c:when test="${media.prettifyType == 'Video'}">
															视频
														</c:when>
														<c:otherwise>
															${media.prettifyType}
														</c:otherwise>
													</c:choose>
												</td>
								<td>${media.lastModified}<br>
								修订：${media.revision}</td>
	                    		<td>                    		
	                    			<c:choose>
										<c:when test="${media.approvalStatus == 'APPROVED'}">
											<span class="label label-sm label-success">已批准</span>											
										</c:when>
										<c:when test="${media.approvalStatus == 'PENDING_APPROVAL'}">
											<span class="label label-sm label-warning">待批准</span>
										</c:when>
										<c:when test="${media.approvalStatus == 'REJECTED'}">
											<span class="label label-sm label-danger">已拒绝</span>
										</c:when>
										<c:otherwise>
										<span class="label label-sm label-default">${media.approvalStatus}</span>											
										</c:otherwise>
									</c:choose> 								
	                    		</td>
	                  		</tr>	
							</c:forEach>					
							</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" id="addMedia-confirm" class="btn blue" data-dismiss="modal">添加</button>						
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN DELETE CONFIRM MODAL FORM -->
			<div class="modal fade" id="remove-confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">删除项</h4>
						</div>
						<div class="modal-body">
							 确认要从此编排表中删除所选媒体项目？
						</div>
						<div class="modal-footer">
							<button type="button" id="removePlaylistItems-confirm" class="btn blue" data-dismiss="modal">删除</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END DELETE CONFIRM MODAL FORM -->
			<!-- BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			文件夹管理 <small>详细信息设置</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/folder/list.do">文件夹列表</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/folder/details.do?id=1">文件夹详细信息</a>
					</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN Table ROWS -->
			<div class="row">
				<div class="col-md-12">
				<div class="tabbable tabbable-custom boxless tabbable-reversed">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#tab_0" data-toggle="tab">
								基本信息 </a>
							</li>
							<li>
								<a href="#tab_1" data-toggle="tab">
								媒体项目 </a>
							</li>
						</ul>
						<div class="tab-content">
								<div class="alert alert-danger display-hide"
									style="display: none;">
									<button class="close" data-close="alert"></button>
									<span>You have some form errors. Please check below.</span>
								</div>
								<div class="alert alert-success display-hide"
									style="display: none;">
									<button class="close" data-close="alert"></button>
									<span>成功修改!</span>
								</div>
								<!-- BEGIN TAB0 -->
							<div class="tab-pane active" id="tab_0">
							<div class="display-hide" style="display: none;" id="playlistId">${playlistId}</div>
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>文件夹A
										</div>
										<div class="tools">
											<a href="" class="reload"> </a>
										</div>
									</div>
									<div class="portlet-body form">
										<!-- BEGIN FORM-->
										<form action="#" class="horizontal-form">
											<div class="form-body">
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">文件夹名称：</label>
															<input type="text" class="form-control" placeholder="请输入名称…" name="name" id="name" value="文件夹A">													
														</div>
													</div>
													<div class="col-md-6">
														<label class="control-label col-md-3">上级文件夹：</label>
														<div class="theme-option">
																<select class="form-control">
																	<option selected="selected">父文件夹A</option>
																	<option >父文件夹B</option>
																	<option >父文件夹C</option>
																</select>
															</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-12 ">
														<div class="form-group">
															<label>描述</label>
															<textarea class="form-control" rows="3"
																placeholder="输入描述…" id="description">图像媒体内容</textarea>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static">上次修改时间在2015-10-09 15:32:43</p>
														</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="button" class="btn blue" id="updatePlaylist-confirm">
													<i class="fa fa-check"></i>确认
												</button>
												<button type="button" class="btn default cancel">取消</button>
											</div>
										</form>
										<!-- END FORM-->
									</div>
								</div>
							</div>
							<!-- END TAB0 -->
							<!-- BEGIN TAB1 -->
							<div class="tab-pane" id="tab_1">
							<div class="row">
							<div class="col-md-12">
									<div class="portlet box blue">
										<div class="portlet-title">
											<div class="caption">
												<i class="fa fa-gift"></i>${playlistDetail.name}
											</div>
											<div class="actions">
												<a href="#remove-confirm" data-toggle="modal" class="btn red" id="delete_option" style="display: none;"> <i class="fa fa-minus"></i>
													删除项
												</a>
												<a href="#portlet-config" data-toggle="modal" class="btn green"> <i class="fa fa-plus"></i>
													添加项
												</a>
											</div>
										</div>
										<div class="portlet-body">
											<div class="scroller" style="height: 500px">
														<table
															class="table table-striped table-bordered table-hover"
															id="playlistItems">
															<thead>
																<tr>
																	<th class="table-checkbox"><input type="checkbox"
																		class="group-checkable"
																		data-set="#playlistItems .checkboxes" /></th>
																	<th>顺序</th>
																	<th>预览</th>
																	<th>基本信息</th>
																	<th>详细信息</th>
																	<th>状态</th>
																</tr>
															</thead>
															<tbody>
																<c:forEach var="playlistItem" items="${playlistItems}">
																	<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>
																	<tr class="odd gradeX">
																		<td><input type="checkbox" class="checkboxes"
																			value="1" /></td>
																		<td id="${playlistItem.id}">${playlistItem.sortOrder}</td>
																		<td><a
																			href="<%=path%>/playlist/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${playlistItem.media.thumbnailDownloadPaths.large}"
																			class="fancybox-button" data-rel="fancybox-button">
																				<img
																				src="<%=path%>/playlist/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${playlistItem.media.thumbnailDownloadPaths.extraSmall}">
																		</a></td>
																		<td>名称：<a
																			href="<%=path%>/media/details.do?mediaId=${playlistItem.media.id}"
																			class="properties">${playlistItem.media.name}</a><br> 类型：
																			<c:choose>
																				<c:when test="${playlistItem.media.prettifyType == 'Image'}">
																					图像
																				</c:when>
																				<c:when test="${playlistItem.media.prettifyType == 'Video'}">
																					视频
																				</c:when>
																				<c:otherwise>
																					${playlistItem.media.prettifyType}
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<td>最后修改：${playlistItem.media.lastModified}<br>
																		项目持续时间：${playlistItem.durationHoursSeconds}
																		</td>
																		<td><c:choose>
																				<c:when test="${playlistItem.media.approvalStatus == 'APPROVED'}">
																					<span class="label label-sm label-success">已批准</span>
																				</c:when>
																				<c:when
																					test="${playlistItem.media.approvalStatus == 'PENDING_APPROVAL'}">
																					<span class="label label-sm label-warning">待批准</span>
																				</c:when>
																				<c:when test="${playlistItem.media.approvalStatus == 'REJECTED'}">
																					<span class="label label-sm label-danger">已拒绝</span>
																				</c:when>
																				<c:otherwise>
																					<span class="label label-sm label-default">${playlistItem.media.approvalStatus}</span>
																				</c:otherwise>
																			</c:choose></td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
										</div>
									</div>
							</div>
							</div>
							</div>
							<!-- END TAB1 -->
						</div>
					</div>
				</div>
			</div>
			<!-- END Table ROWS -->
			<!--  div class="clearfix" -->
			<!--  div-->
		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="footer.jsp" />
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../assets/global/plugins/moment.min.js"></script>
<script type="text/javascript" src="../assets/admin/pages/scripts/components-pickers.js"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../assets/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="../assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="../assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
<script src="../assets/admin/pages/scripts/table-managed.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="../scripts/autods-common.js" type="text/javascript"></script>
<script src="../scripts/navigationbar-action.js" type="text/javascript"></script>
<script src="../scripts/content-manager.api.js" type="text/javascript"></script>
<script>
jQuery(document).ready(function() {    
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout
	   QuickSidebar.init(); // init quick sidebar
	   Demo.init(); // init demo features
	   TableManaged.init();
	   autodsCommon.init();
	   ContentManagerAPI.init();
	   navigationBar.activeSystemMenu();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>