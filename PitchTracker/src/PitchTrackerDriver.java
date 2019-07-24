//Pitch Tracker Driver Class
//@author Dylan Heid
//Has a SortedList of Pitchers
//Has a LList of Games
//-----------------------------------
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

public class PitchTrackerDriver{

	public static void main(String[] args){

		readCSV();
		int option = -1;  //value holder for option
		SortedList<Pitcher> pitchers = new SortedList<>();  //Linked list of Pitcher objects
		LList<Game> games = new LList<>();  //Linked list of Game objects
		Scanner scan = new Scanner(System.in);
		String temp_string1 = ""; //temp string for holding values
		String temp_string2 = "";
		int temp_int1;

		//load data upon starting program
		try{
			loadData(games, pitchers);
		} catch (IOException ex) {
			System.err.println("IO Error Loading");
		}

		//debug
		System.out.println("Number of Games: " + games.size());
		System.out.println("Number of Pitchers: " + pitchers.size());        
		while(option != 0){
			option = getOption(scan);
			switch(option){
			case 0:  break;   //Quits
			case 1:  //Read in team name and if home
				System.out.print("Enter opposing team name: ");
				temp_string1 = scan.next();
				System.out.print("Enter 1 if game is at home (0 if away): ");
				temp_int1 = scan.nextInt();
				if(temp_int1 == 1){
					if(!startGame(temp_string1, true, pitchers, games, scan))
						System.out.println("Error adding game");
				}else{
					if(!startGame(temp_string1, false, pitchers, games, scan))
						System.out.println("Error adding game");
				}
				break;
			case 2:  //Read in pitcher
				System.out.print("Enter first name: ");
				temp_string1 = scan.next();
				System.out.print("Enter last name: ");
				temp_string2 = scan.next();
				System.out.print("Enter jersey #: ");
				temp_int1 = scan.nextInt();
				if(addPitcher(temp_string1 + " " + temp_string2, temp_int1, pitchers))
					System.out.println("Added");
				else
					System.out.println("Error Jersey # already taken");
				break;
			case 3:  //Look at a game
				if(games.size() == 0){
					System.out.println("\nNo Games\n");
					break;
				}
				printGames(games);
				System.out.print("Enter Game #: ");
				temp_int1 = scan.nextInt();
				Game g = games.get(temp_int1 - 1);
				g.viewPitchers();
				break;
			case 4:  //Look at a pitcher
				if(pitchers.size() == 0){
					System.out.println("\nNo Pitchers\n");
					break;
				}
				printPitchers(pitchers);
				System.out.print("Enter Pitcher #: ");
				temp_int1 = scan.nextInt();
				Pitcher p = pitchers.get(temp_int1 - 1);
				//look at that pitchers outings
				p.viewOutings();
				break;
			case 5:  //Edit a Game

				break;
			case 6:  //Edit a Pitcher
				printPitchers(pitchers);
				System.out.print("Enter Pitcher #: ");
				temp_int1 = scan.nextInt();
				Pitcher p2 = pitchers.get(temp_int1 - 1);
				//ask what to edit
				printPitcherEditOptions();
				break;
			case 7:  //Delete a Game

				break;
			case 8:  //Delete a Pitcher

				break;
			case 9:  //Save data
				try{
					saveData(games, pitchers);
				} catch (IOException ex) {
					System.err.println("IO Error Writing");
				} 
				break;

			default: //Wrong input
				System.out.println("Error invalid input");
				break;
			}//ends switch for choices

		}//ends choices loop
		System.out.println("GoodBye");
	}//ends main

	//-----------------------------------------------------------------------------

	private static int getOption(Scanner scan){
		System.out.println("0)  Quit");
		System.out.println("1)  Start new game");
		System.out.println("2)  Create new pitcher");
		System.out.println("3)  Look at a game");
		System.out.println("4)  Look at a pitcher");
		System.out.println("5)  Edit a game");
		System.out.println("6)  Edit a pitcher");
		System.out.println("7)  Delete a game");
		System.out.println("8)  Delete a pitcher");
		System.out.println("9)  Save data");
		System.out.print("Enter Choice: ");
		return scan.nextInt();
	}//ends displayOptions

	//-----------------------------------------------------------------------------

	private static void printPitchers(SortedList<Pitcher> pitchers){
		int i = 1;
		System.out.println("\n-------------------------------");
		for(Pitcher p: pitchers){
			System.out.println(i + ") " + p.getFirstName() + " " + p.getLastName() + " " + p.getNumber());
			i++;
		}
		System.out.println("-------------------------------\n");
	}//ends printPitchers

	//-----------------------------------------------------------------------------

	private static void printGames(LList<Game> games){
		int i = 1;
		System.out.println("\n-------------------------------");
		for(Game g: games){
			System.out.print(i + ") ");
			if(g.getHome())
				System.out.print("Home ");
			else
				System.out.print("Away ");
			System.out.println("against " + g.getTeam() + " " + g.getDate());
			i++;
		}
		System.out.println("-------------------------------\n");
	}//ends printGames

