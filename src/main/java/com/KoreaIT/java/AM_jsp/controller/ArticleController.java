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

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.conn = conn;
		this.request = request;
		this.response = response;
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

}
