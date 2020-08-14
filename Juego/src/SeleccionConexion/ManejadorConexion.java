package SeleccionConexion;

import java.awt.Graphics2D;
import EstadosDeJuego.EstadoJuego;
import EstadosDeJuego.ManejadorEstados;
import SeleccionDeMapas.Nivel1Mapa;

public class ManejadorConexion extends EstadoJuego {
	
	private TipoConexion[] menus;
	private int menuActual;
	
	public static final int CONEXION = 0;
	public static final int LOBBY = 1;
	public static final int JUEGO = 2;
	public static final int MENUFINAL = 3;
	public static final int SALIR = 4; 
	
	public static final int TOTALMENU = 5;
	
	private int port;
	private String ip;
	private String mensajeFinal;
	
	private ConexionLobby lobby; 
	private Nivel1Mapa juego; 
	

	public ManejadorConexion(ManejadorEstados me) {
		this.me = me;
		menus = new TipoConexion[TOTALMENU];
		menuActual = CONEXION;
		cargaMenu(menuActual);
	}
	
	private void cargaMenu(int menu) {
		if(menu  == CONEXION) {
			menus[menu] = new ConexionIP(this);
		}
		if(menu == LOBBY) {
			this.lobby = new ConexionLobby(this,this.getIp(),getPort());
			menus[menu] = this.lobby;
		}
		if(menu == JUEGO) {
			this.juego = new Nivel1Mapa(this,this.lobby);
			me.quitaMusica();
			menus[menu] = this.juego;
		}
		if(menu == MENUFINAL) {
			menus[menu] = new MenuFinal(this);
		}
		
		if(menu == SALIR) {
			if(lobby!= null)
				lobby.cierraLobby();
			
			me.setEstado(ManejadorEstados.MENU);
		}
	}
		
	private void quitaMenu(int menu) {
		menus[menu] = null;
	}
	
	public void setMenu(int menu) {
		quitaMenu(menuActual);
		menuActual = menu;
		cargaMenu(menuActual);
	}
	
	public void flujoDatosRecibidos(String msg){
		this.juego.recibirServidor(msg);
	}
	
	public void flujoUnico(String msg) {
		this.juego.recibirMensajeUnico(msg);
	}
	
	public void cerrarLobby() {
		lobby.cierraLobby();
	}
	
	public void inicia() {}
	
	public void actualiza() {
		try {
			menus[menuActual].actualiza();
		} catch(Exception e) {}
	}
	
	public void dibuja(Graphics2D g) {
		try {
			menus[menuActual].dibuja(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		menus[menuActual].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		menus[menuActual].keyReleased(k);
	}
	
	protected void setPort(int p) { port = p; }
	protected int getPort() { return port; }
	protected void setIp(String i) { ip = i; }
	protected String getIp() { return ip; }
	public void setMensajeFinal(String m) { mensajeFinal = m; }
	public String getMensajeFinal() { return mensajeFinal; } 
	

}
