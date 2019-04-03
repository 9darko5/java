package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import Controllers.AdminOptionsController;
import java.io.File;
import javafx.application.Application;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;


public class Main extends Application {
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try
        {
            Parent root=FXMLLoader.load(getClass().getResource("/View/AdminLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Prijava");
            primaryStage.getIcons().add(new Image("file:garaza.jpg"));
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        }
        catch(Exception e)
        {
            Logger.getLogger(AdminOptionsController.class.getName()).log(Level.WARNING, null, e);
            e.printStackTrace();
        }
    }
    
}
