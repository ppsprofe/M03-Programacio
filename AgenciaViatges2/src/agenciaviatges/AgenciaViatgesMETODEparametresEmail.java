package agenciaviatges;
import java.util.Scanner;
public class AgenciaViatgesMETODEparametresEmail{
    
    
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
  private static boolean validarDNI(String dni){
  //VALIDAR DNI
       int dniInt=0;
       String dniNum="";
       char[] letraDni = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};  
        boolean dniCorrecte=true;
        int caracterNum=0;


        //primer mirem que tingui 9 digits
        if (dni.length()!=9){
            //System.out.println("\nEl DNI ha de tenir una longitud 9 caràcters ");
            //dniCorrecte=false;
            return false;
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
                         //System.out.println("\nLes primeres 8 xifres han ser numèriques");
                         //dniCorrecte=false;                            
                         //break;
                         return false;
                     }
                     dniNum += dni.charAt(i);    
                }
            } else {
                //System.out.println("\nDNI incorrecte, l'última posició ha de ser una lletra");
                //dniCorrecte=false;
                return false;
            }
            //comparem la lletra del dni amb la lletra de la taula
            //if (dniCorrecte){
                dniInt=Integer.parseInt(dniNum);

                if (ult!=letraDni[dniInt%23]) return false;
               /* {
                    System.out.println("\nLa lletra no correspon a aquest DNI");
                    dniCorrecte=false;
                } else {
                    System.out.println("\nDNI Correcte");
                }
                */
            return true;
            
        }


  }
  
  private static boolean validarEmail(String email){
      // validem que el format del email sigui correcte: tingui una @, mínim un punt i al darrere de l'arroba
      
        
        
        /*Validacions que hem de realitzar l'adreça electrònica
          no pot començar i acabar en @ i punt
               
        */
        boolean emailOK=true;
        int pospriarroba = email.indexOf('@');
        int posultarroba = email.lastIndexOf('@');
        
        if (pospriarroba ==-1 ){
            //System.out.println("\n L'adreça electrònica ha de contenir el caràcter \'@\'");
            return false;
        } else {
            
            if (pospriarroba != posultarroba) {
                 //System.out.println("\n L'adreça electrònica no pot contenir més d'una \'@\'");
                 return false;
            } else {
                
            
            
                String local = email.substring(0,pospriarroba);
                String domExt= email.substring(pospriarroba + 1,email.length());
               
                if (local.length() == 0) {
                    //System.out.println("\n L'adreça electrònica ha de tenir la part LOCAL");
                    return false;
                } else {

                    int pospunt = domExt.indexOf('.');
                    int posultpunt= domExt.lastIndexOf('.');
                    
                    if (pospunt ==-1 ){
                        //System.out.println("\n L'adreça electrònica ha de contenir el caràcter \'.\' seguit de l'EXTENSIO");
                        return false;
                    } else {
                        if (pospunt!=posultpunt){
                            //System.out.println("En l'adreça electrònica no pot haver més d'un punt '.'");
                            return false;
                        } else {
                            String subdomini = domExt.substring(0,pospunt);
                            
                            String extensio = domExt.substring(pospunt+1,domExt.length());
                            
                            if (subdomini.length() == 0)  {  
                                //System.out.println("\n L'adreça electrònica ha de tenir la part de domini'");
                                return false;
                            } else {
                                 if (extensio.length() == 0)  {  
                                    //System.out.println("\n L'adreça electrònica ha de tenir extensió");
                                    return false;
                                 } else {
                                     // l'extensió ha de tenir mínim 2 caràcters
                                     if (extensio.length() < 2) {
                                         //System.out.println("\n L'extensió ha de tenir mínim 2 caràcters");
                                         return false;
                                     } else {


                                        //comprovem que els caràters siguin els correctes en local, domini i extensió 
                                        //primer comprovem en la part LOCAL 
                                        //emailOK=validar_caracters(local);
                                        
                                        
                                        //if (emailOK) emailOK=validar_caracters(subdomini);
                                        //if (emailOK) emailOK=validar_caracters(extensio);
                                        
                                        //if (emailOK) System.out.println("El format de l'adreça electrònica ES correcte");
                                        if (!validar_caracters(local)) return false;
                                        if (!validar_caracters(subdomini)) return false;
                                        if (!validar_caracters(extensio)) return false;
                                        
                                        return true;

                                } 
                            }
                        }
                    }
                }
            }
        }
    }
    
  }
  
  private static boolean  validar_caracters(String str){
      for (int i = 0 ; i < str.length(); i ++){
            char l = str.charAt(i);
            if (!((l>='a'&& l<='z')||(l>='A'&& l<='Z')||(l>='0'&&l<='9')||(l=='-')||(l=='_'))){
         
                //System.out.println("\nL'adrecça electrònica conté caràcters no permesos");
                return false;
                
            }
        }
     return true;
  }
  
  

}
