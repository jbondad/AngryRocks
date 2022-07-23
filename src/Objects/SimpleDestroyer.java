package Objects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Simple Destroyer that is literally just a ball. No special powers.
 * 
 * @author Straight Outta CompSci
 */
public class SimpleDestroyer extends Destroyer {
	/**
	 * Constructor. Uses JavaFX coordinates
	 * 
	 * @param x x-pos 
	 * @param y y-pos
	 * @param r radius
	 */
	public SimpleDestroyer(float x, float y, float r) {
		super(x, y, r);
	}
	
	/**
	 * Sets the data for the simpledestroyer. Coordinates 
	 * are in JavaFX coordinates.
	 * 
	 * @param x x-pos 
	 * @param y y-pos
	 * @param r radius
	 */
	@Override
	public void loadJavaFXBody(float x, float y, float r) {
		super.setNode(new Circle(x, y, r));
		super.getNode().setUserData("simple");
		
		Image image = new Image("file:resources/Rock.png");
		ImagePattern imgP = new ImagePattern(image);
		super.getNode().setFill(imgP);
		
		launch();
	}
}
