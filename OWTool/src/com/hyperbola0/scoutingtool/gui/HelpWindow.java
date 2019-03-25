package com.hyperbola0.scoutingtool.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame {

	/**
	 * The window that shows up when the help button is pressed. Basically contains
	 * the readme.
	 */
	public HelpWindow() {
		this.setTitle("Scouting Tool Help");
		this.setSize(600, 500);

		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setFont(text.getFont().deriveFont(16f));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(text); // add scrollbar
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		StringBuilder sb = new StringBuilder();
		sb.append("\tOverwatch Scouting Tool\n");
		sb.append("\t        By hyperbola0\n");
		sb.append("\n");
		sb.append("This tool scrapes a Tespa Overwatch match link, playoverwatch.com and overbuff to ");
		sb.append("scout players.");
		sb.append("The tool retrieves players' status as private or public profiles, their current SR, ");
		sb.append("and their top 5 quickplay and competitive hero playtimes.\n");
		sb.append("\n");

		sb.append("VERSION 3 RELEASE NOTES\n");
		sb.append(" * BANNERS! Each player now has a banner corresponding to their competitive rank!\n");
		sb.append("   - They're shitty MS paint banners, don't judge please. I'm not an artist lol.\n");
		sb.append("   - There's also a banner for private profiles\n");
		sb.append(" * Players automatically refresh upon clicking your team name\n");
		sb.append("   - The team's average SR is now shown where the refresh all button used to be\n");
		sb.append("   - Also the readme is now included with the help button, which I assume is how you got here\n");
		sb.append(
				" * You can now see the players' info as soon as its found, instead of waiting for the whole team to load\n");
		sb.append(" * Bug fixes:\n");
		sb.append("   - Fixed a bug where a player's top characters were not shown correctly\n");
		sb.append("\n");

		sb.append("Requirements:\n");
		sb.append(" - Java 1.8 or later\n");
		sb.append(" - Internet access\n");
		sb.append(" - The Tespa match link (sent by email)\n");
		sb.append("\n");

		sb.append("Use:\n");
		sb.append("The window is divided into ten sections. The top-left box contains match information, ");
		sb.append("while the remaining sections contain info on each player.\n");
		sb.append("\n");

		sb.append("Paste the Tespa Compete match link into the top-right text box and press \"Fetch match\". ");
		sb.append("The tool will change the two buttons below to show the teams competing in the match. ");
		sb.append(
				"Pressing either team's button will fill in the battletags for the team members and scrape the team's info. ");
		sb.append("The team's average SR is calculated and displayed in the match panel.\n\n");
		sb.append(
				"For each player, the SR is shown next to their battletag and their top heroes are displayed underneath. ");
		sb.append(
				"Pressing \"Player URL\" will copy the playoverwatch.com url to your clipboard, where you can paste it in your browser. ");
		sb.append(
				"If the player has a private profile or the profile is not found, the program will check for an overbuff profile, ");
		sb.append("which sometimes has info from previous seasons. ");
		sb.append("If info is pulled from Overbuff, an asterisk is displayed by their SR.\n");
		sb.append("\n");

		sb.append("Known Bugs: \n");
		sb.append(" - The player must have their info refreshed before \"Player URL\" functions. \n");
		sb.append(" - Player info and banners load unevenly");
		sb.append("\n");

		sb.append("Reporting Bugs: \n");
		sb.append("The best way to get in touch with me is DMs through: \n");
		sb.append("\tDiscord: hyperbola0#4962\n");
		sb.append("\tReddit: u/hyperbola0");

		text.setText(sb.toString());
		text.setCaretPosition(0); // start at beginning of readme
		add(scroll);
		setVisible(true);
	}
}
