package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ProfileDao;
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ProfileDao profileDao = new ProfileDao();
	public ProfileServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		String username = "";
		String email = "";
		String bio = "";
		String money = "";
		String creditCard = "";
		String avatar = "";
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		if (session.getAttribute("username") == null) {
			res.sendRedirect("/");
			out.print("Chưa login");
		} else {
			try {
				ArrayList<Object> list = profileDao.getData(req.getParameter("id"));
				username = (String) list.get(0);
				email = (String) list.get(1);
				bio = (String) list.get(2);
				money = Integer.toString((int) list.get(3));
				creditCard = (String) list.get(4);
				avatar = (String) list.get(5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			req.setAttribute("username", username);
			req.setAttribute("email", email);
			req.setAttribute("bio", bio);
			req.setAttribute("money", money);
			req.setAttribute("creditCard", creditCard);
			req.setAttribute("avatar", avatar);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/profile.jsp");
			dispatcher.forward(req, res);
		}
    }
}
