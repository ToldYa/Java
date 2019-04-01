/**
Emil Vesa emve6881
Wilhelm Ericsson wier0584
**/

import java.util.ArrayList;

public class Team {
	private static final int NUM_OF_MEDAL_TYPES = 3;
	private String teamName;
	private ArrayList<Participant> teamMembers = new ArrayList<>();

	// ------------------Constructor------------------------
	public Team(String teamName) {
		this.teamName = teamName;
	}

	// ------------------Methods---------------------------
	public void addTeamMember(Participant newParticipant) {
		teamMembers.add(newParticipant);
	}

	public void removeMember(int atIndex) {
		teamMembers.remove(atIndex);
	}

	public String getTeamName() {
		return teamName;
	}

	public Participant getTeamMember(int atIndex) {
		return teamMembers.get(atIndex);
	}

	public int getTeamSize() {
		return teamMembers.size();
	}

	public int getTeamMemberMedalsType(int typeOfMedal) {
		int[] medals = new int[NUM_OF_MEDAL_TYPES];
		int medalType;
		boolean isMedal;

		for (int i = 0; i < teamMembers.size(); i++) {

			for (int j = 0; j < teamMembers.get(i).getNumOfResult(); j++) {
				isMedal = teamMembers.get(i).getMedals(j) < NUM_OF_MEDAL_TYPES;
				if (isMedal) {
					medalType = teamMembers.get(i).getMedals(j);
					medals[medalType]++;
				}
			}

		}
		return medals[typeOfMedal];
	}

	public boolean teamMemberHasMedal() {
		boolean teamHasMedal = false;
		for (int i = 0; i < teamMembers.size(); i++) {
			teamHasMedal = teamMembers.get(i).hasMedal();
			if (teamHasMedal) {
				i = teamMembers.size();
			}
		}

		return teamHasMedal;
	}

	public String printTeamMedals() {
		String teamMedalsPrint = "";
		for (int i = 0; i < NUM_OF_MEDAL_TYPES; i++) {
			teamMedalsPrint += getTeamMemberMedalsType(i) + "\t";
		}

		return teamMedalsPrint + teamName + "\n";
	}

	public String toString() {
		String teamInfo = "";
		for (int i = 0; i < teamMembers.size(); i++) {
			if (i % 2 == 0 && i > 0) {
				teamInfo += "\n" + teamMembers.get(i).toString() + "\t";
			} else {
				teamInfo += teamMembers.get(i).toString() + "\t";
			}

		}
		return teamInfo;
	}
}
