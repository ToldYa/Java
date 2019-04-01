/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.ArrayList;

public class Message {
	private final int FRAME_WIDTH = 60;
	private final int MESSAGE_MAX_WIDTH = 56;

	private ArrayList<String> message = new ArrayList<String>();

	// ----------------Constructor------------------
	public Message(String messageText) {

		String newMessage = messageText.toUpperCase();
		message.add(newMessage);

		if (newMessage.length() > MESSAGE_MAX_WIDTH) {
			newMessageLine();
		}

	}

	// ----------------Methods----------------------
	public String writeFrameTopBottom() {
		String frameTopBottom = "";
		for (int i = 0; i < FRAME_WIDTH; i++) {
			frameTopBottom += "#";
		}
		frameTopBottom += "\n";
		return frameTopBottom;
	}

	public String writeFrameSide() {
		String frameSide = "";

		for (int i = 0; i < FRAME_WIDTH; i++) {
			if (i == 0 || i == (FRAME_WIDTH - 1)) {
				frameSide += "#";
			} else {
				frameSide += " ";
			}
		}
		frameSide += "\n";

		return frameSide;
	}

	public String writeMessage() {
		String writtenMessage = "";

		for (int i = 0; i < message.size(); i++) {

			writtenMessage += "# " + message.get(i);

			for (int j = message.get(i).length() - 1; j < MESSAGE_MAX_WIDTH; j++) {

				writtenMessage += " ";
			}
			writtenMessage += "#\n";
		}
		return writtenMessage;
	}

	public String printMessage() {

		String messagePrint = writeFrameTopBottom() + writeFrameSide() + writeMessage() + writeFrameSide()
				+ writeFrameTopBottom();

		return messagePrint;
	}

	private void newMessageLine() {

		for (int i = 0; message.get(i).length() > MESSAGE_MAX_WIDTH; i++) {

			String tempMessage = message.get(i).substring(MESSAGE_MAX_WIDTH).trim();
			message.add(tempMessage);

		}

		for (int j = 0; j < message.size(); j++) {

			if (message.get(j).length() > MESSAGE_MAX_WIDTH) {

				String tempMessage = message.get(j).substring(0, MESSAGE_MAX_WIDTH);

				message.add(j, tempMessage);
				message.remove(j + 1);

			}
		}
	}

	public String toString() {

		return printMessage();
	}
}
