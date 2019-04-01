import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;


public class EastPanel extends JPanel {

	private JLabel categoryLabel = new JLabel("Categories");
	
	private ScrollPane scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
	private JButton hideButton = new JButton("Hide category");
	private JList<Category> categories;
	private ActionListener buttonAction;
	//------------------------Constructor----------------------------
	public EastPanel(ActionListener buttonAction){
		this.buttonAction = buttonAction;
		setLayout(new GridLayout(3,1));
		createComponents();
		createPanelLayout();
	}
	
	//------------------------Methods--------------------------------
	private void createComponents(){
		hideButton.addActionListener(buttonAction);
		scroll.setPreferredSize(new Dimension(30, 90));
	}
	public void setCategoryList(CategoryListModel categoryList){
		categories = new JList<>(categoryList);
		categories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		categories.setVisibleRowCount(5);
		scroll.add(categories);
	}
		
	private void createPanelLayout(){
		JPanel topLine = new JPanel(new BorderLayout());
		topLine.add(categoryLabel,BorderLayout.SOUTH);
		categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);;
		
		
		add(topLine);
		add(scroll);
		JPanel bottLine = new JPanel();
		bottLine.add(hideButton);
		add(bottLine);

	}

	public void clearSelection(){
		categories.clearSelection();
	}
	
	public void setListListener(ListSelectionListener listLis){
		categories.addListSelectionListener(listLis);
	}
	public JButton getHideButton(){
		return hideButton;
	}

	public Category getSelectedCategory(){
		return categories.getSelectedValue();
	}
	
	
	
}
