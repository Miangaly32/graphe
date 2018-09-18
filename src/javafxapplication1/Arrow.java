/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author mrandria
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author kn
 */
public class Arrow extends Path {

    private static final double defaultArrowHeadSize = 5.0;

    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize, Color c) {
        super();
        strokeProperty().bind(fillProperty());
        setFill(c);

        double angle = Math.toDegrees(Math.atan((endY - startY) / (endX - startX)));
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        startX = startX + (20 * cos);
        startY = startY + (20 * sin);
        endX = endX + (20 * cos);
        endY = endY + (20 * sin);

        //Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));

        //ArrowHead
        angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        sin = Math.sin(angle);
        cos = Math.cos(angle);

        //point1
        double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }

    public Arrow(double startX, double startY, double endX, double endY, Color c) {
        this(startX, startY, endX, endY, defaultArrowHeadSize, c);
    }
}
