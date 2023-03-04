package com.example.labgui.controller;

import com.example.labgui.domain.Message;
import com.example.labgui.domain.User;
import com.example.labgui.service.UsersService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.security.Provider;
import java.util.List;


public class MessageController {

    public UsersService srv;

    public User user,friend;
    public Button buttonSend;
    public TextField messageField;
    public ScrollPane scrollMain;
    public VBox vboxMessages;

    public void setData(UsersService srv, User user,User friend){
        this.srv=srv;
        this.user=user;
        this.friend=friend;
        initMessage();
    }

    private void initMessage() {
        List<Message> messageList= srv.getNotSeenMessages(user,friend);
        for(Message msg:messageList){
            HBox hbox = new HBox();
            if(msg.getReceivedBy().equals(friend.getUsername()))
                hbox.setAlignment(Pos.CENTER_RIGHT);
            else
                hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(5,5,5,10));
            Text text = new Text(msg.getText());
            TextFlow textFlow = new TextFlow(text);
            hbox.getChildren().add(textFlow);
            vboxMessages.getChildren().add(hbox);
        }
    }


    public void handleSend(ActionEvent actionEvent) {
        if(!messageField.getText().isEmpty()){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.setPadding(new Insets(5,5,5,10));
            Text text = new Text(messageField.getText());
            TextFlow textFlow = new TextFlow(text);
            hbox.getChildren().add(textFlow);
            vboxMessages.getChildren().add(hbox);
            srv.sendMessage(user,friend,messageField.getText());
            messageField.clear();
        }
    }
}
