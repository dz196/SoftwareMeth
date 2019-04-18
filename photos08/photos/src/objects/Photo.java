package objects;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This class defines the photo object with relevant
 * characteristics
 * @author Denis Zou, David Zhang
 *
 */
public class Photo implements Serializable{


	private static final long serialVersionUID = 1L;
	public String caption;
	public Instant lastedited;
	public List<Tag> tags;
	public String filepath;
	
	public Photo (String caption, Instant lastedited, List<Tag> tags, String filepath) {
		this.caption = caption;
		this.lastedited = lastedited;
		this.tags = tags;
		this.filepath = filepath;
	}
	
	/**
	 * This method converts the photo tags into 
	 * one serialized string
	 * @return
	 */
	public String tagtoString() {
		StringBuilder sb = new StringBuilder();
		for (Tag tag: tags) {
			String tmpstr = tag.key + "=" + tag.value + ",";
			sb.append(tmpstr);
		}
		
		String res = sb.toString();
		//Get rid of comma at the end
		try {
		res = res.substring(0, res.length() - 1);
		}catch(Exception e) {
			
		}
		
		return res;
	}
	
	/**
	 * This method retrieves the most recent modification 
	 * date
	 * @return
	 */
	public String dateLastEdited() {
		
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Locale.US )
			                     .withZone( ZoneId.systemDefault() );
				
		return formatter.format(lastedited);
		
	}
	
	/**
	 * This method takes a serialized string of tags
	 * and creates tags for a photo.
	 * @param tagstr
	 */
	public void strtoTag(String tagstr) {
		List<Tag> tmpTags = new ArrayList<Tag>();
		
		try {
			String[] tagvalarr = tagstr.split(",");
			for (String tagval: tagvalarr) {
				String [] tmparr = tagval.split("=");
				String tag = tmparr[0];
				String value = tmparr[1];
				
				tmpTags.add(new Tag(tag, value));
			}
			this.tags = tmpTags;
		} 
		catch (Exception e) {
			System.out.println("Photo Class: strtoTag error");
		}
	}
}
