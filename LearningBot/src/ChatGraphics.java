import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;

public class ChatGraphics {
	private PApplet parent;
	
	private int minChat = 0;
	private int chatHeightModifier = 0;

	public ChatGraphics(PApplet parent) {
		this.parent = parent;
	}

	public void chatBox(List<String> chat) {
		parent.noStroke();
		parent.fill(0);
		parent.rectMode(PConstants.CENTER);
		parent.rect(parent.width / 2, parent.height / 4, parent.width - 20, parent.height / 2 - 20, 20);

		parent.textAlign(PConstants.LEFT);
		parent.textSize(22);
		parent.fill(255);
		
		for(int i = minChat; i < chat.size(); i++) {
			if(chat.size() > minChat+6) {
				minChat++;
				chatHeightModifier -= parent.height/14;
			}
			
			parent.text(chat.get(i), parent.width / 22, parent.height / 14 * i + 40 + chatHeightModifier);
		}
	}

	public void textBox(String userInput) {
		parent.noStroke();
		parent.fill(0);
		parent.rectMode(PConstants.CENTER);
		parent.rect(parent.width / 2, parent.height / 1.35f, parent.width - 20, parent.height / 2 - 20, 20);

		parent.textAlign(PConstants.LEFT);
		parent.textSize(22);
		parent.fill(255);
		parent.text(userInput, parent.width / 22, parent.height / 1.8f);
	}
}
