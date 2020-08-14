package Enemigos;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import Entidad.*;
import MapaTile.MapaTile;

public class Zombie extends Enemigo{
		
	private BufferedImage[] sprites;
		
	public Zombie(MapaTile mt) {
		super(mt);
			
		velMover = 0.3;
		maximoMover = 0.3;
		velCaida = 0.2;
		maxVelCaida = 10.0;
			
		anchura = 40;
		altura = 40;
		clanchura = 20;
		claltura = 20;
			
		vida = maxVida = 15;
		dano = 10;
		
		try {
				
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/ObjetosMapa/Zombie.gif"
				)
			);
				
			sprites = new BufferedImage[3];
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
	miraDer = true;
}
		
		
	private void getSiguientePosicion() {
		if(izquierda) {
			dx -= velMover;
			if(dx < -maximoMover) {
				dx = -maximoMover;
			}
		}
		else if(derecha ) {
			dx += velMover;
			if(dx > maximoMover) {
				dx = maximoMover;
			}

		}
			
			// falling
		if(cayendo) {
			dy += velCaida;
		}
	}
		
	public void actualiza() {
		getSiguientePosicion();
		revisaColisionMapa();
		setPosicion(xtemp, ytemp);
			
		if(retroceso) {
			long restante =
				(System.nanoTime() - retrocesoTimer) / 1000000;
			if(restante > 1000) {
				retroceso = false;
			}
		}
			
		if(derecha && dx == 0) {
			derecha = false;
			izquierda = true;
			miraDer = false;
		}
		else if(izquierda && dx == 0) {
			derecha = true;
			izquierda = false;
			miraDer = true;
		}
		
		animacion.actualiza();
	}
		
	public void dibuja(Graphics2D g) {
			
		setPosicionMapa();
		
		if(retroceso) {
			long elapsed =
				(System.nanoTime() - retrocesoTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
			
		super.dibuja(g);
	}
}
