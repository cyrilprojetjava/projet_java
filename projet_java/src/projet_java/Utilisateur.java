package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utilisateur {
	

	private String nom;
	private String prenom;
	private String telephone;
	private String mail;
	private String formation;
	private String anneeDiplomation;
	
	private Bd BdAuth = new Bd();
	
	public int creerCompte(String pnom, String pprenom, String ptelephone, String pemail, String pformation, String panneeDiplomation) {
		System.out.println("Création de compte");
		BdAuth.ConnexionBd();
		/*Trouver comment utiliser pemail*/
		ResultSet rs = BdAuth.requete("SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+pemail+"';");
		try {
			if (!rs.next() ) 
				{
					    System.out.println("no data");
					    return(0);
				} 
				else 
				{
					while (rs.next())
					{
					    System.out.println(rs.getInt("numero_fiche"));
					    System.out.println(rs.getString("email"));
					    System.out.println(rs.getString("mdp"));
					}
					return(1);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
		
		
	
	
	/*public int connexion(String pemail, String pmdp){
		System.out.println("Essaie de connexion d'un client en cours");
		Statement st =null;
		Connection cn =null;
		String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
		String sql="SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+pemail+"';";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.last(); 
		    Integer nbItem = rs.getRow(); 
		    rs.beforeFirst();
		    if(nbItem ==0)
		    {
			    System.out.println(nbItem);
			    System.out.println("Echec de connexion");    
				return(0);
		    }else
		    {
				while (rs.next())
				{
					if(pmdp.equals(rs.getString("mdp")))
					{
						System.out.println("Connexion réussie");
						return(rs.getInt("numero_fiche"));
					}
					else
					{
						System.out.println("Echec de connexion identifiants incorrects");
						return(0);
					}
				}
		    }
                        
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
                }
		return -1;
	}*/
	
	public int connexion(String pemail, String pmdp){
		System.out.println("Essaie de connexion d'un client en cours");
		BdAuth.ConnexionBd();
		String sql="SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+pemail+"';";
		
		try {
			ResultSet rs = BdAuth.requete(sql);
			rs.last(); 
		    Integer nbItem = rs.getRow(); 
		    rs.beforeFirst();
		    if(nbItem ==0)
				{
					System.out.println("Echec de connexion");    
					return(0);
				} 
			else 
				{
					while (rs.next())
					{
						if(pmdp.equals(rs.getString("mdp")))
						{
							System.out.println("Connexion réussie");
							return(rs.getInt("numero_fiche"));
						}
						else
						{
							System.out.println("Echec de connexion");
							return(0);
						}
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bd BdAuth = new Bd();
		BdAuth.ConnexionBd();
		
		
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getFormation() {
		return formation;
	}


	public void setFormation(String formation) {
		this.formation = formation;
	}


	public String getAnneeDiplomation() {
		return anneeDiplomation;
	}


	public void setAnneeDiplomation(String anneeDiplomation) {
		this.anneeDiplomation = anneeDiplomation;
	}

}
