package be.condorcet.projetgroupe5.modele;

/**
 * classe métier de gestion d'un lieu
 * @author Bilgen Mustafa
 * @version 1.0
 * @see Joueur
 * @see Enigme
 * @see Jeu
 */

public class Lieu {
	/**
	 * identifiant du lieu
	 */
	protected int idLieu;
	/**
	 * succeusseur du lieu
	 */
	protected int successeur;
	/**
	 * nom du lieu
	 */
	protected String nomLieu;
	/**
	 * description du lieu
	 */
	protected String descLieu;
	/**
	 * latitude du lieu
	 */
	protected double latLieu;
	/**
	 * longitude du lieu
	 */
	protected double longLieu;
	
	/**
	 * constructeur par défaut
	 */
	public Lieu() {
		super();
	}
	
	/**
	 * constructeur paramétré
	 * @param idLieu identifiant du lieu
	 * @param successeur successeur du lieu
	 * @param nomLieu nom du lieu
	 * @param descLieu description du lieu
	 * @param latLieu latitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public Lieu(int idLieu, int successeur, String nomLieu, String descLieu,
			double latLieu, double longLieu) {
		super();
		this.idLieu = idLieu;
		this.successeur = successeur;
		this.nomLieu = nomLieu;
		this.descLieu = descLieu;
		this.latLieu = latLieu;
		this.longLieu = longLieu;
	}
	
	/**
	 * constructeur paramétré
	 * @param idLieu identifiant du lieu
	 * @param nomLieu nom du lieu
	 * @param descLieu description du lieu
	 * @param latLieu latitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public Lieu(int idLieu, String nomLieu, String descLieu,
			double latLieu, double longLieu) {
		super();
		this.idLieu = idLieu;
		this.nomLieu = nomLieu;
		this.descLieu = descLieu;
		this.latLieu = latLieu;
		this.longLieu = longLieu;
	}

	/**
	 * getter identifiant du lieu
	 * @return identifiant du lieu
	 */
	public int getIdLieu() {
		return idLieu;
	}

	/**
	 * setter identifiant du lieu
	 * @param idLieu identifiant du lieu
	 */
	public void setIdLieu(int idLieu) {
		this.idLieu = idLieu;
	}

	/**
	 * getter succeusseur du lieu
	 * @return succeusseur du lieu
	 */
	public int getSuccesseur() {
		return successeur;
	}

	/**
	 * setter succeusseur du lieu
	 * @param successeur succeusseur du lieu
	 */
	public void setSuccesseur(int successeur) {
		this.successeur = successeur;
	}

	/**
	 * getter nom du lieu
	 * @return nom du lieu
	 */
	public String getNomLieu() {
		return nomLieu;
	}

	/**
	 * setter nom du lieu
	 * @param nomLieu nom du lieu
	 */
	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}

	/**
	 * getter description du lieu
	 * @return description du lieu
	 */
	public String getDescLieu() {
		return descLieu;
	}

	/**
	 * setter description du lieu
	 * @param descLieu description du lieu
	 */
	public void setDescLieu(String descLieu) {
		this.descLieu = descLieu;
	}

	/**
	 * getter latitude du lieu
	 * @return latitude du lieu
	 */
	public double getLatLieu() {
		return latLieu;
	}

	/**
	 * setter latitude du lieu
	 * @param latLieu latitude du lieu
	 */
	public void setLatLieu(double latLieu) {
		this.latLieu = latLieu;
	}

	/**
	 * getter longitude du lieu
	 * @return longitude du lieu
	 */
	public double getLongLieu() {
		return longLieu;
	}

	/**
	 * setter longitude du lieu
	 * @param longLieu longitude du lieu
	 */
	public void setLongLieu(double longLieu) {
		this.longLieu = longLieu;
	}

	/**
	 * méthode toString
	 * @return informations complètes
	 */
	@Override
	public String toString() {
		return "Lieu [idLieu=" + idLieu + ", successeur=" + successeur
				+ ", nomLieu=" + nomLieu + ", descLieu=" + descLieu
				+ ", latLieu=" + latLieu + ", longLieu=" + longLieu + "]";
	}
}

