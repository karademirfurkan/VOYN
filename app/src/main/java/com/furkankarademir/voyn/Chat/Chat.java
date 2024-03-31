package com.furkankarademir.voyn.Chat;

import java.util.ArrayList;

public class Chat
{
    private String firstUserId;
    private String secondUserId;
    private ArrayList<Message> messagesInBetween;

    public Chat(String firstUserId, String secondUserId, ArrayList<Message> messagesInBetween)
    {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.messagesInBetween = messagesInBetween;
    }

    public String getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(String firstUserId) {
        this.firstUserId = firstUserId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(String secondUserId) {
        this.secondUserId = secondUserId;
    }

    public ArrayList<Message> getMessagesInBetween() {
        return messagesInBetween;
    }

    public void setMessagesInBetween(ArrayList<Message> messagesInBetween) {
        this.messagesInBetween = messagesInBetween;
    }
}
