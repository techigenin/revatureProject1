package com.metalSlime.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalSlime.pojos.Grade;
import com.metalSlime.services.GradeService;
import com.metalSlime.services.GradeServiceImpl;
import com.metalSlime.util.ConnectionFactory;

public class MSGradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("home.html").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionFactory.getConnection();
		
		HttpSession sess = req.getSession();
		
		if (sess == null) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
			
		ObjectMapper om = new ObjectMapper();
		GradeService gs = new GradeServiceImpl();
		
		BufferedReader br = req.getReader();
		int reqNum = Integer.parseInt(br.readLine());
		
		Grade g = gs.getGrade(reqNum);
		
		resp.getWriter().write(om.writeValueAsString(g));
		resp.setStatus(200);
	}
}
