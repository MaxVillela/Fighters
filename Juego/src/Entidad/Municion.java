package Entidad;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import MapaTile.MapaTile;


public class Municion extends ObjetoMapa{
	
	private boolean golpe;
	private boolean remover;
	private BufferedImage[] sprites;
	private BufferedImage[] golpeSprites;
	
	
	public Municion(MapaTile mt, boolean derecha) {
		
		super(mt);
		
		miraDer = derecha;
		
		velMover = 3.8;
		
		if(derecha) 
			dx = velMover;
		else 
			dx = -velMover;
		
		anchura = 30;
		altura = 30;
		clanchura = 14;
		claltura = 14;
		
		//cargar Sprites
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
						"/Sprites/fireball.gif"
					)
				);
				
				sprites = new BufferedImage[4];
				for(int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(
						i * anchura,
						0,
						anchura,
						altura
					);
				}
				
				golpeSprites = new BufferedImage[3];
				for(int i = 0; i < golpeSprites.length; i++) {
					golpeSprites[i] = spritesheet.getSubimage(
						i * anchura,
						altura,
						anchura,
						altura
					);
			}
				
				
				animacion = new Animacion();
				animacion.setFrames(sprites);
				animacion.setRetraso(70);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setGolpe() {
		if(golpe) return;
		golpe = true;
		animacion.setFrames(golpeSprites);
		animacion.setRetraso(70);
		dx = 0;
	}
	
	
	public boolean getRemover() { return remover; }
	
	public void actualiza() {
		
		revisaColisionMapa();
		setPosicion(xtemp,ytemp);
		
		if(dx == 0 && !golpe) {
			setGolpe();
		}
		
		animacion.actualiza();
		if(golpe && animacion.estaAnimacionEnUso()) {
			remover = true;
		}
	}
	
	public void dibuja(Graphics2D g) {
		setPosicionMapa();
		
		super.dibuja(g);
	}
		

}


