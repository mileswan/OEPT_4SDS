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
 * Date: 2015/08/21
 * Description:  Media details management page.
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
<title>媒体信息管理</title>
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
			<!-- BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			营销运营管理 <small>媒体信息设置</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/media/list.do">媒体内容列表</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/media/details.do?mediaId=${mediaId}">媒体详细信息</a>
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
								历史记录 </a>
							</li>
							<li>
								<a href="#tab_2" data-toggle="tab">
								预览 </a>
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
							<div class="display-hide" style="display: none;" id="mediaId">${mediaId}</div>
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${mediaDetail.name}
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
															<label>类型：</label>
																<c:choose>
																	<c:when test="${mediaDetail.prettifyType == 'Image'}">
																		<p class="form-control-static">图像</p>
																	</c:when>
																	<c:when test="${mediaDetail.prettifyType == 'Video'}">
																		<p class="form-control-static">视频</p>
																	</c:when>
																	<c:otherwise>
																		<p class="form-control-static">${mediaDetail.prettifyType}</p>
																	</c:otherwise>
																</c:choose>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>时长：</label>
															<p class="form-control-static">${mediaDetail.prettifyDuration}</p>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label>尺寸：</label>
															<p class="form-control-static">${mediaDetail.width} x
																${mediaDetail.height}</p>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>大小：</label>
															<p class="form-control-static">${mediaDetail.prettifyLength}</p>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-12 ">
														<div class="form-group">
															<label>描述</label>
															<textarea class="form-control" rows="3"
																placeholder="输入描述…" id="description">${mediaDetail.description}</textarea>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label" for="starttime">有效开始日期：</label>
																<div class="input-group input-medium date date-picker"
																	data-date-format="yyyy-mm-dd"
																	data-date="${mediaDetail.startValidDate}">
																	<input type="text" class="form-control" readonly id="starttime" value="${mediaDetail.startValidDate}">
																	<span class="input-group-btn">
																		<button class="btn default" type="button">
																			<i class="fa fa-calendar"></i>
																		</button>
																	</span>
																</div>
															</div>
													</div>
													<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label" for="endtime">有效结束日期：</label>
																<div class="input-group input-medium date date-picker"
																	data-date-format="yyyy-mm-dd"
																	data-date="${mediaDetail.endValidDate}">
																	<input type="text" class="form-control" readonly
																		id="endtime" value="${mediaDetail.endValidDate}">
																	<span class="input-group-btn">
																		<button class="btn default" type="button">
																			<i class="fa fa-calendar"></i>
																		</button>
																	</span>
																</div>
														</div>
													</div>
													<!--/span-->
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">Audio ducking：</label>
															<div class="inlineEdit">
																<span class="button-group audioDucking">
																	<button data-value="true" class="${audioDucking_true}">开</button>
																	<button data-value="false"
																		class="${audioDucking_false}">关</button>
																</span>
															</div>
														</div>
													</div>
													<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<div class="form-group">
																<label class="control-label">全屏：</label>
																<div class="inlineEdit">
																	<span class="button-group playFullscreen">
																		<button data-value="true"
																			class="${playFullscreen_true}">开</button>
																		<button data-value="false"
																			class="${playFullscreen_false}">关</button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<!--/span-->
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static"></p>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static">由
																${mediaDetail.uploadedBy.username}
																在 ${mediaDetail.createdDate} 创建</p>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static">上次由
																${mediaDetail.modifiedBy.username}
																在 ${mediaDetail.lastModified} 修改</p>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static">由
																${mediaDetail.approvalDetail.user.username} 在
																${mediaDetail.approvalDetail.lastModified}
																	<c:choose>
																		<c:when test="${mediaDetail.approvalDetail.approvalStatus == 'APPROVED'}">
																			<span>已批准</span>
																		</c:when>
																		<c:when
																			test="${mediaDetail.approvalDetail.approvalStatus == 'PENDING_APPROVAL'}">
																			<span>待批准</span>
																		</c:when>
																		<c:when test="${mediaDetail.approvalDetail.approvalStatus == 'REJECTED'}">
																			<span>已拒绝</span>
																		</c:when>
																		<c:otherwise>
																			<span>${mediaDetail.approvalDetail.approvalStatus}</span>
																		</c:otherwise>
																	</c:choose>
															</p>
														</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="button" class="btn blue" id="updateMedia-confirm">
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
										<table
											class="table table-striped table-hover table-bordered dataTable no-footer"
											id="sample_editable_1" role="grid"
											aria-describedby="sample_editable_1_info">
											<tbody>
											<c:forEach var="mediaItem" items="${mediaDetail.mediaItemFiles}">
												<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>
												<tr role="row" class="odd">
													<td><a
														href="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${mediaItem.thumbnailDownloadPaths.large}"
														class="fancybox-button" data-rel="fancybox-button"> <img
															src="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerURL%>${mediaItem.thumbnailDownloadPaths.extraSmall}"></a>
													</td>
													<td>名称：${mediaItem.filename}<br>
														大小：${mediaItem.prettifySize}
													</td>
													<td>修订：${mediaItem.version}<br>
														上传：${mediaItem.uploadDate}<br>
														由 ${mediaItem.uploadedBy} 上传</td>
													<td>
														<c:choose>
															<c:when test="${mediaDetail.revision == mediaItem.version}">
															
															</c:when>
															<c:otherwise>
																<a class="edit" href="<%=path%>/media/restore.do?mediaId=${mediaId}&mediaItemFileId=${mediaItem.id}"> 设置为当前版本 </a>
															</c:otherwise>
														</c:choose>						
													</td>
												</tr>
											</c:forEach>												
											</tbody>
										</table>
								</div>

							<!-- END TAB1 -->
							<!-- BEGIN TAB2 -->
							<div class="tab-pane" id="tab_2">
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${mediaDetail.name}
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
													<div class="col-md-12">
														<div class="form-group" style="display: block;">
																<c:if test="${mediaDetail.prettifyType == 'Image'}">
																	<img class="img-responsive"
																			src="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerResourceURL%>${mediaDetail.previewURI}">
																</c:if>
																<c:if test="${mediaDetail.prettifyType == 'Video'}">
																	<video controls>
																			<source src="<%=path%>/media/getThumbnail.do?thumnailURL=<%=ContentManagerResourceURL%>${mediaDetail.previewURI}" type="video/mp4">
																	</video>
																</c:if>															
														</div>
													</div>
												</div>
											</div>
										</form>
										<!-- END FORM-->
									</div>
								</div>
							</div>
							<!-- END TAB2 -->
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
<script type="text/javascript" src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/moment.min.js"></script>
<script type="text/javascript" src="../assets/admin/pages/scripts/components-pickers.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../assets/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="../assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
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
	   autodsCommon.init();
	   ContentManagerAPI.init();
	   navigationBar.activeMediaMenu();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>