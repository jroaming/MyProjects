package Model;

/**
 * Classe que conté la informació per omplir els titols de la finestra de registre (noms dels JLabels)
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ModelRegistre {

    public static final String LGN_REGISTRE = "LGN_REGISTRE";   /**constant del titol del buto registre*/
    public static final String LGN_CANCEL = "LGN_CANCEL";   /**constant del titol del buto de cancellar registre*/

    private final String rgt_nick = "_Nickname: ";  /** constant del titol del camp nickname*/
    private final String rgt_pass1 = "_Password: "; /** constant del titol del camp password*/
    private final String rgt_pass2 = "_Password (again): "; /**constant del titol del camp password again*/
    private final String rgt_correu = "_Email: ";   /**constant del titl del camp de correu*/

    /**
     * Constructora dels camps Registre
     */
    public ModelRegistre(){}

    /**
     * Getter del nick del camp registre
     * @return nick del camp registre
     */
    public String getRgt_nick() {
        return rgt_nick;
    }

    /**
     * Getter del password del camp registre
     * @return password
     */
    public String getRgt_pass1() {
        return rgt_pass1;
    }

    /**
     * Getter del password again
     * @return password again
     */
    public String getRgt_pass2() {
        return rgt_pass2;
    }

    /**
     * Getter del registre del correu
     * @return
     */
    public String getRgt_correu() {
        return rgt_correu;
    }
}