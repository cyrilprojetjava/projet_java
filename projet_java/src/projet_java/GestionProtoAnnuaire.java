package projet_java;

import java.util.*;

public class GestionProtoAnnuaire {

	private Utilisateur user = new Utilisateur();
	private Bd bdAnnu = new Bd();

	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			
			if (requete.length==7 )
			{
				return ("CREATIONOK");

			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
	
		else if(requete[0].equalsIgnoreCase("CONSULTINFOPERSO"))
		{
			if (requete.length==2 ) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String reponse = user.consulterInfoPerso(numFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSONOM"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String nom = requete[2];
				String reponse = user.modificationInformationNom(numFiche, nom);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOPRENOM"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String prenom = requete[2];
				String reponse = user.modificationInformationPrenom(numFiche, prenom);
				return("reponse");
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOTEL"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String tel = requete[2];
				String rep = user.modificationInformationTel(numFiche, tel);
				return(rep);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOFORMATION"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String formation = requete[2];
				String reponse = user.modificationInformationFormation(numFiche, formation);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOANDIPLOME"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String anneeDiplome = requete[2];
				String reponse = user.modificationInformationAnneeDiplome(numFiche, anneeDiplome);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOMAIL"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String mail = requete[2];
				String reponse = user.modificationInformationMail(numFiche, mail);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOMDP"))
		{
			if (requete.length==3) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String numFiche = requete[1];
				String mdp = requete[2];
				String reponse = user.modificationInformationMotDePasse(numFiche, mdp);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("RECHERCHENOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String nom = requete[1];
				String reponse = user.rechercheNom(nom);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("RECHERCHEPRENOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String prenom = requete[1];
				String reponse = user.recherchePrenom(prenom);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("RECHERCHEMAIL"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String mail = requete[1];
				String reponse = user.rechercheMail(mail);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("RECHERCHEFORMATION"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String formation = requete[1];
				String reponse = user.rechercheFormation(formation);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("RECHERCHEANDIPLOME"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String anneeDiplome = requete[1];
				String reponse = user.rechercheAnneeDiplome(anneeDiplome);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("VISIBILITENOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.visibiliteNom(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFVISINOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteNom(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("VISIBILITEPRENOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.visibilitePrenom(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFVISIPRENOM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.modifVisibilitePrenom(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("VISIBILITETEL"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.visibiliteTel(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFVISITEL"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteTel(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("VISIBILITEFORM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.visibiliteFormation(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFVISIFORM"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteFormation(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		else if(requete[0].equalsIgnoreCase("VISIBILITEANDIPLOME"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.visibiliteAnneeDiplome(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MODIFVISIANDIPLOME"))
		{
			if (requete.length==2) /* ne pas oublier de modif suivant le nb de parametre */
			{
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteAnneeDiplome(NumFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		return("ERREURSRVAnnuaire");
	}
	
	

}
