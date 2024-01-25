/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenciaviatges.basedades;

import agenciaviatges.AgenciaViatgesMODULARfacturacio;
import static agenciaviatges.utilitats.Validacions.validarDNI;
import static agenciaviatges.utilitats.Validacions.validarEmail;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pilar
 */
public class AccessBDAgencia {
    
    
 
    
    public static Connection obtenir_conexio_BD(){
        
        Connection conexio = null;
        String servidor = "jdbc:mysql://localhost:3306/";
        String bbdd = "agencia";
        String user = "root";
        String password = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexio= DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (Exception e) {
             e.printStackTrace();

        }
        return conexio;
     }

    public static boolean obtenir_comanda(Connection con, int comanda, String[] dadesComanda) { 
        
        //Obtenim el DNI del client de la comanda
        String sentenciaSql = "SELECT DNI,facturada FROM comanda WHERE idComanda = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, comanda); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             //System.out.println(resultat.getString(1));
             dadesComanda[0]=resultat.getString(1);
             if (resultat.getBoolean(2)) dadesComanda[1]="facturada";
             else dadesComanda[1]="no facturada";
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
    
    public static boolean obtenir_dades_client(Connection con, String dni, String[] dadesClient){
        
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
    
    
    public static boolean esta_facturada (Connection con, int comanda){
        //accedim a factura per veure si aquesta comanda ja ha estat facturada
        
        String sentenciaSql = "SELECT idFactura FROM factura WHERE idComanda = ?";
        PreparedStatement sentencia = null;

        try {
          sentencia = con.prepareStatement(sentenciaSql);
          sentencia.setInt(1, comanda); 
          ResultSet resultat = sentencia.executeQuery();

          //Mostramos los datos
          if (resultat.next()){
             //System.out.println(resultat.getString(1));
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
    public static int crear_factura(Connection con, int comanda, String dni ){
        

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

    public static boolean imprimir_factura(Connection con, int factura, int comanda, String[] dadesClient){
        // DEFINICIÓ DE CONSTANTS
        // les dades de l'empresa les definim com a constants, no te sentit modificar-les en el programa
        final String NOMEMPRESA = "Agència de viatges al vol";
        final String ADREÇAEMPRESA = "Carrer avió, 12, Lleida";
        final String TELEFONEMPRESA = "973 23 44 55";
        final String EMAILEMPRESA = "agencia_al_vol@gmail.com";

        final Double IVA = 16.0;
        
        
        // creem el fitxer de factura
        File impfact = new File("C:/Agencia viatges/factures/factura_c"+factura+ "_"+LocalDate.now()+".txt");
        PrintWriter escriptor=null;
        try {
            escriptor = new PrintWriter(impfact);
            
        // escrivim dades de l'empresa
            
            escriptor.println("\n                               FACTURA");
            escriptor.println("\n\nDADES EMPRESA\t\t\t\t Num. Comanda: "+ comanda);
            escriptor.println("=============\t\t\t\t Num. Factura: "+ factura);

                     
            escriptor.println(NOMEMPRESA);
            escriptor.println(ADREÇAEMPRESA);
            escriptor.println(TELEFONEMPRESA);
            escriptor.println(EMAILEMPRESA);
            
            //escrivim dades del client
            
            escriptor.println("\n\nDADES CLIENT\t\t\t\t Data Factura: "+ LocalDate.now());
            escriptor.println("============");
            //escriptor.println(char);
            escriptor.print(dadesClient[0]);
            escriptor.print(" "+ dadesClient[1]);
            escriptor.println(" "+ dadesClient[2]);
            escriptor.println(dadesClient[3]);
            escriptor.println(dadesClient[4]);
            
            escriptor.println("\n\n________________________________________________________________________");

            escriptor.print("Prod");
            escriptor.print("\tQuantitat");
            escriptor.print("\tPreu");
            escriptor.print("\t\tTotal");
            escriptor.println("\t\tNom producte");
            
            escriptor.println("________________________________________________________________________");

            // ESCRIVIM LES LINIES DELS PRODUCTES DE LA COMANDA
            
            //Obtenim les linies de la comanda
            String sentenciaSql = "SELECT lineaComanda, idproducte, quantitat, preu FROM detallcomanda WHERE idComanda = ?";
            PreparedStatement sentencia = null;
            double totalLinia;
            double totalFactura = 0;
        

            sentencia = con.prepareStatement(sentenciaSql);
            sentencia.setInt(1, comanda); 
            ResultSet resultat = sentencia.executeQuery();

            //Mostramos los datos
            while (resultat.next()){
                int lineaFactura=resultat.getInt(1);
                int idProducte=resultat.getInt(2);
                Double quantitat=resultat.getDouble(3);
                Double preu=resultat.getDouble(4);

                escriptor.print(idProducte);
                escriptor.print("\t"+ quantitat);
                escriptor.print("\t\t" + preu);
                totalLinia=quantitat*preu;
                escriptor.print("\t\t" + totalLinia);
                totalFactura+=totalLinia;
                escriptor.println("\t\t"+ obtenir_dades_producte(con,idProducte));

            }

            escriptor.println("\n________________________________________________________________________");
            escriptor.println("\n\n\t\t\t\t\t\t Total: "+ totalFactura);
            Double impIVA=totalFactura*16/100;
            escriptor.print("\t\t\t\t\t     IVA "+ IVA+"%: "+ impIVA );
            // aqui hem d'obtenir l'oferta
            double desconte = 0;
            escriptor.println("\n\n\t\t\t\t\t     Descompte: " + desconte);
            escriptor.println("\n\n\t\t\t\t\t Total a pagar: "+ (totalFactura+impIVA-desconte));
        } catch (Exception e) {
             e.printStackTrace();
             return false;
        } finally {
            escriptor.close();
        }    
        return true;
    }
    
    public static boolean facturar_i_imprimir (int comanda){
        
        // DECLARACIÓ DE CONSTANTS: ADREÇA DE L'EMPRESA
        final String NOMEMPRESA = "Agència de viatges al vol";
        final String ADREÇAEMPRESA = "Carrer avió, 12, Lleida";
        final String TELEFONEMPRESA = "973 23 44 55";
        final String EMAILEMPRESA = "agencia_al_vol@gmail.com";
        final Double IVA = 16.0;

        // DECLARACIO DE VARIABLES
        
        int idComanda=0;
        int idProducte=0; 
        double preu=0;
        double quantitat=0;
        String nomprod = null;
        int idFactura=0;
        String dni=null;
        String nom=null;
        String cognom1=null;
        String cognom2=null;
        String telefon=null;
        String email=null;
        int facturada=0;
        Date datacomanda=null;


        double totalLinia=0;
        double totalFactura = 0;

        Connection con = obtenir_conexio_BD();       
        
        
        String sentenciaSql = "SELECT c.idComanda, dc.idProducte, dc.preu, dc.quantitat, p.codi, c.dni, cl.nom, cl.cognom1, cl.cognom2, cl.telefon, cl.email, c.facturada, c.dataComanda" +
                                "FROM comanda c, detallcomanda dc, client cl, producte p " +
                                "WHERE c.idComanda = ? " +
                                "AND c.idComanda = dc.idComanda " +
                                "AND c.dni=cl.dni " +
                                "AND dc.idProducte = p.idProducte;";
                                



        PreparedStatement sentencia = null;
        PrintWriter escriptor=null;
                        
        try {
            sentencia = con.prepareStatement(sentenciaSql);
            sentencia.setInt(1, comanda); 
            ResultSet resultat = sentencia.executeQuery();


              // llegim el primer registre del resultat per fer les comprovacions
            boolean trobat = resultat.next(); 
            if (!trobat){
                System.out.println("Aquesta comanda no existeix");
                return false;
            }    
            email = resultat.getString(11);
            facturada=resultat.getInt(12);
            dni = resultat.getString(6);
          
            if (facturada==1) {
                System.out.println("La comanda ja està facturada");
                return false;
            }

            if (!validarDNI(dni)){
                System.out.println("DNI incorrecte");
                return false;
            }
            if (!validarEmail(email)){
                System.out.println("Email incorrecte");
                return false;
            }
            //El métode retorna el numero de factura assignat automàticament pel SGBD
            idFactura=crear_factura(con,comanda,dni);
            
            // Si no s'ha pogut crear correctament el registre en factura retornarà -1 i donem error
            if (idFactura==-1) return false;         
            
            // acutalitzem el flag de facturada de comanda
            if (!marcar_comanda_facturada(con,comanda))return false;
            // creem el fitxer de factura
            
            // IMPRIMIM LA FACTURA
            File impfact = new File("C:/Agencia viatges/factures/factura_c"+idFactura+ "_"+LocalDate.now()+".txt");

     
            escriptor = new PrintWriter(impfact);
            
            // escrivim dades de l'empresa
            
            escriptor.println("\n                               FACTURA");
            escriptor.println("\n\nDADES EMPRESA\t\t\t\t Num. Comanda: "+ comanda);
            escriptor.println("=============\t\t\t\t Num. Factura: "+ idFactura);

                     
            escriptor.println(NOMEMPRESA);
            escriptor.println(ADREÇAEMPRESA);
            escriptor.println(TELEFONEMPRESA);
            escriptor.println(EMAILEMPRESA);
            
            //escrivim dades del client
            
            escriptor.println("\n\nDADES CLIENT\t\t\t\t Data Factura: "+ LocalDate.now());
            escriptor.println("============");
            //escriptor.println(char);
            escriptor.print(nom);
            escriptor.print(" "+ cognom1);
            escriptor.println(" "+ cognom2);
            escriptor.println(telefon);
            escriptor.println(email);
            
            escriptor.println("\n\n________________________________________________________________________");

            escriptor.print("Prod");
            escriptor.print("\tQuantitat");
            escriptor.print("\tPreu");
            escriptor.print("\tTotal");
            escriptor.println("\t\tNom producte");
            
            escriptor.println("________________________________________________________________________");
            
  
          
            do{ 

                System.out.println(idProducte = resultat.getInt(2));
                preu = resultat.getDouble(3);
                quantitat= resultat.getDouble(4);
                nomprod= resultat.getString(5);
                datacomanda=resultat.getDate(13);
                
                escriptor.print(idProducte);
                escriptor.print("\t"+ quantitat);
                escriptor.print("\t\t " + preu);
                totalLinia=quantitat*preu;
                escriptor.print("\t" + totalLinia);
                totalFactura+=totalLinia;
                escriptor.println("\t\t" + nomprod);

            } while (resultat.next());

            escriptor.println("\n________________________________________________________________________");
            escriptor.println("\n\n\t\t\t\t\t\t Total: "+ totalFactura);
            Double impIVA=totalFactura*IVA/100;
            escriptor.print("\t\t\t\t\t     IVA "+ IVA+"%: "+ impIVA );
            // aqui hem d'obtenir l'oferta
            double desconte = 0;
            // Obtenir i imprimir tots els descomptes  
            //double totalDescompte = obtenir_descomptes (dataComanda);
            
            sentenciaSql = "SELECT od.idOferta, od.percentatge, o.nomOferta " +
                                "FROM ofertadata, oferta o " +
                                "WHERE od.idOferta = o.idOferta " +
                                "AND ? BETWEEN dataIniciOferta AND dataFiOferta";
            
            sentencia = con.prepareStatement(sentenciaSql);
            sentencia.setDate(1, datacomanda); 
            resultat = sentencia.executeQuery();
            
            while (resultat.next())
                escriptor.println("\n\n\t\t\t\t\t     Descompte: " + desconte);
            
            
            
            
            escriptor.println("\n\n\t\t\t\t\t Total a pagar: "+ (totalFactura+impIVA-desconte));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (escriptor!=null){
                escriptor.close();
            }        
        }    
        return true;
    }
        
     public static boolean facturar_i_imprimir_amb_ofertes(int comanda){
        
        // DECLARACIÓ DE CONSTANTS: ADREÇA DE L'EMPRESA
        final String NOMEMPRESA = "Agència de viatges al vol";
        final String ADREÇAEMPRESA = "Carrer avió, 12, Lleida";
        final String TELEFONEMPRESA = "973 23 44 55";
        final String EMAILEMPRESA = "agencia_al_vol@gmail.com";
        final Double IVA = 16.0;

        // DECLARACIO DE VARIABLES
        
        int idComanda=0;
        int idProducte=0; 
        double preu=0;
        double quantitat=0;
        String nomprod = null;
        int idFactura=0;
        String dni=null;
        String nom=null;
        String cognom1=null;
        String cognom2=null;
        String telefon=null;
        String email=null;
        int facturada=0;
        Date datacomanda=null;


        double totalLinia=0;
        double totalFactura = 0;

        Connection con = obtenir_conexio_BD();       
        
        
        String sentenciaSql = "SELECT c.idComanda, dc.idProducte, dc.preu, dc.quantitat, p.codi, c.dni, cl.nom, cl.cognom1, cl.cognom2, cl.telefon, cl.email, c.facturada, c.dataComanda " +
                                "FROM comanda c, detallcomanda dc, client cl, producte p " +
                                "WHERE c.idComanda = ? " +
                                "AND c.idComanda = dc.idComanda " +
                                "AND c.dni=cl.dni " +
                                "AND dc.idProducte = p.idProducte;";
                                



        PreparedStatement sentencia = null;
        PrintWriter escriptor=null;
                        
        try {
            sentencia = con.prepareStatement(sentenciaSql);
            sentencia.setInt(1, comanda); 
            ResultSet resultat = sentencia.executeQuery();


              // llegim el primer registre del resultat per fer les comprovacions
            boolean trobat = resultat.next(); 
            if (!trobat){
                System.out.println("Aquesta comanda no existeix");
                return false;
            }    
            email = resultat.getString(11);
            facturada=resultat.getInt(12);
            dni = resultat.getString(6);
          
            if (facturada==1) {
                System.out.println("La comanda ja està facturada");
                return false;
            }

            if (!validarDNI(dni)){
                System.out.println("DNI incorrecte");
                return false;
            }
            if (!validarEmail(email)){
                System.out.println("Email incorrecte");
                return false;
            }
            //El métode retorna el numero de factura assignat automàticament pel SGBD
            idFactura=crear_factura(con,comanda,dni);
            
            // Si no s'ha pogut crear correctament el registre en factura retornarà -1 i donem error
            if (idFactura==-1) return false;         
            
            // acutalitzem el flag de facturada de comanda
            if (!marcar_comanda_facturada(con,comanda))return false;
            // creem el fitxer de factura
            
            // IMPRIMIM LA FACTURA
            File impfact = new File("C:/Agencia viatges/factures/factura_c"+idFactura+ "_"+LocalDate.now()+".txt");

     
            escriptor = new PrintWriter(impfact);
            
            // escrivim dades de l'empresa
            
            escriptor.println("\n                               FACTURA");
            escriptor.println("\n\nDADES EMPRESA\t\t\t\t Num. Comanda: "+ comanda);
            escriptor.println("=============\t\t\t\t Num. Factura: "+ idFactura);

                     
            escriptor.println(NOMEMPRESA);
            escriptor.println(ADREÇAEMPRESA);
            escriptor.println(TELEFONEMPRESA);
            escriptor.println(EMAILEMPRESA);
            
            //escrivim dades del client
            
            escriptor.println("\n\nDADES CLIENT\t\t\t\t Data Factura: "+ LocalDate.now());
            escriptor.println("============");
            //escriptor.println(char);
            escriptor.print(nom);
            escriptor.print(" "+ cognom1);
            escriptor.println(" "+ cognom2);
            escriptor.println(telefon);
            escriptor.println(email);
            
            escriptor.println("\n\n________________________________________________________________________");

            escriptor.print("Prod");
            escriptor.print("\tQuantitat");
            escriptor.print("\tPreu");
            escriptor.print("\tTotal");
            escriptor.println("\t\tNom producte");
            
            escriptor.println("________________________________________________________________________");
            
  
          
            do{ 

                System.out.println(idProducte = resultat.getInt(2));
                preu = resultat.getDouble(3);
                quantitat= resultat.getDouble(4);
                nomprod= resultat.getString(5);
                datacomanda=resultat.getDate(13);
                
                escriptor.print(idProducte);
                escriptor.print("\t"+ quantitat);
                escriptor.print("\t\t " + preu);
                totalLinia=quantitat*preu;
                escriptor.print("\t" + totalLinia);
                totalFactura+=totalLinia;
                escriptor.println("\t\t" + nomprod);

            } while (resultat.next());

            escriptor.println("\n________________________________________________________________________");
            escriptor.println("\n\n\t\t\t\t\t\t Total: "+ totalFactura);
            Double impIVA=totalFactura*IVA/100;
            escriptor.println("\t\t\t\t\t     IVA "+ IVA+"%: "+ impIVA );
            // aqui hem d'obtenir l'oferta
            double desconte = 0;
            // Obtenir i imprimir tots els descomptes  
            //double totalDescompte = obtenir_descomptes (dataComanda);
            
            sentenciaSql = "SELECT od.idDataOferta, od.percentatge, o.nomOferta " +
                                "FROM ofertadata od, oferta o " +
                                "WHERE od.idOferta = o.idOferta " +
                                "AND ? BETWEEN dataIniciOferta AND dataFiOferta";
            
            sentencia = con.prepareStatement(sentenciaSql);
            sentencia.setDate(1, datacomanda); 
            resultat = sentencia.executeQuery();
            double percentatgeOferta=0;
            String nomOferta=null;
            double totalDescompte=0;
            
            while (resultat.next()){
                percentatgeOferta = resultat.getDouble(2);
                nomOferta = resultat.getString(3);
                           
                escriptor.println("\t\t\t   Descompte: " + percentatgeOferta + " "+ nomOferta+ " : ");
                totalDescompte+=percentatgeOferta;
            }
            desconte=totalFactura*totalDescompte/100;
            escriptor.println("\n\n\t\t\t\t\t Total descompte " + totalDescompte +  " %: "+ desconte);
            escriptor.println("\n\n\t\t\t\t\t Total a pagar: "+ (totalFactura+impIVA-desconte));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (escriptor!=null){
                escriptor.close();
            }        
        }    
        return true;
    }
    
    public static boolean creem_imprimim_linies_factura(Connection con, PrintWriter escriptor,  int comanda, int factura){
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
             escriptor.print("\t"+ quantitat);
             escriptor.print("\t " + preu);
             totalLinia=quantitat*preu;
             escriptor.println("\t " + totalLinia);
             totalFactura+=totalLinia;
             
          }
          
          escriptor.println("\n\n________________________________________________________________________________");
          escriptor.println("\n\n\t\t\t\t\t\t\t\t\t Total: "+ totalFactura);
          Double impIVA=totalFactura*16/100;
          escriptor.print("\t\t\t\t\t\t\t\t     IVA "+ 16+"%: "+ impIVA );
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
    
    
    public static String obtenir_dades_producte(Connection con, int idProducte){

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
    
    public static boolean marcar_comanda_facturada(Connection con,int comanda){        
        
        
        String sentenciaSql = "UPDATE comanda SET facturada = ? WHERE idComanda = ?";
        PreparedStatement sentencia = null;
 
        try {
            sentencia = con.prepareStatement(sentenciaSql,RETURN_GENERATED_KEYS);
            sentencia.setBoolean(1, true);
            sentencia.setInt(2, comanda);
            sentencia.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
          //Nos aseguramos de cerrar los recursos abiertos
          if (sentencia != null)
            try {
              sentencia.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
        }      

       return true;    
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
   
