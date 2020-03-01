package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.StringConverter;
import logic.Figure;
import logic.SizeConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private static final double MIN_SIZE = 1;
    private static final double MAX_SIZE = 12.5;
    private final Figure figure = new Figure();
    
    // region FXML
    public Spinner<Double> sizeSpinner;
    public Slider scaleSlider;
    public TextField squareField;
    public Pane shapePane;
    public CheckBox fitToWindowCb;
    public VBox scalePane;
    // endregion
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSizeSpinner();
        scaleSlider.valueProperty().addListener(e -> update());
        shapePane.widthProperty().addListener(e -> update());
        shapePane.heightProperty().addListener(e -> update());
        shapePane.getChildren().add(figure);
        fitToWindowCb.selectedProperty().addListener(e -> updateViewMode());
    }
    
    private void initSizeSpinner() {
        double initialSize = (MAX_SIZE - MIN_SIZE) / 2;
        sizeSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN_SIZE, MAX_SIZE, initialSize, 0.1));
        sizeSpinner.valueProperty().addListener(e -> update());
        sizeSpinner.getEditor().setOnAction(e -> updateSpinnerEditor());
        sizeSpinner.focusedProperty().addListener(e -> updateSpinnerEditor());
    }
    
    private void updateSpinnerEditor() {
        TextField editor = sizeSpinner.getEditor();
        SpinnerValueFactory<Double> valueFactory = sizeSpinner.getValueFactory();
        StringConverter<Double> converter = valueFactory.getConverter();
        double newValue;
        try {
            newValue = converter.fromString(editor.getText());
            valueFactory.setValue(newValue);
        } catch (RuntimeException ex) {
            newValue = sizeSpinner.getValue();
            editor.setText(converter.toString(newValue));
        }
    }
    
    @FXML
    private void updateViewMode() {
        resetScale();
        scalePane.setDisable(fitToWindowCb.isSelected());
        sizeSpinner.setEditable(!fitToWindowCb.isSelected());
        update();
    }
    
    private void update() {
        repaintFigure();
        recalculateSquare();
    }
    
    private void repaintFigure() {
        updateFigureSize();
        updateFigureScale();
        updateFigureLocation();
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
    
    private void updateFigureLocation() {
        double x = (shapePane.getWidth() - figure.getSquareDiagonal()) / 2;
        double y = (shapePane.getHeight() - figure.getSquareDiagonal()) / 2;
        figure.setTranslateX(x);
        figure.setTranslateY(y);
    }
    
    private void clipFigure() {
        Shape clip = new Rectangle(shapePane.getWidth(), shapePane.getHeight());
        clip.setTranslateX(-figure.getTranslateX());
        clip.setTranslateY(-figure.getTranslateY());
        clip.setScaleX(1 / figure.getScaleX());
        clip.setScaleY(1 / figure.getScaleY());
        figure.setClip(clip);
    }
    
    private void recalculateSquare() {
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
