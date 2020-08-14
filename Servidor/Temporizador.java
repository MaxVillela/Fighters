import java.util.*;

public class Temporizador{
	private int interval;
    private Timer timer;
    private String tiempo;
    private Servidor servidor;
    private boolean juegoActivo;

    private boolean activo = true;

	public Temporizador(int secs,Servidor servidor,boolean juegoActivo){
        this.servidor = servidor;
        this.juegoActivo = juegoActivo;
        int delay = 0;
        int period = 1000;
        timer = new Timer();
        interval = secs;
        timer.scheduleAtFixedRate(new TimerTask(){

    public void run() {
        setInterval();
        if(!juegoActivo)
            servidor.enviarTiempo(interval);
        else
            servidor.enviarJuegoTiempo(interval);
        }
     }, delay, period);
    }

    private void setInterval() {
        if (interval == 1 || servidor.getConectados() == 0){
            if(interval == 1){
                if(!juegoActivo){
                    servidor.juegoNuevo();
                }
            }
                timer.cancel();
            }
        --interval;
    }

    public void terminaTempo() { timer.cancel(); } 
        
}