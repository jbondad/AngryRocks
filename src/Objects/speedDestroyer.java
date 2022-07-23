package Objects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Projectile whose special power is to speed up
 * once clicked.
 * 
 * @author Straight Outta CompSci
 */
public class speedDestroyer extends Destroyer {
	/**
	 * Constructor. Uses JavaFX coordinates
	 * 
	 * @param x x-pos 
	 * @param y y-pos
	 * @param r radius
	 */
	public speedDestroyer(float x, float y, float r) {
		super(x, y, r);
	}
	
	/**
	 * Create node for javaFX
	 */
	@Override
	public void loadJavaFXBody(float x, float y, float r) {
		super.setNode(new Circle(x, y, r));
		super.getNode().setUserData("speed");
		
		Image image = new Image("file:resources/fastRock.png");
		ImagePattern imgP = new ImagePattern(image);
		super.getNode().setFill(imgP);
		
		launch();
	}

}


