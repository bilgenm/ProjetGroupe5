package be.condorcet.projetgroupe5.modele;

import java.sql.Connection;
import java.util.ArrayList;


public class TestLieu {
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.out.println("connexion impossible");
			System.exit(0);
		}
		LieuDB.setConnection(con);
		JeuDB.setConnection(con);
		JoueurDB.setConnection(con);
		EnigmeDB.setConnection(con);
		LieuDB lieu1 = null, lieu2 = null,lieu3=null, lieu4=null;
		JeuDB jeu=null;
		JoueurDB j1=null,j2=null;
		EnigmeDB enigme=null;
		try {
			System.out.println("Test ajout fructueux de lieu");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
			lieu1.create();
			int numLieu = lieu1.getIdLieu();
			lieu2 = new LieuDB(numLieu);
			lieu2.read();
			if (lieu2.getNomLieu().equals("La collégiale Sainte-Waudru")
					&& lieu2.getLatLieu() == 50.453263
					&& lieu2.getLongLieu() == 3.947689){
				System.out.println("OK le lieu a été bien ajouté!!!");
				System.out.println(lieu2);
			}
				
			else
				System.err.println("BAD erreur de lecture nom ="
						+ lieu2.getNomLieu() + " latitude="
						+ lieu2.getLatLieu() + " longitude="
						+ lieu2.getLongLieu());
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout " + e);
			// e.printStackTrace();
		}
		try {
			lieu1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test doublon de lieu");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
			lieu1.create();
			lieu2 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu2.create();
	        System.err.println("BAD doublon de lieu accepté");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon de lieu " + e);
			// e.printStackTrace();
		}
		try {
			lieu1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test mise à jour fructueuse (successeur)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        System.out.println("===Avant mise à jour===");
			System.out.println(lieu1);
	        lieu1.setSuccesseur(28);
	        lieu1.update();
			lieu2 = new LieuDB(numLieu);
			lieu2.read();
			System.out.println("===Après mise à jour===");
			System.out.println(lieu2);
			if (lieu2.getSuccesseur() == 28){
				System.out.println("OK le successeur du lieu a été bien mis à jour!");
				
			}	
			else {
				System.err.println("BAD successeur non mis à jour "
						+ lieu2.getSuccesseur());
			}
				
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour de successeur de lieu" + e);
			// e.printStackTrace();
		}
		try {
			lieu1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test mise à jour infructueuse (successeur)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        lieu1.setSuccesseur(114);
	        lieu1.update();
			lieu2 = new LieuDB(numLieu);
			lieu2.read();
			if (lieu2.getSuccesseur() == 114){
				System.err.println("BAD successeur a été mis à jour "
						+ lieu2.getSuccesseur());
			}	
				
		} catch (Exception e) {
			System.out.println("OK exception normale de mise à jour de successeur de lieu" + e);
			// e.printStackTrace();
		}
		try {
			lieu1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test d'effacement fructueux");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
			lieu1.delete();
			lieu2 = new LieuDB(numLieu);
			lieu2.read();
			System.err.println("BAD effacement non effectué pour identifiant du lieu ="
					+ lieu2.getIdLieu());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du lieu " + e);

			// e.printStackTrace();
		}
		try {
			System.out.println("Test d'effacement infructueux(lieu inexistant)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB(10);
			lieu1.delete();
			System.err.println("BAD effacement de lieu effectué");

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du lieu " + e);
		}
		try {
			System.out.println("Test d'effacement fructueux(lieu n'a pas de successeur et il est début d'un jeu)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        jeu=new JeuDB(numLieu,"MONS");
	        jeu.create();
			lieu1.delete();
			lieu2 = new LieuDB(numLieu);
			lieu2.read();
			System.err.println("BAD effacement non effectué pour identifiant du lieu ="
					+ lieu2.getIdLieu());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du lieu " + e);
			// e.printStackTrace();
		}
		try {
			System.out.println("Test d'effacement infructueux(lieu est recherché par un jouer)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        jeu=new JeuDB(numLieu,"MONS");
	        jeu.create();
	        j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			int numJoueur = j1.getIdJoueur();
			j1.setLieuRech(numLieu);
			j1.updateLieuRech();
			j2 = new JoueurDB(numJoueur);
			j2.read();
			lieu1.delete();
			//lieu2 = new LieuDB(numLieu);
			//lieu2.read();
			System.err.println("BAD effacement non effectué pour identifiant du lieu ="
					+ lieu1.getIdLieu());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du lieu qui est recherché par un ou plusieurs joueurs" + e);
			// e.printStackTrace();
		}
		try {
			jeu.delete();
			j1.delete();
			lieu1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test d'effacement fructueux(lieu a successeur,est début d'un jeu ");
			System.out.println("et il y a des engimes pour ce lieu)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        lieu2 = new LieuDB("Successeur de collégiale Sainte-Waudru",50.453273,3.947489);
	        lieu2.create();
	        int numLieu2 = lieu2.getIdLieu();
	        lieu1.setSuccesseur(numLieu2);
	        lieu1.update();
	        lieu3 = new LieuDB("Successeur de Successeur de collégiale Sainte-Waudru",50.453273,3.947489);
	        lieu3.create();
	        int numLieu3 = lieu3.getIdLieu();
	        lieu2.setSuccesseur(numLieu3);
	        lieu2.update();
	        jeu=new JeuDB(numLieu,"MONS");
	        jeu.create();
	        enigme = new EnigmeDB(numLieu, "fr","le pont que vous ...","Ce battiment a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
					+ "Mons...");	
			enigme.create();
			lieu1.delete();
			lieu4 = new LieuDB(numLieu);
			lieu4.read();
			System.err.println("BAD effacement non effectué pour identifiant du lieu ="
					+ lieu1.getIdLieu());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement de lieu a été bien supprimé" + e);

			// e.printStackTrace();
		}
		try {
			jeu.delete();
			lieu3.delete();
			lieu2.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test d'effacement fructueux(lieu a successeur,mais il n'est pas ");
			System.out.println("début d'un jeu et il y a des engimes pour ce lieu)");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru",50.453263,3.947689);
	        lieu1.create();
	        int numLieu = lieu1.getIdLieu();
	        lieu2 = new LieuDB("Successeur de collégiale Sainte-Waudru",50.453273,3.947489);
	        lieu2.create();
	        int numLieu2 = lieu2.getIdLieu();
	        lieu1.setSuccesseur(numLieu2);
	        lieu1.update();
	        lieu3 = new LieuDB("Successeur de Successeur de collégiale Sainte-Waudru",50.453273,3.947489);
	        lieu3.create();
	        int numLieu3 = lieu3.getIdLieu();
	        lieu2.setSuccesseur(numLieu3);
	        lieu2.update();
	        jeu=new JeuDB(numLieu,"MONS");
	        jeu.create();
	        enigme = new EnigmeDB(numLieu2, "fr","le pont que vous ...","Ce battiment a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
					+ "Mons...");	
			enigme.create();
			lieu2.delete();
			lieu4 = new LieuDB(numLieu2);
			lieu4.read();
			System.err.println("BAD effacement non effectué pour identifiant du lieu ="
					+ lieu2.getIdLieu());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement de lieu " + e);
			// e.printStackTrace();
		}
		try {
			jeu.delete();
			lieu3.delete();
			lieu1.delete();
		} catch (Exception e) {
		}
	    try {
			System.out.println("Test de recherche fructueuse d'un lieu et ses successeurs");
			System.out.println("**************************************************************************");
			ArrayList<LieuDB> listeLieux = LieuDB.rechLieu(135);
			for (LieuDB lieu : listeLieux) {
				System.out.println(lieu);
			}
			System.out.println("OK recherche fonctionnelle ");

		} catch (Exception e) {
			System.err.println("exception de recherche " + e);
		}
		try {

			System.out.println("Test de recherche infructueuse d'un lieu et ses successeurs");
			System.out.println("**************************************************************************");
			ArrayList<LieuDB> listeLieux = LieuDB.rechLieu(10);
			for (LieuDB lieu : listeLieux) {
				System.out.println(lieu);
			}
			System.err.println("BAD recherche");

		} catch (Exception e) {
			System.out.println("OK exception normale de recherche de successeur de lieu" + e);
		}
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}

