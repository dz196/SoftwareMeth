package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines album objects with 
 * relevant characteristics
 * @author Denis Zou, David Zhang
 *
 */
public class Album implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public List<Photo> photos;
	
	public Album(String title, List<Photo> searchResults) {
		this.title = title;
		this.photos = searchResults;
	}
	
}
