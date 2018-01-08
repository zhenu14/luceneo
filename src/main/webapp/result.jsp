<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" 
	import="java.util.ArrayList"
	import="com.luceneo.web.model.FileModel"
	import="java.util.regex.*"
	import="com.luceneo.web.service.RegexHtml"
	import="java.util.Iterator"%>
<%
	String path = request.getContextPath();//获取工程根目录
	String basePath = request.getScheme() + "://" + request.getServerName() 
	                  + ":" + request.getServerPort()+ path + "/";
	String regEx_html = "<[^>]+>";
	// 创建 Pattern 对象
	Pattern r = Pattern.compile(regEx_html);
	// 现在创建 matcher 对象
	RegexHtml regexHtml = new RegexHtml();
	ArrayList<FileModel> hitsList = (ArrayList<FileModel>) request.getAttribute("hitsList");
	String queryback = (String) request.getAttribute("queryback");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>搜索结果</title>
<link type="text/css" rel="stylesheet" href="static/css/result.css">
</head>
<body>
	<div class="searchbox">
		<div class="logo">
			<a href="index.jsp"><img alt="文件检索" src="static/images/logo.png"></a>
		</div>
		<div class="searchform">
			<form action="SearchFile" method="get">
				<input type="text" name="query" value="<%=queryback%>"> <input
					type="submit" value="搜索">
			</form>
		</div>
	</div>
	<div class="result">
		<h4>
			共搜到<span style="color: red; font-weight: bold;"><%=hitsList.size()%></span>条结果
		</h4>
		<%
			if (hitsList.size() > 0) {
				Iterator<FileModel> iter = hitsList.iterator();
				FileModel fm;
				while (iter.hasNext()) {
					fm = iter.next();
		%>
		<div class="item">
			<div class="itemtop">
				<h4>
					<img alt="pdf" src="static/images/<%=fm.getTitle().split("\\.")[1]%>.png"
						class="doclogo">
					<%=fm.getTitle().split("\\.")[0]%>
				</h4>
				<h3>
					<a href="FileDownloadServlet?filename=
					<%=regexHtml.delHtmlTag(fm.getTitle())%>">点击下载</a>
				</h3>
			</div>
			<div class="itembuttom">
				<p><%=fm.getContent().length() > 210 ? fm.getContent().substring(0, 210)
						: fm.getContent()%>...	</p>
			</div>
			<hr class="itemline">
		</div>
		<%
			}
			}
		%>
	</div>
	<div class="footer">
		<p>《Elasticsearch入门与实战》之Lucene项目案例</p>
	</div>

</body>
</html>