/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenciaviatges.utilitats;

/**
 *
 * @author pilar
 */
public class Validacions {
    
    public static boolean validarDNI(String dni){
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
            dniCorrecte=false;
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
  
  public static boolean validarEmail(String email){
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
                    System.out.println("\n L'adreça electrònica ha de tenir la part LOCAL");
                    return false;
                } else {

                    int pospunt = domExt.indexOf('.');
                    int posultpunt= domExt.lastIndexOf('.');
                    
                    if (pospunt ==-1 ){
                        System.out.println("\n L'adreça electrònica ha de contenir el caràcter \'.\' seguit de l'EXTENSIO");
                        return false;
                    } else {
                        if (pospunt!=posultpunt){
                            System.out.println("En l'adreça electrònica no pot haver més d'un punt '.'");
                            return false;
                        } else {
                            String subdomini = domExt.substring(0,pospunt);
                            
                            String extensio = domExt.substring(pospunt+1,domExt.length());
                            
                            if (subdomini.length() == 0)  {  
                                System.out.println("\n L'adreça electrònica ha de tenir la part de domini'");
                                return false;
                            } else {
                                 if (extensio.length() == 0)  {  
                                    System.out.println("\n L'adreça electrònica ha de tenir extensió");
                                    return false;
                                 } else {
                                     // l'extensió ha de tenir mínim 2 caràcters
                                     if (extensio.length() < 2) {
                                         System.out.println("\n L'extensió ha de tenir mínim 2 caràcters");
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
                System.out.println("caracter no permes: " + str.charAt(i)); 
                return false;
             }
         }
     return true;
  }
   
    public static boolean validarEmail_versió2(String email){
      // validem que el format del email sigui correcte: tingui una @, mínim un punt i al darrere de l'arroba
         int contarroba=0, contpunt=0;     
         for (int i = 0 ; i < email.length(); i ++){
            char l = email.charAt(i);
            if (l=='@' && contarroba ==0) {
                if ((i==0)|| (i==email.length())){
                    return false; // @ no pot ser ni el primer ni ultim caràcter
                }
                contarroba++;
                //posarroba
                if (contarroba>1) return false; //no pot tenir més de 2 arrobes
            }
            if (l=='.') {
                if ((i==0)|| (i==email.length())){
                    return false; // @ no pot ser ni el primer ni ultim caràcter
                }
                contarroba++;
                if (contarroba>1) return false; //no pot tenir més de 2 arrobes
            }
            if (!((l>'a'&& l<'z')||(l>'A'&& l<'Z')||(l>'0'&&l<'9')||(l=='-')||(l=='_'))){
                 return false; // caràcters no permesos
            }
         }
        
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
  
}
