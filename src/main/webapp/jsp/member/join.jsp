
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script type="text/javascript">
	function checkNull() {
		var re = /^[a-zA-Z0-9]{4,12}$/

		if (idValue == null || idValue == "") {
			alert("id를 입력하세요.");
			return false;
		}

		if (pwValue == null || pwValue == "") {
			alert("비밀번호를 입력하세요.");
			return false;
		}

	}
</script>
</head>



<body>

	<h2>회원가입</h2>


	<a href="../home/main">메인으로 이동</a>

	<form action="doJoin" method="post" onsubmit="return checkNull();">
		<div>
			아이디 : <input type="text" placeholder="아이디 입력" name="loginId"
				id="loginId">
			<button type="button" name="checkId">중복확인</button>
		</div>
		<div>
			비밀번호 : <input type="text" placeholder="비밀번호 입력" name="loginPw"
				id="loginPw">
		</div>

		<div>
			이름 : <input type="text" placeholder="이름 입력" name="name">
		</div>
		<button type="submit">가입</button>
		<button type="reset">다시 입력</button>
	</form>

</body>


</html>
