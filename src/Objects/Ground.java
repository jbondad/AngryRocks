package Objects;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import Utilities.Settings;

/**
 * Ground body for JBox2D to make sure things don't fall through
 * the ground.
 * 
 * @author Straight Outta CompSci
 */
public class Ground {
	private Body body;
	
	/**
	 * Constructor for a simple ground object whose size
	 * is dependent on the scene size settings
	 * 
	 * @param world the world to add it to
	 */
	public Ground(World world) {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.STATIC;
		groundBodyDef.position.set(0.0f,-720f/50);
		
		body = world.createBody(groundBodyDef);
		body.setUserData("ground");
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox((float)Settings.WIDTH/20,0);

		body.createFixture(groundBox,0.0f);
	}

}
