package Controller;

/**
 * Classe encarregada de gestionar el temps de cada ronda.
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class Timer extends Thread {
    private int time_cur; /** int on guardem el temps*/
    private boolean start; /** boolea que indica quan iniciem a contar el temps*/
    private boolean stop; /** boolea que indica quan terminem de contar el temps*/

    /**
     * Constructor del Timer
     * @param time_cur parametre on posem el temps inicial
     */
    public Timer(int time_cur) {
        this.time_cur = time_cur;
        this.start = false;
        this.stop = false;
    }

    /**
     * Getter del time actual
     * @return retorna el time_current
     */
    public int getTime() {
        return time_cur;
    }

    /**
     * Getter del start del timer
     * @return boolean
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Setter del start del timer
     * @param start nou valor de start
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Getter del stop
     * @param stop nou valor del boola stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Run del thread
     */
    @Override
    public void run() {
        try {
            this.time_cur = 0;
            while (!stop) {
                sleep(1000); // casi un segon, per que el reset pugui carregar-se
                this.time_cur++;   // incrementa en un els segons
            }
        } catch (InterruptedException e) {}
    }
}
