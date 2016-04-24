package at.fishkog.als.ui.common;

import at.fishkog.als.ui.customNodes.Rubberband;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PannableCanvas extends Pane {

	public DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	  	
  	public SelectionModel selectModel = new SelectionModel();
  	public Rubberband rubberband;
	
    public PannableCanvas(int w, int h) {
        setPrefSize(w, h);
        
        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
        
        this.rubberband = new Rubberband(0,0,0,0);
		
        this.rubberband.setStroke(Color.LIGHTBLUE.darker());
        this.rubberband.setStrokeWidth(1);
        this.rubberband.setStrokeLineCap(StrokeLineCap.ROUND);
        this.rubberband.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
        
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale(double scale) {
        myScale.set(scale);
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }
}
