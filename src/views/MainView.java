package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainView extends JFrame {
	private JPanel _formContainer = new JPanel();
	private JPanel _clientContainer = new JPanel();
	private JScrollPane _scrollTable;
	private JTabbedPane _tabbedPane = new JTabbedPane();
	private JLabel _labelNom = new JLabel("Nom : ");
	private JTextField _nomTF = new JTextField();
	private JButton _searchBtn = new JButton("Rechercher");
	private JTable _tableClients = new JTable(10, 10);
	
	private JPanel _facturesContainer = new JPanel();
	private JLabel _factNomLabel = new JLabel("Nom : ");
	private JTextField _factNomTF = new JTextField();
	private JLabel _factPrestLabel = new JLabel("Ajouter une prestation");
	private JComboBox _factPrestCombo = new JComboBox();
	
	public MainView() {
		this.setTitle("Garage Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(800, 600);
		//this.setLocationRelativeTo(null);
		initClientsComponents();
		initFacturesComponents();
		this.setContentPane(_tabbedPane);
		this.pack();
	}
	
	private void initClientsComponents() {
		_formContainer.setLayout(new BoxLayout(_formContainer, BoxLayout.LINE_AXIS));
		_clientContainer.setLayout(new BoxLayout(_clientContainer, BoxLayout.PAGE_AXIS));
		
		_formContainer.add(_labelNom);
		_formContainer.add(_nomTF);
		_formContainer.add(_searchBtn);
		_formContainer.setMaximumSize(new Dimension(400, _nomTF.getPreferredSize().height + 10));
		
		_clientContainer.add(_formContainer);
		_scrollTable = new JScrollPane(_tableClients);
		_scrollTable.setPreferredSize(new Dimension(800, 300));
		_clientContainer.add(_scrollTable);
		
		_tabbedPane.addTab("Clients", _clientContainer);
	}
	
	private void initFacturesComponents() {
		_facturesContainer.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 6;
		gc.weighty = 6;
		gc.gridx = 0;
		gc.gridy = 0;
		_facturesContainer.add(new JLabel("Nom : "), gc);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		_facturesContainer.add(new JTextField(), gc);
		
		_tabbedPane.addTab("Factures", _facturesContainer);
	}
}
