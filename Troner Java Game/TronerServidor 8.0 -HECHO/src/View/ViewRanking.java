package View;

import Controller.ControllerRanking;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

/**
 * Classe que mostra la finestra de ranking dels jugadors (puntuacio total)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ViewRanking extends JDialog{
    private final static String[] COLUMNS = {"Nickname",
            "Punts acumulats",
            "Últim accés"}; /** header de la taula */
    private JTable jtUsuaris;    /** Taula d'usuaris */
    private DefaultTableModel dtmUsuaris; /** model de la taula */
    private JButton jbSortir; /** jbutton de tancar la finestra */

    /**
     * Constructor del ranking, genera la finestra
     */
    public ViewRanking(){
        this.setTitle("Taula Ranking");
        this.setSize(700, 400);
        this.setUndecorated(true);

        // 2. Creem la taula i les opcions i models:
        jtUsuaris = new JTable();
        this.setBackground(Color.BLACK);
        jtUsuaris.setBackground(Color.BLACK);
        jtUsuaris.setForeground(Color.CYAN);
        dtmUsuaris = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }};

        dtmUsuaris.setColumnIdentifiers(COLUMNS);
        jtUsuaris.setModel(dtmUsuaris);

        JScrollPane jspTaula = new JScrollPane();
        getContentPane().add(jspTaula, BorderLayout.CENTER);
        jspTaula.setViewportView(jtUsuaris);

        // [3. Creem el panel south]
        JPanel jpSud = new JPanel(new GridLayout(1,1));
        getContentPane().add(jpSud, BorderLayout.SOUTH);

        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);  // creem el tipus de font que farem servir
        jbSortir = new JButton(" -> Tancar finestra <- ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontTitol);
        jpSud.add(jbSortir);

        this.setLocationRelativeTo(null);
    }

    /**
     * Metode que registra el controlador encarregat de gestionar les peticions a aquesta finestra
     * @param e controlador en qúestió
     */
    public void registraControlador(ControllerRanking e) {
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);
    }

    /**
     * Metode que afegeix un usuari al ranking
     * @param nom nom de l'usuari a afegir
     * @param punts punts del jugador a mostrar al ranking
     * @param ultim_acces data d'ultim acces al seu compte del jugador
     */
    public void afegeixUsuariRanking(String nom, long punts, Date ultim_acces) {
        dtmUsuaris.addRow(new Object[] {nom, punts, ultim_acces});
    }

    /**
     * Metode que esborra l'usuari seleccionat a la taula
     */
    public void netejaRanking() {
        int i = 0;
        while (i < dtmUsuaris.getRowCount()) dtmUsuaris.removeRow(i);
    }
}
