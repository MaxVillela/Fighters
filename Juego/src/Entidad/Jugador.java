package Entidad;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import Enemigos.Zombie;
import MapaTile.MapaTile;
import PlayerData.FormatoPersonaje;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Jugador extends ObjetoMapa {
	
	//Atributos Juego
	private int vida;
	private int maxVida;
	private int disparo;
	private int maxDisparo;
	private boolean muerto;
	private boolean retroceso;
	private long retrocesoTimer;
	
	//Fuego
	private boolean disparando;
	private int disparoCosto;
	private int disparoDano;
	private ArrayList<Municion> balas;
	
	//Golpe
	private boolean golpeando;
	private int golpeDano;
	private int golpeAlcance;
	
	//Planear
	private boolean planeando;
	
	//Animacion
	private ArrayList<BufferedImage[]> sprites;
	private int[] numFrames;
	
	//Acciones Animaciones
	private static final int IDLE = 0;
	private static final int CAMINAR = 1;
	private static final int SALTAR = 2;
	private static final int CAER = 3;
	private static final int PLANEAR = 4;
	private static final int GOLPEAR = 5;
	private static final int DISPARAR = 6;
	
	private FormatoPersonaje fp; 
	
	public Jugador(MapaTile mt,String personaje) {
		super(mt);
		
		fp = new FormatoPersonaje(personaje);
		cargaFrames();
		
		anchura = fp.getAltura();
		altura = fp.getAnchura();
		clanchura = 20;
		claltura = 20;
		
		velMover = 0.3;
		maximoMover = fp.getMaximoMover();
		velAlto = 0.4;
		velCaida = 0.15;
		maxVelCaida = 4.0;
		saltoInicial = -4.8;
		altoVelSalto = 0.3;
		
		miraDer = true;
		
		vida = maxVida = fp.getMaxVida();
		disparo = maxDisparo = fp.getMaxDisparo() * 100;
		
		disparoCosto = 200;
		disparoDano = 5;
		balas = new ArrayList<Municion>();
		
		golpeDano = fp.getGolpeDano();
		golpeAlcance = fp.getGolpeAlcance();
		
		cargaSprites();
		
		animacion = new Animacion();
		accionActual = IDLE;
		animacion.setFrames(sprites.get(IDLE));
		animacion.setRetraso(400);	
	}
	
	
	private void cargaFrames() {
		numFrames = new int[7];
		
		for(int i = 0; i<7; i++)
			numFrames[i] = fp.getNumFrames(i);		
	}
	
	private void cargaSprites() {
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					fp.getPath()
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				BufferedImage[] bi =
						new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != DISPARAR && i != GOLPEAR) {
						bi[j] = spritesheet.getSubimage(
								j * anchura,
								i * altura,
								anchura,
								altura
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
								j * anchura * 2,
								i * altura,
								anchura * 2,
								altura
						);
					}
				}	

				sprites.add(bi);
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void golpe(int dano) {
		if(retroceso) return;
		vida -= dano;
		if(vida < 0) vida = 0;
		if(vida == 0) muerto = true;
		retroceso = true;
		retrocesoTimer = System.nanoTime();
	}
	
	public void mejora(String tipo, int mejora) {		
		
		if(tipo.equals("Salud")) {
			vida += mejora;
			if(vida>maxVida) vida = maxVida;
		}
		
		if(tipo.equals("Energia")) {
			disparo += mejora*100;
			if(disparo > maxDisparo) disparo = maxDisparo;
		}
		
	}
	
	public int getVida() { return vida; }
	public int getMaxVida() { return maxVida; }
	public int getDisparo()  { return disparo; }
	public int getMaxDisparo() { return maxDisparo; }
	
	public void setDisparando() { disparando = true; }
	public void setGolpeando() { golpeando = true; }
	public void setPlaneando(boolean b) { planeando = b; }
	
	public boolean getGolpeando() { return golpeando; }
	public boolean getDisparando() { return disparando; }	
	
	public boolean getMuerto() { return muerto; }
	
	public void setVida(int vida) { this.vida = vida;}
	public void setDisparo(int disparo) { this.disparo = disparo; }
	public void setMuerto(boolean muerto) { this.muerto = muerto; }
	
	public void revisaAtaque(ArrayList<Zombie> enemigos) {
		
		for(int i = 0; i<enemigos.size(); i++) {
			
			Enemigo e = enemigos.get(i);
			
			if(golpeando) {
				if(miraDer) {
					if(
						e.getx() > x &&
						e.getx() < x + golpeAlcance && 
						e.gety() > y - altura / 2 &&
						e.gety() < y + altura / 2
					){
						e.golpe(golpeDano);
					}
				}else {
					if(
							e.getx() < x &&
							e.getx() > x - golpeAlcance &&
							e.gety() > y - altura / 2 &&
							e.gety() < y + altura / 2
						) {
							e.golpe(golpeDano);
						}
				}
			}
			
			for(int j = 0; j < balas.size(); j++) {
				if(balas.get(j).intersecta(e)) {
					e.golpe(disparoDano);
					balas.get(j).setGolpe();;
					break;
				}
			}
			
			if(intersecta(e)) {
				golpe(e.getDano());
			}
		
		}
			
			
	}
	
