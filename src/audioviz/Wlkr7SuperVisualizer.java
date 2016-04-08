/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;




import static java.lang.Integer.min;
import static java.lang.Math.sin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author weixianlow
 */
public class Wlkr7SuperVisualizer implements Visualizer{
    
    private final String name = "Wlkr7SuperVisualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private String image;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minHeight = 10.0;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    
   
    
    private Rectangle[] rectangles;
    
    public Wlkr7SuperVisualizer(){
        
    }
    
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane)
    {
        end();
        
        image = Wlkr7SuperVisualizer.class.getResource("Wlkr7BackgroundPicture.jpg").toExternalForm();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        vizPane.setStyle("-fx-background-color: SILVER;");
        
        bandWidth = width / this.numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight/2;
        rectangles = new Rectangle[numBands];
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        
        for(int i=0;i<numBands;i++)
        {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(bandWidth*i);
            rectangle.setY(height/2 - sin(rectangle.getX()*100));
            rectangle.setWidth(bandWidth);
            rectangle.setHeight(minHeight);
            rectangle.setArcWidth(5);
            rectangle.setArcHeight(5);
            rectangle.setFill(Color.WHITE);
        
            vizPane.getChildren().add(rectangle);
            rectangles[i] = rectangle;
        }  
        
        vizPane.setStyle("-fx-background-image: url('"+image+"');"+ "-fx-background-position: center center; "+"-fx-background-repeat: no-repeat;" + "-fx-background-size: auto;");
        
    }
    
    @Override
    public void end(){
        if(rectangles != null)
        {
            for(Rectangle rectangle : rectangles)
            {
                vizPane.getChildren().remove(rectangle);
            }
            rectangles = null;
            vizPane.setStyle("");
        }
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases){
        if(rectangles == null)
        {
            return;
        }
        
        Integer num = min(rectangles.length, magnitudes.length);
        
        
        
        for(int i=0;i<num;i++)
        {
            rectangles[i].setHeight((60.0+magnitudes[i])*6 + minHeight);
           
            
        }
    }
    
}
