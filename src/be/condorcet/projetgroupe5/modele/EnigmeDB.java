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
	 * connexion � la base de donn�es partag�e entre toutes les instances(statique)
	 */
    protected static Connection dbConnect=null;
        
    /**
     * constructeur par d�faut
     */
	public EnigmeDB(){
   	}

	/**
	 * constructeur param�tr� � utiliser avant lors de la cr�ation, l'idEnigme
	 * ne doit pas �tre pr�cis�,il sera affect� par la base de donn�es lors de la cr�ation
	 * @see #create()
	 * @param lieuEnig lieu auquel l'enigme correspond
	 * @param langue langue de l'enigme
	 * @param texte texte de l'enigme
	 */
	public EnigmeDB(int lieuEnig, String langue, String texte){	
      super(0,lieuEnig,langue,texte);
   }
   
   	/**
   	 * constructeur param�tr� � utiliser avant lorsque l'enigme est d�j� pr�sente
   	 * dans la base de donn�es
   	 * @param idEnigme identifiant de l'enigme
   	 * @param lieuEnig lieu auquel l'enigme correspond
   	 * @param langue langue de l'enigme
   	 * @param texte texte de l'enigme
   	 */
   	public EnigmeDB(int idEnigme, int lieuEnig, String langue, String texte){	
       super(idEnigme, lieuEnig, langue, texte);	
   	}   
   
   	/**
   	 * constructeur � utiliser lorsque l'enigme est d�j� pr�sente en base de donn�es
   	 * mais qu'on ne conna�t que son identifiant, � utiliser avec read
   	 * @see #read()
   	 * @param idEnigme
   	 */
   	public EnigmeDB(int idEnigme){
       super(idEnigme,0 , "", "");
    }

   	/**
   	 * m�thode statique permettant de partager la connexion entre toutes les instances de
   	 * EnigmeDB
   	 * @param nouvdbConnect connexion � la base de donn�es
   	 */
    public static void setConnection(Connection nouvdbConnect) {
       dbConnect=nouvdbConnect;
    }
	
    /**
     * enregistrement d'une nouvelle enigme dans la base de donn�es
     * @throws Exception erreur de cr�ation
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
        	 throw new Exception("Erreur de cr�ation "+e.getMessage());
         }
         finally{ 
        	 try{
        		 cstmt.close();
             }
             catch (Exception e){}
         }
     }
	
    /**
     * r�cup�ration des donn�es d'une enigme sur base de son identifiant
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
          		this.langue=rs.getString("LANGUE");
          		this.texte=rs.getString("TEXTE");
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
     * m�thode statique permettant de r�cup�rer l'enigme correspondant au lieu recherch�
     * @param lieuEnig lieu auquel correspond l'enigme
	 * @return enigme du lieu recherch�
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
					String lang=rs.getString("LANGUE");
					String tex=rs.getString("TEXTE");
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
	  * mise � jour du texte d'un enigme sur base de son identifiant
	  * @throws Exception erreur de mise � jour
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
	    		  	throw new Exception("Erreur de mise � jour "+e.getMessage());
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
