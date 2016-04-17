package at.fishkog.als.ui.common.renderer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class PannableCanvas extends Pane {

	DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	public Canvas canvasComp;
    
    public PannableCanvas(int w, int h) {
        setPrefSize(w, h);
        
        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
        
        this.canvasComp = new Canvas(w,h);
        
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
