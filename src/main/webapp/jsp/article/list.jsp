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
<style>
table>thead>tr>th, table>tbody>tr>td {
	padding: 5px;
}
</style>
</head>
<body>

	<h2>게시글 목록</h2>

	<a href="../home/main">메인으로 이동</a>

	<table border="1px">

		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
				<th>내용</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Map<String, Object> articleRow : articleRows) { // 아티클로우즈의 사이즈를 검색한 거임
			%>

			<tr style="text-align: center;">
				<td><%=articleRow.get("id")%>번</td>
				<td><%=articleRow.get("regDate")%></td>
				<td><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("title")%></a></td>
				<td><%=articleRow.get("body")%></td>
				<td><a
					onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
					href="doDelete?id=<%=articleRow.get("id")%>">del</a></td>
			</tr>
			<%
			}
			%>
		</tbody>

	</table>




</body>
</html>