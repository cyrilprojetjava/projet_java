package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.attribute.standard.NumberOfInterveningJobs;

import com.sun.javafx.geom.transform.GeneralTransform3D;

public class Utilisateur {
	

	private String nom;
	private String prenom;
	private String telephone;
	private String mdp;
	private String email;
	private String formation;
	private String anneeDiplome;
	private Integer numeroFiche;
	
	private Bd BdAuth = new Bd();
	private Bd BdAnnuaire = new Bd();
	Statement st = null;

	
	public int creerCompte(Utilisateur user) {
		System.out.println("Essaie de Creation de compte");
		BdAuth.ConnexionBdAuth();
		ResultSet rs = BdAuth.RequeteSelect("SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+user.email+"';");
		try {
			rs.last(); 
		    Integer nbItem = rs.getRow(); 
		    rs.beforeFirst();
		    if(nbItem ==0)
				{
					    System.out.println("Creation de compte realise !");
					    BdAuth.RequeteAutre("INSERT INTO Authentification(email,mdp) VALUES('"+user.email+"','"+user.mdp+"');");
					    rs =  BdAuth.RequeteSelect("SELECT numero_fiche FROM Authentification WHERE email ='"+user.email+"';");
					    Integer numFiche = -1;
					    while(rs.next()){
							numFiche = rs.getInt(1);
						 }
					    this.numeroFiche = numFiche;
					    System.out.println(numFiche);
					    //BdAuth.DeconnexionBd();

					    PrintStream     fluxSortieSocket;
						BufferedReader  fluxEntreeSocket;
						Socket sockCom;
						
						try {
							sockCom = new Socket("localhost",13215);
							fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
							fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
							String messageInscription = "CREATE#"+user.getNumeroFiche()+"#"+user.getNom()+"#"+user.getPrenom()+"#"+user.getTelephone()+"#"+user.getFormation()+"#"+user.getAnneeDiplome();
							System.out.println(messageInscription);
							fluxSortieSocket.println(messageInscription);
							while(true){
								String requete = fluxEntreeSocket.readLine();
								
							if(requete.equals("CREATIONOK"))
							{
								BdAnnuaire.ConnexionBdAnnuaire();
								BdAnnuaire.RequeteAutre("INSERT INTO Annuaire VALUES('"+user.getNumeroFiche()+"','"+user.getNom()+"','"+user.getPrenom()+"','"+user.getTelephone()+"','"+user.getFormation()+"','"+user.getAnneeDiplome()+"');");
								//BdAnnuaire.DeconnexionBd();
								return(1);
							}
							else
							{
								System.out.println("Erreur de creation dans l'annuaire !");
								return(-1);
							}
							}
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
				} 
				else 
				{
					System.out.println("Echec de Creation de compte");
					return(-1);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
		
	
	public int connexion(String pemail, String pmdp){
		System.out.println("Essaie de connexion d'un client en cours");
		BdAuth.ConnexionBdAuth();
		String sql="SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+pemail+"';";
		
		try {
			ResultSet rs = BdAuth.RequeteSelect(sql);
			rs.last(); 
		    Integer nbItem = rs.getRow(); 
		    rs.beforeFirst();
		    if(nbItem ==0)
				{
					System.out.println("Echec de connexion");    
					return(-1);
				} 
			else 
				{
					while (rs.next())
					{
						if(pmdp.equals(rs.getString("mdp")))
						{
							System.out.println("Connexion reussie");
							return(rs.getInt("numero_fiche"));
						}
						else
						{
							System.out.println("Echec de connexion");
							return(-1);
						}
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public String consulterInfoPerso(String pNumeroFiche){
		
		PrintStream     fluxSortieSocket;
		BufferedReader  fluxEntreeSocket;
		Socket sockCom;
		
		try {
			sockCom = new Socket("localhost",13215);
			fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
			fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
			
			 BdAnnuaire.ConnexionBdAnnuaire();
			 ResultSet rs =  BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE numero_Fiche ='"+pNumeroFiche+"';");
			 try {
				while(rs.next()){
					int numfiche = rs.getInt(1);
					String nom = rs.getString(2);
					String prenom = rs.getString(3);
					String telephone = rs.getString(4);
					String formation = rs.getString(5);
					String anneeDiplomation = rs.getString(6);
					String message = "Nom : "+nom+"  Prenom : "+prenom+ "  Telephone : "+telephone+"  Formation : "+formation+"  Année obtention Diplome : "+anneeDiplomation;
					return(message);
					//BdAnnuaire.DeconnexionBd();
					
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return("Erreur consultation informations personnelles");
		
	 }
	
	public String modificationInformationNom(String pNumeroFiche, String pNom){
		
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET nom = '"+pNom+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification du nom a ete correctement effectuee");
		}
		return ("erreur modif nom");
	}

	public String modificationInformationPrenom(String pNumeroFiche, String pPrenom){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET prenom = '"+pPrenom+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification du prenom a ete correctement effectuee");
		}
		return ("erreur modif prenom");
	}
	
	public String modificationInformationTel(String pNumeroFiche, String pTel){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET telephone = '"+pTel+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return ("La modification du numero de telephone a ete correctement effectuee");
		}
		return ("erreur modif tel");
	}
	
	public String modificationInformationFormation(String pNumeroFiche, String pFormation){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET formation = '"+pFormation+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre formation a ete correctement effectuee");
		}
		return ("erreur modif formation");
	}
	
	public String modificationInformationAnneeDiplome(String pNumeroFiche, String pAnneeDiplome){
		BdAnnuaire.ConnexionBdAnnuaire();
		//Corriger dans la BDannuaire le nom de la colonne annnediplome en "anneediplome" et du coup modifier le nom de la colonne dans la requete ci-dessous
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET annnediplome = '"+pAnneeDiplome+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre formation a ete correctement effectuee");
		}
		return ("erreur modif annee diplome");
	}
	
	public String modificationInformationMail(String pNumeroFiche, String pMail){
		BdAuth.ConnexionBdAuth();
		//Corriger dans la BDannuaire le nom de la colonne annnediplome en "anneediplome" et du coup modifier le nom de la colonne dans la requete ci-dessous
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET email = '"+pMail+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mail a ete correctement effectuee");
		}
		return ("erreur modif mail");
	}
	
	public String modificationInformationMotDePasse(String pNumeroFiche, String pMotDePasse){
		BdAuth.ConnexionBdAuth();
		//Corriger dans la BDannuaire le nom de la colonne annnediplome en "anneediplome" et du coup modifier le nom de la colonne dans la requete ci-dessous
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET mdp = '"+pMotDePasse+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mot de passe a ete correctement effectuee");
		}
		return ("erreur modif mot de passe");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bd BdAuth = new Bd();
		BdAuth.ConnexionBdAuth();
		
		
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNumeroFiche(Integer numeroFiche) {
		this.numeroFiche = numeroFiche;
	}
	
	public Integer getNumeroFiche() {
		return numeroFiche;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMdp() {
		return mdp;
	}
	
	 public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getFormation() {
		return formation;
	}


	public void setFormation(String formation) {
		this.formation = formation;
	}


	public String getAnneeDiplome() {
		return anneeDiplome;
	}
	
	public void setAnneeDiplome(String anneeDiplome) {
		this.anneeDiplome = anneeDiplome;
	}

}
