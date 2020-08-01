
public class Classe {

	private int id;
	private String nom;
	private int capacite;



	public Classe(String nom, Integer capacite) {
		this.nom = nom;
		this.capacite = capacite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getCapacite() {
		return capacite;
	}

	public void setCapacite(Integer capacite) {
		this.capacite = capacite;
	}
	 
	
	 
}
