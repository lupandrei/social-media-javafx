package com.example.labgui.repo;

import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepository implements Repository<User,String>{
    String url;
    String userName;
    String parola;

    public UserDBRepository(String url, String username, String parola) {
        this.url = url;
        this.userName = username;
        this.parola = parola;
    }

    /**
     * Function adds a new entity in database.
     * @param entity
     * @throws RepositoryException
     */
    @Override
    public void add(User entity) throws RepositoryException {/*
        if(find(entity.getUsername())!=null){
            throw new RepositoryException("User already exists");
        }*/
        String sql = "INSERT INTO users(username,email,parola) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(url,userName,parola);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,entity.getUsername());
            ps.setString(2,entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.executeUpdate();
        }
        catch(SQLException se){
            se.getMessage();
        }
    }

    /**
     * Function removes user with given details.
     * @param entity
     * @throws RepositoryException, if the user does not exist
     */
    @Override
    public void remove(User entity) throws RepositoryException {
        User user =find(entity.getUsername());
        if(user!=null)
        {
            String sql = "DELETE FROM users WHERE username=?";
            try(Connection connection = DriverManager.getConnection(url,userName,parola);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,entity.getUsername());
                ps.executeUpdate();
            }
            catch(SQLException se){
                se.printStackTrace();
            }
        }
        else
            throw new RepositoryException("User does not exist");

    }

    /**
     *
     * @param user
     * @return User with the given details
     * @throws RepositoryException, if the user does not exist
     */
    @Override
    public User find(String user) throws RepositoryException {
        String sql ="SELECT * FROM users WHERE username=?";
        try(Connection connection = DriverManager.getConnection(url,userName,parola);
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,user);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new RepositoryException("User does not exist");
            String username = resultSet.getString("username");
            String parola =resultSet.getString("parola");
            String email = resultSet.getString("email");
            User usernou = new User(username,email,parola);
            return usernou;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return List of all users
     */
    @Override
    public List<User> get_all() {
        List<User> users= new ArrayList<User>();
        try(Connection connection=DriverManager.getConnection(url,userName,parola);
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()){
                String username = resultSet.getString("username");
                String parola =resultSet.getString("parola");
                String email = resultSet.getString("email");
                User usernou = new User(username,email,parola);
                users.add(usernou);
            }
        }
        catch(SQLException se){
            se.printStackTrace();;
        }
        return users;
    }

    /**
     * Function updates values of entity.
     * @param entity
     */
    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET parola=?, email=? WHERE username=?";
        try(Connection connection = DriverManager.getConnection(url,userName,parola);
        PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1, entity.getPassword());
            ps.setString(2,entity.getEmail());
            ps.setString(3,entity.getUsername());
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public List<User> startsWith(String startsWith) {
        List<User> users= new ArrayList<User>();
        try(Connection connection=DriverManager.getConnection(url,userName,parola);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username LIKE '" +startsWith+"%'"))
           {
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    String username = resultSet.getString("username");
                    String parola =resultSet.getString("parola");
                    String email = resultSet.getString("email");
                    User usernou = new User(username,email,parola);
                    users.add(usernou);
                }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return users;
    }
}
