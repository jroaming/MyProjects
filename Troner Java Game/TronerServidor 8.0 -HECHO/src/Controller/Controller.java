package Controller;

import Model.*;
import Network.*;
import View.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import static java.lang.Thread.sleep;

/**
 * Classe que implementa i gestiona totes les funcions de control que ofereix el Servidor, i estableix les comunicacions
 * entre les demés classes, fent de pont per transmitir info i cridant les funcions de realització d'operacions.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Controller implements ActionListener {
    private ModelMenu modelMenuPrincipal;   /**model del menu principal (constants)*/
    private ModelRegistre modelRegistre;    /**model del registre (constants)*/
    private ViewMenu vistaMenuPrincipal;    /**vista del menu principal*/
    private ViewRegistre vistaRegistre;     /**vista de la finestra de registre de nou usuari*/
    private ViewUsersManager vistaTaulaUsuaris; /**vista de gestio d'usuari*/
    private ViewRanking vistaRanking;       /**vista da ranking*/
    private ViewOpcioGrafica vistaOpcioGrafica; /**vista de establir les opcions de la grafica*/
    private ViewGrafica vistaGrafica;       /**vista de la grafica*/

    private ControllerTextAreaRegistre controllerTextAreaRegistre;  /**controlador de la finestra de registre*/
    private ControllerUsersManager controllerTaulaUsuaris;  /**controlador de la finestra de gestio d'usuaris*/
    private ControllerRanking controllerRanking;    /**controlador del ranking*/
    private ControllerGrafica controllerGrafica;    /**controlador de la grafica*/

    private ThreadServerActiu threadServerActiu;    /**thread del servidor que espera constantment noves connexions*/

    // NUEVO:
    private Lobby[] llistaLobbies;      /**array de lobbies*/
    private DBInfo dbinfo;              /**informacio per establir les connexions del servidor i la base de dades*/

    // CONSTRUCTOR
    /**
     * Constructor de la classe gestora del programari del Servidor Troner
     * @param modelMenu constants del model menu
     * @param mRegistre constants del model registre
     */
    public Controller(ModelMenu modelMenu, ModelRegistre mRegistre) {
        this.modelMenuPrincipal = modelMenu;
        this.modelRegistre = mRegistre;
        this.vistaMenuPrincipal = new ViewMenu(this);
        this.vistaRegistre = new ViewRegistre(this);

        this.controllerTextAreaRegistre = new ControllerTextAreaRegistre(this,this.vistaRegistre);
        this.vistaRegistre.registraControladorCampsRegistre(controllerTextAreaRegistre);
        this.vistaRegistre.registraControladorBotonsRegistre();

        this.dbinfo = new DBInfo();

        this.llistaLobbies = new Lobby[3];

        this.vistaTaulaUsuaris = new ViewUsersManager();
        this.controllerTaulaUsuaris = new ControllerUsersManager(vistaTaulaUsuaris, dbinfo);
        this.vistaTaulaUsuaris.registraControlador(controllerTaulaUsuaris);
        this.vistaTaulaUsuaris.setVisible(false);
        this.vistaRanking = new ViewRanking();
        this.controllerRanking = new ControllerRanking(vistaRanking,dbinfo);
        this.vistaRanking.registraControlador(controllerRanking);
        this.vistaRanking.setVisible(false);
        vistaOpcioGrafica = new ViewOpcioGrafica();
        vistaGrafica = new ViewGrafica();
        controllerGrafica = new ControllerGrafica(vistaOpcioGrafica, dbinfo, vistaGrafica);
        vistaOpcioGrafica.registraController(controllerGrafica);
        vistaOpcioGrafica.setVisible(false);

        // Perque tant aviat comenci sempre ha de carregar la base de dades.
        this.carregaDB();

        threadServerActiu = new ThreadServerActiu(dbinfo, llistaLobbies);
        threadServerActiu.setServidorObert(false);  // nuevo
        vistaMenuPrincipal.setJlMode(false);
    }

    // GETTERS I SETTERS
    /**
     * Getter del model del menu principal (constants)
     * @return constants del model del menu
     */
    public ModelMenu getModelMenuPrincipal() {
        return modelMenuPrincipal;
    }

    /**
     * Getter de la vista del menu principal
     * @return vista del menu principal
     */
    public ViewMenu getVistaMenuPrincipal() {
        return vistaMenuPrincipal;
    }

    /**
     * Getter del model registre
     * @return model registre
     */
    public ModelRegistre getModelRegistre(){
        return modelRegistre;
    }

    // METHODS

    /**
     * Metode que gestiona les demandes de l'usuari del Servidor
     * @param event controlador dels events
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        try {
            switch (command) {
                case ModelMenu.BTN_OPCIO1:  // registrar nou usuari
                    vistaRegistre.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIO2:  // gestionar usuaris
                    controllerTaulaUsuaris.actualitzaTaula();   //actualitzem la taula
                    vistaTaulaUsuaris.setVisible(true);         //obrim la finestra
                    break;

                case ModelMenu.BTN_OPCIO3:  // configurar el sistema
                    this.configurarSistema();
                    break;

                case ModelMenu.BTN_OPCIO4:  // visualitzar ranking
                    controllerRanking.actualitzaRanking();
                    vistaRanking.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIO5:  // visualitzar grafic
                    controllerGrafica.omplirComboboxUsuaris();
                    controllerGrafica.omplirComboboxModalitat();
                    vistaOpcioGrafica.setVisible(true);
                    break;

                case ModelMenu.BTN_OPCIOSORTIR: // tancar tots els processos i sortir del joc
                    // Terminem tota execució en segon pla del programa
                    terminarExecucio();
                    break;

                case ModelRegistre.LGN_REGISTRE:
                    this.ferRegistreServidor();
                    vistaRegistre.buidaCamps();
                    vistaRegistre.setVisible(false);
                    break;

                case ModelRegistre.LGN_CANCEL:
                    vistaRegistre.setVisible(false);
                    break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaMenuPrincipal,"Error al enviar una ordre a la base de dades!","ERROR", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuPrincipal, "Alguna opció ha fallat: ¡execució cancel·lada! :(", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que configura el sistema del servidor (mostra la finestra i espera que l'usuari esculli una opció)
     * @throws InterruptedException
     */
    private void configurarSistema() throws InterruptedException {
        Object[] options = { "Iniciar Servidor", "Modificar el port",
                "Aturar Servidor" };

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel jlAux = new JLabel("Port de connexió al servidor (actual): ");
        jlAux.setForeground(Color.CYAN);
        panel.add(jlAux);
        JTextField port = new JTextField(10);
        port.setText(dbinfo.getPort_client()+"");
        panel.add(port);

        int result = JOptionPane.showOptionDialog(null, panel, "Configuració del Sistema",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null);
        switch (result) {
            case 0: // obrir servidor
                if (!threadServerActiu.isServidorObert()) {
                    this.carregaLobby();
                    this.carregaServidor();
                    JOptionPane.showMessageDialog(null, "Servidor obert pel port "+dbinfo.getPort_client()+"!");
                } else {
                    JOptionPane.showMessageDialog(null, "El servidor ja està obert!");
                }
                break;

            case 1: // modificar port
                if (threadServerActiu.isServidorObert()) {
                    JOptionPane.showMessageDialog(null, "El port no es pot actualitzar mentre està obert!", "WARNING", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (this.portIncorrecte(port.getText())) {
                        JOptionPane.showMessageDialog(null, "Port no vàlid! Introdueix un numero entre 1024 i 65535", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        dbinfo.setPort_client(Integer.parseInt(port.getText()));
                        dbinfo.controllerDBInfo.canviaPortClient();
                        JOptionPane.showMessageDialog(null, "El port s'ha actualitzat correctament al " + dbinfo.getPort_client() + "!");
                    }
                }
                break;

            case 2: // tancar servidor
                if (threadServerActiu.isServidorObert()) {
                    this.tancaLobby();
                    this.tancaServidor();
                } else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap servidor obert!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }

    /**
     * Mètode que diu si el port introduit a la opcio de configuracio no es vàlid
     * @param port port a comprovar
     * @return boolea que indica si el port es correcte
     */
    private boolean portIncorrecte(String port) {
        try {
            if (Integer.parseInt(port) > 1023 && Integer.parseInt(port) <= 65535) {
                return false;
            }
        } catch (NumberFormatException e) {}
        return true;
    }

    /**
     * Metode que realitza tots els passos per crear un nou usuari sense iniciar sessio amb ningú.
     */
    private void ferRegistreServidor() {
        Usuari usuari = new Usuari();
        boolean found = false;
        // Llegim la info de la finestra i creem l'usuari.
        usuari.actualitzaUsuari(vistaRegistre.ompleUsuari(usuari));
        usuari.setNou_usuari(true);
        // El busquem a la base de dades.
        found = dbinfo.getControllerDBInfo().buscarUsuari(usuari);
        // L'inserim si no hi ha hagut cap error
        if (!found) {
            dbinfo.getControllerDBInfo().creaUsuari(usuari);
            JOptionPane.showMessageDialog(vistaRegistre, "Usuari registrat amb éxit!");
        } else {
            JOptionPane.showMessageDialog(vistaRegistre, "No s'ha pogut registrar aquest usuari!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Carrega la base de dades
     */
    private void carregaDB() {
        dbinfo = dbinfo.getControllerDBInfo().loadModelDBInfo(); // enviem les dades, llegides del json
        dbinfo.getControllerDBInfo().connect();  // ens connectem a la base de dades
    }

    /**
     * Mètode que realitza tots elspassos per iniciar el servidor
     */
    private void carregaServidor() throws InterruptedException {
        sleep(250);
        threadServerActiu.setLlistaLobbies(this.llistaLobbies);
        threadServerActiu.start();
        this.vistaMenuPrincipal.setJlMode(true);

        // Esperem a que el threadServerActiu encengui el servidor, i iniciem els lobbies:
        while (!this.threadServerActiu.isServidorObert());
        /* no es un chivato, pero hay que borrarlo!
        this.llistaLobbies[0].start();
        this.llistaLobbies[1].start();
        this.llistaLobbies[2].start();
        */
    }

    /**
     * Metode que carrega i prepara els lobbies (sales d'espera)
     * @throws InterruptedException
     */
    private void carregaLobby() throws InterruptedException {
        // Creem els lobbies del servidor per a cada modalitat de joc:
        this.llistaLobbies[0] = new Lobby(2);   // lobby per a cua de partides de 2 jugadors (casual)
        this.llistaLobbies[1] = new Lobby(4);   // lobby per a cua de partides de 4 jugadors (casual)
        this.llistaLobbies[2] = new Lobby(0);   // lobby per a cua de partides de 4 jugadors en mode torneig

        // Com que sempre hi haurà una classe ThreadServerActiu creada, passem l'actual:
        this.llistaLobbies[0].setServerGeneral(this.threadServerActiu);
        this.llistaLobbies[1].setServerGeneral(this.threadServerActiu);
        this.llistaLobbies[2].setServerGeneral(this.threadServerActiu);
    }

    /**
     * Metode que tanca tots els sockets oberts i elimina els servidors de la llista de servers dedicats del threadServerActiu.
     */
    private void tancaServidor() {
        try {
            if (threadServerActiu.isServidorObert()) {
                int max = threadServerActiu.getLlistaSockets().size();
                for (int i = 0; i < max; i++) {
                    threadServerActiu.getLlistaSockets().get(i).getSocket().close();
                    threadServerActiu.getLlistaSockets().remove(i);
                }
                threadServerActiu.getsServer().close();

                // Hem de reinicar el servidor:
                threadServerActiu = null;
                threadServerActiu = new ThreadServerActiu(dbinfo, llistaLobbies);
                threadServerActiu.setServidorObert(false);
                this.vistaMenuPrincipal.setJlMode(false);

            } else {
                JOptionPane.showMessageDialog(null, "No hi ha cap servidor obert!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Sockets tancats!");
        }
    }

    /**
     * Metode que tanca els tres lobbies (sales d'espera)
     */
    private void tancaLobby() {
        int nplayers = this.llistaLobbies[0].getCuaJugadors().size();
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);    //ja no estàn jugant
            this.llistaLobbies[0].remPlayer(this.llistaLobbies[0].getCuaJugadors().get(i));
        }
        this.llistaLobbies[0] = null;

        nplayers = this.llistaLobbies[1].getCuaJugadors().size();
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);
            this.llistaLobbies[1].remPlayer(this.llistaLobbies[1].getCuaJugadors().get(i));
        }
        this.llistaLobbies[1] = null;

        nplayers = this.llistaLobbies[2].getCuaJugadors().size();
        for (int i = 0; i < nplayers; i++) {
            this.llistaLobbies[0].getCuaJugadors().get(i).setPlaying(false);
            this.llistaLobbies[2].remPlayer(this.llistaLobbies[2].getCuaJugadors().get(i));
        }
        this.llistaLobbies[2] = null;
    }

    /**
     * Termina tots els processos que es poden detenir sobre l'execució del programa
     * @throws IOException
     */
    public void terminarExecucio() throws IOException {    // aixo tancarà tots els processos
        if (threadServerActiu.isServidorObert()) {
            // Comprovem si hi ha cap socket d'un client obert (alguna connexio activa al moment de tancar):
            if (threadServerActiu.getLlistaSockets().size() > 0) {  // Si hi ha connexions actives, les tanquem totes:
                for (int i = 0; i < threadServerActiu.getLlistaSockets().size(); i++) {
                    threadServerActiu.getLlistaSockets().get(i).getSocket().close();
                    threadServerActiu.getLlistaSockets().remove(i);
                }
            }
            // Ara tanquem el socket del servidor:
            threadServerActiu.getsServer().close();     // esto hace que lo otro pete, porque está esperando un 'accept' en el momento en que lo cerramos
            threadServerActiu.setServidorObert(false);
        }

        // tanquem la connexio amb la base de dades
        dbinfo.getControllerDBInfo().disconnect();
        System.exit(0);
    }
}
