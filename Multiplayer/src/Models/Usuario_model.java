package Models;

import Server.SenderServer;
import java.io.Serializable;

public class Usuario_model implements Serializable
{
    private static final long serialVersionUID = 7503368771944847788L;
    private String id;
    private String nome;
    private int trofeus;
    private SenderServer sender;

    public Usuario_model(String id, String nome, int trofeus, SenderServer sender)
    {
        this.id = id;
        this.nome = nome;
        this.trofeus = trofeus;
        this.sender = sender;
    }
    
    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public SenderServer getSender()
    {
        return sender;
    }

    public void setSenderServer(SenderServer sender)
    {
        this.sender = sender;
    }

    public void incrementarTrofeusParaEmpate()
    {
        this.trofeus++;
    }
    
    public void incrementarTrofeus()
    {
        this.trofeus += 3;
    }

    public int getTrofeus()
    {
        return this.trofeus;
    }

    public void decrementarTrofeus()
    {
        if(trofeus > 2) trofeus -= 3;
    }
}
