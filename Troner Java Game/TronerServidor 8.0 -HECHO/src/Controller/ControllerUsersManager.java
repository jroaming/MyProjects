package Controller;

import Model.DBInfo;
import View.ViewUsersManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe que implementa les funcions de control de la taula de gestio dels usuaris (opcio2)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControllerUsersManager implements ActionListener {
    ViewUsersManager vistaTaulaUsuaris; /**vista de la taula dels usuaris*/
    DBInfo database;    /**variable d'acces a la database a on buscar la informacio*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Constructor del controlador
     * @param vista vista de la opcio
     * @param dbinfo var d'acces a la database (amb el camp ControlDBinfo)
     */
    public ControllerUsersManager(ViewUsersManager vista, DBInfo dbinfo) {
        this.vistaTaulaUsuaris = vista;
        this.database = dbinfo;
    }


    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter de la variable de acceso a la database
     * @return var de acceso a la database
     */
    public DBInfo getDatabase() {
        return database;
    }


    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TANCAR":
                vistaTaulaUsuaris.setVisible(false);
                break;
            case "ESBORRAR":
                vistaTaulaUsuaris.eliminaUsuariSeleccionat(this);
                break;
        }
    }

    /**
     * Mètode que actualitza la taula d'usuaris.
     * @throws SQLException si hi ha cap problema al fer la comanda al mysql
     */
    public void actualitzaTaula() throws SQLException{
        vistaTaulaUsuaris.netejaTaula();
        String query = "select nom, punts_totals, data_registre, data_ultim_acces from usuari";
        ResultSet usuaris = database.controllerDBInfo.selectQuery(query);
        while (usuaris.next()) {
            vistaTaulaUsuaris.afegeixUsuari(usuaris.getString("nom"),
                                            usuaris.getLong("punts_totals"),
                                            usuaris.getDate("data_registre"),
                                            usuaris.getDate("data_ultim_acces"));
        }
    }

}
