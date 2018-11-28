package Server;

import Models.Jogo_model;
import Models.Usuario_model;
import java.util.ArrayList;

public class BaseDadosServidor
{
    private static ArrayList<Usuario_model> usuariosOnline;
    private static ArrayList<Jogo_model> jogos;
    private static int idsJogos;
    private static int idsUsers;
    
    public BaseDadosServidor()
    {
        usuariosOnline = new ArrayList<>();
        jogos = new ArrayList<>();
        idsJogos = 0;
        idsUsers = 0;
    }
    
    public int getUserArraySize()
    {
        return this.usuariosOnline.size();
    }
    
    public void addUsuario(Usuario_model user)
    {
        usuariosOnline.add(user);
        
        ArrayList usuario = new ArrayList<>();
        usuario.add("usuario");
        usuario.add(user.getId());
        usuario.add(user.getNome());
        usuario.add(user.getTrofeus());
    
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            if(!verificaSeEstaJogando(usuariosOnline.get(i).getId()))
            {
                ArrayList u = new ArrayList<>();
                u.add(usuariosOnline.get(i).getId());
                u.add(usuariosOnline.get(i).getNome());
                u.add(usuariosOnline.get(i).getTrofeus());

                usuario.add(u);
            }
        }
        
        idsUsers++;
        user.getSender().enviarMsg(usuario);
        this.enviarListaUsuarios(user.getId());
    }
    
    public void enviarListaUsuarios(String id)
    {   
        ArrayList usuarios = new ArrayList<>();
        usuarios.add("lista");
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            ArrayList u = new ArrayList<>();
            if(!verificaSeEstaJogando(usuariosOnline.get(i).getId()))
            {
                u.add(usuariosOnline.get(i).getId());
                u.add(usuariosOnline.get(i).getNome());
                u.add(usuariosOnline.get(i).getTrofeus());

                usuarios.add(u);
            }
        }
        
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            if(!usuariosOnline.get(i).getId().equals(id))
            {
                Usuario_model u = usuariosOnline.get(i);
                u.getSender().enviarMsg(usuarios);                
            }
        }
    }

    public void removerUsuario(String id)
    {       
        for(int i = 0; i < jogos.size(); i++)
        {
            String idX = jogos.get(i).getJogadorXId();
            String idY = jogos.get(i).getJogadorYId();
            if(jogos.get(i).getJogadorXId().equals(id))
            {
                avisarQueInimigoAbandonouPartida(idY);
                incrementarTrofeus(jogos.get(i).getId(), idY);
            } else {
                avisarQueInimigoAbandonouPartida(idX);
                incrementarTrofeus(jogos.get(i).getId(), idX);
            }
        }
        
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            Usuario_model u = usuariosOnline.get(i);
            if(u.getId().equals(id))
            {
                usuariosOnline.remove(i);
                this.enviarListaUsuarios("");
                break;
            }
        }
    }
    
    private void avisarQueInimigoAbandonouPartida(String id)
    {
        Usuario_model u = buscarUser(id);

        ArrayList abandonoDePartida = new ArrayList<>();
        abandonoDePartida.add("abandonou_partida");
        u.getSender().enviarMsg(abandonoDePartida);        
    }

    public synchronized void criarJogo(String idMeu, String idInimigo)
    {
        if(!verificaSeEstaJogando(idInimigo))
        {        
            Usuario_model usuario = buscarUser(idMeu);
            Usuario_model inimigo = buscarUser(idInimigo);
            Jogo_model jogo = new Jogo_model(idsJogos, usuario, inimigo);
            jogos.add(jogo);

            //enviar dados do inimigo para o usuario
            ArrayList dadosInimigo = new ArrayList<>();
            dadosInimigo.add("comecar_jogo");
            dadosInimigo.add(idsJogos);
            dadosInimigo.add("X");
            dadosInimigo.add(inimigo.getId());
            dadosInimigo.add(inimigo.getNome());
            dadosInimigo.add(inimigo.getTrofeus());
            usuario.getSender().enviarMsg(dadosInimigo);

            //enviar dados do usuario para o inimigo
            ArrayList dadosUsuario = new ArrayList<>();
            dadosUsuario.add("comecar_jogo");
            dadosUsuario.add(idsJogos);
            dadosUsuario.add("O");
            dadosUsuario.add(usuario.getId());
            dadosUsuario.add(usuario.getNome());
            dadosUsuario.add(usuario.getTrofeus());
            inimigo.getSender().enviarMsg(dadosUsuario);

            idsJogos++;

            this.enviarListaUsuarios("");
        }
    }

    private boolean verificaSeEstaJogando(String id)
    {
        for(int i = 0; i < jogos.size(); i++)
        {
            if(jogos.get(i).getJogadorXId().equals(id) || jogos.get(i).getJogadorYId().equals(id))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private Usuario_model buscarUser(String id)
    {
        Usuario_model u = new Usuario_model("","",0,null);
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            if(usuariosOnline.get(i).getId().equals(id))
            {
                u = usuariosOnline.get(i);
                break;
            }
        }
        return u;
    }

    public void marcarPosicao(int gameId, String idMeu, String peca, int pos)
    {
        for(int i = 0; i < jogos.size(); i++)
        {
            if(jogos.get(i).getId() == gameId)
            {
                // 1 para X
                // 2 para O
                int jogador = 0;
                if(peca.equals("X")) jogador = 1;
                else if(peca.equals("O")) jogador = 2;
                
                jogos.get(i).marcarPosicao(pos, jogador);
                
                ArrayList atualizarJogo = new ArrayList<>();
                atualizarJogo.add("atualizar_jogo");

                for(int j = 0; j < jogos.get(i).getQuadrados().length; j++)
                {
                    atualizarJogo.add(jogos.get(i).getQuadrados()[j]);
                }
                
                if(jogos.get(i).getJogadorXId().equals(idMeu))
                {
                    Usuario_model u2 = buscarUser(jogos.get(i).getJogadorYId());
                    u2.getSender().enviarMsg(atualizarJogo);                    
                } else {
                    Usuario_model u1 = buscarUser(jogos.get(i).getJogadorXId());
                    u1.getSender().enviarMsg(atualizarJogo);   
                }
                
                break;
            }
        }
    }

    public void incrementarTrofeus(int gameId, String idMeu)
    {     
        String idPerdedor = "";
        for(int i = 0; i < jogos.size(); i++)
        {
            if(jogos.get(i).getId() == gameId)
            {
                if(!jogos.get(i).getJogadorXId().equals(idMeu))
                {
                    idPerdedor = buscarUser(jogos.get(i).getJogadorXId()).getId();
                } else {
                    idPerdedor = buscarUser(jogos.get(i).getJogadorYId()).getId();
                }
                jogos.remove(i);
                break;
            }
        }
        
        //acha o vencedor para incrementar trofeus
        int tag = 0;
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            if(usuariosOnline.get(i).getId().equals(idMeu))
            {
                usuariosOnline.get(i).incrementarTrofeus();
                
                ArrayList usuario = new ArrayList<>();
                usuario.add("usuario_atualizar");
                usuario.add(usuariosOnline.get(i).getId());
                usuario.add(usuariosOnline.get(i).getNome());
                usuario.add(usuariosOnline.get(i).getTrofeus());
                usuariosOnline.get(i).getSender().enviarMsg(usuario);
                tag++;
            }
            
            if(usuariosOnline.get(i).getId().equals(idPerdedor))
            {
                usuariosOnline.get(i).decrementarTrofeus();
                
                ArrayList usuario = new ArrayList<>();
                usuario.add("usuario_atualizar");
                usuario.add(usuariosOnline.get(i).getId());
                usuario.add(usuariosOnline.get(i).getNome());
                usuario.add(usuariosOnline.get(i).getTrofeus());
                usuariosOnline.get(i).getSender().enviarMsg(usuario);
                tag++;
            }
            
            if(tag == 2) break;
        }    
        
        enviarListaUsuarios("");
    }

    public void aumentarTrofeusPorEmpate(int gameId, String idMeu)
    {
        // essa função será chamada 2 vezes, os dois usuários que empataram vão usar ela
        // mas o jogo será removido na primeira chamada, na segunda chamada o jogo não existirá mais
        // na segunda chamada ela vai passar por esse for e tag vai continuar 0 pq nao vai encontrar o jogo
        int tag = 0;
        for(int i = 0; i < jogos.size(); i++)
        {
            if(jogos.get(i).getId() == gameId)
            {
                jogos.remove(i);
                tag++;
                break;
            }
        }    
        
        for(int i = 0; i < usuariosOnline.size(); i++)
        {
            if(usuariosOnline.get(i).getId().equals(idMeu))
            {
                usuariosOnline.get(i).incrementarTrofeusParaEmpate();
                
                ArrayList usuario = new ArrayList<>();
                usuario.add("usuario_atualizar");
                usuario.add(usuariosOnline.get(i).getId());
                usuario.add(usuariosOnline.get(i).getNome());
                usuario.add(usuariosOnline.get(i).getTrofeus());
                usuariosOnline.get(i).getSender().enviarMsg(usuario);
            } 
        }
        
        // na segunda chamada é propício atualizar a lista para todos os usuários
        if(tag == 0) enviarListaUsuarios("");
    }

    public int generateId()
    {
        return this.idsUsers;
    }
}
