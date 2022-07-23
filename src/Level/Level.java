package Level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import Objects.*;
import Utilities.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


/**
 * A level object holds all the information on a current game,
 * including all the structures, all the targets and a queue of 
 * the destroyers. Also contains faculties for checking various things
 * on the current state of the game
 * 
 * @author Straight Outta CompSci
 *
 */
public class Level {
	private ArrayList<Structure> structures;
	private ArrayList<Target> targets;
	private Queue<Destroyer> destroyers;
	private World world;
	private Destroyer currDestroyer= null;
	
	/**
	 * Creates a level object and adds all the information to the 
	 * instance variables. 
	 * 
	 * TODO: add the rest of the levels
	 * TODO: the program crashes when you try to make a level that 
	 * doesn't exist. I don't know if we'll have to fix it since
	 * the problem should disappear once we make all the levels.
	 * 
	 * @param level the level information to load
	 */
	public Level(int level) {
		this.world = new World(new Vec2(0.0f, Settings.GRAVITY));
		// make a ground
		new Ground(this.world);
		
		Structure[] strucList;
		Target[] targetList;
		Destroyer[] destroyerList;
		
		switch(level) {
		/*** LEVEL 1 ***/
		case 1:
			// Load structures
			this.structures = new ArrayList<Structure>();
			strucList = new Structure[] {
					new WoodStruc(620, 675, 8, 45),
					new WoodStruc(700, 675, 8, 45),
					new WoodStruc(660, 615, 45, 8),
					
					new WoodStruc(820, 675, 8, 45),
					new WoodStruc(900, 675, 8, 45),
					new WoodStruc(860, 615, 45, 8),
					new WoodStruc(820, 555, 8, 45),
					new WoodStruc(900, 555, 8, 45),
					new WoodStruc(860, 495, 45, 8),
					
					new WoodStruc(1020, 675, 8, 45),
					new WoodStruc(1100, 675, 8, 45),
					new WoodStruc(1060, 615, 45, 8),
					new WoodStruc(1020, 555, 8, 45),
					new WoodStruc(1100, 555, 8, 45),
					new WoodStruc(1060, 495, 45, 8),
					new WoodStruc(1020, 435, 8, 45),
					new WoodStruc(1100, 435, 8, 45),
					new WoodStruc(1060, 375, 45, 8),
			};
			for (Structure s : strucList) {
				this.structures.add(s);
				s.createBody(this.world);
			}
			
			// Load targets
			this.targets = new ArrayList<Target>();
			
			targetList = new Target[] {
					new Target(660,585,10,20),
					new Target(860,465,10,20),
					new Target(1060,345,10,20)
					
			};
			for (Target t : targetList) {
				this.targets.add(t);
				t.createBody(this.world);
			}

			// Load destroyers
			this.destroyers = new LinkedList<Destroyer>();
			destroyerList = new Destroyer[] {
					new splitDestroyer(95, 635, 20),
					new speedDestroyer(95, 635, 30),
					new SimpleDestroyer(95, 635, 40),
					new SimpleDestroyer(95, 635, 50),
					new SimpleDestroyer(95, 635, 60),
					new SimpleDestroyer(95, 635, 70),
			};
			for (Destroyer d : destroyerList) {
				this.destroyers.add(d);
			}
			
			// load first Destroyer
			nextDestroyer();
			break;
		/*** LEVEL 2 ***/ // TODO make level 2
		case 2:
			// Load structures
			this.structures = new ArrayList<Structure>();
			strucList = new Structure[] {
					new WoodStruc(720, 675, 8, 45),
					new WoodStruc(800, 675, 8, 45),
					new WoodStruc(760, 615, 45, 8),
					new WoodStruc(800, 675, 8, 45),
					new WoodStruc(880, 675, 8, 45),
					new WoodStruc(840, 615, 45, 8),
					new WoodStruc(880, 675, 8, 45),
					new WoodStruc(960, 675, 8, 45),
					new WoodStruc(920, 615, 45, 8),
					new WoodStruc(720, 560, 8, 45),
					new WoodStruc(800, 560, 8, 45),
					new WoodStruc(760, 500, 45, 8),
					new WoodStruc(880, 560, 8, 45),
					new WoodStruc(960, 560, 8, 45),
					new WoodStruc(920, 500, 45, 8),
					new WoodStruc(800, 560, 8, 45),
					new WoodStruc(880, 560, 8, 45),
					new WoodStruc(840, 500, 45, 8),
					new WoodStruc(720, 450, 8, 45),
					new WoodStruc(800, 450, 8, 45),
					new WoodStruc(760, 390, 45, 8),
					new WoodStruc(880, 450, 8, 45),
					new WoodStruc(960, 450, 8, 45),
					new WoodStruc(920, 390, 45, 8),
					new WoodStruc(800, 450, 8, 45),
					new WoodStruc(880, 450, 8, 45),
					new WoodStruc(840, 390, 45, 8),
					
			};
			for (Structure s : strucList) {
				this.structures.add(s);
				s.createBody(this.world);
			}
			
			// Load targets
			this.targets = new ArrayList<Target>();
			targetList = new Target[] {
					new Target(760,675,10,20),
					new Target(920,675,10,20),
					new Target(840,675,10,20),
					new Target(760,585,10,20),
					new Target(920,585,10,20),
					new Target(840,585,10,20),
					new Target(760,470,10,20),
					new Target(920,470,10,20),
					new Target(840,470,10,20),
					
			};
			for (Target t : targetList) {
				
				this.targets.add(t);
				t.createBody(this.world);
			}
			
			// Load destroyers
			this.destroyers = new LinkedList<Destroyer>();
			destroyerList = new Destroyer[] {
					new splitDestroyer(95, 635, 10),
					new speedDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
			};
			for (Destroyer d : destroyerList) {
				this.destroyers.add(d);
			}
			
			// load first Destroyer
			nextDestroyer();
			break;
		/*** LEVEL 3 ***/ // TODO make level 3
		case 3:
			// Load structures
			this.structures = new ArrayList<Structure>();
			strucList = new Structure[] {
					new WoodStruc(620, 675, 8, 45),
					new WoodStruc(700, 675, 8, 45),
					new WoodStruc(660, 615, 45, 8),
					
					new WoodStruc(820, 675, 8, 45),
					new WoodStruc(900, 675, 8, 45),
					new WoodStruc(860, 615, 45, 8),
					new WoodStruc(820, 555, 8, 45),
					new WoodStruc(900, 555, 8, 45),
					new WoodStruc(860, 495, 45, 8),
					
					new WoodStruc(1020, 675, 8, 45),
					new WoodStruc(1100, 675, 8, 45),
					new WoodStruc(1060, 615, 45, 8),
					new WoodStruc(1020, 555, 8, 45),
					new WoodStruc(1100, 555, 8, 45),
					new WoodStruc(1060, 495, 45, 8),
					new WoodStruc(1020, 435, 8, 45),
					new WoodStruc(1100, 435, 8, 45),
					new WoodStruc(1060, 375, 45, 8),
			};
			for (Structure s : strucList) {
				this.structures.add(s);
				s.createBody(this.world);
			}
			
			// Load targets
			this.targets = new ArrayList<Target>();
			
			targetList = new Target[] {
					new Target(660,585,10,20),
					new Target(860,465,10,20),
					new Target(1060,345,10,20)
					
			};
			for (Target t : targetList) {
				this.targets.add(t);
				t.createBody(this.world);
			}

			// Load destroyers
			this.destroyers = new LinkedList<Destroyer>();
			destroyerList = new Destroyer[] {
					new SimpleDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
					new SimpleDestroyer(95, 635, 10),
			};
			for (Destroyer d : destroyerList) {
				this.destroyers.add(d);
			}
			
			// load first Destroyer
			nextDestroyer();
			break;
			
		default:
			System.err.println("ERROR: level does not exist");
		}
	}
	
