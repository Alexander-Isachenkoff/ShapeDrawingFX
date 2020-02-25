package logic;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static java.lang.Math.*;

public class Figure extends Group
{
    private double squareSize;
    private double squareDiagonal;
    
    public double getSquareDiagonal() {
        return squareDiagonal;
    }
    
    public void setSize(double size) {
        squareSize = size;
        squareDiagonal = sqrt(pow(size, 2));
        update();
    }
    
    public void setScale(double scale) {
        setScaleX(scale);
        setScaleY(scale);
    }
    
    public double getSquare() {
        double octagonSquare = 2 * pow(squareSize, 2) / (1 + sqrt(2));
        double r = squareSize / 2;
        double circleSquare = PI * r * r;
        return octagonSquare - circleSquare;
    }
    
    private void update() {
        Shape rectangle = createRectangle();
        Shape diamond = createDiamond();
        Shape circle = createCircle();
        Shape octagon = Shape.intersect(rectangle, diamond);
        octagon.setFill(Color.GRAY);
        getChildren().clear();
        getChildren().addAll(octagon, rectangle, diamond, circle);
    }
    
    private Shape createDiamond() {
        Shape diamond = createRectangle();
        diamond.setStroke(Color.DARKRED);
        diamond.setRotate(45);
        return diamond;
    }
    
    private Shape createRectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.STEELBLUE);
        rectangle.setX((squareDiagonal - squareSize) / 2);
        rectangle.setY((squareDiagonal - squareSize) / 2);
        rectangle.setWidth(squareSize);
        rectangle.setHeight(squareSize);
        return rectangle;
    }
    
    private Shape createCircle() {
        Circle circle = new Circle();
        circle.setStrokeWidth(2);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(squareDiagonal / 2);
        circle.setCenterY(squareDiagonal / 2);
        circle.setRadius(squareSize / 2);
        return circle;
    }
}
