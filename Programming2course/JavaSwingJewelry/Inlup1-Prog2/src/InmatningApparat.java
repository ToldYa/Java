import javax.swing.*;

public class InmatningApparat extends InmatningVardesakForm {

	private JTextField pris = new JTextField(10);
	private JTextField slitage = new JTextField(10);

	// -----------------------Constructor------------------
	public InmatningApparat() {
		super();
		extendNamnEtikett("  ");
		skapaRadMTopp();
		skapaRadMBotten();

	}

	// -----------------------Methods----------------------

	@Override
	public void skapaRadMTopp() {
		addToRadMTopp(new JLabel("     Pris: "));
		addToRadMTopp(pris);

	}

	@Override
	public void skapaRadMBotten() {
		addToRadMBotten(new JLabel("Slitage: "));
		addToRadMBotten(slitage);
	}

	public double getPris() {
		String tempPris = kontrolleraInmatDouble(pris.getText());
		System.out.println(tempPris);
		return Double.parseDouble(tempPris);
	}

	public int getSlitage() {
		return Integer.parseInt(slitage.getText());
	}
}
