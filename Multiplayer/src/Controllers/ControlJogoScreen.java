package Controllers;

import Models.Usuario_model;
import Views.Jogo_view;
import Views.Main_window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControlJogoScreen implements MouseListener
{
    private Jogo_view jogo;
    private Main_window mainWindow;
    private Usuario_model user;
    private Usuario_model inimigo;
    private Controller ctrl;
    private boolean deQuemEaVez; 
    private String minhaPeca;
    private int[] pecas;    
    private int gameId;

    public ControlJogoScreen()
    {
        mainWindow = new Main_window();
        ctrl = new Controller();        
    }
    
    public void openWindow(Usuario_model u, String peca, int gameId)
    {        
        jogo = new Jogo_view();
        user = u;
        this.gameId = gameId;        
        this.pecas = new int[9];
        this.minhaPeca = peca;
        
        inimigo = ctrl.getInimigo();
        jogo.getMeuId().setText("Me: " + user.getNome());
        if(peca.equals("X"))
        {
            jogo.getJogadorXnome().setText(user.getNome() + ": ");
            jogo.getJogadorXtrofeus().setText("Troféus: " + user.getTrofeus());
            
            jogo.getJogadorYnome().setText(inimigo.getNome() + ": ");
            jogo.getJogadorYtrofeus().setText("Troféus: " + inimigo.getTrofeus());
            deQuemEaVez = true;
        } else {
            jogo.getJogadorYnome().setText(user.getNome() + ": ");
            jogo.getJogadorYtrofeus().setText("Troféus: " + user.getTrofeus());
            
            jogo.getJogadorXnome().setText(inimigo.getNome() + ": ");
            jogo.getJogadorXtrofeus().setText("Troféus: " + inimigo.getTrofeus());
            deQuemEaVez = false;
        }
        
        addMouseListeners();
        mainWindow.setPanel(jogo);
    }

    private void addMouseListeners()
    {
        for(int i = 0; i < jogo.getQuadrados().length; i++)
        {
            jogo.getQuadrados()[i].addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        for(int i = 0; i < jogo.getQuadrados().length; i++)
        {
            if(e.getSource() == jogo.getQuadrados()[i])
            {
                if(deQuemEaVez)
                {
                    if(pecas[i] == 0)
                    {
                        jogo.getQuadrados()[i].setText(minhaPeca);
                        deQuemEaVez = false;

                        int tag;
                        if(minhaPeca.equals("X"))
                        {
                            tag = 1;
                            jogo.getTurn().setText("Turn: O");
                        } else {
                            tag = 2;
                            jogo.getTurn().setText("Turn: X");
                        }                        
                        pecas[i] = tag;
                        
                        ctrl.marcarPosicao(gameId, user.getId(), minhaPeca, i);
                        verificaSeEuGanhei();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void atualizarJogo(int[] quadrados)
    {
        this.pecas = quadrados;
        for(int i = 0; i < pecas.length; i++)
        {
            String peca = "";
            if(pecas[i] == 1) 
            {   
                peca = "X";
            } else if(pecas[i] == 2){
                peca = "O";
            }

            jogo.getQuadrados()[i].setText(peca);
        }

        jogo.getTurn().setText("Turn: " + minhaPeca);
        
        verificaSeEuPerdi();
        deQuemEaVez = true;
    }

    private void verificaSeEuGanhei()
    {
        int tag;
        if(minhaPeca.equals("X")) tag = 1;
        else tag = 2;
        
        if(pecas[0] == tag && pecas[1] == tag && pecas[2] == tag)
        {
            incrementarTrofeus();
        } else if(pecas[3] == tag && pecas[4] == tag && pecas[5] == tag){
            incrementarTrofeus();
        } else if(pecas[6] == tag && pecas[7] == tag && pecas[8] == tag){
            incrementarTrofeus();
        } else if(pecas[0] == tag && pecas[3] == tag && pecas[6] == tag){
            incrementarTrofeus();
        } else if(pecas[1] == tag && pecas[4] == tag && pecas[7] == tag){
            incrementarTrofeus();
        } else if(pecas[2] == tag && pecas[5] == tag && pecas[8] == tag){
            incrementarTrofeus();
        } else if(pecas[0] == tag && pecas[4] == tag && pecas[8] == tag){
            incrementarTrofeus();
        } else if(pecas[2] == tag && pecas[4] == tag && pecas[6] == tag){
            incrementarTrofeus();
        } else {
            verificaEmpate();
        }
    }
    
    private void incrementarTrofeus()
    {
        mainWindow.mostrarMensagem("Você Ganhou!");
        ctrl.setTag(0);
        ctrl.incrementarTrofeus(gameId, user.getId());
    }

    private void verificaSeEuPerdi()
    {
        int tag;
        if(minhaPeca.equals("X")) tag = 2;
        else tag = 1;
        
        if(pecas[0] == tag && pecas[1] == tag && pecas[2] == tag)
        {
            decrementarTrofeus();
        } else if(pecas[3] == tag && pecas[4] == tag && pecas[5] == tag){
            decrementarTrofeus();
        } else if(pecas[6] == tag && pecas[7] == tag && pecas[8] == tag){
            decrementarTrofeus();
        } else if(pecas[0] == tag && pecas[3] == tag && pecas[6] == tag){
            decrementarTrofeus();
        } else if(pecas[1] == tag && pecas[4] == tag && pecas[7] == tag){
            decrementarTrofeus();
        } else if(pecas[2] == tag && pecas[5] == tag && pecas[8] == tag){
            decrementarTrofeus();
        } else if(pecas[0] == tag && pecas[4] == tag && pecas[8] == tag){
            decrementarTrofeus();
        } else if(pecas[2] == tag && pecas[4] == tag && pecas[6] == tag){
            decrementarTrofeus();
        } else {
            verificaEmpate();
        }        
    }
    
    private void decrementarTrofeus()
    {
        ctrl.setTag(0);
        mainWindow.mostrarMensagem("Você Perdeu!");
    }
    
    private void verificaEmpate()
    {
        int cont = 0;
        for(int i = 0; i < pecas.length; i++)
        {
            if(pecas[i] == 0)
            {
                cont++;
                break;
            }
        }
        
        if(cont == 0)
        {
            ctrl.aumentarTrofeusPorEmpate(gameId, user.getId());
            ctrl.setTag(0);
            mainWindow.mostrarMensagem("Empatou!");
        }
    }

    public void avisarQueInimigoAbandonou()
    {
        mainWindow.mostrarMensagem("Inimigo correu! Você venceu!");
    }
}
