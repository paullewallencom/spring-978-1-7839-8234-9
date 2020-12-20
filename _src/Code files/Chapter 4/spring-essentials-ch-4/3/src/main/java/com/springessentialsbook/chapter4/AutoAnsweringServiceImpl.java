package com.springessentialsbook.chapter4;

import org.springframework.stereotype.Service;

@Service
public class AutoAnsweringServiceImpl implements  AutoAnsweringService {
    @Override
    public String answer(ClientInfoBean bean) {
        StringBuilder mockBuffer=new StringBuilder();
        mockBuffer.append(bean.getClientName())
                .append(", we have received the message:")
                .append(bean.getClientMessage());
        return  mockBuffer.toString();
    }
}
