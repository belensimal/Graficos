package practica01;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import practica01.ColorChooserButton.ColorChangedListener;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFormattedTextField;

public class InterfazControl {

	static JComboBox<String> comboBox;
	static JList<String> list;
	public static DefaultListModel<String> listModel;
	static JTextPane txtpnNoHaSeleccionado = new JTextPane();
	
	public static void main(String[] args) {
		/**
		 * Muestro mis dos interfaces
		 */
		InterfazGráfica.initialize();
		InterfazControl.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public static void initialize() {

		//Creo la ventana
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(Color.LIGHT_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setTitle("Control Panel");
		frame.setAlwaysOnTop(true);
		frame.setBounds(1050, 100, 450, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane txtAlgoritmo = new JTextPane();
		txtAlgoritmo.setEditable(false);
		txtAlgoritmo.setForeground(Color.LIGHT_GRAY);
		txtAlgoritmo.setText("Seleccione un algoritmo de linea:");
		txtAlgoritmo.setBackground(Color.DARK_GRAY);
		txtAlgoritmo.setBounds(10, 10, 250, 19);
		frame.getContentPane().add(txtAlgoritmo);

		//Creo un combo box con los diferentes algoritmos de linea
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 39, 250, 30);
		comboBox.setBackground(Color.LIGHT_GRAY);
		comboBox.setForeground(Color.DARK_GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Slope Intercept b\u00E1sico", 
				"Slope Intercept modificado", "Algoritmo DDA", "Algoritmo de Bresenham "
						+ "b\u00E1sico", "Algoritmo de Bresenham modificado"}));
		frame.getContentPane().add(comboBox);
		
		
		listModel = new DefaultListModel<String>();
		 
		 
		//Create the list and put it in a scroll pane.
		list = new JList<String>(listModel);
		list.setBackground(Color.BLACK);
		list.setForeground(Color.LIGHT_GRAY);
		list.setBounds(270, 10, 150, 490);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(5);
		//JScrollPane listScrollPane = new JScrollPane(list);
		frame.getContentPane().add(list);
		
		
		JTextPane txtColor = new JTextPane();
		txtColor.setEditable(false);
		txtColor.setForeground(Color.LIGHT_GRAY);
		txtColor.setBackground(Color.DARK_GRAY);
		txtColor.setText("Seleccione una de las lineas y pulse el botón para cambiarle el color:");
		txtColor.setBounds(10, 79, 250, 37);
		frame.getContentPane().add(txtColor);
		
		ColorChooserButton colorChooser = new ColorChooserButton(Color.LIGHT_GRAY);
		colorChooser.setBounds(104, 126, 40, 30);
		colorChooser.setBackground(Color.DARK_GRAY);
		
		colorChooser.addColorChangedListener(new ColorChangedListener() {
		    @Override
		    public void colorChanged(Color newColor) {
		            int index = list.getSelectedIndex();
		            if (index < 0) return;
		            InterfazGráfica.cambiaColorLinea(InterfazGráfica.lineas.get(index), newColor);
		    }
		});
		frame.getContentPane().add(colorChooser);
		
		JTextPane txtBorrar = new JTextPane();
		txtBorrar.setEditable(false);
		txtBorrar.setForeground(Color.LIGHT_GRAY);
		txtBorrar.setBackground(Color.DARK_GRAY);
		txtBorrar.setText("Pulse el siguiente bot\u00F3n para borrar toda la actividad registrada:");
		txtBorrar.setBounds(10, 157, 250, 37);
		frame.getContentPane().add(txtBorrar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setForeground(Color.DARK_GRAY);
		btnBorrar.setBackground(Color.LIGHT_GRAY);
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazGráfica.borrarTodo();
			}
		});
		btnBorrar.setBounds(81, 204, 85, 21);
		frame.getContentPane().add(btnBorrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 235, 250, 19);
		frame.getContentPane().add(separator);
		
		JTextPane txtpnSeleccioneUnRadio = new JTextPane();
		txtpnSeleccioneUnRadio.setText("Seleccione el radio de la c\u00EDrcunferencia:");
		txtpnSeleccioneUnRadio.setForeground(Color.LIGHT_GRAY);
		txtpnSeleccioneUnRadio.setEditable(false);
		txtpnSeleccioneUnRadio.setBackground(Color.DARK_GRAY);
		txtpnSeleccioneUnRadio.setBounds(10, 251, 250, 19);
		frame.getContentPane().add(txtpnSeleccioneUnRadio);
		
		JSpinner spinnerRadio = new JSpinner();
		spinnerRadio.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerRadio.setBounds(92, 280, 60, 30);
		frame.getContentPane().add(spinnerRadio);
		
		JTextPane txtpnPulseEnEl = new JTextPane();
		txtpnPulseEnEl.setText("Pulse en el pixel donde quiera el centro de la circunferencia.");
		txtpnPulseEnEl.setForeground(Color.LIGHT_GRAY);
		txtpnPulseEnEl.setEditable(false);
		txtpnPulseEnEl.setBackground(Color.DARK_GRAY);
		txtpnPulseEnEl.setBounds(10, 321, 250, 37);
		frame.getContentPane().add(txtpnPulseEnEl);
		
		JButton btnNewButton = new JButton("Pintar circunferencia");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Lee el radio
				int radio = (int) spinnerRadio.getValue();
				InterfazGráfica.circuloDeBresenham(radio);
				//Se lo pasa a InterfazGrafica para que pinte la circunferencia
				
			}
		});
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(61, 468, 159, 21);
		frame.getContentPane().add(btnNewButton);
		
		JTextPane txtpnDespusPulseEl = new JTextPane();
		txtpnDespusPulseEl.setText("Despu\u00E9s, pulse el siguiente bot\u00F3n para pintarla:");
		txtpnDespusPulseEl.setForeground(Color.LIGHT_GRAY);
		txtpnDespusPulseEl.setEditable(false);
		txtpnDespusPulseEl.setBackground(Color.DARK_GRAY);
		txtpnDespusPulseEl.setBounds(10, 425, 250, 37);
		frame.getContentPane().add(txtpnDespusPulseEl);
		
		
		txtpnNoHaSeleccionado.setForeground(Color.DARK_GRAY);
		txtpnNoHaSeleccionado.setBackground(Color.LIGHT_GRAY);
		txtpnNoHaSeleccionado.setEditable(false);
		txtpnNoHaSeleccionado.setText("No ha seleccionado ning\u00FAn pixel");
		txtpnNoHaSeleccionado.setBounds(10, 360, 250, 30);
		frame.getContentPane().add(txtpnNoHaSeleccionado);
		
		//Display the window.
		frame.setVisible(true);
	}
}
