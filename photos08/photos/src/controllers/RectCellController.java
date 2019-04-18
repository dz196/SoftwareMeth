package controllers;


import java.util.List;
import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import objects.Photo;

/**
 * This class is the controller for a single item in
 * the single album view/search result screen photo 
 * list
 * @author Denis Zou, David Zhang
 *
 */
public class RectCellController extends ListCell<String>{
	
	List<Photo> photoList;
	
	/**
	 * This constructor controls which list of photos the list items are
	 * generated from.
	 * @param photoList ; Gets either searchResults or the photos inside a selected Album
	 */
	public RectCellController(List<Photo> photoList) {
		this.photoList = photoList;
	}
	
	/**
	 * This method populates an cell in the listView
	 */
	public void updateItem(String filePath, boolean noArg) {
		super.updateItem(filePath, noArg);
		
		HBox cell = new HBox();
		cell.setPrefSize(270, 50);
		cell.setAlignment(Pos.BOTTOM_LEFT);
		
		if(filePath == null) {
			setGraphic(null);
			
		}else {
			
			String title = "";
			for (Photo photo : photoList) {
				if (photo.filepath.equals(filePath)) {
					title = photo.caption;
				}
			}
			
			title = "   " + title;
			boolean possible = true;
			
			if(possible) {
				Image thumbnail = new Image(filePath, 50, 50, true, true);
				ImageView tnView = new ImageView(thumbnail);
				cell.getChildren().add(tnView);
				Text caption = new Text(title);
				cell.getChildren().add(caption);
				setGraphic(cell);
			}
		}
	}
}
