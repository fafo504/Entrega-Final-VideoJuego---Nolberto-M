package clases;

import implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Obstaculo {
	private Rectangle obs = new Rectangle();
	//Parametros dentro de la imagen principal
	private int altoImagen;
	private int anchoImagen;
	private int xImagen;
	private int yImagen;
	private String indiceImagen;
	
	public Obstaculo(int tipoTile,int x, int y, String indiceImagen){
		obs.setTranslateX(x);
		obs.setTranslateY(y);
		obs.setWidth(64);
		obs.setHeight(64);
		this.altoImagen = 64;
		this.anchoImagen = 64;
		this.xImagen = 704;
		this.yImagen = 512;
		this.indiceImagen = indiceImagen;
	}
	
	public double getX() {
		return obs.getTranslateX();
	}
	public void setX(int x) {
		obs.setTranslateX(x);
	}
	public double getY() {
		return obs.getTranslateY();
	}
	public void setY(int y) {
		obs.setTranslateY(y);
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
	
	public void actualizar() {
		obs.setTranslateX(obs.getTranslateX() - 3);
	}
	
	public Rectangle getTileRectangulo()
	{
		return new Rectangle(this.getX()+16, this.getY()+16, this.obs.getWidth() - 32, this.obs.getHeight()+16);
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
