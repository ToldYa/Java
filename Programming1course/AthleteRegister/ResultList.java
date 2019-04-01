/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.ArrayList;

public class ResultList {

	private static final int TYPE_OF_MEDALS = 3;
	private String listName;
	private ArrayList<Result> resultList = new ArrayList<>();

	// ----------Constructor---------------
	public ResultList(String listName) {
		this.listName = listName;
	}

	// ----------Methods-------------------
	public void addResult(Result newResult) {

		boolean resultIsInList = searchResultList(newResult.getResultHolderName());
		if (!resultList.isEmpty() && resultIsInList) {

			declarePlaceInEvent();

		} else {
			resultList.add(newResult);
			declarePlaceInEvent();

		}

	}

	public void sortBestResult() {
		double bestResult;

		for (int i = 0; i < resultList.size(); i++) {
			bestResult = resultList.get(i).getBestResult();
			for (int j = i + 1; j < resultList.size(); j++) {
				if (bestResult < resultList.get(j).getBestResult()) {

					Result tempResult = resultList.get(i);

					resultList.set(i, resultList.get(j));
					resultList.set(j, tempResult);
				}
			}
		}
	}

	public boolean isWinner(Result newResult) {
		boolean isWinner;
		int medalCount = 0;
		int i = 0;
		do {
			isWinner = newResult.getBestResult() == resultList.get(i).getBestResult();

			if (!isWinner) {
				i++;
				medalCount++;
			}
		} while (!isWinner && medalCount < TYPE_OF_MEDALS && i < resultList.size());

		return isWinner;

	}

	public void declarePlaceInEvent() {
		int placeInEvent = 0;
		sortBestResult();
		int medalCount = 0;
		for (int i = 0; i < resultList.size(); i++) {
			int j = i + 1;

			if (j < resultList.size() && resultList.get(i).getBestResult() != resultList.get(j).getBestResult()) {

				resultList.get(i).setTypeOfMedal(placeInEvent);
				placeInEvent += 1 + medalCount;
				medalCount = 0;
			} else {
				medalCount++;
				resultList.get(i).setTypeOfMedal(placeInEvent);
			}
		}

	}

	public void removePreviousWinners() {
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setNoMedal();
		}
	}

	public void removeResultFromList(Result oldResult) {
		resultList.remove(oldResult);
	}

	public boolean searchResultList(String resultHolderName) {
		boolean isInList = false;
		for (int i = 0; i < resultList.size(); i++) {
			isInList = resultList.get(i).getResultHolderName().equals(resultHolderName);
			if (isInList) {
				i = resultList.size();
			}
		}

		return isInList;
	}

	public String toString() {
		int placeInEvent = 1;
		String resultHolder;
		String resultListPrint = "Results for " + listName + "\n";
		int medalCount = 0;
		for (int i = 0; i < resultList.size(); i++) {
			int j = i + 1;
			resultHolder = resultList.get(i).getResultHolderName();

			if (j < resultList.size() && resultList.get(i).getBestResult() != resultList.get(j).getBestResult()) {

				resultListPrint += placeInEvent + ". " + resultList.get(i).getBestResult() + " " + resultHolder + "\n";
				placeInEvent += 1 + medalCount;
				medalCount = 0;
			} else {
				medalCount++;
				resultListPrint += placeInEvent + ". " + resultList.get(i).getBestResult() + " " + resultHolder + "\n";
			}
		}

		return resultListPrint;
	}
}
