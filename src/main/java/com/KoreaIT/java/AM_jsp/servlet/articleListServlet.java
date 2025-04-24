package com.KoreaIT.java.AM_jsp.servlet;

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

		

		String url = "jdbc:mysql://127.0.0.1:3306/AM_JSP_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			response.getWriter().append("연결 성공!");

			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");

//			String sql = "SELECT * FROM article ORDER BY id desc;";

			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql); // DB에서 가져온 걸 압축풀기한 느낌
						

			request.setAttribute("articleRows", articleRows); // 속성에 대해서 하나를 설명

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