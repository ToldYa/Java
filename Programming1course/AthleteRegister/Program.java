/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.Scanner;

public class Program {
	static final int MESSAGE_CMD_MINIMUM_LENGTH = 7;
	private Scanner keyboard = new Scanner(System.in);
	private boolean isRunning = true;
	private CompetitionManager competition = new CompetitionManager();

	final int NAME_MIN_LENGHT = 2;

	// -----------------------Methods--------------------------------
	public String readLine() {
		return keyboard.nextLine();
	}

	public int readInt() {
		String tempInt = askForNumber();
		int Int = Integer.parseInt(tempInt);

		return Int;
	}

	public double readDouble() {
		double tempDouble = keyboard.nextDouble();
		keyboard.nextLine();
		return tempDouble;
	}

	public String enterName() {
		String name;
		do {
			name = readLine();
			if (name.isEmpty()) {
				System.out.println("Names can't be empty!");
			}
		} while (name.isEmpty());
		name = formatName(name);
		return name;
	}

	public String formatName(String name) {
		name = name.trim();
		if (!name.isEmpty()) {
			String tempName = name.toLowerCase().trim();

			name = name.substring(0, 1);
			tempName = tempName.substring(1, tempName.length());
			name = name.toUpperCase() + tempName;
		}
		return name;
	}

	public boolean checkIfEvent(String command) {
		boolean isEvent;
		isEvent = competition.searchEventList(command);

		return isEvent;
	}

