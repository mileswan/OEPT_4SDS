<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oept.autods.common.util.PropFileManager"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<%
	PropFileManager _propFileMgr = new PropFileManager("interface.properties");
	String ContentManagerURL = _propFileMgr.getProperty("ContentManager.uri");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/05/29
 * Description: Media list management page.
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
<title>营销媒体管理</title>
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
<link href="../assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
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
<!-- BEGIN NEW MEDIA DIV CSS author:jjzhu -->
<link href="../css/newpage.css" rel="stylesheet" type="text/css" />
<!-- END NEW MEDIA DIV CSS  -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
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
	<!-- BEGIN UPLOAD SAMPLE DIALOG FORM-->
			<div class="modal fade" id="new_upload_page" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" >
					<div class="modal-content">
						<!-- 15/10/14 NEW-->
						<form action="<%=path%>/media/addMediaUrl.do" method="post" >
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">添加网页媒体项</h4>
						</div>
						<div class="modal-body">
							 名称
							 <br>
							 <input id="add_page_uname" name="addMediaName" class="add_page_n" type="text" style="width: 540px;" placeholder="输入名称..">
							 <br>
							 <br>
							 URL
							 <br>
							 <input id="add_page_url" name="addMediaUrl" class="add_page_u" type="text" style="width: 540px;" placeholder="输入URL..">
							 <br>
						</div>
						<div class="modal-footer">
							<button type="submit" id="addMediaUrlSubmit" class="btn blue">创建</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
						</form>
						
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
	<!-- END UPLOAD SAMPLE DIALOG FORM-->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">删除</h4>
						</div>
						<div class="modal-body">
							 确认要删除所选内容？
						</div>
						<div class="modal-footer">
							<button type="button" id="deleteMedia-confirm" class="btn blue" data-dismiss="modal">删除</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			营销媒体管理 <small>媒体内容列表</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="">营销运营管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="">媒体内容列表</a>
					</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN MESSAGE PROMPT -->
			<div class="alert alert-success display-hide" style="display: none;">
				<button class="close" data-close="alert"></button>
					<span>You have some form success. Please check below.</span>
			</div>
			<div class="alert alert-danger display-hide" style="display: none;">
					<button class="close" data-close="alert"></button>
					<span>You have some form errors. Please check below.</span>
			</div>
			<!-- END MESSAGE PROMPT -->
			<!-- BEGIN Table ROWS -->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN Media List TABLE PORTLET-->
					<div class="portlet box grey-cascade">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>媒体内容列表
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="<%=path%>/media/list.do" class="reload">
								</a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="row">
									<div class="col-md-6">
										<!-- new begin author:jjzhu -->
										<!-- ../scripts/newpage.js -->
										<!-- ../css/newpage.css -->
										<div class="btn-group" id="btn-uploadShowDiv">
												
														<button onmouseover="mopen('newupload_div')"
															onmouseout="mclosetime()" id="sample_new_buttun"
															class="btn green">
															新建 <i class="fa fa-plus"></i>
														</button>
														<div id="newupload_div" class="sample_editable_div"
															onmouseover="mcancelclosetime()"
															onmouseout="mclosetime()">
															<button
																onclick="window.location.href = '<%=path%>/fileupload/mediaupload.do' "
																id="sample_editable_1_new" class="btn green" onmouseover="mb_over('sample_editable_1_new')"
																onmouseout="mb_out('sample_editable_1_new')">
																媒体上传 <i class="fa fa-plus"></i>
															</button>
															<a href="#new_upload_page" data-toggle="modal" style="text-decoration:blink;">
															<button id="sample_new_buttun_page"
																class="btn green" onmouseover="mb_over('sample_new_buttun_page')"
																onmouseout="mb_out('sample_new_buttun_page')">
																网页 <i class="fa fa-plus"></i>
															</button></a>
														</div>
												<!-- new end -->
										</div>
									</div>
									<div class="col-md-6">
											<div class="actions pull-right">
												<a href="#portlet-config" data-toggle="modal"
													class="btn red" id="delete_option" style="display: none;">
													<i class="fa fa-minus"></i> 删除项
												</a>
											</div>
									</div>
								</div>
							</div>
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
	                    		<a href="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${media.thumbnailDownloadPaths.large}" class="fancybox-button" data-rel="fancybox-button">
	                    		<img src="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${media.thumbnailDownloadPaths.extraSmall}"></a>
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
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
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
<script type="text/javascript" src="../assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../assets/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../assets/admin/pages/scripts/table-managed.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN NEW MEDIA DIV JS author:jjzhu -->
<script src="../scripts/newpage.js"></script>
<!-- END NEW MEDIA DIV JS -->
<script src="../scripts/content-manager.api.js" type="text/javascript"></script>
<script src="../scripts/navigationbar-action.js" type="text/javascript"></script>
<script src="../scripts/autods-common.js" type="text/javascript"></script>
<script>
jQuery(document).ready(function() {    
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout
	   QuickSidebar.init(); // init quick sidebar
	   Demo.init(); // init demo features
	   TableManaged.init();
	   autodsCommon.init();
	   ContentManagerAPI.init();
	   navigationBar.activeMediaMenu();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>