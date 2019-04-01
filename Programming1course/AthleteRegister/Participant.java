/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.ArrayList;

public class Participant {
	private static final int NUM_OF_MEDAL_TYPES = 3;
	private String firstName, lastName;
	private int participantNum;
	private int[] medals = new int[NUM_OF_MEDAL_TYPES];
	private ArrayList<Result> resultList = new ArrayList<>();

	// --------------Constructor--------------
	public Participant(String firstName, String lastName, int participantNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.participantNum = participantNum;
		for (int i : medals) {
			medals[i] = 0;
		}
	}

	// --------------Methods------------------
	public void createResult(double result, Event typeOFEvent) {
		if (searchResultList(typeOFEvent.getEventName()) >= 0 && !resultList.isEmpty()) {
			addResult(result, typeOFEvent);

		} else {
			Result newResult = new Result(result, typeOFEvent, Participant.this);
			resultList.add(newResult);
		}

	}

	public void addResult(double result, Event typeOFEvent) {
		int resultIndex = searchResultList(typeOFEvent.getEventName());
		if (resultIndex >= 0) {
			resultList.get(resultIndex).addResult(result);
		} else {
			System.out.println("Not possible to add result to an event that doesn't exist...  "); // oklart
																									// ifall
																									// den
																									// här
																									// kontrollen
																									// ska
																									// göras
																									// här
		}
	}

	private int searchResultList(String eventName) {
		int resultIndex = -1;
		boolean isInList;

		for (int i = 0; i < resultList.size(); i++) {
			isInList = resultList.get(i).getEventName().equals(eventName);
			if (isInList) {
				resultIndex = i;
			}
		}

		return resultIndex;
	}

	public int getParticipantNum() {
		return participantNum;
	}

	public int getMedals(int resultIndex) {
		return resultList.get(resultIndex).getTypeOfMedal();
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLasttName() {
		return lastName;
	}

	public void removeResult() {
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).removeResultFromEvent();
		}
	}

	public boolean hasMedal() {
		boolean hasMedal = false;
		for (int i = 0; i < resultList.size(); i++) {
			hasMedal = resultList.get(i).getTypeOfMedal() < NUM_OF_MEDAL_TYPES;
			if (hasMedal) {
				i = resultList.size();
			}
		}

		return hasMedal;
	}

	public int getNumOfResult() {
		return resultList.size();
	}

	public int getPatNum() {
		return participantNum;
	}

	public String printResult() {
		String printResultList = "";
		for (int i = 0; i < resultList.size(); i++) {
			printResultList += resultList.get(i).toString() + "\n";
		}
		return printResultList;
	}

	public String toString() {
		String participantPrint = "";
		if (!resultList.isEmpty()) {
			for (int i = 0; i < resultList.size(); i++) {
				participantPrint += "Result for " + getFullName() + " in " + resultList.get(i).getEventName() + ": "
						+ resultList.get(i).getBestResult() + "\n";
			}
		} else {
			participantPrint += getFullName();
		}

		return participantPrint;
	}
}
