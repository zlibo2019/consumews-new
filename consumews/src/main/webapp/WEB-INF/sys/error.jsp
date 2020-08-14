<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/taglib.jsp" %>

<script type="text/javascript">
	try{
		$(function(){//正文内返回错误时执行
			$.alert('${mbean.info}');
		});
	}catch(ex){
		alert('${mbean.info}');
		window.location = "./";//返回首页
	}
</script>

