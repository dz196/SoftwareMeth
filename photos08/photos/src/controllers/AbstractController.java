package controllers;

/**
 * This is the abstract class for all screen controllers
 * @author Denis Zou, David Zhang
 *
 */
public abstract class AbstractController {
	
	ScreenController screenController;
	
	/**
	 * This method sets the source screen which allows 
	 * controllers to transition to and reference other
	 * screens.
	 * @param screenController
	 */
	abstract void setScreenController(ScreenController screenController);
	
	/**
	 * This method initializes a new screen and is called
	 * every time a screen needs to be refreshed.
	 */
	abstract void reload();
}
