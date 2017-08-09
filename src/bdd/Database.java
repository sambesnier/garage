package bdd;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

public class Database {
	
	private static Database INSTANCE = null;
	
	private Connection _connexion;
	
	private Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion();
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trouv�e");
		}
	}
	
	public static Database getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
		return INSTANCE;
	}
	
	private void connexion() {
		String url = "jdbc:mysql://localhost:3306/gestion_garage_h";
		String utilisateur = "root";
		String motDePasse = "1234";
		try {
		    set_connexion((Connection) DriverManager.getConnection( url, utilisateur, motDePasse ));
		    /* Ici, nous placerons nos requêtes vers la BDD */
		    /* ... */
		    System.out.println("connexion réssie");

		} catch ( SQLException e ) {
		    /* Gérer les éventuelles erreurs ici */
		}
	}

	private Connection get_connexion() {
		return _connexion;
	}

	private void set_connexion(Connection _connexion) {
		this._connexion = _connexion;
	}
	
	public void addClient(
			String nom, 
			String prenom,
			String email,
			int numVoie,
			String voie,
			int codePostal,
			String ville) {
		String insertClient = "INSERT INTO clients (nom,prenom,email) VALUES (?,?,?)";
		String insertAdresse = "INSERT INTO adresses (num_voie, voie, code_postal, ville, client) VALUES (?,?,?,?,?)";
		try {
			_connexion.setAutoCommit(false);
			PreparedStatement prst = _connexion.prepareStatement(insertClient, Statement.RETURN_GENERATED_KEYS);
			prst.setString(1, nom);
			prst.setString(2, prenom);
			prst.setString(3, email);
			prst.execute();
			ResultSet rs = prst.getGeneratedKeys();
			long id = 0;
			if (rs.next()) {
				id = rs.getLong(1);
			}
			
			PreparedStatement prst2 = _connexion.prepareStatement(insertAdresse);
			prst2.setInt(1, numVoie);
			prst2.setString(2, voie);
			prst2.setInt(3, codePostal);
			prst2.setString(4, ville);
			prst2.setLong(5, id);
			prst2.execute();
			
			_connexion.commit();
			
			prst.close();
			prst2.close();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				_connexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void addCar(
			String email, 
			String marque, 
			String modele, 
			String couleur, 
			int puissance, 
			String immatriculation) {
		String selectMarqueId = "SELECT id_marque FROM marques WHERE marque = ?";
		String selectClientId = "SELECT id_client FROM clients WHERE email = ?";
		String insertCar = "INSERT INTO voitures (marque_id, modele, couleur, puissance, immatriculation) VALUES (?,?,?,?,?)";
		String insertCarClient = "INSERT INTO voiture_proprietaire (client_id, voiture_id) VALUES (?,?)";
		
		try {
			long idClient = 0;
			long idMarque = 0;
			_connexion.setAutoCommit(false);
			
			PreparedStatement prstIdClient = _connexion.prepareStatement(selectClientId);
			prstIdClient.setString(1, email);
			ResultSet rs = prstIdClient.executeQuery();
			if (rs.next()) {
				idClient = rs.getLong("id_client");
			}
			
			PreparedStatement prstIdMarque = _connexion.prepareStatement(selectMarqueId);
			prstIdMarque.setString(1, marque);
			ResultSet rsMarque = prstIdMarque.executeQuery();
			if (rsMarque.next()) {
				idMarque = rsMarque.getLong("id_marque");
			}
			
			PreparedStatement prst = _connexion.prepareStatement(insertCar, Statement.RETURN_GENERATED_KEYS);
			prst.setLong(1, idMarque);
			prst.setString(2, modele);
			prst.setString(3, couleur);
			prst.setInt(4, puissance);
			prst.setString(5, immatriculation);
			prst.execute();
			ResultSet rsAddCar = prst.getGeneratedKeys();
			long idCar = 0;
			if (rsAddCar.next()) {
				idCar = rsAddCar.getLong(1);
			}
			
			PreparedStatement prst2 = _connexion.prepareStatement(insertCarClient);
			prst2.setLong(1, idClient);
			prst2.setLong(2, idCar);
			prst2.execute();
			
			_connexion.commit();
			
			prst.close();
			prst2.close();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				_connexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public DefaultTableModel getClientsAndAdresses() {
		String[] headers = {
				"Nom", "Prénom", "Email", "Num. voie", "Voie", "Code postal", "Ville"
		};
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		
		Statement st;
		try {
			st = _connexion.createStatement();
			String sql = "SELECT c.nom, c.prenom, c.email, a.num_voie, a.voie, a.code_postal, a.ville\r\n" + 
					"FROM clients as c\r\n" + 
					"INNER JOIN adresses as a\r\n" + 
					"WHERE c.id_client = a.client";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
			    String a = rs.getString("nom");
			    String b = rs.getString("prenom");
			    String c = rs.getString("email");
			    String d = rs.getString("num_voie");
			    String e = rs.getString("voie");
			    String f = rs.getString("code_postal");
			    String g = rs.getString("ville");
			    model.addRow(new Object[]{a, b, c, d, e, f, g});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public DefaultTableModel getClients() {
		String[] headers = {
				"Nom", "Prénom", "Email"
		};
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		
		Statement st;
		try {
			st = _connexion.createStatement();
			String sql = "SELECT c.nom, c.prenom, c.email\r\n" + 
					"FROM clients as c";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
			    String a = rs.getString("nom");
			    String b = rs.getString("prenom");
			    String c = rs.getString("email");
			    model.addRow(new Object[]{a, b, c});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public DefaultTableModel getCarsByEmail(String email) {
		String[] headers = {
				"Marque", "Modèle", "Couleur", "Puissance", "Immatriculation"
		};
		DefaultTableModel model = new DefaultTableModel(headers, 0);

		try {
			String sql = "SELECT m.marque, v.modele, v.couleur, v.puissance, v.immatriculation\r\n" + 
					"FROM voitures as v\r\n" + 
					"INNER JOIN marques as m\r\n" + 
					"INNER JOIN voiture_proprietaire as vp\r\n" + 
					"INNER JOIN clients as c\r\n" + 
					"WHERE m.id_marque = v.marque_id\r\n" + 
					"AND v.id_voiture = vp.voiture_id\r\n" + 
					"AND c.id_client = vp.client_id\r\n" + 
					"AND c.email = ?";
			PreparedStatement prst = _connexion.prepareStatement(sql);
			prst.setString(1, email);
			ResultSet rs = prst.executeQuery();
			
			while(rs.next())
			{
			    String a = rs.getString("marque");
			    String b = rs.getString("modele");
			    String c = rs.getString("couleur");
			    int d = rs.getInt("puissance");
			    String e = rs.getString("immatriculation");
			    model.addRow(new Object[]{a, b, c, d, e});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public JComboBox getMarques() {
		JComboBox marques = new JComboBox();
		Statement st;
		try {
			st = _connexion.createStatement();
			String sql = "SELECT marque FROM marques";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
			    String a = rs.getString("marque");
			    marques.addItem(a);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marques;
	}
	
	public void deleteClient(String email) {
		try {
			String clientId = "SELECT id_client from clients WHERE email = ?";
			String delAdresse = "DELETE from adresses WHERE client = ?";
			String delClient = "DELETE from clients WHERE email = ?";
			
			_connexion.setAutoCommit(false);
			PreparedStatement prst = _connexion.prepareStatement(clientId);
			prst.setString(1, email);
			prst.execute();
			ResultSet rs = prst.executeQuery();
			rs.next();
			long id = rs.getLong("id_client");
			
			PreparedStatement prst2 = _connexion.prepareStatement(delAdresse);
			prst2.setLong(1, id);
			prst2.execute();
			
			PreparedStatement prst3 = _connexion.prepareStatement(delClient);
			prst3.setString(1, email);
			prst3.execute();
			
			_connexion.commit();
			
			prst.close();
			prst2.close();
			prst3.close();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				_connexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void updateClient(
			String nom, 
			String prenom,
			String email,
			int numVoie,
			String voie,
			int codePostal,
			String ville,
			String newEmail) {
		String clientId = "SELECT id_client from clients WHERE email = ?";
		String updateClient = "UPDATE clients SET nom = ?, prenom = ?, email = ? WHERE email = ?";
		String updateAdresse = "UPDATE adresses SET num_voie = ?, voie = ?, code_postal = ?, ville = ? WHERE client = ?";
		try {
			_connexion.setAutoCommit(false);
			PreparedStatement prst = _connexion.prepareStatement(updateClient);
			prst.setString(1, nom);
			prst.setString(2, prenom);
			prst.setString(3, newEmail);
			prst.setString(4, email);
			prst.execute();

			PreparedStatement prstId = _connexion.prepareStatement(clientId);
			prstId.setString(1, email);
			prstId.execute();
			ResultSet rs = prstId.executeQuery();
			rs.next();
			long id = rs.getLong("id_client");
			
			PreparedStatement prst2 = _connexion.prepareStatement(updateAdresse);
			prst2.setInt(1, numVoie);
			prst2.setString(2, voie);
			prst2.setInt(3, codePostal);
			prst2.setString(4, ville);
			prst2.setLong(5, id);
			prst2.execute();
			
			_connexion.commit();
			
			prst.close();
			prst2.close();
			prstId.close();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				_connexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
