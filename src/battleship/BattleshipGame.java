package battleship;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by Mike Plucker
 * Date: 2/17/2016
 * Class: CSCI 1933-12
 */
public class BattleshipGame {

    //board object
    BattleshipBoard board;

    //instance variables
    private int turnNum, hitCount, penalty, totalHits, shipNum = 0, mineNum = 0, mode;
    private final int SHIPLENGTH = 3; //all ships have length of 3
    private boolean playAgain = true;


    //starts game
    public void startGame(){

        System.out.println("Welcome to Battleship!\n");

        while(playAgain){ //will loop if user wants to play again

            //initialize or reset variables
            turnNum = 1;
            hitCount = 0;
            penalty = 0;
            String yesOrNo = ""; //local variable

            setupBoard(); //initial setup of game board

            placeShipsAndMines(); //place ships and mines on game board

            //gives user information on how to play game
            System.out.printf("\nThere are %d ship(s) and %d mine(s) on the board", shipNum, mineNum);
            System.out.println("\nYou have Unlimited turns, but the goal is to sink the ship(s) in as few moves as possible.");
            System.out.println("You are Penalized turns for hitting mines and guessing the same coordinates multiple times.");
            System.out.println("\nTry to Hit the ship(s) by guessing a coordinate (row col)");
            System.out.println("The upper left coordinates are (0,0).");
            System.out.println("Enter moves between (0,0) and (" + (board.getRow() - 1) + "," + (board.getColumn() - 1) + ").\n");

            if(mode == 0 || mode == 1){ //if in normal or verbose mode, displays appropriate game board at start
                board.displayGameBoard(board.getBoard()); //displays board without all symbols (no ship or mine locations)
            }

            do{ //will loop until all ships are sunk

                //if in test mode, display board every turn
                if(mode == 2){
                    board.displayTestBoard(board.getBoard()); //displays all symbols (ship and mine locations)
                }

                System.out.println("\nTurn #  " + turnNum); //print which turn user is on
                System.out.print("What is your coordinate guess? (row col) "); //prompt user for coordinates

                takeTurn(Utility.getIntInput(0, (board.getRow() - 1)), Utility.getIntInput(0, (board.getColumn() - 1))); //validates user's input, then calls takeTurn method to determine turn results
            }while(hitCount != totalHits); //loop until hitCount = total number of ship hits available

            //indicates game is over
            System.out.println("\nYou win! You sunk all of the ships!");
            System.out.println("It took you " + (turnNum - 1) + " turns to win.");
            System.out.println("You were penalized " + penalty + " times.");

            board.displayTestBoard(board.getBoard()); //displays final board (all symbols)


            System.out.print("\nDo you want to play again? (y/n) "); //see if user wants to play again
            /*yesOrNo = Utility.getStringInput(); //gets user input using getStringInput method in Utility class and auto-converts it to lowercase

            while(yesOrNo != "y"){ //validates that user enters y or n
                System.out.print("\nError: Please enter either y or n");
                System.out.print("\nDo you want to play again? (y/n) "); //see if user wants to play again
                yesOrNo = Utility.getStringInput(); //gets user input using getStringInput method in Utility class and auto-converts it to lowercase
            }

            if(yesOrNo == "y"){
                playAgain = true;
            }
            else
                playAgain = false;*/

            playAgain = (Utility.getStringInput().charAt(0) == 'y'); //restart game if y, quit if n

            System.out.println(); //spacer
        } //end playAgain loop
        System.out.println("\nThank you for playing!");
    } //end method


    //initial set-up of game board
    public void setupBoard(){

        System.out.println("The Game board can be between (3 X 3) and (10 X 10).");
        System.out.print("What size do you want the game board to be? (row col) "); //prompt user to select game board size

        board = new BattleshipBoard(Utility.getIntInput(3, 10), Utility.getIntInput(3, 10)); //validates input then creates game board with given dimensions

        board.initBoard(board.getBoard()); //sets board to initial symbols

        System.out.println("\nWhat mode do you want to play in? "); //prompt user to select game mode
        System.out.print("Normal (0), Verbose (1) or Test (2)? ");
        mode = Utility.getIntInput(0, 2);
    } //end method


