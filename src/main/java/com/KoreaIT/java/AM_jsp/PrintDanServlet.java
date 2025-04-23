package com.KoreaIT.java.AM_jsp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/printDan")
public class PrintDanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		response.setContentType("text/html;charset=UTF-8");

		

		String inputedDan = request.getParameter("dan");
		String inputedLimit = request.getParameter("limit");
		String inputedColor = request.getParameter("color");
		
		if(inputedDan == null) {
			inputedDan="1";
		}
		
		if(inputedLimit == null) {
			inputedLimit="1";
		}
		
		
		System.out.println(inputedDan);
		System.out.println(inputedLimit);
		System.out.println(inputedColor);

		int dan = Integer.parseInt(inputedDan);
		int limit = Integer.parseInt(inputedLimit);
		String color = inputedColor;
		
		
		response.getWriter().append(String.format("<div style='color:%s;'>==%d단==</div>", color, dan));

		for (int i = 1; i <= limit; i++) {
			response.getWriter().append(String.format("<div style='color:%s;'> %d * %d = %d </div>", color, dan, i, dan * i));
			
		}
		

	}

}
