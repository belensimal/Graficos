package practica02;

public class Linea {

	private int x_ini, y_ini, x_fin, y_fin;
	
	public Linea (int x_ini, int y_ini, int x_fin, int y_fin) {
		this.x_ini = x_ini;
		this.x_fin = x_fin;
		this.y_ini = y_ini;
		this.y_fin = y_fin;
	}
	
	public int getX_ini() {
		return x_ini;
	}

	public int getY_ini() {
		return y_ini;
	}

	public int getX_fin() {
		return x_fin;
	}

	public int getY_fin() {
		return y_fin;
	}
}