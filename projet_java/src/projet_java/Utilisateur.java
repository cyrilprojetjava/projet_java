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
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.NumberOfInterveningJobs;

import com.mysql.jdbc.ResultSetMetaData;
import com.sun.javafx.geom.transform.GeneralTransform3D;

public class Utilisateur {
	

	private String nom;
	private String prenom;
	private String telephone;
	private String mdp;
	private String email;
	private String formation;
	private String anneeDiplome;
	private String competence;
	private Integer numeroFiche;
	
	private Bd BdAuth = new Bd();
	private Bd BdAnnuaire = new Bd();
	private Bd BdVisibilite = new Bd();
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
							String messageInscription = "CREATE#"+user.getNumeroFiche()+"#"+user.getNom()+"#"+user.getPrenom()+"#"+user.getTelephone()+"#"+user.getFormation()+"#"+user.getAnneeDiplome()+"#"+user.getCompetence();
							System.out.println(messageInscription);
							fluxSortieSocket.println(messageInscription);
							while(true){
								String requete = fluxEntreeSocket.readLine();
								
							if(requete.equals("CREATIONOK"))
							{
								BdAnnuaire.ConnexionBdAnnuaire();
								BdAnnuaire.RequeteAutre("INSERT INTO Annuaire VALUES('"+user.getNumeroFiche()+"','"+user.getNom()+"','"+user.getPrenom()+"','"+user.getTelephone()+"','"+user.getFormation()+"','"+user.getAnneeDiplome()+"','"+user.getCompetence()+"');");
								BdAnnuaire.RequeteAutre("INSERT INTO visibilite (numero_fiche,visi_nom,visi_prenom,visi_telephone,visi_formation,visi_anneediplome,visi_competence) VALUES('"+user.getNumeroFiche()+"',1,1,1,1,1,1);");
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
					String competence = rs.getString(7);
					String message = "Nom : "+nom+"  Prenom : "+prenom+ "  Telephone : "+telephone+"  Formation : "+formation+"  Annee obtention Diplome : "+anneeDiplomation+"  Competence : "+competence;
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
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET annnediplome = '"+pAnneeDiplome+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre formation a ete correctement effectuee");
		}
		return ("erreur modif annee diplome");
	}
	
