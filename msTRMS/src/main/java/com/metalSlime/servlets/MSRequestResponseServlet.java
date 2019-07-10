package com.metalSlime.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalSlime.pojos.RequestResponse;
import com.metalSlime.services.RequestService;
import com.metalSlime.services.RequestServiceImpl;
import com.metalSlime.util.ConnectionFactory;
import com.metalSlime.util.Logging;

public class MSRequestResponseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("home.html").forward(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectionFactory.getConnection(this);
		
		HttpSession sess = req.getSession(false);
		
		// If we won't have a user session or if the eid is zero, send back to homepage.
		if (sess == null || (Integer) sess.getAttribute("ied") == new Integer(0)) {
			req.getRequestDispatcher("logout").forward(req, resp);
			return;
		}
		
		Integer eid = (Integer)sess.getAttribute("eid");
		Logging.infoLog("Employee " + eid + "submitted responses to employee requests");
		
		ObjectMapper om = new ObjectMapper();
		
		BufferedReader br = req.getReader();
		String jsonString = br.readLine();
		
		RequestService rs = new RequestServiceImpl();
		RequestResponse[] responses = om.readValue(jsonString, RequestResponse[].class);
		
		rs.submitReqResp(eid, responses);
		
	}
}
