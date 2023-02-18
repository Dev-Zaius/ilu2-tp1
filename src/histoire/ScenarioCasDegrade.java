package histoire;

import personnages.*;
import villagegaulois.*;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Village village = new Village("le village des irreductibles", 10, 5);
		Gaulois abraracourcix = new Gaulois("Abraracourcix", 20);
		Gaulois obelix = new Gaulois("Obelix", 20);
		
		village.ajouterHabitant(abraracourcix);
		village.ajouterHabitant(obelix);
		village.installerVendeur(obelix, "menhirs", 2);
		Etal etal = village.rechercherEtal(obelix);
		
		try	{
			System.out.println(etal.acheterProduit(-2, abraracourcix));
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
		try	{
			etal.libererEtal();
			System.out.println(etal.acheterProduit(2, abraracourcix));
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		System.out.println("fin");
		
	}

}