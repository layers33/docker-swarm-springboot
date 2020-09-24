package com.example.demo.rjson;

public class RespEntity {
    private String Message;

    private Object Data;

    public RespEntity(String message) {
        Message = message;
    }

    public RespEntity(String message, Object data) {
        Message = message;
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
