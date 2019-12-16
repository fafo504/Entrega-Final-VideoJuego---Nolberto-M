package clases;

import java.util.HashMap;

import implementacion.Juego;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class JugadorAnimado {
	private Rectangle jugador = new Rectangle();
	private String indiceImagen;
	private double velocidadX;
	private double velocidadY;
	private double gravedad;
	private HashMap<String, Animacion> animaciones;
	private String animacionActual;
	private int puntuacion = 0;

	// Coordenadas para el fragmento de la imagen a pintar
	
	private int xImagen;
	private int yImagen;
	private int anchoImagen;
	private int altoImagen;

	// Flags
	private boolean enSuelo = true;
	private boolean invertir = false;

	public JugadorAnimado(int x, int y, String indiceImagen, int velocidad, String animacionActual) {
		super();
		this.jugador.setTranslateX(x);
		this.jugador.setTranslateY(y);
		this.jugador.setWidth(18);
		this.jugador.setHeight(18);
		jugador.setVisible(true);
		this.jugador.setHeight(18);
		this.indiceImagen = indiceImagen;
		this.velocidadX = 0;
		this.velocidadY = 0;
		this.gravedad = 2;
		this.animacionActual = animacionActual;
		inicializarAnimaciones();
	}

	public double getVelocidad() {
		return velocidadX;
	}

	public void setVelocidad(int velocidad) {
		this.velocidadX = velocidad;
	}

	public double getX() {
		return this.jugador.getTranslateX();
	}

	public void setX(int x) {
		this.jugador.setTranslateX(x);
	}

	public double getY() {
		return this.jugador.getTranslateY();
	}

	public void setY(int y) {
		this.jugador.setTranslateY(y);
	}

	public String getIndiceImagen() {
		return indiceImagen;
	}

	public void setIndiceImagen(String indiceImagen) {
		this.indiceImagen = indiceImagen;
	}

	// Obtener las coordenas del fragmento de la imagen a pintar
	public void actualizarAnimacion(double t) {
		Rectangle coordenadasActuales = this.animaciones.get(animacionActual).calcularFrame(t);
		this.xImagen = (int) coordenadasActuales.getX();
		this.yImagen = (int) coordenadasActuales.getY();
		this.anchoImagen = (int) coordenadasActuales.getWidth();
		this.altoImagen = (int) coordenadasActuales.getHeight();
		this.jugador.setWidth(anchoImagen);
		this.jugador.setHeight(altoImagen);
	}

	public void mover(double t) {
		
		this.jugador.setTranslateY(this.jugador.getTranslateY() + gravedad);
		this.animacionActual = "correr";
		/*if (velocidadX != 0) {
			this.animacionActual = "correr";
		} else {
			this.animacionActual = "descanso";
		}*/

		/*if (Juego.derecha) {
			velocidadX += 0.2;
			if (velocidadX >= 5) {
				velocidadX = 5;
			}
			invertir = false;
		} else if (velocidadX > 0) {
			velocidadX -= (velocidadX / Math.abs(velocidadX)) * velocidadX * 0.75;
			if (velocidadX <= 0.5) {
				velocidadX = 0;
			}
		}

		if (Juego.izquierda) {
			velocidadX -= 0.2;
			if (velocidadX <= -5) {
				velocidadX = -5;
			}
			invertir = true;
		} else if (velocidadX < 0) {
			velocidadX += (velocidadX / Math.abs(velocidadX)) * velocidadX * 0.75;
			if (velocidadX >= -0.5) {
				velocidadX = 0;
			}
		}*/
		


		if (Juego.arriba) {
			gravedad += 0.45;
			velocidadY = -2.7*6;
			this.jugador.setTranslateY(this.jugador.getTranslateY() + velocidadY);
		}else if(enSuelo){
			gravedad = 6;
		}


		
		
		
		/*
		 * if (Juego.abajo) this.jugador.setTranslateY(this.jugador.getTranslateY() +
		 * 1);
		 */
	}

	public void pintar(GraphicsContext graficos) {

		if(!invertir)
		{
			graficos.drawImage(Juego.imagenes.get(this.indiceImagen), this.xImagen, this.yImagen, this.anchoImagen,
					this.altoImagen, this.getX(), this.getY(), this.anchoImagen, this.altoImagen);
		}else {
			graficos.drawImage(Juego.imagenes.get(this.indiceImagen), this.xImagen, this.yImagen, this.anchoImagen,
					this.altoImagen, this.getX() + this.anchoImagen, this.getY(), -this.anchoImagen, this.altoImagen);
		}
	}

	public Node getJugador() {
		return this.jugador;
	};

	public Rectangle obtenerRectangulo() {
		return new Rectangle(this.getX(), this.getY(), this.anchoImagen, this.altoImagen);
	}

	public Rectangle obtenerColisionAbajo() {
		return new Rectangle(this.getX() + this.anchoImagen * 0.4, this.getY() + this.altoImagen * 0.8,
				this.anchoImagen*0.4, this.altoImagen*0.2);
	}

	public Rectangle obtenerColisionDer() {
		return new Rectangle(this.getX() + anchoImagen, this.getY() + this.altoImagen*0.1, 4, this.altoImagen*0.8);
	}

	public Rectangle obtenerColisionIzq() {
		return new Rectangle(this.getX()-2, this.getY() + this.altoImagen*0.1, 4, this.altoImagen*0.8);
	}

	public Rectangle obtenerColisionArriba() {
		return new Rectangle(this.getX() + this.altoImagen*0.1, this.getY(),
				this.anchoImagen - this.anchoImagen * 0.1, this.altoImagen*0.2);
	}

	public void inicializarAnimaciones() {
		animaciones = new HashMap<String, Animacion>();
		Rectangle coordenadasCorrer[] = {

				new Rectangle(128, 93, 55, 56), new Rectangle(228, 93, 55, 56), new Rectangle(328, 93, 55, 56),
				new Rectangle(428, 93, 55, 56), new Rectangle(528, 93, 55, 56), new Rectangle(628, 93, 55, 56)
				/*
				 * new Rectangle(287,224, 75,73), new Rectangle(423,229, 75,68), new
				 * Rectangle(500,229, 75,68), new Rectangle(576,229, 75,68), new
				 * Rectangle(640,229, 75,68), new Rectangle(699,229, 75,68), new
				 * Rectangle(764,229, 75,68), new Rectangle(836,229, 75,73), new
				 * Rectangle(907,229, 75,68)
				 */

		};

		Animacion animacionCorrer = new Animacion("correr", coordenadasCorrer, 0.1);
		animaciones.put("correr", animacionCorrer);

		Rectangle coordenadasDescanso[] = { new Rectangle(30, 12, 45, 62), new Rectangle(130, 12, 45, 62),
				new Rectangle(230, 12, 45, 62), new Rectangle(330, 12, 45, 62) };
		Animacion animacionDescanso = new Animacion("descanso", coordenadasDescanso, 0.2);
		animaciones.put("descanso", animacionDescanso);
	}

	public void verificarColisiones(Item item) {
		if (this.obtenerRectangulo().intersects(item.obtenerRectangulo().getBoundsInLocal()) && !item.isCapturado()) {
			this.puntuacion++;
			item.setCapturado(true);
			item = null;
		}
	}

	public void verificarColisionesTiles(Tile tile) {
		if (this.obtenerColisionAbajo().intersects(tile.getTileRectangulo().getBoundsInParent())) {
			this.jugador.setTranslateY(this.jugador.getTranslateY() - gravedad - 0.15);
			enSuelo = true;
		}

		if (this.obtenerColisionDer().getBoundsInParent().intersects(tile.getTileRectangulo().getBoundsInParent())) {
			this.jugador.setTranslateX(this.jugador.getTranslateX() - (Math.abs(velocidadX) + 0.1));
		}

		if (this.obtenerColisionIzq().getBoundsInParent().intersects(tile.getTileRectangulo().getBoundsInParent())) {
			this.jugador.setTranslateX(this.jugador.getTranslateX() + (Math.abs(velocidadX) + 0.1));
		}

		if (this.obtenerColisionArriba().intersects(tile.getTileRectangulo().getBoundsInParent())) {
			this.jugador.setTranslateY(this.jugador.getTranslateY() + (3 + 0.1));
		}

	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public boolean verificarColisiones(Obstaculo obs) {
		if (this.obtenerColisionAbajo().intersects(obs.getTileRectangulo().getBoundsInParent()) || 
			this.obtenerColisionDer().getBoundsInParent().intersects(obs.getTileRectangulo().getBoundsInParent()) ||
			this.obtenerColisionIzq().getBoundsInParent().intersects(obs.getTileRectangulo().getBoundsInParent()) ||
			this.obtenerColisionArriba().intersects(obs.getTileRectangulo().getBoundsInParent())) {
			return true;
		}
		return false;
	}
}
