package Objects;


import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class BrickStruc extends Structure {

	//brick structure damage thresholds 
		private float breakDmg = 25.0f; //doesn't break, only tumbles around
		private float health = 25.0f; //never dies, super strong :o 
		private static String type = "brick";

		/**
		 * Constructor. Uses JavaFX coordinates
		 * @param x x-pos 
		 * @param y y-pos
		 * @param w width
		 * @param h height
		 */
		public BrickStruc(float x, float y, float w, float h) {
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
			//Image wood = new Image("file:resources/wood.png");
			//ImagePattern woodP = new ImagePattern(wood);
				
		
			float widthAdjusted = w*2;
	        float heightAdjusted = h*2;
	        super.setNode(new Rectangle(x, y, widthAdjusted, heightAdjusted));
	        super.getNode().setUserData(super.getBody());
	        Rectangle rect = (Rectangle)super.getNode();
	        rect.setFill(Color.CHOCOLATE);
		}
	   
		//change image
		public void breakStructure() {}
		//poof animation??
		public void vanish() {}

}
