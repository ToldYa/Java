/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.ArrayList;

public class CompetitionManager {
	private ArrayList<Event> eventList = new ArrayList<>();
	private ArrayList<Team> teamList = new ArrayList<>();
	private ArrayList<Participant> participantList = new ArrayList<>();
	private static final int FIRST_PARTICIPANT_NUMBER = 100;
	private int participantNumber = FIRST_PARTICIPANT_NUMBER;
	// ----------------Constructor--------------------

	// ----------------Methods------------------------
	public void addResult(double newResult, int participantIndex, String eventName) {
		int eventIndex = searchEventIndex(eventName);
		participantList.get(participantIndex).createResult(newResult, eventList.get(eventIndex));
		sortTeamList();
	}

	public void addEvent(String eventName, int numOfAttempts) {
		boolean isInList = searchEventList(eventName);
		if (!isInList) {
			eventList.add(new Event(eventName, numOfAttempts));
			System.out.println(eventName + " added! \n");
		}
	}

	public void addParticipant(String firstName, String lastName, String teamName) {
		if (!participantList.isEmpty()) {
			participantNumber++;
		}
		Participant newParticipant = new Participant(firstName, lastName, participantNumber);
		participantList.add(newParticipant);

		addParticipantToTeam(teamName, newParticipant);

	}

	public void addParticipantToTeam(String teamName, Participant newParticipant) {
		int teamIndex = searchTeamList(teamName);
		if (teamIndex < 0) {
			addTeam(teamName);
			teamList.get(teamList.size() - 1).addTeamMember(newParticipant);
			sortTeamList();
		} else {
			teamList.get(teamIndex).addTeamMember(newParticipant);
		}
	}

	public void addTeam(String teamName) {

		teamList.add(new Team(teamName));
	}

	public int getParticipantNumber(int participantIndex) {
		int participantNum = participantList.get(participantIndex).getParticipantNum();
		return participantNum;
	}

	public String getParticipantName(int participantIndex) {
		String participantName = participantList.get(participantIndex).getFullName();
		return participantName;
	}

	public String getTeamName(int participantNumber) {
		String teamName = "";

		for (int i = 0; i < teamList.size(); i++) {
			for (int j = 0; j < teamList.get(i).getTeamSize(); j++) {
				if (teamList.get(i).getTeamMember(j).getPatNum() == participantNumber) {
					teamName = teamList.get(i).getTeamName();
				}
			}
		}
		return teamName;
	}

	public void printEventList() {
		for (int i = 0; i < eventList.size(); i++) {
			System.out.println(eventList.get(i).toString());
		}
	}

	public void printEventResult(String eventName) {
		int eventIndex = searchEventIndex(eventName);
		System.out.print(eventList.get(eventIndex).printEventResult());
	}

	public void printTeamResult() {
		final int BAR_WITDH = 35;
		sortTeamList();
		String teamResult = "1 \t2 \t3 \tTeam name \n";
		for (int i = 0; i < BAR_WITDH; i++) {
			teamResult += "*";
		}
		teamResult += "\n";
		for (int i = 0; i < teamList.size(); i++) {
			teamResult += teamList.get(i).printTeamMedals();
		}
		System.out.println(teamResult);
	}

	public void printTeamList() {
		if (teamList.size() > 0) {
			for (int i = 0; i < teamList.size(); i++) {
				System.out.println("----- " + teamList.get(i).getTeamName() + " -----");
				System.out.println(teamList.get(i).toString());
			}
		} else {
			System.out.println("There are no teams available...");
		}

	}

	public void printParticipantResult(int participantNum) {
		int participantIndex = searchParticipantList(participantNum);
		System.out.println(participantList.get(participantIndex));
	}

	public void removeParticipant(int participantNumber) {
		int participantIndex = searchParticipantList(participantNumber);

		if (participantIndex < 0) {
			System.out.println("No participant with number " + participantNumber + " exists");
		} else {
			String participantName = participantList.get(participantIndex).getFullName();
			participantList.get(participantIndex).removeResult();
			participantList.remove(participantIndex);
			String teamName = teamList.get(searchTeamIndex(participantNumber)).getTeamName();
			removeTeamMember(participantNumber);
			updateEventResult();
			System.out
					.println(participantName + " from " + teamName + " with number " + participantNumber + " removed");
		}

	}

	private void removeTeam(int teamIndex) {
		teamList.remove(teamIndex);
	}

