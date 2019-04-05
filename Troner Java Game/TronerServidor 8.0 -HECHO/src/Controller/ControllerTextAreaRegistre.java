package Controller;

import View.ViewRegistre;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Classe que implementa les funcions de gestió d'entrada de dades de la finestra de registre: cada vegada que escrivim un
 * caràcter, comprovarà la validesa del text introduït i actuarà en conseqüència (bloquejant o no els butons)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControllerTextAreaRegistre implements DocumentListener {
    private Controller controller;      /**controlador principal del joc*/
    private ViewRegistre  viewRegistre; /**vista de registre*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Constructor de la classe de control
     * @param controller classe controladora principal, ja que els butons els controlarà la classe Controller
     * @param viewRegisre
     */
    public ControllerTextAreaRegistre(Controller controller, ViewRegistre viewRegisre) {
        this.controller = controller;
        this.viewRegistre = viewRegisre;
    }

    /**
     * Metode que detecta quan l'usuari ecriu un caràcter i fa la comprovacio de la validesa de la info introduïda
     * @param e controlador de l'event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }

    /**
     * Metode que detecta quan l'usuari esborra un caràcter i fa la comprovacio de la validesa de la info introduïda
     * @param e controlador de l'event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }

    /**
     * Metode que detecta quan l'usuari sobreescriu un caràcter i fa la comprovacio de la validesa de la info introduïda
     * @param e controlador de l'event
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        comprovarCampsRegistre();
    }


    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter del controlador de la classe
     * @return controlador
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Setter del controlador principal de la finestra, per a les funcions dels JButton's
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    // METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Metode que comprova si el nom de login i contrassenya son vàlids, tenen majustcules i minuscules, el password i
     * password again tenen el mateix password i mes de 3 caracters, així com si el correu segueix l'estil
     * <caracter>@<caracter>.<caracter>, per desbloquejar els butons i que l'uusari es pugui registrar al sistema
     */
    public void comprovarCampsRegistre() {
        if (viewRegistre.getJtfRGT_nick().getText().length() >= 3
                && passwordValid(viewRegistre.getJtfRGT_pass1().getPassword())
                && passwordValid(viewRegistre.getJtfRGT_pass2().getPassword())
                && passwordsCoincideixen(viewRegistre.getJtfRGT_pass1().getPassword(), viewRegistre.getJtfRGT_pass2().getPassword())
                && emailValid(viewRegistre.getJtfRGT_email().getText())) {
            viewRegistre.getJbRegister().setEnabled(true);
        } else {
            viewRegistre.getJbRegister().setEnabled(false);
        }
    }

    /**
     * Metode que comprova si el password introduït per l'usuari és correcte
     * @param password contrassenya a comprovar
     * @return si es vàlida la contrassenya
     */
    public boolean passwordValid(char[] password) {
        boolean passCorrecte = false;
        if (password.length > 5) {  // la contrassenya deu tenir 6 o més caràcters
            for (int i = 0; i < password.length && !passCorrecte; i++) { // ha de tenir, com a mínim, una minúscula
                if (password[i] >= 'a' && password[i] <= 'z') { // si té minúscula, comprovem que tingui també una majúscula
                    for (int j = 0; j < password.length && !passCorrecte; j++) {
                        if (password[j] >= 'A' && password[j] <= 'Z') { // si té minúscula, comprovem que tingui també una majúscula
                            for (int z = 0; z < password.length; z++) {
                                if (password[z] >= '0' && password[z] <= '9') {
                                    passCorrecte = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return passCorrecte;
    }

    /**
     * Metode que comprova si els password i password_again coincideixen
     * @param pass1 password
     * @param pass2 password again
     * @return boolea que indica si son iguals
     */
    public boolean passwordsCoincideixen(char[] pass1, char[] pass2) {
        if (pass1.length != pass2.length) {
            return false;
        } else {
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i] != pass2[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metode que comprova si l'email introduït és vàlid
     * @param email email introduit per l'ususari
     * @return boolea de si és correcte
     */
    public boolean emailValid(String email) {
        boolean arrovaTrobada = false;
        int indexArrova = 0;

        for (int i = 0; i < email.length(); i++) {
            if (!arrovaTrobada && i != 0 && email.charAt(i) == '@') { // si _@
                arrovaTrobada = true;
                indexArrova = i;
            }
            if (arrovaTrobada
                    && email.charAt(i) == '.'
                    && (i - indexArrova > 1)    // hi ha, com a mínim, un caracter entre l''@' i el '.'
                    && ++i < email.length()) {  // hi ha, com a mínim, un caracter després del '.' (.com, per exemple)
                return true;
            }
        }
        return false;
    }
}
