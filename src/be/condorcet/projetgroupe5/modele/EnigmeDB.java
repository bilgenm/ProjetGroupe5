package be.condorcet.projetgroupe5.modele;
/**
 * classe de mappage poo-relationnel Enigme
 * @author Paz Rojas Laura
 * @version 1.0
 * @see Enigme
 */
import java.sql.*;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class EnigmeDB extends Enigme implements CRUD,Parcelable {
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
	 * @param descLieu description du lieu
	 */
	public EnigmeDB(int lieuEnig, String langue, String texte, String descLieu){	
      super(0,lieuEnig,langue,texte,descLieu);
   }
   
   	/**
   	 * constructeur param�tr� � utiliser avant lorsque l'enigme est d�j� pr�sente
   	 * dans la base de donn�es
   	 * @param idEnigme identifiant de l'enigme
   	 * @param lieuEnig lieu auquel l'enigme correspond
   	 * @param langue langue de l'enigme
   	 * @param texte texte de l'enigme
   	 * @param descLieu description du lieu
   	 */
   	public EnigmeDB(int idEnigme, int lieuEnig, String langue, String texte, String descLieu){	
       super(idEnigme, lieuEnig, langue, texte,descLieu);	
   	}   
   
   	/**
   	 * constructeur � utiliser lorsque l'enigme est d�j� pr�sente en base de donn�es
   	 * mais qu'on ne conna�t que son identifiant, � utiliser avec read
   	 * @see #read()
   	 * @param idEnigme
   	 */
   	public EnigmeDB(int idEnigme){
       super(idEnigme,0 , "", "", "");
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
        	String req = "call CREATEENIGME(?,?,?,?,?)";
        	cstmt = dbConnect.prepareCall(req);
        	cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
        	cstmt.setInt(2,lieuEnig);
        	cstmt.setString(3,langue);
        	cstmt.setString(4,texte);
        	cstmt.setString(5,descLieu);
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
          		this.langue=rs.getString("LANGUE_ENIG");
          		this.texte=rs.getString("TEXTE_ENIG");
          		this.descLieu=rs.getString("DESC_LIEU");
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
     * m�thode statique permettant de r�cup�rer l'enigme correspondant
     * au lieu recherch� et dans une langue d�finie
     * @param lieuRech lieu auquel correspond l'enigme
     * @param langueRech langue dans laquelle on veut afficher
	 * @return enigme du lieu recherch�
	 * @throws Exception lieu inconnu, langue inconnue
	 */
	 public static EnigmeDB rechEnigmeLieu(int lieuRech, String langueRech)throws Exception{
		    String req = "select * from ENIGME where lieu = ? and langue_enig = ?";
			PreparedStatement pstmt=null;
		    try{
				pstmt = dbConnect.prepareStatement(req);
				pstmt.setInt(1,lieuRech);
				pstmt.setString(2, langueRech);
				ResultSet rs=(ResultSet)pstmt.executeQuery();
				if(rs.next()){
	                int idEnig=rs.getInt("ID_ENIGME");
					String tex=rs.getString("TEXTE_ENIG");
					String descl=rs.getString("DESC_LIEU");
					return new EnigmeDB(idEnig,lieuRech,langueRech,tex,descl);
				}
				else throw new Exception("lieu inconnu, langue inconnue");
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idEnigme);
		dest.writeInt(lieuEnig);
		dest.writeString(langue);
		dest.writeString(texte);
		dest.writeString(descLieu);	
	}
	public static final Parcelable.Creator<EnigmeDB> CREATOR = new Parcelable.Creator<EnigmeDB>() {
		  @Override
		  public EnigmeDB createFromParcel(Parcel source) {
		    return new EnigmeDB(source);
		  }
		  @Override
		  public EnigmeDB[] newArray(int size) {
		    return new EnigmeDB[size];
		  }
		};

	public EnigmeDB(Parcel in) {
		  idEnigme = in.readInt();
		  lieuEnig = in.readInt();
		  langue = in.readString();
		  texte = in.readString();
		  descLieu = in.readString();
	}
}
