//Pitch object
//@author Dylan Heid
//---------------------------
import java.io.Serializable;

//sets invalid data to zero to be counted as NULL
public class Pitch implements Serializable{
   
   //fields
   protected byte type;   //1 = fastball: 2 = Curveball: 3 = Slider: 4 = Changeup: 5 = Knuckleball: 6 = else
   protected boolean strike;   //true if pitch is a strike, else a ball
   protected byte velo;   //(-128 - 127) ex. 93 for 93mph 
   
   //default constructor
   //sets to NULL values
   public Pitch(){
      this.velo = 0;
      this.type = 0;
      this.strike = false;
   }//ends default
   
   //constructor
   //0 is a NULL value and will not be counted
   //if invalid data set velo to 0
   public Pitch(int t, int s, int v){
      
      //see if v is valid
      if(v > 127 || v < 30){
         this.velo = 0;
      }else{
         this.velo = (byte)v;
      }
      
      
      //see if t is valid
      if(t > 6 || t < 0){
         this.type = 0;
      }else{
         this.type = (byte)t;
      }
      
      if(s == 1)
         this.strike = true;
      else
         this.strike = false;
      
   }//ends constructor
   
   //for loading in data
   public Pitch(int t, boolean s, int v){
      this.type = (byte)t;
      this.strike = s;
      this.velo = (byte)v;
   }
   
   //Getters------------------------------------------------
   
   public int getType() {  return (int)this.type;  }
   
   public boolean isStrike(){ return this.strike;  }
   
   public int getVelo() {  return (int)this.velo;  }
   
   //Setters------------------------------------------------
   
   public void setStrike(int s){
      if(s == 1)
         this.strike = true;
      else
         this.strike = false;
   }//ends setStrike
   
   public void setVelo(int v){
      if(v > 30 || v < 127)
         this.velo = (byte)v;
   }//ends setVelo
   
   public void setType(int t){
      if(t > 0 || t < 7)
         this.type = (byte)t;
   }//ends setType
   
   //-------------------------------------------------------
   
   
   
}//ends class