	//-----------------------------------------------------------------------------

	private static boolean startGame(String team, boolean home, SortedList<Pitcher> pitchers, LList<Game> games, Scanner scan){
		if(pitchers.isEmpty()){
			System.out.println("No pitchers available");
			return false;
		}   
		Game newGame = new Game(team, home);
		games.add(newGame);
		printPitchers(pitchers);
		int choice = 0;
		System.out.print("Enter starting pitcher (coresponding #): ");
		choice = scan.nextInt();
		while(choice <= 0 || choice > pitchers.size()){
			System.out.println("Invalid input");
			printPitchers(pitchers);
			System.out.print("Enter pitcher (coresponding #): ");
			choice = scan.nextInt();
		}//ends error loop
		//Adds starting pitcher's outing
		newGame.addOuting(pitchers.get(choice - 1), scan);
		//TODO
		int option = -1;
		while(option != 0){
			System.out.print("Enter 1 to add another pitcher (0 to finish game): ");
			option = scan.nextInt();
			if(option == 0){
				break;
			}
			else{
				printPitchers(pitchers);
				System.out.print("Enter pitcher (coresponding #): ");
				choice = scan.nextInt();
				while(choice <= 0 || choice > pitchers.size()){
					System.out.println("Invalid input");
					printPitchers(pitchers);
					System.out.print("Enter pitcher (coresponding #): ");
					choice = scan.nextInt();
				}//ends error loop
				newGame.addOuting(pitchers.get(choice - 1), scan);
			}//ends else
		}//while game still going

		return true;

	}//ends boolean startGame

	//-----------------------------------------------------------------------------

	private static boolean addPitcher(String Name, int num, SortedList<Pitcher> pitchers){
		//travers through SortedList of Pitchers to see if @param num matches
		//any pre-existing numbers
		Pitcher newPitcher = new Pitcher(Name, num);
		for(Pitcher p: pitchers){
			if(newPitcher.equals(p))
				return false;
		}//ends finding if pitcher is already in system

		//pitcher is unique so add
		pitchers.add(newPitcher);
		return true;
	}//ends addPitcher

	//-----------------------------------------------------------------------------

	private static void printPitcherEditOptions(){
		System.out.println("----------------------");
		System.out.println("1)Edit First Name");
		System.out.println("2)Edit Last Name");
		System.out.println("3)Edit Jersey #");
		System.out.println("0)Cancel");
		System.out.println("----------------------");
	}//ends printPitcherEditOptions

	//-----------------------------------------------------------------------------

	private static boolean deleteGame(LList<Game> games, Scanner scan){
		int choice = 0;
		int choice2 = 0;
		printGames(games);
		System.out.print("Enter game to delete: ");
		choice = scan.nextInt();
		if(choice < 1 || choice > games.size()){
			System.out.println("Game not found");
			return false;
		}
		System.out.print("Are you sure? Enter 1 if Yes / Enter 0 to cancel: ");
		if(choice2 == 0){
			System.out.println("Canceled");
			return false;
		}   
		games.remove(choice - 1); 
		return true;  
	}//ends deleteGame

	//-----------------------------------------------------------------------------

	private static boolean deletePitcher(SortedList<Pitcher> pitchers, Scanner scan){
		return false;
	}

	//-----------------------------------------------------------------------------

	private static void saveData(LList<Game> games, SortedList<Pitcher> pitchers) throws IOException{
		String pitchers_file = "Pitchers.txt";   //name of pitchers file
		String games_file = "Games.txt";      //name of games file

		//Save Pitchers
		File pfile = new File(pitchers_file);
		FileWriter pfw = new FileWriter(pfile);
		PrintWriter ppw = new PrintWriter(pfw);

		//write size of pitchers
		ppw.println(pitchers.size());
		for(Pitcher guy: pitchers){
			ppw.print(guy.getFirstName() + " ");
			ppw.print(guy.getLastName() + " ");
			ppw.println(guy.getNumber());
		}
		ppw.close();

		//Save Games
		File gfile = new File(games_file);
		FileWriter gfw = new FileWriter(gfile);
		PrintWriter gpw = new PrintWriter(gfw);

		//write size of games
		gpw.println(games.size());
		for(Game g: games){
			//print  team
			gpw.print(g.getTeam() + " ");
			//print home
			gpw.print(g.getHome() + " ");
			//print date
			gpw.println(g.getDate());
			//print num outings
			gpw.println(g.getNumOutings());
			//for each outing
			for(int i = 0; i < g.getNumOutings(); i++){
				gpw.print(g.outings.get(i).getFirstName() + " ");
				gpw.print(g.outings.get(i).getLastName() + " ");
				gpw.println(g.outings.get(i).getNumber());
				//print num pitches
				gpw.println(g.outings.get(i).getPitchCount());
				//for each pitch   
				for(int j = 0; j < g.outings.get(i).getPitchCount(); j++){
					gpw.print((j+1) + "\t");
					gpw.print(g.outings.get(i).pitches.get(j).getType() + " ");
					gpw.print(g.outings.get(i).pitches.get(j).isStrike() + " ");
					gpw.println(g.outings.get(i).pitches.get(j).getVelo());
				}
			}
		}//ends for each game
		gpw.close();

	}//ends saveData

