package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

import controllers.ScreenController;
import javafx.application.Application;
import javafx.stage.Stage;
import objects.Album;
import objects.CurApplication;
import objects.Photo;
import objects.Tag;
import objects.User;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Initializes the login page and sets up all screens.
 * Class also houses the global state used to transition 
 * between screens.
 * @author Denis Zou, David Zhang
 *
 */
public class Main extends Application {
	
	public static CurApplication curApp;
	static String statepath = "src/state.obj";
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		//startingState();
		
		ScreenController screenController = new ScreenController();
		screenController.loadScreen("AdminScreen", "/view/admin.fxml");
		screenController.loadScreen("LoginScreen", "/view/login.fxml");
		screenController.loadScreen("MultiAlbumScreen", "/view/multialbum.fxml");
		screenController.loadScreen("SearchResultsScreen", "/view/searchResults.fxml");
		screenController.loadScreen("SingleAlbumScreen", "/view/singlealbum.fxml");

		
		screenController.putScreen("LoginScreen");
		
		loadApp();
		
		Group root = new Group();
		root.getChildren().addAll(screenController);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		

	}
	@SuppressWarnings("unchecked")
	public void loadApp() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(statepath));
		curApp = new CurApplication((List<User>) ois.readObject());
		ois.close();
	}
	/*
	public void setCurUser(User user) {
		curApp.curUser = user;
	}*/
	
	public void startingState() throws IOException {
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(statepath));
		
		Path path1 = Paths.get("src/stock/1.jpg");
		FileTime time1 = Files.getLastModifiedTime(path1);
		
		Path path2 = Paths.get("src/stock/2.jpg");
		FileTime time2 = Files.getLastModifiedTime(path2);
		
		Path path3 = Paths.get("src/stock/3.jpg");
		FileTime time3 = Files.getLastModifiedTime(path3);
		
		Path path4 = Paths.get("src/stock/4.jpg");
		FileTime time4 = Files.getLastModifiedTime(path4);
		
		Path path5 = Paths.get("src/stock/5.jpg");
		FileTime time5 = Files.getLastModifiedTime(path5);
		
		List<Tag> tags1 = new ArrayList<Tag>();
		List<Tag> tags2 = new ArrayList<Tag>();
		List<Tag> tags3 = new ArrayList<Tag>();
		List<Tag> tags4 = new ArrayList<Tag>();
		List<Tag> tags5 = new ArrayList<Tag>();
		
		Tag tag1 = new Tag("place", "Tokyo");
		Tag tag1_2 = new Tag("place", "Japan");
		tags1.add(tag1);
		tags1.add(tag1_2);
		
		Tag tag2 = new Tag("place", "Osaka");
		Tag tag2_2 = new Tag("place", "Japan");
		tags2.add(tag2);
		tags2.add(tag2_2);
		
		Tag tag3 = new Tag("place", "Kyoto");
		Tag tag3_2 = new Tag("place", "Japan");
		tags3.add(tag3);
		tags3.add(tag3_2);
		
		Tag tag4 = new Tag("color", "blue");
		tags4.add(tag4);
		
		Tag tag5 = new Tag("time", "day");
		Tag tag5_2 = new Tag("color", "green");
		tags5.add(tag5);
		tags5.add(tag5_2);
		
		Photo photo1 = new Photo("1", time1.toInstant(), tags1, "file:src/stock/1.jpg");
		Photo photo2 = new Photo("2", time2.toInstant(), tags2,"file:src/stock/2.jpg");
		Photo photo3 = new Photo("3", time3.toInstant(), tags3,"file:src/stock/3.jpg");
		Photo photo4 = new Photo("4", time4.toInstant(), tags4, "file:src/stock/4.jpg");
		Photo photo5 = new Photo("5", time5.toInstant(), tags5, "file:src/stock/5.jpg");
		
		List<Photo> photos = new ArrayList<Photo>();
		
		photos.add(photo1);
		photos.add(photo2);
		photos.add(photo3);
		photos.add(photo4);
		photos.add(photo5);
		
		Album album = new Album("initial", photos);
		
		List<Album> albums = new ArrayList<Album>();
		albums.add(album);
		
		User user = new User("stock", albums);
		
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		oos.writeObject(users);
		
		oos.close();
	}
	
	
	public static User getCurUser() {
		return curApp.curUser;
	}
	
	//unnecessary method
	public static List<User> getUsers(){
		return curApp.users;
	}
	
	public static void saveCurApp() throws IOException {
		curApp.save(statepath);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
