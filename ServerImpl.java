package Server;

//This class contains implementation of all the functionalities provided to the client.
import Shared.ClientCallback;
import Shared.ServerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface, Serializable{

    protected ServerImpl(String s) throws RemoteException {
        File storageDir = new File ("C://"+s);
        storageDir.mkdir();
    }

    public void uploadFileToServer(byte[] mydata, String serverpath, int length, ClientCallback client) throws RemoteException {

        try {
            File serverpathfile = new File(serverpath);
            FileOutputStream out = new FileOutputStream(serverpathfile);
            byte [] data = mydata;

            out.write(data);
            out.flush();
            out.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){}
        String[] strar = new String[]{"File transfer successful.!"};
        client.Result(strar);

    }

    public byte[] downloadFileFromServer(String serverpath, ClientCallback client) throws RemoteException {

        byte [] mydata;

        File serverpathfile = new File(serverpath);
        mydata=new byte[(int) serverpathfile.length()];
        FileInputStream in;
        try {
            in = new FileInputStream(serverpathfile);
            try {
                in.read(mydata, 0, mydata.length);
            } catch (IOException e) {

                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        return mydata;

    }

    public void listFiles(String serverpath, ClientCallback client) throws RemoteException {
        File serverpathdir = new File(serverpath);
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){}
        client.Result(serverpathdir.list());
    }

    public void createDirectory(String serverpath, ClientCallback client) throws RemoteException {
        File serverpathdir = new File(serverpath);
        String[] strar = new String[]{"Directory created?: " + serverpathdir.mkdir()};
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){}
        client.Result(strar);
    }

    public void removeDirectoryOrFile(String serverpath, ClientCallback client) throws RemoteException {
        File serverpathdir = new File(serverpath);
        String[] strar = new String[]{"Directory removed?: " + serverpathdir.delete()};
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){}
        client.Result(strar);

    }


}