	/**
	 * Loads the next destroyer for launching and adds it to
	 * the world. Also destroys the body of the old one
	 */
	public void nextDestroyer() {
		// destroy previous destroyer
		if (this.currDestroyer != null) {
			this.world.destroyBody(this.currDestroyer.getBody());
		}
		this.currDestroyer = this.destroyers.remove();
		this.currDestroyer.createBody(this.world);
		this.currDestroyer.getBody().setActive(false);
	}
	
	/**
	 * Returns the current destroyer to be launched
	 * 
	 * @return destroyer to be launched
	 */
	public Destroyer current() {
		return this.currDestroyer;
	}
	
	/**
	 * Returns the world object if you ever want to
	 * add new things to it
	 * 
	 * @return this world
	 */
	public World getWorld() {
		return this.world;
	}
	
	/** 
	 * Returns list of structures
	 * 
	 * @return structure list
	 */
	public ArrayList<Structure> getStructures() {
		return this.structures;
	}
	
	/** 
	 * Returns list of targets
	 * 
	 * @return target list
	 */
	public ArrayList<Target> getTargets() {
		return this.targets;
	}
	
	/**
	 * Find whether there are any destroyers left in the level
	 * @return true if there aren't any left
	 * 			false if there are
	 */
	public boolean noDestroyers() {
		if (this.destroyers.isEmpty() && !this.destroyerActivity()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if there are any targets left. 
	 * 
	 * TODO: currently always returns false. Must be fixed once targets are implemented.
	 * 
	 * @return true if no targets left
	 * 			false if there are
	 */
	public boolean noTargets() {
		if(this.targets.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * True if still active, false otherwise
	 * @return
	 */
	public boolean destroyerActivity() {
		if (!this.current().getBody().isActive()) {
			return true;
		}
		Vec2 vel = this.current().getBody().getLinearVelocity();
		if (vel.x == 0 || vel.y == 0) {
			return false;
		}
		Vec2 pos = this.current().getBody().getPosition();
		float inPixels = Conversions.meterToPixel(pos.x);
		if (inPixels > Settings.WIDTH || inPixels < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * True if any structure objects are moving within a small range
	 * @return true if no movement
	 * false otherwise
	 */
	public boolean noMovement() {
		for (Structure s : this.structures) {
			if (!s.getBody().isActive()) {
				continue;
			}
			Vec2 vel = s.getBody().getLinearVelocity();
			if (Math.abs(vel.x) > 0.1 || Math.abs(vel.y) > 0.1) {
				return false;
			}
		}
		return true;
	}
	
}
