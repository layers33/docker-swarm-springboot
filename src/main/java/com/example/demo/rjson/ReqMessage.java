package com.example.demo.rjson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqMessage {

    @JsonProperty(value = "messageId")
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
