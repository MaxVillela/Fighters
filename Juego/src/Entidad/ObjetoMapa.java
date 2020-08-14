package Entidad;

import java.awt.Rectangle;
import MapaTile.MapaTile;
import MapaTile.Tile;
import Principal.PanelJuego;

public abstract class ObjetoMapa {

	//Mapa Tile
	protected MapaTile mapaTile;
	protected int tamTile;
	protected double xmapa;
	protected double ymapa;

	//Posicion y Vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//Dimensiones
	protected int anchura;
	protected int altura;

	//Caja de Colision
	protected int clanchura;
	protected int claltura;
	
	//Colision
	protected int actualRen;
	protected int actualCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean arribaIzq;
	protected boolean arribaDer;
	protected boolean abajoIzq;
	protected boolean abajoDer;
	
	//Animacion
	protected Animacion animacion;
	protected int accionActual;
	protected int accionPrevia;
	protected boolean miraDer;
	
	//Movimiento
	protected boolean izquierda;
	protected boolean derecha;
	protected boolean arriba;
	protected boolean abajo;
	protected boolean saltando;
	protected boolean cayendo;
	
	//Atributos de movimiento
	protected double velMover;
	protected double maximoMover;
	protected double velAlto;
	protected double velCaida;
	protected double maxVelCaida;
	protected double saltoInicial;
	protected double altoVelSalto;	
		
	public ObjetoMapa(MapaTile mt) {
		mapaTile = mt;
		tamTile = mt.getTamTile();
	}
	
	//Si existion una colision entre objetos
	public boolean intersecta(ObjetoMapa o) {
		Rectangle r1 = getRectangulo();
		Rectangle r2 = o.getRectangulo();
		
		return r1.intersects(r2);
	}
	
	//Devuelve un rectangulo para evaluar	
	public Rectangle getRectangulo() {
		return new Rectangle(
				(int)x -clanchura,
				(int)y - claltura,
				clanchura,
				claltura
			);
	}
	

	//Calcula la posiciones del objeto 
	public void calcularEsquinas(double x, double y) {
		//Columas alrededor del objeto
		int izqTile = (int)(x - clanchura / 2) / tamTile;
		int derTile = (int)(x + clanchura / 2 -1) / tamTile;
		int arribaTile = (int)(y - claltura / 2) / tamTile;
		int abajoTile = (int)(y + claltura / 2 -1) / tamTile;
		
		int ai = mapaTile.getTipo(arribaTile, izqTile);
		int ad = mapaTile.getTipo(arribaTile,derTile);
		int bi = mapaTile.getTipo(abajoTile, izqTile);
		int bd = mapaTile.getTipo(abajoTile,derTile);
		
		arribaIzq = ai == Tile.BLOQUEADO;
		arribaDer = ad == Tile.BLOQUEADO;
		abajoIzq = bi == Tile.BLOQUEADO;
		abajoDer = bd == Tile.BLOQUEADO;
		
	}
	
	//Revisa  el tipo de tile,Bloqueado o Libre
	public void revisaColisionMapa() {
		actualCol = (int)x / tamTile;
		actualRen = (int)y / tamTile;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calcularEsquinas(x, ydest);
		if(dy < 0) {
			if(arribaIzq || arribaDer) {
				dy = 0;
				ytemp = actualRen * tamTile + claltura / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			if(abajoIzq || abajoDer) {
				dy = 0;
				cayendo = false;
				ytemp = (actualRen + 1) * tamTile - claltura / 2;
			}
			else {
				ytemp += dy;
			}
		}
		
		calcularEsquinas(xdest, y);
		if(dx < 0) {
			if(arribaIzq || abajoIzq) {
				dx = 0;
				xtemp = actualCol * tamTile + clanchura / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(arribaDer || abajoDer) {
				dx = 0;
				xtemp = (actualCol + 1) * tamTile - clanchura / 2;
			}
			else {
				xtemp += dx;
			}
		}
		
		if(!cayendo) {
			calcularEsquinas(x, ydest + 1);
			if(!abajoIzq && !abajoDer) {
				cayendo = true;
			}
		}
	}
	
	public void setx(double x) { this.x = x; }
	public void sety(double y) { this.y = y; }
	
	public double getx() { return x; }
	public double gety() { return y; }
	public int getAnchura() { return anchura; }
	public int getAltura() { return altura; }
	public int getClAnchura() { return clanchura; }
	public int getClAltura() { return claltura; }
	
	//Posicion a nivel de Pixel
	public void setPosicion(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	//Posicion a nivel del mapa
	public void setPosicionMapa() {
		xmapa = mapaTile.getx();
		ymapa = mapaTile.gety();
	}
	
	public void setIzquierda(boolean b) { izquierda = b; }
	public void setDerecha(boolean b) { derecha = b; }
	public void setArriba(boolean b) { arriba = b; }
	public void setAbajo(boolean b) { abajo = b; }
	public void setSaltando(boolean b) { saltando = b; }	

	//Determina si un objeto no esta en pantalla
	public boolean NoEnPantalla() {
		return x + xmapa + anchura < 0 ||
				x + xmapa - anchura > PanelJuego.ANCHURA ||
				y + ymapa + altura < 0 ||
				y + ymapa - altura > PanelJuego.ALTURA;
	}
	
	public void dibuja(java.awt.Graphics2D g) {
		if(miraDer) {
			g.drawImage(
				animacion.getImagen(),
				(int)(x + xmapa - anchura / 2),
				(int)(y + ymapa - altura / 2),
				null
			);
		}
		else {
			g.drawImage(
				animacion.getImagen(),
				(int)(x + xmapa - anchura / 2 + anchura),
				(int)(y + ymapa - altura / 2),
				-anchura,
				altura,
				null
			);
		}
	}
	
}
