package be.condorcet.projetgroupe5.modele;

public class Enigme {
	/**
	 * identifiant unique de l'enigme
	 */
	protected int idEnigme;
	/**
	 * lieu auquel l'enigme correspond
	 */
	protected int lieuEnig;
	/**
	 * langue de l'enigme
	 */
	protected String langue;
    /**
	 * texte de l'enigme
	 */
	protected String texte;
	/**
	 * description du lieu
	 */
	protected String descLieu;
	

	/**
	 * constructeur par défaut
	 */
	public Enigme() {
		super();
	}
	
	/**
	 * constructeur paramétré
	 * @param idEnigme identifiant unique de l'enigme, affecté par la base de données
	 * @param lieuEnig lieu auquel l'enigme correspond
	 * @param langue langue de l'enigme
	 * @param texte texte de l'enigme
	 * @param descLieu description du lieu
	 */
	public Enigme(int idEnigme, int lieuEnig, String langue, String texte, String descLieu) {
		super();
		this.idEnigme = idEnigme;
		this.lieuEnig = lieuEnig;
		this.langue = langue;
		this.texte = texte;
		this.descLieu =descLieu;
	}

	/**
	 * getter idEnigme
	 * @return identifiant de l'enigme
	 */
	public int getIdEnigme() {
		return idEnigme;
	}

	 /**
     * setter idEnigme
     * @param idEnigme identifiant de l'enigme
     */
	public void setIdEnigme(int idEnigme) {
		this.idEnigme= idEnigme;
	}

	/**
	 * getter lieuEnig
	 * @return lieu auquel l'enigme correspond
	 */
	public int getLieuEnig() {
		return lieuEnig;
	}

	 /**
     * setter lieuEnig
     * @param lieuEnig lieu de l'enigme 
     */
	public void setLieuEnig(int lieuEnig) {
		this.lieuEnig = lieuEnig;
	}

	/**
	 * getter langue
	 * @return langue de l'enigme
	 */
	public String getLangue() {
		return langue;
	}

	 /**
     * setter langue
     * @param langue langue de l'enigme 
     */
	public void setLangue(String langue) {
		this.langue = langue;
	}
	
	/**
	 * getter texte
	 * @return texte de l'enigme
	 */
	public String getTexte() {
		return texte;
	}

	 /**
     * setter texte
     * @param texte texte de l'enigme 
     */
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	/**
	 * getter descLieu
	 * @return description du lieu
	 */
	public String getDescLieu() {
		return descLieu;
	}
	/**
     * setter descLieu
     * @param descLieu description du lieu 
     */
	public void setDescLieu(String descLieu) {
		this.descLieu = descLieu;
	}


	/**
	 * méthode toString
	 * @return informations complètes
	 */
	
	@Override
	public String toString() {
		return "Enigme [idEnigme=" + idEnigme + ", lieuEnig=" + lieuEnig
				+ ", langue=" + langue + ", texte=" + texte + ", descLieu="
				+ descLieu + "]";
	}
}

