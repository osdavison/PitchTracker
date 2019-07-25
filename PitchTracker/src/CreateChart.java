import javax.swing.JFrame;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.Rotation;

public class CreateChart extends JFrame{
	
	double[] percentages = new double[6];
	   
	   public CreateChart(String appTitle, String chartTitle, double[] ar){
	      for(int i = 0; i < 6; i++){
	         //System.out.println("Copying " + ar[i] + "into spot " + i);
	         percentages[i] = ar[i];
	      }
	      
	      PieDataset dataset = createDataset();
	      JFreeChart chart = createChart(dataset, chartTitle);
	      ChartPanel chartPanel = new ChartPanel(chart);
	      chartPanel.setPreferredSize(new java.awt.Dimension(1000,600));
	      setContentPane(chartPanel);
	      
	   }//ends Constructor
	   
	   private PieDataset createDataset(){
	      DefaultPieDataset result = new DefaultPieDataset();
	      System.out.println("First position: " + percentages[0]);
	      result.setValue("Fastball", percentages[0]);    //values do not need to add up to 100
	      result.setValue("Curveball", percentages[1]);
	      result.setValue("Slider", percentages[2]);
	      result.setValue("Change-up", percentages[3]);
	      result.setValue("Knuckleball", percentages[4]);
	      result.setValue("Splitter", percentages[5]);
	      return result;
	   }//ends createDataset
	   
	   private JFreeChart createChart(PieDataset dataset, String title){
	      JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, false, true, false);
	      
	      PiePlot3D plot = (PiePlot3D) chart.getPlot();
	      plot.setDirection(Rotation.CLOCKWISE);
	      plot.setForegroundAlpha(0.5f);
	      return chart;
	      
	   }//ends createChart
	   
}
