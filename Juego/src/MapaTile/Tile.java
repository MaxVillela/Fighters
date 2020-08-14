package MapaTile;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage imagen;
	private int tipo;
	
	//Tipos de Tiles
	public static final int NORMAL = 0;
	public static final int BLOQUEADO = 1;
	
	public Tile(BufferedImage imagen, int tipo) {
		this.imagen = imagen;
		this.tipo = tipo;
	}
	
	public BufferedImage getImagen() { return imagen; }
	public int getTipo() { return tipo; }

}
