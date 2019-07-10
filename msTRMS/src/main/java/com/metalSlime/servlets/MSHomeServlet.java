package com.metalSlime.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.metalSlime.util.ConnectionFactory;

public class MSHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection(this);
		
		HttpSession sess = req.getSession(false);
		
		// If we won't have a user session, send back to homepage.
		if (sess == null) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
		
		req.getRequestDispatcher("home.html")
			.forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
