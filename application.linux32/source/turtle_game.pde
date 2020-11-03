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
import processing.sound.*;

Turtle turtle;
Jellyfish [] jellyfish = new Jellyfish[5];
Trash [] trash = new Trash[4];
color [] Colors = {#CE4141, #3ACB64, #653ACB, #C9256C, #EADC36};
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

void setup(){
  size(800,600);
  noStroke();
  imageMode(CENTER);
  
  //constructing turtle, trash and jellyfish objects
  turtle = new Turtle(width/2,height/2);
  for (int i=0;i<jellyfish.length;i++){
    jellyfish[i] = new Jellyfish(random(width),random(height),#F5D7EA,1);
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
  

void draw(){  
  if (background.screen == 2){
    //newVariable = millis();
    playGame();
  }   
}

void playGame(){
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
      eatjellyfish.amp(0.1);
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
        levelup.amp(0.2);
        levelup.play();
        jellycount = 0;
        level++;
        newVariable = millis();

        //next level: trash falls faster
        for(int k=0; k<trash.length; k++){
          trash[k].speed = random(startSpeed+0.5,stopSpeed+0.5);
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
    winningSound.amp(0.05);
    winningSound.play();
    background.screen = 4;
    background.display();
  }
}
    
//move the turtle
void keyPressed(){
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
void mouseClicked(){
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
