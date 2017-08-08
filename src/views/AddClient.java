package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import bdd.Database;

public class AddClient extends JDialog implements ActionListener {
	private JTextField _nom = new JTextField();
	private JTextField _prenom = new JTextField();
	private JTextField _email = new JTextField();
	private JTextField _num_voie = new JTextField();
	private JTextField _voie = new JTextField();
	private JTextField _code_postal = new JTextField();
	private JTextField _ville = new JTextField();
	
	private JPanel _mainPanel;
	private JButton _valideBtn = new JButton("Valider");
	private JButton _cancelBtn = new JButton("Annuler");
	
	public AddClient(JFrame f, String titre, boolean modal) {
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
		_mainPanel.add(new JLabel("Nom :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_nom, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Prénom :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_prenom, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Email :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_email, gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Numéro de voie :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 7;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_num_voie, gc);
		
		gc.gridx = 0;
		gc.gridy = 8;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Voie :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 9;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_voie, gc);
		
		gc.gridx = 0;
		gc.gridy = 10;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Code postal :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 11;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_code_postal, gc);
		
		gc.gridx = 0;
		gc.gridy = 12;
		gc.weightx = 1;
		_mainPanel.add(new JLabel("Ville :"), gc);
		
		gc.gridx = 0;
		gc.gridy = 13;
		gc.weightx = 1;
		gc.gridwidth = 2;
		_mainPanel.add(_ville, gc);
		
		gc.gridx = 0;
		gc.gridy = 14;
		gc.weightx = 0.5;
		gc.gridwidth = 1;
		_mainPanel.add(_valideBtn, gc);
		
		gc.gridx = 1;
		gc.gridy = 14;
		gc.weightx = 0.5;
		gc.gridwidth = 1;
		_mainPanel.add(_cancelBtn, gc);
		
		this.getContentPane().add(_mainPanel);
		this.setSize(500, 650);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (_valideBtn == e.getSource()) {
			
			ArrayList<String> errors = new ArrayList<String>();
			
			if (_nom.getText().length() == 0)
				errors.add("Vous devez saisir un nom");
			if (_prenom.getText().length() == 0)
				errors.add("Vous devez saisir un prenom");
			if (_email.getText().length() == 0)
				errors.add("Vous devez saisir un email");
			if (_num_voie.getText().length() == 0)
				errors.add("Vous devez saisir un numero de voie");
			if (_voie.getText().length() == 0)
				errors.add("Vous devez saisir un nom de voie");	
			if (_code_postal.getText().length() == 0)
				errors.add("Vous devez saisir un code postal");
			if (_ville.getText().length() == 0)
				errors.add("Vous devez saisir une ville");	
			
			if (errors.size() == 0) {
				String nom = _nom.getText();
				String prenom = _prenom.getText();
				String email = _email.getText();
				int numVoie = Integer.parseInt(_num_voie.getText());
				String voie = _voie.getText();
				int codePostal = Integer.parseInt(_code_postal.getText());
				String ville = _ville.getText();			
			
				Database bdd = Database.getInstance();			
				bdd.addClient(nom, prenom, email, numVoie, voie, codePostal, ville);			
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(
						null, 
						"Vous devez renseigner tous les champs", 
						"Attention", 
						JOptionPane.WARNING_MESSAGE);
			}
			
		}
		else if (_cancelBtn == e.getSource()) {
			this.setVisible(false);
		}
	}
}
