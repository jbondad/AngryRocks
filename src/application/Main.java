/**
 * This program is an Angry Birds ripoff game built using Java and
 * using the JBox2D game engine. To play, run this file as a .class
 * file.
 * 
 * Winter 2018
 * CPSC233 L03
 * 
 * @author Straight Outta CompSci
 */

package application;

import Utilities.Settings;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry-point into the program. Creates a stage and links
 * it to the global stage so that all parts of the program can access
 * it. Defaults the starting screen to MenuScreen.
 */
public class Main extends Application {
	
	/**
	 * Initialization of the JavaFX stage and defaults it to
	 * the starting menu screen.
	 * @param primaryStage: Stage object initiated by JavaFX platform
	 */
	@Override
	public void start(Stage primaryStage) {
		// Link local stage to the common Stage object in Utils
		primaryStage = Settings.stage;
		
		// Settings for stage
		primaryStage.setResizable(false);
		primaryStage.setTitle("Angry Birds Ripoff");
		
		// Start the program by initializing scene to a menu
		primaryStage.setScene(new MenuScreen());
		primaryStage.show();
	}
	
	/** 
	 * Starts the program
	 * @param args: console arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
