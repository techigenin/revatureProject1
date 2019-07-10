package com.metalSlime.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalSlime.pojos.GradeResponse;
import com.metalSlime.services.GradeResponseServiceImpl;
import com.metalSlime.util.ConnectionFactory;

public class MSGradeResponseServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("home.html").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection(this);
		
		HttpSession sess = req.getSession();
		
		if (sess == null) {
			req.getRequestDispatcher("logout");
			return;
		}
		
		ObjectMapper om = new ObjectMapper();
		BufferedReader br = req.getReader();
		String jsonString = br.readLine();
		
		GradeResponse gr = om.readValue(jsonString, GradeResponse.class);
		GradeResponseService grs = new GradeResponseServiceImpl();
		
		
		grs.sendResponse(gr);
		
		resp.getWriter().write("");
		resp.setStatus(200);
	}

}
