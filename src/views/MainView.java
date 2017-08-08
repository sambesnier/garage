package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import bdd.Database;

public class MainView extends JFrame {
	private JPanel _formContainer = new JPanel();
	private JPanel _clientContainer = new JPanel();
	private JScrollPane _scrollTable;
	private JTabbedPane _tabbedPane = new JTabbedPane();
	private Dimension _textFieldSize = new Dimension(Integer.MAX_VALUE, 27);
	
	// Client panel
	private JLabel _nomLabel = new JLabel("Nom : ");
	private JTextField _nomTF = new JTextField();
	private JButton _searchBtn = new JButton("Rechercher");
	private JButton _addClientBtn = new JButton("Ajouter");
	private JButton _editclientBtn = new JButton("Modifier");
	private JButton _delClientBtn = new JButton("Supprimer");
	private JButton _refreshClientBtn = new JButton("Actualiser");
	private JTable _tableClients;
	
	private JPanel _facturesContainer = new JPanel();
	private JLabel _factNomLabel = new JLabel("Nom : ");
	private JTextField _factNomTF = new JTextField();
	private JLabel _factPrestLabel = new JLabel("Ajouter une prestation");
	private JComboBox _factPrestCombo = new JComboBox();
	
	private String _email;
	
	public MainView() {
		this.setTitle("Garage Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		initClientsComponents();
		//initFacturesComponents();
		this.setContentPane(_tabbedPane);
		//this.pack();
	}
	
	private void initClientsComponents() {
		fillTable();
		
		_clientContainer.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.1;
		_clientContainer.add(_nomLabel, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.weightx = 0.5;
		_clientContainer.add(_nomTF, gc);
		
		gc.gridx = 3;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		_clientContainer.add(_searchBtn, gc);
		
		/*gc.gridx = 4;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		_clientContainer.add(_refreshClientBtn, gc);*/
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 4;
		gc.gridheight = 7;
		gc.weightx = 0.8;
		gc.weighty = 0.9;
		_clientContainer.add(_scrollTable, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 4;
		gc.gridy = 1;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.weighty = 0;
		_clientContainer.add(_addClientBtn, gc);
		
		gc.gridx = 4;
		gc.gridy = 2;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.weighty = 0;
		_clientContainer.add(_editclientBtn, gc);
		
		gc.gridx = 4;
		gc.gridy = 3;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.weighty = 0;
		_clientContainer.add(_delClientBtn, gc);
		
		_tabbedPane.addTab("Clients", _clientContainer);
		AddClient addClientDialog = new AddClient(this, "Ajouter un client", true);
		addClientDialog.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				refreshTable();
			}
		});
		
		_tableClients.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (_tableClients.getSelectedRow() != -1) {
					_email = _tableClients.getValueAt(_tableClients.getSelectedRow(), 2).toString();
				}
			}
		});
		
		_addClientBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addClientDialog.setVisible(true);
			}
		});
		
		_delClientBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				if (_tableClients.getSelectedRow() != -1) {
					String email = _tableClients.getValueAt(_tableClients.getSelectedRow(), 2).toString();
					Database.getInstance().deleteClient(email);
					refreshTable();
				}
			}
		});
		
		_editclientBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				if (_tableClients.getSelectedRow() != -1) {
					String email = _tableClients.getValueAt(_tableClients.getSelectedRow(), 2).toString();
					String nom = _tableClients.getValueAt(_tableClients.getSelectedRow(), 0).toString();
					String prenom = _tableClients.getValueAt(_tableClients.getSelectedRow(), 1).toString();
					int numVoie = Integer.parseInt(_tableClients.getValueAt(_tableClients.getSelectedRow(), 3).toString());
					String voie = _tableClients.getValueAt(_tableClients.getSelectedRow(), 4).toString();
					int codePostal = Integer.parseInt(_tableClients.getValueAt(_tableClients.getSelectedRow(), 5).toString().toString());
					String ville = _tableClients.getValueAt(_tableClients.getSelectedRow(), 6).toString();
					Database.getInstance().updateClient(
							nom, 
							prenom, 
							_email, 
							numVoie, 
							voie, 
							codePostal, 
							ville, 
							email);;
					refreshTable();
				}
			}
		});
	}
	
	private void fillTable() {		
		_tableClients = new JTable();
		refreshTable();
		_scrollTable = new JScrollPane(_tableClients);
	}
	
	private void refreshTable() {
		_tableClients.setModel(Database.getInstance().getClientsAndAdresses());
	}
	
	private void initFacturesComponents() {
		_facturesContainer.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.gridy = gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 0.2;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		_facturesContainer.add(new JLabel("Nom : "), gc);
		gc.insets = new Insets(5, 5, 5, 200);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0.6;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		_facturesContainer.add(new JTextField(), gc);
		gc.insets = new Insets(5, 5, 5, 5);
		gc.weightx = 0.2;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		_facturesContainer.add(new JLabel("Prénom : "), gc);
		gc.insets = new Insets(5, 5, 5, 200);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0.6;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 2;
		_facturesContainer.add(new JTextField(), gc);
		
		_tabbedPane.addTab("Factures", _facturesContainer);
	}
}
