package View;

import Controller.ControllerGrafica;
import javax.swing.*;
import java.awt.*;

/**
 * Classe que llegeix i mostra la informació de la gràfica a demanar per l'usuari
 * @author Alex Pons Camacho
 * @version 8.0
 */
public class ViewOpcioGrafica extends JFrame{
    private JComboBox<String> jcbUsuaris;   /** jcombobox per seleccionar els usuaris creats */
    private JComboBox<String> jcbModalitat; /** jcombobox per seleccionar la modalitat de joc */
    private JButton jbSortir;               /** jbutton de tancar la finestra */
    private JButton jbFerGrafica;           /** jbutton de generar la gràfica */

    /**
     * Contructor de la gràfica
     */
    public ViewOpcioGrafica(){
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        this.setUndecorated(true);
        setVisible(false);

        JPanel jpCenter = new JPanel(new GridLayout(1,2));
        jcbUsuaris = new JComboBox<String>();
        jcbModalitat = new JComboBox<String>();
        jcbUsuaris.setBackground(Color.BLACK);
        jcbModalitat.setBackground(Color.BLACK);
        jpCenter.add(jcbUsuaris);
        jpCenter.add(jcbModalitat);
        this.add(jpCenter);
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        jbSortir = new JButton(" -> Tancar finestra <- ");
        jbSortir.setBackground(Color.BLACK);
        jbSortir.setForeground(Color.CYAN);
        jbSortir.setFont(fontTitol);
        jbFerGrafica = new JButton("-> Fer Grafica <- ");
        jbFerGrafica.setBackground(Color.BLACK);
        jbFerGrafica.setForeground(Color.CYAN);
        jbFerGrafica.setFont(fontTitol);
        JPanel jpSortir = new JPanel(new GridLayout(2,1));
        jpSortir.setBackground(Color.BLACK);
        jpSortir.add(jbFerGrafica);
        jpSortir.add(jbSortir);
        this.add(jpSortir,BorderLayout.SOUTH);
        pack();
    }

    /**
     * Metode que genera la combobox de l'array d'usuaris
     * @param usuaris array d'usuaris amb què omplir la combobox
     */
    public void setJComboboxUsuari(String[] usuaris){
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        for(String e :usuaris){
            jcbUsuaris.addItem(e);
            jcbUsuaris.setForeground(Color.CYAN);
            jcbUsuaris.setFont(fontTitol);
        }
    }

    /**
     * Metode que registra el controlador encarregat de gestionar les peticions de la gràfica
     * @param e controlador encarregat de la gestio dels jbuttons
     */
    public void registraController(ControllerGrafica e){
        jbSortir.setActionCommand("TANCAR");
        jbSortir.addActionListener(e);
        jbFerGrafica.setActionCommand("FER QUERY");
        jbFerGrafica.addActionListener(e);
    }

    /**
     * Metode que buida la combobox
     */
    public void buidaJCombobox(){
        int itemCount = jcbUsuaris.getItemCount();

        for(int i=0;i<itemCount;i++){
            jcbUsuaris.removeItemAt(0);
        }
        int itemCount2 = jcbModalitat.getItemCount();

        for(int i=0;i<itemCount2;i++){
            jcbModalitat.removeItemAt(0);
        }
    }

    /**
     * Getter del nom de l'usuari seleccionat a la combobox
     * @return retorna el nom de l'usuari seleccionat a la combobox
     */
    public String getComboboxUsuari(){
        return (String)jcbUsuaris.getSelectedItem();
    }

    /**
     * Setter de la modalitat de la combobox amb què generar la gràfica
     * @param modalitat array de modalitats disponibles amb què mostrat la gràfica
     */
    public void setJComboboxModalitat(String[] modalitat){
        Font fontTitol = new Font("OCR A Extended", Font.BOLD,15);
        for(String e :modalitat){
            jcbModalitat.addItem(e);
            jcbModalitat.setForeground(Color.CYAN);
            jcbModalitat.setFont(fontTitol);
        }
    }

    /**
     * Getter de la modalitat seleccionada a la combobox
     * @return int de la modalitat de la combobox (2=versus2, 4=versus4, 0=torneig)
     */
    public int getComboboxModalitat(){
        String modalitat = "";
        modalitat = (String)jcbModalitat.getSelectedItem();
        switch(modalitat){
            case "Mode V2":
                return 2;
            case "Mode V4":
                return 4;
            case "Mode Torneig":
                return 0;
        }
        return -1;
    }

}
