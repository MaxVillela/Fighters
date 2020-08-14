package Red;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import SeleccionConexion.ConexionLobby;

public class Cliente {
	
	private Socket servidor;
	private ConexionLobby c;
	private PrintWriter out; 
	private BufferedReader in;
	
	
	public Cliente(ConexionLobby c,String ip, int puerto) {
		this.c = c;
		
		try{
			InetAddress addr = InetAddress.getByName(ip);
            servidor = new Socket(addr,puerto);
            out = new PrintWriter(servidor.getOutputStream(),true);         
            in = new BufferedReader(new InputStreamReader(servidor.getInputStream())); 
       
            hiloCliente t = new hiloCliente(c,in);
            t.start();  
        }catch(Exception e){
            System.out.println("Servidor no disponible");
            c.cierraLobby();
            //e.printStackTrace();
        }
	}
	
	public void cierra() {
		try {
			servidor.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarServidor(String msg) {
		  try{                      
	           out.println(msg);
	        }catch(Exception e){
	            System.out.println("Servidor no disponible");
	            e.printStackTrace();
	        }
	}
}
