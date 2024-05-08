import java.io.File;
import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface FileInterface extends Remote {
   public byte[] descargarArchivo(String nombreArchivo) throws RemoteException, Exception;

   public int cuentaLineas(String nombreArchivo) throws RemoteException, Exception;

   public HashMap<String, Integer> cuentaVocales(String nombreArchivo) throws RemoteException, Exception;

   public void escribe(String nombreArchivo, String nombreArchivoDestino) throws RemoteException, Exception;

   public ArrayList<String> imprimir(String nombreArchivo) throws RemoteException, Exception;

   public void respaldar(String nombreArchivo) throws RemoteException, Exception;

   public void copiar(String nombreArchivo, String nombreArchivoDestino) throws RemoteException, Exception;

   public void renombrar(String nombreArchivo, String nuevoNombre) throws RemoteException, Exception;

   public void eliminar(String nombreArchivo) throws RemoteException, Exception;

   public boolean existeArchivo(String nombreArchivo) throws RemoteException;

   public File[] listarArchivos() throws RemoteException;
   
}