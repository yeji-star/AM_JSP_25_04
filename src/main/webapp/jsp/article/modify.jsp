<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.KoreaIT.java.AM_jsp.dto.Article"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Article article = (Article) request.getAttribute("article");
// 뒤에 있는 게 List 형식이 아니면 어떡하냐고 물어보니까 앞에 List 형변환을 함
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=article.getId()%>글 수정</title>

</head>
<body>

	<h2><%=article.getId() %>글 수정</h2>


	<a href="../home/main">메인으로 이동</a>

	<form action="doModify" method="post">
	<input type="hidden" value="<%=article.getId() %>" name="id" />
	<div>
		번호 :
		<%=article.getId()%>
	</div>
	<div>
		날짜 :
		<%=article.getRegDate()%>
	</div>
		<div>
			새 제목 : <input type="text" placeholder="제목 입력" name="title" />
		</div>
		<div>
			새 내용 : <textarea type="text" placeholder="내용 입력" name="body"></textarea>
		</div>
		<button type="submit">수정</button>
	</form>




</body>
</html>