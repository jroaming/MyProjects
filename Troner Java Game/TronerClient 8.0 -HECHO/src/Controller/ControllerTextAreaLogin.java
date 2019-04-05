package Controller;

import View.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * Classe que implementa les funcions de gestió d'entrada de dades de la finestra de login: cada vegada que escrivim un
 * caràcter, comprovarà la validesa del text introduït i actuarà en conseqüència (bloquejant o no els butons)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControllerTextAreaLogin implements DocumentListener {
    private ViewLogin viewLogin; /** vista del login*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Constructor de la classe controllerTextAreaLogin
     * @param viewLogin l'hi passem la vista al constructor
     */
    public ControllerTextAreaLogin(ViewLogin viewLogin) {
        this.viewLogin = viewLogin;
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha escrit més d'un caràcter al camp Login
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        comprovarCampsLogin();
        comprovarCampsRegistre();
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha eliminat algun caràcter als camps del Login
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        comprovarCampsLogin();
        comprovarCampsRegistre();
    }

    /**
     * Metode que mostra una finestra informativa si l'usuari ha canviat algun caràcter als camps del Login
     * @param e listener del document a comprovar (controlador)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        //chivato
        //System.out.println("Sustituido un caracter");
        comprovarCampsLogin();
        comprovarCampsRegistre();
    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter de la vista del login
     * @return retorna un ViewLogin
     */
    public ViewLogin getViewLogin() {
        return viewLogin;
    }

    /**
     * Setter de la vista Login
     * @param viewLogin se l'hi passa aquest viewLogin per
     */
    public void setViewLogin(ViewLogin viewLogin) {
        this.viewLogin = viewLogin;
    }

    // METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    /**
     * Metode que comprova si el nom de login i contrassenya son vàlids, tenen majustcules i minuscules, el password
     * te mes de 3 caracters, així com si el correu segueix l'estil
     * <caracter>@<caracter>.<caracter>, per desbloquejar els butons i que l'uusari es pugui registrar al sistema
     */
    public void comprovarCampsLogin() {
        if (viewLogin.getJtfLGN_nick().getText().length() >= 3 && passwordValid(viewLogin.getJtfLGN_pass().getPassword())) {
            viewLogin.getJbLogin().setEnabled(true);
        } else {
            viewLogin.getJbLogin().setEnabled(false);
        }
    }

    /**
     * Metode que comprova si els camps de Registre son vàlids, tenen majustcules i minuscules, el password i
     * password again tenen el mateix password i mes de 3 caracters, el correu te així com si el correu segueix l'estil
     * <caracter>@<caracter>.<caracter>, per desbloquejar els butons i que l'uusari es pugui registrar al sistema
     */
    public void comprovarCampsRegistre() {
        if (viewLogin.getJtfRGT_nick().getText().length() >= 3
                && passwordValid(viewLogin.getJtfRGT_pass1().getPassword())
                && passwordValid(viewLogin.getJtfRGT_pass2().getPassword())
                && passwordsCoincideixen(viewLogin.getJtfRGT_pass1().getPassword(), viewLogin.getJtfRGT_pass2().getPassword())
                && emailValid(viewLogin.getJtfRGT_email().getText())) {
            viewLogin.getJbRegister().setEnabled(true);
        } else {
            viewLogin.getJbRegister().setEnabled(false);
        }
    }

    /**
     * Metode que comprova si el password introduït per l'usuari és correcte
     * @param password contrassenya a comprovar
     * @return si es vàlida la contrassenya
     */
    public boolean passwordValid(char[] password) {
        boolean minus = false;  //si hi ha minuscules
        boolean majus = false;  //si hi ha majuscules
        boolean nombr = false;  //si hi ha numeros
        if (password.length > 5) {  // la contrassenya deu tenir 6 o més caràcters
            for (int i = 0; i < password.length; i++) { // ha de tenir, com a mínim, una minúscula
                if (!minus && password[i] >= 'a' && password[i] <= 'z') {
                    minus = true;
                }
                if (!majus && password[i] >= 'A' && password[i] <= 'Z') {
                    majus = true;
                }
                if (!nombr && password[i] >= '0' && password[i] <= '9') {
                    nombr = true;
                }
            }
        }
        return nombr && minus && majus;
    }

    /**
     * Metode que verifica si el password es igual al password again
     * @param pass1 cadena de caraters que forman el password
     * @param pass2 cadena de caraters que forman el password again
     * @return retorna un boolea que ens indica si el pass1 es igual al pass2
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
     * Metode que verifica si el email compleix amb els requisits
     * @param email es passa una string que es el emial, per comprovar si correcta
     * @return retorna un boolea que ens dius si l'email era correcta
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
