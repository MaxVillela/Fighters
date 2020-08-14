package SeleccionDeMapas;

import Audio.Reproductor;

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
import PlayerData.FormatoMapa;
import Principal.PanelJuego;
import SeleccionConexion.*;
import Entidad.*;

public class Nivel1Mapa extends TipoConexion{
	
	private MapaTile mapa;
	private Background bg;
	private HashMap<String,Reproductor> sfx;
	
	private ArrayList<Jugador> jugadores;
	private ArrayList<Mejora> mejoras;
	private ArrayList<HUD> jugadoresHUD;
	private Reproductor bgMusica;
	
	private ArrayList<String[]> movimientos;
	private String[] userMov;
	
	private String personaje;
	private ConexionLobby lobby;
	private String tiempo; 
	
	private int id;
	private int totalJugadores;
	private Font fontTextos;
	private boolean juegoActivo;
	
	public Nivel1Mapa(ManejadorConexion mc,ConexionLobby lobby){
		id = lobby.getId();	
		personaje = lobby.getPersonaje(id);
		totalJugadores = lobby.getTotalJugadores();
		
		this.mc = mc;	
		this.lobby = lobby;
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
		//generaEnemigos();
		generaMejoras();
		generaHUD();

		//explosiones = new ArrayList<Explosion>();
		
		bgMusica = new Reproductor("/Sound/Background/Tutorial.wav");
		bgMusica.play(true);
		
		sfx = new HashMap<>();
		sfx.put("Ganaste",new Reproductor("/Sound/Sfx/Ganaste.wav"));
		sfx.put("Perdiste",new Reproductor("/Sound/Sfx/Perdiste.wav"));
		
		tiempo = "";
		juegoActivo = true;
		
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
		this.movimientos = new ArrayList<String[]>();
		
		Jugador j;
		FormatoMapa fp = new FormatoMapa();
		String[] s = new String[10];
		userMov = new String[6];
		
		for(int k = 0; k<s.length; k++) {
			if(k<6) {
				s[k] = "false";
				userMov[k] = "false";
			}
			else {
				s[k] = "";
			}
		}
		
		for(int i = 0; i <totalJugadores; i++) {
			if(i != id) {
				j = new Jugador(mapa,lobby.getPersonaje(i));
			}
			else {
				j = new Jugador(mapa,personaje);
			}
				
			j.setPosicion(fp.getx(i), fp.gety(i));
			jugadores.add(j);
			
			movimientos.add(s); 
		}
	}
	
	private void generaMejoras() {
		mejoras = new ArrayList<Mejora>();
		
		Salud s; 
			Point[] points = new Point[] {
				new Point(280, 100),
				new Point(810, 100),
				new Point(1300, 100)
			};
		for(int i = 0; i < points.length; i++) {
				s = new Salud(mapa);
				s.setPosicion(points[i].x, points[i].y);
				mejoras.add(s);
			}
		

		Energia e; 
			 points = new Point[] {
				new Point(340, 100),
				new Point(750, 100),
				new Point(1500 , 100)
			};
			
			for(int i = 0; i < points.length; i++) {
				e = new Energia(mapa);
				e.setPosicion(points[i].x, points[i].y);
				mejoras.add(e);
			}
	}
	
	
	
	private void generaHUD() {
		jugadoresHUD = new ArrayList<HUD>();
		
		HUD hud;
		
		for(int i = 0; i < jugadores.size(); i++) {
			if(i != id)
				hud = new HUD(jugadores.get(i),lobby.getPersonaje(i),false);
			else
				hud = new HUD(jugadores.get(i),personaje,true);
			
			jugadoresHUD.add(hud);
		}
	}
	
	private void actualizarMovimientos(int i) {
		Jugador j = jugadores.get(i);
		String[] s = this.movimientos.get(i);
		
		String  x = String.format("%f", j.getx()),
				y = String.format("%f", j.gety()),
				vida = String.format("%d",j.getVida()),
				disparo = String.format("%d", j.getDisparo());
		
		String t = "true";
		
		//Jugador izquierda
		if(s[0].equals(t))
			j.setIzquierda(true);
		else
			j.setIzquierda(false);
		
		//Jugador derecha
		if(s[1].equals(t))
			j.setDerecha(true);
		else
			j.setDerecha(false);
		
		//Jugador salto
		if(s[2].equals(t)) 
			j.setSaltando(true);
		else
			j.setSaltando(false);
		
		//Jugador Planeando
		if(s[3].equals(t))
			j.setPlaneando(true);
		else
			j.setPlaneando(false);
		
		//Jugador Golpeando
		if(s[4].equals(t)) {
			if(!j.getGolpeando()) {
					j.setGolpeando();
				s[4] = "false";
			}
		}
		//Jugador disparando
		if(s[5].equals(t)) {
			if(!j.getDisparando()) {
					j.setDisparando();
				s[5] = "false";
			}
		}
		
		if(!s[6].equals(x)) {
			j.setx(Double.parseDouble(s[6]));
		}
		
		if(!s[7].equals(y)) {
			j.sety(Double.parseDouble(s[7]));
		}
		
		if(!s[8].equals(vida)) {
			j.setVida(Integer.parseInt(s[8]));
		}
		
		if(!s[9].equals(disparo)) {
			j.setDisparo(Integer.parseInt(s[9]));
		}
	}
	
	public void recibirServidor(String msg){
		if(juegoActivo) {
		String[] nuevo = msg.split(":");		
		int user = Integer.parseInt(nuevo[1]);
		String[] viejo = this.movimientos.get(user);
		
		for(int i = 0; i<viejo.length; i++){
				viejo[i] = nuevo[i+2];
			}
		}
	}
	
