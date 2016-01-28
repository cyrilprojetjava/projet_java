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
	
	public int creerCompte(String pnom, String pprenom,  String pemail, String pmdp, String ptelephone, String pformation, String panneeDiplomation) {
		System.out.println("Création de compte");
		BdAuth.ConnexionBd();
		/*Trouver comment utiliser pemail*/
		ResultSet rs = BdAuth.requete("SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+pemail+"';");
		try {
			rs.last(); 
		    Integer nbItem = rs.getRow(); 
		    rs.beforeFirst();
		    if(nbItem ==0)
				{
					    System.out.println("Création de compte réalisé");
					    BdAuth.updateRequete("INSERT INTO Authentification VALUES(67,'"+pemail+"','"+pmdp+"');");
					    //On doit communiquer avec l'autre serveur pour lui dire d'inserer les autres données
					    //Numéro fiche AUTO INCREMENT DANS LA BD ?
					    return(1);
				} 
				else 
				{
					return(0);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
		
	
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
