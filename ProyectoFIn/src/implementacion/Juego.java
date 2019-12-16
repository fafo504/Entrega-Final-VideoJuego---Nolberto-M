package implementacion;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

import clases.Item;
import clases.JugadorAnimado;
import clases.Mapa;
import clases.Obstaculo;
import clases.Puntuacion;
import clases.Tile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Juego extends Application {
	private Scene escena;
	private Group root;
	private Canvas canvas;
	private Canvas gui;
	private GraphicsContext graficos;
	private int puntuacion = 0;
	private double bordeIzq = 0;

	private AnimationTimer loop;
	// private Jugador jugador;
	private JugadorAnimado jugadorAnimado;
	public static boolean derecha = false;
	public static boolean izquierda = false;
	public static boolean arriba = false;
	public static boolean abajo = false;
	public static boolean confirm;
	public static boolean perdio = false;  // atributo para saber cuando el personaje perdio y se termine el ciclo de juego.
	public String nombre = "";
	private long count = 0;
	
	public static HashMap<String, Image> imagenes; // Shift+Ctrl+O
	
	// private ArrayList<Image> imagenes;

	Mapa map;  // Creamos un atributo de tipo mapa
	
	private Canvas bg_canvas;

	
	/*
	 * private int[][] mapa = { {6,4,4,4,4,4,5,0,0,0}, {0,0,0,0,0,0,0,0,0,0},
	 * {0,0,0,0,0,0,2,0,0,0}, {0,0,0,0,0,1,666,0,0,0}, {0,0,0,0,6,4,4,5,0,0},
	 * {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, };
	 */

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage ventana) throws Exception {
		inicializarComponentes();
		graficos = canvas.getGraphicsContext2D();
		root.getChildren().add(bg_canvas);
		root.getChildren().add(canvas);
		root.getChildren().add(gui);
		ventana.setScene(escena);
		ventana.setTitle("Epic adventure - The beginning");
		gestionarEventos();
		ventana.show();
		ventana.setFullScreen(true);
		cicloJuego();
	}

	public void inicializarComponentes() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		// jugador = new Jugador(-50,400,"goku",1);
		jugadorAnimado = new JugadorAnimado(64, 320, "megaman", 1, "correr");
		jugadorAnimado.getJugador().translateYProperty().addListener((obs, viejo, nuevo) -> {
			int dif = nuevo.intValue();
			if (dif >= escena.getHeight() + 96) {
				perder();
			}
		});
		   
		root = new Group();
		root.setManaged(false);
		map = new Mapa(pantalla.getWidth(), pantalla.getHeight(), "mapas//mapa1.csv", 64);
		escena = new Scene(root, pantalla.getWidth(), pantalla.getHeight());
		canvas = new Canvas(escena.getWidth(), escena.getHeight());
		bg_canvas = new Canvas(escena.getWidth(), escena.getHeight());
		gui = new Canvas(escena.getWidth(), escena.getHeight());
		imagenes = new HashMap<String, Image>();

		cargarImagenes();
	}

	public void cargarImagenes() {
		imagenes.put("tilemap", new Image("Assets64.png")); // Tiles para crear el entorno
		imagenes.put("megaman", new Image("pj.png")); //Si borro "Megaman" me tira error.
		imagenes.put("item", new Image("pocima.png"));// item a recolectar
		imagenes.put("bg", new Image("bg.png"));
		imagenes.put("bg2", new Image("bg2.png"));
	}

	public void pintar(double offset) {
		graficos = gui.getGraphicsContext2D();
		graficos.setFill(Color.WHITE);
		graficos.fillRect(0, 0, escena.getWidth(), 10);
		graficos.setFill(Color.BLACK);
		graficos.fillText("Puntuacion: " + puntuacion, 10, 10); //Mostrar en pantalla la puntuacion.
		graficos = canvas.getGraphicsContext2D();
		graficos.setFill(Color.WHITE);
		graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graficos.drawImage(imagenes.get("bg2"), 0, 0, escena.getWidth(), escena.getHeight());
		graficos.drawImage(imagenes.get("bg"), 0, 0, escena.getWidth(), escena.getHeight());
		
		// graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		// jugador.pintar(graficos);
		
		jugadorAnimado.pintar(graficos);
		
		
		/// Pintar tiles
		
		for (int i = 0; i < map.getTiles().size(); i++)
			map.getTiles().get(i).pintar(graficos);

		for (Item x : map.getItems()) {
			x.pintar(graficos);
		}
		
		for(Obstaculo o : map.getObstaculos()) {
			o.pintar(graficos);
		}
	}

	
	public void gestionarEventos() {
		
		// Evento cuando se presiona una tecla
		
		escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent evento) {
				// Aqui tengo que poner el codigo para identificar cuando se presiono una tecla
				switch (evento.getCode().toString()) {
				case "RIGHT": // derecha
					derecha = true;
					break;
				case "LEFT": // izquierda
					izquierda = true;
					break;
				case "UP":
					arriba = true;  //Unico comando que usaremos en el juego
					break;
				case "DOWN":
					abajo = true;
					break;
				case "SPACE":
					// jugador.setVelocidad(10);
					// jugador.setIndiceImagen("goku-furioso");
					break;
				}
			}
		});

		
		//Mismo evento pero para saber cuando se solto la tecla
		escena.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evento) {
				// Aqui tengo que poner el codigo para identificar cuando se soltó una tecla
				switch (evento.getCode().toString()) {
				case "RIGHT": // derecha
					derecha = false;
					break;
				case "LEFT": // derecha
					izquierda = false;
					break;
				case "UP":
					arriba = false;
					break;
				case "DOWN":
					abajo = false;
					break;
				case "ENTER":
					confirm = true;
					break;
				}

			}

		});

	}
	
				//Ciclo del juego.

	public void cicloJuego() {
		final long tiempoInicial = System.nanoTime();
		AnimationTimer animationTimer = new AnimationTimer() {
			// Esta rutina simula un ciclo de 60FPS
			@Override
			public void handle(long tiempoActualNanoSegundos) {
				double t = (tiempoActualNanoSegundos - tiempoInicial) / 1000000000.0;
				bordeIzq = t * (128 + jugadorAnimado.getVelocidad() * 16);
				pintar(bordeIzq);
				actualizar(t, bordeIzq);
				
			}

		};
		loop = animationTimer;
		loop.start(); // Inicia el ciclo
	}

	
			//Refrescar el escenario y actualizar los tiles y mapa
	
	public void actualizar(double t, double offset) {
		puntuacion = jugadorAnimado.getPuntuacion();

		jugadorAnimado.mover(t);

		//Verificar las colisiones en cada tile.
		
		for (Tile tile : map.getTiles()) {
			jugadorAnimado.verificarColisionesTiles(tile);
			tile.actualizar();
			
		}
		
				//Verificar colisiones en obstaculos.
		
		for (Obstaculo obs : map.getObstaculos()) {
			if(jugadorAnimado.verificarColisiones(obs)) {
				jugadorAnimado.getJugador().setVisible(false);
				perder();
			};
			
			//actualizar obstaculos.
			
			obs.actualizar();
		}
		
		//verificar colisiones para recoger items
		
		for (Item item : map.getItems()) {
			jugadorAnimado.verificarColisiones(item);
			item.actualizar();
		}
		
		jugadorAnimado.actualizarAnimacion(t);
		

		/*canvas.setLayoutX(-bordeIzq);
		if (bordeIzq - 50 > jugadorAnimado.getX()) {
			perder();
		}*/
				
		
		// map.actualizarMapa(jugadorAnimado.getY());

	}

	
			//Metodo para determinar cuando el personaje pierde y posteriormente 
			// guardar su puntuacion en un archivo .txt
	public void perder() {
		
		perdio = true;
		loop.stop();
		graficos = gui.getGraphicsContext2D();
		graficos.setFill(Color.BLACK);
		graficos.setFont(new Font(45));

		graficos = gui.getGraphicsContext2D();
		
		//Registrar puntuaciones
		
		FileReader freader;
		
		
		try {
			freader = new FileReader("src//puntuaciones.txt");
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			ArrayList<Puntuacion> lines = new ArrayList<>();
			TextInputDialog dialog = new TextInputDialog(""); //Se muestra el cuadro de dialogo para registrar el nombre del jugador
			
			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				lines.add(new Puntuacion(temp[0], temp[1]));
			}
			reader.close();
			freader.close();
			AnimationTimer timer = new AnimationTimer() {
		        private long time;

		        
		        //Una
		        @Override
		        public void handle(long now) {
		            if (now - time > 1_000_000_000) {
		                count++;
		                if (count == 1) {
		                	
		                    dialog.initStyle(StageStyle.UTILITY);
		                    dialog.setTitle("Input");
		                    dialog.setHeaderText("Ingresa tu nombre");
		                    dialog.setContentText("ingresa tu nombre:");
		                    Platform.runLater(() -> {
		                    	Optional<String> res = dialog.showAndWait();
		                		if(res.isPresent()) {
		                			nombre = res.get();
		                			if(nombre == "") {
		                				nombre = "AAA";
		                			}
		                			lines.add(new Puntuacion(nombre, puntuacion));
		                			Collections.sort(lines, new Comparator<Puntuacion>() {
		                				
		                				//Comparar cuales fueron las mayores puntuaciones y mostrarlas en pantalla

										@Override
										public int compare(Puntuacion o1, Puntuacion o2) {
											
											return o1.puntuacion < o2.puntuacion ? 1 : -1;
										}
		                				
		                			});
		                		}
		                		if(perdio) {graficos.fillText("Mejores puntuaciones", escena.getWidth() / 3 - graficos.getFont().getSize() * 2, escena.getHeight()*0.10);
	                			   int i = 0;
		                			  for(Puntuacion p : lines) {
		                			    	graficos.fillText(p.toString(), escena.getWidth() / 3, escena.getHeight()*0.25 + 50 * (i+1)*0.8);
		                			    	i++;
		                			}
		                			  FileWriter fw;
									try {
										fw = new FileWriter("src//puntuaciones.txt");
										BufferedWriter bf = new BufferedWriter(fw);
										for(int j = 9; j < lines.size() ;j++) {
											lines.remove(j);
			                			}
			                			
										for(Puntuacion p : lines) {
											bf.write(p.print() + "\n");
										}
										bf.close();
										fw.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                		}
		                		
		                    });
		                    
		                }
		                time = now;
		            }

		        }
		    };

		    timer.start();
		   		    
		   
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void checkNombre(TextInputDialog dialog) {

	}
}
