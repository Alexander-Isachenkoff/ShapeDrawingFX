package logic;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Figure extends Group
{
    private double size;
    
    public void setSize(double size) {
        this.size = size;
        update();
    }
    
    public void setScale(double scale) {
        setScaleX(scale);
        setScaleY(scale);
    }
    
    public double getDiagonal() {
        return Math.sqrt(2 * size * size);
    }
    
    public double getSquare() {
        final double trianglesSquare = size * size * (3 - 2 * Math.sqrt(2));
        final double r = size / 2.0;
        final double circleSquare = Math.PI * r * r;
        return size * size - (trianglesSquare + circleSquare);
    }
    
    private void update() {
        double d = getDiagonal();
        Shape rectangle = createRectangle(d);
        Shape diamond = createDiamond(d);
        Shape circle = createCircle(d);
        Shape octagon = Shape.subtract(Shape.intersect(rectangle, diamond), circle);
        octagon.setFill(Color.GRAY);
        getChildren().clear();
        getChildren().addAll(octagon, rectangle, diamond, circle);
    }
    
    private Shape createRectangle(double d) {
        Rectangle rectangle = new Rectangle();
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.STEELBLUE);
        rectangle.setX((d - size) / 2);
        rectangle.setY((d - size) / 2);
        rectangle.setWidth(size);
        rectangle.setHeight(size);
        return rectangle;
    }
    
    private Shape createDiamond(double d) {
        Rectangle diamond = new Rectangle();
        diamond.setStrokeWidth(2);
        diamond.setFill(Color.TRANSPARENT);
        diamond.setStroke(Color.DARKRED);
        diamond.setX((d - size) / 2);
        diamond.setY((d - size) / 2);
        diamond.setWidth(size);
        diamond.setHeight(size);
        diamond.setRotate(45);
        return diamond;
    }
    
    private Shape createCircle(double d) {
        Circle circle = new Circle();
        circle.setStrokeWidth(2);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(d / 2);
        circle.setCenterY(d / 2);
        circle.setRadius(size / 2);
        return circle;
    }
}
