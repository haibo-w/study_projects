package org;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceRef;

import org.test.JAXWSTest;


public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		Endpoint.create(new JAXWSTest()).publish("http://localhost:8181/wstest");
		System.out.println("init success");
	}

	@WebServiceRef(wsdlLocation = "http://192.168.1.234:8181/wstest?wsdl")
	private static JAXWSTest test;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
//		resp.getWriter().print(test.sayHello("zhang"));
	}

	
	
	
}
