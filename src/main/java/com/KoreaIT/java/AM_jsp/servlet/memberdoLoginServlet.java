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

@WebServlet("/member/dologin")
public class memberdoLoginServlet extends HttpServlet {

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
		
			
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");			
			

			// 아이디가 있나 체크
			
			SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?;", loginId);
			
			boolean isNoneId = DBUtil.selectRowBooleanValue(conn, sql);
			
			if(isNoneId == false) {
				// 이게 거짓이면 누가 사용중이라는 것이라 리턴으로 돌려보냄
				response.getWriter()
				.append(String.format("<script>alert('%s는 없는 아이디입니다.'); location.replace('../member/login');</script>", loginId));
				return;
			}
			
			sql = SecSql.from("SELECT COUNT(*) AS cnt FROM `member`");			
			sql.append("WHERE loginId = ?;", loginId);
			
			response.getWriter()
					.append(String.format("<script>alert('로그인 성공!'); location.replace('../article/list');</script>"));

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}