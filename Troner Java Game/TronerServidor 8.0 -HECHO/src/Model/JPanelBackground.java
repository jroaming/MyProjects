package Model;

import javax.swing.*;
import java.awt.*;

/**
 * Clase heredada de JPanel que nos permite modificar el fondo del panel.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class JPanelBackground extends JPanel{
    private Image background;   /** imagen de background que le pasamos para el fondo del panel*/

    /**
     * Metodo que pinta el componente
     * @param g gestor de la imagen
     */
    public void paintComponent(Graphics g) {

    /* Obtenemos el tamaño del panel para hacer que se ajuste a este
    cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
        int width = this.getSize().width;
        int height = this.getSize().height;

        // Mandamos que pinte la imagen en el panel
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    /**
     * Metodo donde le pasaremos la dirección de la imagen a cargar.
     * @param imagePath path de la imagen
     */
    public void setBackground(String imagePath) {
        // Construimos la imagen y se la asignamos al atributo background.
        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }
}
