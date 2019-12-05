import java.util.Scanner;
import java.util.*;

public class main {
    public static HashMap <Character,Integer> ALPHABET = new HashMap<>();
    public static HashMap <Integer,Character> REVERSE = new HashMap<>();
    public static String THE_MESSAGE;
    public static int[][] MMATRIX;
    public static int[][] RESULTM;
    public static int[][] REVERTEDM;
    public static int[][] EMATRIX = new int[2][2];
    public static double[][] DMATRIX = new double[2][2];

    public static void main(String[] args){
        buildAlphabet();
        buildReverse();
        int choice = userChoice();

        while(choice != 7){
            if(choice == 1){
                takeMessage();
                choice = userChoice();
            }
            if(choice == 2){
                checkMessage();
                choice = userChoice();
            }
            if(choice == 3){
                takeMatrix();
                choice = userChoice();
            }
            if(choice == 4){
                printEMessage();
                choice = userChoice();
            }
            if(choice == 5){
                printDKey();
                choice = userChoice();
            }
            if(choice == 6){
                revertBack(RESULTM);
                choice = userChoice();
            }
        }
    }

    public static int userChoice() {

        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Enter a message (press 1)");
        System.out.println("2) Check current message (press 2)");
        System.out.println("3) Enter encryption matrix (press 3)");
        System.out.println("4) Check encrypted message (press 4)");
        System.out.println("5) Check decryption matrix (press 5)");
        System.out.println("6) Check decrypted message (press 6)");
        System.out.println("7) Quit (press 7)");

        Scanner input = new Scanner(System.in);
        int userInput = input.nextInt();
        return userInput;
    }

