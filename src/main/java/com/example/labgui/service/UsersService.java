package com.example.labgui.service;



import com.example.labgui.Graph.Graph;
import com.example.labgui.domain.Friendship;
import com.example.labgui.domain.Message;
import com.example.labgui.domain.User;
import com.example.labgui.domain.domain.FriendshipDTO;
import com.example.labgui.domain.validators.Validator;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.exceptions.ValidationException;
import com.example.labgui.repo.Repository;
import exceptions.ServiceException;
import com.example.labgui.observer.Observable;
import com.example.labgui.observer.Observer;

import java.time.LocalDateTime;
import java.util.*;


public class UsersService implements Observable {
    private Repository<User,String> repo;

    private Repository<Friendship, Set<String>> repoFriendships;
    private Repository<Message, Set<String>> repoMessages;
    private Validator<User> validator;

    List<Observer> obs;


    public UsersService(Repository<User, String> repo, Repository<Friendship,Set<String>> repoFriendships, Repository<Message, Set<String>> repoMessages, Validator<User> validator) {
        this.repo = repo;
        this.repoFriendships = repoFriendships;
        this.repoMessages = repoMessages;
        this.validator = validator;
        this.obs = new ArrayList<>();
    }

    /**
     * Function adds a new friendship.
     * @param user1
     * @param user2
     * @throws RepositoryException
     * @throws ServiceException, if the Users are invalid
     */
    public void addFriendship(String user1,String user2) throws RepositoryException, ServiceException {
        User u1 = repo.find(user1);
        User u2 = repo.find(user2);
        if(u1!=null && u2!=null){
            u1.addFriend(u2);
            u2.addFriend(u1);
            LocalDateTime time = LocalDateTime.now();
            Friendship friends = new Friendship(u1,u2,time,"pending",user1);
            repoFriendships.add(friends);
        }
        else
            throw new ServiceException("Invalid users.\n");

    }

    /**
     * Function removes an existent friendship.
     * @param user1
     * @param user2
     * @throws RepositoryException
     * @throws ServiceException, if the friendship or the users are invalid.
     */
    public void removeFriendship(String user1,String user2) throws RepositoryException,ServiceException {
        User u1=repo.find(user1);
        User u2 =repo.find(user2);
        if(u1!=null && u2!=null){
            HashSet<String> set = new HashSet<>();
            set.add(user1);
            set.add(user2);
            Friendship friends = repoFriendships.find(set);
            if(friends!=null){
                repoFriendships.remove(friends);
                notifyObservers();
                //u1.removeFriend(u2);
                //u2.removeFriend(u1);
            }
            else
                throw new ServiceException("Invalid friendship.\n");

        }
        else throw new ServiceException("Invalid users.\n");
    }

    /**
     * Function adds a new user.
     * @param username
     * @param email
     * @param password
     * @throws RepositoryException
     * @throws ValidationException
     */
    public void add(String username, String email, String password)throws RepositoryException, ValidationException
    {
        User u = new User(username,email,password);
        validator.validate(u);
        repo.add(u);
    }

    /**
     * Function removes a given user.
     * @param username
     * @throws RepositoryException
     * @throws ServiceException
     */
    public void remove(String username)throws RepositoryException,ServiceException{
        User user = repo.find(username);
        List<Friendship> friends = repoFriendships.get_all();
        Friendship[] friendsArray = friends.toArray(new Friendship[0]);
        for(Friendship f: friendsArray){
            User u1=f.getUser1();
            User u2=f.getUser2();
            if(user.equals(u1) || user.equals(u2))
                throw new ServiceException("User has friends and can't be deleted at the moment.\n");
        }
        repo.remove(user);
    }

    public List<User> getAll(){
        return repo.get_all();
    }

