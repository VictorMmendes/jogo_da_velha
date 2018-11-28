package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SenderClient
{
    private ObjectOutputStream saida;

    public SenderClient(ObjectOutputStream saida)
    {
        this.saida = saida;
    }
    
    public void removerUsuario(String id)
    {
        try
        {
            ArrayList remocao = new ArrayList<>();
            remocao.add("remover");
            remocao.add(id);
            
            saida.flush();
            saida.writeObject(remocao);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }        
    }

    public void startGame(String meuId, String id_inimigo)
    {
        try
        {
            ArrayList criarJogo = new ArrayList<>();
            criarJogo.add("criar_jogo");
            criarJogo.add(meuId);
            criarJogo.add(id_inimigo);
            
            saida.flush();
            saida.writeObject(criarJogo);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void marcarPosicao(int gameId, String id, String minhaPeca, int pos)
    {
        try
        {
            ArrayList marcarPosicao = new ArrayList<>();
            marcarPosicao.add("marcar_posicao");
            marcarPosicao.add(gameId);
            marcarPosicao.add(id);
            marcarPosicao.add(minhaPeca);
            marcarPosicao.add(pos);
            
            saida.flush();
            saida.writeObject(marcarPosicao);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }        
    }

    public void incrementarTrofeus(int gameId, String id)
    {
        try
        {
            ArrayList incrementarTrofeus = new ArrayList<>();
            incrementarTrofeus.add("incrementar_trofeus");
            incrementarTrofeus.add(gameId);
            incrementarTrofeus.add(id);
            
            saida.flush();
            saida.writeObject(incrementarTrofeus);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }         
    }

    public void aumentarTrofeusPorEmpate(int gameId, String id)
    {
        try
        {
            ArrayList incrementarTrofeus = new ArrayList<>();
            incrementarTrofeus.add("incrementar_trofeus_empate");
            incrementarTrofeus.add(gameId);
            incrementarTrofeus.add(id);
            
            saida.flush();
            saida.writeObject(incrementarTrofeus);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
