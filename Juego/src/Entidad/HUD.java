package Entidad;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	
	private Jugador jugador;
	private String personaje;
	private Font font;
	
	private BufferedImage imagen;
	private BufferedImage perfil;
	private boolean usuario;
	
	private int xRojo;
	private int yRojo;
	private int xAmarillo;
	private int yAmarillo;
	private int xAzul;
	private int yAzul;
	
	private int xrect;
	private int yrect;
	
	private int xImagen;
	private int yImagen;
	
	private int xPerfil;
	private int yPerfil;
	
	private Color rojo;
	private Color amarillo;
	private Color azul;
	private Color negro;
	
	public HUD(Jugador j,String p,boolean u) {
		jugador = j;
		personaje = p;
		usuario = u;
		
		negro = new Color(52,52,52);
		rojo = new Color(246,86,86); 
		amarillo = new Color(67,60,227);
		azul = new Color(217,192,57);
		
		xrect = 65;
		yrect = 6;
		
		if(u)
			setupUsuario();
		else
			setupOtros();
			
		try {
			imagen = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/HUD/hud.gif"
				)
			);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/pdark.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			font = lfont.deriveFont(18f);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		asignaPerfil();
	}
	
	private void setupUsuario() {
		xRojo = 47;
		yRojo = 19;
		xAmarillo = 53;
		yAmarillo = 36;
		xAzul = 46;
		yAzul = 54;
		
		xImagen = 0;
		yImagen = 15;
				
		xPerfil = 2;
		yPerfil = 15;

	}
	
	private void setupOtros() {
		xRojo = 47;
		yRojo = 269;
		xAmarillo = 53;
		yAmarillo = 286;
		xAzul = 46;
		yAzul = 304;
		
		xImagen = 0;
		yImagen = 265;
				
		xPerfil = 2;
		yPerfil = 265;

	}
	
	private void asignaPerfil() {
		
		String path;
		if(personaje.equals("Flow"))
			path = "/Sprites/HUD/Flow.gif";
		else if(personaje.equals("Skull"))
			path = "/Sprites/HUD/Skull.gif";
		else
			path = "/Sprites/HUD/wofl.gif";	
	
		try {
			perfil = ImageIO.read(
					getClass().getResourceAsStream(
						path
					)
				);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public void dibuja(Graphics2D g) {
		
		g.setColor(negro);
		g.fillRect(xRojo,yRojo,xrect,yrect);
		g.fillRect(xAmarillo,yAmarillo,xrect,yrect);
		g.fillRect(xAzul, yAmarillo, xrect, yrect);
		
		g.setColor(rojo);
		g.fillRect(
				xRojo,
				yRojo,	
				(int)((xrect/(float)(jugador.getMaxVida()) *(float) (jugador.getVida()))),
				yrect);
		
		g.setColor(azul);
		g.fillRect(
				xAmarillo,
				yAmarillo,	
				(int)((xrect/(float)(jugador.getMaxDisparo()) *(float) (jugador.getDisparo()))),
				7);
		
		g.setColor(amarillo);
		g.fillRect(
				xAzul,
				yAzul,	
				xrect,
				yrect);
		
		g.drawImage(imagen,xImagen,yImagen,null);
		g.drawImage(perfil, xPerfil,yPerfil, null);
		
		if(usuario) {
		g.setColor(azul);
		g.setFont(font);
		g.drawString("P1 ",20,80);
		}
		
	}

}
