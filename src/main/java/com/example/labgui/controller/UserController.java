package com.example.labgui.controller;

import com.example.labgui.Main;
import com.example.labgui.domain.Friendship;
import com.example.labgui.domain.User;
import com.example.labgui.domain.domain.FriendshipDTO;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.observer.Observer;
import com.example.labgui.repo.FriendshipDBRepo;
import com.example.labgui.service.UsersService;
import exceptions.ServiceException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserController implements Observer {
    private UsersService srv;

    private long last_called;

    private User user;
    private ObservableList<FriendshipDTO> model = FXCollections.observableArrayList();

    private ObservableList<User> modelUser = FXCollections.observableArrayList();
    @FXML
    Button addFriend;

    @FXML
    Button removeFriend;

    @FXML
    Button acceptButton;

    @FXML
    Button rejectButton;

    @FXML
    TextField addFriendField;

    @FXML
    TextField removeFriendField;

    @FXML
    TableView<FriendshipDTO> tableViewUser;

/*    @FXML
    TableView<User> tableViewAllUsers;

    @FXML
    TableColumn<User,String> tableColumnAllUsers;*/

    @FXML
    TableColumn<FriendshipDTO,String> tableColumnUsername;

    @FXML
    TableColumn<FriendshipDTO,String> tableColumnFriendsFrom;
    @FXML
    TableColumn<FriendshipDTO,String> tableColumnStatus;

    @FXML
    TextField searchTextField;

    @FXML
    Button undoButton;
    @FXML
    Button buttonConversation;


    public void setData(UsersService srv, User user1){
        this.srv=srv;
        this.user=user1;
        srv.addObserver(this);
        this.last_called=System.currentTimeMillis();
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);
        initModel(srv.getAllRelationshipsWithUsers(user));
        /*initModelUser();*/
    }

    @Override
    public void update(){

        initModel(srv.getAllRelationshipsWithUsers(user));
       /* initModelUser();*/
    }

   /* private void initModelUser() {
        modelUser.setAll(srv.getAll());
    }*/

    @FXML
    public void initModel(List<FriendshipDTO> user){
        model.setAll(user);
    }

    @FXML
    public void initialize(){
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<FriendshipDTO,String>("friendUsername"));
        tableColumnFriendsFrom.setCellValueFactory(new PropertyValueFactory<FriendshipDTO,String>("friendsFrom"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<FriendshipDTO,String>("status"));
        tableViewUser.setItems(model);
       /* tableColumnAllUsers.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        tableViewAllUsers.setItems(modelUser);*/
        /*tableViewAllUsers.setVisible(false);*/
    }
    @FXML
    public void handleAddFriend(ActionEvent event){
        /*String friendUsername = addFriendField.getText();
        try{
            srv.addFriendship(user.getUsername(),friendUsername);
            addFriendField.setText("");
            update();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Friendship request sent");
            alert.setContentText("Friendship request sent successfully!");
            alert.showAndWait();
        }
        catch(ServiceException | RepositoryException se){
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(se.getMessage());
            alert.showAndWait();
        }*/
        if(tableViewUser.getSelectionModel().getSelectedItem()!=null &&tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("not friends")){
            try{
                User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
                System.out.println(user.getUsername());
                srv.addFriendship(user.getUsername(),friend.getUsername());
                initModel(srv.getAllRelationshipsWithUsers(user));
            }
            catch(RepositoryException|ServiceException ex){

            }
        }
        else{
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("You and the user are already friends");
            alert.showAndWait();
        }
    }

    public void handleFriendRequestsButton(ActionEvent actionEvent) {
        /*Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/requestview.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 920, 460);
            stage.setTitle(user.getUsername());
            stage.setScene(scene);

            RequestController requestController =fxmlLoader.getController();
            requestController.setData(srv,user);

            stage.show();
        }
        catch(IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Error!");
            alert.showAndWait();
        }*/
        try {
            initModel(srv.getAllFriendshipRequests(user));
            acceptButton.setVisible(true);
            rejectButton.setVisible(true);
        }
        catch(RepositoryException re){}
    }

    public void handleRemoveFriend(ActionEvent actionEvent) {
/*        String friendUsername = removeFriendField.getText();
        try{
            srv.removeFriendship(user.getUsername(),friendUsername);
            update();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Friend removed");
            alert.setContentText("Friend removed");
            alert.showAndWait();

        }
        catch(RepositoryException|ServiceException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }*/
        if(tableViewUser.getSelectionModel().getSelectedItem()!=null &&tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("accepted")){
            try{
                User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
                System.out.println(user.getUsername());
                srv.removeFriendship(user.getUsername(),friend.getUsername());
                initModel(srv.getAllRelationshipsWithUsers(user));
            }
            catch(RepositoryException|ServiceException ex){

            }
        }
        else{
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("You and the user are not friends");
            alert.showAndWait();
        }
    }

    public void handleFriendList(ActionEvent actionEvent) {
        initModel(srv.getAllFriendshipsForUser(user));
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);
    }

    public void handleSearchTextField() {
        long current_time = System.currentTimeMillis();
        if(last_called+700<current_time)
        {
            last_called=current_time;
            String startsWith = searchTextField.getText();
            if(!startsWith.equals("")){
                srv.startsWith(startsWith,user);
                initModel(srv.startsWith(startsWith,user));
                return;
            }
            initModel(srv.getAllRelationshipsWithUsers(user));
        }
    }
    public void handleAcceptButton(ActionEvent actionEvent) {
        //System.out.println(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
        if(tableViewUser.getSelectionModel().getSelectedItem()!=null &&tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("pending")){
            try{
                User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
                System.out.println(user.getUsername());
                srv.acceptFriendshipRequest(user.getUsername(),friend.getUsername());
                initModel(srv.getAllFriendshipRequests(user));
            }
           catch(RepositoryException re){}
        }
    }

    public void handleRejectButton(ActionEvent actionEvent) {
        if (tableViewUser.getSelectionModel().getSelectedItem() != null && tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("pending")) {
            try {
                User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
                srv.removeFriendship(user.getUsername(), friend.getUsername());
                initModel(srv.getAllFriendshipsForUser(user));
            } catch (RepositoryException | ServiceException re) {}
        }
    }

    public void handleAllUsersButton(ActionEvent actionEvent) {
        initModel(srv.getAllRelationshipsWithUsers(user));
        rejectButton.setVisible(false);
        acceptButton.setVisible(false);
    }

    public void handleUndoButton(ActionEvent actionEvent) {
        if(tableViewUser.getSelectionModel().getSelectedItem()!=null &&tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("pending")){
            try{
                User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());
                srv.removeFriendship(user.getUsername(),friend.getUsername());
                initModel(srv.getAllRelationshipsWithUsers(user));
            }
            catch(RepositoryException|ServiceException ex){

            }
        }
        else{
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("You have not sent a friend request to this user");
            alert.showAndWait();
        }
    }

    public void handleOpenConversation(ActionEvent actionEvent) {
        if(tableViewUser.getSelectionModel().getSelectedItem()!=null &&tableViewUser.getSelectionModel().getSelectedItem().getStatus().equals("accepted")){
            try{
            User friend = srv.findUser(tableViewUser.getSelectionModel().getSelectedItem().getFriendUsername());

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/messageview.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setTitle("Conversation");
                stage.setScene(scene);

                MessageController messageController= fxmlLoader.getController();
                messageController.setData(srv,user,friend);

                stage.show();
            }
            catch(IOException|RepositoryException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Error!");
                alert.showAndWait();
            }
        }

    }
}

