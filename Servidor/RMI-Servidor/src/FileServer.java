import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileServer {
   public static void main(String argv[]) {
      try {
         @SuppressWarnings("unused")
         Registry registry = LocateRegistry.createRegistry(1099);
   
         FileInterface fi = new IOArchivo("FileServer");
         Naming.rebind("//localhost/FileServer", fi);
	      System.out.println("FileServer Stared");
         
      }catch(RemoteException f){
         System.out.println("remote exception: "+f.getMessage());
      }
      catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}