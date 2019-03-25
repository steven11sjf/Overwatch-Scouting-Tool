package com.hyperbola0.scoutingtool.gui;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public Player[] players;

	public Match m;

	/**
	 * Creates a new GUI
	 */
	public GUI() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Overwatch Scouting Tool");
		this.setSize(1200, 1000);

		JPanel main = new JPanel(); // main layout of gui, 5 rows 2 cols
		main.setLayout(new GridLayout(5, 2));

		// declares and initializes players
		players = new Player[9];
		for (int i = 0; i < 9; i++) {
			players[i] = new Player();
		}

		// initializes match
		m = new Match(this);

		main.add(m); // add match panel to main jpanel

		// add players to main jpanel
		for (int i = 0; i < 9; i++) {
			main.add(players[i]);
		}

		add(main); // add main to the gui
		setVisible(true); // make gui visible
	}

	/**
	 * Creates a new GUI with given battletags. Mostly the same as the regular
	 * constructor
	 * 
	 * @param tags the battletags of the players
	 */
	public GUI(String[] tags) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Overwatch Scouting Tool");
		this.setSize(1200, 1000);

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(5, 2));

		players = new Player[9];
		for (int i = 0; i < 9; i++) {
			players[i] = new Player();
		}

		m = new Match(this);

		main.add(m);

		for (int i = 0; i < 9; i++) {
			main.add(players[i]);
		}

		add(main);
		setPlayerNames(tags); // sets the players' names to the tags
		refreshAll(); // refresh all players
		setVisible(true);
	}

	/**
	 * sets player names, used when run with a file or when teams are loaded from
	 * the tespa match link
	 * 
	 * @param names the names that are being loaded in
	 */
	public void setPlayerNames(String[] names) {
		for (int i = 0; i < 9; i++) {
			players[i].nameTextField.setText(names[i]);
		}
	}

	/**
	 * Refreshes all players entered in the program, and returns the team's average
	 * SR
	 * 
	 * @return the average SR, or 0 if no valid SR's were found
	 */
	public int refreshAll() {
		int num = 0; // sr of refreshed player
		int numFound = 0; // number of players who found a valid sr
		int sr = 0; // total team sr

		// loop through players
		for (Player p : players) {
			p.clear(); // clear the profile to make sure previous player is fully overwritten

			if ((num = p.refresh()) > 0) { // refresh player, check that return value >= 0
				sr += num; // add their sr
				numFound++; // increment numFound
			}
		}

		if (numFound == 0) // if nobody was found, return 0 to avoid division by 0
			return 0;

		int avgSR = sr / numFound; // calculate average SR

		return avgSR;
	}

	/**
	 * main function, launches a new gui. If a file is input, parses for battletags
	 * and runs gui with the first 9 found.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) // start in "classic" mode if no file is provided
			new GUI();
		else {
			if (args.length > 1) {// if there is more than 1 argument supplied
				System.out.println("Usage: Scouting Tool v4.jar [file]");
				System.out.println("    [file] : A file containing a list of battletags");

				return;
			}

			String next; // next word parsed
			int index = 0; // number of battletags found
			String[] tags = new String[9]; // array for holding battletags
			File file = new File(args[0]); // open new file

			try {
				Scanner scan = new Scanner(file); // make new scanner for file
				while (scan.hasNext()) { // scan until eof
					next = scan.next(); // scan next word
					// check if next is a battletag
					if (next.matches("^.{3,12}#[0-9]{4,6}$")) { // if it's a battletag (3-12 chars w/ 4-6 digit
																// identifier
						tags[index] = next; // put it in tags
						index++; // increment index
					}

					// break out of loop if there are 9 tags read
					if (index == 9)
						break;
				}

				scan.close(); // close scanner

				new GUI(tags); // launch gui with tags from input
			} catch (FileNotFoundException e) { // thrown if file doesn't exist
				System.out.println("Error: File " + args[0] + " not found!");
				new GUI(); // load gui without input
			}
		}
	}
}