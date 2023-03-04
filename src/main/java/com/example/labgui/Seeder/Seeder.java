package com.example.labgui.Seeder;


import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.exceptions.ValidationException;
import com.example.labgui.service.UsersService;
import exceptions.ServiceException;

public class Seeder {

    public void seed(UsersService srv) throws RepositoryException, ValidationException, ServiceException {
        User u1 = new User("andrei","andrei@gmail.com","parolaandrei");
        User u2 = new User("thomas","thomas@gmail.com","parolathomas");
        User u3 = new User("bogdan","bogdan@gmail.com","parolabogdan");
        User u4 = new User("stefan","stefan@gmail.com","parolastefan");
        User u5 = new User("raul","raul@gmail.com","parolaraul");
        User u6 = new User("sebi","sebi@gmail.com","parolasebi");
        User u7 = new User("rares","rares@gmail.com","parolarare");
        User u8 = new User("dan","dan@gmail.com","paroladan");
        User u9 = new User("ana","ana@gmail.com","parolaana");
        User u10 = new User("bianca","bianca@gmail.com","parolabianca");

        srv.add("andrei","andrei@gmail.com","parolaandrei");
        srv.add("thomas","thomas@gmail.com","parolathomas");
        srv.add("bogdan","bogdan@gmail.com","parolabogdan");
        srv.add("stefan","stefan@gmail.com","parolastefan");
        srv.add("raul","raul@gmail.com","parolaraul");
        srv.add("sebi","sebi@gmail.com","parolasebi");
        srv.add("rares","rares@gmail.com","parolarare");
        srv.add("dan","dan@gmail.com","paroladan");
        srv.add("ana","ana@gmail.com","parolaana");
        srv.add("bianca","bianca@gmail.com","parolabianca");
        srv.addFriendship(u1.getUsername(),u2.getUsername());
        srv.addFriendship(u1.getUsername(),u3.getUsername());
        srv.addFriendship(u1.getUsername(),u4.getUsername());
        srv.addFriendship(u1.getUsername(),u5.getUsername());
        srv.addFriendship(u6.getUsername(),u7.getUsername());
        srv.addFriendship(u7.getUsername(),u8.getUsername());
        srv.addFriendship(u8.getUsername(),u9.getUsername());
        srv.addFriendship(u10.getUsername(),u9.getUsername());

    }
}
