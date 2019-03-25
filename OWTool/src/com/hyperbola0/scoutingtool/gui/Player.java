package com.hyperbola0.scoutingtool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hyperbola0.scoutingtool.GetStats;

@SuppressWarnings("serial")
public class Player extends JPanel implements ActionListener {

	Color defaultColor; // default color of box, used for clear

	BufferedImage empty;
	BufferedImage bronze;
	BufferedImage silver;
	BufferedImage gold;
	BufferedImage plat;
	BufferedImage diamond;
	BufferedImage master;
	BufferedImage gm;
	BufferedImage pp;

	private JPanel main;
	private JPanel header;
	private JPanel heroes;
	private JPanel qpHeroes;
	private JPanel compHeroes;

	private JLabel nameLabel = new JLabel("Battletag: ");
	private JLabel srLabel = new JLabel("SR: ");
	private JLabel qpHeroNameHeader = new JLabel("QP Hero");
	private JLabel qpHeroName1 = new JLabel();
	private JLabel qpHeroName2 = new JLabel();
	private JLabel qpHeroName3 = new JLabel();
	private JLabel qpHeroName4 = new JLabel();
	private JLabel qpHeroName5 = new JLabel();
	private JLabel qpHeroTimeHeader = new JLabel("Time");
	private JLabel qpHeroTime1 = new JLabel();
	private JLabel qpHeroTime2 = new JLabel();
	private JLabel qpHeroTime3 = new JLabel();
	private JLabel qpHeroTime4 = new JLabel();
	private JLabel qpHeroTime5 = new JLabel();
	private JLabel compHeroNameHeader = new JLabel("Comp Hero");
	private JLabel compHeroName1 = new JLabel();
	private JLabel compHeroName2 = new JLabel();
	private JLabel compHeroName3 = new JLabel();
	private JLabel compHeroName4 = new JLabel();
	private JLabel compHeroName5 = new JLabel();
	private JLabel compHeroTimeHeader = new JLabel("Time");
	private JLabel compHeroTime1 = new JLabel();
	private JLabel compHeroTime2 = new JLabel();
	private JLabel compHeroTime3 = new JLabel();
	private JLabel compHeroTime4 = new JLabel();
	private JLabel compHeroTime5 = new JLabel();

	private JLabel icon;

	JTextField nameTextField = new JTextField();

	private JButton refreshButton = new JButton("Refresh");
	private JButton copyLink = new JButton("Player URL");

	private GetStats player;

