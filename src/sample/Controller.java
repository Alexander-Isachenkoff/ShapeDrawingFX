package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import logic.Figure;
import logic.SizeConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    // region FXML
    public Spinner<Double> sizeSpinner;
    public Slider scaleSlider;
    public TextField squareField;
    public Pane shapePane;
    public CheckBox fitToWindowCb;
    public VBox scalePane;
    // endregion
    
    private Figure figure = new Figure();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sizeSpinner.setValueFactory(new SpinnerValueFactory
                .DoubleSpinnerValueFactory(1, 12.5, 4.5, 0.1));
        sizeSpinner.valueProperty().addListener(e -> update());
        scaleSlider.valueProperty().addListener(e -> update());
        shapePane.widthProperty().addListener(e -> update());
        shapePane.heightProperty().addListener(e -> update());
        shapePane.getChildren().add(figure);
        fitToWindowCb.selectedProperty().addListener(e -> updateViewMode());
    }
    
    @FXML
    private void updateViewMode() {
        resetScale();
        scalePane.setDisable(fitToWindowCb.isSelected());
        update();
    }
    
    private void update() {
        updateFigure();
        updateSquare();
    }
    
    private void updateFigure() {
        updateFigureSize();
        updateFigureScale();
        updateFigureTranslate();
        clipFigure();
    }
    
    private void updateFigureSize() {
        double size;
        if (fitToWindowCb.isSelected()) {
            size = Math.min(shapePane.getWidth(), shapePane.getHeight()) / Math.sqrt(2);
            sizeSpinner.getValueFactory().setValue(SizeConverter.toCm(size));
        } else {
            double cm = sizeSpinner.getValue();
            size = SizeConverter.toPixels(cm);
        }
        figure.setSize(size);
    }
    
    private void updateFigureScale() {
        double scale;
        if (fitToWindowCb.isSelected()) {
            scale = 1;
        } else {
            scale = scaleSlider.getValue() / 100;
        }
        figure.setScale(scale);
    }
    
    private void updateFigureTranslate() {
        double x = (shapePane.getWidth() - figure.getDiagonal()) / 2;
        double y = (shapePane.getHeight() - figure.getDiagonal()) / 2;
        figure.setTranslateX(x);
        figure.setTranslateY(y);
    }
    
    private void clipFigure() {
        Shape clip = new Rectangle(shapePane.getWidth() - 2, shapePane.getHeight() - 2);
        clip.setTranslateX(-figure.getTranslateX() + 1);
        clip.setTranslateY(-figure.getTranslateY() + 1);
        clip.setScaleX(1 / figure.getScaleX());
        clip.setScaleY(1 / figure.getScaleY());
        figure.setClip(clip);
    }
    
    private void updateSquare() {
        double square =
                Math.round(SizeConverter.squareToCm(figure.getSquare()) * 1000) / (double) 1000;
        squareField.setText(String.valueOf(square));
    }
    
    @FXML
    private void resetScale() {
        scaleSlider.setValue(100);
    }
    
    @FXML
    private void onScroll(ScrollEvent event) {
        if (scaleSlider.isDisabled()) {
            return;
        }
        if (event.getDeltaY() > 0) {
            scaleSlider.increment();
        } else {
            scaleSlider.decrement();
        }
    }
}