	public boolean isNumberInt(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public boolean isNotNumberDouble(String num) {
		if (num.contains(",")) {
			num = num.replace(",", ".");
			return tryIfIsNaN(num);
		} else {
			return tryIfIsNaN(num);
		}

	}

	public int enterNumber() {
		int number;

		System.out.print("Number: ");
		number = readInt();

		return number;
	}

	private boolean tryIfIsNaN(String num) {
		boolean isNaN;
		try {
			isNaN = Double.isNaN(Double.parseDouble(num));
			return isNaN;
		} catch (NumberFormatException e) {
			isNaN = true;
			return isNaN;
		}
	}

	public String askForNumber() {
		String tempInt;
		boolean isNum = false;
		do {
			tempInt = readLine();
			isNum = isNumberInt(tempInt);
			if (!isNum) {
				System.out.print("\nNeeds to be number: ");
			}
		} while (!isNum);

		return tempInt;
	} // NY ANVDS I readInt

	public double askForResult(String participantName, String teamName, String eventName) { // FIXA
																							// PRINT,
																							// DO
																							// WHILE
																							// IST
																							// FÖR
																							// WHILE
		String tempResult = "";
		double result = 0;
		boolean lessThanZero = true;

		do {
			System.out.print("Results for " + participantName + " from " + teamName + " in " + eventName + ": ");
			tempResult = readLine();

			if (checkResult(tempResult)) {
				tempResult = fixResult(tempResult);
				result = Double.parseDouble(tempResult);
				lessThanZero = false;
			}
			System.out.println();
		} while (lessThanZero);
		return result;
	}

	private String fixResult(String result) {
		if (result.contains(",")) {
			result = result.replace(",", ".");
		}
		return result;
	}

	private boolean checkResult(String result) {

		boolean isNum = false;
		double tempResult;
		if (isNotNumberDouble(result)) {
			System.out.println("Result must be number! ");
		} else {

			result = fixResult(result);

			tempResult = Double.parseDouble(result);

			if (tempResult < 0.0) {
				System.out.println("Must be greater than or equal to zero!");
			} else {
				isNum = true;
			}
		}

		return isNum;
	}

	public String askForEventName(boolean shouldBeInList) {
		String tempEventName;
		do {
			System.out.print("Event name: ");
			tempEventName = enterName();
			if (checkIfEvent(tempEventName) && !shouldBeInList) {
				System.out.println("Unable to add already existing event!");
			} else if (shouldBeInList) {
				isEventInList(tempEventName);
			}
		} while (tempEventName.isEmpty() || checkIfEvent(tempEventName) && !shouldBeInList);

		return tempEventName;
	}

	public int askForNumOfAttempts() {
		int tempNumOfAttempts;
		System.out.print("Attempts allowed: ");
		tempNumOfAttempts = readInt();

		while (tempNumOfAttempts < 1) {
			System.out.print("Too low, must allow at least one attempt: ");
			tempNumOfAttempts = readInt();
		}
		return tempNumOfAttempts;
	}

	public String askForFirstName() {
		System.out.print("First name: ");
		String firstName = enterName();
		return firstName;
	}

	public String askForLastName() {
		System.out.print("Last name: ");
		String lastName = enterName();
		return lastName;
	}

	public String askForTeamName() {
		System.out.print("Team name: ");
		String teamName = enterName();
		return teamName;
	}

	public String breakOutMessageCmd(String cmdAndMessage) {

		String cmdMessage;
		if (cmdAndMessage.length() < MESSAGE_CMD_MINIMUM_LENGTH) {
			cmdMessage = "";
		} else {
			cmdMessage = cmdAndMessage.trim().substring(0, MESSAGE_CMD_MINIMUM_LENGTH);
		}

		return cmdMessage;
	}

	public String breakOutMessageBody(String messageCmdAndBody) {
		final int MESSAGE_START = 8;
		String messageBody = "";
		if (messageCmdAndBody.length() > MESSAGE_START) {

			messageBody = messageCmdAndBody.substring(MESSAGE_START);
		}

		return messageBody.trim();
	}

	public boolean searchForMessageCmd(String cmd) {
		boolean isMessage = false;
		String messageCmd = "message";
		if (messageCmd.equals(cmd.toLowerCase().substring(0, MESSAGE_CMD_MINIMUM_LENGTH))) {
			isMessage = true;
		}

		return isMessage;
	}

	public boolean isEventInList(String eventName) {
		boolean isInList = checkIfEvent(eventName);
		if (!isInList) {
			System.out.println("No event called \"" + eventName + "\" found! \n");
		}
		return isInList;
	}

	public int searchForParticipant() {
		int participantNumber = enterNumber();
		int participantIndex = competition.searchParticipantList(participantNumber);
		if (participantIndex < 0) {
			System.out.println("No participant with number " + participantNumber + " found! \n");
		}
		return participantIndex;
	}

	public int getParticipantIndex() {
		int participantIndex = searchForParticipant();
		return participantIndex;
	}

	public String getTeamName(int participantIndex) {
		int participantNum = competition.getParticipantNumber(participantIndex);
		String teamName = competition.getTeamName(participantNum);

		return teamName;
	}

	public void printMessage(String messageToPrint) {
		Message message = new Message(messageToPrint);
		System.out.println();
		System.out.println(message.toString());
	}

	public boolean printEventResult(String command) {
		boolean isEvent = checkIfEvent(command);
		if (isEvent) {
			competition.printEventResult(command);
		}
		return isEvent;
	}

	public void printParticipant() {
		System.out.print("Participant Number:");
		int participantIndex = readInt();
		competition.printParticipantResult(participantIndex);
	}

	public void addResult() {
		double result = 0.0;
		String participantName = "";
		String teamName = "";
		String eventName = "";

		int participantIndex = getParticipantIndex();
		if (participantIndex >= 0) {
			participantName = competition.getParticipantName(participantIndex);
			teamName = getTeamName(participantIndex);
			eventName = askForEventName(true);
			if (isEventInList(eventName)) {
				result = askForResult(participantName, teamName, eventName);
				competition.addResult(result, participantIndex, eventName);
			}
		}

	}

	public void addEvent() {
		String tempEventName;
		int tempNumOfAttempts;
		tempEventName = askForEventName(false);
		tempNumOfAttempts = askForNumOfAttempts();

		competition.addEvent(tempEventName, tempNumOfAttempts);
	}

	public void addParticipant() {
		String firstName, lastName, teamName;
		do {
			firstName = askForFirstName();
		} while (firstName.isEmpty());

		do {
			lastName = askForLastName();
		} while (lastName.isEmpty());

		do {
			teamName = askForTeamName();
		} while (teamName.isEmpty());

		competition.addParticipant(firstName, lastName, teamName);
	}

	public void removeParticipant() {
		int participantNumber;
		String isNumTest;
		do {
			System.out.print("Number: ");
			isNumTest = readLine();
			System.out.println();
		} while (isNumTest.isEmpty() || !isNumberInt(isNumTest));
		participantNumber = Integer.parseInt(isNumTest);
		competition.removeParticipant(participantNumber);
	}

	public String enterCmd() {
		String cmd = readLine();
		cmd = formatName(cmd);
		return cmd;
	}

	public void commandLine() {
		System.out.println("1.Add event \t\t 3.Remove partcipant \t 5.Add result \t 7.Teams \t 9.Exit \n"
				+ "2.Add participant \t 4.Print team list \t 6.Participant \t 8.Message");
		System.out.print("Command> ");
	}

	public boolean participantSwitch(String command) {
		boolean switchActivated = true;
		switch (command) {
		case "Add participant":
			addParticipant();
			break;
		case "Remove participant":
			removeParticipant();
			break;
		case "Participant":
			printParticipant();
			break;
		default:
			switchActivated = false;
		}
		return switchActivated;
	}

	public boolean teamSwitch(String command) {
		boolean switchActivated = true;
		switch (command) {
		case "Teams":
			competition.printTeamResult();
			break;
		case "Print team list":
			competition.printTeamList();
			break;
		default:
			switchActivated = false;
		}
		return switchActivated;
	}

	public boolean eventSwitch(String command) {
		boolean switchActivated = true;
		switch (command) {
		case "Add event":
			addEvent();
			break;
		case "Add result":
			addResult();
			break;
		default:
			boolean isEvent = printEventResult(command);
			if (!isEvent) {
				switchActivated = false;

			}
		}
		return switchActivated;
	}

	public boolean checkIfActivated(String command) {
		boolean switchActivated = participantSwitch(command);
		if (!switchActivated) {
			switchActivated = teamSwitch(command);
		}
		if (!switchActivated) {
			switchActivated = eventSwitch(command);
		}
		return switchActivated;
	}

	public void commandSwitch() {
		String message = "";
		String command = enterCmd();
		if (command.length() > MESSAGE_CMD_MINIMUM_LENGTH && searchForMessageCmd(command)) {
			message = breakOutMessageBody(command);
			command = breakOutMessageCmd(command);
		}
		switch (command) {
		case "Message":
			printMessage(message);
			break;

		case "Exit":
			System.out.println("Exiting...");
			isRunning = false;
			break;

		default:
			boolean activated = checkIfActivated(command);
			if (!activated) {
				System.out.println("Invalid Command!");
			}
		}
	}

	public void startUp() {
		System.out.println("Running startUp... \n ");
	}

	public void run() {

		while (isRunning) {
			commandLine();
			commandSwitch();

		}

	}

	public void exitProg() {
		System.out.println("Done!");
	}

	// -----------------------Main--------------------------------
	public static void main(String[] args) {
		Program Prog = new Program();

		Prog.startUp();
		Prog.run();
		Prog.exitProg();
	}
}
