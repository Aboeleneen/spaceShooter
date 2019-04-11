/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.ENEMY;
import model.SHIP;
import model.smallInfoLabel;

/**
 *
 * @author Lenovo
 */
public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    
    private final static int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 700;
    
    private Stage menuStage;
    private ImageView ship ;
    
    private Boolean isLeftKeyPressed=false;
    private Boolean isRightKeyPressed=false;
    private int angle=0;
    private AnimationTimer gameTimer;
    
    private GridPane gridPane1;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "view/resources/3.jpg";
    
    private final static String METEOR_BROWN_IMAGE = "view/resources/meteorBrown_small1.png";
    private final static String METEOR_GREY_IMAGE = "view/resources/meteorGrey_small1.png";
    
    
    private List<ImageView> brownMeteors ;
    private List<ImageView> greyMeteors;
    Random randomPostionGenerator;
    
    private ImageView star;
    private ImageView life;
    private smallInfoLabel pointsLabel;
    private ImageView[]  playerLifes;
    private int playerLife;
    private int points;
    private static final String GOLD_STAR_IMAGE = "view/resources/star_gold.png";
    private static final String SHOOT_IMAGE_SHIP = "view/resources/fire01.png";
    private static final String SHOOT_IMAGE_ENEMY = "view/resources/spaceMissiles_006.png";
    private List<ImageView> shipShoots = new ArrayList<>();
    private List<ImageView> enemyShoots = new ArrayList<>();
    private final static int STAR_RADIUS = 12 ;
    private final static int SHIP_RADIUS = 27 ;
    private final static int METEOR_RADIUS = 2 ;
    
    private double t = 0;
    private int level = 1 ;  
    private final static String METEOR_GREY_MED_IMAGE  = "view/resources/meteorGrey_med2.png";
    private final static String METEOR_BROWN_MED_IMAGE = "view/resources/meteorBrown_med3.png";
    private final static String METEOR_GREY_BIG_IMAGE  = "view/resources/meteorGrey_big2.png";
    private final static String METEOR_BROWN_BIG_IMAGE = "view/resources/meteorBrown_big1.png";
    private List<ImageView> brownMeteorsMed = new ArrayList<>() ;
    private List<ImageView> greyMeteorsMed = new ArrayList<>() ;
    private List<ImageView> brownMeteorBig = new ArrayList<>() ;
    private List<ImageView> greyMeteorsBig = new ArrayList<>() ;
    private smallInfoLabel levelLabel ;
    
    private List<ImageView> enemies = new ArrayList<>() ;
    private List<String> lifeOfEnemies = new ArrayList<>() ;
    private double timeBetweenShoots = 0 ;
    
    private ImageView shield ;
    private final static String SHIELD_IMAGE = "view/resources/shipChooser/shield3.png";
    public GameViewManager(){
       initStage();
       initEnemy();
       createKeyListeners();
       randomPostionGenerator = new Random();
      
}

    private void initStage() {
       gamePane = new AnchorPane();
       gameScene = new Scene(gamePane,GAME_WIDTH,GAME_HEIGHT);
       gameStage = new Stage();
       gameStage.setScene(gameScene);
    }
    
    private void createKeyListeners(){
        gameScene.setOnKeyPressed( e ->{
            if(e.getCode() == KeyCode.LEFT){
                isLeftKeyPressed=true;
            }
            else if (e.getCode() == KeyCode.RIGHT){
                isRightKeyPressed=true;
            }
            else if(e.getCode() == KeyCode.UP){
                ship.setLayoutY(ship.getLayoutY() - 8);
            }
            else if (e.getCode() == KeyCode.DOWN){
                 ship.setLayoutY(ship.getLayoutY() + 8);
            }
            else if(e.getCode() == KeyCode.SPACE){
                shoot(ship);
            }
        
         });
        gameScene.setOnKeyReleased( e ->{
            if(e.getCode() == KeyCode.LEFT){
                  isLeftKeyPressed=false;
            }
            else if (e.getCode() == KeyCode.RIGHT){
                   isRightKeyPressed=false;
            }
            
        });
    }
    
    public void createNewGame(Stage mainStage,SHIP choosenShip){
        this.menuStage=mainStage;
        this.menuStage.hide();
        createBackground();
        createShip(choosenShip);
        gameStage.show();
        createGameElements(choosenShip);
        createGameLoop();
        
    }
    
    private void createShip(SHIP choosenShip){
        ship = new ImageView(choosenShip.getUrl());
        ship.setLayoutX(GAME_WIDTH/2 -50);
        ship.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(ship);
    }
    
    private void createGameElements(SHIP choosenShip){
        playerLife = 2 ;
        star = new ImageView(GOLD_STAR_IMAGE);
        setNewElementPostion(star);
        gamePane.getChildren().add(star);
        life = new ImageView(choosenShip.getUrlLife());
        setNewElementPostion(life);
        gamePane.getChildren().add(life);
        shield = new ImageView(SHIELD_IMAGE);
        shield.setLayoutX(ship.getLayoutX() - 20);
        shield.setLayoutY(ship.getLayoutY() - 10);
         gamePane.getChildren().add(shield);
        pointsLabel = new smallInfoLabel("Points:00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
        playerLifes = new ImageView[3];
        
        levelLabel = new smallInfoLabel("Level: 0" + level);
        levelLabel.setLayoutX(20);
        levelLabel.setLayoutY(20);
        gamePane.getChildren().add(levelLabel);
        
        for(int i=0;i<playerLifes.length;i++){
            playerLifes[i] = new ImageView(choosenShip.getUrlLife());
            playerLifes[i].setLayoutX(455 + (i*50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }
        
        brownMeteors = new ArrayList<>();
        greyMeteors = new ArrayList<>();
        addMoreMeteors(3,brownMeteors,METEOR_BROWN_IMAGE);
        addMoreMeteors(4,greyMeteors,METEOR_GREY_IMAGE);
        
        
    }
    private void moveGameElements(){
        star.setLayoutY(star.getLayoutY() + 5);
        life.setLayoutY(star.getLayoutY() + 15);
        for(int i=0;i<brownMeteors.size();i++){
            brownMeteors.get(i).setLayoutY(brownMeteors.get(i).getLayoutY()+7);
            brownMeteors.get(i).setRotate(brownMeteors.get(i).getRotate()+4);
        }
        
        for(int i=0;i<greyMeteors.size();i++){
            greyMeteors.get(i).setLayoutY(greyMeteors.get(i).getLayoutY()+7);
            greyMeteors.get(i).setRotate(greyMeteors.get(i).getRotate()+4);
        }
        for(int i=0;i<greyMeteorsMed.size();i++){
            greyMeteorsMed.get(i).setLayoutY(greyMeteorsMed.get(i).getLayoutY()+5);
            greyMeteorsMed.get(i).setRotate(greyMeteorsMed.get(i).getRotate()+3);
        }
        for(int i=0;i<brownMeteorsMed.size();i++){
            brownMeteorsMed.get(i).setLayoutY(brownMeteorsMed.get(i).getLayoutY()+5);
            brownMeteorsMed.get(i).setRotate(brownMeteorsMed.get(i).getRotate()+3);
        }
        for(int i=0;i<brownMeteorBig.size();i++){
            brownMeteorBig.get(i).setLayoutY(brownMeteorBig.get(i).getLayoutY()+5);
            brownMeteorBig.get(i).setRotate(brownMeteorBig.get(i).getRotate()+3);
        }
        for(int i=0;i<greyMeteorsBig.size();i++){
            greyMeteorsBig.get(i).setLayoutY(greyMeteorsBig.get(i).getLayoutY()+5);
            greyMeteorsBig.get(i).setRotate(greyMeteorsBig.get(i).getRotate()+3);
        }
        if(level == 3){
            for(int i=0;i<enemies.size();i++){
                if(enemies.get(i).getLayoutY() < 120)
                enemies.get(i).setLayoutY(enemies.get(i).getLayoutY()+3);
            }
        }
      
        
        
        checkIfElementAreBehindTheShipAndRelocate();
    }
    
    private void checkIfElementAreBehindTheShipAndRelocate(){
        if(star.getLayoutY() > 900){
             setNewElementPostion(star); 
        }
        if(life.getLayoutY() > 6000){
             setNewElementPostion(life); 
        }
        for(int i=0;i<brownMeteors.size();i++){
            if(brownMeteors.get(i).getLayoutY() > 900){
                setNewElementPostion(brownMeteors.get(i));
            }
        }
        for(int i=0;i<greyMeteors.size();i++){
            if(greyMeteors.get(i).getLayoutY() > 900){
                setNewElementPostion(greyMeteors.get(i));
            }
        }
        for(int i=0;i<greyMeteorsMed.size();i++){
            if(greyMeteorsMed.get(i).getLayoutY() > 900){
                setNewElementPostion(greyMeteorsMed.get(i));
            }
        }
        for(int i=0;i<brownMeteorsMed.size();i++){
            if(brownMeteorsMed.get(i).getLayoutY() > 900){
                setNewElementPostion(brownMeteorsMed.get(i));
            }
        }
        for(int i=0;i<brownMeteorBig.size();i++){
            if(brownMeteorBig.get(i).getLayoutY() > 900){
                setNewElementPostion(brownMeteorBig.get(i));
            }
        }
        for(int i=0;i<greyMeteorsBig.size();i++){
            if(greyMeteorsBig.get(i).getLayoutY() > 900){
                setNewElementPostion(greyMeteorsBig.get(i));
            }
        }
    }
    private void setNewElementPostion(ImageView image){
        image.setLayoutX(randomPostionGenerator.nextInt(600));
        image.setLayoutY(-(randomPostionGenerator.nextInt(3200))+600);
    }
    
    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
               moveBackground();
               moveGameElements();
               moveShip();
               ifELementsCollide();
               moveShoots();
               destoryMeteors();
               nextLevel();
               t+=.016;
               if(level >= 3){
               timeBetweenShoots+=.016;
               if( timeBetweenShoots > 1){
                   timeBetweenShoots=0;
                   if(Math.random() > 0.3){
                       int firstEnemy = randomPostionGenerator.nextInt(5);
                       shoot(enemies.get(firstEnemy));
                       int secondEnemy = randomPostionGenerator.nextInt(5);
                       shoot(enemies.get(secondEnemy));
                   }
               }
               }
            }
        };
        gameTimer.start();
    }
    
   private void moveShip(){
        if ( isLeftKeyPressed && !isRightKeyPressed){
            if(angle > -30){
                angle-=5; 
            }
            ship.setRotate(angle);
            if(ship.getLayoutX() > -20){
                ship.setLayoutX(ship.getLayoutX()-3);
            }
        }
        
        if ( ! isLeftKeyPressed && isRightKeyPressed){
            if(angle < 30){
                angle+=5; 
            }
            ship.setRotate(angle);
            if(ship.getLayoutX() < 522){
                ship.setLayoutX(ship.getLayoutX()+3);
            }
        }
        
        if (isLeftKeyPressed && isRightKeyPressed){
             if(angle < 0){
                angle+=5;
            }
            else if(angle > 0){
                angle-=5;
            }
            ship.setRotate(angle);
        }
         
        if (! isLeftKeyPressed && !isRightKeyPressed){
            if(angle < 0){
                angle+=5;
            }
            else if(angle > 0){
                angle-=5;
            }
            ship.setRotate(angle);
        }
                shield.setLayoutX(ship.getLayoutX() - 20);
        shield.setLayoutY(ship.getLayoutY() - 28);
   }
   
   private void createBackground(){
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        
        for(int i=0;i<21;i++){
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1, i%3, i/3);
            GridPane.setConstraints(backgroundImage2, i%3, i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
            
        }
        
        gridPane2.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1,gridPane2);
       /* Image img = new Image(BACKGROUND_IMAGE,980,700,false,true);
        BackgroundImage background = new BackgroundImage(img,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,null);
        gamePane.setBackground(new Background(background));*/
   }
   private void moveBackground(){
       gridPane1.setLayoutY(gridPane1.getLayoutY() + 3);
       gridPane2.setLayoutY(gridPane2.getLayoutY() + 3);
       
       if(gridPane1.getLayoutY() >= 1024){
          
           gridPane1.setLayoutY(-1024);
       }
       
         if(gridPane2.getLayoutY() >= 1024){
            
           gridPane2.setLayoutY(-1024);
       }
   }
   private void ifELementsCollide(){
     if(SHIP_RADIUS + STAR_RADIUS  > calculateDistance(ship.getLayoutX()+49,star.getLayoutX()+15,
             ship.getLayoutY()+37,star.getLayoutY()+15))
     {
         setNewElementPostion(star);
         calculatePoints(5);
         
     }
      if(SHIP_RADIUS + STAR_RADIUS  > calculateDistance(ship.getLayoutX()+49,life.getLayoutX()+15,
             ship.getLayoutY()+37,life.getLayoutY()+15))
     {
         setNewElementPostion(life);
        
     
         if (playerLife < 2){
            playerLife++;
            gamePane.getChildren().add(playerLifes[playerLife]);
         }
         
         
     }
     for(int i=0;i<brownMeteors.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS  > calculateDistance(ship.getLayoutX()+49,brownMeteors.get(i).getLayoutX()+20,
             ship.getLayoutY()+37,brownMeteors.get(i).getLayoutY()+20))
         {
             removeLife();
             setNewElementPostion(brownMeteors.get(i));
         }
     }
     
     for(int i=0;i<greyMeteors.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS  > calculateDistance(ship.getLayoutX()+49,greyMeteors.get(i).getLayoutX()+20,
             ship.getLayoutY()+37,greyMeteors.get(i).getLayoutY()+20))
         {
             removeLife();
             setNewElementPostion(greyMeteors.get(i));
         }
     }
     
      for(int i=0;i<greyMeteorsMed.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS + 7.5  > calculateDistance(ship.getLayoutX()+49,greyMeteorsMed.get(i).getLayoutX()+30,
             ship.getLayoutY()+37,greyMeteorsMed.get(i).getLayoutY()+30))
         {
             removeLife();
             setNewElementPostion(greyMeteorsMed.get(i));
         }
     }
      
         for(int i=0;i<brownMeteorsMed.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS + 7.5  > calculateDistance(ship.getLayoutX()+49,brownMeteorsMed.get(i).getLayoutX()+30,
             ship.getLayoutY()+37,brownMeteorsMed.get(i).getLayoutY()+30))
         {
             removeLife();
             setNewElementPostion(brownMeteorsMed.get(i)) ;
         }
     }
    for(int i=0;i<brownMeteorBig.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS + 35  > calculateDistance(ship.getLayoutX()+49,brownMeteorBig.get(i).getLayoutX()+60,
             ship.getLayoutY()+37,brownMeteorBig.get(i).getLayoutY()+60))
         {
             removeLife();
             setNewElementPostion(brownMeteorBig.get(i)) ;
         }
     }
    for(int i=0;i<greyMeteorsBig.size();i++){
         if(SHIP_RADIUS + METEOR_RADIUS + 35  > calculateDistance(ship.getLayoutX()+49,greyMeteorsBig.get(i).getLayoutX()+60,
             ship.getLayoutY()+37,greyMeteorsBig.get(i).getLayoutY()+60))
         {
             removeLife();
             setNewElementPostion(greyMeteorsBig.get(i)) ;
         }
     }
     
     
   }
   private void removeLife(){
       gamePane.getChildren().remove(playerLifes[playerLife]);
       playerLife--;
       if(playerLife < 0){
          // gameStage.close();
           ship.setImage(new Image("view/resources/shipChooser/playerShip3_damage3.png"));
           gameTimer.stop();
          // menuStage.show();
         
       }
   }
   private double calculateDistance(double x1,double x2 ,double y1,double y2){
       return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
   }
   
   private void shoot(ImageView whoShoot){
       
       if(whoShoot == ship){
         ImageView shoot = new ImageView(SHOOT_IMAGE_SHIP);
       shoot.setLayoutX(whoShoot.getLayoutX() + 43);
       shoot.setLayoutY(whoShoot.getLayoutY() - 37);
        shipShoots.add(shoot);
        gamePane.getChildren().add(shoot);
       }
       else{
       ImageView shoot = new ImageView(SHOOT_IMAGE_ENEMY);
        shoot.setLayoutX(whoShoot.getLayoutX() + 43);
        shoot.setLayoutY(whoShoot.getLayoutY() + 45);
                enemyShoots.add(shoot);
                gamePane.getChildren().add(shoot);
       }
      
       
  

   }
   private void moveShoots(){
      shipShoots.forEach( shoot -> {
          shoot.setLayoutY(shoot.getLayoutY()-5);
          if(shoot.getLayoutY() <= 0){
                gamePane.getChildren().remove(shoot);
                shipShoots.remove(shoot);
                gridPane1.getChildren().remove(shoot);
                gridPane2.getChildren().remove(shoot);
          }
       });
     enemyShoots.forEach( shoot -> {
          shoot.setLayoutY(shoot.getLayoutY()+5);
          if(shoot.getLayoutY() >= 600){
                gamePane.getChildren().remove(shoot);
                shipShoots.remove(shoot);
                gridPane1.getChildren().remove(shoot);
                gridPane2.getChildren().remove(shoot);
          }
       });
      
   }
   private void destoryMeteors(){
       shipShoots.forEach( shoot ->{
          
            for(int i=0;i<brownMeteors.size();i++){
                if(shoot.getBoundsInParent().intersects(brownMeteors.get(i).getBoundsInParent())){
                    setNewElementPostion(brownMeteors.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                    calculatePoints(1);
                }
            }
            
            for(int i=0;i<greyMeteors.size();i++){
                if(shoot.getBoundsInParent().intersects(greyMeteors.get(i).getBoundsInParent())){
                    setNewElementPostion(greyMeteors.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                      calculatePoints(1);
                }
            }
            
             for(int i=0;i<greyMeteorsMed.size();i++){
                if(shoot.getBoundsInParent().intersects(greyMeteorsMed.get(i).getBoundsInParent())){
                    setNewElementPostion(greyMeteorsMed.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                      calculatePoints(2);
                }
            }
               for(int i=0;i<brownMeteorsMed.size();i++){
                if(shoot.getBoundsInParent().intersects(brownMeteorsMed.get(i).getBoundsInParent())){
                    setNewElementPostion(brownMeteorsMed.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                      calculatePoints(2);
                }
            }
              for(int i=0;i<greyMeteorsBig.size();i++){
                if(shoot.getBoundsInParent().intersects(greyMeteorsBig.get(i).getBoundsInParent())){
                    setNewElementPostion(greyMeteorsBig.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                      calculatePoints(3);
                }
            }
              for(int i=0;i<brownMeteorBig.size();i++){
                if(shoot.getBoundsInParent().intersects(brownMeteorBig.get(i).getBoundsInParent())){
                    setNewElementPostion(brownMeteorBig.get(i));
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                      calculatePoints(3);
                }
            }
              
              
             for(int i=0;i<enemies.size();i++){
                    if(shoot.getBoundsInParent().intersects(enemies.get(i).getBoundsInParent())){
                           shipShoots.remove(shoot);
                           gamePane.getChildren().remove(shoot);
                       int enemyLife = Integer.parseInt(lifeOfEnemies.get(i));
                       if (enemyLife-1 == 0){
                           gamePane.getChildren().remove(enemies.get(i));
                           
                           lifeOfEnemies.remove(lifeOfEnemies.get(i));
                           enemies.remove(enemies.get(i));
                             calculatePoints(10);
                           
                       }
                       else{
                           lifeOfEnemies.set(i, Integer.toString(enemyLife-1));
                       }
                    }
              }
             
            
       
       });
       enemyShoots.forEach(shoot -> {
               for(int i=0;i<enemyShoots.size();i++){
                if(ship.getBoundsInParent().intersects(enemyShoots.get(i).getBoundsInParent())){
                    setNewElementPostion(enemyShoots.get(i));
                    removeLife();
                    shipShoots.remove(shoot);
                    gamePane.getChildren().remove(shoot);
                    
                    break;
                }
            }
       
       });
   }
   
   private void addMoreMeteors(int size,List<ImageView> list,String imagePath){
       for(int i=0;i<size;i++){
            ImageView meteor = new ImageView(imagePath);
            setNewElementPostion(meteor);
            list.add(meteor);
            gamePane.getChildren().add(meteor);
       }
   }
   private void nextLevel(){
     if(t > 20+20*(level-1) && (level <3 || level==4)){
           level++;
               // Start level two 
      if(level == 2){
          addMoreMeteors(3,brownMeteors,METEOR_BROWN_IMAGE);
          addMoreMeteors(2,greyMeteors,METEOR_GREY_IMAGE);
          addMoreMeteors(2,brownMeteorsMed,METEOR_BROWN_MED_IMAGE);
          addMoreMeteors(2,greyMeteorsMed,METEOR_GREY_MED_IMAGE);
       }
      // Start Level third
       if(level == 3){
          clearFromPane(brownMeteors);
          clearFromPane(greyMeteors);
          clearFromPane (brownMeteorsMed);
          clearFromPane(greyMeteorsMed);
             for(int i=1;i<=5;i++){
                  if(i==1) enemies.get(i-1).setLayoutX(20);
                  else enemies.get(i-1).setLayoutX(30*(i)+80*(i-1));
                  
                  enemies.get(i-1).setLayoutY(-500);
                  gamePane.getChildren().add(enemies.get(i-1));
              }
       }
           t=0;
      }
     if( level == 3 && enemies.isEmpty()){
         level++;
        if(level == 4){
          addMoreMeteors(6,brownMeteors,METEOR_BROWN_IMAGE);
          addMoreMeteors(6,greyMeteors,METEOR_GREY_IMAGE);
          addMoreMeteors(3,brownMeteorsMed,METEOR_BROWN_MED_IMAGE);
          addMoreMeteors(3,greyMeteorsMed,METEOR_GREY_MED_IMAGE);
           t=0;
       }
        
     }
     
     if( level == 4 && t > 20){
         level++;
          if(level == 5){
          clearFromPane(brownMeteors);
          clearFromPane(greyMeteors);
          clearFromPane (brownMeteorsMed);
          clearFromPane(greyMeteorsMed);
          
          addMoreMeteors(5,brownMeteorBig,METEOR_BROWN_BIG_IMAGE);
          addMoreMeteors(5,greyMeteorsBig,METEOR_GREY_BIG_IMAGE);
          t=0;
       }
          
     }

    
    // Start level four

    // Start level five 
      
     // Start level Six 
     /* if(t > (10 *level)){
          addMoreMeteors(3*level,brownMeteors,METEOR_BROWN_IMAGE);
          addMoreMeteors(2*level,greyMeteors,METEOR_GREY_IMAGE);

          level++;
          if(level == 3){
              for(int i=1;i<=5;i++){
                  if(i==1) enemies.get(i-1).setLayoutX(20);
                  else enemies.get(i-1).setLayoutX(30*(i)+80*(i-1));
                  
                  enemies.get(i-1).setLayoutY(-500);
                  gamePane.getChildren().add(enemies.get(i-1));
              }
          }
          if(level == 3){
           addMoreMeteors(2,brownMeteorsMed,METEOR_BROWN_MED_IMAGE);
           addMoreMeteors(2,greyMeteorsMed,METEOR_GREY_MED_IMAGE);
           }
          if(level == 4){
            addMoreMeteors(2,brownMeteorsMed,METEOR_BROWN_MED_IMAGE);
            addMoreMeteors(2,greyMeteorsMed,METEOR_GREY_MED_IMAGE);
            addMoreMeteors(2,brownMeteorBig,METEOR_BROWN_BIG_IMAGE);
            addMoreMeteors(2,greyMeteorsBig,METEOR_GREY_BIG_IMAGE);
          }
          
         
          t=0;
       }*/
        
        levelLabel.setText("Level: 0" + level);
   }
   
   private void initEnemy(){

       for(ENEMY enemy:ENEMY.values()){
           enemies.add(new ImageView(enemy.getUrl()));
           lifeOfEnemies.add(Integer.toString(enemy.getLife()));
       }
   }
   private void clearFromPane(List<ImageView> list){
       list.forEach( e-> {
         gamePane.getChildren().remove(e);
       });
       list.clear();
   }
   
   private void calculatePoints(int num){
                points+=num;
         String textToSet = "Points: ";
         if (points < 10){
             textToSet+= "0";
         }
         pointsLabel.setText(textToSet+points);
   }
           
   
   
   
}
