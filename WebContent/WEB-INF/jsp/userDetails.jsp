<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 
/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/09/06
 * Description:  User details management page.
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
<title>用户管理</title>
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
			用户管理 <small>用户信息设置</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="<%=path%>/auth/dashboard.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/user/list.do">用户列表</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="<%=path%>/user/details.do?userId=${userId}">用户详细信息</a>
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
								权限设置 </a>
							</li>
							<li>
								<a href="#tab_2" data-toggle="tab">
								其他设置 </a>
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
							<div class="display-hide" style="display: none;" id="userId">${userId}</div>
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${userDetails.lastname} ${userDetails.firstname}
										</div>
										<div class="tools">
											<a href="" class="reload"> </a>
										</div>
									</div>
									<div class="portlet-body form">
										<!-- BEGIN FORM-->
										<form action="<%=path%>/user/update.do" id="update_form"  method="post">
											<div class="form-body">
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">用户名：</label>
															<input type="text" class="form-control" placeholder="请输入用户名…" name="username" id="name" value="${userDetails.username}">													
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>电子邮箱：</label>
																<div class="input-group">
																	<span class="input-group-addon"> <i
																		class="fa fa-envelope"></i>
																	</span> <input type="text" class="form-control" name="emailaddress" id="emailaddress" value="${userDetails.emailaddress}"
																		placeholder="电子邮箱…">
																		<input type="text" name="userId" value="${userId }" style="display: none;">
																</div>
															</div>
													</div>	
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="checkbox-inline">
															<input type="checkbox" id="changePassword" value="changePassword">修改密码</label>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6 ">
														<div class="form-group">
															<label for="exampleInputPassword1">新密码</label>
															<div class="input-group">
																<input type="password" class="form-control" name="password"
																	id="newPassword" placeholder="请输入新密码…" readonly>
																<span class="input-group-addon"> <i
																	class="fa fa-user"></i>
																</span>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label>职位：</label>
																<div class="theme-option">
																	<%/*嵌套java代码，用以显示选中下拉框*/ %>
																	<%int i = 2;String upid ;%>
																	<%upid= request.getParameter("uic_pid");%>
																	<select class="form-control" id="select_position">
																	
																	<c:forEach var="allPosition" items="${allPosition}">
																	
																	<option <%if(upid.equals(Integer.toString(i))){out.print("selected='selected'");} %>  value="${allPosition.uic_position_id} ">${allPosition.uic_position_name }</option>
																	<%i++; %>
																	</c:forEach>
																	</select>
																	
																</div>
															</div>
														</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label for="exampleInputPassword1">确认新密码</label>
															<div class="input-group">
																<input type="password" class="form-control"
																	id="confirmPassword" placeholder="请确认新密码…" readonly>
																<span class="input-group-addon"> <i
																	class="fa fa-user"></i>
																</span>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<div class="theme-option">
																<label class="control-label">所在工作组：</label>
																<select class="form-control">
																	<option selected="selected">信息业务组</option>
																	<option >信息管理组</option>
																	<option >区域管理组</option>
																</select>
															</div>				
														</div>
													</div>
												</div>
												
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">姓氏：</label>
															<input type="text" class="form-control" placeholder="请输入姓氏…" name="lastname" id="lastname" value="${userDetails.lastname}">
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">名字：</label>
															<input type="text" class="form-control" placeholder="请输入名字…" name="firstname" id="firstname" value="${userDetails.firstname}">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<p class="form-control-static">
																最近一次登录是在 ${userDetails.lastLogin}</p>
														</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="button" onclick="form_userUpdate()" class="btn blue" id="updateUser-confirm">
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
											<i class="fa fa-gift"></i>${userDetails.lastname} ${userDetails.firstname}
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
															<label>权限角色：</label>
																<div class="radio-list">
																	<c:forEach var="role" items="${rolesList}">
	                    											<label>
	                    												<c:choose>
																				<c:when test="${role.checked == true}">
																					<input type="checkbox" id="${role.id}" checked> ${role.name}
																				</c:when>
																				<c:otherwise>
																					<input type="checkbox" id="${role.id}"> ${role.name}
																				</c:otherwise>
																		</c:choose>
																	</label>
	                    											</c:forEach>
																</div>
															</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="button" class="btn blue" id="updateUser-confirm1">
													<i class="fa fa-check"></i>确认
												</button>
												<button type="button" class="btn default cancel">取消</button>
											</div>
										</form>
										<!-- END FORM-->
									</div>
								</div>		
							</div>
							<!-- END TAB1 -->
							<!-- BEGIN TAB2 -->
							<div class="tab-pane" id="tab_2">
								<div class="portlet box blue">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>${userDetails.lastname} ${userDetails.firstname}
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
															<label class="control-label">账户是否激活：</label>
															<div class="inlineEdit">
																<span class="button-group userActive">
																	<button data-value="true" class="${userActive_true}">激活</button>
																	<button data-value="false" class="${userActive_false}">禁用</button>
																</span>
															</div>
														</div>
													</div>
													<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<div class="form-group">
																<label class="control-label">是否可修改密码：</label>
																<div class="inlineEdit">
																	<span class="button-group canChangePassword">
																		<button data-value="true"
																			class="${canChangePassword_true}">是</button>
																		<button data-value="false"
																			class="${canChangePassword_false}">否</button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">是否自动批准媒体内容：</label>
															<div class="inlineEdit">
																<span class="button-group isAutoMediaApprover">
																	<button data-value="true" class="${isAutoMediaApprover_true}">是</button>
																	<button data-value="false" class="${isAutoMediaApprover_false}">否</button>
																</span>
															</div>
														</div>
													</div>
													<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<div class="form-group">
																<label class="control-label">是否接受警告邮件：</label>
																<div class="inlineEdit">
																	<span class="button-group receiveEmailAlerts">
																		<button data-value="true"
																			class="${receiveEmailAlerts_true}">是</button>
																		<button data-value="false"
																			class="${receiveEmailAlerts_false}">否</button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<!--/row-->
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label">是否接受审批邮件：</label>
															<div class="inlineEdit">
																<span class="button-group receiveApprovalEmails">
																	<button data-value="true" class="${receiveApprovalEmails_true}">是</button>
																	<button data-value="false" class="${receiveApprovalEmails_false}">否</button>
																</span>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="form-actions right">
												<button type="submit" class="btn blue" id="updateUser-confirm2">
													<i class="fa fa-check"></i>确认
												</button>
												<button type="button" class="btn default cancel">取消</button>
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
<!-- begin user update -->
<script src="../scripts/user-page.js" type="text/javascript"></script>
<!-- end  -->
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
	   navigationBar.activeSystemMenu();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>