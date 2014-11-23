package be.condorcet.projetgroupe5.modele;

import java.sql.Connection;

public class TestJoueur {
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.out.println("connexion impossible");
			System.exit(0);
		}
		JoueurDB.setConnection(con);
		JoueurDB j1 = null, j2 = null;
		try {
			System.out.println("Test ajout fructueux d'un joueur");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			int numJoueur = j1.getIdJoueur();
			j2 = new JoueurDB(numJoueur);
			j2.read();
			if (j2.getNom().equals("Mustafa")
					&& j2.getPrenom().equals("Bilgen")){
				System.out.println("OK le joueur a �t� bien ajout�!!!");
				System.out.println(j1);
				
			}	
			else {
				System.err.println("BAD erreur de lecture nom=" + j2.getNom()
						+ " prenom=" + j2.getPrenom());	
			}
				
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout de joueur " + e);
			// e.printStackTrace();
		}
		try {
			j1.delete();
		} catch (Exception e) {
			System.err.println("BAD test" + e);
		}
		try {
			System.out.println("Test doublon");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			j2 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "bbb");
			j2.create();
			System.err.println("BAD doublon accept�");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon " + e);
			// e.printStackTrace();
		}
		try {
			j1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test mise � jour(pseudo et password)");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			int numJoueur = j1.getIdJoueur();
			System.out.println("===Avant mise � jour===");
			System.out.println(j1);
			j1.setPseudo("bilgen33");
			j1.setPassword("bbb");
			j1.update();
			j2 = new JoueurDB(numJoueur);
			j2.read();
			System.out.println("===Apr�s mise � jour===");
			System.out.println("j2=" + j2);
			if (j2.getPseudo().equals("bilgen33")
					&& j2.getPassword().equals("bbb")) {
				System.out.println("OK le pseudo et password du joueur ont �t� mis � jour");	
			}	
			else {
				System.err.println("BAD pseudo non mis � jour pseudo "
						+ j2.getPseudo() + " password " + j2.getPassword());	
			}
				
		} catch (Exception e) {
			System.err.println("BAD exception de mise � jour de pseudo et password " + e);
			// e.printStackTrace();
		}
		try {
			j1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test mise � jour (lieuRech)");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			int numJoueur = j1.getIdJoueur();
			j2 = new JoueurDB(numJoueur);
			System.out.println("===Avant mise � jour===");
			System.out.println(j1);
			j1.setLieuRech(28);
			j1.updateLieuRech();
			j2.read();
			System.out.println("===Apr�s mise � jour===");
			System.out.println("j2=" + j2);
			if (j2.getLieuRech() == 28) {
				System.out.println("OK le lieu recherch� a �t� bien mis � jour");	
			}	
			else {
				System.err.println("BAD lieu recherch� non mis � jour "
						+ j2.getLieuRech());	
			}
				
		} catch (Exception e) {
			System.err.println("BAD exception de mise � jour de lieu recherch� " + e);
			// e.printStackTrace();
		}
		try {
			j1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test d'effacement fructueux");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			int numJoueur = j1.getIdJoueur();
			j1.delete();
			j2 = new JoueurDB(numJoueur);
			j2.read();
			System.err.println("BAD effacement non effectu� pour identifiant ="
					+ j2.getIdJoueur());

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du joueur " + e);

			// e.printStackTrace();
		}
		try {
			System.out
					.println("Test d'effacement infructueux(joueur inexistant)");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB(10);
			j1.delete();
			System.err.println("BAD effacement effectu�");

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement du joueur " + e);
		}
		try {
			System.out.println("Test de recherche fructueuse");
			System.out.println("**************************************************************************");
			j1 = new JoueurDB("Mustafa", "Bilgen", "bilgen55", "aaa");
			j1.create();
			j2 = JoueurDB.rechPseudo("bilgen55");
			System.out.println("OK le joueur qui a le pseudo bilgen55 trouv�!!!");
			System.out.println(j2);		
			
		} catch (Exception e) {
			System.err.println("Exception de recherche de pseudo du joueur" + e);
		}
		try {
			j1.delete();
		} catch (Exception e) {
		}
		try {
			System.out.println("Test de recherche infructueuse");
			System.out.println("**************************************************************************");
			j1 = JoueurDB.rechPseudo("bbbbbb");
			// System.out.println(j1);
			System.err.println("BAD");
		} catch (Exception e) {
			System.out.println("OK exception normale recherche de pseudo " + e);
		}
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}


