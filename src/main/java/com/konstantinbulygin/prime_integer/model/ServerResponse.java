package com.konstantinbulygin.prime_integer.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerResponse {

    private List<List<Integer>> messages = new ArrayList<>();

    public ServerResponse() {
    }

    public ServerResponse(List<List<Integer>> messages) {
        this.messages = messages;
    }

    public List<List<Integer>> getMessages() {
        return messages;
    }

    public void setMessages(List<List<Integer>> messages) {
        this.messages = messages;
    }
}