	public String modificationInformationMail(String pNumeroFiche, String pMail){
		BdAuth.ConnexionBdAuth();
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET email = '"+pMail+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mail a ete correctement effectuee");
		}
		return ("erreur modif mail");
	}
	
	public String modificationInformationMotDePasse(String pNumeroFiche, String pMotDePasse){
		BdAuth.ConnexionBdAuth();
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET mdp = '"+pMotDePasse+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mot de passe a ete correctement effectuee");
		}
		return ("erreur modif mot de passe");
	}
	
	public String modificationInformationCompetence(String pNumeroFiche, String pCompetence){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET competence = '"+pCompetence+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre competence a ete correctement effectuee");
		}
		return ("erreur modif annee competence");
	}
	
	public String rechercheNom(String pNom){
		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE nom = '"+pNom+"';");
		try {
			String message ="";
			int i=0;
			int j=0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
		    if(nbItem ==0)
				{ return ("Pas d'information concernant ces criteres.");}
			ArrayList<Object> message1 = new ArrayList<Object> ();
			ArrayList<Object> numero_fiche = new ArrayList<>();
			ArrayList<Object> nom = new ArrayList<>();
			ArrayList<Object> prenom = new ArrayList<>();
			ArrayList<Object> telephone = new ArrayList<>();
			ArrayList<Object> formation = new ArrayList<>();
			ArrayList<Object> anneediplome = new ArrayList<>();
			ArrayList<Object> competence = new ArrayList<>();
			while(rs.next())
			{
				numero_fiche.add(rs.getInt(1));
				BdVisibilite.ConnexionBdAnnuaire();
				ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(j)+"';");
				while(rs_visi.next())
				{
					if (rs_visi.getInt(3) == 1)
					{
						nom.add(rs.getString(2));
						if (rs_visi.getInt(4) == 1)
						{
							prenom.add(rs.getString(3));
						}
						else
						{
							prenom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(5) == 1)
						{
							telephone.add(rs.getString(4));
						}
						else
						{
							telephone.add("NON VISIBLE");
						}
						if (rs_visi.getInt(6) == 1)
						{
							formation.add(rs.getString(5));
						}
						else
						{
							formation.add("NON VISIBLE");
						}
						if (rs_visi.getInt(7) == 1)
						{
							anneediplome.add(rs.getString(6));
						}
						else
						{
							anneediplome.add("NON VISIBLE");
						}
						if (rs_visi.getInt(8) == 1)
						{
							competence.add(rs.getString(7));
						}
						else
						{
							competence.add("NON VISIBLE");
						}
						message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
						message = message + message1.get(i);
						i++;
					}
				}
				j++;
			}
			return(message);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("Le nom saisi n'existe pas[");
	}
	
	public String recherchePrenom(String pPrenom){
		
		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE prenom = '"+pPrenom+"';");
		try {
			String message ="";
			int i=0;
			int j=0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
		    if(nbItem ==0)
				{ return ("Pas d'information concernant ces criteres.");}
			ArrayList<Object> message1 = new ArrayList<Object> ();
			ArrayList<Object> numero_fiche = new ArrayList<>();
			ArrayList<Object> nom = new ArrayList<>();
			ArrayList<Object> prenom = new ArrayList<>();
			ArrayList<Object> telephone = new ArrayList<>();
			ArrayList<Object> formation = new ArrayList<>();
			ArrayList<Object> anneediplome = new ArrayList<>();
			ArrayList<Object> competence = new ArrayList<>();
			while(rs.next())
			{
				numero_fiche.add(rs.getInt(1));
				BdVisibilite.ConnexionBdAnnuaire();
				ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(j)+"';");
				while(rs_visi.next())
				{
					
					if (rs_visi.getInt(4) == 1)
					{
						prenom.add(rs.getString(3));
						if (rs_visi.getInt(3) == 1)
						{
							nom.add(rs.getString(2));
						}
						else
						{
							nom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(5) == 1)
						{
							telephone.add(rs.getString(4));
						}
						else
						{
							telephone.add("NON VISIBLE");
						}
						if (rs_visi.getInt(6) == 1)
						{
							formation.add(rs.getString(5));
						}
						else
						{
							formation.add("NON VISIBLE");
						}
						if (rs_visi.getInt(7) == 1)
						{
							anneediplome.add(rs.getString(6));
						}
						else
						{
							anneediplome.add("NON VISIBLE");
						}
						if (rs_visi.getInt(8) == 1)
						{
							competence.add(rs.getString(7));
						}
						else
						{
							competence.add("NON VISIBLE");
						}
						message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
						message = message + message1.get(i);
						i++;
					}	
				}
				j++;
			}
			return(message);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("Le prenom saisi n'existe pas");
	}
	
