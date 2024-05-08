import java.io.*;

import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FileClient{
   public static void main(String argv[]) {
      if(argv.length != 1) {
        System.out.println("Usage: java FileClient machineName");
        System.exit(0);
      }
      try {
         Scanner s = new Scanner(System.in);
         String name = "//" + argv[0] + "/FileServer";
         FileInterface fi = (FileInterface) Naming.lookup(name);
         
         while(true){
            System.out.println("\n------------------------------------");
            System.out.println("1. descargar");
            System.out.println("2. contar lineas");
            System.out.println("3. contar vocales");
            System.out.println("4. escribir");
            System.out.println("5. imprimir");
            System.out.println("6. respaldar");
            System.out.println("7. copiar");
            System.out.println("8. renombrar");
            System.out.println("9. eliminar");
            System.out.println("10. listar archivos");
            System.out.println("11. limpiar");
            System.out.println("<cualquier otra opcion>. salir");
            System.out.println("------------------------------------\n");
            int opcion = 0;
            
            System.out.print("> ");

            try{
               opcion = s.nextInt();
            }catch(InputMismatchException e){
               System.out.println("dato incorrecto.");
               s.nextLine();
               continue;
            }
            
            
            String nombre;

            switch(opcion){
               case 1:
                  s.nextLine();
                  System.out.print("nombre del archivo a descargar: > ");
                  nombre = s.nextLine();
                  FileClient.descargarArchivo(fi, nombre);
                  break;

               case 2:
                  s.nextLine();
                  System.out.print("nombre del archivo: > ");
                  nombre = s.nextLine();
                  FileClient.cuentaLineas(fi, nombre);
                  break;

               case 3:
                  s.nextLine();
                  System.out.print("nombre del archivo: > ");
                  nombre = s.nextLine();
                  FileClient.cuentaVocales(fi, nombre);
                  break;

               case 4:
                  String nombreArchivoLocal;
                  s.nextLine();
                  System.out.print("nombre del archivo: (servidor)  > ");
                  nombre = s.nextLine();

                  System.out.print("nombre del archivo a crear: >"); 
                  nombreArchivoLocal = s.nextLine();

                  FileClient.escribe(fi, nombre, nombreArchivoLocal);
                  break;

               case 5:
                  s.nextLine();

                  System.out.print("nombre del archivo: > ");
                  nombre = s.nextLine();

                  FileClient.imprimir(fi, nombre);
                  break;

               case 6: 
                  s.nextLine();

                  System.out.print("nombre del archivo a respaldar: > ");
                  nombre = s.nextLine();

                  FileClient.respaldar(fi, nombre);
                  break;

               case 7: 
                  s.nextLine();
                  System.out.print("nombre del archivo a copiar: > ");
                  nombre = s.nextLine();

                  s.nextLine();

                  System.out.print("nombre del archivo destino: > ");
                  String nombreCopia = s.nextLine();

                  FileClient.copiar(fi, nombre, nombreCopia);
                  break;

               case 8: 
                  s.nextLine();
                  System.out.print("nombre del archivo a renombrar: > ");
                  nombre = s.nextLine();

                  s.nextLine();

                  System.out.print("nuevo nombre: > ");
                  String nuevoNombre = s.nextLine();

                  FileClient.renombrar(fi, nombre, nuevoNombre);
                  break;

               case 9:
                  s.nextLine();

                  System.out.print("nombre del archivo a eliminar: > ");
                  nombre = s.nextLine();

                  FileClient.eliminar(fi, nombre);
                  break;

               case 10:
                  s.nextLine();
                  listarArchivos(fi);
                  break;

               case 11:
                  s.nextLine();

                  System.out.print("\033[H\033[2J");  
                  System.out.flush(); 
                  break;

               default:
                  s.close();
                  System.exit(0);
            }
         }

      } catch(Exception e) {
         System.err.println("FileServer exception: "+ e.getMessage());
         e.printStackTrace();
      }
   }

   public static void descargarArchivo(FileInterface fi, String nombreArchivo){
      try{
         byte[] filedata = fi.descargarArchivo(nombreArchivo);
         File file = new File("archivos/"+nombreArchivo);
         BufferedOutputStream output = new
           BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
         output.write(filedata,0,filedata.length);
         output.flush();
         output.close();

         System.out.println("Descargado con exito!");

      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void cuentaLineas(FileInterface fi, String nombreArchivo){
      try{
         int lineas = fi.cuentaLineas( nombreArchivo );
         System.out.println("El archivo "+nombreArchivo+"tiene "+lineas+" lineas.");

      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void cuentaVocales(FileInterface fi, String nombreArchivo){
      try{
         HashMap<String, Integer> vocales = fi.cuentaVocales(nombreArchivo);
         
         System.out.println("Vocales: ");
         System.out.println(vocales.toString());
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }
   
   public static void respaldar(FileInterface fi, String nombreArchivo){
      try{
         fi.respaldar(nombreArchivo);
         System.out.println("Respaldado con exito!");
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void copiar(FileInterface fi, String nombreArchivo, String nombreArchivoCopia){
      try{
         fi.copiar(nombreArchivo, nombreArchivoCopia);
         System.out.println("Copiado con exito!");
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void renombrar(FileInterface fi, String nombreArchivo, String nuevoNombre){
      try{
         fi.renombrar(nombreArchivo, nuevoNombre);
         System.out.println("Renombrado con exito!");
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void imprimir(FileInterface fi, String nombreArchivo){
      try{
         ArrayList<String> contenido = fi.imprimir(nombreArchivo);

         for (String linea : contenido){
            System.out.println(linea);
         }

      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void eliminar(FileInterface fi, String nombreArchivo){
      try{
         fi.eliminar(nombreArchivo);

         System.out.println("Eliminado con exito!");
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public static void listarArchivos(FileInterface fi){
      try {
         for ( File archivo : fi.listarArchivos() ){
            System.out.println(archivo.getName());
         }
      } catch (RemoteException e) {
         System.out.println(e.getMessage());
      }
   }

   public static void escribe(FileInterface fi, String nombreArchivo, String nombreArchivoLocal){
      try{
         fi.escribe(nombreArchivo, nombreArchivoLocal);
         
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }
}