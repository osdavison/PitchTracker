//Pitch Tracker Driver Class
//@author Dylan Heid
//Has a SortedList of Pitchers
//Has a LList of Games
//-----------------------------------
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.awt.Color; 
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import javax.swing.JFrame;

public class PitchTrackerDriver{

	public static void main(String[] args){

		SortedList<Pitcher> pitchers = new SortedList<>();  //Linked list of Pitcher objects
		LList<Outing> Outings = new LList<>();
		Game game = new Game(Outings);  //Linked list of Game objects
		
		readCSV(pitchers, game);
		Outing a = Outings.get(0);
		a.calcAll();
//		System.out.println("Average velosity:\n"
//				+ a.avgV1 + "\n"
//				+ a.avgV2 + "\n"
//				+ a.avgV3 + "\n"
//				+ a.avgV4 + "\n"
//				+ a.avgV5 + "\n"
//				+ a.avgV6 + "\n"
//				+ "Pitch Count:\n"
//				+ a.pitch_count + "\n"
//				+ "percent thrown for each pitch:\n"
//				+ a.pt1 + "\n"
//				+ a.pt2 + "\n"
//				+ a.pt3 + "\n"
//				+ a.pt4 + "\n"
//				+ a.pt5 + "\n"
//				+ a.pt6 + "\n"
//				+ "strike %:\n"
//				+ a.sp + "\n"
//				+ a.sp2 + "\n"
//				+ a.sp3 + "\n"
//				+ a.sp4 + "\n"
//				+ a.sp5 + "\n"
//				+ a.sp6 + "\n"
//				+ "num Strikes\n"
//				+ a.strikes + "\n"
//				+ 
//				+ a.t1 + "\n"
//				+ a.t2 + "\n"
//				+ a.t3 + "\n"
//				+ a.t4 + "\n"
//				+ a.t5 + "\n"
//				+ a.t6 + "\n"
//				);
		
        CreateChart CC = new CreateChart("Chart", "Pitch percentages", game.outings.get(0).getPercentages());
        CC.pack();  //packs up everything into JFrame
        CC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CC.setVisible(true);
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
				}
				game.addOuting(outing);
				System.out.println("Total pitches: " + pitchesList.size);
				outing.calcAll();
				

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}//ends class