package dungeon;

public abstract class Space {

    /**
     * getDescription abrstact method.
     *
     * @return a string description.
     */
    public abstract String getDescription();

    /**
     * setDoor method that sets the door.
     *
     * @param theDoor -- theDoor of type door.
     */
    public abstract void setDoor(Door theDoor);

}