	//-----------------------------------------------------------------------------


	private static void readCSV(/*LList<Game> games, SortedList<Pitcher> pitchers*/)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File("OldPitchFormDataSample.csv"));			
			while (scanner.hasNextLine())
			{
				String[] d = scanner.nextLine().split(",");
				Pitcher P = new Pitcher(d[1], Integer.parseInt(d[0]));
				System.out.println(P.getNameAndNum());
				Outing Out = new Outing(P, null, false, null);
				LList<Pitch> pitchesList = new LList<Pitch>();
				for(int i = 3; i < d.length; i = i + 3)
				{
					if(d[i].equals(""))
					{
						//hacky way to skip blanks
						i = i - 2;
						continue;
					}
						
					System.out.println(i);	
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
					case "Fastball": 
						p.setType(1);
						break;
					case "Curveball": 
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
					case "Splitter": 
						p.setType(6);
						break;
					}

					p.setVelo(Integer.parseInt(d[i + 2]));
					System.out.println(p.getType() + " " + p.getVelo() +" " + " " + p.isStrike());
					System.out.println(d[i + 2] + "\n");
					pitchesList.add(p);
				}
				Out.CalcPitches(pitchesList);
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	
	private static void loadData(LList<Game> games, SortedList<Pitcher> pitchers) throws IOException{
		Scanner scan;
		String pitchersFile = "Pitchers.txt";   //name of pitchers file
		String gamesFile = "Games.txt";      //name of games file

		int num_pitchers = 0;
		int num_games = 0;
		int num_outings = 0;
		int num_pitches = 0;
		String f_name = "";
		String l_name = "";
		int number = 0;
		String team = "";
		boolean home = false;
		LocalDate date;
		Pitcher pMan;
		int pitcher_number = 0;

		//data for each pitch
		int type = 0;
		boolean strike = false;
		int velo = 0;

		//Load Pitchers
		try {
			scan = new Scanner(new File(pitchersFile));
			//File now Opened
			num_pitchers = scan.nextInt();
			for(int i = 0; i < num_pitchers; i++){
				f_name = scan.next();
				l_name = scan.next();
				number = scan.nextInt();
				addPitcher(f_name + " " + l_name, number, pitchers);
			}
		} catch (Exception e) {
			System.err.println(pitchersFile + " was not found");
		}


		//Load Games
		try {
			scan = new Scanner(new File(gamesFile));
			//File now Opened**********************
			//read number of games
			num_games = scan.nextInt();
			//for every game... do this
			for(int i = 0; i < num_games; i++){
				//read current game
				team = scan.next();
				home = Boolean.parseBoolean(scan.next());
				date = LocalDate.parse(scan.next());
				//add game
				games.add(new Game(team, home, date));
				//read number of outings
				num_outings = scan.nextInt();
				//for every outing... do this
				for(int j = 0; j < num_outings; j++){
					//read pitcher
					f_name = scan.next();
					l_name = scan.next();
					number = scan.nextInt();
					Pitcher temp = new Pitcher(f_name, l_name, number);
					//find what pitcher threw
					for(int q = 0; q < pitchers.size(); q++){
						if(temp.equals(pitchers.get(q))){
							pitcher_number = q;
							break;
						}
					}
					//add outing to games
					games.get(i).outings.add(new Outing(pitchers.get(pitcher_number), team, home, date));
					//add outing to cooresponding pitchers
					pitchers.get(pitcher_number).addOuting(new Outing(pitchers.get(pitcher_number), team, home, date));
					//read number of pitches
					num_pitches = scan.nextInt();
					//for every pitch... do this
					for(int k = 0; k < num_pitches; k++){
						//read (but not really) pitch#
						scan.nextInt();
						//read type
						type = scan.nextInt();
						//read strike?
						strike = Boolean.parseBoolean(scan.next());
						//read velo
						velo = scan.nextInt();
						Pitch temp_pitch = new Pitch(type, strike, velo);
						//add pitch to games outings
						games.get(i).outings.get(j).pitches.add(temp_pitch);
						//add pitch to pitchers outings
						int temp_int = pitchers.get(pitcher_number).outings.size() - 1;
						pitchers.get(pitcher_number).outings.get(temp_int).pitches.add(temp_pitch);
					}//ends for every pitch
					pitchers.get(pitcher_number).reloadData();
					games.get(i).outings.get(j).calcAll();
				}//ends for every outing
			}//ends for every game
		} catch (IOException e) {
			System.err.println(gamesFile + " was not found");
		} catch (Exception e) {
			System.err.println("Exception Error");
		}

	}//ends loadData

}//ends class