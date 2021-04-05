package Client;
//This class connects to the server and accepts commands from the user.

import Shared.ClientCallback;
import Shared.ServerInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Client implements Serializable, ClientCallback {

    public Client() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }
    public void runServer(String[] args) {

        String clientpath;
        String serverpath;
        String upload = "upload";
        String download = "download";
        String dir= "dir";
        String mkdir= "mkdir";
        String rmdir= "rmdir";
        String rm= "rm";
        String shutdown= "shutdown";


        try{

            Registry myreg = LocateRegistry.getRegistry("localhost", 1099);
            ServerInterface inter = (ServerInterface)myreg.lookup("remoteObject");

            //to upload a file
            if(upload.equals(args[0]))
            {
                clientpath= args[1];
                serverpath = "C://Server/" + args[2];

                File clientpathfile = new File(clientpath);
                byte [] mydata = new byte[(int) clientpathfile.length()];
                FileInputStream in = new FileInputStream(clientpathfile);
                System.out.println("uploading to server...");
                in.read(mydata, 0, mydata.length);
                inter.uploadFileToServer(mydata, serverpath, (int) clientpathfile.length(),this);

                in.close();
            }
            //to download a file
            if(download.equals(args[0]))
            {
                serverpath = "C://Server/" + args[1];
                clientpath= args[2];

                byte [] mydata = inter.downloadFileFromServer(serverpath,this);
                System.out.println("downloading...");
                File clientpathfile = new File(clientpath);
                FileOutputStream out=new FileOutputStream(clientpathfile);
                out.write(mydata);
                out.flush();
                out.close();
            }

            //to list all the files in a directory
            if(dir.equals(args[0]))
            {
                serverpath = "C://Server/";
                inter.listFiles(serverpath,this);
            }

            //to create a new directory
            if(mkdir.equals(args[0]))
            {
                serverpath = "C://Server/" + args[1];
                inter.createDirectory(serverpath,this);
            }

            //to remove/delete a directory
            if(rmdir.equals(args[0]) || rm.equals(args[0]))
            {
                serverpath = "C://Server/" + args[1];
                inter.removeDirectoryOrFile(serverpath, this);
            }
            //to shutdown the client
            if(shutdown.equals(args[0]))
            {
                System.out.println("Client has shutdown");
                System.exit(0);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Connection error.!");
            System.exit(0);
        }
    }

    public static void main(String[] args) throws RemoteException {
        Client obj = new Client();
        obj.runServer(args);
    }


    @Override
    public void Result(String[] result) throws RemoteException {
        for (String i: result)
        {
            System.out.println(i);
        }
        System.exit(0);
    }
}
