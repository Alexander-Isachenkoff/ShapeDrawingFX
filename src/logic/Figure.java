package logic;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Figure extends Group
{
    private double size;
    private Rectangle rectangle = new Rectangle();
    private Rectangle diamond = new Rectangle();
    private Ellipse circle = new Ellipse();
    
    public Figure() {
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.STEELBLUE);
        getChildren().add(rectangle);
        diamond.setFill(Color.TRANSPARENT);
        diamond.setStroke(Color.DARKRED);
        getChildren().add(diamond);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);
    }
    
    public void setSize(double size) {
        this.size = size;
        update();
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
        rectangle.setX((d - size) / 2);
        rectangle.setY((d - size) / 2);
        rectangle.setWidth(size);
        rectangle.setHeight(size);
        
        diamond.setX((d - size) / 2);
        diamond.setY((d - size) / 2);
        diamond.setWidth(size);
        diamond.setHeight(size);
        diamond.setRotate(45);
        
        circle.setCenterX(d / 2);
        circle.setCenterY(d / 2);
        circle.setRadiusX(size / 2);
        circle.setRadiusY(size / 2);
    }
}
