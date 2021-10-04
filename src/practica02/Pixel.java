package practica02;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Pixel extends JLabel {
	Color colorFondo;
	int numCapas;

    /**
     * Crea y colorea el pixel del color que se le pase como parámetro
     **/
    public Pixel(Color color) {
    	pintaFondo(color);
        //setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setOpaque(true);
    }

    /**
     * Cambia el color del pixel por un nuevo color pasado como parámetro
     **/
    public void pintaColor (Color color) {
    	numCapas++;
    	setBackground(color);
    }
    
    public void pintaFondo(Color color) {
    	setBackground(color);
    	colorFondo = color;
    	numCapas = 0;
    }

    public void borraColor () {
    	numCapas--;
    	if (numCapas <= 0) {
    		setBackground(colorFondo);
    	}
    }
}
