<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/09/03
 * Description:  Channel details management page.
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
<title>营销频道管理</title>
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
			营销网络管理 <small>营销频道设置</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/channel/list.do">播放频道列表</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/channel/details.do?channelId=${channelId}">频道详细信息</a>
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
								显示属性 </a>
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
							<div class="display-hide" style="display: none;" id="channelId">${channelId}</div>
							<div class="display-hide" style="display: none;" id="channelName">${channelDetail.name}</div>
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${channelDetail.name}
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
															<label class="control-label">名称：</label>
															<input type="text" class="form-control" placeholder="请输入名称…" name="channelName" id="channelName" value="${channelDetail.name}">													
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label>类型：</label>
																<c:choose>
																	<c:when test="${channelDetail.type == 'AUDIOVISUAL'}">
																		Audiovisual
																	</c:when>
																	<c:otherwise>
																		${channelDetail.type}
																	</c:otherwise>
																</c:choose>					
															</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-12 ">
														<div class="form-group">
															<label>描述</label>
															<textarea class="form-control" rows="3"
																placeholder="输入描述…" id="description">${channelDetail.description}</textarea>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">在可视媒体中禁用音频：</label>
															<div class="inlineEdit">
																<span class="button-group muteAudioFromVisual">
																	<button data-value="true" class="${muteAudioFromVisual_true}">是</button>
																	<button data-value="false" class="${muteAudioFromVisual_false}">否</button>
																</span>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="button" class="btn blue" id="updateChannel-confirm">
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
							<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${channelDetail.name}
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
															<div class="theme-option">
																<label class="control-label">屏幕分辨率：</label>
																<select class="form-control">
																	<option width="1024"  height="768" selected="selected">1024 x 768</option>
																	<option width="1024"  height="768">1280 x 1024</option>
																	<option width="1024"  height="768">1920 x 1080</option>
																	<option value="user define">自定义…</option>
																</select>
															</div>
														</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label>框架集名称：</label>
															<a>${channelDetail.frameset.name}</a>				
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="actions" style="display:none;">
																<a href="#" class="btn yellow"> <i
																	class="fa fa-pencil"></i> 编辑框架时间表
																</a>
														</div>
														<div class="framesetThumbnailContainer" style="height: 306.5px; width: 306.5px;">
															<div class="framesetThumbnail"
																style="height: 72%; width: 100%;">
																<c:forEach var="frame" items="${channelDetail.frameset.frames}">
																	<div id="${frame.id}" class="frame"
																		style="width: ${frame.width/channelDetail.frameset.width*100}%; height: ${frame.height/channelDetail.frameset.height*100}%; left: ${frame.left/channelDetail.frameset.width*100}%; top: ${frame.top/channelDetail.frameset.height*100}%; position: absolute; background-color: ${frame.color};">
																	<h6>${frame.name}</h6>
																	</div>
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
									</div>
									</form>
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
	   navigationBar.activeNetworkMenu();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>