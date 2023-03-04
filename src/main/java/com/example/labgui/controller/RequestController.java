/*
package com.example.labgui.controller;

import com.example.labgui.domain.User;
import com.example.labgui.domain.domain.FriendshipDTO;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.observer.Observer;
import com.example.labgui.service.UsersService;
import exceptions.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestController implements Observer {

    private UsersService srv;
    private User user;

    private ObservableList<User> model= FXCollections.observableArrayList();
    @FXML
    Button acceptButton;

    @FXML
    Button rejectButton;

    @FXML
    TableView<User> tableRequestsView;

    @FXML
    TableColumn<User,String> tableRequestsColumn;

    public void setData(UsersService srv,User user){
        this.srv=srv;
        this.user=user;
        srv.addObserver(this);
        initModel();
    }*/
/**//*


    @Override
    public void update() {
        initModel();
    }

    private void initModel() {
        try {
            model.setAll(srv.getAllFriendshipRequests(user));
        }
        catch(RepositoryException ex){}
    }

    public void handleAcceptButton(ActionEvent actionEvent) {
        System.out.println(tableRequestsView.getSelectionModel().getSelectedItem());
        if(tableRequestsView.getSelectionModel().getSelectedItem()!=null){
            User friend = tableRequestsView.getSelectionModel().getSelectedItem();
            srv.acceptFriendshipRequest(user.getUsername(),friend.getUsername());
            update();
        }
    }

    public void handleRejectButton(ActionEvent actionEvent) {
        if(tableRequestsView.getSelectionModel().getSelectedItem()!=null){
            User friend = tableRequestsView.getSelectionModel().getSelectedItem();
            try {
                srv.removeFriendship(user.getUsername(), friend.getUsername());
                update();
            }
            catch(RepositoryException | ServiceException re){};
        }
    }

    @FXML
    public void initialize(){
        tableRequestsColumn.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        tableRequestsView.setItems(model);
    }
}
*/
