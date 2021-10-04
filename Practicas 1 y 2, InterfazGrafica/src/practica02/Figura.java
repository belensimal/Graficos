package practica02;

import java.awt.Color;
import java.util.ArrayList;

public class Figura {

	//Extremos de las líneas que usaré para las transformaciones
	ArrayList <Linea> extremos_lineas = new ArrayList <Linea>();
	//Color actual de la figura
	Color colorActual;

	public int x_ref;
	public int y_ref;

	/**
	 * Conjunto de líneas que forman una figura
	 */
	public Figura (int x_ref, int y_ref, ArrayList <Linea> l) {
		colorActual=Color.LIGHT_GRAY;
		copiaArray(l);
		this.x_ref=x_ref;
		this.y_ref=y_ref;
	}

	private void copiaArray(ArrayList<Linea> l) {
		for (Linea linea : l) {
			extremos_lineas.add(linea);
		}
	}

	public void anhadeLinea(int x_ini, int y_ini, int x_fin, int y_fin) {
		Linea l = new Linea(x_ini, y_ini, x_fin, y_fin);
		extremos_lineas.add(l);
		pintaLinea(x_ini, y_ini, x_fin, y_fin);
	}

	public void sustituyeLineas(ArrayList<Linea> puntosModificados) {
		extremos_lineas.clear();
		extremos_lineas = puntosModificados;
	}

	public void setColor (Color color) {
		colorActual = color;
	}

	public void eliminaAntiguaFigura() {
		for (Linea l : extremos_lineas) {
			borraLinea(l.getX_ini(), l.getY_ini(), l.getX_fin(), l.getY_fin());
		}  
	}

	public void pintaFigura() {
		for (Linea l : extremos_lineas) {
			pintaLinea(l.getX_ini(), l.getY_ini(), l.getX_fin(), l.getY_fin());
		}
	}

	public int getTamañoLineas() {
		return extremos_lineas.size();
	}


	/**************************************************************************************************/

	/**
	 * TRANSLACIÓN
	 * T(x, y) = (x + xt, y + yt)
	 */

	public void traslacion (int xt, int yt) {
		eliminaAntiguaFigura();

		//Me salto la ejecución si no se van a producir cambios
		if ( xt != 0 || yt != 0 ) {
			ArrayList<Linea> puntosTrasladados = new ArrayList<Linea>();
			int x_ini, y_ini, x_fin, y_fin;
			int x_ini_tras, y_ini_tras, x_fin_tras, y_fin_tras;
			Linea newLinea, l;

			for (int i = 0 ; i < extremos_lineas.size() ; i++) {
				l = extremos_lineas.get(i);
				x_ini = l.getX_ini();
				y_ini = l.getY_ini();
				x_fin = l.getX_fin();
				y_fin = l.getY_fin();

				x_ini_tras = x_ini + xt;
				y_ini_tras = y_ini + yt;
				x_fin_tras = x_fin + xt;
				y_fin_tras = y_fin + yt;

				newLinea = new Linea(x_ini_tras, y_ini_tras, x_fin_tras, y_fin_tras);
				puntosTrasladados.add(newLinea);
			}
			sustituyeLineas(puntosTrasladados);
		}
		pintaFigura();
	}


	/**
	 * ESCALADO
	 * S(x, y) = (x*s, y*s)
	 */

