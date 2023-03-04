package com.example.labgui.repo;

import com.example.labgui.domain.Friendship;
import com.example.labgui.domain.Message;
import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageDBRepo implements Repository<Message, Set<String>> {
    private String url;
    private String username;
    private String password;

    public MessageDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(Message entity) throws RepositoryException {
        String sql ="INSERT INTO message(text,receivedby,sentby,status) VALUES (?,?,?,?)";
        try(Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)
        ){

            ps.setString(1, entity.getText());
            ps.setString(2,entity.getFriend().getUsername());
            ps.setString(3,entity.getUser().getUsername());
            ps.setString(4,entity.getStatus());

            ps.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public void remove(Message entity) throws RepositoryException {
    }

    @Override
    public Message find(Set<String> strings) throws RepositoryException {
        return null;
    }

    @Override
    public List<Message> get_all() {
        String sql="""
        SELECT U1.username as username_user1,
        U1.parola as parola_user1,
        U1.email as email_user1,
        U2.username as username_user2,
        U2.parola as parola_user2,
        U2.email as email_user2,
        M.sentby as sentbyuser,
        M.receivedby as receivedbyuser,
        M.text as text,
        M.status as messagestatus
        from message M
        INNER JOIN users U1 ON U1.username = M.sentby
        INNER JOIN users U2 ON U2.username = M.receivedby
        """;
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet resultSet=ps.executeQuery();
            List<Message> messages = new ArrayList<>();
            while(resultSet.next()){
                User user1 = new User(resultSet.getString("username_user1"),
                        resultSet.getString("email_user1"),resultSet.getString("parola_user1"));
                User user2 = new User(resultSet.getString("username_user2"),
                        resultSet.getString("email_user2"),resultSet.getString("parola_user2"));
                String status = resultSet.getString("messagestatus");

                String text = resultSet.getString("text");
                Message msg = new Message(text,user1,user2,status,user2.getUsername());
                messages.add(msg);
            }
            return messages;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public List<Message> startsWith(Set<String> startsWith) {
        return null;
    }
}
