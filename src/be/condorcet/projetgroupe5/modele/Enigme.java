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
	 */
	public Enigme(int idEnigme, int lieuEnig, String langue, String texte) {
		super();
		this.idEnigme = idEnigme;
		this.lieuEnig = lieuEnig;
		this.langue = langue;
		this.texte = texte;
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
	 * méthode toString
	 * @return informations complètes
	 */
	@Override
	public String toString() {
		return "Enigme [id Enigme :" + idEnigme + ", lieu Enigme :" + lieuEnig + ", Langue :" + langue + ", Texte :" + texte +"]";
				
	}
	
}

