package View;

import Model.*;
import Controller.*;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * Classe que mostra la vista quan iniciem sessio o ens registrem
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewLogin extends JDialog {
    private JTextField jtfLGN_nick; /** JTextField del nick login*/
    private JPasswordField jtfLGN_pass; /** JPasswordField del pass login*/
    private JTextField jtfRGT_nick; /** JTextField del nick registre*/
    private JPasswordField jtfRGT_pass1; /** JPasswordField del pass1 registre*/
    private JPasswordField jtfRGT_pass2; /** JPasswordField del pass2 registre*/
    private JTextField jtfRGT_email; /** */

    private JButton jbLogin; /** Butó de Login*/
    private JButton jbRegister; /** Butó de Registe*/
    private JButton jbCancel; /** Butó de cancelar*/

    private GridBagConstraints c; /** constraint del GridBagConstraints*/

    private Controller controller; /** controlador de Controller*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * constructor de la vista ViewLogin
     * @param controller
     */
    public ViewLogin(Controller controller) {
        this.c = new GridBagConstraints();   // inicialitzem la variable de les constants del GridBagLayout
        this.controller = controller;

        this.setTitle("Login / Registre");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //this.setUndecorated(true);      //para que no aparezca la barra superior del JDialog -lo quitamos, queda feo
        this.setDefaultCloseOperation(0);
        this.getContentPane().setBackground(Color.BLACK);

        this.setLayout(new GridBagLayout());

        // Creem les instàncies de la finestra:
            // 1. Els atributs de la classe (caselles per escriure):
        jtfLGN_nick = new JTextField();
        jtfLGN_pass = new JPasswordField();
        jtfRGT_nick = new JTextField();
        jtfRGT_pass1 = new JPasswordField();
        jtfRGT_pass2 = new JPasswordField();
        jtfRGT_email = new JTextField();

            // 2. Les fonts que farem servir:
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,20);
        Font fontText = new Font("OCR A Extended", Font.PLAIN,17);
        Font fontPetita = new Font("OCR A Extended", Font.PLAIN,15);

        // Cridem al mètode que omple la finestra amb les instàncies creades:
        this.generarLogin(fontTitol, fontText, fontPetita);
        // Registrem els diferents botons de la finestra:
        this.registraControladorBotonsLogin();
    }


    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter del JTextField del nick del login
     * @return retorna un JTextField
     */

    public JTextField getJtfLGN_nick() {
        return jtfLGN_nick;
    }

    /**
     * Setter del JTextField del nick del login
     * @param jtfLGN_nick JTextField
     */
    public void setJtfLGN_nick(JTextField jtfLGN_nick) {
        this.jtfLGN_nick = jtfLGN_nick;
    }

    /**
     * Getter del JPasswordField del password del login
     * @return retorna un JPasswordField
     */
    public JPasswordField getJtfLGN_pass() {
        return jtfLGN_pass;
    }

    /**
     * Setter del JPasswordField del password del login
     * @param jtfLGN_pass es un JPasswordField
     */
    public void setJtfLGN_pass(JPasswordField jtfLGN_pass) {
        this.jtfLGN_pass = jtfLGN_pass;
    }

    /**
     * Getter del JTextField del nick del registre
     * @return retornem un JTextField
     */
    public JTextField getJtfRGT_nick() {
        return jtfRGT_nick;
    }

    /**
     * Setter del JTextField del nick del registre
     * @param jtfRGT_nick JTextField
     */
    public void setJtfRGT_nick(JTextField jtfRGT_nick) {
        this.jtfRGT_nick = jtfRGT_nick;
    }

    /**
     * Getter del JTextField del password del registre
     * @return retorna un JPasswordField
     */
    public JPasswordField getJtfRGT_pass1() {
        return jtfRGT_pass1;
    }

    /**
     * Setter del JTextField del password del registre
     * @param jtfRGT_pass1 JTextField
     */
    public void setJtfRGT_pass1(JPasswordField jtfRGT_pass1) {
        this.jtfRGT_pass1 = jtfRGT_pass1;
    }

    /**
     * Getter del JTextField del password 2 del registre
     * @return retorna un JPasswordField
     */
    public JPasswordField getJtfRGT_pass2() {
        return jtfRGT_pass2;
    }

    /**
     * Setter del JTextField del password 2 del registre
     * @param jtfRGT_pass2 JTextField
     */
    public void setJtfRGT_pass2(JPasswordField jtfRGT_pass2) {
        this.jtfRGT_pass2 = jtfRGT_pass2;
    }

    /**
     * Getter del JTextField del email del registre
     * @return retorna un JTextField
     */
    public JTextField getJtfRGT_email() {
        return jtfRGT_email;
    }

    /**
     * Setter del JTextField del email del registre
     * @param jtfRGT_email JTextField
     */
    public void setJtfRGT_email(JTextField jtfRGT_email) {
        this.jtfRGT_email = jtfRGT_email;
    }

    /**
     * Getter del Controler del controlador
     * @return retorna un controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Setter del controlador
     * @param controller controlador
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Getter del JButton del Login
     * @return retorna un JButton
     */
    public JButton getJbLogin() {
        return jbLogin;
    }

    /**
     * Setter del JButton del Login
     * @param jbLogin es un JButton
     */
    public void setJbLogin(JButton jbLogin) {
        this.jbLogin = jbLogin;
    }

    /**
     * Getter del JButton del Butó Registre
     * @return
     */
    public JButton getJbRegister() {
        return jbRegister;
    }

    /**
     * Setter del JButton del Butó Registre
     * @param jbRegister es un JButton
     */
    public void setJbRegister(JButton jbRegister) {
        this.jbRegister = jbRegister;
    }

    /**
     * Getter del JButton del Butó Cancel
     * @return
     */
    public JButton getJbCancel() {
        return jbCancel;
    }

    /**
     * Setter del JButton del Butó Cancel
     * @param jbCancel es un JButton
     */
    public void setJbCancel(JButton jbCancel) {
        this.jbCancel = jbCancel;
    }

    // METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    /**
     * Metode que genera la finestra de login
     * @param fTitol    font de titol
     * @param fText     font del text
     * @param fPetita   font petita per a la informació
     */
    private void generarLogin(Font fTitol, Font fText, Font fPetita) {
        // Creem totes les instàncies privades de la classe per fer la finestra de login:

        // a. Labels del Login:
        JLabel jlLGN_nick = new JLabel(controller.getModelFinestraLogin().getLgn_nick());
        JLabel jlLGN_pass = new JLabel(controller.getModelFinestraLogin().getLgn_pass());
        jlLGN_nick.setFont(fText);
        jlLGN_pass.setFont(fText);
        jlLGN_nick.setForeground(Color.white);
        jlLGN_pass.setForeground(Color.white);

        // b. Labels del Registre:
        JLabel jlRGT_nick = new JLabel(controller.getModelFinestraLogin().getRgt_nick());
        JLabel jlRGT_pass1 = new JLabel(controller.getModelFinestraLogin().getRgt_pass1());
        JLabel jlRGT_pass2 = new JLabel(controller.getModelFinestraLogin().getRgt_pass2());
        JLabel jlRGT_correu = new JLabel(controller.getModelFinestraLogin().getRgt_correu());
        jlRGT_nick.setFont(fText);
        jlRGT_pass1.setFont(fText);
        jlRGT_pass2.setFont(fText);
        jlRGT_correu.setFont(fText);
        jlRGT_nick.setForeground(Color.white);
        jlRGT_pass1.setForeground(Color.white);
        jlRGT_pass2.setForeground(Color.white);
        jlRGT_correu.setForeground(Color.white);

        // 1. LOGIN:
            // 1.1. Titol Login:
        JLabel jlLogin = new JLabel(" Ja estàs registrat? Fes LOGIN_");
        jlLogin.setFont(fTitol);
        jlLogin.setForeground(Color.YELLOW);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        this.getContentPane().add(jlLogin, c);
        c.fill = GridBagConstraints.HORIZONTAL;
            // 1.2. Nick Login
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        this.getContentPane().add(jlLGN_nick, c);
            // 1.3. Password Login
        c.gridy = 2;
        this.getContentPane().add(jlLGN_pass, c);
            // 1.4. Nick TextField Login
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.getContentPane().add(jtfLGN_nick, c);
            // 1.5. Password TextField Login
        c.gridy = 2;
        this.getContentPane().add(jtfLGN_pass, c);
            // 1.6 Botó Login
        jbLogin = new JButton("LOGIN");
        jbLogin.setBackground(Color.white);
        jbLogin.setFont(fTitol);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        jbLogin.setEnabled(false);
        this.getContentPane().add(jbLogin, c);

        // 2. REGISTRE
            // 2.1. Titol Registre
        JLabel jlRegistre = new JLabel(" No tens compte? REGISTRA'T aquí_");
        jlRegistre.setForeground(Color.LIGHT_GRAY);
        jlRegistre.setFont(fTitol);
        jlRegistre.setForeground(Color.YELLOW);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        this.getContentPane().add(jlRegistre, c);
        c.fill = GridBagConstraints.HORIZONTAL;
            // 2.2. Nick Registre
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.WEST;
        this.getContentPane().add(jlRGT_nick, c);
            // 2.3. Pass1 Registre
        c.gridy = 6;
        this.getContentPane().add(jlRGT_pass1, c);
            // 2.4. Pass2 Registre
        c.gridy = 7;
        this.getContentPane().add(jlRGT_pass2, c);
            // 2.5. Correu Registre
        c.gridy = 8;
        this.getContentPane().add(jlRGT_correu, c);
            // 2.6. Nick TextField Registre
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.getContentPane().add(jtfRGT_nick, c);
            // 2.7. Password TextField Registre
        c.gridy = 6;
        this.getContentPane().add(jtfRGT_pass1, c);
            // 2.8. Password again TextField Registre
        c.gridy = 7;
        this.getContentPane().add(jtfRGT_pass2, c);
            // 2.9. Correu TextField Registre
        c.gridy = 8;
        this.getContentPane().add(jtfRGT_email, c);
            // 2.10. Botó Registre
        jbRegister = new JButton("REGISTRE");
        jbRegister.setBackground(Color.white);
        jbRegister.setFont(fTitol);
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        jbRegister.setEnabled(false);
        this.getContentPane().add(jbRegister, c);

        // 3. Botó CANCEL
        jbCancel = new JButton("SORTIR");
        jbCancel.setBackground(Color.LIGHT_GRAY);
        jbCancel.setFont(fTitol);
        c.gridy = 10;
        c.fill = GridBagConstraints.NONE;
        this.getContentPane().add(jbCancel, c);

        // Instruccions per als camps:
        JLabel jlInstruccions1 = new JLabel("Nick: més de 3 caracters__");
        JLabel jlInstruccions2 = new JLabel("Password: més de 5 caràcters + numeros + MAJUS. + minus.__");
        JLabel jlInstruccions3 = new JLabel("Si et registres, comprova que escrius la mateixa password!__");
        JLabel jlInstruccions4 = new JLabel("Si fas login, pots fer servir el teu nick o el correu__");
        jlInstruccions1.setForeground(Color.CYAN);
        jlInstruccions1.setFont(fPetita);
        jlInstruccions2.setForeground(Color.CYAN);
        jlInstruccions2.setFont(fPetita);
        jlInstruccions3.setForeground(Color.CYAN);
        jlInstruccions3.setFont(fPetita);
        jlInstruccions4.setForeground(Color.CYAN);
        jlInstruccions4.setFont(fPetita);
        c.anchor = GridBagConstraints.EAST;
        c.weighty = 0.0;
        c.gridy = 11;
        this.getContentPane().add(jlInstruccions1, c);
        c.gridy = 12;
        this.getContentPane().add(jlInstruccions2, c);
        c.gridy = 13;
        this.getContentPane().add(jlInstruccions3, c);
        c.gridy = 14;
        this.getContentPane().add(jlInstruccions4, c);
    }

    /**
     * Registra el controlador dels butons
     */
    public void registraControladorBotonsLogin() {
        jbLogin.setActionCommand(ModelLogin.LGN_LOGIN);
        jbLogin.addActionListener(controller);
        jbRegister.setActionCommand(ModelLogin.LGN_REGISTRE);
        jbRegister.addActionListener(controller);
        jbCancel.setActionCommand(ModelLogin.LGN_CANCEL);
        jbCancel.addActionListener(controller);
    }

    /**
     * Metode que registra el controlador que comprova els camps dels JTextFields, el DocumentListener
     * @param controladorCamps controlador que implementa el DocumentListener
     */
    public void registraControladorCampsLogin(DocumentListener controladorCamps) {
        jtfLGN_nick.getDocument().addDocumentListener(controladorCamps);
        jtfLGN_pass.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_nick.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_pass1.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_pass2.getDocument().addDocumentListener(controladorCamps);
        jtfRGT_email.getDocument().addDocumentListener(controladorCamps);
    }

    /**
     * Metode que buida tots els camps de la finestra de login d'inici de sessio del client
     */
    public void buidaCamps() {
        this.jtfLGN_nick.setText("");
        this.jtfLGN_pass.setText("");
        this.jtfRGT_nick.setText("");
        this.jtfRGT_pass1.setText("");
        this.jtfRGT_pass2.setText("");
        this.jtfRGT_email.setText("");
    }

}
