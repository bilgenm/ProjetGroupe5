package be.condorcet.projetgroupe5.modele;

public class Jeu {
	protected int idVille;
	protected int debut;
	protected String nomVille;

	

	public Jeu() {
		super();
	}

	public Jeu(int idVille, int debut, String nomVille) {
		super();
		this.idVille = idVille;
		this.debut = debut;
		this.nomVille = nomVille;
	}

	public int getIdVille() {
		return idVille;
	}

	public void setIdVille(int idVille) {
		this.idVille = idVille;
	}

	public int getDebut() {
		return debut;
	}

	public void setDebut(int debut) {
		this.debut = debut;
	}

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	@Override
	public String toString() {
		return "Jeu [idVille=" + idVille + ", debut=" + debut + ", nomVille="
				+ nomVille + "]";
	}
	
}

