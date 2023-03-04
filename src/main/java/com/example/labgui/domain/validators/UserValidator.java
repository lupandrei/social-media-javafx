package com.example.labgui.domain.validators;

import com.example.labgui.domain.User;

import com.example.labgui.exceptions.ValidationException;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User>{

    private boolean validateEmail(String email){
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }
    public void validate(User user)throws ValidationException
    {
        String message="";
        if(!validateEmail(user.getEmail())){
            message+="Invalid email\n";
        }
        String username = user.getUsername();
        if(username.length()<4)
        {
            message+="Username should have atleast 3 characthers\n";
        }
        for(int i=0;i<username.length();i++)
        {
            if(username.charAt(i)==' '|| username.charAt(i)==';'){
                message+="Invalid username\n";
                break;
            }
        }
        String password = user.getPassword();
        if(password.length()<4)
        {
            message+="Password should have atleast 6 characthers\n";
        }
        for(int i=0;i<password.length();i++)
        {
            if(password.charAt(i)==' '|| password.charAt(i)==';'){
                message+="Invalid password\n";
                break;
            }
        }
        if(!message.isEmpty()){
            throw new ValidationException(message);
        }
    }
}
