import java.io.*;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class IOArchivo extends UnicastRemoteObject
  implements FileInterface {

   @SuppressWarnings("unused")
   private String name;

   public IOArchivo(String s) throws RemoteException{
      super();
      name = s;
   }

   @Override
   public byte[] descargarArchivo(String nombreArchivo) throws RemoteException, Exception{
      try {
         File file = new File(obtenerRuta(nombreArchivo));
         byte buffer[] = new byte[(int)file.length()];

         BufferedInputStream input = new BufferedInputStream(new FileInputStream(obtenerRuta(nombreArchivo)));
         input.read(buffer,0,buffer.length);
         input.close();
         return(buffer);
      } catch(Exception e){
         throw e;
      }
   }

   @Override
   public int cuentaLineas(String nombreArchivo) throws RemoteException, Exception {
      try{
         FileReader fr = new FileReader(obtenerRuta(nombreArchivo));

         BufferedReader br = new BufferedReader(fr);

         int contador = 0;
         @SuppressWarnings("unused")
         String linea;

         while ((linea = br.readLine()) != null) {
            contador++;
         }

         br.close();

         return contador;
      }catch(Exception e){
         throw e;
      }
   }

   @Override
   public HashMap<String, Integer> cuentaVocales(String fileName) throws RemoteException, Exception {
      HashMap<String, Integer> vocales = new HashMap<>();

      vocales.put("a", 0); 
      vocales.put("e", 0); 
      vocales.put("i", 0); 
      vocales.put("o", 0); 
      vocales.put("u", 0); 
      vocales.put("A", 0); 
      vocales.put("E", 0); 
      vocales.put("I", 0); 
      vocales.put("O", 0); 
      vocales.put("U", 0); 

      try{
         FileReader archivo = new FileReader( obtenerRuta(fileName) );

         int caracter;
         String character;

         while ((caracter = archivo.read()) != -1){
            character = String.valueOf( (char) caracter);

            if (!vocales.containsKey(character)){
               continue;
            }

            vocales.put( character,  (vocales.get(character)+1) );
         }

         archivo.close();
         
         return vocales;
      }catch(Exception e){
         throw e;
      }
   }

   @Override
   public void escribe(String nombreArchivo, String nombreArchivoDestino) throws RemoteException, Exception {
      try{
         FileInputStream archivo = new FileInputStream( obtenerRuta(nombreArchivo) );
         FileOutputStream destino = new FileOutputStream( nombreArchivoDestino );

         byte[] buffer = new byte[1024];
         int length;

         while ((length = archivo.read(buffer)) > 0) {
            destino.write(buffer, 0, length);
         }

         archivo.close();
         destino.close();

      }catch(Exception e){
         throw e;
      }
   }

   @Override
   public ArrayList<String> imprimir(String nombreArchivo) throws RemoteException, Exception {
         try{
            String linea;
            ArrayList<String> contenido = new ArrayList<>();

            BufferedReader archivo = new BufferedReader( new FileReader(obtenerRuta(nombreArchivo)) );

            while ((linea = archivo.readLine()) != null){
               contenido.add(linea);
            }

            archivo.close();

            return contenido;
         }catch(Exception g){
            throw g;
         }
   }

   @Override
   public void respaldar(String nombreArchivo) throws RemoteException, Exception {
      String nombreArchivoDestino = "respaldo "+nombreArchivo ;

      while( existeArchivo(nombreArchivoDestino) ){
         nombreArchivoDestino = "respaldo " + nombreArchivoDestino;
      }

      System.out.println("nombre del respaldo: "+nombreArchivoDestino);

      try{
         FileInputStream inputStream = new FileInputStream(obtenerRuta(nombreArchivo));
         FileOutputStream outputStream = new FileOutputStream(obtenerRuta(nombreArchivoDestino));
         
         byte[] buffer = new byte[1024];
         int length;

         while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
         }

         inputStream.close();
         outputStream.close();

      }catch(Exception e){
         throw e;
      } 
   }

   @Override
   public void copiar(String nombreArchivo, String nombreArchivoDestino) throws RemoteException, Exception {

      if(existeArchivo(nombreArchivoDestino)){
         throw new Exception("El archivo "+nombreArchivoDestino+" ya existe!");
      }

      try{
         FileInputStream inputStream = new FileInputStream(obtenerRuta(nombreArchivo));
         FileOutputStream outputStream = new FileOutputStream(obtenerRuta(nombreArchivoDestino));
         
         byte[] buffer = new byte[1024];
         int length;

         while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
         }

         inputStream.close();
         outputStream.close();

      }catch(Exception e){
         throw e;
      } 
   }

   @Override
   public void renombrar(String nombreArchivo, String nuevoNombre) throws RemoteException, Exception {
      File original = new File(obtenerRuta(nombreArchivo));
      File renombrado = new File(obtenerRuta(nuevoNombre));

      if (!existeArchivo(nombreArchivo)){
         throw new Exception();
      }

      if(! original.renameTo(renombrado) ){
         throw new Exception();
      }
   }

   @Override
   public void eliminar(String nombreArchivo) throws RemoteException, Exception {
      File archivo = new File(obtenerRuta(nombreArchivo));

      if (! archivo.delete()){
         throw new Exception();
      }
   }

   @Override
   public boolean existeArchivo(String nombreArchivo) throws RemoteException {
      return (new File(obtenerRuta(nombreArchivo)).exists());
   }

   @Override
   public File[] listarArchivos() throws RemoteException {
      File archivos = new File(obtenerRuta(""));
      File[] res = archivos.listFiles();
      
      for (File a : res){
         System.out.println(a);
      }
      return archivos.listFiles();
   }

   public String obtenerRuta(String nombreArchivo){
      if (nombreArchivo.isEmpty()){
         return "./archivos";
      }

      return "./archivos" + "/" + nombreArchivo;
   }
}