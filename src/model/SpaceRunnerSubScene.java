/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

/**
 *
 * @author Lenovo
 */
public class SpaceRunnerSubScene extends SubScene{
    /*
        we create this class to use it as a subScene to add it to mainPane as buttons 
        we ca add scenes to panes
    */
    private final String FONT_PATH ="src/resources/Kenvector_future.ttf" ;
    private final String BACKGROUND_IMAGE ="/resources/yellow_panel.png" ;
    private final int TRANSITION_X = 630;
    private Boolean isHidden ;
    public SpaceRunnerSubScene() {
        super(new AnchorPane(), 600, 400);
         this.prefHeight(400);
         this.prefWidth(600);
         BackgroundImage Image = new BackgroundImage(new Image(BACKGROUND_IMAGE,600,400,false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
         AnchorPane root2  = (AnchorPane) this.getRoot();
         root2.setBackground(new Background(Image));
         
         isHidden= true;
         this.setLayoutX(980);
         this.setLayoutY(180);
    }
    
    public void moveSubScene(){
        TranslateTransition transition  = new TranslateTransition();
        transition.setDuration(Duration.seconds(.3));
        transition.setNode(this);
        
        if(isHidden){
            transition.setByX(- TRANSITION_X);
            isHidden = false;
        }
        else{
            transition.setByX(+ TRANSITION_X);
            isHidden = true;
        }
       
        transition.play();
        
    }
    
    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
    
}
