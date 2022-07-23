package Objects;



import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Target extends Structure {
	//target object thresholds 
		public float breakDmg = 5.0f; 
		public float health = 9.0f; 
		public static String type = "target";
	
	/**
	 * Constructor. Uses JavaFX coordinates
	 * @param x x-pos 
	 * @param y y-pos
	 * @param w width
	 * @param h height
	 */
	public Target(float x, float y, float w, float h) {
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
		Image wood = new Image("file:resources/toySoldier1.png");
		ImagePattern woodP = new ImagePattern(wood);
			
	
		float widthAdjusted = w*2;
        float heightAdjusted = h*2;
        super.setNode(new Rectangle(x, y, widthAdjusted, heightAdjusted));
        super.getNode().setUserData(super.getBody());
        Rectangle rect = (Rectangle)super.getNode();
        rect.setFill(woodP);
	}

		@Override
		public void breakStructure() {}
		@Override
		public void vanish() {}


}