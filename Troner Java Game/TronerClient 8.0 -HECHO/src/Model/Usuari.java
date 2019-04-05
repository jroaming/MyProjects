package Model;

import java.io.Serializable;
import java.util.Date;  // si peta, es porque es el otro tipo de dato Date, el de SQL.

/**
 * Classe model que conté tota la informacio i els getters i setters per modificar les dades referents als usuaris connectats
 * al servidor i implementa serializable, ja que hem de poder transmetre aquesta classe entre servidor i client
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Usuari implements Serializable {
    private String nickname;    /** nick de l'usuari */
    private String password;    /** contrassenya de l'usuari*/
    private String correu;      /** correu de l'usuari */
    private Date registre;      /** data de registre d'aquest usuari */
    private Date ultim_acces;   /** data d'ultim acces al compte d'aquest usuari */
    private long punts_totals;  /** punts totals de l'usuari en totes les modalitats */
    private long punts_vs2;     /** punts totals de l'usuari en partides de tipus vs2 */
    private long punts_vs4;     /** punts totals de l'usuari en partides de tipus vs4 */
    private long punts_torneig; /** punts totals de l'usuari en partides de tipus torneig */
    private String mov_up;      /** tecla de control de moviment cap amunt */
    private String mov_down;    /** tecla de control de moviment cap avall */
    private String mov_left;    /** tecla de control de moviment cap a l'esquerra */
    private String mov_right;   /** tecla de control de moviment cap a la dreta */
    private boolean nou_usuari; /** boolea que indica si l'usuari és nou (ha fet registre) o no (ha fet login) */
    private boolean playing;    /** boolea que indica si esta jugant o esta eliminat */
    private boolean onGame;     /** boolea que indica si l'usuari està a una partida actualment */
    private boolean exit;       /** boolea que indica si l'usuari ha fet exit */
    private int punts_partida;  /** quantitat de punts de la partida actual */

    // nuevo
    private int direccion;      /** int que indica la dirección del jugador */
    private int wins;           /** int que indica el nombre de rondes guanyades */

    // CONSTRUCTORS

    /**
     * Constructor de la classe Usuari
     */
    public Usuari() {
        this.punts_totals = 0;
        this.punts_vs2 = 0;
        this.punts_vs4 = 0;
        this.punts_torneig = 0;
        this.punts_partida = 0;
        this.mov_up = "W";
        this.mov_down = "S";
        this.mov_left = "A";
        this.mov_right = "D";
        this.playing = false;
        this.onGame = false;
        this.exit = false;
        this.direccion = 0;
    }

    // GETTERS I SETTERS

    /**
     * Getter de wins
     * @return nombre de victories
     */
    public int getWins() {
        return wins;
    }

    /**
     * Setter de rondes guanyades
     * @param wins nou nombre de rondes guanyades
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Getter del nom d'usuari
     * @return retorna el nom de l'usuari
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter del nom de l'suuari
     * @param nickname nou nom de l'usuari
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter de la contrassenya de l'usuari
     * @return contrassenya de l'usuari en format string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter de la contrassenya de l'usuari
     * @param password nova contrassenya
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter del correu de l'usuari
     * @return retorna el correu de l'usuari
     */
    public String getCorreu() {
        return correu;
    }

    /**
     * Setter del correu de l'usuari
     * @param correu nou correu de l'usuari
     */
    public void setCorreu(String correu) {
        this.correu = correu;
    }

    /**
     * Getter de la data de registre de l'usuari
     * @return data de registre
     */
    public Date getRegistre() {
        return registre;
    }

    /**
     * Setter de la data de registre de l'usuari
     * @param registre nova data de registre
     */
    public void setRegistre(Date registre) {
        this.registre = registre;
    }

    /**
     * Getter de la data d'ultim acces
     * @return data d'ultim acces de l'usuari
     */
    public Date getUltim_acces() {
        return ultim_acces;
    }

    /**
     * Setter de l'ultim acces de l'usuari
     * @param ultim_acces nova data d'ultim acces
     */
    public void setUltim_acces(Date ultim_acces) {
        this.ultim_acces = ultim_acces;
    }

    /**
     * Getter del boolea que indica si l'usuari és nou
     * @return valor de nou usuari
     */
    public boolean isNou_usuari() {
        return nou_usuari;
    }

    /**
     * Setter del boolea de "nou usuari"
     * @param nou_usuari nou valor boolea de nou usuari
     */
    public void setNou_usuari(boolean nou_usuari) {
        this.nou_usuari = nou_usuari;
    }

    /**
     * Metode que retorna en format String[] els controls del nostre usuari
     * @return array de strings amb els controls (per ordre: amunt, avall, esquerra, dreta).
     */
    public String[] getControls() {
        return new String[] {this.mov_up, this.mov_down, this.mov_left, this.mov_right};
    }

    /**
     * Setter que modifica els nostres controls de l'usuari
     * @param controls controls nous
     */
    public void setControls(String[] controls) {
        this.mov_up = controls[0];
        this.mov_down = controls[1];
        this.mov_left = controls[2];
        this.mov_right = controls[3];
    }

    /**
     * Getter dels punts totals
     * @return
     */
    public long getPunts_totals() {
        return punts_totals;
    }

    /**
     * Setter dels punts totals
     * @param punts_totals
     */
    public void setPunts_totals(long punts_totals) {
        this.punts_totals = punts_totals;
    }

    /**
     * Getter de si esta jugant a una partida actualment
     * @return
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Setter del boolea playing de l'usuari
     * @param playing
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * Getter dels punts de la partida actual
     * @return
     */
    public int getPunts_partida() {
        return punts_partida;
    }

    /**
     * Setter dels punts de la partida actuals
     * @param punts_partida
     */
    public void setPunts_partida(int punts_partida) {
        this.punts_partida = punts_partida;
    }

    /**
     * Getter de tots els punts de la modalitat versus2 guanyats per l'usuari
     * @return quantitat de punts d'auqesta modalitat
     */
    public long getPunts_vs2() {
        return punts_vs2;
    }

    /**
     * Setter de tots els punts de la modalitat versus2 guanyats per l'usuari
     * @param punts_vs2 nova quantitat de punts d'auqesta modalitat
     */
    public void setPunts_vs2(long punts_vs2) {
        this.punts_vs2 = punts_vs2;
    }

    /**
     * Getter de tots els punts de la modalitat versus4 guanyats per l'usuari
     * @return quantitat de punts d'auqesta modalitat
     */
    public long getPunts_vs4() {
        return punts_vs4;
    }

    /**
     * Setter de tots els punts de la modalitat versus4 guanyats per l'usuari
     * @param punts_vs4 nova quantitat de punts d'auqesta modalitat
     */
    public void setPunts_vs4(long punts_vs4) {
        this.punts_vs4 = punts_vs4;
    }

    /**
     * Getter de tots els punts de la modalitat torneig guanyats per l'usuari
     * @return quantitat de punts d'auqesta modalitat
     */
    public long getPunts_torneig() {
        return punts_torneig;
    }

    /**
     * Setter de tots els punts de la modalitat torneig guanyats per l'usuari
     * @param punts_torneig nova quantitat de punts d'auqesta modalitat
     */
    public void setPunts_torneig(long punts_torneig) {
        this.punts_torneig = punts_torneig;
    }

    /**
     * Getter de si el jugador està a una partida actualment
     * @return retorna el valor boolea de si es a una partida
     */
    public boolean isOnGame() {
        return onGame;
    }

    /**
     * Getter de onGame
     * @param onGame nou valor de onGame de l'usuari
     */
    public void setOnGame(boolean onGame) {
        this.onGame = onGame;
    }

    /**
     * Getter del moviment up
     * @return tecla en qüestió per a fer aquest canvi de direcció
     */
    public String getMov_up() {
        return mov_up;
    }

    /**
     * Getter del moviment down
     * @return tecla en qüestió per a fer aquest canvi de direcció
     */
    public String getMov_down() {
        return mov_down;
    }

    /**
     * Getter del moviment left
     * @return tecla en qüestió per a fer aquest canvi de direcció
     */
    public String getMov_left() {
        return mov_left;
    }

    /**
     * Getter del moviment right
     * @return tecla en qüestió per a fer aquest canvi de direcció
     */
    public String getMov_right() {
        return mov_right;
    }

    /**
     * Metode que actualitza la data d'ultim acces de l'usuari
     */
    public void actualitzaUltimAcces() {
        this.ultim_acces = new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * Metode que actualitza la data de registre de l'usuari
     */
    public void actualitzaDataRegistre() {
        this.registre = new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * Getter del boolea exit del jugador
     * @return valor booleà de si el jugador ha abandonat una partida
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Setter del valor exit d'un usuari
     * @param exit nou valor d'exit del jugador
     */
    public void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * Getter de la direccio
     * @return int de la dirección
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * Setter de la dirección
     * @param direccion dirección nueva
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    // METHODS

    /**
     * Nou toString d'aquest usuari
     * @return
     */
    @Override
    public String toString() {
        return "nom:"+nickname+"/pass:"+password+"/correu:"+correu+"/registre:"+registre+"/ultim_acces:"+ultim_acces+"/punts_totals:"+punts_totals+"/mov_up:"+mov_up+".";
    }

    /**
     * Metode que actualitza l'usuari
     * @param usuari usuari amb les dades que hem d'igualar al nostre
     */
    public void actualitzaUsuari(Usuari usuari) {
        this.nickname = usuari.getNickname();
        this.correu = usuari.getCorreu();
        this.password = usuari.getPassword();
        this.punts_totals = usuari.getPunts_totals();
        this.punts_vs2 = usuari.getPunts_vs2();
        this.punts_vs4 = usuari.getPunts_vs4();
        this.punts_torneig = usuari.getPunts_torneig();
        this.registre = usuari.getRegistre();
        this.ultim_acces = usuari.getUltim_acces();
        this.mov_up = usuari.getControls()[0];
        this.mov_down = usuari.getControls()[1];
        this.mov_left = usuari.getControls()[2];
        this.mov_right = usuari.getControls()[3];
        this.playing = usuari.isPlaying();
        this.onGame = usuari.isOnGame();
        this.exit = usuari.isExit();
        this.direccion = usuari.getDireccion();
    }
}
