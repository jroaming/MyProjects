package View;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;
/**
 * Classe que compta enrere per a la compta enrere d'inici de ronda de partida
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class CompteEnrere extends JFrame {
    private JButton jlCount; /** Butó del count*/

    /**
     * Constructor de la classe CompteEnrere
     */
    public CompteEnrere() {
        //this.setGlassPane(this);
        this.setUndecorated(true);
        //this.setAlwaysOnTop(true);
        this.setSize(150,150);

        Font font = new Font("OCR A Extended", Font.BOLD, 75);
        jlCount = new JButton();
        jlCount.setEnabled(true);
        jlCount.setFont(font);
        JPanel jp = new JPanel(new BorderLayout());
        jp.setBackground(Color.BLACK);
        jlCount.setForeground(Color.white);
        jlCount.setBackground(Color.BLACK);
        jp.add(jlCount, BorderLayout.CENTER);

        add(jp, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    /**
     * Metode que mostra la classe durant una mica menys d'un segon
     * @param count nombre a mostrar durant 1 segon aprox
     */
    public void mostraCount(int count) {
        try {
            this.jlCount.setText(count + "");
            this.setVisible(true);
            sleep(1000);
            this.setVisible(false);
        } catch (InterruptedException e){}
    }
}
