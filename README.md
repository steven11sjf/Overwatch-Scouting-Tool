# !!! This tool is deprecated !!!
When this was written, it was in fact able to do all this. However, that was like 3 years ago. A fair amount has changed. 

For one, the Tespa league is no longer running, which means the main functionality (automatically getting all teams' info from one link) is not working. It could be made to work with other tournaments, but I don't care enough to go to the effort, nobody uses this anymore anyways. 

Another, and bigger issue, is that Overwatch introduced role queue to the competitive mode so their website's career profile format has changed. So even if you manually enter a battletag, it doesn't work. This would require a significant amount of work to fix, I would need to add three different SRs for each player to the GUI, and adjust the web scraper code to find where everything is again. 

So in conclusion, this is mostly a relic of the past. The program still opens, and I think the code is pretty well documented, so I'll leave it on my page. I wish I'd taken screenshots but that was from an old computer that I no longer have and I don't think any images have survived. 

# Overwatch Scouting Tool
A scouting tool that scouts up to 9 players at a time, and is designed to scout Tespa teams easily. Support for other tournaments (such as Open Division) is also available. 

## Requirements
- Java 1.8 or later
- Internet access
- The Tespa match link (sent by email)
  - Or, a text file containing a team's roster
  - Or, each specific battletag

## Use
The window is divided into ten sections. The top-left box contains match information, while the remaining sections contain info on each player.

Paste the Tespa Compete match link into the top-right text box and press "Fetch match". The tool will change the two buttons below to show the teams competing in the match. Pressing either team's button will fill in the battletags for the team members and scrape the team's info. The team's average SR is calculated and displayed in the match panel.

For each player, the SR is shown next to their battletag and their top heroes are displayed underneath. Pressing "Player URL" will copy the playoverwatch.com url to your clipboard, where you can paste it in your browser. If the player has a private profile or the profile is not found, the program will check for an overbuff profile, which sometimes has info from previous seasons. If info is pulled from Overbuff, an asterisk is displayed by their SR.



## Version 4 Release Notes
- Rudimentary support for tournaments other than Tespa!
  - Highlight and copy the list of players on a team, and paste it into \'roster.txt\'. Run the jar, and the program will scan the text document for battletags and autofill the first 9 battletags found in the document.
  - This means that you don't need to copy each battletag individually, or clear out extra characters that were highlighted. The program should do all the work for you.
  - One thing to note is that this does not work with non-ANSI characters. So if the player uses non-English characters or funky accents you likely need to enter it in manually.
- Bug Fixes
  - In my quest to make crappy rank banners in paint, I grabbed a GM icon instead of a Masters icon. The masters rank now exists, and GM starts from 4000 as it should. 

## Known Bugs: 
 - The player must have their info refreshed before "Player URL" functions. 
 - Player info and banners load unevenly

### Reporting Bugs: 
The best way to get in touch with me is DMs through: 
	Discord: hyperbola0#4962
	Reddit: u/hyperbola0
