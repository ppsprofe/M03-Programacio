package agenciaviatges;
import java.util.Scanner;
public class AgenciaViatgesINICI{
    public static void main(String[] args) {
        //definim les variables necessàries
       Scanner teclat = new Scanner(System.in);
       boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
      // dniInt=0;
       String dniNum="";
       
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
                        System.out.println("Has seleccionado la opcion VALIDAR DNI");
                        System.out.println("Introdueix dni: ");
                        String dni=teclat.next();
                        boolean dniCorrecte=true;
                        
                        if (dni.length()!=9){
                            System.out.println("El DNI ha de contenir 9 caràctes");
                        } else{
                            //mirem que l'ultim digit tingui una lletra 
                            char ult = dni.charAt(dni.length()-1);
                                                       
                            if (ult>='a' && ult<='z'){
                                //convertim lletra majuscula 
                                ult-=32;
                            }
                            if (ult>='A' && ult<='Z'){
                                // mirem que els primers 8 digits siguin numeros
                                
                                for (int i=0;i<8; i++ ){
                                    if (dni.charAt(i)<'0' || dni.charAt(i)>'9') {
                                        System.out.println("Les 8 primeres posicions han de ser numeriques");
                                        dniCorrecte=false;
                                        break;
                                    }
                                    
                                }
                                
                                if (dniCorrecte) {
                                    //fem validació formula
                                    dni=dni.substring(0,dni.length()-1);
                                    System.out.println("dni: " + dni);
                                    //conversions de dades IMPLICITES i EXPLICITA
                                   
                                    int dniInt = Integer.parseInt(dni);
                                    
                                    int reste= dniInt % 23;
                                    System.out.println("reste"+ reste);
                                    
                                    char lletra;
                                    if (reste==0) lletra='T';
                                    
                                }
                                
                                
                                
                                
                                
                                
                                                                
                            
                            } else {
                                System.out.println("La ultima posicion debe ser una letra");
                            }
                        
                        }
                        
                        
                        
                        break;
                     case 2:
                        System.out.println("Has seleccionado la opcion 2");
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
