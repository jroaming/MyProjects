package View;

import Controller.ControllerUsersManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;

/**
 * Classe que mostra la finestra de gestio d'usuaris del servidor, des d'on es poden esborrar els usuaris seleccionats
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ViewUsersManager extends JDialog {
    private final static String[] COLUMNS = {"Nickname",
            "Punts acumulats",
            "Registre",
            "Últim accés"};     /** Header de la taula */
    private JTable jtUsuaris;    /** Taula d'usuaris */
    private DefaultTableModel dtmUsuaris;    /** Model de la taula a generar (usuaris) */
    private JButton jlIntro;     /** JButton que actua com un JLabel, només per informar de com eborrar usuaris */
    private JButton jbEsborrarSeleccionat;   /** Botó per esborrar l'usuari seleccionat amb el mouse a la taula */
    private JButton jbSortir;    /** JButton per tancar la finestra de la taula */

    /**
     * Constructora de la classe
     */
    public ViewUsersManager() {
        this.setTitle("Taula de gestió d'usuaris");
        this.setSize(700, 400);
        this.setUndecorated(true);

        // 1. Creem el JLabel:
        jlIntro = new JButton(">> Pots seleccionar un usuari clickant a sobre amb el mouse <<");
        jlIntro.setEnabled(false);
        jlIntro.setBackground(Color.BLACK);
        jlIntro.setForeground(Color.CYAN);
        getContentPane().add(jlIntro, BorderLayout.NORTH);

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
        JPanel jpSud = new JPanel(new GridLayout(1,2));
        getContentPane().add(jpSud, BorderLayout.SOUTH);

        // 3. Creem el botó d'esborrat:
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);  // creem el tipus de font que farem servir
        jbEsborrarSeleccionat = new JButton(" -> Esborrar usuari <- ");
        jbEsborrarSeleccionat.setBackground(Color.BLACK);
        jbEsborrarSeleccionat.setForeground(Color.CYAN);
        jbEsborrarSeleccionat.setFont(fontTitol);
        jpSud.add(jbEsborrarSeleccionat);

        // 4. Creem el botó de sortida:
        jbSortir = new JButton(" -> Tancar finestra <- ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontTitol);
        jpSud.add(jbSortir);

        this.setLocationRelativeTo(null);
    }

    /**
     * Registra la classe controladora del EventManager
     * @param e controladora de l'event
     */
    public void registraControlador(ControllerUsersManager e) {
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);
        jbEsborrarSeleccionat.setActionCommand("ESBORRAR");
        jbEsborrarSeleccionat.addActionListener(e);

    }

    /**
     * Metode que genera una nova fila a la taula d'usuaris
     * @param nom   nom de l'usuari a afegir
     * @param punts punts totals acumulats de l'usuari a afegir
     * @param registre  data de registre de l'usuari
     * @param ultim_acces   data d'ultim acces de l'usuari a afegir
     */
    public void afegeixUsuari(String nom, long punts, Date registre, Date ultim_acces) {
        dtmUsuaris.addRow(new Object[] {nom, punts, registre, ultim_acces});
    }

    /**
     * Mètode que elimina l'usuari seleccionat amb el mouse.
     */
    public void eliminaUsuariSeleccionat(ControllerUsersManager c) {
        try {
            if (jtUsuaris.getSelectedRow() >= 0) {  // Si hi ha cap usuari seleccionat
                // 1. Agafa el nom de l'usuari seleccionat
                String nom = (String) jtUsuaris.getValueAt(jtUsuaris.getSelectedRow(), 0);
                // 2. El borra de la database amb una query
                c.getDatabase().controllerDBInfo.deleteQuery("delete from usuari where nom like '" + nom + "';");
                // 3. Actualitzem la taula d'usuaris
                c.actualitzaTaula();
                // 4. Mostra missatge informatiu
                JOptionPane.showMessageDialog(this, "Usuari " + nom + " esborrat amb éxit!");
            } else {    // Si cap usuari està seleccionat
                JOptionPane.showMessageDialog(this, "No hi ha cap usuari seleccionat!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al esborrar l'usuari seleccionat!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que esborra tots els usuaris de la taula, buidant-la abans de que el programa la torni a omplir
     */
    public void netejaTaula() {
        int i = 0;
        while (i < dtmUsuaris.getRowCount()) dtmUsuaris.removeRow(i);
    }
}