public String rechercheMail(String pMail){
		
		BdAuth.ConnexionBdAuth();
		ResultSet rsnum = BdAuth.RequeteSelect("SELECT numero_fiche FROM Authentification WHERE email = '"+pMail+"';");
		Integer numFiche;
		try {
			while(rsnum.next()){
				numFiche = rsnum.getInt(1);
				BdAnnuaire.ConnexionBdAnnuaire();
				ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE numero_fiche = '"+numFiche+"';");
				try {
					String message ="";
					int i=0;
					rs.last();
					Integer nbItem = rs.getRow();
					rs.beforeFirst();
				    if(nbItem ==0)
						{ return ("Pas d'information concernant ces criteres.");}
					ArrayList<Object> message1 = new ArrayList<Object> ();
					ArrayList<Object> numero_fiche = new ArrayList<>();
					ArrayList<Object> nom = new ArrayList<>();
					ArrayList<Object> prenom = new ArrayList<>();
					ArrayList<Object> telephone = new ArrayList<>();
					ArrayList<Object> formation = new ArrayList<>();
					ArrayList<Object> anneediplome = new ArrayList<>();
					ArrayList<Object> competence = new ArrayList<>();
					while(rs.next())
					{
						numero_fiche.add(rs.getInt(1));
						BdVisibilite.ConnexionBdAnnuaire();
						ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(i)+"';");
						while(rs_visi.next())
						{
							if (rs_visi.getInt(3) == 1)
							{
								nom.add(rs.getString(2));
							}
							else
							{
								nom.add("NON VISIBLE");
							}
							if (rs_visi.getInt(4) == 1)
							{
								prenom.add(rs.getString(3));
							}
							else
							{
								prenom.add("NON VISIBLE");
							}
							if (rs_visi.getInt(5) == 1)
							{
								telephone.add(rs.getString(4));
							}
							else
							{
								telephone.add("NON VISIBLE");
							}
							if (rs_visi.getInt(6) == 1)
							{
								formation.add(rs.getString(5));
							}
							else
							{
								formation.add("NON VISIBLE");
							}
							if (rs_visi.getInt(7) == 1)
							{
								anneediplome.add(rs.getString(6));
							}
							else
							{
								anneediplome.add("NON VISIBLE");
							}
							if (rs_visi.getInt(8) == 1)
							{
								competence.add(rs.getString(7));
							}
							else
							{
								competence.add("NON VISIBLE");
							}
							
						}
						message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
						
						message = message + message1.get(i);
						i++;
						}
					return(message);
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return("Le mail saisi n'existe pas");
	}

public String rechercheFormation(String pFormation){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE formation = '"+pFormation+"';");
	
	try {
		String message ="";
		int i=0;
		int j=0;
		rs.last();
		Integer nbItem = rs.getRow();
		rs.beforeFirst(); 
	    if(nbItem ==0)
			{ return ("Pas d'information concernant ces criteres.");}
		ArrayList<Object> message1 = new ArrayList<Object> ();
		ArrayList<Object> numero_fiche = new ArrayList<>();
		ArrayList<Object> nom = new ArrayList<>();
		ArrayList<Object> prenom = new ArrayList<>();
		ArrayList<Object> telephone = new ArrayList<>();
		ArrayList<Object> formation = new ArrayList<>();
		ArrayList<Object> anneediplome = new ArrayList<>();
		ArrayList<Object> competence = new ArrayList<>();
		while(rs.next())
		{
			numero_fiche.add(rs.getInt(1));
			BdVisibilite.ConnexionBdAnnuaire();
			ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(j)+"';");
			while(rs_visi.next())
			{
				
				if (rs_visi.getInt(6) == 1)
				{
					formation.add(rs.getString(5));
					if (rs_visi.getInt(3) == 1)
					{
						nom.add(rs.getString(2));
					}
					else
					{
						nom.add("NON VISIBLE");
					}
					if (rs_visi.getInt(4) == 1)
					{
						prenom.add(rs.getString(3));
					}
					else
					{
						prenom.add("NON VISIBLE");
					}
					if (rs_visi.getInt(5) == 1)
					{
						telephone.add(rs.getString(4));
					}
					else
					{
						telephone.add("NON VISIBLE");
					}
					if (rs_visi.getInt(7) == 1)
					{
						anneediplome.add(rs.getString(6));
					}
					else
					{
						anneediplome.add("NON VISIBLE");
					}
					if (rs_visi.getInt(8) == 1)
					{
						competence.add(rs.getString(7));
					}
					else
					{
						competence.add("NON VISIBLE");
					}
					message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
					message = message + message1.get(i);
					i++;
				}	
			}
			j++;
		}
		return(message);
		
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return("La formation saisie n'existe pas");
}
	
public String rechercheAnneeDiplome(String pAnDiplome){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE annnediplome = '"+pAnDiplome+"';");
	
		try {
			String message ="";
			int i=0;
			int j=0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst(); 
		    if(nbItem ==0)
				{ return ("Pas d'information concernant ces criteres.");}
			ArrayList<Object> message1 = new ArrayList<Object> ();
			ArrayList<Object> numero_fiche = new ArrayList<>();
			ArrayList<Object> nom = new ArrayList<>();
			ArrayList<Object> prenom = new ArrayList<>();
			ArrayList<Object> telephone = new ArrayList<>();
			ArrayList<Object> formation = new ArrayList<>();
			ArrayList<Object> anneediplome = new ArrayList<>();
			ArrayList<Object> competence = new ArrayList<>();
			while(rs.next())
			{
				numero_fiche.add(rs.getInt(1));
				BdVisibilite.ConnexionBdAnnuaire();
				ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(j)+"';");
				while(rs_visi.next())
				{
					
					if (rs_visi.getInt(7) == 1)
					{
						anneediplome.add(rs.getString(6));
						if (rs_visi.getInt(3) == 1)
						{
							nom.add(rs.getString(2));
						}
						else
						{
							nom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(4) == 1)
						{
							prenom.add(rs.getString(3));
						}
						else
						{
							prenom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(5) == 1)
						{
							telephone.add(rs.getString(4));
						}
						else
						{
							telephone.add("NON VISIBLE");
						}
						if (rs_visi.getInt(6) == 1)
						{
							formation.add(rs.getString(5));
						}
						else
						{
							formation.add("NON VISIBLE");
						}
						if (rs_visi.getInt(8) == 1)
						{
							competence.add(rs.getString(7));
						}
						else
						{
							competence.add("NON VISIBLE");
						}
						message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
						
						message = message + message1.get(i);
						i++;
					}		
				}
				j++;
			}
			return(message);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return("Personne n'a obtenu de diplome pour cette annee");
}

public String rechercheCompetence(String pCompetence){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM Annuaire WHERE competence = '"+pCompetence+"';");
	
		try {
			String message ="";
			int j=0;
			int i=0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst(); 
		    if(nbItem == 0)
				{ return ("Pas d'information concernant ces criteres.");}
			ArrayList<Object> message1 = new ArrayList<Object> ();
			ArrayList<Object> numero_fiche = new ArrayList<>();
			ArrayList<Object> nom = new ArrayList<>();
			ArrayList<Object> prenom = new ArrayList<>();
			ArrayList<Object> telephone = new ArrayList<>();
			ArrayList<Object> formation = new ArrayList<>();
			ArrayList<Object> anneediplome = new ArrayList<>();
			ArrayList<Object> competence = new ArrayList<>();
			while(rs.next())
			{
				numero_fiche.add(rs.getInt(1));
				BdVisibilite.ConnexionBdAnnuaire();
				ResultSet rs_visi = BdVisibilite.RequeteSelect("SELECT * FROM visibilite WHERE numero_fiche = '"+numero_fiche.get(j)+"';");
				while(rs_visi.next())
				{
					if (rs_visi.getInt(8) == 1)
					{
						competence.add(rs.getString(7));
						if (rs_visi.getInt(3) == 1)
						{
							nom.add(rs.getString(2));
						}
						else
						{
							nom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(4) == 1)
						{
							prenom.add(rs.getString(3));
						}
						else
						{
							prenom.add("NON VISIBLE");
						}
						if (rs_visi.getInt(5) == 1)
						{
							telephone.add(rs.getString(4));
						}
						else
						{
							telephone.add("NON VISIBLE");
						}
						if (rs_visi.getInt(6) == 1)
						{
							formation.add(rs.getString(5));
						}
						else
						{
							formation.add("NON VISIBLE");
						}
						if (rs_visi.getInt(7) == 1)
						{
							anneediplome.add(rs.getString(6));
						}
						else
						{
							anneediplome.add("NON VISIBLE");
						}
						message1.add("Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
						message = message + message1.get(i);
						i++;	
					}	
				}
				j++;
			}
			return(message);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return("Personne ne possede cette competence");
}

public String visibiliteNom(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_nom FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_nom = rs.getInt(1);
			if(visi_nom == 1)
				return("Votre nom est actuellement visible");
			else
				return("Votre nom est actuellement cache aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibiliteNom(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_nom FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_nom = rs.getInt(1);
			if(visi_nom == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_nom = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre nom est maintenant cache aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_nom = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre nom est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite nom");
	
}

public String visibilitePrenom(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_prenom FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_prenom = rs.getInt(1);
			if(visi_prenom == 1)
				return("Votre prenom est actuellement visible");
			else
				return("Votre prenom est actuellement cache aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibilitePrenom(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_prenom FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_prenom = rs.getInt(1);
			if(visi_prenom == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_prenom = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre prenom est maintenant cache aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_prenom = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre prenom est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite prenom");
	
}

public String visibiliteTel(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_telephone FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_tel = rs.getInt(1);
			if(visi_tel == 1)
				return("Votre numero de telephone est actuellement visible");
			else
				return("Votre numero de telephone est actuellement cache aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibiliteTel(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_telephone FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_tel = rs.getInt(1);
			if(visi_tel == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_telephone = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre numero de telephone est maintenant cache aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_telephone = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre numero de telephone est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite telephone");
	
}

public String visibiliteFormation(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_formation FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_formation = rs.getInt(1);
			if(visi_formation == 1)
				return("Votre nom de formation est actuellement visible");
			else
				return("Votre nom de formation est actuellement cache aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibiliteFormation(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_formation FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_formation = rs.getInt(1);
			if(visi_formation == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_formation = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre nom de formation est maintenant cache aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_formation = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre nom de formation est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite formation");
	
}

public String visibiliteAnneeDiplome(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_anneediplome FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_anneeDiplome = rs.getInt(1);
			if(visi_anneeDiplome == 1)
				return("Votre annee de d'obtention de diplome est actuellement visible");
			else
				return("Votre annee de d'obtention de diplome est actuellement cache aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibiliteAnneeDiplome(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_anneediplome FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_anneeDiplome = rs.getInt(1);
			if(visi_anneeDiplome == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_anneediplome = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre annee d'obtention de votre diplome est maintenant cache aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_anneediplome = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre annee d'obtention de votre diplome est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite telephone");
	
}

public String visibiliteCompetence(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_competence FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	try {
		while(rs.next()){
			int visi_competence = rs.getInt(1);
			if(visi_competence == 1)
				return("Votre competence est actuellement visible");
			else
				return("Votre competence est actuellement cachee aux yeux des autres utilisateurs");
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur visibilite");
	
}

public String modifVisibiliteCompetence(String pNumFiche){
	
	BdAnnuaire.ConnexionBdAnnuaire();
	ResultSet rs = BdAnnuaire.RequeteSelect("SELECT visi_competence FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
	
		try{
		while(rs.next()){
			int visi_nom = rs.getInt(1);
			if(visi_nom == 1){
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_competence = 0 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre competence est maintenant cachee aux yeux des autres utilisateurs");
			}
			else
			{
				BdAnnuaire.RequeteAutre("UPDATE visibilite SET visi_competence = 1 WHERE numero_fiche = '"+pNumFiche+"';");
				return("Votre competence est maintenant visible aux yeux des autres utilisateurs");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return("erreur modif visibilite competence");
	
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
	
	public String getCompetence() {
		return competence;
	}
	
	public void setCompetence(String competence) {
		this.competence = competence;
	}

}
