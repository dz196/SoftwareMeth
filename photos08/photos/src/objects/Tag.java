package objects;

import java.io.Serializable;

/**
 * This class defines a tag with relevant characteristics.
 * @author Denis Zou, David Zhang
 *
 */
public class Tag implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String key;
	public String value;
	
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public boolean equals(Tag tag) {
		
		return (tag.key.equals(key) && tag.value.equals(value));
	}
}
