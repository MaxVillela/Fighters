package EstadosDeJuego;

import Audio.Reproductor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import MapaTile.Background;

public class EstadoMenu extends EstadoJuego{
	
	private Background bg;
	private HashMap<String,Reproductor> sfx;
	
	private int opcionActual = 0;
	private String[] opciones = {
			"Jugar",
			"Tutorial",
			"Ayuda",
			"Salir"
	};
	
	private Color colorTitulo;
	
	private Font font;
	
	public EstadoMenu(ManejadorEstados me) {
		this.me = me;
		
		try {
			bg = new Background("/Background/Opening.gif",1);
			bg.setVector(-0.1,0);
			
			colorTitulo = new Color(255,255,225);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			font = lfont.deriveFont(17f);
			
			sfx = new HashMap<>();
			sfx.put("Boton",new Reproductor("/Sound/Sfx/Boton.wav"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inicia() {}
	
	public void actualiza() {}
	
	public void dibuja(Graphics2D g) {
		
		bg.dibuja(g);
		
		g.setColor(colorTitulo);
		g.setFont(font);
		g.drawString("Figthers",300,100);
		
		g.setFont(font);
		for(int i = 0; i < opciones.length; i++) {
			if(i == opcionActual) {
				g.setColor(Color.BLUE);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(opciones[i], 250, 140 + i * 15);
		}
	}
	
	private void selecciona() {
		if(opcionActual == 0) {
			me.setEstado(ManejadorEstados.JUGAR);
		}
		if(opcionActual == 1) {
			me.setEstado(ManejadorEstados.TUTORIAL);
		}
		if(opcionActual == 2) {
			me.setEstado(ManejadorEstados.AYUDA);
		}
		if(opcionActual == 3) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER ||  k == KeyEvent.VK_J) {
			selecciona();
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
			System.exit(0);
		}
	}
	
	public void keyReleased(int k) {}
	
	

}
