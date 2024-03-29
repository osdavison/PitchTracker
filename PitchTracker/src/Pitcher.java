//Pitcher class
//@author Dylan Heid
//---------------------------
import java.io.Serializable;

public class Pitcher implements Serializable, Comparable<Pitcher>{
   
   //fields
   protected String first_name;
   protected String last_name;
   protected int number;
   
   //transient variables
   protected LList<Outing> outings; //list of this pitchers outings
   protected transient double avgV1;  //avg fastball velo
   protected transient double avgV2;  //avg curveball velo
   protected transient double avgV3;  //avg slider velo
   protected transient double avgV4;  //avg change-up velo
   protected transient double avgV5;  //avg knuckleball velo
   protected transient double avgV6;  //avg other velo
   
   //param n is a unique number
   public Pitcher(String f, String l, int n){
	  this.first_name = f;
      this.last_name = l;
      this.number = n;
      this.outings = new LList<>();
   }//ends constructor
   
   public Pitcher(String name, int n){
	      String[] NAME = name.split(" ");
		  this.first_name = NAME[0];
	      this.last_name = NAME[1];
	      this.number = n;
	      this.outings = new LList<>();
	   }//ends constructor
   
   
   //*********************************************************************************
   
   //Getters-GGGGGGGGGGGGGGGGGGGGG
   public String getFirstName(){ return this.first_name; }
   
   public String getLastName(){  return this.last_name;  }
   
   public int getNumber(){ return this.number;  }

   
   //GGGGGGGGGGGGGGGGGGGGGGGGGGGGG
   
   //Setters-SSSSSSSSSSSSSSSSSSSSSSSSSSS
   public void setFirstName(String name){ this.first_name = name; }
   
   public void setLastName(String name){  this.last_name = name;  }
   
   public boolean setnumber(int num, LList<Pitcher> pitchers){  
      //see if another pitcher already has that number
      for(Pitcher p: pitchers){
         if(p.getNumber() == num)
            return false;
      }
      //if you get here the number was not found
      this.number = num;
      return true;
   }//ends set number
   
   
   //SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
   
   //*********************************************************************************
   
   public void addOuting(Outing o){
      this.outings.add(o);
   }
   
   //--------------------------------------------------------------------------------------------
   
   //returns true if first and last name are same
   //returns true if number is the same
   public boolean equals(Pitcher p){
      if(this.first_name.equals(p.first_name) && this.last_name.equals(p.last_name))
         return true;
      if(this.number == p.number)
         return true;
      return false;
   }//ends equals method
   
   //--------------------------------------------------------------------------------------------
   
   public String getNameAndNum(){
      return (this.first_name + " " + this.last_name + " #" + this.number);
   }

   //--------------------------------------------------------------------------------------------
   
   public void viewOutings(){
      int i = 1;
      for(Outing o: this.outings){
         System.out.println("---------------------------------------------------------------");
         System.out.print(i + ") ");
         o.printOuting();
         System.out.println("---------------------------------------------------------------");
         i++;
      }
   }//ends viewOutings
   
   //--------------------------------------------------------------------------------------------
   
   public void reloadData(){
      for(Outing o: outings){
         o.calcAll();
      }
   }//ends reloadData
   
   @Override
   public int compareTo(Pitcher p) {
      int lengthParam = p.getLastName().length(); 
      int lengthThis = this.getLastName().length(); 
      int lmin = Math.min(lengthParam, lengthThis); 
  
      for (int i = 0; i < lmin; i++) { 
         int str1_ch = (int)this.getLastName().charAt(i); 
         int str2_ch = (int)p.getLastName().charAt(i); 
  
         if (str1_ch != str2_ch) { 
            return str1_ch - str2_ch; 
         } 
      } 
  
      // Edge case for strings like 

      if (lengthParam != lengthThis) { 
         return lengthParam - lengthThis; 
      } 
  
      // If none of the above conditions is true, 
      // it implies both the strings are equal 
      else { 
         return 0; 
      }
   }//ends compareTo
   
}//ends class