package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Classe que mostra la vista de espera fins que el usuari trobi partida o deixi la cua
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewWaitingLobby extends JDialog {
    private static final String TANCAR = "TANCAR_LOBBY"; /** String del butó de sortida*/
    private JButton jbSortir; /** Butó de sortida  */
    private JButton jlIntro; /** JLabel per dona entendre que estem en una cua de espera */

    /**
     * Constructor de la finestra
     */
    public ViewWaitingLobby() {
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        Font fontTitol = new Font("OCR A Extended", Font.PLAIN,15);  // creem el tipus de font que farem servir
        Font fontButo = new Font("OCR A Extended", Font.PLAIN,18);  // creem el tipus de font que farem servir
        // Creem el label i el fiquem al jpanel
        jlIntro = new JButton("____________________");
        jlIntro.setFont(fontTitol);
        jlIntro.setEnabled(false);
        jlIntro.setForeground(Color.gray);
        jlIntro.setBackground(Color.BLACK);
        add(jlIntro, BorderLayout.NORTH);
        // Creem el jbutton i el fiquem al jpanel
        jbSortir = new JButton(" > Sortir de la cua < ");
        jbSortir.setFont(fontButo);
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.GREEN);
        add(jbSortir, BorderLayout.CENTER);

        this.setAlwaysOnTop(true);
        this.setSize(400, 60);
        setLocationRelativeTo(null);
    }

    /**
     * Metode que registra el controlador encarregat de gestionar el boto
     * @param e controlador
     */
    public void registraControlador(ActionListener e) {
        jbSortir.setActionCommand(TANCAR);
        jbSortir.addActionListener(e);
    }

    /**
     * Metode que setteja el text del jlabel de intro de la finestra
     * @param nova_intro text pel qual canviar el nostre JLabel
     */
    public void setJlIntro(String nova_intro) {
        this.jlIntro.setText(nova_intro);
    }
}
