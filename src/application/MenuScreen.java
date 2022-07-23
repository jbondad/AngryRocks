package application;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

import Utilities.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is the menu layout, and has options for starting the game,
 * quitting or resetting the level log. Starting the game from this screen
 * creates a new LevelSelectScreen to place on the stage.
 * 
 * @author Straight Outta CompSci
 */
public class MenuScreen extends Screen {

	public MenuScreen() {
		super();
		setLayout(super.getPane());
	}
	
	/**
	 * Puts a prespecified layout on a pane object
	 * @param pane: the pane to set the layout for
	 */
	@Override
	protected void setLayout(Pane pane) {
		//a wallpaper image for MenuScreen of type ImageView that will later be added to the scene along w/ buttons/labels.
		ImageView viewWallpaper = Settings.setImageView(Settings.menu_bgrdImg,0,0,Settings.WIDTH,Settings.HEIGHT);
		//coordinates/colour/font of label
		Label welcomeLabel =Settings.setLabel("Welcome to Angry Birds Ripoff",Settings.WIDTH / 2 - 310,100,
							new Font("Yuppy SC",45),Color.CHOCOLATE);
		
		Button playBtn = Settings.setButton("Play",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 - 125,
						 Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Settings.stage.setScene(new LevelSelectScreen());
			}
		});
		
		Button quitBtn = Settings.setButton("Quit",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 - 50,Settings.MenuBtns_W,
				         Settings.MenuBtns_H,Settings.allButtons_style);
		quitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		
		Button resetBtn = Settings.setButton("Reset Progress",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 +25,
					     Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				resetProgress();
				final Stage dialog = new Stage();
				// this prevents interacting with the other windows of the game until the dialog is closed
                dialog.initModality(Modality.APPLICATION_MODAL); 
                dialog.initOwner(Settings.stage);
                VBox dialogVbox = new VBox(8);
                Label msg=Settings.setLabel("Progress reset is successfully completed." ,40
                		,40,new Font("Batang",20),Color.BLACK);
                dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                dialogVbox.getChildren().addAll(msg);
                Scene dialogScene = new Scene(dialogVbox, 450, 40);
                dialog.setScene(dialogScene);
                dialog.show();
			} 
		});
		
		pane.getChildren().addAll(viewWallpaper, welcomeLabel, playBtn, quitBtn, resetBtn);		
	}
	
	
	/**
	 * Resets saved level progress to level 1.
	 */
	public void resetProgress() {
		try {
			File file = new File("resources/level.txt");
			// false allows overwrite
			PrintWriter updater = new PrintWriter(file);
			updater.write("1");
			updater.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: level log not found");
		} catch (NoSuchElementException e) {
			System.err.println("ERROR: level log has incorrect value");
		} catch (IOException e) {
			System.err.println("ERROR: IO exception");
		}
	}
}
