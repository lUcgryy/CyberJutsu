package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	 
    public IndexServlet() {
    	super();
    }
 
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	if (session == null) {
    		session = req.getSession();
    	}
    	if (!checkSession(session) || session == null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/index.jsp");
            dispatcher.forward(req, res);
    	} else {
    		res.sendRedirect("/game");
    	}

    }
    private boolean checkSession(HttpSession session) {
    	return session.getAttribute("username") != null;
    }
}
