package com.test.cp1.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = "/asynioout")
public class AsyncIOOutputServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("out start");
		final AsyncContext acontext = request.startAsync();
		final ServletOutputStream stream = response.getOutputStream();
		response.setHeader("Content-Disposition", new String("attachment;filename=grails-2.4.4.zip"));
		stream.setWriteListener(new WriteListener() {
			
			byte buffer[] = new byte[4 * 1024];
			@Override
			public void onWritePossible() throws IOException {
				// TODO Auto-generated method stub
				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(new File("C:\\Users\\Haibo-W\\Downloads\\grails-2.4.4.zip"));
				//if(stream.isReady()){
					int length = 0;
					while((length = fis.read(buffer)) > 0){
						stream.write(buffer, 0, length);
					}
					//acontext.complete();
				//}
				
			}
			
			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}
		});
		System.out.println("out is ok");
	}
}
