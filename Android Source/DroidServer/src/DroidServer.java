import java.net.*;
import java.io.*;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kyle
 */
public class DroidServer extends Thread
{
    public static final int SERVERPORT = 4444;
    //public static final int SERVERPORT = 8080;//other possible port
    private ServerSocket serverSocket;
    private Socket sock;
    private boolean running = false;
    
    public static ArrayList<ClientManager> clients = new ArrayList<>();
    public static ArrayList<String> AssignedBays = new ArrayList<>();
    
    public void DroidServer()
    {
        running = true;
        System.out.println("Android Server Started");
        //RunServer();
        
    }
    
    public void run()
    {
        running = true;
        try
        {
            serverSocket = new ServerSocket(SERVERPORT);
            System.out.println("Android Server Started, Ready for Clients");
            
            while(running)
            {
                sock = serverSocket.accept();
                //System.out.println();
                System.out.println("Client Connected : " + sock.getLocalAddress().getHostName());
                
                ClientManager cm = new ClientManager(sock);
                clients.add(cm);
                cm.start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    public void BroadcastStateChange(String bayID, String state)
    {
        
    }
    
    public void BroadcastStateChangeExcluding(String bayID, String state, ClientManager client)
    {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DroidServer dS = new DroidServer();
        dS.start();
        
        // TODO code application logic here
    }
    
}
