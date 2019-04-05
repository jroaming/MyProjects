package Model;

/**
 * Classe que conté la informació per omplir la informacio per generar el menu principal del joc.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ModelMenu {
    // constants per als ActionCommands (no fan falta, pero queda millor organitzat)
    public static final String BTN_OPCIO1 = "OPCIO1";   /** valor constant de representacio de la opcio 1 del menu principal (registrar usuari)*/
    public static final String BTN_OPCIO2 = "OPCIO2";   /** valor constant de representacio de la opcio 2 del menu principal (gestionar usuaris)*/
    public static final String BTN_OPCIO3 = "OPCIO3";   /** valor constant de representacio de la opcio 3 del menu principal (configurar sistema)*/
    public static final String BTN_OPCIO4 = "OPCIO4";   /** valor constant de representacio de la opcio 4 del menu principal (mostrar ranking)*/
    public static final String BTN_OPCIO5 = "OPCIO5";   /** valor constant de representacio de la opcio 5 del menu principal (mostrar grafic jugador)*/
    public static final String BTN_OPCIOSORTIR = "SORTIR"; /** valor constant de representacio de la opcio 6 del menu principal (sortida)*/

    private final String TITLE = "TRONER "; /** valor constant de rep. del camp titol (TRONER) del menu principal*/
    private final String MODE = "[admin/host_mode]";    /** valor constant de rep. del camp d'estat del servidor (offline/online) */
    private final String OPCIO1 = "   Registrar nou usuari";    /** representacio de la opcio registrar nou usuari del menu*/
    private final String OPCIO2 = "   Gestionar usuaris";   /** rep. de la opcio de gestionar usuaris*/
    private final String OPCIO3 = "   Configurar el sistema";   /** rep. de coniguracio del sistema*/
    private final String OPCIO4 = "   Visualitzar ranking"; /** constant de visualitzar ranking*/
    private final String OPCIO5 = "   Visualitzar gràfic jugador";  /** constant del grafic jugador*/
    private final String OPCIOSORTIR = "   Sortir de Troner"; /** sortir del troner*/

    // CONSTRUCTOR

    /**
     * Constructor de les constants del menu principal
     */
    public ModelMenu() {
    }

    // GETTERS (no setters)

    /**
     * Getter del titol del menu
     * @return titol del menu
     */
    public String getTITLE() {
        return TITLE;
    }

    /**
     * Getter del mode del servidor (online/offline)
     * @return mode del servidor
     */
    public String getMODE() {
        return MODE;
    }

    /**
     * Getter de la constant de la opcio1
     * @return constant opcio1
     */
    public String getOPCIO1() {
        return OPCIO1;
    }

    /**
     * Getter de la constant de la opcio2
     * @return constant opcio2
     */
    public String getOPCIO2() {
        return OPCIO2;
    }

    /**
     * Getter de la constant de la opcio3
     * @return constant opcio3
     */
    public String getOPCIO3() {
        return OPCIO3;
    }

    /**
     * Getter de la constant de la opcio4
     * @return constant opcio4
     */
    public String getOPCIO4() {
        return OPCIO4;
    }

    /**
     * Getter de la constant de la opcio5
     * @return constant opcio5
     */
    public String getOPCIO5() {
        return OPCIO5;
    }

    /**
     * Getter de la constant de la opcio sortir
     * @return constant opcio sortir
     */
    public String getOPCIOSORTIR() {
        return OPCIOSORTIR;
    }
}
