package Entidad;

import MapaTile.MapaTile;

public class Mejora extends ObjetoMapa {
	
	protected int vida; 
	protected int maxVida;
	protected boolean remover; 
	protected int cantMejorar;
	protected boolean movimiento;
	
	protected boolean retroceso;
	protected long retrocesoTimer;
	
	protected String tipoMejora;
		
	public Mejora(MapaTile tm) {
		super(tm);
		movimiento = false;		
	}
	
	public void getSiguientePosicion() {
		if(movimiento) {
		if(izquierda) {
			dx -= velMover;
			if(dx < -maximoMover) {
				dx = -maximoMover;
			}
		}
		else if(derecha ) {
			dx += velMover;
			if(dx > maximoMover) {
				dx = maximoMover;
				}
			}
		}
			
			// falling
		if(cayendo) {
			dy += velCaida;
		}
	}
	
	public void actualiza() {
		getSiguientePosicion();
		revisaColisionMapa();
		setPosicion(xtemp,ytemp);
		
		if(retroceso) {
			long restante =
					(System.nanoTime() - retrocesoTimer) / 1000000;
				if(restante > 1000) {
					retroceso = false;
				}
		}
		
		if(movimiento) {
		
		if(derecha && dx == 0) {
			derecha = false;
			izquierda = true;
			miraDer = false;
		}
		else if(izquierda && dx == 0) {
			derecha = true;
			izquierda = false;
			miraDer = true;
			}
		}
		
		animacion.actualiza();
	}
	
	public boolean estaRemovido() { return remover; }
	
	public int getMejorado() { return cantMejorar; }
	
	public void quitarMejora() {
		if(remover || retroceso) return;

		if(vida == 0) remover = true;
		retroceso = true;
		retrocesoTimer = System.nanoTime();
	}
	
	public void setMovimiento(boolean movimiento) {this.movimiento = movimiento;}
	
	public String getTipoMejora() { return tipoMejora; }
	
}
