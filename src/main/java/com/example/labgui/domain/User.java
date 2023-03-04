package com.example.labgui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements HasID<String>{
    private String username;
    private String email;
    private String password;

    List<User> friends;

    public void addFriend(User usr){
        friends.add(usr);
    }
    public boolean hasFriends(){
        if(friends.isEmpty()){
            return false;
        }
        return true;
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends=new ArrayList<>();
    }
    public void removeFriend(User u2){
        friends.remove(u2);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    @Override
    public String getId(){
        return username;
    }

    @Override
    public void setId(String usr){
        this.username=usr;
    }
}
