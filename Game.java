//Game.java
//@author Dylan Heid
//------------------------------
import java.util.Scanner;
import java.time.LocalDate;
import java.io.Serializable;

public class Game implements Serializable{
   
   String opposing_team;   //name of team being faced
   boolean home;  //true if game is at home: false if game is away
   LocalDate date;   //date the game took place
   LList<Outing> outings = new LList<>(); //LList of outings for each pitcher that throws
   
   public Game(String t, boolean h){
      this.opposing_team = t;
      this.home = h;
      this.date = LocalDate.now();
   }//ends Game constructor
   
   public Game(String t, boolean h, LocalDate d){
      this.opposing_team = t;
      this.home = h;
      this.date = d;
   }//ends Game constructor (For loading from file)
   
   //Getters-GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
   public String getTeam(){   return opposing_team;   }
   
   public boolean getHome(){  return home;   }
   
   public LocalDate getDate() {  return date;   }
   
   public int getNumOutings() {  return outings.size();  }
   //ends getters-GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
   
   //Setters-SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
   public void setTeam(String name){ this.opposing_team = name;   }
   
   public void setHome(boolean home){  this.home = home;  }
   
   public void setDate(LocalDate date){   this.date = date;  }
   //ends setters-SSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
   
   
   //----------------------------------------------------------------------------------
   
   public void addOuting(Pitcher p, Scanner scan){
      Outing newOuting = new Outing(p, this.opposing_team, this.home, this.date);
      this.outings.add(newOuting);
      //throw pitches for outing
      newOuting.throwPitches(scan);
      //copy over outing to that pitchers outings
      p.addOuting(newOuting);         
   }//ends addOuting
   
   //----------------------------------------------------------------------------------
   
   public void viewPitchers(){
      System.out.println("Pitchers for this game...");
      System.out.println("--------------------------------");
      int i = 1;
      for(Outing o: this.outings){
         System.out.print(i + ") ");
         System.out.println(o.getPitcher().getNameAndNum());
         i++;
      }
      System.out.println("--------------------------------\n");
   }//ends viewPitchers
   
   //----------------------------------------------------------------------------------
   
   public void viewGame(){
      //print if home
      if(this.home)
         System.out.print("Home ");
      else
         System.out.print("Away ");
      
      //print who against
      System.out.println(" vs " + this.opposing_team);
      
      //print starting pitcher
      System.out.print("Starting Pitcher: ");
      System.out.println(this.outings.get(0).getPitcher().getNameAndNum());
   
   }
   
}//ends Game class