package Controller;

import Model.*;
import View.*;
import Network.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

import static Model.ModelMenu.*;
import static java.lang.Math.abs;

/**
 * Classe que controla els camps d'escritura de la vista ViewModificaControls
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class Controller extends KeyAdapter implements ActionListener {
    private ModelMenu modelMenuPrincipal; /** model de modelMenuPrincipal*/
    private ModelConfiguracio modelFinestraConfiguracio; /** model de ModelConfiguracio*/
    private ModelLogin modelFinestraLogin; /** model de ModelLogin*/

    private ViewMenu vistaMenuPrincipal; /** vista de la ViewMenu*/
    private ViewLogin vistaLogin; /** vista de la ViewLogin*/
    private ViewConfiguracio vistaConfiguracio; /** vista de la ViewConfiguracio*/
    private ViewRankingModalitat vistaRanking; /** vista de la ViewaRankingModalitat*/

    private ViewModificarControls vistaModificarControls; /** vista de la ViewModificaControls*/
    private ControllerModificarControls controllerModificarControls; /** controlador del ControllerModificaControls*/

    private ControllerTextAreaLogin controllerLogin; /** controlador del ControllerTextAreaLogin*/
    private ControllerRankingModalitat controllerRankingModalitat; /** controlador del ControllerRankingModalitat*/

    private ViewWaitingLobby vistaWaitingLobby; /** vista de la ViewWaitingLobby*/

    private ConnexioUsuari connexioUsuari; /** connexio de la ConnexioUsuari*/
    private Usuari usuari; /** usuari de Usuari*/

    private boolean sessioIniciada; /** boolea que indica que la sessio esta iniciada*/

    private Timer timer; /** Timer que indica el time*/

    // CONSTRUCTOR

    /**
     * constructor del controller
     * @param modelLogin es un ModelLogin
     * @param modelMenu es un ModelMenu
     * @param modelConfig es un ModelConfiguracio
     */
    public Controller(ModelLogin modelLogin, ModelMenu modelMenu, ModelConfiguracio modelConfig) {
        this.modelFinestraConfiguracio = modelConfig;
        this.modelFinestraLogin = modelLogin;
        this.modelMenuPrincipal = modelMenu;

        this.vistaMenuPrincipal = new ViewMenu(this);
        this.vistaConfiguracio = new ViewConfiguracio(this);
        this.vistaConfiguracio.registrarBotonsConfiguracio();
        this.vistaMenuPrincipal.registraControladorBotonsMenu();

        this.vistaRanking = new ViewRankingModalitat();
        this.controllerRankingModalitat = new ControllerRankingModalitat(vistaRanking);
        vistaRanking.registraControlador(controllerRankingModalitat);
        vistaRanking.setVisible(false);

        this.vistaModificarControls = new ViewModificarControls();
        this.controllerModificarControls = new ControllerModificarControls(vistaModificarControls);
        vistaModificarControls.registraControladorModificarControls(this, controllerModificarControls);

        // nuevo
        this.vistaWaitingLobby = new ViewWaitingLobby();
        vistaWaitingLobby.registraControlador(this);

        this.sessioIniciada = false;
        this.usuari = new Usuari();
        usuari.setPlaying(false);
        usuari.setOnGame(false);

        this.connexioUsuari = new ConnexioUsuari();

    }

    // GETTERS I SETTERS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Getter del ModelFinestraLogin
     * @return ModelLogin
     */
    public ModelLogin getModelFinestraLogin() {
        return modelFinestraLogin;
    }

    /**
     * Setter del ModelFinestraLogin
     * @param  modelFinestraLogin
     */
    public void setModelFinestraLogin(ModelLogin modelFinestraLogin) {
        this.modelFinestraLogin = modelFinestraLogin;
    }

    /**
     * Getter del ModelFinestraPrincipal
     * @return ModelMenu
     */
    public ModelMenu getModelMenuPrincipal() {
        return modelMenuPrincipal;
    }

    /**
     * Setter del ModelMenuPrincipal
     * @param modelMenuPrincipal
     */
    public void setModelMenuPrincipal(ModelMenu modelMenuPrincipal) {
        this.modelMenuPrincipal = modelMenuPrincipal;
    }

    /**
     * Getter de la vistaMenuPrincipal
     * @return ViewMenu
     */
    public ViewMenu getVistaMenuPrincipal() {
        return vistaMenuPrincipal;
    }

    /**
     * Getter del ModelFinestraConfiguracio
     * @return ModelConfiguracio
     */
    public ModelConfiguracio getModelFinestraConfiguracio() {
        return modelFinestraConfiguracio;
    }

    /**
     * Setter de la vista VistaMenuPrincipal
     * @param vistaMenuPrincipal
     */
    public void setVistaMenuPrincipal(ViewMenu vistaMenuPrincipal) {
        this.vistaMenuPrincipal = vistaMenuPrincipal;
    }

    /**
     * Getter de la SessioIniciada
     * @return boolean
     */
    public boolean getSessioIniciada() {
        return sessioIniciada;
    }

    /**
     * Setter de la sessioIniciada
     * @param sessioIniciada
     */
    public void setSessioIniciada(boolean sessioIniciada) {
        this.sessioIniciada = sessioIniciada;
    }

    /**
     * Getter de la VistaLogin
     * @return ViewLogin
     */
    public ViewLogin getVistaLogin() {
        return vistaLogin;
    }

    /**
     * Setter del VistaLogin
     * @param vistaLogin
     */
    public void setVistaLogin(ViewLogin vistaLogin) {
        this.vistaLogin = vistaLogin;
    }

    /**
     * Getter del Configuracio
     * @return ViewConfiguracio
     */
    public ViewConfiguracio getVistaConfiguracio() {
        return vistaConfiguracio;
    }

    /**
     * Setter del VistaConfiguracio
     * @param vistaConfiguracio
     */
    public void setVistaConfiguracio(ViewConfiguracio vistaConfiguracio) {
        this.vistaConfiguracio = vistaConfiguracio;
    }

    /**
     * Getter del ConnexioUsuari
     * @return ConnexioUsuari
     */
    public ConnexioUsuari getConnexioUsuari() {
        return connexioUsuari;
    }

    /**
     * Setter de la connexioUsuari
     * @param connexioUsuari
     */
    public void setConnexioUsuari(ConnexioUsuari connexioUsuari) {
        this.connexioUsuari = connexioUsuari;
    }

    public boolean isSessioIniciada() {
        return sessioIniciada;
    }

    // METHODS -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    /**
     * Accio que espera a rebre la opció seleccionada per l'usuari i fa les crides pertinents
     * @param event pulsació en qüestió
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        try {
            switch (command) {
                case ModelConfiguracio.CONFIG_CONNECTAR:
                    this.connectarUsuariServidor();
                    if (connexioUsuari.isConnexioEstablerta()) this.prepararFinestraLogin();
                    //vistaLogin.setVisible(true);
                    break;

                case ModelConfiguracio.CONFIG_SORTIR:
                    System.out.println("Has premut el botó de sortir!");
                    this.sessioIniciada = false;
                    terminaExecucioPrograma();
                    break;

                case BTN_OPCIO1:    // mostrar el ranking per modalitat
                    connexioUsuari.enviaOpcio("RANKING");
                    if (connexioUsuari.repOpcio()) mostraRankingModalitats();
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIO2:  // canviar els controls
                    System.out.println("L'usuari ha dit de canviar els controls.");
                    // 0. Enviem la nostra ordre
                    connexioUsuari.enviaOpcio("CONTROLS");
                    if (connexioUsuari.repOpcio()){
                        this.canviarControls();
                        this.vistaMenuPrincipal.setJButtonsEnable(false);
                    }
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIO3:  // jugar vs2
                    System.out.println("L'usuari ha dit de jugar vs2");
                    connexioUsuari.enviaOpcio("V2");
                    if (connexioUsuari.repOpcio()){
                        this.enviaCuaEspera(2);
                        System.out.println("Opcio enviada!");
                    }
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIO4:  // jugar vs4
                    System.out.println("L'usuari ha dit de jugar vs4");
                    connexioUsuari.enviaOpcio("V4");
                    if (connexioUsuari.repOpcio()){
                        this.enviaCuaEspera(4);
                        System.out.println("Opcio enviada!");
                    }
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIO5:  // jugar torneig
                    System.out.println("L'usuari ha dit de jugar torneig");
                    connexioUsuari.enviaOpcio("VT");
                    if (connexioUsuari.repOpcio()){
                        this.enviaCuaEspera(0);
                        System.out.println("Opcio enviada!");
                    }
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIO6:  //tancar sessió
                    //System.out.println("Cridant a la " + ModelMenu.BTN_OPCIO6 + " (bySWITCH)!\n");
                    connexioUsuari.enviaOpcio("TANCAR_SESSIO");
                    if (connexioUsuari.repOpcio()) this.tancaSessio();
                    else JOptionPane.showMessageDialog(vistaMenuPrincipal, "Error al exeutar la opció!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;

                case ModelMenu.BTN_OPCIOSORTIR: // tancar tots els processos i sortir del joc
                    //System.out.println("Cridant a la " + ModelMenu.BTN_OPCIOSORTIR + " (bySWITCH)!\n");
                    connexioUsuari.enviaOpcio(BTN_OPCIOSORTIR);
                    this.terminaExecucioPrograma();
                    break;

                case ModelLogin.LGN_LOGIN:
                    usuari.setNou_usuari(false);    // perque si fem login és que ja existim a la db d'usuaris del joc
                    this.iniciaSessio();
                    break;

                case ModelLogin.LGN_REGISTRE:
                    usuari.setNou_usuari(true);     // si hem fet registre és perquè som nous al joc
                    this.iniciaSessio();
                    break;

                case ModelLogin.LGN_CANCEL:
                    this.connexioUsuari.enviaOpcio("TANCAR");   // avisem al servidor que tancarem el programa sense iniciar sessió
                    this.terminaExecucioPrograma();
                    break;

                //  AQUESTS SON EXTRA, DE LA FINESTRA DE ViewModificarControls
                // els hem hagut de posar aqui perque l'altre controlador no te connexio per enviar dades al servidor
                case "CANVIAR_CONTROLS":
                    this.modificaControlsFinestra();
                    break;

                case "TANCAR_CONTROLS":
                    this.connexioUsuari.enviaOpcio("TANCA");
                    this.vistaModificarControls.setVisible(false);
                    this.vistaMenuPrincipal.setJButtonsEnable(true);
                    break;

                case "TANCAR_LOBBY":
                    this.connexioUsuari.enviaOpcio("EXIT_LOBBY");
                    this.vistaWaitingLobby.setVisible(false);
                    this.vistaMenuPrincipal.setJButtonsEnable(true);
                    break;

                    // esto es de la vista partida:
                case "EXIT":
                    this.connexioUsuari.enviaOpcio(this.usuari.getNickname()+"/"+5);    // envia l'avis
                    JOptionPane.showMessageDialog(null, "Has premut el botó de sortir!!!!");
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vistaLogin, "Alguna de les opcions ha fallat, ¡l'ordre ha estat cancel·lada! :(", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al comunicar-nos amb el servidor!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que registra quan un usuari prem una tecla registrada a la finestra de joc
     * @param e event que representa la tecla premuda
     */
    public void keyTyped(KeyEvent e) {
        char char1 = Character.toUpperCase(e.getKeyChar());
        int direccio = 0;
        if (char1 == this.usuari.getMov_up().charAt(0)) direccio = 1;
        else {
            if (char1 == this.usuari.getMov_down().charAt(0)) direccio = 2;
            else {
                if (char1 == this.usuari.getMov_left().charAt(0)) direccio = 3;
                else {
                    if (char1 == this.usuari.getMov_right().charAt(0)) direccio = 4;
                }
            }
        }


        try {   // si no envia la opcion, pues nada.
            if (isAble(direccio)) {     // si el gir és vàlid:
                this.usuari.setDireccion(direccio);
                this.connexioUsuari.enviaOpcio(this.usuari.getNickname()+"/"+this.usuari.getDireccion());

            }
        } catch (IOException ex1) {}
    }

    /**
     * Metode que retorna si el jugador pot girar en la direccio seleccionada
     * @param direccio nova direccio a actualitzar
     * @return boolea que indica si es pot girar o no
     */
    private boolean isAble(int direccio) {
        return abs(direccio-this.usuari.getDireccion()) != 1;
    }

    /**
     * Metode que fa tots els passos per actualitzar la finestra (desde el Boto de la finestra nova, no el del menu de client)
     * @throws IOException
     */
    private void modificaControlsFinestra() throws IOException{
        // Canviem els controls de l'usuari:
        this.usuari.setControls(this.controllerModificarControls.getControls());
        // 3. Creem la finestra i actualitzem els valors dels controls:
        if (this.controllerModificarControls.setControls(this.usuari.getControls())) {
            // Enviem la ordre al servidor
            this.connexioUsuari.enviaOpcio("ENVIANT");
            // Enviem els controls escrits per l'usuari
            this.controllerModificarControls.enviaControls(this.connexioUsuari);
            // Esperem la resposta del servidor
            String resposta = this.connexioUsuari.respostaServidor();
            // Mostrem la resposta per un dialog
            JOptionPane.showMessageDialog(null, resposta);
        } else {
            JOptionPane.showMessageDialog(null, "Alguna tecla està repetida!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metode que fa tots els passos per enviar l'usuari actual a la llista d'espera de matchmaking corresponent
     * @param tipusCua int que simblitza el tipus de cua on hem de posar l'usuari (2: versus2 / 4: versus4 / 0: mode torneig)
     */
    private void enviaCuaEspera(int tipusCua) {
        switch (tipusCua) {
            case 2:
                this.vistaWaitingLobby.setJlIntro(" > Esperant un altre jugador... < ");
                this.vistaWaitingLobby.setVisible(true);
                break;
            case 4:
                this.vistaWaitingLobby.setJlIntro(" > Esperant tres jugadors més... < ");
                this.vistaWaitingLobby.setVisible(true);
                break;
            case 0:
                this.vistaWaitingLobby.setJlIntro(" > Esperant a començar el torneig... < ");
                this.vistaWaitingLobby.setVisible(true);
                break;
        }
        PartidaClient partida = new PartidaClient(tipusCua, this.connexioUsuari, this.usuari, this.vistaWaitingLobby, this);
        partida.start();

    }

    /**
     * Metode que realitza tots els passos per canviar els controls
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void canviarControls() throws IOException, ClassNotFoundException {
        // 1. Creem el String[] encarregat de guardar els controls que rebrem del servidor i que enviarem més tard
        String[] controls = new String[4];  //array de 4 strings que representen els codis ASCII ([] >>> {amunt, avall, esquerra, dreta})
        // 2. Rebem els controls del servidor
        controls = connexioUsuari.repControlsServidor(controls);
        // 2. Actualitzem els controls del nostre usuari:
        this.usuari.setControls(controls);
        // 3. Actualitzem els controls del controller de la vista:
        this.controllerModificarControls.setSending(true);
        this.controllerModificarControls.setControls(controls);
        this.controllerModificarControls.setSending(false);
        // 4. Tornem a desbloquejar el buto de enable SI NO ESTÀ ENABLEJAT  :
        if (!this.vistaModificarControls.getJbCanviar().isEnabled()) this.vistaModificarControls.setEnableJBCanviar(true);
        // 5. Ja podem mostrar la finestra amb totes les dades inicades correctament:
        this.vistaModificarControls.setVisible(true);
        //System.out.println("En teoria la vista es visible ahora");
    }

    /**
     * Tanca la sessió de l'usuari i canvia de finestra, tornant a la d'inici de sessió.
     */
    public void tancaSessio() {
        //this.usuari = null;
        //this.usuari = new Usuari();     //hem de reinicar el nou usuari
        this.vistaMenuPrincipal.setVisible(false);  // el socket es queda obert, ja que només hem tancat la sessió
        this.vistaLogin.setVisible(true);
        this.sessioIniciada = false;
    }

    /**
     * Metode que fa totes les rebudes d'informació del servidor per actualitzar el ranking
     */
    public void mostraRankingModalitats() {
        try {
            // Limpiar el ranking
            controllerRankingModalitat.getVistaRankingModalitat().netejaRanking();

            System.out.println("Ha borrao la tabla!");

            // Va a recibir el array de nombres y puntos del V2:
            controllerRankingModalitat.setUsuarisV2((String[]) connexioUsuari.repObject());
            controllerRankingModalitat.setPuntsv2((long[]) connexioUsuari.repObject());

            // Va a recibir el array de nombres y puntos del V2:
            controllerRankingModalitat.setUsuarisV4((String[]) connexioUsuari.repObject());
            controllerRankingModalitat.setPuntsv4((long[]) connexioUsuari.repObject());

            // Va a recibir el array de nombres y puntos del V2:
            controllerRankingModalitat.setUsuarisVT((String[]) connexioUsuari.repObject());
            controllerRankingModalitat.setPuntsvT((long[]) connexioUsuari.repObject());

            // Actualitza ranking modalitat:
            controllerRankingModalitat.actualitzaRankingModalitat(new String[][]
                            {controllerRankingModalitat.getUsuarisV2(),
                                    controllerRankingModalitat.getUsuarisV4(),
                                    controllerRankingModalitat.getUsuarisVT()},
                    new long[][]{controllerRankingModalitat.getPuntsv2(),
                            controllerRankingModalitat.getPuntsv4(),
                            controllerRankingModalitat.getPuntsvT()});

            vistaRanking.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualitzar el rànking!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error amb el rànking!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error amb les classes del rànking!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode que prepara la finestra de fer Login per al client
     */
    public void prepararFinestraLogin() {
        vistaLogin = new ViewLogin(this);
        controllerLogin = new ControllerTextAreaLogin(vistaLogin);
        vistaLogin.registraControladorCampsLogin(controllerLogin);
        // creem l'usuari i enviem les dades al servidor per comprobar que el jugador existeix, etc.
    }

    /**
     * Metode que inicia la connexio entre l'usuari i el servidor
     */
    public void connectarUsuariServidor() {
        // Establim la connexió amb el servidor
        int port = convertToInt(vistaConfiguracio.getJtfPort().getText());
        String ip_server = vistaConfiguracio.getJtfIPServer().getText();
        connexioUsuari.connectToServer(ip_server, port);    // aixo no hauria d'estar a un bucle, crec.
        if (connexioUsuari.isConnexioEstablerta()) {
            vistaConfiguracio.getJlResultat().setText("Connexió establerta!");
            vistaConfiguracio.setVisible(false);    // un cop ens hem pogut connectar, la vista de la config desapareix
        } else {
            JOptionPane.showMessageDialog(vistaConfiguracio,"Error al connectar-nos al servidor!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Això tracta les dades de l'usuari, envia l'objecte al servidor i espera si tot és correcte per iniciar sessió
     */
    public void iniciaSessio() throws IOException {
        // Avisem a l'usuari que volem inicar sessió:
        this.connexioUsuari.enviaOpcio("INICIAR_SESSIO");

        System.out.println("Ha enviat la opcio iniciar sessio!");
        if (this.connexioUsuari.repOpcio()) {   // Si el servidor dóna "el visto bueno"
            System.out.println("Ha llegit el boolea i ha estat un true!");
            // Variables auxiliars d'aquest metode i la seva inicialització:
            String aux = "";

            if (usuari.isNou_usuari()) {    // Si és nou usuari > REGISTRE:
                // el password no el podem llegir tal cual, perque es char[], hem de generar l'String char a char:
                for (int i = 0; i < vistaLogin.getJtfRGT_pass1().getPassword().length; i++) {
                    aux += vistaLogin.getJtfRGT_pass1().getPassword()[i];
                }
                usuari.setNickname(vistaLogin.getJtfRGT_nick().getText());
                usuari.setPassword(aux);
                usuari.setCorreu(vistaLogin.getJtfRGT_email().getText());
                usuari.actualitzaDataRegistre();
            } else {    // Si és un LOGIN, i ha entrat amb el correu, l'inicialitzarem també, per si de cas:
                for (int i = 0; i < vistaLogin.getJtfLGN_pass().getPassword().length; i++) {
                    aux += vistaLogin.getJtfLGN_pass().getPassword()[i];
                }
                usuari.setNickname(vistaLogin.getJtfLGN_nick().getText());
                usuari.setPassword(aux);
                // podem reutilitzar el metode de "emailValid" per veure si el nick és un correu.
                if (controllerLogin.emailValid(vistaLogin.getJtfLGN_nick().getText()))
                    usuari.setCorreu(vistaLogin.getJtfLGN_nick().getText());
            }


            // Un cop ha llegit tots els camps, els buida:
            this.vistaLogin.buidaCamps();
            // Envia l'usuari amb les dades omplertes al servidor:
            connexioUsuari.enviaUsuari(usuari);
            // El servidor respon sobre l'inici de sessio
            String resposta = connexioUsuari.respostaServidor();
            this.gestionaResposta(resposta);
        } else{
            System.out.println("El boolea del servidor ha estat un false!");
        }

    }

    /**
     * Metode que gestiona la reposta del servidor a l'ordre d'inici de sessió
     * @param resposta missatge enviat pel servidor, per distinguir si s'ha pogut fer login o no
     * @throws IOException
     */
    public void gestionaResposta(String resposta) throws IOException {
        switch (resposta) {
            case "LOGIN_KO":
                sessioIniciada = false;
                break;
            case "REGISTRE_KO":
                sessioIniciada = false;
                break;
            case "LOGIN_OK":
                sessioIniciada = true;
                break;
            case "REGISTRE_OK":
                sessioIniciada = true;
                break;
        }

        try {
            if (!sessioIniciada) {
                resposta = connexioUsuari.sessionError();   //llegeix l'error del servidor.
                JOptionPane.showMessageDialog(vistaLogin, resposta, "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {    // Un cop hem iniciat sessió, podem passar al menu principal del joc:
                // actualitzem l'usuari amb el que el servidor ens envia
                usuari.actualitzaUsuari(connexioUsuari.rebreUsuariServidor());
                JOptionPane.showMessageDialog(null, "Sessió iniciada!");
                this.vistaLogin.setVisible(false);
                this.vistaMenuPrincipal.setVisible(true);   // menu principal desplegat!
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vistaLogin, "Error al rebre l'usuari trobat pel servidor", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(vistaLogin, "Classe rebuda desconeguda!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aquest procediment tanca tots els processos amb sockets i altres actualment en execució i surt del joc.
     */
    public void terminaExecucioPrograma() {
        try {
            if (this.connexioUsuari.isConnexioEstablerta()) {
                this.connexioUsuari.getsServer().close();
                this.connexioUsuari.setConnexioEstablerta(false);
                this.usuari = null;
            } else {
                //chivato
                System.out.println("no hi ha cap connexió establerta! Sortint...");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terminant l'execució!", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(0);
    }

    /**
     * Mètode per a convertir un String a int
     * @param text string a convertir a int
     * @return retorna el int el qüestió
     */
    public int convertToInt(String text) {
        int res = 0;
        for (int i = 0; i < text.length(); i++) {
            res = res*10 + text.charAt(i) - '0';
        }
        return res;
    }
}
