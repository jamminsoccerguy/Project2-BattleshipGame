package battleship;

/**
 * Created by Mike Plucker
 * Date: 2/17/2016
 * Class: CSCI 1933-12
 */

//imports
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

//contains utility methods for BattleshipGame
public class Utility {

    //creates scanner
    static Scanner scan = new Scanner(System.in);

    //create reader/writer
    static private PrintWriter write = null;
    static private Scanner read = null;


    //method to read int input from user
    public static int getIntInput(int min, int max){

        //local variable
        int num = scan.nextInt();

        //validate user's input
        while(num < min || num > max){
            System.out.print("\nError: Please enter an Integer between " + min + " and " + max + " ");
            num = scan.nextInt();
        }
        return num;
    }


    //method to read string input from user
    public static String getStringInput(){
        return scan.next();
    }


    //returns a random number between min and max
    public static int randomInt(int min, int max){
        return min + (int)(Math.random() * (max - min + 1));
    }


    //writes the bestScore to a text file
    public static boolean write(int score){

        try{ //test for Exception in call to File constructor
            write = new PrintWriter(new File("data.txt")); //sets the file path
        }
        catch(Exception e){}

        String bestScore = Integer.toString(score); //convert int score to string
        write.println(bestScore); //writes score to data file

        write.close(); //closes the file

        return true;
    }


    //reads the bestScore from a text file
    public static int read(){

        try{ //test for Exception in call to File constructor
            read = new Scanner(new File("data.txt")); //sets the file path
        } //end try block for File usage
        catch(Exception e){}

        String bestScore = read.next(); //reads score from data file

        int score = Integer.parseInt(bestScore); //convert string score to int

        return score;
    }
}
