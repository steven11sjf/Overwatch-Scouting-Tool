package com.hyperbola0.scoutingtool;

import java.io.IOException;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetStats {

	// ============ PLAYER INFO =============
	private String btag; // player's battletag
	private String url; // playoverwatch.com url
	private Document doc; // the playoverwatch.com profile
	
	// ============ SCRAPED DATA ============
	private boolean profileFound, publicProfile;
	public boolean currentSR;
	private int sr; // the player's sr
	private String[][] topHeroes = new String[5][2]; // their top heroes' names
	private String[][] topCompHeroes = new String[5][2]; // their top heroes' names
	
	public GetStats(String playerTag) {
		this.btag = playerTag;
		this.currentSR = false;
		this.url = "https://playoverwatch.com/en-us/career/pc/" + btag;
		try {
			this.doc = Jsoup.connect(this.url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		// checks if a public-profile player is found
		checkProfileVisibility();
		if(profileFound && publicProfile) {
			// scrapes information and puts it into data
			findSR();
			findTopQPHeroes();
			findTopCompHeroes();
		}
	}
	
	public int getScrapeStatus() {
		if(!profileFound) {
			return -2;
		} else if(!publicProfile) {
			return 0;
		} else if(!currentSR) {
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * Checks whether the profile exists and is public; marks them as so if they are. 
	 */
	private void checkProfileVisibility() {
		if(doc.title().equals("Overwatch")) {
			log("Profile not found!");
			this.profileFound = false;
			return;
		} else {
			this.profileFound = true;
		}
		
		Element visibility = doc.selectFirst("p.masthead-permission-level-text");
		if(visibility.text().equals("Public Profile")) {
			this.publicProfile = true;
		} else {
			this.publicProfile = false;
		}
	}
	
	/**
	 * Internal function that gets the SR of the player
	 */
	private void findSR() {
		try {
			Element rank = doc.selectFirst("div.competitive-rank"); // contains comp rank
			log(rank.text()); // send to the log
			int sr = Integer.parseInt(rank.text()); // gets their sr as an int
			this.sr = sr;
			this.currentSR = true;
		} catch (NullPointerException e) { // thrown if the player does not have a sr in the current season
			log("Player does not have SR");
			if(!findSR_Overbuff())
				this.sr = -1;
		} catch (NumberFormatException e) { // if it's not a number in there, means blizz fucked up
			this.sr = -2;
		}
	}
	
	private boolean findSR_Overbuff() {
		String url = "https://www.overbuff.com/players/pc/" + btag;
		try {
			Document doc = Jsoup.connect(url).get();
			
			Element rank = doc.selectFirst("span.color-stat-rating span.player-skill-rating"); // contains comp rank
			int sr = Integer.parseInt(rank.text()); // gets their sr as an int
			this.sr = sr;
			this.currentSR = false;
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (NullPointerException e) { // thrown if the player does not have a sr in the current season
			return false;
		} catch (NumberFormatException e) { // if it's not a number in there, means blizz fucked up
			return false;
		}
	}
	
	/**
	 * Internal function that scrapes the site for top qp heroes
	 */
	private void findTopQPHeroes() {
		try {
			Element section = doc.select("div#quickplay section div div.progress-category").first(); // the section for the QP heroes tab
			Elements heroes = section.select("div.progressbar-TextWrapper"); // creates an array of the hero elements in section
			
			// gets the top 5 heroes and puts info in the arrays
			for(int i = 0; i < 5; i++) {
				Element h = heroes.get(i);
				this.topHeroes[i][0] = h.select("div.ProgressBar-title").text();
				this.topHeroes[i][1] = h.select("div.ProgressBar-description").text();
			}
		} catch (IndexOutOfBoundsException e) { // player hasn't played 5 QP heroes
			log("Player has not played 5 heroes in QP");
		} catch (NullPointerException e) {
			log("Player has a private profile");
			this.publicProfile = false;
		}
		
		// fixes the bug where it would list 0 play time for heroes
		// reloads the html using jsoup and then gets the top heroes
		if(this.topHeroes[0][1].equals("0"))
			update();
	}
	
	private void update() {
		try {
			this.doc = Jsoup.connect(this.url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}		
		findTopQPHeroes();
		findTopCompHeroes();
	}

	/**
	 * Internal function that scrapes the site for top comp heroes
	 */
	private void findTopCompHeroes() {
		if(!publicProfile) return;
		try {
			Element section = doc.select("div#competitive section div div.progress-category").first(); // the section for the comp heroes tab
			Elements heroes = section.select("div.ProgressBar-TextWrapper"); // creates an array of the hero elements in section
			
			// gets the top 5 heroes and puts info in the arrays
			for(int i = 0; i < 5; i++) {
				Element h = heroes.get(i);
				this.topCompHeroes[i][0] = h.select("div.ProgressBar-title").text();
				this.topCompHeroes[i][1] = h.select("div.ProgressBar-description").text();
			}
		} catch (IndexOutOfBoundsException e) { // player hasn't played 5 Comp heroes
			log("Player has not played 5 heroes in Competitive");
		}
	}
	
	/**
	 * Internal function that prints to a log
	 * @param msg
	 * @param vals
	 */
	public static void log(String msg) {
		System.out.println(String.format("LOG: " + msg));
	}
	
	public int getSR() {
		return this.sr;
	}
	
	public String[][] getTopQPHeroes() {
		return this.topHeroes;
	}
	
	public String[][] getTopCompHeroes() {
		return this.topCompHeroes;
	}
	
	public void copyUrlToClipboard() {
		StringSelection stringSelection = new StringSelection(this.url);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	/**
	 * Returns this player's info as a string
	 */
	public String toString() {
		if(!profileFound) return this.btag + "\nProfile not found!";
		if(!publicProfile) return this.btag + "\nPrivate Profile!";
		
		String result = "";
		result += this.btag + "\n";
		result += "SR: " + this.sr + "\n\n";
		
		result += "Top Heroes:\n";
		for(int i = 0; i < 5; i++) {
			if(this.topHeroes[i][0] == null) break;
			result += this.topHeroes[i][0] + "\t" + this.topHeroes[i][1] + "\n";
		}
		
		result += "\nTop Comp Heroes:\n";
		for(int i = 0; i < 5; i++) {
			if(this.topCompHeroes[i][0] == null) break;
			result += this.topCompHeroes[i][0] + "\t" + this.topCompHeroes[i][1] + "\n";
		}
		
		return result;
	}
}
