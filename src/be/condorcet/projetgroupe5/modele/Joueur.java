package be.condorcet.projetgroupe5.modele;

public class Joueur {
	protected int idJoueur;
	protected int lieuRech;
	protected String nom;
	protected String prenom;
	protected String pseudo;
	protected String password;
	
	public Joueur() {
		super();
	}
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
	public int getIdJoueur() {
		return idJoueur;
	}
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}
	public int getLieuRech() {
		return lieuRech;
	}
	public void setLieuRech(int lieuRech) {
		this.lieuRech = lieuRech;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Joueur [idJoueur=" + idJoueur + ", lieuRech=" + lieuRech
				+ ", nom=" + nom + ", prenom=" + prenom + ", pseudo=" + pseudo
				+ ", password=" + password + "]";
	}
}
