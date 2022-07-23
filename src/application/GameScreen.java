package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import Level.Level;
import Objects.Destroyer;
import Objects.Structure;
import Objects.Target;
import Utilities.Settings;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import Utilities.Conversions;

/**
 * This scene object is the screen on which the game is played.
 * Takes information from its Level object, which holds all the
 * level information, and draws it onto a JavaFX animation as a
 * GUI. Also contains faculties for game I/O originating from the
 * JavaFX libraries.
 * 
 * @author Straight Outta CompSci
 */
public class GameScreen extends Screen {
	// Level information
	private int levelNum;
	private Level level;
	private int score = 0;
	
	// Interface components
	private Label scoreLbl;
	private Rectangle slingshot;
	
	/*private*/ 
	ArrayList<Destroyer> dList = new ArrayList<Destroyer>();
	
	public Level getLevel() {
		return this.level;
	}
	
	
	public Pane getPane() {
		return super.getPane();
	}
	
	/**
	 * Constructor for GameScreen object. Loads the specified
	 * level and calls setLayout so it can draw the level on the screen.
	 * @param level the level to load.
	 */
	public GameScreen(int level) {
		super();
		this.levelNum = level;
		this.level = new Level(level);
		World world = this.level.getWorld();
		world.setContactListener(createListener());
		setLayout( super.getPane() );
	}
	
