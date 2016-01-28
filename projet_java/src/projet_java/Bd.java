package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {

	Statement st =null;

	
	public void ConnexionBd(){
		String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			System.out.println("Connecté");
        	}
		catch (SQLException e) {
		e.printStackTrace();
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		} 
	}
	
	public ResultSet requete(String req){
		try {
				return st.executeQuery(req);
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}

	}
	
	public void insererBd(String nom, String prenom, String telephone, String mail, String formation, String anneeDiplomation) {
		//String sql = "INSERT INTO Authentification VALUES ('"
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    
        Integer num;
        String test;
	    
			
            ResultSet rs = st.executeQuery(sql);
            
	    finally {
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
                }
    }

}