package com.example.labgui.controller;

import com.example.labgui.Main;
import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.exceptions.ValidationException;
import com.example.labgui.service.UsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    private UsersService srv;
    @FXML
    Button buttonSignup;
    @FXML
    PasswordField signupPasswordField;
    @FXML
    TextField signupUsernameField;
    @FXML
    TextField signupEmailField;

    @FXML
    Hyperlink loginHyperlink;


    public void handleLoginHyperlink(ActionEvent actionEvent) {
        Stage currentStage = (Stage) signupPasswordField.getScene().getWindow();
        currentStage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/newlogin.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Log in");
            stage.setScene(scene);

            LoginController loginController= fxmlLoader.getController();
            loginController.setData(srv);

            stage.show();
        }
        catch(IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }

    public void handleButtonSignup(ActionEvent actionEvent) {
        String username = signupUsernameField.getText();
        String password = signupPasswordField.getText();
        String email = signupEmailField.getText();
        try{
            srv.add(username,email,password);
            User user = new User(username,email,password);
            loadData(user);
        }
        catch(RepositoryException | ValidationException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
    public void loadData(User user){
        Stage currentStage = (Stage) signupPasswordField.getScene().getWindow();
        currentStage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/userview.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 920, 460);
            stage.setTitle(user.getUsername());
            stage.setScene(scene);

            UserController userController =fxmlLoader.getController();
            userController.setData(srv,user);

            stage.show();
        }
        catch(IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }
    public void setData(UsersService service){
        this.srv=service;
    }

}
