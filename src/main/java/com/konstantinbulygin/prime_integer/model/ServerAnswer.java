package com.konstantinbulygin.prime_integer.model;

import java.util.ArrayList;
import java.util.List;

public class ServerAnswer {

    private int message;
    private List<List<Integer>> messages = new ArrayList<>();

    public ServerAnswer(int message, List<List<Integer>> messages) {
        this.message = message;
        this.messages = messages;
    }

    public ServerAnswer(int message) {
        this.message = message;
    }

    public ServerAnswer(List<List<Integer>> messages) {
        this.messages = messages;
    }

    public ServerAnswer() {
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public List<List<Integer>> getMessages() {
        return messages;
    }

    public void setMessages(List<List<Integer>> messages) {
        this.messages = messages;
    }
}
