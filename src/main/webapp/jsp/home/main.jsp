<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
boolean isLogined = (boolean) request.getAttribute("isLogined");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<h2>메인 페이지</h2>

	<%
	if (isLogined) {
	%>
	<div>
	<%=loginedMemberId%>번 회원 로그인중
		<a href="../member/doLogout">로그아웃</a>
	</div>
	<%
	}
	%>

	<%
	if (!isLogined) {
	%>
	<div>
		<a href="../member/login">로그인</a>
	</div>
	<%
	}
	%>
	
	<ul>
		<li><a href="../article/list">리스트로 이동</a></li>
		<li><a href="../member/join">회원가입</a></li>
	</ul>
</body>
</html>