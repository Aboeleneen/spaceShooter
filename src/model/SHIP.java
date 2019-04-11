/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lenovo
 */
public enum SHIP {
    /*
       we have to choose one ship to use it as a player in the game so we 
       have four ship to choose. 
    */
    BLUE("view/resources/shipChooser/playerShip3_blue.png","view/resources/shipChooser/playerLife3_blue.png"),
    GREEN("view/resources/shipChooser/playerShip3_green.png","view/resources/shipChooser/playerLife3_green.png"),
    ORANGE("view/resources/shipChooser/playerShip3_orange.png","view/resources/shipChooser/playerLife3_orange.png"),
    RED("view/resources/shipChooser/playerShip3_red.png","view/resources/shipChooser/playerLife3_red.png");
    
    private String urlShip ;
    private String urlLife;
    
    
    /* 
      this constructor is private because we won't add a new choice put we use it 
      to add url of image to our choice  
    */
    private SHIP(String urlShip,String urlLife){
        this.urlShip= urlShip;
        this.urlLife = urlLife;
    }
    
    public String getUrl(){
        return this.urlShip;
    }
    public String getUrlLife(){
        return this.urlLife;
    }
    
}
