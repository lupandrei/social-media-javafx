package com.example.labgui.domain;

import java.util.HashSet;
import java.util.Set;

public class Message implements HasID<Set<String>>{
    private String text;
    private User user;
    private User friend;

    private String status;

    private String receivedBy;

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public User getFriend() {
        return friend;
    }

    public Message(String text, User user, User friend, String status, String receivedBy) {
        this.text = text;
        this.user = user;
        this.friend = friend;
        this.status = status;
        this.receivedBy = receivedBy;
    }
    @Override
    public Set<String> getId(){
        HashSet<String> ids = new HashSet<>();
        ids.add(user.getId());
        ids.add(friend.getId());
        return ids;
    }

    @Override
    public void setId(Set<String> strings) {
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public String getStatus() {
        return status;
    }
}
