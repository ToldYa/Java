import javax.swing.*;
import java.awt.*;

abstract public class InmatningVardesakForm extends JPanel {
	private JLabel namnEtikett = new JLabel("Namn: ");
	private JTextField namnFalt = new JTextField(10);
	private JPanel radTopp, radMTopp, radMBotten;

	// -----------------------Constructor------------------
	public InmatningVardesakForm() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		skapaRadTopp();
		skapaRadM();
	}

	// -----------------------Methods----------------------
	public void skapaRadTopp() {
		radTopp = new JPanel();
		add(radTopp);
		radTopp.add(namnEtikett);
		radTopp.add(namnFalt);
	}

	private void skapaRadM() {
		radMTopp = new JPanel();
		add(radMTopp);
		radMBotten = new JPanel();
		add(radMBotten);
	}

	abstract public void skapaRadMTopp();

	abstract public void skapaRadMBotten();

	public String kontrolleraInmatDouble(String inmatDouble) {
		if (inmatDouble.contains(",")) {
			inmatDouble = inmatDouble.replace(",", ".");
		}
		return inmatDouble;
	}

	public String getNamn() {
		return namnFalt.getText();
	}

	public void addToRadMTopp(Component comp) {
		radMTopp.add(comp);
	}

	public void addToRadMBotten(Component comp) {
		radMBotten.add(comp);
	}

	public void extendNamnEtikett(String tillagg) {
		namnEtikett.setText(tillagg + namnEtikett.getText());

	}
}
