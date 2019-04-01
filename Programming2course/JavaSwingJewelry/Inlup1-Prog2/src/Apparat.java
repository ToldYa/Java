
public class Apparat extends Vardesaker {
	private double inkopspris;
	private int slitage;

	// -----------------Constructor------------------
	public Apparat(String namn, double inkopspris, int slitage) {
		super(namn);
		this.inkopspris = inkopspris;
		this.slitage = slitage;
	}

	// -----------------Methods----------------------
	public double getInkopspris() {
		return inkopspris;
	}

	public int getSlitage() {
		return slitage;
	}

	public double slitageIProcent() {
		return slitage / 10.0;
	}

	public double vardeEfterSlitage() {
		return inkopspris * slitageIProcent();
	}

	@Override
	protected double getVarde() {
		return vardeEfterSlitage();
	}

	public String toString() {
		return "Apparat: " + getNamn() + " Inköpspris: " + inkopspris + " Slitage: " + slitage + " Värde: "
				+ getRiktigtVarde();
	}
}
