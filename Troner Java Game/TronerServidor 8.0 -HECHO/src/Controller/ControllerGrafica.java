package Controller;

import Model.DBInfo;
import View.ViewGrafica;
import View.ViewOpcioGrafica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Classe controladora que implementa les funcions que gestionen la gràfica dels jugadors
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControllerGrafica implements ActionListener {
    private ViewOpcioGrafica vistaOpcioGrafica; /**vista que demana les opcions als usuaris*/
    private ViewGrafica vistaGrafica;   /**vista de la gràfica*/
    private DBInfo dbInfo;  /**variable d'acces a la info de la base de dades*/

    /**
     * Constructor de la gràfica
     * @param vistaOpcioGrafica vista de les opcions de la gràfica
     * @param dbinfo variable de database
     * @param vistaGrafica vista de la grafica
     */
    public ControllerGrafica(ViewOpcioGrafica vistaOpcioGrafica, DBInfo dbinfo,ViewGrafica vistaGrafica){
        this.vistaOpcioGrafica = vistaOpcioGrafica;
        this.vistaGrafica = vistaGrafica;
        this.dbInfo = dbinfo;
    }

    /**
     * Metode que gestiona les comandes de la grafica, salir, crearla y hacer la query
     * @param e controlador de eventos de la vista
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "TANCAR":
                vistaOpcioGrafica.setVisible(false);
                vistaOpcioGrafica.buidaJCombobox();
                break;
            case "FER QUERY":
                String q = "";
                q = montarQuery();
                try {
                    int i = 0;
                    Integer[] grafica = dbInfo.controllerDBInfo.buscarInfoGrafica(q);
                    i = buscarMaxPuntuacio(grafica);
                    vistaGrafica.limitPunts(i);
                    vistaGrafica.inserirNumPartides(contarParitdas(grafica));
                    vistaGrafica.valorRectangles(grafica);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                vistaGrafica.setVisible(true);
                break;
        }
    }

    /**
     * Metode que omple la combobox de la grafica amb un array de noms dels usuaris
     * @throws SQLException
     */
    public void omplirComboboxUsuaris() throws SQLException {
        vistaOpcioGrafica.setJComboboxUsuari(dbInfo.controllerDBInfo.buscarUsuaris());
    }

    /**
     * Metodo que llena la combobox de las modalidades
     */
    public void omplirComboboxModalitat() {
        String[] modalitat = new String[3];
        modalitat[0] = "Mode V2";
        modalitat[1] = "Mode V4";
        modalitat[2] = "Mode Torneig";
        vistaOpcioGrafica.setJComboboxModalitat(modalitat);
    }

    /**
     * Metode que retorna la query segons les seleccions de la combobox
     * @return query formada
     */
    public String montarQuery(){
        String q = "";
        q += "SELECT punts_partida FROM juga AS j, partida WHERE j.id_partida = partida.id_partida AND nom_usuari LIKE '" + vistaOpcioGrafica.getComboboxUsuari() + "' AND tipus =" + vistaOpcioGrafica.getComboboxModalitat()+ ";";
        return q;
    }

    /**
     * Metode que busca la máxima puntuació
     * @param grafica array de ints amb els valors
     * @return retorna el maxim de ints
     */
    public int buscarMaxPuntuacio(Integer[] grafica){
        int max = 0;
        for(int i = 0; i < grafica.length; i++){
            if(max < grafica[i]){
                max = grafica[i];
            }
        }
        return max;
    }

    /**
     * Metode que compta les partides
     * @param s array de ints de les puntuacions
     * @return retorna el nombre de partides
     */
    public int contarParitdas(Integer[] s){
        int numP = 0;
        while(numP < s.length){
            numP++;
        }
        return numP;
    }
}