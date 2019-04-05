package Network;

import Model.*;
import javax.swing.*;
import java.net.Socket;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe que conté tota la informació sobre la connexio establerta concretament entre un usuari i el servidor general,
 * amb les atributs necessaris per a transmitir informació i buscar informacio; i s'encarrega d'estar constantment esperant
 * demandes del servidor (per aixo extèn de thread).
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ServerDedicat extends Thread {    // aixo més endavant heretarà de thread, i el podrem fer servir durant el joc per a les comandes cap al server
    private Socket socket;  /** socket del servidor dedicat */

    private ObjectInputStream   oinput; /** atribut per a rebre un objecte del servidor */
    private ObjectOutputStream  ooutput; /** atribut per a enviar un objecte al servidor */
    private DataInputStream     dinput; /** atribut per a rebre un valor del servidor */
    private DataOutputStream    doutput; /** atribut per a enviar un valor al servidor */

    private Usuari usuari;  /** uusari del servidor dedicat */
    private DBInfo dbinfo;  /** classe per buscar informacio de la base de dades i més */
    // NUEVO
    private Lobby[] lobbies; /** array de cada tipus de Lobby (mode vs2 = [0], mode vs4 = [1] i mode torneig = [2] */
    private ThreadServerActiu servidorGeneral;  /** servidor general */

    private int wins;       /** int del numero de victorias del jugador */

    // CONSTRUCTORS
    /**
     * Constructor del Servidor Dedicat
     * @param socket socket del servidor en qüestio
     * @param dbinfo classe amb la info per a fer totes les connexios i realitzar queries i canvis a la database
     * @param lobbies array de Lobby, per accedir a més informació
     * @param servidorGeneral acces al servidor general, ThreadServerActiu, per accedir als atributs de transmitir
     *                        informacio entre client i servidor.
     * @throws IOException
     */
    public ServerDedicat(Socket socket, DBInfo dbinfo, Lobby[] lobbies, ThreadServerActiu servidorGeneral) throws IOException {
        this.dbinfo = dbinfo;
        this.usuari = new Usuari();
        this.lobbies = lobbies;
        this.servidorGeneral = servidorGeneral;
        this.socket = socket;
        this.ooutput = new ObjectOutputStream(socket.getOutputStream());
        this.oinput = new ObjectInputStream(socket.getInputStream());
        this.doutput = new DataOutputStream(socket.getOutputStream());
        this.dinput = new DataInputStream(socket.getInputStream());
        this.wins = 0;
    }


    // GETTERS I SETTERS

    /**
     * Getter del nombre de victories del jugador connectat a aquest servidor dedicat
     * @return
     */
    public int getWins() {
        return wins;
    }

    /**
     * Getter del atribut ObjectOutputStream
     * @return retorna l'atrbut ObjectOutputStream
     */
    public ObjectOutputStream getOoutput() {
        return ooutput;
    }

    /**
     * Getter del socket d'aquest servidor dedicat en qüestió
     * @return socket d'aquest serverDedicat
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Metode que actualitza l'usuari d'aquest servidor dedicat
     * @param usuari usuari nou
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Getter de la classe Usuari que hi ha connectat a aquest servidor dedicat
     * @return usuari d'aquest servidor dedicat
     */
    public Usuari getUsuari() {
        return this.usuari;
    }

    /**
     * Getter del ThreadServerActiu, per saber si el servidar està obert
     * @return retorna threadserveractiu
     */
    public ThreadServerActiu getServidorGeneral() {
        return servidorGeneral;
    }

    // METHODS

    /**
     * Metode que sobreescriu el run del thread i s'encarrega de rebre totes les demandes del client mentre aquest no es
     * desconecti, i fa els procediments i les crides necessàries per efectuar-les.
     */
    @Override
    public void run() {

        try {
            boolean clientTancat = false;
            boolean sessioTancada = false;
            boolean sessioIniciada = false;
            String opcio = "";

            while (!clientTancat) {
                // 0. Espera a rebre quina opció ha escollit l'usuari: si INICIAR_SESSIO o bé TANCAR
                opcio = dinput.readUTF();
                this.doutput.writeBoolean(true);
                if (opcio.equals("INICIAR_SESSIO")) {
                    // 1. Inicia sessió (login):
                    sessioIniciada = this.iniciaSessioUsuari();
                    opcio = "";
                    String[] rep;
                    if (sessioIniciada) {
                        sessioTancada = false;
                        // 2. Espera a rebre l'ordre de l'usuari:
                        while (!sessioTancada) {
                            opcio = dinput.readUTF();
                            switch (opcio) {
                                case "CONTROLS":  // canviar controls
                                    this.modificaIEnviaControls();
                                    break;

                                case "V2":  // jugar vs2
                                    doutput.writeBoolean(true);
                                    lobbies[0].addPlayer(usuari);
                                    break;

                                case "V4":  // jugar vs4
                                    doutput.writeBoolean(true);
                                    lobbies[1].addPlayer(usuari);
                                    break;

                                case "VT":  // jugar torneig
                                    doutput.writeBoolean(true);
                                    lobbies[2].addPlayer(usuari);
                                    break;

                                case "RANKING":  // mostrar ranking-modalitat
                                    doutput.writeBoolean(true);
                                    doutput.flush();

                                    // Enviamos el array de info de los usuarios y puntos de modo vs2
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingUsuaris(2));
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingPunts(2));

                                    // Enviamos el array de info de los usuarios y puntos de modo vs4
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingUsuaris(4));
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingPunts(4));

                                    // Enviamos el array de info de los usuarios y puntos de modo torneo
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingUsuaris(0));
                                    ooutput.writeObject(dbinfo.controllerDBInfo.getRankingPunts(0));

                                    break;

                                case "TANCAR_SESSIO":  // tancar sessió
                                    doutput.writeBoolean(true);
                                    sessioTancada = true;
                                    clientTancat = false;
                                    break;

                                case "OPCIOSORTIR": // sortida de l'usuari
                                    sessioTancada = true;
                                    clientTancat = true;
                                    break;

                                case "EXIT_LOBBY":
                                    this.treuJugadorCuaEspera();
                                    this.doutput.writeBoolean(false);
                                    break;

                                case "EXIT_PARTIDA":

                                    break;

                                default:
                                    // Con un if, gestionamos si ha recibido el formato de cambio de dirección
                                    rep = opcio.split("/");

                                    if (!rep[0].equals("WINS")) {
                                        if (!rep[1].equals("5")) {
                                            for (int lobby = 0; lobby < 3; lobby++) {
                                                for (int i = 0; i < lobbies[lobby].getCuaJugadors().size(); i++) {
                                                    if (lobbies[lobby].getCuaJugadors().get(i).getNickname().equals(rep[0])) {
                                                        if (rep[1].charAt(0) != '0') {
                                                            lobbies[lobby].getCuaJugadors().get(i).setDireccion(Integer.parseInt(rep[1]));
                                                        }
                                                        // esto lo actualiza bien!!!! ambas!!!
                                                    }
                                                }
                                            }
                                        } else {
                                            for (int i = 0; i < 3; i++) {   // busca el lobby corresponent
                                                for (int j = 0; j<this.servidorGeneral.getLlistaLobbies()[i].getCuaJugadors().size(); j++) {
                                                    if (this.servidorGeneral.getLlistaLobbies()[i].getCuaJugadors().get(j).getNickname().equals(rep[0])){
                                                        this.servidorGeneral.getLlistaLobbies()[i].getCuaJugadors().get(j).setExit(true);


                                                    }
                                                }
                                            }
                                        }
                                    } else {    // actualitza les victories de cada jugador
                                        for (int iJugador = 0; iJugador < Integer.parseInt(rep[1]); iJugador++) {
                                            this.actualitzaVictories(rep[2],Integer.parseInt(rep[3]),Integer.parseInt(rep[3]));
                                        }
                                    }
                                    //JOptionPane.showMessageDialog(null,"Opcio no definida!","ERROR",JOptionPane.ERROR_MESSAGE);
                                    break;
                            }
                        }
                    }
                } else {
                    clientTancat = true;
                }
            }

            this.servidorGeneral.tancaServidorDedicat(this);


        } catch(IOException e) {
            System.out.println("Error amb els data/object/input/outputStream's");
        } catch(SQLException e) {
            System.out.println("Error al actualitzar la base de dades!");
        } catch(ClassNotFoundException e) {}
    }

    /**
     * Metode que actualitza els punts del jugador indicat
     * @param nomPlayer nom del jugador a actualitzar
     * @param nPunts nombre de punts a afegir a la db
     */
    private void actualitzaVictories(String nomPlayer, int nPunts, int nVictories) {
        for (int iLobby = 0; iLobby < 3; iLobby++) {
            for (int p = 0; p < lobbies[iLobby].getCuaJugadors().size(); p++) {
                if (lobbies[iLobby].getCuaJugadors().get(p).getNickname().equals(nomPlayer)) {
                    // si el nombre coincide con alguno de los jugadores de la cola, actualiza sus puntos
                    lobbies[iLobby].getCuaJugadors().get(p).setPunts_partida(nPunts);
                    lobbies[iLobby].getCuaJugadors().get(p).setWins(nVictories);
                }
            }
        }
    }

    /**
     * Metode que treu un uusari de la llista d'espera on es trobava
     */
    private void treuJugadorCuaEspera() {
        boolean found = false;
        for (int lobby = 0; !found && lobby < 3; lobby++) {
            found = this.lobbies[lobby].remPlayer(this.usuari);
        }
    }

    /**
     * Metode que implementa tots els passos per intercambiar la informacio amb el servidor sobre els controls de l'usuari
     */
    private void modificaIEnviaControls() {
        try {
            doutput.writeBoolean(true);
            // 0. Crea la classe on guardarem la informació temporal
            String[] smsClient = new String[]{"amunt", "avall", "esquerra", "dreta"}; // aquest es el format
            // 1. Envia al client els controls actuals en format String[]:
            smsClient = this.enviaControlsUsuari(smsClient);  // NO FA FALTA, PERO BUENO, el client ja ho te al carregar el programa

            boolean vistaTancada = false;
            while (!vistaTancada) {
                // Llegeix el missatge del client
                smsClient[0] = dinput.readUTF();
                // String enviado por el cliente con los codigos KeyEvent separados por '/'
                if (smsClient[0].equals("TANCA")) vistaTancada = true;
                if (smsClient[0].equals("ENVIANT")) {
                    // Rebem l'array de Strings amb els controls seleccionats
                    smsClient = dinput.readUTF().split("/");
                    // Actualitza la base de dades + Actualitzem el nostre usuari
                    String resposta = dbinfo.controllerDBInfo.actualitzaControls(smsClient, this.usuari);
                    // Actualitzem el notre usuari del servidor
                    this.usuari.setControls(smsClient);
                    // Envia la resposta de la base de dades sobre el canvi
                    this.doutput.writeUTF(resposta);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar els controls!","ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la database!","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que envia al client els controls de l'usuari de la database en format String[] (tot i que son INT's)
     */
    private String[] enviaControlsUsuari(String[] controls) throws SQLException, IOException {
        ResultSet rs = this.dbinfo.controllerDBInfo.selectQuery("select mov_up, mov_down, mov_left, mov_right from usuari where nom like '"+usuari.getNickname()+"';");
        rs.next();
        controls[0] = rs.getString("mov_up");
        controls[1] = rs.getString("mov_down");
        controls[2] = rs.getString("mov_left");
        controls[3] = rs.getString("mov_right");
        ooutput.writeObject(controls);
        return controls;
    }

    /**
     * Metode que realitza tots els passos de inici de sessió d'usuari, així com l'actualització d'algunes dades de la db, com
     * seria el cas de la data d'ultim acces de l'usuari.
     * @throws IOException
     * @throws Exception
     * @throws SQLException
     */
    private boolean iniciaSessioUsuari() throws IOException, ClassNotFoundException{
        boolean usuari_logejat = false; // indica si l'usuari ha conseguit logejar-se al lobby

        // 1.1. Tot començar, espera a rebre l'usuari per executar l'inici de sessió:
        this.sessionRequest();
        // 1.2. Buscamos al usuario en cuestión en la base de datos:
        boolean found = dbinfo.getControllerDBInfo().buscarUsuari(usuari);    // por ahora comentamos esto, cuando el server reciba el usuario lo descomentamos.
        // 1.3. Enviamos el resultado del inicio de sesion al usuario
        usuari_logejat = this.enviaIniciSessio(found);
        // 1.4. Actualizamos la fecha de ultimo acceso del usuario en la base de datos.
        if (usuari_logejat) dbinfo.getControllerDBInfo().updateQuery("update usuari set data_ultim_acces = '"+new java.sql.Timestamp(System.currentTimeMillis())+"' where nom like '"+usuari.getNickname()+"';");

        return usuari_logejat;
    }

    /**
     * Metode que espera a rebre la classe usuari del client, i la guarda al nostre ServidorDedicat
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void sessionRequest() throws IOException, ClassNotFoundException {
        usuari = (Usuari)oinput.readObject();
    }

    /**
     * Respon al client el resultat de l'inici de sessió desde la base de dades mitjançant el paràmetre found.
     * @param found si l'usuari s'ha trobat o no a la base de dades
     * @return retorna si l'inici de sessio ha estat satisfactori
     * @throws IOException
     */
    private boolean enviaIniciSessio(boolean found) throws IOException{
        // responem al client si s'ha pogut iniciar sessió:
        if (found) {    // hem trobat aquest usuari en la base de dades
            if (usuari.isNou_usuari()) {
                doutput.writeUTF("REGISTRE_KO"); // no pot registrarse, ja hi ha un usuari amb les dades introduides
                doutput.writeUTF("¡Ja existeix un usuari amb aquestes dades de registre!");
                return false;
            } else {    // hem trobat l'usuari a la base de dades, pot fer login
                doutput.writeUTF("LOGIN_OK");
                usuari.actualitzaUltimAcces();
                ooutput.writeObject(usuari);
                return true;
            }
        } else {        // no hem trobat cap usuari que coincideixi
            if (usuari.isNou_usuari()) {
                usuari.actualitzaDataRegistre();
                doutput.writeUTF("REGISTRE_OK"); // al ser un registre, no hi ha problema
                dbinfo.getControllerDBInfo().creaUsuari(usuari);
                ooutput.writeObject(usuari);
                return true;
            } else {
                doutput.writeUTF("LOGIN_KO");  // no hem trobat cap usuari que coincideixi amb les dades del login
                doutput.writeUTF("¡Usuari no trobat! Torna a introduïr les dades o registra't.");
                return false;
            }
        }
    }

    /**
     * Metode que envia un boolea
     * @param valor valor boolea a enviar
     * @throws IOException
     */
    public void enviaBoolea(boolean valor) throws IOException {
        this.doutput.writeBoolean(valor);
    }

    /**
     * Metode que envia un jugador pel servidor seleccionat cap al client
     * @param u jugador a enviar
     */
    public void enviaUsuari(Usuari u) throws IOException {
        this.ooutput.reset();
        this.ooutput.writeObject(u);
    }

    /**
     * Envia un String a l'usuari ('ok' si tot està correcte, 'ko' si està eliminat)
     * @param estat estat a enviar al client
     */
    public void enviaEstat(String estat) throws IOException {
        this.doutput.writeUTF(estat);
    }

    /**
     * Metode que rep un boolea del client
     */
    public void repBoolea() throws IOException {
        this.dinput.readBoolean();
    }

    /**
     * Metode que envia un int
     * @param iWinner int a enviar
     */
    public void enviaInt(int iWinner) throws IOException {
        this.doutput.writeInt(iWinner);
    }
}
