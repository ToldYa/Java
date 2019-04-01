import javax.swing.*;

public class InmatningSmycke extends InmatningVardesakForm {
	private JTextField antalStenar = new JTextField(10);
	private JCheckBox arGuld = new JCheckBox("Av guld");

	// -----------------------Constructor------------------
	public InmatningSmycke() {
		super();
		extendNamnEtikett("            ");
		skapaRadMTopp();
		skapaRadMBotten();

	}

	// -----------------------Methods----------------------

	@Override
	public void skapaRadMTopp() {
		addToRadMTopp(new JLabel("Antal stenar: "));
		addToRadMTopp(antalStenar);

	}

	@Override
	public void skapaRadMBotten() {
		addToRadMBotten(arGuld);
	}

	public int getAntalStenar() {
		return Integer.parseInt(antalStenar.getText());
	}

	public boolean arGuld() {
		return arGuld.isSelected();
	}

}
