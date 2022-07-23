package Objects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Projectile whose special power is to split into three
 * once clicked.
 * 
 * @author Straight Outta CompSci
 */
public class splitDestroyer extends Destroyer {
	/**
	 * Constructor. Uses JavaFX coordinates
	 * 
	 * @param x x-pos 
	 * @param y y-pos
	 * @param r radius
	 */
	public splitDestroyer(float x, float y, float r) {
		super(x, y, r);
	}
	
	/**
	 * Create node for javaFX
	 */
	@Override
	public void loadJavaFXBody(float x, float y, float r) {
		super.setNode(new Circle(x, y, r));
		super.getNode().setUserData("split");
		
		Image image = new Image("file:resources/splitRock.png");
		ImagePattern imgP = new ImagePattern(image);
		super.getNode().setFill(imgP);

		launch();
	}
}


