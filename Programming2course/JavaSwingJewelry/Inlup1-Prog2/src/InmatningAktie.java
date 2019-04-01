import javax.swing.JLabel;
import javax.swing.JTextField;

public class InmatningAktie extends InmatningVardesakForm {

	private JTextField inmatAntal = new JTextField(10);
	private JTextField inmatKurs = new JTextField(10);

	// -----------------------Constructor------------------
	public InmatningAktie() {
		super();
		skapaRadMTopp();
		skapaRadMBotten();
	}

	// -----------------------Methods----------------------
	public int getAntal() {
		return Integer.parseInt(inmatAntal.getText());
	}

	public double getKurs() {
		String tempKurs = kontrolleraInmatDouble(inmatKurs.getText());
		return Double.parseDouble(tempKurs);
	}

	@Override
	public void skapaRadMTopp() {
		addToRadMTopp(new JLabel("Antal: "));
		addToRadMTopp(inmatAntal);
	}

	@Override
	public void skapaRadMBotten() {
		addToRadMBotten(new JLabel("Kurs: "));
		addToRadMBotten(inmatKurs);
	}

}
