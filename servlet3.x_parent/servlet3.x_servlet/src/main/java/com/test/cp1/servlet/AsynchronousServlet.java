package com.test.cp1.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/async",asyncSupported=true)
public class AsynchronousServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final List<String> arr = new ArrayList<String>(); 
		final AsyncContext ctx = req.startAsync();
		System.out.println("start --- ");
		ctx.start(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int idx = 0;
				while(idx < 10){
					arr.add("str - " + idx);
					idx ++;
					Thread.currentThread();
					try {
						Thread.sleep(500);
						System.out.println("sleep 500ms " + idx);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					ctx.getResponse().getWriter().print(" arr " + arr.size());
					ctx.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		System.out.println("end --- ");
	}

	
}
