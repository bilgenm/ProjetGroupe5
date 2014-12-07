package be.condorcet.projetgroupe5.modele;
/**
 * classe de mappage poo-relationnel joueur
 * @author Bilgen Mustafa
 * @version 1.0
 * @see Joueur
 */

import java.sql.*;
import android.util.Log;


public class JoueurDB extends Joueur implements CRUD {

	/**
	 * connexion � la base de donn�es partag�e entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;

	/**
	 * constructeur par d�faut
	 */
	public JoueurDB() {
		super();

	}

	/**
	 * constructeur param�tr� � utiliser lors de la cr�ation, l'idJoueur ne doit
	 * pas �tre pr�cis�,il sera affect� par la base de donn�es lors de la
	 * cr�ation
	 * @param nom nom du joueur
	 * @param prenom pr�nom du joueur
	 * @param pseudo pseudo du joueur
	 * @param password mot de passe du joueur
	 */
	public JoueurDB(String nom, String prenom, String pseudo, String password) {
		super(0, 0, nom, prenom, pseudo, password);
	}

	/**
	 * constructeur param�tr� � utiliser lorsque le joueur est d�j� pr�sent
     * dans la base de donn�es 
	 * @param idJoueur identifiant du joueur
	 * @param nom nom du joueur
	 * @param prenom pr�nom du joueur
	 * @param pseudo pseudo du joueur
	 * @param password mot de passe du joueur
	 */
	public JoueurDB(int idJoueur, String nom, String prenom, String pseudo,
			String password) {
		super(idJoueur, 0, nom, prenom, pseudo, password);
	}
	/**
	 * constructeur param�tr� � utiliser lorsque le joueur est d�j� pr�sent
     * dans la base de donn�es 
	 * @param idJoueur identifiant du joueur
	 * @param lieuRech lieu recherch� par le joueur
	 * @param nom nom du joueur
	 * @param prenom pr�nom du joueur
	 * @param pseudo pseudo du joueur
	 * @param password mot de passe du joueur
	 */
	public JoueurDB(int idJoueur, int lieuRech, String nom, String prenom,
			String pseudo, String password) {
		super(idJoueur, lieuRech, nom, prenom, pseudo, password);
	}

	/**
	 * constructeur � utiliser lorsque le joueur est d�j� pr�sent dans la base
	 * de donn�es mais qu'on ne conna�t que son identifiant, � utiliser avec
	 * read
	 * @see #read()
	 * @param idJoueur identifiant du joueur
	 */
	public JoueurDB(int idJoueur) {
		super(idJoueur, 0, "", "", "", "");
	}

	/**
	 * m�thode statique permettant de partager la connexion entre toutes les
	 * instances de JoueurDB
	 * @param nouvdbConnect connexion � la base de donn�es
	 */
	public static void setConnection(Connection nouvdbConnect) {
		dbConnect = nouvdbConnect;
	}

	/**
	 * enregistrement d'un nouveau joueur dans la base de donn�es
	 * @throws Exception erreur de cr�ation
	 */
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
			throw new Exception("Erreur de cr�ation " + e.getMessage());
		} finally {
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * r�cup�ration des donn�es d'un joueur sur base de son identifiant
	 * @throws Exception code inconnu
	 */
	public void read() throws Exception {
		String req = "select * from joueur where id_joueur=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setInt(1, idJoueur);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			if (rs.next()) {
				this.lieuRech = rs.getInt("LIEURECH");
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
	
	/**
     * mise � jour des donn�es du joueur sur base de son identifiant
     * @throws Exception erreur de mise � jour
     */
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
			throw new Exception("Erreur de mise � jour " + e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}

	}
	
	/**
     * mise � jour de lieu recherch� par le joueur sur base de 
     * l'identifiant du joueur
     * @throws Exception erreur de mise � jour
     */
	public void updateLieuRech() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updatelieurech(?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idJoueur);
			cstmt.setInt(2, lieuRech);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Erreur de mise � jour de lieur recherch� "
					+ e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * effacement du joueur sur base de son identifiant 
	 * @throws Exception erreur d'effacement
	 */
	public void delete() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call deljoueur(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idJoueur);
			cstmt.executeUpdate();
		} catch (SQLException sqle) {
			// System.out.println("test effacement1");
			// System.out.println("test  effacement joueur 1"+sqle.getErrorCode());
			if (sqle.getErrorCode() == 20002) {
				// System.out.println("test effacement2");
				throw new Exception("Le joueur n'existe pas!");
			}
		} catch (Exception e) {
			throw new Exception("Erreur d'effacement " + e.getMessage());
		} finally {// effectu� dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * m�thode statique permettant de r�cup�rer les infos du joueur 
	 * portant un pseudo pr�cis pass� au param�tre
	 * @param pseudorech pseudo recherch�
	 * @return un objet de type JoueurDB
	 * @throws Exception pseudo inconnu
	 */
	public static JoueurDB rechPseudo(String pseudorech) throws Exception {
		JoueurDB joueur = null;
		String req = "select * from joueur where pseudo=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnect.prepareStatement(req);
			pstmt.setString(1, pseudorech);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			if (rs.next()) {
				int idJoueur = rs.getInt("ID_JOUEUR");
				int lieuRech = rs.getInt("LIEURECH");
				String nom = rs.getString("NOM");
				String prenom = rs.getString("PRENOM");
				String pseudo = rs.getString("PSEUDO");
				String password = rs.getString("PASSJOUEUR");
				joueur = new JoueurDB(idJoueur, lieuRech, nom, prenom, pseudo,
						password);
				return joueur;

			} else {
				throw new Exception("pseudo inconnu");
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

}
