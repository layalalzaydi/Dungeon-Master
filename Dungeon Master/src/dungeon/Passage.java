package dungeon;

import dnd.die.D20;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import java.util.HashMap;

public class Passage extends Space {

    /**
     * passageList instance variable of type array list of passage section.
     */
    private ArrayList<PassageSection> passageList;

    /**
     * doorMap instance variable that maps a door to psaage section.
     */
    private HashMap<Door, PassageSection> doorMap;

    /**
     * doorsList instance variable of type array list of door.
     */
    private ArrayList<Door> doorsList;

    /**
     * passageDescription instance variable of type string.
     */
    private String passageDescription;

    private String passageName;
    private ArrayList<Treasure> treasureList;
    private ArrayList<Monster> monsterList;

    /**
     * init method that creates new objects of the instance variables.
     */
    public void init() {
        passageList = new ArrayList<PassageSection>();
        doorsList = new ArrayList<Door>();
        doorMap = new HashMap<Door, PassageSection>();
        treasureList = new ArrayList< Treasure>();
        monsterList = new ArrayList< Monster>();
        passageDescription = "";
    }

    /**
     * Passage constructor that calles the init method.
     */
    public Passage() {
        this.init();
    }

    /**
     * getDoors method that returns the rray list of doors.
     *
     * @return doorsList.
     */
    public ArrayList<Door> getDoors() {
//gets all of the doors in the entire passage
        return doorsList;
    }

    /**
     * getDoor method that returns a door in the array list of doors.
     *
     * @param i -- int i
     * @return doorsList.get(i).
     */
    public Door getDoor(int i) {
//returns the door in section 'i'. If there is no door, returns null
        if (this.doorsList.size() >= i) {
            return doorsList.get(i);
        } else {
            return null;
        }
    }

    /**
     * addMonster method thats adds a monster to a specific index in the array.
     *
     * @param theMonster theMonster.
     * @param i -- int i.
     */
    public void addMonster(Monster theMonster, int i) {
// adds a monster to section 'i' of the passage
        passageList.get(i).setMonster(theMonster);
        passageDescription = passageDescription + theMonster.getDescription();
    }

    /**
     * getMonster returns the monsterin that specific index i.
     *
     * @param i - int i.
     * @return a monster object.
     */
    public Monster getMonster(int i) {
//returns Monster door in section 'i'. If there is no Monster, returns null
        if (passageList.size() > i) {
            return passageList.get(i).getMonster();
        } else {
            return null;
        }
    }

    /**
     * addPassageSection method adds a passage section to an arra list of
     * passage sections.
     *
     * @param toAdd -- toAdd object of type passage section.
     */
    public void addPassageSection(PassageSection toAdd) {
//adds the passage section to the passageway
        passageList.add(toAdd);
        toAdd.setparentPassage(this);
        passageDescription = toAdd.getDescription();
    }

    /**
     * setDoor method that sets the door connection.
     *
     * @param newDoor -- newDoor of type door.
     */
    @Override
    public void setDoor(Door newDoor) {
//should add a door connection to the current Passage Section
        doorsList.add(newDoor);
        newDoor.setSpaceList(this);
    }

    /**
     * getDescription gets the description of the passage.
     *
     * @return -- passageDescription string of the passage description.
     */
    @Override
    public String getDescription() {
        passageDescription = ("The Passage Name is: " + getPassageName() + "\n"); //Passage name
        passageDescription += ("Passage has two PassageSections which are:");
        for (int ps = 0; ps < getPassageSection().size(); ps++) {
            passageDescription += ("\n" + "PassageSection (" + (ps + 1) + ") " + getPassageSection().get(ps).getDescription());
        }
        for (int i = 0; i < getTreasureList().size(); i++) { // Passage Treasure List Description
            passageDescription += "\n Treasure: " + getTreasureList().get(i).getContainer();
            passageDescription += "\t" + getTreasureList().get(i).getWholeDescription();
        }
        for (int i = 0; i < getMonsters().size(); i++) {// Passage Monster List Description
            passageDescription += "\n Monster: " + getMonsters().get(i).getDescription();
        }
        return passageDescription;
    }

    /**
     * setDoorPassageSection method that sets the passage section.
     *
     * @param newDoor -- newDoor of type door.
     * @param passageSection -- new passageSection of type passage sction
     */
    public void setDoorPassageSection(Door newDoor, PassageSection passageSection) {
        doorMap.put(newDoor, passageSection);
    }

    /**
     * getDoorPassageSection method that maps a door to a passage scetion.
     *
     * @return the doorMap.
     */
    public HashMap<Door, PassageSection> getDoorPassageSection() {
        return doorMap;
    }

    /**
     * getPassageSection method that returns the passage list.
     *
     * @return passageList.
     */
    public ArrayList<PassageSection> getPassageSection() {
        return passageList;
    }

    public String getPassageName() {
        return passageName;
    }

    public void setPassageName(String passageName) {
        this.passageName = passageName;
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