	public void escalado (double s) {
		eliminaAntiguaFigura();

		//Me salto la ejecución si no se van a producir cambios
		if ( s != 1 ) {
			ArrayList<Linea> puntosEscalados = new ArrayList<Linea>();

			int x_ini, y_ini, x_fin, y_fin;
			int x_ini_t, y_ini_t, x_fin_t, y_fin_t;
			Linea newLinea;

			for (Linea l : extremos_lineas) {
				
				x_ini = l.getX_ini();
				y_ini = l.getY_ini();
				x_fin = l.getX_fin();
				y_fin = l.getY_fin();

				x_ini_t = (int)Math.round((x_ini - x_ref) * s + x_ref);
				y_ini_t = (int)Math.round((y_ini - y_ref) * s + y_ref);
				x_fin_t = (int)Math.round((x_fin - x_ref) * s + x_ref);
				y_fin_t = (int)Math.round((y_fin - y_ref) * s + y_ref);

				newLinea = new Linea(x_ini_t, y_ini_t, x_fin_t, y_fin_t);
				puntosEscalados.add(newLinea);
			}
			sustituyeLineas(puntosEscalados);
		}
		pintaFigura();
	}

	/**
	 * ROTACION
	 * R(x, y) = (x*cos(alpha)-y*sen(alpha), x*sen(alpha)+y*cos(alpha))
	 */

	public void rotacion (int alpha) {
		eliminaAntiguaFigura();
		
		//Me salto la ejecución si no se van a producir cambios
		if ( alpha != 0 ) {
			ArrayList<Linea> puntosRotados = new ArrayList<Linea>();
			int x_ini, y_ini, x_fin, y_fin;
			int x_ini_r, y_ini_r, x_fin_r, y_fin_r;
			Linea newLinea;

			for (Linea l : extremos_lineas) {
				x_ini = l.getX_ini();
				y_ini = l.getY_ini();
				x_fin = l.getX_fin();
				y_fin = l.getY_fin();


				//y = (((xPunto - xReferencia) * sin(angulo)) + ((yPunto - yReferencia)*cos(angulo))) + yReferencia

				x_ini_r = (int)Math.round((x_ini-x_ref) * Math.cos(alpha * Math.PI/180.0) - (y_ini-y_ref) * Math.sin(-alpha * Math.PI/180.0)) + x_ref;
				y_ini_r = (int)Math.round((x_ini-x_ref) * Math.sin(-alpha * Math.PI/180.0) + (y_ini-y_ref) * Math.cos(alpha * Math.PI/180.0)) + y_ref;
				x_fin_r = (int)Math.round((x_fin-x_ref) * Math.cos(alpha * Math.PI/180.0) - (y_fin-y_ref) * Math.sin(-alpha * Math.PI/180.0)) + x_ref;
				y_fin_r = (int)Math.round((x_fin-x_ref) * Math.sin(-alpha * Math.PI/180.0) + (y_fin-y_ref) * Math.cos(alpha * Math.PI/180.0)) + y_ref;

				newLinea = new Linea(x_ini_r, y_ini_r, x_fin_r, y_fin_r);
				puntosRotados.add(newLinea);
			}
			sustituyeLineas(puntosRotados);
		}
		pintaFigura();
	}

	/**
	 * CIZALLA
	 * C(x, y) = (x + y*xc, x*yc + y)
	 */ 
	public void cizalla (int xc, int yc) {
		eliminaAntiguaFigura();
		//Me salto la ejecución si no se van a producir cambios
		if ( xc!=0 || yc!=0 ) {
			ArrayList<Linea> puntosCizallados = new ArrayList<Linea>();
			int x_ini, y_ini, x_fin, y_fin;
			int x_ini_c, y_ini_c, x_fin_c, y_fin_c;
			Linea newLinea;

			for (Linea l : extremos_lineas) {
				x_ini = l.getX_ini();
				y_ini = l.getY_ini();
				x_fin = l.getX_fin();
				y_fin = l.getY_fin();

				x_ini_c = ((x_ini-x_ref) + (y_ini-y_ref) * xc) + x_ref;
				y_ini_c = ((x_ini-x_ref) * yc + (y_ini-y_ref)) + y_ref;
				x_fin_c = ((x_fin-x_ref) + (y_fin-y_ref) * xc) + x_ref;
				y_fin_c = ((x_fin-x_ref) * yc + (y_fin-y_ref)) + y_ref;

				newLinea = new Linea(x_ini_c, y_ini_c, x_fin_c, y_fin_c);
				puntosCizallados.add(newLinea);
			}
			sustituyeLineas(puntosCizallados);
		}
		pintaFigura();
	}


