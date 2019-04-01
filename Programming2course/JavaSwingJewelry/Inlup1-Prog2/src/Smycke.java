
public class Smycke extends Vardesaker {
	private static final int VARDE_GULD = 2000;
	private static final int VARDE_SILVER = 700;
	private static final int VARDE_STENAR = 500;
	private int antalStenar;
	private boolean arGuld;
	private int metallvarde;

	// -----------------------Constructor------------------
	public Smycke(String namn, int antalStenar, Boolean metallTyp) {
		super(namn);
		this.antalStenar = antalStenar;
		this.arGuld = metallTyp;
		setMetallvarde();
	}

	// -----------------------Methods------------------
	public int getAntalStenar() {
		return antalStenar;
	}

	public boolean getMetalTyp() {
		return arGuld;
	}

	private void setMetallvarde() {
		if (arGuld) {
			metallvarde = VARDE_GULD;
		} else {
			metallvarde = VARDE_SILVER;
		}
	}

	@Override
	protected double getVarde() {
		return metallvarde + (VARDE_STENAR * antalStenar);
	}

	public String toString() {
		return "Smycke: " + getNamn() + " Antal Stenar: " + antalStenar + " Metaltyp: " + getMetalltyp() + " Värde: "
				+ getRiktigtVarde();

	}

	private String getMetalltyp() {
		String metalltyp = "Silver";
		if (arGuld) {
			metalltyp = "Guld";
		}
		return metalltyp;
	}
}
