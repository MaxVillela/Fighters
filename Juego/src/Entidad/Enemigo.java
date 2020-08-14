package Entidad;

import MapaTile.MapaTile;

public class Enemigo extends ObjetoMapa {
	
	protected int vida;
	protected int maxVida;
	protected boolean muerto;
	protected int dano;
	
	protected boolean retroceso;
	protected long retrocesoTimer;
	
	public Enemigo(MapaTile tm) {
		super(tm);
	}
	
	public boolean estaMuerto() { return muerto; }
	
	public int getDano() { return dano; }
	
	public void golpe(int dano) {
		if(muerto || retroceso) return;
		vida -= dano;
		if(vida < 0) vida = 0;
		if(vida == 0) muerto = true;
		retroceso = true;
		retrocesoTimer = System.nanoTime();
	}
	
	public void actualiza() {}
	
}
