package com.hyperbola0.scoutingtool;

import com.hyperbola0.scoutingtool.gui.GUI;
import com.hyperbola0.scoutingtool.gui.Player;

public class UpdatePlayers extends Thread {

	private GUI gui;

	/**
	 * constructor for the thread
	 * 
	 * @param g the gui
	 */
	public UpdatePlayers(GUI g) {
		this.gui = g;
	}

	/**
	 * updates each player in a separate thread from the main thread, so the gui
	 * updates as the players are updated instead of all at the end.
	 */
	public void run() {
		int num = 0; // sr of refreshed player
		int numFound = 0; // number of players with valid sr
		int sr = 0; // total sr

		for (Player p : gui.players) {
			p.clear(); // clear all players' panels
		}

		for (Player p : gui.players) {
			if ((num = p.refresh()) > 0) { // refresh player, if sr > 0 update sr and numfound
				sr += num; // add num to sr
				numFound++; // increment numFound
			}
		}

		if (numFound == 0) { // makes sure players were found to avoid division by zero
			gui.m.teamSR.setText("Average team SR: No Team Members Found");
			return;
		}

		int avgSR = sr / numFound; // compute average sr

		gui.m.teamSR.setText("Average team SR: " + Integer.toString(avgSR)); // update gui
	}
}
