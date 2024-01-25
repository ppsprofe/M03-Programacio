package agenciaviatges;
import static agenciaviatges.utilitats.Validacions.*;


import java.util.Scanner;
public class AgenciaViatgesMODULAR{
    
    
    static Scanner teclat = new Scanner(System.in);
    
    public static void main(String[] args) {
        //definim les variables necessàries

       boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
       
       // Fem un bucle per llegir l'opció que triï l'usuari i executar-la
       while(!salir){
           System.out.println("\n\n");
           //System.out.println("");
           System.out.println("************ M E N U   P R I N C I P A L **************");
           System.out.println("*                                                     *");
           System.out.println("*   1. Validar DNI                                    *");
           System.out.println("*   2. Validar Email                                  *");
           System.out.println("*   3. Sortir                                         *");
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
                        System.out.println("\n Has seleccionat VALIDAR DNI. Introdueix un DNI");
                        String dni = teclat.next();
                        
                        if (validarDNI(dni)) System.out.println("DNI correcte");
                        else System.out.println("DNI incorrecte");
                        
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();
                        break;
                     case 2:
                        System.out.println("\n Has seleccionat VALIDAR EMAIL. Introdueix un EMAIL");
                        String email = teclat.next();
        
                        if (validarEmail(email)) System.out.println("Email correcte");
                        else System.out.println("Email incorrecte");
                        
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();
                            
                        break;

                     case 3:
                        System.out.println("Has seleccionado la opcion 3. Adeu!!");
                        salir=true;
                        break;
                     default:
                        System.out.println("Opció no vàlida");
                }
        }   
    }
  }
 
  

}
