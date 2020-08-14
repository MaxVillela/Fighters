package SeleccionConexion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import MapaTile.Background;

public class MenuFinal extends TipoConexion{
	
	private Background bg;
	private String mensaje;
	private String nombrePersonaje;

	private BufferedImage personaje; 
	
	private Font fontTitulo;
	
	public MenuFinal(ManejadorConexion mc) {
		this.mc = mc;
		
		try {
			System.out.println(mc.getMensajeFinal());
			
			String datos[] = mc.getMensajeFinal().split(":");
			
			mensaje = datos[0];
			nombrePersonaje = datos[1];
			
			bg = new Background("/Background/Opening.gif",1);
			bg.setVector(-0.1,0);
		
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTitulo = lfont.deriveFont(17f);
			

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
		
		g.drawImage(personaje,50,70,null);
		
		g.setColor(Color.GRAY);
		g.drawString("Pulsa [ Esc ] para salir ",160,330);

	}
	
	private void salir() {
		mc.setMenu(ManejadorConexion.SALIR);
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
	
