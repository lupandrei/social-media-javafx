package com.example.labgui.controller;

import com.example.labgui.Main;
import com.example.labgui.domain.User;
import com.example.labgui.exceptions.RepositoryException;
import com.example.labgui.exceptions.ValidationException;
import com.example.labgui.observer.Observer;
import com.example.labgui.service.UsersService;
import exceptions.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Provider;
import java.security.Signature;

public class LoginController{

    private UsersService srv;
    @FXML
    Button buttonLogin;

   /* @FXML
    Button buttonSignup;*/
    @FXML
    TextField loginUsernameTextField;

    @FXML
    PasswordField loginPasswordTextField;

    @FXML
    Hyperlink signupHyperlink;
   /* @FXML
    TextField signupPasswordTextField;
    @FXML
    TextField signupUsernameTextField;
    @FXML
    TextField signupEmailTextField;*/

    public void loadData(User user){
        Stage currentStage = (Stage) loginPasswordTextField.getScene().getWindow();
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
    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        String username =loginUsernameTextField.getText();
        String password = loginPasswordTextField.getText();
        try{
            User user = srv.login(username,password);
            loadData(user);
        }
        catch(ServiceException | RepositoryException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

   /* @FXML
    public void handleSignupButton(ActionEvent actionEvent){
        String username = signupUsernameTextField.getText();
        String password = signupPasswordTextField.getText();
        String email = signupEmailTextField.getText();
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

    }*/
    public void setData(UsersService srv) {
        this.srv = srv;
    }

    public void handleSignupHyperlink(ActionEvent actionEvent) {
        Stage currentStage = (Stage) loginPasswordTextField.getScene().getWindow();
        currentStage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/signupview.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Sign up");
            stage.setScene(scene);

            SignupController signupController= fxmlLoader.getController();
            signupController.setData(srv);

            stage.show();
        }
        catch(IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Error!");
            alert.showAndWait();
        }
    }
}
