package com.example.labgui.repo;



import com.example.labgui.domain.User;
import com.example.labgui.domain.validators.UserValidator;
import com.example.labgui.domain.validators.Validator;
import com.example.labgui.exceptions.ValidationException;

import java.util.List;

public class UserFile extends AbstractFileRepo<String, User> {
    public UserFile(String filename) {
        super(filename);
    }

    @Override
    public User extractEntity(List<String> attr) {
        User us = new User(attr.get(0),attr.get(1),attr.get(2));
        Validator<User> val = new UserValidator();
        try{
            val.validate(us);
            return us;
        }
        catch(ValidationException ve){
            ve.printStackTrace();
        }
        return null;
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getUsername()+";"+entity.getEmail()+";"+entity.getPassword();
    }
}
