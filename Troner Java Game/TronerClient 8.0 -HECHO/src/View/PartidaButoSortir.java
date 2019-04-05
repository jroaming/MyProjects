package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
/**
 * Classe que mostra la vista del butó de sortida del joc
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class PartidaButoSortir extends JDialog {
    private JButton jbSortir;   /** buto de sortir */

    /**
     * Constructor de la vista del buto
     */
    public PartidaButoSortir() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        jbSortir = new JButton("Sortir de la partida");
        Font fontGran = new Font("OCR A Extended", Font.PLAIN, 25);
        jbSortir.setForeground(Color.black);
        jbSortir.setBackground(Color.white);
        this.setSize(200,50);
        this.setLayout(new BorderLayout());
        add(jbSortir, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-100,65);
    }

    /**
     * Metode uqe registra el controlador que gestionara el buto
     * @param controller controlador encarregat
     */
    public void registraControlador(Controller controller) {
        this.jbSortir.setActionCommand("EXIT");
        this.jbSortir.addActionListener(controller);
    }
}
