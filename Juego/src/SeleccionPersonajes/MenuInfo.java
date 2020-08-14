package SeleccionPersonajes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import MapaTile.Background;

public class MenuInfo extends PersonajeJuego{
	
	private Background bg;
	private String estado;
	private String mensaje;
	private String vidaFinal;
	private String vidaMaxima;
	private String nombrePersonaje;
	private String disparo;
	private String maxDisparo;

	private BufferedImage personaje; 
	
	private Color colorTitulo;
	private Font fontTitulo;
	private Font fontTextos;
	
	public MenuInfo(ManejadorPersonajes mp) {
		this.mp = mp;
		
		String datos[] = mp.getMensajeFinal().split(":");
		estado = datos[0];
		mensaje = datos[1];
		vidaFinal = datos[2];
		vidaMaxima = datos[3];
		nombrePersonaje = datos[4];
		disparo = datos[5];
		maxDisparo = datos[6];
		
		System.out.println("mensaje"+mensaje);
		
		try {
			bg = new Background("/Background/Opening.gif",1);
			bg.setVector(-0.1,0);
			
			colorTitulo = new Color(255,255,225);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTitulo = lfont.deriveFont(17f);
			
			lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font3.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTextos = lfont.deriveFont(21f);
			
			personaje = ImageIO.read(
					getClass().getResourceAsStream(
							obtenerDireccion()
						)
					);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String obtenerDireccion(){
		String path = "";
			if(nombrePersonaje.equals("Wolf"))
				path = "/Sprites/HUD/WolfFull.gif";
			else if(nombrePersonaje.equals("Skull"))
				path = "/Sprites/HUD/SkullFull.gif";
			else
				path = "/Sprites/HUD/FlowFull.gif";
			return path;
	}
	
	public void inicia() {}
	
	public void actualiza() {}
	
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);
		
		
		g.setFont(fontTitulo);
		g.setColor(Color.GRAY);
		g.drawString("Resultado Final", 240, 40);
		
		g.setColor(new Color(67,60,227));
		g.drawString(mensaje,260,70);
		
		g.setColor(new Color(217,192,57));
		
		g.drawString("Tu tiempo:",200,130);
		
		g.drawString("Tu vida:",200,160);
		
		g.drawString("Energia :",200,190);
		
		g.setColor(colorTitulo);
		g.setFont(fontTextos);
		g.drawString(estado+" segundos",350,130);	

		g.setColor(new Color(246,86,86));
		g.drawString(String.format("%s de %s" ,vidaFinal,vidaMaxima),350,160);
		
		g.setColor(new Color(67,60,227));
		g.drawString(String.format("%s de %s" ,disparo,maxDisparo),350,190);
		
		g.drawImage(personaje,50,70,null);
		
		g.setColor(Color.GRAY);
		g.drawString("Pulsa [ Esc ] para salir ",160,330);

	}
	
	private void salir() {
		mp.setPersonaje(ManejadorPersonajes.SALIR);
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			salir();
		}
		
		if(k == KeyEvent.VK_ESCAPE) {
			salir();
		}
	}
	
	public void keyReleased(int k) {}
}
	
