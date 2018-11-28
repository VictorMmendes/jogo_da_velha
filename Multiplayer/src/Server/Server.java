package Server;

import Models.Usuario_model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        try
        {      
            BaseDadosServidor bd = new BaseDadosServidor();
            //criando socket para receber novas conexões
            ServerSocket server = new ServerSocket(12345);
            System.out.println("Iniciando servidor na porta " + server.getLocalPort());
            
            while(true)
            {
                //aceitando novos usuários
                Socket socket = server.accept();

                String id = socket.getRemoteSocketAddress().toString();

                // criando novo usuário
                SenderServer sender = new SenderServer(socket);
                Usuario_model user = new Usuario_model(id, "user" + bd.generateId(), 0, sender); 
                System.out.println(user.getId());
                bd.addUsuario(user);
                
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                //cria uma thread que recebe mensagem para todo usuário novo que é criado
                //cada usuário vai ter uma thread Observer no servidor esperando mensagens somente dele
                ReceiverServer receiver = new ReceiverServer(entrada, bd);
                Thread receiverRun = new Thread(receiver);
                receiverRun.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }     
    }
}
