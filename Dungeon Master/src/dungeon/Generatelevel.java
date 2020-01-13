package dungeon;

import dnd.die.D20;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Generatelevel {

    private ArrayList<Object> chamberPassgesList;// for GUI ListView

    /**
     * chamberList instance variable of type array list of chambers.
     */
    private ArrayList<Chamber> chamberList;

    /**
     * passageList instance variable of type array list of passages.
     */
    private ArrayList<Passage> passageList;

    /**
     * connectionsMap instance variable that maps a door to an array list of
     * chambers.
     */
    private HashMap<Door, ArrayList<Chamber>> connectionsMap;

    /**
     * chamberCount int is the number of chambers.
     */
    private int chamberCount;

    /**
     * Generatelevel constructor.
     */
    public Generatelevel() {
        chamberList = new ArrayList<Chamber>();
        passageList = new ArrayList<Passage>();
        connectionsMap = new HashMap<Door, ArrayList<Chamber>>();
        chamberPassgesList = new ArrayList<Object>();// for GUI ListView
        chamberCount = 5;
    }

    public ArrayList<Passage> getPassageList() {// for GUI ListView
        return passageList;
    }

    public ArrayList<Chamber> getChamberList() {// for GUI ListView
        return chamberList;
    }

    public void setChamberPassgesList() {// for GUI ListView
        this.chamberPassgesList.addAll(getChamberList());
        this.chamberPassgesList.addAll(getPassageList());
    }

    public ArrayList<Object> getChamberPassgesList() {// for GUI ListView
        return chamberPassgesList;
    }

    /**
     * setChambersList method that sets the chamber list.
     */
    public void setChambersList() {
        int exitNum;
        for (int i = 1; i <= chamberCount; i++) {
            Chamber chamber = new Chamber();
            chamber.setChamberName("CH(" + (i) + ")");
            chamberList.add(chamber);
            exitNum = chamber.getNumExits();
            for (int k = 0; k < exitNum; k++) {
                Door chamberDoor = new Door();
                chamberDoor.setDoorName("D(" + (k + 1) + ")" + chamber.getChamberName());
                chamber.setDoor(chamberDoor);
                chamber.setExitDoor(chamberDoor.getExit());
            }
        }
    }

    /**
     * setDoorHashMap method that sets the door hashmap.
     */
    public void setDoorHashMap() {
        ArrayList<Chamber> tempChamberList;
        ArrayList<Chamber> revChamberList;
        ArrayList<Chamber> copyChamberList;

        for (int i = 0; i < chamberList.size(); i++) {

            copyChamberList = (ArrayList<Chamber>) chamberList.clone();
            copyChamberList.remove(chamberList.get(i));
            Random rand = new Random();
            for (int j = 0; j < chamberList.get(i).getDoors().size(); j++) {
                int reqTargets = rand.nextInt(copyChamberList.size());
                if (reqTargets == 0) {
                    reqTargets++;
                }
                tempChamberList = new ArrayList<Chamber>();

                for (int target = 0; target < reqTargets; target++) {
                    int randTarget = rand.nextInt(copyChamberList.size());
                    if (!tempChamberList.contains(copyChamberList.get(randTarget))) {
                        tempChamberList.add(copyChamberList.get(randTarget));
                    }
                }
                connectionsMap.put(chamberList.get(i).getDoors().get(j), tempChamberList);
                // two way direction Start
                for (int s = 0; s < tempChamberList.size(); s++) {
                    for (int n = 0; n < tempChamberList.get(s).getDoors().size(); n++) {
                        revChamberList = new ArrayList<Chamber>();
                        revChamberList.add(chamberList.get(i));
                        connectionsMap.put(tempChamberList.get(s).getDoors().get(n), revChamberList);
                    }
                }
            }
        }
    }

    /**
     * setDoorPassage method that links/sets the door with a passage.
     */
    public void setDoorPassage() {
        int roll;
        int chamberPassageNo = 0;
        ArrayList<Chamber> mapChamberList;
        Set set = connectionsMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            mapChamberList = (ArrayList<Chamber>) mentry.getValue();

            for (int k = 0; k < mapChamberList.size(); k++) {

                Passage instance = new Passage();
                chamberPassageNo++;
                instance.setPassageName("P(" + chamberPassageNo + ")" + mapChamberList.get(k).getChamberName());
                passageList.add(instance);
                roll = new D20().roll();
                String tableDescrptions = getPaasageTableDescrptions(roll);
                PassageSection ps1 = new PassageSection(tableDescrptions);
                roll = new D20().roll();
                tableDescrptions = getPaasageTableDescrptions(roll);
                PassageSection ps2 = new PassageSection(tableDescrptions);

                instance.addPassageSection(ps1);
                instance.addPassageSection(ps2);
                Door door = (Door) mentry.getKey();
                instance.setDoor(door);
                door.setDoorName(door.getDoorName());
                instance.setDoorPassageSection(door, ps1);
                instance.setDoorPassageSection(door, ps2);

                door.setSpaces(mapChamberList.get(k), instance);
            }
        }
    }

    /**
     * return Door Targets description of the whole level.
     */
    public String getDoorTargets(Door door) {
        String doorTargets = "";
        for (int ch = 0; ch < connectionsMap.get(door).size(); ch++) {
            doorTargets += connectionsMap.get(door).get(ch).getChamberName() + "\n";
        }
        return doorTargets;
    }

    /**
     * printLevel method that prints the description of the whole level.
     */
    private void printLevel() {
        ArrayList<Door> doorList;
        ArrayList<Space> doorSpaceList;
        LinkedHashSet<Space> hashSet;

        System.out.println("This Level Composed Of " + chamberList.size() + " Chambers And " + passageList.size() + " Passages as the following:");
        for (int i = 0; i < chamberList.size(); i++) {
            System.out.println("Chamber " + chamberList.get(i).getChamberName());
            System.out.println(chamberList.get(i).getDescription());
            System.out.print("Chamber has (" + chamberList.get(i).getDoors().size() + ") Doors ");
            System.out.println("with the following Descrptions: " + chamberList.get(i).getExitsDescrption());

            doorList = chamberList.get(i).getDoors();
            for (int j = 0; j < doorList.size(); j++) {
                System.out.print("Door " + doorList.get(j).getDoorName() + " Target to chambers: ");
                for (int ch = 0; ch < connectionsMap.get(doorList.get(j)).size(); ch++) {
                    System.out.print(connectionsMap.get(doorList.get(j)).get(ch).getChamberName() + " ");
                }
                System.out.println(" ");
                doorSpaceList = doorList.get(j).getSpaces();
                hashSet = new LinkedHashSet<>(doorSpaceList);
                doorSpaceList = new ArrayList<>(hashSet);

                for (int s = 0; s < doorSpaceList.size(); s++) {
                    if (doorSpaceList.get(s) instanceof Passage) {
                        Passage passge = (Passage) doorSpaceList.get(s);
                        System.out.println("Door connected to a Passage:");
                        System.out.println("The Passage Description: " + passge.getDescription());
                        System.out.println("Passage has two PassageSections which are:");

                        for (int ps = 0; ps < passge.getPassageSection().size(); ps++) {
                            System.out.println("PassageSection (" + (ps + 1) + ") " + passge.getPassageSection().get(ps).getDescription());
                        }
                    }
                }
            }
            System.out.println("\n");
        }
    }

    /**
     * getPaasageTableDescrptions method that gets the passage table
     * description.
     *
     * @param roll - int roll.
     * @return - paasageTable string.
     */
    private String getPaasageTableDescrptions(int roll) {
        String paasageTable;
        if (roll >= 1 && roll <= 2) {
            paasageTable = "passage goes straight for 10 ft";
        } else if (roll >= 3 && roll <= 5) {
            paasageTable = "passage ends in Door to a Chamber";
        } else if (roll >= 6 && roll <= 7) {
            paasageTable = "archway (door) to right (main passage continues straight for 10 ft)";
        } else if (roll >= 8 && roll <= 9) {
            paasageTable = "archway (door) to left (main passage continues straight for 10 ft)";
        } else if (roll >= 10 && roll <= 11) {
            paasageTable = "passage turns to left and continues for 10 ft";
        } else if (roll >= 12 && roll <= 13) {
            paasageTable = "passage turns to right and continues for 10 ft";
        } else if (roll >= 14 && roll <= 16) {
            paasageTable = "passage ends in archway (door) to chamber";
        } else if (roll == 17) {
            paasageTable = "Stairs, (passage continues straight for 10 ft)";
        } else if (roll >= 18 && roll <= 19) {
            paasageTable = "Dead End";
        } else if (roll == 20) {
            paasageTable = "Wandering Monster (passage continues straight for 10 ft)";
        } else {
            System.out.println("ERROR, the number should be between 1 and 20");
            paasageTable = "ERROR";
        }
        return paasageTable;
    }

    /**
     * main method.
     *
     * @param args args.
     */
    /*
    public static void main(String[] args) {
        Generatelevel generateLevel = new Generatelevel();
        generateLevel.setChambersList();
        generateLevel.setDoorHashMap();
        generateLevel.setDoorPassage();
        generateLevel.printLevel();
    }
     */
}
