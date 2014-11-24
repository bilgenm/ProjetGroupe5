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
		LieuDB lieu1 = null, lieu2 = null;
		try {
			System.out.println("Test ajout fructueux de lieu");
			System.out.println("**************************************************************************");
			lieu1 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
							+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
							+ "Mons...",50.453263,3.947689);
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
			lieu1 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
							+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
							+ "Mons...",50.453263,3.947689);
			lieu1.create();
			lieu2 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses.",50.453263,3.947689);
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
			lieu1 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
					+ "Mons...",50.453263,3.947689);
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
			lieu1 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
					+ "Mons...",50.453263,3.947689);
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
			lieu1 = new LieuDB("La collégiale Sainte-Waudru","La collégiale a été bâtie au XVe siècle "
					+ "sur ordre des chanoinesses. Elle constitue un symbole majeur de la ville de"
					+ "Mons...",50.453263,3.947689);
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

			System.out.println("Test de recherche fructueuse d'un lieu et ses successeurs");
			System.out.println("**************************************************************************");
			ArrayList<LieuDB> listeLieux = LieuDB.rechLieu(135);
			for (LieuDB lieu : listeLieux) {
				System.out.println(lieu);
			}
			System.out.println("OK recherche fonctionnelle :)");

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

