package Principal;

import javax.swing.JPanel;

import EstadosDeJuego.ManejadorEstados;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class PanelJuego extends JPanel 
	implements Runnable,KeyListener{
	
	//Dimensiones
	public static final int ANCHURA = 640;
	public static final int ALTURA= 360;
	public static final int ESCALA = 2;
	
	//Thread de Juego
	private Thread thread;
	private boolean activo;
	private int FPS = 100;
	private long targetTime = 1000 / FPS;
	
	//Imagen
	private BufferedImage imagen;
	private Graphics2D g;
	
	//Manejador de Estados de Juego
	private ManejadorEstados me;
	
	public PanelJuego() {
		super();
		setPreferredSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));
		setFocusable(true);
		requestFocus();
	}
	
	//Llamada a JComponent
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void inicia() {
		imagen = new BufferedImage(
				ANCHURA,ALTURA,
				BufferedImage.TYPE_INT_RGB
				);
			
		g = (Graphics2D) imagen.getGraphics();
		
		activo = true;
		
		me = new ManejadorEstados();
	}
	
	public void run() {
		
		inicia();
		
		long inicio;
		long restante;
		long espera;
		
		while(activo) {
			inicio = System.nanoTime();
			
			actualiza();
			dibuja();
			dibujaPantalla();
			
			restante = System.nanoTime() - inicio;
			
			espera = targetTime - restante / 1000000;
			
			if(espera < 0) espera = 5;
			try {
				Thread.sleep(espera);
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	private void actualiza() {
		me.actualiza();
	}
	
	private void dibuja() {
		me.dibuja(g);
	}
	
	public void dibujaPantalla() {
		Graphics g2 = getGraphics();
		g2.drawImage(imagen,0,0,
				ANCHURA * ESCALA, ALTURA * ESCALA,
				null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		try {
		me.keyPressed(key.getKeyCode());
		}catch(Exception e) {}
	}
	
	public void keyReleased(KeyEvent key) {
		try {
		me.keyReleased(key.getKeyCode());
		}catch(Exception e) {}
	}
	
	
	
	
	
}
