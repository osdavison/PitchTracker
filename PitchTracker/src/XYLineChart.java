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

public class XYLineChart extends ApplicationFrame {

   public XYLineChart( String applicationTitle, String chartTitle, LList<Pitch> pitchlist) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Pitch #" ,
         "Velocity" ,
         createDataset(pitchlist) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 600 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesPaint( 3 , Color.BLUE );
      renderer.setSeriesPaint( 4 , Color.BLACK );
      renderer.setSeriesPaint( 5 , Color.ORANGE );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 3 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 4 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 5 , new BasicStroke( 4.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset(LList<Pitch> pitches) {
      //add fastballs
      final XYSeries fastball = new XYSeries( "Fastball" );
      final XYSeries curveball = new XYSeries( "Curveball" );
      final XYSeries slider = new XYSeries( "Slider" );
      final XYSeries changeup = new XYSeries( "Change-up" );
      final XYSeries knuckleball = new XYSeries( "Knuckleball" );
      final XYSeries splitter = new XYSeries( "Splitter" );
      
      //loop through pitch list and add each pitch to their respective list
      for(int i = 0; i < pitches.size(); i++){
    	  switch(pitches.get(i).getType()) {
    	  	case 1: fastball.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  	case 2: curveball.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  	case 3: slider.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  	case 4: changeup.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  	case 5: knuckleball.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  	case 6: splitter.add((i+1), pitches.get(i).getVelo());
    	  			break;
    	  }//ends switch
      }//ends for
      
      //add series to dataset
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( fastball );          
      dataset.addSeries( curveball );          
      dataset.addSeries( slider );
      dataset.addSeries( changeup );
      dataset.addSeries( knuckleball );;
      dataset.addSeries( splitter );;
      return dataset;
   }

}//ends class
