package com.konstantinbulygin.prime_integer.model;

public class ClientRequest {

    private String clientMessage;

    public ClientRequest(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public ClientRequest() {
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }
}
