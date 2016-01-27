package projet_java;

import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Connect !");
		System.out.println("Annuaire partag�");
		System.out.println("Tapez 1 pour vous connecter si vous avez d�j� un compte");
		System.out.println("Tapez 2 pour vous inscrire");
		Integer menuIncorrect = 1;
		while (menuIncorrect == 1)
		{
			Integer choixMenu = LireIntClavier();
			switch (choixMenu) {
			case 1:
				menuIncorrect = 0;
				System.out.println("Connexion");
				break;
			case 2:
				menuIncorrect = 0;
				System.out.println("Inscription");
				break;
			default:
				System.out.println("Choix inconnu, veuillez r�essayer.");
				menuIncorrect = 1;
				break;
			}
		}
	}

	private static String LireStringClavier() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		return str;
	}
	
	private static Integer LireIntClavier() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Integer num = sc.nextInt();
		return num;
	}

}
