package com.KoreaIT.java.AM_jsp.articleDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/list")
public class articleListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// DB 연결
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 x");
			e.printStackTrace();

		}

		String url = "jdbc:mysql://127.0.0.1:3306/AM_JSP_25_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

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

//			String sql = "SELECT * FROM article ORDER BY id desc;";

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

//			response.getWriter().append(articleRows.toString()); // 출력하는 놈(데이터);

			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response); // 연동

		} catch (SQLException e) {
			System.out.println("에러 1 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}