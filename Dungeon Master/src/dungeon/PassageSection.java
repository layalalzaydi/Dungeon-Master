package dungeon;

import dnd.die.D20;
import dnd.models.ChamberShape;
import dnd.models.Monster;

public class PassageSection {

    /**
     * door instance variable of type Door.
     */
    private Door door;

    /**
     * monster instance variable of type Monster.
     */
    private Monster monster;

    /**
     * chamber instance variable of type Chamber.
     */
    private Chamber chamber;

    /**
     * parentPassage instance variable of type Passage.
     */
    private Passage parentPassage;

    /**
     * sectionDescription instance variable of type String.
     */
    private String sectionDescription;

    /**
     * PassageSection constructor.
     */
    public PassageSection() {
        this.passageSectionListN();
    }

    /**
     * PassageSection method that sets the passage section accourding ot the
     * description.
     *
     * @param description -- description string.
     */
    public PassageSection(String description) {
        this.setDescription(description);
    }

    /**
     * setDescription method that sets the description of the passage section
     * accourding to the description string.
     *
     * @param description -- description string.
     */
    private void setDescription(String description) {
//sets up a specific passage based on the values sent in from
//modified table 1
        sectionDescription = description;
        if (description.contains("Door to a Chamber")) {
            door = new Door();
            chamber = new Chamber();
            chamber.setDoor(door);
            sectionDescription = sectionDescription + "\n" + chamber.getDescription();

        } else if (description.contains("Monster")) {
            monster = new Monster();
            this.setMonster(monster);
            monster.setType(new D20().roll());
        } else if (description.contains("archway")) {
            door = new Door();
            door.setArchway(true); //set it as an archway
        } else {
            sectionDescription = sectionDescription + description;
        }
    }

    /**
     * passageSectionListN method that sets the contents randomly accourding to
     * a rol die.
     */
    private void passageSectionListN() {

        int roll = new D20().roll();

        if (roll >= 1 && roll <= 2) {
            sectionDescription = "passage goes straight for 10 ft";

        } else if (roll >= 3 && roll <= 5) {
            door = new Door();
            chamber = new Chamber();
            chamber.setDoor(door);
            chamber.setShape(ChamberShape.selectChamberShape(roll)); //chamber.setShape(new ChamberShape());
            sectionDescription = "passage ends in Door to a Chamber";

        } else if (roll >= 6 && roll <= 7) {
            sectionDescription = "archway (door) to right (main passage continues straight for 10 ft)";

        } else if (roll >= 8 && roll <= 9) {
            sectionDescription = "archway (door) to left (main passage continues straight for 10 ft)";

        } else if (roll >= 10 && roll <= 11) {
            sectionDescription = "passage turns to left and continues for 10 ft";

        } else if (roll >= 12 && roll <= 13) {
            sectionDescription = "passage turns to right and continues for 10 ft";

        } else if (roll >= 14 && roll <= 16) {
            sectionDescription = "passage ends in archway (door) to chamber";

        } else if (roll == 17) {
            sectionDescription = "Stairs, (passage continues straight for 10 ft)";

        } else if (roll >= 18 && roll <= 19) {
            sectionDescription = "Dead End";

        } else if (roll == 20) {
            monster = new Monster();
            monster.setType(roll);
            sectionDescription = "Wandering Monster (passage continues straight for 10 ft)";
        } else {
            sectionDescription = "ERROR, the number should be between 1 and 20";
        }
    }

    /**
     * getDoor method that returns a door.
     *
     * @return -- returns a door.
     */
    public Door getDoor() {
//returns the door that is in the passage section, if there is one
        return door;
    }

    /**
     * getMonster method that returns a monster.
     *
     * @return -- returns a monster.
     */
    public Monster getMonster() {
//returns the monster that is in the passage section, if there is one
        return monster;
    }

    /**
     * setMonster method that sets the monster.
     *
     * @param mnstr of type monster.
     */
    public void setMonster(Monster mnstr) {
//add Or replace the monster that is in the passage section, if there is one
//user made
        monster = mnstr;
    }

    /**
     * getDescription method returns the description of the passage section.
     *
     * @return sectionDescription string.
     */
    public String getDescription() {
        return sectionDescription;
    }

    /**
     * setparentPassage method that sets the parent passage.
     *
     * @param parentPassageadded parentPassageadded.
     */
    public void setparentPassage(Passage parentPassageadded) {
        parentPassage = parentPassageadded;
//door.setSpaces(chamber, parentPassage);
    }
}
