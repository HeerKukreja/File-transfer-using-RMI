package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    void Result(String[] result) throws RemoteException;
}