	/**
	 * Creates a new player
	 */
	public Player() {

		try {
			empty = ImageIO.read(this.getClass().getResource("/Banners/empty.png"));
			bronze = ImageIO.read(this.getClass().getResource("/Banners/bronze.png"));
			silver = ImageIO.read(this.getClass().getResource("/Banners/silver.png"));
			gold = ImageIO.read(this.getClass().getResource("/Banners/gold.png"));
			plat = ImageIO.read(this.getClass().getResource("/Banners/plat.png"));
			diamond = ImageIO.read(this.getClass().getResource("/Banners/diamond.png"));
			master = ImageIO.read(this.getClass().getResource("/Banners/master.png"));
			gm = ImageIO.read(this.getClass().getResource("/Banners/gm.png"));
			pp = ImageIO.read(this.getClass().getResource("/Banners/private.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setLayout(new FlowLayout());
		refreshButton.addActionListener(this);
		copyLink.addActionListener(this);

		header = new JPanel();
		header.setLayout(new GridLayout(1, 5));
		header.add(nameLabel);
		header.add(nameTextField);
		header.add(refreshButton);
		header.add(srLabel);
		header.add(copyLink);

		defaultColor = header.getBackground();

		qpHeroes = new JPanel();
		qpHeroes.setLayout(new GridLayout(6, 2));
		qpHeroes.setBorder(BorderFactory.createLineBorder(Color.black));
		qpHeroes.setBackground(Color.decode("#ffffff"));

		qpHeroes.add(qpHeroNameHeader);
		qpHeroes.add(qpHeroTimeHeader);
		qpHeroes.add(qpHeroName1);
		qpHeroes.add(qpHeroTime1);
		qpHeroes.add(qpHeroName2);
		qpHeroes.add(qpHeroTime2);
		qpHeroes.add(qpHeroName3);
		qpHeroes.add(qpHeroTime3);
		qpHeroes.add(qpHeroName4);
		qpHeroes.add(qpHeroTime4);
		qpHeroes.add(qpHeroName5);
		qpHeroes.add(qpHeroTime5);

		compHeroes = new JPanel();
		compHeroes.setLayout(new GridLayout(6, 2));
		compHeroes.setBorder(BorderFactory.createLineBorder(Color.black));
		compHeroes.setBackground(Color.decode("#ffffff"));

		compHeroes.add(compHeroNameHeader);
		compHeroes.add(compHeroTimeHeader);
		compHeroes.add(compHeroName1);
		compHeroes.add(compHeroTime1);
		compHeroes.add(compHeroName2);
		compHeroes.add(compHeroTime2);
		compHeroes.add(compHeroName3);
		compHeroes.add(compHeroTime3);
		compHeroes.add(compHeroName4);
		compHeroes.add(compHeroTime4);
		compHeroes.add(compHeroName5);
		compHeroes.add(compHeroTime5);

		heroes = new JPanel();
		heroes.setLayout(new GridLayout(1, 2));
		heroes.add(qpHeroes);
		heroes.add(compHeroes);

		main = new JPanel();
		main.setPreferredSize(new Dimension(590, 145));
		main.setLayout(new BorderLayout());
		main.add(header, BorderLayout.NORTH);
		main.add(heroes, BorderLayout.CENTER);
		this.setBackground(Color.decode("#ffffff"));

		icon = new JLabel(new ImageIcon(empty));
		add(icon);

		add(main);
		this.setVisible(true);

		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(Color.decode("#ffffff"));
	}

	/**
	 * Refreshes the player's info and returns their sr
	 * @return the player's sr, or 0 if it is not found
	 */
	public int refresh() {
		String playerTag = nameTextField.getText();

		if (playerTag.equals(""))
			return 0;

		playerTag = playerTag.replace('#', '-'); // replaces # with -, if necessary

		this.player = new GetStats(playerTag); // calls GetStats for player tag

		String[][] qHeroes = new String[5][2];
		String[][] cHeroes = new String[5][2];
		qHeroes = player.getTopQPHeroes();
		cHeroes = player.getTopCompHeroes();

		int sr = player.getSR();
		int scrapeStatus = player.getScrapeStatus();

		this.removeAll(); // remove all elements from panel

		if (scrapeStatus == -2) { // player not found
			srLabel.setText("SR: Player Not Found");
			icon = new JLabel(new ImageIcon(empty));
			this.add(icon);
			this.add(main);
			return -1;
		} else if (scrapeStatus == -1) { // sr is from previous season
			srLabel.setText("SR: " + Integer.toString(sr) + "*");
		} else if (scrapeStatus == 0) { // private profile
			srLabel.setText("SR: Private Profile");
			icon = new JLabel(new ImageIcon(pp));
			this.add(icon);
			this.add(main);
			return -1;
		} else { // sr found normally
			srLabel.setText("SR: " + Integer.toString(sr));
		}

		if (sr > 0 && sr < 1500) { // Bronze
			icon = new JLabel(new ImageIcon(bronze));
		} else if (sr < 2000) { // Silver
			icon = new JLabel(new ImageIcon(silver));
		} else if (sr < 2500) { // Gold
			icon = new JLabel(new ImageIcon(gold));
		} else if (sr < 3000) { // Plat
			icon = new JLabel(new ImageIcon(plat));
		} else if (sr < 3500) { // Diamond
			icon = new JLabel(new ImageIcon(diamond));
		} else if (sr < 4000) { // Master
			icon = new JLabel(new ImageIcon(master));
		} else if (sr >= 4000) { // GM
			icon = new JLabel(new ImageIcon(gm));
		}
		this.add(icon);
		this.add(main);

		this.qpHeroName1.setText(qHeroes[0][0]);
		this.qpHeroName2.setText(qHeroes[1][0]);
		this.qpHeroName3.setText(qHeroes[2][0]);
		this.qpHeroName4.setText(qHeroes[3][0]);
		this.qpHeroName5.setText(qHeroes[4][0]);
		this.qpHeroTime1.setText(qHeroes[0][1]);
		this.qpHeroTime2.setText(qHeroes[1][1]);
		this.qpHeroTime3.setText(qHeroes[2][1]);
		this.qpHeroTime4.setText(qHeroes[3][1]);
		this.qpHeroTime5.setText(qHeroes[4][1]);

		this.compHeroName1.setText(cHeroes[0][0]);
		this.compHeroName2.setText(cHeroes[1][0]);
		this.compHeroName3.setText(cHeroes[2][0]);
		this.compHeroName4.setText(cHeroes[3][0]);
		this.compHeroName5.setText(cHeroes[4][0]);
		this.compHeroTime1.setText(cHeroes[0][1]);
		this.compHeroTime2.setText(cHeroes[1][1]);
		this.compHeroTime3.setText(cHeroes[2][1]);
		this.compHeroTime4.setText(cHeroes[3][1]);
		this.compHeroTime5.setText(cHeroes[4][1]);

		return sr; // returns the sr, or the reason it wasn't found
	}

	/**
	 * Clears all info and basically resets the player. 
	 */
	public void clear() {
		srLabel.setText("SR:");

		this.qpHeroName1.setText("");
		this.qpHeroName2.setText("");
		this.qpHeroName3.setText("");
		this.qpHeroName4.setText("");
		this.qpHeroName5.setText("");
		this.qpHeroTime1.setText("");
		this.qpHeroTime2.setText("");
		this.qpHeroTime3.setText("");
		this.qpHeroTime4.setText("");
		this.qpHeroTime5.setText("");

		this.compHeroName1.setText("");
		this.compHeroName2.setText("");
		this.compHeroName3.setText("");
		this.compHeroName4.setText("");
		this.compHeroName5.setText("");
		this.compHeroTime1.setText("");
		this.compHeroTime2.setText("");
		this.compHeroTime3.setText("");
		this.compHeroTime4.setText("");
		this.compHeroTime5.setText("");

		icon = new JLabel(new ImageIcon(empty));
		this.removeAll();
		this.add(icon);
		this.add(main);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source.equals(refreshButton)) {
			refresh();
		} else if (source.equals(copyLink)) {
			player.copyUrlToClipboard();
		}
	}
}
