package agenciaviatges;
//import agenciaviatges.altresutilitats.Validacions;
//import agenciaviatges.utilitats.Validacions.*;
//import static agenciaviatges.altresutilitats.Validacions.validarEmail;

import static agenciaviatges.utilitats.Validacions.validarDNI;
import static agenciaviatges.utilitats.Validacions.validarEmail;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.time.LocalDate;


import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AgenciaViatgesMODULARfacturacio{
    
    // Definim les dades de l'empresa com a constant
    
    static final String NOMEMPRESA = "Agència de viatges al vol";
    static final String ADREÇAEMPRESA = "Carrer avió, 12, Lleida";
    static final String TELEFONEMPRESA = "973 23 44 55";
    static final String EMAILEMPRESA = "agencia_al_vol@gmail.com";
    
    static final Double IVA = 16.0;
    
    
    
    
    public static void main(String[] args) {
        //definim les variables necessàries
       Scanner teclat = new Scanner(System.in);
       boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
       /*for (int i=0 ;i<=1000;i++){
           System.out.println( i+": "+ (char)i );
       }
       */
       // Fem un bucle per llegir l'opció que triï l'usuari i executar-la
       while(!salir){
           

           System.out.println("\n\n");
           System.out.println("************ M E N U   P R I N C I P A L **************");
           System.out.println("*                                                     *");
           System.out.println("*   1. Validar DNI                                    *");
           System.out.println("*   2. Validar Email                                  *");
           System.out.println("*   3. Facturar comanda                               *");
           System.out.println("*   4. Sortir                                         *");
           System.out.println("*                                                     *");
           System.out.println("*      Tria una de les opcions:                       *");
           System.out.println("*******************************************************");
           if (!teclat.hasNextInt()){
               System.out.println("Has d'introduir un numero com a opció");
               teclat.next();
           } else {
                opcion = teclat.nextInt();
                switch(opcion){
                    case 1:
                        System.out.println("\n Has seleccionat VALIDAR DNI.\n\nIntrodueix un DNI: ");
                        String dni = teclat.next();
                        
                        if (validarDNI(dni)) System.out.println("DNI correcte");
                        else System.out.println("DNI incorrecte");
                        
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();
                        break;
                     case 2:
                        System.out.println("\n Has seleccionat VALIDAR EMAIL.\n\nIntrodueix un EMAIL: ");
                        String email = teclat.next();
        
                        if (validarEmail(email)) System.out.println("Email correcte");
                        else System.out.println("Email incorrecte");
                        
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();
                            
                        break;
                        
                        
                     case 3:
                        System.out.println("\n Has seleccionat FACURAR COMANDA.\n\nIntrodueix NUMERO DE COMANDA: ");
                        int comanda = teclat.nextInt();
        
                        if (facturar(comanda)) System.out.println("La comanda ha segut facturada correctament");
                        else System.out.println("No s'ha pogut facturar la comnada");
                        
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();
                            
                        break;    

                     case 4:
                        System.out.println("Has seleccionado SORTIR. Adeu!!");
                        salir=true;
                        break;
                     default:
                        System.out.println("Opció no vàlida");
                }
        }   
    }
  }
    
    private static Connection obtenir_conexio_BD(){
        
        Connection conexio = null;
        String servidor = "jdbc:mysql://localhost:3306/";
        String bbdd = "agencia";
        String user = "root";
        String password = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexio= DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaViatgesMODULARfacturacio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AgenciaViatgesMODULARfacturacio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AgenciaViatgesMODULARfacturacio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgenciaViatgesMODULARfacturacio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexio;
    } 
    private static boolean facturar (int comanda){
        String[] dadesClient= new String[5];
        // comprovem si la comanda ja ha estat facturada
        Connection con = obtenir_conexio_BD();
        if (esta_facturada(con,comanda)){
            System.out.println("La comanda ja ha segut facturada");
            return false;
        } else {
            String dni = obtenir_comanda(con,comanda); 
            // comprovem que existeix la comanda
            if (dni!=null){
                if (!validarDNI(dni)){
                    System.out.println("No es pot facturar, DNI incorrecte");
                    return false;
                } else {
                    if (obtenir_dades_client(con,dni,dadesClient)){
                        System.out.println("email: " + dadesClient[4] );
                        if (!validarEmail(dadesClient[4])){
                            System.out.println("No es pot facturar, format email incorrecte");
                            return false;
                        } else {
                            int factura=creem_factura(con,comanda,dni);
                            PrintWriter escriptor = imprimim_capcalera_factura(dadesClient,comanda,factura);
                            if (factura!=-1){
                                System.out.println("escriptor: " + escriptor);
                                if (creem_imprimim_linies_factura(con,escriptor,comanda,factura)) {
                                    //calculem_totals();
                                    //imprimim_factura();
                                    return true;
                                }else {
                                        System.out.println("No s'ha pogut generar la factura");
                                        return false;                                            
                                }
                            } else {
                                System.out.println("No s'ha pogut generar la factura");
                                return false;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

  
    private static String obtenir_comanda(Connection con, int comanda) { 
        
        //Obtenim el DNI del client de la comanda
        String sentenciaSql = "SELECT DNI FROM comanda WHERE idComanda = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, comanda); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             System.out.println(resultat.getString(1));
             return resultat.getString(1);
          }

        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } finally {
             if (sentencia != null)
             try {
              //Debemos cerrar solo cuando hemos leido los datos.
              //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                 sentencia.close();  
              } catch (SQLException sqle) {
                 sqle.printStackTrace();
              }
        }    
        return null;  
    }
    
    private static boolean obtenir_dades_client(Connection con, String dni, String[] dadesClient){
        
        String sentenciaSql = "SELECT nom, cognom1, cognom2, telefon, email FROM client WHERE DNI = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setString(1, dni); 
          ResultSet resultat = sentencia.executeQuery();

          //obtenim les dades del client
          if (resultat.next()){
              
             dadesClient[0]=resultat.getString("nom");
             dadesClient[1]=resultat.getString("cognom1");
             dadesClient[2]=resultat.getString("cognom2");
             dadesClient[3]=resultat.getString("telefon");
             dadesClient[4]=resultat.getString("email");
            
             return true;
          }

        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } finally {
             if (sentencia != null)
             try {
              //Debemos cerrar solo cuando hemos leido los datos.
              //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                 sentencia.close();  
              } catch (SQLException sqle) {
                 sqle.printStackTrace();
              }
        }    
        return false;   
    }
    
    
    private static boolean esta_facturada (Connection con, int comanda){
        //accedim a factura per veure si aquesta comanda ja ha estat facturada
        
        String sentenciaSql = "SELECT idFactura FROM factura WHERE idComanda = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, comanda); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             System.out.println(resultat.getString(1));
             return true;
          } else {
              return false;
          }

        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } finally {
             if (sentencia != null)
             try {
              //Debemos cerrar solo cuando hemos leido los datos.
              //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                 sentencia.close();  
              } catch (SQLException sqle) {
                 sqle.printStackTrace();
              }
        }    
        return false;   
    }
    private static int creem_factura(Connection con, int comanda, String dni ){
        
        //alta_factura();
                
        //per cada producte de factura hem de donar d'alta una linia de factura
        String sentenciaSql = "INSERT INTO factura (DNI, idComanda) VALUES (?, ?)";
        PreparedStatement sentencia = null;
 
        try {
            sentencia = con.prepareStatement(sentenciaSql,RETURN_GENERATED_KEYS);
            sentencia.setString(1, dni);
            sentencia.setInt(2, comanda);
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
               int factura = rs.getInt(1);
               return factura;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
          //Nos aseguramos de cerrar los recursos abiertos
          if (sentencia != null)
            try {
              sentencia.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
        }      

            return -1;
        }

    private static PrintWriter imprimim_capcalera_factura(String[] dadesClient, int comanda, int factura){
        
        // creem el fitxer de factura
        File impfact = new File("C:/Agencia viatges/factures/factura_c"+comanda+ "_"+LocalDate.now()+".txt");
        PrintWriter escriptor=null;
        try {
            escriptor = new PrintWriter(impfact);
            // escrivim dades de l'empresa
            
            escriptor.println("\n                                      FACTURA");
            escriptor.println("\n\nDADES EMPRESA\t\t\t\t\t\t Num. Comanda: "+ comanda);
            escriptor.println("=============\t\t\t\t\t\t Num. Factura: "+ factura);

            escriptor.println(NOMEMPRESA);
            escriptor.println(ADREÇAEMPRESA);
            escriptor.println(TELEFONEMPRESA);
            escriptor.println(EMAILEMPRESA);
            
            //escrivim dades del client
            
            escriptor.println("\nDADES CLIENT\t\t\t\t\t\t Data Factura: "+ LocalDate.now());
            escriptor.println("============");
            //escriptor.println(char);
            escriptor.print(dadesClient[0]);
            escriptor.print(" "+ dadesClient[1]);
            escriptor.println(" "+ dadesClient[2]);
            escriptor.println(dadesClient[3]);
            escriptor.println(dadesClient[4]);
            
            escriptor.println("_________________________________________________________________________________________");
            escriptor.print("Nom producte");
            escriptor.print("\t\t\t Quantitat");
            escriptor.print("\t\t Preu");
            escriptor.println("\t\t\t Total");
            
            escriptor.println("_________________________________________________________________________________________");
            
        } catch (IOException ex) {
            Logger.getLogger(AgenciaViatgesMODULARfacturacio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            e.printStackTrace();
        }
        return escriptor;
    }
    
    
    private static boolean creem_imprimim_linies_factura(Connection con, PrintWriter escriptor,  int comanda, int factura){
        // per cada linia de la comanda insertem una linia de factura

        String sentenciaSql = "SELECT lineaComanda, idproducte, quantitat, preu FROM detallcomanda WHERE idComanda = ?";
        PreparedStatement sentencia = null;
        double totalLinia;
        double totalFactura = 0;
        
        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, comanda); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          while (resultat.next()){
             int lineaFactura=resultat.getInt(1);
             int idProducte=resultat.getInt(2);
             Double quantitat=resultat.getDouble(3);
             Double preu=resultat.getDouble(4);
           
             escriptor.print(obtenir_dades_producte(con,idProducte));
             escriptor.print("\t\t\t "+ quantitat);
             escriptor.print("\t\t " + preu);
             totalLinia=quantitat*preu;
             escriptor.println("\t\t\t " + totalLinia);
             totalFactura+=totalLinia;
             
          }
          
          escriptor.println("\n\n_________________________________________________________________________________________");
          escriptor.println("\n\n\t\t\t\t\t\t\t\t\t Total: "+ totalFactura);
          Double impIVA=totalFactura*IVA/100;
          escriptor.print("\t\t\t\t\t\t\t\t     IVA "+ IVA+"%: "+ impIVA );
          // aqui hem d'obtenir l'oferta
          double desconte = 0;
          escriptor.println("\n\n\t\t\t\t\t\t\t\t     Descuento: " + desconte);
          escriptor.println("\n\n\t\t\t\t\t\t\t\t Total a pagar: "+ (totalFactura+impIVA-desconte));
         } catch (SQLException sqle) {
             sqle.printStackTrace();
             return false;
        } finally {
             escriptor.close();
             if (sentencia != null)
                try {
                 //Debemos cerrar solo cuando hemos leido los datos.
                 //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                    sentencia.close();  
                 } catch (SQLException sqle) {
                    sqle.printStackTrace();
                 }
        }    
        return true;   
    }
    
    
    private static String obtenir_dades_producte(Connection con, int idProducte){

        String sentenciaSql = "SELECT codi FROM producte WHERE idProducte = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, idProducte); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             return resultat.getString("codi");
          }          

        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } finally {
             if (sentencia != null)
             try {
              //Debemos cerrar solo cuando hemos leido los datos.
              //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                 sentencia.close();  
              } catch (SQLException sqle) {
                 sqle.printStackTrace();
              }
        }    
        return null;   
    }
    
       /* private static boolean obtenir_dades_client(Connection con, String dni, String[] dadesClient){
        
        String sentenciaSql = "SELECT nom, cognom1, cognom2, telefon, email FROM clent WHERE DNI = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setString(1, dni); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             dadesClient[0]=resultat.getString("nom");
             dadesClient[1]=resultat.getString("cognom1");
             dadesClient[2]=resultat.getString("cognom2");
             dadesClient[3]=resultat.getString("telefon");
             dadesClient[4]=resultat.getString("email");
            
             return true;
          }

        } catch (SQLException sqle) {
             sqle.printStackTrace();
        } finally {
             if (sentencia != null)
             try {
              //Debemos cerrar solo cuando hemos leido los datos.
              //Si cerramos antes de recorrer el ResultSet, perdemos los datos.
                 sentencia.close();  
              } catch (SQLException sqle) {
                 sqle.printStackTrace();
              }
        }    
        return false;   
     
    }
    */
}
