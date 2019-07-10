package com.metalSlime.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalSlime.services.EmployeeService;
import com.metalSlime.services.EmployeeServiceImpl;
import com.metalSlime.services.RequestService;
import com.metalSlime.services.RequestServiceImpl;
import com.metalSlime.util.ConnectionFactory;

public class MSEmployeeNameServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection(this);
		HttpSession sess = req.getSession(false);
		
		if (sess == null) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
		
		Integer eid = (Integer)sess.getAttribute("eid");
		
		EmployeeService es = new EmployeeServiceImpl();
		String employeeName = es.getEmployeeName(eid);

		resp.getWriter().write(employeeName);
		resp.setStatus(200);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
