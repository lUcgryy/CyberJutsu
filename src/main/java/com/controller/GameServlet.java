package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/game")
public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	 
    public GameServlet() {
    	super();
    }
 
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session.getAttribute("username") == null) {
			res.sendRedirect("/");
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/game.jsp");
			dispatcher.forward(req, res);
		}
    }
}
