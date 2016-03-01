//#####################################################################################
// Class pour la connexion de la base de donnes										 
//#####################################################################################
package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {

	Statement st = null;
	Connection cn = null;

	//#####################################################################################
	// Fonction pour la connexion a la base de donnees ou sont stocké les infos des utilisateurs
	//#####################################################################################
	 public void ConnexionBdAnnuaire(){
		String url = "jdbc:mysql://binary-digit.net:3306/bd_annuaire";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connexion à la base de donnees
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
        	}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();}
		} 
	
	//#####################################################################################
	// Fonction pour la connexion a la base de donnees ou sont stocké les infos de connexion des utilisateurs
	//#####################################################################################
	public void ConnexionBdAuth(){
		String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connexion à la base de donnees
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
        	}
		catch (SQLException e) {
		e.printStackTrace();
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		} 
	}
	
	//#####################################################################################
	// Fonction pour la déconnexion des base de données
	//#####################################################################################
	public void DeconnexionBd(){
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//#####################################################################################
	// Fonction pour executer une requette SELECT dans une base de données
	//#####################################################################################
	public ResultSet RequeteSelect(String req){
		try {
				return (st.executeQuery(req));
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}

	}
	
	//#####################################################################################
	// Fonction pour executer une requette dans une base de données
	//#####################################################################################
	public int RequeteAutre(String req){
		try {
				st.executeUpdate(req);
				return(1);
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;

	}
	 
	
}