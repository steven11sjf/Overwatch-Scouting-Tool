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
	
	public MatchStats(String tespaurl) {
		this.url = tespaurl;
		this.t1tags = new String[9];
		this.t2tags = new String[9];
		
		try {
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getTeamNames();
		getPlayers();
	}
	
	private void getTeamNames() {
		String t1name = doc.select("div.match-detail__team1 > a > h2").first().text();
		String t2name = doc.select("div.match-detail__team2 > a > h2").first().text();
		
		this.team1Name = t1name;
		this.team2Name = t2name;
	}
	
	private void getPlayers() {
		Elements t1players = doc.select("div.match-detail__players div.match-detail__players_team1 p");
		Elements t2players = doc.select("div.match-detail__players div.match-detail__players_team2 p");
		Element player;
	
		for(int i = 0; i < 9; i++) {
			if(t1players.size() > i) {
				player = t1players.get(i);
				this.t1tags[i] = player.select("span").text();
			}
		
			if(t2players.size() > i) {
				player = t2players.get(i);
				this.t2tags[i] = player.select("span").text();
			}
		
		}
	}

	
	public String getTeam1Name() {
		return this.team1Name;
	}
	
	public String getTeam2Name() {
		return this.team2Name;
	}
	
	public String[] getTeam1Roster() {
		return this.t1tags;
	}
	
	public String[] getTeam2Roster() {
		return this.t2tags;
	}
}
