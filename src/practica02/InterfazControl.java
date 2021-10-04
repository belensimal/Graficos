package practica02;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class InterfazControl extends JPanel implements MouseListener{
	public DefaultListModel<String> listModel;
	public JList<String> list;
	public JTextField textX_translacion;
	public JTextField textY_translacion;
	public JTextField text_escalado;
	public JTextField textX_cizalla;
	public JTextField textY_cizalla;


	public int cont_figuras;


	public Pixel pixel;
	public static Pixel pixel_grid[];
	public static ArrayList <Figura> figuras;
	public static ArrayList <Linea> lineas;

	//Variables de linea
	private int x_ini;
	private int y_ini;
	private int x_fin;
	private int y_fin;

	public int x_ref, y_ref;

	//Variables para el grid de Pixels
	public static final int TAMAÑO_PX = 8;
	public static final int ANCHO_PX = 91;
	public static final int ALTO_PX = 61;
	public static final int TOTAL_PX = ANCHO_PX * ALTO_PX;
	public static final int ALTO_VENTANA = ALTO_PX * TAMAÑO_PX;
	public static final int ANCHO_VENTANA = ANCHO_PX * TAMAÑO_PX;



	public static void main(String[] args) {
		/**
		 * Muestro mis dos interfaces
		 */
		InterfazControl ic = new InterfazControl();
		ic.initialize();
	}

	public InterfazControl(){
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

		//Pinto los ejes de coordenadas
		pintoCoordenadas(0, (int)Math.ceil(ALTO_PX / 2), ANCHO_PX, (int)Math.ceil(ALTO_PX / 2)); //Eje X
		pintoCoordenadas((int)Math.ceil(ANCHO_PX / 2), 0, (int)Math.ceil(ANCHO_PX / 2), ALTO_PX); //Eje Y

		x_ref = (int)Math.ceil(ANCHO_PX / 2);
		y_ref = (int)Math.ceil(ALTO_PX / 2);

		x_ini=-1;
		figuras = new ArrayList<Figura>();
		lineas = new ArrayList <Linea>();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		cont_figuras = 0;

		//Create and set up the window.
		JFrame frame2 = new JFrame("LINE DRAWING ALGORITHMS");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setResizable(false);

		//Create and set up the content pane.
		JComponent newContentPane = new InterfazControl();
		newContentPane.setOpaque(true); //tiene que ser opaco
		frame2.setContentPane(newContentPane);

		//Display the window.
		frame2.pack();
		frame2.setVisible(true);

		//Creo la ventana de control
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(Color.LIGHT_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setTitle("Control Panel");
		frame.setAlwaysOnTop(true);
		frame.setBounds(740, 300, 800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setVisibleRowCount(5);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setForeground(Color.LIGHT_GRAY);
		list.setBackground(Color.BLACK);
		list.setBounds(625, 63, 150, 437);
		frame.getContentPane().add(list);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBackground(Color.LIGHT_GRAY);
		btnBorrar.setForeground(Color.DARK_GRAY);
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				figuras.clear();
				lineas.clear();
				x_ini=-1;
				listModel.clear();
				cont_figuras=0;
				for(int i=0;i<TOTAL_PX;i++) {
					pixel_grid[i].pintaFondo(Color.BLACK);
				}

				//Pinto los ejes de coordenadas
				pintoCoordenadas(0, (int)Math.ceil(ALTO_PX / 2), ANCHO_PX, (int)Math.ceil(ALTO_PX / 2)); //Eje X
				pintoCoordenadas((int)Math.ceil(ANCHO_PX / 2), 0, (int)Math.ceil(ANCHO_PX / 2), ALTO_PX); //Eje Y
			}
		});
		btnBorrar.setBounds(101, 421, 85, 21);
		frame.getContentPane().add(btnBorrar);

		JTextPane txtTranslacion = new JTextPane();
		txtTranslacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTranslacion.setText("TRANSLACI\u00D3N");
		txtTranslacion.setEditable(false);
		txtTranslacion.setForeground(Color.LIGHT_GRAY);
		txtTranslacion.setBackground(Color.DARK_GRAY);
		txtTranslacion.setBounds(341, 63, 121, 21);
		frame.getContentPane().add(txtTranslacion);

		JTextPane txtX_Translacion = new JTextPane();
		txtX_Translacion.setToolTipText("");
		txtX_Translacion.setBackground(Color.DARK_GRAY);
		txtX_Translacion.setForeground(Color.LIGHT_GRAY);
		txtX_Translacion.setEditable(false);
		txtX_Translacion.setText("X");
		txtX_Translacion.setBounds(366, 99, 25, 20);
		frame.getContentPane().add(txtX_Translacion);

		JTextPane txtY_Tanslacion = new JTextPane();
		txtY_Tanslacion.setToolTipText("");
		txtY_Tanslacion.setText("Y");
		txtY_Tanslacion.setForeground(Color.LIGHT_GRAY);
		txtY_Tanslacion.setEditable(false);
		txtY_Tanslacion.setBackground(Color.DARK_GRAY);
		txtY_Tanslacion.setBounds(455, 99, 25, 20);
		frame.getContentPane().add(txtY_Tanslacion);

		JTextPane txtEscalado = new JTextPane();
		txtEscalado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEscalado.setText("ESCALADO");
		txtEscalado.setForeground(Color.LIGHT_GRAY);
		txtEscalado.setEditable(false);
		txtEscalado.setBackground(Color.DARK_GRAY);
		txtEscalado.setBounds(341, 129, 85, 21);
		frame.getContentPane().add(txtEscalado);

		JTextPane txt_Escalado = new JTextPane();
		txt_Escalado.setToolTipText("");
		txt_Escalado.setText("S");
		txt_Escalado.setForeground(Color.LIGHT_GRAY);
		txt_Escalado.setEditable(false);
		txt_Escalado.setBackground(Color.DARK_GRAY);
		txt_Escalado.setBounds(366, 165, 25, 20);
		frame.getContentPane().add(txt_Escalado);

		JTextPane txtCizalla = new JTextPane();
		txtCizalla.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCizalla.setText("CIZALLA");
		txtCizalla.setForeground(Color.LIGHT_GRAY);
		txtCizalla.setEditable(false);
		txtCizalla.setBackground(Color.DARK_GRAY);
		txtCizalla.setBounds(341, 195, 85, 21);
		frame.getContentPane().add(txtCizalla);

		JTextPane txtX_Cizalla = new JTextPane();
		txtX_Cizalla.setToolTipText("");
		txtX_Cizalla.setText("X");
		txtX_Cizalla.setForeground(Color.LIGHT_GRAY);
		txtX_Cizalla.setEditable(false);
		txtX_Cizalla.setBackground(Color.DARK_GRAY);
		txtX_Cizalla.setBounds(366, 231, 25, 20);
		frame.getContentPane().add(txtX_Cizalla);

		JTextPane txtY_Cizalla = new JTextPane();
		txtY_Cizalla.setToolTipText("");
		txtY_Cizalla.setText("Y");
		txtY_Cizalla.setForeground(Color.LIGHT_GRAY);
		txtY_Cizalla.setEditable(false);
		txtY_Cizalla.setBackground(Color.DARK_GRAY);
		txtY_Cizalla.setBounds(455, 231, 25, 20);
		frame.getContentPane().add(txtY_Cizalla);

		JTextPane txtRotacion = new JTextPane();
		txtRotacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRotacion.setText("ROTACI\u00D3N\r\n");
		txtRotacion.setForeground(Color.LIGHT_GRAY);
		txtRotacion.setEditable(false);
		txtRotacion.setBackground(Color.DARK_GRAY);
		txtRotacion.setBounds(341, 261, 85, 21);
		frame.getContentPane().add(txtRotacion);

		JTextPane txtX_Cizalla_1 = new JTextPane();
		txtX_Cizalla_1.setToolTipText("");
		txtX_Cizalla_1.setText("\u00C1ngulo");
		txtX_Cizalla_1.setForeground(Color.LIGHT_GRAY);
		txtX_Cizalla_1.setEditable(false);
		txtX_Cizalla_1.setBackground(Color.DARK_GRAY);
		txtX_Cizalla_1.setBounds(366, 286, 50, 20);
		frame.getContentPane().add(txtX_Cizalla_1);

		JSpinner spinner_Angulo_rot = new JSpinner();
		spinner_Angulo_rot.setModel(new SpinnerNumberModel(0, 0, 360, 1));
		spinner_Angulo_rot.setForeground(Color.LIGHT_GRAY);
		spinner_Angulo_rot.setBackground(Color.DARK_GRAY);
		spinner_Angulo_rot.setBounds(420, 281, 50, 25);
		frame.getContentPane().add(spinner_Angulo_rot);

		JRadioButton rdbtnSentidoHorario = new JRadioButton("Sentido horario");
		rdbtnSentidoHorario.setForeground(Color.DARK_GRAY);
		rdbtnSentidoHorario.setBackground(Color.LIGHT_GRAY);
		rdbtnSentidoHorario.setBounds(366, 312, 114, 21);
		frame.getContentPane().add(rdbtnSentidoHorario);

		JTextPane txtpnPulseElSiguiente = new JTextPane();
		txtpnPulseElSiguiente.setText("Pulse el siguiente bot\u00F3n para borrar toda la actividad registrada:");
		txtpnPulseElSiguiente.setForeground(Color.LIGHT_GRAY);
		txtpnPulseElSiguiente.setEditable(false);
		txtpnPulseElSiguiente.setBackground(Color.DARK_GRAY);
		txtpnPulseElSiguiente.setBounds(32, 370, 245, 41);
		frame.getContentPane().add(txtpnPulseElSiguiente);

		JButton btnNewFigura = new JButton("Nueva figura");


		btnNewFigura.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listModel.addElement("Figura " + cont_figuras);
				cont_figuras++;
				Figura figura = new Figura(x_ref, y_ref, lineas);
				figuras.add(figura);
				lineas.clear();
			}
		});

		btnNewFigura.setForeground(Color.LIGHT_GRAY);
		btnNewFigura.setBackground(Color.DARK_GRAY);
		btnNewFigura.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewFigura.setBounds(76, 165, 153, 33);
		frame.getContentPane().add(btnNewFigura);

		textX_translacion = new JTextField();
		textX_translacion.setText("0");
		textX_translacion.setForeground(Color.DARK_GRAY);
		textX_translacion.setBackground(Color.LIGHT_GRAY);
		textX_translacion.setBounds(395, 94, 50, 25);
		frame.getContentPane().add(textX_translacion);
		textX_translacion.setColumns(10);

		textY_translacion = new JTextField();
		textY_translacion.setBackground(Color.LIGHT_GRAY);
		textY_translacion.setForeground(Color.DARK_GRAY);
		textY_translacion.setText("0");
		textY_translacion.setColumns(10);
		textY_translacion.setBounds(484, 94, 50, 25);
		frame.getContentPane().add(textY_translacion);

		text_escalado = new JTextField();
		text_escalado.setText("1");
		text_escalado.setForeground(Color.DARK_GRAY);
		text_escalado.setBackground(Color.LIGHT_GRAY);
		text_escalado.setColumns(10);
		text_escalado.setBounds(395, 160, 50, 25);
		frame.getContentPane().add(text_escalado);

		textX_cizalla = new JTextField();
		textX_cizalla.setText("0");
		textX_cizalla.setForeground(Color.DARK_GRAY);
		textX_cizalla.setBackground(Color.LIGHT_GRAY);
		textX_cizalla.setColumns(10);
		textX_cizalla.setBounds(395, 226, 50, 25);
		frame.getContentPane().add(textX_cizalla);

		textY_cizalla = new JTextField();
		textY_cizalla.setText("0");
		textY_cizalla.setForeground(Color.DARK_GRAY);
		textY_cizalla.setBackground(Color.LIGHT_GRAY);
		textY_cizalla.setColumns(10);
		textY_cizalla.setBounds(484, 226, 50, 25);
		frame.getContentPane().add(textY_cizalla);

		JComboBox<String> comboBoxReflexion = new JComboBox<String>();
		comboBoxReflexion.setForeground(Color.DARK_GRAY);
		comboBoxReflexion.setBackground(Color.LIGHT_GRAY);
		comboBoxReflexion.setModel(new DefaultComboBoxModel <String>(new String[] {"No", "Respecto al eje X", "Respecto al eje Y", "Respecto al origen (0,0)"}));
		comboBoxReflexion.setBounds(366, 381, 139, 21);
		frame.getContentPane().add(comboBoxReflexion);

		JTextPane txtpnReflexin = new JTextPane();
		txtpnReflexin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnReflexin.setText("REFLEXI\u00D3N\r\n");
		txtpnReflexin.setForeground(Color.LIGHT_GRAY);
		txtpnReflexin.setEditable(false);
		txtpnReflexin.setBackground(Color.DARK_GRAY);
		txtpnReflexin.setBounds(341, 350, 85, 21);
		frame.getContentPane().add(txtpnReflexin);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(606, 10, 9, 490);
		frame.getContentPane().add(separator_2);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setOrientation(SwingConstants.VERTICAL);
		separator_2_1.setBounds(315, 10, 9, 490);
		frame.getContentPane().add(separator_2_1);

		JTextPane txtpnTransformaciones = new JTextPane();
		txtpnTransformaciones.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnTransformaciones.setText("TRANSFORMACIONES");
		txtpnTransformaciones.setForeground(Color.LIGHT_GRAY);
		txtpnTransformaciones.setEditable(false);
		txtpnTransformaciones.setBackground(Color.DARK_GRAY);
		txtpnTransformaciones.setBounds(366, 10, 213, 30);
		frame.getContentPane().add(txtpnTransformaciones);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(341, 45, 240, 8);
		frame.getContentPane().add(separator_1_1);

		JButton btnAnimacion = new JButton("Animación");


		btnAnimacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = list.getSelectedIndex();
				if (index < 0) return;

				Figura figura = InterfazControl.figuras.get(index);
				InterfazControl.animacion(figura);
			}
		});

		btnAnimacion.setForeground(Color.LIGHT_GRAY);
		btnAnimacion.setBackground(Color.DARK_GRAY);
		btnAnimacion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAnimacion.setBounds(76, 300, 153, 33);
		frame.getContentPane().add(btnAnimacion);

		JButton btnNewButton = new JButton("Aplicar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = list.getSelectedIndex();
				if (index < 0) return;

				Figura figura = InterfazControl.figuras.get(index);

				//TRASLACION -> default: xt=0, yt=0
				String xt = textX_translacion.getText();
				String yt = textY_translacion.getText();
				figura.traslacion(Integer.parseInt(xt), -Integer.parseInt(yt));

				//ESCALADO -> default xs=1, ys=1
				String s = text_escalado.getText();
				figura.escalado(Double.parseDouble(s));

				//CIZALLA -> default xc=0, yc=0
				String xc = textX_cizalla.getText();
				String yc = textY_cizalla.getText();
				figura.cizalla(Integer.parseInt(xc), -Integer.parseInt(yc));


				//ROTACION -> default angulo=0º
				int angulo = (Integer)spinner_Angulo_rot.getValue();
				boolean sentidoHorario = rdbtnSentidoHorario.isSelected();
				if (sentidoHorario) {
					figura.rotacion(-angulo);
				} else {
					figura.rotacion(angulo);
				}

				//REFLEXION
				int i = comboBoxReflexion.getSelectedIndex();
				switch (i) {
				//No
				case 0:
					//No hace nada
					break;
					//Respecto al eje X
				case 1:
					figura.reflexionX();
					break;
					//Respecto al eje Y
				case 2:
					figura.reflexionY();
					break;
					//Respecto al origen (0,0)
				case 3:
					figura.reflexionOrigen();
					break;
					//Ninguno de los anteriores
				default:
					System.out.println("ERROR");
					break;
				}

			}
		});

		btnNewButton.setForeground(Color.LIGHT_GRAY);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(406, 431, 110, 30);
		frame.getContentPane().add(btnNewButton);

		JTextPane txtpnCreacionDeFiguras = new JTextPane();
		txtpnCreacionDeFiguras.setText("CREACI\u00D3N DE FIGURAS");
		txtpnCreacionDeFiguras.setForeground(Color.LIGHT_GRAY);
		txtpnCreacionDeFiguras.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnCreacionDeFiguras.setEditable(false);
		txtpnCreacionDeFiguras.setBackground(Color.DARK_GRAY);
		txtpnCreacionDeFiguras.setBounds(45, 10, 232, 30);
		frame.getContentPane().add(txtpnCreacionDeFiguras);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(37, 45, 240, 8);
		frame.getContentPane().add(separator_1_1_1);

		JTextPane txtpnParaCrearUna = new JTextPane();
		txtpnParaCrearUna.setText("Para crear una figura, pulse en cualquier pixel del panel. Continue pulsando p\u00EDxeles para que las aristas se vayan creando. Una vez haya terminado, pulse el siguiente bot\u00F3n para crear la figura y a\u00F1adirla a la lista:");
		txtpnParaCrearUna.setForeground(Color.LIGHT_GRAY);
		txtpnParaCrearUna.setEditable(false);
		txtpnParaCrearUna.setBackground(Color.DARK_GRAY);
		txtpnParaCrearUna.setBounds(32, 63, 245, 87);
		frame.getContentPane().add(txtpnParaCrearUna);

		JSeparator separator_1_1_1_1 = new JSeparator();
		separator_1_1_1_1.setBounds(37, 268, 240, 14);
		frame.getContentPane().add(separator_1_1_1_1);

		JTextPane txtpnModificacionesYOtros = new JTextPane();
		txtpnModificacionesYOtros.setText("BORRAR Y ANIMACIÓN");
		txtpnModificacionesYOtros.setForeground(Color.LIGHT_GRAY);
		txtpnModificacionesYOtros.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnModificacionesYOtros.setEditable(false);
		txtpnModificacionesYOtros.setBackground(Color.DARK_GRAY);
		txtpnModificacionesYOtros.setBounds(32, 231, 257, 30);
		frame.getContentPane().add(txtpnModificacionesYOtros);

		JTextPane txtpnLista = new JTextPane();
		txtpnLista.setText("LISTA");
		txtpnLista.setForeground(Color.LIGHT_GRAY);
		txtpnLista.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnLista.setEditable(false);
		txtpnLista.setBackground(Color.DARK_GRAY);
		txtpnLista.setBounds(663, 10, 78, 30);
		frame.getContentPane().add(txtpnLista);

		JSeparator separator_1_1_2 = new JSeparator();
		separator_1_1_2.setBounds(618, 45, 157, 8);
		frame.getContentPane().add(separator_1_1_2);

		//Display the window.
		frame.setVisible(true);
	}



	static void animacion(Figura figura){

		//Se mueve a la derecha, vuelve a donde estaba, sube y vuelve a donde estaba
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.traslacion(5, 0);
		System.out.println("Pinto");

		
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.traslacion(0, 2);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.traslacion(-5, 0);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.traslacion(0, -2);
		System.out.println("Pinto");


		//Rota hasta quedarse al revés, y después gira hasta quedarse como estaba
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.rotacion(60);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.rotacion(60);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.rotacion(60);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.rotacion(180);
		System.out.println("Pinto");


		//Se hace pequeño y después grande
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.escalado(0.5);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.escalado(1.5);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.escalado(1.5);
		System.out.println("Pinto");


		//Se cizalla en x y despues en y, y acaba como estaba inicialmente
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.cizalla(1, 0);
		System.out.println("Pinto");

		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.cizalla(-1, 0);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.cizalla(0, 1);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.cizalla(0, -1);
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionX();
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionX();
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionY();
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionY();
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionOrigen();
		System.out.println("Pinto");


		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		figura.reflexionOrigen();
		System.out.println("Pinto");
		System.out.println("Terminé");
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x_raton;
		int y_raton;

		x_raton=e.getX();
		x_raton=x_raton/TAMAÑO_PX;

		y_raton=e.getY();
		y_raton=y_raton/TAMAÑO_PX;


		if (x_ini==-1) {
			//Pinta el pixel
			pixel_grid[(x_raton+y_raton*ANCHO_PX)].setBackground(Color.LIGHT_GRAY);
			x_ini=x_raton;
			y_ini=y_raton;
		} else {
			x_fin=x_raton;
			y_fin=y_raton;

			pintaLinea(x_ini, y_ini, x_fin, y_fin);
			Linea l = new Linea(x_ini, y_ini, x_fin, y_fin);
			lineas.add(l);

			x_ini = -1;
		}
	}


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
				InterfazControl.pixel_grid[(x+y*InterfazControl.ANCHO_PX)].setBackground(Color.LIGHT_GRAY);
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
	 * Método que usa el algoritmo de linea de bresenham
	 */
	public static void pintoCoordenadas(int x_inicial, int y_inicial, int x_final, int y_final){
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
		for (int i = 1 ; i<= x_increment ; i++) {
			pixel_grid[(x+y*ANCHO_PX)].pintaFondo(Color.DARK_GRAY);
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
	public static int signo(int a) {
		if (a < 0) return -1;
		if (a > 0) return 1;
		return 0;
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
