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
	
	public GUI() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Overwatch Scouting Tool");
		this.setSize(1200, 1000);
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(5, 2));
		
		players = new Player[9];
		for(int i = 0; i < 9; i++) {
			players[i] = new Player();
		}
		
		m = new Match(this);
		
		main.add(m);
		
		for(int i = 0; i < 9; i++) {
			main.add(players[i]);
		}
		
		add(main);
		setVisible(true);
	}
	
	public GUI(String[] tags) {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Overwatch Scouting Tool");
		this.setSize(1200, 1000);
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(5, 2));
		
		players = new Player[9];
		for(int i = 0; i < 9; i++) {
			players[i] = new Player();
			players[i].nameTextField.setText(tags[i]);
		}
		
		m = new Match(this);
		
		main.add(m);
		
		for(int i = 0; i < 9; i++) {
			main.add(players[i]);
		}
		
		add(main);
		setVisible(true);
	}
	
	public void setPlayerNames(String[] names) {
		for(int i = 0; i < 9; i++) {
			players[i].nameTextField.setText(names[i]);
		}
	}
	
	public int refreshAll() {
		int num = 0;
		int numFound = 0;
		int sr = 0;
		
		for(Player p : players) {
			p.clear();
			
			if((num = p.refresh()) > 0) {
				sr += num;
				numFound++;
			}
		}
		
		int avgSR = sr / numFound;
		
		return avgSR;
	}
	
	public static void main(String[] args) {
		if(args.length == 0) // start in classic mode if no file is provided
			new GUI();
		else {
			if (args.length > 1) {// if there is more than 1 argument supplied
				System.out.println("Usage: Scouting Tool v4.jar [file]");
				System.out.println("    [file] : A file containing a list of battletags");
				
				return;
			}
			
				String next;
				int index = 0;
				String[] tags = new String[9];
				File file = new File(args[0]);
				
				try {
					Scanner scan = new Scanner(file);
					while(scan.hasNext()) {
						next = scan.next();
						// check if next is a battletag
						if(next.matches("^.{3,12}#[0-9]{4,6}$")) { // if it's a battletag (3-12 chars w/ 4-6 digit identifier
							tags[index]=next;
							index++;
						}
						
						// break out of loop if there are 9 tags read
						if(index==9) break;
					}
					
					scan.close(); // close scanner
					
					new GUI(tags); // launch gui with tags embedded
				} catch (FileNotFoundException e) {
					System.out.println("Error: File " + args[0] + " not found!");
					return;
				}
		}
	}
}