    //places ships and mines on game board
    public void placeShipsAndMines(){

        //local variable
        int locations = board.getRow() * board.getColumn(); //total locations on game board

        if(locations > 36){ //more than 36 locations
            for(int i = 0; i < 3; i++){ //add 3 ships and mines
                addShip();
                addMine();
            }
            shipNum = 3;
            mineNum = 3;
        }
        else if(locations >= 17 && locations <= 36 ){ //between 17 and 36 locations
            for(int i = 0; i < 2; i++){ //add 2 ships and mines
                addShip();
                addMine();
            }
            shipNum = 2;
            mineNum = 2;
        }
        else{ //between 9 and 16 locations
            addShip(); //add 1 ship
            addMine(); //add 1 mine
            shipNum = 1;
            mineNum = 1;
        }
        totalHits = shipNum * SHIPLENGTH; //calculates total number of ship hits available
    } //end method


    //adds a ship
    public void addShip(){

        //local variable
        boolean add = false;

        while(!add){ //loops until a ship is added

            //picks random (x,y) coordinate using randomInt(int min, int max) method in Utility class
            int x = Utility.randomInt(0, board.getBoard().length - 1); //gets random number between 0 and size of game board
            int y = Utility.randomInt(0, board.getBoard()[0].length - 1); //^^

            boolean horizontal = (Utility.randomInt(0, 1)) == 1; //decides if ship is horizontal or vertical by randomly picking 0 or 1

            if(horizontal){ //adds horizontal ship
                boolean willFit = true;

                //checks if the ship will fit horizontally and that there are no ships/mines there already
                for(int i = 0; i < SHIPLENGTH; i++){ //iterates over ship length (3)
                    if(y + i >= board.getBoard()[0].length){ //if any of the ship's 3 elements are greater than or equal to the # of columns
                        willFit = false; //won't fit
                        break; //exit for loop
                    }
                    if(board.getBoard()[x][y + i] != 0){ //if there is something else occupying that element already (ship/mine)
                        willFit = false; //won't fit
                        break; //exit for loop
                    }
                }
                if(!willFit){ //if the ship won't fit, start over with new coordinates
                    continue; //jumps back up to while(!add)
                }

                //ship will fit!
                for(int i = 0; i < SHIPLENGTH; i++){ //iterates over ship length (3)
                    board.getBoard()[x][y + i] = 1; //places ship horizontally starting at "y" and going to the right
                }
                add = true; //leave loop
            }//end horizontal if
            else{ //adds vertical ship
                boolean willFit = true;

                for(int i = 0; i < SHIPLENGTH; i++){ //iterates over ship length (3)
                    if(x + i >= board.getBoard().length){ //if any of the ship's 3 elements are greater than or equal to the # of rows
                        willFit = false; //won't fit
                        break; //exit for loop
                    }
                    if(board.getBoard()[x + i][y] != 0){ //if there is something else occupying that element already (ship/mine)
                        willFit = false; //won't fit
                        break; //exit for loop
                    }
                }
                if(!willFit){ //if the ship won't fit, start over with new coordinates
                    continue; //jumps back up to while(!add)
                }

                //ship will fit!
                for(int i = 0; i < SHIPLENGTH; i++){ //iterates over ship length (3)
                    board.getBoard()[x + i][y] = 1; //places ship vertically starting at "x" and going down
                }
                add = true; //leave loop
            } //end else
        } //end added loop
    } //end method


    //adds a mine
    public void addMine(){

        //local variable
        boolean add = false;

        while(!add){ //loops until a mine is added

            //picks random (x,y) coordinate using randomInt(int min, int max) method in Utility class
            int x = Utility.randomInt(0, board.getBoard().length - 1); //gets random number between 0 and size of game board
            int y = Utility.randomInt(0, board.getBoard()[0].length - 1); //^^

            boolean willFit = true;

            if(x >= board.getBoard().length || y >= board.getBoard()[0].length){ //if coordinates are out of bounds
                willFit = false; //won't fit
                continue; //jumps back up to while(!add) -- start over with new coordinates
            }
            if(board.getBoard()[x][y] != 0){ //if there is something else occupying that element already (ship/mine)
                willFit = false; //won't fit
                continue; //jumps back up to while(!add) -- start over with new coordinates
            }

            //mine will fit!
            board.getBoard()[x][y] = 2; //places mine at that coordinate
            add = true; //leave loop
        } //end added loop
    } //end method


