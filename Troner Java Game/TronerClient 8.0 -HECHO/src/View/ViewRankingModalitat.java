package View;

import Controller.ControllerRankingModalitat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
/**
 * Classe que mostra la vista el ranking de del top 10 de totes les modalitats
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewRankingModalitat extends JDialog{
    private final static String[] COLUMNSV2 = {"Nickname",
            "Punts VS2"};
    private final static String[] COLUMNSV4 = {"Nickname",
            "Punts VS4"};
    private final static String[] COLUMNSVT = {"Nickname",
            "Punts Torneig"};
    private JTable jtUsuarisV2;    /** Taula d'usuaris de modalitat versus2*/
    private JTable jtUsuarisV4;    /** Taula d'usuaris de modalitat versus4*/
    private JTable jtUsuarisVT;    /** Taula d'usuaris de modalitat torneig*/
    private DefaultTableModel dtmUsuarisV2;  /** Model de la taula versus2 */
    private DefaultTableModel dtmUsuarisV4;  /** Model de la taula versus4 */
    private DefaultTableModel dtmUsuarisVT;  /** Model de la taula versusT */
    private JButton jbSortir;    /** Butó de sortida */


    // CONTRUCTORS
    public ViewRankingModalitat(){
        this.setTitle("Taula Ranking por Modalitat de Joc");
        this.setSize(800, 300);
        //this.setUndecorated(true);

        // 1. Panell NORTH (JLabels)
        JPanel jpNorth = new JPanel(new GridLayout(1,3));
        jpNorth.setBackground(Color.BLACK);
        jpNorth.setForeground(Color.GREEN);
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,20);  // creem el tipus de font que farem servir

        // 2. Fiquem els JLabels
        JButton jlV2 = new JButton("Mode vs 2");
        jlV2.setEnabled(false);
        jlV2.setBackground(Color.BLACK);
        jlV2.setForeground(Color.white);
        jlV2.setFont(fontTitol);
        jlV2.setForeground(Color.white);
        jpNorth.add(jlV2);
        JButton jlV4 = new JButton("Mode vs 4");
        jlV4.setEnabled(false);
        jlV4.setBackground(Color.BLACK);
        jlV4.setForeground(Color.white);
        jlV4.setFont(fontTitol);
        jlV4.setForeground(Color.white);
        jpNorth.add(jlV4);
        JButton jlVT = new JButton("Mode Torneig");
        jlVT.setEnabled(false);
        jlVT.setBackground(Color.BLACK);
        jlVT.setForeground(Color.white);
        jlVT.setFont(fontTitol);
        jlVT.setForeground(Color.white);
        jpNorth.add(jlVT);
        getContentPane().add(jpNorth, BorderLayout.NORTH);

        // 3. Panell CENTER
        JPanel jpCenter = new JPanel(new GridLayout(1,3));

        // 4. Fiquem les taules
        jtUsuarisV2 = new JTable();
        this.setBackground(Color.BLACK);
        jtUsuarisV2.setBackground(Color.BLACK);
        jtUsuarisV2.setForeground(Color.CYAN);
        dtmUsuarisV2 = new DefaultTableModel(COLUMNSV2, 10) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }};

        dtmUsuarisV2.setColumnIdentifiers(COLUMNSV2);
        jtUsuarisV2.setModel(dtmUsuarisV2);

        jpCenter.add(jtUsuarisV2);

        // 5. Taula usuaris V4
        jtUsuarisV4 = new JTable();
        this.setBackground(Color.BLACK);
        jtUsuarisV4.setBackground(Color.BLACK);
        jtUsuarisV4.setForeground(Color.CYAN);
        dtmUsuarisV4 = new DefaultTableModel(COLUMNSV4, 10) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }};

        dtmUsuarisV4.setColumnIdentifiers(COLUMNSV4);
        jtUsuarisV4.setModel(dtmUsuarisV4);

        jpCenter.add(jtUsuarisV4);

        // 6. Taula usuaris VT
        jtUsuarisVT = new JTable();
        this.setBackground(Color.BLACK);
        jtUsuarisVT.setBackground(Color.BLACK);
        jtUsuarisVT.setForeground(Color.CYAN);
        dtmUsuarisVT = new DefaultTableModel(COLUMNSVT, 10) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }};

        dtmUsuarisVT.setColumnIdentifiers(COLUMNSVT);
        jtUsuarisVT.setModel(dtmUsuarisVT);

        jpCenter.add(jtUsuarisVT);

        // 5. Creem el panel south
        Font fontSortir = new Font("OCR A Extended", Font.BOLD,16);  // creem el tipus de font que farem servir
        jbSortir = new JButton(" -> Tancar finestra <- ");

        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.WHITE);
        jbSortir.setFont(fontSortir);
        getContentPane().add(jbSortir, BorderLayout.SOUTH);

        add(jpCenter, BorderLayout.CENTER);

        pack();
        this.setLocationRelativeTo(null);
    }

    // GETTERS I SETTERS

    /**
     * Getter que ens retorna la JTable dels jugadorsV2
     * @return retorna un JTable
     */
    public JTable getJtUsuarisV2() {
        return jtUsuarisV2;
    }

    /**
     * Setter de la JTable dels jugadorsV2
     * @param jtUsuarisV2
     */
    public void setJtUsuarisV2(JTable jtUsuarisV2) {
        this.jtUsuarisV2 = jtUsuarisV2;
    }

    /**
     * Getter de la JTable dels jugadorsV4
     * @return retorna un JTable
     */
    public JTable getJtUsuarisV4() {
        return jtUsuarisV4;
    }

    /**
     * Setter de la JTable dels jugadorsV4
     * @param jtUsuarisV4
     */
    public void setJtUsuarisV4(JTable jtUsuarisV4) {
        this.jtUsuarisV4 = jtUsuarisV4;
    }

    /**
     * Getter de la JTable dels jugadorsVT
     * @return retorna un JTable
     */
    public JTable getJtUsuarisVT() {
        return jtUsuarisVT;
    }

    /**
     * Setter de la JTable dels jugadorsVT
     * @param jtUsuarisVT
     */
    public void setJtUsuarisVT(JTable jtUsuarisVT) {
        this.jtUsuarisVT = jtUsuarisVT;
    }

    // INNER_METHODS

    /**
     * Metode que registra els botons de la vista al controlador
     * @param e controlador
     */
    public void registraControlador(ControllerRankingModalitat e) {
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);


    }

    /**
     * Mètode que afegeix els usuaris al ranking
     * @param nom   nom de l'usuari
     * @param punts punts de l'usuari
     */
    public void afegeixUsuariRanking(String nom, long punts, int mode) {
        switch (mode) {
            case 0:
                dtmUsuarisVT.addRow(new Object[]{nom, punts});
                break;

            case 2:
                dtmUsuarisV2.addRow(new Object[]{nom, punts});
                break;

            case 4:
                dtmUsuarisV4.addRow(new Object[]{nom, punts});
                break;
        }
    }

    /**
     * Mètode que buida el ranking
     */
    public void netejaRanking() {
        //System.out.println("Nombre de files: " + dtmUsuarisV2.getRowCount());
        int i = 0;
        while (i < dtmUsuarisV2.getRowCount()) dtmUsuarisV2.removeRow(i);
        i = 0;
        while (i < dtmUsuarisV4.getRowCount()) dtmUsuarisV4.removeRow(i);
        i = 0;
        while (i < dtmUsuarisVT.getRowCount()) dtmUsuarisVT.removeRow(i);
    }
}
