package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * Classe que mostra la vista de la configuració de la IP del server i el port
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewConfiguracio extends JFrame {
    private JLabel jlIPServer;
    private JLabel jlPort;
    private JTextField jtfIPServer;
    private JTextField jtfPort;

    private JButton jbConnectar;
    private JButton jbSortir;
    private JLabel jlResultat;

    private GridBagConstraints c;

    private Controller controller;

    // CONSTRUCTOR

    public ViewConfiguracio(Controller controller) {
        this.controller = controller;

        this.c = new GridBagConstraints();

            // 0. Inicialitzem la finestra:
        this.setTitle("Configuració Server Troner");
        this.setSize(500,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

            // 1. Les fonts que farem servir:
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,20);
        Font fontText = new Font("OCR A Extended", Font.PLAIN,17);
        Font fontPetita = new Font("OCR A Extended", Font.PLAIN,15);

            // 2. Creem les classes que farem servir
        this.jlIPServer = new JLabel(controller.getModelFinestraConfiguracio().getIP());
        this.jtfIPServer = new JTextField();
        this.jlPort = new JLabel(controller.getModelFinestraConfiguracio().getPORT());
        this.jtfPort = new JTextField();
        this.jbConnectar = new JButton(controller.getModelFinestraConfiguracio().getCONNECTAR());
        this.jbSortir = new JButton(controller.getModelFinestraConfiguracio().getSORTIR());
        this.jlResultat = new JLabel();

            // 3. Canviem el tipus de lletra:
        this.jlIPServer.setFont(fontText);
        this.jlPort.setFont(fontText);
        this.jlResultat.setFont(fontPetita);
        this.jbConnectar.setFont(fontTitol);
        this.jbSortir.setFont(fontTitol);

        this.generarFinestra();

    }

    // GETTERS I SETTERS

    /**
     * Getter  del JLabel de la IP del server
     * @return retorna un JLabel
     */
    public JLabel getJlIPServer() {
        return jlIPServer;
    }

    /**
     * Setter del JLabel de la IP del server
     * @param jlIPServer es un JLabel
     */
    public void setJlIPServer(JLabel jlIPServer) {
        this.jlIPServer = jlIPServer;
    }

    /**
     * Getter del Port
     * @return retorna un JLabel
     */
    public JLabel getJlPort() {
        return jlPort;
    }

    /**
     * Setter del Port
     * @param jlPort es un JLabel
     */
    public void setJlPort(JLabel jlPort) {
        this.jlPort = jlPort;
    }

    /**
     * Getter del JTextField de la IP del servidor
     * @return retorna un JTextField
     */
    public JTextField getJtfIPServer() {
        return jtfIPServer;
    }

    /**
     * Setter del JTextField de la IP del servidor
     * @param jtfIPServer es un JTextField
     */
    public void setJtfIPServer(JTextField jtfIPServer) {
        this.jtfIPServer = jtfIPServer;
    }

    /**
     * Getter del JTextField del Port
     * @return retorna un JTextField
     */
    public JTextField getJtfPort() {
        return jtfPort;
    }

    /**
     * Setter del JTextField del Port
     * @param jtfPort
     */
    public void setJtfPort(JTextField jtfPort) {
        this.jtfPort = jtfPort;
    }

    /**
     * Getter del JButton del Butó Connectar
     * @return retorna un JButton
     */
    public JButton getJbConnectar() {
        return jbConnectar;
    }

    /**
     * Setter del JButton del Butó Connectar
     * @param jbConnectar es un JButton
     */
    public void setJbConnectar(JButton jbConnectar) {
        this.jbConnectar = jbConnectar;
    }

    /**
     * Getter del JButton del Butó Sortir
     * @return retorna un JButton
     */
    public JButton getJbSortir() {
        return jbSortir;
    }

    /**
     * Setter del JButton deL Butó Sortir
     * @param jbSortir
     */
    public void setJbSortir(JButton jbSortir) {
        this.jbSortir = jbSortir;
    }

    /**
     * Getter del JLabel del Resultat
     * @return retorna un JLabel
     */
    public JLabel getJlResultat() {
        return jlResultat;
    }

    /**
     * Setter del JLabel del Resultat
     * @param jlResultat es un JLabel
     */
    public void setJlResultat(JLabel jlResultat) {
        this.jlResultat = jlResultat;
    }



    // METHODS

    /**
     * Constructor de la vista
     */
    private void generarFinestra() {
        // 1. JLabel + JTextField IP_SERVER
        c.weightx = 0.0;
        c.weighty = 0.4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        this.getContentPane().add(jlIPServer, c);
        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.getContentPane().add(jtfIPServer, c);

        // 2. JLabel + JTextField PORT_SERVER
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        this.getContentPane().add(jlPort, c);
        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.getContentPane().add(jtfPort, c);

        // 3. JButton Connectar + JButton Sortir
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.3;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(jbConnectar, c);
        c.gridx = 1;
        c.weightx = 1.0;
        this.getContentPane().add(jbSortir, c);

        // 4. JLabel del resultat:
        c.weighty = 0.4;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(jlResultat, c);
        jlResultat.setText("(esperant ordre)");

    }

    /**
     * Metode que registra els botons de la vista al controlador
     */
    public void registrarBotonsConfiguracio() {
        this.jbConnectar.setActionCommand(controller.getModelFinestraConfiguracio().getConfigConnectar());
        this.jbConnectar.addActionListener(controller);
        this.jbSortir.setActionCommand(controller.getModelFinestraConfiguracio().getConfigSortir());
        this.jbSortir.addActionListener(controller);
    }
}
