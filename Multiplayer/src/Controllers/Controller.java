package Controllers;

import Client.ReceiverClient;
import Client.SenderClient;
import Models.Usuario_model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller
{
    private static ControlInicioScreen ctrlInicio;
    private static ControlJogoScreen ctrlJogo;
    private static SenderClient sender;
    private static Usuario_model user;
    private static Usuario_model inimigo;
    private static Socket client;
    private ObjectOutputStream saida;
    // 0 significa que está na view inicio
    // 1 significa que está na view jogo 
    private static int tag = 0;
    private static ArrayList<Usuario_model> resultados;
    
    public Controller(){} 
    
    public Controller(int tagInicializator)
    {
        ctrlInicio = new ControlInicioScreen();
        ctrlJogo = new ControlJogoScreen();
        resultados = new ArrayList<>();

        try
        {        
            client = new Socket("10.20.24.111", 12345);
              
            saida = new ObjectOutputStream(client.getOutputStream());
            sender = new SenderClient(saida);
            
            ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
            ReceiverClient receiver = new ReceiverClient(entrada);
            Thread receiverRun = new Thread(receiver);
            receiverRun.start();
        } catch (MalformedURLException | RemoteException ex) {} catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUser(Usuario_model user)
    {
        this.user = user;
    }
    
    public void setInimigo(Usuario_model inimigo)
    {
        this.inimigo = inimigo;
    }
    
    public void setResultados(ArrayList<Usuario_model> resultados)
    {
        this.resultados = resultados;
    }

    public int getQualViewEstaAtiva()
    {
        return this.tag;
    }
    
    public void setInicioVisible()
    {
        ctrlInicio.openWindow(user);
        tag = 0;
    }
    
    public void setJogoVisible(String peca, int gameId)
    {
        ctrlJogo.openWindow(user, peca, gameId);
        tag = 1;
    }

    public void removerUsuario()
    {
        sender.removerUsuario(user.getId());
    }

    public void setJogar(int id_inimigo)
    {
        String idInimigo = resultados.get(id_inimigo).getId();
        sender.startGame(user.getId(), idInimigo);
    }

    public ArrayList<Usuario_model> getListaUsuarios()
    {
        return this.resultados;
    }

    public Usuario_model getUser()
    {
        return this.user;
    }

    public Usuario_model getInimigo()
    {
        return this.inimigo;
    }

    public void marcarPosicao(int gameId, String id, String minhaPeca, int pos)
    {
        sender.marcarPosicao(gameId, id, minhaPeca, pos);
    }

    public void atualizarJogo(int[] quadrados)
    {
        ctrlJogo.atualizarJogo(quadrados);
    }

    public void incrementarTrofeus(int gameId, String id)
    {
        sender.incrementarTrofeus(gameId, id);
    }

    public void setTag(int tag)
    {
        this.tag = 0;
    }

    public void aumentarTrofeusPorEmpate(int gameId, String id)
    {
        sender.aumentarTrofeusPorEmpate(gameId, id);
    }

    public void avisarQueInimigoAbandonou()
    {
        ctrlJogo.avisarQueInimigoAbandonou();
    }
}
