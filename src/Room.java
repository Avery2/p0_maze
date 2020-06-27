/*-
 * Author: Justin Chan
 * Email: jachan@wisc.edu
 * Lecture: LEC 1
 * A class to represent a Room
 */

/**
 * Models a Room which can be connected to other Rooms
 * 
 * @author averychan
 *
 */
public class Room {
  private Room up, down, left, right;
  private boolean isExplored;
  private int id;
  private static int numRooms;
  private static Room goalRoom;

  /**
   * Construct a new room unconnected to any other roons
   */
  Room() {
    numRooms++;
    id = numRooms;
  }

  /**
   * Construct a new room connected to other Rooms. Pass null if you do not want a connection in a
   * certain direction.
   * 
   * @param roomUp room above current room
   * @param roomDown room below current room
   * @param roomLeft room left of current room
   * @param roomRight room right of current room
   */
  Room(Room roomUp, Room roomDown, Room roomLeft, Room roomRight) {
    numRooms++;
    id = numRooms;
    this.up = roomUp;
    this.down = roomDown;
    this.left = roomLeft;
    this.right = roomRight;
  }

  /**
   * Creates a new room connected to the current room in a certain direction
   * @param direction Direction of new Room with respect to current room
   * @return the new Room
   */
  public Room generateRoom(Main.direction direction) {
    Room newRoom = new Room();
    switch (direction) {
      case UP:
        this.up = newRoom;
        newRoom.setDown(this);
        break;
      case DOWN:
        this.down = newRoom;
        newRoom.setUp(this);
        break;
      case LEFT:
        this.left = newRoom;
        newRoom.setRight(this);
        break;
      case RIGHT:
        this.right = newRoom;
        newRoom.setLeft(this);
        break;
    }
    return newRoom;
  }

  /**
   * Creates a "map" or collection of Rooms for the game to use
   * @return the beginning room
   */
  public static Room generateMap() {
    Room.setNumRooms(0);
    Room root = new Room(); // could later implement random generation, and Map structure error
                            // checking
    root.generateRoom(Main.direction.UP).generateRoom(Main.direction.UP);
    root.generateRoom(Main.direction.DOWN).generateRoom(Main.direction.DOWN);
    root.generateRoom(Main.direction.LEFT).generateRoom(Main.direction.LEFT);
    root.generateRoom(Main.direction.RIGHT).generateRoom(Main.direction.RIGHT).setGoal(true);
    return root;
  }

  /**
   * Print a representation of this Room
   */
  public void printRoom() {
    String tag;
    if (this.isGoal()) {
      tag = "X";
    } else {
      tag = "" + this.getId();
    }
    System.out.print("\n  ┌─");
    // up?
    if (this.hasUp()) {
      System.out.print("┴");
    } else {
      System.out.print("─");
    }
    System.out.print("─┐\n");
    System.out.print(" ");
    // left?
    if (this.hasLeft()) {
      System.out.print("─┤");
    } else {
      System.out.print(" │");
    }
    System.out.print(" " + tag + " ");
    // right?
    if (this.hasRight()) {
      System.out.print("├─");
    } else {
      System.out.print("│");
    }
    System.out.print("\n");
    System.out.print("  └─");
    // down?
    if (this.hasDown()) {
      System.out.print("┬");
    } else {
      System.out.print("─");
    }
    System.out.print("─┘\n\n");
  }

  /**
   * @param numRooms the numRooms to set
   */
  public static void setNumRooms(int numRooms) {
    Room.numRooms = numRooms;
  }

  /**
   * @param isGoal set true to set current room to the goal Room, false otherwise
   */
  public void setGoal(boolean isGoal) {
    if (isGoal) {
      goalRoom = this;
    } else {
      if (goalRoom == this) {
        goalRoom = null;
      }
    }
  }

  /**
   * @return true if Room has a Room connected above it, false if not
   */
  public boolean hasUp() {
    return up != null;
  }

  /**
   * @return true if Room has a Room connected below it, false if not
   */
  public boolean hasDown() {
    return down != null;
  }

  /**
   * @return true if Room has a Room connected to the left of it, false if not
   */
  public boolean hasLeft() {
    return left != null;
  }

  /**
   * @return true if Room has a Room connected to the right of it, false if not
   */
  public boolean hasRight() {
    return right != null;
  }

  /**
   * @return true if the Room is the goal room, false otherwise
   */
  public boolean isGoal() {
    return goalRoom == this;
  }

  /**
   * @return the Room above
   */
  public Room getUp() {
    return up;
  }

  /**
   * @return the Room below
   */
  public Room getDown() {
    return down;
  }

  /**
   * @return the Room to the left
   */
  public Room getLeft() {
    return left;
  }

  /**
   * @return the Room to the right
   */
  public Room getRight() {
    return right;
  }

  /**
   * @param up Room to connect to
   */
  public void setUp(Room up) {
    this.up = up;
  }

  /**
   * @param down Room to connect to
   */
  public void setDown(Room down) {
    this.down = down;
  }

  /**
   * @param left Room to connect to
   */
  public void setLeft(Room left) {
    this.left = left;
  }

  /**
   * @param right Room to connect to
   */
  public void setRight(Room right) {
    this.right = right;
  }

  /**
   * @return if Room has been explored
   */
  public boolean isExplored() {
    return isExplored;
  }

  /**
   * @param explored the value of if the Room has been explored
   */
  public void setExplored(boolean explored) {
    this.isExplored = explored;
  }

  /**
   * @return the id of the Room
   */
  public int getId() {
    return id;
  }
}
