package Network;

import Model.Usuari;
import View.*;
import Controller.*;

import javax.swing.*;
import javax.xml.transform.Result;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe encarregada de la sessio amb el servidor, i que conté els atributs per gestionar la transmissio d'informacio
 * @autor: Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version: 8.0
 */
public class ConnexioUsuari {
    private Socket sServer; /** Socket del server*/
    private int port; /** int pel port*/
    private String ipServer; /** String per la ip del server*/

    private boolean connexioEstablerta; /** boolea que indica que hi ha connexio establerta*/

    private ObjectInputStream oinput; /** ObjectInputStrem per rebre objectes*/
    private ObjectOutputStream  ooutput; /** ObjectOutputStrem per enviar objectes*/
    private DataInputStream dinput; /** DataInputStrem per rebre data*/
    private DataOutputStream doutput; /** DataInputStrem per enviar data*/

    // CONSTRUCTOR

    /**
     * Constructor de la classe connexio usuari;
     */
    public ConnexioUsuari() {
        this.connexioEstablerta = false;
    }


    // GETTERS I SETTERS

    /**
     * Getter del socket client de la connexio amb el servidor
     * @return retorna el socket client
     */
    public Socket getsServer() {
        return sServer;
    }

    /**
     * getter que diu si la connexio esta establerta
     * @return retorna l'estat de la connexio (true si esta oberta, false si esta tancada)
     */
    public boolean isConnexioEstablerta() {
        return connexioEstablerta;
    }

    /**
     * setter de l'estat de la connexio amb el servidor
     * @param connexioEstablerta nou estat de la connexio amb el servidor
     */
    public void setConnexioEstablerta(boolean connexioEstablerta) {
        this.connexioEstablerta = connexioEstablerta;
    }

    /**
     * getter del object input
     * @return retorna l'object input
     */
    public ObjectInputStream getOinput() {
        return oinput;
    }

    /**
     * getter del object output
     * @return retorna l'object output
     */
    public ObjectOutputStream getOoutput() {
        return ooutput;
    }

    /**
     * getter del data input
     * @return retorna el data input
     */
    public DataInputStream getDinput() {
        return dinput;
    }

    /**
     * getter del data output
     * @return retorna el data output
     */
    public DataOutputStream getDoutput() {
        return doutput;
    }

    // METHODS

    /**
     * Mètode que inicia la connexio del socket amb el servidor i les variables de transmissió de dades
     * @param ipServer ip del servidor a connectar
     * @param portServer port pel qual connectar-nos
     */
    public void connectToServer(String ipServer, int portServer) {
        this.port = portServer;
        this.ipServer = ipServer;

        try {
            this.sServer = new Socket(ipServer, port);
            this.connexioEstablerta = true;

            this.ooutput = new ObjectOutputStream(sServer.getOutputStream());
            this.oinput = new ObjectInputStream(sServer.getInputStream());
            this.doutput = new DataOutputStream(sServer.getOutputStream());
            this.dinput = new DataInputStream(sServer.getInputStream());

        } catch (IOException ex) {
            System.out.println("Problema al connectar-nos al servidor en qüestió!!");
        }
    }

    /**
     * Métode que envia la classe usuari amb les dades bàsiques necessàries omplertes cap al Servidor per iniciar sessió
     * @param usuari    usuari a iniciar sessió
     * @throws IOException
     */
    public void enviaUsuari(Usuari usuari) throws IOException{
        ooutput.reset();
        ooutput.writeObject(usuari);
    }

    /**
     * Metode que retorna si l'usuari ha iniciat sessió correctament al sistema (tant LOGIN com REGISTRE iniciarà sessió)
     * @return  boolea que indica si la sessió s'ha pogut iniciar o no
     * @throws IOException
     */
    public String respostaServidor() throws IOException {
        return dinput.readUTF();
    }

    /**
     * Metode que retorna un string amb l'error que envia el servidor que hi ha hagut a l'iniciar sessió
     * @return  string amb l'error comunicat pel servidor durant l'inici de sessió
     * @throws IOException
     */
    public String sessionError() throws IOException {
        return dinput.readUTF();
    }

    /**
     * Metode que rep l'usuari enviat pel servidor, amb totes les dades omplertes des de la base de dades
     * @return Usuari enviat pel servidor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Usuari rebreUsuariServidor() throws IOException, ClassNotFoundException {
        return (Usuari)oinput.readObject();
    }

    /**
     *
     * @param opcio
     * @throws IOException
     */
    public void enviaOpcio(String opcio) throws IOException {
        //doutput.flush();
        doutput.writeUTF(opcio);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public boolean repOpcio() throws IOException {
        return dinput.readBoolean();
    }

    /**
     * Metode que llegeix qualsevol objecte que el servidor envii
     * @return  retorna l'objecte, sonse definir el tipus
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Object repObject() throws ClassNotFoundException, IOException {
        return oinput.readObject();
    }

    /**
     * Metode que demana els controls de l'usuari al servidor i retorna els codis ASCII d'aquests en format String[] de
     * 4 caselles: amunt, avall, esquerra i dreta.
     * @return codis ASCII dels controls de l'usuari en qüestió
     */
    public String[] repControlsServidor(String[] controls) throws IOException, ClassNotFoundException {
        controls = (String[])this.oinput.readObject();

        return controls;
    }

    /**
     * Metode que envia un boolea
     */
    public void enviaSenyal() throws IOException {
        this.doutput.writeBoolean(true);
    }
}
