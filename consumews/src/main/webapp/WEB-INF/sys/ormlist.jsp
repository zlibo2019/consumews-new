<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<title>欢迎登录</title>
<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../themes/icon.css">
<script type="text/javascript" src="../themes/jquery.js"></script>
<script type="text/javascript" src="../themes/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../themes/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../themes/UICore.js"></script>
<script type="text/javascript" src="../themes/jquery.json.js"></script>
<script type="text/javascript" src="../js/sys/orm.js"></script>
<style type="text/css">
	.ormTable{
		border-collapse: collapse;
		table-layout: fixed;
		min-height: 300px;
		border:1px solid #aaa;
		font-size:12px;
	}
	
	.ormTable td{
		border:1px solid #aaa;
	}
	
	.divTable{
		border:1px solid #666;
		min-width: 140px;
		margin:2px;
		padding:2px;
		text-align:left;
		float:left;
		cursor: pointer;
	}
	
	.divTableFocus{
		border:1px solid red;
		font-weight:700;
	}
	
	.divPackage{
		border-collapse: collapse;
		table-layout: fixed;
		width:95%;
		border:1px solid #555;
		margin:auto;
		margin-top:5px;
		
	}
	
	.divPackageFocus{
		background-color: #eee;
	}
	
	.divPackage .cTable{
		height: 30px;
	}
</style>
</head>
<body>
<table class="ormTable">
	<tr style="height:40px;">
		<td style="padding-left:8px;border:none;font-weight:700;font-size:14px;">表导出类设置</td>
		<td style="border:none;"></td>
		<td style="text-align: right;border:none;padding-right:8px;" >
			导出路径:<input type="text" id="txt_savepath" style="width:120px;"  value="d:/orm/"/>	
			<a href="#" class="easyui-linkbutton" id="btn_expBag" data-options="iconCls:'icon-save'">导出</a>
		</td>
	</tr>
	<tr style="height:40px;">
		<td style="padding-left:8px;font-weight:700;font-size:14px;">
			<a href="#" class="easyui-linkbutton" id="btn_allTable" data-options="iconCls:'icon-add'">全选</a>
			<a href="#" class="easyui-linkbutton" id="btn_notAllTable" data-options="iconCls:'icon-add'">取反</a>
			<input type="text" style="width:160px;" id="table_search" />
		</td>
		<td style=""></td>
		<td style="text-align: right;padding-right:8px;" >
			<a href="#" class="easyui-linkbutton" id="btn_addBag" data-options="iconCls:'icon-add'">添加包</a>
			<a href="#" class="easyui-linkbutton" id="btn_delBag" data-options="iconCls:'icon-add'">删除包</a>
		</td>
	</tr>
	<tr>
		<td style="width:610px;vertical-align: top;" id="srcTable">
			<c:forEach items="${ormlist}" var="orm">
				<div class="divTable">${orm}</div>
			</c:forEach>
		</td>
		<td style="width:40px; vertical-align: top;">
			<img src="../images/Right.gif" id="left2right" style="cursor: pointer;"/>
			<br/>
			<img src="../images/Left.gif" id="right2left"  style="cursor: pointer;"/>
		</td>
		<td style="width:400px;vertical-align: top;text-align: center;">
			<table class="divPackage divPackageFocus" id="myBag">
				<tr>
					<td style="padding:5px;">
					<label>包名:</label>
					<input type="text" style="width:220px;"  value="com.garen.xxx"/>
					</td>
				</tr>
				<tr>
					<td class="cTable">
						
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>