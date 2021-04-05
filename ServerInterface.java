package Shared;

//This class interfaces directly between the client and the server

import java.rmi.*;

public interface ServerInterface extends Remote {

    void uploadFileToServer(byte[] mybyte, String serverpath, int length, ClientCallback call) throws RemoteException;
    byte[] downloadFileFromServer(String servername, ClientCallback call) throws RemoteException;
    void listFiles(String serverpath, ClientCallback call) throws RemoteException;
    void createDirectory(String serverpath, ClientCallback call) throws RemoteException;
    void removeDirectoryOrFile(String serverpath, ClientCallback call) throws RemoteException;

}