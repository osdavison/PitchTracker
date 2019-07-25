//Outing class
//@author Dylan Heid
//---------------------------
import java.time.LocalDate;
import java.io.Serializable;

public class Outing implements Serializable{

   //global fields
   protected Pitcher pitcher;  //pitcher of the outing
   protected String opposing_team;   //name of opposing team
   protected boolean home;  //true if home, false if away
   protected LocalDate date;  //date outing was thrown
   protected LList<Pitch> pitches = new LList<>(); //list of pitch objects
   
   //transient variables
   protected int pitch_count;          //number of pitches thrown in outing
   protected transient int strikes;    //total number of strikes
   protected transient double sp;      //overall strike percentage
   protected transient double avgV1;   //avg fastball velo
   protected transient double avgV2;   //avg curveball velo
   protected transient double avgV3;   //avg slider velo
   protected transient double avgV4;   //avg change-up velo
   protected transient double avgV5;   //avg knuckleball velo
   protected transient double avgV6;   //avg splitter velo
   protected transient int t1;     //Top fastball
   protected transient int t2;     //Top curveball
   protected transient int t3;     //Top slider
   protected transient int t4;     //Top change-up
   protected transient int t5;     //Top knuckleball
   protected transient int t6;     //Top splitter
   protected transient double sp1;     //strike % fastball
   protected transient double sp2;     //strike % curveball
   protected transient double sp3;     //strike % slider
   protected transient double sp4;     //strike % change-up
   protected transient double sp5;     //strike % knuckleball
   protected transient double sp6;     //strike % splitter
   protected transient double pt1;     //PT% fastball
   protected transient double pt2;     //PT% curveball
   protected transient double pt3;     //PT% slider
   protected transient double pt4;     //PT% change-up
   protected transient double pt5;     //PT% knuckleball
   protected transient double pt6;     //PT% splitter
   

   public Outing(Pitcher p, LList<Pitch> Pitches, String team, boolean home, LocalDate d){
	  this.pitches = Pitches;
      this.pitcher = p;
      this.opposing_team = team;
      this.home = home;
      this.pitch_count = 0;
      this.strikes = 0;
      this.sp = 0;
      this.avgV1 = 0;  
      this.avgV2 = 0;  
      this.avgV3 = 0;  
      this.avgV4 = 0;  
      this.avgV5 = 0;  
      this.avgV6 = 0;
      this.sp1 = 0;  
      this.sp2 = 0;  
      this.sp3 = 0;  
      this.sp4 = 0;  
      this.sp5 = 0;  
      this.sp6 = 0;
      this.t1 = 0;     
      this.t2 = 0;     
      this.t3 = 0;
      this.t4 = 0;
      this.t5 = 0;
      this.t5 = 0;  
      this.date = d;
      
   }//ends constructor
   
   //GETTERS---------------------------------------------------------------
   public Pitcher getPitcher(){  return this.pitcher; }
   
   public String getFirstName(){ return this.pitcher.getFirstName(); }
   
   public String getLastName(){ return this.pitcher.getLastName(); }
   
   public int getNumber(){ return this.pitcher.getNumber(); }
   
   public int getPitchCount(){   return this.pitches.size();   }
   
   public double[] getPercentages(){
	      calcAll();
	      double[] array = new double[6];
	      array[0] = 70;
	      array[1] = 20;
	      array[2] = 22;
	      array[3] = 44;
	      array[4] = 54;
	      array[5] = 0;
	      return array;
	   }
   
   public LList<Pitch> getPitchList(){	return this.pitches;	}
   
   
   //SETTERS---------------------------------------------------------------
   public void setPitcher(Pitcher p){this.pitcher = p;}
   
   public void addPitch(Pitch p) {this.pitches.add(p);}
   
   public void CalcPitches(LList<Pitch> p){
 
       for(int i = 0; i < p.size; i++)
    	   this.pitches.add(p.get(i));
         
      
      //all pitches have been throw now adjust values*******
      
      //adjusts all data
      calcAll();
      
   }//ends throwPitches
   
   
   
   
   //------------------------------------------------------------------------------
   
   public void printPitches() {
	   
	   for(int i = 0; i < pitches.size(); i++) {
		   System.out.println(pitches.get(i).getType() + " " + pitches.get(i).getVelo() + " " + pitches.get(i).isStrike());
	   }
   }//ends printPitches
   
   
   //calc avg velocity for given pitch type
   private double calcAvg(int type){
   
      int count = 0;   //number of pitches
      long total_velo = 0;
      double avg;
      
      for(Pitch p: this.pitches){
         if(p.getType() == type){
            if(p.getVelo() > 0){
               count++;
               total_velo += p.getVelo();
            }//ends 
         }
      }//ends looping through list
      if(count > 0)
         avg = (double)total_velo / (double)count;
      else
         avg = 0;
      return avg;
        
   }//ends calcAvg
   
   //------------------------------------------------------------------------------
   
