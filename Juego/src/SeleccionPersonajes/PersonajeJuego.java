package SeleccionPersonajes;

import java.awt.Graphics2D;

public abstract class PersonajeJuego {

	protected ManejadorPersonajes mp;
	
	protected String personaje;
	
	public abstract void inicia();
	public abstract void actualiza();
	public abstract void dibuja(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}