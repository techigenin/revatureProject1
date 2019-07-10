package com.metalSlime.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.metalSlime.services.UserService;
import com.metalSlime.services.UserServiceImpl;
import com.metalSlime.util.ConnectionFactory;
import com.metalSlime.util.Logging;
import com.metalSlime.pojos.User;

public class MSLoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection(this);
		HttpSession sess = req.getSession(false);
		
		if (sess != null && sess.getAttribute("eid") != new Integer(0)) 
			getServletContext()
				.getRequestDispatcher("/home")
					.forward(req, resp);
		else
			getServletContext()
			.getRequestDispatcher("/login.html")
				.forward(req, resp);;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 try {
             Class.forName("org.postgresql.Driver");
     } catch (ClassNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
     }
		
		ConnectionFactory.getConnection(this);
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = new User(username, password);
		
		UserService us = new UserServiceImpl();
		Integer eid = us.loginUser(user);
		
		Logging.infoLog("User " + eid + "has logged in");
		
		if (eid == 0) {	
			resp.setStatus(401);
			resp.getWriter().write("Falied Login");
		} else {
			HttpSession sess = req.getSession(true);
			sess.setAttribute("eid", eid);
			sess.setAttribute("username", username);
			req.getRequestDispatcher("/home").forward(req, resp);
		}
	
	}

}
