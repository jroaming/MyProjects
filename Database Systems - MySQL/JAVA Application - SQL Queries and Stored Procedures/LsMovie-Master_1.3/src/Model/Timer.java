package Model;

public class Timer {    // se encarga de controlar el tiempo que tarda en cargar la base de datos (en segundos)
    private long start;
    private long end;

    public void start() {
        start = System.currentTimeMillis()/1000;
    }

    public void end() {
        end = System.currentTimeMillis()/1000;
    }

    public String getTime() {
        return (end-start)+"";
    }
}
