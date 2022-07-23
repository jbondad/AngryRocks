package Utilities;


import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class contains only static variables and methods. It contains
 * settings that probably can be changed without breaking the game. It also
 * contains global variables and static utility methods that really shouldn't be
 * changed.
 * 
 * @author Straight Outta CompSci
 */
public class Settings {
	
	/*** Game theme ***/
	public static final Image LevelselectScre_bgrdImg=new Image("file:resources/l.gif");
	public static final Image menu_bgrdImg=new Image("file:resources/f.gif");
	public static final Image slingshotImg = new Image("file:resources/sc.png");
	public static final Image scoreBoard_wallpaperImg=new Image("file:resources/kb.jpg");
	public static final Image level1_bgrdImg =new Image("file:resources/a.jpg");
	public static final Image level2_bgrdImg =new Image("file:resources/b.jpg");
	public static final Image level3_bgrdImg =new Image("file:resources/c.jpg");
	public static final Image lockImg =new Image("file:resources/lock.png");
	public static final Image wood = new Image("file:resources/wood.png");
	public static final AudioClip level1_sound = new AudioClip("file:resources/true.mp3");
	
	public static final String allButtons_style = "-fx-background-color: orange;-fx-font-size: 2em; -fx-text-fill: navajowhite";
	public static final Font scoreFont= Font.loadFont("file:resources/font.ttf", 40);
	public static final Font levelSelectionFont=new Font("Yuppy SC",45);
	
	// Dimensions for the application
	public static final double WIDTH = 1280;      //java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-50;
	public static final double HEIGHT = 720;     //java.awt.Toolkit.getDefaultToolkit().getScreenSize().height-50;
	public static final double MenuBtns_H = 50;   
	public static final double MenuBtns_W= 210;   
	public static final double scoreLblX = WIDTH - 300;
	public static final double scoreLblY = 35;
	// Physics Parameters
	public static final float GRAVITY = -9.81f;
	public static final float SCALE = 50f;
	public static final float MIN_IMPULSE = 0.6f;

	
	/*** UTILITIES ***/
	
	// Common JavaFX stage for all components
	public static final Stage stage = new Stage();
	//destroyed structures/targets are added to this list for removal  
	public static ArrayList<Body> bodiesToSweep = new ArrayList<Body>();
	
	public static ImageView setImageView(Image img,double x,double y,double w,double h) {
		ImageView imageView = new ImageView(img);
		imageView.setFitHeight(h);
		imageView.setFitWidth(w);
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);
		return imageView;
	}
	public static Button setButton(String name ,double x,double y,double w,double h,String style) {
		Button Btn = new Button(name);
		Btn.setLayoutX(x);
		Btn.setLayoutY(y);
		Btn.setPrefSize(w, h);
		Btn.setStyle(style);
		return Btn;
	}
	public static Label setLabel(String name ,double x,double y,Font font,Color color) {
		Label label = new Label(name);
		label.setFont(font);
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setTextFill(color);
	return label;
	}

	

}
