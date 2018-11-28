package Views;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Jogo_view extends JPanel
{
    private JLabel jogoDaVelha;
    private JPanel tabuleiro;
    private JButton quadrados[]; 
    
    private JLabel meuId;
    private JLabel turn;
    
    private JPanel jogadorX;
    private JLabel jogadorXtrofeus;
    private JLabel jogadorXpeca;
    private JLabel jogadorXnome;
    private JLabel jogadorXpontos;
    
    private JPanel jogadorY;
    private JLabel jogadorYtrofeus;
    private JLabel jogadorYpeca;
    private JLabel jogadorYnome;
    private JLabel jogadorYpontos;
    
    public Jogo_view()
    {
        this.setSize(400,500);
        this.setLayout(null);
        
        this.jogoDaVelha = new JLabel("Tic Tac Toe");
        jogoDaVelha.setBounds(160, 20, 150, 25);
        this.add(jogoDaVelha); 
        
        this.meuId = new JLabel("Me: user0");
        this.meuId.setFont(new Font("Calibri", Font.BOLD, 11));
        this.meuId.setBounds(50, 50, 150, 25);
        this.add(meuId);
        
        this.turn = new JLabel("Turn: X");
        this.turn.setFont(new Font("Calibri", Font.BOLD, 11));
        this.turn.setBounds(300, 50, 150, 25);
        this.add(turn);
        
        this.tabuleiro = new JPanel();
        this.tabuleiro.setLayout(new GridLayout(3,3,1,1));
        this.tabuleiro.setBounds(50, 75, 300, 300);
        this.quadrados = new JButton[9];
        for(int i = 0; i < 9; i++)
        {
            this.quadrados[i] = new JButton();
            this.tabuleiro.add(quadrados[i]);
        }
        this.add(tabuleiro);
        
        this.jogadorX = new JPanel();
        this.jogadorX.setLayout(new FlowLayout());
        this.jogadorX.setBounds(20,390, 140, 20);
        
        this.jogadorXpeca = new JLabel("X");
        this.jogadorXpeca.setFont(new Font("Calibri", Font.BOLD, 11));
        this.jogadorXnome = new JLabel("Nome: ");
        this.jogadorXnome.setFont(new Font("Calibri", Font.BOLD, 11));
        this.jogadorXpontos = new JLabel("0");
        this.jogadorXpontos.setFont(new Font("Calibri", Font.BOLD, 11));
        
        this.jogadorX.add(jogadorXpeca);
        this.jogadorX.add(jogadorXnome);
        this.jogadorX.add(jogadorXpontos);
        
        this.add(jogadorX);
        
        this.jogadorXtrofeus = new JLabel("Troféus: ");
        this.jogadorXtrofeus.setBounds(54,395, 140, 40);
        this.jogadorXtrofeus.setFont(new Font("Calibri", Font.BOLD, 11));
        this.add(jogadorXtrofeus);
        
        this.jogadorY = new JPanel();
        this.jogadorY.setLayout(new FlowLayout());
        this.jogadorY.setBounds(230,390, 140, 20);

        this.jogadorYpeca = new JLabel("O");
        this.jogadorYpeca.setFont(new Font("Calibri", Font.BOLD, 11));
        this.jogadorYnome = new JLabel("Nome: ");
        this.jogadorYnome.setFont(new Font("Calibri", Font.BOLD, 11));
        this.jogadorYpontos = new JLabel("0");
        this.jogadorYpontos.setFont(new Font("Calibri", Font.BOLD, 11));
        
        this.jogadorY.add(jogadorYpeca);
        this.jogadorY.add(jogadorYnome);
        this.jogadorY.add(jogadorYpontos);
        
        this.add(jogadorY);
        
        this.jogadorYtrofeus = new JLabel("Troféus: ");
        this.jogadorYtrofeus.setBounds(265,395, 140, 40);
        this.jogadorYtrofeus.setFont(new Font("Calibri", Font.BOLD, 11));
        this.add(jogadorYtrofeus);
    }

    public JLabel getJogoDaVelha()
    {
        return jogoDaVelha;
    }

    public JPanel getTabuleiro() 
    {
        return tabuleiro;
    }

    public JButton[] getQuadrados()
    {
        return quadrados;
    }

    public JPanel getJogadorX() 
    {
        return jogadorX;
    }

    public JLabel getJogadorXpeca() 
    {
        return jogadorXpeca;
    }

    public JLabel getJogadorXnome() 
    {
        return jogadorXnome;
    }

    public JLabel getJogadorXpontos() 
    {
        return jogadorXpontos;
    }

    public JPanel getJogadorY() 
    {
        return jogadorY;
    }

    public JLabel getJogadorYpeca() 
    {
        return jogadorYpeca;
    }

    public JLabel getJogadorYnome()
    {
        return jogadorYnome;
    }

    public JLabel getJogadorYpontos() 
    {
        return jogadorYpontos;
    }

    public JLabel getMeuId() 
    {
        return meuId;
    }

    public JLabel getTurn() 
    {
        return turn;
    }

    public JLabel getJogadorXtrofeus()
    {
        return jogadorXtrofeus;
    }

    public JLabel getJogadorYtrofeus() 
    {
        return jogadorYtrofeus;
    }  
}
