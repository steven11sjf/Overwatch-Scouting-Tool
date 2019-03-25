package com.hyperbola0.scoutingtool;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MatchStats {

	private String url;
	private Document doc;

	private String team1Name;
	private String team2Name;

	private String[] t1tags;
	private String[] t2tags;

	/**
	 * Gets match stats from the Tespa URL provided
	 * 
	 * @param tespaurl the match link emailed to team members
	 */
	public MatchStats(String tespaurl) {
		this.url = tespaurl;
		this.t1tags = new String[9];
		this.t2tags = new String[9];

		try {
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		getTeamNames(); // get team names
		getPlayers(); // get players' battletags
	}

	/**
	 * Gets the name of each team from the tespa page
	 */
	private void getTeamNames() {
		String t1name = doc.select("div.match-detail__team1 > a > h2").first().text(); // team 1 name
		String t2name = doc.select("div.match-detail__team2 > a > h2").first().text(); // team 2 name

		this.team1Name = t1name;
		this.team2Name = t2name;
	}

	/**
	 * Gets the name of the players for each team
	 */
	private void getPlayers() {
		Elements t1players = doc.select("div.match-detail__players div.match-detail__players_team1 p");
		Elements t2players = doc.select("div.match-detail__players div.match-detail__players_team2 p");
		Element player;

		for (int i = 0; i < 9; i++) { // loop through players 1-9
			if (t1players.size() > i) { // if there are 6-8 players on team 1, makes sure it stops reading after last
										// player
				player = t1players.get(i);
				this.t1tags[i] = player.select("span").text(); // saves battletag to t1tags[i]
			}

			if (t2players.size() > i) { // see above if statement, same for team 2
				player = t2players.get(i);
				this.t2tags[i] = player.select("span").text();
			}
		}
	}

	/**
	 * getter func for team 1 name
	 * 
	 * @return team 1 name
	 */
	public String getTeam1Name() {
		return this.team1Name;
	}

	/**
	 * getter func for team 2 name
	 * 
	 * @return team 2 name
	 */
	public String getTeam2Name() {
		return this.team2Name;
	}

	/**
	 * getter function for team 1 roster
	 * 
	 * @return array containing team 1 battletags
	 */
	public String[] getTeam1Roster() {
		return this.t1tags;
	}

	/**
	 * getter function for team 2 roster
	 * 
	 * @return array containing team 2 battletags
	 */
	public String[] getTeam2Roster() {
		return this.t2tags;
	}
}
