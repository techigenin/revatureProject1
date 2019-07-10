package com.metalSlime.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalSlime.pojos.RequestFiles;
import com.metalSlime.services.RequestService;
import com.metalSlime.services.RequestServiceImpl;
import com.metalSlime.util.ConnectionFactory;

public class MSRequestFilesServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("home.html").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection();
		
		HttpSession sess = req.getSession(false);
		
		if (sess == null || ((Integer)sess.getAttribute("eid")).equals(new Integer(0))) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
		
		RequestService rs = new RequestServiceImpl();
		
		ObjectMapper om = new ObjectMapper();
		BufferedReader br = req.getReader();
		String jsonString = br.readLine();
		RequestFiles rf = om.readValue(jsonString, RequestFiles.class);
		
		rs.sendRequestFiles(rf);
	}
}
