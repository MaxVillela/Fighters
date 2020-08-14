package SeleccionDeMapas;

import Audio.Reproductor;
import SeleccionPersonajes.ManejadorPersonajes;
import SeleccionPersonajes.PersonajeJuego;
import Util.Cronometro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import Audio.Reproductor;
import MapaTile.Background;
import MapaTile.MapaTile;
import Mejoras.Energia;
import Mejoras.Salud;
import Principal.PanelJuego;
import Enemigos.Zombie;
import Entidad.*;

public class MapaTutorial extends PersonajeJuego{
	
	private MapaTile mapa;
	private HashMap<String,Reproductor> sfx;
	private Background bg;
	
	private ArrayList<Zombie> enemigos;
	private ArrayList<Explosion> explosiones;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Mejora> mejoras;
	private ArrayList<HUD> jugadoresHUD;
	private Reproductor bgMusica;
	
	private String tiempo;
	private String enemigosFaltantes;
	private Font fontTextos;
	private Cronometro cronometro;
	
	private final int id = 0;
	
	public MapaTutorial(ManejadorPersonajes mp, String personaje) {
		this.personaje = personaje;
		this.mp = mp;
		inicia();
	}

	public void inicia() {
		mapa = new MapaTile(32);
		mapa.cargaTiles("/TileSet/TileSet.gif");
		mapa.cargaMapa("/Mapas/Tutorial.map");
		mapa.setPosicion(0, 0);
		mapa.setIntercalado(0.07);
		
		bg = new Background("/Background/Background.gif",0.1);
		
		generaJugadores();
		generaEnemigos();
		generaMejoras();
		generaHUD();

		explosiones = new ArrayList<Explosion>();
		
		bgMusica = new Reproductor("/Sound/Background/Tutorial.wav");
		bgMusica.play(true);
		
		sfx = new HashMap<>();
		sfx.put("Tiempo",new Reproductor("/Sound/Sfx/Tiempo.wav"));
		
		cronometro = new Cronometro();
		tiempo = String.format("%d", cronometro.getTiempo());
		enemigosFaltantes = String.format("%d",enemigos.size());
		
		try {
		
		Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font3.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTextos = lfont.deriveFont(18f);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void generaJugadores() {
		jugadores = new ArrayList<Jugador>();
		
		Jugador j;
		Point[] points = new Point[] {
				new Point(790, 50),
				
			};
		
		for(int i = 0; i < points.length; i++) {
			if(i != id)
				j = new Jugador(mapa,"Skull");
			else
				j = new Jugador(mapa,personaje);
		
			j.setPosicion(points[i].x, points[i].y);
			jugadores.add(j);
		}
		
	}
	
	private void generaEnemigos(){
		enemigos = new ArrayList<Zombie>();
		
		Zombie z;
		Point[] points = new Point[] {
				new Point(150, 100),
				new Point(500, 100),
				new Point(1000, 100),
				new Point(1300, 100),
				new Point(220, 100),
				new Point(660, 100),
				new Point(1100, 100),
				new Point(300, 100) 
			};
		for(int i = 0; i < points.length; i++) {
			z = new Zombie(mapa);
			z.setPosicion(points[i].x, points[i].y);
			enemigos.add(z);
		}
	
	}
	
	private void generaMejoras() {
		mejoras = new ArrayList<Mejora>();
		
		Salud s; 
			Point[] points = new Point[] {
				new Point(280, 100),
				new Point(800, 100),
				new Point(1300, 100)
			};
		for(int i = 0; i < points.length; i++) {
				s = new Salud(mapa);
				s.setMovimiento(true);
				s.setPosicion(points[i].x, points[i].y);
				mejoras.add(s);
			}

		Energia e; 
			 points = new Point[] {
				new Point(340, 100),
				new Point(750, 100),
				new Point(1500, 100)
			};
			
			for(int i = 0; i < points.length; i++) {
				e = new Energia(mapa);
				e.setMovimiento(true);
				e.setPosicion(points[i].x, points[i].y);
				mejoras.add(e);
			}
	}
	
	private void generaHUD() {
		jugadoresHUD = new ArrayList<HUD>();
		
		HUD hud;
		
		for(int i = 0; i < jugadores.size(); i++) {
			if(i == id) {
				hud = new HUD(jugadores.get(i),personaje,true);
			jugadoresHUD.add(hud);
			}
		}
		
	}
	
	private void actualizaPantalla() {
		if(Integer.parseInt(tiempo) != cronometro.getTiempo())
			tiempo = String.format("%d",cronometro.getTiempo());
		if(Integer.parseInt(enemigosFaltantes) != enemigos.size()) {
			enemigosFaltantes = String.format("%d",enemigos.size());
		}
	}
	
	private void revisaEnemigos() {
		if(enemigos.size() == 0) {
			Jugador j = jugadores.get(id);
			
			mp.setMensajeFinal(String.format("%s:Ganaste:%d:%d:%s:%d:%d",
					tiempo,
					j.getVida(),
					j.getMaxVida(),
					personaje,
					j.getDisparo(),
					j.getMaxDisparo()));
			
			terminarJuego();
		}
	}
	
	private void revisaJugador() {
		Jugador j = jugadores.get(id);
		if(jugadores.get(id).getMuerto()) { 
			
			mp.setMensajeFinal(String.format("%s:Moriste:%d:%d:%s:%d:%d",
					tiempo,
					j.getVida(),
					j.getMaxVida(),
					personaje,
					j.getDisparo(),
					j.getMaxDisparo()));
			
			terminarJuego(); 
		}
	}

	public void actualiza() {
		actualizaPantalla();
		revisaJugador();
		for(int i = 0; i<jugadores.size();i++) {
			Jugador j = jugadores.get(i);
		
			j.revisaAtaqueJugador(jugadores,i);
			j.revisaAtaque(enemigos);
			j.revisaMejora(mejoras,i);
			j.actualiza();
			}
		
		mapa.setPosicion(
				PanelJuego.ANCHURA / 2 - jugadores.get(id).getx(),
				PanelJuego.ALTURA / 2 - jugadores.get(id).gety()
			);

		bg.setPosicion(mapa.getx(),mapa.gety());
		

		for(int i = 0; i< enemigos.size(); i++) {
			Enemigo e = enemigos.get(i);
			e.actualiza();
			if(enemigos.size()>0) {
				if(e.estaMuerto()) {
					enemigos.remove(i);
					i--;
					explosiones.add(
							new Explosion((int)e.getx(),(int)e.gety()));
					}
				}
			else {
				System.out.println("NO");
				//terminarJuego();
			}
		}
		
		revisaEnemigos();
		
			for(int i = 0; i<explosiones.size();i++) {
				explosiones.get(i).actualiza();
				if(explosiones.get(i).getRemover()) {
					explosiones.remove(i);
					i--;
				}
			}
			
			for(int i = 0; i< mejoras.size(); i++) {
				mejoras.get(i).actualiza();
				if(mejoras.get(i).estaRemovido()) {
					mejoras.remove(i);
					i--;
				}
			}
	}
	
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);
		
		mapa.dibuja(g);
		
		for(int i = 0; i<jugadores.size(); i++)
			jugadores.get(i).dibuja(g);
		
		for(int i = 0; i<enemigos.size(); i++) {
			enemigos.get(i).dibuja(g);
		}
		
		for(int i = 0; i<explosiones.size(); i++) {
			explosiones.get(i).setPosicionMapa(
				(int)mapa.getx(), (int)mapa.gety());
			explosiones.get(i).dibuja(g);
		}
		
		for(int i = 0; i<mejoras.size(); i++) {
			mejoras.get(i).dibuja(g);
		}
		
		for(int i = 0; i<jugadoresHUD.size(); i++) {
			if(i == id)
				jugadoresHUD.get(i).dibuja(g);
		}
		
		g.setFont(fontTextos);
		g.setColor(Color.WHITE);
		g.drawString("Tiempo:",500,30);
		g.drawString(tiempo,600,30);
		
		g.drawString("Zombies:",500,60);
		g.drawString(enemigosFaltantes,600,60);
		
		g.setColor(Color.GRAY);
		g.drawString("Destruye a todos los zombies",140,350);
	}
	
