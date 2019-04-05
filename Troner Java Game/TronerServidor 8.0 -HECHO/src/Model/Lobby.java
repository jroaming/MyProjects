package Model;

import Network.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe equivalent a la sala d'espera del matchamking del joc, pot ser de 2, 4 jugadors o en mode campionat; i està
 * constantment repasant la llista de jugadors per veure si n'hi han suficients per començar una partida del tipus en qüestió
 * (2, 4 o tornaig), a més de gestionar la llista d'usuaris connectats al servidor que han iniciat sessió, i estan esperant
 * per jugar una
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Lobby {
    private int nplayers;   /** int que indica el tipo de lobby que es (cuántos jugadores debe haber por partida) (si es 0 -> torneig) */
    private ArrayList<Usuari> cuaJugadors;      /** array list dels usuaris connectats (que JA HAN FET LOGIN)*/
    private ArrayList<Partida> llistaPartides;  /** llista de partides en execució */
    private ThreadServerActiu serverGeneral;    /** servidor general, per saber l'estat del servidor */
    private boolean torneig;    /** boolea que indica si es un torneig, la partida, per distingir entre 4 jugadors i torneig*/

    // CONSTRUCTORS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_

    /**
     * Metode constructor de la classe Lobby
     * @param nombre_jugadors int que indica el tipus de partides que rebrà el lobby, de 2 (2) o 4 (4 si es normal o 0
     *                        si es torneig) jugadors
     */
    public Lobby(int nombre_jugadors) {
        nplayers = nombre_jugadors;
        if (nombre_jugadors == 0) {
            this.torneig = true;
            nplayers = 4;
        } else {
            this.torneig = false;
        }
        cuaJugadors = new ArrayList<Usuari>();
        llistaPartides = new ArrayList<Partida>();
    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter de Nplayers, agafem el numero de players
     * @return nplayers
     */
    public int getNplayers() {
        return nplayers;
    }

    /**
     * Setter del Nplayers, inicialitzem el numero de players que hi ha
     * @param nplayers
     */
    public void setNplayers(int nplayers) {
        this.nplayers = nplayers;
    }

    /**
     * Getter de cuaJugadors, agafem l'array de usuaris
     * @return ArrayList de Usuari
     */
    public ArrayList<Usuari> getCuaJugadors() {
        return cuaJugadors;
    }

    /**
     * Setter de CuaJugador, inicialitzem l'array de jugadors
     * @param cuaJugadors
     */
    public void setCuaJugadors(ArrayList<Usuari> cuaJugadors) {
        this.cuaJugadors = cuaJugadors;
    }

    /**
     * Getter de LlistaPartides, agafem l'array de partidas
     * @return ArrayList de partidas
     */
    public ArrayList<Partida> getLlistaPartides() {
        return llistaPartides;
    }

    /**
     * Setter de LlistaPartides, inicialitzem la llista de partidas
     * @param llistaPartides
     */
    public void setLlistaPartides(ArrayList<Partida> llistaPartides) {
        this.llistaPartides = llistaPartides;
    }

    /**
     * Getter del ThreadServidorActiu, per saber si està actiu o tancat
     * @return ThreadServerActiu
     */
    public ThreadServerActiu getServerGeneral() {
        return serverGeneral;
    }

    /**
     * Setter del ThreadServerActiu, per inicialitzar-lo
     * @param serverGeneral servidorActiu
     */
    public void setServerGeneral(ThreadServerActiu serverGeneral) {
        this.serverGeneral = serverGeneral;
    }

    // INNER-METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    /**
     * Afegeix un usuari a la llista de usuaris que han iniciat sessió i esperen per jugar.
     * @param player    jugador que afegirem a la llista
     */
    public void addPlayer(Usuari player) {
        player.setPlaying(false);
        cuaJugadors.add(player);
        this.comprobarCua();
    }

    /**
     * Metode que extrau un usuari de la cua del lobby que pertoqui
     * @param player jugador a eliminar
     * @return boole que indica si l'ha trobat i, per tant, eliminat
     */
    public boolean remPlayer(Usuari player) {
        player.setPlaying(false);
        boolean found = false;
        for (int i = 0; !found && i<cuaJugadors.size(); i++){
            if (cuaJugadors.get(i).getNickname().equals(player.getNickname())) {
                cuaJugadors.remove(i);
                found = true;
            }
        }
        this.comprobarCua();
        return found;
    }

    /**
     * Metode que comprova si hi ha suficients jugadors d'un tipus de cua per començar una nova partida
     */
    private void comprobarCua() {
        try {
            int usuarisTrobats = 0;
            int[] iUsuarisPartida = new int[this.nplayers];

            // Busca si hi ha suficients usuaris.
            for (int i = 0; i < this.cuaJugadors.size() && usuarisTrobats < this.nplayers; i++) {
                if (!cuaJugadors.get(i).isOnGame()) {
                    iUsuarisPartida[usuarisTrobats] = i;
                    usuarisTrobats++;
                }
            }

            // Crea una partida
            if (usuarisTrobats == nplayers) {
                // Creem l'array d'usuaris
                Usuari[] users = new Usuari[nplayers];
                // L'omplim
                for (int u = 0; u < nplayers; u++) {
                    users[u] = this.cuaJugadors.get(iUsuarisPartida[u]);
                }
                // Creem la partida
                creaPartida(users);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de partida!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que genera una partida a partir d'un array d'usuaris, i l'afegeix a la llista
     * @param usuaris array d'ususaris de la partida, per inicialitzar-la
     */
    public void creaPartida(Usuari[] usuaris) throws IOException {
        Partida p = null;
        // Posem que els usuaris estàn jugant:
        for (Usuari u: usuaris) u.setPlaying(true);
        // Enviem el booleano a los jugadores inseridos en la partida
        this.enviaAvisIniciPartida(usuaris);
        // Creem la partida:
        if (nplayers == 2) {
            p = new Partida(2, usuaris[0], usuaris[1], false, this.serverGeneral, this.llistaPartides.size());
        } else {
            if (!torneig) {
                p = new Partida(4, usuaris[0], usuaris[1], usuaris[2], usuaris[3], false, this.serverGeneral, this.llistaPartides.size());
            } else {
                p = new Partida(4, usuaris[0], usuaris[1], usuaris[2], usuaris[3], true, this.serverGeneral, this.llistaPartides.size());
            }
        }
        // Afegim la partida a la llista de partides del lobby
        this.llistaPartides.add(p);

        // Comencem la partida
        p.start();

    }

    /**
     * Metode que envia l'avis (boolea) A CADA USUARI de que la partida s'està iniciant
     * @param usersPartida array d'usuaris de la partida, aquells a qui avisar
     */
    private void enviaAvisIniciPartida(Usuari[] usersPartida) throws IOException {
        for (Usuari u : usersPartida) { //per cada usuari de la partida
            for (int j = 0; j < this.cuaJugadors.size(); j++) { // per cada jugador de la cua
                if (u.getNickname().equals(this.cuaJugadors.get(j).getNickname())) {    // si el nom coincideix amb el de la cua
                    for (int i = 0; i < this.serverGeneral.getLlistaSockets().size(); i++) {
                        if (this.serverGeneral.getLlistaSockets().get(i).getUsuari().getNickname().equals(u.getNickname())) {
                            this.serverGeneral.getLlistaSockets().get(i).enviaBoolea(true);
                        }   // este es el booleano de que ha encontrao partida
                    }
                }
            }
        }
    }
}
