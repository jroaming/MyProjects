package View;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe que mostra la vista per modificar els controls de joc
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewModificarControls extends JDialog {
    private JButton jbCanviar; /** Butó per canviar els controls*/
    private JButton jbSortir; /** Butó per sortir de la vista*/
    private JButton jbUP; /** Butó pel moviment UP*/
    private JButton jbDOWN; /** Butó pel moviment DOWN*/
    private JButton jbLEFT; /** Butó pel moviment LEFT*/
    private JButton jbRIGHT; /** Butó pel moviment RIGHT*/
    private JTextField jtfUP; /** JLabel moviment UP*/
    private JTextField jtfDOWN; /** JLabel moviment DOWN*/
    private JTextField jtfLEFT; /** JLabel moviment LEFT*/
    private JTextField jtfRIGHT; /** JLabel moviment RIGHT*/
    private boolean enableJBCanviar; /** boolea que ens diu si podem canviar o no de controls*/

    // CONSTRUCTOR
    /**
     * Metode constructor de la vista de modificacio de controls
     */
    public ViewModificarControls() {
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());

        Font fontIntro = new Font("OCR A Extended", Font.PLAIN,18);  // creem el tipus de font que farem servir
        Font fontTaula = new Font("OCR A Extended", Font.BOLD,18);  // creem el tipus de font que farem servir
        Font fontButton = new Font("OCR A Extended", Font.PLAIN,20);  // creem el tipus de font que farem servir

        // JLabel de introducccio
        JPanel jpNorth = new JPanel(new FlowLayout());
        jpNorth.setBackground(Color.BLACK);
        JLabel jlIntro = new JLabel(">> Escriu el nou caràcter de control: <<");
        jlIntro.setFont(fontIntro);
        jlIntro.setBackground(Color.BLACK);
        jlIntro.setForeground(Color.CYAN);
        jpNorth.add(jlIntro);
        add(jpNorth, BorderLayout.NORTH);

        // Taula de controls
        JPanel jpControls = new JPanel(new GridLayout(4,2));
        jpControls.setBackground(Color.BLACK);

        // 1. UP
        jbUP = new JButton("UP");
        jbUP.setBackground(Color.BLACK);
        jbUP.setForeground(Color.white);
        jpControls.add(jbUP);
        jtfUP = new JTextField("-");
        jtfUP.setHorizontalAlignment(JLabel.CENTER);
        jtfUP.setBackground(Color.GRAY);
        jtfUP.setForeground(Color.GREEN);
        jtfUP.setFont(fontTaula);
        jbUP.setEnabled(false);
        jpControls.add(jtfUP);

        // 2. DOWN
        jbDOWN = new JButton("DOWN");
        jbDOWN.setBackground(Color.BLACK);
        jbDOWN.setForeground(Color.white);
        jpControls.add(jbDOWN);
        jtfDOWN = new JTextField("-");
        jtfDOWN.setHorizontalAlignment(JLabel.CENTER);
        jtfDOWN.setBackground(Color.GRAY);
        jtfDOWN.setForeground(Color.GREEN);
        jtfDOWN.setFont(fontTaula);
        jbDOWN.setEnabled(false);
        jpControls.add(jtfDOWN);

        // 3. LEFT
        jbLEFT = new JButton("LEFT");
        jbLEFT.setBackground(Color.BLACK);
        jbLEFT.setForeground(Color.white);
        jpControls.add(jbLEFT);
        jtfLEFT = new JTextField("-");
        jtfLEFT.setHorizontalAlignment(JLabel.CENTER);
        jtfLEFT.setBackground(Color.GRAY);
        jtfLEFT.setForeground(Color.GREEN);
        jtfLEFT.setFont(fontTaula);
        jbLEFT.setEnabled(false);
        jpControls.add(jtfLEFT);

        // 4. RIGHT
        jbRIGHT = new JButton("RIGHT");
        jbRIGHT.setBackground(Color.BLACK);
        jbRIGHT.setForeground(Color.white);
        jpControls.add(jbRIGHT);
        jtfRIGHT = new JTextField("-");
        jtfRIGHT.setHorizontalAlignment(JLabel.CENTER);
        jtfRIGHT.setBackground(Color.GRAY);
        jtfRIGHT.setForeground(Color.GREEN);
        jtfRIGHT.setFont(fontTaula);
        jbRIGHT.setEnabled(false);
        jpControls.add(jtfRIGHT);

        // Afegim la taula al JDialos
        getContentPane().add(jpControls,BorderLayout.CENTER);

        // Panell sud, amb els dos ultims butons
        JPanel jpSud = new JPanel(new GridLayout(1,2));
        jbCanviar = new JButton(" > Modificar controls <");
        jbCanviar.setBackground(Color.BLACK);
        jbCanviar.setForeground(Color.CYAN);
        jbCanviar.setFont(fontButton);
        jbSortir = new JButton(" > Tancar finestra < ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontButton);
        jpSud.add(jbCanviar);
        jpSud.add(jbSortir);

        add(jpSud, BorderLayout.SOUTH);

        //setAlwaysOnTop(true);
        pack();
        this.setLocationRelativeTo(null);
    }

    // GETTERS I SETTERS

    /**
     * Getter del buto de canviar
     * @return butó de canviar controls
     */
    public JButton getJbCanviar() {
        return jbCanviar;
    }

    /**
     * Setter del buto de canviar
     * @param jbCanviar buto nou
     */
    public void setJbCanviar(JButton jbCanviar) {
        this.jbCanviar = jbCanviar;
    }

    /**
     * Getter del buto de sortir de la opcio
     * @return buto de sortida (tancar finestra)
     */
    public JButton getJbSortir() {
        return jbSortir;
    }

    /**
     * Setter del buto de sortida
     * @param jbSortir nou buto de tancar finestra
     */
    public void setJbSortir(JButton jbSortir) {
        this.jbSortir = jbSortir;
    }

    /**
     * getter del jtextfield UP
     * @return jtextfield UP
     */
    public JTextField getJtfUP() {
        return jtfUP;
    }

    /**
     * Setter del JTextField UP
     * @param jtfUP nou JTextField del control UP
     */
    public void setJtfUP(JTextField jtfUP) {
        this.jtfUP = jtfUP;
    }

    /**
     * getter del jtextfield DOWN
     * @return jtextfield DOWN
     */
    public JTextField getJtfDOWN() {
        return jtfDOWN;
    }

    /**
     * Setter del JTextField DOWN
     * @param jtfDOWN nou JTextField del control DOWN
     */
    public void setJtfDOWN(JTextField jtfDOWN) {
        this.jtfDOWN = jtfDOWN;
    }

    /**
     * getter del jtextfield LEFT
     * @return jtextfield LEFT
     */
    public JTextField getJtfLEFT() {
        return jtfLEFT;
    }

    /**
     * Setter del JTextField LEFT
     * @param jtfLEFT nou JTextField del control LEFT
     */
    public void setJtfLEFT(JTextField jtfLEFT) {
        this.jtfLEFT = jtfLEFT;
    }

    /**
     * getter del jtextfield RIGHT
     * @return jtextfield RIGHT
     */
    public JTextField getJtfRIGHT() {
        return jtfRIGHT;
    }

    /**
     * Setter del JTextField RIGHT
     * @param jtfRIGHT nou JTextField del control RIGHT
     */
    public void setJtfRIGHT(JTextField jtfRIGHT) {
        this.jtfRIGHT = jtfRIGHT;
    }


    // INNER-METHODS
    /**
     * metode que registra ambdos controladors encarregats de gestionar l'execucio de les funcionalitats dels butons
     * @param controller controlador generic, pels botons de canviar controls i sortir de la finestra
     * @param modificarControls controlador de gestió dels controls de l'usuari a canviar
     */
    public void registraControladorModificarControls(ActionListener controller, DocumentListener modificarControls) {
        jbCanviar.setActionCommand("CANVIAR_CONTROLS");
        jbCanviar.addActionListener(controller);
        jbSortir.setActionCommand("TANCAR_CONTROLS");
        jbSortir.addActionListener(controller);
        jtfUP.getDocument().addDocumentListener(modificarControls);
        jtfDOWN.getDocument().addDocumentListener(modificarControls);
        jtfLEFT.getDocument().addDocumentListener(modificarControls);
        jtfRIGHT.getDocument().addDocumentListener(modificarControls);
    }

    /**
     * Metode que modifica els JLabels de cada control de tecla
     * @param fieldsControls String[] amb els nous controls
     */
    public void setFieldsControls(String[] fieldsControls) {
        this.jtfUP.setText(fieldsControls[0]);
        this.jtfDOWN.setText(fieldsControls[1]);
        this.jtfLEFT.setText(fieldsControls[2]);
        this.jtfRIGHT.setText(fieldsControls[3]);
    }

    /**
     * Metode que retorna un array de String amb els caracters de control
     * @return Array amb els controls de la vista introduïts per l'usuari
     */
    public String[] getFieldsControls() {
        return new String[] {jtfUP.getText(), jtfDOWN.getText(), jtfLEFT.getText(), jtfRIGHT.getText()};
    }

    /**
     * Metode que bloqueja el buto de canviar si ha hagut cap error.
     * @param enableJBCanviar false per defecte, per a quan es genera un error
     */
    public void setEnableJBCanviar(boolean enableJBCanviar) {
        this.jbCanviar.setEnabled(enableJBCanviar);
    }
}
