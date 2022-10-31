package com.controller;

import java.io.IOException;
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
import com.model.User;
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ProfileDao profileDao = new ProfileDao();
	public ProfileServlet() {
		super();
	}
	String adminFlag = "";
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
		HttpSession session = req.getSession(false);
		if (!checkSession(session)) {
			res.sendRedirect("/login");
		} else {
			String sessionUsername = (String) session.getAttribute("username");
			try {
				if (profileDao.getAdminUsername().equals(sessionUsername)) {
					adminFlag = "Java{adm1N_I5_Aw35ome}";
				}
				User user = profileDao.getData(id);
				username = user.getUsername();
				email = user.getEmail();
				bio = user.getBio();
				money = user.getMoney();
				creditCard = user.getCreditCard();
				avatar = user.getAvatar();
				kyc = user.isKyc();
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
			req.setAttribute("adminFlag", adminFlag);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/profile.jsp");
			dispatcher.forward(req, res);
		}
    }
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (!checkSession(session)) {
			res.sendRedirect("/login");
		} else {
			
			try {
				String id = req.getParameter("id");
				id = Integer.toString(Integer.parseInt(id));
				if (!checkAdmin(id)) {
					String email = req.getParameter("email");
					String creditCard = req.getParameter("creditCard");
					String bio = req.getParameter("bio");
					String avatar = req.getParameter("avatar");
					String kyc = req.getParameter("kyc");
					User user = profileDao.getData(id);
					if (email != null) {
						user.setEmail(email);
					}
	
					if (creditCard != null) {
						user.setCreditCard(creditCard);
					}
					if (bio != null) {
						user.setBio(bio);
					}
					if (avatar != null) {
						user.setAvatar(avatar);
					}
					if (kyc == null && !user.isKyc()) {
						user.setKyc(false);
					} else {
						user.setKyc(true);
					}
					res.sendRedirect("/profile?id=" + user.getId());
	//			System.out.println(id + email + creditCard + bio);
					profileDao.updateInfo(user);
				} else {
					String hacked = "Admin không cập nhật";
			    	req.setAttribute("hacked", hacked);
			    	RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/hack.jsp");
		    		dispatcher.forward(req, res);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				String hacked = "Có gì đó sai sai";
		    	req.setAttribute("hacked", hacked);
		    	RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/hack.jsp");
	    		dispatcher.forward(req, res);
			}
		}
	}
	private boolean checkSession(HttpSession session) {
    	return session.getAttribute("username") != null;
    }
	private boolean checkAdmin(String id) {
		try {
			String adminId = profileDao.getAdminId();
			return id.equals(adminId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	private boolean checkAdminUsername(String username) {
		try {
			String adminId = profileDao.getAdminUsername();
			return username.equals(adminId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
