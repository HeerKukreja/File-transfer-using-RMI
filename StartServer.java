package Server;

//This class sets up the server.

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer implements Serializable{

    static String start = "start";

    public static void main(String[] args) {

        try{
            if(start.equals(args[0])) {
                Registry reg = LocateRegistry.createRegistry(1099);   //Creates and exports a Registry instance on the local host that accepts requests
                //on the specified port.

                ServerImpl imp = new ServerImpl("Server");
                reg.bind("remoteObject", imp);
                System.out.println("Server is online....");
            }
        }
        catch(Exception e){
            System.out.println("Server failed: " + e);
        }
    }
}