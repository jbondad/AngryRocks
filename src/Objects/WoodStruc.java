package Objects;


import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import Utilities.Settings;
/**
 * Simple structure with no special faculties.
 * @author
 *
 */
public class WoodStruc extends Structure {
	//wooden structure damage thresholds 
	private float breakDmg = 5.0f; 
	private float health = 9.0f;
	private static String type = "wood";

	/**
	 * Constructor. Uses JavaFX coordinates
	 * @param x x-pos 
	 * @param y y-pos
	 * @param w width
	 * @param h height
	 */
	public WoodStruc(float x, float y, float w, float h) {
		super(x, y, w, h, type);
		this.setHealth(health); 
		this.setBreakDmg(breakDmg);


	}
	
	/**
	 * body for the structure.
	 * 
	 * NOTE: the current code uses black magic but it works so don't
	 * touch it unless absolutely necessary
	 * @param x x-pos 
	 * @param y y-pos
	 * @param w width
	 * @param h height
	 */
	@Override
	public void loadJavaFXBody(float x, float y, float w, float h) {
		ImagePattern woodP = new ImagePattern(Settings.wood);
			
		float widthAdjusted = w*2;
        float heightAdjusted = h*2;
        super.setNode(new Rectangle(x, y, widthAdjusted, heightAdjusted));
        super.getNode().setUserData(super.getBody());
        Rectangle rect = (Rectangle)super.getNode();
        rect.setFill(woodP);
	}
   
	//change image
	public void breakStructure() {
		//replace node image here
	}
	//poof animation if there is time??
	public void vanish() {System.out.println("wood vanish called");}

	
	}