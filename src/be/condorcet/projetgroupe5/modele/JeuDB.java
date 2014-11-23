package be.condorcet.projetgroupe5.modele;
/**
 * classe de mappage poo-relationnel Jeu
 * @author Paz Rojas Laura
 * @version 1.0
 * @see Jeu
 */


import java.sql.*;

import android.util.Log;

public class JeuDB extends Jeu implements CRUD {

	/**
	 * connexion � la base de donn�es partag�e entre toutes les instances(statique)
	 */
	protected static Connection dbConnect=null;

	/**
	 * constructeur par d�faut
	 */
	public JeuDB(){
		super();
	}
   	

	/**
	 * constructeur param�tr� � utiliser avant lors de la cr�ation, l'idjeu
   * ne doit pas �tre pr�cis�,il sera affect� par la base de donn�es lors de la cr�ation
   * @see #create()
   * @param debut lieu par lequel debute le jeu
   * @param nomVille ville dans laquelle se d�roule le jeu
   */
	public JeuDB(int debut, String nomVille){	
		super(0,debut,nomVille);
    }
   
	/**
   * constructeur param�tr� � utiliser avant lorsque le jeu est d�j� pr�sent
   * dans la base de donn�es
   * @param idVille identifiant de la ville du jeu
   * @param debut lieu par lequel debute le jeu
   * @param nomVille ville dans laquelle se d�roule le jeu
   */
	public JeuDB(int idVille,int debut, String nomVille){	
		super(idVille,debut,nomVille);	
    }     
   
	/**
   * constructeur � utiliser lorsque le jeu est d�j� pr�sent en base de donn�es
   * mais qu'on ne conna�t que son identifiant, � utiliser avec read
   * @see #read()
   * @param idVille
   */
    public JeuDB(int idVille){
		super(idVille,0,"");
    }
    
    /**
   * m�thode statique permettant de partager la connexion entre toutes les instances de
   * JeuDB
   * @param nouvdbConnect connexion � la base de donn�es
   */
    public static void setConnection(Connection nouvdbConnect) {
    	dbConnect=nouvdbConnect;
    }
     
    

    /**
  	* enregistrement d'un nouveau jeu dans la base de donn�es
    * @throws Exception erreur de cr�ation
    */   
    public void create()throws Exception{
    	CallableStatement cstmt=null;
    	try{
    		String req = "call CREATEJEU(?,?,?)";
    	    cstmt = dbConnect.prepareCall(req);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.setInt(2,debut);
            cstmt.setString(3,nomVille);
            cstmt.executeUpdate();
            this.idVille=cstmt.getInt(1);
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
	* r�cup�ration des donn�es d'un Jeu sur base de son identifiant
	* @throws Exception code inconnu
	*/	
	
	public void read() throws Exception{
		String req = "select * from JEU where id_ville =?"; 
	    PreparedStatement  pstmt=null;
        try{
        	pstmt=dbConnect.prepareStatement(req);
            pstmt.setInt(1,idVille);
     	    ResultSet rs=(ResultSet)pstmt.executeQuery();	
        
		if(rs.next()){
	     	this.debut=rs.getInt("DEBUT");
	     	this.nomVille=rs.getString("NOM_VILLE");
		}
	    else { 
			throw new Exception("Identifiant inconnu");
	      }

        }
		catch(Exception e){
			//Log.d("connexion","erreur"+e);   
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
	* m�thode statique permettant de r�cup�rer le lieu par lequel debute le jeu pour la ville choisie
	* @param nom_ville nom de la ville recherch�
	* @return lieu de debut
	* @throws Exception nom inconnu
	*/
    public static JeuDB rechLieuDebut(String villeRech)throws Exception{
	    String req = "select * from JEU where nom_ville =?";
		PreparedStatement pstmt=null;
	    try{
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setString(1,villeRech);
			ResultSet rs=(ResultSet)pstmt.executeQuery();
			if(rs.next()){
                int idV=rs.getInt("ID_VILLE");
				int debutR=rs.getInt("DEBUT");
				return new JeuDB(idV,debutR,villeRech);
			}
			else throw new Exception("ville inconnue");
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
    * mise � jour des donn�es du jeu sur base de son identifiant
    * @throws Exception erreur de mise � jour
    */
    public void update() throws Exception{
    	 CallableStatement cstmt=null;

    	    try{
    		    String req = "call UPDATEJEU(?,?)";
    		    cstmt=dbConnect.prepareCall(req);
    	        cstmt.setInt(1,idVille);
    		    cstmt.setInt(2,debut);
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
     * effacement du jeu sur base de son identifiant
	 * @throws Exception erreur d'effacement
	 */
    public void delete()throws Exception{
	
    	CallableStatement cstmt =null;
		try{
			String req = "call DELJEU(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1,idVille);
			cstmt.executeUpdate();
 	    }	
		catch (SQLException sqle){
			if(sqle.getErrorCode()==20005){
				throw new Exception("Ce jeu n'existe pas!"); 
			}
		}
		catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur d'effacement " + e.getMessage());
		}
        finally{ 
            try{
				cstmt.close();
            }
            catch (Exception e){}
        }
    }
	
}
