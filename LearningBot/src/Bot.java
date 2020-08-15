import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Bot {
	private PApplet parent;

	private File f;

	private String botAnswer;
	
	public static boolean answered;

	public Bot(PApplet parent) {
		this.parent = parent;
		
		f = new File("bot.txt");
		
		botAnswer = "";
	}

	public void answer(String userInput) {
		try {
			BufferedReader reader = parent.createReader("bot.txt");

			List<String> answers = new ArrayList<String>();
			boolean canAnswer = false;
			String line = null;

			while ((line = reader.readLine()) != null) {
				String newLine = line;
				newLine = newLine.substring(newLine.indexOf("|") + 1);
				newLine = newLine.substring(0, newLine.indexOf("|"));
				newLine = newLine.substring(0, newLine.lastIndexOf(" "));

				if (newLine.equalsIgnoreCase(userInput)) {
					answers.add(line.substring(line.lastIndexOf("|") + 1));
					canAnswer = true;
				}
			}

			if (canAnswer) {
				botAnswer = answers.get((int) parent.random(answers.size()));
			} else {
				botAnswer = "I haven't learned what to answer to \"" + userInput + "\" yet.";
			}
			
			LearningBot.chat.add("Bot: " + botAnswer);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void learn(String userInput) {
		userInput = userInput.replaceAll("Learn ", "|");
		userInput = userInput.replaceAll("Answer ", "|");

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
			BufferedReader reader = parent.createReader("bot.txt");

			boolean stringIsEqual = false;
			String line = null;

			while ((line = reader.readLine()) != null) {

				if (line.equalsIgnoreCase(userInput)) {
					stringIsEqual = true;
				}
			}

			if (stringIsEqual) {

				String iSay = userInput;
				iSay = iSay.substring(iSay.lastIndexOf("|") + 1);

				botAnswer = "I've already learned to say \"" + iSay + "\".";
			} else {
				pw.println(userInput);

				String uSay = userInput;
				uSay = uSay.substring(uSay.indexOf("|") + 1);
				uSay = uSay.substring(0, uSay.indexOf("|"));
				uSay = uSay.substring(0, uSay.lastIndexOf(" "));

				String iSay = userInput;
				iSay = iSay.substring(iSay.lastIndexOf("|") + 1);

				botAnswer = "If you say \"" + uSay + "\" I will answer \"" + iSay + "\".";
			}
			
			LearningBot.chat.add("Bot: " + botAnswer);

			reader.close();
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
