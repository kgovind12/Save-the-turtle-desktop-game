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
  
  void display(){
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
    
