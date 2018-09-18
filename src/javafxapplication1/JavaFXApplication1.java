/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import fonction.Dijkstra;
import graphe.Arc;
import graphe.Graphe;
import graphe.Noeud;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

/**
 *
 * @author mrandria
 */
public class JavaFXApplication1 extends Application {

    ObjectProperty<StackPane> lastUnconnectedNode = new SimpleObjectProperty<>();
    List<Noeud> sommets = new ArrayList();
    Noeud src = new Noeud();
    Noeud dest = new Noeud();

    @Override
    public void start(Stage stage) {

        //Creating a Group object  
        Group root = new Group();

        //Retrieving the observable list object 
        ObservableList list = root.getChildren();

        //Creating a Text object 
        Text title = new Text();
        //Setting font to the text 
        title.setFont(new Font(36));
        //setting the position of the text 
        title.setX(50);
        title.setY(50);
        //Setting the text to be added. 
        title.setText("Algorithm of Dijkstra");

        Button add = new Button();
        add.setText("1. Add node");
        add.setLayoutX(30);
        add.setLayoutY(100);

        Button arrange = new Button();
        arrange.setText("2. Arrange");
        arrange.setLayoutX(250);
        arrange.setLayoutY(100);

        Button addEdge = new Button();
        addEdge.setText("3. Add edge");
        addEdge.setLayoutX(450);
        addEdge.setLayoutY(100);

        Button dijkstra = new Button();
        dijkstra.setText("4. Shortest path");
        dijkstra.setLayoutX(650);
        dijkstra.setLayoutY(100);

        Button clear = new Button();
        clear.setText("5. Clear all");
        clear.setLayoutX(850);
        clear.setLayoutY(100);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TextInputDialog dialog = new TextInputDialog("A");
                dialog.setTitle("Enter node name");
                dialog.setHeaderText("Enter node name");
                dialog.setContentText("Please enter node name:");
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    Random r = new Random();
                    int x = r.nextInt((1000 - 100) + 1) + 100;
                    int y = r.nextInt((700 - 100) + 1) + 100;
                    Circle circle = new Circle(x, y, 20, Color.BISQUE);
//               
                    Text text2 = new Text(name);
                    text2.setBoundsType(TextBoundsType.VISUAL);

                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(circle, text2);
                    stack.setLayoutX(x);
                    stack.setLayoutY(y);

                    list.add(stack);
                });
            }
        });

        arrange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ObjectProperty<Point2D> mouseLoc = new SimpleObjectProperty<>();

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof StackPane) {
                        StackPane stack = (StackPane) list.get(i);
                        Circle circle = (Circle) stack.getChildren().get(0);
                        //get all stack and add drag event
                        stack.setOnMousePressed(ev -> mouseLoc.set(new Point2D(ev.getX(), ev.getY())));
                        stack.setOnMouseDragged(ev -> {
                            double deltaX = ev.getX() - mouseLoc.get().getX();
                            double deltaY = ev.getY() - mouseLoc.get().getY();

                            stack.setLayoutX(stack.getLayoutX() + deltaX);
                            stack.setLayoutY(stack.getLayoutY() + deltaY);
//                            System.out.println(">>" + circle);
                            circle.setCenterX(circle.getCenterX() + deltaX);
                            circle.setCenterY(circle.getCenterY() + deltaY);

                            mouseLoc.set(new Point2D(ev.getX(), ev.getY()));
                        });
                    }
                }

            }

        });

        addEdge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof StackPane) {
                        StackPane stack = (StackPane) list.get(i);
                        stack.setOnMousePressed(null);
                        stack.setOnMouseDragged(null);
                        stack.setOnMouseClicked(ev -> {
                            if (lastUnconnectedNode.get() == null) {
                                lastUnconnectedNode.set(stack);
                            } else {
                                connect(lastUnconnectedNode.get(), stack, Color.DARKBLUE, true);
                                Text text = (Text) stack.getChildren().get(1);
                                lastUnconnectedNode.set(null);
                            }
                        });
                    }
                }
            }
        });

        dijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                List<String> choices = new ArrayList<>(sommets.size());
                for (Noeud s : sommets) {
                    choices.add(s.getNom());
                }
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle("Choose source");
                dialog.setHeaderText("Choose the source");
                dialog.setContentText("Choose the source:");
                Optional<String> source = dialog.showAndWait();

                dialog.setTitle("Choose destination");
                dialog.setHeaderText("Choose the destination");
                dialog.setContentText("Choose the destination:");
                Optional<String> destination = dialog.showAndWait();

                source.ifPresent(s -> src.setNom(s));
                destination.ifPresent(d -> dest.setNom(d));

                for (Noeud n : sommets) {
                    if (n.equals(src)) {
                        src = n;
                    }
                    if (n.equals(dest)) {
                        dest = n;
                    }
                }

                Graphe graphe = new Graphe();
                graphe.setSommets(sommets);

                for (Noeud s : sommets) {
                    List<Arc> arcs = s.getArcs();
                    System.out.println(s.getNom());
                    for (Arc a : arcs) {
                        System.out.println(a.getA().getNom() + " - " + a.getB().getNom() + " : " + a.getDistance());
                    }
                    System.out.println();
                }

                Dijkstra dijkstra = new Dijkstra();
                Graphe pluscourtchemin = dijkstra.dijkstra(graphe, src, dest);
                List<Noeud> sommetsR = pluscourtchemin.getSommets();
                ListIterator<Noeud> iterator = sommetsR.listIterator(sommetsR.size());

                List<StackPane> circles = new ArrayList<StackPane>();

                while (iterator.hasPrevious()) {
                    Noeud n = iterator.previous();
                    System.out.print(n.getNom() + " - ");
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof StackPane) {
                            StackPane stack = (StackPane) list.get(i);
                            Text text = (Text) stack.getChildren().get(1);
                            if (text.getText().equals(n.getNom())) {
                                circles.add(stack);
                            }
                        }
                    }
                }

                for (int i = 0; i < circles.size() - 1; i++) {
                    connect(circles.get(i), circles.get(i + 1), Color.RED, false);
                }

            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                List recupList = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    if (!(list.get(i) instanceof StackPane)) {
                        recupList.add(list.get(i));
                    }
                }
                list.clear();
                list.addAll(recupList);
            }
        });

        //Setting the text object as a node to the group object 
        list.add(title);

        list.add(add);

        list.add(arrange);

        list.add(addEdge);

        list.add(dijkstra);

        list.add(clear);

        //Creating a scene object 
        Scene scene = new Scene(root, 1200, 900);

        //Setting title to the Stage 
        stage.setTitle(
                "Dijkstra");

        //Adding scene to the stage 
        stage.setScene(scene);

        //Displaying the contents of the stage 
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void connect(StackPane n1, StackPane n2, Color c, boolean popup) {
        if (n1.getParent() != n2.getParent()) {
            throw new IllegalArgumentException("Nodes are in different containers");
        }
        Group parent = (Group) n1.getParent();

        Circle c1 = (Circle) n1.getChildren().get(0);
        Circle c2 = (Circle) n2.getChildren().get(0);
        Text t1 = (Text) n1.getChildren().get(1);
        Text t2 = (Text) n2.getChildren().get(1);

        double startX = c1.getCenterX() + c1.getRadius();
        double startY = c1.getCenterY() + c1.getRadius();
        double endX = c2.getCenterX() + c2.getRadius();
        double endY = c2.getCenterY() + c2.getRadius();

        Arrow arrow = new Arrow(startX, startY, endX, endY, c);
        if (popup) {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Enter edge value");
            dialog.setHeaderText("Enter edge value");
            dialog.setContentText("Please enter edge value:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(value -> {
                Text text2 = new Text(value);
                text2.setBoundsType(TextBoundsType.VISUAL);
                text2.setX((startX + endX) / 2);
                text2.setY((startY + endY) / 2);

                // create nodes
                Noeud a = new Noeud(t1.getText());
                Noeud b = new Noeud(t2.getText());

                boolean dejavuA = false;
                boolean dejavuB = false;

                for (Noeud noeud1 : sommets) {
                    if (noeud1.getNom().equals(t1.getText())) {
                        for (Noeud noeud2 : sommets) {
                            if (noeud2.getNom().equals(t2.getText())) {
                                dejavuB = true;
                                noeud1.getArcs().add(new Arc(noeud1, noeud2, Integer.valueOf(text2.getText())));
                            }
                        }
                        if (!dejavuB) {
                            sommets.add(b);
                            noeud1.getArcs().add(new Arc(noeud1, b, Integer.valueOf(text2.getText())));
                        }
                        dejavuA = true;
                        break;
                    }
                }
                if (!dejavuA) {
                    sommets.add(a);
                    for (Noeud noeud2 : sommets) {
                        if (noeud2.getNom().equals(t2.getText())) {
                            dejavuB = true;
                            a.getArcs().add(new Arc(a, noeud2, Integer.valueOf(text2.getText())));
                        }
                    }
                    if (!dejavuB) {
                        sommets.add(b);
                        a.getArcs().add(new Arc(a, b, Integer.valueOf(text2.getText())));
                    }
                }

                parent.getChildren().add(text2);
                parent.getChildren().add(arrow);
            });
        } else {
            parent.getChildren().add(arrow);
        }
    }
}
