package Red;

import java.io.BufferedReader;

import SeleccionConexion.ConexionLobby;

public class hiloCliente extends Thread{
	
	private BufferedReader in;
	private ConexionLobby c;
	private boolean running = true;
	
	public hiloCliente(ConexionLobby c,BufferedReader in) {
		this.c = c; 
		this.in = in;
	}
	
	private void terminar() {
		running = false;
		c.cierraLobby();
	}
	
	private void revisaEstadoCliente() {
		if(!c.getEstadoCliente()) {
			terminar();	
		}
	}
	
	private void decodificaMensaje(String msg){
		try {
		String[] data = msg.split(":");
		String op = data[0];
		
		//Movimiento de Jugador
		if(op.equals("OP_Jugador")) {
			c.flujoDatosRecibidos(msg);
		}
		//Si un jugador murio,tiempo del Jugador o termina el juego, 
		if(op.equals("OP_Muerto") || op.equals("OP_Tiempo") || op.equals("OP_TerminaJuego") ) {
			c.flujoUnico(msg);
		}
		//EsperaLobby: Tiempos
		if(op.equals("OP_Lobby")){
			c.muestraTiempoEspera(data[1],data[2]);	
		}
		//Envia id del cliente
		if(op.equals("OP_Cliente")) {
			System.out.println("Id"+data[1]);
			c.setIdCliente(Integer.parseInt(data[1]));
		}
		//Cuando se cierra el lobby del juego
		if(op.equals("OP_Salir")) {
			c.cierraLobby();
		}
		if(op.equals("OP_JuegoNuevo")){
			c.juegoNuevo();
		}
		
		//Carga los personajes para ponerlos en el mapa antes
		if(op.equals("OP_CargarJD")){
			c.cargaJugadores(msg);
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		 String msg;
		 try {   
             while(running){    
                 while((msg = in.readLine())!= null){
            		 revisaEstadoCliente();
            		 decodificaMensaje(msg);
                 }
             }
         }catch (Exception e) {
             //e.printStackTrace();
             terminar();
         }
	}

}
