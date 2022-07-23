package application;

import Utilities.Settings;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * This is a parent class for all Screen-type objects
 * used in the program. Includes a mandatory setLayout()
 * function for all subclasses.
 * 
 * @author Straight Outta CompSci
 */
abstract public class Screen extends Scene {
	private Pane root;
	
	/**
	 * Constructor. This creates a new scene object with
	 * a new pane and dimensions as specified in the settings.
	 */
	public Screen() {
		super(new Pane(), Settings.WIDTH, Settings.HEIGHT);
		root = (Pane)this.getRoot();
	}
	
	/**
	 * Returns the resident Pane object
	 * 
	 * @return root pane object
	 */
	public Pane getPane() {
		return this.root;
	}
	
	/**
	 * All subclasses must implement this method to set the layout
	 * for the new pane object created in 
	 * 
	 * @param pane: the pane to set layout for
	 */
	protected abstract void setLayout(Pane pane);
}
