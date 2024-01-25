package agenciaviatges;
//import agenciaviatges.altresutilitats.Validacions;
//import agenciaviatges.utilitats.Validacions.*;
//import static agenciaviatges.altresutilitats.Validacions.validarEmail;

import static agenciaviatges.basedades.AccessBDAgencia.crear_factura;
import static agenciaviatges.basedades.AccessBDAgencia.creem_imprimim_linies_factura;
import static agenciaviatges.basedades.AccessBDAgencia.esta_facturada;
import static agenciaviatges.basedades.AccessBDAgencia.facturar_i_imprimir;
import static agenciaviatges.basedades.AccessBDAgencia.facturar_i_imprimir_amb_ofertes;
import static agenciaviatges.basedades.AccessBDAgencia.imprimir_factura;
import static agenciaviatges.basedades.AccessBDAgencia.marcar_comanda_facturada;
import static agenciaviatges.basedades.AccessBDAgencia.obtenir_comanda;
import static agenciaviatges.basedades.AccessBDAgencia.obtenir_conexio_BD;
import static agenciaviatges.basedades.AccessBDAgencia.obtenir_dades_client;
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
public class AgenciaViatgesMODULARfacturacio_1_1{
    
    // FEM AMB UNA SOLA SELECT
    
    // Definim les dades de l'empresa com a constant
    
       
    
    public static void main(String[] args) {
        // En aquesta versió accedim a comanda per veure si esta facturat
        
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
                        System.out.println("\n Has seleccionat FACTURAR COMANDA.\n\nIntrodueix NUMERO DE COMANDA: ");
                        int comanda = teclat.nextInt();
        
                        if (facturar_i_imprimir_amb_ofertes(comanda)) System.out.println("La comanda ha segut facturada correctament");
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
 
    private static boolean facturar (int comanda){
        String[] dadesClient= new String[5]; //posicions: 0->nom ,1->cognom1 ,2->cognom2 , 3-> telefon ,4-> email
        String[] dadesComanda = new String[3]; //posicions: 0->dni ,1-> facturada,2-> , 3-> ,4-> ,5->
        // realitzem la connexió amb la base de dades
        Connection con = obtenir_conexio_BD();       
        //comprovem si la connexió ha funcionat o no
        if  (con!=null){   
            // comprovem que existeix la comanda
            if (obtenir_comanda(con,comanda,dadesComanda)){ 
                // en dadesComanda[0] tinc el dni
                // en dadesComanda[1] tinc la informació de si la comanda ha estat facturada o no
                
                //Ara hem de comprovar si la comanda ha estat facturada o no
                //System.out.println("dadesComanda[1]:"+ dadesComanda[1]);
                if (dadesComanda[1].equals("no facturada")){
                    //Ara hem de comprovar si el DNI és correcte
                    String dni=dadesComanda[0];
                    if (validarDNI(dni)){
                        // obtenim les dades del client per validar el seu email
                        if (obtenir_dades_client(con,dni,dadesClient)){
                            ////tinc en dadesClient posicion: 0->nom ,1->cognom1 ,2->cognom2 , 3-> telefon ,4-> email
                            //System.out.println("email: " + dadesClient[4] );
                            if (validarEmail(dadesClient[4])){
                                int factura=crear_factura(con,comanda,dni);
                                if (factura!=-1){
                                    if (marcar_comanda_facturada(con,comanda)){
                                        if (!(imprimir_factura(con,factura,comanda,dadesClient))){
                                            System.out.println("Error a l'imprimir factura");
                                        }
                                    }else{
                                        System.out.println("No s'ha pogut marcar la comanda com a facturada");
                                        return false;
                                    }
                                }else{
                                    System.out.println("No s'ha pogut crear la factura");
                                    return false;
                                }
                            } else {
                                System.out.println("No es pot facturar, format email incorrecte");
                                return false;
                            } 
                        } else {
                            System.out.println("No s'han pogut obtenir les dades del client");
                            return false;
                        }
                    }else{
                        System.out.println("No es pot facturar, DNI incorrecte");
                        return false;
                    } 
                } else {
                    System.out.println("Aquesta comanda ja ha estat facturada, no és pot tornar a facturar");
                    return false;
                }
            } else {
                System.out.println("Aquesta comanda, no existeix");
                return false;
            }
        } else {
            System.out.println("No s'ha pogut establir la connexió a la Base de Dades ");
            return false;
        }
        return true;
    }
}
    
/*    
    private static boolean facturarv2 (int comanda){
        String[] dadesClient= new String[5]; //posicions: 0->nom ,1->cognom1 ,2->cognom2 , 3-> telefon ,4-> email
        String[] dadesComanda = new String[3]; //posicions: 0->dni ,1-> facturada,2-> , 3-> ,4-> ,5->
        // realitzem la connexió amb la base de dades
        Connection con = obtenir_conexio_BD();       
        //comprovem si la connexió ha funcionat o no
        if  (con!=null){   
            // comprovem que existeix la comanda
            if (facturar_i_imprimir (int comanda){)){ 
                // en dadesComanda[0] tinc el dni
                // en dadesComanda[1] tinc la informació de si la comanda ha estat facturada o no
                if (!(imprimir_factura(con,factura)){
                    System.out.println("Error a l'imprimir factura");
                }                        
            }
        } 
        return true;             
    }
} 
                //Ara hem de comprovar si la comanda ha estat facturada o no
                //System.out.println("dadesComanda[1]:"+ dadesComanda[1]);
                if (dadesComanda[1].equals("no facturada")){
                    //Ara hem de comprovar si el DNI és correcte
                    String dni=dadesComanda[0];
                    if (validarDNI(dni)){
                        // obtenim les dades del client per validar el seu email
                        if (obtenir_dades_client(con,dni,dadesClient)){
                            ////tinc en dadesClient posicion: 0->nom ,1->cognom1 ,2->cognom2 , 3-> telefon ,4-> email
                            //System.out.println("email: " + dadesClient[4] );
                            if (validarEmail(dadesClient[4])){
                                int factura=crear_factura(con,comanda,dni);
                                if (factura!=-1){
                                    if (marcar_comanda_facturada(con,comanda)){
                                        if (!(imprimir_factura(con,factura,comanda,dadesClient))){
                                            System.out.println("Error a l'imprimir factura");
                                        }
                                    }else{
                                        System.out.println("No s'ha pogut marcar la comanda com a facturada");
                                        return false;
                                    }
                                }else{
                                    System.out.println("No s'ha pogut crear la factura");
                                    return false;
                                }
                            } else {
                                System.out.println("No es pot facturar, format email incorrecte");
                                return false;
                            } 
                        } else {
                            System.out.println("No s'han pogut obtenir les dades del client");
                            return false;
                        }
                    }else{
                        System.out.println("No es pot facturar, DNI incorrecte");
                        return false;
                    } 
                } else {
                    System.out.println("Aquesta comanda ja ha estat facturada, no és pot tornar a facturar");
                    return false;
                }
            } else {
                System.out.println("Aquesta comanda, no existeix");
                return false;
            }
        } else {
            System.out.println("No s'ha pogut establir la connexió a la Base de Dades ");
            return false;
        } */
        

