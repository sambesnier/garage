package models;

import java.util.ArrayList;

public class Facture {
	private long _id_client;
	private ArrayList<Prestation> _prestas = new ArrayList<Prestation>();
	private long _id_voiture;
	private long _id_rdv;
	
	public Facture(long client, ArrayList<Prestation> prestas, long voiture) {
		set_id_client(client);
		set_prestas(prestas);
		set_id_voiture(voiture);
	}
	
	public long get_id_client() {
		return _id_client;
	}
	public void set_id_client(long _id_client) {
		this._id_client = _id_client;
	}
	public ArrayList<Prestation> get_prestas() {
		return _prestas;
	}
	public void set_prestas(ArrayList<Prestation> _prestas) {
		this._prestas = _prestas;
	}
	public long get_id_voiture() {
		return _id_voiture;
	}
	public void set_id_voiture(long _id_voiture) {
		this._id_voiture = _id_voiture;
	}
	public long get_id_rdv() {
		return _id_rdv;
	}
	public void set_id_rdv(long _id_rdv) {
		this._id_rdv = _id_rdv;
	}
}
