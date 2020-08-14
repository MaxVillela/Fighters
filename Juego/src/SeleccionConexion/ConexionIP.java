package SeleccionConexion;

import Audio.Reproductor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;

import MapaTile.Background;

public class ConexionIP extends TipoConexion{
	
	private HashMap<String,Reproductor> sfx;
	
	private Background bg;
	private Image imagen;
	private Color colorTitulo;
	private Font fontTitulos;
	private Font fontTextos;
	
	private String ip;
	private String puerto;
	
	private String estado;
	
	//Hasta almacenar ip y puerto estaran estos;
	private boolean listoPruebaConexion;
	
	
	public ConexionIP(ManejadorConexion mc) {
		this.mc = mc;
		
		ip = "";
		puerto = "";
		estado = "Ingresa ip"; 
		
		try {
			imagen = new ImageIcon(getClass().getResource(
					"/Background/LogoConexion.gif")).getImage();
			
			bg = new Background("/Background/Mapa.gif",1);
			bg.setVector(-0.1,0);
			
			colorTitulo = new Color(255,255,225);
			
			Font lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font7.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTitulos = lfont.deriveFont(18f);
			
			lfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/TileSet/Font/Font3.ttf"));
			lfont.deriveFont(Font.PLAIN,100);
			
			fontTextos = lfont.deriveFont(21f);
			
			sfx = new HashMap<>();
			sfx.put("Online", new Reproductor("/Sound/Sfx/Online.wav"));
			
			sfx.get("Online").play(false);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void inicia() {}

	public void actualiza() {}
	
	public void dibuja(Graphics2D g) {
		bg.dibuja(g);
		
		g.drawImage(imagen,450,120,null);
		
		g.setColor(colorTitulo);
		g.setFont(fontTitulos);
		g.drawString("Ingresa IP y puerto del Servidor",150,60);
		
		g.setFont(fontTextos);
		
		g.setColor(new Color(217,192,57));
		
		g.drawString(String.format("IP: %s",ip),130,180);
			
		g.drawString(String.format("Puerto: %s",puerto),130,220);
		
		g.setColor(Color.GRAY);
		g.setFont(fontTitulos);
		g.drawString(estado, 240,330);
		
	}
	
	private void setEstado(String e) {
		estado = e;
	}
	
	private void selecciona() {
		if(!listoPruebaConexion) {
			setEstado("Ingresa puerto");
			listoPruebaConexion = true;
		}else {
			try{
				setEstado("Conectando");
				InetAddress addr = InetAddress.getByName(ip);
				Socket servidor = new Socket(addr,Integer.parseInt(puerto));
				servidor.close();
				mc.setIp(ip);
				mc.setPort(Integer.parseInt(puerto));
				mc.setMenu(ManejadorConexion.LOBBY);
			}catch(Exception e) {
				setEstado("Servidor no disponible");
				listoPruebaConexion = false;
				ip = "";
				puerto = "";
			}
		}
	}
	
	private void agregarTexto(String letra) {
		if(!listoPruebaConexion) {
			if(ip.length()<17)
				ip += letra;
		}
		else {
			if(puerto.length()<7)
				puerto += letra;
		}
		
	}
	
	private void borrarTexto() {
		String aux;
		if(!listoPruebaConexion) {
			if (ip != null && ip.length() > 0){
			      aux = ip.substring(0, ip.length() - 1);
			      ip = aux;
			}
		}else {
			if (puerto != null && puerto.length() > 0){
			      aux = puerto.substring(0, puerto.length() - 1);
			      puerto = aux;
			}
		}
	}
		
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER ||  k == KeyEvent.VK_J) {
			selecciona();
		}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_K) {
			sfx.get("Online").stop();
			mc.setMenu(ManejadorConexion.SALIR);
		}
		if(k == KeyEvent.VK_BACK_SPACE) {
			borrarTexto();
		}
		if(k == KeyEvent.VK_0) {
			agregarTexto("0");
		}	
		if(k == KeyEvent.VK_1) {
			agregarTexto("1");
		}
		if(k == KeyEvent.VK_2){
			agregarTexto("2");
		}
		if(k == KeyEvent.VK_3) {
			agregarTexto("3");
		}
		if(k == KeyEvent.VK_4) {
			agregarTexto("4");
		}
		if(k == KeyEvent.VK_5) {
			agregarTexto("5");
		}
		if(k == KeyEvent.VK_6) {
			agregarTexto("6");
		}
		if(k == KeyEvent.VK_7) {
			agregarTexto("7");
		}
		if(k == KeyEvent.VK_8) {
			agregarTexto("8");
		}
		if(k == KeyEvent.VK_9) {
			agregarTexto("9");
		}	
		if(k == KeyEvent.VK_PERIOD) {
			agregarTexto(".");
		}
	}
	
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
