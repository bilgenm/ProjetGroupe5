package be.condorcet.projetgroupe5.modele;
/**
 * classe de mappage poo-relationnel Jeu
 * @author Paz Rojas Laura
 * @version 1.0
 * @see Jeu
 */


import java.sql.*;
import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class JeuDB extends Jeu implements CRUD,Parcelable  {

	/**
	 * connexion à la base de données partagée entre toutes les instances(statique)
	 */
	protected static Connection dbConnect=null;

	/**
	 * constructeur par défaut
	 */
	public JeuDB(){
		super();
	}
   	

	/**
	 * constructeur paramétré à utiliser avant lors de la création, l'idjeu
   * ne doit pas être précisé,il sera affecté par la base de données lors de la création
   * @see #create()
   * @param debut lieu par lequel debute le jeu
   * @param nomVille ville dans laquelle se déroule le jeu
   */
	public JeuDB(int debut, String nomVille){	
		super(0,debut,nomVille);
    }
   
	/**
   * constructeur paramétré à utiliser avant lorsque le jeu est déjà présent
   * dans la base de données
   * @param idVille identifiant de la ville du jeu
   * @param debut lieu par lequel debute le jeu
   * @param nomVille ville dans laquelle se déroule le jeu
   */
	public JeuDB(int idVille,int debut, String nomVille){	
		super(idVille,debut,nomVille);	
    }     
   
	/**
   * constructeur à utiliser lorsque le jeu est déjà présent en base de données
   * mais qu'on ne connaît que son identifiant, à utiliser avec read
   * @see #read()
   * @param idVille
   */
    public JeuDB(int idVille){
		super(idVille,0,"");
    }
    
    /**
   * méthode statique permettant de partager la connexion entre toutes les instances de
   * JeuDB
   * @param nouvdbConnect connexion à la base de données
   */
    public static void setConnection(Connection nouvdbConnect) {
    	dbConnect=nouvdbConnect;
    }
     
    

    /**
  	* enregistrement d'un nouveau jeu dans la base de données
    * @throws Exception erreur de création
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
	* récupération des données d'un Jeu sur base de son identifiant
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
	* méthode statique permettant de récupérer le lieu par lequel debute le jeu pour la ville choisie
	* @param nom_ville nom de la ville recherché
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
   	* méthode statique permettant de récupérer toutes les villes 
   	* @return liste des villes
   	* @throws Exception aucune ville trouvée
   	*/
    
	public static ArrayList<JeuDB> listeVilles() throws Exception {
		ArrayList<JeuDB> listeVilles = new ArrayList<JeuDB>();
		String req = "select * from JEU";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			boolean trouve = false;
			while (rs.next()) {
				trouve = true;
				int idV = rs.getInt("ID_VILLE");
				int debutR = rs.getInt("DEBUT");
				String nomVille = rs.getString("NOM_VILLE");
				listeVilles.add(new JeuDB(idV, debutR, nomVille));
			}
			if (!trouve)
				throw new Exception("Aucun ville trouvée!!!");
			else
				return listeVilles;
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
	}
	
    /**
    * mise à jour des données du jeu sur base de son identifiant
    * @throws Exception erreur de mise à jour
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
    @Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idVille);
		dest.writeInt(debut);
		dest.writeString(nomVille);	
	}
	public static final Parcelable.Creator<JeuDB> CREATOR = new Parcelable.Creator<JeuDB>() {
		  @Override
		  public JeuDB createFromParcel(Parcel source) {
		    return new JeuDB(source);
		  }
		  @Override
		  public JeuDB[] newArray(int size) {
		    return new JeuDB[size];
		  }
		};

	public JeuDB(Parcel in) {
		  idVille = in.readInt();
		  debut = in.readInt();
		  nomVille = in.readString();
		}
	
}
