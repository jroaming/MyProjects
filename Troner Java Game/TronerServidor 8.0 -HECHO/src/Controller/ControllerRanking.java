package Controller;

import Model.DBInfo;
import View.ViewRanking;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe que implementa les funcions que controlen el ranking de jugadors
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControllerRanking implements ActionListener{
    private ViewRanking vistaRanking;   /**vista del ranking*/
    private DBInfo database;    /**variable d'on demanarem la info per omplir el ranking*/

    // CONSTRUCTOR -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Constructor del ranking
     * @param vista vista del ranking
     * @param dbinfo variable d'acces a la info de la base de dades
     */
    public ControllerRanking(ViewRanking vista, DBInfo dbinfo) {
        this.vistaRanking = vista;
        this.database = dbinfo;
    }

    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Metode que actualitza el ranking del total de punts dels jugadors de la base de dades
     * @throws SQLException
     */
    public void actualitzaRanking() throws SQLException {
        vistaRanking.netejaRanking();
        String query = "select nom, punts_totals, data_ultim_acces from usuari order by punts_totals desc;";
        ResultSet usuaris = database.controllerDBInfo.selectQuery(query);
        while (usuaris.next()) {
            vistaRanking.afegeixUsuariRanking(usuaris.getString("nom"),
                    usuaris.getLong("punts_totals"),
                    usuaris.getDate("data_ultim_acces"));
        }
    }

    /**
     * Metode que gestione si l'usuari ha clickat el buto de tancar el ranking
     * @param e controlador de l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TANCAR":
                vistaRanking.setVisible(false);
                break;
        }
    }
}
