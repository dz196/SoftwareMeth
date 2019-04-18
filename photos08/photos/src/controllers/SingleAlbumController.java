package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import objects.Album;
import objects.Photo;
import objects.Tag;

/**
 * This class is the controller for the Single Album screen.
 * @author Denis Zou, David Zhang
 *
 */
public class SingleAlbumController extends AbstractController{
	ScreenController screencontroller;
	@FXML ListView<String> photos = new ListView<String>();
	@FXML TextField photoCaptionField;
	@FXML TextField photoDateField;
	@FXML TextField photoTagsField;
	@FXML ImageView imageView;
	
	ObservableList<String> observableList;
	
	@Override
	void setScreenController(ScreenController screenController) {
		// TODO Auto-generated method stub
		this.screencontroller = screenController;
	}
	
	@Override
	void reload() {
		// TODO Auto-generated method stub
		imageView.setImage(null);

		List<String> paths = new ArrayList<String>();
		for (Photo photo : Main.curApp.curAlbum.photos) {
			paths.add(photo.filepath);
		}
		
		this.observableList = FXCollections.observableArrayList(paths);
		this.photos.setItems(this.observableList);
		
		if (paths.size() > 0){
			photos.getSelectionModel().select(0);
			imageView.setImage(new Image(paths.get(0)));
			photoCaptionField.setText(Main.curApp.curAlbum.photos.get(0).caption);
			photoDateField.setText(Main.curApp.curAlbum.photos.get(0).lastedited.toString());
			photoTagsField.setText(Main.curApp.curAlbum.photos.get(0).tagtoString());
		}
		else {
			photoCaptionField.setText("");
			photoDateField.setText("");
			photoTagsField.setText("");
		}
		int ind = 0;
		if (Main.curApp.curAlbum.photos.size() > 0) {
			photos.getSelectionModel().select(ind);
			Photo photo = Main.curApp.curAlbum.photos.get(ind);
			imageView.setImage(new Image(photo.filepath));
			
			photoCaptionField.setText(photo.caption);
			photoTagsField.setText(photo.tagtoString());
			photoDateField.setText(photo.dateLastEdited());
		}
	}
	
	/**
	 * This method updates the image view and text fields
	 * when a new photo is selected
	 */
	public void select() {
		int ind = this.photos.getSelectionModel().getSelectedIndex();
		
		if (ind != -1) {
			photos.getSelectionModel().select(ind);
			Photo photo = Main.curApp.curAlbum.photos.get(ind);
			imageView.setImage(new Image(photo.filepath));
			
			photoCaptionField.setText(photo.caption);
			photoTagsField.setText(photo.tagtoString());
			photoDateField.setText(photo.dateLastEdited());
		}
	}
	
	/**
	 * This method switches the image view to the next
	 * photo in the list.
	 */
	public void next() {
		if (Main.curApp.curAlbum.photos.size() > 0) {			
			int ind = this.photos.getSelectionModel().getSelectedIndex();
			int nextInd = (ind + 1) % Main.curApp.curAlbum.photos.size();
			
			photos.getSelectionModel().select(nextInd);
			Photo photo = Main.curApp.curAlbum.photos.get(nextInd);
			imageView.setImage(new Image(photo.filepath));
			
			photoCaptionField.setText(photo.caption);
			photoTagsField.setText(photo.tagtoString());
			photoDateField.setText(photo.dateLastEdited());
		}		
	}
	
	/**
	 * This method switches the image view to the previous photo
	 * in the list.
	 */
	public void previous() {
		if (Main.curApp.curAlbum.photos.size() > 0) {			
			int ind = this.photos.getSelectionModel().getSelectedIndex();
			int size = Main.curApp.curAlbum.photos.size();
			int prevInd = (ind - 1 + size) % size;
			
			photos.getSelectionModel().select(prevInd);
			Photo photo = Main.curApp.curAlbum.photos.get(prevInd);
			imageView.setImage(new Image(photo.filepath));
			
			photoCaptionField.setText(photo.caption);
			photoTagsField.setText(photo.tagtoString());
			photoDateField.setText(photo.dateLastEdited());
		}		
	}
	
