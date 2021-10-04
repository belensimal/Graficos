package practica01;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Pixel extends JLabel {
	
	Color fondo;

    /**
     * Crea y colorea el pixel del color que se le pase como parámetro
     **/
    public Pixel(Color color) {
    	fondo = color;
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setOpaque(true);
    }

    /**
     * Cambia el color del pixel por un nuevo color pasado como parámetro
     **/
    public void cambiaColor (Color color) {
    	fondo = color;
    	setBackground(color);
    }
    
    public Color getColor () {
    	return fondo;
    }

}
