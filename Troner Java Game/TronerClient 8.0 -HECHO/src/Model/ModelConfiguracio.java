package Model;
/**
 * Classe encargada del modelConfiguracio
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ModelConfiguracio {
    public static final String CONFIG_CONNECTAR = "CONFIG_CONNECTAR"; /** String de la CONFIG_CONNECTAR */
    public static final String CONFIG_SORTIR = "CONFIG_SORTIR"; /** String de la CONFIG_SORTIR */

    public final String CONNECTAR = "Connectar"; /** String de CONNECTR */
    public final String SORTIR = "Sortir"; /** String del SORTIR */
    public final String PORT = " Port a utilitzar: "; /** String del PORT*/
    public final String IP = " Direcció IP: "; /** String de la IP*/

    /**
     * Constructor del ModelConfiguracio
     */
    public ModelConfiguracio() {
    }
    /**
     * Getter de Connectar
     * @return Retorna una String
     */
    public String getCONNECTAR() {
        return CONNECTAR;
    }

    /**
     * Getter de Sortir
     * @return Retorna una String
     */
    public String getSORTIR() {
        return SORTIR;
    }

    /**
     * Getter del port
     * @return Retorna una String
     */
    public String getPORT() {
        return PORT;
    }

    /**
     * Getter de la ip
     * @return Retorna una String
     */
    public String getIP() {
        return IP;
    }

    /**
     * Getter del config connectar
     * @return Retorna una String
     */
    public String getConfigConnectar() {
        return CONFIG_CONNECTAR;
    }

    /**
     * Getter del config sortir
     * @return Retorna una String
     */
    public String getConfigSortir() {
        return CONFIG_SORTIR;
    }
}
