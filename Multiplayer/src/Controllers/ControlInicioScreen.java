package Controllers;

import Models.Usuario_model;
import Views.Inicio_view;
import Views.Main_window;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ControlInicioScreen implements MouseListener
{
    private Inicio_view inicio;
    private Main_window mainWindow;
    private ArrayList<Usuario_model> resultados;
    private JPanel[] panels;
    private Usuario_model user;
    private Controller ctrl;
    
    public ControlInicioScreen()
    {
        mainWindow = new Main_window();
        ctrl = new Controller();
    }
    
    public void openWindow(Usuario_model u)
    {               
        inicio = new Inicio_view();
        user = u;
        
        inicio.getMeuNome().setText("ID: " + user.getNome());
        inicio.getMeusTrofeus().setText("Troféus: " + user.getTrofeus());
        resultados = ctrl.getListaUsuarios();
        if(!resultados.isEmpty())
        {
            inicio.desenha(resultados);

            this.addMouseListeneres();
        }
        mainWindow.setPanel(inicio); 
    }
    
    public void addMouseListeneres()
    {
        this.panels = inicio.getPanels();
        for(JPanel jp:panels)
        {
            jp.addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        for(int i = 0; i < panels.length; i++)
        {
            if(e.getSource() == panels[i])
            {
                ctrl.setJogar(i);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {}

    @Override
    public void mouseReleased(MouseEvent e)
    {}

    @Override
    public void mouseEntered(MouseEvent e)
    {
        for(JPanel pn: panels)
        {
            if(e.getSource() == pn)
            {
                pn.setBackground(new Color(216,216,216));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        for(JPanel pn: panels)
        {
            if(e.getSource() == pn)
            {
                pn.setBackground(new Color(237, 237, 237));
            }
        }
    }
}
