package net.peer;

public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client();
        Server server = new Server();
        
        System.out.println("Client at " + client.myIp());
        System.out.println("Server at " + server.myIp());
    }
}
