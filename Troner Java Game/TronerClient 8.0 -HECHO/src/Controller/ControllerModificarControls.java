package Controller;

import Network.ConnexioUsuari;
import View.ViewModificarControls;
import com.mysql.jdbc.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Classe que controla els camps d'escritura de la vista ViewModificaControls
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ControllerModificarControls implements DocumentListener {
    private ViewModificarControls vistaModificarControls; /** vista del viewModificaControls*/
    private boolean waiting; /** boolea que indica si esta esperant*/
    private String[] controls; /** array de string on guardem els controls*/
    private boolean sending; /** boolea que indica si ha enviat*/

    /**
     * Constructor del controlador ControllerModificaControls
     * @param vista se l'hi passa aquest parametre al constructor
     */
    public ControllerModificarControls(ViewModificarControls vista) {
        this.vistaModificarControls = vista;
        this.waiting = false;
        controls = new String[4];
        sending = true;
    }

    /**
     * Getter dels controls seleccionats per l'usuari
     * @return retorna un String[] amb els codis dels controls (son int's, pero ho guardem con strings)
     */
    public String[] getControls() {
        return controls;
    }

    /**
     * Metode que actualitza els controls configurats actualment pels que rep per parametre i ho cancela i oho deixa
     * com estava si algun està repetit.
     * @param controls controls nous.
     * @return boolea que indica si hi ha hagut error al modificar els controls (estaven repetits)
     */
    public boolean setControls(String[] controls) {
        // Si arriba aqui és que no hi ha hagut errors:
        this.controls = controls;
        this.vistaModificarControls.setFieldsControls(controls);    // esto es lo que explota
        return true;
    }

    /**
     * Setter del flag 'sending' per evitar problemes.
     * @param sending
     */
    public void setSending(boolean sending) {
        this.sending = sending;
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha escrit més d'un caràcter als camps dels controls
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (!sending) this.vistaModificarControls.setEnableJBCanviar(this.comprovaControls());
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha eliminat algun caràcter als camps dels controls
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        if (!sending) this.vistaModificarControls.setEnableJBCanviar(this.comprovaControls());
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha canviat algun caràcter als camps dels controls
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!sending) this.vistaModificarControls.setEnableJBCanviar(this.comprovaControls());
    }

    /**
     * Metodqe que comprova els controls introduits per l'usuari: si hi ha cap amb més d'un caràcter
     * @return boolea que indica si no hi ha cap error (true) o algun dels camps té més d'un caràcter (false)
     */
    private boolean comprovaControls() {
        controls = this.vistaModificarControls.getFieldsControls();
        for (int i = 0; i < 4; i++) {   //passa a upperCase si és minúscula
            if (controls[i].length()==1) {    // si hi ha cap caracter PASSA'L A MAJUS
                if (Character.isLowerCase(controls[i].charAt(0))) {
                    controls[i] = controls[i].toUpperCase();
                    //chivato
                    System.out.println("nous controls: "+controls[0]+"/"+controls[1]+"/"+controls[2]+"/"+controls[3]+".");
                }
                for (int j = 0; j < 4; j++) {   // compara amb els demés controls
                    if ((j != i) && controls[j].equals(controls[i])) return false;  // si no es la mateixa tecla i la combinació és la mateixa, error
                }
            } else {
                return false;   // perque vol dir que algun camp es buit
            }
        }
        return true;
    }

    /**
     * Metode que envia al servidor els controls actual.litzats
     * @param connexioUsuari connexio amb el servidor, per poder enviar els controls
     */
    public void enviaControls(ConnexioUsuari connexioUsuari) throws IOException {
        String missatge = "";
        for (int i = 0; i < 4; i++) {
            missatge += controls[i];
            if (i < 3) missatge+="/";
        }
        // chivato
        System.out.println("Missatge enviat al servidor: "+missatge);
        // Ara enviem el missatge al servidor:
        connexioUsuari.enviaOpcio(missatge);
    }

}
