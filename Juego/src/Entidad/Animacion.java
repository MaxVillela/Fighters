package Entidad;

import java.awt.image.BufferedImage;

public class Animacion {
	
	private BufferedImage[] frames;
	private int actualFrame;
	
	private long tiempoInicial;
	private long retraso; 
	
	private boolean animacionEnUso;
	
	public Animacion() {
		animacionEnUso = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		actualFrame = 0;
		tiempoInicial = System.nanoTime();
		animacionEnUso = false;
	}
	
	public void setRetraso(long d) { retraso = d; }
	public void setFrame(int i) { actualFrame = i; }
	
	public void actualiza() {
		
		//En caso de que no sea necesario un cambio de animacion
		if(retraso == -1) return;
		
		long restante = (System.nanoTime() - tiempoInicial) / 1000000;
		if(restante > retraso) {
			actualFrame++;
			tiempoInicial = System.nanoTime();
		}
		if(actualFrame == frames.length) {
			actualFrame = 0;
			animacionEnUso = true;
		}
	}
	
	public int getFrame() { return actualFrame; }
	public BufferedImage getImagen() { return frames[actualFrame]; }
	public boolean estaAnimacionEnUso() { return animacionEnUso; }	

}