	private void enviarServidor() {
		if(juegoActivo) {
		Jugador j = jugadores.get(id);
		String formato = "";
		
		for(int i = 0; i<userMov.length; i++){
			 formato += userMov[i]+":";	
		}
		
		String atributos = String.format("%f:%f:%d:%d",j.getx(),
				j.gety(),j.getVida(),j.getDisparo()); 
		
		this.lobby.flujoDatosEnviados(String.format(
				"OP_Jugador:%d:%s%s",id,formato,atributos));
		}
	}
	
	private void enviarMensajeUnico(String msg){
			this.lobby.flujoDatosEnviados(msg);
	}
	
	public void recibirMensajeUnico(String msg) {

		String[] data = msg.split(":");
		String op = data[0];
		if(op.equals("OP_Muerto")) {
			eliminarJugador(Integer.parseInt(data[1]));
		}
		
		if(juegoActivo) {
		if(op.equals("OP_Tiempo")) {
			muestraTiempo(data[1]);
			}
		}
		
		if(op.equals("OP_TerminaJuego")) {
			cerrarJuego();
			}
	}
	
	private void muestraTiempo(String t) {
		if(!tiempo.equals(t))
			tiempo = t;
	}
	
	private void revisaUsuario() {
		Jugador j = jugadores.get(id);
		
		if(userMov[4].equals("true")) {
			if(!j.getGolpeando()) {
				userMov[4] = "false";
			}
		}
		//Jugador disparando
		if(userMov[5].equals("true")) {
			if(!j.getDisparando()) {
				userMov[5] = "false";
			}
		}
	}
	
	private void eliminarJugador(int i){
		if(i != id) {
			jugadores.remove(i);
			if(jugadores.size() == 1)
				terminarJuego(true);
		}
		else
			terminarJuego(false);
	}

	public void actualiza() {
		revisaUsuario();
		enviarServidor();
		
		for(int i = 0; i<jugadores.size();i++) {
			if(i != id)
				actualizarMovimientos(i);
			
			jugadores.get(i).actualiza();
			jugadores.get(i).revisaAtaqueJugador(jugadores,i);
		//	jugadores.get(i).revisaAtaque(enemigos);
			jugadores.get(i).revisaMejora(mejoras,i);
			
			
			if(jugadores.size()>1) {
				if(jugadores.get(i).getMuerto()) {
					if(i!= id) {
						enviarMensajeUnico(String.format("OP_Muerto:%d",i));
					}
				}
			}
		}
		
		mapa.setPosicion(
				PanelJuego.ANCHURA / 2 - jugadores.get(id).getx(),
				PanelJuego.ALTURA / 2 - jugadores.get(id).gety()
			);

		bg.setPosicion(mapa.getx(),mapa.gety());
		
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
		
		for(int i = 0; i<mejoras.size(); i++) {
			mejoras.get(i).dibuja(g);
		}
		
		for(int i = 0; i<jugadoresHUD.size(); i++) {
				jugadoresHUD.get(i).dibuja(g);
		}
		
		g.setFont(fontTextos);
		g.setColor(Color.WHITE);
		g.drawString("Tiempo:",500,30);
		g.drawString(tiempo,600,30);
	}
	
	private void terminarJuego(boolean ganar){
		juegoActivo = false;
		terminarMusica();
		
		if(ganar) {
			System.out.println("ganar");
			sfx.get("Ganaste").play(false);
				mc.setMensajeFinal(String.format("Ganaste:%s",
						lobby.getPersonaje(id)
						));
						mc.setMenu(ManejadorConexion.MENUFINAL);
		}else {	
			System.out.println("perder");
			sfx.get("Perdiste").play(false);
			mc.setMensajeFinal(String.format("Perdiste:%s",
					lobby.getPersonaje(id)
					));
					mc.setMenu(ManejadorConexion.MENUFINAL);
		}
		
	}
	
	private void cerrarJuego(){
		terminarMusica();
		mc.setMenu(ManejadorConexion.SALIR);
	}
	
	private void terminarMusica() {
		bgMusica.stop();
		bgMusica.close();
	}
	
	public void keyPressed(int k) {
		Jugador j = jugadores.get(id);
		
		if(k == KeyEvent.VK_A) {
			userMov[0] = "true";
			j.setIzquierda(true);
		}
		if(k == KeyEvent.VK_D) {
			userMov[1] = "true";
			j.setDerecha(true);
		}
		
		if(k == KeyEvent.VK_J) {
			userMov[2] = "true";
			j.setSaltando(true);
		}
		if(k == KeyEvent.VK_I) {
			userMov[3] = "true";
			j.setPlaneando(true);
		}
		if(k == KeyEvent.VK_K) {
			userMov[4] = "true";
			j.setGolpeando();
		}
		
		if(k == KeyEvent.VK_L) {
			userMov[5] = "true";	
			j.setDisparando();
		}
		
		if(k == KeyEvent.VK_ESCAPE){
			cerrarJuego();
		}
	}
	
	public void keyReleased(int k) {	
		Jugador j = jugadores.get(id);
		
		if(k == KeyEvent.VK_A) {
			userMov[0] = "false";
			j.setIzquierda(false);
		}
		if(k == KeyEvent.VK_D) {
			userMov[1] = "false";
			j.setDerecha(false);
		}
		if(k == KeyEvent.VK_J) {
			userMov[2] = "false";
			j.setSaltando(false);
		}
		if(k == KeyEvent.VK_I){
			userMov[3] = "false";
			j.setPlaneando(false);
		}
	}
}
