//Game.java
//@author Dylan Heid
//------------------------------
import java.time.LocalDate;
import java.io.Serializable;

public class Game implements Serializable{
   
   String opposing_team;   //name of team being faced
   boolean home;  //true if game is at home: false if game is away
   LocalDate date;   //date the game took place
   LList<Outing> outings = new LList<>(); //LList of outings for each pitcher that throws
   
   public Game( LList<Outing> outings){
	   this.outings = outings;
   }//ends Game constructor
   
   public Outing getOuting(int index) {return this.outings.get(index);}
   
   public int getNumOutings() {  return outings.size();  }
   //ends getters-GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
   
   //----------------------------------------------------------------------------------
   
   public void addOuting(Outing O){
      this.outings.add(O);
   }//ends addOuting
   
   //----------------------------------------------------------------------------------

   
}//ends Game class