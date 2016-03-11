package BreakoutGame;



import ch.aplu.jgamegrid.*;
import comunication_Handler.client_Sender;
import comunication_Handler.client_handler;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Lists.Double_Linked_List;

public class BreakoutGame extends GameGrid implements GGMouseListener, GGActorCollisionListener
{
  private int points;
  private int TIMELIMIT ; //in seconds
  private int STARTSPEED ;
  private long startTime;
  private int _ballCounter;
  private Double_Linked_List<Actor> _breakers;
  private Double_Linked_List<Actor> _actors;
  private Double_Linked_List<Actor> _bars;
  private Double_Linked_List<Actor> _bricks;
  private boolean gameOver, startingStage;


  public BreakoutGame(int pWidth, int pHeigth,int pTimeLimit, int pStartSpeed)
  {
    super(pWidth, pHeigth, 1, false);
    TIMELIMIT = pTimeLimit;
    STARTSPEED = pStartSpeed;
    _breakers= new Double_Linked_List();
    _actors = new Double_Linked_List();
    _bars = new Double_Linked_List();
    _bricks = new Double_Linked_List();
    getBg().setBgColor(Color.green);   
    initGame();
  }

  public void initGame(){
	    reset();
	    setSimulationPeriod(STARTSPEED);
	    addMouseListener(this, GGMouse.move | GGMouse.lClick);
	    getBg().setPaintColor(Color.red);
	    show();
	    doRun();
  }
  
  public void act()
  {
    if (!(startingStage || gameOver))
    {
      getBg().clear();
      long timeLeft = (TIMELIMIT * 1000 + startTime - System.currentTimeMillis()) / 1000;
      if (timeLeft > 0)
        getBg().drawText(timeLeft + " seconds left", new Point(600, 540));
      else
        gameOver();
    }
  }

  public void reset()
  {
    setTitle("Breaker-Game, start by clicking. Break as many bricks as possible in " + TIMELIMIT + " seconds");
    deleteAll(_breakers);
    deleteAll(_bars);
    client_handler.getInstance().newBall();
    client_handler.getInstance().newBar();
    getBg().clear();
    _ballCounter = 0;
    points = 0;
    startTime = 0;
    gameOver = false;
    startingStage = true;
    removeActors(Brick.class);
    setAllState(_breakers, false);
    createBricks();
  }

  public boolean mouseEvent(GGMouse mouse)
  {
    switch (mouse.getEvent())
    {
      case GGMouse.lClick:
        if (gameOver)
        {
          reset();
        }
        else if (startTime == 0)
        { //start game
          startTime = System.currentTimeMillis();
          _breakers.getNode(0).get_Data().setActEnabled(true);
          startingStage = false;
        }
        break;

      case GGMouse.move:
        _bars.getNode(0).get_Data().setX(mouse.getX());
       // if (startingStage)
         // _breakers.getNode(0).get_Data().setX(mouse.getX());
        //break;
    }
    return true;
  }

  public int collide(Actor actor1, Actor actor2)
  {
    double dir = actor1.getDirection();
    //how did they hit each other?
    double hitDirection = actor2.getLocation().getDirectionTo(actor1.getLocation());
    System.out.println("Collision! " + actor1 + " vs " + actor2 + " hitDir: " + hitDirection);
    System.out.println("direction before: " + actor1.getDirection());
    if (actor2.getClass().equals(Brick.class))
    { //hit brick
      actor1.setDirection(reflectPhysicallyCorrect(actor1.getLocation(), actor2.getLocation(), dir));
      points += 10;
      if(points%40==0){
      client_handler.getInstance().newBall();}
      if (getActors(Brick.class).size() == 0)
        gameOver();
      else
        setTitle("Breaker-Game   Points: " + points);
      removeActor(actor2);
      System.out.println("direction after: " + actor1.getDirection());
      return 0; //be immediately ready for next collision
    }
    else
    { //hit pad
      if (actor1.getY() <= actor2.getY()) // breaker has to be higher than bar
        actor1.setDirection(reflectWithHitZones(actor1.getLocation(), actor2.getLocation()));
      return 10; //don't hit for another 10 cycles
    }
  }