    //compares user's guess to game board and determines results of turn
    public void takeTurn(int row, int column){

        //local variable
        int boardValue = board.getBoard()[row][column]; //holds the array value at the coordinate guessed by user

        //turn results depending on what array value is
        switch(boardValue){
            case 0: //spot was empty
                turnNum++; //increment turn counter
                System.out.println("\nYou missed!");
                System.out.println("Your score is: " + (turnNum - 1));
                board.getBoard()[row][column] = 4; //converts element in array to miss
                break;
            case 1: //hit ship
                turnNum++; //increment turn counter
                hitCount++; //increment hit counter
                System.out.println("\nYou hit a ship!");
                System.out.println("Your score is: " + (turnNum - 1));
                board.getBoard()[row][column] = 3; //converts element in array to hit
                if(mode == 1){ //if in verbose mode, display board after ship hit
                    board.displayGameBoard(board.getBoard()); //displays board without all symbols (no ship or mine locations)
                }
                break;
            case 2: //hit mine
                turnNum += 2; //increment turn counter (1 turn penalty for hitting mine)
                penalty++;
                System.out.println("\nYou hit a mine! (1 turn penalty)");
                System.out.println("Your score is: " + (turnNum - 1));
                board.getBoard()[row][column] = 5; //converts element in array to hit mine
                if(mode == 1){ //if in verbose mode, display board after mine hit
                    board.displayGameBoard(board.getBoard()); //displays board without all symbols (no ship or mine locations)
                }
                break;
            case 3: //already a hit
                turnNum += 2; //increment turn counter (1 turn penalty for guessing same spot more than once)
                penalty++;
                System.out.println("\nUh oh! You've already guessed that spot! It's still a hit! (1 turn penalty)");
                System.out.println("Your score is: " + (turnNum - 1));
                break;
            case 4: //already a miss
                turnNum += 2; //increment turn counter (1 turn penalty for guessing same spot more than once)
                penalty++;
                System.out.println("\nUh oh! You've already guessed that spot! It's still a miss! (1 turn penalty)");
                System.out.println("Your score is: " + (turnNum - 1));
                break;
            case 5: //already hit the mine
                turnNum += 3; //increment turn counter (2 turn penalty for guessing same spot more than once + hitting mine)
                penalty += 2;
                System.out.println("\nUh oh! You've already guessed that spot! It's still a mine! (2 turn penalty)");
                System.out.println("Your score is: " + (turnNum - 1));
                break;
        }//end switch
        hint(row, column); //tell player if their guess is close to a ship location
    } //end method


    //tells player if their guess is close to a ship location
    public void hint(int row, int column){

        //local variables
        int x = row, y = column;
        int rowMax = board.getBoard().length - 1;
        int colMax = board.getBoard()[0].length - 1;

        System.out.println("You Guessed = (" + x + "," + y + ")");

        System.out.println("RowMax = " + rowMax);
        System.out.println("ColMax = " + colMax);

        System.out.println(board.getBoard()[x][y]);


        if(board.getBoard()[x][y] != 3){ //if the guessed coordinate is not a ship

            if(x+1 <= rowMax && x-1 >= 0){
                if((board.getBoard()[x+1][y] == 1) || (board.getBoard()[x-1][y] == 1)){
                    System.out.println("A miss, but very close!");
                }
            }
            if(y+1 <= colMax && y-1 >= 0){
                if((board.getBoard()[x][y+1] == 1 || board.getBoard()[x][y-1] == 1)){
                    System.out.println("A miss, but very close!");
                }
            }
        }
    } //end method
}//end class
