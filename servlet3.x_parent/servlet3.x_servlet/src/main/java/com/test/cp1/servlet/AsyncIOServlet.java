package com.test.cp1.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = "/asynio")
public class AsyncIOServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		final AsyncContext acontext = request.startAsync();
		final ServletInputStream input = request.getInputStream();

		//in  version 1.7
		input.setReadListener(new ReadListener() {
			byte buffer[] = new byte[4 * 1024];
			StringBuilder sbuilder = new StringBuilder();

			@Override
			public void onDataAvailable() {
				try {
					do {
						int length = input.read(buffer);
						sbuilder.append(new String(buffer, 0, length));
					} while (input.isReady());
					
					System.out.println(sbuilder);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void onAllDataRead() {
				try {
					acontext.getResponse().getWriter()
							.write("...the response...");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				acontext.complete();
			}

			@Override
			public void onError(Throwable t) {

			}
		});
	}
}
