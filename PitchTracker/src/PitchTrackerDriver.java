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
		Game games = new Game(Outings);  //Linked list of Game objects
		
		readCSV(pitchers, games);
	}//ends main

	//-----------------------------------------------------------------------------


	private static void readCSV(SortedList<Pitcher> pitchers, Game games)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File("PitchForm8DataSample.csv"));			
			while (scanner.hasNextLine())
			{
				String[] d = scanner.nextLine().split(",");
				Pitcher P = new Pitcher(d[1], Integer.parseInt(d[0]));
				pitchers.add(P);
				System.out.println(P.getNameAndNum());
				LList<Pitch> pitchesList = new LList<Pitch>();
				Outing Out = new Outing(P, pitchesList, null, false, null);
				for(int i = 3; i < d.length; i = i + 3)
				{
					if(d[i].equals(""))
					{
						//hacky way to skip blanks
						i = i - 2;
						continue;
					}
					
					Pitch p = new Pitch();
					//result,type,speed
					if(d[i].equals("Ball"))
					{
						p.setStrike(0);
					}
					else
					{
						p.setStrike(1);
					}
					
					switch(d[i + 1])
					{
					case "Fast Ball": 
						p.setType(1);
						break;
					case "Curve Ball": 
						p.setType(2);
						break;
					case "Slider": 
						p.setType(3);
						break;
					case "Change Up":
						p.setType(4);
						break;
					case "Knuckle Ball": 
						p.setType(5);
						break;
					case "Split": 
						p.setType(6);
						break;
					}

					p.setVelo(Integer.parseInt(d[i + 2]));
					System.out.println(p.getType() + " " + p.getVelo() + " " + p.isStrike());
					Out.addPitch(p);
					games.addOuting(Out);
				}
				Out.CalcPitches(pitchesList);
				
				

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}//ends class