package bdd;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		String url = "jdbc:mysql://localhost:3306/gestion_garage";
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
}