	/**
	 * This method adds a new photo to the album
	 * @throws IOException
	 */
	public void addPhoto() throws IOException {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a photo to add");
		
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.tiff", "*.gif"));
		
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null) {	

			String path = selectedFile.getAbsolutePath();
			Instant lastModified = Files.getLastModifiedTime(Paths.get(path)).toInstant();
			
			Main.curApp.curAlbum.photos.add(
					new Photo("", lastModified, new ArrayList<Tag>(),selectedFile.toURI().toURL().toExternalForm()));
			
			Main.saveCurApp();
			reload();
			
			int ind = Main.curApp.curAlbum.photos.size() - 1;
			
			if (ind != -1) {
				photos.getSelectionModel().select(ind);
				Photo photo = Main.curApp.curAlbum.photos.get(ind);
				imageView.setImage(new Image(photo.filepath));
				
				photoCaptionField.setText(photo.caption);
				photoTagsField.setText(photo.tagtoString());
				photoDateField.setText(photo.dateLastEdited());
			}
			
		}
	}
	
	/**
	 * This method removes a photo from the album.
	 * @throws IOException
	 */
	public void removePhoto() throws IOException {
		int ind = photos.getSelectionModel().getSelectedIndex();
		
		if (ind != -1) {
			Main.curApp.curAlbum.photos.remove(ind);
			Main.saveCurApp();
			reload();
		}
	}
	
	/** 
	 * This method saves any changes made to a photo in the album
	 * @throws IOException 
	 */
	public void save() throws IOException {
		String caption = photoCaptionField.getText();
		String tags = photoTagsField.getText();
		
		int ind = photos.getSelectionModel().getSelectedIndex();
		
		if (ind != -1) {
			Photo photo = Main.curApp.curAlbum.photos.get(ind);
			photo.caption = caption;
			try {
				photo.strtoTag(tags);
			} catch (Exception e) {
				System.out.println("Error saving tags. Formatting incorrect");
			}
		}
		Main.saveCurApp();
	}
	
	/**
	 * This method copies a selected photo to another album
	 * of the users choice.
	 * @throws IOException
	 */
	public void copyPhoto() throws IOException {
		int ind = photos.getSelectionModel().getSelectedIndex();
		if (ind != -1) {
			Photo photo = Main.curApp.curAlbum.photos.get(ind);
			
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("CopyPhoto");
			dialog.setContentText("Enter a valid album name:");
			
			Optional<String> userInput = dialog.showAndWait();
			
			if (userInput.isPresent()) {
				for (Album album : Main.curApp.curUser.albums) {
					if (album.title.equals(userInput.get())) {
						album.photos.add(photo);
						break;
					}
				}
			}
			reload();
			Main.saveCurApp();
		}
	}
	
	/**
	 * This method moves a photo to a new album of the users choice
	 * @throws IOException
	 */
	public void movePhoto() throws IOException {
		int ind = photos.getSelectionModel().getSelectedIndex();
		if (ind != -1) {
			Photo photo = Main.curApp.curAlbum.photos.get(ind);
			
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("CopyPhoto");
			dialog.setContentText("Enter a valid album name:");
			
			Optional<String> userInput = dialog.showAndWait();
			
			if (userInput.isPresent()) {
				for (Album album : Main.curApp.curUser.albums) {
					if (album.title.equals(userInput.get())) {
						album.photos.add(photo);
						Main.curApp.curAlbum.photos.remove(ind);
						break;
					}
				}
			}
			
			reload();
			Main.saveCurApp();
		}
	}
	
	/**
	 * This method returns to the album selection screen
	 */
	public void back() {
		screencontroller.putScreen("MultiAlbumScreen");
	}
	
	/**
	 * This populates the list of photos and the image view slides
	 */
	public void initialize() {
		this.photos.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {

				return new RectCellController(Main.curApp.curAlbum.photos);

			}

		});
	}

}
