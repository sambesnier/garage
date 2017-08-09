package models;

public class Prestation {
	private String _prestation;
	private int _duree;
	private double _montant;
	private long _id;
	
	public Prestation(String prestation, double montant, int duree, long id) {
		set_prestation(prestation);
		set_montant(montant);
		set_duree(duree);
		set_id(id);
	}
	
	public String get_prestation() {
		return _prestation;
	}
	public void set_prestation(String _prestation) {
		this._prestation = _prestation;
	}
	public int get_duree() {
		return _duree;
	}
	public void set_duree(int _duree) {
		this._duree = _duree;
	}
	public double get_montant() {
		return _montant;
	}
	public void set_montant(double _montant) {
		this._montant = _montant;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}
	
	
}
