package Utilities;

import org.jbox2d.common.Vec2;

/**
 * Contains conversions for JavaFx and JBox2d world Coordinates
 * 
 *
 */
public class Conversions {
	
	public static final float SCALE = 50f;
	/**
	 * Converts meters to pixels
	 * 
	 * @param meter: meter measurement to be converted
	 * @return argument in pixels
	 */
	public static float meterToPixel(float meter) {
	    return meter * Conversions.SCALE;
	}
	
	/**
	 * Converts pixels to meters
	 * 
	 * @param pixel: pixel measurement to be converted
	 * @return argument in meters
	 */
	public static float pixelToMeter(float pixel) {
	    return pixel / Conversions.SCALE;
	}

	
	/**
	 * Converts JavaFX pixel coordinates to world meter coordinates
	 * 
	 * @param screen: Vec2 coordinates from JavaFX
	 * @return World coordinate vector
	 */
	public static Vec2 screenToWorld(Vec2 screen) {
	    return new Vec2(Conversions.pixelToMeter(screen.x), -1.0f * (Conversions.pixelToMeter(screen.y)));
	}
	
	/**
	 * Converts world meter coordinates to JavaFX pixel coordinates
	 * 
	 * @param world: Vec2 coordinates from JBox2D world
	 * @return Screen coordinate vector
	 */
	public static Vec2 worldToScreen(Vec2 world) {
	    return new Vec2(Conversions.meterToPixel(world.x), -1.0f * (Conversions.meterToPixel(world.y)));
	}

}
