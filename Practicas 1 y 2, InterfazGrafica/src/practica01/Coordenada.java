package practica01;

public class Coordenada {
	public int coordenada_x;
	public int coordenada_y;

	public Coordenada(int x, int y) {
		this.coordenada_x = x;
        this.coordenada_y = y;
	}
	
	public String getCoordenadas() {
    	return "(" + getX() + ", " + getY() + " )\n";
    }

	public int getX() {
		return coordenada_x;
	}

	public void setX(int coordenada_x) {
		this.coordenada_x = coordenada_x;
	}

	public int getY() {
		return coordenada_y;
	}

	public void setY(int coordenada_y) {
		this.coordenada_y = coordenada_y;
	}
	
}