	private void terminarJuego() {
		sfx.get("Tiempo").play(false);
		cronometro.terminaTiempo();
		bgMusica.stop();
		bgMusica.close();
		mp.setPersonaje(ManejadorPersonajes.MENUINFO);
	}
	
	private void cerrarJuego() {
		bgMusica.stop();
		bgMusica.close();
		mp.setPersonaje(ManejadorPersonajes.SALIR);
	}
	
	public void keyPressed(int k) {
		
		Jugador jugador = jugadores.get(id);
		
		if(k == KeyEvent.VK_A) jugador.setIzquierda(true);
		if(k == KeyEvent.VK_D) jugador.setDerecha(true);
		if(k == KeyEvent.VK_W) jugador.setArriba(true);
		if(k == KeyEvent.VK_S) jugador.setAbajo(true);
		if(k == KeyEvent.VK_J) jugador.setSaltando(true);
		if(k == KeyEvent.VK_I) jugador.setPlaneando(true);
		if(k == KeyEvent.VK_K) jugador.setGolpeando();
		if(k == KeyEvent.VK_L) jugador.setDisparando();
			
		if(k == KeyEvent.VK_ESCAPE){
			cerrarJuego();
		}
	}
	
	public void keyReleased(int k) {
		
		Jugador jugador = jugadores.get(id);
		
		if(k == KeyEvent.VK_A) jugador.setIzquierda(false);
		if(k == KeyEvent.VK_D) jugador.setDerecha(false);
		if(k == KeyEvent.VK_W) jugador.setArriba(false);
		if(k == KeyEvent.VK_S) jugador.setAbajo(false);
		if(k == KeyEvent.VK_J) jugador.setSaltando(false);
		if(k == KeyEvent.VK_I) jugador.setPlaneando(false);
	}

}
