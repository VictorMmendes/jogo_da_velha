package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverServer implements Runnable
{
    private BaseDadosServidor bd;
    private ObjectInputStream entrada;
    private boolean finalizarSessao;

    public ReceiverServer(ObjectInputStream entrada, BaseDadosServidor bd)
    {
        this.entrada = entrada;
        this.bd = bd;
        this.finalizarSessao = true;
    }

    @Override
    public void run()
    {
        while(finalizarSessao)
        {
            try
            {
                ArrayList informacoes = (ArrayList) entrada.readObject();
                if(informacoes instanceof ArrayList)
                {
                    if(!informacoes.isEmpty())
                    {
                        String tipo = (String) informacoes.get(0);
                        if(tipo.equals("remover"))
                        {
                            String id = (String) informacoes.get(1);
                            bd.removerUsuario(id);
                            finalizarSessao = false;
                        } else if(tipo.equals("criar_jogo")){
                            String idMeu = (String) informacoes.get(1);
                            String idInimigo = (String) informacoes.get(2);
                            bd.criarJogo(idMeu, idInimigo);
                        } else if(tipo.equals("marcar_posicao")){
                            int gameId = (int) informacoes.get(1);
                            String idMeu = (String) informacoes.get(2);
                            String peca = (String) informacoes.get(3);
                            int pos = (int) informacoes.get(4);
                            
                            bd.marcarPosicao(gameId, idMeu, peca, pos);
                        } else if(tipo.equals("incrementar_trofeus")){
                            int gameId = (int) informacoes.get(1);
                            String idMeu = (String) informacoes.get(2);   
                            
                            bd.incrementarTrofeus(gameId, idMeu);
                        } else if(tipo.equals("incrementar_trofeus_empate")){
                            int gameId = (int) informacoes.get(1);
                            String idMeu = (String) informacoes.get(2);   
                            
                            bd.aumentarTrofeusPorEmpate(gameId, idMeu);                            
                        }
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReceiverServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReceiverServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReceiverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
