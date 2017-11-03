package com.test.websocket;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpointConfig;
@WebServlet(urlPatterns="/init",loadOnStartup=2)
public class Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//PriceVolumeBean bean = new PriceVolumeBean();
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//bean.registerServlet();
		//ServerEndpointConfig.Builder.create(ExtendWebSocketTest.class, "/echo").build();
		System.out.println("--------init servlet-------------");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		System.out.println("okkkkkkkkkkkkkk");
	}
	
	

	
}
