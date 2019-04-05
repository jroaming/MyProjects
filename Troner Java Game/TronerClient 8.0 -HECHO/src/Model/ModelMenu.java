package Model;
/**
 * Classe encargada del modelMenu
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ModelMenu {
    public static final String BTN_OPCIO1 = "OPCIO1"; /** String de OPCIO 1*/
    public static final String BTN_OPCIO2 = "OPCIO2"; /** String de OPCIO 2*/
    public static final String BTN_OPCIO3 = "OPCIO3"; /** String de OPCIO 3*/
    public static final String BTN_OPCIO4 = "OPCIO4"; /** String de OPCIO 4*/
    public static final String BTN_OPCIO5 = "OPCIO5"; /** String de OPCIO 5*/
    public static final String BTN_OPCIO6 = "OPCIO6"; /** String de OPCIO 6*/
    public static final String BTN_OPCIOSORTIR = "OPCIOSORTIR"; /** String de OPCIO SORTIR*/

    private final String TITLE = "TRONER "; /** String de TITLE*/
    private final String MODE = "[player/client_mode]"; /** String de MODE*/
    private final String OPCIO1 = "   Ranking per modalitat"; /** String de OPCIO 1*/
    private final String OPCIO2 = "   Modificar controls"; /** String de OPCIO 2*/
    private final String OPCIO3 = "   Jugar mode vs1"; /** String de OPCIO 3*/
    private final String OPCIO4 = "   Jugar mode vs4"; /** String de OPCIO 4*/
    private final String OPCIO5 = "   Jugar mode campionat"; /** String de OPCIO 5*/
    private final String OPCIO6 = "   Tancar sessió"; /** String de OPCIO 6*/
    private final String OPCIOSORTIR = "   Sortir de Troner"; /** String de OPCIO SORTIR*/

    // CONSTRUCTOR

    /**
     * Constructor del ModelMenu
     */
    public ModelMenu() {

    }

    // GETTERS (no setters)

    /**
     * Getter del Title
     * @return retorna un String
     */
    public String getTITLE() {
        return TITLE;
    }

    /**
     * Getter del Mode
     * @return retorna un String
     */
    public String getMODE() {
        return MODE;
    }

    /**
     * Getter de la opcio 1
     * @return retorna un String
     */
    public String getOPCIO1() {
        return OPCIO1;
    }

    /**
     * Getter de la opcio 2
     * @return retorna un String
     */
    public String getOPCIO2() {
        return OPCIO2;
    }

    /**
     * Getter de la opcio 3
     * @return retorna un String
     */
    public String getOPCIO3() {
        return OPCIO3;
    }

    /**
     * Getter de la opcio 4
     * @return retorna un String
     */
    public String getOPCIO4() {
        return OPCIO4;
    }

    /**
     * Getter de la opcio 5
     * @return retorna un String
     */
    public String getOPCIO5() {
        return OPCIO5;
    }

    /**
     * Getter de la opcio 6
     * @return retorna un String
     */
    public String getOPCIO6() {
        return OPCIO6;
    }

    /**
     * Getter de la opcio sortir
     * @return retorna un String
     */
    public String getOPCIOSORTIR() {
        return OPCIOSORTIR;
    }

    /**
     * Getter de la opcio 1
     * @return retorna un String
     */
    public static String getBtnOpcio1() {
        return BTN_OPCIO1;
    }

    /**
     * Getter de la opcio 2
     * @return retorna un String
     */
    public static String getBtnOpcio2() {
        return BTN_OPCIO2;
    }

    /**
     * Getter de la opcio 3
     * @return retorna un String
     */
    public static String getBtnOpcio3() {
        return BTN_OPCIO3;
    }

    /**
     * Getter de la opcio 4
     * @return retorna un String
     */
    public static String getBtnOpcio4() {
        return BTN_OPCIO4;
    }

    /**
     * Getter de la opcio 5
     * @return retorna un String
     */
    public static String getBtnOpcio5() {
        return BTN_OPCIO5;
    }

    /**
     * Getter de la opcio 6
     * @return retorna un String
     */
    public static String getBtnOpcio6() {
        return BTN_OPCIO6;
    }

    /**
     * Getter de la opcio sortir
     * @return retorna un String
     */
    public static String getBtnOpciosortir() {
        return BTN_OPCIOSORTIR;
    }
}
