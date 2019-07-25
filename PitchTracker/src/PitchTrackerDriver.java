//Pitch Tracker Driver Class
//@author Dylan Heid
//Has a SortedList of Pitchers
//Has a LList of Games
//-----------------------------------
import java.util.Scanner;
import java.io.*;

public class PitchTrackerDriver{

	public static void main(String[] args){

		SortedList<Pitcher> pitchers = new SortedList<>();  //Linked list of Pitcher objects
		LList<Outing> Outings = new LList<>();
		Game game = new Game(Outings);  //Linked list of Game objects
		
		readCSV(pitchers, game);
	}//ends main

	//-----------------------------------------------------------------------------


	private static void readCSV(SortedList<Pitcher> pitchers, Game game)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File("PitchForm8DataSample.csv"));			
			while (scanner.hasNextLine())
			{
				String[] d = scanner.nextLine().split(",");
				Pitcher pitcher = new Pitcher(d[1], Integer.parseInt(d[0]));
				pitchers.add(pitcher);
				System.out.println(pitcher.getNameAndNum());
				LList<Pitch> pitchesList = new LList<Pitch>();
				Outing outing = new Outing(pitcher, pitchesList, null, false, null);
				for(int i = 3; i < d.length; i = i + 3)
				{
					if(d[i].equals(""))
					{
						//hacky way to skip blanks
						i = i - 2;
						continue;
					}
					
					Pitch pitch = new Pitch();
					//result,type,speed
					if(d[i].equals("Ball"))
					{
						pitch.setStrike(0);
					}
					else
					{
						pitch.setStrike(1);
					}
					
					switch(d[i + 1])
					{
					case "Fast Ball": 
						pitch.setType(1);
						break;
					case "Curve Ball": 
						pitch.setType(2);
						break;
					case "Slider": 
						pitch.setType(3);
						break;
					case "Change Up":
						pitch.setType(4);
						break;
					case "Knuckle Ball": 
						pitch.setType(5);
						break;
					case "Split": 
						pitch.setType(6);
						break;
					}

					pitch.setVelo(Integer.parseInt(d[i + 2]));
					System.out.println(pitch.getType() + " " + pitch.getVelo() + " " + pitch.isStrike());
					outing.addPitch(pitch);
					game.addOuting(outing);
				}
				System.out.println("Total pitches: " + pitchesList.size);
				outing.CalcPitches(pitchesList);
				

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}//ends class