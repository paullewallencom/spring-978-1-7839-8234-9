package com.springessentialsbook.chapter4;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatroomController {

    @MessageMapping("/broadcastMyMessage")
    @SendTo("/chatroomTopic/broadcastClientsMessages")
    public ReturnedDataModelBean broadCastClientMessage(ClientInfoBean message) throws Exception {

        String returnedMessage=message.getClientName() + ":"+message.getClientMessage();
        return new ReturnedDataModelBean(returnedMessage );
    }
}
