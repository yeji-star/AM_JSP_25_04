package com.KoreaIT.java.AM_jsp.controller;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private HttpSession session;
	
	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn,
			HttpSession session) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		this.session = session;
	}
	
	public void doJoin() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
		
	}

	public void login() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
		
	}

	
}
