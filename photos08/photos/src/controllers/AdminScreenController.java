package controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import objects.Album;
import objects.Photo;
import objects.User;

/**
 * This is the controller for the Admin screen
 * @author Denis Zou, David Zhang
 *
 */
public class AdminScreenController extends AbstractController{
	
	ScreenController screenController;
	private ObservableList<String> observableList;
	@FXML ListView<String> userList = new ListView<String>();
	@FXML TextField createUserNameField;
	@FXML Text adminText;
	

	@Override
	void setScreenController(ScreenController screenController) {
		// TODO Auto-generated method stub
		this.screenController = screenController;
	}
	
	@FXML
	//needs to remove user
	public void logOut() {
		//Main.setCurUser(null);
		adminText.setText("");
		Main.curApp.curUser = null;
		screenController.putScreen("LoginScreen");
	}
	
	/**
	 * This method creates a new user with a unique default username.
	 */
	@FXML
	public void createUser() {
		adminText.setText("");
		String newUser = "new user";
		boolean found = true;
		//
		try {
			for(User user : Main.curApp.users) {
				if(user.username.equals("new user")) {
					int count = 0;
					while(found) {
						final int tempCount = count;
						if(Main.curApp.users.stream().filter(use -> use.username.equals(String.format("new user %d", tempCount))).findFirst().isPresent()) {
							count++;
						}else found = false;
						//found = false;
					}
					
					newUser = String.format("new user %d", count);
				}
			}
			Main.curApp.users.add(new User(newUser, new ArrayList<Album>()));
			userList.getSelectionModel().select(Main.curApp.users.size() - 1);
			createUserNameField.setText(newUser);
			
			reload();
			Main.saveCurApp();
		}catch (Exception e) {
			System.out.println("problem with newUser()");
		}
	}
	
	/**
	 * This method deletes a user.
	 */
	public void deleteUser() {
		adminText.setText("");
		int selectedNum = userList.getSelectionModel().getSelectedIndex();
		
		try {
			if (selectedNum != -1) {
				Main.curApp.users.remove(selectedNum);
				adminText.setText("deleted");
				
				if (Main.curApp.users.size() > 0) {
					userList.getSelectionModel().select(0);
					
				}
			}
			Main.saveCurApp();
			reload();
		}catch (Exception e) {
			System.out.println("deleteUser error");
		}
		
	}
	
	/**
	 * This method saves name changes to a user.
	 */
	public void saveUser() {
		adminText.setText("");
		String saveUsername = createUserNameField.getText();
		//System.out.println(saveUsername);
		boolean found = false;
		int index = userList.getSelectionModel().getSelectedIndex();
		try {
			for(User user : Main.curApp.users) {
				if (user.username.equals(saveUsername)) {
					adminText.setText("error user exists");
					found = true;
					return;
				}
			}
			if(index != -1) {
				Main.curApp.users.get(index).username = saveUsername;
				reload();
				Main.saveCurApp();
			}
			

		} catch (Exception e) {
			System.out.println("error in saveUser");
		}
	}

	@Override
	public void reload() {
		List<String> users = Main.curApp.users.stream().map(o -> o.username).collect(Collectors.toList());
		observableList = FXCollections.observableArrayList(users);
		userList.setItems(observableList);
		// TODO Auto-generated method stub
		
	}
	
}
