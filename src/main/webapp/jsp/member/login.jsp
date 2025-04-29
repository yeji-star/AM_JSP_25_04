
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

</head>

<body>

	<h2>로그인</h2>


	<a href="../home/main">메인으로 이동</a>
	<a href="join">회원가입</a>

	<script type="text/javascript">
		function loginForm__submit(form) { // 빈칸없이 잘썼냐

			let loginPw = form.loginPw.value.trim();

			if (form.loginId.value.trim().length == 0) {
				alert("아이디를 써주세요");
				form.loginId.focus();
				return;
			}
			if (loginPw.length == 0) {
				alert("비밀번호를 써주세요");
				form.loginPw.focus();
				return;
			}

			form.submit();
		}
	</script>

	<form onsubmit="loginForm__submit(this); return false;" action="dologin" method="post">
		<div>
			아이디 : <input autocomplete="off" type="text" placeholder="아이디 입력"
				name="loginId" />

		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비밀번호 입력"
				name="loginPw" />
		</div>
		<button type="submit">로그인</button>
	</form>

</body>


</html>
