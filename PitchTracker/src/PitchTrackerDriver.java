//Pitch Tracker Driver Class
//@author Dylan Heid, Andrew Pressman, Seth Davison
//Has a SortedList of Pitchers
//Has a LList of Games
//-----------------------------------
import java.util.Scanner;
import java.io.*;
import org.jfree.ui.RefineryUtilities;

public class PitchTrackerDriver{

	public static void main(String[] args){

		SortedList<Pitcher> pitchers = new SortedList<>();  //Linked list of Pitcher objects
		LList<Outing> Outings = new LList<>();
		Game game = new Game(Outings);  //Linked list of Game objects

		Scanner scan = new Scanner(System.in);
		boolean isFile = false;
		while(!isFile)
		{
			System.out.print("Enter Full .csv file path:");
			String input = scan.nextLine();
			try 
			{
				isFile = readCSV(pitchers, game, input);
			}
			catch(Exception E)
			{
				System.out.println("Error: " + E.getMessage());
				isFile = false;
			}

		}
		
		System.out.println("Reading File: \n ... \n ... \n");
		Outing outing = Outings.get(0);
		outing.calcAll();
		int option = -1;
		
		System.out.println("Number of Pitchers: " + pitchers.size());        
		while(option != 0){
			option = getOption(scan, game);
			switch(option){
			case 0:
				System.out.println("Goodbye!");
				break;   //Quits
			case 1: 
				showTypeChart(game);
				break;
			case 2:
				showVelocityChart(game);
				break;
			case 3:
				game.outings.get(0).printPitches();
				break;
			case 4:
				System.out.println("Not in the Office");
				break;
			}
		}

		System.exit(0);
		
	}//ends main

	//-----------------------------------------------------------------------------

	
	private static int getOption(Scanner scan, Game game) {
		int input = 0;
		String I;
		System.out.println("Total pitches: " + game.outings.get(0).getPitchCount());
		System.out.println("0)  Quit");
		System.out.println("1)  Show pitch type graph");
		System.out.println("2)  Show pitch velocity graph");
		System.out.println("3)  Print pitches");
		System.out.print("Enter Choice: ");
		I = scan.nextLine();
		try
		{
			input = Integer.parseInt(I);
		}
		catch(Exception E)
		{
			if(I.equals("Where is Dan?"))
				input = 4;
			else
			{
			input = 5;
			System.out.println("Error: No Command " + E.getMessage());				
			}

		}
		
		return input;
	}

	private static void showTypeChart(Game game) {
		CreateChart chart = new CreateChart("Chart", "Pitch percentages", game.outings.get(0).getPercentages());
		chart.pack();  //packs up everything into JFrame
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		chart.setAlwaysOnTop(true);
	}

	
	private static void showVelocityChart(Game game) {
		XYLineChart chart = new XYLineChart("Pitch Graph", "Pitch type velocity graph", game.outings.get(0).getPitchList());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		chart.setAlwaysOnTop(true);
	}


	private static boolean readCSV(SortedList<Pitcher> pitchers, Game game, String Path)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File(Path));			
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

					
					if(d[i].equals("BLANK")) {
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
					outing.addPitch(pitch);

					
				}//ends for
				game.addOuting(outing);
				outing.calcAll();

			}//ends while

			scanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}

		return true;
	}//ends readcsv

}//ends class
