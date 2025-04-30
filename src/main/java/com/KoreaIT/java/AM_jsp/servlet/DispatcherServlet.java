package com.KoreaIT.java.AM_jsp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.controller.ArticleController;
import com.KoreaIT.java.AM_jsp.controller.HomeController;
import com.KoreaIT.java.AM_jsp.controller.MemberController;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {

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

			HttpSession session = request.getSession();

			// 로그인 확인 //
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

			String requestUri = request.getRequestURI();

			System.out.println(requestUri);

			// 요청 쪼개기 //
			String[] reqUriBits = requestUri.split("/");

			if (reqUriBits.length < 5) {
				response.getWriter().append(
						String.format("<script>alert('올바른 요청이 아닙니다.'); location.replace('../home/main');</script>"));
				return;
			}

			String controllerName = reqUriBits[3];
			String actionMethodName = reqUriBits[4];

			// 어떤 컨트롤러에게 일시킬까 //

			if (controllerName.equals("home")) {
				HomeController homeController = new HomeController(request, response, conn, session);

				homeController.main();

			} else if (controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(request, response, conn, session);

				switch (actionMethodName) {
				case "list":
					articleController.showList();
					break;
				case "detail":
					articleController.showDetail();
					break;
				case "modify":
					articleController.showModify();
					break;
				case "write":
					articleController.showWrite();
					break;
				case "doDelete":
					articleController.doDelete();
					break;
				case "doModify":
					articleController.doModify();
					break;
				case "doWrite":
					articleController.doWrite();
					break;
				}

			} else if (controllerName.equals("member")) {
				MemberController memberController = new MemberController(request, response, conn, session);

				switch (actionMethodName) {
				case "join":
					memberController.showJoin();
					break;
				case "doJoin":
					memberController.doJoin();
					break;
				case "login":
					memberController.showLogin();
					break;
				case "doLogin":
					memberController.doLogin();
					break;
				case "doLogout":
					memberController.doLogout();
					break;
				}

			}
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