  private double reflectWithHitZones(Location loc1, Location loc2)
  {
    int distance = loc1.x - loc2.x;
    int dir = (int)(distance * 2.5);
    System.out.println(distance + " -> winkel: " + dir);
    return dir - 90;
    //TODO: what if exactly 90°
  }

  private double reflectPhysicallyCorrect(Location loc1, Location loc2, double dir)
  {
    int xMovement = loc2.x - loc1.x;
    int yMovement = loc2.y - loc1.y;
    if (Math.abs(xMovement) <= 25) //von oben und unten
      dir = 360 - dir;
    else if (Math.abs(yMovement) <= 10) //von links und rechts
      dir = 180 - dir;
    else
      System.out.println("omg, none of the cases!");
    return dir;
  }

  public void gameOver()
  {
	  
    gameOver = true;
    setAllState(_breakers, false);
    allHide(_bars);
    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
    setTitle("Game Over! " + points + " points " + " in " + elapsedTime + " seconds. Reset by clicking.");
  }
  
  public void newBall(int pX,int pY, int pNumber){
	  Breaker ball = new Breaker(this,pNumber);
	  ball.setCollisionCircle(new Point(0, 0), 10);
	  addActor(ball, new Location(pX, pY));
	  ball.addActorCollisionListener(this);
	  _breakers.insert(ball);
	  collisionAll(ball, _bars);
	  collisionAll(ball, _bricks);
	  ball.setDirection((Math.random() * 120 - 60) + 270);
	  _ballCounter++;
	  System.out.println(_ballCounter);
	  
  }
  
  public void collisionAll(Actor pAct, Double_Linked_List<Actor> pActList){
	  int cont = 0;
	  while(pActList.getNode(cont)!=null){
		  collisionAdder(pAct,pActList.getNode(cont).get_Data() );
		  collisionAdder(pActList.getNode(cont).get_Data(),pAct );
		  cont++;
	  }
  }
  
  public void setAllState(Double_Linked_List<Actor> pActList, boolean pState){
	  int cont = 0;
	  while(pActList.getNode(cont)!=null){
		  pActList.getNode(cont).get_Data().setActEnabled(pState);
		  cont++;
	  }
  }
  
  public void newBar(int pX,int pY, int pNumber){
	   BreakerBar Bar = new BreakerBar(pNumber);
	   addActor(Bar, new Location(pX, pY));
	   Bar.setCollisionRectangle(new Point(0, 0), 100,80);
	   _actors.insert(Bar);
	   _bars.insert(Bar);
	   collisionAll(Bar, _breakers);
	       
  }
  
  public void deleteAll(Double_Linked_List pList){
	  while(pList.getNode(0)!=null){
		  pList.delete(0);
	  }
  }
  
  public Double_Linked_List<Actor> resetLists(Double_Linked_List<Actor> pList){
	  pList = null;
	  pList = new Double_Linked_List();
	  return pList;
  }
  
  public void collisionAdder(Actor pAct1, Actor pAct2){
	  pAct1.addCollisionActor(pAct2);
  }
 
  public void ballCounter(){
	 _ballCounter =  _ballCounter-1;
	  if(_ballCounter < 0 ){
		  gameOver();
	  }
	  
  }

  public void allHide(Double_Linked_List<Actor> pList){
	  int cont = 0;
	  while(pList.getNode(cont)!=null){
		  pList.getNode(cont).get_Data().hide();;
		  cont++;
	  }
  }

  public void createBricks(){
	    for (int j = 0; j < 10; j++)
	    {
	      for (int i = 0; i < 13; i++)
	      {
	        if (Math.random() < 0.85)
	        { //erstelle nur 85 % der Steine
	          Brick brick = new Brick(j / 3);
	          brick.setCollisionRectangle(new Point(0, 0), 50, 20);
	          collisionAll(brick, _breakers);
	          addActor(brick, new Location(i * 51 + 90, 21 * j + 60));
	          _bricks.insert(brick);
	        
	        }
	      }
	    }
  }
  
  public void createBrick(int pX, int pY, int pHeigth, int pWidth){
	  Brick brick = new Brick(pY / 3);
	  brick.setCollisionRectangle(new Point(0, 0), pWidth, pHeigth);
      collisionAll(brick, _breakers);
      addActor(brick, new Location(pX, pY));
      _bricks.insert(brick);
  }
  
}
