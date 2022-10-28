package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect("/");
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		System.out.println("User="+session.getAttribute("username"));
    	if(session != null){
    		session.invalidate();
    	}
		
		out.print("Log out successfully");
		out.close();
	}	
}
