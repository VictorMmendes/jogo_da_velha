package Views;

import Controllers.Controller;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main_window
{
    private static JFrame window;
    private Controller ctrl;
    
    public Main_window()
    {
        ctrl = new Controller();
        
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 500);
        window.setTitle("Jogo da velha");
        window.setResizable(false);
        
        window.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                e.getWindow().dispose();
                ctrl.removerUsuario();
            }
        });
    }
    
    public void setPanel(JPanel panel)
    {
        window.setContentPane(panel);
        window.setVisible(true); 
    }

    public void mostrarMensagem(String msg)
    {
        JOptionPane.showMessageDialog(window, msg);
    }
}
