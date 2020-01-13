package dungeon;

import dnd.die.D20;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;

public class Door {
//instance variables

    /**
     * trap instance variable of type Trap.
     */
    private Trap trap;

    /**
     * exit instance variable of type Exit.
     */
    private Exit exit;

    /**
     * trapped instance boolean variable.
     */
    private boolean trapped;

    /**
     * open instance boolean variable.
     */
    private boolean open;

    /**
     * locked instance boolean variable.
     */
    private boolean locked;

    /**
     * archway instance boolean variable.
     */
    private boolean archway;

    /**
     * doorName instance boolean variable.
     */
    private String doorName;

    /**
     * spaceList instance variable of type array list of spaces.
     */
    private ArrayList<Space> spaceList; //add a getter method

    /**
     * init method that created a new array list of different types, that is
     * called by the constructor.
     */
    public void init() {
        trapped = false;
        open = false;
        locked = false;
        archway = false;
    }

    /**
     * Door constructor that calls the init method.
     */
    public Door() {
        this.init();
        exit = new Exit();
        spaceList = new ArrayList<Space>(2);
    }

    /**
     * Door constructor that calls the init method that has a parameter.
     *
     * @param theExit theExit.
     */
    public Door(Exit theExit) {
//sets up the door based on the Exit from the tables
        this.init();
        exit = theExit;
    }

    /**
     * setTrapped method sets the door to be trapped.
     *
     * @param flag -- boolean variable.
     * @param roll -- an integer.
     */
    public void setTrapped(boolean flag, int... roll) {
// true == trapped.  Trap must be rolled if no integer is given
        trapped = flag;
        if (flag) {
            trap = new Trap();
            if (roll.length == 1) {
                trap.chooseTrap(roll[0]);
            } else {
                trap.chooseTrap(new D20().roll());
            }
        }
    }

    /**
     * setOpen method sets the door to be opened.
     *
     * @param flag -- boolean variable.
     */
    public void setOpen(boolean flag) {
//true == open
        if ((flag) || isArchway()) {
            open = true;
//locked = false;
        } else {
// open = false;
            locked = true;
        }
    }

    /**
     * setArchway method that sets the door to be an archway.
     *
     * @param flag -- boolean variable.
     */
    public void setArchway(boolean flag) {
//true == is archway
        archway = flag;
        if ((flag)) {
            open = true;
//locked = false;
// trapped = false;
        } else {
// open = false;
            locked = true;
        }
    }

    /**
     * isTrapped method checks of the door is trapped.
     *
     * @return trapped.
     */
    public boolean isTrapped() {
        return this.trapped;
    }

    /**
     * isOpen method checks if the door is opened.
     *
     * @return open.
     */
    public boolean isOpen() {
        return this.open;
    }

    /**
     * isArchway method checks if the door is an archway.
     *
     * @return archway.
     */
    public boolean isArchway() {
        return this.archway;
    }

    /**
     * getTrapDescription returns the description of the trap.
     *
     * @return trap.getDescription().
     */
    public String getTrapDescription() {
        if (isTrapped()) {
            return trap.getDescription();
        } else {
            return null;
        }
    }

    /**
     * getSpaces method that returns an array list of spaces.
     *
     * @return spaceList.
     */
    public ArrayList<Space> getSpaces() {
//returns the two spaces that are connected by the door
        return spaceList;
    }

    /**
     * setSpaces sets the 2 spaces to te door.
     *
     * @param spaceOne spaceOne.
     * @param spaceTwo spaceTwo.
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
//identifies the two spaces with the door
// this method should also call the addDoor method from Space
//spaceOne.setDoor(this);
        spaceList.add(spaceOne);
//spaceTwo.setDoor(this);
        spaceList.add(spaceTwo);
    }

    /**
     * getDescription method returns the description of the door.
     *
     * @return doorDescription.
     */
    public String getDescription() {
        String doorDescription = "The Door is";
//checking for trapped
        if (isTrapped()) {
            doorDescription += " Trapped " + trap.getDescription();
        } else {
            doorDescription += " Not Trapped ";
        }
//checking for archway
        if (isArchway()) {
            doorDescription += " Is An Archway ";
        } else {
            doorDescription += " Is Not An Archway ";
        }
//checking for open
        if (isOpen()) {
            doorDescription += " Is Opened ";
        } else {
            doorDescription += " Is Closed ";
        }
        return doorDescription;
    }

    /**
     * getExit method that returns an exit.
     *
     * @return exit.
     */
    public Exit getExit() {
        return exit;
    }

    /**
     * setDoorName method that sets a name to the door.
     *
     * @param doorName doorName.
     */
    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    /**
     * getDoorName method that returns the name of the door.
     *
     * @return doorName.
     */
    public String getDoorName() {
        return doorName;
    }

    /**
     * setSpaceList method sets an array list of spaces.
     *
     * @param space space.
     */
    public void setSpaceList(Space space) {
        this.spaceList.add(space);
    }
}
