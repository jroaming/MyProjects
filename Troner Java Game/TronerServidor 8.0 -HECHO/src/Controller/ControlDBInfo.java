package Controller;

import Model.DBInfo;
import Model.Usuari;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.*;
import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe que implementa les funcions de crida i extraccio d'informació de la base de dades
 * @author Joel López Romero, Alex Pons Camacho, Rubén Puertas Rey
 * @version 8.0
 */
public class ControlDBInfo {
    private DBInfo dbinfo;
    static String username;
    static String password;
    static String nomDB;
    static int port;
    static String url = "jdbc:mysql://localhost";
    static Connection conn = null;
    static Statement s;

    // CONSTRUCTORS
    /**
     * Constructor de la classe ControlDBInfo
     * @param dbinfo dbinfo a passar al atribut d'aquesta classe
     */
    public ControlDBInfo(DBInfo dbinfo) {
        this.dbinfo = dbinfo;
    }

    // METHODS
    /**
     * Metode que genera una cerca a la base de dades connectada
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, username, password);
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Conexió a base de dades establerta!");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    /**
     * Metode que executa un insert a la base de dades connectada
     * @param query insert a fer
     */
    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
        }
    }

    /**
     * Metode que fa una sentencia de tipus update per modificar la base de dades connectada
     * @param query sentencia update a fer
     */
    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    /**
     * Realitza un delete from a la base de dades connectada
     * @param query sentencia del delete a fer
     */
    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    /**
     * Metode que retorna el resultat d'una query feta a la base de dades connectada
     * @param query query a fer
     * @return ResultSet del resultat de la query
     */
    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Metode per desconnectar-nos de la base de dades
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }

    // APPLIED-METHODS
    /**
     * Metode que busca l'usuari guardat en aquesta classe a la base de dades
     * @param usuari usuari a buscar a la db
     * @return booleà que indica si ha trobat l'usuari (true) o no (false)
     */
    public boolean buscarUsuari(Usuari usuari) {
        String comanda = "";
        if (usuari.isNou_usuari()) {    // Això vol dir que és un REGISTRE:
            // com que l'usuari és nou, només hem de buscar si no hi ha cap nick ni correu com aquest ja a la database:
            comanda = "select * from usuari where nom like \'";
            comanda += usuari.getNickname();
            comanda += "\' or correu like \'";
            comanda += usuari.getCorreu();
            comanda += "\';";
        } else {                        // Si no, l'usuari ha fet un LOGIN:
            // com que l'usuari ja existeix i és només un login, hem de comparar els nicks/correus i la seva contrassenya:
            comanda = "select * from usuari where (nom like \'";
            comanda += usuari.getNickname();
            comanda += "\' or correu like \'";
            comanda += usuari.getNickname();
            comanda += "\') and contrassenya like \'";
            comanda += usuari.getPassword();
            comanda += "\';";
        }

        boolean found = false;
        try {
            ResultSet rs = dbinfo.getControllerDBInfo().selectQuery(comanda);
            String[] movs = new String[4];
            // comprovem el resultat dels selects per a cada situació:
            while (rs.next()) { //si comença el bucle és que ha trobat un usuari en comú
                //Actualitzem l'usuari per si l'hem d'enviar al client
                usuari.setNickname(rs.getString("nom"));
                usuari.setPassword(rs.getString("contrassenya"));
                usuari.setCorreu(rs.getString("correu"));
                usuari.setPunts_totals(rs.getLong("punts_totals"));
                usuari.setRegistre(rs.getDate("data_registre"));
                usuari.setUltim_acces(rs.getDate("data_ultim_acces"));
                movs[0] = rs.getString("mov_up");
                movs[1] = rs.getString("mov_down");
                movs[2] = rs.getString("mov_left");
                movs[3] = rs.getString("mov_right");
                usuari.setControls(movs);
                found = true;
            }

        } catch (SQLException e) {
            System.out.println("Error SQLException!");
        }
        return found;
    }

    /**
     * Metode que genera la crida que farem servir per crear l'usuari
     * @param usuari
     */
    public void creaUsuari(Usuari usuari) {
        insertQuery("insert into usuari values('"+usuari.getNickname()+"','"+
                usuari.getCorreu()+"','"+
                usuari.getPassword()+"',"+
                usuari.getPunts_totals()+","+
                usuari.getPunts_vs2()+","+
                usuari.getPunts_vs4()+","+
                usuari.getPunts_torneig()+","+
                "CURDATE()"+","+
                "CURDATE()"+",'"+
                usuari.getControls()[0]+"','"+
                usuari.getControls()[1]+"','"+
                usuari.getControls()[2]+"','"+
                usuari.getControls()[3]+"');");
    }

    /**
     * Metode que llegeix els atributs corresponents del dbinfo i actualitza el fitxer .json
     */
    public void canviaPortClient() {
        // Crea el nou json amb les dades corresponents
        JsonObject json = new JsonObject();
        json.addProperty("port_bbdd", dbinfo.getPort_bbdd());
        json.addProperty("ip_server",dbinfo.getIp_server());
        json.addProperty("nom_bbdd",dbinfo.getNom_bbdd());
        json.addProperty("user_bbdd",dbinfo.getUser_bbdd());
        json.addProperty("pass_bbdd",dbinfo.getPass_bbdd());
        json.addProperty("port_client",dbinfo.getPort_client()+"");
        // L'escriu
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonProva = gson.toJson(json);

            FileWriter file = new FileWriter("config.json");
            file.write(jsonProva);
            file.flush();
            file.close();
        } catch (Exception e) {
            System.out.println("Error al generar/actualitzar el fitxer de favorits!");
        }
    }

    /**
     * Metode que retorna els punts dels usuaris del ranking 10 de la modalitat indicada
     * @param mode mode de joc
     * @return array de ints dels punts dels usuaris
     * @throws SQLException
     */
    public long[] getRankingPunts(int mode) throws SQLException {
        long[] users = new long[10];
        int i = 0;
        ResultSet rs;
        switch (mode) {
            case 2:
                rs = this.selectQuery("select nom, punts_vs2 from usuari order by punts_vs2 desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getLong("punts_vs2");
                    i++;
                }
                break;
            case 4:
                rs = this.selectQuery("select nom, punts_vs4 from usuari order by punts_vs4 desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getLong("punts_vs4");
                    i++;
                }
                break;
            case 0:
                rs = this.selectQuery("select nom, punts_torneig from usuari order by punts_torneig desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getLong("punts_torneig");
                    i++;
                }
                break;
        }

        return users;
    }

    /**
     * Metode que retorna el nom dels usuaris del ranking 10 de la modalitat indicada
     * @param mode mode de joc
     * @return array de strings dels noms dels usuaris
     * @throws SQLException
     */
    public String[] getRankingUsuaris(int mode) throws SQLException {
        String[] users = new String[10];
        int i = 0;
        ResultSet rs;
        switch (mode) {
            case 2:
                rs = this.selectQuery("select nom, punts_vs2 from usuari order by punts_vs2 desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getString("nom");
                    i++;
                }
                break;
            case 4:
                rs = this.selectQuery("select nom, punts_vs4 from usuari order by punts_vs4 desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getString("nom");
                    i++;
                }
                break;
            case 0:
                rs = this.selectQuery("select nom, punts_torneig from usuari order by punts_torneig desc limit 10;");
                while (rs.next()) {
                    users[i] = rs.getString("nom");
                    i++;
                }
                break;
        }

        return users;
    }

    /**
     * Metode que omple la informació del dbinfo, llegint el .json corresponent (config.json)
     * @return dbinfo amb la informació omplerta
     */
    public DBInfo loadModelDBInfo() {
        // Leemos los datos del config.json AHORA
        Gson gson = new GsonBuilder().create();

        try {
            dbinfo = gson.fromJson(new BufferedReader(new FileReader("config.json")), new TypeToken<DBInfo>() {
            }.getType());


            username = dbinfo.getUser_bbdd();
            password = dbinfo.getPass_bbdd();
            nomDB = dbinfo.getNom_bbdd();
            url += ":"+dbinfo.getPort_bbdd()+"/";
            url += dbinfo.getNom_bbdd();


        } catch (IOException e) {
            System.out.println("Error al llegir l'arxiu .json");
        }

        return dbinfo;
    }

    /**
     * Metode que actualitza la base de dades amb els nous controls definiits per l'usuari i també el nostre usuari amb
     * els controls nous.
     * @param smsClient controls nous
     * @param usuari usuari a actualitzar
     * @return missatge d'éxit de la operacio
     */
    public String actualitzaControls(String[] smsClient, Usuari usuari) {
        // Actualitzem la base de dades:
        String update = "update usuari set mov_up = '"+smsClient[0]+
                "', mov_down = '"+smsClient[1]+
                "', mov_left = '"+smsClient[2]+
                "', mov_right = '"+smsClient[3]+"' where nom like '"+usuari.getNickname()+"';";
        this.updateQuery(update);

        return "Controls actualitzats amb éxit!";
    }

    /**
     * Funcio que retorna tots els usuaris de la base de dades
     * @return Llista de Strings
     * @throws SQLException
     */
    public String[] buscarUsuaris() throws SQLException {
        int numUsuaris = 0;
        int i = 0;
        ResultSet rs = this.selectQuery("select nom from usuari");
        while(rs.next()){
            numUsuaris++;
        }
        String[] usuaris = new String[numUsuaris];
        ResultSet rl = this.selectQuery("select nom from usuari");
        while(rl.next()){
            usuaris[i] = rl.getString(1);
            i++;
        }
        return usuaris;
    }

    /**
     * Funcio que retorna els punts de una modalitat en concreta d'un jugador
     * @param q aquesta string, conte el select que busca l'informacio que hem escollit per fer la grafica
     * @return
     * @throws SQLException
     */
    public Integer[] buscarInfoGrafica(String q) throws SQLException {
        ResultSet rl = this.selectQuery(q);
        int num_partida = 0;
        while (rl.next()){
            num_partida++;
        }
        Integer[] grafica = new Integer[num_partida];
        ResultSet rs = this.selectQuery(q);
        int x = 0;
        while(rs.next()){
            grafica[x] = rs.getInt(1);
            x++;
        }

        int i = 0;
        while ( i < num_partida) {
            i++;
        }
        return grafica;
    }
}
