package PlayerData;

public class FormatoMapa {

	protected int[] posx = {
			330,
			1255,
			360,
			1220,
	};
	
	protected int[] posy = {
			50,
			50,
			50,
			50
	};
	
	protected int numJugadores; 
	
	public FormatoMapa() {
	}
	
	
	public int getx(int index) { return posx[index]; }
	public int gety(int index) { return posy[index]; }
	
}
