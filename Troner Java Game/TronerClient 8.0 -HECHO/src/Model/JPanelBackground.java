package Model;

import javax.swing.*;
import java.awt.*;

/**
 * Classe  de JPanel que ens permet modifica el fons del panel
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class JPanelBackground extends JPanel{

    // Atributo que guardara la imagen de Background que le pasemos.
    private Image background; /** Image de fons de la vista*/
    /**
     * Metode que es crida automaticament per la maquina virtual Java cada cop que repinta
     * @param g es un Graphics
     */
    // Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
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
     * Metode on pasarem la direcció de la image a carregar
     * @param imagePath es una String
     */
    public void setBackground(String imagePath) {

        // Construimos la imagen y se la asignamos al atributo background.
        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }
}
