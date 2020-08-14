package EstadosDeJuego;

import java.awt.Graphics2D;

import Audio.Reproductor;
import SeleccionConexion.ManejadorConexion;
import SeleccionPersonajes.ManejadorPersonajes;

public class ManejadorEstados {
	
	private EstadoJuego[] estadosJuego;
	private int estadoActual;
	
	public static final int MENU = 0;
	public static final int JUGAR = 1;
	public static final int TUTORIAL = 2;
	public static final int AYUDA = 3;
	
	public static final int TOTALESTADOS = 4;
	
	private Reproductor bgMusica;
	private boolean reproduciendo;

	public ManejadorEstados() {
		
		estadosJuego = new EstadoJuego[TOTALESTADOS];
		
		reproduciendo = false;
	
		estadoActual = MENU;
		cargaEstado(estadoActual);
		
	}
	
	private void cargaEstado(int estado) {
		if(estado == MENU) {
			estadosJuego[estado] = new EstadoMenu(this);
			reproducirMusica();
		}
		if(estado == JUGAR) {
			estadosJuego[estado] = new ManejadorConexion(this);
		}
		if(estado == TUTORIAL) {
			estadosJuego[estado] = new ManejadorPersonajes(this);
		}
		if(estado == AYUDA) {
			estadosJuego[estado] = new EstadoAyuda(this);
		}
	}
	
	private void reproducirMusica() {
		if(!reproduciendo) {
		bgMusica = new Reproductor("/Sound/Background/Opening.wav");
		bgMusica.play(true);
		reproduciendo = true;
		}
	}
	
	public void quitaMusica() {
		bgMusica.stop();
		bgMusica.close();
		reproduciendo =false;
	}
	
	private void quitaEstado(int estado) {
		estadosJuego[estado] = null;
	}
	
	public void setEstado(int estado) {
		quitaEstado(estadoActual);
		estadoActual = estado;
		cargaEstado(estadoActual);
	}
	
	public void actualiza() {
		try {
			estadosJuego[estadoActual].actualiza();
		} catch(Exception e) {}
	}
	
	public void dibuja(Graphics2D g) {
		try {
			estadosJuego[estadoActual].dibuja(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		estadosJuego[estadoActual].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		estadosJuego[estadoActual].keyReleased(k);
	}
}
