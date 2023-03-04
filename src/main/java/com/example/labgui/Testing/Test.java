/*
package com.example.labgui.Testing;


import com.example.labgui.domain.User;
import com.example.labgui.domain.validators.UserValidator;
import com.example.labgui.domain.validators.Validator;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.repo.inMemoryRepository;
import com.example.labgui.service.UsersService;

public class Test {
    public void runTests()  {
        try {
            communitiesTest();
            longestComponentTest();
        }catch(RepositoryException re){

        }
    }
    private void longestComponentTest() throws RepositoryException {
        inMemoryRepository testUserRepo =new inMemoryRepository<>();
        inMemoryRepository testFriendshipRepo = new inMemoryRepository();
        Validator testValidator = new UserValidator();
        UsersService serviceTest = new UsersService(testUserRepo,testFriendshipRepo, repoMessages, testValidator);
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
        testUserRepo.add(u1);
        testUserRepo.add(u2);
        testUserRepo.add(u3);
        testUserRepo.add(u4);
        testUserRepo.add(u5);
        testUserRepo.add(u6);
        testUserRepo.add(u7);
        testUserRepo.add(u8);
        testUserRepo.add(u9);
        testUserRepo.add(u10);
       */
/* testFriendshipRepo.add(new Friendship(u1,u2));
        testFriendshipRepo.add(new Friendship(u1,u3));
        testFriendshipRepo.add(new Friendship(u1,u4));
        testFriendshipRepo.add(new Friendship(u1,u5));
        testFriendshipRepo.add(new Friendship(u1,u6));
        testFriendshipRepo.add(new Friendship(u7,u8));
        testFriendshipRepo.add(new Friendship(u8,u9));
        testFriendshipRepo.add(new Friendship(u9,u10));
        User []result = serviceTest.mostSociableCommunity();
        assert(result.length==4);*//*

    }
    private void communitiesTest() throws RepositoryException {
        inMemoryRepository testUserRepo =new inMemoryRepository<>();
        inMemoryRepository testFriendshipRepo = new inMemoryRepository();
        Validator testValidator = new UserValidator();
        UsersService serviceTest = new UsersService(testUserRepo,testFriendshipRepo, repoMessages, testValidator);
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

        testUserRepo.add(u1);
        testUserRepo.add(u2);
        testUserRepo.add(u3);
        testUserRepo.add(u4);
        testUserRepo.add(u5);
        testUserRepo.add(u6);
        testUserRepo.add(u7);
        testUserRepo.add(u8);
        testUserRepo.add(u9);
        testUserRepo.add(u10);
       */
/* testFriendshipRepo.add(new Friendship(u1,u2));
        testFriendshipRepo.add(new Friendship(u3,u2));
        testFriendshipRepo.add(new Friendship(u3,u4));
        testFriendshipRepo.add(new Friendship(u5,u6));
        testFriendshipRepo.add(new Friendship(u7,u5));
        testFriendshipRepo.add(new Friendship(u8,u9));*//*

       */
/* int totalCommunities = serviceTest.communities();
        assert(totalCommunities==4);*//*

    }
}
*/
