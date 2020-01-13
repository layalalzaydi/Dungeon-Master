package gui;

import dungeon.Chamber;
import dungeon.Door;
import dungeon.Generatelevel;
import dungeon.Passage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author
 */
public class dungeanController {

    private dungeanGui dungeangui;
    private Generatelevel generatelevel;
    private FileChooser fileChooser;
    private FileChooser.ExtensionFilter extFilter;
    private File file;
    private fileSavingSerial FileSerial;

    public dungeanController(dungeanGui dungeangui) {
        this.dungeangui = dungeangui;
        setGeneratelevel(new Generatelevel());
    }

    public dungeanGui getDungeangui() {
        return dungeangui;
    }

    public void setDungeangui(dungeanGui dungeangui) {
        this.dungeangui = dungeangui;
    }

    public void setGeneratelevel(Generatelevel generatelevel) {
        this.generatelevel = generatelevel;
        this.generatelevel.setChambersList();
        this.generatelevel.setDoorHashMap();
        this.generatelevel.setDoorPassage();
        this.generatelevel.setChamberPassgesList();
    }

    public void setChamberLisViews() {
        for (int i = 0; i < generatelevel.getChamberPassgesList().size(); i++) {
            if (generatelevel.getChamberPassgesList().get(i) instanceof Passage) {
                Passage pass = (Passage) generatelevel.getChamberPassgesList().get(i);
                dungeangui.getChambersListView().getItems().add(pass.getPassageName());
            }
            if (generatelevel.getChamberPassgesList().get(i) instanceof Chamber) {
                Chamber chamb = (Chamber) generatelevel.getChamberPassgesList().get(i);
                dungeangui.getChambersListView().getItems().add(chamb.getChamberName());
            }
        }
    }

    public void setChamberPassageDoorsList(int indx) {
        dungeangui.getChamberPassageDoors().getItems().clear();
        if (isSelectedChamberInst(indx)) {
            for (int i = 0; i < getSelectedChamberInst(indx).getDoors().size(); i++) {
                dungeangui.getChamberPassageDoors().getItems().add(getSelectedChamberInst(indx).getDoors().get(i).getDoorName());
            }
        }
        if (isSelectedPassgeInst(indx)) {
            for (int i = 0; i < getSelectedPassgeInst(indx).getDoors().size(); i++) {
                dungeangui.getChamberPassageDoors().getItems().add(getSelectedPassgeInst(indx).getDoors().get(i).getDoorName());
            }
        }
    }

    public Chamber getSelectedChamberInst(int indx) {
        if (indx >= 0) {
            if (generatelevel.getChamberPassgesList().get(indx) instanceof Chamber) {
                return (Chamber) generatelevel.getChamberPassgesList().get(indx);
            }
        }
        return null;
    }

    public boolean isSelectedChamberInst(int indx) {
        if (getSelectedChamberInst(indx) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Passage getSelectedPassgeInst(int indx) {
        if (indx >= 0) {
            if (generatelevel.getChamberPassgesList().get(indx) instanceof Passage) {
                return (Passage) generatelevel.getChamberPassgesList().get(indx);
            }
        }
        return null;
    }

    public boolean isSelectedPassgeInst(int indx) {
        if (getSelectedPassgeInst(indx) != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getChamberDescription(int indx) {
        String describtion = "";
        if (isSelectedChamberInst(indx)) {
            describtion = "Chamber Describtion: \n" + getSelectedChamberInst(indx).getChamberDescription();
        }
        if (isSelectedPassgeInst(indx)) {
            describtion = "Passage Describtion: \n" + getSelectedPassgeInst(indx).getDescription();
        }
        return describtion;
    }

    public String getChamberDoorDescriotion(int indxChamber, int indxDoor) {
        String describtion = "";
        if (isSelectedChamberInst(indxChamber)) {
            describtion = "Door " + getSelectedChamberInst(indxChamber).getDoors().get(indxDoor).getDoorName() + " Target to : \n";
            describtion += generatelevel.getDoorTargets(getSelectedChamberInst(indxChamber).getDoors().get(indxDoor));
        }
        if (isSelectedPassgeInst(indxChamber)) {
            describtion = "Door " + getSelectedPassgeInst(indxChamber).getDoors().get(indxDoor).getDoorName() + " Target to : \n";
            describtion += generatelevel.getDoorTargets(getSelectedPassgeInst(indxChamber).getDoors().get(indxDoor));
        }
        return describtion;
    }

    public void saveTextToFile(String content, Stage stage) {
        FileSerial = new fileSavingSerial();
        FileSerial.decription = content;

        fileChooser = new FileChooser();
        extFilter = new FileChooser.ExtensionFilter("SERIAL files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(FileSerial);
            } catch (IOException i) {
            }
        }
    }

    public String readTextFromFile(Stage stage) {
        fileChooser = new FileChooser();
        extFilter = new FileChooser.ExtensionFilter("SERIAL files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(stage);
        FileSerial = null;
        if (file != null) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                FileSerial = (fileSavingSerial) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                return "";
            } catch (ClassNotFoundException c) {
                System.out.println("fileSerial class not found");
                return "";
            }
        }
        return FileSerial.decription;
    }

    public void addTreasureButton(int indxChamber) {
        if (isSelectedChamberInst(indxChamber)) {
            getSelectedChamberInst(indxChamber).addNewTreasure();
        }
        if (isSelectedPassgeInst(indxChamber)) {
            getSelectedPassgeInst(indxChamber).addNewTreasure();
        }
    }

    public void removeTreasureButton(int indxChamber) {
        if (isSelectedChamberInst(indxChamber)) {
            getSelectedChamberInst(indxChamber).removeTreasure();
        }
        if (isSelectedPassgeInst(indxChamber)) {
            getSelectedPassgeInst(indxChamber).removeTreasure();
        }
    }

    public void addNewMonsterButton(int indxChamber) {
        if (isSelectedChamberInst(indxChamber)) {
            getSelectedChamberInst(indxChamber).addNewMonster();
        }
        if (isSelectedPassgeInst(indxChamber)) {
            getSelectedPassgeInst(indxChamber).addNewMonster();
        }
    }

    public void removeMonsterButton(int indxChamber) {
        if (isSelectedChamberInst(indxChamber)) {
            getSelectedChamberInst(indxChamber).removeMonster();
        }
        if (isSelectedPassgeInst(indxChamber)) {
            getSelectedPassgeInst(indxChamber).removeMonster();
        }
    }
}
