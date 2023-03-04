package com.example.labgui.domain.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FriendshipDTO {
    private String friendUsername;
    private String friendsFrom;

    private String status;
    private String sentBy;

    public FriendshipDTO(String friendUsername, LocalDateTime friendsFrom,String status,String sentBy) {
        this.friendUsername = friendUsername;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if(status=="not friends")
            this.friendsFrom="-";
        else
            this.friendsFrom = friendsFrom.format(formatter);
        this.status =status;
        this.sentBy=sentBy;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public String getFriendsFrom() {
        return friendsFrom;
    }

    public String getStatus() {
        return status;
    }

    public String getSentBy() {
        return sentBy;
    }
}
