package projet_java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
	

	private String nom;
	private String prenom;
	private String telephone;
	private String mail;
	private String formation;
	private String anneeDiplomation;
	
	private Bd BdAuth = new Bd();
	
	public void creerCompte(String pnom, String pprenom, String ptelephone, String pmail, String pformation, String panneeDiplomation) {
		
		BdAuth.ConnexionBd();
		/*Trouver comment utiliser pmail*/
		ResultSet rs = BdAuth.requete("SELECT email FROM Authentification WHERE email = pmail;");
		try {
			while (rs.next())
			{
			  System.out.println(rs.getInt("numero_fiche"));
			  System.out.println(rs.getString("email"));
			  System.out.println(rs.getString("mdp"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BdAuth.insererBd(pnom,pprenom,ptelephone,pmail,pformation,panneeDiplomation);
		
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
