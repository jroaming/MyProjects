package Network;

import Controller.*;
import Model.*;
import Controller.Timer;
import View.PartidaButoSortir;
import View.ViewPartida;
import View.ViewWaitingLobby;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
/**
 * Classe encarregada de gestionar la transmissió de informació d'una partida entre client i servidor
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class PartidaClient extends Thread {
    private static final int ROWS = 46; /** constant del nombre de files del grid del joc */
    public static final int COLS = 110; /** constant del nombre de columnes del grid del joc */

    private boolean running;    /** booleano que indica que la partida se está ejecutando, lo recibe del servidor */

    private ConnexioUsuari con; /** conexion con el servidor, para transmiitir informacion */
    //private int matriu_inicial[][];   // ESTA ES LA DEL SERVIDOR
    private int[][] matriu_actual;  /** matriz actual de la pantalla de juego a printar */
    private Usuari[] players;   /** array de jugadore de la partida */
    private Usuari myUser;      /** usuari que ens representa a nosaltres al array de jugadors de la partida */
    private int myUserIndex;    /** posicio que ocupem al array de jugadors de la partida (més comoditat)  */
    private int njugadors;      /** numero de jugadores de la partida (2 o 4) */
    private int tipusPartida;   /** int que representa el tipo de partida (0=versus2 / 2=versus2 / 4=versus4)*/

    private ViewWaitingLobby vistaWaitingLobby;

    private ViewPartida vistaPartida;
    private PartidaButoSortir vistaPartidaSortir;
    private Controller controller;

    private Timer timer;

    /**
     * Metode constructor de la classe partida
     * @param tipusPartida int que indica el tipus partida:
     */
    public PartidaClient(int tipusPartida, ConnexioUsuari con, Usuari me, ViewWaitingLobby vistaWaitingLobby, Controller controller) {
        this.vistaWaitingLobby = vistaWaitingLobby;
        this.con = con;
        this.tipusPartida = tipusPartida;
        switch (tipusPartida) {
            case 2:
                this.njugadors = 2;
                break;
            case 4:
                this.njugadors = 4;
                break;
            case 0:
                this.njugadors = 4;
                break;
        }
        this.matriu_actual = new int[ROWS][COLS];
        this.running = false;
        this.players = new Usuari[njugadors];
        this.myUser = me;
        matriu_actual = new int[ROWS][COLS];
        this.controller = controller;

    }

    /**
     * Metode que s'encarrega de rebre un array amb els JUGADORS de la partida i actualitzar el nostre (me)
     */
    public void carregaUsuaris() {
        try {
            for (int i = 0; i < njugadors; i++) {
                players[i] = new Usuari();
                players[i] = (Usuari) this.con.repObject();
                if (players[i].getNickname().equals(myUser.getNickname())) {
                    myUser = players[i];
                    myUserIndex = i; // actualitzem la nostra posicio al array de jugadors
                }
            }
        } catch (ClassNotFoundException e) {
            this.running = false;
        } catch (IOException e) {
            this.running = false;
            System.out.println("Error al crear los jugadores.");
        }
    }

    /**
     * Metode run del Thread
     */
    @Override
    public void run() {
        String resposta = "";
        try {
            // Aquest es el boolea de si ha creat una partida
            this.running = this.con.getDinput().readBoolean();

            if (running) {
                // Hem entrat a la partida
                this.myUser.setOnGame(true);
                // Tanquem la vista de waiting for game:
                this.vistaWaitingLobby.setVisible(false);
                this.carregaUsuaris();
                // Creem la vista del joc i registrem el controlador
                this.vistaPartida = new ViewPartida(tipusPartida, this.players, this.controller);
                this.vistaPartida.registraControlador(controller);
                vistaPartida.setGrid(matriu_actual);
                vistaPartida.setVisible(true);
                // Creem el buto de sortir de la partida
                this.vistaPartidaSortir = new PartidaButoSortir();
                this.vistaPartidaSortir.registraControlador(controller);
                this.vistaPartidaSortir.setVisible(true);

                boolean novaRonda = false;
                boolean primeraRonda = true;
                boolean alguExit = false;
                int iWinner = 5;

                // Compta-Enrere d'inici de la partida
                this.vistaPartida.setEnabled(false);
                this.vistaPartida.mostraComptaEnrere(3);
                this.vistaPartida.mostraComptaEnrere(2);
                this.vistaPartida.mostraComptaEnrere(1);
                this.vistaPartida.setEnabled(true);

                this.timer = new Timer(0);
                this.timer.start();

                // Mentre duri la partida
                while (!alguExit) {
                    alguExit = this.con.repOpcio(); // ESTO ES EL BOOLEANO QUE INDICA SI ALGUIEN HA HECHO EXIT
                    if (!alguExit) { // SI NADIE HA HECHO EXIT SIGUE CON EL PROCEDIMIENTO HABITUAL:
                        try {
                            // leemos el aviso de nueva ronda
                            novaRonda = this.con.repOpcio();
                            iWinner = this.con.getDinput().readInt();

                            // actualizamos los winners
                            if (novaRonda) {    // si es una nova ronda, rebrà més dades: el compte enrere
                                this.reiniciaMatriu();
                                this.timer.setStop(true);
                                this.timer = null;
                                this.timer = new Timer(0);
                                this.timer.start();
                                this.reiniciaDireccioJugadors();
                                players[myUserIndex].setWins(this.vistaPartida.getWinsPlayer(myUserIndex));
                            }
                            this.repGrid();     // recibe la grid del servidor

                            // Rep l'array d'usuaris del servidor
                            for (int i = 0; i < njugadors; i++) players[i] = this.con.rebreUsuariServidor();
                            this.vistaPartida.setTime(this.timer.getTime());

                            //System.out.println("Va a actualizar los jugadores");
                            this.vistaPartida.actualitzaJugadors(iWinner, players[myUserIndex].getPunts_partida());
                            this.vistaPartida.actualitzaPunts(njugadors, players);
                            sleep(50);
                        } catch (InterruptedException e) {
                        } catch (IOException e) {
                            System.out.println("Ha petao el IOException");
                        }
                    }
                }

                this.vistaPartida.setVisible(false);    // ocultem la partida
                this.vistaPartidaSortir.setVisible(false);  // ocultem el boto

                this.con.enviaOpcio("WINS/"+this.players.length+"/"+this.players[myUserIndex].getNickname()+"/"+this.vistaPartida.getPunts(njugadors)[myUserIndex]+"/"+this.vistaPartida.getWins(njugadors)[myUserIndex]);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Espera detinguda!", "WWARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metode qie reiniica les direccions
     */
    private void reiniciaDireccioJugadors() {
        switch (myUserIndex) {
            case 0:
                this.players[myUserIndex].setDireccion(4);
                break;
            case 1:
                this.players[myUserIndex].setDireccion(3);
                break;
            case 2:
                this.players[myUserIndex].setDireccion(2);
                break;
            case 3:
                this.players[myUserIndex].setDireccion(1);
                break;

        }
    }

    /**
     * Metode que reinicia la matriu actual de joc
     */
    private void reiniciaMatriu() {
        for (int i = 0; i < ROWS; i++) for (int j = 0; j < COLS; j++) {
            this.matriu_actual[i][j] = 0;
        }
        this.vistaPartida.reiniciaMatriu();
    }


    /**
     * Metode que espera a rebre un valor tipus int[][] (grid del joc)
     * @return retorna la grid enviada pel servidor
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void repGrid() throws ClassNotFoundException, IOException {
        String[] aux;
        int[] ints = new int[3];
        for (int i = 0; i < njugadors; i++) {
            aux = this.con.respostaServidor().split("/");
            ints[0]=Integer.parseInt(aux[0]);
            ints[1]=Integer.parseInt(aux[1]);
            ints[2]=Integer.parseInt(aux[2]);

            //System.out.println("Missatge: '"+aux[0]+"/"+aux[1]+"/"+aux[2]+"',");  // LO RECIBE BIEN
            this.matriu_actual[ints[0]][ints[1]] = ints[2];
            vistaPartida.actualitzaCasella(ints[0],ints[1],ints[2]);
        }
    }

    /**
     * Metode que envia la ordre al servidor
     * @param direccio direccio a canviar
     * @throws IOException
     */
    public void enviaOrdre(int direccio) throws IOException {
        this.con.enviaOpcio(this.players[myUserIndex].getNickname()+"/"+direccio);

    }

}
