package clases;

import implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Tile {
	private Rectangle tile = new Rectangle();
	//Parametros dentro de la imagen principal
	private int altoImagen;
	private int anchoImagen;
	private int xImagen;
	private int yImagen;
	private String indiceImagen;
	private int velocidad;

	//Constructor de tile
	public Tile(int x, int y, int anchoImagen, int altoImagen, int xImagen, int yImagen, String indiceImagen,
			int velocidad) {
		super();
		tile.setTranslateX(x);
		tile.setTranslateY(y);
		tile.setWidth(10);
		tile.setHeight(10);
		this.altoImagen = altoImagen;
		this.anchoImagen = anchoImagen;
		this.xImagen = xImagen;
		this.yImagen = yImagen;
		this.indiceImagen = indiceImagen;
		this.velocidad = velocidad;
	}
	
	public Tile(int tipoTile,int x, int y, String indiceImagen, int velocidad){
		tile.setTranslateX(x);
		tile.setTranslateY(y);
		tile.setWidth(64);
		tile.setHeight(64);
		this.indiceImagen = indiceImagen;
		this.velocidad = velocidad;
		//this.invertir = invertir;
		switch(tipoTile){
			case 1:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 128;
				this.yImagen = 0;
				break;
			case 2:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 192;
				this.yImagen = 0;
				break;
			case 3:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 256;
				this.yImagen = 0;
				break;
			case 4:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 384;
				this.yImagen = 192;
				break;
			case 5:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 192;
				this.yImagen = 64;
				break;
			case 6:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 256;
				this.yImagen = 256;
				break;
			case 7:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 128;
				this.yImagen = 256;
				break;
			case 8:
				this.altoImagen = 64;
				this.anchoImagen = 64;
				this.xImagen = 576;
				this.yImagen = 128;
				break;
		}
	}
	
	//Mover a la izquierda 3 unidades, por cada item del arraylist items, revisa si colisionan.
	
	public void actualizar() {
		tile.setTranslateX(tile.getTranslateX() - 3);
	}
	
	public double getX() {
		return tile.getTranslateX();
	}
	public void setX(int x) {
		tile.setTranslateX(x);
	}
	public double getY() {
		return tile.getTranslateY();
	}
	public void setY(int y) {
		tile.setTranslateY(y);
	}
	public int getAltoImagen() {
		return altoImagen;
	}
	public void setAltoImagen(int altoImagen) {
		this.altoImagen = altoImagen;
	}
	public int getAnchoImagen() {
		return anchoImagen;
	}
	public void setAnchoImagen(int anchoImagen) {
		this.anchoImagen = anchoImagen;
	}
	public int getxImagen() {
		return xImagen;
	}
	public void setxImagen(int xImagen) {
		this.xImagen = xImagen;
	}
	public int getyImagen() {
		return yImagen;
	}
	public void setyImagen(int yImagen) {
		this.yImagen = yImagen;
	}
	public String getIndiceImagen() {
		return indiceImagen;
	}
	public void setIndiceImagen(String indiceImagen) {
		this.indiceImagen = indiceImagen;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public Rectangle getTileRectangulo()
	{
		return this.tile;
	}
	
	public void pintar(GraphicsContext graficos) {
			graficos.drawImage(
				Juego.imagenes.get(this.indiceImagen), 
				this.xImagen, this.yImagen, 
				this.anchoImagen, this.altoImagen, 
				this.getX(), this.getY(),
				this.anchoImagen, this.altoImagen
			);
			
			/*
			 * graficos.drawImage(
				Juego.imagenes.get(this.indiceImagen), 
				this.xImagen, this.yImagen, 
				this.anchoImagen, this.altoImagen, 
				this.x + (invertir==-1?70:0), this.y,
				this.anchoImagen*invertir, this.altoImagen
			);*/
		
	}
	
	
	
}

/*
if (condicion)
	verdadero
else 
	falso
	
	
condicion?verdadero:falso;*/
