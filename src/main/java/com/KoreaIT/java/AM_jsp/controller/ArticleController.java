package com.KoreaIT.java.AM_jsp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dto.Article;
import com.KoreaIT.java.AM_jsp.service.ArticleService;
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

	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn,
			HttpSession session) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		this.session = session;

		this.articleService = new ArticleService(conn);
	}

	private boolean isLogined() {
		return request.getSession().getAttribute("loginedMemberId") != null;

	}

	private int getLoginedMemberId() {
		return (int) request.getSession().getAttribute("loginedMemberId");
	}

	public void showList() throws ServletException, IOException {

		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int itemsInAPage = 10;
		int limitFrom = (page - 1) * itemsInAPage;

		int totalCnt = articleService.getTotalCnt(); // 총 가지고 있는 게시글 수
		int totalPage = (int) Math.ceil(totalCnt / (double) itemsInAPage); // 나누기

//		String sql = "SELECT * FROM article ORDER BY id desc;";

		List<Article> articles = articleService.getForPrintArticles(limitFrom, itemsInAPage); // DB에서 가져온 걸 압축풀기한 느낌

		request.setAttribute("page", page);
		request.setAttribute("articles", articles); // 속성에 대해서 하나를 설명
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("totalPage", totalPage);

//		response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response); // 연동

	}

	public void showDetail() throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		Article article = articleService.getArticleById(id); // DB에서 가져온 걸 압축풀기한 느낌

		request.setAttribute("article", article); // 속성에 대해서 하나를 설명

//		response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response); // 연동

	}

	public void showWrite() throws ServletException, IOException {

		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인 하고와'); location.replace('../member/login');</script>");
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);

	}

	public void showModify() throws ServletException, IOException {

		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인 하고와'); location.replace('../member/login');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		Article article = articleService.getArticleById(id); // DB에서 가져온 걸 압축풀기한 느낌

		request.setAttribute("article", article); // 속성에 대해서 하나를 설명

//		response.getWriter().append(article.toString()); // 출력하는 놈(데이터);

		if (article == null) {
			// todo
		}

		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response); // 연동

	}

	public void doDelete() throws ServletException, IOException {

		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인 하고와'); location.replace('../member/login');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getArticleById(id);

		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		if (loginedMemberId != (int) article.getMemberId()) {
			response.getWriter().append(
					String.format("<script>alert('%d번 글에 대한 권한이 없습니다.'); location.replace('list');</script>", id));
			return;
		}

		articleService.doDelete(id);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 삭제됨'); location.replace('list');</script>", id));

	}

	public void doModify() throws ServletException, IOException {

		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인 하고와'); location.replace('../member/login');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

		String title = request.getParameter("title");
		String body = request.getParameter("body");

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		articleService.doUpdate(title, body, id);

		response.getWriter().append(
				String.format("<script>alert('%d번 글이 수정됨'); location.replace('detail?id=%d');</script>", id, id));

	}

	public void doWrite() throws ServletException, IOException {

		if (!isLogined()) {
			response.getWriter().append("<script>alert('로그인 하고와'); location.replace('../member/login');</script>");
			return;
		}

		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int loginedMemberId = getLoginedMemberId();

//		String sql = "SELECT * FROM article ORDER BY id desc;";

//		String sql = String.format("SELECT * FROM article WHERE id = %d;", id);

		int memberId = loginedMemberId;

		int id = articleService.doWrite(memberId, title, body);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 작성됨'); location.replace('list');</script>", id));

	}

}
