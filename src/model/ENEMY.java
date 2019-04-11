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
public enum ENEMY {
    ENEMY4("view/resources/enemyRed2.png",20),
    ENEMY1("view/resources/enemyBLue4.png",40),
    ENEMY3("view/resources/enemyRed1.png",20),
    ENEMY2("view/resources/enemyGreen5.png",30),
    ENEMY5("view/resources/enemyRed3.png",20);
    private String urlEnemy ;
    private int life;
    
    private ENEMY(String urlEnemy,int life){
        this.urlEnemy= urlEnemy;
        this.life=life;
    }
    
    public String getUrl(){
        return this.urlEnemy;
    }
    public int getLife(){
        return this.life;
    }

    
    
}
