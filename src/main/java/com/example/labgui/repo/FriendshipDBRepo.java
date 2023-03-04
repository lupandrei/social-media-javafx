package com.example.labgui.repo;

import com.example.labgui.domain.Friendship;
import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendshipDBRepo implements Repository<Friendship, Set<String>> {
    private String url;
    private String username;
    private String password;

    public FriendshipDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Function adds a new entity
     * @param entity
     * @throws RepositoryException
     */
    @Override
    public void add(Friendship entity) throws RepositoryException {
        Set<String> setString = new HashSet<>();
        setString.add(entity.getUser1().getUsername());
        setString.add(entity.getUser2().getUsername());
        if(find(setString)!=null){
            throw new RepositoryException("Friendship already exists!");
        }
        String sql ="INSERT INTO friendship(id1,id2,friendsFrom,sentby) VALUES (?,?,?,?)";
        try(Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            ps.setString(1,entity.getUser1().getUsername());
            ps.setString(2,entity.getUser2().getUsername());
            ps.setString(3, entity.getFriendsFrom().format(formatter));
            ps.setString(4,entity.getUser1().getUsername());
            ps.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Function removes given friendship.
     * @param entity, friendship to be removed
     * @throws RepositoryException, if the friendship does not exist
     */
    @Override
    public void remove(Friendship entity) throws RepositoryException {
        Set<String> setString = new HashSet<>();
        setString.add(entity.getUser1().getUsername());
        setString.add(entity.getUser2().getUsername());
        String sql = "DELETE FROM friendship WHERE id1=? AND id2=? OR id1=? AND id2=?";
        try(Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1,entity.getUser1().getUsername());
            ps.setString(2,entity.getUser2().getUsername());
            ps.setString(3,entity.getUser2().getUsername());
            ps.setString(4,entity.getUser1().getUsername());
            ps.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    /**
     *
     * @param stringSet, ids of users
     * @return friendship with the given ids
     * @throws RepositoryException, if the friendship does not exist
     */
    @Override
    public Friendship find(Set<String> stringSet) throws RepositoryException {
        String sql="""
        SELECT U1.username as username_user1,
        U1.parola as parola_user1,
        U1.email as email_user1,
        U2.username as username_user2,
        U2.parola as parola_user2,
        U2.email as email_user2,
        F.friendsfrom as friendsfrom,
        F.status as friendstatus,
        F.sentby as friendsentby
        FROM friendship F
        INNER JOIN users U1 ON U1.username = F.id1
        INNER JOIN users U2 ON U2.username = F.id2
        WHERE U1.username = ? AND U2.username = ? OR U1.username = ? AND U2.username = ?
        """;
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            String u1 = stringSet.stream().findFirst().get();
            String u2 = stringSet.stream().skip(1).findFirst().get();
            ps.setString(1,u1);
            ps.setString(2,u2);
            ps.setString(3,u2);
            ps.setString(4,u1);
            ResultSet resultSet= ps.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            User user1 = new User(resultSet.getString("username_user1"),
                    resultSet.getString("email_user1"),resultSet.getString("parola_user1"));
            User user2 = new User(resultSet.getString("username_user2"),
                    resultSet.getString("email_user2"),resultSet.getString("parola_user2"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(resultSet.getString("friendsfrom"),formatter);
            String status = resultSet.getString("friendstatus");
            String sentBy = resultSet.getString("friendsentby");
            Friendship fr = new Friendship(user1,user2,ldt,status,sentBy);
            return fr;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return list of all friendships
     */
    @Override
    public List<Friendship> get_all() {
        String sql="""
        SELECT U1.username as username_user1,
        U1.parola as parola_user1,
        U1.email as email_user1,
        U2.username as username_user2,
        U2.parola as parola_user2,
        U2.email as email_user2,
        F.friendsfrom as friendsfrom,
        F.status as friendstatus,
        F.sentby as friendsentby
        from friendship F
        INNER JOIN users U1 ON U1.username = F.id1
        INNER JOIN users U2 ON U2.username = F.id2
        """;
        try(Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet resultSet=ps.executeQuery();
            List<Friendship> friendships = new ArrayList<>();
            while(resultSet.next()){
                User user1 = new User(resultSet.getString("username_user1"),
                        resultSet.getString("email_user1"),resultSet.getString("parola_user1"));
                User user2 = new User(resultSet.getString("username_user2"),
                        resultSet.getString("email_user2"),resultSet.getString("parola_user2"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime ldt = LocalDateTime.parse(resultSet.getString("friendsfrom"),formatter);
                String status = resultSet.getString("friendstatus");
                String sentBy = resultSet.getString("friendsentby");
                Friendship fr = new Friendship(user1,user2,ldt,status,sentBy);
                friendships.add(fr);
            }
            return friendships;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Friendship entity) {
        String user1 = entity.getUser1().getUsername();
        String user2 = entity.getUser2().getUsername();
        String sql= """
        UPDATE friendship set status='accepted' WHERE id1=? AND id2 = ? OR id1=? AND id2=?    
        """;
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,user1);
            ps.setString(2,user2);
            ps.setString(3,user2);
            ps.setString(4,user1);
            ps.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<Friendship> startsWith(Set<String> startsWith) {
        return null;
    }
}
