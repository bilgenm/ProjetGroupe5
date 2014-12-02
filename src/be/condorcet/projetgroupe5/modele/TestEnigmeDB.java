package be.condorcet.projetgroupe5.modele;

import java.sql.Connection;




public class TestEnigmeDB {

	public static void main(String[] args) {
	    DBConnection dbc =new DBConnection();
	    Connection con = dbc.getConnection();
	    if(con==null) { 
	    System.out.println("connexion impossible");
	    System.exit(0);
	    }
	    EnigmeDB.setConnection(con);
	    LieuDB.setConnection(con);
	    EnigmeDB en1=null,en2=null;
	    LieuDB lieu=null;
	    
	    
	    try {
	    	System.out.println("---------------------------------------------------");
	    	System.out.println("Test ajout et lecture correcte");
	    	lieu=new LieuDB("Pont de xyz", 38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
	    	en1 = new EnigmeDB(lieuId, "fr","trouvez le pont...","Le pont bla bla bla...");
			en1.create();
			int idEn = en1.getIdEnigme();
			en2 = new EnigmeDB(idEn);
			en2.read();
			System.out.println("OK enigme créée  en2 : "+en2);
	        
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout" + e);
			// e.printStackTrace();
		}
		try {
			en1.delete();
			lieu.delete();
		} catch (Exception e) {
			System.err.println("BAD test" + e);
		}
	    
		try{ 
        	System.out.println("---------------------------------------------------");
            System.out.println("test mise à jour du texte de l'enigme");
            lieu=new LieuDB("Pont de xyz",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            en1 = new EnigmeDB(lieuId, "fr","le pont que vous ...","Le pont bla bla bla...");
			en1.create();
			int idEn = en1.getIdEnigme();
			System.out.println("enigme avant modification :" +en1);
			en1.setTexte("Trouvez l'endroit... ");
			en1.update();
			en2 = new EnigmeDB(idEn);
			en2.read();
			System.out.println("OK enigme après modification :" +en2);
            	//jeu1.delete();
        }
        catch(Exception e){
            System.err.println("BAD exception de mise à jour "+e);
            //e.printStackTrace();
        }
        
        try{ 
            en1.delete();
            lieu.delete();
            
        }catch(Exception e){}
        
        
        try{
        	System.out.println("---------------------------------------------------");
            System.out.println("test d'effacement fructueux");
            lieu=new LieuDB("Pont de xyz",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            en1 = new EnigmeDB(lieuId, "fr","le pont que vous ...","Le pont bla bla bla...");
			en1.create();
			int idEn = en1.getIdEnigme();
            en1.delete();
            en2=new EnigmeDB(idEn);
            en2.read();
            System.err.println("BAD l'effacement ne s'est pas effectué :  /n en2 ="+en2);
          }
         catch(Exception e){
             System.out.println("OK exception normale d'effacement"+e);
            
            // e.printStackTrace();
         }
         try{ 
            //jeu1.delete();
            lieu.delete();
         }catch(Exception e){}
         
         try {
         	System.out.println("---------------------------------------------------");
         	System.out.println("test d'effacement infructueux(enigme inexistant)");
 			en1 = new EnigmeDB(10000);
 			en1.delete();
 			System.err.println("BAD effacement effectué");

 		} catch (Exception e) {
 			System.out.println("OK exception normale d'effacement" + e);
 		}
         
         try{
         	System.out.println("---------------------------------------------------");
             System.out.println("test recherche fructueuse d'un enigme pour un lieu particulier");
             lieu=new LieuDB("Pont de xyz",38.4566,3.56565);
             lieu.create();
             int lieuId=lieu.getIdLieu();
             en1 = new EnigmeDB(lieuId, "fr","le pont que vous ...","Le pont bla bla bla...");
 			 en1.create();
             en2 = EnigmeDB.rechEnigmeLieu(lieuId,"fr");
             System.out.println("OK enigme trouvé : " + en2);
         }
         catch(Exception e){
             System.err.println("exception de recherche "+e);
         }
         try{
             en1.delete();
             lieu.delete();
             }
          catch(Exception e){}
          
         
         try {
        	System.out.println("---------------------------------------------------");
 			System.out.println("Test de recherche infructueuse d'une enigme pour un lieu sans enigme");
 			lieu=new LieuDB("Pont de xyz",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            en1=EnigmeDB.rechEnigmeLieu(lieuId,"fr");
 			System.err.println("BAD recherche");

 		} catch (Exception e) {
 			System.out.println("OK exception normale de recherche lieu sans enigme" + e);
 		}
         
         try{
             lieu.delete();
             }
          catch(Exception e){}
 		try {
 			con.close();
 		} catch (Exception e) {
 		}
	    
	}
}
