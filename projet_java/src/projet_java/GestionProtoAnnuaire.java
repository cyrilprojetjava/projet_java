package projet_java;

import java.util.*;

public class GestionProtoAnnuaire {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		int validerConnexion;
		int validerCreation;
		
		
		/*
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			if (requete.length==7 ){
				validerCreation = user.creerCompte(user);
				if(validerCreation == 0)
				{	
					return ("CREATIONREFUSEE");
				}else
					return ("CREATIONOK");
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}else if(requete[0].equalsIgnoreCase("CONNECT"))
		{
			if (requete.length==3 ){
				validerConnexion = user.connexion(requete[1],requete[2]);
				if (validerConnexion == 0)
				{
					return("CONNEXIONREFUSEE");
				}
				else
					return ("CONNEXIONOK#"+validerConnexion);
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}*/
		return("ERREURSRV");
	}

}
