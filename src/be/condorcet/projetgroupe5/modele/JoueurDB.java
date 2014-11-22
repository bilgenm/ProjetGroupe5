package be.condorcet.projetgroupe5.modele;

import java.sql.*;
//import java.util.*;
import android.util.Log;

public class JoueurDB extends Joueur implements CRUD {

	/**
	 * connexion � la base de donn�es partag�e entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;

	public void create() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call createjoueur(?,?,?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2, nom);
			cstmt.setString(3, prenom);
			cstmt.setString(4, pseudo);
			cstmt.setString(5, password);
			cstmt.executeUpdate();
			this.idJoueur = cstmt.getInt(1);
		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur de cr�ation " + e.getMessage());
		} finally {
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}

	public void read() throws Exception {
		/* Si on utilise une requete prepar�e */
		String req = "select * from joueur where id_joueur =?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setInt(1, idJoueur);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			if (rs.next()) {
				this.nom = rs.getString("NOM");
				this.prenom = rs.getString("PRENOM");
				this.pseudo = rs.getString("PSEUDO");
				this.password = rs.getString("PASSJOUEUR");
			} else {
				throw new Exception("Code inconnu");
			}

		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}
	}

	public void update() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updatejoueur(?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idJoueur);
			cstmt.setString(2, pseudo);
			cstmt.setString(3, password);
			cstmt.executeUpdate();
		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur de mise � jour " + e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}

	public void delete() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call deljoueur(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idJoueur);
			cstmt.executeUpdate();
		} catch (Exception e) {
			Log.d("connexion", "erreur" + e);
			throw new Exception("Erreur d'effacement " + e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}
}
