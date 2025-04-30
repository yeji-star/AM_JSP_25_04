package com.KoreaIT.java.AM_jsp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dto.Member;
import com.KoreaIT.java.AM_jsp.service.MemberService;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private HttpSession session;

	private MemberService memberService;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn,
			HttpSession session) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		this.session = session;

		this.memberService = new MemberService();
	}

	public void showJoin() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);

	}

	public void showLogin() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);

	}

	public void doJoin() throws ServletException, IOException {

		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String loginPwConfirm = request.getParameter("loginPwConfirm");
		String name = request.getParameter("name");

		// 아이디 중복 체크

		boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

//		boolean isJoinableId = DBUtil.selectRowIntValue(conn, sql) == 0;

		if (isLoginIdDup == false) {
			// 이게 거짓이면 누가 사용중이라는 것이라 리턴으로 돌려보냄
			response.getWriter().append(String.format(
					"<script>alert('이미 사용중인 아이디 입니다.'); location.replace('../member/join');</script>", loginId));
			return;
		}

		int id = memberService.doJoin(loginId, loginPw, name);

		response.getWriter().append(
				String.format("<script>alert('%d번째로 가입하였습니다!'); location.replace('../article/list');</script>", id));

	}

	public void doLogin() throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		// 아이디가 있나 체크

		Member member = memberService.getMemberByLoginId(loginId);

//		if (member.isEmpty()) {
//			// 이게 거짓이면 누가 사용중이라는 것이라 리턴으로 돌려보냄
//			response.getWriter().append(String
//					.format("<script>alert('%s는 없는 아이디입니다.'); location.replace('../member/login');</script>", loginId));
//			return;
//		}

		if (member.getLoginPw().equals(loginPw) == false) {
			response.getWriter()
					.append(String.format(
							"<script>alert('비밀번호가 일치하지 않습니다.'); location.replace('../member/login');</script>",
							member.getName()));
			return;
		}

		session.setAttribute("loginedMember", member); // 속성을 키밸류로 저장
//		session.setAttribute("loginedMemberId", member.get("id")); // 왼쪽이 키, 오른쪽이 밸류
//		session.setAttribute("loginedMemberLoginId", member.get("loginId"));

		response.getWriter()
				.append(String.format("<script>alert('로그인 성공!'); location.replace('../article/list');</script>"));

	}

	public void doLogout() throws ServletException, IOException {

		session.removeAttribute("loginedMember"); // 속성을 키밸류로 저장
		session.removeAttribute("loginedMemberId"); // 왼쪽이 키, 오른쪽이 밸류
		session.removeAttribute("loginedMemberLoginId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃 되었습니다.'); location.replace('../home/main');</script>"));

	}

}
