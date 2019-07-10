package com.metalSlime.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.metalSlime.util.ConnectionFactory;

public class MSEmployeeUserIDServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection(this);
		HttpSession sess = req.getSession(false);
		
		if (sess == null || sess.getAttribute("username") == null) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
		
		resp.getWriter().write((String)sess.getAttribute("username"));
		resp.setStatus(200);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
