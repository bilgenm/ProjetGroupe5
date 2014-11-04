package be.condorcet.projetgroupe5.modele;

public class Lieu {
	protected int idLieu;
	protected int successeur;
	protected String nomLieu;
	protected String descLieu;
	protected double longLieu;
	protected double latLieu;
	
	
	
	public Lieu() {
		super();
	}

	public Lieu(int idLieu, int successeur, String nomLieu, String descLieu,
			double longLieu, double latLieu) {
		super();
		this.idLieu = idLieu;
		this.successeur = successeur;
		this.nomLieu = nomLieu;
		this.descLieu = descLieu;
		this.longLieu = longLieu;
		this.latLieu = latLieu;
	}

	public int getIdLieu() {
		return idLieu;
	}

	public void setIdLieu(int idLieu) {
		this.idLieu = idLieu;
	}

	public int getSuccesseur() {
		return successeur;
	}

	public void setSuccesseur(int successeur) {
		this.successeur = successeur;
	}

	public String getNomLieu() {
		return nomLieu;
	}

	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}

	public String getDescLieu() {
		return descLieu;
	}

	public void setDescLieu(String descLieu) {
		this.descLieu = descLieu;
	}

	public double getLongLieu() {
		return longLieu;
	}

	public void setLongLieu(double longLieu) {
		this.longLieu = longLieu;
	}

	public double getLatLieu() {
		return latLieu;
	}

	public void setLatLieu(double latLieu) {
		this.latLieu = latLieu;
	}

	@Override
	public String toString() {
		return "Lieu [idLieu=" + idLieu + ", successeur=" + successeur
				+ ", nomLieu=" + nomLieu + ", descLieu=" + descLieu
				+ ", longLieu=" + longLieu + ", latLieu=" + latLieu + "]";
	}
}