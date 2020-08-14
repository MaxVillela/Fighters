package Entidad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Explosion {

	private int x;
	private int y;
	private int xmapa;
	private int ymapa;
	
	private int anchura;
	private int altura;
	
	private Animacion animacion;
	private BufferedImage[] sprites;
	
	private boolean remover;
	
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		
		anchura = 30;
		altura = 30;
		
	try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/explosion.gif"
				)
			);
			
			sprites = new BufferedImage[6];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * anchura,
					0,
					anchura,
					altura
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		animacion = new Animacion();
		animacion.setFrames(sprites);
		animacion.setRetraso(70);
	}
	
	public void actualiza() {
		animacion.actualiza();
		if(animacion.estaAnimacionEnUso()) {
			remover = true;
		}
	}
	
	public boolean getRemover() { return remover; }
	
	public void setPosicionMapa(int x, int y) {
		xmapa = x;
		ymapa = y;
	}
	
	public void dibuja(Graphics2D g) {
		g.drawImage(
				animacion.getImagen(),
				x + xmapa - anchura / 2,
				y + ymapa - altura / 2,
				null
			);
	}
	
		
}
