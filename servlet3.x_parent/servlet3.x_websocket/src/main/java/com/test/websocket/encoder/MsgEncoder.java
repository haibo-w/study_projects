package com.test.websocket.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

public class MsgEncoder implements Text<Msg> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Msg object) throws EncodeException {
		return object.getMsg();
	}

	

}
