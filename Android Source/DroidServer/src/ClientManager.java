
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kyle
 */
public class ClientManager extends Thread
{
    
    
    private Socket socky;
    private PrintWriter out = null;
    private BufferedReader in = null;
    
    ClientManager(Socket s)
    {
        socky = s;
    }
    
    @Override
    public void run()
    {
        try
        {
            out = new PrintWriter(socky.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socky.getInputStream()));

            String inputLine, outputLine;
            BusLogic bL = new BusLogic();
            //outputLine = bL.processRequest(null);
            //out.println(outputLine);

            //Read from socket and write back the response to client.
            while ((inputLine = in.readLine()) != null) 
            {
                    outputLine = bL.processRequest(inputLine);
                    if(outputLine != null) 
                    {
                        out.println(outputLine);
                        if (outputLine.equals("exit")) 
                        {
                                System.out.println("Server is closing socket for client:" + socky.getLocalSocketAddress());
                                break;
                        }
                    }
                    else
                    {
                            System.out.println("OutputLine is null!!!");
                    }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        { //In case anything goes wrong we need to close our I/O streams and sockets.
                try {
                        out.close();
                        in.close();
                        socky.close();
                } 
                catch(Exception e) 
                {
                        System.out.println("Failed to close I/O streams with client");
                }
        }

    }
}
