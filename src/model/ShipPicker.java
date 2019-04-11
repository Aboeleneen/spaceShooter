/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Lenovo
 */
public class ShipPicker extends VBox{
    /*
        this class we use it as a container of the ship and a circle to click on it
        to choose this ship
    */
    private ImageView circleImage;
    private ImageView shipImage;
    
    private String circleNotChoosen= "view/resources/shipChooser/grey_circle.png";
    private String circleChoosen = "view/resources/shipChooser/yellow_boxTick.png";
    
    private SHIP ship ;
    
    private Boolean isCircleChoosen;
    
    public ShipPicker(SHIP ship){
        this.ship=ship;
        circleImage = new ImageView(new Image(circleNotChoosen));
        shipImage = new ImageView(new Image(ship.getUrl()));
        isCircleChoosen=false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(circleImage,shipImage);
    }
    
    public SHIP getShip(){
        return ship;
    }
    public Boolean getIsCircleChoosen(){
        return isCircleChoosen;
    }
    public void setISCircleChoosen(Boolean isCircleChoosen){
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));  
    }
    
}
