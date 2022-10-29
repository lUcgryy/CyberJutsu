package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.JSONObject;

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
		String id = req.getParameter("id");
		String username = "";
		String email = "";
		String bio = "";
		int money = 0;
		String creditCard = "";
		String avatar = "";
		boolean kyc = false;
		JSONObject json = new JSONObject();
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		if (session.getAttribute("username") == null) {
			res.sendRedirect("/");
			out.print("Chưa login");
		} else {
			try {
				ArrayList<Object> list = profileDao.getData(id);
				username = (String) list.get(0);
				email = (String) list.get(1);
				bio = (String) list.get(2);
				money = (int) list.get(3);
				creditCard = (String) list.get(4);
				avatar = (String) list.get(5);
				json.put("id", id);
				json.put("username", username);
				json.put("email", email);
				json.put("bio", bio);
				json.put("money", money);
				json.put("creditCard", creditCard);
				json.put("avatar", avatar);
				json.put("kyc", kyc);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			req.setAttribute("json", json);
			req.setAttribute("id", id);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/profile.jsp");
			dispatcher.forward(req, res);
		}
    }
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String creditCard = req.getParameter("creditCard");
		String bio = req.getParameter("bio");
		String avatar = req.getParameter("avatar");
		String kyc = req.getParameter("kyc");
		System.out.println(id + email + creditCard + bio);
		try {
			profileDao.updateInfo(id, email, creditCard, bio, avatar, kyc);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.sendRedirect("/profile?id=" + id);
	}
}
