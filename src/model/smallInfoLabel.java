/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import static model.InfoLabel.FONT_PATH;

/**
 *
 * @author Lenovo
 */
public class smallInfoLabel extends Label {
    
    public final static String FONT_PATH = "src/resources/Kenvector_future.ttf" ;
        
    public smallInfoLabel(String text){
        this.setPrefHeight(50);
        this.setPrefWidth(130);
        this.setText(text);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10,10,10,10));
        BackgroundImage background = new BackgroundImage(new Image("view/resources/buttonBlue.png",
                130,50,false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,null);
        this.setBackground(new Background(background));
        setLabelFont();

    }
      private void setLabelFont(){
        try {
             setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));

        } catch (FileNotFoundException ex) {
            this.setFont(Font.font("Verdana", 15));
        }
    }
}
