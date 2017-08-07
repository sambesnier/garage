package bdd;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Database {
	
	private static Database INSTANCE = null;
	
	private Connection _connexion;
	
	private Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connexion réussie");
			connexion();
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trouvée");
		}
	}
	
	public static Database getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
		return INSTANCE;
	}
	
	public void connexion() {
		String url = "jdbc:mysql://localhost:3306/gestion_garage";
		String utilisateur = "root";
		String motDePasse = "1234";
		try {
		    set_connexion((Connection) DriverManager.getConnection( url, utilisateur, motDePasse ));
		    /* Ici, nous placerons nos requêtes vers la BDD */
		    /* ... */

		} catch ( SQLException e ) {
		    /* Gérer les éventuelles erreurs ici */
		}
	}

	public Connection get_connexion() {
		return _connexion;
	}

	public void set_connexion(Connection _connexion) {
		this._connexion = _connexion;
	}
}