	/**
	 * REFLEXION POR EL EJE X
	 * F(x, y) = (x, -y)
	 */ 
	public void reflexionX () {
		eliminaAntiguaFigura();
		ArrayList<Linea> puntosReflejadosX = new ArrayList<Linea>();
		int x_ini, y_ini, x_fin, y_fin;
		int x_ini_r, y_ini_r, x_fin_r, y_fin_r;
		Linea newLinea;

		for (Linea l : extremos_lineas) {
			x_ini = l.getX_ini();
			y_ini = l.getY_ini();
			x_fin = l.getX_fin();
			y_fin = l.getY_fin();

			x_ini_r = (x_ini - x_ref) + x_ref;
			y_ini_r = (-1 * (y_ini-y_ref)) + y_ref;
			x_fin_r = (x_fin - x_ref) + x_ref;
			y_fin_r = (-1 * (y_fin - y_ref)) + y_ref;

			newLinea = new Linea(x_ini_r, y_ini_r, x_fin_r, y_fin_r);
			puntosReflejadosX.add(newLinea);
		}
		sustituyeLineas(puntosReflejadosX);
		pintaFigura();
	}

	/**
	 * REFLEXION POR EL EJE Y
	 * F(x, y) = (-x, y)
	 */ 
	public void reflexionY () {
		eliminaAntiguaFigura();
		ArrayList<Linea> puntosReflejadosY = new ArrayList<Linea>();
		int x_ini, y_ini, x_fin, y_fin;
		int x_ini_r, y_ini_r, x_fin_r, y_fin_r;
		Linea newLinea;

		for (Linea l : extremos_lineas) {
			x_ini = l.getX_ini();
			y_ini = l.getY_ini();
			x_fin = l.getX_fin();
			y_fin = l.getY_fin();

			x_ini_r = (-1 * (x_ini - x_ref)) + x_ref;
			y_ini_r = (y_ini - y_ref) + y_ref;
			x_fin_r = (-1 * (x_fin - x_ref)) + x_ref;
			y_fin_r = (y_fin - y_ref) + y_ref;

			newLinea = new Linea(x_ini_r, y_ini_r, x_fin_r, y_fin_r);
			puntosReflejadosY.add(newLinea);
		}
		sustituyeLineas(puntosReflejadosY);
		pintaFigura();
	} 

	/**
	 * REFLEXION RESPECTO AL ORIGEN
	 * F(x, y) = (-x, -y)
	 */ 
	public void reflexionOrigen () {
		eliminaAntiguaFigura();
		ArrayList<Linea> puntosReflejadosY = new ArrayList<Linea>();
		int x_ini, y_ini, x_fin, y_fin;
		int x_ini_r, y_ini_r, x_fin_r, y_fin_r;
		Linea newLinea;

		for (Linea l : extremos_lineas) {
			x_ini = l.getX_ini();
			y_ini = l.getY_ini();
			x_fin = l.getX_fin();
			y_fin = l.getY_fin();

			x_ini_r = (-1 * (x_ini - x_ref)) + x_ref;
			y_ini_r = (-1 * (y_ini - y_ref)) + y_ref;
			x_fin_r = (-1 * (x_fin - x_ref)) + x_ref;
			y_fin_r = (-1 * (y_fin - y_ref)) + y_ref;

			newLinea = new Linea(x_ini_r, y_ini_r, x_fin_r, y_fin_r);
			puntosReflejadosY.add(newLinea);
		}
		sustituyeLineas(puntosReflejadosY);
		pintaFigura();
	} 

	/**************************************************************************************************/

