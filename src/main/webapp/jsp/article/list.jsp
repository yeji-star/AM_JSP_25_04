<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
// 뒤에 있는 게 List 형식이 아니면 어떡하냐고 물어보니까 앞에 List 형변환을 함
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>

	<h2>게시글 목록</h2>

	<a href="https://www.naver.com/" target="_blank">네이버</a>
	<a href="http://localhost:8080/AM_JSP_25_04/article/list"
		target="_blank">리스트 새 탭</a>

	<a href="http://localhost:8080/AM_JSP_25_04/article/detail"
		target="_blank">디테일 새 탭</a>

	<ul>
		<%
		for (Map<String, Object> articleRow : articleRows) { // 아티클로우즈의 사이즈를 검색한 거임
		%>
		<li><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("id")%>번,
				<%=articleRow.get("regDate")%>, <%=articleRow.get("title")%>, <%=articleRow.get("body")%></a>
		</li>
		<%
		}
		%>
	</ul>


</body>
</html>