	private void removeTeamMember(int participantNumber) {
		int teamIndex = searchTeamIndex(participantNumber);
		int teamMemberIndex = searchTeamMemberIndex(participantNumber);
		teamList.get(teamIndex).removeMember(teamMemberIndex);

		if (teamList.get(teamIndex).getTeamSize() == 0) {
			removeTeam(teamIndex);
		}

	}

	private void updateEventResult() {
		for (int i = 0; i < eventList.size(); i++) {
			eventList.get(i).declareWinner();
		}
	}

	public void sortTeamList() {

		if (teamHasMedals()) {
			sortTeamsAfterMedal();
		} else {
			sortTeamListName();
		}
	}

	private void sortTeamListName() {
		int isEqual;
		for (int i = 0; i < teamList.size(); i++) {

			for (int j = i + 1; j < teamList.size(); j++) {
				isEqual = teamList.get(i).getTeamName().compareTo(teamList.get(j).getTeamName());
				if (isEqual > 0) {
					teamList.add(i, teamList.get(j));
					teamList.remove(j + 1);
				}
			}
		}
	}

	private void sortTeamsAfterMedal() {

		for (int i = 0; i < teamList.size(); i++) {
			for (int j = i + 1; j < teamList.size(); j++) {

				compareMedals(i, j);
			}
		}

	}

	private void compareMedals(int comparedIndex, int compareToIndex) {
		boolean isEqual;
		boolean isGreater;
		final int MEDAL_TYPE = 3;
		int medalCount = 0;
		Team tempTeam;

		do {
			isGreater = teamList.get(comparedIndex).getTeamMemberMedalsType(medalCount) < teamList.get(compareToIndex)
					.getTeamMemberMedalsType(medalCount);
			isEqual = teamList.get(comparedIndex).getTeamMemberMedalsType(medalCount) == teamList.get(compareToIndex)
					.getTeamMemberMedalsType(medalCount);
			if (isGreater) {
				tempTeam = teamList.get(comparedIndex);
				teamList.set(comparedIndex, teamList.get(compareToIndex));
				teamList.set(compareToIndex, tempTeam);
				medalCount = MEDAL_TYPE;
			}
			medalCount++;
		} while (isEqual && medalCount < MEDAL_TYPE);

	}

	public boolean teamHasMedals() {
		boolean hasMedals = false;

		for (int i = 0; i < teamList.size(); i++) {

			hasMedals = teamList.get(i).teamMemberHasMedal();
			if (hasMedals) {
				i = teamList.size();
			}

		}
		return hasMedals;
	}

	private int searchTeamList(String teamName) {
		boolean isInList = false;
		int index = -1;

		for (int i = 0; i < teamList.size(); i++) {
			isInList = teamList.get(i).getTeamName().equals(teamName);
			if (isInList) {
				index = i;
				i = teamList.size();
			}
		}

		return index;
	}

	private int searchTeamMemberIndex(int participantNumber) {
		int participantIndex = -1;
		for (int i = 0; i < teamList.size(); i++) {

			for (int j = 0; j < teamList.get(i).getTeamSize(); j++) {

				if (teamList.get(i).getTeamMember(j).getPatNum() == participantNumber) {
					participantIndex = j;
				}

			}
		}
		return participantIndex;
	}

	private int searchTeamIndex(int participantNumber) {
		int teamIndex = -1;
		for (int i = 0; i < teamList.size(); i++) {

			for (int j = 0; j < teamList.get(i).getTeamSize(); j++) {

				if (teamList.get(i).getTeamMember(j).getPatNum() == participantNumber) {
					teamIndex = i;
				}

			}
		}
		return teamIndex;
	}

	public boolean searchEventList(String eventName) {
		boolean isInList = false;
		for (int i = 0; i < eventList.size(); i++) {
			isInList = eventList.get(i).getEventName().equals(eventName);
			if (isInList) {
				i = eventList.size();
			}
		}
		return isInList;
	}

	private int searchEventIndex(String eventName) {
		int eventIndex = -1;
		for (int i = 0; i < eventList.size(); i++) {
			if (eventList.get(i).getEventName().equals(eventName)) {
				eventIndex = i;
			}
		}

		return eventIndex;
	}

	public int searchParticipantList(int participantNum) {
		boolean isInList = false;
		int index = -1;

		for (int i = 0; i < participantList.size(); i++) {
			isInList = participantList.get(i).getPatNum() == participantNum;
			if (isInList) {
				index = i;
				i = participantList.size();
			}
		}

		return index;
	}
}
