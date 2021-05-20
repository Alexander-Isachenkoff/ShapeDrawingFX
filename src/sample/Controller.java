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
import logic.Rounding;
import logic.SizeConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private static final double MIN_SIZE = 1;
    private static final double MAX_SIZE = 12.7;
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
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN_SIZE, MAX_SIZE);
        sizeSpinner.setValueFactory(valueFactory);
        valueFactory.setAmountToStepBy(0.1);
        valueFactory.setConverter(new IgnoreSeparatorStringConverter("см"));
        valueFactory.setValue(initialSize);
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
        } catch (NumberFormatException ex) {
            newValue = sizeSpinner.getValue();
        }
        valueFactory.setValue(newValue);
        editor.setText(converter.toString(newValue));
    }
    
    @FXML
    private void updateViewMode() {
        resetScale();
        scalePane.setDisable(fitToWindowCb.isSelected());
        sizeSpinner.setEditable(!fitToWindowCb.isSelected());
        update();
    }
    
    private void update() {
        updateFigure();
        recalculateSquare();
    }
    
    private void updateFigure() {
        double size, scale;
        if (fitToWindowCb.isSelected()) {
            size = Math.min(shapePane.getWidth(), shapePane.getHeight()) / Math.sqrt(2);
            sizeSpinner.getValueFactory().setValue(SizeConverter.toCm(size));
            scale = 1;
        } else {
            size = SizeConverter.toPixels(sizeSpinner.getValue());
            scale = scaleSlider.getValue() / 100;
        }
        repaintFigure(size, scale);
    }
    
    private void repaintFigure(double size, double scale) {
        figure.setSize(size);
        figure.setScale(scale);
        updateFigureLocation();
        clipFigure();
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
        double accurateSquare = SizeConverter.squareToCm(figure.getSquare());
        double roundedSquare = Rounding.roundTo(accurateSquare, 3);
        squareField.setText(roundedSquare + " см²");
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
