import Model.*;
import Controller.*;
import View.ViewPartida;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

/**
 * Classe principal del Client, aqui generem les classes principals pel funcionament del programa
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class TronerClient {
    /**
     * Metode principal del programa
     * @param args es una String[]
     */
    public static void main(String[] args) {
        try {
            // 1. Inicialitzem i creem el controlador mitjançant el model
            ModelMenu modelMenu = new ModelMenu();
            ModelLogin modelLogin = new ModelLogin();
            ModelConfiguracio modelConfig = new ModelConfiguracio();
            Controller controller = new Controller(modelLogin, modelMenu, modelConfig);


            // 2. Mostrem la finestra de configuracio per a la connexió amb el server (el Controller la genera al contructor)
            controller.getVistaConfiguracio().setVisible(true);

            // Extra: musica
            //ReproductorMP3 gameSong = new ReproductorMP3(); //empieza a sonar la canción (por ahora lo quitamos).

        } catch (Exception e) {
            System.out.println("ERROR A L'EXECUCIÓ DEL JOC!");
        }
    }
}
