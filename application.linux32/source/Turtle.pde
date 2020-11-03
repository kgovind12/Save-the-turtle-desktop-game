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
  
  void displayLeft(){
    noStroke();
    image(turtleImg,xpos,ypos,90,55);
  }
  
  void displayRight(){
    noStroke();
    image(turtleImgFlipped,xpos,ypos,90,55);
  }
  
  void moveUp(){
    ypos = ypos-speed;
    if (ypos < 0){
      ypos = 0;
    }
  }
  
  void moveDown(){
    ypos = ypos + speed;
    if (ypos > height){
      ypos = height;
    }
  }
  
  void moveLeft(){
    xpos = xpos-speed;
    if (xpos < 0){
      xpos = 0;
    }
  }
  
  void moveRight(){
    xpos = xpos+speed;
    if (xpos > width){
      xpos = width;
    }
  }
  
}
