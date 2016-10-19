<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/08/10
 * Description:  Locations list management page.
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
<title>播放位置管理</title>
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
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/jstree/dist/themes/default/style.min.css"/>
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
							<button type="button" id="deleteChannel-confirm" class="btn blue" data-dismiss="modal">删除</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN NEW PLAYLIST CONFIRM MODAL FORM -->
			<div class="modal fade" id="new-confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">新建播放位置</h4>
						</div>
						<div class="modal-body">
							<label class="control-label">名称：</label>
							<input type="text" class="form-control" placeholder="请输入名称…" name="name" id="name">
							<label>描述</label>
							<textarea class="form-control" rows="3"	placeholder="输入描述…" id="description"></textarea>
							<div class="theme-option">
							<span>上级位置</span>
							<select class="form-control">
								<option value="SCALA" selected="selected">北京分店</option>
								<option value="ANDROID_TYPE_1">深圳分店</option>
								<option value="IADEA_SD">上海分店</option>
							</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" id="newPlayer-confirm" class="btn blue" data-dismiss="modal">确认</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END NEW PLAYLIST CONFIRM MODAL FORM -->
			<!-- BEGIN STYLE CUSTOMIZER -->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			播放位置管理 <small>播放位置列表</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/location/list.do">播放位置管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/location/list.do">播放位置列表</a>
					</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
				<div class="alert alert-danger display-hide" style="display: none;">
					<button class="close" data-close="alert"></button>
					<span>You have some form errors. Please check below.</span>
				</div>
				<div class="alert alert-success display-hide" style="display: none;">
					<button class="close" data-close="alert"></button>
					<span>成功修改!</span>
				</div>
				<!-- BEGIN Table ROWS -->
			<div class="row">
				<div class="col-md-6">
					<!-- BEGIN LOCATION List TREE-->
					<div class="portlet blue box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>播放位置列表
							</div>
							<div class="tools">
<!-- 								<a href="#new-confirm" data-toggle="modal" class="btn green"> -->
<!-- 													<i class="fa fa-plus"></i> 新建 -->
<!-- 												</a> -->
								<a href="javascript:;" class="reload">
								</a>
							</div>
						</div>
						<div class="portlet-body">
							<div id="location_tree" class="tree-demo">
							</div>
						</div>
					</div>
					<!-- END LOCATION List TREE-->
				</div>
				<div class="col-md-6">
					<!-- BEGIN MEMBER List TREE-->
					<div class="portlet green box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>终端成员列表
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="javascript:;" class="reload">
								</a>
							</div>
						</div>
						<div class="portlet-body">
							<div id="member_tree" class="tree-demo">
							</div>
						</div>
					</div>
					<!-- END MEMBER List TREE-->
<!-- 					BEGIN LOCATION BASIC INFO -->
<!-- 					<div class="portlet blue box"> -->
<!-- 						<div class="portlet-title"> -->
<!-- 							<div class="caption"> -->
<!-- 								<i class="fa fa-cogs"></i>基本信息 -->
<!-- 							</div> -->
<!-- 							<div class="tools"> -->
<!-- 								<a href="javascript:;" class="collapse"> -->
<!-- 								</a> -->
<!-- 								<a href="javascript:;" class="reload"> -->
<!-- 								</a> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="portlet-body"> -->
<!-- 							/row -->
<!-- 								<div class="row"> -->
<!-- 									<div class="col-md-3"> -->
<!-- 										<div class="form-group"> -->
<!-- 											<label class="control-label">名称：</label> <input type="text" -->
<!-- 												class="form-control" placeholder="请输入名称…" name="playerName" -->
<!-- 												id="playerName" value=""> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 					</div> -->
<!-- 					END LOCATION BASIC INFO -->
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
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../assets/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/global/plugins/jstree/dist/jstree.min.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../scripts/content-manager.api.js" type="text/javascript"></script>
<script src="../scripts/navigationbar-action.js" type="text/javascript"></script>
<script src="../scripts/autods-common.js" type="text/javascript"></script>
<script>
jQuery(document).ready(function() {    
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout
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