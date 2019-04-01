/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

public class Event {
	private String eventName;
	private int numOfAttempts;
	private ResultList eventResult;

	// ----------Constructor-------------------
	public Event(String newEventName, int newNumOfAttempts) {
		eventName = newEventName;
		numOfAttempts = newNumOfAttempts;
		eventResult = new ResultList(eventName);
	}
	// ----------Methods------------------------

	public String getEventName() {
		return eventName;
	}

	public int getNumOfAttempts() {
		return numOfAttempts;
	}

	public void declareWinner() {

		eventResult.declarePlaceInEvent();
	}

	public void addEventResult(Result newResult) {
		eventResult.addResult(newResult);
	}

	public String printEventResult() {

		return eventResult.toString();
	}

	public void removeResultFromList(Result oldResult) {
		eventResult.removeResultFromList(oldResult);
	}

	public String toString() {
		return "Event name: " + eventName + "\t Number of attempts: " + numOfAttempts;
	}
}
