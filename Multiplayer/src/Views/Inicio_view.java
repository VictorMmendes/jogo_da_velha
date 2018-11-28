package Views;

import Models.Usuario_model;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Inicio_view extends JPanel
{
    private JLabel listaUsuarios;
    private JScrollPane scroll;
    private JPanel usuariosAtivos;
    private JLabel meuNome;
    private JLabel meusTrofeus;
    private JPanel usuarios[];
    
    public Inicio_view()
    {
        this.setSize(400,500);
        this.setLayout(null);
        
        this.listaUsuarios = new JLabel("Usuários Online");
        listaUsuarios.setBounds(145, 20, 150, 25);
        this.add(listaUsuarios);
        
        this.meuNome = new JLabel("ID: user_0");
        meuNome.setBounds(25, 20, 150, 25);
        this.add(meuNome);
        
        this.meusTrofeus = new JLabel("Troféus: 23");
        meusTrofeus.setBounds(25, 35, 150, 25);
        this.add(meusTrofeus);
        
        scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setBounds(25, 60, 350, 380);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        
        this.add(scroll);
    }
        
    public void desenha(ArrayList Array)
    {            
        usuariosAtivos = new JPanel();
        usuariosAtivos.setLayout(new GridLayout(0, 1, 0, 0));
        
        if(Array.size() > 0)
        {
            usuarios = new JPanel[Array.size()];
            for(int i = 0; i < Array.size(); i++)
            {
                Usuario_model u = (Usuario_model) Array.get(i);
                usuarios[i] = gerarPanel(u.getNome());
                usuariosAtivos.add(usuarios[i]);
            }
        }
        scroll.setViewportView(usuariosAtivos);
        
        repaint();
    }
    
    public JPanel gerarPanel(String nome)
    {
        JLabel nomeLb;
        JPanel usuario = new JPanel();
                
        usuario.setLayout(new GridLayout(1, 1));        

        nomeLb = new JLabel(nome);
        usuario.add(nomeLb);
        
        return usuario;
    } 
    
    public JPanel[] getPanels()
    {
        return this.usuarios;
    }    

    public JLabel getMeuNome() 
    {
        return meuNome;
    }

    public JLabel getMeusTrofeus() 
    {
        return meusTrofeus;
    }
}
