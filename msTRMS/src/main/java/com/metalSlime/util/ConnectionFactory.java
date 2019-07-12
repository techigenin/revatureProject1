package com.metalSlime.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

public class ConnectionFactory {
	private String user = System.getenv("TRMS_USERNAME");
    private String password = System.getenv("TRMS_PASSWORD");
    private String url = "jdbc:postgresql://" + System.getenv("TRMS_URL") + ":5432/mJamesDB_061119?" ;
//    private static String PROPERTIES_FILE = "/WEB-INF/database.properties";
    private static ConnectionFactory cf;
      
    // Must run this at first of Servlet!!!
    public static synchronized Connection getConnection(HttpServlet servlet) {
    	try {
            Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }	
    	
    	if (cf == null)
                cf = new ConnectionFactory(servlet); 

        return cf.createConnection();
    }
    
    public static Connection getConnection() {
    	
    	return cf.createConnection();
    }

    private Connection createConnection() {
            Connection conn = null;

            try {
                    conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }

            return conn;
    }

    private ConnectionFactory(HttpServlet servlet) {
//            Properties prop = new Properties();
//            InputStream fis = servlet.getServletContext().getResourceAsStream(PROPERTIES_FILE);
            
//            try
//            {
//                    prop.load(fis);
//                    url = prop.getProperty("url");
//                    user = prop.getProperty("user");
//                    password = prop.getProperty("password");
//            } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//            } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//            }
    }
}
