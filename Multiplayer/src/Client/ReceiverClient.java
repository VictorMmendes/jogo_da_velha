package Client;

import Controllers.Controller;
import Models.Usuario_model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverClient implements Runnable
{
    private ObjectInputStream entradaServidor;
    private Controller ctrl;

    public ReceiverClient(ObjectInputStream entrada)
    {
        entradaServidor = entrada;
        ctrl = new Controller();
    }
    
    @Override
    public void run()
    {
        while(true)
        {  
            try
            {
                ArrayList obj = (ArrayList) entradaServidor.readObject();
                if(obj instanceof ArrayList)
                {
                    if(!obj.isEmpty())
                    {
                        String tipo = (String) obj.get(0);
                        if(tipo.equals("usuario"))
                        {
                            String id = (String) obj.get(1);
                            String nome = (String) obj.get(2);
                            int trofeus = (int) obj.get(3);
                            Usuario_model user = new Usuario_model(id, nome, trofeus, null);
                            ctrl.setUser(user);
                            
                            ArrayList<Usuario_model> resultados = new ArrayList<>();
                            for(int i = 4; i < obj.size(); i++)
                            {
                                ArrayList u = (ArrayList) obj.get(i);
                                String idU = (String) u.get(0);
                                String nomeU = (String) u.get(1);
                                int trofeusU = (int) u.get(2);
                                if(!idU.equals(user.getId()))
                                {
                                    Usuario_model usuario = new Usuario_model(idU, nomeU, trofeusU, null);
                                    resultados.add(usuario);
                                }
                            }
                            ctrl.setResultados(resultados);
                            
                            ctrl.setInicioVisible();
                        } else if(tipo.equals("lista")){
                            ArrayList<Usuario_model> resultados = new ArrayList<>();
                            for(int i = 1; i < obj.size(); i++)
                            {
                                ArrayList u = (ArrayList) obj.get(i);
                                String idU = (String) u.get(0);
                                String nomeU = (String) u.get(1);
                                int trofeusU = (int) u.get(2);
                                if(!idU.equals(ctrl.getUser().getId()))
                                {
                                    Usuario_model usuario = new Usuario_model(idU, nomeU, trofeusU, null);
                                    resultados.add(usuario);
                                }
                            }
                            ctrl.setResultados(resultados);
                            if(ctrl.getQualViewEstaAtiva() == 0) ctrl.setInicioVisible();
                        } else if(tipo.equals("comecar_jogo")){
                            int idDoJogo = (int) obj.get(1);
                            String minhaPeca = (String) obj.get(2);
                            String id = (String) obj.get(3);
                            String nome = (String) obj.get(4);
                            int trofeus = (int) obj.get(5);                           
                            Usuario_model inimigo = new Usuario_model(id,nome,trofeus,null);
                            ctrl.setInimigo(inimigo);
                            
                            ctrl.setJogoVisible(minhaPeca, idDoJogo);
                        } else if(tipo.equals("atualizar_jogo")){
                            int quadrados[] = new int[9];
                            for(int j = 1; j < 10; j++)
                            {
                                int n = (int) obj.get(j);
                                quadrados[j-1] = n;
                            }
                            
                            ctrl.atualizarJogo(quadrados);
                        } else if(tipo.equals("usuario_atualizar")){
                            String id = (String) obj.get(1);
                            String nome = (String) obj.get(2);
                            int trofeus = (int) obj.get(3);
                            Usuario_model user = new Usuario_model(id, nome, trofeus, null);
                            ctrl.setUser(user);                            
                        } else if(tipo.equals("abandonou_partida")){
                            ctrl.setTag(0);
                            ctrl.avisarQueInimigoAbandonou();
                        }
                    }
                }
                    
                Thread.sleep(100);                
            } catch (InterruptedException ex) {
                Logger.getLogger(ReceiverClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReceiverClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReceiverClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
