package Network;

import Model.*;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe que gestiona la llista de servidors dedicats del servidor general, a més de servir d'enllaç per a connectar
 * funcions entre altres classes i contenir l'array de Lobby, sales d'espera de cada mode de joc (2, 4 i torneig)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ThreadServerActiu extends Thread {  //AIXO SEMPRE ESTARÀ ACTIU, I ANIRÀ REBENT USUARIS.
    private DBInfo dbinfo;  /** dbinfo, amb tota la informació sobre els clients, ports i altres dades per establir les connexions
     necessàries amb els servidors i la base de dades*/
    private ServerSocket sServer;   /** server socket que actua com a servidor general del servidor del Troner */
    private ArrayList<ServerDedicat> llistaServers;    /** llista de servidors dedicats actius del programa */

    private boolean servidorObert;  /** flag que indica si el servidor està obert */
    private Lobby[] llistaLobbies;  /** llista de lobbies del servidor general (tres sales d'espera diferents) */

    // CONSTRUCTOR

    /**
     * Constructor de la classe
     * @param dbinfo dbinfo amb què igualar el nostre atribut
     * @param llistaLobbies llista de sales d'espera amb què omplir el servidor general
     */
    public ThreadServerActiu(DBInfo dbinfo, Lobby[] llistaLobbies) {
        this.dbinfo= dbinfo;
        this.servidorObert = false;
        this.llistaServers = new ArrayList<>();
        this.llistaLobbies = llistaLobbies;
    }

    // GETTERS I SETTERS

    /**
     * Getter del dbinfo
     * @return dbinfo de la classe
     */
    public DBInfo getDbinfo() {
        return dbinfo;
    }

    /**
     * Getter del servidor general del Troner
     * @return servidor general del joc
     */
    public ServerSocket getsServer() {
        return sServer;
    }

    /**
     * Llista dels sockets oberts actualment
     * @return llista de sockets oberts
     */
    public ArrayList<ServerDedicat> getLlistaSockets() {
        return llistaServers;
    }

    /**
     * Getter de l'estat del servidor general
     * @return boolea de si el servidor està obert
     */
    public boolean isServidorObert() {
        return servidorObert;
    }

    /**
     * Setter de l'estat del servidor
     * @param servidorObert nou estat del servidor
     */
    public void setServidorObert(boolean servidorObert) {
        this.servidorObert = servidorObert;
    }

    /**
     * Getter de la llista de lobbies del sistema
     * @return llista de lobbies (classe Lobby)
     */
    public Lobby[] getLlistaLobbies() {
        return llistaLobbies;
    }

    /**
     * Setter de la llista de lobbies del servidor general
     * @param llistaLobbies nova llista de lobbies
     */
    public void setLlistaLobbies(Lobby[] llistaLobbies) {
        this.llistaLobbies = llistaLobbies;
    }

    // METHODS

    /**
     * Metode run sobreescrit que executa el procediment del server general, i espera i va afegint sockets a la llista
     */
    @Override
    public void run() {
        try {
            sServer = new ServerSocket(dbinfo.getPort_client()); //rep el port llegit al .json
            this.servidorObert = true;

            while (servidorObert) {
                // 1. Creem el servidor dedicat, que espera que el client es connecti per poder acceptar-lo:
                Socket sUsuari = sServer.accept();
                ServerDedicat sDedicat = new ServerDedicat(sUsuari, dbinfo, llistaLobbies, this);
                // 2. El runejem
                sDedicat.start();
                // 3. L'afegim a la llista de ServidorsDedicats
                llistaServers.add(sDedicat);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Espera d'usuaris detinguda, servidor tancat!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metode que tanca el servidor dedicat enviat per parametre
     * @param serverDedicat servidorDedicat a treure de la llista.
     */
    public void tancaServidorDedicat(ServerDedicat serverDedicat) {
        try {
            sleep(250);
            serverDedicat.getSocket().close();
            this.llistaServers.remove(serverDedicat);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Error al tancar el servidor dedicat!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualitzar la llista de servidors dedicats!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }
}
