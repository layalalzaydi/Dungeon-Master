package dungeon;

import dnd.die.D20;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import dnd.exceptions.UnusualShapeException;
import dnd.models.Exit;

public class Chamber extends Space {

    /**
     * myContents instance variable of type ChamberContents.
     */
    private ChamberContents myContents;

    /**
     * mySize instance variable of type ChamberShape.
     */
    private ChamberShape mySize;

    /**
     * doorList instance variable whhic is an ArrayList of Doors.
     */
    private ArrayList<Door> doorList;

    /**
     * monsterList instance variable whhic is an ArrayList of monsters.
     */
    private ArrayList<Monster> monsterList;

    /**
     * treasureList instance variable whhic is an ArrayList of treasures.
     */
    private ArrayList<Treasure> treasureList;

    /**
     * exitList instance variable whhic is an ArrayList of exits.
     */
    private ArrayList<Exit> exitList;

    /**
     * chamberDescription instance variable of type string that is initialized
     * to null.
     */
    private String chamberDescription = "";

    /**
     * chamberName instance variable of type string.
     */
    private String chamberName;

    /**
     * init method that create a new array list of different tyes called in the
     * constructor.
     */
    public void init() {
        this.doorList = new ArrayList<Door>();
        this.exitList = new ArrayList<Exit>();
        this.monsterList = new ArrayList<Monster>();
        this.treasureList = new ArrayList<Treasure>();
    }

    /**
     * chamber constructor with no parameters.
     */
    public Chamber() {
        this.init();
        int roll = new D20().roll();
        mySize = ChamberShape.selectChamberShape(roll);
        myContents = new ChamberContents();
        myContents.chooseContents(roll);
    }

    /**
     * chamber constructor with parameters.
     *
     * @param theShape theShape.
     * @param theContents theContents.
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        this.init();
        mySize = theShape;
        myContents = theContents;
    }

    /**
     * setShape method that sets the shape of the chamber.
     *
     * @param theShape theShape.
     */
    public void setShape(ChamberShape theShape) {
        this.mySize = theShape;
    }

    /**
     * get Doors method return the array list of doors.
     *
     * @return doorList.
     */
    public ArrayList<Door> getDoors() {
        return doorList;
    }

    /**
     * addMonster method.
     *
     * @param theMonster theMonster.
     */
    public void addMonster(Monster theMonster) {
        monsterList.add(theMonster);
    }

    /**
     * getMonsters method that retuns the array list of monsters.
     *
     * @return monsterList.
     */
    public ArrayList<Monster> getMonsters() {
        return monsterList;
    }

    /**
     * addTreasure method that adds a treasure to the array list of treasures.
     *
     * @param theTreasure theTreasure.
     */
    public void addTreasure(Treasure theTreasure) {
        this.treasureList.add(theTreasure);
    }

    /**
     * getTreasureList adds a treasure to the array list of treasureList.
     *
     * @return treasureList.
     */
    public ArrayList<Treasure> getTreasureList() {
        return this.treasureList;
    }

    /**
     * getDescription method that returns the description of the chamber
     * contents and chamber shape.
     *
     * @return chamberDescription.
     */
    @Override
    public String getDescription() {
        chamberDescription = getShapeDescription() + getContentsDescription();
        return chamberDescription;
    }

    /**
     * setDoor method that sets the door.
     *
     * @param newDoor newDoor.
     */
    @Override
    public void setDoor(Door newDoor) {
//should add a door connection to this room
        doorList.add(newDoor);
        newDoor.setSpaceList(this);
    }

    /**
     * getNumExits that retuens the number of exits.
     *
     * @return exitNum.
     */
    public int getNumExits() {
        int exitNum = this.mySize.getNumExits();
        if (exitNum == 0) {
            exitNum++;
        }
        return exitNum;
    }

    /**
     * getShape method that returns the chamber shape object.
     *
     * @return mySize.
     */
    public ChamberShape getShape() {
        return this.mySize;
    }

    /**
     * getContents method that returns the content of the chamber.
     *
     * @return myContents.
     */
    public ChamberContents getContents() {
        return this.myContents;
    }

