/**
 * 
 */
package com.game.kalah.model;

import org.junit.Test;

import com.game.kalah.model.Pit;

import static org.junit.Assert.*;

public class PitTest {
	
	@Test
	public void testPickUpStones(){
		Pit pit = new Pit(6,0);
		int countOfStones = pit.pickUpStones();
		assertEquals(6, countOfStones);
		assertEquals(0, pit.getCountOfStones());
	}
	
	@Test
	public void testPitEquals(){
		Pit pit1 = new Pit(6,1);		
		Pit pit2 = new Pit(4,1);
		assertEquals(pit1, pit2);
	}
	
	@Test
	public void testPickUpEmty(){
		Pit pit = new Pit(0,5);
		int countOfStones = pit.pickUpStones();
		assertEquals(0, countOfStones);
		assertEquals(0, pit.getCountOfStones());
	}

}
