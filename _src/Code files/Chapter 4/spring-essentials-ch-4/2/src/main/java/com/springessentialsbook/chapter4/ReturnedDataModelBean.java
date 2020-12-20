package com.springessentialsbook.chapter4;

public class ReturnedDataModelBean {
    
    private String returnedMessage;

    public ReturnedDataModelBean(String returnedMessage) {        this.returnedMessage = returnedMessage; }

    public String getReturnedMessage() {
        return returnedMessage;
    }

}
