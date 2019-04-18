package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import objects.Album;
import objects.Photo;
import objects.User;

/**
 * This class is the controller for the search result screen
 * @author Denis Zou, David Zhang
 *
 */
public class SearchController extends AbstractController {
	
	ScreenController screencontroller;
	
	private ObservableList<String> observableList;
	//@FXML TextField Caption;
	@FXML ImageView image;
	@FXML ListView<String> photos = new ListView<String>();
	@FXML Text createText;

	@Override
	void setScreenController(ScreenController screenController) {
		// TODO Auto-generated method stub
		this.screencontroller = screenController;
	}
	
	/**
	 * This method populates the screen and creates the listView cells
	 * with thumbnails
	 */
	public void initialize() {
		this.photos.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {

				return new RectCellController(Main.curApp.searchResults);

			}

		});
	}
	
	/**
	 * This method returns to the album selection screen
	 */
	public void back() {
		createText.setText("");
		screencontroller.putScreen("MultiAlbumScreen");
	}
	
	/**
	 * This method populates text fields when a new photo is 
	 * selected/being viewed
	 */
	public void select() {
		createText.setText("");
		int ind = this.photos.getSelectionModel().getSelectedIndex();
		
		if (ind != -1) {
			photos.getSelectionModel().select(ind);
			Photo photo = Main.curApp.searchResults.get(ind);
			image.setImage(new Image(photo.filepath));
			
		}
	}
	
	/**
	 * This method switches the photo in the image view to
	 * the next in the list.
	 */
	public void next() {
		createText.setText("");
		if (Main.curApp.curAlbum.photos.size() > 0) {			
			int ind = this.photos.getSelectionModel().getSelectedIndex();
			int nextInd = (ind + 1) % Main.curApp.curAlbum.photos.size();
			
			photos.getSelectionModel().select(nextInd);
			Photo photo = Main.curApp.curAlbum.photos.get(nextInd);
			image.setImage(new Image(photo.filepath));
			
		}		
	}
	
	/**
	 * This method switches the image view to the previous
	 * image in the list.
	 */
	public void previous() {
		createText.setText("");
		if (Main.curApp.curAlbum.photos.size() > 0) {			
			int ind = this.photos.getSelectionModel().getSelectedIndex();
			int size = Main.curApp.curAlbum.photos.size();
			int prevInd = (ind - 1 + size) % size;
			
			photos.getSelectionModel().select(prevInd);
			Photo photo = Main.curApp.curAlbum.photos.get(prevInd);
			image.setImage(new Image(photo.filepath));
			
		}		
	}
	
	/**
	 * This method creates a new album from search results.
	 */
	public void createAlbum() {
		
		String albTitle = "default";
		boolean found = true;
		
			for(Album album : Main.curApp.curUser.albums){
				if(album.title.equals("default")) {
					int count = 0;
					while(found) {
						final int tempCount = count;
						if(Main.curApp.curUser.albums.stream().filter(alb -> alb.title.equals(String.format("default %d", tempCount))).findFirst().isPresent()) {
							count++;
						}else found = false;
						//found = false;
					}
					
					albTitle = String.format("default %d", count);
				}
			}
			Main.curApp.curUser.albums.add(new Album(albTitle, Main.curApp.searchResults));
			createText.setText("new album created:" + albTitle);
	}
	
	@Override
	void reload() {
		// TODO Auto-generated method stub
		image.setImage(null);
		List<String> paths = new ArrayList<String>();
		for (Photo photo : Main.curApp.searchResults) {
			paths.add(photo.filepath);
			System.out.println(photo.caption);
		}

		this.observableList = FXCollections.observableArrayList(paths);
		this.photos.setItems(this.observableList);
		
		if (paths.size() > 0){
			photos.getSelectionModel().select(0);
			image.setImage(new Image(paths.get(0)));
		}
		
		int ind = 0;
		if (Main.curApp.searchResults.size() > 0) {
			photos.getSelectionModel().select(ind);
			Photo photo = Main.curApp.searchResults.get(ind);
			image.setImage(new Image(photo.filepath));
			
		}
	}
	
}
