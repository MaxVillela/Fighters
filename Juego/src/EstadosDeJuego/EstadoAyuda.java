package EstadosDeJuego;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import MapaTile.Background;

public class EstadoAyuda extends EstadoJuego{

	private ManejadorEstados me;
	
	private Background bg;
	
	public EstadoAyuda(ManejadorEstados me) {
		this.me = me;
		
		try {
				bg = new Background("/Background/Ayuda.gif",0);
				bg.setVector(0, 0);

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void inicia() {}
	
	public void actualiza() {
		bg.actualiza();
	}
	
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_K){
			me.setEstado(ManejadorEstados.MENU);
		}
	}
	
	public void keyReleased(int k) {}
	
}
