package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private Marche marche;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.marche = new Marche(nbEtal);
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}
	
	
	private static class Marche{
		
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal e = new Etal();
			this.etals[indiceEtal] = e;
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] == null)
					return  i;
			}
			return 0;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsLibres = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsLibres++;
				}
			}
			Etal[] etalsLibres = new Etal[nbEtalsLibres];
			int j = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsLibres[j++] = etals[i];
				}
			}
			return etalsLibres;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					return etals[i];
				}
			}
			return null;
		}

		
		private String afficherMarche() {
			String phrase = "";
			int nbEtalLibre = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && etals[i].isEtalOccupe()) {
					phrase += etals[i].afficherEtal() + "\n";
					nbEtalLibre++;
				}
			}
			if (nbEtalLibre != 0) {
				phrase += "Il reste " + nbEtalLibre + " étals non utilisés dans le marché.\n";
			}
			return phrase;
		}
		
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		try {
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		} catch (VillageSansChefException e) {
			throw new VillageSansChefException("village sans chef");
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int indiceLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceLibre, vendeur, produit, nbProduit);
		//marche.utiliserEtal(nbProduit, vendeur, produit, nbProduit);
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "+ nbProduit + " " + produit +".\n");
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal n°" + indiceLibre +".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		Etal[] bonEtals = this.marche.trouverEtals(produit);
		for (Etal etal : bonEtals)
			chaine.append(" - " + etal.getVendeur().getNom() + "\n");
		return chaine.toString();
	}
	

	public Etal rechercherEtal(Gaulois vendeur) {
		for (Etal etal : marche.etals) {
			if (etal.getVendeur().getNom().equals(vendeur.getNom()))
				return etal;
		}
		return null;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalAfuir = rechercherEtal(vendeur);
		return etalAfuir.libererEtal();
	}
	public String afficherMarche() {
		return marche.afficherMarche();
	}
}