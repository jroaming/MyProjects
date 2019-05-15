package Controlador;

import Model.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connector.SSHConnector;
import Model.Usuari;
import Vista.FinestraPrincipal;
import javax.imageio.ImageIO;
import javax.swing.*;
import static Vista.PanelCercador.SEARCH;
import static Vista.PanelLogin.LOGIN;
import static Vista.PanelLogin.NEW_USER;
import static Vista.PanelRegistrar.REGISTER;

/**
 *
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 *
 * <b> Classe: ListenerBotons </b> <br/>
 * Implementa el controlador de FinestraPrincipal
 * </p>
 *
 * @version 1.0
 * @author  Clàudia Peiró - cpeiro@salleurl.edu
 * 			Xavier Roma - xroma@salleurl.edu <br/>
 * 			Arxius i Bases de Dades <br/>
 * 			La Salle - Universitat Ramon Llull. <br/>
 * 			<a href="http://www.salle.url.edu" target="_blank">www.salle.url.edu</a>
 *
 */
public class ListenerBotons implements ActionListener {

	private FinestraPrincipal finestraPrincipal;
	private SSHConnector localCon;
	private Usuari usuari;

	public ListenerBotons(FinestraPrincipal vista) {
		this.finestraPrincipal = vista;
		this.usuari = new Usuari();
	}

	public void actionPerformed(ActionEvent event) {

		switch (event.getActionCommand()) {

			case LOGIN:
				this.usuari.setName(finestraPrincipal.getLogin());
				this.usuari.setPass(finestraPrincipal.getPassword());
				this.ferLogin();
				break;

			case SEARCH:
				this.ferCerca();
				break;

			case NEW_USER:
				finestraPrincipal.swapToRegisterPanel();
				break;

			case REGISTER:
				this.usuari.setName(finestraPrincipal.getNewLogin());
				this.usuari.setPass(finestraPrincipal.getNewPassword());
				this.ferRegistre();
				break;
		}
	}

	private static Image getLoginFoto(String login) {
		Image image = null;
		try {
			URL url = new URL("https://estudy.salle.url.edu/fotos2/eac/" + login + ".jpg");
			image = ImageIO.read(url);
		} catch (IOException e) {
			try {
				image = ImageIO.read(new File("./img/default_profile.png"));
			} catch (IOException e1) {
				image = null;
			}
		}

		return image;
	}

