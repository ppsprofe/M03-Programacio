package agenciaviatges;
import java.util.Scanner;
public class AgenciaViatges_1{
    public static void main(String[] args) {
        //definim les variables necessàries
       Scanner teclat = new Scanner(System.in);
       boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
       int dniInt=0;
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
                        //char[] lletres = {'T','R', 'W', 
                        boolean dniCorrecte=true;
                        int caracterNum=0;
                        int reste;
                        char lletra = 0;
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
                                     System.out.println("dniNum: "+ dniNum);
                                }
                            } else {
                                System.out.println("\nDNI incorrecte, l'última posició ha de ser una lletra");
                                dniCorrecte=false;
                                        
                            }
                            //comparem la lletra del dni amb la lletra de la taula
                            if (dniCorrecte){
                                dniInt=Integer.parseInt(dniNum);
                                reste=dniInt%23;
                                /*if (reste == 0) lletra='T';
                                else if (reste==1) lletra='R';
                                else if (reste==2) lletra='W';
                                else if (reste==3) lletra='A';
                                else if (reste==4) lletra='G';
                                else if (reste==5) lletra='M';
                                else if (reste==6) lletra='Y';
                                else if (reste==7) lletra='F';
                                else if (reste==8) lletra='P';
                                else if (reste==9) lletra='D';
                                else if (reste==10) lletra='X';
                                else if (reste==11) lletra='B';
                                else if (reste==12) lletra='N';
                                else if (reste==13) lletra='J';
                                else if (reste==14) lletra='Z';
                                else if (reste==15) lletra='S';
                                else if (reste==16) lletra='Q';
                                else if (reste==17) lletra='V';
                                else if (reste==18) lletra='H';
                                else if (reste==19) lletra='L';
                                else if (reste==20) lletra='C';
                                else if (reste==21) lletra='K';
                                else if (reste==22) lletra='E';*/
                                
                                switch (reste){
                                    case 0: lletra='T';break;
                                    case 1: lletra='R';break;
                                    case 2: lletra='W';break;
                                    case 3: lletra='A';break;
                                    case 4: lletra='G';break;
                                    case 5: lletra='M';break;
                                    case 6: lletra='Y';break;
                                    case 7: lletra='F';break;
                                    case 8: lletra='P';break;
                                    case 9: lletra='D';break;
                                    case 10: lletra='X';break;
                                    case 11: lletra='B';break;
                                    case 12: lletra='N';break;
                                    case 13: lletra='J';break;
                                    case 14: lletra='Z';break;
                                    case 15: lletra='S';break;
                                    case 16: lletra='Q';break;
                                    case 17: lletra='V';break;
                                    case 18: lletra='H';break;
                                    case 19: lletra='L';break;
                                    case 20: lletra='C';break;
                                    case 21: lletra='K';break;
                                    case 22: lletra='E';break;
                                }
                                if (ult!=lletra){
                                
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
