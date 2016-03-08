//#####################################################################################
// Class qui permet d'executer les fonctions une fois les messages du client dÃ©codÃ©									 
//#####################################################################################
package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Utilisateur 
{
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

	//#####################################################################################
	// Fonction de creation d'utilisateur										 
	//#####################################################################################
	public int creerCompte(Utilisateur user) 
	{
		System.out.println("Essaie de Creation de compte");
		BdAuth.ConnexionBdAuth();
		ResultSet rs = BdAuth.RequeteSelect("SELECT numero_fiche,email,mdp FROM Authentification WHERE email = '"+user.email+"';");
		try 
		{
			rs.last(); 
			Integer nbItem = rs.getRow(); 
			rs.beforeFirst();
			if(nbItem ==0)
			{
				System.out.println("Creation de compte realise !");
				BdAuth.RequeteAutre("INSERT INTO Authentification(email,mdp) VALUES('"+user.email+"','"+user.mdp+"');");
				rs =  BdAuth.RequeteSelect("SELECT numero_fiche FROM Authentification WHERE email ='"+user.email+"';");
				Integer numFiche = -1;
				while(rs.next())
				{
					numFiche = rs.getInt(1);
				}
				this.numeroFiche = numFiche;
				System.out.println(numFiche);

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


	//#####################################################################################
	// Fonction de connexion de l'utilisateur										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui sert a consulter ses informations										 
	//#####################################################################################
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
	
	//#####################################################################################
	// Fonction qui permet de modifier son nom										 
	//#####################################################################################
	public String modificationInformationNom(String pNumeroFiche, String pNom){

		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET nom = '"+pNom+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification du nom a ete correctement effectuee");
		}
		return ("erreur modif nom");
	}

	//#####################################################################################
	// Fonction qui permet de modifier son prenom										 
	//#####################################################################################
	public String modificationInformationPrenom(String pNumeroFiche, String pPrenom){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET prenom = '"+pPrenom+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification du prenom a ete correctement effectuee");
		}
		return ("erreur modif prenom");
	}

	//#####################################################################################
	// Fonction qui permet de modifier son tel										 
	//#####################################################################################
	public String modificationInformationTel(String pNumeroFiche, String pTel){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET telephone = '"+pTel+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return ("La modification du numero de telephone a ete correctement effectuee");
		}
		return ("erreur modif tel");
	}

	//#####################################################################################
	// Fonction qui permet de modifier sa formation										 
	//#####################################################################################
	public String modificationInformationFormation(String pNumeroFiche, String pFormation){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET formation = '"+pFormation+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre formation a ete correctement effectuee");
		}
		return ("erreur modif formation");
	}

	//#####################################################################################
	// Fonction qui permet de modifier son annee de diplome										 
	//#####################################################################################
	public String modificationInformationAnneeDiplome(String pNumeroFiche, String pAnneeDiplome){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET annnediplome = '"+pAnneeDiplome+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre formation a ete correctement effectuee");
		}
		return ("erreur modif annee diplome");
	}

	//#####################################################################################
	// Fonction qui permet de modifier son mail										 
	//#####################################################################################
	public String modificationInformationMail(String pNumeroFiche, String pMail){
		BdAuth.ConnexionBdAuth();
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET email = '"+pMail+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mail a ete correctement effectuee");
		}
		return ("erreur modif mail");
	}

	//#####################################################################################
	// Fonction qui permet de modifier son mot de passe										 
	//#####################################################################################
	public String modificationInformationMotDePasse(String pNumeroFiche, String pMotDePasse){
		BdAuth.ConnexionBdAuth();
		int rs =  BdAuth.RequeteAutre("UPDATE Authentification SET mdp = '"+pMotDePasse+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre mot de passe a ete correctement effectuee");
		}
		return ("erreur modif mot de passe");
	}

	//#####################################################################################
	// Fonction qui permet de modifier sa compÃ©tence										 
	//#####################################################################################
	public String modificationInformationCompetence(String pNumeroFiche, String pCompetence){
		BdAnnuaire.ConnexionBdAnnuaire();
		int rs =  BdAnnuaire.RequeteAutre("UPDATE Annuaire SET competence = '"+pCompetence+"' WHERE numero_Fiche = '"+pNumeroFiche+"';");
		if(rs == 1)
		{
			return("La modification de votre competence a ete correctement effectuee");
		}
		return ("erreur modif annee competence");
	}

	//#####################################################################################
	// Fonction qui recherche avec le nom										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(i)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
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

	//#####################################################################################
	// Fonction qui recherche avec le prenom										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(i)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
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

	//#####################################################################################
	// Fonction qui recherche avec le mail										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(i)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");

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

	//#####################################################################################
	// Fonction qui recherche avec la formation										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(j)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
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

	//#####################################################################################
	// Fonction qui recherche avec l annee du diplome										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(i)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");

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

	//#####################################################################################
	// Fonction qui recherche avec la competence										 
	//#####################################################################################
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
						message1.add("Numero utilisateur : "+numero_fiche.get(i)+" Nom : "+nom.get(i)+" Prenom : "+prenom.get(i)+" Telephone : "+telephone.get(i)+" Formation : "+formation.get(i)+" Annee Diplome : "+anneediplome.get(i)+" Competence : "+competence.get(i)+"]");
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

	//#####################################################################################
	// Fonction qui recherche avec le nom suivant la visibilite										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite du nom										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui permet de voir la visibilite du prenom										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite du prenom										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui permet de voir la visibilite du telephone									 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite du telephone										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui permet de voir la visibilite de la formation									 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite de la formation										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui permet de voir la visibilite de l annee de formation 									 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite de l anne de diplome										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui permet de voir la visibilite de la competence									 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui modifie la visibilite de la competence										 
	//#####################################################################################
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

	//#####################################################################################
	// Fonction qui supprime un prenom										 
	//#####################################################################################
	public String supprimerCompte(String pNumFiche){

		BdAnnuaire.ConnexionBdAnnuaire();
		BdAuth.ConnexionBdAuth();

		BdAnnuaire.RequeteAutre("DELETE FROM visibilite WHERE numero_fiche = '"+pNumFiche+"';");
		BdAnnuaire.RequeteAutre("DELETE FROM annuaire WHERE numero_fiche = '"+pNumFiche+"';");
		BdAuth.RequeteAutre("DELETE FROM authentification WHERE numero_fiche = '"+pNumFiche+"';");
		return("Votre compte a bien ete supprime");	
	}

	//#####################################################################################
	// Fonction qui indique le port sur lequel le client ecoute										 
	//#####################################################################################
	public String initialiserSocketMessagerie(String pNumFiche,String pAdresseIp,String pNumPort){

		BdAnnuaire.ConnexionBdAnnuaire();

		BdAnnuaire.RequeteAutre("INSERT INTO ip_port_connexion VALUES('"+pNumFiche+"','"+pAdresseIp+"','"+pNumPort+"');");
		return("INITMESSAGERIEOK");	
	}

	//#####################################################################################
	// Fonction qui recherche les utilisateurs qui sont en ligne									 
	//#####################################################################################
	public String rechercheUserInLine(String pNumFiche){

		ArrayList<Object> message1 = new ArrayList<Object> ();
		ArrayList<Object> numero_fiche = new ArrayList<>();
		ArrayList<Object> nom = new ArrayList<>();
		ArrayList<Object> prenom = new ArrayList<>();
		ArrayList<Object> formation = new ArrayList<>();

		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs =  BdAnnuaire.RequeteSelect("SELECT a.numero_fiche, nom, prenom, formation FROM Annuaire a, ip_port_connexion ip WHERE a.numero_fiche = ip.numero_fiche AND ip.numero_Fiche <> "+pNumFiche+";");
		try {
			int i =0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst(); 
			if(nbItem ==0)
			{ return ("Aucun utilisateur connecte.");}
			String message = "";
			while(rs.next()){

				numero_fiche.add(rs.getInt(1));
				nom.add(rs.getString(2));
				prenom.add(rs.getString(3));
				formation.add(rs.getString(4));
				message1.add("Numero de conversation : "+numero_fiche.get(i)+"  Nom : "+nom.get(i)+ "  Prenom : "+prenom.get(i)+"  Formation : "+formation.get(i)+"]");
				//BdAnnuaire.DeconnexionBd();
				message = message + message1.get(i);
				i++;
			}
			return(message);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("Erreur de recherche utilisateurs InLine");
	}

	//#####################################################################################
	// Fonction qui recherche les utilisateurs qui sont pas en ligne									 
	//#####################################################################################
	public String rechercheUserOffLine(){
		ArrayList<Object> message1 = new ArrayList<Object> ();
		ArrayList<Object> numero_fiche = new ArrayList<>();
		ArrayList<Object> nom = new ArrayList<>();
		ArrayList<Object> prenom = new ArrayList<>();
		ArrayList<Object> formation = new ArrayList<>();

		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs =  BdAnnuaire.RequeteSelect("SELECT numero_fiche, nom, prenom, formation FROM Annuaire a WHERE a.numero_fiche NOT IN(SELECT numero_fiche FROM ip_port_connexion);");
		try {
			int i =0;
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst(); 
			if(nbItem ==0)
			{ return ("Aucun utilisateur deconnecte.");}
			String message = "";
			while(rs.next()){

				numero_fiche.add(rs.getInt(1));
				nom.add(rs.getString(2));
				prenom.add(rs.getString(3));
				formation.add(rs.getString(4));
				message1.add("Numero de messagerie : "+numero_fiche.get(i)+"  Nom : "+nom.get(i)+ "  Prenom : "+prenom.get(i)+"  Formation : "+formation.get(i)+"]");
				//BdAnnuaire.DeconnexionBd();
				message = message + message1.get(i);
				i++;
			}
			return(message);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("Erreur de recherche utilisateurs OffLine");

	}

	//#####################################################################################
	// Fonction qui retire les utilisateurs de la base de donnees quand ils ne sont pas connecte									 
	//#####################################################################################
	public String decoUserInLine(String pNumFiche){

		BdAnnuaire.ConnexionBdAnnuaire();

		BdAnnuaire.RequeteAutre("DELETE FROM ip_port_connexion WHERE numero_fiche = "+pNumFiche+";");
		return("Deconnexion OK");	
	}

	//#####################################################################################
	// Fonction qui retourne l adresse ip et le port de la personne a qui on veut parler								 
	//#####################################################################################
	public String convInLine(String pNumConv){

		BdAnnuaire.ConnexionBdAnnuaire();

		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT adresse_Ip, numero_port FROM ip_port_connexion WHERE numero_fiche = "+pNumConv+";");
		try {
			String adresseIp = "NONDISPO";
			int numPort = -1;
			while(rs.next()){
				adresseIp = rs.getString(1);
				numPort = rs.getInt(2);
			}
			String message = "CONVINSTANT#"+adresseIp+"#"+numPort;
			return(message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return("ERREUR");	
	}

	//#####################################################################################
	// Fonction qui sert a faire le depot de la messagerie
	//#####################################################################################
	public String depotMessagerie(String pNumMessagerie, String pMessageDepose, String pNumFicheUserDeposeMessage){

		BdAnnuaire.ConnexionBdAnnuaire();
		BdAnnuaire.RequeteAutre("INSERT INTO messagerie SET num_messagerie = '"+pNumMessagerie+"', message = '"+pMessageDepose+"', numero_fiche_depose_message = '"+pNumFicheUserDeposeMessage+"'" );
		return("Votre message a bien ete depose");

	}

	//#####################################################################################
	// Fonction qui permet de lire les messages rÃ§u en offline								 
	//#####################################################################################
	public String LireMessage(String pNumFiche){

		ArrayList<Object> message1 = new ArrayList<Object> ();
		ArrayList<Object> messageDepose = new ArrayList<>();
		ArrayList<Object> nom = new ArrayList<>();
		ArrayList<Object> prenom = new ArrayList<>();


		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT message, a.nom, a.prenom FROM messagerie m, annuaire a WHERE num_messagerie = '"+pNumFiche+"' AND m.numero_fiche_depose_message = a.numero_fiche");

		try {
			int i =0;
			String message = "";
			while(rs.next()){

				messageDepose.add(rs.getString(1));
				nom.add(rs.getString(2));
				prenom.add(rs.getString(3));
				message1.add("Message depose par : "+nom.get(i)+" "+prenom.get(i)+"   Contenu du message : "+messageDepose.get(i)+"]");
				//BdAnnuaire.DeconnexionBd();
				message = message + message1.get(i);
				i++;
			}
			return(message);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return("");

	}

	//#####################################################################################
	// Fonction qui permet de savoir qui envoie un message en instatane								 
	//#####################################################################################
	public String quiSuisJe(String pNumFiche){

		ArrayList<Object> message1 = new ArrayList<Object> ();
		ArrayList<Object> nom = new ArrayList<>();
		ArrayList<Object> prenom = new ArrayList<>();

		BdAnnuaire.ConnexionBdAnnuaire();
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT nom, prenom FROM annuaire WHERE numero_fiche = '"+pNumFiche+"'" );

		try {
			int i = 0;
			String message = "";
			while (rs.next()){
				nom.add(rs.getString(1));
				prenom.add(rs.getString(2));
				message1.add(" - Message envoye par "+nom.get(i)+" "+prenom.get(i)+"]");
				message = message + message1.get(i);
				i++;
			}
			return(message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("");

	}
	
	//#####################################################################################
	// Fonction qui permet de voir les likes d'une competence d'un utilisateur								 
	//#####################################################################################
	public String seeLike(String pNumFiche){
		BdAnnuaire.ConnexionBdAnnuaire();
		
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT * FROM like_competence WHERE num_pers_like = "+pNumFiche+";");
		try {
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
			//On cherche à savoir si l'utilisateur a déjà eu une compétence likée
			if(nbItem ==0)
			{
				return ("Personne n'a deja mis un like sur la competence de cet utilisateur.");
			}
			else
			{
					ResultSet rs1 = BdAnnuaire.RequeteSelect("SELECT COUNT(*) FROM like_competence WHERE num_pers_like="+pNumFiche+" AND competence = TRUE;");
					Integer nbLike = 0;
					//On compte le nombre de like
					while(rs1.next())
					{
						nbLike = rs1.getInt(1);
					}
					Utilisateur user1 = new Utilisateur();
					rs1 =  BdAnnuaire.RequeteSelect("SELECT nom, prenom, formation, competence FROM Annuaire a WHERE a.numero_fiche = "+pNumFiche+";");
					while(rs1.next())
					{
						user1.setNom(rs1.getString(1));
						user1.setPrenom(rs1.getString(2));
						user1.setFormation(rs1.getString(3));
						user1.setCompetence(rs1.getString(4));
					}
					ArrayList<Object> message1 = new ArrayList<Object> ();
					ArrayList<Object> numero_fiche = new ArrayList<>();
					ArrayList<Object> nom = new ArrayList<>();
					ArrayList<Object> prenom = new ArrayList<>();
					ArrayList<Object> formation = new ArrayList<>();
					String message = "L'utilisateur "+user1.getNom()+" "+user1.getPrenom()+" de la formation "+user1.getFormation()+" a "+nbLike+" like pour sa competence "+user1.getCompetence()+".]]";
					Integer i = 0;
					rs1 =  BdAnnuaire.RequeteSelect("SELECT num_likeur, nom, prenom, formation FROM Annuaire a, like_competence l WHERE a.numero_fiche = l.num_likeur AND l.competence = TRUE AND l.num_pers_like = "+pNumFiche+";");
					while(rs1.next())
					{
						numero_fiche.add(rs1.getInt(1));
						nom.add(rs1.getString(2));
						prenom.add(rs1.getString(3));
						formation.add(rs1.getString(4));
						message1.add(nom.get(i)+ " "+prenom.get(i)+" de la formation "+formation.get(i)+" a mis un like.]");
						message = message + message1.get(i);
						i++;
					}
					System.out.println(message);
					return(message);
			}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return("Erreur de communication avec le serveur de base de donnees");
	}
	
	//#####################################################################################
	// Fonction qui permet de liker une competence d'un utilisateur							 
	//#####################################################################################
	public String like(String pNumFicheLikeur,String pNumPersLike){
		BdAnnuaire.ConnexionBdAnnuaire();
		
		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT id_like FROM like_competence WHERE num_likeur = "+pNumFicheLikeur+" AND num_pers_like ="+pNumPersLike+";");
		try {
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
			//On cherche à savoir si l'utilisateur a déjà liké TRUE OU FALSE
			if(nbItem ==0)
			{
				BdAnnuaire.RequeteAutre("INSERT INTO like_competence(num_likeur,num_pers_like,competence) VALUES("+pNumFicheLikeur+","+pNumPersLike+",TRUE);");
				return ("LIKEOK#"+pNumFicheLikeur);
			}
			else
			{
				//On vérifie que le like est FALSE
				while(rs.next())
				{
					String id_like = rs.getString(1);
					ResultSet rs1 = BdAnnuaire.RequeteSelect("SELECT id_like FROM like_competence WHERE id_like="+id_like+" AND competence = TRUE;");
					rs1.last();
					nbItem = rs1.getRow();
					rs1.beforeFirst();
					if(nbItem ==0)
					{
						BdAnnuaire.RequeteAutre("UPDATE like_competence SET competence = TRUE WHERE id_like="+id_like);
						return ("LIKEOK#"+pNumFicheLikeur);
					}
					else
						return("Vous avez deja mis un like a cet utilisateur.");
				}
			}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return("Erreur de communication avec le serveur de base de donnees");
	}
		
	//#####################################################################################
	// Fonction qui permet de ne plus liker une competence d'un utilisateur								 
	//#####################################################################################
	public String dontLike(String pNumFicheLikeur,String pNumPersLike){
		BdAnnuaire.ConnexionBdAnnuaire();

		ResultSet rs = BdAnnuaire.RequeteSelect("SELECT id_like FROM like_competence WHERE num_likeur = "+pNumFicheLikeur+" AND num_pers_like ="+pNumPersLike+";");
		try {
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
			if(nbItem ==0)
			{
				return("Vous n'avez pas mis de like a cet utilisateur precedemment.");
			}
			else
			{
				//On vérifie que le like est FALSE
				while(rs.next())
				{
					String id_like = rs.getString(1);
					ResultSet rs1 = BdAnnuaire.RequeteSelect("SELECT id_like FROM like_competence WHERE id_like="+id_like+" AND competence = TRUE;");
					rs1.last();
					nbItem = rs1.getRow();
					rs1.beforeFirst();
					if(nbItem ==0)
					{
						return("Vous n'avez pas mis de like a cet utilisateur precedemment.");
					}
					else
					{
						BdAnnuaire.RequeteAutre("UPDATE like_competence SET competence = FALSE WHERE id_like="+id_like);
						return ("DONTLIKEOK#"+pNumFicheLikeur);
					}
				}
			}
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return("Erreur de communication avec le serveur de base de donnees");
		
		/*ResultSet rs = BdAnnuaire.RequeteSelect("SELECT id_like FROM like_competence WHERE num_likeur = '"+pNumFicheLikeur+"' AND num_pers_like = '"+pNumPersLike+"' AND competence = TRUE;");
		try {
			rs.last();
			Integer nbItem = rs.getRow();
			rs.beforeFirst();
			//On vérifie que l'utilisateur a déjà liké
			if(nbItem ==0)
			{
				return("Vous n'avez pas mis de like a cet utilisateur precedemment.");
			}
			else
			{
				while(rs.next())
				{
					String id_like = rs.getString(1);
					BdAnnuaire.RequeteAutre("UPDATE like_competence SET competence = FALSE WHERE id_like="+id_like+";");
				}
				return ("DONTLIKEOK#"+pNumFicheLikeur);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("Erreur de communication avec le serveur de base de donnees");*/
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
