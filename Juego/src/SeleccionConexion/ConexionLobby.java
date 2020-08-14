package SeleccionConexion;

import Audio.Reproductor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import MapaTile.Background;
import Red.Cliente;


public class ConexionLobby extends TipoConexion{

	private Background bg;
	private HashMap<String,Reproductor> sfx;	
	
	private ArrayList<BufferedImage> imagenPersonajes;
		
	private BufferedImage seleccion;
	private BufferedImage noSeleccion;
	
	private static final int WOLF = 0;
	private static final int SKULL = 1;
	//private static final int FLOW = 2;
	
	private int opcionActual = 0;
	private String[] opciones = {
			"Wolf",
			"Skull",
			"Flow"
	};
	
	private Color colorTitulo;
	private Font fontTitulos;
	private Font fontTextos;
	
	//Cliente 
	private Cliente cliente;
	private boolean estadoCliente;
	private boolean seleccionRealizada = false;
	private int idUser;
	
	//Todos los clientes
	private String[] usuariosPersonajes;
	private int totalJugadores;
	
	//Grafico
	private String tiempo;
	private String usuariosConectados;
	
	public ConexionLobby(ManejadorConexion mc,String ip,int puerto) {
		this.mc = mc;
		estadoCliente = true;
		tiempo = "";
		usuariosConectados = "";
		
		
		try {
			bg = new Background("/Background/Personaje.gif",1);
			bg.setVector(-0.1,0);
			
			seleccion = ImageIO.read(
					getClass().getResourceAsStream(
							"/Botones/Seleccion.gif"
						)
					);
			
			noSeleccion = ImageIO.read(
					getClass().getResourceAsStream(
							"/Botones/NoSeleccion.gif"
						)
					);
			
			imagenPersonajes = new ArrayList<BufferedImage>();
			agregarImagenes();
			
			colorTitulo = new Color(128,0,0);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			

			fontTitulos = lfont.deriveFont(18f);
			
			lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font3.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTextos = lfont.deriveFont(21f);
			
			cliente = new Cliente(this,ip,puerto);
			
			sfx = new HashMap<>();
			sfx.put("Elegir",new Reproductor("/Sound/Sfx/EligePersonaje.wav"));
			sfx.put("Boton",new Reproductor("/Sound/Sfx/Boton.wav"));
			sfx.get("Elegir").play(false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public void inicia() {}
	
	public void actualiza(){
		if(tiempo.equals("6")){
			seleccionRealizada = true;
		}
	}
	
	private void agregarImagenes() {
		try {
		
		String path; 
			
		for(int i = 0; i<3; i++) {
			if(i == WOLF)
				path = "/Sprites/HUD/WolfFull.gif";
			else if(i == SKULL)
				path = "/Sprites/HUD/SkullFull.gif";
			else
				path = "/Sprites/HUD/FlowFull.gif";
		
		
			BufferedImage img = ImageIO.read(
				getClass().getResourceAsStream(
					path
				)
			);
			
			imagenPersonajes.add(img); 
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Es el jugador seleccionado en el lobby
	private void cargaSeleccionJugador(String personaje) {
		this.cliente.enviarServidor(
				String.format("OP_Seleccion:%s",personaje));
		seleccionRealizada = true;
	}
	
	//Flujo de datos para que recibe del servidor
	public void flujoDatosRecibidos(String msg){
		this.mc.flujoDatosRecibidos(msg);
	}
	
	public void flujoUnico(String msg) {
		this.mc.flujoUnico(msg);
	}
	
	//Flujo de datos para enviar al servidor
	public void flujoDatosEnviados(String msg) {
		this.cliente.enviarServidor(msg);
	}
	
	//Cierra el lobby en caso de el servidor se desconecte
	public void cierraLobby() {
		sfx.get("Elegir").stop();
		sfx.get("Boton").stop();
		this.cliente.cierra();
		this.estadoCliente = false;
	}
	
	//Carga el juego nuevo
	public void juegoNuevo() {
		mc.setMenu(ManejadorConexion.JUEGO);
	}
	
	public void setIdCliente(int i) {
		this.idUser = i; 
	}
	
	//Mostrar el tiempo de espera en el lobby
	public void muestraTiempoEspera(String t,String u) {
		if(!tiempo.equals(t))
			tiempo = t;
		if(!usuariosConectados.equals(u))
		   usuariosConectados = u;
	}
	
	//Carga todos los jugadores
	public void cargaJugadores(String msg) {
		String[] data = msg.split(":");
		totalJugadores = Integer.parseInt(data[1]);
		
		usuariosPersonajes = new String[totalJugadores];
		
		for(int j = 0; j<totalJugadores; j++) {
			usuariosPersonajes[j] = data[j+2]; 
		}
	}
	
	public boolean getEstadoCliente() {  return estadoCliente; }
	public String getPersonaje(int i) {  return usuariosPersonajes[i]; }
	public int getId() { return idUser; }
	public int getTotalJugadores() { return totalJugadores; }
		
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);

		g.setFont(fontTitulos);
		g.setColor(Color.WHITE);
		g.drawString("JUEGO EN LINEA",250,40);
		g.setColor(colorTitulo);
		g.drawString("Personajes",250,70);
		
		for(int i = 0; i < opciones.length; i++) {
			if(i == opcionActual) {
				g.setColor(new Color(67,160,227));
				g.drawImage(seleccion,210,120+i*40,null);
			}
			else {
				g.drawImage(noSeleccion,210,120+i*40,null);
				g.setColor(new Color(217,192,57));
			}
			g.drawString(opciones[i], 240, 140 + i * 40);
		}
		
		g.setFont(fontTextos);
		g.setColor(new Color(67,160,227));
		g.drawString(tiempo,500,300);
		
		g.setColor(Color.WHITE);
		g.drawString(usuariosConectados,100,300);
		
		g.drawImage(imagenPersonajes.get(opcionActual),400,100,null);
		
		
	}
	
	private void selecciona() {
		if(opcionActual == 0) {
			cargaSeleccionJugador("Wolf");
		}
		if(opcionActual == 1) {
			cargaSeleccionJugador("Skull");	
		}
		if(opcionActual == 2) {
			cargaSeleccionJugador("Flow");		
		}
	}
	
	public void keyPressed(int k) {
		if(!seleccionRealizada) {
		if(k == KeyEvent.VK_ENTER ||  k == KeyEvent.VK_J) {
			selecciona();
		}
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			sfx.get("Boton").play(false);
			opcionActual--;
			if(opcionActual == -1) {
				opcionActual = opciones.length -1;
			}
		}
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			sfx.get("Boton").play(false);
			opcionActual++;
			if(opcionActual == opciones.length) {
				opcionActual = 0;
				}
			}
		}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_K) {
			cierraLobby();
			mc.setMenu(ManejadorConexion.SALIR);
			}
		
	}
	
	public void keyReleased(int k) {}



}

	
