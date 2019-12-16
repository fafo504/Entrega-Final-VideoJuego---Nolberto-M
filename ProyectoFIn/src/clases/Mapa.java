package clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Mapa {
	private ArrayList<ArrayList<Integer>> mapa;

	private BufferedReader reader;
	private FileReader freader;

	private int tileSize; //Tamaño de los tiles
	private double Pwidth, width;
	private double Pheight, height;

	private ArrayList<Tile> tiles;
	private ArrayList<Item> items;
	private ArrayList<Obstaculo> obstaculos;

	private double extraHeight;
	private int temp = 0;
	private int offset = 0;

	public Mapa(double d, double e, String path, int tileSize) {
		this.Pwidth = d;
		this.Pheight = e;
		this.tileSize = tileSize;
		cargarMapa(tileSize, path);
		cargarTiles();		
	}

	
			//Leer y cargar los mapas
	
	private void cargarMapa(int tileSize, String path) {
		try {
			freader = new FileReader(path);
			reader = new BufferedReader(freader);
			String line = "";
			ArrayList<int[]> lines = new ArrayList<>();

			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				int[] arreglo = new int[temp.length];
				for (int index = 0; index < temp.length; index++) {
					arreglo[index] = Integer.valueOf(temp[index]);
				}
				lines.add(arreglo);
			}
			width = lines.get(0)[0];
			height = lines.get(1)[0];
			lines.remove(0);
			lines.remove(0);

			extraHeight = Pheight/tileSize - height;
			if (height < Pheight/tileSize) {
				for (int k = (int) height; k < (int) Pheight / tileSize; k++) {
					lines.add(0, new int[(int) width]);
				}
				height = lines.size();
			}

			mapa = new ArrayList<ArrayList<Integer>>();

			for (int i = 0; i < height; i++) {
				mapa.add(new ArrayList<Integer>());
			}

			for (int i = 0; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(i).length; j++) {
					mapa.get(i).add(lines.get(i)[j]);
				}
			}
			reader.close();
			freader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.print("CTM");
		}
	}

	public ArrayList<ArrayList<Integer>> cargar(String path) {
		ArrayList<ArrayList<Integer>> carga = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < height; i++) {
			carga.add(new ArrayList<Integer>());
		}
		
		try {
			freader = new FileReader(path);
			reader = new BufferedReader(freader);
			String line = "";
			ArrayList<int[]> lines = new ArrayList<>();

			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				int[] arreglo = new int[temp.length];
				for (int index = 0; index < temp.length; index++) {
					arreglo[index] = Integer.valueOf(temp[index]);
				}
				lines.add(arreglo);
				
			}
			
			Random generator = new Random(System.currentTimeMillis());
			while(temp == offset) {
				offset = (int)(generator.nextDouble()*(extraHeight));
			}
			temp = offset;
			for (int k = 0; k < (int) offset; k++) {
				lines.add(0, new int[(int) 14]);
			}
			
			for (int k = 0; k < (int)(extraHeight - offset); k++) {
				lines.add(new int[(int) 14]);
			}
			
			for (int i = 0; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(i).length; j++) {
					carga.get(i).add(lines.get(i)[j]);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.print("a");
		}
		return carga;
	}

	public void cargarTiles() {
		items = new ArrayList<Item>();
		tiles = new ArrayList<Tile>();
		obstaculos = new ArrayList<Obstaculo>();
		
		int temp = 0;
		int sel = 0;
		for(int k = 0; k <= 100; k++) {
			Random generator = new Random(System.currentTimeMillis());			
			while(sel == temp) {
				sel = (int)(generator.nextDouble()*(5) + 1);
			}
			
			ArrayList<ArrayList<Integer>> x = cargar("mapas//obs" + String.valueOf(sel) +".csv");
			for (int i = 0; i < height; i++) {
				if(i < x.size()) {
					for (int j = 0; j < x.get(i).size(); j++) {
						mapa.get(i).add(x.get(i).get(j));
					}
				}else {
					for (int j = 0; j < x.get(i).size(); j++) {
						mapa.get(i).add(0);
					}
				}
			}
			
			temp = sel;
		}
		
	/*	x = cargar("mapas//obs2.csv");
		
		for (int i = 0; i < height; i++) {
			if(i < x.size()) {
				for (int j = 0; j < x.get(i).size(); j++) {
					mapa.get(i).add(x.get(i).get(j));
				}
			}else {
				for (int j = 0; j < x.get(0).size(); j++) {
					mapa.get(i).add(0);
				}
			}
		}*/

		for (int i = 0; i < mapa.size(); i++) {
			for (int j = 0; j < mapa.get(i).size(); j++) {
				if (mapa.get(i).get(j) == 9) {
					obstaculos.add(new Obstaculo(mapa.get(i).get(j), j * tileSize, i * tileSize, "tilemap"));
				}else if (mapa.get(i).get(j) > 0 && mapa.get(i).get(0) != 99 && mapa.get(i).get(0) != 9) {
					tiles.add(new Tile(mapa.get(i).get(j), j * tileSize, i * tileSize, "tilemap", 0));
				} else if (mapa.get(i).get(j) < 0) {
					items.add(new Item(tileSize * (j + 0.35), tileSize * (i + 0.35), 0, 0, "item"));
				}
			}
		}
	}

	public void agregarTile(int i, int j) {
		tiles.add(new Tile(mapa.get(i).get(j), j * tileSize, i * tileSize, "tilemap", 0));

	}

	public void actualizarMapa(double jugadorY) {

		int posY = (int) Math.floor((Math.random() * this.getHeightPx() + jugadorY - 1) / tileSize);

		width = mapa.size();
		tiles = new ArrayList<Tile>();
		items = new ArrayList<Item>();
		for (int i = 0; i < mapa.size(); i++) {
			for (int j = 0; j < mapa.get(i).size(); j++) {
				if (mapa.get(i).get(j) > 0 && mapa.get(i).get(0) != 99) {
					tiles.add(new Tile(mapa.get(i).get(j), j * tileSize, i * tileSize, "tilemap", 0));
				} else if (mapa.get(i).get(j) < 0) {
					items.add(new Item(tileSize * (j + 0.35), tileSize *i - 32, 0, 0, "item"));
				} 
			}
		}
		// tiles.remove(0);

	}

	public ArrayList<ArrayList<Integer>> getMapa() {
		return mapa;
	}

	public double getWidth() {
		return width;
	}

	public double getWidthPx() {
		return mapa.get(0).size() * tileSize;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public double getHeight() {
		return height;
	}

	public double getHeightPx() {
		return height * tileSize;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}
}
