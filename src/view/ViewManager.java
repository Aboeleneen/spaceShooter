/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

/**
 *
 * @author Lenovo
 */
public class ViewManager {
    /*
       we use this class to create scene and add some layouts to it then add this scenes to a stage
       also we use this class to add out controls which we create like SpaceRunnerButton 
    */
    private static final int WIDTH = 980;
    private static final int HEIGHT = 700;
    private AnchorPane mainPane;
    private Stage mainStage;
    private Scene mainScene ;
    private final static int MENU_BUTTONS_START_X=100;
    private final static int MENU_BUTTONS_START_Y=150;
    private  SpaceRunnerSubScene creditsSubScene ;
    private  SpaceRunnerSubScene helpSubScene ;
    private  SpaceRunnerSubScene scoresSubScene ;
    private  SpaceRunnerSubScene ShipChooserSubScene ;
    private  SpaceRunnerSubScene sceneToHide;
    List<SpaceRunnerButton> menuButtons = new ArrayList<>();
    List<ShipPicker> shipList;
    private SHIP choosenShip;
    
    public ViewManager(){
        // we use anchor pane to add elements to it by (X,Y)
        mainPane = new AnchorPane();
        mainScene = new Scene (mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createButtons();
        createBackground();
        createLogo();
        createSubScene();
        
     
    }
    
    public Stage getMainStage(){
        return mainStage;
    }
    
    private void showSubScene(SpaceRunnerSubScene subScene){
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        if (sceneToHide != subScene){
        subScene.moveSubScene();
        sceneToHide = subScene;
        }
        else{
             sceneToHide= null;
        }
    }
    
    private HBox createShipsToChoose(){
        HBox box = new HBox();
        box.setSpacing(20);
        shipList = new ArrayList<>();
        for(SHIP ship :SHIP.values()){
            ShipPicker shipToSet = new ShipPicker(ship);
            box.getChildren().add(shipToSet);
            shipToSet.setOnMouseClicked( e ->{ 
                 for(ShipPicker shippicker : shipList){
                     shippicker.setISCircleChoosen(Boolean.FALSE);
                 }
                 shipToSet.setISCircleChoosen(true);
                 choosenShip=shipToSet.getShip();
            });
            shipList.add(shipToSet);
        }
        box.setLayoutX(300-2*118);
        box.setLayoutY(100);
        return box;
    }
    
    
    private SpaceRunnerButton createButtonToStart(){
        SpaceRunnerButton startButton = new SpaceRunnerButton("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);
        startButton.setOnAction( e->{
           if (choosenShip != null){
               GameViewManager gameManager = new GameViewManager();
               gameManager.createNewGame(mainStage, choosenShip);
           }
        });
        return startButton ;
    }
    
    
    private void addMenuButton(SpaceRunnerButton button){
       button.setLayoutX(MENU_BUTTONS_START_X);
       button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
       menuButtons.add(button);
       mainPane.getChildren().add(button);
    }
    
    private void createSubScene(){
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);
        
        helpSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);
        
        scoresSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoresSubScene);
        
       
       
        
        createShipChooserSubScene();
    }
    
    private void createShipChooserSubScene() {
           ShipChooserSubScene = new SpaceRunnerSubScene();
           mainPane.getChildren().add(ShipChooserSubScene);
           
           InfoLabel chooseShipLabel = new InfoLabel("choose your ship");
           chooseShipLabel.setLayoutX(110);
           chooseShipLabel.setLayoutY(0);
           
           ShipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
           ShipChooserSubScene.getPane().getChildren().add(createShipsToChoose());
            ShipChooserSubScene.getPane().getChildren().add(createButtonToStart());
           
    }
    
    private void createButtons(){
     createStartButton();
             createScoresButton();
                     createHelpButton();
                             createCreditsButton();
                                     createExitButton();
    }
    
    
    private void createStartButton(){
        SpaceRunnerButton StartButton = new SpaceRunnerButton("Start");
        StartButton.setOnAction( e -> { showSubScene(ShipChooserSubScene);});
        addMenuButton(StartButton);
    }
    private void createScoresButton(){
        SpaceRunnerButton ScoresButton = new SpaceRunnerButton("Scores");
        ScoresButton.setOnAction( e -> { showSubScene(scoresSubScene);});
        addMenuButton(ScoresButton);
    }
    private void createHelpButton(){
        SpaceRunnerButton HelpButton = new SpaceRunnerButton("Help");
        HelpButton.setOnAction( e -> { showSubScene(helpSubScene);});
        addMenuButton(HelpButton);
    }
    private void createCreditsButton(){
        SpaceRunnerButton CreditsButton = new SpaceRunnerButton("Credits");
        CreditsButton.setOnAction( e -> { showSubScene(creditsSubScene);});
        addMenuButton(CreditsButton);
    }
    private void createExitButton(){
        SpaceRunnerButton button = new SpaceRunnerButton("Exit");
        addMenuButton(button);
    }
    
    
    private void createBackground(){
        Image backgroundImage = new Image("view/resources/4.jpg",WIDTH,HEIGHT,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
        
    }
    
    private void createLogo(){
        ImageView logo = new ImageView("view/resources/3.jpg");
        logo.setLayoutX(400);
        logo.setLayoutY(50);
        logo.setOnMouseEntered( e ->{
            logo.setEffect(new DropShadow());
        });
        logo.setOnMouseExited(e ->{
            logo.setEffect(null);
        });
        mainPane.getChildren().add(logo);
    }

    
}
