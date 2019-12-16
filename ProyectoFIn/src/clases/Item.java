package clases;

import implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Item {
	private Rectangle item = new Rectangle();
	private String indiceImagen;
	private boolean capturado;   //Verdadera cuando el item fue recolectado, falso en caso contrario
	public Item(double x, double d, int ancho, int alto, String indiceImagen) {
		super();
		item.setTranslateX(x);
		item.setTranslateY(d);
		item.setHeight(alto);
		item.setHeight(ancho);
		this.indiceImagen = indiceImagen;
	}
	
	public void pintar(GraphicsContext graficos) {
		if (!capturado)
			graficos.drawImage(Juego.imagenes.get(indiceImagen), this.getX(), this.getY() - 32);
		//graficos.fillRect(this.x, this.y, 18, 18);
	}
	
	
	//Coordenadda x y y de la imagen y tambien las dimensiones  para lograr colisionar
	public Rectangle obtenerRectangulo() {
		return new Rectangle(this.getX(), this.getY(), 18, 18);
	}

	public boolean isCapturado() {
		return capturado;
	}
	
	//Metodos get
	
	public double getX() {
		return item.getTranslateX();
	}
	
	public double getY() {
		return item.getTranslateY();
	}
	
	public void actualizar() {
		item.setTranslateX(item.getTranslateX() - 3);
	}
	
	public void setCapturado(boolean capturado) {
		this.capturado = capturado;
	}
}
