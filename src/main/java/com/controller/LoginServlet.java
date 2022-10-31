package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.Timer;

import com.dao.LoginDao;
import com.model.Login;
import com.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private LoginDao loginDao;
    String warning = "";
    public int loginAttempt = 20;
    String hacked = "";

    public void init() {
        loginDao = new LoginDao();
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	if (checkAttempt(loginAttempt)) {
	    	HttpSession session = req.getSession(false);
	    	if (checkSession(session)) {
	    		res.sendRedirect("/game");
	    	} else {
	    		req.setAttribute("warning", warning);
	    		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
	    		dispatcher.forward(req, res);
	    	}
	    } else {
	    	hacked = "Bạn không còn quyền truy cập nữa. Hãy đợi 5 phút để đăng nhập lại.";
	    	req.setAttribute("hacked", hacked);
	    	RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/hack.jsp");
    		dispatcher.forward(req, res);
    		Timer timer = new Timer(5*10*1000, new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					loginAttempt = 3;
					warning = "";
				}
			});
    		timer.setRepeats(false);
    		timer.start();
	    }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    	if (checkAttempt(loginAttempt)) {
	    	HttpSession session = req.getSession(false);
	    	if (checkSession(session)) {
	    		res.sendRedirect("/game");
			} else {
		        String username = req.getParameter("username");
		        String password = req.getParameter("password");
		        User user = new User();
		        user.setUsername(username);
		        user.setPassword(password);
		
		        try {
		            if (loginDao.validate(user)) {
		                session = req.getSession();
		                session.setAttribute("username",username);
		                loginAttempt = 20;
		                warning = "";
		            } else {
		            	loginAttempt -= 1;
		            	if (loginAttempt <= 5) {
		            	warning = "Bạn còn " + loginAttempt + " lần đăng nhập trước khi bị khóa quyền truy cập vào game của chúng tôi";
		            	}
		            }
		                res.sendRedirect("/game");
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		    }
    	} else {
    		hacked = "Bạn không còn quyền truy cập nữa. Hãy liên hệ Admin.";
	    	req.setAttribute("hacked", hacked);
	    	RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/hack.jsp");
    		dispatcher.forward(req, res);
    	}
    }
    private boolean checkSession(HttpSession session) {
    	return session.getAttribute("username") != null;
    }
    private boolean checkAttempt(int attempt) {
		return attempt != 0;
	}
}
