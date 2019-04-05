package View;

import javax.swing.*;
import java.awt.*;

/**
 * Classe casilla que conforma la matriz de panels que generan la grid
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class Casilla extends JPanel {
    private int num_player; /** num del jugador que ha ocupao la casilla */

    /**
     * Constructor de la clase casilla
     * @param num_player
     */
    public Casilla(int num_player){
        this.num_player = num_player;
        actualitza(num_player);
    }

    /**
     * Metodo que actualiza la casilla por el valor enviado por parametro, cambiando el color de fondo del panel
     * @param num_player jugador que ha ocupado la casilla
     */
    public void actualitza(int num_player){
        this.num_player = num_player;
        switch (num_player){
            case 0:
                setBackground(Color.BLACK);
                break;
            case 1:
                setBackground(Color.GREEN);
                break;
            case 2:
                setBackground(Color.BLUE);
                break;
            case 3:
                setBackground(Color.RED);
                break;
            case 4:
                setBackground(Color.YELLOW);
                break;
            default:
                setBackground(Color.GRAY);
                break;
        }
    }

    /**
     * Getter del player que ha ocupat aquesta casella
     * @return int del player en qüestió
     */
    public int getNum_player() {
        return num_player;
    }
}