    public static void takeMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your message:");
        THE_MESSAGE = input.nextLine();
        if(THE_MESSAGE.length()%2 == 0){
            MMATRIX = new int[2][(THE_MESSAGE.length()/2)];
            RESULTM = new int[2][(THE_MESSAGE.length()/2)];
            REVERTEDM = new int[2][(THE_MESSAGE.length()/2)];
        }
        else{
            MMATRIX = new int[2][(THE_MESSAGE.length()/2)+1];
            RESULTM = new int[2][(THE_MESSAGE.length()/2)+1];
            REVERTEDM = new int[2][(THE_MESSAGE.length()/2)+1];
        }
        messageToMatrix(THE_MESSAGE);
    }

    public static void checkMessage(){
        System.out.println("\nCurrent message:");
        System.out.println(THE_MESSAGE+"\n");

    }

    public static void takeMatrix(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the numbers you would like to use for encryption:");
        System.out.println("E11: ");
        int a11 = input.nextInt();
        System.out.println("E12: ");
        int a12 = input.nextInt();
        System.out.println("E21: ");
        int a21 = input.nextInt();
        System.out.println("E22: ");
        int a22 = input.nextInt();
        EMATRIX[0][0] = a11;
        EMATRIX[0][1] = a12;
        EMATRIX[1][0] = a21;
        EMATRIX[1][1] = a22;
        encrypt(MMATRIX);
        findDKey();
    }

    public static void printEMessage(){

        System.out.println("Encryption Matrix");
        System.out.println("--   --");
        System.out.println("| "+EMATRIX[0][0]+" "+EMATRIX[0][1]+" |");
        System.out.println("| "+EMATRIX[1][0]+" "+EMATRIX[1][1]+" |");
        System.out.println("--   --");

        StringBuilder top = new StringBuilder();
        StringBuilder bottom = new StringBuilder();

        System.out.println("Encrypted Matrix");
        for(int i = 0; i<RESULTM[0].length; i++){
            top.append(RESULTM[0][i]+" ");
        }
        for(int i = 0; i<RESULTM[1].length; i++){
            bottom.append(RESULTM[1][i]+" ");
        }
        System.out.println("--                        --");
        System.out.println(top);
        System.out.println(bottom);
        System.out.println("--                        --");

    }

    public static void printDKey(){
        System.out.println("--      --");
        System.out.println("|"+DMATRIX[0][0]+" "+DMATRIX[0][1]+"|");
        System.out.println("|"+DMATRIX[1][0]+" "+DMATRIX[1][1]+"|");
        System.out.println("--      --");
    }

    public static void messageToMatrix(String messageString){
        for(int i = 0; i < messageString.length(); i++){
            int j = i/2;
            MMATRIX[0][j] = ALPHABET.get(messageString.charAt(i));
            //System.out.println(MMATRIX[0][j]);
            if(i+1 < messageString.length()){
                i++;
                MMATRIX[1][j] = ALPHABET.get(messageString.charAt(i));
            }
            else{
                MMATRIX[1][j] = 0;
            }
        }

    }

    public static void encrypt(int[][] messageMatrix){
        for(int i = 0; i < messageMatrix[0].length; i++){
            RESULTM[0][i] = (EMATRIX[0][0]*messageMatrix[0][i])+(EMATRIX[0][1]*messageMatrix[1][i]);
            //System.out.println("RESULT 0 "+i+" = "+RESULTM[0][i]);
            RESULTM[1][i] = (EMATRIX[1][0]*messageMatrix[0][i])+(EMATRIX[1][1]*messageMatrix[1][i]);
            //System.out.println("RESULT 1 "+i+" = "+RESULTM[1][i]);
        }
    }

    public static void findDKey(){
        double determinant = ((EMATRIX[0][0]*EMATRIX[1][1])-(EMATRIX[0][1]*EMATRIX[1][0]));
        //System.out.println(determinant);
        DMATRIX[0][0] = EMATRIX[1][1]/determinant;
        DMATRIX[0][1] = (-1*EMATRIX[0][1])/determinant;
        DMATRIX[1][0] = (-1*EMATRIX[1][0])/determinant;
        DMATRIX[1][1] = EMATRIX[0][0]/determinant;
    }

    public static void revertBack(int[][]encryptedM){
        for(int i = 0; i < encryptedM[0].length; i++){
            double d1 = (DMATRIX[0][0]*encryptedM[0][i])+(DMATRIX[0][1]*encryptedM[1][i]);
            int val1 = (int)d1;
            REVERTEDM[0][i] = val1;

            double d2 = (DMATRIX[1][0]*encryptedM[0][i])+(DMATRIX[1][1]*encryptedM[1][i]);
            int val2 = (int)d2;
            REVERTEDM[1][i] = val2;
        }

        StringBuilder answer = new StringBuilder();

        for(int i = 0; i < REVERTEDM[1].length; i++){
            answer.append(REVERSE.get(REVERTEDM[0][i]));
            answer.append(REVERSE.get(REVERTEDM[1][i]));
        }

        System.out.println(answer+"\n");
    }

    public static void buildAlphabet(){
        ALPHABET.put('A',1);
        ALPHABET.put('B',2);
        ALPHABET.put('C',3);
        ALPHABET.put('D',4);
        ALPHABET.put('E',5);
        ALPHABET.put('F',6);
        ALPHABET.put('G',7);
        ALPHABET.put('H',8);
        ALPHABET.put('I',9);
        ALPHABET.put('J',10);
        ALPHABET.put('K',11);
        ALPHABET.put('L',12);
        ALPHABET.put('M',13);
        ALPHABET.put('N',14);
        ALPHABET.put('O',15);
        ALPHABET.put('P',16);
        ALPHABET.put('Q',17);
        ALPHABET.put('R',18);
        ALPHABET.put('S',19);
        ALPHABET.put('T',20);
        ALPHABET.put('U',21);
        ALPHABET.put('V',22);
        ALPHABET.put('W',23);
        ALPHABET.put('X',24);
        ALPHABET.put('Y',25);
        ALPHABET.put('Z',26);
        ALPHABET.put('.',27);
        ALPHABET.put('?',28);
        ALPHABET.put(' ',29);
    }

    public static void buildReverse(){
        REVERSE.put(0,' ');
        REVERSE.put(1,'A');
        REVERSE.put(2,'B');
        REVERSE.put(3,'C');
        REVERSE.put(4,'D');
        REVERSE.put(5,'E');
        REVERSE.put(6,'F');
        REVERSE.put(7,'G');
        REVERSE.put(8,'H');
        REVERSE.put(9,'I');
        REVERSE.put(10,'J');
        REVERSE.put(11,'K');
        REVERSE.put(12,'L');
        REVERSE.put(13,'M');
        REVERSE.put(14,'N');
        REVERSE.put(15,'O');
        REVERSE.put(16,'P');
        REVERSE.put(17,'Q');
        REVERSE.put(18,'R');
        REVERSE.put(19,'S');
        REVERSE.put(20,'T');
        REVERSE.put(21,'U');
        REVERSE.put(22,'V');
        REVERSE.put(23,'W');
        REVERSE.put(24,'X');
        REVERSE.put(25,'Y');
        REVERSE.put(26,'Z');
        REVERSE.put(27,'.');
        REVERSE.put(28,'?');
        REVERSE.put(29,' ');


    }
}
