<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<% String username = session.getAttribute("username").toString();%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
	<div class="page-sidebar navbar-collapse collapse">
		<!-- BEGIN SIDEBAR MENU -->
		<!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
		<!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
		<!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
		<!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
			<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
			<li class="sidebar-toggler-wrapper">
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				<div class="sidebar-toggler">
				</div>
				<!-- END SIDEBAR TOGGLER BUTTON -->
			</li>
			<li class="home-menu">
				<a href="javascript:;">
				<i class="icon-home"></i>
				<span class="title">首页</span>
				<span class="selected"></span>
				<span class="arrow open"></span>
				</a>
				<ul class="sub-menu">
					<li class="active">
						<a href="<%=path%>/auth/dashboard.do">
						<i class="icon-bar-chart"></i>
						工作中心</a>
					</li>
				</ul>
			</li>
			<li class="media-menu">
				<a href="javascript:;">
				<i class="icon-basket"></i>
				<span class="title">营销运营管理</span>
				<span class="arrow "></span>
				</a>
				<ul class="sub-menu">
					<li>
						<a href="<%=path%>/media/list.do" >
						<i class="icon-home"></i>
						营销媒体</a>
					</li>
					<li>
						<a href="<%=path%>/media/approval.do">
						<i class="icon-basket"></i>
						营销审核</a>
					</li>
					<li>
						<a href="<%=path%>/playlist/list.do">
						<i class="icon-tag"></i>
						营销编排</a>
					</li>
					<li>
						<a href="<%=path%>/channel/publish.do">
						<i class="icon-handbag"></i>
						营销发布</a>
					</li>
					<!-- li>
						<a href="ecommerce_products_edit.html">
						<i class="icon-pencil"></i>
						Product Edit</a>
					</li -->
				</ul>
			</li>
			<li class="network-menu">
				<a href="javascript:;">
				<i class="icon-rocket"></i>
				<span class="title">营销网络管理</span>
				<span class="arrow "></span>
				</a>
				<ul class="sub-menu">
					<li>
						<a href="<%=path%>/frameset/list.do">
						显示框架</a>
					</li>
					<li>
						<a href="<%=path%>/channel/list.do">
						营销频道</a>
					</li>
					<li>
						<a href="<%=path%>/schedule/info.do">
						营销计划</a>
					</li>
					<li>
						<a href="<%=path%>/player/list.do">
						播放器</a>
					</li>
					<li>
						<a href="<%=path%>/location/list.do">
						播放位置</a>
					</li>
				</ul>
			</li>
			<% 
			if("administrator".equals(username)){
			%>
			<li class="heading">
				<!-- li class="tooltips" data-container="body" data-placement="right" data-html="true" data-original-title="管理员相关操作" -->
					<h3 class="uppercase">系统管理</h3>
				<!-- /li-->
			</li>
			<li class="user-menu">
				<a href="javascript:;">
				<i class="icon-user"></i>
				<span class="title">系统用户管理</span>
				<span class="arrow "></span>
				</a>
				<ul class="sub-menu">
					<li>
						<a href="<%=path %>/user/list.do">
						用户管理</a>
					</li>
					<li>
						<a href="<%=path %>/role/list.do">
						权限管理</a>
					</li>
					<li>
						<a href="<%=path %>/position/list.do">
						职位管理</a>
					</li>
				</ul>
			</li>		
			<li class="system-menu">
				<a href="javascript:;">
				<i class="icon-settings"></i>
				<span class="title">系统设置管理</span>
				<span class="arrow "></span>
				</a>
				<ul class="sub-menu">
					<li>
						<a href="<%=path %>/workgroup/list.do">
						工作组设置</a>
					</li>
					<li>
						<a href="<%=path %>/folder/list.do">
						文件夹设置</a>
					</li>
					<li>
						<a href="<%=path %>/system/settings.do">
						系统参数设置</a>
					</li>
				</ul>
			</li>
			<%}%>		
			<li class="heading">
				<h3 class="uppercase">更多</h3>
			</li>
			<li class="help-menu">
				<a href="javascript:;">
				<i class="icon-logout"></i>
				<span class="title">帮助中心</span>
				<span class="arrow "></span>
				</a>
				<ul class="sub-menu">
					<li>
						<a href="quick_sidebar_push_content.html">
						用户手册</a>
					</li>
					<li>
						<a href="quick_sidebar_push_content.html">
						系统API</a>
					</li>
				</ul>
			</li>
		</ul>
		<!-- END SIDEBAR MENU -->
	</div>
</div>
<!-- END SIDEBAR -->