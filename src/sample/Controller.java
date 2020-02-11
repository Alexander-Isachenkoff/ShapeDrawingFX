package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import logic.Figure;
import logic.SizeConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    public Spinner<Double> sizeSpinner;
    public Slider scaleSlider;
    public TextField squareField;
    public Pane shapePane;
    
    private Figure figure = new Figure();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sizeSpinner.setValueFactory(new SpinnerValueFactory
                .DoubleSpinnerValueFactory(1, 20, 2,0.1));
        sizeSpinner.getValueFactory().valueProperty().addListener(e -> update());
        scaleSlider.valueProperty().addListener(e -> update());
        shapePane.getChildren().add(figure);
        shapePane.widthProperty().addListener(e -> update());
        shapePane.heightProperty().addListener(e -> update());
    }
    
    private void update() {
        drawShape();
        updateSquare();
    }
    
    private void drawShape() {
        double cm = sizeSpinner.getValue();
        double size = SizeConverter.toPixels(cm);
        double scale = scaleSlider.getValue() / 100;
        figure.setScaleX(scale);
        figure.setScaleY(scale);
        figure.setSize(size);
        figure.setTranslateX((shapePane.getWidth() - figure.getDiagonal()) / 2);
        figure.setTranslateY((shapePane.getHeight() - figure.getDiagonal()) / 2);
    }
    
    private void updateSquare() {
        double square =
                Math.round(SizeConverter.squareToCm(figure.getSquare()) * 100) / (double) 100;
        squareField.setText(String.valueOf(square));
    }
}
