package projet_java;

import java.util.*;

public class GestionProtoAuth {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			if (requete.length==8 ){
				user.creerCompte(requete[1],requete[2],requete[3],requete[4],requete[5],requete[6]);
				return ("OK CREATION");
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}

	}

}