    /**
     * Function that returns the number of communities.
     * @return int - number of communities
     */
    public int communities(){
        List<User> userList = repo.get_all();
        User[] userArray = userList.toArray(new User[0]);
        int users = userList.size();
        int [][]friendships = new int[users+1][users+1];
        /*for(int i=0;i<users-1;i++)
        {
            for(int j=i+1;j<users;j++){
                Friendship friends = new Friendship(userArray[i],userArray[j]);
                HashSet<String> set = new HashSet<>();
                set.add(userArray[i].getUsername());
                set.add(userArray[j].getUsername());
                if(repoFriendships.find(set)!=null){
                    friendships[i][j]=1;
                    friendships[j][i]=1;
                }
            }
        }*/
        int[]visited = new int[users+1];
       /* for(int i =0;i<users;i++)
        {
            visited[i]=0;
        }*/
        initialize(friendships,visited,userArray,users);

        Graph graph = new Graph(friendships,visited);
        int comm=graph.numberOfComponents(users);
        /*for(int i =0;i<users;i++)
        {
            if(visited[i]==0){
                comm+=1;
                graph.dfs(i);
            }
        }*/
        return comm;
    }

    private void initialize(int [][] friendships,int[]visited, User[] userArray, int users){
        LocalDateTime time=LocalDateTime.now();
        for(int i=0;i<users-1;i++)
        {
            for(int j=i+1;j<users;j++){

                Friendship friends = new Friendship(userArray[i],userArray[j],time,"pending","sent");
                HashSet<String> set = new HashSet<>();
                set.add(userArray[i].getUsername());
                set.add(userArray[j].getUsername());
                try {
                    if (repoFriendships.find(set) != null) {
                        friendships[i][j] = 1;
                        friendships[j][i] = 1;
                    }
                }
                catch(RepositoryException re){}
            }
        }
        for(int i =0;i<users;i++)
        {
            visited[i]=0;
        }
    }

    /**
     *
     * @return USER[] - array of the members of the most sociable community
     */
    public User[] mostSociableCommunity(){
        List<User> userList= repo.get_all();
        User[] userArray= userList.toArray(new User[0]);
        int users = userList.size();
        int [][]friendships = new int[users+1][users+1];

        /*for(int i=0;i<users-1;i++)
        {
            for(int j=i+1;j<users;j++){
                Friendship friends = new Friendship(userArray[i],userArray[j]);
                HashSet<String> set = new HashSet<>();
                set.add(userArray[i].getUsername());
                set.add(userArray[j].getUsername());
                if(repoFriendships.find(set)!=null){
                    friendships[i][j]=1;
                    friendships[j][i]=1;
                }
            }
        }

        for(int i =0;i<users;i++)
        {
            visited[i]=0;
        }*/
        int[]visited = new int[users+1];
        initialize(friendships,visited,userArray,users);
        Graph graph = new Graph(friendships,visited);
        int []result = graph.longestComponent();
        User[] mostSociable = new User[result.length];
        int k=0;
        for(int val:result){
            mostSociable[k++]=userArray[val];
        }
        return mostSociable;
    }
    public List<Friendship> getAllFriendships(){
        return repoFriendships.get_all();
    }

    @Override
    public void addObserver(Observer o) {
        obs.add(o);
    }
    public void notifyObservers(){
        for(Observer o:obs) {
            o.update();
        }
    }

    public List<FriendshipDTO> getAllFriendshipsForUser(User user) {
        List<FriendshipDTO> friends = new ArrayList<>();
        for(Friendship f:repoFriendships.get_all()){
            if(f.getUser1().equals(user)){
                friends.add(new FriendshipDTO(f.getUser2().getUsername(),f.getFriendsFrom(),f.getStatus(),f.getSentBy()));
            } else if (f.getUser2().equals(user)) {
                friends.add(new FriendshipDTO(f.getUser1().getUsername(),f.getFriendsFrom(),f.getStatus(),f.getSentBy()));
            }
        }
        return friends;
    }

