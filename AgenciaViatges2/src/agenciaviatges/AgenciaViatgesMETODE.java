package agenciaviatges;
import java.util.Scanner;
public class AgenciaViatgesMETODE {
    
    
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
                        validarDNI();
                        break;
                     case 2:
                        validarEmail();
                            
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
  private static void validarDNI(){
  //VALIDAR DNI
       int dniInt=0;
       String dniNum="";
       char[] letraDni = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};  
        boolean dniCorrecte=true;
        int caracterNum=0;
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
            } else {
                System.out.println("\nDNI incorrecte, l'última posició ha de ser una lletra");
                dniCorrecte=false;

            }
            //comparem la lletra del dni amb la lletra de la taula
            if (dniCorrecte){
                dniInt=Integer.parseInt(dniNum);

                if (ult!=letraDni[dniInt%23]){
                    System.out.println("\nLa lletra no correspon a aquest DNI");
                    dniCorrecte=false;
                } else {
                    System.out.println("\nDNI Correcte");
                }
            }
        }
        System.out.println("\nPrem RETURN per continuar...");
        teclat.nextLine();
        teclat.nextLine();

  }
  
  private static void validarEmail(){
      // validem que el format del email sigui correcte: tingui una @, mínim un punt i al darrere de l'arroba
      
        System.out.println("\n Has seleccionat VALIDAR EMAIL. Introdueix un EMAIL");
        String email = teclat.next();
        
        
        /*Validacions que hem de realitzar l'adreça electrònica
          no pot començar i acabar en @ i punt
               
        */
        boolean emailOK=true;
        int pospriarroba = email.indexOf('@');
        int posultarroba = email.lastIndexOf('@');
        
        if (pospriarroba ==-1 ){
            System.out.println("\n L'adreça electrònica ha de contenir el caràcter \'@\'");
        } else {
            
            if (pospriarroba != posultarroba) {
                 System.out.println("\n L'adreça electrònica no pot contenir més d'una \'@\'");
            } else {
                
            
            
                String local = email.substring(0,pospriarroba);
                String domExt= email.substring(pospriarroba + 1,email.length());
                System.out.println("local: " + local);
                System.out.println("domini: " + domExt);

                if (local.length() == 0) {
                    System.out.println("\n L'adreça electrònica ha de tenir la part LOCAL");
                } else {

                    int pospunt = domExt.indexOf('.');
                    int posultpunt= domExt.lastIndexOf('.');
                    System.out.println("pospunt: " + pospunt);
                    if (pospunt ==-1 ){
                        System.out.println("\n L'adreça electrònica ha de contenir el caràcter \'.\' seguit de l'EXTENSIO");
                    } else {
                        if (pospunt!=posultpunt){
                            System.out.println("En l'adreça electrònica no pot haver més d'un punt '.'");
                        } else {
                            String subdomini = domExt.substring(0,pospunt);
                            System.out.println("domini: " + subdomini);
                            String extensio = domExt.substring(pospunt+1,domExt.length());
                            System.out.println("Extensio: " + extensio);
                            if (subdomini.length() == 0)  {  
                                System.out.println("\n L'adreça electrònica ha de tenir la part de domini'");
                            } else {
                                 if (extensio.length() == 0)  {  
                                    System.out.println("\n L'adreça electrònica ha de tenir extensió");
                                 } else {
                                     // l'extensió ha de tenir mínim 2 caràcters
                                     if (extensio.length() < 2) {
                                         System.out.println("\n L'extensió ha de tenir mínim 2 caràcters");
                                     } else {


                                        //comprovem que els caràters siguin els correctes en local, domini i extensió 
                                        //primer comprovem en la part LOCAL 
                                        for (int i = 0 ; i < local.length(); i ++){
                                            char l = local.charAt(i);
                                            /*if ((l>'a'&& l<'z')||(l>'A'&& l<'Z')||(l>'0'&&l<'9')||(l=='-')||(l=='_')){
                                                continue;
                                            } else {
                                                System.out.println("\nL'adrecça electrònica conté caracters no permesos");
                                                emailOK=false;
                                                break;
                                            }*/
                                            if (!((l>='a'&& l<='z')||(l>='A'&& l<'Z')||(l>'0'&&l<'9')||(l=='-')||(l=='_'))){
                                                System.out.println("l: "+ l);
                                                System.out.println("\nL'adrecça electrònica conté caracters no permesos");
                                                emailOK=false;
                                                break;
                                            }
                                            
                                            
                                        }
                                        if (emailOK){
                                        //mirem que tots els caràcters siguin correctes en el DOMINI         
                                           for (int i = 0 ; i < subdomini.length(); i ++){
                                               char l = subdomini.charAt(i);
                                               if ((l>='a'&& l<='z')||(l>='A'&& l<='Z')||(l>'0'&&l<'9')||(l=='-')||(l=='_')){
                                                   continue;
                                               } else {
                                                   System.out.println("\nL'adrecça electrònica conté caracters no permesos");
                                                   emailOK=false;
                                                   break;
                                               }
                                           }
                                        }
                                        if (emailOK){
                                        //mirem que tots els caràcters siguin correctes en el EXTENSIO         
                                           for (int i = 0 ; i < extensio.length(); i ++){
                                               char l = extensio.charAt(i);
                                               if ((l>='a'&& l<='z')||(l>='A'&& l<='Z')||(l>'0'&&l<'9')||(l=='-')||(l=='_')){
                                                   continue;
                                               } else {
                                                   System.out.println("\nL'adrecça electrònica conté caracters no permesos");
                                                   emailOK=false;
                                                   break;
                                               }
                                           }
                                        }
                                        if (emailOK)
                                         System.out.println("El format de l'adreça electrònica ES correcte");

                                } 
                            }
                        }
                    }
                }
            }
        }
    }
    System.out.println("\nPrem RETURN per continuar...");
    teclat.nextLine();
    teclat.nextLine();
  }
  
  private static boolean validarEmail2(String email){
  //Se definen las varibles tipo boolean
  boolean valido = false;
  //Se definen las variables tipo int
  int posArroba;
  int posPunto;
  //Se definen las variables tipo String
  String local;
  String dominio;
  //Se define el array tipo char
  char [] caracteres = {'(', ')', '[', ']', '\\',',', ';',':', '<', '>', ' '};
  //Condición que me valida la longitud
  //if (validarLongitud(email, min, max) == true){
    //Comprobación de que tenemos una @
    posArroba = email.indexOf('@');
    //Condición para que halla una @
    if (posArroba != -1){
      //Definimos los dos conjuntos(variables) para diferenciarlos: local y dominio
      local = email.substring(0,posArroba);
      dominio= email.substring(posArroba + 1,email.length());
      //Condición para que local y dominio no sean nulos, es decir, tengan más de un carácter
      if(local.length()> 0 && dominio.length() > 0){
        //Definimos una variables de la ultima posición
        posPunto = local.lastIndexOf('.');
        //Condición para que esa posición no sea '.'
        if(posPunto == -1){
          //Bucle con el array superior para imponer la condición de que no se encuentre ningun caracter de los antriores
          for (int i = 0; i < local.length(); i++) {
            for (int j = 0; j < caracteres.length; j++) {
              if(local.charAt(i)!= caracteres[j]){
                valido = true;
              }
            }
          }
        }
      }
    }
  //}
  return valido;
}


}