    /**
     * setContents method that sets the contents of the chamber.
     *
     * @param theContents theContents.
     */
    public void setContents(ChamberContents theContents) {
        this.myContents = theContents;
    }

    /**
     * setExitDoor method that sets the number of exits in a chamber.
     *
     * @param newExit newExit.
     */
    public void setExitDoor(Exit newExit) {
        exitList.add(newExit);
    }

    /**
     * getExitDoor method returns the exitlist.
     *
     * @return exitList.
     */
    public ArrayList<Exit> getExitDoor() {
        return exitList;
    }

    /**
     * getShapeDescription method returns the description of the shape of the
     * chamber.
     *
     * @return shapeDescription;
     */
    public String getShapeDescription() {
        String shapeDescription = "";
        try {
            shapeDescription = ("Chamber Shape Is: " + mySize.getShape() + ". \n") + ("The Length of the Chamber Is " + mySize.getLength() + " Long And Width " + mySize.getWidth() + ".");

        } catch (UnusualShapeException e) {
            shapeDescription = ("Chamber Area Is: " + mySize.getArea() + ".");
        }
        return shapeDescription;
    }

    /**
     * getContentsDescription method returns the description of the contents of
     * the chamber.
     *
     * @return contentsDescription.
     */
    public String getContentsDescription() {
        String contentsDescription = "";
        contentsDescription = "Contents Of The Chamber Is: " + this.myContents.getDescription() + ". ";
        return contentsDescription;
    }

    /**
     * getExitsDescrption method thar returns the description of the exit.
     *
     * @return exitDescription.
     */
    public String getExitsDescrption() {
        String exitDescription = "";
        for (int i = 0; i < exitList.size(); i++) {
            exitDescription += "\n";
            //exitDescription += ("Exit Door No (" + (i + 1) + "): ");
            exitDescription += ("Exit Door " + getDoors().get(i).getDoorName() + " : ");
            exitDescription += ("has the direction: " + exitList.get(i).getDirection() + "  ");
            exitDescription += ("And the Location is: " + exitList.get(i).getLocation() + ".");
        }
        return exitDescription;
    }

    public String getChamberDescription() {
        chamberDescription = ("The Chamber Name is: " + getChamberName() + "\n"); //chamber name
        chamberDescription += ("The Chamber's contents are/is : " + getContentsDescription() + "\n"); //chamber contents
        chamberDescription += ("The Chamber's Shape is : " + getShapeDescription() + "\n"); //chamber shape
        chamberDescription += "Chamber has (" + getDoors().size() + ") Doors " + "\n"; // chamber Exit No
        chamberDescription += "with the following Descrptions: " + getExitsDescrption();//chamber Exits Descr

        for (int i = 0; i < getTreasureList().size(); i++) { // chamber Treasure List Description
            chamberDescription += "\n Treasure: " + getTreasureList().get(i).getContainer();
            chamberDescription += "\t" + getTreasureList().get(i).getWholeDescription();
        }
        for (int i = 0; i < getMonsters().size(); i++) {// chamber Monster List Description
            chamberDescription += "\n Monster: " + getMonsters().get(i).getDescription();
        }

        return chamberDescription;
    }

    /**
     * setChamberName method sets the name of the chamber.
     *
     * @param chamberName chamberName.
     */
    public void setChamberName(String chamberName) {
        this.chamberName = chamberName;
    }

    /**
     * getChamberName method that returns the name of the chamber.
     *
     * @return chamberName;
     */
    public String getChamberName() {
        return chamberName;
    }

    public void addNewTreasure() {
        Treasure treasure = new Treasure();
        treasure.setContainer(new D20().roll());
        addTreasure(treasure);
    }

    public void removeTreasure() {
        if (getTreasureList().size() > 0) {
            getTreasureList().remove(getTreasureList().size() - 1);
        }
    }

    public void addNewMonster() {
        Monster monster = new Monster();
        monster.setType(new D20().roll());
        addMonster(monster);
    }

    public void removeMonster() {
        if (getMonsters().size() > 0) {
            getMonsters().remove(getMonsters().size() - 1);
        }
    }

}
