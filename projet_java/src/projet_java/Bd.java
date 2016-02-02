package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {

	Statement st = null;
	Connection cn = null;

	
	 public void ConnexionBdAnnuaire(){
		String url = "jdbc:mysql://binary-digit.net:3306/bd_annuaire";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			System.out.println("Connexion BD Annuaire reussie");
        	}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();}
		} 
	
	public void ConnexionBdAuth(){
		String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			System.out.println("Connexion BD Authentification reussie");
        	}
		catch (SQLException e) {
		e.printStackTrace();
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		} 
	}
	
	public void DeconnexionBd(){
		try {
			cn.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet RequeteSelect(String req){
		try {
				return (st.executeQuery(req));
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}

	}
	
	public int RequeteAutre(String req){
		try {
				st.executeUpdate(req);
				return(1);
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;

	}
	
	
	 public void consulterBdAnnuaire(int pNumeroFiche){
		 ResultSet rs =  RequeteSelect("SELECT * FROM Annuaire WHERE numeroFiche ='"+pNumeroFiche+"';");
		 try {
			while(rs.next()){
				int numfiche = rs.getInt(1);
				String nom = rs.getString(2);
				String prenom = rs.getString(3);
				String telephone = rs.getString(4);
				String formation = rs.getString(5);
				String anneeDiplomation = rs.getString(6);
				System.out.println("nom : "+nom+" prenom : "+prenom+ " telephone : "+telephone+" formation : "+formation+" année de Diplomation : "+anneeDiplomation);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	
}