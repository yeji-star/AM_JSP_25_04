package com.KoreaIT.java.AM_jsp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ArticleController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private HttpSession session;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn,
			HttpSession session) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		this.session = session;
	}

	public void showList() throws ServletException, IOException {

		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int itemsInAPage = 10;
		int LimitFrom = (page - 1) * itemsInAPage;

		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article;");

		int totalCnt = DBUtil.selectRowIntValue(conn, sql); // 총 가지고 있는 게시글 수
		int totalPage = (int) Math.ceil(totalCnt / (double) itemsInAPage); // 나누기

		sql = SecSql.from("SELECT article.*, `member`.name");
		sql.append("FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.memberId = `member`.id");
		sql.append("ORDER BY article.id DESC");
		sql.append("LIMIT ?, ?;", LimitFrom, itemsInAPage);

//		String sql = "SELECT * FROM article ORDER BY id desc;";

		List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql); // DB에서 가져온 걸 압축풀기한 느낌

		HttpSession session = request.getSession();

		boolean isLogined = false;
		int loginedMemberId = -1;
		Map<String, Object> loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = (Map<String, Object>) session.getAttribute("loginedMember");
		}

		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberId", loginedMemberId);

		request.setAttribute("page", page);
		request.setAttribute("articleRows", articleRows); // 속성에 대해서 하나를 설명
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("totalPage", totalPage);

		request.setAttribute("loginedMember", loginedMember);

//		response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response); // 연동

	}

	public void showDetail() throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		SecSql sql = SecSql.from("SELECT article.*, `member`.name");
		sql.append("FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.memberId = `member`.id");
		sql.append("WHERE article.id = ?;", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql); // DB에서 가져온 걸 압축풀기한 느낌

		request.setAttribute("articleRow", articleRow); // 속성에 대해서 하나를 설명

//		response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response); // 연동

	}

	public void doWrite() throws ServletException, IOException {
		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인이 되어 있지 않습니다.'); location.replace('../member/login');</script>"));
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);

	}

	public void doModify() throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql); // DB에서 가져온 걸 압축풀기한 느낌

		request.setAttribute("articleRow", articleRow); // 속성에 대해서 하나를 설명

//		response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

		if (articleRow == null) {
			// todo
		}

		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response); // 연동

	}

}
