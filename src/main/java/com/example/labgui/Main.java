package com.example.labgui;

import com.example.labgui.controller.LoginController;
import com.example.labgui.domain.validators.UserValidator;
import com.example.labgui.repo.FriendshipDBRepo;
import com.example.labgui.repo.MessageDBRepo;
import com.example.labgui.repo.UserDBRepository;
import com.example.labgui.service.UsersService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//(new UserFile("src/userfile")
//new FriendshipFile("src/friendshipfile")
public class Main extends Application {
    private UsersService srv;
    public static void main(String [] args){
        launch(args);
    }
    public void start(Stage primaryStage)throws IOException {
        String url = "jdbc:postgresql://localhost:5432/social-network";
        FriendshipDBRepo friendshipDBRepo = new FriendshipDBRepo(url,"postgres","postgres");
        UserDBRepository userDbRepo = new UserDBRepository(url,"postgres","postgres");
        MessageDBRepo repoMessages = new MessageDBRepo(url,"postgres","postgres");
        //Test tester = new Test();
        //tester.runTests();
        //UsersService userService = new UsersService(new inMemoryRepository<>(),new FriendshipRepo<>(),new UserValidator());
        srv= new UsersService(userDbRepo,friendshipDBRepo, repoMessages, new UserValidator());
        initView(primaryStage);
        primaryStage.show();

    }
    private void initView(Stage primaryStage) throws IOException{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("views/newlogin.fxml"));
        Scene scene = new Scene(loader.load(),600,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Log in");

        LoginController loginController = loader.getController();
        loginController.setData(srv);
    }

}

