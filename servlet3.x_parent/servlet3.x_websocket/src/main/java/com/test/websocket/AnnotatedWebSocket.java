package com.test.websocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class AnnotatedWebSocket {

	private static final Logger logger = Logger.getLogger("ETFEndpoint");

	static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

	public static void sendText(double price, int volume) {
		String msg = String.format("%.2f / %d", price, volume);

		for (Session session : queue) {
			try {
				session.getBasicRemote().sendText(msg);
				logger.log(Level.INFO, "Sent: {0}", msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		queue.add(session);
		 logger.log(Level.INFO, "Connection opened.");
	}

	@OnClose
	public void closedConnection(Session session) {
		/* Remove this connection from the queue */
		queue.remove(session);
		logger.log(Level.INFO, "Connection closed.");
	}

	@OnError
	public void error(Session session, Throwable t) {
		/* Remove this connection from the queue */
		queue.remove(session);
		logger.log(Level.INFO, t.toString());
		logger.log(Level.INFO, "Connection error.");
	}
}
