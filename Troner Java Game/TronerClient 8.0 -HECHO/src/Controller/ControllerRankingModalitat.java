package Controller;

import Model.Usuari;
import View.ViewRankingModalitat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe que controla els camps de escritura de ViewModificaControls
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ControllerRankingModalitat implements ActionListener{
    ViewRankingModalitat vistaRankingModalitat; /** vista del ViewRankingModalitat*/
    String[] usuarisV2; /** array de Strings dels noms usuaris de la modalitat de joc dos jugadors */
    String[] usuarisV4; /** array de Strings dels noms usuaris de la modalitat de joc quatre jugadors*/
    String[] usuarisVT; /** array de Strings dels noms usuaris de la modalitat de joc torneig*/
    long[]   puntsv2; /** array de long de la puntació dels usuaris de la modalitat de joc dos jugadors*/
    long[]   puntsv4; /** array de long de la puntació dels usuaris de la modalitat de joc quatre jugadors*/
    long[]   puntsvT; /** array de long de la puntació dels usuaris de la modalitat de joc torneig*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Constructor de la classe Controladora del Ranking per Modalitat de joc
     * @param vista vista de la qual en som controladors
     */
    public ControllerRankingModalitat(ViewRankingModalitat vista) {
        this.vistaRankingModalitat = vista;
        usuarisV2 = new String[10];
        usuarisV4 = new String[10];
        usuarisVT = new String[10];
        puntsv2 = new long[10];
        puntsv4 = new long[10];
        puntsvT = new long[10];
    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Metode que retorna un array de noms d'usuaris de la modalitat versus2
     * @return array dels noms dels usuaris
     */
    public String[] getUsuarisV2() {
        return usuarisV2;
    }

    /**
     * Setter de l'array d'usuaris de la modalitat versus2
     * @param usuarisV2 array pel qual actualitzar el nostre
     */
    public void setUsuarisV2(String[] usuarisV2) {
        this.usuarisV2 = usuarisV2;
    }

    /**
     * Metode que retorna un array de noms d'usuaris de la modalitat versus4
     * @return array dels noms dels usuaris
     */
    public String[] getUsuarisV4() {
        return usuarisV4;
    }

    /**
     * Setter de l'array d'usuaris de la modalitat versus4
     * @param usuarisV4 array pel qual actualitzar el nostre
     */
    public void setUsuarisV4(String[] usuarisV4) {
        this.usuarisV4 = usuarisV4;
    }

    /**
     * Metode que retorna un array de noms d'usuaris de la modalitat torneig
     * @return array dels noms dels usuaris
     */
    public String[] getUsuarisVT() {
        return usuarisVT;
    }

    /**
     * Setter de l'array d'usuaris de la modalitat torneig
     * @param usuarisVT array pel qual actualitzar el nostre
     */
    public void setUsuarisVT(String[] usuarisVT) {
        this.usuarisVT = usuarisVT;
    }

    /**
     * Metode que retorna un array dels punts de la modalitat versus2
     * @return array de la puntuació
     */
    public long[] getPuntsv2() {
        return puntsv2;
    }

    /**
     * Metode que actualitza el notre array de punts dels usuaris de la modalitat versus2
     * @param puntsv2 array de punts nous
     */
    public void setPuntsv2(long[] puntsv2) {
        this.puntsv2 = puntsv2;
    }

    /**
     * Metode que retorna un array dels punts de la modalitat versus4
     * @return array de la puntuació
     */
    public long[] getPuntsv4() {
        return puntsv4;
    }

    /**
     * Metode que actualitza el notre array de punts dels usuaris de la modalitat versus4
     * @param puntsv4 array de punts nous
     */
    public void setPuntsv4(long[] puntsv4) {
        this.puntsv4 = puntsv4;
    }

    /**
     * Metode que retorna un array dels punts de la modalitat torneig
     * @return array de la puntuació
     */
    public long[] getPuntsvT() {
        return puntsvT;
    }

    /**
     * Metode que actualitza el notre array de punts dels usuaris de la modalitat torneig
     * @param puntsvT array de punts nous
     */
    public void setPuntsvT(long[] puntsvT) {
        this.puntsvT = puntsvT;
    }

    /**
     * Metode que retorna la vista del ranking per modalitat dels usuaris
     * @return retorna la vista, per poder fer crides de les seves funcions
     */
    public ViewRankingModalitat getVistaRankingModalitat() {
        return vistaRankingModalitat;
    }

    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Metode que actualitza el ranking de cada modalitat dels usuaris de la base de dades
     * @param usuaris array d'usuaris per modalitat (matriu de noms d'usuari)
     * @param punts array de punts d'usuari per modalitat (matriu de punts)
     * @throws SQLException
     */
    public void actualitzaRankingModalitat(String[][] usuaris, long[][] punts) throws SQLException {
        vistaRankingModalitat.netejaRanking();

        for (int i = 0; i < 10; i++) {
            vistaRankingModalitat.afegeixUsuariRanking(usuaris[0][i], punts[0][i],2);
        }
        for (int i = 0; i < 10; i++) {
            vistaRankingModalitat.afegeixUsuariRanking(usuaris[1][i], punts[1][i],4);
        }
        for (int i = 0; i < 10; i++) {
            vistaRankingModalitat.afegeixUsuariRanking(usuaris[2][i], punts[2][i],0);
        }

    }

    /**
     * Accio que espera a rebre la opció seleccionada per l'usuari i fa les crides pertinents
     * @param e pulsació en qüestió
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TANCAR":
                vistaRankingModalitat.setVisible(false);
                break;
        }
    }
}