	private void ferLogin() {
		try {
			this.localCon = new SSHConnector(usuari.getName(), usuari.getPass(), "new_movies2", 3306, "localhost");
			if (localCon.connect()) {
				finestraPrincipal.swapToSearchPanel();	//canvia al panell de cerca
				finestraPrincipal.addUser(getLoginFoto("ls31155"), finestraPrincipal.getLogin());
				this.actualitzaDBMovies();	//pop up  i carrega de DB Movies desde el servidor remot
			}
			else JOptionPane.showMessageDialog(finestraPrincipal, "No s'ha pogut fer login!", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println("Error al fer el LOGIN!");
		}
	}

	private void ferRegistre() {
		try {
			// per fer registre s'ha de connectar amb l'admin i crear l'usuari (REUTILITZEM LA CLASSE SSHConnector
			this.localCon = new SSHConnector("admin","adminpass", "new_movies2", 3306, "localhost");
			if (localCon.connect()) {
				boolean afegit = localCon.insertQuery("call afegeixUsuari('"+usuari.getName()+"', '"+usuari.getPass()+"');");
				localCon.disconnect();
				if (afegit) {
					JOptionPane.showMessageDialog(finestraPrincipal, "Usuari creat amb éxit!");
					finestraPrincipal.swapToLoginPanel();
				} else {
					JOptionPane.showMessageDialog(finestraPrincipal, "L'usuari ja existeix! Escull un altre nom!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				//amb aixo basta, perque si l'usuari no existeix el procedure ja l'haurà registrat!
			} else JOptionPane.showMessageDialog(finestraPrincipal, "No es pot establir comunicació amb la base de dades!", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error al fer el REGISTRE!");
		}
	}

	private void actualitzaDBMovies() {		//ahora remote es con la database new_movies; y la local donde meteremos la new_movies es new_movies2
		try {
			// FASE 1
			// 0. demana el grup
			//int ngrup = Integer.parseInt(JOptionPane.showInputDialog(finestraPrincipal, "Introdueix el nombre de grup del servidor (només el número -39-):"));
			SSHConnector remoteCon = new SSHConnector("admin", "adminpass", "new_movies", 3306, "localhost");
			if (remoteCon.connect()) {
				JOptionPane.showMessageDialog(finestraPrincipal, "Carregant base de dades, trigarà uns minuts...", "WARNING", JOptionPane.WARNING_MESSAGE);


				Timer timer = new Timer();
				timer.start();	// comencem a comptar el temps.

				// Exportem la info:
				// 4. Exportem la info de les taules ___________________________________________________________________________
				ResultSet dades;
				// 4.1. Person      ______________________________________________
				dades = remoteCon.selectQuery("select * from Person;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Person'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into person values("+
								dades.getInt("id_person")+", "+
								"'"+dades.getString("name").replace("'", " ")+"', "+
								dades.getInt("facebook_likes")+");");
					}
					System.out.println("'Person' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("\t-> Problema de SQL!");
				}

				// 4.2. Director    ______________________________________________
				dades = remoteCon.selectQuery("select * from Director;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Director'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into director values ("+
								dades.getInt("id_director")+");");
					}
					System.out.println("'Director' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("\t-> Problema de SQL!");
				}

				// 4.3. Actor       ______________________________________________
				dades = remoteCon.selectQuery("select * from Actor;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Actor'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into actor values ("+
								dades.getInt("id_actor")+");");
					}
					System.out.println("'Actor' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("\t-> Problema de SQL!");
				}

				// 4.4. Actor_Movie ______________________________________________
				dades = remoteCon.selectQuery("select * from Actor_movie;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Actor_Movie'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into actor_movie values ("+
								dades.getInt("id_actor")+", "+
								dades.getInt("id_movie")+");");
					}
					System.out.println("'Actor_Movie' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("\t-> Problema de SQL!");
				}

				// 4.5. Movie       ______________________________________________
				dades = remoteCon.selectQuery("select * from Movie;");
				String aux = "";
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Movie'...\t");
					while (dades.next()) {
						aux = "insert into movie values (";
						if (dades.getObject("id_movie") != null) {
							aux+=dades.getInt("id_movie")+", ";
						} else aux+="4923, ";
						if (dades.getObject("title") != null) {
							aux+="'"+dades.getString("title").replace("'", " ")+"', ";
						} else aux+="'', ";
						if (dades.getObject("id_director") != null) {
							aux+=dades.getInt("id_director")+", ";
						} else aux+=" 0, ";
						if (dades.getObject("year") != null) {
							aux+=dades.getInt("year")+", ";
						} else aux+="0, ";
						if (dades.getObject("duration") != null) {
							aux+=dades.getInt("duration")+", ";
						} else aux+="0, ";
						if (dades.getObject("country") != null) {
							aux+="'"+dades.getString("country")+"', ";
						} else aux+="'', ";
						if (dades.getObject("movie_facebook_likes") != null) {
							aux+=dades.getInt("movie_facebook_likes")+", ";
						} else aux+="0, ";
						if (dades.getObject("imdb_score") != null) {
							aux+=dades.getDouble("imdb_score")+", ";
						} else aux += "0.0, ";
						if (dades.getObject("gross") != null) {
							aux+=dades.getLong("gross")+", ";
						} else aux+= "0, ";
						if (dades.getObject("budget") != null) {
							aux+=dades.getLong("budget")+");";
						} else aux+="0);";
						localCon.insertQuery(aux);
					}
					System.out.println("'Movie' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("\t-> Problema de SQL!");
				}

				// 4.6.Genre_Movie ______________________________________________
				dades = remoteCon.selectQuery("select * from Genre_Movie;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Movie_Genre'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into genre_movie(id_genre, id_movie) values(" +
								dades.getInt("id_genre") + ", " +
								dades.getInt("id_movie") + ");");
					}
					System.out.println("'Movie_Genre' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("Error de SQL!");
				}

				// 4.7. Genre       ______________________________________________
				dades = remoteCon.selectQuery("select * from Genre;");
				try {
					System.out.print("\t-> Comencem a exportar a la taula 'Genre'...\t");
					while (dades.next()) {
						localCon.insertQuery("insert into genre values (" +
								dades.getInt("id_genre")+", "+
								"'"+dades.getString("description")+"'"+");");

					}
					System.out.println("'Genre' exportada amb éxit!");
				} catch (SQLException ex) {
					System.out.println("Error de SQL!");
				}

				timer.end();	// deixem de contar el temps

				remoteCon.disconnect();    // un cop hem actualitzat la base local, desconnectem la connexió amb la remota.
				JOptionPane.showMessageDialog(finestraPrincipal, "Base de dades actualitzada amb éxit! ["+timer.getTime()+" segons]");
			} else
				JOptionPane.showMessageDialog(finestraPrincipal, "No s'ha pogut actualitzar la base de dades! Es farà servir una versió antiga.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			System.out.println("Error, no has introduít un numero, no actualitzarà la base de dades.");
			JOptionPane.showMessageDialog(finestraPrincipal, "No has introduït un numero, la base de dades no s'actualitzarà.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println("Error de connexió remota!");
		}
	}

	private void ferCerca() {
		// agafem els valors de la vista
		String title = finestraPrincipal.getJtfMovTitle();
		String genre = finestraPrincipal.getJtfGenre();
		String actor = finestraPrincipal.getJtfActor();
		String director = finestraPrincipal.getJtfDirector();
		String country = finestraPrincipal.getJtfCountry();
		String order_by_what = finestraPrincipal.getOrderWhat();
		String order_by_how = finestraPrincipal.getOrderHow();

		switch (order_by_what) {
			case "Movie title":
				order_by_what = "title";
				break;
			case "Genre":
				order_by_what = "description";
				break;
			case "Director":
				order_by_what = "director";
				break;
			case "Country":
				order_by_what = "country";
				break;
			case "IMDB score":
				order_by_what = "imdb_score";
				break;
		}

		switch (order_by_how) {	// aixo és perque a l'hora d'omplir la taula, els orders by s'inverteixen.
			case "ASC":
				order_by_how = "DESC";
				break;
			case "DESC":
				order_by_how = "ASC";
				break;
		}

		// un cop tenim els valors i hem premut la tecla de search, netejem tota la vista per omplir-la amb els nous resultats.
		finestraPrincipal.clearFields();

		try {
			JOptionPane.showMessageDialog(finestraPrincipal, "Realitzant cerca, pot trigar uns minuts (~5)...");
			System.out.println("call fesCercaUltimate('" + title + "', '" + genre + "', '" + actor + "', '"+ director + "', '" + country + "', '" + order_by_what + "', '" + order_by_how + "');");
			ResultSet dades = localCon.selectQuery("call fesCercaUltimate('" + title + "', '" + genre + "', '" + actor + "', '" + director + "', '" + country + "', '" + order_by_what + "', '" + order_by_how + "');");
			int i = 0;
			while (dades.next()) {
				finestraPrincipal.addResultsRow(new String[]{dades.getString("title"),
															dades.getString("genre"),
															dades.getString("director"),
															dades.getString("country"),
															dades.getDouble("imdb_score")+""});
			}
			JOptionPane.showMessageDialog(finestraPrincipal, "Ha terminat la cerca!");
		} catch (Exception e) {
			System.out.println("Error de SQL.");
			JOptionPane.showMessageDialog(finestraPrincipal, "La query no ha retornat cap resultat!", "WARNING", JOptionPane.WARNING_MESSAGE);
		}

	}
}