   //if no pitches thrown for pitch type, sp will be -1
   private double calcSP(int type){
   
      int count = 0;   //number of pitches
      int strikes_temp = 0;
      double percent = -1.0;
      
      for(Pitch p: this.pitches){
         if(p.getType() == type){
            count++;
            if(p.isStrike())
               strikes_temp++;
         }
      }//ends looping through list
      
      if(count > 0)
         percent = (double)strikes_temp / (double)count;
         
      return percent;
        
   }//ends calcAvg
   
   //-------------------------------------------------------------------------------
   
   //if no pitches thrown for pitch type, T will be -1
   private int calcT(int type){
   
      int top = -1;   //number of pitches
      //double percent;
      
      for(Pitch p: this.pitches){
         if(p.getType() == type){
            if(p.getVelo() > top)
               top = p.getVelo();
         }
      }//ends looping through list
      
      return top;
        
   }//ends calcAvg
   
   //-------------------------------------------------------------------------------
   
   //calculate percentage of that pitch thrown
   //if no pitches thrown for pitch type, PT will be -1
   private double calcPT(int type){
   
      int count = 0; //number of that pitch thrown
      //double percent;
      
      for(Pitch p: this.pitches){
         if(p.getType() == type){
               count++;
         }
      }//ends looping through list
      
      return (double)count/(double)pitches.size();
        
   }//ends calcAvg
   
   //-------------------------------------------------------------------------------
   
   public void printOuting(){
   
      //print first name, last name, and number
      System.out.println(this.pitcher.getNameAndNum());
      
      //print if home
      if(this.home)
         System.out.print("Home ");
      else
         System.out.print("Away ");
      
      //print date
      System.out.println("vs " + this.opposing_team + " " + this.date);
      
      //print pitch count
      System.out.println("Pitch count: " + this.pitch_count);
      
      //print strike %
      System.out.println("General K%: " + Math.round(this.sp * 100.0)/100.0);
      
      
      //only prints avg velos & strike percentages for pitches that were thrown
      //print avg velo 1
      if(avgV1 > 0)
         System.out.println("Fastball:    Avg  " + Math.round(this.avgV1 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp1 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt1 * 100.0)/100.0 + "\t\tT: " + this.t1);
      //print avg velo 2
      if(avgV2 > 0)
         System.out.println("Curveball:   Avg: " + Math.round(this.avgV2 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp2 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt2 * 100.0)/100.0 + "\t\tT: " + this.t2);
      //print avg velo 3
      if(avgV3 > 0)
         System.out.println("Slider:      Avg: " + Math.round(this.avgV3 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp3 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt3 * 100.0)/100.0 + "\t\tT: " + this.t3);
      //print avg velo 4
      if(avgV4 > 0)
         System.out.println("Change-up:   Avg: " + Math.round(this.avgV4 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp4 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt4 * 100.0)/100.0 + "\t\tT: " + this.t4);
      //print avg velo 5
      if(avgV5 > 0)
         System.out.println("Knuckleball: Avg: " + Math.round(this.avgV5 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp5 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt5 * 100.0)/100.0 + "\t\tT: " + this.t5);
      //print avg velo 6
      if(avgV6 > 0)
         System.out.println("Splitter:       Avg: " + Math.round(this.avgV6 * 100.0)/100.0 + "\t\tK%: " + Math.round(this.sp6 * 100.0)/100.0 + "\t\tPT: " + Math.round(this.pt6 * 100.0)/100.0 + "\t\tT: " + this.t6);
         
   }//ends printOuting
   
   //------------------------------------------------------------------------------
   
   protected void calcAll(){
      //get pitch count
      this.pitch_count = this.pitches.size();
      
      //count strikes
      strikes = 0;
      for(Pitch p: this.pitches){
         if(p.isStrike())
            this.strikes++;
      }
      
      //calc strike %
      this.sp = (double)this.strikes / (double)this.pitches.size();
      
      //calc each avg velo
      this.avgV1 = calcAvg(1);  
      this.avgV2 = calcAvg(2);  
      this.avgV3 = calcAvg(3);  
      this.avgV4 = calcAvg(4);  
      this.avgV5 = calcAvg(5);  
      this.avgV6 = calcAvg(6);
      
      //calc each strike %
      this.sp1 = calcSP(1);  
      this.sp2 = calcSP(2);  
      this.sp3 = calcSP(3);  
      this.sp4 = calcSP(4);  
      this.sp5 = calcSP(5);  
      this.sp6 = calcSP(6);
      
      //calc the top velo for each pitch
      this.t1 = calcT(1);  
      this.t2 = calcT(2);  
      this.t3 = calcT(3);  
      this.t4 = calcT(4);  
      this.t5 = calcT(5);  
      this.t6 = calcT(6);
      
      //calc the percent thrown for each pitch
      this.pt1 = calcPT(1);  
      this.pt2 = calcPT(2);  
      this.pt3 = calcPT(3);  
      this.pt4 = calcPT(4);  
      this.pt5 = calcPT(5);  
      this.pt6 = calcPT(6);
      
   }//ends calcAll
   
}//ends class