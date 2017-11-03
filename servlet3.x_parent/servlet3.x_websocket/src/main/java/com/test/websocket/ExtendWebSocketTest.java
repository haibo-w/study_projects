/**
 * 
 */
package com.test.websocket;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 * 继承方式
 * @author Haibo-W
 *
 */
public class ExtendWebSocketTest extends Endpoint {

	/* (non-Javadoc)
	 * @see javax.websocket.Endpoint#onOpen(javax.websocket.Session, javax.websocket.EndpointConfig)
	 */
	@Override
	public void onOpen(final Session session, EndpointConfig config) {
		// TODO Auto-generated method stub
		session.addMessageHandler(new MessageHandler.Whole<String>() {

			@Override
			public void onMessage(String message) {
				// TODO Auto-generated method stub
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

}
