
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

</head>

<body>

	<h2>회원가입</h2>


	<a href="../home/main">메인으로 이동</a>

	<script type="text/javascript">
		function JoinForm__submit(form) { // 빈칸없이 잘썼냐
			
			let loginPw = form.loginPw.value.trim();
			let loginPwConfirm = form.loginPwConfirm.value.trim();
			
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
			if (loginPwConfirm.length == 0) {
				alert("비밀번호 확인을 써주세요");
				form.loginPwConfirm.focus();
				return;
			}
			
			if(loginPw != loginPwConfirm) { // 비번과 비번 확인 서로 다를 때
				alert('비번을 다시 확인해주세요.');
				return;
			}
			
			if (form.name.value.trim().length == 0) {
				alert("이름을 써주세요");
				form.name.focus();
				return;
			}
			
			form.submit();
		}
	</script>

	<form onsubmit="JoinForm__submit(this); return false;" action="doJoin"
		method="post">
		<div>
			아이디 : <input autocomplete="off" type="text" placeholder="아이디 입력"
				name="loginId" />

		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비밀번호 입력"
				name="loginPw" />
		</div>
		<div>
			비밀번호 확인: <input autocomplete="off" type="text"
				placeholder="비밀번호 확인 입력" name="loginPwConfirm" />
		</div>
		<div>
			이름 : <input autocomplete="off" type="text" placeholder="이름 입력"
				name="name" />
		</div>
		<button type="submit">가입</button>
		<button type="reset">다시 입력</button>
	</form>

</body>


</html>
