/*
package com.example.labgui.repo;

import domain.Friendship;
import domain.User;
import domain.validators.Validator;
import exceptions.RepositoryException;
import exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendshipFile extends AbstractFileRepo<Set<String>,Friendship> {
    public FriendshipFile(String filename) {
        super(filename);
    }

    @Override
    public Friendship extractEntity(List<String> attr) {
        User u1 = new User(attr.get(0),attr.get(1),attr.get(2));
        User u2 = new User(attr.get(3),attr.get(4),attr.get(5));
        LocalDateTime time = LocalDateTime.parse(attr.get(6));
        Friendship fr = new Friendship(u1,u2,time);
        return fr;
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getUser1().getUsername()+";"+entity.getUser1().getEmail()+";"+entity.getUser1().getPassword()+";"+
                entity.getUser2().getUsername()+";"+entity.getUser2().getEmail()+";"+entity.getUser2().getPassword()+";"+entity.getFriendsFrom();
    }
}
*/
