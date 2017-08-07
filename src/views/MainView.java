package views;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class MainView extends JFrame {
	public MainView() {
		this.setTitle("Garage Manager");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		//tabbedPane.setLayout(new BoxLayout(tabbedPane, BoxLayout.Y_AXIS));
		JPanel formClient = new JPanel();
		formClient.setLayout(new BoxLayout(formClient, BoxLayout.X_AXIS));
		JLabel nomLabel = new JLabel("Nom du client : ");
		JTextField nomTextField = new JTextField();
		JButton rechercheButton = new JButton("Recherche");
		nomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		nomTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		rechercheButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		formClient.add(nomLabel);
		formClient.add(nomTextField);
		formClient.add(rechercheButton);
		
		tabbedPane.addTab("Clients", formClient);
		this.getContentPane().add(tabbedPane);
	}
}
