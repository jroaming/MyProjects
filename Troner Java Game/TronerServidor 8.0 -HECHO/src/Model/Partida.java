package Model;

import Network.ServerDedicat;
import Network.ThreadServerActiu;
import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Classe que conté totes les dades i arrays d'informació i que s'utilitzen durant la partida, per enviar dades, i realitza
 * el procediment que es va repetint, per ronda, mentre dura la partida
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Partida extends Thread {
    private static final int ROWS = 46; /** constant del nombre de files del grid del joc */
    public static final int COLS = 110; /** constant del nombre de columnes del grid del joc */

    private int[][] grid;     /** grid del joc */
    private Jugador[] jugadors;  /** llista de jugadors de la partida (2 o 4) */
    private Date    data;       /** data de la partida, per a la base de dades */
    private int     iGuanyador;  /** guanyador de la partida */
    private int     njugadors;  /** nombre de jugadors de la partida (valor del lobby) */
    private boolean torneig;    /** booleà que indica si és torneig o no */
    private ThreadServerActiu servidorGeneral;  /** ThreadServerActiu, per poder enviar a jugador el servidorDedicat on es troba */

    private boolean partidaTerminada;   /** boolea que indica si la partida està terminada */
    private boolean ultimaRonda;    /** boolea que passa a valer true quan un usuari prem la tecla de sortida */

    private boolean empat;  /** boolea que indica si hi ha hagut un empat */

    // NUEVO
    private int iPartida;   /** index que senyala quina partida de la llista es la nostra */

    // CONSTRUCTORS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_
    /**
     * Constructor de la partida per a 2 jugadors (només mode no torneig)
     * @param njugadors nombre de jugadors de la partida (el mateix que el del lobby)
     * @param player1   usuari 1 enviat pel lobby a aquesta partida
     * @param player2   usuari 2 enviat pel lobby a aquest partida
     * @param torneig   booleà que indica si la partida és mode torneig
     * @param iPartida  index de la partida en qüestió
     * @param servidorGeneral  servidor general de la partida (ThreadServidorActiu)
     */
    public Partida(int njugadors, Usuari player1, Usuari player2, boolean torneig, ThreadServerActiu servidorGeneral, int iPartida) {
        this.njugadors = njugadors;
        this.jugadors = new Jugador[2];
        this.torneig = torneig;
        this.jugadors = new Jugador[njugadors];
        this.jugadors[0] = new Jugador(4, 5, ROWS/2, player1);
        this.jugadors[1] = new Jugador(3, COLS-5, ROWS/2, player2);
        this.data = new java.sql.Timestamp(System.currentTimeMillis());
        this.servidorGeneral = servidorGeneral;

        this.iPartida = iPartida;

        // Creem el grid:
        this.grid = new int[ROWS][COLS];

        // Nuevo:
        this.partidaTerminada = false;
        this.ultimaRonda = false;
        this.iGuanyador = 4;
        this.empat = false;
    }

    /**
     * Constructor de la partida per a 2 jugadors (només mode no torneig)
     * @param njugadors nombre de jugadors de la partida (el mateix que el del lobby)
     * @param p1    usuari 1 enviat pel lobby a aquesta partida
     * @param p2    usuari 2 enviat pel lobby a aquest partida
     * @param p3    usuari 3 enviat pel lobby a aquesta partida
     * @param p4    usuari 4 enviat pel lobby a aquesta partida
     * @param torneig   booleà que indica si la partida és mode torneig
     * @param iPartida  index de la partida en qüestió
     * @param servidorGeneral  servidor general de la partida (ThreadServidorActiu)
     */
    public Partida(int njugadors, Usuari p1, Usuari p2, Usuari p3, Usuari p4, boolean torneig, ThreadServerActiu servidorGeneral, int iPartida) {
        this.njugadors = njugadors;
        this.jugadors = new Jugador[4];
        this.torneig = torneig;
        this.jugadors = new Jugador[njugadors];
        this.jugadors[0] = new Jugador(4, 5, ROWS/2, p1);
        this.jugadors[1] = new Jugador(3, COLS-5, ROWS/2, p2);
        this.jugadors[2] = new Jugador(2, COLS/2, 5, p3);
        this.jugadors[3] = new Jugador(1, COLS/2, ROWS-5, p4);
        this.servidorGeneral = servidorGeneral;
        this.data = new java.sql.Timestamp(System.currentTimeMillis());

        // Creem el grid:
        this.grid = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) grid[i] = new int[COLS];

        // Nuevo:
        this.partidaTerminada = false;
        this.ultimaRonda = false;
        this.iGuanyador = 4;
        this.empat = false;
        this.iPartida = iPartida;

    }

    //GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_
    /**
     * getter de la llista de jugadors, per si la necessitem
     * @return retorna la llista de jugadors
     */
    public Jugador[] getJugadors() {
        return jugadors;
    }

    /**
     * setter de la llista de jugadors, tot i que no hauria de fer falta. Millor.
     * @param jugadors array de jugadors a canviar
     */
    public void setJugadors(Jugador[] jugadors) {
        this.jugadors = jugadors;
    }

    /**
     * getter de la data de la partida
     * @return retorna la data
     */
    public Date getData() {
        return data;
    }

    /**
     * setter de la data de la partida
     * @param data data de la partida a què canviar-la
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * getter del nombre de jugadors de la partida
     * @return nombre de jugadors de lapartida en format int
     */
    public int getNjugadors() {
        return njugadors;
    }

    /**
     * setter del nombre de jugadors.
     * @param njugadors nombre de jugadors de la partida a fer.
     */
    public void setNjugadors(int njugadors) {
        this.njugadors = njugadors;
    }

    /**
     * getter de si la partida és de tipus torneig
     * @return retorna true si la partida es un torneig, false si es casual
     */
    public boolean isTorneig() {
        return torneig;
    }

    /**
     * setter del tipus de partida (torneig o casual)
     * @param torneig boolea que indica si la partida es torneig (true) o casual (per defecte, false)
     */
    public void setTorneig(boolean torneig) {
        this.torneig = torneig;
    }

    /**
     * Getter de partidaTerminada
     * @return si la partida terminada
     */
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    /**
     * Setter de la partidaTerminada
     * @param partidaTerminada nou valor de partida terminada
     */
    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }

    /**
     * Getter del boolea ultimaRonda
     * @return si es la ultima ronda (algun usuari ha premut la tecla de sortir)
     */
    public boolean isUltimaRonda() {
        return ultimaRonda;
    }

    /**
     * Setter de la ultimaRonda
     * @param ultimaRonda nou valor de la ultima ronda
     */
    public void setUltimaRonda(boolean ultimaRonda) {
        this.ultimaRonda = ultimaRonda;
    }

    /**
     * Metode que gestiona totes les accions durant la partida, fent servir la informació proporcionada per la classe
     * LogicaPartida i transmetent informació a tots els jugadors.
     */
    @Override
    public void run() {
        try {
            // 1. Antes de empezar la partida definimos los servidores dedicados
            for (int i = 0; i < njugadors; i++) {
                jugadors[i] = this.defineixServidorsDedicats(jugadors[i]);   //Aixo diu a quin servidorDedicat treballa el jugador en qüestió
            }

            // 2. Enviem tots els usuaris:
            //this.actualitzaDireccioUsuaris();
            this.enviaUsuaris();

            // (2.) De nou, un cop hem definit els servers dedicats,
            this.inicialitzaMatriu();
            this.inicialitzaJugadors();
            this.reiniciaDireccioUsuaris();


            boolean alguExit = false;   // algu li ha donat al exit

            // 4. Per ara només agafem la opcio de si no es torneig
            if (!torneig) {
                sleep(3000);
                this.enviaAvisNovaRonda(alguExit);  // ESTO ES QUE NADIE HA HECHO EXIT AL PRINCIPIO
                // 4.1. Ara comença el bucle de la partida: mentre la partida no estigui terminada (si queda >1 jugador viu)
                while (!alguExit) {  // mentre ningu hagi abandonat
                    empat = false;
                    iGuanyador = 5;
                    // enviar si ronda nueva
                    this.ultimaRonda = this.fiRonda();

                    this.enviaAvisNovaRonda(ultimaRonda);
                    // enviar winner (reinicia ronda)
                    this.enviaWinner(ultimaRonda);
                    if (ultimaRonda) {
                        // actualitzar les posicions dels jugadors:
                        this.reiniciaPosicions();
                        this.reiniciaDireccioUsuaris();
                        this.inicialitzaMatriu();
                        ultimaRonda = false;
                    }


                    // comprovem si ha mort cap jugador
                    this.comprovaMortJugador();
                    // actualitzem els jugadors     y las direcciones
                    this.actualitzaJugadors();

                    // actualitzem la grid
                    this.actualitzaGrid();

                    // enviar grid
                    this.enviaGrid();
                    // actualitzem els jugadors
                    this.enviaUsuaris();
                    // esperem al tic
                    sleep(50);

                    // enviar si exit
                    alguExit = this.alguHaFetExit();
                    this.enviaAvisNovaRonda(alguExit);  // envia si algu ha pulsat sortir
                }

                // Aqui ya ha terminado la partida: eliminar a los jugadores de la cola.
                for (int i = 0; i < njugadors; i++) {
                    if (njugadors == 2){
                        for (Usuari u: this.servidorGeneral.getLlistaLobbies()[0].getCuaJugadors()) {
                            if (u.getNickname().equals(jugadors[i].getUsuari().getNickname())) {
                                jugadors[i].getUsuari().setOnGame(false);
                                jugadors[i].getUsuari().setExit(false);
                            }
                        }
                        this.servidorGeneral.getLlistaLobbies()[0].remPlayer(jugadors[i].getUsuari());
                    }
                    if (njugadors == 4 && !torneig) {
                        for (Usuari u: this.servidorGeneral.getLlistaLobbies()[1].getCuaJugadors()) {
                            if (u.getNickname().equals(jugadors[i].getUsuari().getNickname())) {
                                jugadors[i].getUsuari().setOnGame(false);
                                jugadors[i].getUsuari().setExit(false);
                            }
                        }
                        this.servidorGeneral.getLlistaLobbies()[1].remPlayer(jugadors[i].getUsuari());
                    }
                    if (njugadors == 4 && torneig) {
                        for (Usuari u: this.servidorGeneral.getLlistaLobbies()[2].getCuaJugadors()) {
                            if (u.getNickname().equals(jugadors[i].getUsuari().getNickname())) {
                                jugadors[i].getUsuari().setOnGame(false);
                                jugadors[i].getUsuari().setExit(false);
                            }
                        }
                        this.servidorGeneral.getLlistaLobbies()[2].remPlayer(jugadors[i].getUsuari());
                    }
                }

                try {
                    // sacamos el ganador:
                    iGuanyador = 0;
                    String guanyador = this.jugadors[0].getUsuari().getNickname();
                    int punts_guanyador = this.jugadors[0].getUsuari().getPunts_partida();
                    int numPartida = 0;
                    // calculem la id de la nova partida:
                    ResultSet rs = this.servidorGeneral.getDbinfo().controllerDBInfo.selectQuery("select count(id_partida) from partida;");
                    rs.next();
                    numPartida = rs.getInt(1) + 1;

                    // sacamos el numero de victorias
                    for (int i = 1; i < njugadors; i++) {
                        if (jugadors[i].getWins() > jugadors[iGuanyador].getServer().getWins()) iGuanyador = i;
                    }
                    punts_guanyador = jugadors[iGuanyador].getServer().getUsuari().getPunts_partida();

                    // ACTUALIZAMOS LA BASE DE DATOS:
                    // 1. Creamos la tabla partida
                    if (njugadors == 2) {
                        this.servidorGeneral.getDbinfo().controllerDBInfo.insertQuery("insert into partida(tipus, data, guanyador) values (2,'" + data + "','" + jugadors[iGuanyador].getUsuari().getNickname() + "');");
                    } else {
                        if (!torneig){
                            this.servidorGeneral.getDbinfo().controllerDBInfo.insertQuery("insert into partida(tipus, data, guanyador) values (4,'" + data + "','" + jugadors[iGuanyador].getUsuari().getNickname() + "');");
                        } else {
                            this.servidorGeneral.getDbinfo().controllerDBInfo.insertQuery("insert into partida(tipus, data, guanyador) values (0,'" + data + "','" + jugadors[iGuanyador].getUsuari().getNickname() + "');");
                        }
                    }
                    // 2. Creamos la tabla juga, por cada jugador
                    for (int i = 0; i < njugadors; i++) {   // por cada jugador de la partida, creamos la fila de tabla 'juga'
                        if (i == iGuanyador) {
                            this.servidorGeneral.getDbinfo().controllerDBInfo.insertQuery("insert into juga select u.nom, u.correu, p.id_partida, "+punts_guanyador+" from Usuari as u, Partida as p where u.nom like '"+jugadors[i].getUsuari().getNickname()+"' and u.correu like '"+jugadors[i].getUsuari().getCorreu()+"' and p.id_partida like "+numPartida+";");
                            // Actualizar los usuarios (sumar puntos)
                            if (njugadors == 2) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador+", punts_vs2 = punts_vs2+"+punts_guanyador+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                            else {
                                if (!torneig) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador+", punts_vs4 = punts_vs4+"+punts_guanyador+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                                if (torneig) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador+", punts_torneig = punts_torneig+"+punts_guanyador+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                            }
                        } else {    // si no es el guanyador
                            this.servidorGeneral.getDbinfo().controllerDBInfo.insertQuery("insert into juga select u.nom, u.correu, p.id_partida, "+punts_guanyador/2+" from Usuari as u, Partida as p where u.nom like '"+jugadors[i].getUsuari().getNickname()+"' and u.correu like '"+jugadors[i].getUsuari().getCorreu()+"' and p.id_partida like "+numPartida+";");
                            if (njugadors == 2) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador/2+", punts_vs2 = punts_vs2+"+punts_guanyador/2+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                            else {
                                if (!torneig) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador/2+", punts_vs4 = punts_vs4+"+punts_guanyador/2+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                                if (torneig) this.servidorGeneral.getDbinfo().controllerDBInfo.updateQuery("update usuari set punts_totals = punts_totals+"+punts_guanyador/2+", punts_torneig = punts_torneig+"+punts_guanyador/2+" where nom like '"+jugadors[i].getUsuari().getNickname()+"';");
                            }
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al actualitzar la base de dades!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Temps detingut", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que actualitza les direccions de cada usuari
     */
    private void reiniciaDireccioUsuaris() {
        jugadors[0].getUsuari().setDireccion(4);
        jugadors[1].getUsuari().setDireccion(3);
        if (njugadors == 4) {
            jugadors[2].getUsuari().setDireccion(2);
            jugadors[3].getUsuari().setDireccion(1);
        }
    }

    /**
     * Metode que crida al metode que reseteja cada jugador a la seva posicio inicial
     */
    private void reiniciaPosicions() {
        for (int i = 0;  i < njugadors; i++) {
            this.jugadors[i].resetPosicioIDireccio(i, ROWS, COLS, this.jugadors[i]);
            this.jugadors[i].getUsuari().setPlaying(true);
        }
    }

    /**
     * Metode que envia a tots els jugadors l'inici de la nova ronda (true)
     * @param valor boolea de si es una nova ronda
     * @throws IOException
     */
    private void enviaAvisNovaRonda(boolean valor) throws IOException {
        for (Jugador k: jugadors) {
            k.getServer().enviaBoolea(valor);
        }
    }

    /**
     * Metode que actualitza els jugadors
     */
    private void actualitzaJugadors() {
        for (Jugador j: jugadors) { // per cada jugador
            // si està jugant, incrementem en dos els punts de la partida
            if (j.getUsuari().isPlaying()) {
                j.getUsuari().setPunts_partida(j.getUsuari().getPunts_partida()+2);
                if (this.njugadors == 2) {  // si es de dos jugadors:
                    for (Usuari l : this.servidorGeneral.getLlistaLobbies()[0].getCuaJugadors()) {
                        if (l.getNickname().equals(j.getUsuari().getNickname())){
                            j.getUsuari().setDireccion(l.getDireccion());
                        }
                    }
                } else {
                    if (!this.torneig) {  // si es de dos jugadors:
                        for (Usuari l : this.servidorGeneral.getLlistaLobbies()[1].getCuaJugadors()) {
                            if (l.getNickname().equals(j.getUsuari().getNickname())){
                                j.getUsuari().setDireccion(l.getDireccion());
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Metode que llegeix i retorna si a la partida actual hi ha més de un jugador playing.
     * @return retorna si encara no ha terminat la ronda
     */
    private boolean fiRonda() {
        int playersVius = 0;
        for (int i = 0; i < njugadors; i++) {
            if (jugadors[i].getUsuari().isPlaying()) {
                playersVius++;
            }
        }
        if (playersVius == 0) empat = true; // han collisionat a la vegada
        return playersVius<2;
    }

    /**
     * Metode que reinicia la ronda (o la "inicia", segons el boolea paràmetre)
     * @param ultimaRonda boolea que indica si hem d'enviar els jugadors
     */
    private void enviaWinner(boolean ultimaRonda) throws IOException, InterruptedException {
        this.iGuanyador = 5;
        // 0. Primer busquem el guanyador de l'anterior ronda
        if (ultimaRonda) {  // si es el fi de ronda, actualitzem el winner, si no el deixem a 5 i el player no actualitzara les victories
            if (empat) iGuanyador = 4;
            else {
                for (int q = 0; q < njugadors; q++) {
                    if (jugadors[q].getUsuari().isPlaying() && !empat) iGuanyador = q;
                }
            }
        }
        // 1. Enviem l'index del guanyador a tots els usuaris
        for (Jugador j : jugadors) {
            j.getServer().enviaInt(iGuanyador);
        }
        //sleep(3000);
    }

    /**
     * Metode que llegeix si algu ha fet exit de la partida i, per tant, aquesta ha d'acabar
     * @return
     */
    private boolean alguHaFetExit() {
        boolean alguExit = false;
        for (Jugador j: jugadors) if (j.getUsuari().isExit()) alguExit = true;
        if (alguExit) {
            //this.ultimaRonda = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metode que actualitza la grid
     */
    private void actualitzaGrid() {
        for (int j = 0; j < njugadors; j++) {   // per cada jugador
            if (jugadors[j].getUsuari().isPlaying()) {  // només actualitza la grid si l'usuari està jugant
                this.grid[jugadors[j].getPosy()][jugadors[j].getPosx()] = (j+1); // primero pintamos la grid con el int del usuario que corresponde
                if (jugadors[j].getUsuari().getDireccion() == 1) {
                    jugadors[j].setPosy(jugadors[j].getPosy() - 1);         // luego avanzamos
                } else {
                    if (jugadors[j].getUsuari().getDireccion() == 2) {
                        jugadors[j].setPosy(jugadors[j].getPosy() + 1);     // luego avanzamos
                    } else {
                        if (jugadors[j].getUsuari().getDireccion() == 3) {
                            jugadors[j].setPosx(jugadors[j].getPosx() - 1); // luego avanzamos
                        } else {    // solo puede ser direccion 4, ahora
                            jugadors[j].setPosx(jugadors[j].getPosx() + 1); // luego avanzamos
                        }
                    }
                }
            }
        }
    }

    /**
     * Metode que comprova per cada jugador si ha mort (tot i que hauria d'estar a logica, aqui podem executar-lo en
     * general, sense haver de fer un altre bucle que iteri entre les 3 LogicaPartida)
     */
    private void comprovaMortJugador() throws IOException {
        int direccio_final = 0;
        String estat = "";
        for (Jugador j: jugadors) {
            //estat = "ok";
            if (j.getUsuari().isPlaying()) {
                direccio_final = j.getUsuari().getDireccion();
                if (direccio_final == 1) {   // si està mirant amunt
                    if (j.getPosy() == 0 ||   // i la posicio Y ja és 0
                            grid[j.getPosy() - 1][j.getPosx()] != 0 ) {    // o la proxima posicio Y està ocupada
                        //estat = "ko";    // el jugador morirà
                        //j.setViu(false);
                        j.getUsuari().setPlaying(false);
                    }
                } else {
                    if (direccio_final == 2) {    // està mirant avall
                        if (j.getPosy() == ROWS-1 ||    // i la posicio Y és a sota de tot
                                grid[j.getPosy() + 1][j.getPosx()] != 0) {    // o la proxima posicio Y està ocupada
                            //estat = "ko";   // el jugador morirà
                            //j.setViu(false);
                            j.getUsuari().setPlaying(false);
                        }
                    } else {
                        if (direccio_final == 3) {    // està mirant a esquerra
                            if (j.getPosx() == 0 ||   // i ja està al limit
                                    grid[j.getPosy()][j.getPosx() - 1] != 0) {    // o la proxima posX està ocupada
                                //estat = "ko";   // el jugador morirà
                                //j.setViu(false);
                                j.getUsuari().setPlaying(false);
                            }
                        } else {
                            //if (direccio_final == 4) {    // està mirant a la dreta segur
                            if (j.getPosx() == COLS-1 ||   // i ja està al limit
                                    grid[j.getPosy()][j.getPosx() + 1] != 0) {    // o la proxima posX està ocupada
                                //estat = "ko";   // el jugador morirà
                                //j.setViu(false);
                                j.getUsuari().setPlaying(false);
                            }
                            //}
                        }
                    }
                }
            }
        }
    }

    /**
     * Metode que envia la grid a tots els jugadors de la partida
     */
    private void enviaGrid() throws IOException {
        for (Jugador p: jugadors) {
            for (int i = 0; i < njugadors; i++) {
                p.getServer().enviaEstat(jugadors[i].getPosy()+"/"+jugadors[i].getPosx()+"/"+(i+1));
            }
        }
    }

    /**
     * Metode que busca el servidor dedicat al que pertany cada usuari, per optimitzar l'experiència
     * @param j jugador a descubrir el seu servidor dedicat
     * @return retorna el jugador amb el servidor dedicat actualitzat
     */
    private Jugador defineixServidorsDedicats(Jugador j) {
        for (ServerDedicat s: this.servidorGeneral.getLlistaSockets()) {
            if (j.getUsuari().getNickname().equals(s.getUsuari().getNickname())) j.setServer(s);
        }
        return j;
    }

    /**
     * Metode que envia l'array de jugadors a tots els jugadors de la partida
     */
    private void enviaUsuaris() throws IOException {
        for (Jugador u: jugadors) for (Jugador p:jugadors) {
            u.getServer().enviaUsuari(p.getUsuari());
        }
    }

    /**
     * Metode que inicialitza el grid de joc, posant-ho tot a 0
     */
    public void inicialitzaMatriu() {
        for (int i=0; i<ROWS; i++) for (int j=0; j<COLS; j++) grid[i][j] = 0;
    }

    /**
     * Metode que inicialitza l'estat dels jugadors (els posa tots en "viu")
     */
    public void inicialitzaJugadors() {
        for (int i = 0; i < njugadors; i++) {
            this.jugadors[i].getUsuari().setPlaying(true);
            this.jugadors[i].getUsuari().setOnGame(true);
        }
    }
}
