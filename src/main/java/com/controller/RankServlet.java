package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dao.RankDao;
import com.model.User;

@WebServlet("/rank")
public class RankServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private RankDao rankDao = new RankDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session.getAttribute("username") == null) {
			res.sendRedirect("/");
		} else {
			JSONArray jsons = new JSONArray();
			try {
				ArrayList<User> users = rankDao.getData();
				for (User user : users) {
					JSONObject json = new JSONObject();
					json.put("id", user.getId());
					json.put("username", user.getUsername());
					json.put("email", user.getEmail());
					json.put("bio", user.getBio());
					json.put("money", user.getMoney());
					json.put("avatar", user.getAvatar());
					jsons.add(json);
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			req.setAttribute("jsons", jsons);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/rank.jsp");
			dispatcher.forward(req, res);
		}
	}
}
