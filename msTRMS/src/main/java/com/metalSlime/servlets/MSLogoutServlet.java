package com.metalSlime.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.metalSlime.util.Logging;

public class MSLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession sess = req.getSession(false);
		
		if (sess != null)
		{
			Integer eid = (Integer) sess.getAttribute("eid");
			Logging.infoLog("User " + eid + " has logged out.");
			
			sess.invalidate();
			sess = null;
		}
		
		resp.sendRedirect("login");
	}

}
