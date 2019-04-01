/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

public class Result {
	static final int NO_MEDAL = 4;
	static final int BEST_RESULT = 0;
	private Participant resultHolder;
	private Event typeOfEvent;
	private String eventName;
	private double[] result;
	private int attempt, attemptCount;
	private int typeOfMedal = NO_MEDAL;

	// ------------------Constructor-------------------
	public Result(double newResult, Event typeOfEvent, Participant resultHolder) {
		this.resultHolder = resultHolder;
		this.typeOfEvent = typeOfEvent;
		attempt = typeOfEvent.getNumOfAttempts();
		attemptCount = attempt;
		eventName = typeOfEvent.getEventName();
		result = new double[attempt];
		addResult(newResult);
		typeOfEvent.addEventResult(Result.this);
	}

	// ------------------Methods-------------------
	public void addResult(double newResult) {
		if (attemptCount > 0) {
			result[attempt - attemptCount] = newResult;
			attemptCount--;
			sortResult();
			typeOfEvent.declareWinner();
		} else {
			System.out.println("Result not added...\n ...No attempts left!");
		}

	}

	public void removeResultFromEvent() {
		typeOfEvent.removeResultFromList(Result.this);
	}

	public void setTypeOfMedal(int typeOfMedal) {
		this.typeOfMedal = typeOfMedal;
	}

	public void setNoMedal() {
		typeOfMedal = NO_MEDAL;
	}

	public void setAttempt(int newAttempt) {
		attempt = newAttempt;
	}

	public double getResult(int attempt) {
		return result[attempt];
	}

	public int getTypeOfMedal() {
		return typeOfMedal;
	}

	public int getParticipantNum() {
		return resultHolder.getPatNum();
	}

	public int getAttempt() {
		return attempt;
	}

	public int getNumOfAttemptsLeft() {
		return attemptCount;
	}

	public String getEventName() {
		return eventName;
	}

	public double getBestResult() {
		return result[BEST_RESULT];
	}

	public String getResultHolderName() {
		return resultHolder.getFullName();
	}

	public void sortResult() {
		double tempResult;

		for (int i = 0; i < result.length; i++) {
			tempResult = result[i];
			for (int j = i + 1; j < result.length; j++) {

				if (tempResult < result[j]) {
					tempResult = result[j];
					result[j] = result[i];
				}

			}
			result[i] = tempResult;

		}

	}

	public String toString() {
		String resultPrint = "----" + eventName + "----\n";
		for (int i = 0; i < attempt; i++) {
			resultPrint += result[i] + "\t";
		}

		return resultPrint += "\n";
	}
}
