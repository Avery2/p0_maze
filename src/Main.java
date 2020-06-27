/*-
 * Author: Justin Chan
 * Email: jachan@wisc.edu
 * Lecture: LEC 1
 * Project Name: p0 java io practice
 * A game about exploring rooms
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A game about exploring rooms.
 * 
 * @author averychan
 *
 */
public class Main {

  private static final String title = "Justin Chan jachan@wisc.edu LEC 1";
  private static final Scanner sc = new Scanner(System.in);
  private static Scanner text;
  private static PrintWriter pw;
  private static String name;
  private static int numWins;

  protected enum direction {
    UP, DOWN, LEFT, RIGHT
  }

  /**
   * Initializes resources and asks user for possible input file with saved user information.
   */
  private static void initialize() {
    try {
      if (askYN("Do you have an input file? (it's probably named \"output.txt\""
          + " if you've played before)")) {
        System.out.println("Enter the name of your input file:");
        text = new Scanner(new File(getFileName()));
        if (text.hasNext())
          name = text.nextLine();
        if (text.hasNext()) {
          numWins = text.nextInt();
        }
        System.out.println("Welcome back " + name + "!");
      } else {
        getUserInfo();
      }
    } catch (FileNotFoundException e) {
      System.out.println("No file of that name was found.");
      name = "defaultname";
    }
    try {
      pw = new PrintWriter(new File("output.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get information about user
   */
  private static void getUserInfo() {
    System.out.println("What is your name?");
    name = sc.nextLine();
  }

  /**
   * Closes resources
   */
  private static void terminate() {
    sc.close();
    if (text != null) {
      text.close();
    }
    if (pw != null) {
      pw.close();
    }
  }

  /**
   * Gets response to a yes/no question from the user
   * 
   * @param question the yes/no question
   * @return the response of the user
   */
  private static boolean askYN(String question) {
    boolean invalidInput = true;
    boolean answer = false;
    String response;
    System.out.println(question);
    System.out.println("  [y]es\n  [n]o");
    while (invalidInput) {
      System.out.print("> ");
      response = sc.next();
      sc.nextLine();
      switch (response.toLowerCase()) {
        case "y":
          invalidInput = false;
          answer = true;
          break;
        case "n":
          invalidInput = false;
          answer = false;
          break;
        default:
          System.out.println("Must respond yes (y) or no (n)");
      }
    }
    return answer;
  }

  /**
   * Gets the name of a file from the user
   * 
   * @return name of file
   */
  private static String getFileName() {
    boolean invalidInput = true;
    String response = "output.txt";
    while (invalidInput) {
      System.out.print("> ");
      response = sc.nextLine();
      if (response.length() < 4) {
        System.out.println("Must be a text file (*.txt)");
      } else if (response.substring(response.length() - 4, response.length()).equals(".txt")) {
        invalidInput = false;
      } else {
        System.out.println("Must be a text file (*.txt)");
      }
    }
    return response;
  }

  /**
   * Prints opening dialog
   */
  private static void printOpenMenu() {

    System.out.println(title);

    boolean doTutorial;
    doTutorial = askYN("Do you want to see the tutorial?");

    if (doTutorial) {
 // @formatter:off
    System.out.println("\nWelcome to the room explorer tutorial.\n\n" + 
        "This is an example room with a hallway to the right called room 0:\n\n" + 
        " ┌───┐\n" + 
        " │ 0 ├─\n" + 
        " └───┘\n\n" + 
        "You can navigate to the right by typing w, a, s, d when prompted. " +
        "Your goal is to reach the final room marked with an X:\n\n" + 
        " ┌───┐\n" + 
        " │ X │\n" + 
        " └───┘\n\n" +
        "Have fun!\n");
    // @formatter:on
    }
  }

  /**
   * Asks the user which direction they would like to move
   * 
   * @param rm The room the user is moving from
   * @return The next movement direction
   */
  private static direction nextMovement(Room rm) {
    boolean invalidInput = true;
    String response;

    System.out.println("Move which direction?");
    if (rm.hasUp()) {
      System.out.println("  [w] up");
    } else {
      System.out.println("  [ ]");
    }
    if (rm.hasDown()) {
      System.out.println("  [s] down");
    } else {
      System.out.println("  [ ]");
    }
    if (rm.hasLeft()) {
      System.out.println("  [a] left");
    } else {
      System.out.println("  [ ]");
    }
    if (rm.hasRight()) {
      System.out.println("  [d] right");
    } else {
      System.out.println("  [ ]");
    }

    while (invalidInput) {
      System.out.print("> ");
      response = sc.next();
      sc.nextLine();
      switch (response.toLowerCase()) {
        case "w":
          if (rm.hasUp()) {
            return direction.UP;
          } else {
            System.out.println("No room above.");
          }
          break;
        case "s":
          if (rm.hasDown()) {
            return direction.DOWN;
          } else {
            System.out.println("No room below.");
          }
          break;
        case "a":
          if (rm.hasLeft()) {
            return direction.LEFT;
          } else {
            System.out.println("No room to the left.");
          }
          break;
        case "d":
          if (rm.hasRight()) {
            return direction.RIGHT;
          } else {
            System.out.println("No room to the right.");
          }
          break;
        default:
          System.out.println("Invalid input.");
      }
    }
    return null;
  }

  /**
   * Runs the basic Room movement
   */
  private static void runGame() {
    direction move;
    Room currRoom = Room.generateMap();
    while (true) {
      currRoom.printRoom();
      if (currRoom.isGoal()) {
        numWins++;
        System.out.println("You win!");
        break;
      }
      move = nextMovement(currRoom);
      switch (move) {
        case UP:
          currRoom = currRoom.getUp();
          break;
        case DOWN:
          currRoom = currRoom.getDown();
          break;
        case LEFT:
          currRoom = currRoom.getLeft();
          break;
        case RIGHT:
          currRoom = currRoom.getRight();
          break;
      }
      System.out.println();
    }
  }

  /**
   * Runs the program
   * 
   * @param args
   */
  public static void main(String[] args) {
    printOpenMenu();
    initialize();

    do {
      runGame();
    } while (askYN("Play again?"));

    System.out.println("Thanks for playing " + name + ".");
    System.out.println("You have " + numWins + " total wins!");
    System.out.println("Your information has been saved to the file named output.txt");
    // save user information to file to possibly be retrieved later
    pw.println(name);
    pw.println(numWins);
    pw.flush();

    terminate();
  }
}
