/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

/**
 * 
 * @author Lenovo
 */
public class InfoLabel extends Label {
    /*
       -we create this class instead of usin label class directly so we add to it
       some css styles and a background images from assests .
       -when we need to add a background image we follow this step :
          1.we put url of image in final String 
          2.we create image object (Image img = new Image('url',width,height,false,true)
          3.we create BackgroundImage object ( BackgroundImage background = new BackgroundImage(img,repeatX,repeatY,postion,null)
          4.we add this BackgroundImage to the element ( setBackground( new Background(background)) )
        - when we need to add external Font file to text in any element:
          1.we put url of font in final string
          2. then we add it to element ( this.setFont(Font.loadFont( new FileInputStream(url),font size)) )
    */
    public final static String FONT_PATH = "src/resources/Kenvector_future.ttf" ;
    public final static String BACKGROUND_IMAGE = "/resources/yellow_button13.png";
    
    public InfoLabel(String text){
        this.setPrefWidth(350);
        this.setPrefHeight(49);
        this.setPadding(new Insets(40,40,40,40));
        this.setText(text);
        this.setAlignment(Pos.CENTER);
        setLabelFont();
        
        BackgroundImage Image = new BackgroundImage(new Image(BACKGROUND_IMAGE,350,49,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,null);
        
        this.setBackground(new Background(Image));
        
    
    }  
    
    
    private void setLabelFont(){
        try {
             setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));

        } catch (FileNotFoundException ex) {
            this.setFont(Font.font("Verdana", 23));
        }
    }
}
