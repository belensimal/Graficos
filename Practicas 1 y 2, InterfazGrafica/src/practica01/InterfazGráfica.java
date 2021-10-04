package practica01;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


@SuppressWarnings("serial")
public class InterfazGráfica extends JPanel implements MouseListener {

	Pixel pixel;
	static Pixel pixel_grid[];
	JTextArea txtArea;

	//Variables de linea
	private static int x_ini;
	private static int y_ini;
	private int x_fin;
	private int y_fin;
	private static int x_ult;
	private static int y_ult;

	//Variables para el grid de Pixels
	static final int TAMAÑO_PX=12;
	static final int ANCHO_PX=80;
	static final int ALTO_PX=60;
	static final int TOTAL_PX=ANCHO_PX*ALTO_PX;
	static final int ALTO_VENTANA=ALTO_PX*TAMAÑO_PX;
	static final int ANCHO_VENTANA=ANCHO_PX*TAMAÑO_PX;

	static //Contador para guardar los nombres de las lineas
	int contador_lineas = 0;
	static int contador_circunferencias = 0;

	//Lista de lineas creadas
	public static ArrayList <ArrayList <Coordenada>> lineas = new ArrayList<ArrayList <Coordenada>>();
	/**
	 * Crea la interfaz gráfica
	 */
	public InterfazGráfica() {

		super(new GridLayout(ALTO_PX,ANCHO_PX));

		pixel_grid=new Pixel[TOTAL_PX];

		for(int i=0;i<TOTAL_PX;i++) {
			pixel = new Pixel(Color.BLACK);
			pixel_grid[i]=pixel;
			add(pixel);
		}

		//Register for mouse events on the panel.
		addMouseListener(this);
		setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));

		InterfazGráfica.x_ini=-1;

	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static void initialize() {

		//Create and set up the window.
		JFrame frame = new JFrame("LINE DRAWING ALGORITHMS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		//Create and set up the content pane.
		JComponent newContentPane = new InterfazGráfica();
		newContentPane.setOpaque(true); //tiene que ser opaco
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		int x_raton;
		int y_raton;

		x_raton=e.getX();
		x_raton=x_raton/TAMAÑO_PX;

		y_raton=e.getY();
		y_raton=y_raton/TAMAÑO_PX;
		
		x_ult=x_raton;
		y_ult=y_raton;
		
		String mensaje = "Última coordenada seleccionada es (" + x_ult + "," + y_ult + ")";
		InterfazControl.txtpnNoHaSeleccionado.setText(mensaje);

		//Pinta el pixel
		pixel_grid[(x_raton+y_raton*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);


		if (x_ini==-1) {
			x_ini=x_raton;
			y_ini=y_raton;
		} else {
			x_fin=x_raton;
			y_fin=y_raton;

			//Creo y pinto la linea
			ArrayList<Coordenada> linea = null;


			//Añado la nueva linea a la lista de lineas
			//lineas.add(linea);

			switch(InterfazControl.comboBox.getSelectedIndex()) {
			case 0: //Slope Intercept básico
				linea = slopeInterceptBasico();
				break;
			case 1: //Slope Intercept modificado
				linea = slopeInterceptModificado();
				break;
			case 2: //Algoritmo DDA
				linea = dda();
				break;
			case 3: //Algoritmo de Bresenham (aritmética real)
				linea = bresenhamBasico();
				break;
			case 4: //Algoritmo de Bresenham (aritmética entera)
				linea = bresenhamGeneralizado();
				break;
			default:
				break;
			}
			//if(cmbOS.getSelectedIndex() == 0)
			
			if (linea != null) {

				InterfazControl.listModel.addElement("Linea " + contador_lineas);
				contador_lineas++;
				lineas.add(linea);
			} else {
				System.out.println("ERROR");
			}

		}
	}

	/**************************
	 * Line Drawing Algorithms *
	 *         y=mx+b          *
	 ***************************/


	/**
	 * Slope Intercept Basico
	 **/
	public ArrayList<Coordenada> slopeInterceptBasico() {
		//Creo una lista de puntos (linea)
		ArrayList <Coordenada> pixels_linea = new ArrayList<Coordenada>();

		double m; //Pendiente de la recta
		double b; //Punto de intercepcion en la ordenada (Eje Y) para x=0
		int x; //x inicial
		int y; //y inicial
		double x_increment; //Incremento en x
		double y_increment; //Incremento en y

		x = x_ini;
		y = y_ini;
		x_increment = x_fin - x_ini;
		y_increment = y_fin - y_ini;
		m = y_increment / x_increment;
		b = y_ini - m * x_ini;
		while (x <= x_fin) {
			//Se pintan todos los pixels del intervalo con la misma x
			pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

			//Añado el pixel a la linea
			Coordenada c= new Coordenada(x,y);
			pixels_linea.add(c);

			//Actualizo x, y
			x = x + 1;
			y = (int)Math.round(m*x+b);
		}

		//Como la próxima vez que se forme una recta necesitamos a -1 el pixel inicial
		InterfazGráfica.x_ini=-1; //solo haría falta este

		return pixels_linea;

	}




	/**
	 * Slope Intercept Modificado
	 **/
	public ArrayList<Coordenada> slopeInterceptModificado() {
		//Creo una lista de puntos (linea)
		ArrayList <Coordenada> pixels_linea = new ArrayList<Coordenada>();

		double m; //Pendiente de la recta
		double b; //Punto de intercepcion en la ordenada (Eje Y) para x=0
		int x; //x inicial
		int y; //y inicial
		double x_increment; //Incremento en x
		double y_increment; //Incremento en y

		//Si la linea va en sentido izquierdo
		if (x_fin < x_ini) {
			//Cambiamos los puntos de inicio y fin
			int temp = x_ini;
			x_ini=x_fin;
			x_fin=temp;

			temp = y_ini;
			y_ini=y_fin;
			y_fin=temp;
			//Ahora la linea tiene la misma dirección pero sentido contrario
		}

		//Inicialización de variables
		x = x_ini;
		y = y_ini;
		x_increment = x_fin - x_ini;
		y_increment = y_fin - y_ini;
		m = y_increment / x_increment;
		b = y - m * x;

		//Si la linea es vertical (x_ini = x_fin)
		if (x_increment == 0) {

			//Si la linea sube
			if (y_increment > 0) {
				//Para cada y en el intervalo [y_ini, y_fin]
				while (y <= y_fin) { 
					//Se pintan todos los pixels del intervalo con la misma x
					pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

					//Añado el pixel a la linea
					Coordenada c= new Coordenada(x,y);
					pixels_linea.add(c);

					//Se aumenta la y un pixel
					y = y + 1;
				}

				//Si la línea baja o es un punto (y_increment <= 0)
			} else {
				//Para cada y en el intervalo [y_ini, y_fin]
				while (y >= y_fin) {
					//Se pintan todos los pixels del intervalo con la misma x
					pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

					//Añado el pixel a la linea
					Coordenada c= new Coordenada(x,y);
					pixels_linea.add(c);

					//Se decrementa la y un pixel
					y = y - 1;
				}
			}

			//Si la línea no es vertical (y_ini = y_fin)
		} else {
			//Si la pendiente es mayor de 1 (y_increment > x_increment)
			//La linea pasa por encima de la diagonal en los cuadrantes 1 y 4, y por debajo en los cuadrantes 2 y 3.
			if (Math.abs(m) > 1) {
				//swap(x, y);
				int temp = x;
				x=y;
				y=temp;
				m=1/m;
				b = y - m * x; //Actualizo b
				//Si la linea está en los cuadrantes 1 o 3
				if (m < 0) {
					while (x > y_fin) {
						//Se pintan todos los pixels del intervalo
						pixel_grid[(y+x*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

						//Añado el pixel a la linea
						Coordenada c= new Coordenada(x,y);
						pixels_linea.add(c);

						//Actualizo x, y
						x = x - 1;
						y = (int) Math.round(m*x+b);
					}
					//Si la linea está en los cuadrantes 2 o 4
				} else { 
					while (x < y_fin) {
						//Se pintan todos los pixels del intervalo
						pixel_grid[(y+x*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

						//Añado el pixel a la linea
						Coordenada c= new Coordenada(x,y);
						pixels_linea.add(c);

						//Actualizo x, y
						x = x + 1;
						y = (int) Math.round(m*x+b);
					}
				}

				//Si la línea es horizontal, y va a ser b en cada caso
				//Si la pendiente es menor o igual a 1 (y_increment <= x_increment). La linea puede pasar por las diagonales,
				//o por debajo de la diagonal en los cuadrantes 1 y 4, y por encima en los cuadrantes 2 y 3,
			} else {

				//Pixel aux;
				//Para cada x en el intervalo [x_ini, x_fin]
				while (x <= x_fin) {
					//Se pintan todos los pixels del intervalo
					pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

					//Añado el pixel a la linea
					Coordenada c= new Coordenada(x,y);
					pixels_linea.add(c);

					//Actualizo x, y
					x = x + 1;
					y = (int) Math.round(m*x+b);
				}
			}
		}


		//Como la próxima vez que se forme una recta necesitamos a -1 el pixel inicial
		InterfazGráfica.x_ini=-1; //solo haría falta este

		return pixels_linea;
	}




	/**
	 * Digital Differential Analyzer (DDA)
	 **/
	public ArrayList <Coordenada> dda(){
		ArrayList <Coordenada> pixels_linea = new ArrayList<Coordenada>();

		double x; //x inicial
		double y; //y inicial
		double x_increment; //Incremento en x
		double y_increment; //Incremento en y
		int x_aux; //x en int
		int y_aux; //y en int

		x_increment = x_fin - x_ini;
		y_increment = y_fin - y_ini;


		double M = Math.max(Math.abs(x_increment), Math.abs(y_increment));

		x_increment = x_increment/M;
		y_increment = y_increment/M;

		x = x_ini + 0.5;
		y = y_ini + 0.5;

		for (int i=0 ; i<=M ;  i++ ) {
			x_aux=(int)Math.floor(x);
			y_aux=(int)Math.floor(y);
			pixel_grid[(x_aux+y_aux*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

			//Añado el pixel a la linea
			Coordenada c= new Coordenada(x_aux,y_aux);
			pixels_linea.add(c);

			x += x_increment; 
			y += y_increment;
		}

		//Como la próxima vez que se forme una recta necesitamos a -1 el pixel inicial
		InterfazGráfica.x_ini=-1; //solo haría falta este

		return pixels_linea;
	}


	/**
	 * Bresenham básico
	 **/
	public ArrayList <Coordenada> bresenhamBasico (){
		ArrayList <Coordenada> pixels_linea = new ArrayList<Coordenada>();

		double m; //Pendiente de la recta
		double e; //Valor inicial de error
		int x; //x inicial
		int y; //y inicial
		double x_increment; //Incremento en x
		double y_increment; //Incremento en y

		x = x_ini;
		y = y_ini;
		x_increment = x_fin - x_ini;
		y_increment = y_fin - y_ini;
		m = y_increment / x_increment;
		e = m - 1 / 2;

		for (int i = 0 ; i <= x_increment ; i++) { //bucle principal
			//System.out.println(x + " " + y  + " " + m + " "+ e);
			//Añado las coordenadas a la linea
			Coordenada c= new Coordenada(x, y);
			pixels_linea.add(c);
			//Pinto las coordenadas en la cuadricula
			pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
			//
			while(e > 0) {
				//Actualizo y y e
				y = y + 1;
				e = e - 1;
			}
			//Actualizo x y e
			x = x + 1;
			e = e + m;
		}

		//Como la próxima vez que se forme una recta necesitamos a -1 el pixel inicial
		InterfazGráfica.x_ini=-1; //solo haría falta este

		return pixels_linea;
	}



	/**
	 * Bresenham Generalizado
	 **/
	public ArrayList <Coordenada> bresenhamGeneralizado (){
		ArrayList <Coordenada> pixels_linea = new ArrayList<Coordenada>();

		//Añado las coordenadas a la linea
		Coordenada c= new Coordenada(x_fin, y_fin);
		pixels_linea.add(c);

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
		x = x_ini;
		y = y_ini;
		x_increment = Math.abs(x_fin - x);
		y_increment = Math.abs(y_fin - y);

		s_x = signo(x_fin - x_ini);
		s_y = signo(y_fin - y_ini);

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
		for (int i = 1 ; i<= x_increment ; i++) {
			//Añado las coordenadas a la linea
			c= new Coordenada(x, y);
			pixels_linea.add(c);
			//Pinto las coordenadas en la cuadricula
			pixel_grid[(x+y*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);

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


		//Como la próxima vez que se forme una recta necesitamos a -1 el pixel inicial
		InterfazGráfica.x_ini=-1; //solo haría falta este

		return pixels_linea;
	}

	public static void circuloDeBresenham(int radio){
		ArrayList <Coordenada> pixels_circulo = new ArrayList<Coordenada>();
		
		int x_centro = x_ult;
		int y_centro = y_ult;
		int rad = radio;
		
		pixel_grid[((x_centro)+(y_centro)*ANCHO_PX)].cambiaColor(Color.BLACK);
		
		//Si el punto no ha sido utilizado por ninguna linea se cambia de color al del fondo
		for (ArrayList<Coordenada> l : lineas) {
			for (Coordenada c : l ) {
				//La coordenada ha sido usada por una línea
				if (c.getX()==x_centro && c.getY()==y_centro) {
					Color fondo = pixel_grid[(x_centro+y_centro*ANCHO_PX)].getColor();
					pixel_grid[((x_centro)+(y_centro)*ANCHO_PX)].cambiaColor(fondo);
					break;
				}
			}
		}
		
		
		//Primera coordenada (0, rad)
		int x = 0;
		int y = rad; 
		//Parámetro de decisión
	    int d = 3 - (2 * rad); 
		
	    pintaCirculo(x_centro, y_centro, x, y, pixels_circulo);
		
		//Repeat steps 5 to 8 until x < = y
		while (x <= y) {
			//Increment value of x.
			x++;
			
			//If d < 0, set d = d + (4*x) + 6
			if (d < 0) {
				d = d + (4 * x) + 6;
			} 
			//Else, set d = d + 4 * (x – y) + 10 and decrement y by 1.
			else { 
				d = d + 4 * (x - y) + 10;
				y--;
			}
			//call drawCircle(int xc, int yc, int x, int y) function
			pintaCirculo(x_centro, y_centro, x, y, pixels_circulo);
		}
		
		//Para que la próxima línea no empiece en el centro de este círculo
		x_ini=-1; //solo haría falta este
		
		if (pixels_circulo != null) {

			InterfazControl.listModel.addElement("Circunferencia " + contador_circunferencias);
			contador_circunferencias++;
			lineas.add(pixels_circulo);
			
		}
	}
	
	public static void pintaCirculo(int x_centro, int y_centro, int x, int y, ArrayList <Coordenada> pixels_circulo){
		Coordenada c;
		try {
	    	//putpixel(xc+x, yc+y, RED);
	    	pixel_grid[((x_centro+x)+(y_centro+y)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
	    } catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
	    
		try {
	        //putpixel(xc-x, yc+y, RED);
			pixel_grid[((x_centro-x)+(y_centro+y)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
			
		try {
	        //putpixel(xc+x, yc-y, RED);
			pixel_grid[((x_centro+x)+(y_centro-y)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
		
		try {
	        //putpixel(xc-x, yc-y, RED);
			pixel_grid[((x_centro-x)+(y_centro-y)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
		
		try {
	        //putpixel(xc+y, yc+x, RED);
			pixel_grid[((x_centro+y)+(y_centro+x)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
			
		try {
	        //putpixel(xc-y, yc+x, RED);
			pixel_grid[((x_centro-y)+(y_centro+x)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
			
		try {
	        //putpixel(xc+y, yc-x, RED);
			pixel_grid[((x_centro+y)+(y_centro-x)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
			
		try {
	        //putpixel(xc-y, yc-x, RED);
			pixel_grid[((x_centro-y)+(y_centro-x)*ANCHO_PX)].cambiaColor(Color.LIGHT_GRAY);
	    	c= new Coordenada(x, y);
			pixels_circulo.add(c);
		} catch (ArrayIndexOutOfBoundsException e) {}	//Si se sale del rango no pinta nada
	}
	

	/**
	 *******************************************************************************
	 																			  **/

	public static void cambiaColorLinea(ArrayList<Coordenada> linea, Color color) throws IndexOutOfBoundsException{
		int x;
		int y;

		for (Coordenada c: linea) {
			x = c.getX();
			y = c.getY();
			pixel_grid[(x+y*ANCHO_PX)].cambiaColor(color);
		}
	}

	public static void borrarTodo() {
		//Ponemos todos los Pixels en negro otra vez
		for(int i=0;i<TOTAL_PX;i++) {
			pixel_grid[i].cambiaColor(Color.BLACK);
		}
		InterfazControl.listModel.removeAllElements();
		contador_lineas=0;
		contador_circunferencias = 0;
		//Borramos el contenido de la lista de lineas
		lineas.clear();
		InterfazControl.txtpnNoHaSeleccionado.setText("No ha seleccionado ning\u00FAn pixel");
		x_ult=0;
		y_ult = 0;
	}

	public int signo(int a) {
		if (a < 0) return -1;
		if (a > 0) return 1;
		return 0;
	}

	/**
	 * Métodos que no se usan pero son necesarios para que la clase no crashee
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
