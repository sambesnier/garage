package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bdd.Database;

public class AddCar extends JDialog implements ActionListener {
	private JComboBox _marque;
	private JTextField _modele = new JTextField();
	private JTextField _couleur = new JTextField();
	private JTextField _puissance = new JTextField();
	private JTextField _immatriculation = new JTextField();
	
	private String _email;
	
	private JPanel _mainPanel;
	private JButton _valideBtn = new JButton("Valider");
	private JButton _cancelBtn = new JButton("Annuler");
	
	public AddCar(JFrame f, String titre, boolean modal) {
		super(f, titre, modal);
		
		_valideBtn.addActionListener(this);
		_cancelBtn.addActionListener(this);
		
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Marque :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_marque = Database.getInstance().getMarques();
		_mainPanel.add(_marque, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Modèle :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_modele, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Couleur :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_couleur, gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Puissance :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 7;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_puissance, gc);
		
		gc.gridx = 0;
		gc.gridy = 8;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Immatriculation :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 9;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_immatriculation, gc);
		
		gc.gridx = 0;
		gc.gridy = 10;
		gc.weightx = 0.5;
		gc.gridwidth = 1;
		_mainPanel.add(_valideBtn, gc);
		
		gc.gridx = 1;
		gc.gridy = 10;
		gc.weightx = 0.5;
		gc.gridwidth = 1;
		_mainPanel.add(_cancelBtn, gc);
		
		this.getContentPane().add(_mainPanel);
		this.setSize(500, 500);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (_valideBtn == e.getSource()) {
			ArrayList<String> errors = new ArrayList<String>();

			if (_modele.getText().length() == 0)
				errors.add("Vous devez saisir un modele");
			if (_couleur.getText().length() == 0)
				errors.add("Vous devez saisir une couleur");
			if (_puissance.getText().length() == 0)
				errors.add("Vous devez saisir une puissance");
			if (_immatriculation.getText().length() == 0)
				errors.add("Vous devez saisir une immatriculation");
			
			if (errors.size() == 0) {
				String marque = _marque.getSelectedItem().toString();
				String modele = _modele.getText();
				String couleur = _couleur.getText();
				int puissance = Integer.parseInt(_puissance.getText());
				String immatriculation = _immatriculation.getText();		
			
				Database bdd = Database.getInstance();			
				bdd.addCar(_email, marque, modele, couleur, puissance, immatriculation);			
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(
						null, 
						"Vous devez renseigner tous les champs", 
						"Attention", 
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (_cancelBtn == e.getSource()) {
			setVisible(false);
		}
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

}
