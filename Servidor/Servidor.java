import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.util.ElementScanner6;

public class Servidor{
        private ServerSocket servidor;
        private Socket cliente;
        private HashMap<Socket,PrintWriter> outputs;
        private HashMap<Socket,Integer> idUser;
        private String[] personajes;

        private Temporizador temporizador; 
        private Temporizador tempoJuego;
        private boolean running = true;
        private final int puerto = 8080;
        
        private final int tiempoTempo = 30;
        private final int tiempoJuego = 120;
        
        private final int totalJugadores = 4;

        private boolean juegoActivo;

    public Servidor(){    
        try{ 
            servidor = new ServerSocket(puerto);
            outputs = new HashMap<>();
            idUser = new HashMap<>();
            personajes = new String[totalJugadores];

            limpiarJuagores();

            InetAddress ipAddr = InetAddress.getLocalHost();
            System.out.println("Servidor iniciado ");
            System.out.println(ipAddr.getHostAddress());
            System.out.println("Puerto:"+puerto);
            System.out.println("En espera de cliente\n");

            //Estado de juego en curso
            juegoActivo = false;

            while(running){
                if(!juegoActivo){
                cliente = servidor.accept();
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                outputs.put(cliente, out);
                System.out.println(String.format("Nuevo Usuario Conectado: %s",
                                    cliente.getRemoteSocketAddress().toString())); 
                agregarNuevoCliente(cliente);                            
                HiloServidor t = new HiloServidor(this,cliente);
                t.start();
                }
            }

            }catch(Exception e){
                e.printStackTrace();
                stop();
            }

        }

    private void agregarNuevoCliente(Socket cliente){
        int contador = 0;
        boolean agregado = false;

        while(!agregado){
            if(idUser.containsValue(contador)){
                contador++;
            }else{
                idUser.put(cliente,contador);
                agregado = true;
            }
        }

        if(idUser.size() == 1){
            if(temporizador != null)
               temporizador.terminaTempo();
                temporizador = null;
            if(temporizador == null){
                temporizador = new Temporizador(tiempoTempo, this,false);
            }      
        }

        System.out.println("Usuario: "+contador);
        System.out.println("Usuarios Activos: "+idUser.size());
        enviarCliente(cliente,String.format("OP_Cliente:%d",contador));
    }

    //Envio de todos los clientes
    public void enviarConectados(String msg){
        for (Map.Entry<Socket, PrintWriter> map : outputs.entrySet()){
            PrintWriter pw = map.getValue();
                try{
                    pw.println(msg);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.print("Excepcion Manejada");
                }
            }
    }

    public void enviarOtrosClientes(Socket c, String msg){
            for (Map.Entry<Socket, PrintWriter> map : outputs.entrySet()){
                Socket clt = map.getKey();
                if(!(c.equals(clt))){
                PrintWriter pw = map.getValue();
                    try{
                        pw.println(msg);
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.print("Excepcion Manejada");
                        }
                    }
                }
    }

    //Envio de un solo cliente
    public void enviarCliente(Socket c, String msg){
     for (Map.Entry<Socket, PrintWriter> map : outputs.entrySet()){
                Socket clt = map.getKey();
                if(c.equals(clt)){
                PrintWriter pw = map.getValue();
                    try{
                        pw.println(msg);
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.print("Excepcion Manejada");
                        }
                    }
                }
        
    }

    public void eliminarCliente(Socket c){
            outputs.remove(c);
            personajes[idUser.get(c)] = "";
            idUser.remove(c);
            try{
                c.close();
            }catch(Exception e){
                e.printStackTrace();
                System.out.print("Excepcion Manejada");
            }
            System.out.println("Usuarios Activos: "+idUser.size());
    
    }

    public void enviarTiempo(int tiempo){
        
        //Envia el tiempo y los jugadores totales (Lobby)
        if(!juegoActivo)
            enviarConectados(String.format("OP_Lobby:%d:%d",tiempo,idUser.size()));

        //Carga los personajes
            if(tiempo == 5){
               if(idUser.size()>=2){

                String formato = "";
                for(int i = 0; i<idUser.size(); i++){
                    
                    if(personajes[i].equals(""))
                        formato += "Wolf:";
                    else
                        formato += personajes[i]+":";
                }
   
                System.out.println(String.format("OP_CargarJD:%d:%s",
                idUser.size(),formato)); 

            enviarConectados(String.format("OP_CargarJD:%d:%s",
            idUser.size(),formato));
          }
        }
    }

    
    public void enviarJuegoTiempo(int tiempo){
        enviarConectados(String.format("OP_Tiempo:%d",tiempo));
        
            if(tiempo == 1){
                terminaJuego();
            }
    }

    public void terminaJuego(){
        enviarConectados(String.format("OP_TerminaJuego"));
        juegoActivo = false;
        tempoJuego.terminaTempo();
        tempoJuego = null;
    }

    public void guardaPersonaje(Socket c, String personaje){
        int indice = idUser.get(c);
        System.out.println(String.format("Indice %d: personaje %s",indice,personaje));

        personajes[indice] = personaje;
    }

    public void juegoNuevo(){
       if(idUser.size()>=2){
            juegoActivo = true;
            System.out.println("JUEGO ACTIVO");
            enviarConectados("OP_JuegoNuevo");
            temporizador.terminaTempo();
            temporizador = null;
            iniciaJuego();
       }else{
           enviarConectados("OP_Salir");
        }
    }

    private void iniciaJuego(){
        tempoJuego = new Temporizador(tiempoJuego,this,true);
    }

    public void muerto(String msg){
        enviarConectados(msg);
    }

    private void limpiarJuagores(){
        for(int i = 0; i<totalJugadores; i++){
            personajes[i] = "";
        }
    }

    public void enviarMovimientos(Socket c, String msg){
        int i = Integer.parseInt(msg.split(":")[1]);
        if(i != idUser.get(c))
            enviarOtrosClientes(c, msg);
    }

    public int getConectados(){ return idUser.size(); }

    private synchronized void stop(){
        running = false;
    }

        public static void main(String [] args){
            new Servidor();
        }
    }
