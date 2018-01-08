<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.Calendar"%>
<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR); //获取年份
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>lucene文件检索</title>
<link type="text/css" rel="stylesheet" href="static/css/index.css">
</head>
<body>
	<div class="indexbox">
		<div class="logo">
			<a href="index.jsp">
				<img alt="文件检索" src="static/images/logo.png">
			</a>
		</div>
		<div class="searchform">
			<form action="SearchFile" method="get" >
				<input type="text" name="query"> <input type="submit"
					value="搜索">
			</form>
		</div>
		<div class="info">
			<p>基于Lucene的文件检索系统</p>
			<br />

		</div>
	</div>
</body>
</html>