	/**
	 * Draws all the JavaFX elements to a scene, and then starts
	 * the animation for the game. Includes background, a back button,
	 * and all the structures.
	 * 
	 * @param the pane to draw on
	 */
	protected void addGameComponents( Pane pane) {
		// add destroyer, structures and targets
		pane.getChildren().add(this.level.current().getNode());
		for (Structure s : this.level.getStructures()) {
			pane.getChildren().add(s.getNode());
		}
		for (Target t : this.level.getTargets()) {
			pane.getChildren().add(t.getNode());
		}
		//add slingShot Image
		slingshot = new Rectangle(85, 635, 40, 80);
		ImagePattern woodP = new ImagePattern(Settings.slingshotImg);
		slingshot.setFill(woodP);
	}
	protected ImageView setGametbackground() {
		ImageView background = new ImageView();
		if(GameScreen.this.levelNum==1) {
		background.setImage(Settings.level1_bgrdImg);
		}
		else if(GameScreen.this.levelNum==2) {
			background.setImage(Settings.level2_bgrdImg);
			}
		else{
			background.setImage(Settings.level3_bgrdImg);
			}
		background.setFitHeight(Settings.HEIGHT);
		background.setFitWidth(Settings.WIDTH);
		return background;
	}
	protected void setLayout(Pane pane) {
		ImageView viewWallpaper = setGametbackground();

		scoreLbl = Settings.setLabel("" ,Settings.scoreLblX,Settings.scoreLblY,Settings.scoreFont,
				Color.WHITE);
		
		Button button =Settings.setButton("Back",20,20,
				Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				Settings.stage.setScene(new LevelSelectScreen());
			}
		});//
		pane.getChildren().addAll(viewWallpaper, button);
		addGameComponents(pane);
		// powers
		System.out.println("set layout "+GameScreen.this.level.current().isLaunched());
		super.getPane().setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(GameScreen.this.level.current().isLaunched()) {
					System.out.println("Clicked (pane)");
					if(GameScreen.this.level.current().getNode().getUserData().equals("speed")) {
						GameScreen.this.level.current().speedPower(GameScreen.this);
						GameScreen.this.level.current().setLaunched(false);
					}
					else if(GameScreen.this.level.current().getNode().getUserData().equals("split")) {
						GameScreen.this.level.current().splitPower(GameScreen.this);
						GameScreen.this.level.current().setLaunched(false);
					}
					else {
						GameScreen.this.level.current().setLaunched(false);
					}	
				}
			}
		});

		pane.getChildren().addAll(scoreLbl,slingshot);

		startGame();
	}

	/**
	 * Main game loop. Each increment, it checks if the game has been
	 * won or lost. Else, it will move the world one step and redraw
	 * the elements to their new locations.
	 */
	private void startGame() {
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				scoreLbl.setText("SCORE: " + score );
				slingshot.toFront();
				// checks if game is over
				if (level.noTargets()) {
					win();
					this.stop();
				} else if (level.noDestroyers() && level.noMovement()) {
					lose();
					this.stop();
				} else {
				// if game isn't over, step
					update();
					move();
				}
			}
		};
		gameLoop.start();
	}
	
	/**
	 * Moves the world ahead one time unit, and moves all the JavaFX representations
	 * to their new respective locations.
	 */
	private void move() {
		this.level.getWorld().step(1.0f/60, 6, 10);
		
		// move structures
		for (Structure s : this.level.getStructures()) {
			Node node = s.getNode();
			node.relocate(Conversions.meterToPixel(s.getXPos()), Conversions.meterToPixel(s.getYPos()));
			node.setRotate(s.getAngle());
			
		}
		for (Target t : this.level.getTargets()) {
			Node node = t.getNode();
			node.relocate(Conversions.meterToPixel(t.getXPos()), Conversions.meterToPixel(t.getYPos()));
			node.setRotate(t.getAngle());
			
		}
		
		// move destroyer
		Destroyer d = this.level.current();
		Vec2 pos = Conversions.worldToScreen(d.getPosition());
		d.getNode().setCenterX(pos.x);
		d.getNode().setCenterY(pos.y);
		d.getNode().setRotate(- d.getBody().getAngle() *180 /Math.PI);
		
		if(!dList.isEmpty()) {
			for(Destroyer d2: dList) {
				Vec2 pos2 = Conversions.worldToScreen(d2.getPosition());
				if (pos2.x > Settings.WIDTH+5 || pos2.x < 0-5) {
					d2.getBody().setActive(false);
				}
				
				d2.getNode().setCenterX(pos2.x);
				d2.getNode().setCenterY(pos2.y);
				d2.getNode().setRotate(- d2.getBody().getAngle() * 180 / Math.PI);;
				
			}
		}
	}

	/**
	 * Currently it just checks if the new destroyer should be loaded.
	 * 
	 * Should also include things like adding and removing structures. 
	 * Add that stuff here please.
	 */
	private void update() {
		
		if (!this.level.destroyerActivity() && this.level.noMovement()) {
				this.level.current().setLaunched(false);
				super.getPane().getChildren().remove(this.level.current().getNode());
				this.level.nextDestroyer();
				super.getPane().getChildren().add(this.level.current().getNode());
				//launch(this.level.current());
		}
		else if(!dList.isEmpty()) {
			if(noMovement()) {
				for(Destroyer d: dList) {
					d.getBody().setActive(false);
					super.getPane().getChildren().remove(d.getNode());
				}
				this.level.current().setLaunched(false);
				super.getPane().getChildren().remove(this.level.current().getNode());
				this.level.nextDestroyer();
				super.getPane().getChildren().add(this.level.current().getNode());
				//launch(this.level.current());		
				dList.clear();
			}

		}
		
	}
	public boolean noMovement() {
		for (Destroyer d  : this.dList) {
			if (!d.getBody().isActive()) {
				continue;
			}
			Vec2 vel = d.getBody().getLinearVelocity();
			if (Math.abs(vel.x) > 0.2 || Math.abs(vel.y) > 0.2) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Handles a game lost. Creates a label shaming the player, and
	 * new buttons allowing for retry of level or going back to the 
	 * menu screen
	 */
	private void lose() {
		Label loseLabel = Settings.setLabel("You lose :(",Settings.WIDTH / 2 - 250,250.0,new Font("Yuppy SC",120),
				Color.CHOCOLATE);
		
		//set buttons on action
		Button retry =Settings.setButton("Retry",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 + 80,
				Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		retry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Settings.stage.setScene(new GameScreen(GameScreen.this.levelNum));
			}
		});
		Button back = Settings.setButton("Back",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 + 140,
				Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Settings.level1_sound.stop();
				Settings.stage.setScene(new LevelSelectScreen());
			}
		});
		super.getPane().getChildren().addAll(loseLabel, retry, back);
	}
	
	/**
	 * Handles a game won. Creates a label congratulating the player, and
	 * new buttons allowing for next level or going back to the 
	 * menu screen. Also saves progress.
	 */
	private void win() {
		// save progress
		try {
			File file = new File("resources/level.txt");
			// false allows overwrite
			FileWriter updater = new FileWriter(file, false);
			if (this.levelNum < 3) {
				updater.write(Integer.toString(this.levelNum + 1));
			} else {
				updater.write(Integer.toString(this.levelNum + 1));
			}
			updater.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: level log not found");
		} catch (NoSuchElementException e) {
			System.err.println("ERROR: level log has incorrect value");
		} catch (IOException e) {
			System.err.println("ERROR: IO exception");
		}

		Label winLabel = Settings.setLabel("You Win :)",Settings.WIDTH / 2 - 250,250.0,new Font("Yuppy SC",120),
				Color.CHOCOLATE);
		Button back =Settings.setButton("Back",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 + 140,
				Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Settings.stage.setScene(new LevelSelectScreen());
			}
		});
		if (this.levelNum < 3) {	
			Button nextLevel = Settings.setButton("Next",Settings.WIDTH / 2 - 100,Settings.HEIGHT / 2 + 80,
					Settings.MenuBtns_W,Settings.MenuBtns_H,Settings.allButtons_style);
			
			nextLevel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if (GameScreen.this.levelNum < 3) {
						Settings.stage.setScene(new GameScreen(GameScreen.this.levelNum + 1));
					} else {
						Settings.stage.setScene(new LevelSelectScreen());
					}
				}
			});
			super.getPane().getChildren().add(nextLevel);
		}
		super.getPane().getChildren().addAll(winLabel, back);
	}

	//////////////////ANALYZE COLLISIONS//////////////////////
	
	private ContactListener createListener() {
		ContactListener contactListener = new ContactListener()
	    {
			//inherited abstract methods:
	        @Override
	        public void beginContact(Contact contact) {}
	        @Override
	        public void endContact(Contact contact) {}
	        @Override
	        public void preSolve(Contact contact, Manifold oldManifold) {}
	        @Override
	        public void postSolve(Contact contact, ContactImpulse impulse)
	        {	   
        			float damage = 0; 
        		   //CONTACT B/W BODIES 
        	 	   Body bodyA = contact.getFixtureA().getBody();
        	 	   Body bodyB = contact.getFixtureB().getBody();
        	 	   //Strength of impact 
        	 	   float[] impulses = impulse.normalImpulses; 
	               for(float imp : impulses) {
		            	   	if (imp > Settings.MIN_IMPULSE) {damage += imp; }
	        	 	  
		        	 		  if (Structure.class.isInstance(bodyA.getUserData())){
		        	 			Structure entityA = (Structure) bodyA.getUserData();
		        	 			entityA.decrementHealth(damage);
//update score on screen??? 
		        	 			score += (int)damage*1000;
		        	 		
		        	 			if (entityA.getHealth() <= entityA.getBreakDmg()) entityA.breakStructure();
		        	 			if (entityA.getHealth()<=0.0f){
		        	 				/*
		        	 				if (!(Settings.bodiesToSweep.contains(bodyA))) {
		        	 					Settings.bodiesToSweep.add(bodyA); 
		        	 				   }
		        	 				*/
			            			   bodyA.setActive(false);
			            			   for(Structure s: GameScreen.this.level.getStructures()) {
			            				   if(s.getBody().equals(bodyA)) {
			            					   GameScreen.this.getPane().getChildren().remove(s.getNode());
			            					   
			            				   }
			            			   }
			            			   for (Target t : GameScreen.this.level.getTargets()) {
			            				   if (t.getBody().equals(bodyA)) {
			            					   GameScreen.this.getPane().getChildren().remove(t.getNode());
			            					   GameScreen.this.level.getTargets().remove(t);
			            					   break;
			            				   }
			            			   }
			            			   
		        	 			}
		        	 		  }
		        	 		   if (Structure.class.isInstance(bodyB.getUserData())) {
							 Structure entityB = (Structure) bodyB.getUserData();
							 entityB.decrementHealth(damage);
		        	 			if (entityB.getHealth() <= entityB.getBreakDmg()) entityB.breakStructure();
		        	 			if (entityB.getHealth()<=0f) {
		        	 				if (!(Settings.bodiesToSweep.contains(bodyB))) {
		        	 					Settings.bodiesToSweep.add(bodyB); 
		        	 				    }
		        	 			}
		        	 		  }		  
            	       }
	       }
	   };
	    return contactListener;
	}
	
	/**
	 * Returns destroyer list
	 * @return
	 */
	public ArrayList<Destroyer> getDList(){
		return this.dList;
	}
}




