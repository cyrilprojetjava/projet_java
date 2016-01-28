package projet_java;

import java.util.*;

public class GestionProtoAuth {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		int validerConnexion;
		int validerCreation;
		
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			if (requete.length==8 ){
				validerCreation = user.creerCompte(requete[1],requete[2],requete[3],requete[4],requete[5],requete[6],requete[7]);
				if(validerCreation == 0)
				{	
					return ("CREATIONREFUSEE");
				}
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
				return ("CONNEXIONOK#"+validerConnexion);
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}
		return("toto");
	}

}
