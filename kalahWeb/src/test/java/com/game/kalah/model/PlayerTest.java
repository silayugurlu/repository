package com.game.kalah.model;

import org.junit.Before;
import org.junit.Test;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {

	private Player player;

	@Before
	public void setUp() {
		List<Pit> pits = new ArrayList<Pit>();
		for(int i = 0; i<6; i++){
			Pit pit = new Pit(6,0);
			pits.add(pit);
		}
		
		Pit kalah = new Kalah(0,6);
		player = new Player(1,"test",pits,kalah);

	}
	@Test
	public void testCountStones(){
		int count = player.countStonesOnPits();
		assertEquals(36, count);
	}
	
	@Test
	public void testPutAllStonesToKalah(){
		assertEquals(0, player.getKalah().getCountOfStones());
		player.putAllStonesToKalah();
		assertEquals(36, player.getKalah().getCountOfStones());
		assertEquals(0, player.countStonesOnPits());
	}

}
