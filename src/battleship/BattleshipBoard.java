package battleship;

/**
 * Created by Mike Plucker
 * Date: 2/17/2016
 * Class: CSCI 1933-12
 */
public class BattleshipBoard {

    //instance variables
    private final int ROW, COLUMN;
    private final int BOARD [][];

    //symbol variables
    private final int EMPTY = 0;
    private final int SHIP = 1;
    private final int MINE = 2;
    private final int HIT = 3;
    private final int MISS = 4;
    private final int HITMINE = 5;


    //default constructor will set to minimum board size (3 x 3)
    public BattleshipBoard(){
        this.ROW = 3;
        this.COLUMN = 3;

        BOARD = new int [getRow()][getColumn()]; //creates game board with 3 X 3 dimensions
    }


    //constructor
    public BattleshipBoard(int row, int column){

        this.ROW = row;
        this.COLUMN = column;

        BOARD = new int [getRow()][getColumn()]; //creates game board with user given dimensions
    }


    //getters
    public int getRow(){
        return ROW;
    }

    public int getColumn(){
        return COLUMN;
    }

    public int [][] getBoard(){
        return BOARD;
    }


    //initializes board to starting empty symbol (~)
    public void initBoard(int [][] board){

        //iterates over all elements of 2D array
        for(int row = 0; row < getRow(); row++){
            for(int column = 0; column < getColumn(); column++){
                board[row][column] = EMPTY; //sets all elements to "0"
            }
        }
    } //end method


    //displays normal or verbose game board
    public void displayGameBoard(int [][] board){

        System.out.println(); //spacer

        //prints out header numbers
        for(int header = 0; header < getColumn(); header++){
            System.out.print("\t" + (header));
        }

        System.out.println(); //spacer

        //prints out each game row
        for(int row = 0; row < getRow(); row++){
            System.out.print((row) + ""); //prints out row numbers

            for(int column = 0; column < getColumn(); column++){ //prints out each game column

                switch(board[row][column]){ //checks the element at this coordinate
                    case EMPTY:
                        System.out.print("\t" + "~"); //empty symbol (nothing has happened yet)
                        break;
                    case HIT:
                        System.out.print("\t" + "X"); //hit symbol
                        break;
                    case HITMINE:
                        System.out.print("\t" + "M"); //mine that's been hit symbol
                        break;
                    default:
                        System.out.print("\t" + "~"); //empty symbol
                } //end switch
            } //end column loop
            System.out.println(); //prints next row on new line
        } //end row loop
    } //end method


    //displays test game board
    public void displayTestBoard(int [][] board){

        System.out.println(); //spacer

        //prints out header numbers
        for(int header = 0; header < getColumn(); header++){
            System.out.print("\t" + (header));
        }

        System.out.println(); //spacer

        //prints out each game row
        for(int row = 0; row < getRow(); row++){
            System.out.print((row) + ""); //prints out row numbers

            for(int column = 0; column < getColumn(); column++){ //prints out each game column

                switch(board[row][column]){ //checks the element at this coordinate
                    case EMPTY:
                        System.out.print("\t" + "~"); //empty symbol (nothing has happened yet)
                        break;
                    case SHIP:
                        System.out.print("\t" + "S"); //ship symbol
                        break;
                    case MINE:
                        System.out.print("\t" + "m"); //mine symbol
                        break;
                    case HIT:
                        System.out.print("\t" + "X"); //hit ship symbol
                        break;
                    case MISS:
                        System.out.print("\t" + "*"); //miss symbol
                        break;
                    case HITMINE:
                        System.out.print("\t" + "M"); //mine that's been hit symbol
                        break;
                } //end switch
            } //end column loop
            System.out.println(); //prints next row on new line
        } //end row loop
    } //end method
}//end class
