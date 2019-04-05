import Controller.Controller;
import Model.ModelMenu;
import Model.ModelRegistre;
import Model.ReproductorMP3;

import javax.swing.*;

/**
 * Classe que conté el procediment géneric (main) d'ús del servidor de Troner.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class TronerServidor {
    /**
     * Metode d'execucio del programa Troner Servidor
     * @param args arguments inicials
     */
    public static void main(String[] args) {
        try {
            // 1) Creamos el modelo del menú y el controlador de éste, para las opciones que escoja ejecutar el admin.
            ModelMenu modelMenu = new ModelMenu();
            ModelRegistre modelRegistre = new ModelRegistre();

            // 2) Creem la classe controller, que maneja tota la lògica del joc i la gestió de la sessio i les partides dels usuaris.
            Controller controller = new Controller(modelMenu, modelRegistre);

            // 3) Registramos el controlador del menú principal:
            controller.getVistaMenuPrincipal().registraControladorBotonsMenu(controller);

            // 4) Mostramos la vista del menú al haber cargado todos los anteriores pasos
            controller.getVistaMenuPrincipal().setVisible(true);

            // EXTRA:
            //ReproductorMP3 gameSong = new ReproductorMP3(); //empieza a sonar la canción (por ahora lo quitamos).

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR D'EXECUCIÓ DEL JOC!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
