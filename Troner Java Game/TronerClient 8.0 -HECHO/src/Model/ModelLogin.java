package Model;
/**
 * Classe encargada del modelLogin
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ModelLogin {

    public static final String LGN_LOGIN = "LGN_LOGIN"; /** String del LOGIN */
    public static final String LGN_REGISTRE = "LGN_REGISTRE"; /** String del REGISTRE */
    public static final String LGN_CANCEL = "LGN_CANCEL"; /** String del CANCEL */

    private final String lgn_nick = "_Nick/Email: "; /** String del nick */
    private final String lgn_pass = "_Password: "; /** String de la pass*/
    private final String rgt_nick = "_Nickname: "; /** String del nick registre */
    private final String rgt_pass1 = "_Password: "; /** String de la pass registre */
    private final String rgt_pass2 = "_Password (again): "; /** String de la pass2  registre */
    private final String rgt_correu = "_Email: ";/** String del correu*/

    /**
     * Constructor del Login
     */
    public ModelLogin() {
    }

    /**
     * Getter del Login
     * @return Retorna una String
     */
    public static String getLgnLogin() {
        return LGN_LOGIN;
    }

    /**
     * Getter del Register
     * @return Retorna una String
     */
    public static String getLgnRegistre() {
        return LGN_REGISTRE;
    }

    /**
     * Getter del Cancel
     * @return Retorna una String
     */
    public static String getLgnCancel() {
        return LGN_CANCEL;
    }

    /**
     * Getter del nick login
     * @return Retorna una String
     */
    public String getLgn_nick() {
        return lgn_nick;
    }

    /**
     * Getter de la pass login
     * @return Retorna una String
     */
    public String getLgn_pass() {
        return lgn_pass;
    }

    /**
     * Getter del nick registre
     * @return Retorna una String
     */
    public String getRgt_nick() {
        return rgt_nick;
    }

    /**
     * Getter del pass 1 registre
     * @return Retorna una String
     */
    public String getRgt_pass1() {
        return rgt_pass1;
    }

    /**
     * Getter del pass 2 registre
     * @return Retorna una String
     */
    public String getRgt_pass2() {
        return rgt_pass2;
    }

    /**
     * Getter del correu
     * @return Retorna una String
     */
    public String getRgt_correu() {
        return rgt_correu;
    }

}
