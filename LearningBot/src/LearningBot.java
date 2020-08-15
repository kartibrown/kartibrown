import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class LearningBot extends PApplet {
	private Bot bot;
	private ChatGraphics cg;
	public static List<String> chat;
	private String userInput;

	@Override
	public void settings() {
		size(800, 600);
	}

	@Override
	public void setup() {
		bot = new Bot(this);
		cg = new ChatGraphics(this);

		chat = new ArrayList<String>();

		userInput = "";

		if (!Files.exists(Paths.get("bot.txt"))) {
			println("File does not exist!");
			chat.add("This is your first time opening this program.");
			chat.add("I learn to talk to you by writing \"Learn hello Answer Hi\".");
			chat.add("If you say hello I will answer Hi");

			try {
				Files.createFile(Paths.get("bot.txt"));
				println("File created!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw() {
		background(100);

		cg.chatBox(chat);
		cg.textBox(userInput);

		if(chat.size() > 0) {
			if (isGoodBye(chat.get(chat.size()-1))) {
					exit();
			}
		}
	}

	private boolean isGoodBye(String chat) {
		boolean isGoodbye = false;

		List<String> byes = new ArrayList<String>();

		// All goodbyes
		byes.add("bye");
		byes.add("bye!");
		byes.add("goodbye");
		byes.add("goodbye!");

		for (int i = 0; i < byes.size(); i++) {
			if (chat.contains(byes.get(i))) {
				isGoodbye = true;
			} else if (chat.equalsIgnoreCase(byes.get(i))) {
				isGoodbye = true;
			} else if (chat.contentEquals(byes.get(i))) {
				isGoodbye = true;
			}
		}

		return isGoodbye;
	}

	@Override
	public void keyPressed() {
		if (keyCode == BACKSPACE) {
			if (userInput.length() > 0) {
				userInput = userInput.substring(0, userInput.length() - 1);
			}
		}

		if (keyCode == ENTER) {
			if (userInput.contains("Learn ") && userInput.contains("Answer ")) {
				chat.add("You: " + userInput);
				bot.learn(userInput);
			} else {
				chat.add("You: " + userInput);
				bot.answer(userInput);
			}

			userInput = "";
		}

		if ((key >= 'a' && key <= 'ö') || (key >= 'A' && key <= 'Ö') || (key == ' ') || (key >= '!' && key <= '?')) {
			userInput += key;
		}
	}

	public static void main(String[] args) {
		PApplet.main("LearningBot");
	}
}