    public User login(String username, String password) throws RepositoryException, ServiceException {
        User user = repo.find(username);
        if(!user.getPassword().equals(password)){
            throw new ServiceException("Incorrect password");
        }
        return user;
    }
    public void acceptFriendshipRequest(String user1,String user2){
        Set<String> fr = new HashSet<>();
        fr.add(user1);
        fr.add(user2);
        try{
            Friendship friendship = repoFriendships.find(fr);
            repoFriendships.update(friendship);
            notifyObservers();
        }
       catch(RepositoryException re){
            System.out.println(re.getMessage());
       }



    }
    public User findUser(String username)throws RepositoryException{
        return repo.find(username);
    }

    public List<FriendshipDTO> getAllFriendshipRequests(User user) throws RepositoryException{
        List<FriendshipDTO> friendshipsRequests=new ArrayList<>();
        List<FriendshipDTO> friendships = getAllFriendshipsForUser(user);
        for(FriendshipDTO f:friendships){
            if(!f.getSentBy().equals(user.getUsername())&&!f.getStatus().equals("accepted")){
                friendshipsRequests.add(f);
            }
        }
        return friendshipsRequests;
    }

    public List<FriendshipDTO> startsWith(String startsWith,User user) {
        List<User>users= repo.startsWith(startsWith);
        List<FriendshipDTO> friendshipDTOS=new ArrayList<>();
        for(User u:users){
            Set<String> set = new HashSet<>();
            set.add(user.getUsername());
            set.add(u.getUsername());
            try {
               Friendship friendship = repoFriendships.find(set);
                if (friendship!=null){
                    FriendshipDTO friendshipDTO = new FriendshipDTO(u.getUsername(),friendship.getFriendsFrom(), friendship.getStatus(),friendship.getSentBy());
                    friendshipDTOS.add(friendshipDTO);
                }
                else{
                    FriendshipDTO friendshipDTO = new FriendshipDTO(u.getUsername(),LocalDateTime.now(),"not friends","-");
                    friendshipDTOS.add(friendshipDTO);
                }
            }
            catch(RepositoryException re){}
        }
        return friendshipDTOS;
    }

    public List<FriendshipDTO> getAllRelationshipsWithUsers(User user) {
        List<User> users = repo.get_all();
        List<FriendshipDTO> friendshipDTOS=new ArrayList<>();
        for(User u:users){
            if(!u.equals(user))
            {
                Set<String> set = new HashSet<>();
                set.add(user.getUsername());
                set.add(u.getUsername());
                try {
                    Friendship friendship = repoFriendships.find(set);
                    if (friendship!=null){
                        FriendshipDTO friendshipDTO = new FriendshipDTO(u.getUsername(),friendship.getFriendsFrom(), friendship.getStatus(),friendship.getSentBy());
                        friendshipDTOS.add(friendshipDTO);
                    }
                    else{
                        FriendshipDTO friendshipDTO = new FriendshipDTO(u.getUsername(),LocalDateTime.now(),"not friends","-");
                        friendshipDTOS.add(friendshipDTO);
                    }
                }
                catch(RepositoryException re){}
            }

        }
        return friendshipDTOS;


    }

    public List<Message> getNotSeenMessages(User user, User friend) {
        List<Message> messages = repoMessages.get_all();
        List<Message> filteredMessages = messages.stream().filter(x->x.getReceivedBy().equals(user.getUsername())||x.getUser().getUsername().equals(user.getUsername())).toList();
        List<Message> filteredMessages2 = filteredMessages.stream().filter(x->x.getReceivedBy().equals(friend.getUsername())||x.getUser().getUsername().equals(friend.getUsername())).toList();
        List<Message> filtered = filteredMessages2.subList(Math.max(filteredMessages.size()-15, 0),filteredMessages2.size());

        return filtered;

    }

    public void sendMessage(User user, User friend, String text) {
        Message msg = new Message(text,user,friend,"delivered",friend.getUsername());
        try {
            repoMessages.add(msg);
        }
        catch(RepositoryException re){}
    }
}
