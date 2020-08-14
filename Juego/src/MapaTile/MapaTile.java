package MapaTile;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import Principal.PanelJuego;

public class MapaTile {

	//Posicion
	private double x;
	private double y;
	
	//Limites
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double intercalado;
	
	//Mapa
	private int[][] mapa;
	private int tamTile;
	private int numRen;
	private int numCol;
	private int anchura;
	private int altura;
	
	//Set de Tiles
	private BufferedImage setTile;
	private int numDeTiles;
	private Tile[][] tiles;
	
	//Dibujo
	private int renFueraSet;
	private int colFueraSet;
	private int numRenADibujar;
	private int numColADibujar;
	
	public MapaTile(int tamTile) {
		this.tamTile = tamTile;
		numRenADibujar = PanelJuego.ALTURA / tamTile + 2;
		numColADibujar = PanelJuego.ANCHURA / tamTile + 2;
		intercalado = 0.07;
	}
	
	public void cargaTiles(String ruta) {
		try {
			
			setTile = ImageIO.read(getClass().getResourceAsStream(ruta));
			numDeTiles = setTile.getWidth() / tamTile;
			tiles = new Tile[2][numDeTiles];
			
			BufferedImage subimagen;
			for(int col = 0; col < numDeTiles; col++) {
				subimagen = setTile.getSubimage(
						col * tamTile,
						0,
						tamTile,
						tamTile
						);
				tiles[0][col] = new Tile(subimagen,Tile.NORMAL);
				subimagen = setTile.getSubimage(
						col * tamTile,
						tamTile,
						tamTile,
						tamTile
						);
				tiles[1][col] = new Tile(subimagen,Tile.BLOQUEADO);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargaMapa(String ruta){
		try {
			
			InputStream in = getClass().getResourceAsStream(ruta);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(in));
			
			numCol = Integer.parseInt(br.readLine());
			numRen = Integer.parseInt(br.readLine());
			mapa = new int[numRen][numCol];
			anchura = numCol * tamTile;
			altura = numRen * tamTile;
			
			xmin = PanelJuego.ANCHURA - anchura;
			xmax = 0;
			ymin = PanelJuego.ALTURA - altura;
			ymax = 0;
			
			String delimitar = "\\s+";
			for(int ren = 0; ren < numRen; ren++) {
				String linea = br.readLine();
				String[] tokens = linea.split(delimitar);
				
				for(int col = 0; col < numCol; col++) {
					mapa[ren][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTamTile() { return tamTile; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getAnchura() { return anchura; }
	public int getAltura() { return altura; } 
	
	public int getTipo(int ren, int col) {
		int rc = mapa[ren][col];
		int r = rc / numDeTiles;
		int c = rc % numDeTiles;
		return tiles[r][c].getTipo();
	}
	
	public void setIntercalado(double d) { intercalado = d;}
	
	public void setPosicion(double x, double y) {
		this.x += (x - this.x) * intercalado;
		this.y += (y - this.y) * intercalado;
		
		mantenLimites();
		
		colFueraSet = (int)-this.x / tamTile;
		renFueraSet = (int)-this.y / tamTile;
	}
	
	private void mantenLimites() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	
	public void dibuja(Graphics2D g) {
		
		for(
			int ren = renFueraSet;
			ren < renFueraSet + numRenADibujar;
			ren ++){
					
				if(ren >= numRen) break;
				
				for(
					int col = colFueraSet;
						col < colFueraSet + numColADibujar;
						col++) {
					
					if(col >= numCol) break;
					
					if(mapa[ren][col] == 0) continue;
					
					int rc = mapa[ren][col];
					int r = rc / numDeTiles;
					int c = rc % numDeTiles;
					
					g.drawImage(
							tiles[r][c].getImagen(),
							(int)x + col * tamTile,
							(int)y + ren * tamTile,
							null
							);
					}
			}
	}			
				
}
