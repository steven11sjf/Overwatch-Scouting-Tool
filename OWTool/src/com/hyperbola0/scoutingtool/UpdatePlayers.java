package com.hyperbola0.scoutingtool;

import com.hyperbola0.scoutingtool.gui.GUI;
import com.hyperbola0.scoutingtool.gui.Player;

public class UpdatePlayers extends Thread {
	
	private GUI gui;
	
	public UpdatePlayers(GUI g) {
		this.gui = g;
	}
	
	public void run() {
		int num = 0;
		int numFound = 0;
		int sr = 0;
		
		
		for(Player p : gui.players) {
			p.clear();
		}
		
		for(Player p : gui.players) { 
			if((num = p.refresh()) > 0) {
				sr += num;
				numFound++;
			}
		}
		
		int avgSR = sr / numFound;
		
		gui.m.teamSR.setText("Average team SR: " + Integer.toString(avgSR));
	}
}
