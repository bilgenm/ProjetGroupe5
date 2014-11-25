package be.condorcet.projetgroupe5.modele;
/**
 * classe de mappage poo-relationnel Enigme
 * @author Paz Rojas Laura
 * @version 1.0
 * @see Enigme
 */
import java.sql.*;

import android.util.Log;

public class EnigmeDB extends Enigme implements CRUD {
	/**
	 * connexion à la base de données partagée entre toutes les instances(statique)
	 */
    protected static Connection dbConnect=null;
        
    /**
     * constructeur par défaut
     */
	public EnigmeDB(){
   	}

	/**
	 * constructeur paramétré à utiliser avant lors de la création, l'idEnigme
	 * ne doit pas être précisé,il sera affecté par la base de données lors de la création
	 * @see #create()
	 * @param lieuEnig lieu auquel l'enigme correspond
	 * @param langue langue de l'enigme
	 * @param texte texte de l'enigme
	 */
	public EnigmeDB(int lieuEnig, String langue, String texte){	
      super(0,lieuEnig,langue,texte);
   }
   
   	/**
   	 * constructeur paramétré à utiliser avant lorsque l'enigme est déjà présente
   	 * dans la base de données
   	 * @param idEnigme identifiant de l'enigme
   	 * @param lieuEnig lieu auquel l'enigme correspond
   	 * @param langue langue de l'enigme
   	 * @param texte texte de l'enigme
   	 */
   	public EnigmeDB(int idEnigme, int lieuEnig, String langue, String texte){	
       super(idEnigme, lieuEnig, langue, texte);	
   	}   
   
   	/**
   	 * constructeur à utiliser lorsque l'enigme est déjà présente en base de données
   	 * mais qu'on ne connaît que son identifiant, à utiliser avec read
   	 * @see #read()
   	 * @param idEnigme
   	 */
   	public EnigmeDB(int idEnigme){
       super(idEnigme,0 , "", "");
    }

   	/**
   	 * méthode statique permettant de partager la connexion entre toutes les instances de
   	 * EnigmeDB
   	 * @param nouvdbConnect connexion à la base de données
   	 */
    public static void setConnection(Connection nouvdbConnect) {
       dbConnect=nouvdbConnect;
    }
	
    /**
     * enregistrement d'une nouvelle enigme dans la base de données
     * @throws Exception erreur de création
     */
    public void create() throws Exception{
    	CallableStatement cstmt=null;
        try{
        	String req = "call CREATEENIGME(?,?,?,?)";
        	cstmt = dbConnect.prepareCall(req);
        	cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
        	cstmt.setInt(2,lieuEnig);
        	cstmt.setString(3,langue);
        	cstmt.setString(4,texte);
        	cstmt.executeUpdate();
        	this.idEnigme=cstmt.getInt(1);
  	   	 }
         catch(Exception e ){
        	 throw new Exception("Erreur de création "+e.getMessage());
         }
         finally{ 
        	 try{
        		 cstmt.close();
             }
             catch (Exception e){}
         }
     }
	
    /**
     * récupération des données d'une enigme sur base de son identifiant
     * @throws Exception code inconnu
     */
    public void read ()throws Exception{
    	String req = "select * from enigme where id_enigme =?"; 
     	PreparedStatement  pstmt=null;
        try{
        	pstmt=dbConnect.prepareStatement(req);
            pstmt.setInt(1,idEnigme);
          	ResultSet rs=(ResultSet)pstmt.executeQuery();	
          	if(rs.next()){
          		this.lieuEnig=rs.getInt("LIEU");
          		this.langue=rs.getString("LANGUE_ENIG");
          		this.texte=rs.getString("TEXTE_ENIG");
          	}
          	else { 
          		throw new Exception("Code inconnu");
          	}
        }
        catch(Exception e){
        	Log.d("connexion","erreur"+e);   
            throw new Exception("Erreur de lecture "+e.getMessage());
        }
        finally{ 
        	try{
        		pstmt.close();
            }
            catch (Exception e){}
            }
     }
        
    /**
     * méthode statique permettant de récupérer l'enigme correspondant au lieu recherché
     * @param lieuEnig lieu auquel correspond l'enigme
	 * @return enigme du lieu recherché
	 * @throws Exception lieu inconnu
	 */
	 public static EnigmeDB rechEnigmeLieu(int lieuRech)throws Exception{
		    String req = "select * from ENIGME where lieu = ?";
			PreparedStatement pstmt=null;
		    try{
				pstmt = dbConnect.prepareStatement(req);
				pstmt.setInt(1,lieuRech);
				ResultSet rs=(ResultSet)pstmt.executeQuery();
				if(rs.next()){
	                int idEnig=rs.getInt("ID_ENIGME");
					String lang=rs.getString("LANGUE_ENIG");
					String tex=rs.getString("TEXTE_ENIG");
					return new EnigmeDB(idEnig,lieuRech,lang,tex);
				}
				else throw new Exception("lieu inconnu");
	        }
		    catch(Exception e){
				throw new Exception("Erreur de lecture "+e.getMessage());
	        }
	        finally{ 
	            try{
	              pstmt.close();
	            }
	            catch (Exception e){}
	        }
	    }
        
        
        
	 /**
	  * mise à jour du texte d'un enigme sur base de son identifiant
	  * @throws Exception erreur de mise à jour
	  */
	 public void update() throws Exception{
	    	 CallableStatement cstmt=null;

	    	    try{
	    		    String req = "call UPDATEENIGME(?,?)";
	    		    cstmt=dbConnect.prepareCall(req);
	    	        cstmt.setInt(1,idEnigme);
	    		    cstmt.setString(2,texte);
					cstmt.executeUpdate();
	    	    }
				catch(Exception e){
	    		  	throw new Exception("Erreur de mise à jour "+e.getMessage());
	    	    }
	    	    finally{ 
	    	        try{
						cstmt.close();
	    	        }
	    	        catch (Exception e){}
	    	    }
	    }
		
	 /**
	  * effacement d'un enigme sur base de son identifiant
	  * @throws Exception erreur d'effacement
	  */
	 public void delete()throws Exception{
		
	    	CallableStatement cstmt =null;
			try{
				String req = "call DELENIGME(?)";
				cstmt = dbConnect.prepareCall(req);
				cstmt.setInt(1,idEnigme);
				cstmt.executeUpdate();
	 	    }
			

          	catch (SQLException sqle){
    			if(sqle.getErrorCode()==20004){
    				throw new Exception("Cet enigme n'existe pas!"); 
    			}
          	
        }
			catch (Exception e){
	 	     	throw new Exception("Erreur d'effacement "+e.getMessage());
	        }
	        finally{ 
	            try{
					cstmt.close();
	            }
	            catch (Exception e){}
	        }
	    }
}
