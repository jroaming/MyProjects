package Model;

import Controller.ControlDBInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;

/**
 * Clase que contendrá los datos leídos del Json, asi como el controlador, que utilizaremos para gestionar las comandas
 * y las demandas de información a la base de datos
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class DBInfo {
    private int port_bbdd;      /**puerto de la base de datos*/
    private String ip_server;   /**direccion IP del servidor*/
    private String nom_bbdd;    /**nombre de la base de datos*/
    private String user_bbdd;   /**usuari de la base de datos*/
    private String pass_bbdd;   /**contrasenya de la base de datos*/
    private int port_client;    /**puerto del cliente para los sockets*/

    public ControlDBInfo controllerDBInfo;  /**controladora que realiza las comandas de la base de datos*/

    // CONSTRUCTOR

    /**
     * Constructor de la clase que crea el controlador del dbinfo
     */
    public DBInfo() {
        this.controllerDBInfo = new ControlDBInfo(this);
    }

    // GETTERS I SETTERS
    /**
     * Getter del puerto de la base de datos
     * @return puerto de la base de datos
     */
    public int getPort_bbdd() {
        return port_bbdd;
    }

    /**
     * Getter de la direccion del servidor
     * @return ip del servidor
     */
    public String getIp_server() {
        return ip_server;
    }

    /**
     * Getter del nombre de la base de datos
     * @return nombre de la base de datos
     */
    public String getNom_bbdd() {
        return nom_bbdd;
    }

    /**
     * Getter del usuario de la base de datos
     * @return nombre del usuario
     */
    public String getUser_bbdd() {
        return user_bbdd;
    }

    /**
     * Getter de la contraseña de la base de datos
     * @return contraseña de la base de datos
     */
    public String getPass_bbdd() {
        return pass_bbdd;
    }

    /**
     * Getter del puerto de los clientes
     * @return puerto de los clientes
     */
    public int getPort_client() {
        return port_client;
    }

    /**
     * Setter del puerto cliente
     * @return nuevo puerto de cliente
     */
    public void setPort_client(int port_client) {
        this.port_client = port_client;
    }

    /**
     * Getter del controlador de la base de datos
     * @return controlador de la base de datos
     */
    public ControlDBInfo getControllerDBInfo() {
        return controllerDBInfo;
    }
}
