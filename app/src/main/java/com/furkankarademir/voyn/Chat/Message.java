package com.furkankarademir.voyn.Chat;

public class Message
{
    private String messageText;
    private String senderId;
    private String senderName;
    private String senderSurname;
    private boolean isMine;

    public Message(String messageText, String senderId, String senderName,
                   String senderSurname, boolean isMine)
    {
        this.messageText = messageText;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderSurname = senderSurname;
        this.isMine = isMine;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderSurname() {
        return senderSurname;
    }

    public void setSenderSurname(String senderSurname) {
        this.senderSurname = senderSurname;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
