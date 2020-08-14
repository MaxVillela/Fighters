package MapaTile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Principal.PanelJuego;

public class Background {

	private BufferedImage imagen;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double movimiento;
	
	public Background(String direccion, double m) {
		try {
			imagen = ImageIO.read(getClass().getResourceAsStream(direccion));
			movimiento = m;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosicion(double x, double y) {
		this.x = (x * movimiento) % PanelJuego.ANCHURA;
		this.y = (y * movimiento) % PanelJuego.ALTURA;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void actualiza() {
		x +=dx;
		y +=dy;
	}
	
	public void dibuja(Graphics2D g) {
		g.drawImage(imagen, (int)x,
				(int)y, PanelJuego.ANCHURA,
				PanelJuego.ALTURA, null);
		
		if(x < 0) {
			g.drawImage(imagen, (int)x + PanelJuego.ANCHURA,
					(int)y, PanelJuego.ANCHURA,
					PanelJuego.ALTURA, null);
		}
					
		if(x > 0) {
			g.drawImage(imagen, (int)x - PanelJuego.ANCHURA,
					(int)y, PanelJuego.ANCHURA,
					PanelJuego.ALTURA, null);
		}
	}
	
}
