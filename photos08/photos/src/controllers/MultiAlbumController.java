package controllers;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import objects.Album;
import objects.Photo;
import objects.Tag;

/**
 * This class contains the constructor for the 
 * album selection screen.
 * @author Denis Zou, David Zhang
 *
 */
public class MultiAlbumController extends AbstractController{
	
	ScreenController screencontroller;
	
	@FXML ListView<String> albumsList = new ListView<String>();
	@FXML TextField albumField;
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	@FXML TextField tagsSearchField;
	

	ObservableList<String> observableList;
	
	@Override
	void setScreenController(ScreenController screenController) {
		// TODO Auto-generated method stub
		this.screencontroller = screenController;
	}
	
	/**
	 * This method creates a new empty album
	 */
	public void createAlbum() {
		String newAlbum = "new album";
		boolean found = true;
		try {
			for(Album album : Main.curApp.curUser.albums) {
				if(album.title.equals("new album")) {
					int count = 0;
					while (found) {
						final int tmpCount = count;
						if (Main.curApp.curUser.albums.stream().filter(o -> o.title.equals(String.format("new album %d", tmpCount))).findFirst().isPresent()) {
							count++;
						}
						else {
							found = false;
						}
					}
					newAlbum = String.format("new album %d", count);
				}
			}
			Main.curApp.curUser.albums.add(new Album(newAlbum, new ArrayList<Photo>()));
			reload();
	    	
	    	albumsList.getSelectionModel().select(Main.curApp.curUser.albums.size() - 1);
	    	albumField.setText(newAlbum);
	    	
	    	Main.saveCurApp();
		}catch (Exception e) {
			
			
		}
	}
	
	/**
	 * This method deletes a selected album
	 * @throws IOException
	 */
	public void removeAlbum() throws IOException {
		int ind = albumsList.getSelectionModel().getSelectedIndex();
		
		if (ind != -1 ) {
			Main.curApp.curUser.albums.remove(ind);
			Main.saveCurApp();
			reload();
			if (Main.curApp.curUser.albums.size() > 0) {
				albumsList.getSelectionModel().select(0);
				albumField.setText(Main.curApp.curUser.albums.get(0).title);
			}
			else {
				albumField.setText("");
			}
		}
	}
	
	/**
	 * This method saves name changes to an album
	 * @throws IOException
	 */
	public void saveAlbum() throws IOException {
		String newAlbum = albumField.getText();
		
		for (Album album : Main.curApp.curUser.albums) {
			if (album.title.equals(newAlbum)) {
				//SET ERROR TEXTFIELD
				return;
			}
		}
		int ind = albumsList.getSelectionModel().getSelectedIndex();
		
		if (ind != -1) {
			Main.curApp.curUser.albums.get(ind).title = newAlbum;
			reload();
			Main.saveCurApp();
		}
	}
	
	/**
	 * This album opens the single album view 
	 * screen with the selected album.
	 */
	public void openAlbum() {
		
		if (albumsList.getSelectionModel().getSelectedIndex() != -1) {
    		screencontroller.putScreen("SingleAlbumScreen");
    	}
	}
	
	/**
	 * This method searches all albums for photos matching
	 * the search parameters and creates a photo list
	 * containing all the relevant photos
	 */
	public void search() {
		Instant start;
    	Instant end;
    	
    	if (startDate.getValue() == null) {
    		start = Instant.now().minusSeconds(999999999);
    	} else {
    		start = Instant.ofEpochSecond(startDate.getValue().toEpochDay() * 60 * 60 * 24);
    	}
    	
    	if (endDate.getValue() == null) {
    		end = Instant.now();
    	} else {
    		end = Instant.ofEpochSecond((endDate.getValue().toEpochDay() + 1) * 60 * 60 * 24);
    	}
    	
		List<Photo> photoMasterList = new ArrayList<Photo>();
		for(Album album : Main.curApp.curUser.albums) {
			for(Photo photo : album.photos) {
				photoMasterList.add(photo);
			}
		}
		List<Photo> results = new ArrayList<Photo>();

		String tags = tagsSearchField.getText();
		
		
		
		if (tags.equals("")) {
			for (Photo photo: photoMasterList) {
				if (!results.contains(photo)) {
					if (photo.lastedited.isAfter(start) && photo.lastedited.isBefore((end))) {
						results.add(photo);
					}
				}
				
			}
		}
		else {
			Photo tempPhoto = new Photo("", null, new ArrayList<Tag>(),"");
			tempPhoto.strtoTag(tags);
			
			for(Photo photo : photoMasterList) {
				for(Tag tag : tempPhoto.tags) {
					for(Tag tag1 : photo.tags) {
						if(tag1.equals(tag)) {
							if(!results.contains(photo)) {
								if (photo.lastedited.isAfter(start) && photo.lastedited.isBefore((end))) {
									results.add(photo);
								}
							}	
						}
					}
				}
			}
		}
			
		Main.curApp.searchResults = results;
		for (Photo photo : Main.curApp.searchResults) {
			System.out.println(photo.caption);
		}
		screencontroller.putScreen("SearchResultsScreen");
		return;
	}

	
	/**
	 * This method populates text fields when an album is 
	 * selected in the list
	 */
	public void select() {
		int ind = albumsList.getSelectionModel().getSelectedIndex();
		if (ind != -1) {
			Album targetAlbum = Main.curApp.curUser.albums.get(ind);
			Main.curApp.curAlbum = targetAlbum;
			albumField.setText(targetAlbum.title);
		}
	}
	
	/**
	 * This method logs the current user out and returns to the 
	 * login screen.
	 * @throws IOException 
	 */
	public void logOut() throws IOException {
		Main.curApp.curUser = null;
		Main.saveCurApp();
		screencontroller.putScreen("LoginScreen");
	}
	@Override
	void reload() {
		List<String> albumListDescriptions = new ArrayList<String>();
		
		for (Album album : Main.curApp.curUser.albums) {
			String description = String.format("%s\n%d photos", album.title, album.photos.size());
			
			albumListDescriptions.add(description);
		}
		this.observableList = FXCollections.observableArrayList(albumListDescriptions);
		this.albumsList.setItems(this.observableList);
	}

}
