package application;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Utilities.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



/**
 * This screen allows users to select the level they want 
 * to play. Levels made available to the player are based
 * on an exteral level.txt file that contains the max levels
 * made available to the player.
 * 
 * @author Straight Outta CompSci
 */
public class LevelSelectScreen extends Screen {
	private int maxLevel = 0;

	public LevelSelectScreen() {
		super();
		setLayout(super.getPane());
	}
	
	/**
	 * Loads the maximum level currently available to the player
	 * from information stored in a text file. 
	 */
	private void loadLevel() {
		try {
			File file = new File("resources/level.txt");
			Scanner sc = new Scanner(file);
			// loadLevel
			int fileLevel = sc.nextInt();
			if (fileLevel > 3) {
				this.maxLevel = 3;
			} else {
				this.maxLevel = fileLevel;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: level log not found");
		} catch (NoSuchElementException e) {
			System.err.println("ERROR: level log has incorrect value");
		}
	}
	/**
	 * Puts a prespecified layout on a pane object
	 * 
	 * @param pane: the pane to set the layout for
	 */
	@Override
	protected void setLayout(Pane pane) {
		 // x & y coordinate for level1 level1_img
		double level1_imgX=150;  
		double level1_imgY=250;
		// x-axis distance between level's images
		double distance=400;    		
		// standard width & height for level's image
		final double  level_imgH=200;
		final double level_imgW=200;
		
		// Background
		ImageView viewWallpaper =Settings.setImageView(Settings.LevelselectScre_bgrdImg, 0, 0,Settings.WIDTH,Settings.HEIGHT);
		Settings.setImageView(Settings.scoreBoard_wallpaperImg, 0, 0,300,200);
		
		// levels images
		ImageView level1_img = Settings.setImageView( Settings.level1_bgrdImg,level1_imgX,level1_imgY,level_imgW,level_imgH);
		ImageView level2_img = Settings.setImageView( Settings.level2_bgrdImg,level1_imgX+distance,level1_imgY,level_imgW,
							  level_imgH);
		ImageView lock_img2 = Settings.setImageView( Settings.lockImg,level1_imgX+distance,level1_imgY,60,
				  			60);
		ImageView level3_img =  Settings.setImageView( Settings.level3_bgrdImg,level1_imgX+distance*2,level1_imgY,level_imgW,
				                level_imgH);
		ImageView lock_img3 = Settings.setImageView( Settings.lockImg,level1_imgX+distance*2,level1_imgY,60,
							60);
		// Labels
		Label levelLabel =Settings.setLabel("Select a Level",Settings.WIDTH / 2 - 135,80,
				Settings.levelSelectionFont,Color.CHOCOLATE);
		
		// Back button
		Button back =Settings.setButton("Back",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 +200,
				Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Settings.level1_sound.stop();
				Settings.stage.setScene(new MenuScreen());
			}
		});
		pane.getChildren().addAll(viewWallpaper, levelLabel, back,level1_img,level2_img,level3_img,lock_img2,lock_img3);

		loadLevel(); //Load level limit from txt file 
		if (this.maxLevel>=1) {
			level1_img.setOnMouseClicked((MouseEvent e) -> {
//				final Stage dialog = new Stage();
//                dialog.initModality(Modality.APPLICATION_MODAL);
//                dialog.initOwner(Settings.stage);
//                VBox dialogVbox = new VBox(20);
//                dialogVbox.getChildren().addAll(new Text("This is a Dialog"),scoreboard_img);
//                Scene dialogScene = new Scene(dialogVbox, 300, 200);
//                dialog.setScene(dialogScene);
//                dialog.show();
				Settings.level1_sound.stop();
				Settings.level1_sound.play();
				Settings.stage.setScene(new GameScreen(1));
		    });
			//pane.getChildren().add(level1_img);
		}

		if (this.maxLevel>=2){
			level2_img.setOnMouseClicked((MouseEvent e) -> {
				Settings.level1_sound.stop();
				Settings.level1_sound.play();
				Settings.stage.setScene(new GameScreen(2));
		    });
		//	pane.getChildren().add(level2_img);
		}
		
		if (this.maxLevel>=3) {
			Settings.level1_sound.stop();
			Settings.level1_sound.play();
			level3_img.setOnMouseClicked((MouseEvent e) -> {
				Settings.stage.setScene(new GameScreen(3));
		    });
			//pane.getChildren().add(level3_img);
		}
	}

}