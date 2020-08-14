package Util;

import java.util.*;

public class Cronometro{
	private int interval;
    private Timer timer;

    private boolean activo = true;

	public Cronometro(){
        int delay = 0;
        int period = 1000;
        timer = new Timer();
        interval = 0;
   
        timer.scheduleAtFixedRate(new TimerTask(){

    public void run() {
        setInterval();
        }
     }, delay, period);
    }

    private void setInterval() {
        if (!activo) {
                timer.cancel();
            }
        ++interval;
    }

    public void terminaTiempo() { activo = false; }
    public int getTiempo() { return interval; }
}