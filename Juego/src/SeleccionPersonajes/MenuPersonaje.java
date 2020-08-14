package SeleccionPersonajes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Audio.Reproductor;
import MapaTile.Background;

public class MenuPersonaje extends PersonajeJuego{
	
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
	
	private Font font;
	
	public MenuPersonaje(ManejadorPersonajes mp) {
		this.mp = mp;
		
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
			
			colorTitulo = Color.BLUE;
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			font = lfont.deriveFont(17f);
			
			sfx = new HashMap<>();
			sfx.put("Elegir",new Reproductor("/Sound/Sfx/EligePersonaje.wav"));
			sfx.put("Boton",new Reproductor("/Sound/Sfx/Boton.wav"));
			
			sfx.get("Elegir").play(false);
		
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public void inicia() {}
	
	public void actualiza() {}
	
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
	
	
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);
		
		g.setColor(Color.WHITE);
		g.drawString("Juego Tutorial",250,40);
		g.setFont(font);
		g.setColor(colorTitulo);
		g.drawString("Personajes",250,70);
		
		g.setFont(font);
		
		
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
		
		g.drawImage(imagenPersonajes.get(opcionActual),400,100,null);
		
		
	}
	
	private void selecciona() {
		if(opcionActual == 0) {
			mp.setPersonaje(ManejadorPersonajes.WOLF);
		}
		if(opcionActual == 1) {
			mp.setPersonaje(ManejadorPersonajes.SKULL);
		}
		if(opcionActual == 2) {
			mp.setPersonaje(ManejadorPersonajes.FLOW);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER ||  k == KeyEvent.VK_J) {
			selecciona();
			sfx.get("Elegir").stop();
			sfx.get("Boton").stop();
		}
		if(k == KeyEvent.VK_UP ||  k == KeyEvent.VK_W) {
			sfx.get("Boton").play(false);
			opcionActual--;
			if(opcionActual == -1) {
				opcionActual = opciones.length -1;
			}
		}
		if(k == KeyEvent.VK_DOWN ||  k == KeyEvent.VK_S) {
			sfx.get("Boton").play(false);
			opcionActual++;
			if(opcionActual == opciones.length) {
				opcionActual = 0;
			}
		}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_K) {
			sfx.get("Elegir").stop();
			mp.setPersonaje(ManejadorPersonajes.SALIR);
		}
	}
	
	public void keyReleased(int k) {}

}
