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
				//bdAnnu.consulterBdAnnuaire(requete[1]);
				return("CONSULTINFOPERSO");
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
				user.modificationInformationNom(numFiche, nom);
				return("MODIFINFOPERSONOMOK");
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
				user.modificationInformationPrenom(numFiche, prenom);
				return("MODIFINFOPERSOPRENOMOK");
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
				user.modificationInformationTel(numFiche, tel);
				return("MODIFINFOTELOK");
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
				user.modificationInformationFormation(numFiche, formation);
				return("MODIFINFOFORMATIONOK");
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
				user.modificationInformationAnneeDiplome(numFiche, anneeDiplome);
				return("MODIFINFOANDIPLOMEOK");
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		return("ERREURSRVAnnuaire");
	}

}
