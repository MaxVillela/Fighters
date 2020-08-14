import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class HiloServidor extends Thread{
    private final Servidor servidor;
    private final Socket cliente;
    private BufferedReader in;

    public HiloServidor(Servidor servidor, Socket cliente){
        this.cliente = cliente;
        this.servidor = servidor;
        iniciar();
    }

    private void iniciar(){
        try{
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("EXCEPCION MANEJADA");
        }
    }

    private void desconectado(){
            System.out.println("Usuario desconectado\n");
            servidor.eliminarCliente(cliente);
    }

    private void decodificarMensaje(String msg){ 
        String[] data = msg.split(":");
        String op = data[0]; 


        //Movimientos de jugadores
        if(op.equals("OP_Jugador")){
            servidor.enviarOtrosClientes(cliente,msg);
        }

        //Envia operacion y el tiempo;
        if(op.equals("OP_Lobby")){
            servidor.enviarConectados(msg);
        }

        //Guarda al personaje en la posicion indicada.
        if(op.equals("OP_Seleccion")){
           servidor.guardaPersonaje(cliente,data[1]);
        }
        if(op.equals("OP_Muerto")){
            System.out.println(msg);
            servidor.muerto(msg);
        }
        if(op.equals("OP_TerminaJuego")){
            servidor.terminaJuego();
        }

    }

    @Override
    public void run() {
        String msg;
        try {      
            while(true){ 
                msg = in.readLine();
                if(msg!=null)
                    decodificarMensaje(msg);
                   
            }
        }catch (Exception e) {
                //e.printStackTrace();
                desconectado();
                //System.out.println("Excepcion Manejada");
        }
    }
}