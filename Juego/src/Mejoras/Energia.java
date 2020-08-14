package Mejoras;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entidad.Animacion;
import Entidad.Mejora;
import MapaTile.MapaTile;

public class Energia extends Mejora{
	
	private BufferedImage[] sprites;
	
	
	public Energia(MapaTile mt) {
		super(mt);
		velMover = 0.3;
		maximoMover = 0.6;
		velCaida = 0.2;
		maxVelCaida = 10.0;
		
		anchura = 30;
		altura = 30;
		clanchura = 20;
		claltura = 20;
		
		cantMejorar = 6;
		
		tipoMejora = "Energia";
		
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Mejoras/Energia.gif"
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
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animacion = new Animacion();
		animacion.setFrames(sprites);
		animacion.setRetraso(300);
		
		derecha = true;
		izquierda = true;
		miraDer = false;
	}
	
	
	
	public void actualiza() {
		super.actualiza();
	}
	
	public void dibuja(Graphics2D g) {
		setPosicionMapa();
		super.dibuja(g);
	}
}
