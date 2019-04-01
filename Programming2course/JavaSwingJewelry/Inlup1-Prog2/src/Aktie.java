
public class Aktie extends Vardesaker {

	private int antal;
	private double kurs;

	// ---------------------Constructor----------------
	public Aktie(String namn, int antal, double kurs) {
		super(namn);
		this.antal = antal;
		this.kurs = kurs;
	}

	// ---------------------Methods--------------------
	public int getAntal() {
		return antal;
	}

	public double getKurs() {
		return kurs;
	}

	public void setKurs(double nyKurs) {
		kurs = nyKurs;
	}

	@Override
	protected double getVarde() {
		return antal * kurs;
	}

	public String toString() {
		return "Aktie: " + getNamn() + " Antal: " + antal + " Kurs: " + kurs + " Värde: " + getRiktigtVarde();
	}
}
