package com.springessentialsbook.chapter4;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SampleTextWebSocketHandler extends TextWebSocketHandler {

	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);
        StringBuilder builder=new StringBuilder();
        builder.append("From Myserver-").append(jsonObject.get("clientName")).append("- Received Your Message:").append(jsonObject.get("clientMessage"));
        session.sendMessage(new  TextMessage(builder.toString()));

	}
}
