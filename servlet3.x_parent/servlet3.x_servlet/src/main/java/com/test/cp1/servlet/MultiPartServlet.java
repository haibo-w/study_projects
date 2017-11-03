package com.test.cp1.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(location = "e://tmp", fileSizeThreshold = 1024 * 1024, 
	maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "upload", loadOnStartup = 1, value = "/upload")
public class MultiPartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse arg1)
			throws ServletException, IOException {
//		Collection<Part> parts = req.getParts();
//		Iterator<Part> iterator = parts.iterator();
//		while(iterator.hasNext()){
//			Part part = iterator.next();
//			getFileName(part);
//			
//			
//		}
		Part part = req.getPart("up");
		System.out.println("fileName:" + getFileName(part));
	}
	
	private String getFileName(final Part part) {
		Collection<String> headerNames = part.getHeaderNames();
		
		for(String st : headerNames){
			String header = part.getHeader(st);
			System.out.println("Part header ["+st+"]:" + header);
		}
		
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("Part Header = {0}"+ partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
