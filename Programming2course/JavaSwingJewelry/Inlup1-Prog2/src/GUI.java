import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class GUI extends JFrame {
	private Prog prog = new Prog();
	private JPanel soPanel, eaPanel;
	private JButton visa, borskrasch;
	private JRadioButton varde, namn;
	private JLabel overskrift, nytt, sortera;
	private JTextArea textA;
	private String val[] = { "Smycke", "Aktie", "Apparat" };
	private JComboBox<String> vardesaksTyp;
	private ButtonGroup radioKnappar;
	private JScrollPane skroll;
	// -----------------------------------------Constructor---------------------------------------

	public GUI() {
		super("Sakregister");

		skapaLayout();

		setResizable(false);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	};

	// -----------------------------------------Methods---------------------------------------

	public void skapaLayout() {
		skapaKomponenter();
		skapaSoPanel();
		skapaEaPanel();
		addToFrame();
	};

	private void skapaKomponenter() {
		skapaPanel();
		skapaKnappKomp();
		skapaTextKomp();
	};

	private void skapaKnappKomp() {
		skapaRadioKnappar();
		skapaKnappar();
		skapaComboBox();
	};

	private void skapaTextKomp() {
		skapaEtiketter();
		skapaTextArea();
	};

	private void skapaPanel() {
		soPanel = new JPanel();
		eaPanel = new JPanel();
		eaPanel.setLayout(new BoxLayout(eaPanel, BoxLayout.PAGE_AXIS));
	};

	private void skapaSoPanel() {
		soPanel.add(nytt);
		soPanel.add(vardesaksTyp);
		soPanel.add(visa);
		soPanel.add(borskrasch);
	};

	private void skapaEaPanel() {
		eaPanel.add(sortera);
		eaPanel.add(namn);
		eaPanel.add(varde);
	};

	private void skapaTextArea() {
		textA = new JTextArea(10, 35);
		textA.setEditable(false);
		skroll = new JScrollPane(textA);

	};

	private void skapaKnappar() {
		visa = new JButton("Visa");
		visa.addActionListener(new VisaLyss());
		borskrasch = new JButton("Börskrasch");
		borskrasch.addActionListener(new BorskraschLyss());
	};

	private void skapaRadioKnappar() {
		varde = new JRadioButton("Värde");
		namn = new JRadioButton("Namn");
		varde.addActionListener(new SorteringLyss());
		namn.addActionListener(new SorteringLyss());
		skapaKnappGrupp();
	};

	private void skapaKnappGrupp() {
		radioKnappar = new ButtonGroup();
		radioKnappar.add(varde);
		radioKnappar.add(namn);
		radioKnappar.setSelected(namn.getModel(), true);
	}

	private void skapaEtiketter() {
		overskrift = new JLabel("Värdesaker");
		overskrift.setHorizontalAlignment(SwingConstants.CENTER);
		nytt = new JLabel("Nytt: ");
		sortera = new JLabel("Sortera");
	};

	private void skapaComboBox() {
		vardesaksTyp = new JComboBox<>(val);
		vardesaksTyp.addActionListener(new LaggTillSakerLyss());
	};

	private void addToFrame() {
		add(overskrift, BorderLayout.NORTH);
		add(skroll, BorderLayout.WEST);
		add(eaPanel, BorderLayout.EAST);
		add(soPanel, BorderLayout.SOUTH);
	};

	public String hittaValdRadioknapp(ButtonGroup knappgrupp) {
		boolean hittad = false;
		AbstractButton valdKnapp;
		Enumeration<AbstractButton> knapp = radioKnappar.getElements();
		do {
			valdKnapp = knapp.nextElement();
			hittad = valdKnapp.isSelected();
		} while (!hittad && knapp.hasMoreElements());

		return valdKnapp.getText();
	}

	public void addNyAktie(InmatningAktie aktie) {
		Aktie tempAktie = new Aktie(aktie.getNamn(), aktie.getAntal(), aktie.getKurs());
		prog.addVardesaker(tempAktie);
	}

	public void addNyttSmycke(InmatningSmycke smycke) {
		Smycke tempSmycke = new Smycke(smycke.getNamn(), smycke.getAntalStenar(), smycke.arGuld());
		prog.addVardesaker(tempSmycke);
	}

	public void addNyApparat(InmatningApparat apparat) {
		Apparat tempApparat = new Apparat(apparat.getNamn(), apparat.getPris(), apparat.getSlitage());
		prog.addVardesaker(tempApparat);
	}

	// -----------------------------------------Inre
	// Klasser---------------------------------------

	class VisaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			prog.sortSaker(hittaValdRadioknapp(radioKnappar));
			textA.setText(prog.toString());
		}
	}

	class BorskraschLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			prog.borskrasch();
			fixaLista();
		}

		private void fixaLista() {
			if (!textA.getText().isEmpty()) {
				prog.sortSaker(hittaValdRadioknapp(radioKnappar));
				textA.setText(prog.toString());
			}
		}
	}

	class LaggTillSakerLyss implements ActionListener {
		int svar;

		public void actionPerformed(ActionEvent ave) {
			skapaPopUpMenu();
		}

		public void skapaPopUpMenu() {
			String tempVal = (String) vardesaksTyp.getSelectedItem();
			if (tempVal.equals("Aktie")) {
				inmatningAktie();
			} else if (tempVal.equals("Smycke")) {
				inmatningSmycke();
			} else if (tempVal.equals("Apparat")) {
				inmatningApparat();
			}
		}

		public void inmatningAktie() {
			try {
				InmatningAktie tempAktie = new InmatningAktie();
				svar = JOptionPane.showConfirmDialog(GUI.this, tempAktie, "", JOptionPane.OK_CANCEL_OPTION);
				if (svar == JOptionPane.OK_OPTION) {
					addNyAktie(tempAktie);
				}
			} catch (NumberFormatException e) {
				felmeddelande(e);
			}
		}

		public void inmatningApparat() {
			try {
				InmatningApparat tempApparat = new InmatningApparat();
				svar = JOptionPane.showConfirmDialog(GUI.this, tempApparat, "", JOptionPane.OK_CANCEL_OPTION);
				if (svar == JOptionPane.OK_OPTION) {
					inmatningApparatVardekontroll(tempApparat);
				}
			} catch (NumberFormatException e) {
				felmeddelande(e);
			}

		}

		private void inmatningApparatVardekontroll(InmatningApparat tempApparat) {
			if (tempApparat.getSlitage() < 11 && tempApparat.getSlitage() > 0) {
				addNyApparat(tempApparat);
			} else {
				JOptionPane.showMessageDialog(GUI.this, "Fel uppstod! Ogiltigt värde på slitage(utanför spannet: 1-10)",
						"ERROR", JOptionPane.WARNING_MESSAGE);
			}
		}

		public void inmatningSmycke() {
			try {
				InmatningSmycke tempSmycke = new InmatningSmycke();
				svar = JOptionPane.showConfirmDialog(GUI.this, tempSmycke, "", JOptionPane.OK_CANCEL_OPTION);
				if (svar == JOptionPane.OK_OPTION) {
					addNyttSmycke(tempSmycke);
				}
			} catch (NumberFormatException e) {
				felmeddelande(e);
			}
		}

		private void felmeddelande(NumberFormatException error) {
			JOptionPane.showMessageDialog(GUI.this, "Fel uppstod! " + error, "ERROR", JOptionPane.WARNING_MESSAGE);
		}

	}

	class SorteringLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			if (ave.getSource() instanceof JRadioButton) {

				prog.sortSaker(hittaValdRadioknapp(radioKnappar));
				if (!textA.getText().isEmpty()) {
					textA.setText(prog.toString());
				}

			} else {
				System.out.println("ERROR ---> ActionListner NOT COMPATIBLE WITH: " + ave.getSource());
			}

		};
	}

	// ---------------------------------------------------------MAIN------------------------------------------------------
	public static void main(String[] args) {
		new GUI();
	};

}
