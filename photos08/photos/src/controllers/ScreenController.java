package controllers;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/**
 * This class manages all screens and allows for transitioning
 * @author Denis Zou, David Zhang
 *
 */
public class ScreenController extends StackPane{
	
	private HashMap<String, Node> screenMap = new HashMap<String, Node>();
	private HashMap<String, AbstractController> controllerMap = new HashMap<String, AbstractController>();
	private Scene main;
	
	/*public ScreenController(Scene main) {
		this.main = main;
	}*/
	
	/**
	 * This method adds a new screen to the data structure
	 * containing all screens
	 * @param name
	 * @param node
	 */
	public void addScreen(String name, Node node){
		this.screenMap.put(name, node);
	}
	
	/**
	 * This method removes a screen from the hashmap
	 */
	public void removeScreen(String name) {
		this.screenMap.remove(name);
	}
	
	/**
	 * This method creates a container for the Controller abstract class
	 * @param name
	 * @param controller
	 */
	public void addController(String name, AbstractController controller) {
		this.controllerMap.put(name, controller);
	}
	
	public void removeController(String name, String path) {
		this.controllerMap.remove(name);
	}
	
	/**
	 * This method sets up a screen to be opened.
	 * @param name
	 * @param path
	 * @throws IOException
	 */
	public void loadScreen(String name, String path) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent screen = (Parent) loader.load();
        AbstractController screenController = ((AbstractController) loader.getController());
        screenController.setScreenController(this);
        addScreen(name, screen);
        addController(name, screenController);
	}
	
	/**
	 * This method sets the currently open screen.
	 * @param name
	 */
	public void putScreen(String name) {
		try {
			getChildren().remove(0);
		} catch (Exception E) {
		
		}
		getChildren().add(0, screenMap.get(name));
		controllerMap.get(name).reload();
	}
	public void activate(String name) {
		main.setRoot((Parent) screenMap.get(name));
	}
}
