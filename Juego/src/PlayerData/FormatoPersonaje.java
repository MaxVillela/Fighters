package PlayerData;

public class FormatoPersonaje {
	
	protected int maxVida;
	
	protected int[] numFrames;
	
	protected int anchura;
	protected int altura;
	
	protected int maxDisparo;
	protected int golpeDano;
	protected int golpeAlcance;
	protected double maximoMover;
	
	protected String path;
	
	public FormatoPersonaje(String personaje) {
		
		numFrames = new int[7];
		
		if(personaje.equals("Skull"))
			setupSkull();
		else if(personaje.equals("Flow"))
			setupFlow();
		else
			setupWolf();
	}
	
	private void setupFlow() {
		anchura = 40;
		altura = 40;
		
		maxVida = 40;
		maxDisparo = 40;
		golpeDano = 10;
		golpeAlcance = 30;
		maximoMover = 1.7;
		
		
		path = "/Sprites/Personaje/Flow.gif";
		
		numFrames[0] = 4;
		numFrames[1] = 7;
		numFrames[2] = 1;
		numFrames[3] = 2;
		numFrames[4] = 1;
		numFrames[5] = 6;
		numFrames[6] = 6;
	}
	
	private void setupSkull() {
		anchura = 60;
		altura = 60;
		
		maxVida = 60;
		maxDisparo = 20;
		golpeDano = 15;
		golpeAlcance = 45;
		maximoMover = 1.3;
		
		path = "/Sprites/Personaje/Skull.gif";
		
		numFrames[0] = 6;
		numFrames[1] = 6;
		numFrames[2] = 3;
		numFrames[3] = 3;
		numFrames[4] = 1;
		numFrames[5] = 7;
		numFrames[6] = 5;
	}
	
	private void setupWolf() {
		anchura = 60;
		altura = 60;
		
		maxVida = 55;
		maxDisparo = 40;
		golpeDano = 10;
		golpeAlcance = 37;
		maximoMover = 1.5;
		
		
		path = "/Sprites/Personaje/Wolf.gif";
		
		numFrames[0] = 7;
		numFrames[1] = 8;
		numFrames[2] = 3;
		numFrames[3] = 2;
		numFrames[4] = 1;
		numFrames[5] = 7;
		numFrames[6] = 5;
		
	}

	
	public int getMaxVida() { return maxVida; }
	public int getMaxDisparo() { return maxDisparo; }
	public int getGolpeDano() { return golpeDano; }
	public int getGolpeAlcance() { return golpeAlcance; }
	public double getMaximoMover() { return maximoMover; }
	
	public int getNumFrames(int i) { return numFrames[i]; }
	
	public int getAnchura() { return anchura; }
	public int getAltura() { return altura; }
	
	public String getPath() { return path; }
	

}
