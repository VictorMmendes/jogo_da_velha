package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SenderServer
{
    ObjectOutputStream saida;
    
    public SenderServer(Socket socket)
    {
        try
        { 
            saida = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void enviarMsg(ArrayList msg)
    {
        try
        { 
            saida.flush();
            saida.writeObject(msg);
            saida.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
