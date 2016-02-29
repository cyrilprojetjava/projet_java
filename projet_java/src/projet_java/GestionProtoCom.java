package projet_java;

public class GestionProtoCom {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		
		if(requete[0].equalsIgnoreCase("INITMESSAGERIE"))
		{
			if (requete.length==4)
			{
				String numFiche = requete[1];
				String adresseIp = requete[2];
				String numPort = requete[3];
				String reponse = user.initialiserSocketMessagerie(numFiche,adresseIp,numPort);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("USERCONNECT"))
		{
			if (requete.length==2)
			{
				String numFiche = requete[1];
				String reponse = user.rechercheUserInLine(numFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("DECONNECTINLINE"))
		{
			if (requete.length==2)
			{
				String numFiche = requete[1];
				String reponse = user.decoUserInLine(numFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("CONVINSTANT"))
		{
			if (requete.length==2)
			{
				String numConv = requete[1];
				String reponse = user.convInLine(numConv);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("USERNOTCONNECT"))
		{
			if (requete.length==1)
			{
				String reponse = user.rechercheUserOffLine();
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("DEPOTMESSAGE"))
		{
			if (requete.length==4)
			{
				String numMessagerie = requete[1];
				String messageDepose = requete[2];
				String numFicheUserDeposeMessage = requete[3];
				String reponse = user.depotMessagerie(numMessagerie,messageDepose,numFicheUserDeposeMessage);
				return(reponse);
			}
			
		}
		else if(requete[0].equalsIgnoreCase("MESSAGERECU"))
		{
			if (requete.length==2)
			{
				String numFiche = requete[1];
				String reponse = user.LireMessage(numFiche);
				return(reponse);
			}
			
		}
		return("ERREURSRVCom");
	}
	
}