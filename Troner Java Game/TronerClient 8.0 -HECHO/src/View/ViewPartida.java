package View;

import Model.Usuari;
import Network.PartidaClient;
import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * Classe que mostra la vista que representa el joc
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ViewPartida extends JFrame {
    public static final int ROWS = 46;  /** Files de la grid */
    public static final int COLS = 110;  /** Files de la grid */

    private JPanel jpArena; /** panell on es guarda la grid de la vista */
    private Casilla[][] casillas;   /** grid de caselles */
    private JLabel punts1;  /** punts de la partida del jugador 1 */
    private JLabel punts2;  /** punts de la partida del jugador 2 */
    private JLabel punts3;  /** punts de la partida del jugador 3 */
    private JLabel punts4;  /** punts de la partida del jugador 4 */
    private JLabel time;    /** valor de temps de la partida */
    private JLabel wins1;   /** numero de rondes guanyades pel jugador 1 */
    private JLabel wins2;   /** numero de rondes guanyades pel jugador 2 */
    private JLabel wins3;   /** numero de rondes guanyades pel jugador 3 */
    private JLabel wins4;   /** numero de rondes guanyades pel jugador 4 */
    private CompteEnrere comptaEnrere; /** classe de compta enrere, per a l'inici de la partida */

    // NUEVO:
    private JButton jbSortir;   /** JButton de sortida de la partida */


    /**
     * Constructor de la vista de joc
     * @param mode mode de joc, 2 4 o 0
     * @param players nombre de jugadors
     * @param controlador controlador encarregat de gestionar el boto de sortida
     */
    public ViewPartida(int mode, Usuari[] players, ActionListener controlador){

        this.comptaEnrere = new CompteEnrere();

        Font font = new Font("OCR A Extended", Font.PLAIN, 16);
        Font fontGran = new Font("OCR A Extended", Font.PLAIN, 25);

        this.setUndecorated(true);

        //Inicial
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(2*dim.width/3, 2*dim.height/3);
        setLocation(dim.width/6,dim.height/6);

        casillas = new Casilla[ROWS][COLS];

        setTitle("LS Troner");
        setResizable(false);

        JPanel jpBase = new JPanel(new BorderLayout());
        jpBase.setBackground(Color.darkGray);

        JPanel jpNorth = new JPanel(new GridLayout(1,6));
        jpNorth.setBackground(Color.BLACK);

        JPanel jpPlayer1 = new JPanel(new BorderLayout());
        JPanel jpPlayer2 = new JPanel(new BorderLayout());
        JPanel jpPlayer3 = new JPanel(new BorderLayout());
        JPanel jpPlayer4 = new JPanel(new BorderLayout());
        JPanel jpTime = new JPanel (new BorderLayout());
        JPanel jpSortir = new JPanel(new BorderLayout());

        JLabel nom1 = new JLabel(players[0].getNickname());
        JLabel nom2 = new JLabel(players[1].getNickname());
        JLabel nom3 = new JLabel();
        JLabel nom4 = new JLabel();
        if (mode == 4) {
            nom3.setText(players[2].getNickname());
            nom4.setText(players[3].getNickname());
        }
        JLabel nomTime = new JLabel("Time");

        wins1 = new JLabel("0");
        wins2 = new JLabel("0");
        if (mode == 4) {
            wins3 = new JLabel("0");
            wins4 = new JLabel("0");
        }
        time = new JLabel("0 s");

        punts1 = new JLabel("0");
        punts2 = new JLabel("0");
        if (mode == 4) {
            punts3 = new JLabel("0");
            punts4 = new JLabel("0");
        }

        nom1.setFont(font);
        nom2.setFont(font);
        if (mode == 4) {
            nom3.setFont(font);
            nom4.setFont(font);
        }
        nomTime.setFont(font);

        wins1.setFont(fontGran);
        wins2.setFont(fontGran);
        if (mode == 4) {
            wins3.setFont(fontGran);
            wins4.setFont(fontGran);
        }
        time.setFont(fontGran);

        punts1.setFont(font);
        punts2.setFont(font);
        if (mode == 4) {
            punts3.setFont(font);
            punts4.setFont(font);
        }

        punts1.setForeground(Color.white);
        punts2.setForeground(Color.white);
        if (mode == 4) {
            punts3.setForeground(Color.white);
            punts4.setForeground(Color.white);
        }

        nom1.setForeground(Color.white);
        nom2.setForeground(Color.white);
        if (mode == 4) {
            nom3.setForeground(Color.white);
            nom4.setForeground(Color.white);
        }
        nomTime.setForeground(Color.white);

        wins1.setForeground(Color.white);
        wins2.setForeground(Color.white);
        if (mode == 4) {
            wins3.setForeground(Color.white);
            wins4.setForeground(Color.white);
        }
        time.setForeground(Color.white);


        punts1.setHorizontalAlignment(JLabel.CENTER);
        punts2.setHorizontalAlignment(JLabel.CENTER);
        if (mode == 4) {
            punts3.setHorizontalAlignment(JLabel.CENTER);
            punts4.setHorizontalAlignment(JLabel.CENTER);
        }

        wins1.setHorizontalAlignment(JLabel.CENTER);
        wins2.setHorizontalAlignment(JLabel.CENTER);
        if (mode == 4) {
            wins3.setHorizontalAlignment(JLabel.CENTER);
            wins4.setHorizontalAlignment(JLabel.CENTER);
        }
        time.setHorizontalAlignment(JLabel.CENTER);

        nom1.setHorizontalAlignment(JLabel.CENTER);
        nom2.setHorizontalAlignment(JLabel.CENTER);
        if (mode == 4) {
            nom3.setHorizontalAlignment(JLabel.CENTER);
            nom4.setHorizontalAlignment(JLabel.CENTER);
        }
        nomTime.setHorizontalAlignment(JLabel.CENTER);

        jpPlayer1.setBackground(Color.black);
        jpPlayer2.setBackground(Color.black);
        if (mode == 4) {
            jpPlayer3.setBackground(Color.black);
            jpPlayer4.setBackground(Color.black);
        }
        jpTime.setBackground(Color.black);

        jpPlayer1.setBorder(BorderFactory.createLineBorder(Color.GREEN,6));
        jpPlayer2.setBorder(BorderFactory.createLineBorder(Color.BLUE,6));
        if (mode == 4) {
            jpPlayer3.setBorder(BorderFactory.createLineBorder(Color.RED, 6));
            jpPlayer4.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 6));
        }
        jpTime.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));

        jpPlayer1.add(nom1, BorderLayout.NORTH);
        jpPlayer2.add(nom2, BorderLayout.NORTH);
        if (mode == 4) {
            jpPlayer3.add(nom3, BorderLayout.NORTH);
            jpPlayer4.add(nom4, BorderLayout.NORTH);
        }
        jpTime.add(nomTime, BorderLayout.NORTH);

        jpPlayer1.add(wins1, BorderLayout.CENTER);
        jpPlayer2.add(wins2, BorderLayout.CENTER);
        if (mode == 4) {
            jpPlayer3.add(wins3, BorderLayout.CENTER);
            jpPlayer4.add(wins4, BorderLayout.CENTER);
        }
        jpTime.add(time, BorderLayout.CENTER);

        jpPlayer1.add(punts1, BorderLayout.SOUTH);
        jpPlayer2.add(punts2, BorderLayout.SOUTH);
        if (mode == 4) {
            jpPlayer3.add(punts3, BorderLayout.SOUTH);
            jpPlayer4.add(punts4, BorderLayout.SOUTH);
        }

        jpNorth.add(jpPlayer1);
        jpNorth.add(jpPlayer2);
        if (mode == 4) {
            jpNorth.add(jpPlayer3);
            jpNorth.add(jpPlayer4);
        }
        jpNorth.add(jpTime);

        jpBase.add(jpNorth, BorderLayout.NORTH);
        add(jpBase, BorderLayout.CENTER);


        jpArena = new JPanel(new GridLayout(ROWS,COLS));
        jpArena.setBackground(Color.black);
        jpArena.setBorder(BorderFactory.createLineBorder(Color.black,12));
        jpBase.add(jpArena, BorderLayout.CENTER);


        for (int i=0; i < ROWS; i++){
            for (int j=0; j < COLS; j++) {
                casillas[i][j] = new Casilla(0);
                casillas[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,1));
                jpArena.add(casillas[i][j]);
            }
        }
        this.setVisible(true);
    }

    /**
     * Getter dels punts del jugador
     * @return array de strings amb les victories
     */
    public String[] getPunts(int nplayers) {
        String[] victories = new String[4];
        victories[0] = punts1.getText();
        victories[1] = punts2.getText();
        if (nplayers == 4) {
            victories[2] = punts3.getText();
            victories[3] = punts4.getText();
        }
        return victories;
    }

    /**
     * Getter del panell de caselles
     * @return retorna el panell de caselles
     */
    public JPanel getJpArena() {
        return jpArena;
    }


    /**
     * Metode uqe rep el nombre de victories dels jugadors
     * @return array de wins
     */
    public int[] getWins(int njugadors) {
        int[] wins = new int[njugadors];
        wins[0] = Integer.parseInt(wins1.getText());
        wins[1] = Integer.parseInt(wins1.getText());
        if (njugadors == 4) {
            wins[2] = Integer.parseInt(wins1.getText());
            wins[3] = Integer.parseInt(wins1.getText());
        }
        return wins;
    }

    /**
     * Metode que actualitza la grid de la finestra de joc
     * @param grid matriu de caselles per on pasen els jugadors
     */
    public void setGrid(int[][] grid) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0;  j < COLS; j++) {
                this.casillas[i][j].actualitza(grid[i][j]);

            }
        }
    }

    /**
     * Getter del valor de la casella indicada
     * @param cx coordenada X de la casella a veure
     * @param cy coordenada y de la casella a veure
     * @return retorna l'int del valor de la casella
     */
    public int getCasella(int cx, int cy) {
        return this.casillas[cx][cy].getNum_player();
    }

    /**
     * Metode que registra el controlador controller com a key listener del keyboard
     * @param controller controlador del keyboard
     */
    public void registraControlador(Controller controller) {
        this.addKeyListener(controller);
    }

    /**
     * Metode que actualitza la casella
     * @param cx coordenada x de la casella a actualitzar
     * @param cy coordenada y de la casella a actualitzar
     * @param valor valor de la casella a actualitzar
     */
    public void actualitzaCasella(int cx, int cy, int valor) {
        this.casillas[cx][cy].actualitza(valor);
    }

    /**
     * Metode encarregat d'actualitzar les victories del array de jugadors de la partida
     * @param iWinner jugador a incrementar la victoria
     */
    public void actualitzaJugadors(int iWinner, int punts) {
        switch (iWinner) {
            case 0:
                wins1.setText((Integer.parseInt(wins1.getText())+1)+"");
                break;
            case 1:
                wins2.setText((Integer.parseInt(wins2.getText())+1)+"");
                break;
            case 2:
                wins3.setText((Integer.parseInt(wins3.getText())+1)+"");
                break;
            case 3:
                wins4.setText((Integer.parseInt(wins4.getText())+1)+"");
                break;
            default:
                break;
        }
    }

    /**
     * Metode que actualitza els punts de cada jugador
     * @param njugadors nombre de jugadors de la partida
     * @param players array de players de la partida
     */
    public void actualitzaPunts(int njugadors, Usuari[] players) {
        this.punts1.setText(players[0].getPunts_partida()+"");
        this.punts2.setText(players[1].getPunts_partida()+"");
        if (njugadors == 4) {
            this.punts3.setText(players[2].getPunts_partida()+"");
            this.punts4.setText(players[3].getPunts_partida()+"");
        }
    }

    /**
     * Meode encarregat de mostrar un missatge no interactiu a la vista
     */
    public void mostraComptaEnrere(int count) {
        this.comptaEnrere.mostraCount(count);
    }

    /**
     * Metode que setteja el buto de sortida, el bloqueja o l'allibera, per un major control
     * @param enabled nou valor del enable del boto
     */
    public void setSortirEnabled(boolean enabled) {
        this.jbSortir.setEnabled(enabled);
    }

    /**
     * Getter del compteEnrere
     * @return
     */
    public CompteEnrere getComptaEnrere() {
        return comptaEnrere;
    }

    /**
     * Setter del compteEnrere
     * @param comptaEnrere
     */
    public void setComptaEnrere(CompteEnrere comptaEnrere) {
        this.comptaEnrere = comptaEnrere;
    }

    /**
     * Metode que reinicia la matriu
     */
    public void reiniciaMatriu() {
        for (int i = 0; i < ROWS; i++) for (int j = 0; j < COLS; j++) this.casillas[i][j].actualitza(0);
    }

    /**
     * Setter del time (actualitza el time de la partida)
     * @param time nou time
     */
    public void setTime(int time) {
        this.time.setText(time +" s");
    }

    /**
     * Metode uqe retorna el nombre de rondes guanyades per un usuari determinat
     * @param myUserIndex index de l'usuari a saber les seves rondes guanyades
     * @return int de rondes guanyades
     */
    public int getWinsPlayer(int myUserIndex) {
        switch (myUserIndex) {
            case 0:
                return Integer.parseInt(wins1.getText());
            case 1:
                return Integer.parseInt(wins2.getText());
            case 2:
                return Integer.parseInt(wins3.getText());
            case 3:
                return Integer.parseInt(wins4.getText());
        }
        return -1;
    }
}
