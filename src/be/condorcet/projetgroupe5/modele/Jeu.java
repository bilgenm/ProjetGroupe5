package be.condorcet.projetgroupe5.modele;

public class Jeu {
	/**
	 * identifiant unique de la ville du jeu
	 */
	protected int idVille;
	/**
	 * lieu de debut du jeu
	 */
	protected int debut;
	/**
	 * nom de la ville 
	 */
	protected String nomVille;

	
	/**
	 * constructeur par défaut
	 */
	public Jeu() {
		super();
	}

	/**
	 * constructeur paramétré
	 * @param idVille identifiant unique de la ville du jeu, affecté par la base de données
	 * @param debut lieu de debut du jeu
	 * @param nomVille nom de la ville
	 */
	public Jeu(int idVille, int debut, String nomVille) {
		super();
		this.idVille = idVille;
		this.debut = debut;
		this.nomVille = nomVille;
	}

	/**
	 * getter idVille
	 * @return identifiant de la ville du jeu
	 */
	public int getIdVille() {
		return idVille;
	}

	 /**
     * setter idVille
     * @param idVille identifiant de la ville du jeu
     */
	public void setIdVille(int idVille) {
		this.idVille = idVille;
	}

	/**
	 * getter debut
	 * @return lieu de debut du jeu
	 */
	public int getDebut() {
		return debut;
	}

	/**
     * setter debut
     * @param debut lieu de debut du jeu
     */
	public void setDebut(int debut) {
		this.debut = debut;
	}

	/**
	 * getter nomVille
	 * @return nom de la ville du jeu
	 */
	public String getNomVille() {
		return nomVille;
	}

	/**
     * setter nomVille
     * @param nomVille nom de la ville du jeu
     */
	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	/**
	 * méthode toString
	 * @return informations complètes
	 */
	@Override
	public String toString() {
		return "Jeu [idVille=" + idVille + ", debut=" + debut + ", nomVille="
				+ nomVille + "]";
	}
	
}

