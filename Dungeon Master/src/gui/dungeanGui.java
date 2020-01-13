package gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class dungeanGui extends Application {

    private dungeanController controller;

    private Menu mainMenu;
    private MenuBar menuBar;

    private VBox leftvb;
    private VBox hvbx;
    private FlowPane editBtnFlowPane;
    private BorderPane rootNode;
    private Scene rootScene;

    private Button editButton;
    private Label listViewlabel;
    private TextArea chamberDescriptionTxtA;
    private ComboBox chamberPassageDoors;
    private ListView chambersListView;
    private Popup popup;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setController(new dungeanController(this));

        rootNode = new BorderPane();
        rootNode.setRight(getRightNode());
        rootNode.setTop(getTopNode());
        rootNode.setBottom(getBottomNode());
        rootNode.setCenter(getCenterNode());
        rootNode.setLeft(getLeftNode());

        createChamberListView();
        createchamberDoors();

        rootScene = new Scene(rootNode, 1000, 600);
        rootScene.getStylesheets().add("file:res/stylesCss.css");

        primaryStage.setTitle("Hello dungean GUI!!");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    private Node getRightNode() {
        hvbx = new VBox();
        hvbx.setManaged(true);
        hvbx.setPrefSize(200, 400);
        hvbx.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");
        hvbx.setAlignment(Pos.TOP_CENTER);
        listViewlabel = new Label("list of Chambers&Passages Doors");
        listViewlabel.setWrapText(true);
        hvbx.getChildren().add(listViewlabel);
        return hvbx;
    }

    private Node getTopNode() {
        mainMenu = new Menu("File");
        MenuItem saveMenuItem = new MenuItem("Save File");
        MenuItem loaadMenuItem = new MenuItem("Load File");
        mainMenu.getItems().addAll(saveMenuItem, loaadMenuItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(mainMenu);

        saveMenuItem.setOnAction((ActionEvent t) -> {
            String textData = chamberDescriptionTxtA.getText().trim();
            if (!textData.equals("")) {
                controller.saveTextToFile(chamberDescriptionTxtA.getText(), primaryStage);
            }
        });

        loaadMenuItem.setOnAction((ActionEvent arg0) -> {
            chamberDescriptionTxtA.setText(
                    controller.readTextFromFile(primaryStage)
            );
        });
        return menuBar;
    }

    private Node getBottomNode() {
        editBtnFlowPane = new FlowPane();
        editButton = new Button("Edit");
        editButton.setPrefSize(100, 50);
        editBtnFlowPane.getChildren().add(editButton);
        editBtnFlowPane.setMargin(editButton, new Insets(20, 0, 20, 20));

        Image image = new Image("file:res/editBtn.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(30);

        editButton.setGraphic(imageView);

        editButton.setOnAction(e -> {
            if (chambersListView.getSelectionModel().getSelectedIndex() != -1) {
                editButtonPopUp();
            }
        });

        return editBtnFlowPane;
    }

    private Node getCenterNode() {
        chamberDescriptionTxtA = new TextArea();
        chamberDescriptionTxtA.setMinWidth(80);
        chamberDescriptionTxtA.setMinHeight(50);
        chamberDescriptionTxtA.setEditable(false);
        chamberDescriptionTxtA.setWrapText(true);
        return chamberDescriptionTxtA;
    }

    private Node getLeftNode() {
        leftvb = new VBox();
        leftvb.setAlignment(Pos.TOP_CENTER);
        leftvb.setManaged(true);
        leftvb.setPrefSize(200, 400);

        leftvb.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");
        listViewlabel = new Label("list of Chambers and Passages");
        listViewlabel.setWrapText(true);
        leftvb.getChildren().add(listViewlabel);
        return leftvb;
    }

    private void createChamberListView() {
        chambersListView = new ListView();
        chambersListView.setPrefSize(150, 400);
        chambersListView.setEditable(true);
        leftvb.getChildren().add(chambersListView);

        controller.setChamberLisViews();

        chambersListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                chamberDescriptionTxtA.setText(new_val);
                chamberDescriptionTxtA.setText(controller.getChamberDescription(chambersListView.getSelectionModel().getSelectedIndex()));
                if (chambersListView.getSelectionModel().getSelectedIndex() < 0) {
                    chambersListView.getSelectionModel().selectLast();
                }
                controller.setChamberPassageDoorsList(chambersListView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    private void createchamberDoors() {
        chamberPassageDoors = new ComboBox();
        chamberPassageDoors.setPrefSize(200, 20);
        hvbx.getChildren().add(chamberPassageDoors);

        chamberPassageDoors.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                String doorDescription = controller.getChamberDoorDescriotion(chambersListView.getSelectionModel().getSelectedIndex(), chamberPassageDoors.getSelectionModel().getSelectedIndex());

                Label label = new Label("");
                label.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10; -fx-text-fill: white;");
                label.setText(doorDescription);
                label.setMaxWidth(300);
                label.setMinHeight(100);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setWrapText(true);

                popup = new Popup();
                popup.getContent().add(label);
                popup.setHideOnEscape(true);
                popup.setAutoHide(true);
                popup.setAutoFix(true);
                popup.setOpacity(0.85);
                popup.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_BOTTOM_LEFT);
                popup.show(primaryStage);
            }
        });
    }

    private void editButtonPopUp() {

        Button addTreasure = new Button("Add Treasure");
        Button addMonsters = new Button("Add Monster");
        Button removeTreasure = new Button("Remove Treasure");
        Button removeMonster = new Button("Remove Monster");

        HBox secondaryLayout = new HBox();
        secondaryLayout.setAlignment(Pos.TOP_CENTER);
        secondaryLayout.setPadding(new Insets(30, 20, 30, 20));
        secondaryLayout.setSpacing(10);
        secondaryLayout.setStyle("-fx-background-color: #336699;");

        secondaryLayout.getChildren().addAll(addTreasure, addMonsters, removeTreasure, removeMonster);
        Scene secondScene = new Scene(secondaryLayout, 600, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("add or remove Monsters or Treasure");
        newWindow.setScene(secondScene);
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 300);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);
        newWindow.setResizable(false);
        newWindow.show();

        addTreasure.setOnAction(e -> {
            controller.addTreasureButton(chambersListView.getSelectionModel().getSelectedIndex());
            chamberDescriptionTxtA.setText(controller.getChamberDescription(chambersListView.getSelectionModel().getSelectedIndex()));
        });
        removeTreasure.setOnAction(e -> {
            controller.removeTreasureButton(chambersListView.getSelectionModel().getSelectedIndex());
            chamberDescriptionTxtA.setText(controller.getChamberDescription(chambersListView.getSelectionModel().getSelectedIndex()));
        });

        addMonsters.setOnAction(e -> {
            controller.addNewMonsterButton(chambersListView.getSelectionModel().getSelectedIndex());
            chamberDescriptionTxtA.setText(controller.getChamberDescription(chambersListView.getSelectionModel().getSelectedIndex()));
        });

        removeMonster.setOnAction(e -> {
            controller.removeMonsterButton(chambersListView.getSelectionModel().getSelectedIndex());
            chamberDescriptionTxtA.setText(controller.getChamberDescription(chambersListView.getSelectionModel().getSelectedIndex()));
        });

    }

    public void setController(dungeanController controller) {
        this.controller = controller;
    }

    public ListView getChambersListView() {
        return chambersListView;
    }

    public ComboBox getChamberPassageDoors() {
        return chamberPassageDoors;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
