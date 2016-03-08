package BreakoutGame;


import java.util.Random;
import java.awt.Image;

import ch.aplu.jgamegrid.*;

public class Breaker extends Actor
{
  private double speed;
  private BreakoutGame gg;
  private int _number;
  private Random _ran;
  private int _randomInt;

  public Breaker(BreakoutGame gg, int pNumber)
  {
    super("/home/vargas/Documentos/sprites/ball.png",4);
    this.gg = gg;
    _number = pNumber;
    _ran  = new Random();
    _randomInt = _ran.nextInt(3);
    show(_randomInt);
    
  }
  
  public int getNumber(){
	  return _number;
  }
  
  public void act()
  {
    double dir = getDirection();
    if (getY() > this.gameGrid.nbVertCells - 60)
    { //breaker has dropped
      this.hide();
      this.setActEnabled(false);
      gg.ballCounter();
    }
    //collision with wall:
    if (getX() < 10)
    {
      dir = 180 - dir;
    }
    if (getX() > 790)
    {
      dir = 180 - dir;
    }
    if (getY() < 10)
    {
      dir = 360 - dir;
    }
    setDirection(dir);
    move();
  }
}