public void revisaAtaqueJugador(ArrayList<Jugador> jugadores, int ID) {
		
		for(int i = 0; i<jugadores.size(); i++) {			
			Jugador e = jugadores.get(i);
			
			
			if(golpeando) {
				if(i != ID) {
				if(miraDer) {
					if(
						e.getx() > x &&
						e.getx() < x + golpeAlcance && 
						e.gety() > y - altura / 2 &&
						e.gety() < y + altura / 2
					){
						e.golpe(golpeDano);
					}
				}else {
					if(
							e.getx() < x &&
							e.getx() > x - golpeAlcance &&
							e.gety() > y - altura / 2 &&
							e.gety() < y + altura / 2
						) {
							if(i != ID)
							e.golpe(golpeDano);
						}
					}
				}
			}
			
			for(int j = 0; j < balas.size(); j++) {
				if(balas.get(j).intersecta(e)) {
					if(i != ID) {
					e.golpe(disparoDano);
					balas.get(j).setGolpe();;
					break;
					}
				}
			}
		}
			
			
	}

public void revisaMejora(ArrayList<Mejora> mejoras, int ID) {
	
	for(int i = 0; i<mejoras.size(); i++) {			
		Mejora e = mejoras.get(i);
		
		if(golpeando) {
			if(miraDer) {
				if(
					e.getx() > x &&
					e.getx() < x + golpeAlcance && 
					e.gety() > y - altura / 2 &&
					e.gety() < y + altura / 2
				){
					e.quitarMejora();
					mejora(e.getTipoMejora(),e.getMejorado());
				}
			}else {
				if(
						e.getx() < x &&
						e.getx() > x - golpeAlcance &&
						e.gety() > y - altura / 2 &&
						e.gety() < y + altura / 2
					) {
						e.quitarMejora();
						mejora(e.getTipoMejora(),e.getMejorado());
					}
				}
			}
		}
	}
	
	public void actualiza() { 
		// update position
			getSiguientePosicion();
			revisaColisionMapa();
			setPosicion(xtemp, ytemp);
			
			// check attack has stopped
			if(accionActual == GOLPEAR) {
				if(animacion.estaAnimacionEnUso()) golpeando = false;
			}
			if(accionActual == DISPARAR) {
				if(animacion.estaAnimacionEnUso()) disparando = false;
			}
				
			disparo += 1;
			if(disparo > maxDisparo) disparo = maxDisparo;
			if(disparando && accionActual != DISPARAR) {
				if(disparo > disparoDano) {
					disparo -= disparoCosto;
					Municion m = new Municion(mapaTile, miraDer);
					m.setPosicion(x, y);
					balas.add(m);
				}
			}
			
			for(int i = 0; i<balas.size(); i++) {
				balas.get(i).actualiza();
				if(balas.get(i).getRemover()) {
					balas.remove(i);
				}
			}
			
			// check done flinching
			if(retroceso) {
				long restante =
					(System.nanoTime() - retrocesoTimer) / 1000000;
				if(restante > 1000) {
					retroceso = false;
				}
			}
			
			// set animation
			if(golpeando) {
				if(accionActual != GOLPEAR) {
					accionActual = GOLPEAR;
					animacion.setFrames(sprites.get(GOLPEAR));
					animacion.setRetraso(60);
					anchura = fp.getAltura() * 2;
				}
			}
			else if(disparando) {
				if(accionActual != DISPARAR) {
					accionActual = DISPARAR;
					animacion.setFrames(sprites.get(DISPARAR));
					animacion.setRetraso(80);
					anchura = fp.getAltura() * 2;
				}
			}
			else if(dy > 0) {
				if(planeando) {
					if(accionActual != PLANEAR) {
						accionActual = PLANEAR;
						animacion.setFrames(sprites.get(PLANEAR));
						animacion.setRetraso(80);
						anchura = fp.getAltura();
					}
				}
				else if(accionActual != CAER) {
					accionActual = CAER;
					animacion.setFrames(sprites.get(CAER));
					animacion.setRetraso(100);
					anchura = fp.getAltura();
				}
			}
			else if(dy < 0) {
				if(accionActual != SALTAR) {
					accionActual = SALTAR;
					animacion.setFrames(sprites.get(SALTAR));
					animacion.setRetraso(-1);
					anchura = fp.getAltura();
				}
			}
			else if(izquierda || derecha) {
				if(accionActual != CAMINAR) {
					accionActual = CAMINAR;
					animacion.setFrames(sprites.get(CAMINAR));
					animacion.setRetraso(60);
					anchura = fp.getAltura();
				}
			}
			else {
				if(accionActual != IDLE) {
					accionActual = IDLE;
					animacion.setFrames(sprites.get(IDLE));
					animacion.setRetraso(100);
					anchura = fp.getAltura();
				}
			}
			
			animacion.actualiza();
			
			// set direction
			if(accionActual != GOLPEAR && accionActual != DISPARAR) {
				if(derecha) miraDer = true;
				if(izquierda) miraDer = false;
			}
	}
	
	//Determina la siguiente posicion del jugador por el teclado
	private void getSiguientePosicion() {
		// movement
				if(izquierda) {
					dx -= velMover;
					if(dx < -maximoMover) {
						dx = -maximoMover;
					}
				}
				else if(derecha) {
					dx += velMover;
					if(dx > maximoMover) {
						dx = maximoMover;
					}
				}
				else {
					if(dx > 0) {
						dx -= velAlto;
						if(dx < 0) {
							dx = 0;
						}
					}
					else if(dx < 0) {
						dx += velAlto;
						if(dx > 0) {
							dx = 0;
						}
					}
				}
				
				// cannot move while attacking, except in air
				if(
				(accionActual == GOLPEAR || accionActual == DISPARAR) &&
				!(saltando || cayendo)) {
					dx = 0;
				}
				
				// jumping
				if(saltando && !cayendo) {
					dy = saltoInicial;
					cayendo = true;
				}
				
				// falling
				if(cayendo) {
					
					if(dy > 0 && planeando) dy += velCaida * 0.1;
					else dy += velCaida;
					
					if(dy > 0) saltando = false;
					if(dy < 0 && !saltando) dy += altoVelSalto;
					
					if(dy > maxVelCaida) dy = maxVelCaida;
					
				}
				
	}

	public void dibuja(Graphics2D g) {
		setPosicionMapa();
		
		for(int i = 0; i<balas.size(); i++) {
			balas.get(i).dibuja(g);
		}
		
		//Si golpean al jugador
		if(retroceso) {
			long elapsed =
				(System.nanoTime() - retrocesoTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.dibuja(g);
	}
	
	
		
	

}
