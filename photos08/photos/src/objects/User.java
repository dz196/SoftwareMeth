package objects;

import java.io.Serializable;
import java.util.List;

/**
 * This class defines the User object with relevant 
 * characteristics
 * @author Denis Zou, David Zhang
 *
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String username;
	public List<Album> albums;
	
	public User(String username, List<Album> albums) {
		this.username = username;
		this.albums = albums;
	}
	
	//unnecessary method
	public boolean is(String username) {
		return this.username.equals(username);
	}
	
}
