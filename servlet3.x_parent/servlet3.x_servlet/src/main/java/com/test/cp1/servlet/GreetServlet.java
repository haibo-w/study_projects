package com.test.cp1.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="hello-greet",loadOnStartup=1,urlPatterns={"/gr","/gr/*"},
		initParams = {
		@WebInitParam(value="msg", name = "initParam")
})
public class GreetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String init = null;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		init = getInitParameter("initParam");
	}





	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getSession().setAttribute("msg",init);
		res.getWriter().print("initParam is  " + init);
	}
	

}
