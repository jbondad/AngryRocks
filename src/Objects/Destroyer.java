package Objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;

import Utilities.*;
import application.*;

/**
 * Abstract parent class for all projectiles, hence named
 * Destroyers. Contains basic faculties for the creation of
 * both a JavaFX object and a world object. 
 * 
 * Any classes inheriting from this class must implement a
 * method specifying the nature of the JavaFX node representing
 * the object.
 * 
 * @author Straight Outta CompSci
 */
public abstract class Destroyer {
	// JavaFX body
	private Circle node;
	
	// JBox2D body
	private Body body;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;
	
	//data for launching
	double orgmouseX;
	double orgmouseY;
	double mouseX;
	double mouseY;
	private boolean launched = false;
	
	/**
	 * Constructor. Initializes a JavaFX body and creates
	 * the data for a World object. Does not actually make
	 * the world object - createBody needs to be called. 
	 * Arguments are in JavaFX coordinates
	 * 
	 * @param x x-pos
	 * @param y y-pos
	 * @param r radius
	 */
	public Destroyer(float x, float y, float r) {
		/*** Initialize JavaFX body ***/
		loadJavaFXBody(x, y, r);
		
		/*** Initialize JBox2D body ***/
		// Body
		this.bodyDef = new BodyDef();
		this.bodyDef.type = BodyType.DYNAMIC;
		Vec2 pos = new Vec2(x, y);
		this.bodyDef.position.set(Conversions.screenToWorld(pos));
		
		// Shape
		CircleShape circle = new CircleShape();
		circle.setRadius(Conversions.pixelToMeter(r));
		
		// Fixture
		this.fixtureDef = new FixtureDef();
		this.fixtureDef.setShape(circle);
		this.fixtureDef.setDensity(0.8f);
		this.fixtureDef.setFriction(0.7f);
		this.fixtureDef.setRestitution(0.4f);
	}
	
	/**
	 * Abstract class for creation of the JavaFX
	 * node representing the object. Arguments are in
	 * JavaFX coordinates
	 * 
	 * @param x x-pos
	 * @param y y-pos
	 * @param r radius
	 */
	public abstract void loadJavaFXBody(float x, float y, float r);
	
	/**
	 * Creates the actual world body and adds it to the specified world.
	 * 
	 * @param world for the object to be added to
	 */
	public void createBody(World world) {

		this.body = world.createBody(this.bodyDef);
		this.body.createFixture(fixtureDef);
		this.body.setUserData("ball");
		this.body.setFixedRotation(false);
		this.body.setLinearDamping(0.4f);
	}
	
	/**
	 * Primes a destroyer object for launching by adding listeners for mouse
	 * clicking and draggin to the JavaFX representation of the destroyer.
	 * Also removes all listeners once it's been launched.
	 */
	protected void launch() {
		AudioClip sound = new AudioClip("file:resources/launch.mp3");
		
		this.node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				orgmouseX = e.getX();
	            orgmouseY = e.getY();
	            
			}
		});
		
		this.node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				mouseX = e.getX();
				mouseY = e.getY();
				double distance = Math.sqrt(Math.pow((orgmouseX - mouseX),2) + Math.pow((orgmouseY - mouseY),2));
				if(distance > 65) {
					
					mouseX =  orgmouseX - ((65*(orgmouseX-mouseX)) / distance );
					mouseY =  orgmouseY - ((65*(orgmouseY-mouseY)) / distance );
				}
				Destroyer.this.node.setCenterX(mouseX);
				Destroyer.this.node.setCenterY(mouseY);
				Vec2 pos = new Vec2(Conversions.screenToWorld(new Vec2((float)mouseX,(float)mouseY)));
				Destroyer.this.getBody().setTransform(pos, 0);
			}
		});
		
		this.node.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				 setLaunched(true);
				 System.out.println(isLaunched());
				 sound.play();
	            double xvel = 0.3*(orgmouseX - mouseX);
	            double yvel = 0.3*(mouseY - orgmouseY);
	            
	            Destroyer.this.getBody().setActive(true);
	            Destroyer.this.getBody().setLinearVelocity(new Vec2((float)xvel,(float)yvel));
	            // remove listeners
	            Destroyer.this.node.setOnMousePressed(null);
	            Destroyer.this.node.setOnMouseDragged(null);
	            Destroyer.this.node.setOnMouseReleased(null);
			}
		});
	}
	
	/*** GETTERS ***/
	public Vec2 getPosition() {
		return body.getPosition();
	}
	
	public Body getBody() {
		return this.body;
	}
	
	public Circle getNode() {
		return this.node;
	}
	
	public double getRadius() {
		return this.node.getRadius();
	}
	
	public boolean isLaunched() {
		return this.launched;
	}
	
	/*** SETTERS ***/
	public void setNode(Node node) {
		this.node = (Circle)node;
	}
	
	public void setLaunched(boolean b) {
		this.launched = b;
	}
	public void speedPower(GameScreen g){
	
		Vec2 speed = g.getLevel().current().getBody().getLinearVelocity();
		g.getLevel().current().getBody().setLinearVelocity(new Vec2(speed.x * 2, speed.y));
	}
	
	public void splitPower(GameScreen g) {
		
		double posx = g.getLevel().current().getNode().getCenterX();
		double posy = g.getLevel().current().getNode().getCenterY();
		Vec2 speed = g.getLevel().current().getBody().getLinearVelocity();
		for(int i = 0; i< 3; i++) {
			Destroyer d = new SimpleDestroyer((float)posx, (float)posy-10 +(10*i), 7);
			d.createBody(g.getLevel().getWorld());
			g.getDList().add(d);
			d.getBody().setLinearVelocity(new Vec2(speed.x,speed.y- 10+ (10*i)));
			g.getPane().getChildren().add(d.getNode());
			
		}
		g.getLevel().current().getBody().setActive(false);
		g.getPane().getChildren().remove(g.getLevel().current().getNode());
		
	}
}
