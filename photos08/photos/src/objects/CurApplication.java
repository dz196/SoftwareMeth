package objects;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * This class saves the user data from a session
 * and outputs to a state file.
 * @author Denis Zou, David Zhang
 *
 */
public class CurApplication{
	
	public List<User> users;
	public User curUser;
	public Album curAlbum;
	public List<Photo> searchResults;
	
	public CurApplication(List<User> users) {
		this.users = users;
	}
	
	public void save(String path) throws IOException{
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(this.users);
		oos.close();
	}
}
