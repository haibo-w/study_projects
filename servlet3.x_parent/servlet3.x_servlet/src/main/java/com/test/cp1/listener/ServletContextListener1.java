package com.test.cp1.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
@WebListener
public class ServletContextListener1 implements ServletContextListener,HttpSessionAttributeListener {

	
	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(" attr is add to session" + arg0.getName());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent env) {
		System.out.println(" servlet Context destory");
	}

	@Override
	public void contextInitialized(ServletContextEvent env) {
		System.out.println(" servlet Context init");
	}

}
