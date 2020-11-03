import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class turtle_game extends PApplet {

//Krithika Govind
//Save the Turtle
//Images licensed from Adobe stock

//All sounds under creative commons license
//bite sound - josepharaoh99 on freesound.org
//chime sound - bwg2020 on freesound.org
//winning sound - unadamlar on freesound.org
//lose sound - Lysaura on freesound.org
//underwater background sound - Bpianoholic from freesound.org

//https://processing.org/reference/libraries/sound/SoundFile.html


Turtle turtle;
Jellyfish [] jellyfish = new Jellyfish[5];
Trash [] trash = new Trash[4];
int [] Colors = {0xffCE4141, 0xff3ACB64, 0xff653ACB, 0xffC9256C, 0xffEADC36};
Background background;
Boolean startClicked = false;
Boolean gameOver = false;
Boolean win = false;
Boolean nextClicked = false;
Boolean restartClicked = false;
int score = 0;
int level = 1;
int numJellyfish = 3;
int jellycount = 0;
Boolean left = false;
Boolean right = false;
float startSpeed = 1;
float stopSpeed = 2;
int timeLevelChange;
int currentTime;
SoundFile eatjellyfish;
SoundFile levelup;
SoundFile gameover;
SoundFile winningSound;
SoundFile backgroundsound;
float newVariable;

public void setup(){
  
  noStroke();
  imageMode(CENTER);
  
  //constructing turtle, trash and jellyfish objects
  turtle = new Turtle(width/2,height/2);
  for (int i=0;i<jellyfish.length;i++){
    jellyfish[i] = new Jellyfish(random(width),random(height),0xffF5D7EA,1);
  }
  for (int i=0; i<trash.length; i++){
    trash[i] = new Trash(random(width), 0, Colors[(int)random(Colors.length)], random(startSpeed,stopSpeed),(int)random(0,4));
  }
  background = new Background(0);
  background.display();
  
  //creating sound files
  eatjellyfish = new SoundFile(this,"bite.mp3");
  levelup = new SoundFile(this,"chime.wav");
  gameover = new SoundFile(this,"lose.wav");
  winningSound = new SoundFile(this,"winningsound.wav");
  backgroundsound = new SoundFile(this,"bubbles.wav");
  backgroundsound.loop();
}
  

public void draw(){  
  if (background.screen == 2){
    //newVariable = millis();
    playGame();
  }   
}

public void playGame(){
  currentTime = millis();
  background.display();
  

  //displays score and level on top of the screen
  fill(0);
  textSize(25);
  text("Score: "+score,20,20,160,30);
  textSize(30);
  textAlign(CENTER);
  text("Level: "+level,width/2-50,20,120,50);
  

  //displays level number in the center of the screen
  if (currentTime < newVariable + 2000){ 
    textAlign(CENTER);
    textSize(60);
    text("LEVEL "+level,width/2,height/2-40);
  }
  
  //which direction the turtle should display; straight or horizontally flipped
  if (left == true){
    turtle.displayLeft();
  }
  else {
    turtle.displayRight();
  }
  
  //display jellyfish
  for (int i=0; i<numJellyfish; i++){
    jellyfish[i].display();
    jellyfish[i].move();
    
    //if turtle touches jellyfish, the jellyfish disappears (gets eaten)
    if ((turtle.xpos +45 > jellyfish[i].xpos-10 )&& (turtle.xpos -45< jellyfish[i].xpos +10)&& (turtle.ypos+27 > jellyfish[i].ypos-15)&& (turtle.ypos-27< jellyfish[i].ypos+20)){
      eatjellyfish.amp(0.1f);
      eatjellyfish.play();
      score++;
      
      //how to win the game
      if (score == 10){
        win = true;
      }
      
      jellycount++;
      jellyfish[i].ypos = -40;
      
      //increase level whenever all the jellyfish are eaten
      if (jellycount == numJellyfish){
        levelup.amp(0.2f);
        levelup.play();
        jellycount = 0;
        level++;
        newVariable = millis();

        //next level: trash falls faster
        for(int k=0; k<trash.length; k++){
          trash[k].speed = random(startSpeed+0.5f,stopSpeed+0.5f);
        }
        
        //reset position of jellyfish on screen
        for(int j=0; j<numJellyfish; j++){
          jellyfish[j].display();
          jellyfish[j].ypos = random(height);
        }
      }
    }  
  }
  
  //display trash
  for (int i=0; i<trash.length; i++){
    trash[i].display();
    trash[i].fall();
    
    //what happens when the turtle touches different types of trash
    //with reference to objectsArray_Cars.pde
    
    //bottle
    if (trash[i].TShape == 0){
      if ((turtle.xpos +45 > trash[i].xpos-10) && (turtle.xpos-45< trash[i].xpos) && (turtle.ypos+27 > trash[i].ypos-20) && (turtle.ypos-27< trash[i].ypos+40)){
        gameOver = true;
      }
    }
    //plastic bag
    if (trash[i].TShape == 1){
      if ((turtle.xpos+45 > trash[i].xpos-15) && (turtle.xpos -45< trash[i].xpos+15) && (turtle.ypos+27 > trash[i].ypos-20) && (turtle.ypos-25< trash[i].ypos+15)){
        gameOver = true;
      }
    }
    //plastic ring
    else if (trash[i].TShape == 2){
      if ((turtle.xpos+45 > trash[i].xpos-15) && (turtle.xpos -45< trash[i].xpos+15) && (turtle.ypos+27 > trash[i].ypos-15) && (turtle.ypos-27< trash[i].ypos+15)){
        gameOver = true;
      }
    }
    //can
    else{
      if ((turtle.xpos +45 > trash[i].xpos-15) && (turtle.xpos -45< trash[i].xpos +15) && (turtle.ypos+27 > trash[i].ypos-25) && (turtle.ypos-27< trash[i].ypos+25)){
        gameOver = true;
      }
    }
  }
  //check if game over, then switch to the losing screen
  if (gameOver == true){
    gameover.play();
    background.screen = 3;
    background.display();
  }
  //chceck if user won, then switch to winning screen
  if (win == true){
    winningSound.amp(0.05f);
    winningSound.play();
    background.screen = 4;
    background.display();
  }
}
    
//move the turtle
public void keyPressed(){
  if (key == CODED){
    if (keyCode == UP){
      turtle.moveUp();
    }
    else if (keyCode == DOWN){
      turtle.moveDown();
    }
    else if (keyCode == LEFT){
      left = true;
      right = false;
      turtle.moveLeft();
    }
    else if (keyCode == RIGHT){
      right = true;
      left = false;
      turtle.moveRight();
    }
  }
}

//when user clicks on the 'start' or 'next button', switch to next screen
//if it is in game over screen, switch back to start screen
public void mouseClicked(){
  if ((mouseX > (width/2)-50) && (mouseX < (width/2)+50) && (mouseY > height-150) && (mouseY < height-100)){
    if ((background.screen == 0) || (background.screen == 1)){
      background.screen = background.screen + 1;
      background.display();
      newVariable = millis();
    }
    
    //resetting everything
    else if ((background.screen == 3) || (background.screen == 4)){
      background.screen = 0;
      turtle.xpos = width/2;
      turtle.ypos = height/2;
      score = 0;
      level = 1;
      startSpeed = 1;
      stopSpeed = 2;
      
      for (int i=0; i<trash.length; i++){
        trash[i].xpos = random(width);
        trash[i].ypos = 0;
        trash[i].display();
        trash[i].fall();
      }
      for (int i=0; i<numJellyfish; i++){
        jellyfish[i].ypos = random(height);
      }
      
      background.display();
      
      gameOver = false;
      win = false;
    }
  }
}
class Background {
  int screen;
  PImage underwaterImg; 
  PImage gameOverImg;
  PImage startImg;
  PImage youwinImg;
  PFont titlefont;
  PFont characterfont;
  
  Background(int sc){
    screen = sc;
    underwaterImg = loadImage("underwater.jpg");
    gameOverImg = loadImage("gameoverbg.jpg");
    youwinImg = loadImage("winbg.jpg");
    startImg = loadImage("startbg.jpg");
    titlefont = loadFont("junegull48.vlw");
    characterfont = loadFont("lieberuthbold.vlw");
  }
  
  public void display(){
    //start screen
    if (screen == 0){
      String info = "Loggerhead sea turtles are an endangered species. Human interference like garbage and fishing nets kills hundreds of them every year! The baby sea turtles often die before they even make it to the water. Our actions are harming these innocent little creatures! We need to save them!";
      background(startImg);
      stroke(0);
      noFill();
      rect(width/2-50,height-150,100,50);
      
      fill(0);     
      textFont(titlefont);
      textAlign(CENTER);
      textSize(35);
      text("Welcome to Save the Turtle!",width/2,150);
      
      textFont(characterfont);
      textSize(30);
      text("Next",width/2,height-115);
      textSize(23);
      text(info, 80, 200, width-160, height-40);
    }
    //instructions screen
    else if (screen == 1){
      String instructions = "This little turtle needs your help!\n\nMove the turtle up, down, left or right using the arrow keys.\n\nCatch the jellyfish by making the turtle touch them, but make sure to avoid the falling trash!\n\nCatch 10 jellyfish to win the game!";
      background(startImg);
      stroke(0);
      noFill();
      rect(width/2-50,height-150,100,50);
      
      fill(0);
      textFont(titlefont);
      textAlign(CENTER);
      textSize(30);
      text("Instructions",width/2,100);
      
      textFont(characterfont);
      textAlign(CENTER);
      textSize(30);
      text("Start",width/2,height-115);
      textSize(24);
      textAlign(LEFT);
      text(instructions, 90, 130, width-180, height-40);
    }
    //play game screen
    else if (screen == 2){
      textFont(titlefont);
      background(underwaterImg);
    }
    //losing screen
    else if (screen == 3){
      String message = "Remember: a real turtle gets only one life! We must all help conserve them and make sure we don't cause them any harm! Let's let these loveable loggerheads live!";
      background(gameOverImg);
      
      fill(0);
      textAlign(CENTER);
      textSize(50);
      text("GAME OVER",width/2,100);
      
      textFont(characterfont);
      textSize(23);   
      text(message, 80, 170, width-160, height-40);
      text("Click the turtle to restart!",60,300,width-100,height-30);
    }
    //winning screen
    else if (screen == 4){
      String winMessage = "Your turtle is safe! Keep your efforts going!";
      background(youwinImg);
      
      fill(0);
      textAlign(CENTER);
      textSize(50);
      text("YOU WIN!",width/2,100);
      
      textFont(characterfont);
      textSize(23);   
      text(winMessage, 90, 170, width-160, height-40);
      text("Click the turtle to restart!",60,300,width-100,height-30);
    }
  }
  
}
    
class Jellyfish {
  float xpos;
  float ypos;
  int JColor;
  float speed;
  PImage jellyfish;
  
  Jellyfish(float x, float y, int c, float sp){
    xpos = x;
    ypos = y;
    JColor = c;
    speed = sp;
    jellyfish = loadImage("jellyfish.png");
  }
  
  public void display(){
    noStroke();
    fill(JColor);
    image(jellyfish,xpos,ypos,35,60);
  }
  
  public void move(){
    xpos = xpos + speed;
    if ((xpos <= 0) || (xpos >= width)){
      speed = -speed;
    }
  }
  
  public void disappear(){
    xpos = width +50;
  }
  
}
class Trash {
  float xpos;
  float ypos;
  int TColor;
  float speed;
  int TShape;
  PImage plasticbag;
  PImage bottle;
  PImage plasticrings;
  PImage can;
  
  Trash(float x, float y, int c, float sp, int sh){
    xpos = x;
    ypos = y;
    TColor = c;
    speed = sp;
    TShape = sh;
    plasticbag = loadImage("plasticbag.png");
    bottle = loadImage("bottle.png");
    plasticrings = loadImage("plasticrings.png");
    can = loadImage("can.png");
  }
  
  public void display(){
    fill(TColor);
    noStroke();
    //TShape = 0: bottle; 1: plastic bag; 2: plastic ring; 3: can
    if (TShape == 0){
      //rect(xpos,ypos,20,40);
      image(bottle, xpos,ypos,30,75);
    }
    else if (TShape == 1){
      //ellipse(xpos,ypos,40,50);
      image(plasticbag, xpos,ypos,60,70);
    }
    else if (TShape == 2){
      noFill();
      stroke(TColor);
      //ellipse(xpos,ypos,30,30);
      image(plasticrings, xpos,ypos,60,60);
    }
    else {
      image(can, xpos,ypos,30,50);
    }
  }
  
  public void fall(){
    ypos = ypos + speed;
    
    if (ypos >= height){
      ypos = 0;
      xpos = random(width);
      TShape = (int)random(0,4);
    }
  }
  
}
  
  
class Turtle{
  float xpos;
  float ypos;
  float speed = 15;
  PImage turtleImg;
  PImage turtleImgFlipped;

  Turtle(float x, float y){
    xpos = x;
    ypos = y;
    turtleImg = loadImage("turtle.png");
    turtleImgFlipped = loadImage("turtleflipped.png");
  }
  
  public void displayLeft(){
    noStroke();
    image(turtleImg,xpos,ypos,90,55);
  }
  
  public void displayRight(){
    noStroke();
    image(turtleImgFlipped,xpos,ypos,90,55);
  }
  
  public void moveUp(){
    ypos = ypos-speed;
    if (ypos < 0){
      ypos = 0;
    }
  }
  
  public void moveDown(){
    ypos = ypos + speed;
    if (ypos > height){
      ypos = height;
    }
  }
  
  public void moveLeft(){
    xpos = xpos-speed;
    if (xpos < 0){
      xpos = 0;
    }
  }
  
  public void moveRight(){
    xpos = xpos+speed;
    if (xpos > width){
      xpos = width;
    }
  }
  
}
  public void settings() {  size(800,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "turtle_game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
