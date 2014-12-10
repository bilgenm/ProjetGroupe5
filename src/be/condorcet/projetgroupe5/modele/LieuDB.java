package be.condorcet.projetgroupe5.modele;

/**
 * classe de mappage poo-relationnel lieu
 * @author Bilgen Mustafa
 * @version 1.0
 * @see Lieu
 */

import java.sql.*;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class LieuDB extends Lieu implements CRUD, Parcelable {
	/**
	 * connexion à la base de données partagée entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;

	/**
	 * constructeur par défaut
	 */
	public LieuDB() {
		super();
	}
	
	/**
	 * constructeur paramétré à utiliser lors de la création, l'idLieu ne doit
	 * pas être précisé,il sera affecté par la base de données lors de la
	 * création
	 * @param nomLieu nom du lieu
	 * @param latLieu latitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public LieuDB(String nomLieu, double latLieu,
			double longLieu) {
		super(0, nomLieu, latLieu, longLieu);
	}
	/**
	 * constructeur paramétré à utiliser lorsque le lieu est déjà présent
     * dans la base de données  
	 * @param successeur successeur du lieu
	 * @param nomLieu nom du lieu
	 * @param latLieu latitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public LieuDB(int successeur, String nomLieu, double latLieu,
			double longLieu) {
		super(0, successeur, nomLieu, latLieu, longLieu);
	}

	/**
	 * constructeur paramétré à utiliser lorsque le lieu est déjà présent
     * dans la base de données 
	 * @param idLieu identifiant du lieu
	 * @param successeur successeur du lieu
	 * @param nomLieu nom du lieu
	 * @param latLieu latitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public LieuDB(int idLieu, int successeur,String nomLieu, double latLieu,
			double longLieu) {
		super(idLieu, successeur, nomLieu, latLieu, longLieu);
	}
	/**
	 * constructeur à utiliser lorsque le lieu est déjà présent dans la base
	 * de données mais qu'on ne connaît que son identifiant, à utiliser avec
	 * read
	 * @see #read()
	 * @param idLieu identifiant du lieu
	 */
	public LieuDB(int idLieu) {
		super(idLieu, 0, "", 0, 0);
	}

	/**
	 * méthode statique permettant de partager la connexion entre toutes les
	 * instances de LieuDB
	 * @param nouvdbConnect connexion à la base de données
	 */
	public static void setConnection(Connection nouvdbConnect) {
		dbConnect = nouvdbConnect;
	}

	/**
	 * enregistrement d'un nouveau lieu dans la base de données
	 * @throws Exception erreur de création
	 */
	public void create() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call createlieu(?,?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2, nomLieu);
			cstmt.setDouble(3, latLieu);
			cstmt.setDouble(4, longLieu);
			cstmt.executeUpdate();
			this.idLieu = cstmt.getInt(1);
		} catch (Exception e) {
			throw new Exception("Erreur de création " + e.getMessage());
		} finally {
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * récupération des données d'un lieu sur base de son identifiant
	 * @throws Exception code inconnu
	 */
	public void read() throws Exception {
		String req = "select * from lieu where id_lieu =?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setInt(1, idLieu);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			if (rs.next()) {
				this.successeur = rs.getInt("SUCCESSEUR");
				this.nomLieu = rs.getString("NOM_LIEU");
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

	/**
     * mise à jour de successuer d'un lieu sur base de son identifiant
     * @throws Exception erreur de mise à jour
     */
	public void update() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updatelieu(?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idLieu);
			cstmt.setInt(2, successeur);

			cstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Erreur de mise à jour " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * effacement d'un lieu sur base de son identifiant 
	 * @throws Exception erreur d'effacement
	 */
	public void delete() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call dellieu(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idLieu);
			cstmt.executeUpdate();
		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == 20003) {
				throw new Exception("Le lieu n'existe pas!");
			}
		} catch (Exception e) {
			throw new Exception("Erreur d'effacement " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}
	
	/**
	 * méthode statique permettant de récupérer tous les lieux succeusseur 
	 * à partir d'un debut (lieu donné) d'une ville
	 * @param lieuRech le lieu qu'on recherche ses successeurs
	 * @return liste des lieux appartenant à un jeu(ville)
	 * @throws Exception lieu inconnu
	 */
	public static ArrayList<LieuDB> rechLieu(int lieuRech) throws Exception {
		ArrayList<LieuDB> listeLieux = new ArrayList<LieuDB>();
		String req = "select * from lieu where id_lieu=?";
        boolean flag=true, ok=false;
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			do{
				pstmt.setInt(1, lieuRech);
				ResultSet rs = (ResultSet) pstmt.executeQuery();
				if (rs.next()) {
					ok=true;
					int idLieu = rs.getInt("ID_LIEU");
					int successeur = rs.getInt("SUCCESSEUR");
					String nomLieu = rs.getString("NOM_LIEU");
					double latLieu = rs.getDouble("LATITUDE");
					double longLieu = rs.getDouble("LONGITUDE");
					listeLieux.add(new LieuDB(idLieu,successeur,nomLieu,latLieu,longLieu));
					lieuRech=successeur;

				} else {
					flag=false;
				}
			}while(flag);
			
			if(ok){
				return listeLieux;
			}else {
				throw new Exception("Lieu inconnu");
				
			}

		} catch (Exception e) {

			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idLieu);
		dest.writeInt(successeur);
		dest.writeDouble(latLieu);
		dest.writeDouble(longLieu);		
	}
	public static final Parcelable.Creator<LieuDB> CREATOR = new Parcelable.Creator<LieuDB>() {
		  @Override
		  public LieuDB createFromParcel(Parcel source) {
		    return new LieuDB(source);
		  }
		  @Override
		  public LieuDB[] newArray(int size) {
		    return new LieuDB[size];
		  }
		};

	public LieuDB(Parcel in) {
		  idLieu = in.readInt();
		  successeur = in.readInt();
		  latLieu = in.readDouble();
		  longLieu = in.readDouble();
	}
}




