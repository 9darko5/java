package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminLoginController implements Initializable{
	
	
	@FXML
    private Button back;
	
	@FXML
	private Button login;
	
	@FXML 
	private TextField username;
	
	@FXML
	private PasswordField password;

	
	@FXML 
	public void backAction(ActionEvent e) throws IOException
	{
		back.getScene().getWindow().hide();
		
		Stage s=new Stage();
                s.setTitle("Prijava");
                s.getIcons().add(new Image("file:garaza.jpg"));
		Parent root=FXMLLoader.load(getClass().getResource("/View/AdminOptionsController.fxml"));
		Scene scene=new Scene(root);
		s.setScene(scene);
		s.show();
		s.setResizable(false);
	}
	
	//@SuppressWarnings("static-access")
	@FXML
	public void loginAction(ActionEvent e) throws IOException
	{
            
            if(username.getText().isEmpty() || password.getText().isEmpty())
            {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setHeaderText(null);
                alertError.setContentText("Polja su prazna!");
                alertError.show();
                return;
            }
            String user = username.getText();
            String pass = password.getText();

            if(checkAuthentication(user,pass)){
                try 
                {
                    login.getScene().getWindow().hide();
                    Stage s=new Stage();
                    s.setTitle("Administrator");
                    s.getIcons().add(new Image("file:garaza.jpg"));
                    FXMLLoader loader=new FXMLLoader();
                    Parent root=loader.load(getClass().getResource("/View/AdminOptions.fxml"));
                    Scene scene=new Scene(root);
                    s.setScene(scene);
                    s.show();
                    s.setResizable(false);
            } catch (Exception ex) {
                Logger.getLogger(AdminLoginController.class.getName()).log(Level.WARNING, null, e);
                ex.printStackTrace();
            }
            }
            else{
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setHeaderText(null);
                alertError.setContentText("Korisnicko ime ili lozinka nisu ispravni!");
                alertError.show();
                return;
            }
		
			
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
        private boolean checkAuthentication(String username, String password) {
        try {
            String passwordHash = hashSHA256(password);

            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line = br.readLine();
            while (line != null) {
                if (line.split("#")[0].equals(username) && line.split("#")[1].equals(passwordHash)) {
                    br.close();
                    return true;
                } else {
                    line = br.readLine();
                }
            }
            br.close();
            return false;
        } catch (Exception ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }

        return false;
    }
        private String hashSHA256(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
