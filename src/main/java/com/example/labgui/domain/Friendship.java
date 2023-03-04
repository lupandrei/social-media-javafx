package com.example.labgui.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Friendship implements HasID<Set<String>>{
    private User user1;
    private User user2;
    private LocalDateTime friendsFrom;
    private String status;
    private String sentBy;

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public Friendship(User user1, User user2,LocalDateTime friendsFrom,String status,String sentBy) {
        this.user1 = user1;
        this.user2 = user2;
        this.friendsFrom= friendsFrom;
        this.status=status;
        this.sentBy=sentBy;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
    @Override
    public Set<String> getId(){
        HashSet<String> ids = new HashSet<>();
        ids.add(user1.getId());
        ids.add(user2.getId());
        return ids;
    }

    @Override
    public void setId(Set<String> strings) {
    }

    public String getStatus() {
        return status;
    }

    public String getSentBy() {
        return sentBy;
    }
}
