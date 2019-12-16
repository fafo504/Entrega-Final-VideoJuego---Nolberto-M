package clases;

public class Puntuacion {
	public String nombre;
	public int puntuacion;
	
	public Puntuacion(String n, String puntuacion) {
		this.nombre = n;
		this.puntuacion = Integer.valueOf(puntuacion);
	}
	
	public Puntuacion(String n, int puntuacion) {
		this.nombre = n;
		this.puntuacion = puntuacion;
	}
	
	@Override
	public String toString() {
		return this.nombre + " " + String.valueOf(this.puntuacion);
	}
	
	public String print() {
		return this.nombre + "," + String.valueOf(this.puntuacion);
	}
}
