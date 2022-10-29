package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	req.setAttribute("noti", noti);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/register.jsp");
        dispatcher.forward(req, res);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(username, password);
        try {
        	if (userdao.checkUser(user)) {
            userdao.registerUser(user);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(req, res);
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
        

}