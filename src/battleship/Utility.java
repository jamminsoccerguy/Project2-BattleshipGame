package battleship;

/**
 * Created by Mike Plucker
 * Date: 2/17/2016
 * Class: CSCI 1933-12
 */

//imports
import java.util.Scanner;

//contains utility methods for BattleshipGame
public class Utility {

    //instance variable
    static Scanner scan = new Scanner(System.in);


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
}
