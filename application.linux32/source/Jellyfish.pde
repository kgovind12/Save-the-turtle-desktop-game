class Jellyfish {
  float xpos;
  float ypos;
  color JColor;
  float speed;
  PImage jellyfish;
  
  Jellyfish(float x, float y, color c, float sp){
    xpos = x;
    ypos = y;
    JColor = c;
    speed = sp;
    jellyfish = loadImage("jellyfish.png");
  }
  
  void display(){
    noStroke();
    fill(JColor);
    image(jellyfish,xpos,ypos,35,60);
  }
  
  void move(){
    xpos = xpos + speed;
    if ((xpos <= 0) || (xpos >= width)){
      speed = -speed;
    }
  }
  
  void disappear(){
    xpos = width +50;
  }
  
}
