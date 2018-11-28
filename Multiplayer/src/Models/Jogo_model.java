package Models;

public class Jogo_model
{
    private int id;
    private int quadrados[];
    private Usuario_model jogadorX;
    private Usuario_model jogadorY;
    private boolean gameEnded;
    
    public Jogo_model(int id, Usuario_model jogadorX, Usuario_model jogadorY)
    {
        this.id = id;
        this.jogadorX = jogadorX;
        this.jogadorY = jogadorY;
        quadrados = new int[9];
        gameEnded = false;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void marcarPosicao(int pos, int jogador)
    {
        quadrados[pos] = jogador;
    }
    
    public String getJogadorXId()
    {
        return this.jogadorX.getId();
    }
    
    public String getJogadorYId()
    {
        return this.jogadorY.getId();
    }

    public int[] getQuadrados()
    {
        return quadrados;
    }

    public String getJogadorYNome()
    {
        return this.jogadorY.getNome();
    }
    
    public String getJogadorXNome()
    {
        return this.jogadorX.getNome();
    }
}
