package com.test.websocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/receive/{time}")
public class AnWebSocket {

	private static final Logger logger = Logger.getLogger("ETFEndpoint");


	@OnMessage
	public void onMessage(Session session, String msg) {
		System.out.println(" receive msg : " + msg);
	}

	@OnOpen
	public void onOpen(Session session,@PathParam(value = "time") String time) {
		 logger.log(Level.INFO, "Connection opened." + time);
	}

	@OnClose
	public void closedConnection(Session session) {
		/* Remove this connection from the queue */
		logger.log(Level.INFO, "Connection closed.");
	}

	@OnError
	public void error(Session session, Throwable t) {
		/* Remove this connection from the queue */
		logger.log(Level.INFO, t.toString());
		logger.log(Level.INFO, "Connection error.");
	}
}
