<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/main.css?_4"></link>
<script type="text/javascript" src="themes/jquery.js"></script>
<script type="text/javascript" src="themes/jquery.easyui.min.js"></script>
<script type="text/javascript" src="themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="themes/UICore.js?_3"></script>
<script type="text/javascript" src="themes/UITable.js?_2"></script>
<script type="text/javascript" src="themes/extEasyUI.js?_2"></script>
<script type="text/javascript" src="themes/jquery.json.js"></script>
<script type="text/javascript" src="js/sys/index2.js?_<%= new java.util.Date().getTime()%>"></script>

<c:set var="layout_top" value="35"/> <%--布局高度 --%>
<c:set var="layout_left" value="220"/> <%--布局高度 --%>

<style  type="text/css">
	.div_layout{
		position: absolute;left:0;top:0;right:0;bottom:0;
	}

	.div_layout_top{
		height:${layout_top}px;
	    background: rgb(238,248,255);
	}
	
	.div_layout_left{
		position: absolute;left:0px;
		top:${layout_top}px;
		bottom:0;
		width:${layout_left}px;
		/* background:rgb(39,38,47) */
	}
	
	.div_layout_right{
		position: absolute;
		left:${layout_left}px;
		right:0;top:${layout_top}px;bottom:0;
		/* background:#888 */
	}
	<%--菜单项样式--%>
	.layout_left_menu{
		width: 96%; 
		text-align: left;
		display: inline-block;
		border: 1px solid #fff;
		color: #000000;
		margin:3px;
		height:17px;
		padding-top:5px;
		text-decoration: none;
	}
	<%--菜单项鼠标悬停时样式--%>
	.layout_left_menu:HOVER {
		background: #eaf2ff;
		border: 1px solid #b7d2ff;
		border-radius: 5px 5px 5px 5px;
	}
	
	.layout_left_menu span{
		padding-left:25px;
	}
</style>
</head> 
<body>
 <div class="div_layout">
 	<div class="div_layout_top">
 		<div class="titlebar" style="margin-left:8px;float:left;height:30px;margin-top:5px;
                background: url('images/index_logo.gif') no-repeat;border:0px solid red;padding-top:4px;">
                    <span class="icon-hamburg-user"  style="background-image: none;padding-left:115px;padding-top:8px;">
                    </span>
        </div>
        <div style="width:600px;padding-top:5px;float:right;text-align:right;margin-right:30px;">
	         <a href="#" class="easyui-linkbutton"
					id="layout_btnModify_Pwd" data-options="plain:true,iconCls: 'icon-Modify_Pwd'">修改密码</a>
	        <a href="#" class="easyui-linkbutton"
					id="layout_btnExit" data-options="plain:true,width:60,iconCls: 'icon-Login_out'">退出</a>
        </div>
 	</div>
 	<div class="div_layout_left" id="div_layout_left">
 		<div id="layout_left_header" class="panel-header" style="width: ${layout_left-16}px;">
 			<div class="panel-title">${USER.realName},欢迎光临</div>
			<div class="panel-tool">
				<a class="panel-tool-collapse" href="javascript:void(0)" style="display: none;"></a>
				<a href="javascript:void(0)" id="layout_left_menu" class="layout-button-left"></a>
			</div>
		</div>
		<div  style="width: ${layout_left-8}px; padding: 1px; height: 100%;" 
				id="div_left_accordion" class="panel-body layout-body">
				<c:set value="0" var="flag" />  <%--菜单状态标志 --%>
		 		<c:forEach items="${menulist}" var="mainmenu" varStatus="status">
		              <c:if test="${mainmenu.parentId == 0}">
		              	<div class="accordion accordion-noborder" style="text-align: left;">
		              		<div class="panel-header panel-header-noborder accordion-header ${flag==0?'accordion-header-selected':''}" 
										style="height: 16px; width: 204px;">
									<div class="panel-title panel-with-icon">${mainmenu.name}</div>
									<div class="panel-icon icon-hamburg-u44"></div>
									<div class="panel-tool">
										<a class="panel-tool-collapse panel-tool-expand" href="javascript:void(0)" style="display: none;"></a>
										<a href="javascript:void(0)" class="accordion-collapse ${flag==0?'':'accordion-expand'}"></a>
									</div>
							</div>
		                	<div style="overflow: hidden; display: ${flag==0?'block':'none'}; width: 214px; height: auto;"
								class="panel-body panel-body-noborder accordion-body">
			                <c:forEach items="${menulist}" var = "submenu">
				 		 		  <c:if test="${ submenu.parentId == mainmenu.id}">
		                            <a href="#" class="layout_left_menu" id="${submenu.url}">
										<span>${submenu.name}</span>
									</a> 
		                          </c:if>
				            </c:forEach>
				            </div>
			            </div>	
		              	<c:if test="${ flag == 0}"><%--默认第一个选中状态 --%>
		              		<c:set value="1" var="flag" />
		              	</c:if>
		              </c:if>
		        </c:forEach>
		</div>

		<div id="layout_left_expand" class="panel layout-expand layout-expand-west"
			style="display: none; left: 0px; top: 0px; width: 28px;height: 100%;">
			<div class="panel-header" style="width: 16px;">
				<div class="panel-title">&nbsp;</div>
				<div class="panel-tool">
					<a href="javascript:void(0)" id="layout_left_menu_expand"  class="layout-button-right"></a>
				</div>
			</div>
			<div title="" class="panel-body" style="width: 26px; height: 100%;"></div>
		</div>

	</div>
 	
 	<div class="div_layout_right" id="div_layout_right">
 		<div class="easyui-tabs" id="layout_tabs" style="width:100%" 
 				data-options="fit:true,title:'',tabHeight:32">
 		   <div title="主页" 
				data-options="iconCls: 'icon-hamburg-home'" style="padding:0px;overflow:hidden;"> 
			</div>
 		</div>
 	</div>
 </div>
</body>
</html>