	/**
	 * Algoritmo de línea
	 **/ 
	public void pintaLinea(int x_inicial, int y_inicial, int x_final, int y_final){
		//double m; //Pendiente de la recta
		int e; //Valor inicial de error
		//double ne; //Valor inicial de error //int¿?

		int x; //x inicial
		int y; //y inicial
		int x_increment; //Incremento en x
		int y_increment; //Incremento en y
		int s_x;
		int s_y;
		int intercambio;

		//Inicializo las variables
		x = x_inicial;
		y = y_inicial;
		x_increment = Math.abs(x_final - x);
		y_increment = Math.abs(y_final - y);

		s_x = signo(x_final - x_inicial);
		s_y = signo(y_final - y_inicial);

		//Interchange x_increment and y_increment, depending on the slope of the line
		if (y_increment > x_increment) {
			int temp = x_increment;
			x_increment = y_increment;
			y_increment = temp;
			intercambio = 1;
		} else {
			intercambio = 0;
		}

		//Initialize the error term to compensate for a nonzero intercept
		e = 2 * y_increment - x_increment;

		//Main loop
		for (int i = 0 ; i<= x_increment ; i++) {
			try {
				InterfazControl.pixel_grid[(x+y*InterfazControl.ANCHO_PX)].pintaColor(colorActual);
			}
			catch(IndexOutOfBoundsException e1) {}
			while (e > 0) {
				if (intercambio == 1) {
					x = (x + s_x);
				} else {
					y = (y + s_y);
				}
				e = e - 2 * x_increment;
			}

			if (intercambio == 1) {
				y = y + s_y;
			} else {
				x = x + s_x;
			}
			e = e + 2 * y_increment;
		}
	}

	/**
	 * Algoritmo de línea
	 **/ 
	public void borraLinea(int x_inicial, int y_inicial, int x_final, int y_final){
		//double m; //Pendiente de la recta
		int e; //Valor inicial de error
		//double ne; //Valor inicial de error //int¿?

		int x; //x inicial
		int y; //y inicial
		int x_increment; //Incremento en x
		int y_increment; //Incremento en y
		int s_x;
		int s_y;
		int intercambio;

		//Inicializo las variables
		x = x_inicial;
		y = y_inicial;
		x_increment = Math.abs(x_final - x);
		y_increment = Math.abs(y_final - y);

		s_x = signo(x_final - x_inicial);
		s_y = signo(y_final - y_inicial);

		//Interchange x_increment and y_increment, depending on the slope of the line
		if (y_increment > x_increment) {
			int temp = x_increment;
			x_increment = y_increment;
			y_increment = temp;
			intercambio = 1;
		} else {
			intercambio = 0;
		}

		//Initialize the error term to compensate for a nonzero intercept
		e = 2 * y_increment - x_increment;

		//Main loop
		for (int i = 0 ; i<= x_increment ; i++) {
			try {
				InterfazControl.pixel_grid[(x+y*InterfazControl.ANCHO_PX)].borraColor();
			}
			catch(IndexOutOfBoundsException e1) {}
			while (e > 0) {
				if (intercambio == 1) {
					x = (x + s_x);
				} else {
					y = (y + s_y);
				}
				e = e - 2 * x_increment;
			}

			if (intercambio == 1) {
				y = y + s_y;
			} else {
				x = x + s_x;
			}
			e = e + 2 * y_increment;
		}
	} 

	/**
	 * Método que calcula el signo de un número entero
	 * @param a int del que queremos saber el signo
	 * @return 0 si a = 0
	 * 		   -1 si a < 0
	 *         1 si a > 0
	 */
	public int signo(int a) {
		if (a < 0) return -1;
		if (a > 0) return 1;
		return 0;
	}

	public void imprime_lineas() {
		for (Linea l : extremos_lineas) {
			System.out.println("Linea: ini " + l.getX_ini() + ", " + l.getY_ini() + " fin " + l.getX_fin() + ", " + l.getY_fin());
		}
	}

}
