package Model;

import Network.ServerDedicat;

/**
 * Classe jugador del joc, la utilizada durant la partida, on gestionem les posicions i direccions, i que conte els atributs
 * per a actualitzar les matrius de la vista, etc.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Jugador {
    private int direccio;   /**direccio on esta mirant el jugador*/
    private int posx;   /**posx del jugador*/
    private int posy;   /**posy del jugador*/
    private int wins;   /**wins del jugador*/
    private Usuari usuari;  /**usuari al que representa aquest jugador*/
    private ServerDedicat server;   /**server a on està connectat aquest jugador*/

    /**
     * Constructor de la classe jugador
     * @param direccio direccio del jugador
     * @param posx posicio inicial x
     * @param posy posicio inicial y
     * @param usuari usuari a qui representa aquest jugador durant la partida
     */
    public Jugador(int direccio, int posx, int posy, Usuari usuari){
        this.direccio = direccio;
        this.posx = posx;
        this.posy = posy;
        this.wins = 0;
        this.usuari = usuari;
    }

    /**
     * Getter del nombre de victories d'aquest jugador
     */
    public int getWins(){
        return wins;
    }

    /**
     * Getter de la posicio X
     * @return int que equival a la posicio X a la grid de la partida
     */
    public int getPosx() {
        return posx;
    }

    /**
     * Getter de la posicio Y del jugador a la grid de la partida
     * @return int de la posicio Y a la matriu
     */
    public int getPosy() {
        return posy;
    }

    /**
     * Setter de la posicio X del jugador
     * @param posx nova posicio X
     */
    public void setPosx(int posx) {
        this.posx = posx;
    }

    /**
     * Setter de la posicio Y del jugador
     * @param posy nova posicio Y del jugador
     */
    public void setPosy(int posy) {
        this.posy = posy;
    }


    /**
     * Getter de l'usuari
     * @return el nostre usuari
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Metode per actualitzar l'uusari en qüestió
     * @param usuari nou usuari
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Getter del servidor dedicat per on està connectat el nostre jugador
     * @return ServidorDedicat
     */
    public ServerDedicat getServer() {
        return server;
    }

    /**
     * Setter del servidor dedicat
     * @param server nou servidor
     */
    public void setServer(ServerDedicat server) {
        this.server = server;
    }

    /**
     * Metode que reseteja els jugadors
     * @param i index del jugador
     * @param rows nombre de files de la matriu
     * @param cols nombre de columnes de la matriu
     */
    public void resetPosicioIDireccio(int i, int rows, int cols, Jugador player) {
        switch (i) {
            case 0:
                player.direccio = 4;
                player.posx = 5;
                player.posy = rows/2;
                break;
            case 1:
                player.direccio = 3;
                player.posx = cols-5;
                player.posy = rows/2;
                break;
            case 2:
                player.direccio = 2;
                player.posx = cols/2;
                player.posy = 5;
                break;
            case 3:
                player.direccio = 1;
                player.posx = cols/2;
                player.posy = rows-5;
                break;

        }
    }
}
