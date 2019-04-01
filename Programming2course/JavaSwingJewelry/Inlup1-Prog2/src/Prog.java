import java.util.ArrayList;
import java.util.Comparator;

public class Prog {
	private ArrayList<Vardesaker> saker = new ArrayList<Vardesaker>();

	// ------------------------Constructor----------------------
	public Prog() {
		// testvärden
		addVardesaker(new Apparat("ApparatETT", 500.35, 7));
		addVardesaker(new Smycke("Ring", 5, true));
		addVardesaker(new Aktie("AB RenoVESA", 500, 34.5));
	}

	// -----------------------Methods-----------------------------
	public void addVardesaker(Vardesaker vardesak) {
		saker.add(vardesak);
	}

	public void sortSaker(String sorteraEfter) {
		if (sorteraEfter.equals("Namn")) {
			sorteraPaNamn();
		} else {
			sorteraPaVarde();
		}
	};

	private void sorteraPaNamn() {
		saker.sort(new CompareName());
	};

	private void sorteraPaVarde() {
		saker.sort(new CompareVarde().reversed());
	};

	public void borskrasch() {
		for (int i = 0; i < saker.size(); i++) {
			if (saker.get(i) instanceof Aktie) {
				Aktie temp = (Aktie) saker.get(i);
				temp.setKurs(0);
			}
		}
	}

	public String toString() {
		String test = "";
		for (int i = 0; i < saker.size(); i++) {
			test += saker.get(i).toString() + "\n";
		}
		return test;
	}

	// -----------------------------------------Inre
	// Klasser---------------------------------------
	class CompareName implements Comparator<Vardesaker> {

		@Override
		public int compare(Vardesaker o1, Vardesaker o2) {

			return o1.getNamn().compareTo(o2.getNamn());
		}
	}

	class CompareVarde implements Comparator<Vardesaker> {

		@Override
		public int compare(Vardesaker o1, Vardesaker o2) {

			Double tempO1 = o1.getRiktigtVarde();
			Double tempO2 = o2.getRiktigtVarde();
			return tempO1.compareTo(tempO2);
		}
	}

}
