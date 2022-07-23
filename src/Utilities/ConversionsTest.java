package Utilities;

import static org.junit.Assert.*;

import org.jbox2d.common.Vec2;
import org.junit.Test;

/*
 * J Unit tests for jbox2d world coordinates and Javafx coordinates
 */
public class ConversionsTest {


	@Test 
	public void TestWorldToScreen() {
		Vec2 worldCor = new Vec2(10,10);
		Vec2 res = Conversions.worldToScreen(worldCor);
		Vec2 expected = new Vec2(500,-500);
		
		assertEquals("Testing world to screen conversions", expected, res);
	}
	@Test
	public void TestScreenToWorld() {
		Vec2 screenCor = new Vec2(1000,1000);
		Vec2 res = Conversions.screenToWorld(screenCor);
		Vec2 expected = new Vec2(20,-20);
		
		assertEquals("Testing world to screen conversions", expected, res);
	}
	
	@Test
	public void TestPixelToMeter() {
		float num = 50;
		float res = Conversions.pixelToMeter(num);
		float expected = 1;
		
		assertEquals( expected, res, 0.001);
	}
	
	@Test
	public void MeterToPixel() {
		float num = 0;
		float res = Conversions.pixelToMeter(num);
		float expected = 0;
		
		assertEquals( expected, res, 0.001);
	}

}
