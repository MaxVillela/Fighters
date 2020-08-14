package SeleccionPersonajes;

import java.awt.Graphics2D;
import EstadosDeJuego.EstadoJuego;
import EstadosDeJuego.ManejadorEstados;
import SeleccionDeMapas.MapaTutorial;

public class ManejadorPersonajes extends EstadoJuego {
	
	private PersonajeJuego[] personajes;
	private int personajeActual;
	
	public static final int WOLF = 0;
	public static final int SKULL = 1;
	public static final int FLOW = 2;
	public static final int MENU = 3;
	public static final int MENUINFO = 4;
	public static final int SALIR = 5;
	
	public static final int TOTALPERSONAJES = 5;
	
	private String mensajeFinal;
	
	public ManejadorPersonajes(ManejadorEstados me) {
		this.me = me;
		
		personajes = new PersonajeJuego[TOTALPERSONAJES];
		personajeActual = MENU;
		cargaPersonaje(personajeActual);
	}
	
	private void cargaPersonaje(int personaje) {
		if(personaje == MENU) {
			personajes[personaje] = new MenuPersonaje(this);
		}
		if(personaje == WOLF) {
			personajes[personaje] = new MapaTutorial(this,"Wolf");
			me.quitaMusica();
		}
		if(personaje == SKULL) {
			personajes[personaje] = new MapaTutorial(this,"Skull");
			me.quitaMusica();
		}
		if(personaje == FLOW) {
			personajes[personaje] = new MapaTutorial(this,"Flow");
			me.quitaMusica();
		}
		if(personaje == MENUINFO) {
			personajes[personaje] = new MenuInfo(this);
		}
		if(personaje == SALIR) {
			me.setEstado(ManejadorEstados.MENU);
		}
	}
	
	private void quitaPersonaje(int personaje) {
		personajes[personaje] = null;
	}
	
	public void setPersonaje(int personaje) {
		quitaPersonaje(personajeActual);
		personajeActual = personaje;
		cargaPersonaje(personajeActual);
	}
	
	public void inicia() {}
	
	public void actualiza() {
		try {
			personajes[personajeActual].actualiza();
		} catch(Exception e) {}
	}
	
	public void dibuja(Graphics2D g) {
		try {
			personajes[personajeActual].dibuja(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		personajes[personajeActual].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		personajes[personajeActual].keyReleased(k);
	}

	public void setMensajeFinal(String m) { mensajeFinal = m; }
	public String getMensajeFinal() { return mensajeFinal; } 

}
