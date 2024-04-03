package com.furkankarademir.voyn.Chat;

public class Message
{
    private String messageText;
    private String senderId;
    private String senderName;
    private String senderSurname;
    private boolean isMine;

    private String receiverId;


    public Message() {

    }

    public Message(String messageText, String senderId, String receiverId)
    {
        this.messageText = messageText;
        this.senderId = senderId;
        //this.senderName = senderName;
        //this.senderSurname = senderSurname;
        //this.isMine = isMine;
        this.receiverId = receiverId;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
