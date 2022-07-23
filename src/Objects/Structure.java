package Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import Utilities.*;
import javafx.scene.Node;
/**
 * Abstract parent class for all structures. Contains basic faculties 
 * for the creation of both a JavaFX object and a world object. 
 * 
 * Any classes inheriting from this class must implement a
 * method specifying the nature of the JavaFX node representing
 * the object.
 * 
 * @author Straight Outta CompSci
 *
 */

public abstract class Structure {
	
	// JavaFX body
	Node node;
	public String type; 
	
	// JBox2D body
	private Body body;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;
	private Float minX = null;  
    private Float maxX = null;
    private Float minY = null;
    private Float maxY = null;
    
    // Game data
    private boolean hit = false;
	private float breakDmg; 
	private float health;
   
	
    /**
	 * Constructor. Initializes a JavaFX body and creates
	 * the data for a World object. Does not actually make
	 * the world object - createBody needs to be called. 
	 * Arguments are in JavaFX coordinates
	 * @param x x-pos
	 * @param y y-pos
	 * @param w width
	 * @param h height
	 */
	public Structure (float x, float y, float w, float h, String type) {
		this.type = type; 
		
		/*** JBox2D body ***/
		// Body
		this.bodyDef = new BodyDef();
		this.bodyDef.type = BodyType.DYNAMIC;
		Vec2 pos = (new Vec2(x,y));
		this.bodyDef.position.set(Conversions.screenToWorld(pos));
		
		// Shape
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(Conversions.pixelToMeter(w),Conversions.pixelToMeter(h));
		for (int i = 0; i < poly.getVertexCount(); i++) {
            Vec2 nextVertex = poly.getVertex(i);
            float vx = nextVertex.x;
            float vy = nextVertex.y;
            if (minX == null || vx < minX) {
                minX = vx;
            }
            if (maxX == null || vx > maxX) {
                maxX = vx;
            }
            if (minY == null || vy < minY) {
                minY = vy;
            }
            if (maxY == null || vy > maxY) {
                maxY = vy;
            }
        }
		
		// Fixture
		this.fixtureDef = new FixtureDef();
		this.fixtureDef.shape = poly;
		this.fixtureDef.density = 0.6f;
		this.fixtureDef.friction = 0.8f;
		this.fixtureDef.restitution = 0.1f;
		
		/*** JavaFX Body ***/
		loadJavaFXBody(x, y, w, h);
		
	}
	
	/**
	 * Abstract class for creation of the JavaFX
	 * node representing the object. Arguments are in
	 * JavaFX coordinates
	 * 
	 * TODO: this and destroyer classes have a lot of the same code - possibly make one parent code for all?
	 * 
	 * @param x x-pos
	 * @param y y-pos
	 * @param r radius
	 */
	public abstract void loadJavaFXBody(float x, float y, float w, float h);
	
	/**
	 * Creates the actual world body and adds it to the specified world.
	 * @param world world for the object to be added to
	 */
	public void createBody(World world) {
		this.body = world.createBody(this.bodyDef);
		this.body.createFixture(this.fixtureDef);
		this.body.setUserData(this);
	}
	
	/** collision responses **/
	public void decrementHealth(float damage) {this.health -= damage;}
	public abstract void breakStructure();
	public abstract void vanish();
	
	/*** GETTERS ***/
	
	/** the most damage an object can withstand before destruction. */
	public float getHealth() {return this.health;}
	
	/** the most damage an object can withstand before breaking/being injured. */
	public float getBreakDmg() {return this.breakDmg;}
	
	public float getWidth() {    
	    float width = maxX - minX;
	    return width;
	}
	
	public float getHeight() {
        float height = maxY - minY;
        return height;
    }
	
	public float getXPos() {
		return body.getPosition().x - getWidth()/2;
	}
	
	public float getYPos() {
		return -body.getPosition().y - getHeight()/2;
	}

	public Vec2 getBodyPosition() {

		return body.getPosition();
	}
	
    public Body getBody() {
		return this.body;
	}
	
	public float getAngle() {
		return (float) -((body.getAngle() * 180) / Math.PI);
	}
	
	public Node getNode() {
		return this.node;
	}
	
	/*** SETTERS ***/
	public void setNode(Node node) {
		this.node = node;
	}
	public void setHealth(float h) {this.health = h;}
	public void setBreakDmg(float bd) {this.breakDmg = bd;}

}
