package EstadosDeJuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import MapaTile.Background;

public class EstadoConexion extends EstadoJuego{
	
	private Background bg;
	

	private Color colorTitulo;
	
	private Font font;
	
	private String texto;
	
	public EstadoConexion(ManejadorEstados me){
		this.me = me; 
		
		texto = ""; 
		
		try {
			bg = new Background("/Background/Conexion.gif",1);
			bg.setVector(-0.1,0);
			
			colorTitulo = new Color(255,255,225);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			font = lfont.deriveFont(17f);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inicia() {}
	
	public void actualiza() {}
	
	public void dibuja(Graphics2D g) {
		
		bg.dibuja(g);
		
		g.setColor(colorTitulo);
		g.setFont(font);
		g.drawString("Ingresa IP del Servidor",300,100);
		g.setFont(font);
		
		g.drawString(texto, 260, 160);
		System.out.println(texto);
	}

	private void selecciona() {
		if(texto.length()>2) {
			me.setEstado(ManejadorEstados.JUGAR);
		}
	}
	
	
	private void agregaTexto(String letra) {
		texto += letra;
	}
	
	private void borrarTexto() {
		String aux;
		
		if (texto != null && texto.length() > 0) {
	        aux = texto.substring(0, texto.length() - 1); 
	        texto = aux;
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			selecciona();
		}
	
		if(k == KeyEvent.VK_BACK_SPACE) {
			me.setEstado(ManejadorEstados.MENU);
		}
		
		if(k == KeyEvent.VK_J) {
			borrarTexto();
		}
		
		if(k == KeyEvent.VK_0) {
			agregaTexto("0");
		}
		
		if(k == KeyEvent.VK_1) {
			agregaTexto("1");
		}
		
		if(k == KeyEvent.VK_2) {
			agregaTexto("2");
		}
		
		if(k == KeyEvent.VK_3) {
			agregaTexto("3");
		}
		
		if(k == KeyEvent.VK_4) {
			agregaTexto("4");
		}
		
		if(k == KeyEvent.VK_5) {
			agregaTexto("5");
		}
		
		if(k == KeyEvent.VK_6) {
			agregaTexto("6");
		}
		
		if(k == KeyEvent.VK_7) {
			agregaTexto("7");
		}
		
		if(k == KeyEvent.VK_8) {
			agregaTexto("8");
		}
		
		if(k == KeyEvent.VK_9) {
			agregaTexto("9");
		}
		
		if(k == KeyEvent.VK_PERIOD) {
			agregaTexto(".");
		}
	}

	public void keyReleased(int k) {}

}
