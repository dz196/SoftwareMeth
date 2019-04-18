package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import objects.User;
/**
 * This class handles the login screen.
 * @author Denis Zou, David Zhang
 *
 */
public class LoginController extends AbstractController{

	ScreenController screenController;
	@FXML TextField usernameField;
	@FXML Text usernameText;
	
	@Override
	void setScreenController(ScreenController screenController) {
		// TODO Auto-generated method stub
		this.screenController = screenController;
		
	}

	@Override
	void reload() {
		usernameField.setText("");
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method takes the username input and checks if it is valid.
	 */
	@FXML
	private void login() {
		
		String username = usernameField.getText().trim();
		
		if (username.equals("")) {
			usernameText.setText("no input");
			return;	
		}
		
		if (username.equals("admin")) {
			screenController.putScreen("AdminScreen");
			return;
		}
		
		for(User user : Main.curApp.users) {
			if(user.username.equals(username)) {
				Main.curApp.curUser = user;
				screenController.putScreen("MultiAlbumScreen");
				return;
			}
		}
		usernameText.setText("user does not exist");
		
	}

}
