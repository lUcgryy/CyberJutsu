package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.model.User;

@WebServlet("/register")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userdao = new UserDao();
    String noti = "";
    public UserServlet() {
        super();
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	if (!checkSession(session)) {
    		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/register.jsp");
            dispatcher.forward(req, res);
    	} else {
    		res.sendRedirect("/game");
    	}
        
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        User user = new User(username, password);
        try {
        	if (userdao.checkUser(user)) {
        		if (password.equals(confirmPassword)) {
                    userdao.registerUser(user);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
                    dispatcher.forward(req, res);
        		} else {
        			noti = "Mật khẩu xác nhận không trùng khớp";
            		req.setAttribute("noti", noti);
            		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/register.jsp");
                    dispatcher.forward(req, res);
        		}
        	} else {
        		noti = "Trùng tên đăng nhập rồi";
        		req.setAttribute("noti", noti);
        		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/register.jsp");
                dispatcher.forward(req, res);
        	}
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean checkSession(HttpSession session) {
    	return session.getAttribute("username") != null;
    }
}