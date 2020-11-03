class Trash {
  float xpos;
  float ypos;
  color TColor;
  float speed;
  int TShape;
  PImage plasticbag;
  PImage bottle;
  PImage plasticrings;
  PImage can;
  
  Trash(float x, float y, color c, float sp, int sh){
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
  
  void display(){
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
  
  void fall(){
    ypos = ypos + speed;
    
    if (ypos >= height){
      ypos = 0;
      xpos = random(width);
      TShape = (int)random(0,4);
    }
  }
  
}
  
  
