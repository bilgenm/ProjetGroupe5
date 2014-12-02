package be.condorcet.projetgroupe5.modele;

import java.sql.Connection;


public class TestJeuDB {

	public static void main(String[] args) {
	    DBConnection dbc =new DBConnection();
	    Connection con = dbc.getConnection();
	    if(con==null) { 
	    System.out.println("connexion impossible2");
	    System.exit(0);
	    }
	    JeuDB.setConnection(con);
	    LieuDB.setConnection(con);
	    JeuDB jeu1=null,jeu2=null;
	    LieuDB lieu=null, lieu2=null;
	

	    
	    try{
	    	System.out.println("---------------------------------------------------");
            System.out.println("test ajout fructeux et lecture correcte");
            lieu=new LieuDB("Cathédrale de abc",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            jeu1=new JeuDB(lieuId,"abc");
            jeu1.create();
            int idJ=jeu1.getIdVille();
            jeu2=new JeuDB(idJ);
            jeu2.read();
            if(jeu2.getDebut()==lieuId && jeu2.getNomVille().equals("abc")){
            	System.out.println("lecture et ajout OK \n jeu2 :" +jeu2);
            }
            else
            	System.err.println("BAD erreur de lecture");
            
        }
        catch(Exception e){
        	  System.err.println("BAD exception d'ajout" + e);
            // e.printStackTrace();
        } 
        try{ 
            jeu1.delete();
            lieu.delete();
        }catch(Exception e){}
        
        
        try{ 
        	System.out.println("---------------------------------------------------");
            System.out.println("test doublon");
            lieu=new LieuDB("Cathédrale de abc",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            jeu1=new JeuDB(lieuId,"abc");
            jeu1.create();
            jeu2=new JeuDB(lieuId,"abc");
            jeu2.create();
            System.err.println("BAD le doublon a été créé ");
        }
        catch(Exception e){
            System.out.println("OK exception normale de doublon "+e);
           //e.printStackTrace();
        } 
        try{ 
            jeu1.delete();
            jeu2.delete();
            lieu.delete();
        }catch(Exception e){}
        
        
        try{ 
        	System.out.println("---------------------------------------------------");
            System.out.println("test mise à jour du lieu de debut d'un jeu");
            lieu=new LieuDB("Cathédrale de abc",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            jeu1=new JeuDB(lieuId,"abc");
            jeu1.create();
            int idJ=jeu1.getIdVille();
            System.out.println("jeu1 avant modification :" +jeu1);
            lieu2=new LieuDB("Monument de abc",36.4566,5.56565);
            lieu2.create();
            int lieuId2=lieu2.getIdLieu();
            jeu1.setDebut(lieuId2);
            jeu1.update();
            jeu2=new JeuDB(idJ);
            jeu2.read();
            if(jeu2.getDebut()==lieuId2){
            	System.out.println("jeu1 après modification :" +jeu2);
            	System.out.println("OK");
            	//jeu1.delete();
            }
            else
            	System.err.println("BAD  Mise à jour pas effectuée /n"+jeu2);
        }
        catch(Exception e){
            System.err.println("BAD exception de mise à jour "+e);
            //e.printStackTrace();
        }
        
        try{ 
            jeu1.delete();
            lieu.delete();
            lieu2.delete();
        }catch(Exception e){}
         
        try{
        	System.out.println("---------------------------------------------------");
            System.out.println("test d'effacement fructueux");
            lieu=new LieuDB("Cathédrale de abc",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            jeu1=new JeuDB(lieuId,"abc");
            jeu1.create();
            int idJ=jeu1.getIdVille();
            jeu1.delete();
            jeu2=new JeuDB(idJ);
            jeu2.read();
            System.err.println("BAD l'effacement ne s'est pas effectué :  /njeu2 ="+jeu2);
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
        	System.out.println("test d'effacement infructueux(jeu inexistant)");
			jeu1 = new JeuDB(10000);
			jeu1.delete();
			System.err.println("BAD effacement effectué");

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement" + e);
		}
        
        
        try{
        	System.out.println("---------------------------------------------------");
            System.out.println("test recherche fructueuse du lieu de debut du jeu pour une ville particulière");
            lieu=new LieuDB("Cathédrale de abc",38.4566,3.56565);
            lieu.create();
            int lieuId=lieu.getIdLieu();
            jeu1 = new JeuDB(lieuId,"abc");
            jeu1.create();
            jeu2 = JeuDB.rechLieuDebut("abc");
            System.out.println("OK lieu de debut trouvé : " + jeu2.getDebut());
        }
        catch(Exception e){
            System.err.println("exception de recherche "+e);
        }
        try{
            jeu1.delete();
            lieu.delete();
            }
         catch(Exception e){}
        
        try{
        	System.out.println("---------------------------------------------------");
            System.out.println("test de recherche infructueuse, (ville inexistante)");
            jeu1=JeuDB.rechLieuDebut("MMM");
            System.err.println("BAD");
        }
        catch(Exception e){
            System.out.println("OK exception normale recherche "+e);
        }
        try {
			con.close();
		} catch (Exception e) {
		}
	}

	
}
