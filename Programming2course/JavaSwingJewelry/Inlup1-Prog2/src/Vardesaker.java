
abstract public class Vardesaker {
	private String namn;
	private static final double moms = 1.25;

	// -----------------------Constructor------------------
	public Vardesaker(String namn) {
		this.namn = namn;
	}

	// ----------------------Methods------------------------
	public String getNamn() {
		return namn;
	}

	public double getRiktigtVarde() {
		return getVarde() * moms;
	};

	abstract protected double getVarde();

}
