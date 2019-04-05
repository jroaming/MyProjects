package View;

import Model.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Classe que mostra la vista principal del client, es a dir, el menú
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewMenu extends JFrame {
    private GridBagConstraints constraints; /** constraints del gridbagconstraints*/
    private Controller controladorMenuPrincipal; /** controlador del controladorMenuPrincipal*/

    private JLabel jlTitol; /** JLabel del titol*/
    private JLabel jlMode; /** JLabel del mode*/
    private JButton jbOpcio1; /** Butó de l'opcio 1*/
    private JButton jbOpcio2; /** Butó de l'opcio 2*/
    private JButton jbOpcio3; /** Butó de l'opcio 3*/
    private JButton jbOpcio4; /** Butó de l'opcio 4*/
    private JButton jbOpcio5; /** Butó de l'opcio 5*/
    private JButton jbOpcio6; /** Butó de l'opcio 6*/
    private JButton jbOpcioSortir; /** Butó de l'opcio sortir*/

    // CONSTRUCTOR

    /**
     * constructor del ViewMenu
     * @param controlMenu constructor
     */
    public ViewMenu(Controller controlMenu) {
        controladorMenuPrincipal = controlMenu;
        constraints = new GridBagConstraints();

        // CONFIGURACIO DEL MENU:
        setTitle("Troner");
        setLayout(new BorderLayout());
        setSize(1280,720);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CREACIO DELS ELEMENTS DEL MENU:

        // 0. indicador de la sessió: ADMIN
        jlMode = new JLabel(controlMenu.getModelMenuPrincipal().getMODE());
        jlMode.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        jlMode.setForeground(Color.CYAN);
        // 1. titol
        jlTitol = new JLabel(controladorMenuPrincipal.getModelMenuPrincipal().getTITLE());
        jlTitol.setFont(new Font("OCR A Extended", Font.BOLD, 120));
        jlTitol.setForeground(Color.WHITE);
        // 2. opcions
        jbOpcio1 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO1());
        jbOpcio1.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio1.setForeground(Color.white);
        jbOpcio1.setBackground(Color.black);
        jbOpcio2 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO2());
        jbOpcio2.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio2.setForeground(Color.white);
        jbOpcio2.setBackground(Color.black);
        jbOpcio3 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO3());
        jbOpcio3.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio3.setForeground(Color.white);
        jbOpcio3.setBackground(Color.black);
        jbOpcio4 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO4());
        jbOpcio4.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio4.setForeground(Color.white);
        jbOpcio4.setBackground(Color.black);
        jbOpcio5 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO5());
        jbOpcio5.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio5.setForeground(Color.white);
        jbOpcio5.setBackground(Color.black);
        jbOpcio6 = new JButton(controlMenu.getModelMenuPrincipal().getOPCIO6());
        jbOpcio6.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcio6.setForeground(Color.white);
        jbOpcio6.setBackground(Color.black);
        jbOpcioSortir = new JButton(controlMenu.getModelMenuPrincipal().getOPCIOSORTIR());
        jbOpcioSortir.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        jbOpcioSortir.setForeground(Color.white);
        jbOpcioSortir.setBackground(Color.black);


        // INSERCIO DELS ELEMENTS AL MENU:
            // -> Añadimos el background:
        JPanelBackground backgroundMainMenu = new JPanelBackground();
        backgroundMainMenu.setBackground("assets/tron_menu.jpg");
        getContentPane().add(backgroundMainMenu, BorderLayout.CENTER);
        getContentPane().add(jlMode, BorderLayout.NORTH);
        generarMenu(backgroundMainMenu);

    }

    private void generarMenu(JPanelBackground backgroundMainMenu) {
        backgroundMainMenu.setLayout(new GridBagLayout());

        // -> Mode de la sessió:
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        backgroundMainMenu.add(jlMode, constraints);

        // -> Títol:
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        backgroundMainMenu.add(jlTitol, constraints);

        constraints.weighty = 0.05;

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridwidth = 2;
        // -> Opcio1:
        constraints.gridy = 1;
        backgroundMainMenu.add(jbOpcio1, constraints);
        // -> Opcio2:
        constraints.gridy = 2;
        backgroundMainMenu.add(jbOpcio2, constraints);
        // -> Opcio3:
        constraints.gridy = 3;
        backgroundMainMenu.add(jbOpcio3, constraints);
        // -> Opcio4:
        constraints.gridy = 4;
        backgroundMainMenu.add(jbOpcio4, constraints);
        // -> Opcio5:
        constraints.gridy = 5;
        backgroundMainMenu.add(jbOpcio5, constraints);
        // -> Opcio6:
        constraints.gridy = 6;
        backgroundMainMenu.add(jbOpcio6, constraints);
        // -> OpcioSortir:
        constraints.gridy = 7;
        backgroundMainMenu.add(jbOpcioSortir, constraints);

    }

    // GETTERS I SETTERS

    /**
     * Getter del controladorMenuPrincipal
     * @return retorna el controlador
     */
    public Controller getControladorMenuPrincipal() {
        return controladorMenuPrincipal;
    }

    /**
     * Setter del controladorMenuPrincipal
     * @param controladorMenuPrincipal el controlador
     */
    public void setControladorMenuPrincipal(Controller controladorMenuPrincipal) {
        this.controladorMenuPrincipal = controladorMenuPrincipal;
    }

    // METHODS

    /**
     * Metode que registre els botons de la vista al controaldor
     */
    public void registraControladorBotonsMenu() {
        // Opcio 1  -> ranking per modalitat
        jbOpcio1.setActionCommand(ModelMenu.BTN_OPCIO1);
        jbOpcio1.addActionListener(controladorMenuPrincipal);
        // Opcio 2  -> modificar els controls del jugador en qüestió
        jbOpcio2.setActionCommand(ModelMenu.BTN_OPCIO2);
        jbOpcio2.addActionListener(controladorMenuPrincipal);
        // Opcio 3  -> jugar vs 1
        jbOpcio3.setActionCommand(ModelMenu.BTN_OPCIO3);
        jbOpcio3.addActionListener(controladorMenuPrincipal);
        // Opcio 4  -> jugar vs 4
        jbOpcio4.setActionCommand(ModelMenu.BTN_OPCIO4);
        jbOpcio4.addActionListener(controladorMenuPrincipal);
        // Opcio 5  -> jugar campionat
        jbOpcio5.setActionCommand(ModelMenu.BTN_OPCIO5);
        jbOpcio5.addActionListener(controladorMenuPrincipal);
        // Opcio 6  -> tancar sessió
        jbOpcio6.setActionCommand(ModelMenu.BTN_OPCIO6);
        jbOpcio6.addActionListener(controladorMenuPrincipal);
        // Opcio SORTIDA    -> sortir -.-
        jbOpcioSortir.setActionCommand(ModelMenu.BTN_OPCIOSORTIR);
        jbOpcioSortir.addActionListener(controladorMenuPrincipal);
    }

    /**
     * Metode encarregat de bloquejar els jbuttons, per evitar errors de caire de l'usuari
     * @param enabled valor boolea que volem passar al enable dels butons
     */
    public void setJButtonsEnable(boolean enabled) {
        this.jbOpcio1.setEnabled(enabled);
        this.jbOpcio2.setEnabled(enabled);
        this.jbOpcio3.setEnabled(enabled);
        this.jbOpcio4.setEnabled(enabled);
        this.jbOpcio5.setEnabled(enabled);
        this.jbOpcio6.setEnabled(enabled);
        this.jbOpcioSortir.setEnabled(enabled);
    }
}
