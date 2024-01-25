package agenciaviatges;
import java.util.Scanner;
public class AgenciaViatgesArray {
    public static void main(String[] args) {
        //definim les variables necessàries
       Scanner teclat = new Scanner(System.in);
       boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
       int dniInt=0;
       String dniNum="";
       char[] letraDni = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
        };  
       
       // Fem un bucle per llegir l'opció que triï l'usuari i executar-la
       while(!salir){
           System.out.println("\n\n");
           //System.out.println("");
           System.out.println("************ M E N U   P R I N C I P A L **************");
           System.out.println("*                                                     *");
           System.out.println("*   1. Validar DNI                                    *");
           System.out.println("*   2. Opció 2                                        *");
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
                        //VALIDAR DNI
                        boolean dniCorrecte=true;
                        int caracterNum=0;
                        int reste;
                        System.out.println("\n Has seleccionat VALIDAR DNI. Introdueix un DNI");
                        String dni = teclat.next();

                        //primer mirem que tingui 9 digits
                        if (dni.length()!=9){
                            System.out.println("\nEl DNI ha de tenir una longitud 9 caràcters ");
                            dniCorrecte=false;
                        } else {
                            char ult = dni.charAt(dni.length()-1);
                            //mirem que l'ultima posició del DNI sigui una lletra
                            //si es miniscula la passem a majuscula
                            //System.out.println("ult: "+ ult);
                            //si es una lletra minuscula la converteixo en majuscula
                            if (ult>='a' && ult<='z') ult-=32;
                            //System.out.println("ult: "+ ult);
                            if ((ult>='A' && ult<='Z')){
                                 // ara validarem que els 8 primers caràcters són numèrics i l'ultim és una lletra
                                dniNum="";
                                for (int i=0; i<dni.length()-1; i++){
                                     //caracterNum= (int)dni.charAt(i);
                                     //if (caracterNum < 48 || caracterNum > 57){
                                     if (dni.charAt(i) < '0' || dni.charAt(i) > '9'){
                                         System.out.println("\nLes primeres 8 xifres han ser numèriques");
                                         dniCorrecte=false;                            
                                         break;
                                     }
                                     dniNum += dni.charAt(i);    
                                }     
                                if (dniCorrecte){
                                   // convertim el dni a int per poder fer l'operacio modus
                                    dniInt=Integer.parseInt(dniNum);
                                   //comparem la lletra del dni amb la lletra de la taula
                                    reste = dniInt%23;
                                    System.out.println("ult: " + ult);
                                    
                                    System.out.println(reste+ " letraDNI: " + letraDni[reste]);
                                   if (ult!=letraDni[reste]){
                                       System.out.println("\nLa lletra no correspon a aquest DNI");
                                       dniCorrecte=false;
                                   } else {
                                       System.out.println("\nDNI Correcte");
                                   }
                               }
                                
                            } else {
                                System.out.println("\nDNI incorrecte, l'última posició ha de ser una lletra");
                                dniCorrecte=false;
                                        
                            }
                      
                            
                        }
                        System.out.println("\nPrem RETURN per continuar...");
                        teclat.nextLine();
                        teclat.nextLine();

                        break;
                     case 2:
                        System.out.println("Has seleccionado la opcion validar email");
                        
                        
                        
                        
                        
                        
                        
                        
                        break;

                     case 3:
                        System.out.println("Has seleccionado la opcion Facturar una comanda!!");
                        
                        
                        
                        
                        
                        
                        salir=true;
                        break;
                     default:
                        System.out.println("Opció no vàlida");
                }
        }   
    }
  }
}
