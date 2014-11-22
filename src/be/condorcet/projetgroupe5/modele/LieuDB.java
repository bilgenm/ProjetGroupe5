package be.condorcet.projetgroupe5.modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import android.util.Log;

public class LieuDB extends Lieu implements CRUD {
	/**
     * connexion à la base de données partagée entre toutes les instances(statique)
     */
    protected static Connection dbConnect=null;
	public void create() throws Exception {
		CallableStatement cstmt=null;
        try{
            String req = "call createlieu(?,?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.setInt(2,successeur);
            cstmt.setString(3,nomLieu);
            cstmt.setString(4,descLieu);
            cstmt.setDouble(5,latLieu);
            cstmt.setDouble(5,longLieu);
            cstmt.executeUpdate();
            this.idLieu = cstmt.getInt(1);
        }
        catch(Exception e ){
        	Log.d("connexion", "erreur" + e);
            throw new Exception("Erreur de création "+e.getMessage());
        }
        finally{
            try{
                cstmt.close();
            }
            catch (Exception e){}
        }
		
		
	}

	public void read() throws Exception {
		/* Si on utilise une requete preparée */
		String req = "select * from lieu where id_lieu =?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setInt(1, idLieu);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			if (rs.next()) {
				this.successeur= rs.getInt("SUCCESSEUR");
				this.nomLieu = rs.getString("NOM_LIEU");
				this.descLieu = rs.getString("DESC_LIEU");
				this.latLieu = rs.getDouble("LATITUDE");
				this.longLieu = rs.getDouble("LONGITUDE");
			} else {
				throw new Exception("Code inconnu");
			}

		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				pstmt.close();
			} catch (Exception e) {
			}

		}
	}
	public void update() throws Exception {
		CallableStatement cstmt=null;
        try{
            String req = "call updatelieu(?,?)";
            cstmt=dbConnect.prepareCall(req);
            cstmt.setInt(1,idLieu);
            cstmt.setInt(2,successeur);
  
            cstmt.executeUpdate();
        }
        catch(Exception e){
        	Log.d("connexion", "erreur" + e);
            throw new Exception("Erreur de mise à jour "+e.getMessage());
        }
        finally{//effectué dans tous les cas
            try{
                cstmt.close();
            }
            catch (Exception e){}
        }
		
		
	}
	
	public void delete() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call dellieu(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idLieu);
			cstmt.executeUpdate();
		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur d'effacement " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}

}


