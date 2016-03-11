package BreakoutGame;



import ch.aplu.jgamegrid.Actor;

public class Brick extends Actor
{
  public Brick(int sprite)
  {
    super("/home/adrian/Documentos/sprites/brickS.png", 4);
    show(sprite);
  }
}
