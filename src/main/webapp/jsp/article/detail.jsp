<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
// 뒤에 있는 게 List 형식이 아니면 어떡하냐고 물어보니까 앞에 List 형변환을 함
%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
</head>
<body>

	<a href="../home/main">메인으로 이동</a>

	<h2>게시글 상세페이지</h2>

	<div>
		번호 :
		<%=articleRow.get("id")%>
	</div>
	<div>
		날짜 :
		<%=articleRow.get("regDate")%>
	</div>
	<div>
		제목 :
		<%=articleRow.get("title")%>
	</div>
	<div>
		내용 :
		<%=articleRow.get("body")%>
	</div>
	
	<div>
	<button class="btn1">삭제</button>
	<script>
	$('.btn1').click(funtion() {
		
	})
	</script>
	</div>

	<div>
		<a style="color: green;" href="list">리스트로 돌아가기</a>
	</div>

</body>
</html>