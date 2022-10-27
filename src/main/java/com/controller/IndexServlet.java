package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	 
    public IndexServlet() {
    	super();
    }
 
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
 
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, res);
    }
 
    public void destroy() {
        // do nothing.
    }
}
