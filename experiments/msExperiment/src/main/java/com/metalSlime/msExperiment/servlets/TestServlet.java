package com.metalSlime.msExperiment.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import com.metalSlime.msExperiment.pojo.EmployeeRequest;
import com.metalSlime.msExperiment.services.EmployeeRequestService;
import com.metalSlime.msExperiment.services.EmployeeRequestServiceImpl;
import com.metalSlime.msExperiment.util.ConnectionFactory;

public class TestServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
				throws IOException, ServletException
	{	
		try {
	            Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
		
		ConnectionFactory.getConnection(this);
		
		// Get the JSON string
		BufferedReader br = req.getReader();
		String jsonString = br.readLine();
		JSONObject obj = new JSONObject(jsonString);
		
		// Now build an EmployeeRequest and add it to DB
		EmployeeRequestService ers = new EmployeeRequestServiceImpl();
		EmployeeRequest er = ers.buildEmployeeRequest(obj);
		ers.addEmployeeRequest(er);
		
		// Now we get the string back out.
		EmployeeRequest returnInfo = ers.getEmployeeRequest(er.getName());
		
		obj = new JSONObject(returnInfo);
		String retString = obj.toString();
		
		// And send it back to the page.
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(retString);
	}
}
