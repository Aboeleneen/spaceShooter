/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;



/**
 *
 * @author Lenovo
 */
public class SpaceRunnerButton extends Button{
    /* 
        * this is a button for the game so we should add some css style like font and background images which
        * we git it from assests (UI) .
    */
    private final String FONT_PATH = "src/resources/Kenvector_future.ttf" ;
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button01.png'); " ;
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resources/yellow_button00.png'); " ;
    
    /* we will use this class instead of Button Class directly */
    public SpaceRunnerButton(String text){
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initButtonListener();
    }
    
    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.loadFont("Verdana", 23));
        }
    }
    
    /* 
        when we press the button we add button_pressed_img to button background then change his postion by increase 
        his layoutY by 5 
    */
    private void setButtonPressedStyle(){
       setStyle(BUTTON_PRESSED_STYLE);
       setPrefHeight(45);
       setLayoutY( getLayoutY()  + 4);
    }
    /*
       when we release the buttn we return to default one by change his background image and decrease his 
       layoutY by 5 also
    */
    
    private void setButtonReleasedStyle(){
       setStyle(BUTTON_FREE_STYLE);
       setPrefHeight(49);
       setLayoutY( getLayoutY()  - 4);
    }
    /*
       we use this method to add actions to our button when we pressed,released,entered and exited
    */
    private void initButtonListener(){
        setOnMousePressed( e -> {
        if(e.getButton().equals(MouseButton.PRIMARY)){
            setButtonPressedStyle();
        }
        
        });
        
        setOnMouseReleased( e ->{
        if(e.getButton().equals(MouseButton.PRIMARY)){
            setButtonReleasedStyle();
        }
        });
        
        setOnMouseExited(e ->{
                 setEffect(null);
        });
        
        setOnMouseEntered(e ->{
                 setEffect(new DropShadow());
        });
    }
}
