package be.condorcet.projetgroupe5.modele;

/**
 * classe métier de gestion d'un joueur
 * @author Bilgen Mustafa
 * @version 1.0
 */
public class Joueur {
	/**
	 * identifiant unique du joueur
	 */
	protected int idJoueur;
	/**
	 * lieu recherché par le joueur
	 */
	protected int lieuRech;
	/**
	 * nom du joueur
	 */
	protected String nom;
	/**
	 * prénom du joueur
	 */
	protected String prenom;
	/**
	 * pseudo du joueur
	 */
	protected String pseudo;
	/**
	 * mot de passe du joueur
	 */
	protected String password;
    /**
     * constructeur par défaut
     */
	public Joueur() {
		super();
	}

	/**
	 * constructeur paramétré
	 * @param idJoueur identifiant unique du joueur , affecté par la base de données 
	 * @param lieuRech lieu recherché par le joueur
	 * @param nom nom du joueur 
	 * @param prenom prénom du joueur
	 * @param pseudo pseudo du joueur
	 * @param password mot de passe du joueur
	 */
	public Joueur(int idJoueur, int lieuRech, String nom, String prenom,
			String pseudo, String password) {
		super();
		this.idJoueur = idJoueur;
		this.lieuRech = lieuRech;
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.password = password;
	}
	/**
	 * getter idJoueur
	 * @return identifiant du joueur
	 */
	public int getIdJoueur() {
		return idJoueur;
	}
	/**
	 * setter idJoueur
	 * @param idJoueur identifiant du joueur
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}
	/**
	 * getter lieu recherché
	 * @return lieu recherché
	 */
	public int getLieuRech() {
		return lieuRech;
	}
	/**
	 * setter lieu recherché
	 * @param lieuRech lieu recherché
	 */
	public void setLieuRech(int lieuRech) {
		this.lieuRech = lieuRech;
	}
	/**
	 * getter nom du joueur
	 * @return nom du joueur
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * setter nom du joueur
	 * @param nom nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * getter prénom du joueur
	 * @return prénom du joueur
	 */
	public String getPrenom() {
		return prenom;
	}
	/**
	 * setter prénom du joueur
	 * @param prenom prénom du joueur
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	/**
	 * getter pseudo du joueur
	 * @return pseudo du joueur
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * setter pseudo du joueur
	 * @param pseudo pseudo du joueur
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	/**
	 * getter password du joueur
	 * @return password du joueur
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * setter password du joueur
	 * @param password password du joueur
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 *méthode toString
	 *@return informations du joueur
	 */
	
}

